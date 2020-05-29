package com.example.speechdemo;


import android.os.Bundle ;
import android.support.v7.app.AppCompatActivity;
import android.view.View ;
import android.widget.Button ;
import android.widget.EditText ;

import com.example.speechdemo.speech.IShowSpeechResult;
import com.example.speechdemo.speech.SpeechManager;
import com.iflytek.cloud.SpeechConstant ;
import com.iflytek.cloud.SpeechUtility ;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IShowSpeechResult {

    private Button mStartspeechBtn;
    private EditText mInputEdt;

    private SpeechManager mSpeechManager;

    static final String TAG = "yangyong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    /***
     *  1.显示主的监听语音的界面,提供查看规则的的点击按钮,跳转到说明界面.
     *  2.显示语音输入的结果: 1.错误提示错误内容 2.语音转换换错误.
     *  3.
     */

    private void initView() {
        setContentView(R.layout.activity_main) ;

        mInputEdt = (EditText) findViewById(R.id.et_input );
        mStartspeechBtn = (Button) findViewById(R.id.btn_startspeech );
        mStartspeechBtn.setOnClickListener(this);
        mSpeechManager = new SpeechManager(this,this);
    }

    private void initData() {
        // 将“12345678”替换成您申请的 APPID，申请地址： http://www.xfyun.cn
        // 请勿在 “ =”与 appid 之间添加任务空字符或者转义符
        SpeechUtility.createUtility( this, SpeechConstant.APPID + "=5e2d4827" );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_startspeech: //语音识别（把声音转文字）
                mSpeechManager.initSpeech();
                break;
        }
    }

    @Override
    public void onShowSuccess(String str) {
        mInputEdt.setText(str);
    }

    @Override
    public void onShowError() {

    }
}
