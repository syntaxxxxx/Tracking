package com.fiqri.ganteng.base;

public interface BasePresenter<T extends BaseView> {

    void onAttach(T view);

    void onDettach();
}
