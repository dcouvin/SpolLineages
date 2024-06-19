#!/usr/bin/env python3

import subprocess
import sys
import pandas as pd
import numpy as np
import argparse
import time
import csv
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn import svm
from sklearn.naive_bayes import GaussianNB
# from sklearn.externals import joblib
from sklearn import tree
from sklearn.neural_network import MLPClassifier
from sklearn.neighbors import NearestNeighbors, KNeighborsClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import ExtraTreesClassifier
from sklearn.ensemble import AdaBoostClassifier

parser = argparse.ArgumentParser(description="Prediction from csv file.")
parser.add_argument("-i", "--input", metavar="csvfile", help="Input csv file", type=str)
parser.add_argument("-m", "--model", metavar="modeloption",
                    help="choose your option (ex: dt, svm, nn, nb, svm_reg, rf, et, ab, knn, ao)", type=str)
parser.add_argument("-d", "--delimiter", metavar="delimiter",
                    help="choose your CSV files's delimiter (ex: ',', ';', '\t', ...)", type=str)
parser.add_argument("-r", "--remove", metavar="remove",
                    help="remove CSV files's columns for prediction (ex: 'ID','Type','Country',...)", type=str)
parser.add_argument("-l", "--label", metavar="label",
                    help="Class or label used for prediction (ex: 'Type')", type=str)
parser.add_argument("-e", "--estimators", metavar="estimators ", help="choose a value (10 by default)", default=10, type=int)
parser.add_argument("-k", "--kneighbors", metavar="kneighbors ", help="choose a value (3 by default)", default=3, type=int)
parser.add_argument("-o", "--output", help="Directs the output to a name of your choice", type=str)
parser.add_argument("-a", "--all", action='store_true', help="All model")

args = parser.parse_args()
in_file = args.input
model_option = args.model
output = args.output
delim = args.delimiter
remove = args.remove
list = list(remove.split(","))
cl = args.label
model_estimator = args.estimators
model_kneighbors = args.kneighbors

tb_data = pd.read_csv(in_file, delimiter=delim, engine='python')
tb_data = tb_data.dropna()

# tb_data = pd.read_csv('tb_file.csv')
X = tb_data.drop(columns=[remove]) #Type = Lineage ; Country = Family
y = tb_data[cl] # Lineage / Family
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

# test table for prediction
tab = [
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
     1, 1, 1, 1, 1], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
     0, 0, 1, 1, 1]]
#tab = [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 7, 1],
 #            [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1],
  #           [7, 7, 3, 7, 7, 7, 0, 3, 1, 1, 1, 0, 0, 0, 1]]

# sys.stdout = open(output, "w")
# header = "Name\Age\BirthYear"
report = open(output, "w")  # csvfile

