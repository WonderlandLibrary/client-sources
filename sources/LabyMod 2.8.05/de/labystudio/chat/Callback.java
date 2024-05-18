package de.labystudio.chat;

public interface Callback
{
    void success();

    void failure();

    void failure(String var1);
}
