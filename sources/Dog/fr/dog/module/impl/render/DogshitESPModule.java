package fr.dog.module.impl.render;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.render.Render3DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.module.impl.combat.AntiBot;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class DogshitESPModule extends Module {
    public DogshitESPModule() {
        super("RavenESP", ModuleCategory.RENDER);
    }

    @SubscribeEvent
    public void onRender2D(Render3DEvent event) {
        AntiBot antiBotModule = Dog.getInstance().getModuleManager().getModule(AntiBot.class);

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityPlayer player) {
                if (player != mc.thePlayer && player.deathTime == 0 &&
                        (!antiBotModule.isEnabled() || !antiBotModule.isBot(player))) {
                    drawBoxAroundEntity(player, 0.0, 0.0);
                }
            }
        }
    }

    //the number of variables here, ewwwwwwwwwwwwwwwwwwwwwwwwww,
    public static void drawBoxAroundEntity(Entity e, double expand, double shift) {
        if (e instanceof EntityLivingBase en) {
            double x = (e.lastTickPosX + ((e.posX - e.lastTickPosX) * (double) mc.timer.renderPartialTicks)) - RenderManager.viewerPosX;
            double y = (e.lastTickPosY + ((e.posY - e.lastTickPosY) * (double) mc.timer.renderPartialTicks)) - RenderManager.viewerPosY;
            double z = (e.lastTickPosZ + ((e.posZ - e.lastTickPosZ) * (double) mc.timer.renderPartialTicks)) - RenderManager.viewerPosZ;

            float scale = (float) expand / 40.0F;

            GlStateManager.pushMatrix();
            double factor = en.getHealth() / en.getMaxHealth();

            int xCoord = (int) (21.0D + (shift * 2.0D));
            int yCoord = (int) (74.0D * factor);

            int healthColor = factor < 0.3D ? Color.red.getRGB() : (factor < 0.5D ? Color.orange.getRGB() : (factor < 0.7D ? Color.yellow.getRGB() : Color.green.getRGB()));

            glTranslated(x, y - 0.2D, z);
            glRotated(-mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
            GlStateManager.disableDepth();
            glScalef(0.03F + scale, 0.03F + scale, 0.03F + scale);

            Gui.drawRect(xCoord,    -1,          xCoord + 5, 75,     Color.black.getRGB());
            Gui.drawRect(xCoord + 1, yCoord,     xCoord + 4, 74,     Color.darkGray.getRGB());
            Gui.drawRect(xCoord + 1, 0,          xCoord + 4, yCoord, healthColor);

            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
    }
}
