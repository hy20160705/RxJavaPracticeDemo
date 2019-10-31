package com.hy.rxjavapracticedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.util.SorterFunction;


/**
 * @Name: RxJavaPracticeDemo
 * @Description: 过滤操作符
 * @Author: Created by heyong on 2019-10-31
 */
public class FilterActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    /**
     * 过滤不合格的奶粉
     *
     * @param view
     */
    public void filter(View view) {
        Observable.just("三鹿", "合生元", "飞鸽")
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        if (s.equals("三鹿")) {
                            return false;
                        }
                        return true; // 默认全部打印
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept " + s);
                    }
                });
    }

    public void take(View view) {
        // 定时器运行 只有在定时器运行的基础上 加入take操作符 才有take的价值
        Observable.interval(1, TimeUnit.SECONDS)
                .take(8) // 执行8次停下来
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept " + aLong);
                    }
                });
    }

    public void distinct(View view) {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(1);
                        e.onNext(1);
                        e.onNext(3);
                        e.onNext(2);
                        e.onNext(3);
                        e.onNext(2);
                        e.onNext(12);
                    }
                })
                .distinct() // 过滤掉重复的发射事件
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept " + integer);
                    }
                });
    }

    /**
     * elementAt 会指定过滤的内容
     *
     * @param view
     */
    public void elementAt(View view) {
        Observable.just("九阳神功","九阳神功","九阴真经","玄冥神掌")

//                .elementAt(2,"默认的") // 输出 accept 九阴真经
                .elementAt(100,"默认的") // 输出 accept 默认的

                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept " + s);
                    }
                });
    }
}
