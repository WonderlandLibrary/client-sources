package src.Wiksi.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.functions.impl.render.HUD;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.math.StopWatch;
import src.Wiksi.utils.math.Vector4i;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.KawaseBlur;
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.utils.text.GradientUtil;
import net.minecraft.util.text.StringTextComponent;

public class KeyStrokes2Renderer
        implements ElementRenderer {
    private StopWatch time = new StopWatch();
    private final Dragging dragging;
    private float width;
    private float height;

    @Override
    public void render(EventDisplay e) {
        MatrixStack mv = e.getMatrixStack();
        float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        float padding = 3.0f;
        float fontSize = 8.0f;
        float textPadding = 22.0f;
        StringTextComponent w = GradientUtil.gradient("W");
        StringTextComponent a = GradientUtil.gradient("A");
        StringTextComponent s = GradientUtil.gradient("S");
        StringTextComponent d = GradientUtil.gradient("D");
        StringTextComponent pr = GradientUtil.gradient("SPACE");
        this.drawRect(posX, posY, this.width - 2.0f, this.height);
        Fonts.sfMedium.drawCenteredText(mv, "W", posX + 43.0f, posY + padding - 25.0f,ColorUtils.rgb(255,255,255), 10);
        Fonts.sfMedium.drawCenteredText(mv, "A", posX - textPadding + 35.0f, posY + padding + 6.0f,ColorUtils.rgb(255,255,255), 10);
        Fonts.sfMedium.drawText(mv, "SPACE", posX + 23.0f, posY + 35.5f,ColorUtils.rgb(255,255,255), 10);
        Fonts.sfMedium.drawCenteredText(mv, "S", posX + 43.0f, posY + padding + 6.0f,ColorUtils.rgb(255,255,255), 10);
        Fonts.sfMedium.drawCenteredText(mv, "D", posX + textPadding + 50.0f, posY + padding + 6.0f,ColorUtils.rgb(255,255,255), 10);
        this.onClick(posX, posY, this.width - 2.0f, this.height);
        float maxWidth = Fonts.sfMedium.getWidth(w, fontSize) + padding * 2.0f;
        float localHeight = fontSize + padding * 2.0f;
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, this.width - 2.0f, this.height);
        Scissor.unset();
        Scissor.pop();
        this.width = Math.max(maxWidth, 80.0f);
        this.height = localHeight + 2.5f;
        this.dragging.setWidth(this.width);
        this.dragging.setHeight(this.height);
    }

    public void drawRect(float x, float y, float width, float height) {
        Vector4i vector4i = new Vector4i(HUD.getColor(0, 1.0f), HUD.getColor(0, 1.0f), HUD.getColor(90, 1.0f), HUD.getColor(90, 1.0f));
        width = 28.0f;
        KawaseBlur.blur.updateBlur(1, 1); // STEPS - интенсивность блюра!
        float finalWidth = width;
        float finalWidth1 = width;
        float finalWidth2 = width;
        float finalWidth3 = width;

        DisplayUtils.drawRoundedRect(x + 30.0f, y, width, height + 10.0f, 4.0f, ColorUtils.rgba(21, 21, 21, 215));
        DisplayUtils.drawRoundedRect(x + 30.0f, y - 30.0f, width, height + 10.0f, 4.0f, ColorUtils.rgba(21, 21, 21, 215));
        DisplayUtils.drawRoundedRect(x, y + 30.0f, width + 60.0f, height + 7.0f, 4.0f, ColorUtils.rgba(21, 21, 21, 215));
        DisplayUtils.drawRoundedRect(x, y, width, height + 10.0f, 4.0f, ColorUtils.rgba(21, 21, 21, 215));
        DisplayUtils.drawRoundedRect(x + 60.0f, y, width, height + 10.0f, 4.0f, ColorUtils.rgba(21, 21, 21, 215));
        //DisplayUtils.drawRoundedRect(x, y + 55.0f, width + 60.0f, height - 10.0f, new Vector4f(4.0f, 4.0f, 4.0f, 4.0f), vector4i);
    }

    public void onClick(float x, float y, float width, float height) {
        width = 28.0f;
        if (KeyStrokes2Renderer.mc.gameSettings.keyBindBack.pressed) {
            DisplayUtils.drawRoundedRect(x + 30.0f, y, width, height + 10.0f, 4.0f, ColorUtils.rgba(255, 255, 255, 150));
        }
        if (KeyStrokes2Renderer.mc.gameSettings.keyBindJump.pressed) {
            DisplayUtils.drawRoundedRect(x, y + 30.0f, width + 60.0f, height + 7.0f, 4.0f, ColorUtils.rgba(255, 255, 255, 150));
        }
        if (KeyStrokes2Renderer.mc.gameSettings.keyBindForward.pressed) {
            DisplayUtils.drawRoundedRect(x + 30.0f, y - 30.0f, width, height + 10.0f, 4.0f, ColorUtils.rgba(255, 255, 255, 150));
        }
        if (KeyStrokes2Renderer.mc.gameSettings.keyBindLeft.pressed) {
            DisplayUtils.drawRoundedRect(x, y, width, height + 10.0f, 4.0f, ColorUtils.rgba(255, 255, 255, 150));
        }
        if (KeyStrokes2Renderer.mc.gameSettings.keyBindRight.pressed) {
            DisplayUtils.drawRoundedRect(x + 60.0f, y, width, height + 10.0f, 4.0f, ColorUtils.rgba(255, 255, 255, 150));
        }
    }

    public KeyStrokes2Renderer(Dragging dragging) {
        this.dragging = dragging;
    }
}

