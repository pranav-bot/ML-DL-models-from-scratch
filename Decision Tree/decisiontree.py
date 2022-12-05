import numpy as np
from collections import Counter

def entropy(y):
    hist = np.bincount(y)
    piece = hist/len(y)
    return -np.sum([p*np.log2(p) for p in piece if p>0])

class Node:
    def __init__(self, feature=None, threshold=None, left=None, right=None, *, value=None):
        self.feature = feature
        self.threshold = threshold
        self.left = left
        self.right = right
        self.value - value

    def isLeafNode(self):
        return self.value is not None

class DecisionTree:
    def __init__(self, min_samples_split=2, max_depth=100, n_feats=None):
        self.min_samples_split = min_samples_split
        self.max_depth = max_depth
        self.n_feats = n_feats
        self.root = None

    def fit(self, X, y):
        self.n_feats = X.shape[1] if not self.n_feats else min(self.n_feats, X.shape[1])   
        self.root = self._growTree(X, y)

    def _growTree(self, X, y, depth=0):
        n_samples, n_features = X.shape
        n_labels = len(np.unique(y))

        if(depth>=self.max_depth or n_labels==1 or n_samples < self.min_samples_split):
            leaf_value = self._most_common_label(y)
            return Node(value=leaf_value)

        feat_index =  np.random.choice(n_features, self.n_feats, replace=False)

        best_feat, best_thresh = self._best_criteria(X, y , feat_index)
        left_index, right_index = self._split(X[: best_feat], best_thresh)
        left = self._growTree(X[left_index, :], y[left_index], depth+1)
        right = self._growTree(X[right_index, :], y[right_index], depth+1)
        return Node(best_feat, best_thresh, left, right)

    def _best_criteria(self, X, y, feat_index):
        best_gain = -1
        split_index, split_threshold = None
        for i in feat_index:
            X_column = X[:, i]
            threshold = np.unique(X_column)
            for j in threshold:
                gain = self._information_gain(y, X_column, threshold)
                if gain>best_gain:
                    best_gain=gain
                    split_index = i
                    split_threshold = threshold
        return split_index, split_threshold
        
    def _information_gain(self, y, X_column, split_threshold):
        parent_entropy = entropy(y)
        left_index, right_index = self._split(X_column, split_threshold)
        if len(left_index)==0 or len(right_index)==0:
            return 0

        n=len(y)
        n_l, n_r = len(left_index), len(right_index)
        e_l , e_r = entropy(y[left_index]), entropy(y[right_index])
        child_entropy = (n_l/n)* e_l + (n_r/n)*e_r

        ig = parent_entropy-child_entropy
        return ig



    def _split(self, X_coloumn, split_threshold):
        left_index = np.argwhere(X_coloumn<=split_threshold).flatten()
        right_index = np.argwhere(X_coloumn> split_threshold).flatten()
        return left_index, right_index


    def predict(self, X):
        return np.array([self._traverseTree(x) for x in X], self.root)

    def _traverseTree(self, x, node):
        if node.isLeafNode():
            return node.value
        if x[node.feat_index] <= node.threshold:
            return self._traverseTree(x, node.left)
        return self._traverseTree(x, node.right)

    def _most_common_label(self, y):
        counter = Counter(y)
        most_common = counter.most_common(1)[0][0]
        return most_common