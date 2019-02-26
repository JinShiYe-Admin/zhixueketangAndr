package net.jiaobaowang.zhixueketang.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONArray;

import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.StandardFeature;
import io.dcloud.common.util.JSUtil;

public class FileUtils extends StandardFeature {

    private String CallBackID;
    private Activity activity;
    private String  URL,TYPE,NAME;

    @TargetApi(Build.VERSION_CODES.M)
    public void openFileFromURL(IWebview pWebview, JSONArray array ) {
        String json="";
        try {
            this.activity = pWebview.getActivity();
            this.CallBackID = array.optString(0);
            String jsonStr =array.optString(1);
            JSONObject obj=JSON.parseObject(jsonStr);
            this.URL=obj.get("openFileUrl")+"";
            this.TYPE=obj.get("file_ext")+"";
            Log.d("app", " openFileFromURL is " + URL);

            int i = URL.lastIndexOf("/");
            int z = URL.indexOf("?");
            String NAME = URL.substring(i,z)+"."+TYPE;
            Intent intent =new Intent();
            intent.setClass(activity,TbsActivity.class);
            intent.putExtra("URL",URL);
            intent.putExtra("NAME",NAME);
            activity.startActivity(intent);
            json="{\"code\":0,\"msg\":\"接收文件路径成功\"}";
            JSUtil.execCallback(pWebview, CallBackID,new JSONArray().put(json), JSUtil.OK, false);
        }catch (Exception e){
            json="{\"code\":-1,\"msg\":\"文件格式不正确，无法打开，e:\""+e.getMessage()+"}";
            JSUtil.execCallback(pWebview, CallBackID,new JSONArray().put(json), JSUtil.OK, false);
        }
    }
}