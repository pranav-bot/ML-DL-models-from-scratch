import numpy as np


def linear_regression_model(X, Y, learning_rate, iterations):
    m = Y.size
    theta = np.zeros((2,1))

    for i in range(iterations):
        
        y_preds = np.dot(X, theta)
        cost =(1/(2*m))*np.sum(np.square(y_preds - Y))
        d_theta = (1/m)*np.dot(X.T, y_preds - Y)

        theta = theta - learning_rate*d_theta
    
    
    return theta

