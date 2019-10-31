package com.hy.rxjavapracticedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void test(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }

    public void create(View view) {
        startActivity(new Intent(this, RxCreateActivity.class));
    }

    public void vary(View view) {
        startActivity(new Intent(this, VaryActivity.class));
    }

    public void filter(View view) {
        startActivity(new Intent(this, FilterActivity.class));
    }

    public void merge(View view) {
        startActivity(new Intent(this, MergeActivity.class));
    }
}
