import numpy as np


class LinearRegression:

    def __init__(self,alpha=0.001 ,n_iters=1000):
        self.alpha=alpha
        self.n_iters = n_iters
        self.weights = None
        self.bias = None

    def fit(self, X, y):
        n_samples, n_features = X.shape
        self.weights = np.zeros(n_features)
        self.bias = 0

        for _ in range(self.n_iters):
            y_predicted = np.dot(X, self.weights) + self.bias

            dw = (1/n_samples) * 2 * np.dot(X.T, (y_predicted-y))
            db =  (1/n_samples) * 2 * np.sum(y_predicted-y)

            self.weights = self.weights-(self.alpha*dw)
            self.bias = self.bias-(self.alpha*db)

    def predict(self, X):
        y_approximated = np.dot(X, self.weights) + self.bias
        return y_approximated