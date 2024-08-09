package src.Wiksi.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.functions.impl.render.HUD;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.math.Vector4i;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.RenderUtils;
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.font.Fonts;
import lombok.Getter;
import net.minecraft.util.math.vector.Vector4f;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;


public class KeyStrokesRenderer implements ElementRenderer {
    private final Dragging dragging;
    private float width = 20.0f;
    private float height = 20.0f;
    @Getter
    private static Animation animation = new Animation();
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        int firstColor = ColorUtils.getColor(0);
        int secondColor = ColorUtils.getColor(0);
        int alpha = 0;
        float posX = this.dragging.getX();
        float posY = this.dragging.getY();
        float fontSize = 9.5f;
        float padding = 8.0f;
        float width = 20.0f;
        float height = 20.0f;
        float maxWidth = 15.0f;
        float localWidth = 15.0f;
        float localHeight = 15.0f;
        Scissor.push();
        String w = "W";
        String a = "A";
        String s = "S";
        String d = "D";
        String inv = "SPACE";
        DisplayUtils.drawRoundedRect(posX, posY, width, height, 4.0f, ColorUtils.setAlpha(ColorUtils.rgb(21,24,40), 255));
        Fonts.sfMedium.drawText(ms, w, posX + 4.2f, posY + 6.1f, -1, fontSize);
        DisplayUtils.drawRoundedRect(posX, posY + 23.0f, width, height, 4.0f, ColorUtils.setAlpha(ColorUtils.rgb(21,24,40), 255));
        Fonts.sfMedium.drawText(ms, s, posX + 6.0f, posY + 28.0f, -1, fontSize);
        DisplayUtils.drawRoundedRect(posX - 23.0f, posY + 23.0f, width, height, 4.0f, ColorUtils.setAlpha(ColorUtils.rgb(21,24,40), 255));
        Fonts.sfMedium.drawText(ms, a, posX - 17.95f, posY + 28.0f, -1, fontSize + 1.0f);
        DisplayUtils.drawRoundedRect(posX + 23.0f, posY + 23.0f, width, height, 4.0f, ColorUtils.setAlpha(ColorUtils.rgb(21,24,40), 255));
        Fonts.sfMedium.drawText(ms, d, posX + 29.0f, posY + 28.0f, -1, fontSize);
        DisplayUtils.drawRoundedRect(posX - 23.0f, posY + 45.0f, width + 47.0f, height, 4.0f, ColorUtils.setAlpha(ColorUtils.rgb(21,24,40), 255));
        Fonts.sfMedium.drawText(ms, inv, posX - 8.7f, posY + 50.5f, -1, fontSize);

        if (KeyStrokesRenderer.mc.gameSettings.keyBindForward.isKeyDown()) {
            DisplayUtils.drawRoundedRect(posX, posY, width, height, new Vector4f(5.0f, 5.0f, 5.0f, 5.0f), new Vector4i(HUD.getColor(0, 1), HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(90, 1)));
            Fonts.sfMedium.drawText(ms, w, posX + 4.2f, posY + 6.1f, ColorUtils.rgba(255, 255, 255, 255), fontSize);
            
            DisplayUtils.drawRoundedRect(posX, posY, width, height, 4.0f, ColorUtils.setAlpha(ColorUtils.getColor(0), alpha));
        }
        if (KeyStrokesRenderer.mc.gameSettings.keyBindBack.isKeyDown()) {
            DisplayUtils.drawRoundedRect(posX, posY + 23.0f, width, height, new Vector4f(5.0f, 5.0f, 5.0f, 5.0f), new Vector4i(HUD.getColor(0, 1), HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(90, 1)));
            Fonts.sfMedium.drawText(ms, s, posX + 6.0f, posY + 28.0f, ColorUtils.rgba(255, 255, 255, 255), fontSize);
        }
        if (KeyStrokesRenderer.mc.gameSettings.keyBindLeft.isKeyDown()) {
            DisplayUtils.drawRoundedRect(posX - 23.0f, posY + 23.0f, width, height, new Vector4f(5.0f, 5.0f, 5.0f, 5.0f), new Vector4i(HUD.getColor(0, 1), HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(90, 1)));
            Fonts.sfMedium.drawText(ms, a, posX - 17.95f, posY + 28.0f, ColorUtils.rgba(255, 255, 255, 255), fontSize + 1.0f);
        }
        if (KeyStrokesRenderer.mc.gameSettings.keyBindRight.isKeyDown()) {
            DisplayUtils.drawRoundedRect(posX + 23.0f, posY + 23.0f, width, height, new Vector4f(5.0f, 5.0f, 5.0f, 5.0f), new Vector4i(HUD.getColor(0, 1), HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(90, 1)));
            Fonts.sfMedium.drawText(ms, d, posX + 29.0f, posY + 28.0f, ColorUtils.rgba(255, 255, 255, 255), fontSize);
        }
        if (KeyStrokesRenderer.mc.gameSettings.keyBindJump.isKeyDown()) {
            DisplayUtils.drawRoundedRect(posX - 23.0f, posY + 45.0f, width + 47.0f, height, new Vector4f(5.0f, 5.0f, 5.0f, 5.0f), new Vector4i(HUD.getColor(0, 1), HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(90, 1)));
            Fonts.sfMedium.drawText(ms, inv, posX - 8.7f, posY + 50.3f, ColorUtils.rgba(255, 255, 255, 255), fontSize);
        }
        if (localWidth > maxWidth) {
            maxWidth = localWidth;
        }
        Scissor.unset();
        Scissor.pop();
        width = Math.max(maxWidth, 50.0f);
        height = localHeight + 40.5f;
        animation = animation.animate(2, 0.5f, Easings.BACK_IN);
        animation.update();
        this.dragging.setWidth(width);
        this.dragging.setHeight(height);
    }

    public KeyStrokesRenderer(Dragging dragging) {
        this.dragging = dragging;
    }
}
