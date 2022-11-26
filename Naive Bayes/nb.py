import numpy as np

class NaiveBayes:

    def fit(self, X, y):
        n_samples, n_features = X.shape
        self.classes = np.unique(y)
        n_classes = len(self.classes)

        self.mean = np.zeros((n_classes, n_features), dtype=np.float64)
        self.variance = np.zeros((n_classes, n_features), dtype=np.float64)
        self.priors = np.zeros(n_classes, dtype=np.float64)

        for index, c in enumerate(self.classes):
            X_c = X[c==y]
            self.mean[index,:] = X_c.mean(axis=0)
            self.variance[index, :] = X_c.var(axis=0)
            self.priors[index] = X_c.shape[0] / float(n_samples)


    def predict(self, X):
        y_pred = [self._predict(x) for x in X]
        return np.array(y_pred)

    def _predict(self, x):
        posteriors = []
        for index, c in enumerate(self.classes):
            prior = np.log(self.priors[index])
            class_conditional = np.sum(np.log(self.probability_density(index, x)))
            posterior = prior+class_conditional
            posteriors.append(posterior)

        return self.classes[np.argmax(posteriors)]


    def probability_density(self, class_index, x):
        mean = self.mean[class_index]
        variance = self.variance[class_index]
        numerator = np.exp(-((x-mean)**2) / ( 2* variance))
        denominator = np.sqrt(2*np.pi * variance)
        return numerator/denominator

