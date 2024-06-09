/*
 * Decompiled with CFR 0_122.
 */
package winter.utils;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import winter.module.modules.ESP;
import winter.module.modules.Nametags;
import winter.module.modules.Speed;
import winter.module.modules.waypoint.WaypointUtil;
import winter.utils.friend.FriendUtil;
import winter.utils.value.types.BooleanValue;
import winter.utils.value.types.NumberValue;

public class Render {
    public static void drawRectWH(double x2, double y2, double width, double height, int color) {
        Gui.drawRect(x2, y2, x2 + width, y2 + height, color);
    }

    public static void drawRect(double x2, double y2, double x22, double y22, int color) {
        Gui.drawRect(x2, y2, x22, y22, color);
    }

    public static void drawBorderedRectWithoutATop(double x2, double y2, double x22, double y22, double thickness, int inside, int outline) {
        double fix = 0.0;
        if (thickness < 1.0) {
            fix = 1.0;
        }
        Render.drawRect(x2 + thickness, y2 + thickness, x22 - thickness, y22 - thickness, inside);
        Render.drawRect(x2, y2 + 1.0 - fix, x2 + thickness, y22, outline);
        Render.drawRect(x22 - thickness, y2, x22, y22 - 1.0 + fix, outline);
        Render.drawRect(x2 + 1.0 - fix, y22 - thickness, x22, y22, outline);
    }

    public static void drawBorderedRect(double x2, double y2, double x22, double y22, double thickness, int inside, int outline) {
        double fix = 0.0;
        if (thickness < 1.0) {
            fix = 1.0;
        }
        Render.drawRect(x2 + thickness, y2 + thickness, x22 - thickness, y22 - thickness, inside);
        Render.drawRect(x2, y2 + 1.0 - fix, x2 + thickness, y22, outline);
        Render.drawRect(x2, y2, x22 - 1.0 + fix, y2 + thickness, outline);
        Render.drawRect(x22 - thickness, y2, x22, y22 - 1.0 + fix, outline);
        Render.drawRect(x2 + 1.0 - fix, y22 - thickness, x22, y22, outline);
    }

