/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.text.ITextComponent;

public interface IProgressUpdate {
    public void displaySavingString(ITextComponent var1);

    public void resetProgressAndMessage(ITextComponent var1);

    public void displayLoadingString(ITextComponent var1);

    public void setLoadingProgress(int var1);

    public void setDoneWorking();
}

