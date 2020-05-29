package com.example.speechdemo.data;

import android.util.Log;

import com.example.speechdemo.data.bean.AccountInfo;
import com.example.speechdemo.data.bean.AccountType;
import com.example.speechdemo.speech.IShowSpeechResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.speechdemo.MainActivity.TAG;

/**
 * Created by yangyong on 20-5-29.
 */

public class DataManagement {
    // 定义语句:
    // 吃喝: ....吃/喝....花了xxx元   如:晚上吃饭花了12元
    // 住(房租,水,电): .....(房租,水,电)....交了xxx元 如:这个月房租交了888元.
    // 行: ......乘(地地铁).....花了xxx花了

    private IShowSpeechResult mShowCallback;
    public DataManagement(IShowSpeechResult mShowCallback) {
        this.mShowCallback = mShowCallback;
    }

    // 解析数据
    public void parseVoice(String str) {
        parseEating(str);
    }

    private void parseEating(String str) {
        Log.d(TAG,"str: " + str);
        if (str.contains("吃") || (str.contains("喝"))){
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccountType(1);
            accountInfo.setContent(str);
            Pattern p = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");
            Matcher m = p.matcher(str);
            accountInfo.setMonney(Integer.valueOf(m.group().toString()));
            mShowCallback.onShowSuccess(accountInfo.toString());
            Log.d(TAG,"accountInfo: " + accountInfo.toString());
        }
    }
}
