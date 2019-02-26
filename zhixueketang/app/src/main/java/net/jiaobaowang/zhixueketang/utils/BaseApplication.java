package net.jiaobaowang.zhixueketang.utils;

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.tencent.smtt.sdk.QbSdk;

import io.dcloud.application.DCloudApplication;


public class BaseApplication extends DCloudApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initOkhttp();
        initTBS();
    }

    private  void initOkhttp(){
        OkGo.getInstance().init(this);
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    .debug("OkGo")
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS*10)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS*10)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS*10) ;   //全局的写入超时时间
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化TBS浏览服务X5内核
     */
    private void initTBS() {


        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.setDownloadWithoutWifi(true);//非wifi条件下允许下载X5内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {}
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
