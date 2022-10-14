package edu.wbar.jpcalendar2016;

import android.content.Context;
import android.widget.ImageView;

interface IMainView {
    void updateHaiku(Model model);
    void showCurrentDate(String date);
    Context getContext();
    ImageView getPictureView();
}
