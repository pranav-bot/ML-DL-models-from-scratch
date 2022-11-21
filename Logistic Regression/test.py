import numpy as np
from sklearn.model_selection import train_test_split
from sklearn import datasets
import matplotlib.pyplot as pyplot

from lr import LogisticRegression

bc = datasets.load_breast_cancer()
X, y = bc.data, bc.target

X_train, X_test, y_train, y_test = train_test_split(X,y, test_size=0.2)

def accuracy(y_true, y_pred):
    accuracy = np.sum(y_true==y_pred)/len(y_true)
    return accuracy

clf =   LogisticRegression(alpha=0.0001, n_iters = 1000)
clf.fit(X_train, y_train)
predictions = clf.predict(X_test)

print(accuracy(y_test, predictions))