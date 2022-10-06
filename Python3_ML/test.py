import argparse
import csv
import time

import pandas as pd
from sklearn import svm
from pandas import read_csv
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

parser = argparse.ArgumentParser(description="Prediction from csv file.")
parser.add_argument("-i", "--input", metavar="csvfile", help="Input csv file", type=str)
parser.add_argument("-m", "--model", metavar="modeloption",
                    help="choose your option (ex: dt, svm, nn, nb, svm_reg, rf, et, ab, knn, ao)", type=str)

args = parser.parse_args()
in_file = args.input
model_option = args.model
tb_data = pd.read_csv(in_file)

# tb_data = pd.read_csv('tb_file.csv')
# X = tb_data.drop(columns=['ID', 'Type', 'Country'])
# y = tb_data['Type']
# X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

# df = read_csv('simpiTB_1474_strains.csv')
# df = pd.DataFrame(raw_data)
# print(df.head())


# To select rows whose column value is in an iterable array, which we'll define as array, you can use
array = ['ND', 'Unknown', 'unknown']
# print(tb_data.isin(array))
# print(df.loc[df['favorite_color'].isin(array)])
# print(df.loc[df['Type'].isin(array)])

results = []
testfile = tb_data.drop(columns=['ID', 'Type', 'Country'])
reader = csv.reader(testfile, quoting=csv.QUOTE_NONNUMERIC)  # change contents to floats
print(reader)
for line in testfile:
    results.append(line)

print(results)


# svm
if model_option == "svm":
    # X = [[0, 0], [1, 1]]
    # y = [0, 1]
    array = ['ND', 'Unknown', 'unknown']
    tb_data.isin(array)
    x_d = tb_data.loc[tb_data['Type'].isin(array)].drop(columns=['ID', 'Type', 'Country'])
    print(x_d)
    debut = time.perf_counter()
    X = tb_data.drop(columns=['ID', 'Type', 'Country'])
    # X = x_d.drop(columns=['ID', 'Type', 'Country'])
    y = tb_data['Type']
    # y = x_d['Type']
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)
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
# svm
