package com.example.speechdemo.speech;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.speechdemo.JsonParser;
import com.example.speechdemo.MainActivity;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by yangyong on 20-5-28.
 */

public class SpeechManager {

    private Context mContext;
    private IShowSpeechResult mIShowSpeech;
    private HashMap<String, String> mIatResults = new LinkedHashMap();

    public SpeechManager(Context context, IShowSpeechResult callback) {
        this.mContext = context;
        this.mIShowSpeech = callback;
    }

    public void initSpeech() {
        startSpeechDialog();
    }

    private void startSpeechDialog() {
        //1. 创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(mContext, new MyInitListener()) ;
        //2. 设置accent、 language等参数
        mDialog.setParameter(SpeechConstant. LANGUAGE, "zh_cn" );// 设置中文
        mDialog.setParameter(SpeechConstant. ACCENT, "mandarin" );
        // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后 onResult回调返回将是语义理解
        // 结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener(new MyRecognizerDialogListener()) ;
        //4. 显示dialog，接收语音输入
        mDialog.show() ;
    }

    class MyInitListener implements InitListener {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                mIShowSpeech.onShowError();
            }
        }
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {

        /**
         * @param results
         * @param isLast  是否说完了
         */
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            String result = results.getResultString(); //为解析的
            Log.d("yangyong"," 没有解析的 :" + result);

            String text = JsonParser.parseIatResult(result) ;//解析过后的
            Log.d("yangyong"," 解析后的 :" + text);

            String sn = null;
            // 读取json结果中的 sn字段
            try {
                JSONObject resultJson = new JSONObject(results.getResultString()) ;
                sn = resultJson.optString("sn" );
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults.put(sn, text) ;//没有得到一句，添加到

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults .get(key));
            }
            mIShowSpeech.onShowSuccess(resultBuffer.toString());

        //    et_input.setText();// 设置输入框的文本
         //   et_input .setSelection(et_input.length()) ;//把光标定位末尾
            // 在界面显示输入的内容
            // 解析存入数据库,
        }
        @Override
        public void onError(SpeechError speechError) {}
    }



}
