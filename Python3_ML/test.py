import argparse
import csv
import time

import pandas as pd
from sklearn import svm
from sklearn import tree
from pandas import read_csv
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import ExtraTreesClassifier
from sklearn.neural_network import MLPClassifier
from sklearn.neighbors import NearestNeighbors, KNeighborsClassifier
from sklearn.ensemble import AdaBoostClassifier
from sklearn.naive_bayes import GaussianNB

parser = argparse.ArgumentParser(description="Prediction from csv file.")
parser.add_argument("-i", "--input", metavar="csvfile", help="Input csv file", type=str)
parser.add_argument("-m", "--model", metavar="modeloption",
                    help="choose your option (ex: dt, svm, nn, nb, svm_reg, rf, et, ab, knn, ao)", type=str)
parser.add_argument("-e", "--estimators", metavar="estimators ", help="choose a value (10 by default)", default=10, type=int)
parser.add_argument("-k", "--kneighbors", metavar="kneighbors ", help="choose a value (3 by default)", default=3, type=int)
parser.add_argument("-o", "--output", help="Directs the output to a name of your choice", type=str)
parser.add_argument("-a", "--all", action='store_true', help="All model")

args = parser.parse_args()
in_file = args.input
model_option = args.model
output = args.output
model_estimator = args.estimators
model_kneighbors = args.kneighbors
tb_data = pd.read_csv(in_file)

# tb_data = pd.read_csv('tb_file.csv')
# X = tb_data.drop(columns=['ID', 'Type', 'Country'])
# y = tb_data['Type']
# X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)
X = tb_data.drop(columns=['ID', 'Type'])
y = tb_data['Type']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

# df = read_csv('simpiTB_1474_strains.csv')
# df = pd.DataFrame(raw_data)
# print(df.head())


# To select rows whose column value is in an iterable array, which we'll define as array, you can use
array = ['ND', 'Unknown', 'unknown']
# print(tb_data.isin(array))
# print(df.loc[df['favorite_color'].isin(array)])
# print(df.loc[df['Type'].isin(array)])

results = []
#testfile = tb_data.drop(columns=['ID', 'Type', 'Country'])
testfile = tb_data.drop(columns=['ID', 'Type'])
reader = csv.reader(testfile, quoting=csv.QUOTE_NONNUMERIC)  # change contents to floats
print(reader)
for line in testfile:
    results.append(line)

print(results)

debut = time.perf_counter()
# dt
if model_option == "dt":
    array = ['ND', 'Unknown', 'unknown']
    tb_data.isin(array)
    x_d = tb_data.loc[tb_data['Type'].isin(array)].drop(columns=['ID', 'Type'])
    model_option = DecisionTreeClassifier()
    model_option.fit(X_train, y_train)
    tree.export_graphviz(model_option, out_file='graph.dot',
                         feature_names=['var1', 'var2', 'var3', 'var4', 'var5', 'var6', 'var7', 'var8', 'var9',
                                        'var10',
                                        'var11', 'var12', 'var13', 'var14', 'var15', 'var16', 'var17', 'var18',
                                        'var19',
                                        'var20',
                                        'var21', 'var22', 'var23', 'var24', 'var25', 'var26', 'var27', 'var28',
                                        'var29',
                                        'var30',
                                        'var31', 'var32', 'var33', 'var34', 'var35', 'var36', 'var37', 'var38',
                                        'var39',
                                        'var40',
                                        'var41', 'var42', 'var43'],
                         class_names=sorted(y.unique()),
                         label='all',
                         rounded=True,
                         filled=True)
    predictions = model_option.predict(X_test)
    prediction2 = model_option.predict(x_d)

    score_dt = accuracy_score(y_test, predictions)
    fin_dt = time.perf_counter()
    print("Prediction of X_test: ", predictions)
    print("Prediction of 2 spoligo: ", prediction2)
    print("dt score :")
    print(score_dt)
    df = pd.DataFrame([('Score', score_dt),
                       ('Temp(s)', f'{fin_dt - debut:0.4}')],
                      columns=('Metrics/Models', 'DT')
                      )
    df.to_csv(output, index=False)
    # f.write(fin)
# dt

# svm
elif model_option == "svm":
    # X = [[0, 0], [1, 1]]
    # y = [0, 1]
    array = ['ND', 'Unknown', 'unknown']
    tb_data.isin(array)
    #x_d = tb_data.loc[tb_data['Type'].isin(array)].drop(columns=['ID', 'Type', 'Country'])
    x_d = tb_data.loc[tb_data['Type'].isin(array)].drop(columns=['ID', 'Type'])
    #print(x_d)
    #X = tb_data.drop(columns=['ID', 'Type', 'Country'])
    # X = x_d.drop(columns=['ID', 'Type', 'Country'])
    # y = x_d['Type']
    #print(X)
    clf = svm.SVC()
    #print(X_test)
    clf.fit(X, y)
    # SVC()

    predictions = clf.predict(X_test)
    svm_prediction = clf.predict(x_d)
    score_svm = accuracy_score(y_test, predictions)
    fin_svm = time.perf_counter()
    print("Predictions1 :", predictions)
    print("Prediction of 2 spoligo with svm: ", svm_prediction)
    print("svm score :")
    print(score_svm)
    df = pd.DataFrame([('Score', score_svm),
                       ('Temp(s)', f'{fin_svm - debut:0.4}')],
                      columns=('Metrics/Models', 'SVM')
                      )
    df.to_csv(output, index=False)
