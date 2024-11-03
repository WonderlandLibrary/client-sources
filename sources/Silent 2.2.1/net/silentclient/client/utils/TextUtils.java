package net.silentclient.client.utils;


import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.*;

/**
 * TextUtils for drawing scaled string
 *
 *
 * I would like to get credited :)
 * @author wcaleniekubaa#4196
 * If there is any problem write to me :)
 * or to the Eric Golde discord server
 * you can also create issue on github
 *
 * @see FontRenderer#drawString(String, float, float, int, boolean)
 * You can also set the methods static lol
 *
 */
public class TextUtils {
    private final FontRenderer font;

    public TextUtils(FontRenderer font){
        this.font = font;
    }

    /**
     * Creates Scaled String
     */
    public void drawScaledString(String text, float x,float y, int color, float scale, boolean shadow){
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale,scale,scale);
        font.drawString(text,x,y,color,shadow);
        GlStateManager.popMatrix();
    }
    /**
     * Creates Centered Scaled String
     */
    public void drawCenteredScaledString(String text, int x,int y, int color, float scale){
        this.drawCenteredScaledString(text, x, y, color, scale, false);
    }

    public void drawCenteredScaledString(String text, int x,int y, int color, float scale, boolean shadow){
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale,scale,scale);
        /* make draw centered string static after it you can delete this comment*/
        this.drawCenteredString(text, (int) (x / scale), (int) (y / scale), color, shadow);
        GlStateManager.popMatrix();
    }

    public void drawCenteredString(String text, int x, int y, int color, boolean shadow)
    {
        font.drawString(text, (float)(x - font.getStringWidth(text) / 2), (float)y, color, shadow);
    }

    /**
     * Creates Scaled String <br>
     * (you can desort it)
     */
    public void drawScaledString(String text, int x,int y, int color, float scaleX, float scaleY, boolean shadow){
        GlStateManager.pushMatrix();
        GlStateManager.scale(scaleX,scaleY,scaleY); /* Text isn't 3d */
        font.drawString(text,x,y,color,shadow);
        GlStateManager.popMatrix();
    }
    /**
     * Creates Centered Scaled String <br>
     * (you can disort it) :)
     */
    public void drawCenteredScaledString(String text, int x,int y, int color, float scaleX, float scaleY){
        GlStateManager.pushMatrix();
        GlStateManager.scale(scaleX,scaleY,scaleY); /* Text isn't 3d */
        /* make draw centered string static after it you can delete this comment*/
        this.drawCenteredString(text, (int) (x / scaleX), (int) (y / scaleY), -1, false);
        GlStateManager.popMatrix();
    }
}