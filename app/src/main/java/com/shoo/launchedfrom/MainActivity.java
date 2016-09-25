package com.shoo.launchedfrom;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String launchedFromPkgName = "unknown";
        // note: ActivityClientRecord.referrer 仅在API 22(Android 5.1)及之后版本提供
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            // 这里是通过代码注入，在当前Activity对象还未创建时，
            // 通过反射获取来源app包名（ActivityClientRecord.referrer）
            launchedFromPkgName = LaunchedFromManager.getInstance().getLaunchedFromPkgName();

            // 然而!创建当前Activity对象后，会通过Activity.attach()方法，
            // 将来源app包名（ActivityClientRecord.referrer）保存到Activity.mReferrer！
            // note:
            // 通过Android Studio直接运行应用后，Activity.mReferrer为空
            // 因为此时并不是通过系统桌面启动应用，而是通过adb命令启动，故Activity.mReferrer为空
            // adb shell am start com.shoo.launchedfrom/com.shoo.launchedfrom.MainActivity
            launchedFromPkgName = getSafeReferrer();
        }

        String notice = "App Launched from: " + launchedFromPkgName;

        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(notice);

        Toast.makeText(this, notice, Toast.LENGTH_LONG).show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private String getSafeReferrer() {
        if (getReferrer() != null && !TextUtils.isEmpty(getReferrer().getHost())) {
            return getReferrer().getHost();
        }

        return getPackageName();
    }
}
