package com.edu.self.mimamori;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * <h1>バックグランド処理を行う</h1>
 *
 * バックグランドでの処理には
 *   1:AscyntaskLoader
 *   2:Service
 *   がある。この中で
 *
 *   1は、Call backで書くことが多く、今回のように常駐する処理には向いていないので除外
 *   2は、今回使う。ActivityやFragmentとライフサイクルが似ているので使いすい。

 *   Created by Takumi Arimura on 16/01/24.
 */
public class BackgroundController extends Service {

    private Binder mBinder = new Binder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class LocalBinder extends Binder{
        BackgroundController getService(){
            return BackgroundController.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


}
