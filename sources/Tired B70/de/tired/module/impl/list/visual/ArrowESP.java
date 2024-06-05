package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.extension.processors.extensions.generally.RenderProcessor;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.impl.ColorPickerSetting;
import de.tired.event.EventTarget;
import de.tired.event.events.Render2DEvent;
import de.tired.interfaces.FHook;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.module.ModuleManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleAnnotation(name = "ArrowESP", category = ModuleCategory.RENDER, clickG = "Render arrows to the players")
public class ArrowESP extends Module {


    public NumberSetting radius = new NumberSetting("radius", this, 12, 1, 220, 1);
    public NumberSetting size = new NumberSetting("size", this, 12, 1, 120, 1);
    public ColorPickerSetting colorPickerSetting = new ColorPickerSetting("ColorArrowESP", this, true, new Color(0, 0, 0, 255), (new Color(0, 0, 0, 255)).getRGB(), null);

    @EventTarget
    public void onRender(Render2DEvent e) {


    }


    public static ArrowESP getInstance() {
        return ModuleManager.getInstance(ArrowESP.class);
    }


    public void renderArrow() {
        ScaledResolution e = new ScaledResolution(MC);
        MC.theWorld.loadedEntityList.forEach(entity -> {
            if (isValid(entity)) {

                float yaw = getYawToEntity(entity) - MC.thePlayer.rotationYaw;
                int posX = e.getScaledWidth() / 2;
                int posY = e.getScaledHeight() / 2;
                int y = posY - radius.getValueInt();
                GlStateManager.pushMatrix();
                GL11.glTranslatef((float) posX, (float) posY, 0);
                GL11.glRotatef(yaw, 0, 0, 1);
                GL11.glTranslatef((float) (-posX), (float) (-posY), 0);

                boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_TEXTURE_2D);

                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glBegin(GL11.GL_LINE_STRIP);


                RenderProcessor.glColor(ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());

                GL11.glVertex2d(posX, y);
                GL11.glVertex2d(posX - size.getValue() / 2, y + size.getValue());
                GL11.glVertex2d(posX, y + size.getValue());
                GL11.glVertex2d(posX + size.getValue() / 2, y + size.getValue());
                GL11.glVertex2d(posX, y);
                GL11.glEnd();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                if (!blend) {
                    GL11.glDisable(GL11.GL_BLEND);
                }
                GL11.glDisable(GL11.GL_LINE_SMOOTH);

                GL11.glTranslatef((float) posX, (float) posY, 0);
                GL11.glRotatef(-yaw, 0, 0, 1);
                GL11.glTranslatef((float) (-posX), (float) (-posY), 0);
                GlStateManager.popMatrix();
                GlStateManager.resetColor();
            }

        });
    }

    public void renderArrow2() {
        ScaledResolution e = new ScaledResolution(MC);
        MC.theWorld.loadedEntityList.forEach(entity -> {
            if (isValid(entity)) {

                float yaw = getYawToEntity(entity) - MC.thePlayer.rotationYaw;
                int posX = e.getScaledWidth() / 2;
                int posY = e.getScaledHeight() / 2;
                int y = posY - radius.getValueInt();
                GlStateManager.pushMatrix();
                GL11.glTranslatef((float) posX, (float) posY, 0);
                GL11.glRotatef(yaw, 0, 0, 1);
                GL11.glTranslatef((float) (-posX), (float) (-posY), 0);
				if (size.getValue() < 17) {
					//Gui.drawRect(posX - FHook.fontRenderer4.getStringWidth(Math.round(entity.getDistanceToEntity(MC.thePlayer)) + ""), y + size.getValue() -  2, posX + FHook.fontRenderer4.getStringWidth(Math.round(entity.getDistanceToEntity(MC.thePlayer)) + ""),  y +  size.getValue() +FHook.fontRenderer4.FONT_HEIGHT - 5, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());
					//Gui.drawRect(posX - FHook.fontRenderer4.getStringWidth(Math.round(entity.getDistanceToEntity(MC.thePlayer)) + ""), y + size.getValue(), posX + FHook.github.getStringWidth(Math.round(entity.getDistanceToEntity(MC.thePlayer)) + ""),  y +  size.getValue() + FHook.fontRenderer4.FONT_HEIGHT, Integer.MIN_VALUE);
				} else {
				//	Gui.drawRect(posX - FHook.fontRenderer3.getStringWidth(Math.round(entity.getDistanceToEntity(MC.thePlayer)) + ""), y + size.getValue() +  FHook.fontRenderer3.FONT_HEIGHT, posX + FHook.fontRenderer3.getStringWidth(Math.round(entity.getDistanceToEntity(MC.thePlayer)) + ""),  y +  size.getValue() + FHook.fontRenderer3.FONT_HEIGHT, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());
					//Gui.drawRect(posX - FHook.fontRenderer3.getStringWidth(Math.round(entity.getDistanceToEntity(MC.thePlayer)) + ""), y + size.getValue(), posX + FHook.fontRenderer3.getStringWidth(Math.round(entity.getDistanceToEntity(MC.thePlayer)) + ""),  y +  size.getValue() +  FHook.fontRenderer3.FONT_HEIGHT, Integer.MIN_VALUE);
				}

       //         FHook.github.drawString(Math.round(entity.getDistanceToEntity(MC.thePlayer)) + "", (float) tabGui.calculateMiddle(Math.round(entity.getDistanceToEntity(MC.thePlayer)) + "", FHook.github, (float) (posX - size.getValue() / 2), size.getValueFloat()), (float) (y + size.getValue() + 6), -1);
                boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_TEXTURE_2D);

                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glBegin(9);


                RenderProcessor.glColor(ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());

                GL11.glVertex2d(posX, y);
                GL11.glVertex2d(posX - size.getValue() / 2, y + size.getValue());
                GL11.glVertex2d(posX, y + size.getValue());
                GL11.glVertex2d(posX + size.getValue() / 2, y + size.getValue());
                GL11.glVertex2d(posX, y);
                GL11.glEnd();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                if (!blend) {
                    GL11.glDisable(GL11.GL_BLEND);
                }
                GL11.glDisable(GL11.GL_LINE_SMOOTH);

                GL11.glTranslatef((float) posX, (float) posY, 0);
                GL11.glRotatef(-yaw, 0, 0, 1);
                GL11.glTranslatef((float) (-posX), (float) (-posY), 0);
                GlStateManager.popMatrix();
                GlStateManager.resetColor();
            }

        });
    }

    private boolean isValid(Entity entity) {
        return entity != MC.thePlayer && entity.getEntityId() != -1488 && isValidType(entity) && (!entity.isInvisible() && (entity instanceof EntityLivingBase && (!(((EntityLivingBase) entity).getHealth() <= 0 || entity.isDead))));
    }

    private boolean isValidType(Entity entity) {
        return (entity instanceof EntityPlayer);
    }

    private Color getColor(Entity player) {
        Color clr = null;
        float f = MC.thePlayer.getDistanceToEntity(player);
        float f2 = 40.0f;
        float f3 = Math.max(0.0f, Math.min(f, f2) / f2);
        clr = new Color(Color.HSBtoRGB(f3 / 3.0f, 1.0f, 1.0f));
        return new Color(clr.getRed(), clr.getGreen(), clr.getBlue(), 5);
    }

    private float getYawToEntity(Entity entity) {
        double diffX = entity.posX - MC.thePlayer.posX;
        double diffZ = entity.posZ - MC.thePlayer.posZ;
        return (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }

}
