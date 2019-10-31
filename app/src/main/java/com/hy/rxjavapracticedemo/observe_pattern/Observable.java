package com.hy.rxjavapracticedemo.observe_pattern;

/**
 * @Name: RxJavaPracticeDemo
 * @Description: 被观察者 标准
 * @Author: Created by heyong on 2019-10-31
 */
public interface Observable {
    /**
     * 在被观察者中注册观察者
     *
     * @param observer
     */
    void registerObserver(Observer observer);

    /**
     * 在被观察者中移除观察者
     *
     * @param observer
     */
    void removeObserver(Observer observer);

    /**
     * 在被观察这者中同志所有的观察者
     */
    void notifyObservers();
}
