import numpy as np

class LinearRegression:
    def __init__(self, alpha=0.001, n_iters=1000):
        self.alpha= alpha
        self.n_iters = n_iters
        self.weights=None
        self.bias=None

    def fit(self, X, y):
        n_samples, n_features = X.shape
        self.weights = np.zeros(n_features)
        self.bias=0

        for _ in range(self.n_iters):
            y_predicted = np.dot(X, self.weights)+ self.bias
            dw = (1/n_samples)

    def predict(self, X):
        pass

