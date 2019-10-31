package com.hy.rxjavapracticedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @Name: RxJavaPracticeDemo
 * @Description: 描述信息
 * @Author: Created by heyong on 2019-10-31
 */
public class RxCreateActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
    }

    /************************创建型操作符********************************/
    public void create(View view) {
        // 起点 被观察者
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {

            }
        }).subscribe(new Observer<Integer>() { // 订阅 终点 观察者
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void just(View view) {
        Observable.just("A", "B") // 内部会去先发送A 再发送B
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "接收 onNext " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void fromArray(View view) {
        String[] strings = {"A", "B", "C"};
        Observable.fromArray(strings)
//                .subscribe(new Observer<String>() {
////                    @Override
////                    public void onSubscribe(Disposable d) {
////
////                    }
////
////                    @Override
////                    public void onNext(String s) {
////                        Log.e(TAG, "接收 onNext " + s);
////                    }
////
////                    @Override
////                    public void onError(Throwable e) {
////
////                    }
////
////                    @Override
////                    public void onComplete() {
////
////                    }
////                });
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "接收 onNext " + s);
                    }
                });
    }

    /**
     * 只支持Object
     * 因为上游没有发射有值的事件，默认就是Object
     * 做一个耗时操作 不需要刷新UI
     *
     * @param view
     */
    public void empty(View view) {
        // 上游无法指定事件类型
        Observable.empty() // 内部一定回调用onComplete事件
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        // 没有事件可以接收
                        Log.e(TAG, "接收 onNext ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        //  隐藏加载框
                        Log.e(TAG, "接收 onComplete ");
                    }
                });
        // 简化版
//        .subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                // 也不会接收到
//                Log.e(TAG, "接收 accept ");
//            }
//        });
    }

    public void rang(View view) {
        // range 内部会去发射
//        Observable.range(1, 8) // 从1开始 数量共8个 1，2，3，4，5，6，7，8
        Observable.range(90, 8) // 从1开始 数量共8个 90,91,82,93,94,,,,97
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "接收 onNext  integer=" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
