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

/**
 * @Name: RxJavaPracticeDemo
 * @Description: 测试 RxJava的基本使用
 * @Author: Created by heyong on 2019-10-31
 */
public class TestActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void r01(View view) {
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
        // 终点
    }

    public void r02(View view) {
        // 上游 Observable 被观察者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            // ObservableEmitter<Integer> e 发射器 发射事件
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "subscribe 发射事件");
                e.onNext(2);
                Log.e(TAG, "subscribe 发射完成");
            }
        });
        // 下游 Observer 观察者
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "接收事件 onNext" + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        // 被观察者 订阅 观察者
        observable.subscribe(observer);

    }

    // 链式调用
    public void r03(View view) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // ObservableEmitter<Integer> e 发射器 发射事件
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "subscribe 发射事件");
                e.onNext(3);
                Log.e(TAG, "subscribe 发射完成");
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "接收事件 onNext" + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    // 流程-1
    public void r04(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {
            // ObservableEmitter<Integer> e 发射器 发射事件
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "subscribe 发射事件"); // 2
                e.onNext("RxJavaStudy");
                e.onComplete();
                Log.d(TAG, "subscribe 发射完成"); // 4
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                // 弹出加载框。。。
                Log.d(TAG, "订阅成功 4"); // 1
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "接收事件 onNext" + s); // 3
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                // 隐藏加载框。。。
                Log.d(TAG, "下游接收完成 onComplete"); // 5

            }
        });
    }

    // 流程-2
    public void r05(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {
            // ObservableEmitter<Integer> e 发射器 发射事件
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "subscribe 发射事件"); // 2
                e.onNext("RxJavaStudy");
////                e.onComplete();
//                Log.d(TAG, "subscribe 发射完成"); // 4
//                e.onError(new IllegalArgumentException("抛出错误 error RxJava"));
//
//                // TODO 在 e.onComplete()/onError() 完成之后再发射一条事件 下游不再接收上游的事件
//                e.onNext("再一次发射a");
//                e.onNext("再一次发射b");
//                e.onNext("再一次发射c");

                // TODO 先调onComplete在调onError会发生崩溃
                // 先调onError在调onComplete不回回调到observer.onComplete()
                e.onError(new IllegalArgumentException("抛出错误 error RxJava"));
                e.onComplete();


            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                // 弹出加载框。。。
                Log.d(TAG, "订阅成功 onSubscribe"); // 1
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "接收事件 onNext" + s); // 3
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError" + e.getMessage()); // 3
            }

            @Override
            public void onComplete() {
                // 隐藏加载框。。。
                Log.d(TAG, "下游接收完成 onComplete"); // 5

            }
        });
    }

    // 切断下游 让下游不再接收上游的事件
    public void r06(View view) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 100; i++) {
                    e.onNext(i);
                }
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                // 接收上游的一个事件之后 切断下游 不再接收
                Log.d(TAG, "接收事件 onNext" + integer); // 3
                disposable.dispose();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity销毁的时候 将下游切断 不再接收上游发过来的事件
        if (null != disposable) {
            disposable.dispose();
        }
    }
}
