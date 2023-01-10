import gym
import numpy as np
import matplotlib.pyplot as plt

env = gym.make('MountainCar-v0')
env.reset()

learning_rate = 0.1
discount = 0.95
episodes = 25000

show_every = 2000

DISCRETE_OS_SIZE = [20] * len(env.observation_space.high)
discrete_os_win_size = (env.observation_space.high-env.observation_space.low) / DISCRETE_OS_SIZE

epsilon = 0.5
start_epsilon_deacay = 1
end_epsilon_decay = episodes//2

epsilon_decay_value = epsilon/(end_epsilon_decay-start_epsilon_deacay)

q_table = np.random.uniform(low=-2, high=0, size=(DISCRETE_OS_SIZE + [env.action_space.n]))

ep_rewards = []
agggregate_ep_rewards = {'ep':[], 'avg':[], 'min':[], 'max':[]}

def get_discrete_state(state):
    discrete_state = (state-env.observation_space.low) / discrete_os_win_size
    return tuple(discrete_state.astype(np.int))

for episode in range(episodes):
    ep_reward = 0 
    discrete_state = get_discrete_state(env.reset())
    done = False
    if episode % show_every == 0:
        print(episode)
        render = True
    else:
        render = False
    
    while not done:
        if np.random.random() >epsilon:
            action = np.argmax(q_table[discrete_state])
        else:
            action = np.random.randint(0, env.action_space.n)
        new_state, reward, done, _ = env.step(action)
        ep_reward +=reward
        new_discrete_state = get_discrete_state(new_state)
        if render:
            env.render()
        if not done:
            max_future_q = np.max(q_table[new_discrete_state])
            current_q = q_table[discrete_state + (action, )]
            new_q = (1-learning_rate) * current_q + learning_rate * (reward + discount*max_future_q)
            q_table[discrete_state + (action, )] = new_q
        elif new_state[0] >= env.goal_position:
            print(f"made it on episode{episode}")
            q_table[discrete_state + (action, )] = 0
        discrete_state = new_discrete_state
    if end_epsilon_decay >= episode >=start_epsilon_deacay:
        epsilon -= epsilon_decay_value 
    ep_rewards.append(ep_reward)
    if not episode%show_every:
        average_reward = sum(ep_rewards[-show_every:])/len(ep_rewards[-show_every:])
        agggregate_ep_rewards['ep'].append(episode)
        agggregate_ep_rewards['avg'].append(average_reward)
        agggregate_ep_rewards['min'].append(min(ep_rewards[-show_every:]))
        agggregate_ep_rewards['max'].append(max(ep_rewards[-show_every:]))

        print(f"Episode: {episode}, avg:{average_reward}, min:{min(ep_rewards[-show_every:])}, max:{max(ep_rewards[-show_every:])}")

env.close()

plt.plot(agggregate_ep_rewards['ep'], agggregate_ep_rewards['avg'], label = "avg")
plt.plot(agggregate_ep_rewards['ep'], agggregate_ep_rewards['min'], label = "min")
plt.plot(agggregate_ep_rewards['ep'], agggregate_ep_rewards['max'], label = "max")
plt.legend(loc=4)
plt.show()


