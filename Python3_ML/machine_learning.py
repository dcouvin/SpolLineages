#!/usr/bin/env python3

import pandas as pd
import argparse
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
#from sklearn.externals import joblib
from sklearn import tree

parser = argparse.ArgumentParser(description="Prediction from csv file.")
parser.add_argument("-i", "--input", metavar="csvfile", help="Input csv file", type=str)
parser.add_argument("-m", "--model", metavar="modeloption", help="choose your option (ex: dt)", type=str)
args = parser.parse_args()
in_file = args.input
model_option = args.model
tb_data = pd.read_csv(in_file)


#tb_data = pd.read_csv('tb_file.csv')
X = tb_data.drop(columns=['ID','Type','Country'])
y = tb_data['Type']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

#dt
if model_option == "dt" :
	model_option = DecisionTreeClassifier()
	model_option.fit(X_train, y_train)
	tree.export_graphviz(model_option, out_file='graph.dot',
		            feature_names=['var1', 'var2', 'var3', 'var4', 'var5', 'var6', 'var7', 'var8', 'var9', 'var10',
		             'var11', 'var12', 'var13', 'var14', 'var15', 'var16', 'var17', 'var18', 'var19', 'var20',
		             'var21', 'var22', 'var23', 'var24', 'var25', 'var26', 'var27', 'var28', 'var29', 'var30',
		             'var31', 'var32', 'var33', 'var34', 'var35', 'var36', 'var37', 'var38', 'var39', 'var40',
		             'var41', 'var42', 'var43'],
		            class_names=sorted(y.unique()),
		            label='all',
		            rounded=True,
		            filled=True)
	predictions = model_option.predict(X_test)
	prediction2 = model_option.predict([ [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1], [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,1,1], [1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1] ])
	print("Prediction of X_test: ", predictions)
	print("Prediction of 2 spoligo: ", prediction2)

#dt

#model = DecisionTreeClassifier()
#model.fit(X_train, y_train) #model.fit(X, y)

#tree.export_graphviz(model, out_file='graph.dot',
 #                   feature_names=['var1', 'var2', 'var3', 'var4', 'var5', 'var6', 'var7', 'var8', 'var9', 'var10',
  #                   'var11', 'var12', 'var13', 'var14', 'var15', 'var16', 'var17', 'var18', 'var19', 'var20',
   #                  'var21', 'var22', 'var23', 'var24', 'var25', 'var26', 'var27', 'var28', 'var29', 'var30',
    #                 'var31', 'var32', 'var33', 'var34', 'var35', 'var36', 'var37', 'var38', 'var39', 'var40',
     #                'var41', 'var42', 'var43'],
      #              class_names=sorted(y.unique()),
       #             label='all',
        #            rounded=True,
         #           filled=True)

#joblib.dump(model, 'model-name.joblib')

#predictions = model.predict(X_test)

#prediction2 = model.predict([ [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1], [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,1,1], [1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1] ])

score = accuracy_score(y_test, predictions)
print(score)


# Printing the dataset shape
print ("Dataset Length: ", len(tb_data))
print ("Dataset Shape: ", tb_data.shape)

# Printing the dataset obseravtions
print ("Dataset: ",tb_data.head())
