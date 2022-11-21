import numpy as np

class LogisticRegression:
    def __init__(self, alpha=0.001, n_iters=1000):
        self.alpha = alpha
        self.n_iters = n_iters
        self.weights = None
        self.bias = None

    def fit(self, X, y):
        n_samples, n_features = X.shape
        self.weights = np.zeros(n_features)
        self.bias = 0

        for _ in range(self.n_iters):
            linear_model = np.dot(X, self.weights) + self.bias
            y_predicted = self._sigmoid(linear_model)

            dw = (1/n_samples) * 2 * np.dot(X.T, (y_predicted-y))
            db =  (1/n_samples) * 2 * np.sum(y_predicted-y)

            self.weights = self.weights-(self.alpha*dw)
            self.bias = self.bias-(self.alpha*db)


    def _sigmoid(self,x):
        return 1/(1+np.exp(-x))
    
    def predict(self, X):
        linear_model = np.dot(X, self.weights) + self.bias
        y_predicted = self._sigmoid(linear_model)
        y_cls = [1 if i>0.5 else 0 for i in y_predicted]
        return y_cls