package me.xatzdevelopments.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventRenderNametag;
import me.xatzdevelopments.events.listeners.EventRenderWorld;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.util.font.FontUtil;
import me.xatzdevelopments.util.font.MinecraftFontRenderer;


public class Nametags2 extends Module {
    NumberSetting scale = new NumberSetting("Scale", 5, 3, 8, 0.2);

    BooleanSetting healthBar = new BooleanSetting("Health Bar", true),
            armor = new BooleanSetting("Armor", true),
            background = new BooleanSetting("Background", true);

    public Nametags2() {
        super("Nametags444", Keyboard.KEY_NONE, Category.RENDER, "Seeeeeeex");
        addSettings(background, scale, healthBar, armor);
    }

    public static void renderItem(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().zLevel = -100.0f;
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
        GlStateManager.enableDepth();
        Minecraft.getMinecraft().getRenderItem().func_180450_b(stack, x, y + 8);
        //mc.getRenderItem().renderItemOverlayIntoGUINameTags(mc.fontRendererObj, stack, x - 1, y + 10, null);
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0f;
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        //NameTags.renderEnchantText(stack, x, y);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }

    public void onEvent(Event e) {
        if (e instanceof EventRenderNametag) {
            e.setCancelled(true);
        }
        if (e instanceof EventRenderWorld) {
            MinecraftFontRenderer fr = FontUtil.cleanlarge;

            for (final Object o : mc.theWorld.loadedEntityList) {
                if (o instanceof EntityPlayer) {
                    final EntityPlayer player = (EntityPlayer)o;

                if (player.isInvisible() || player == mc.thePlayer)
                    continue;

                GL11.glPushMatrix();


                double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
                double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
                double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
                //float distance = mc.thePlayer.getDistanceToEntity(entity);


                GL11.glTranslated(x, y + player.getEyeHeight() + 1.7, z);
                GL11.glNormal3f(0, 1, 0);
                if (mc.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
                    GlStateManager.rotate(-mc.getRenderManager().playerViewX, 1, 0, 0);
                } else {
                    GlStateManager.rotate(-mc.thePlayer.rotationYaw, 0, 1, 0);
                    GlStateManager.rotate(mc.thePlayer.rotationPitch, 1, 0, 0);
                }
                float distance = mc.thePlayer.getDistanceToEntity(player),
                        scaleConst_1 = 0.02672f, scaleConst_2 = 0.10f;
                double maxDist = 7.0;


                float scaleFactor = (float) (distance <= maxDist ? maxDist * scaleConst_2 : (double) (distance * scaleConst_2));
                scaleConst_1 *= scaleFactor;

                float scaleBet = (float) (scale.getValue() * 10E-3);
                scaleConst_1 = Math.min(scaleBet, scaleConst_1);


                GL11.glScalef(-scaleConst_1, -scaleConst_1, .2f);

                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GL11.glDisable(GL11.GL_DEPTH_TEST);


                String colorCode = player.getHealth() > 15 ? "\247a" : player.getHealth() > 10 ? "\247e" : player.getHealth() > 7 ? "\2476" : "\247c";
                int colorrectCode = player.getHealth() > 15 ? 0xff4DF75B : player.getHealth() > 10 ? 0xffF1F74D : player.getHealth() > 7 ? 0xffF7854D : 0xffF7524D;
                String thing = player.getName() + " " + colorCode + (int) player.getHealth();
                float namewidth = (float) fr.getStringWidth(thing);


                Gui.drawRect(-namewidth / 2 - 2, 42, namewidth / 2 + 2, 40, 0x90080808);


                if (healthBar.isEnabled())
                    Gui.drawRect(-namewidth / 2 - 15, 42, namewidth / 2 + 15 - (1 - (player.getHealth() / player.getMaxHealth())) * (namewidth + 4), 40, colorrectCode);

                if (background.isEnabled())
                    Gui.drawRect(-namewidth / 2 - 15, 20, namewidth / 2 + 15, 40, 0x90202020);


                fr.drawCenteredStringWithShadow(player.getName(), -20, 23, -1);
                fr.drawCenteredStringWithShadow(colorCode + (int) player.getHealth(), namewidth / 2, 23, -1);

                GlStateManager.disableBlend();
                GlStateManager.depthMask(true);
                GL11.glEnable(GL11.GL_DEPTH_TEST);


                double movingArmor = 1.2;

                if (namewidth <= 65) {
                    movingArmor = 2;
                }
                if (namewidth <= 85) {
                    movingArmor = 1.2;
                }

                if (namewidth <= 100) {
                    movingArmor = 1.1;
                }

                if (armor.isEnabled()) {
                    for (int index = 0; index < 5; index++) {

                        if (player.getEquipmentInSlot(index) == null)
                            continue;


                        renderItem(player.getEquipmentInSlot(index), (int) (index * 19 / movingArmor) - 30, -10);


                    }
                }

                GL11.glPopMatrix();

            }
        }

    }

    }
}

