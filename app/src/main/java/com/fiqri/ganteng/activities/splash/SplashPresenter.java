package com.fiqri.ganteng.activities.splash;

import com.fiqri.ganteng.base.BasePresenter;

public class SplashPresenter implements BasePresenter<SplashContract.ViewInterface> {

    SplashContract.ViewInterface _view;

    public SplashPresenter(SplashContract.ViewInterface _view) {
        this._view = _view;
    }

    @Override
    public void onAttach(SplashContract.ViewInterface view) {
        _view = view;
    }

    @Override
    public void onDettach() {
        _view = null;
    }
}
