package com.fiqri.ganteng.activities.login;

import com.fiqri.ganteng.base.BasePresenter;

public class LoginPresenter implements BasePresenter<LoginContract.ViewPresenter> {

    LoginContract.ViewPresenter _view;

    public LoginPresenter(LoginContract.ViewPresenter _view) {
        this._view = _view;
    }

    @Override
    public void onAttach(LoginContract.ViewPresenter view) {
        _view = view;
    }

    @Override
    public void onDettach() {
        _view = null;
    }
}
