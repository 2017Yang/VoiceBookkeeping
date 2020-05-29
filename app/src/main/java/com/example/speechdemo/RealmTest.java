package com.example.speechdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.speechdemo.data.bean.AccountInfo;
import com.example.speechdemo.data.bean.AccountType;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static com.example.speechdemo.MainActivity.TAG;

/**
 * Created by yangyong on 20-5-29.
 */

public class RealmTest extends Activity implements View.OnClickListener{
    private Realm mRealm;

    private Button btnAdd;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realm_ly);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btn1 = (Button) findViewById(R.id.btn_query1);
        btn2 = (Button) findViewById(R.id.btn_query2);
        btn3 = (Button) findViewById(R.id.btn_query3);
        btn4 = (Button) findViewById(R.id.btn_query4);
        btn5 = (Button) findViewById(R.id.btn_query5);

        btnAdd.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

        initRealmConfig();
    }

    public void initRealmConfig() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm") //文件名
                .schemaVersion(0) //版本号
                .deleteRealmIfMigrationNeeded()
                .build();
        mRealm = Realm.getInstance(config);
        mRealm.setDefaultConfiguration(config);
    }


    public void addInfo() { //添加
        final AccountInfo accountInfo = new AccountInfo();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                accountInfo.setTime("2020-05-01");
                accountInfo.setMonney(15);
                accountInfo.setAccountType(1);
                accountInfo.setContent("eating");
                realm.copyToRealmOrUpdate(accountInfo);

                accountInfo.setTime("2020-05-02");
                accountInfo.setMonney(16);
                realm.copyToRealmOrUpdate(accountInfo);

                accountInfo.setTime("2020-05-03");
                accountInfo.setMonney(17);
                realm.copyToRealmOrUpdate(accountInfo);

                accountInfo.setTime("2020-05-04");
                accountInfo.setMonney(18);
                realm.copyToRealmOrUpdate(accountInfo);

                accountInfo.setTime("2020-05-05");
                accountInfo.setMonney(19);
                realm.copyToRealmOrUpdate(accountInfo);

            }
        });
    }

    public void queryInfo() { //全部
        RealmResults<AccountInfo> accountInfo = mRealm.where(AccountInfo.class).findAll();
        //Toast.makeText(this,"accountInfo: " +  accountInfo.toString(),Toast.LENGTH_SHORT).show();
        Log.d(TAG,"accountInfo: " +  accountInfo.toString());
    }

    public void queryInfo1() { // 时间排序

    }

    public void queryInfo2() { // 平均
        Double avg = (Double) mRealm.where(AccountInfo.class).findAll().average("monney");
       // Toast.makeText(this,"平均: " +  avg,Toast.LENGTH_SHORT).show();
        Log.d(TAG,"平均: " +  avg);
    }

    public void queryInfo3() { //总的
        Number sunNum = mRealm.where(AccountInfo.class).findAll().sum("monney");
        //Toast.makeText(this,"总的: " +  sunNum,Toast.LENGTH_SHORT).show();
        Log.d(TAG,"总的: " +  sunNum.toString());
    }

    public void del() {//删除
        final RealmResults<AccountInfo> dogs=  mRealm.where(AccountInfo.class).findAll();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                //删除所有数据
                dogs.deleteAllFromRealm();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                addInfo();
                break;

            case R.id.btn_query1:
                queryInfo();
                break;

            case R.id.btn_query2:
                queryInfo1();
                break;

            case R.id.btn_query3:
                queryInfo2();
                break;

            case R.id.btn_query4:
                queryInfo3();
                break;

            case R.id.btn_query5:
                  del();
                break;

            default:
                break;
        }
    }
}
