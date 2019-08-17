package com.fiqri.ganteng.activities.regis;

import com.fiqri.ganteng.base.BasePresenter;

public class RegisterPresenter implements BasePresenter<RegisterContract.ViewInterface> {

    RegisterContract.ViewInterface _view;

    public RegisterPresenter(RegisterContract.ViewInterface _view) {
        this._view = _view;
    }

    @Override
    public void onAttach(RegisterContract.ViewInterface view) {
        _view = view;
    }

    @Override
    public void onDettach() {
        _view = null;
    }
}
