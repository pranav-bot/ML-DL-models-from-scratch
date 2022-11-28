import numpy as np

class SupportVectorMachine:

    def __init__(self, alpha=0.001, lambda_param=0.01, n_iters=1000):
        self.alpha = alpha
        self.lambda_param = lambda_param
        self.n_iters = n_iters
        self.weight = None
        self.bias = None

    def fit(self, X, y):
        y_ = np.where(y<=0 , -1, 1)
        n_samples, n_features = X.shape

        self.weight = np.zeros(n_features)
        self.bias = 0

        for _ in range(self.n_iters):
            for index, x_i in enumerate(X):
                condition = y_[index] * (np.dot(x_i, self.weight) - self.bias) >=1
                if condition:
                    self.weight = self.weight - self.alpha * (2*self.lambda_param*self.weight)
                else:
                    self.weight = self.weight - self.alpha * (2*self.lambda_param*self.weight - np.dot(x_i, y_[index]))
                    self.bias = self.bias - self.alpha * y_[index]

    def predict(self, X):
        linear_output = np.dot(X, self.weight) - self.bias
        return np.sign(linear_output)