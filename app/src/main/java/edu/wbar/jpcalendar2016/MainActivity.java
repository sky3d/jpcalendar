package edu.wbar.jpcalendar2016;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements IMainView, View.OnClickListener {
    private static final int DATE_PICKER_ACTIVITY_REQUEST_CODE = 0;


    private MainPresenter mPresenter;
    private Context mContext;

    private Button btnPrev;
    private Button btnNext;
    private Button btnRand;
    private Button btnDatePicker;
    private Button btnSettings;

    private TextView txtHaiku;
    private TextView txtDate;
    private TextView txtAuthor;
    private ImageView imPicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bindViews();
        mContext = this;

        attachPresenter();
    }

    private void bindViews() {
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnRand = findViewById(R.id.btnRandom);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnSettings = findViewById(R.id.btnSettings);

        txtHaiku = findViewById(R.id.txtHaiku);
        txtDate = findViewById(R.id.txtDate);
        txtAuthor = findViewById(R.id.txtAuthor);
        imPicture = findViewById(R.id.fragImage);

        btnPrev.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnRand.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
    }

    private void attachPresenter() {
        mPresenter = (MainPresenter) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new MainPresenter(new Model(), new Repository());
        }
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }

    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPrev:
                mPresenter.getPrevHaiku();
                break;
            case R.id.btnNext:
                mPresenter.getNextHaiku();
                break;

            case R.id.btnRandom:
                mPresenter.getRandomHaiku();
                break;
            case R.id.btnDatePicker:
                this.pickDate();
                break;

            case R.id.btnSettings:
                this.showSettings();
                break;
        }
    }

    @Override
    public Context getContext() {
        return this.mContext;
    }

    @Override
    public ImageView getPictureView() {
        return this.imPicture;
    }

    @Override
    public void showCurrentDate(String date) {
        this.setTitle(String.format("%s", date));
    }

    @Override
    public void updateHaiku(Model model) {
        txtHaiku.setText(model.haiku);
        txtAuthor.setText(model.author);
        txtDate.setText(model.published);
    }

    void pickDate() {
        Intent intent = new Intent(this, DatePickerActivity.class);
        intent.putExtra("dt", mPresenter.currentDate());
        this.startActivityForResult(intent, DATE_PICKER_ACTIVITY_REQUEST_CODE);
    }

    void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DATE_PICKER_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                int year = data.getIntExtra("year", 1);
                int month = data.getIntExtra("month", 1);
                int day = data.getIntExtra("day", 1);
                mPresenter.getHaikuByDate(year, month, day);
            }
        }
    }
}
