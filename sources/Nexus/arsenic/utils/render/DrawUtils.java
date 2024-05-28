package arsenic.utils.render;

import arsenic.utils.java.UtilityClass;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import static net.minecraft.client.renderer.GlStateManager.resetColor;
import static org.lwjgl.opengl.GL11.*;

public class DrawUtils extends UtilityClass {

    // horrible shitcode need to fix

    public static void drawRect(float x, float y, float x1, float y1, int color) {
        float finalX = x * 2f;
        float finalY = y * 2f;
        float finalX1 = x1 * 2f;
        float finalY1 = y1 * 2f;

        drawCustom(color, () -> {
            glVertex2d(finalX, finalY1);
            glVertex2d(finalX1, finalY1);
            glVertex2d(finalX1, finalY);
            glVertex2d(finalX, finalY);
        });
    }

    public static void drawCustom(int color, Runnable v) {
        setup(color);
        glBegin(9);
        v.run();
        finish();
    }


    //no worke :(
    public static void drawCustomOutline(int color, float borderWidth, Runnable v) {
        setupOutline(color, borderWidth);
        v.run();
        finishOutline();
    }
    public static void drawCustomWithOutline(int fillColour, int borderColour, float borderWidth, Runnable v) {
        drawCustom(fillColour, v);
        drawCustomOutline(borderColour, borderWidth, v);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, final float radius, final int color, boolean[] round) {
        float finalX = x * 2f;
        float finalY = y * 2f;
        float finalX1 = x1 * 2f;
        float finalY1 = y1 * 2f;

        drawCustom(color, () -> round(finalX, finalY, finalX1, finalY1, radius, round));
    }
    public static void drawRoundedOutline(float x, float y, float x1, float y1, final float radius, final float borderSize, final int color, boolean[] round) {
        float finalX = x * 2f;
        float finalY = y * 2f;
        float finalX1 = x1 * 2f;
        float finalY1 = y1 * 2f;

        drawCustomOutline(color, borderSize, () -> round(finalX, finalY, finalX1, finalY1, radius, round));
    }

    private static void roundHelper(float x, float y, float radius, int pn, int pn2, int originalRotation,
                                    int finalRotation) {
        for (int i = originalRotation; i <= finalRotation; i += 1)
            glVertex2d(x + (radius * -pn) + (Math.sin((i * Math.PI) / 180.0) * radius * pn),
                    y + (radius * pn2) + (Math.cos((i * Math.PI) / 180.0) * radius * pn));
    }

    private static void round(float x, float y, float x1, float y1, float radius, final boolean[] round) {
        if (round[0])
            roundHelper(x, y, radius, -1, 1, 0, 90);
        else
            glVertex2d(x, y);

        if (round[1])
            roundHelper(x, y1, radius, -1, -1, 90, 180);
        else
            glVertex2d(x, y1);

        if (round[2])
            roundHelper(x1, y1, radius, 1, -1, 0, 90);
        else
            glVertex2d(x1, y1);

        if (round[3])
            roundHelper(x1, y, radius, 1, 1, 90, 180);
        else
            glVertex2d(x1, y);
    }

    public static void drawRoundedOutline(float x, float y, float x1, float y1, final float radius, final float borderSize, final int color) {
        drawRoundedOutline(x, y, x1, y1, radius, borderSize, color, new boolean[]{true, true, true, true});
    }

    public static void drawBorderedRoundedRect(float x, float y, float d, float y1, float radius, float borderSize, int borderC, int insideC, boolean[] round) {
        drawRoundedRect(x, y, d, y1, radius, insideC, round);
        drawRoundedOutline(x, y, d, y1, radius, borderSize, borderC, round);
    }

    //y = lower, y1 = upper
    public static void drawRoundedRect(float x, float y, float x1, float y1, final float radius, final int color) {
        drawRoundedRect(x, y, x1, y1, radius, color, new boolean[]{true, true, true, true});
    }

    public static void drawBorderedRoundedRect(float x, float y, float x1, float y1, float radius, float borderSize, int borderC, int insideC) {
        drawRoundedRect(x, y, x1, y1, radius, insideC);
        drawRoundedOutline(x, y, x1, y1, radius, borderSize, borderC);
    }

