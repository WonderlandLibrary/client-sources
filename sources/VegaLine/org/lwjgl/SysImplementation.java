/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl;

interface SysImplementation {
    public int getRequiredJNIVersion();

    public int getJNIVersion();

    public int getPointerSize();

    public void setDebug(boolean var1);

    public long getTimerResolution();

    public long getTime();

    public void alert(String var1, String var2);

    public boolean openURL(String var1);

    public String getClipboard();

    public boolean has64Bit();
}

