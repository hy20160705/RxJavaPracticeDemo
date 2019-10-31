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
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * @Name: RxJavaPracticeDemo
 * @Description: 合并型操作符
 * @Author: Created by heyong on 2019-10-31
 */
public class MergeActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge);
    }

    /**
     * concatWith按顺序执行 被观察者observable_1.startWith(observable_2) 会先执行observable_1
     *
     * @param view
     */
    public void concatWith(View view) {
        // concatWith
//        Observable
//                .create(new ObservableOnSubscribe<Integer>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                        e.onNext(1);
//                        e.onNext(2);
//                        e.onNext(3);
//                        // 不执行 onComplete  observable_2不会发射数据
//                        e.onComplete();
//                    }
//                })
//                .concatWith(Observable.create(new ObservableOnSubscribe<Integer>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                        e.onNext(4);
//                        e.onNext(5);
//                        e.onNext(6);
//                        // 不执行 onComplete  observable_1不会发射数据
//                        e.onComplete();
//                    }
//                }))
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "accept " + integer);
//                    }
//                });
        // concat 最多存四个 按顺序执行
        Observable
                .concat(
                        Observable.just(1),
                        Observable.just(2),
                        Observable.just(3),
                        Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                                e.onNext(4);
                                e.onComplete();
                            }
                        })
                )
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept " + integer);
                    }
                });

    }

    /**
     * startWith 被观察者observable_1.startWith(observable_2) 会先执行observable_2
     *
     * @param view
     */
    public void startWith(View view) {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onNext(3);
//                        e.onComplete();
                    }
                })
                .startWith(Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(4);
                        e.onNext(5);
                        e.onNext(6);
                        // 不执行 onComplete  observable_1不会发射数据
                        e.onComplete();
                    }
                }))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept " + integer);
                    }
                });
    }

    public void merge(View view) {
        // 为了体现并列执行（并发）所以要学一个操作符
        // start 开始累计 count累计多少个数量 initialDelay 开始等待事件 period 每隔多久执行，TimeUnit 时间单位
        /**
         * 2019-10-31 20:00:09.268 450-484/com.hy.rxjavapracticedemo D/MergeActivity: accept 1
         * 2019-10-31 20:00:11.269 450-484/com.hy.rxjavapracticedemo D/MergeActivity: accept 2
         * 2019-10-31 20:00:13.269 450-484/com.hy.rxjavapracticedemo D/MergeActivity: accept 3
         * 2019-10-31 20:00:15.269 450-484/com.hy.rxjavapracticedemo D/MergeActivity: accept 4
         * 2019-10-31 20:00:17.269 450-484/com.hy.rxjavapracticedemo D/MergeActivity: accept 5
         */
//        Observable.intervalRange(1,5,1, 2, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.d(TAG, "accept " + aLong);
//                    }
//                });
/**
 * 2019-10-31 20:04:09.947 589-641/com.hy.rxjavapracticedemo D/MergeActivity: accept 1
 * 2019-10-31 20:04:09.950 589-642/com.hy.rxjavapracticedemo D/MergeActivity: accept 6
 * 2019-10-31 20:04:09.950 589-643/com.hy.rxjavapracticedemo D/MergeActivity: accept 11
 * 2019-10-31 20:04:11.947 589-641/com.hy.rxjavapracticedemo D/MergeActivity: accept 2
 * 2019-10-31 20:04:11.950 589-642/com.hy.rxjavapracticedemo D/MergeActivity: accept 7
 * 2019-10-31 20:04:11.950 589-643/com.hy.rxjavapracticedemo D/MergeActivity: accept 12
 * 2019-10-31 20:04:13.948 589-641/com.hy.rxjavapracticedemo D/MergeActivity: accept 3
 * 2019-10-31 20:04:13.950 589-642/com.hy.rxjavapracticedemo D/MergeActivity: accept 8
 * 2019-10-31 20:04:13.951 589-643/com.hy.rxjavapracticedemo D/MergeActivity: accept 13
 * 2019-10-31 20:04:15.948 589-641/com.hy.rxjavapracticedemo D/MergeActivity: accept 4
 * 2019-10-31 20:04:15.950 589-642/com.hy.rxjavapracticedemo D/MergeActivity: accept 9
 * 2019-10-31 20:04:15.951 589-643/com.hy.rxjavapracticedemo D/MergeActivity: accept 14
 * 2019-10-31 20:04:17.947 589-641/com.hy.rxjavapracticedemo D/MergeActivity: accept 5
 * 2019-10-31 20:04:17.951 589-643/com.hy.rxjavapracticedemo D/MergeActivity: accept 15
 * 2019-10-31 20:04:17.953 589-642/com.hy.rxjavapracticedemo D/MergeActivity: accept 10
 */
        Observable observable1=Observable.intervalRange(1,5,1, 2, TimeUnit.SECONDS);
        Observable observable2=Observable.intervalRange(6,5,1, 2, TimeUnit.SECONDS); // 6,7,8,9,10
        Observable observable3=Observable.intervalRange(11,5,1, 2, TimeUnit.SECONDS); // 11,12,13,14,15

        Observable
                .merge(observable1
                ,observable2,observable3)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d(TAG, "accept " + o);
                    }
                });
    }

    /**
     * 考试 课程=分数
     * @param view
     */
    public void zip(View view) {
        // 课程
        Observable observable1= Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("英语");
                e.onNext("数学");
                e.onNext("语文");
                e.onComplete();
            }
        });
        Observable observable2= Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(80);
                e.onNext(81);
                // 如果注释掉一个  e.onNext(82); 就不会一一对应
                /**
                 * 2019-10-31 22:22:45.502 5574-5574/com.hy.rxjavapracticedemo D/MergeActivity: 考试结果 apply 英语 80
                 * 2019-10-31 22:22:45.503 5574-5574/com.hy.rxjavapracticedemo D/MergeActivity: 考试结果 accept 课程英语==80
                 * 2019-10-31 22:22:45.503 5574-5574/com.hy.rxjavapracticedemo D/MergeActivity: 考试结果 apply 数学 81
                 * 2019-10-31 22:22:45.503 5574-5574/com.hy.rxjavapracticedemo D/MergeActivity: 考试结果 accept 课程数学==81
                 */
//                e.onNext(82);
                e.onComplete();
            }
        });
        Observable.zip(observable1, observable2, new BiFunction<String,Integer,StringBuffer>() {
            @Override
            public StringBuffer apply(String s, Integer i) throws Exception {
                Log.d(TAG, "考试结果 apply " + s+" "+i);
                return new StringBuffer().append("课程"+s).append("==").append(i);
            }
        }).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(TAG, "考试结果 accept " + o);
            }
        });
    }
}
