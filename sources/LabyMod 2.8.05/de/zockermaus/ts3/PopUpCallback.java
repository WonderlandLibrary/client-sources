package de.zockermaus.ts3;

public interface PopUpCallback
{
    void cancel();

    void ok();

    void ok(int var1, String var2);

    boolean tick(int var1);
}
