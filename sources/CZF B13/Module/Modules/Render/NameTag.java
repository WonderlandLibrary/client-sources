/*
 * Decompiled with CFR 0_132.
 *
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender3D;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Manager.FriendManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Combat.AntiBot;
import gq.vapu.czfclient.Module.Modules.Misc.Teams;
import gq.vapu.czfclient.UI.Font.CFontRenderer;
import gq.vapu.czfclient.UI.Font.FontLoaders;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class NameTag extends Module {
    CFontRenderer Font = FontLoaders.GoogleSans20;
    private final Mode<Enum> health = new Mode("Health Mode", "healthmode", HealthMode.values(), HealthMode.Hearts);
    private final Option<Boolean> armor = new Option<Boolean>("Armor", "armor", true);
    private final Option<Boolean> dura = new Option<Boolean>("Durability", "durability", true);
    private final Numbers<Double> scale = new Numbers<Double>("Scale", "scale", 3.0, 1.0, 5.0, 0.1);
    private final ArrayList<Entity> entities = new ArrayList();
    private final Option<Boolean> players = new Option<Boolean>("Players", "players", true);
    private final Option<Boolean> animals = new Option<Boolean>("Animals", "animals", true);
    private final Option<Boolean> mobs = new Option<Boolean>("Mobs", "mobs", false);
    private final Option<Boolean> invis = new Option<Boolean>("Invisibles", "invisibles", true);
    private final int i = 0;

    public NameTag() {
        super("Nametags", new String[]{"tags"}, ModuleType.Render);
        this.setColor(new Color(29, 187, 102).getRGB());
        this.addValues(this.health, this.armor, this.dura, this.invis, this.scale);
    }

    public static void setGlState(final int cap, final boolean state) {
        if (state)
            GL11.glEnable(cap);
        else
            GL11.glDisable(cap);
    }

    @EventHandler
    private void onRender(EventRender3D render) {
        ArrayList<EntityPlayer> validEnt = new ArrayList<EntityPlayer>();
        if (validEnt.size() > 100) {
            validEnt.clear();
        }
        for (EntityLivingBase player2 : mc.theWorld.playerEntities) {
            if (player2.isEntityAlive()) {
                if (player2.isInvisible() && !this.invis.getValue().booleanValue()) {
                    if (!validEnt.contains(player2)) continue;
                    validEnt.remove(player2);
                    continue;
                }
                if (player2 == mc.thePlayer) {
                    if (!validEnt.contains(player2)) continue;
                    validEnt.remove(player2);
                    continue;
                }
                if (validEnt.size() > 100) break;
                if (validEnt.contains(player2)) continue;
                validEnt.add((EntityPlayer) player2);
                continue;
            }
            if (!validEnt.contains(player2)) continue;
            validEnt.remove(player2);
        }
        validEnt.forEach(player -> {
            float x = (float) (player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) render.getPartialTicks() - mc.getRenderManager().viewerPosX);
            float y = (float) (player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) render.getPartialTicks() - mc.getRenderManager().viewerPosY);
            float z = (float) (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) render.getPartialTicks() - mc.getRenderManager().viewerPosZ);
            this.renderNametag(player, x, y, z);
        });
    }

    private String getHealth(EntityPlayer player) {
        DecimalFormat numberFormat = new DecimalFormat("0.#");
        return this.health.getValue() == HealthMode.Percentage ? numberFormat.format(player.getHealth() * 5.0f + player.getAbsorptionAmount() * 5.0f) : numberFormat.format(player.getHealth() / 2.0f + player.getAbsorptionAmount() / 2.0f);
    }

    private void drawNames(EntityPlayer player) {
        float xP = 2.2f;
        float width = (float) this.getWidth(this.getPlayerName(player)) / 2.0f + xP;
        float w = width = (float) ((double) width + ((double) (this.getWidth(" " + this.getHealth(player)) / 2) + 2.5));
        float nw = -width - xP;
        float offset = this.getWidth(this.getPlayerName(player)) + 4;
        if (this.health.getValue() == HealthMode.Percentage) {
            RenderUtil.drawBorderedRect(nw, -3.0f, width, 10.0f, 1.0f, new Color(20, 20, 20, 180).getRGB(), new Color(10, 10, 10, 200).getRGB());
        } else {
            RenderUtil.drawBorderedRect(nw + 5.0f, -3.0f, width, 10.0f, 1.0f, new Color(20, 20, 20, 180).getRGB(), new Color(10, 10, 10, 200).getRGB());
        }
        GlStateManager.disableDepth();
        offset = this.health.getValue() == HealthMode.Percentage ? (offset += (float) (this.getWidth(this.getHealth(player)) + this.getWidth(" %") - 1)) : (offset += (float) (this.getWidth(this.getHealth(player)) + this.getWidth(" ") - 1));
        this.drawString(this.getPlayerName(player), w - offset, 0.0f, 16777215);
        if (player.getHealth() == 10.0f) {
            int n = 16776960;
        }
        int color = player.getHealth() > 10.0f ? RenderUtil.blend(new Color(-16711936), new Color(-256), 1.0f / player.getHealth() / 2.0f * (player.getHealth() - 10.0f)).getRGB() : RenderUtil.blend(new Color(-256), new Color(-65536), 0.1f * player.getHealth()).getRGB();
        if (this.health.getValue() == HealthMode.Percentage) {
            this.drawString(this.getHealth(player) + "%", w - (float) this.getWidth(this.getHealth(player) + " %"), 0.0f, color);
        } else {
            this.drawString(this.getHealth(player), w - (float) this.getWidth(this.getHealth(player) + " "), 0.0f, color);
        }
        GlStateManager.enableDepth();
    }

    private void drawString(String text, float x, float y, int color) {
        mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
//        CFontRenderer font = FontLoaders.GoogleSans20;
//        font.drawString(text, x, y, color);
    }

    private int getWidth(String text) {
        return mc.fontRendererObj.getStringWidth(text);
    }

    private void startDrawing(float x, float y, float z, EntityPlayer player) {
        float var10001 = mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f;
        double size = (double) (this.getSize(player) / 10.0f) * this.scale.getValue() * 1.5;
        GL11.glPushMatrix();
        RenderUtil.startDrawing();
        GL11.glTranslatef(x, y, z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(RenderManager.playerViewX, var10001, 0.0f, 0.0f);
        GL11.glScaled(-0.01666666753590107 * size, -0.01666666753590107 * size, 0.01666666753590107 * size);
    }

    private void stopDrawing() {
        RenderUtil.stopDrawing();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }

    private void renderNametag(EntityPlayer player, float x, float y, float z) {
        if (AntiBot.isServerBot(player))
            return;
        y = (float) ((double) y + (1.55 + (player.isSneaking() ? 0.5 : 0.7)));
        this.startDrawing(x, y, z, player);
        this.drawNames(player);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        if (this.armor.getValue().booleanValue()) {
            this.renderArmor(player);
        }
        this.stopDrawing();
    }

    private void renderArmor(EntityPlayer player) {
        ItemStack armourStack;
        ItemStack[] renderStack = player.inventory.armorInventory;
        int length = renderStack.length;
        int xOffset = 0;
        ItemStack[] arritemStack = renderStack;
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack aRenderStack = arritemStack[n2];
            armourStack = aRenderStack;
            if (armourStack != null) {
                xOffset -= 8;
            }
            ++n2;
        }
        if (player.getHeldItem() != null) {
            xOffset -= 8;
            ItemStack stock = player.getHeldItem().copy();
            if (stock.hasEffect() && (stock.getItem() instanceof ItemTool || stock.getItem() instanceof ItemArmor)) {
                stock.stackSize = 1;
            }
            this.renderItemStack(stock, xOffset, -20);
            xOffset += 16;
        }
        renderStack = player.inventory.armorInventory;
        int index = 3;
        while (index >= 0) {
            armourStack = renderStack[index];
            if (armourStack != null) {
                this.renderItemStack(armourStack, xOffset, -20);
                xOffset += 16;
            }
            --index;
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public boolean Bot(Entity entity) {
        if (this.isEnabled()) {
            if (Helper.onServer("hypixel")) {
                return !entity.getDisplayName().getFormattedText().startsWith("\u00a7") || entity.isInvisible() || entity.getDisplayName().getFormattedText().toLowerCase().contains("npc");
            }
        }
        return false;
    }

    private String getPlayerName(EntityPlayer player) {
        String name = player.getDisplayName().getFormattedText();
        if (this.Bot(player)) {
            name = "\247a[Bot]" + FriendManager.getAlias(player.getName());
            return "NPC";
        }
        if (FriendManager.isFriend(player.getName())) {
            name = "\247a[Friend]" + FriendManager.getAlias(player.getName());
        }
        if (Teams.isOnSameTeam(player)) {
            name = "\247a[Teams]" + player.getDisplayName().getFormattedText();
        }
        return name;
    }

    private float getSize(EntityPlayer player) {
        return mc.thePlayer.getDistanceToEntity(player) / 4.0f <= 2.0f ? 2.0f : mc.thePlayer.getDistanceToEntity(player) / 4.0f;
    }

    private void renderItemStack(ItemStack stack, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, x, y);
        mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        double s = 0.5;
        GlStateManager.scale(s, s, s);
        GlStateManager.disableDepth();
        this.renderEnchantText(stack, x, y);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }

    private void renderEnchantText(ItemStack stack, int x, int y) {
        CFontRenderer font2 = FontLoaders.GoogleSans20;
        int unbreakingLevel2;
        int enchantmentY = y - 24;
        if (stack.getEnchantmentTagList() != null && stack.getEnchantmentTagList().tagCount() >= 6) {
            mc.fontRendererObj.drawStringWithShadow("god", x * 2, enchantmentY, 16711680);
//            font2.drawString("god", x * 2, enchantmentY, 16711680);
            return;
        }
        if (stack.getItem() instanceof ItemArmor) {
            CFontRenderer font = FontLoaders.GoogleSans16;
            int protectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
            int projectileProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack);
            int blastProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack);
            int fireProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack);
            int thornsLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
            int unbreakingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            int damage = stack.getMaxDamage() - stack.getItemDamage();
            if (this.dura.getValue().booleanValue()) {
                mc.fontRendererObj.drawStringWithShadow("" + damage, x * 2, y, 16777215);//
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("" + damage, x * 2, y, 16777215);
            }
            if (protectionLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("prot" + protectionLevel, x * 2, enchantmentY, -1);//
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("prot" + protectionLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (projectileProtectionLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("proj" + projectileProtectionLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("proj" + projectileProtectionLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (blastProtectionLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("bp" + blastProtectionLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("bp" + blastProtectionLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (fireProtectionLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("frp" + fireProtectionLevel, x * 2, enchantmentY, -1);//
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("frp" + fireProtectionLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (thornsLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("th" + thornsLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("th" + thornsLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (unbreakingLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("unb" + unbreakingLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("unb" + unbreakingLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
        }
        if (stack.getItem() instanceof ItemBow) {
            CFontRenderer font = FontLoaders.GoogleSans16;
            int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            int flameLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
            unbreakingLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (powerLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("pow" + powerLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("pow" + powerLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (punchLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("pun" + punchLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("pun" + punchLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (flameLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("flame" + flameLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("flame" + flameLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (unbreakingLevel2 > 0) {
                mc.fontRendererObj.drawStringWithShadow("unb" + unbreakingLevel2, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("unb" + unbreakingLevel2, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
        }
        if (stack.getItem() instanceof ItemSword) {
            CFontRenderer font = FontLoaders.GoogleSans16;
            int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
            int knockbackLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
            int fireAspectLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
            unbreakingLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (sharpnessLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("sh" + sharpnessLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("sh" + sharpnessLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (knockbackLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("kb" + knockbackLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("kb" + knockbackLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (fireAspectLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("fire" + fireAspectLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("fire" + fireAspectLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (unbreakingLevel2 > 0) {
                mc.fontRendererObj.drawStringWithShadow("unb" + unbreakingLevel2, x * 2, enchantmentY, -1);
//                font.drawString("unb" + unbreakingLevel2, x * 2, enchantmentY, -1);
            }
        }
        if (stack.getItem() instanceof ItemTool) {
            int unbreakingLevel22 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
            int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack);
            int silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, stack);
            if (efficiencyLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("eff" + efficiencyLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("eff" + efficiencyLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (fortuneLevel > 0) {
                mc.fontRendererObj.drawStringWithShadow("fo" + fortuneLevel, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("fo" + fortuneLevel, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (silkTouch > 0) {
                mc.fontRendererObj.drawStringWithShadow("silk" + silkTouch, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("silk" + silkTouch, x * 2, enchantmentY, -1);
                enchantmentY += 8;
            }
            if (unbreakingLevel22 > 0) {
                mc.fontRendererObj.drawStringWithShadow("ub" + unbreakingLevel22, x * 2, enchantmentY, -1);
//                CFontRenderer font = FontLoaders.GoogleSans16;
//                font.drawString("ub" + unbreakingLevel22, x * 2, enchantmentY, -1);
            }
        }
        if (stack.getItem() == Items.golden_apple && stack.hasEffect()) {
            mc.fontRendererObj.drawStringWithShadow("god", x * 2, enchantmentY, -1);
//            CFontRenderer font = FontLoaders.GoogleSans16;
//            font.drawString("god", x * 2, enchantmentY, -1);
        }
    }


    enum HealthMode {
        Hearts,
        Percentage
    }

}

