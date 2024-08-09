/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.exception.RealmsDefaultUncaughtExceptionHandler;
import com.mojang.realmsclient.gui.LongRunningTask;
import java.util.HashSet;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.IErrorConsumer;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsLongRunningMcoTaskScreen
extends RealmsScreen
implements IErrorConsumer {
    private static final Logger field_224238_b = LogManager.getLogger();
    private final Screen field_224241_e;
    private volatile ITextComponent field_224243_g = StringTextComponent.EMPTY;
    @Nullable
    private volatile ITextComponent field_224245_i;
    private volatile boolean field_224246_j;
    private int field_224247_k;
    private final LongRunningTask field_224248_l;
    private final int field_224249_m = 212;
    public static final String[] field_224237_a = new String[]{"\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _"};

    public RealmsLongRunningMcoTaskScreen(Screen screen, LongRunningTask longRunningTask) {
        this.field_224241_e = screen;
        this.field_224248_l = longRunningTask;
        longRunningTask.func_224987_a(this);
        Thread thread2 = new Thread((Runnable)longRunningTask, "Realms-long-running-task");
        thread2.setUncaughtExceptionHandler(new RealmsDefaultUncaughtExceptionHandler(field_224238_b));
        thread2.start();
    }

    @Override
    public void tick() {
        super.tick();
        RealmsNarratorHelper.func_239553_b_(this.field_224243_g.getString());
        ++this.field_224247_k;
        this.field_224248_l.func_224990_b();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.func_224236_c();
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public void init() {
        this.field_224248_l.func_224991_c();
        this.addButton(new Button(this.width / 2 - 106, RealmsLongRunningMcoTaskScreen.func_239562_k_(12), 212, 20, DialogTexts.GUI_CANCEL, this::lambda$init$0));
    }

    private void func_224236_c() {
        this.field_224246_j = true;
        this.field_224248_l.func_224992_d();
        this.minecraft.displayGuiScreen(this.field_224241_e);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        RealmsLongRunningMcoTaskScreen.drawCenteredString(matrixStack, this.font, this.field_224243_g, this.width / 2, RealmsLongRunningMcoTaskScreen.func_239562_k_(3), 0xFFFFFF);
        ITextComponent iTextComponent = this.field_224245_i;
        if (iTextComponent == null) {
            RealmsLongRunningMcoTaskScreen.drawCenteredString(matrixStack, this.font, field_224237_a[this.field_224247_k % field_224237_a.length], this.width / 2, RealmsLongRunningMcoTaskScreen.func_239562_k_(8), 0x808080);
        } else {
            RealmsLongRunningMcoTaskScreen.drawCenteredString(matrixStack, this.font, iTextComponent, this.width / 2, RealmsLongRunningMcoTaskScreen.func_239562_k_(8), 0xFF0000);
        }
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public void func_230434_a_(ITextComponent iTextComponent) {
        this.field_224245_i = iTextComponent;
        RealmsNarratorHelper.func_239550_a_(iTextComponent.getString());
        this.func_237850_a_();
        this.addButton(new Button(this.width / 2 - 106, this.height / 4 + 120 + 12, 200, 20, DialogTexts.GUI_BACK, this::lambda$func_230434_a_$1));
    }

    private void func_237850_a_() {
        HashSet hashSet = Sets.newHashSet(this.buttons);
        this.children.removeIf(hashSet::contains);
        this.buttons.clear();
    }

    public void func_224234_b(ITextComponent iTextComponent) {
        this.field_224243_g = iTextComponent;
    }

    public boolean func_224235_b() {
        return this.field_224246_j;
    }

    private void lambda$func_230434_a_$1(Button button) {
        this.func_224236_c();
    }

    private void lambda$init$0(Button button) {
        this.func_224236_c();
    }
}

