package com.hy.rxjavapracticedemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

/**
 * @Name: RxJavaPracticeDemo
 * @Description: 变换操作符
 * @Author: Created by heyong on 2019-10-31
 */
public class VaryActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vary);
    }

    /************************变换操作符********************************/
    public void map(View view) {
        Observable.just(1) // 上游
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "[" + integer + "]";
                    }
                })
                .map(new Function<String, Bitmap>() { // 可以多次变换
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        Log.e(TAG, "接收 apply2  s=" + s);
//                        return null; // 如果返回null 下游无法接收
                        return Bitmap.createBitmap(1920, 1200, Bitmap.Config.ARGB_8888);
                    }
                })
                .subscribe( // 订阅
                        new Observer<Bitmap>() { // 下游
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Bitmap s) {
                                Log.e(TAG, "接收 onNext  s=" + s);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
    }

    public void flatMap(View view) {
        Observable.just(111, 222, 333)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(final Integer integer) throws Exception {
                        // ObservableSource 再次发射
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> e) throws Exception {
                                e.onNext(integer + "flatMap变换操作符");
                            }
                        });
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "接收flatMap变换操作符 onNext  s=" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 体现flatMap不排序
     *
     * @param view
     */
    public void flatMapTest(View view) {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("张三");
                        e.onNext("李四");
                        e.onNext("王二");
                    }
                })
                .flatMap(new Function<String, ObservableSource<?>>() { // ？适配符 默认Object
                    @Override
                    public ObservableSource<?> apply(String s) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add(s + " 下标：" + (1 + i));
                        }
                        return Observable.fromIterable(list).delay(6, TimeUnit.SECONDS); // 创建型操作符
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e(TAG, "accept " + o);
                    }
                });
    }

    /**
     * 排序的
     *
     * @param view
     */
    public void concatMap(View view) {
        Observable.just("A", "B", "C")
                .concatMap(new Function<String, ObservableSource<?>>() { // ？适配符 默认Object
                    @Override
                    public ObservableSource<?> apply(String s) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add(s + " 下标：" + (1 + i));
                        }
                        return Observable.fromIterable(list).delay(6, TimeUnit.SECONDS); // 创建型操作符
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e(TAG, "accept " + o);
                    }
                });
    }

    /**
     * 分组变换
     *
     * @param view
     */
    public void groupBy(View view) {
        Observable.just(111, 222, 333, 444, 555)
                .groupBy(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {

                        return integer > 300 ? "高配置" : "低配置"; // 分组
                    }
                })
                .subscribe(new Consumer<GroupedObservable<String, Integer>>() {
                    @Override
                    public void accept(final GroupedObservable<String, Integer> stringIntegerGroupedObservable) throws Exception {
                        Log.d(TAG, "accept " + stringIntegerGroupedObservable.getKey());
                        // 以上代码不能把信息打印全 只拿到分组的key

                        // 细节 还需一层
                        stringIntegerGroupedObservable.subscribe(new Consumer<Integer>() { // 它也是一个被观察者
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.d(TAG, "accept 类别" + stringIntegerGroupedObservable.getKey() + "价格：" + integer);
                            }
                        });
                    }
                });
    }

    public void buffer(View view) {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        for (int i = 0; i < 100; i++) {
                            e.onNext(i);
                        }
                        e.onComplete();
                    }
                })
                .buffer(20)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.d(TAG, "accept " + integers.toString());
                    }
                });
    }
}
