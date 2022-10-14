package edu.wbar.jpcalendar2016;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.io.InputStream;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


class MainPresenter {
    Repository mRepository;
    Model mModel;
    IMainView mView;
    DateKeeper mDT;

    public MainPresenter(@NonNull Model model, @NonNull Repository repository) {
        mModel = model;
        mRepository = repository;

        mDT = new DateKeeper();
    }


    public void attachView(@NonNull IMainView view) {
        mView = view;
        load();
        this.updateView();
    }

    public void detachView() {
        mView = null;
    }


    public Long currentDate() {
        return mDT.current().getTime();
    }

    public void getPrevHaiku() {
        mDT.prev();
        load();
        updateView();
    }

    public void getNextHaiku() {
        mDT.next();
        load();
        updateView();
    }

    public void getRandomHaiku() {
        mDT.random();
        load();
        updateView();
    }

    public void getHaikuByDate(int year, int month, int day) {
        mDT.set(year, month, day);
        load();
        updateView();
    }

    private void updateView() {
        if (mView != null) {
            mView.updateHaiku(mModel);
            mView.showCurrentDate(mDT.formatCurrent());
        }
    }

    private void load() {
        final Context cxt = mView.getContext();
        final String prevImageUrl = mModel.imageUrl;

        InputStream stream = cxt.getResources().openRawResource(R.raw.verses);
        Model model = mRepository.load(stream, mDT.getKey());
        mModel.haiku = model.haiku;
        mModel.author = model.author;
        mModel.published = model.published;
        mModel.imageUrl = model.imageUrl;

        this.loadImage(cxt, prevImageUrl);
    }

    private void loadImage(final Context cxt, String prevImageUrl) {
        final String host = cxt.getString(R.string.images_host);
        final String url = host + mModel.imageUrl;
        final String prevUrl = prevImageUrl == null ? url : host + prevImageUrl;

        final ImageView view = mView.getPictureView();

        Picasso
                .get()
                .load(prevUrl)
                .placeholder(R.drawable.placeholder)
                .into(view, new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso
                                .get()
                                .load(url)
                                .noPlaceholder()
                                .into(view);
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }
}