    public static void drawBorderedCircle(float centrePointX, float centrePointY, float radius, float borderSize, int borderColour, int insideColour) {
        drawCircle(centrePointX, centrePointY, radius, insideColour);
        drawCircleOutline(centrePointX, centrePointY, radius, borderSize, borderColour);
    }

    public static void drawCircleOutline(float centrePointX, float centrePointY, float radius, float borderSize, int color) {
        float circleX1 = centrePointX - radius;
        float circleX2 = centrePointX + radius;
        float circleY1 = centrePointY - radius;
        float circleY2 = centrePointY + radius;
        drawRoundedOutline(circleX1, circleY1, circleX2, circleY2, radius * 2, borderSize, color);
    }

    public static void drawCircle(float centrePointX, float centrePointY, float radius, int color) {
        float circleX1 = centrePointX - radius;
        float circleX2 = centrePointX + radius;
        float circleY1 = centrePointY - radius;
        float circleY2 = centrePointY + radius;
        drawRoundedRect(circleX1, circleY1, circleX2, circleY2, radius * 2, color);
    }

    //draws a perfect triangle when height == width
    public static void drawTriangle(float x1, float y1, float width, float height, int colour) {
        final float realY1 = y1 * 2;
        final float realX1 = x1 * 2;
        final float realWidth = width * 2;
        final float realHeight = (float) ((height)*Math.sqrt(3));
        DrawUtils.drawCustom(colour, () -> {
            if(realHeight > 0) {
                glVertex2d(realX1, realY1);
                glVertex2d(realX1 + (realWidth/2f), realY1 + realHeight);
                glVertex2d(realX1 + realWidth, realY1);
            } else {
                glVertex2d(realX1 + (realWidth/2f), realY1 + realHeight);
                glVertex2d(realX1, realY1);
                glVertex2d(realX1 + realWidth, realY1);
            }
        });
    }
    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        GLUtil.setup2DRendering();
        glEnable(GL_LINE_SMOOTH);
        glShadeModel(GL_SMOOTH);
        glPushMatrix();
        glBegin(GL_QUADS);
        color(startColor);
        glVertex2d(left, top);
        glVertex2d(left, bottom);
        color(endColor);
        glVertex2d(right, bottom);
        glVertex2d(right, top);
        glEnd();
        glPopMatrix();
        glDisable(GL_LINE_SMOOTH);
        GLUtil.end2DRendering();
        resetColor();
    }
    // This method colors the next avalible texture with a specified alpha value ranging from 0-1
    public static void color(int color, float alpha) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GlStateManager.color(r, g, b, alpha);
    }

    // Colors the next texture without a specified alpha value
    public static void color(int color) {
        color(color, (float) (color >> 24 & 255) / 255.0F);
    }
    public static void drawGradientRectBordered(double left, double top, double right, double bottom, double width, int startColor, int endColor, int borderStartColor, int borderEndColor) {
        drawGradientRect(left + width, top + width, right - width, bottom - width, startColor, endColor);
        drawGradientRect(left + width, top, right - width, top + width, borderStartColor, borderEndColor);
        drawGradientRect(left, top, left + width, bottom, borderStartColor, borderEndColor);
        drawGradientRect(right - width, top, right, bottom, borderStartColor, borderEndColor);
        drawGradientRect(left + width, bottom - width, right - width, bottom, borderStartColor, borderEndColor);
    }
    private static void setup(int color) {
        glScaled(0.5, 0.5, 0.5);
        glPushAttrib(0);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glEnable(GL_LINE_SMOOTH);
        RenderUtils.setColor(color);
    }

    private static void finish() {
        glEnd();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glScalef(2f, 2f, 2f);
        glPopAttrib();
        RenderUtils.resetColor();
    }

    private static void setupOutline(int color, float borderSize) {
        setup(color);
        glLineWidth(borderSize);
        glBegin(2);
    }
    private static void finishOutline() {
        finish();
        glLineWidth(1.0f);
    }

}
