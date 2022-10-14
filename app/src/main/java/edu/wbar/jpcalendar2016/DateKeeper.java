package edu.wbar.jpcalendar2016;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


class DateKeeper {

    final Calendar mCal = Calendar.getInstance();
    final Random rand = new Random();

    public DateKeeper() {
    }

    public void prev() {
        mCal.add(Calendar.DATE, -1);
    }

    public void next() {
        mCal.add(Calendar.DATE, 1);
    }

    public void random() {
        this.set(0, 1);
        mCal.add(Calendar.DATE, rand.nextInt(365));
    }

    public void set(int month, int day) {
        this.set(mCal.get(Calendar.YEAR), month, day);
    }

    public void set(int year, int month, int day) {
        mCal.set(Calendar.YEAR, year);
        mCal.set(Calendar.MONTH, month);
        mCal.set(Calendar.DAY_OF_MONTH, day);
    }

    public String getKey() {
        return String.format("2016%02d%02d",
                mCal.get(Calendar.MONTH) + 1,
                mCal.get(Calendar.DAY_OF_MONTH));
    }

    public Date current() {
        return mCal.getTime();
    }

    public String formatCurrent() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, EEEE");
        return sdf.format(this.current());
    }
}
