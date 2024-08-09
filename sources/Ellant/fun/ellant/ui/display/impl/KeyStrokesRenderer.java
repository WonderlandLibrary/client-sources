package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;

public class KeyStrokesRenderer implements ElementRenderer {
    private StopWatch time = new StopWatch();
    private final Dragging dragging;
    private float width;
    private float height;

    public void render(EventDisplay e) {
        MatrixStack mv = e.getMatrixStack();
        float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        float padding = 3.0F;
        float fontSize = 8.0F;
        float textPadding = 23.0F;
        ITextComponent w = GradientUtil.white("W");
        ITextComponent a = GradientUtil.white("A");
        ITextComponent s = GradientUtil.white("S");
        ITextComponent d = GradientUtil.white("D");
        ITextComponent pr = GradientUtil.white("SPACE");
        this.drawRect(posX, posY, this.width - 2.0F, this.height);
        Fonts.sfMedium.drawCenteredText(mv, w, posX + 43.0F, posY + padding - 25.0F, fontSize);
        Fonts.sfMedium.drawCenteredText(mv, a, posX - textPadding + 35.0F, posY + padding + 6.0F, fontSize);
        Fonts.sfMedium.drawText(mv, pr, posX + 27.0F, posY + 38.5F, fontSize, 255);
        Fonts.sfMedium.drawCenteredText(mv, s, posX + 43.0F, posY + padding + 6.0F, fontSize);
        Fonts.sfMedium.drawCenteredText(mv, d, posX + textPadding + 50.0F, posY + padding + 6.0F, fontSize);
        this.onClick(posX, posY, this.width - 2.0F, this.height);
        float maxWidth = Fonts.sfMedium.getWidth(w, fontSize) + padding * 2.0F;
        float localHeight = fontSize + padding * 2.0F;
        Scissor.push();
        Scissor.setFromComponentCoordinates((double)posX, (double)posY, (double)(this.width - 2.0F), (double)this.height);
        Scissor.unset();
        Scissor.pop();
        this.width = Math.max(maxWidth, 80.0F);
        this.height = localHeight + 2.5F;
        this.dragging.setWidth(this.width);
        this.dragging.setHeight(this.height);
    }

    public void drawRect(float x, float y, float width, float height) {
        final Vector4i vector4i = new Vector4i(HUD.getColor(0, 1), HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(90, 1));
        width = 28.0F;
        DisplayUtils.drawRoundedRect(x + 30, y, width, height + 10,4, ColorUtils.rgba(15, 15, 15, 200));//S

        DisplayUtils.drawRoundedRect(x + 30, y - 30, width, height + 10,4, ColorUtils.rgba(15, 15, 15, 200));//W

        DisplayUtils.drawRoundedRect(x, y + 30, width + 60, height + 7,4, ColorUtils.rgba(15, 15, 15, 200));//space

        DisplayUtils.drawRoundedRect(x, y, width, height + 10, 4, ColorUtils.rgba(15, 15, 15, 200));//A

        DisplayUtils.drawRoundedRect(x + 60, y, width, height + 10,4, ColorUtils.rgba(15, 15, 15, 200));//D

        DisplayUtils.drawRoundedRect(x, y + 55, width + 60, height - 10, new Vector4f(4.0f, 4.0f, 4.0f, 4.0f), vector4i);//GRADIENT LINE
    }

    public void onClick(float x, float y, float width, float height) {
        width = 28.0F;
        if (mc.gameSettings.keyBindBack.pressed) {
            DisplayUtils.drawRoundedRect(x + 30,y,width,height + 10,4,ColorUtils.rgba(225,225,225,150));//S
        }

        if (mc.gameSettings.keyBindJump.pressed) {
            DisplayUtils.drawRoundedRect(x,y + 30,width + 60,height + 7,4,ColorUtils.rgba(225,225,225,150));//space
        }

        if (mc.gameSettings.keyBindForward.pressed) {
            DisplayUtils.drawRoundedRect(x + 30,y - 30,width,height + 10,4,ColorUtils.rgba(225,225,225,150));//W
        }

        if (mc.gameSettings.keyBindLeft.pressed) {
            DisplayUtils.drawRoundedRect(x,y,width,height + 10,4,ColorUtils.rgba(225,225,225,150));//A
        }

        if (mc.gameSettings.keyBindRight.pressed) {
            DisplayUtils.drawRoundedRect(x + 60,y,width,height + 10,4,ColorUtils.rgba(225,225,225,150));//D
        }

    }

    public KeyStrokesRenderer(Dragging dragging) {
        this.dragging = dragging;
    }
}