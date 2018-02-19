package com.slack.jeanpokou.todo;


public interface BaseView<T> {
    void attachPresenter(T presenter );
    void detachPresenter();
}
