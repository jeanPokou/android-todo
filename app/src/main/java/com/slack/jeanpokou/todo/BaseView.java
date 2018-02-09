package com.slack.jeanpokou.todo;


public interface BaseView<T> {
    void attach( T presenter );
    void detach();
}
