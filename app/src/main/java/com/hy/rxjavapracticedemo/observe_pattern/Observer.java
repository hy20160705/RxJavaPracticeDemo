package com.hy.rxjavapracticedemo.observe_pattern;

/**
 * @Name: RxJavaPracticeDemo
 * @Description: 观察者 标准
 * @Author: Created by heyong on 2019-10-31
 */
public interface Observer {
    /**
     * 被观察者发生改变了
     *
     * @param observableInfo
     * @param <T>
     */
    <T> void update(T observableInfo);
}
