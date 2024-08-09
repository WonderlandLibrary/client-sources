/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui;

import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.realms.IErrorConsumer;
import net.minecraft.util.text.ITextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LongRunningTask
implements IErrorConsumer,
Runnable {
    public static final Logger field_238124_a_ = LogManager.getLogger();
    protected RealmsLongRunningMcoTaskScreen field_224993_a;

    protected static void func_238125_a_(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (InterruptedException interruptedException) {
            field_238124_a_.error("", (Throwable)interruptedException);
        }
    }

    public static void func_238127_a_(Screen screen) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> LongRunningTask.lambda$func_238127_a_$0(minecraft, screen));
    }

    public void func_224987_a(RealmsLongRunningMcoTaskScreen realmsLongRunningMcoTaskScreen) {
        this.field_224993_a = realmsLongRunningMcoTaskScreen;
    }

    @Override
    public void func_230434_a_(ITextComponent iTextComponent) {
        this.field_224993_a.func_230434_a_(iTextComponent);
    }

    public void func_224989_b(ITextComponent iTextComponent) {
        this.field_224993_a.func_224234_b(iTextComponent);
    }

    public boolean func_224988_a() {
        return this.field_224993_a.func_224235_b();
    }

    public void func_224990_b() {
    }

    public void func_224991_c() {
    }

    public void func_224992_d() {
    }

    private static void lambda$func_238127_a_$0(Minecraft minecraft, Screen screen) {
        minecraft.displayGuiScreen(screen);
    }
}

