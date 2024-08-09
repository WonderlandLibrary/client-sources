/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.optifine.gui.IOptionControl;
import net.optifine.util.GuiUtils;

public class GuiScreenOF
extends Screen {
    protected List<Widget> buttonList;
    protected FontRenderer fontRenderer;
    protected boolean mousePressed;

    public GuiScreenOF(ITextComponent iTextComponent) {
        super(iTextComponent);
        this.buttonList = this.buttons;
        this.fontRenderer = Minecraft.getInstance().fontRenderer;
        this.mousePressed = false;
    }

    protected void actionPerformed(Widget widget) {
    }

    protected void actionPerformedRightClick(Widget widget) {
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        boolean bl = super.mouseClicked(d, d2, n);
        this.mousePressed = true;
        Widget widget = GuiScreenOF.getSelectedButton((int)d, (int)d2, this.buttonList);
        if (widget != null && widget.active) {
            IOptionControl iOptionControl;
            if (n == 1 && widget instanceof IOptionControl && (iOptionControl = (IOptionControl)((Object)widget)).getControlOption() == AbstractOption.GUI_SCALE) {
                widget.playDownSound(this.minecraft.getSoundHandler());
            }
            if (n == 0) {
                this.actionPerformed(widget);
            } else if (n == 1) {
                this.actionPerformedRightClick(widget);
            }
            return false;
        }
        return bl;
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        if (!this.mousePressed) {
            return true;
        }
        this.mousePressed = false;
        this.setDragging(true);
        return this.getListener() != null && this.getListener().mouseReleased(d, d2, n) ? true : super.mouseReleased(d, d2, n);
    }

    @Override
    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        return !this.mousePressed ? false : super.mouseDragged(d, d2, n, d3, d4);
    }

    public static Widget getSelectedButton(int n, int n2, List<Widget> list) {
        for (int i = 0; i < list.size(); ++i) {
            Widget widget = list.get(i);
            if (!widget.visible) continue;
            int n3 = GuiUtils.getWidth(widget);
            int n4 = GuiUtils.getHeight(widget);
            if (n < widget.x || n2 < widget.y || n >= widget.x + n3 || n2 >= widget.y + n4) continue;
            return widget;
        }
        return null;
    }

    public static void drawCenteredString(MatrixStack matrixStack, FontRenderer fontRenderer, IReorderingProcessor iReorderingProcessor, int n, int n2, int n3) {
        fontRenderer.func_238407_a_(matrixStack, iReorderingProcessor, n - fontRenderer.func_243245_a(iReorderingProcessor) / 2, n2, n3);
    }
}

