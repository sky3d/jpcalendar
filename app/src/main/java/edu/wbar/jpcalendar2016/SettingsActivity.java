package edu.wbar.jpcalendar2016;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnRate;
    private TextView txtVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.setTitle(R.string.activity_settings_title);

        btnRate = findViewById(R.id.btnRate);
        btnRate.setOnClickListener(this);

        txtVersion = findViewById(R.id.txtVersion);
        txtVersion.setText(String.format("Версия %s", getVersion()));
    }

    private String getVersion() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    private boolean isActivityStarted(Intent aIntent) {
        try {
            startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public void onClickRateThisApp(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + getPackageName()));
        if (!isActivityStarted(intent)) {
            intent.setData(Uri
                    .parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            if (!isActivityStarted(intent)) {
                Toast.makeText(this, R.string.error_open_market, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRate:
                onClickRateThisApp(view);
                break;
        }
    }
}
