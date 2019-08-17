package com.fiqri.ganteng.activities.list;

import com.fiqri.ganteng.base.BasePresenter;

public class ListUserPresenter implements BasePresenter<ListContract.ViewInterface> {

    ListContract.ViewInterface _view;

    public ListUserPresenter(ListContract.ViewInterface _view) {
        this._view = _view;
    }

    @Override
    public void onAttach(ListContract.ViewInterface view) {
        _view = view;
    }

    @Override
    public void onDettach() {
        _view = null;
    }
}
