/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

public interface IProgressUpdate {
    public void displaySavingString(String var1);

    public void resetProgressAndMessage(String var1);

    public void displayLoadingString(String var1);

    public void setLoadingProgress(int var1);

    public void setDoneWorking();
}