# svm

# rf (not in oa model)
elif model_option == "rf":
    # X = [[0, 0], [1, 1]]
    # y = [0, 1]
    # X = tb_data.drop(columns=['ID','Type','Country'])
    # y = tb_data['Type']
    # clf = RandomForestClassifier(n_estimators=10)
    array = ['ND', 'Unknown', 'unknown']
    tb_data.isin(array)
    x_d = tb_data.loc[tb_data['Type'].isin(array)].drop(columns=['ID', 'Type'])
    clf = RandomForestClassifier(n_estimators=model_estimator)
    clf.fit(X, y)
    # SVC()
    predictions = clf.predict(X_test)
    rf_prediction = clf.predict(x_d)
    score_rf = accuracy_score(y_test, predictions)
    fin_rf = time.perf_counter()
    print("Prediction of 2 spoligo with rf: ", rf_prediction)
    print(score_rf)
    df = pd.DataFrame([('Score', score_rf),
                       ('Temp(s)', f'{fin_rf - debut:0.4}')],
                      columns=('Metrics/Models', 'RF')
                      )
    df.to_csv(output, index=False)
# rf

# et (not in oa model)
elif model_option == "et":
    # clf = ExtraTreesClassifier(n_estimators=10)
    array = ['ND', 'Unknown', 'unknown']
    tb_data.isin(array)
    x_d = tb_data.loc[tb_data['Type'].isin(array)].drop(columns=['ID', 'Type'])
    clf = ExtraTreesClassifier(n_estimators=model_estimator)
    clf.fit(X, y)
    predictions = clf.predict(X_test)
    rf_prediction = clf.predict(x_d)
    score_et = accuracy_score(y_test, predictions)
    fin_et = time.perf_counter()
    print("Prediction of 2 spoligo with rf: ", rf_prediction)
    print(score_et)
    df = pd.DataFrame([('Score', score_et),
                       ('Temp(s)', f'{fin_et - debut:0.4}')],
                      columns=('Metrics/Models', 'ET')
                      )
    df.to_csv(output, index=False)
# scores = cross_val_score(clf, X, y, cv=5)
# print(scores.mean())
# et