with report as f:
    debut = time.perf_counter()
    # dt
    if model_option == "dt":
        model_option = DecisionTreeClassifier()
        model_option.fit(X_train, y_train)
        #tree.export_graphviz(model_option, out_file='graph.dot',
         #                    feature_names=['var1', 'var2', 'var3', 'var4', 'var5', 'var6', 'var7', 'var8', 'var9',
          #                                  'var10',
           #                                 'var11', 'var12', 'var13', 'var14', 'var15', 'var16', 'var17', 'var18',
            #                                'var19',
             #                               'var20',
              #                              'var21', 'var22', 'var23', 'var24', 'var25', 'var26', 'var27', 'var28',
               #                             'var29',
                #                            'var30',
                 #                           'var31', 'var32', 'var33', 'var34', 'var35', 'var36', 'var37', 'var38',
                  #                          'var39',
                   #                         'var40',
                    #                        'var41', 'var42', 'var43'],
                     #        class_names=sorted(y.unique()),
                      #       label='all',
                       #      rounded=True,
                        #     filled=True)
        predictions = model_option.predict(X_test)
        #prediction2 = model_option.predict(
         #  [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 7, 1],
          #   [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1],
           #  [7, 7, 3, 7, 7, 7, 0, 3, 1, 1, 1, 0, 0, 0, 1]])

        score_dt = accuracy_score(y_test, predictions)
        fin_dt = time.perf_counter()
        print("Prediction of X_test: ", predictions)
       # print("Prediction of some spoligos: ", prediction2)
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
        #X = tb_data.drop(columns=['ID', 'Lineage', 'Family'])
        #y = tb_data['Family']
        clf = svm.SVC()
        clf.fit(X, y)
        # SVC()

        predictions = clf.predict(X_test)
        #svm_prediction = clf.predict(
         #   [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 7, 1],
          #   [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1],
           #  [7, 7, 3, 7, 7, 7, 0, 3, 1, 1, 1, 0, 0, 0, 1]])
        score_svm = accuracy_score(y_test, predictions)
        fin_svm = time.perf_counter()
        #print("Prediction of some spoligo with svm: ", svm_prediction)
        print("svm score :")
        print(score_svm)
        df = pd.DataFrame([('Score', score_svm),
                           ('Temp(s)', f'{fin_svm - debut:0.4}')],
                          columns=('Metrics/Models', 'SVM')
                          )
        df.to_csv(output, index=False)
    # svm

    # nn
    elif model_option == "nn":
        # X = [[0, 0], [1, 1]]
        # y = [0, 1]
        #X = tb_data.drop(columns=['ID', 'Lineage', 'Family'])
        #y = tb_data['Family']
        clf = MLPClassifier(solver='lbfgs', alpha=1e-5, hidden_layer_sizes=(5, 2), random_state=1)
        clf.fit(X, y)
        # MLPClassifier(alpha=1e-05, hidden_layer_sizes=(5, 2), random_state=1,solver='lbfgs')
        predictions = clf.predict(X_test)
        #nn_prediction = clf.predict(
            #[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            #  0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1],
            # [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            #  0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1],
            # [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0,
            #  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1]])
            #[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 7, 1],
            # [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1],
            # [7, 7, 3, 7, 7, 7, 0, 3, 1, 1, 1, 0, 0, 0, 1]])
        score_nn = accuracy_score(y_test, predictions)
        fin_nn = time.perf_counter()
        #print("Prediction of 2 spoligo with nn: ", nn_prediction)
        print("nn score :")
        print(score_nn)
        df = pd.DataFrame([('Score', score_nn),
                           ('Temp(s)', f'{fin_nn - debut:0.4}')],
                          columns=('Metrics/Models', 'NN')
                          )
        df.to_csv(output, index=False)
    # nn

    # nb
    elif model_option == "nb":
        # X, y = load_iris(return_X_y=True)
        #X = tb_data.drop(columns=['ID', 'Lineage', 'Family'])
        #y = tb_data['Family']
        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=0)
        gnb = GaussianNB()
        y_pred = gnb.fit(X_train, y_train).predict(X_test)
        score_nb = accuracy_score(y_test, y_pred)
        fin_nb = time.perf_counter()
        print("Number of mislabeled points out of a total %d points : %d" % (X_test.shape[0], (y_test != y_pred).sum()))
        print("nb score :")
        print(score_nb)
        df = pd.DataFrame([('Score', score_nb),
                           ('Temp(s)', f'{fin_nb - debut:0.4}')],
                          columns=('Metrics/Models', 'NB')
                          )
        df.to_csv(output, index=False)
    # nb

    # ne
    elif model_option == "ne":
        #X = np.array(
           # [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
           #   0, 0, 1, 1, 1, 1, 1, 1, 1],
           #  [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
           #   0, 0, 1, 0, 1, 0, 1, 1, 1],
           #  [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
           #   0, 0, 0, 0, 0, 0, 1, 1, 1]])
         #   [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 7, 1],
          #   [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1],
           #  [7, 7, 3, 7, 7, 7, 0, 3, 1, 1, 1, 0, 0, 0, 1]])
        nbrs = NearestNeighbors(n_neighbors=2, algorithm='ball_tree').fit(X)
        distances, indices = nbrs.kneighbors(X)
        fin_ne = time.perf_counter()
        #print("Prediction of 2distances ", distances)
        print("Prediction of indice ", indices)
        df = pd.DataFrame([('Score', nbrs),
                           ('Temp(s)', f'{fin_ne - debut:0.4}')],
                          columns=('Metrics/Models', 'NE')
                          )
        df.to_csv(output, index=False)

    # ne

    # svm_reg (not in oa model)
    elif model_option == "svm_reg":
        #X = tb_data.drop(columns=['ID', 'Lineage', 'Family'])
        #y = tb_data['Family']
        regr = svm.SVR()
        regr.fit(X, y)
        regr.predict(X_test)
        predictions = clf.predict(X_test)
        #svm_prediction = clf.predict(
           # [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
           #   0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1],
           #  [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
           #   0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1],
           #  [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0,
           #   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1]])
           # [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 7, 1],
           #  [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1],
           #  [7, 7, 3, 7, 7, 7, 0, 3, 1, 1, 1, 0, 0, 0, 1]])
        score_svm_reg = accuracy_score(y_test, predictions)
        fin_svm_reg = time.perf_counter()
        #print("Prediction of 2 spoligo with svm: ", svm_prediction)
        print("svm score :")
        print(score_svm_reg)
        df = pd.DataFrame([('Score', score_svm_reg),
                           ('Temp(s)', f'{fin_svm_reg - debut:0.4}')],
                          columns=('Metrics/Models', 'SVM_REG')
                          )
        df.to_csv(output, index=False)
    # svm_reg

    # rf (not in oa model)
    elif model_option == "rf":
        # X = [[0, 0], [1, 1]]
        # y = [0, 1]
        # X = tb_data.drop(columns=['ID','Type','Country'])
        # y = tb_data['Type']
        # clf = RandomForestClassifier(n_estimators=10)
        clf = RandomForestClassifier(n_estimators=model_estimator)
        clf.fit(X, y)
        # SVC()
        predictions = clf.predict(X_test)
        #rf_prediction = clf.predict(tab)
        score_rf = accuracy_score(y_test, predictions)
        fin_rf = time.perf_counter()
        #print("Prediction of 2 spoligo with rf: ", rf_prediction)
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
        clf = ExtraTreesClassifier(n_estimators=model_estimator)
        clf.fit(X, y)
        predictions = clf.predict(X_test)
        #rf_prediction = clf.predict(tab)
        score_et = accuracy_score(y_test, predictions)
        fin_et = time.perf_counter()
        #print("Prediction of 2 spoligo with rf: ", rf_prediction)
        print(score_et)
        df = pd.DataFrame([('Score', score_et),
                           ('Temp(s)', f'{fin_et - debut:0.4}')],
                          columns=('Metrics/Models', 'ET')
                          )
        df.to_csv(output, index=False)
    # scores = cross_val_score(clf, X, y, cv=5)
    # print(scores.mean())
    # et

    # ab
    elif model_option == "ab":
        # clf = AdaBoostClassifier(n_estimators=100)
        clf = AdaBoostClassifier(n_estimators=model_estimator)
        clf.fit(X, y)
        predictions = clf.predict(X_test)
        #ab_prediction = clf.predict(tab)
        score_ab = accuracy_score(y_test, predictions)
        fin_ab = time.perf_counter()
        #print("Prediction of 2 spoligo with rf: ", ab_prediction)
        print(score_ab)
        df = pd.DataFrame([('Score', score_ab),
                           ('Temp(s)', f'{fin_ab - debut:0.4}')],
                          columns=('Metrics/Models', 'AB')
                          )
        df.to_csv(output, index=False)
    # ab

    # knn
    elif model_option == "knn":
        clf = KNeighborsClassifier(n_neighbors=model_kneighbors)
        clf.fit(X, y)
        predictions = clf.predict(X_test)
        #knn_prediction = clf.predict(tab)
        score_knn = accuracy_score(y_test, predictions)
        fin_knn = time.perf_counter()
        #print("Prediction of 2 spoligo with knn: ", knn_prediction)
        print(score_knn)
        df = pd.DataFrame([('Score', score_knn),
                           ('Temp(s)', f'{fin_knn - debut:0.4}')],
                          columns=('Metrics/Models', 'KNN')
                          )
        df.to_csv(output, index=False)
    # knn

    # all_options
    if args.all == True:
        debut_dt = time.perf_counter()
        model_option = DecisionTreeClassifier()
        model_option.fit(X_train, y_train)
        predictions = model_option.predict(X_test)
        score_dt = accuracy_score(y_test, predictions)
        fin_dt = time.perf_counter()
        print("Prediction of X_test: ", predictions)
        #print("Prediction of 2 spoligo: ", prediction2)
        print("dt score :")
        print(score_dt)
        print(f" L'option DT {fin_dt - debut_dt:0.4f} secondes")

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
        score_svm = accuracy_score(y_test, predictions)
        fin_svm = time.perf_counter()
        #print("Prediction of 2 spoligo with svm: ", svm_prediction)
        print("svm score :")
        print(score_svm)
        print(f" L'option SVM {fin_svm - debut_svm:0.4f} secondes")
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
        score_nn = accuracy_score(y_test, predictions)
        fin_nn = time.perf_counter()
        #print("Prediction of 2 spoligo with nn: ", nn_prediction)
        print("nn score :")
        print(score_nn)
        print(f" L'option NN {fin_nn - debut_nn:0.4f} secondes")
        # nn

        # nb
        # X, y = load_iris(return_X_y=True)
        debut_nb = time.perf_counter()
        #X = tb_data.drop(columns=['ID', 'Type', 'Country'])
        #y = tb_data['Type']
        #X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.5, random_state=0)
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
        #rf_prediction = clf.predict(tab)
        score_rf = accuracy_score(y_test, predictions)
        fin_rf = time.perf_counter()
        #print("Prediction of 2 spoligo with rf: ", rf_prediction)
        print(score_rf)

        # rf

        # et (not in oa model)
        # clf = ExtraTreesClassifier(n_estimators=10)
        debut_et = time.perf_counter()
        clf = ExtraTreesClassifier(n_estimators=model_estimator)
        clf.fit(X, y)
        predictions = clf.predict(X_test)
        #rf_prediction = clf.predict(tab)
        score_et = accuracy_score(y_test, predictions)
        fin_et = time.perf_counter()
        #print("Prediction of 2 spoligo with rf: ", rf_prediction)
        print(score_et)
        # scores = cross_val_score(clf, X, y, cv=5)
        # print(scores.mean())
        # et

        # ab
        debut_ab = time.perf_counter()
        clf = AdaBoostClassifier(n_estimators=model_estimator)
        clf.fit(X, y)
        predictions = clf.predict(X_test)
        #ab_prediction = clf.predict(tab)
        score_ab = accuracy_score(y_test, predictions)
        fin_ab = time.perf_counter()
        #print("Prediction of 2 spoligo with ab: ", ab_prediction)
        print(score_ab)
        print(f" L'option ab {fin_ab - debut_ab:0.4f} secondes")
        # ab

        # knn
        debut_knn = time.perf_counter()
        clf = KNeighborsClassifier(n_neighbors=model_kneighbors)
        clf.fit(X, y)
        predictions = clf.predict(X_test)
        #knn_prediction = clf.predict(tab)
        score_knn = accuracy_score(y_test, predictions)
        fin_knn = time.perf_counter()
        #print("Prediction of 2 spoligo with knn: ", knn_prediction)
        print(score_knn)
        print(f" L'option KNN {fin_knn - debut_knn:0.4f} secondes")
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
print(f" The total running time is: {fin - debut:0.4f} secondes")

    # if args.all == True:
    #     f.write("Metrics/Models;DT;SVM;NN;NB;AB;KNN\n")
    #     f.write("Score;" + str(score_dt) + ";" + str(score_svm) + ";" + str(score_nn) + ";" + str(score_nb) + ";" + str(score_ab) + ";" + str(score_knn) + "\n")
    #     f.write("Temp(s);"+f'{fin_dt-debut:0.4}'+";"+f'{fin_svm-debut: 0.4}'+";"+f'{fin_nn-debut:0.4}'+";"+f'{fin_nb-debut:0.4}'+";"+f'{fin_ab-debut:0.4}'+";"+f'{fin_knn-debut:0.4}'+"\n")
    #
    #     # f.write("Score;" + score_dt + "RF;ET;AB;SVM;NN;NB;AB;KNN\n")
    #     f.close

    # Printing the dataset shape
    # print ("Dataset Length: ", len(tb_data))
    # print ("Dataset Shape: ", tb_data.shape)

    # Printing the dataset obseravtions
    # print ("Dataset: ",tb_data.head())

    # lines from previous script

    # model = DecisionTreeClassifier()
    # model.fit(X_train, y_train) #model.fit(X, y)

    # tree.export_graphviz(model, out_file='graph.dot',
    #                   feature_names=['var1', 'var2', 'var3', 'var4', 'var5', 'var6', 'var7', 'var8', 'var9', 'var10',
    #                   'var11', 'var12', 'var13', 'var14', 'var15', 'var16', 'var17', 'var18', 'var19', 'var20',
    #                  'var21', 'var22', 'var23', 'var24', 'var25', 'var26', 'var27', 'var28', 'var29', 'var30',
    #                 'var31', 'var32', 'var33', 'var34', 'var35', 'var36', 'var37', 'var38', 'var39', 'var40',
    #                'var41', 'var42', 'var43'],
    #              class_names=sorted(y.unique()),
    #             label='all',
    #            rounded=True,
    #           filled=True)

    # joblib.dump(model, 'model-name.joblib')

    # predictions = model.predict(X_test)

    # prediction2 = model.predict([ [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1], [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,1,1], [1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1] ])

    # lines from previous script
