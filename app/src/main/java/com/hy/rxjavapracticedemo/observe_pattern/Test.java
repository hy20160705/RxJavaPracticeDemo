package com.hy.rxjavapracticedemo.observe_pattern;

/**
 * @Name: RxJavaPracticeDemo
 * @Description: 描述信息
 * @Author: Created by heyong on 2019-10-31
 */
public class Test {
    public static void main(String[] args) {
        Observer observer_1 = new ObserverImpl();
        Observer observer_2 = new ObserverImpl();
        Observer observer_3 = new ObserverImpl();

        Observable observable = new ObservableImpl();

        observable.registerObserver(observer_1);
        observable.registerObserver(observer_2);
        observable.registerObserver(observer_3);
        observable.notifyObservers();
    }
}
