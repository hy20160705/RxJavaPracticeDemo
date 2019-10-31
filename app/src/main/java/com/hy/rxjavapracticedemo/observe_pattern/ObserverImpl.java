package com.hy.rxjavapracticedemo.observe_pattern;

import android.util.Log;

/**
 * @Name: RxJavaPracticeDemo
 * @Description: 观察者实现类
 * @Author: Created by heyong on 2019-10-31
 */
public class ObserverImpl implements Observer {

    @Override
    public <T> void update(T observableInfo) {
       System.out.println(observableInfo);
    }
}
