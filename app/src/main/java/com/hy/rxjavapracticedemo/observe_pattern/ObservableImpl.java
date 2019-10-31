package com.hy.rxjavapracticedemo.observe_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name: RxJavaPracticeDemo
 * @Description: 被观察者实现类
 * @Author: Created by heyong on 2019-10-31
 */
public class ObservableImpl implements Observable {
    private List<Observer> observableList = new ArrayList<>();// 观察者容器

    @Override
    public void registerObserver(Observer observer) {
        observableList.add(observer);

    }

    @Override
    public void removeObserver(Observer observer) {
        observableList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observableList) {
            observer.update("被观察者发生改变。。。。");
        }
    }
}
