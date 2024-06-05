package alphentus.utils;

import alphentus.init.Init;
import alphentus.mod.mods.hud.KeyStrokes;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao
 * @since on 04/08/2020.
 */
public class KeyStrokesUtil {

    private final int x;
    private int y;
    private float animation;
    private final int key;
    private final Translate translate;
    private final FontRenderer fontRenderer = Init.getInstance().fontManager.myinghei21;


    public KeyStrokesUtil(final int x, final int y, final int key) {
        this.x = x;
        this.y = y;
        this.key = key;
        this.translate = new Translate(0, 0);
    }

    public void draw() {
        int sizeWidthHeight = 20;

        boolean animation = Init.getInstance().modManager.getModuleByClass(KeyStrokes.class).animation.isState();
        boolean blur = Init.getInstance().modManager.getModuleByClass(KeyStrokes.class).blur.isState();

        if (blur) {
            Init.getInstance().tabGUIBlur.blur(x, y, sizeWidthHeight, sizeWidthHeight, 30);
            Init.getInstance().blurUtil.blur(x, y, sizeWidthHeight, sizeWidthHeight, 30);
        }
        GL11.glPushMatrix();

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GLUtil.makeScissorBox(x, y + 1, x + sizeWidthHeight, y + sizeWidthHeight);
        RenderUtils.relativeRect(x, y, x + sizeWidthHeight, y + sizeWidthHeight, new Color(0, 0, 0, 175).getRGB());
        if (Keyboard.isKeyDown(key) && !animation)
            RenderUtils.relativeRect(x, y, x + sizeWidthHeight, y + sizeWidthHeight, 0x99ffffff);

        if (animation) {
            if (Keyboard.isKeyDown(key)) {
                if (this.animation < 14)
                    this.animation += 0.1 * RenderUtils.deltaTime;
            } else {
                if (this.animation > 0)
                    this.animation -= 0.1 * RenderUtils.deltaTime;
            }
        }

        if (animation && this.animation > 0)
            RenderUtils.drawFilledCircle(x + sizeWidthHeight / 2, y + sizeWidthHeight / 2, this.animation, new Color(255, 255, 255, 150));
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();

        fontRenderer.drawStringWithShadow(Keyboard.getKeyName(key), x + sizeWidthHeight / 2 - fontRenderer.getStringWidth(Keyboard.getKeyName(key)) / 2 - 0.7F, y + sizeWidthHeight / 2 - fontRenderer.FONT_HEIGHT / 2 - 0.5F, 0xffffffff);

        // ANIMATION


    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
