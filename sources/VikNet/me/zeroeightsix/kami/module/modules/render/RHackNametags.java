package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.event.events.RenderEvent;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

/**
* PASTE CITY!!!!! THANKS JOHN!!!
 */
@Module.Info(name = "RHackNametags", category = Module.Category.RENDER)
public class RHackNametags extends Module {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);
    private Setting<Boolean> Armor = this.register(Settings.b("Armor", false));


    @Override
    public void onWorldRender(RenderEvent event) {
        for (EntityPlayer p : mc.world.playerEntities) {
            if ((p != mc.getRenderViewEntity()) && (p.isEntityAlive())) {
                double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * mc.timer.renderPartialTicks
                        - mc.getRenderManager().renderPosX;
                double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * mc.timer.renderPartialTicks
                        - mc.getRenderManager().renderPosY;
                double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * mc.timer.renderPartialTicks
                        - mc.getRenderManager().renderPosZ;
                if (!p.getName().startsWith("Body #")) {
                    renderNametag(p, pX, pY, pZ);
                }
            }
        }

    }

    private void renderNametag(EntityPlayer player, double x, double y, double z) {
        int l4 = 0;
        GL11.glPushMatrix();
        String name = player.getName() + "\u00A7a " + MathHelper.ceil(player.getHealth() + player.getAbsorptionAmount());
        name = name.replace(".0", "");
        float distance = mc.player.getDistance(player);
        float var15 = (distance / 5 <= 2 ? 2.0F : distance / 5) * 2.5f;
        float var14 = 0.016666668F * getNametagSize(player);

        GL11.glTranslated((float) x, (float) y + 2.5D, (float) z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-var14, -var14, var14);

        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GL11.glDisable(2929);
        int width = cFontRenderer.getStringWidth(name) / 2;

        drawBorderedRect(-width - 2, 10, width + 1, 20, 0,
                Friends.isFriend(name) ? new Color(0, 130, 130).getRGB() : 0x837d7d, -1);
        cFontRenderer.drawString(name, -width, 11, -1);

        int xOffset = 0;
        for (ItemStack armourStack : player.inventory.armorInventory) {
            if (armourStack != null) {
                xOffset -= 8;
            }
        }

        Object renderStack;
        if (player.getHeldItemMainhand() != null) {
            xOffset -= 8;
            renderStack = player.getHeldItemMainhand().copy();
            renderItem(player, (ItemStack) renderStack, xOffset, -10);
            xOffset += 16;
        }
        for (int index = 3; index >= 0; --index) {
            ItemStack armourStack = player.inventory.armorInventory.get(index);
            if (armourStack != null) {
                ItemStack renderStack1 = armourStack.copy();

                renderItem(player, renderStack1, xOffset, -10);
                xOffset += 16;
            }
        }

        ItemStack renderOffhand;
        if (player.getHeldItemOffhand() != null) {
            xOffset -= 0;
            renderOffhand = player.getHeldItemOffhand().copy();

            renderItem(player, renderOffhand, xOffset, -10);
            xOffset += 8;
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }

    private float getNametagSize(EntityLivingBase player) {
        // return mc.thePlayer.getDistanceToEntity(player) / 4.0F <= 2.0F ? 2.0F
        // : mc.thePlayer.getDistanceToEntity(player) / 4.0F;

        ScaledResolution scaledRes = new ScaledResolution(mc);
        double twoDscale = scaledRes.getScaleFactor() / Math.pow(scaledRes.getScaleFactor(), 2.0D);

        return (float) twoDscale + (mc.player.getDistance(player) / (0.7f * 10));
    }

    public void drawBorderRect(float left, float top, float right, float bottom, int bcolor, int icolor, float f) {
        drawGuiRect(left + f, top + f, right - f, bottom - f, icolor);
        drawGuiRect(left, top, left + f, bottom, bcolor);
        drawGuiRect(left + f, top, right, top + f, bcolor);
        drawGuiRect(left + f, bottom - f, right, bottom, bcolor);
        drawGuiRect(right - f, top + f, right, bottom - f, bcolor);
    }

    // man i love doubles!
    private void drawBorderedRect(double x, double y, double x1, double y1, double width, int internalColor,
                                  int borderColor) {
        enableGL2D();
        fakeGuiRect(x + width, y + width, x1 - width, y1 - width, internalColor);
        fakeGuiRect(x + width, y, x1 - width, y + width, borderColor);
        fakeGuiRect(x, y, x + width, y1, borderColor);
        fakeGuiRect(x1 - width, y, x1, y1, borderColor);
        fakeGuiRect(x + width, y1 - width, x1 - width, y1, borderColor);
        disableGL2D();
    }

    private void renderItem(EntityPlayer player, ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);

        GlStateManager.disableDepth();
        GlStateManager.enableDepth();

        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().zLevel = -100.0F;
        GlStateManager.scale(1, 1, 0.01f);
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, (y / 2) - 12);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, (y / 2) - 12);
        mc.getRenderItem().zLevel = 0.0F;
        GlStateManager.scale(1, 1, 1);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.disableDepth();
        renderEnchantText(player, stack, x, y - 18);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GL11.glPopMatrix();
    }

    private void renderEnchantText(EntityPlayer player, ItemStack stack, int x, int y) {
        int encY = y - 24;
        int yCount = encY - -5;
        if (stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemSword
                || stack.getItem() instanceof ItemTool) {
            cFontRenderer.drawStringWithShadow(stack.getMaxDamage() - stack.getItemDamage() + "\u00A74", x * 2 + 8, y + 26,
                    -1);
        }
        NBTTagList enchants = stack.getEnchantmentTagList();
        for (int index = 0; index < enchants.tagCount(); ++index) {
            short id = enchants.getCompoundTagAt(index).getShort("id");
            short level = enchants.getCompoundTagAt(index).getShort("lvl");
            Enchantment enc = Enchantment.getEnchantmentByID(id);
            if (enc != null) {
                String encName = enc.isCurse()
                        ? TextFormatting.RED
                        + enc.getTranslatedName(level).substring(11).substring(0, 1).toLowerCase()
                        : enc.getTranslatedName(level).substring(0, 1).toLowerCase();
                encName = encName + level;
                GL11.glPushMatrix();
                GL11.glScalef(0.9f, 0.9f, 0);
                cFontRenderer.drawStringWithShadow(encName, x * 2 + 13, yCount, -1);
                GL11.glScalef(1f, 1f, 1);
                GL11.glPopMatrix();
                encY += 8;
                yCount -= 10;
            }
        }
    }

    private void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    private void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    private void drawGuiRect(double x1, double y1, double x2, double y2, int color)
    {
        float red = (color>> 24 & 0xFF) / 255.0F;
        float green = (color>> 16 & 0xFF) / 255.0F;
        float blue = (color>> 8 & 0xFF) / 255.0F;
        float alpha = (color& 0xFF) / 255.0F;

        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);

        GL11.glPushMatrix();
        GL11.glColor4f(green, blue, alpha, red);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);

    }

    private void fakeGuiRect(double left, double top, double right, double bottom, int color)
    {
        if (left < right)
        {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, top, 0.0D).endVertex();
        bufferbuilder.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }


}
