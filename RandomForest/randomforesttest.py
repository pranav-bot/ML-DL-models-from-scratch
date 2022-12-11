import numpy as np
from sklearn import datasets
from sklearn.model_selection import train_test_split

from randomforest import RandomForest

def accuracy(y_true, y_pred):
    accuracy = np.sum(y_true==y_pred)/len(y_true)
    return accuracy

data = datasets.load_breast_cancer()

X = data.data
y = data.target

X_train, X_test, y_train , y_test = train_test_split(X,y, test_size=0.2, random_state=1234)

rfr = RandomForest(n_trees=3)
rfr.fit(X_train, y_train)

y_pred = rfr.predict(X_test)
acc = accuracy(y_test, y_pred)

print(acc)