# all_options
if args.all == True:
    array = ['ND', 'Unknown', 'unknown']
    tb_data.isin(array)
    x_d = tb_data.loc[tb_data['Type'].isin(array)].drop(columns=['ID', 'Type'])
    debut_dt = time.perf_counter()
    model_option = DecisionTreeClassifier()
    model_option.fit(X_train, y_train)
    tree.export_graphviz(model_option, out_file='graph.dot',
                         feature_names=['var1', 'var2', 'var3', 'var4', 'var5', 'var6', 'var7', 'var8', 'var9',
                                        'var10',
                                        'var11', 'var12', 'var13', 'var14', 'var15', 'var16', 'var17', 'var18',
                                        'var19',
                                        'var20',
                                        'var21', 'var22', 'var23', 'var24', 'var25', 'var26', 'var27', 'var28',
                                        'var29',
                                        'var30',
                                        'var31', 'var32', 'var33', 'var34', 'var35', 'var36', 'var37', 'var38',
                                        'var39',
                                        'var40',
                                        'var41', 'var42', 'var43'],
                         class_names=sorted(y.unique()),
                         label='all',
                         rounded=True,
                         filled=True)
    predictions = model_option.predict(X_test)
    prediction2 = model_option.predict(x_d)

    score_dt = accuracy_score(y_test, predictions)
    fin_dt = time.perf_counter()
    print("Prediction of X_test: ", predictions)
    print("Prediction of 2 spoligo: ", prediction2)
    print("dt score :")
    print(score_dt)
    print(f" L'option NearestNeighbors {fin_dt - debut_dt:0.4f} secondes")

    # dt
    # svm
    # X = [[0, 0], [1, 1]]
    # y = [0, 1]
    debut_svm = time.perf_counter()
    #X = tb_data.drop(columns=['ID', 'Type', 'Country'])
    #y = tb_data['Type']
    clf = svm.SVC()
    clf.fit(X, y)
    # SVC()

    predictions = clf.predict(X_test)
    svm_prediction = clf.predict(x_d)

    score_svm = accuracy_score(y_test, predictions)
    fin_svm = time.perf_counter()
    print("Prediction of 2 spoligo with svm: ", svm_prediction)
    print("svm score :")
    print(score_svm)
    print(f" L'option svm {fin_svm - debut_svm:0.4f} secondes")
    # svm

    # nn
    # X = [[0, 0], [1, 1]]
    # y = [0, 1]
    debut_nn = time.perf_counter()
    #X = tb_data.drop(columns=['ID', 'Type', 'Country'])
    #y = tb_data['Type']
    clf = MLPClassifier(solver='lbfgs', alpha=1e-5, hidden_layer_sizes=(5, 2), random_state=1)
    clf.fit(X, y)
    # MLPClassifier(alpha=1e-05, hidden_layer_sizes=(5, 2), random_state=1,solver='lbfgs')
    predictions = clf.predict(X_test)
    nn_prediction = clf.predict(x_d)

    score_nn = accuracy_score(y_test, predictions)
    fin_nn = time.perf_counter()
    print("Prediction of 2 spoligo with nn: ", nn_prediction)
    print("nn score :")
    print(score_nn)
    print(f" L'option nn {fin_nn - debut_nn:0.4f} secondes")
    # nn

    # nb
    # X, y = load_iris(return_X_y=True)
    debut_nb = time.perf_counter()
    #X = tb_data.drop(columns=['ID', 'Type', 'Country'])
    #y = tb_data['Type']
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.5, random_state=0)
    gnb = GaussianNB()
    y_pred = gnb.fit(X_train, y_train).predict(X_test)
    score_nb = accuracy_score(y_test, y_pred)
    fin_nb = time.perf_counter()
    print("Number of mislabeled points out of a total %d points : %d" % (X_test.shape[0], (y_test != y_pred).sum()))
    print("nb score :")
    print(score_nb)
    print(f" L'option Nayve Bayes {fin_nb - debut_nb:0.4f} secondes")
    # nb

    # rf (not in oa model)
    debut_rf = time.perf_counter()
    # X = [[0, 0], [1, 1]]
    # y = [0, 1]
    # X = tb_data.drop(columns=['ID','Type','Country'])
    # y = tb_data['Type']
    # clf = RandomForestClassifier(n_estimators=10)
    clf = RandomForestClassifier(n_estimators=model_estimator)
    clf.fit(X, y)
    # SVC()
    predictions = clf.predict(X_test)
    rf_prediction = clf.predict(x_d)
    score_rf = accuracy_score(y_test, predictions)
    fin_rf = time.perf_counter()
    print("Prediction of 2 spoligo with rf: ", rf_prediction)
    print(score_rf)

    # rf

    # et (not in oa model)
    # clf = ExtraTreesClassifier(n_estimators=10)
    debut_et = time.perf_counter()
    clf = ExtraTreesClassifier(n_estimators=model_estimator)
    clf.fit(X, y)
    predictions = clf.predict(X_test)
    rf_prediction = clf.predict(x_d)
    score_et = accuracy_score(y_test, predictions)
    fin_et = time.perf_counter()
    print("Prediction of 2 spoligo with rf: ", rf_prediction)
    print(score_et)
    # scores = cross_val_score(clf, X, y, cv=5)
    # print(scores.mean())
    # et

    # ab
    debut_ab = time.perf_counter()
    clf = AdaBoostClassifier(n_estimators=model_estimator)
    clf.fit(X, y)
    predictions = clf.predict(X_test)
    ab_prediction = clf.predict(x_d)
    score_ab = accuracy_score(y_test, predictions)
    fin_ab = time.perf_counter()
    print("Prediction of 2 spoligo with ab: ", ab_prediction)
    print(score_ab)
    print(f" L'option ab {fin_ab - debut_ab:0.4f} secondes")
    # ab

    # knn
    debut_knn = time.perf_counter()
    clf = KNeighborsClassifier(n_neighbors=model_kneighbors)
    clf.fit(X, y)
    predictions = clf.predict(X_test)
    knn_prediction = clf.predict(x_d)
    score_knn = accuracy_score(y_test, predictions)
    fin_knn = time.perf_counter()
    print("Prediction of 2 spoligo with knn: ", knn_prediction)
    print(score_knn)
    print(f" L'option Nayve Bayes {fin_knn - debut_knn:0.4f} secondes")
    # knn
    df = pd.DataFrame([('Score', score_dt, score_rf, score_et, score_ab, score_svm, score_nn, score_nb, score_knn),
                       ('Temp(s)', f'{fin_dt - debut_dt:0.4}', f'{fin_rf - debut_rf:0.4}', f'{fin_et - debut_et:0.4}', f'{fin_ab - debut_ab:0.4}', f'{fin_svm - debut_svm: 0.4}',
                        f'{fin_nn - debut_nn:0.4}', f'{fin_nb - debut_nb:0.4}',
                        f'{fin_knn - debut_knn:0.4}')],
                      columns=('Metrics/Models', 'DT', 'RF', 'ET', 'AB', 'SVM', 'NN', 'NB', 'KNN')
                      )
    df.to_csv(output, index=False)
# all_options
fin = time.perf_counter()
print(f" Le script à tourné durant {fin - debut:0.4f} secondes")


