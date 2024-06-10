package me.sleepyfish.smok.gui.guis;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.misc.FileUtils;
import me.sleepyfish.smok.utils.render.GlUtils;
import me.sleepyfish.smok.utils.misc.MouseUtils;
import me.sleepyfish.smok.utils.render.RenderUtils;
import me.sleepyfish.smok.utils.entities.capes.Cape;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import java.awt.Color;

// Class from SMok Client by SleepyFish
public class CosmeticsGui extends GuiScreen {

    private float capeSliderValueY;

    @Override
    public void initGui() {
        this.capeSliderValueY = 0.0F;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawCustomGui(this.width, this.height);

        GlUtils.disableSeeThru();

        GL11.glPushMatrix();
        GL11.glRotatef(5, 0.0f, 1.0f, 0.0f);

        drawEntityOnScreen(this.width / 2 + 165, this.height / 2 + 60, 85, 0, this.mc.thePlayer);
        GlUtils.stopScale();

        GlUtils.enableSeeThru();

        //FontUtils.r20.drawString("Hint: Drag to move", this.width / 2F + 125, this.height / 2F + 75, ColorUtils.getFontColor(1));

        int index = 0;
        int index2 = 1;
        int offsetX = 45;
        int offsetY = 15;

        if (Smok.inst.capeManager == null)
            return;

        for (Cape cape : Smok.inst.capeManager.getCapes()) {
            if (cape == null)
                return;

            drawRound(this.width / 2F - 243 + offsetX - 1, this.height / 2F - 150 + offsetY - 1 + this.capeSliderValueY, 60 + 2, 90 + 2, 6, ColorUtils.getBackgroundColor(3).darker());
            drawRound(this.width / 2F - 243 + offsetX, this.height / 2F - 150 + offsetY + this.capeSliderValueY, 60, 90, 6, ColorUtils.getBackgroundColor(3));

            if (MouseUtils.isInside(MouseUtils.getX(), MouseUtils.getY(), this.width / 2F - 243 + offsetX - 1, this.height / 2F - 150 + offsetY - 1 + this.capeSliderValueY, 60 + 2, 90 + 2)) {
                drawRound(this.width / 2F - 243 + offsetX - 1, this.height / 2F - 150 + offsetY - 1 + this.capeSliderValueY, 60 + 2, 90 + 2, 6, ColorUtils.getBackgroundColor(3).darker());
            }

            this.mc.getTextureManager().bindTexture(cape.getPreview());

            if (!cape.getName().equals("None")) {
                GlStateManager.enableBlend();
                RenderUtils.drawRoundTextured(this.width / 2F - 243 + offsetX + 8, this.height / 2F - 150 + offsetY + 6 + this.capeSliderValueY, 44, 70, 4, 1F);
                GlStateManager.disableBlend();
            }

            FontUtils.r20.drawString(cape.getName(), this.width / 2F - 243 + offsetX + (50 - FontUtils.r20.getStringWidth(cape.getName())), (int) (this.height / 2 - 150 + offsetY + 80 + this.capeSliderValueY), ColorUtils.getFontColor(1));

            index++;
            offsetX += 70;
            if (index == index2 * 4) {
                index2++;
                offsetX = 45;
                offsetY += 100;
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y, int b) {
        int index = 0;
        int index2 = 1;
        int offsetX = 45;
        int offsetY = 15;

        for (Cape cape : Smok.inst.capeManager.getCapes()) {
            if (cape != null) {
                if (MouseUtils.isInside(x, y, this.width / 2F - 243 + offsetX + 8, this.height / 2F - 150 + offsetY + 6 + this.capeSliderValueY, 44, 70)) {
                    Smok.inst.capeManager.setCurrentCape(cape);
                    return;
                }

                index++;
                offsetX += 70;
                if (index == index2 * 4) {
                    index2++;
                    offsetX = 45;
                    offsetY += 100;
                }
            }
        }
    }

    @Override
    public void updateScreen() {
        this.capeSliderValueY += MouseUtils.getScroll();
    }

    public static void drawCustomGui(float width, float height) {
        drawRoundCustom(width / 2F - 213F, height / 2F - 150F, 500F, 300F, 10F, ColorUtils.getBackgroundColor(3, 190).darker().darker(), false, false, true, true);
        drawRound(width / 2F - 214F, height / 2F - 150F, 1F, 300F, 1F, ColorUtils.getBackgroundColor(3, 190).brighter());
        drawRoundCustom(width / 2F - 250F, height / 2F - 150F, 37F, 300F, 10F, ColorUtils.getBackgroundColor(3, 190).darker().darker().darker(), true, true, false, false);
        //RenderUtils.drawImageWC(FileUtils.path + "/gui/icons/back.png", (int) (width / 2 - 237), (int) (height / 2 + 121 + 8), 10, 10, ColorUtils.getFontColor(1));
    }

    public static void drawRound(float x, float y, float width, float height, float radius, Color color) {
        drawRoundCustom(x, y, width, height, radius, color, true, true, true, true);
    }

    public static void drawRoundCustom(float x, float y, float width, float height, float radius, Color c, boolean leftTop, boolean leftBottom, boolean rightBottom, boolean rightTop) {
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GlStateManager.enableBlend();
        x *= 2D;
        y *= 2D;
        width *= 2D;
        height *= 2D;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        ColorUtils.clearColor();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        ColorUtils.setColor(c);
        int i;

        if (leftTop)
            for (i = 0; i <= 90; i += 3)
                GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180D) * radius * -1D, y + radius + Math.cos(i * Math.PI / 180D) * radius * -1D);
        else GL11.glVertex2d(x, y);

        if (leftBottom)
            for (i = 90; i <= 180; i += 3)
                GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180D) * radius * -1D, y + height - radius + Math.cos(i * Math.PI / 180D) * radius * -1D);
        else GL11.glVertex2d(x, y + height);

        if (rightBottom)
            for (i = 0; i <= 90; i += 3)
                GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180D) * radius, y + height - radius + Math.cos(i * Math.PI / 180D) * radius);
        else GL11.glVertex2d(x + width, y + height);

        if (rightTop)
            for (i = 90; i <= 180; i += 3)
                GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180D) * radius, y + radius + Math.cos(i * Math.PI / 180D) * radius);
        else GL11.glVertex2d(x + width, y);

        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.disableBlend();
        GL11.glScaled(2D, 2D, 2D);
        GL11.glPopAttrib();
    }

    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseY, EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, 50.0F);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);

        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;

        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan((mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);

        float newRotation = 210.0F;
        ent.renderYawOffset = newRotation;
        ent.rotationYaw = newRotation;
        ent.rotationPitch = -((float) Math.atan((mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = newRotation;
        ent.prevRotationYawHead = newRotation;

        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

}