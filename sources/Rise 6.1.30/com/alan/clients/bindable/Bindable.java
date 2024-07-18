package com.alan.clients.bindable;

public interface Bindable {
    int getKey();
    void setKey(int key);

    void onKey();

    String[] getAliases();

    String getName();
}