    public static void wallhack(Entity e2, double x2, double y2, int color) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (e2 instanceof EntityPlayer) {
            double modifier = Minecraft.getMinecraft().gameSettings.ofKeyBindZoom.pressed ? 5 : 1;
            double fovMod = Minecraft.getMinecraft().gameSettings.fovSetting / 70.0f;
            double scaleMod = (double)Display.getWidth() / 856.0;
            double width = (double)((float)(player.isSprinting() ? 18 : 20) / (player.getDistanceToEntity(e2) / 10.0f)) * modifier / fovMod * scaleMod;
            double height = (double)((float)(player.isSprinting() ? 28 : 33) / (player.getDistanceToEntity(e2) / 10.0f)) * modifier / fovMod * scaleMod;
            x2 -= width / 2.0;
            if (ESP.box.isEnabled()) {
                Render.drawBorderedRect(x2 - 0.5, y2 - 0.5, x2 + width + 0.5, y2 + height + 0.5, 1.5, 0, -16777216);
                Render.drawBorderedRect(x2, y2, x2 + width, y2 + height, 0.5, 0, color);
            }
            double increment = (width + 0.5) / 20.0;
            if (ESP.health.isEnabled()) {
                Render.drawBorderedRect(x2 - 0.5, y2 + height + 1.0, x2 + increment * 20.0, y2 + height + 2.5, 0.5, -14540254, -16777216);
                Render.drawBorderedRect(x2 - 0.5, y2 + height + 1.0, x2 + increment * (double)((EntityLivingBase)e2).getHealth(), y2 + height + 2.5, 0.5, Render.healthColor(((EntityLivingBase)e2).getHealth()), -16777216);
            }
            float center = (float)x2 * 2.0f + (float)width - (float)(Minecraft.getMinecraft().fontRendererObj.getStringWidth(e2.getName()) / 2);
            String str = Render.clean(e2.getName());
            if (ESP.name.isEnabled()) {
                Render.drawCustomString(str, center, (float)y2 * 2.0f - 12.0f, -1);
            }
            if (((EntityPlayer)e2).getCurrentEquippedItem() != null) {
                ItemStack current = ((EntityPlayer)e2).getCurrentEquippedItem();
                str = Render.clean(current.getDisplayName());
                center = (float)x2 * 2.0f + (float)width - (float)(Minecraft.getMinecraft().fontRendererObj.getStringWidth(current.getDisplayName()) / 2);
                if (ESP.item.isEnabled()) {
                    Render.drawCustomString(str, center, ((float)y2 + (float)height) * 2.0f + 8.0f, -1);
                }
            }
        }
    }

    public static void nametag(Entity e2, double x2, double y2, int color) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (e2 instanceof EntityPlayer) {
            String str = FriendUtil.isAFriend(e2.getName()) ? String.valueOf(Render.clean(FriendUtil.getFriend(e2.getName()).getAlias())) + " " + (int)((EntityLivingBase)e2).getHealth() / 2 + "\u2764" : String.valueOf(Render.clean(e2.getName())) + " " + (int)((EntityLivingBase)e2).getHealth() / 2 + "\u2764";
            float center = (float)x2 * 2.0f - (float)(Minecraft.getMinecraft().fontRendererObj.getStringWidth(str) / 2);
            int width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(str) + 4;
            double inc = ((double)width + 0.5) / 20.0;
            GL11.glPushMatrix();
            GL11.glScaled(Nametags.scale.getValue(), Nametags.scale.getValue(), Nametags.scale.getValue());
            Render.drawRect(center - 3.0f, (float)y2 - 12.5f, center + (float)Minecraft.getMinecraft().fontRendererObj.getStringWidth(str) + 3.0f, (float)(y2 -= 1.0) - (str.contains("y") || str.contains("j") || str.contains("g") || str.contains("p") || str.contains("q") ? -1.0f : -1.0f), -1795162112);
            Render.drawCustomStringNametag(str, center, (float)y2 - 7.0f - 2.5f, Render.healthColor((int)((EntityLivingBase)e2).getHealth()));
            Render.drawCustomStringNametag(Render.clean(FriendUtil.isAFriend(e2.getName()) ? FriendUtil.getFriend(e2.getName()).getAlias() : e2.getName()), center, (float)y2 - 7.0f - 2.5f, color);
            ArrayList<ItemStack> equipped = new ArrayList<ItemStack>();
            ItemStack hand = ((EntityPlayer)e2).getCurrentEquippedItem();
            ItemStack[] items = e2.getInventory();
            if (hand != null) {
                equipped.add(hand);
            }
            if (items[3] != null) {
                equipped.add(items[3]);
            }
            if (items[2] != null) {
                equipped.add(items[2]);
            }
            if (items[1] != null) {
                equipped.add(items[1]);
            }
            if (items[0] != null) {
                equipped.add(items[0]);
            }
            center = (float)x2 * 2.0f - (float)(16 * equipped.size() / 2);
            x2 = center;
            for (ItemStack stack : equipped) {
                RenderHelper.enableGUIStandardItemLighting();
                Minecraft.getMinecraft().getRenderItem().func_175042_a(stack, (int)x2, (int)y2 - 29);
                Minecraft.getMinecraft().getRenderItem().func_175030_a(Minecraft.getMinecraft().fontRendererObj, stack, (int)x2, (int)y2 - 29);
                RenderHelper.disableStandardItemLighting();
                x2 += 16.0;
            }
            GL11.glPopMatrix();
        }
    }

    public static void waypoint(WaypointUtil.Point p2, double x2, double y2) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        double pX = p2.pos()[0];
        double pY = p2.pos()[1];
        double pZ = p2.pos()[2];
        double dist = Speed.round(player.getDistance(pX, pY, pZ), 1);
        String str = Render.clean(String.valueOf(p2.name()) + " [" + dist + "]");
        float center = - Minecraft.getMinecraft().fontRendererObj.getStringWidth(str) / 2;
        GL11.glPushMatrix();
        GL11.glScaled(0.75, 0.75, 0.75);
        Render.drawBorderedRect(center - 2.0f, (float)y2 - 14.0f, center + (float)Minecraft.getMinecraft().fontRendererObj.getStringWidth(str) + 2.0f, (float)y2 - 2.0f, 1.0, -1429089839, -1432774247);
        Render.drawCustomStringXD(str, center, (float)y2 * 2.0f - 12.0f, -9868951);
        GL11.glPopMatrix();
    }

    public static void drawCustomString(String str, float x2, float y2, int color) {
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2 - 1.0f, y2, 0);
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2, y2 - 1.0f, 0);
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2, y2 + 1.0f, 0);
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2 + 1.0f, y2, 0);
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2 + 1.0f, y2 - 1.0f, 0);
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2 - 1.0f, y2 - 1.0f, 0);
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2 - 1.0f, y2 + 1.0f, 0);
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2 + 1.0f, y2 + 1.0f, 0);
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2, y2, color);
        GL11.glPopMatrix();
    }

    public static void drawCustomStringNametag(String str, float x2, float y2, int color) {
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2, y2, color);
    }

    public static void drawCustomStringXD(String str, float x2, float y2, int color) {
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2 + 1.0f, y2 + 1.0f, -13421773);
        Minecraft.getMinecraft().fontRendererObj.drawString(str, x2, y2, color);
    }

    public static String clean(String text) {
        String cleaned = text;
        cleaned = cleaned.replaceAll("\u00a7a", "");
        cleaned = cleaned.replaceAll("\u00a7b", "");
        cleaned = cleaned.replaceAll("\u00a7c", "");
        cleaned = cleaned.replaceAll("\u00a7d", "");
        cleaned = cleaned.replaceAll("\u00a7e", "");
        cleaned = cleaned.replaceAll("\u00a7f", "");
        cleaned = cleaned.replaceAll("\u00a70", "");
        cleaned = cleaned.replaceAll("\u00a71", "");
        cleaned = cleaned.replaceAll("\u00a72", "");
        cleaned = cleaned.replaceAll("\u00a73", "");
        cleaned = cleaned.replaceAll("\u00a74", "");
        cleaned = cleaned.replaceAll("\u00a75", "");
        cleaned = cleaned.replaceAll("\u00a76", "");
        cleaned = cleaned.replaceAll("\u00a77", "");
        cleaned = cleaned.replaceAll("\u00a78", "");
        cleaned = cleaned.replaceAll("\u00a79", "");
        return cleaned;
    }

    public static int healthColor(float health) {
        int color = 0;
        if (health >= 20.0f) {
            color = -16736768;
        }
        if (health == 19.0f) {
            color = -15032832;
        }
        if (health == 18.0f) {
            color = -13984256;
        }
        if (health == 17.0f) {
            color = -12608000;
        }
        if (health == 16.0f) {
            color = -11362816;
        }
        if (health == 15.0f) {
            color = -10183168;
        }
        if (health == 14.0f) {
            color = -8937984;
        }
        if (health == 13.0f) {
            color = -7430656;
        }
        if (health == 12.0f) {
            color = -5456128;
        }
        if (health == 11.0f) {
            color = -2827520;
        }
        if (health == 10.0f) {
            color = -590080;
        }
        if (health == 9.0f) {
            color = -1192448;
        }
        if (health == 8.0f) {
            color = -1196544;
        }
        if (health == 7.0f) {
            color = -1203712;
        }
        if (health == 6.0f) {
            color = -1207808;
        }
        if (health == 5.0f) {
            color = -1212928;
        }
        if (health == 4.0f) {
            color = -1220864;
        }
        if (health == 3.0f) {
            color = -7921664;
        }
        if (health == 2.0f) {
            color = -9105408;
        }
        if (health == 1.0f) {
            color = -10944512;
        }
        if (health == 0.0f) {
            color = -12582912;
        }
        return color;
    }

    public static void beginGl() {
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.func_179090_x();
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
    }

    public static void endGl() {
        GL11.glLineWidth(2.0f);
        GL11.glDisable(2848);
        GlStateManager.func_179098_w();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public static void renderOne() {
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.5f);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1028, 6913);
    }

    public static void renderTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1028, 6914);
    }

    public static void renderThree() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1028, 6913);
    }

    public static void renderFour(Minecraft mc2, Entity renderEntity) {
        float[] color = new float[]{1.0f, 1.0f, 1.0f};
        if (renderEntity instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)renderEntity;
            float distance = mc2.thePlayer.getDistanceToEntity(entity);
            if (entity instanceof EntityPlayer && FriendUtil.isAFriend(entity.getName())) {
                color = new float[]{0.3f, 0.7f, 1.0f};
            } else if (entity.isInvisibleToPlayer(mc2.thePlayer)) {
                color = new float[]{1.0f, 1.0f, 1.0f};
            } else if (entity.hurtTime > 0) {
                color = new float[]{1.0f, 0.26f, 0.0f};
            } else if (distance <= 3.9f) {
                color = new float[]{1.0f, 1.0f, 1.0f};
            }
        } else {
            float distance = mc2.thePlayer.getDistanceToEntity(renderEntity);
            if (renderEntity.isInvisibleToPlayer(mc2.thePlayer)) {
                color = new float[]{1.0f, 1.0f, 1.0f};
            } else if (distance <= 3.9f) {
                color = new float[]{1.0f, 1.0f, 1.0f};
            }
        }
        GlStateManager.color(color[0], color[1], color[2], 1.0f);
        Render.renderFour();
    }

    public static void renderFour() {
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }

    public static void renderFive() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
}

