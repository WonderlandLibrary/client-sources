/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

public interface Replaceable {
    public int length();

    public char charAt(int var1);

    public int char32At(int var1);

    public void getChars(int var1, int var2, char[] var3, int var4);

    public void replace(int var1, int var2, String var3);

    public void replace(int var1, int var2, char[] var3, int var4, int var5);

    public void copy(int var1, int var2, int var3);

    public boolean hasMetaData();
}

