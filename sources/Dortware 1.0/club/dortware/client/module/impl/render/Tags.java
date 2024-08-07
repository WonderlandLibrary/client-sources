package club.dortware.client.module.impl.render;

import club.dortware.client.event.impl.Render3DEvent;
import club.dortware.client.event.impl.RenderPlayerTagEvent;
import club.dortware.client.module.Module;
import club.dortware.client.module.annotations.ModuleData;
import club.dortware.client.module.enums.ModuleCategory;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@ModuleData(name = "Name Tags", category = ModuleCategory.RENDER)
public class Tags extends Module {
    @Override
    public void setup() {

    }

    private final DecimalFormat decimalFormat = new DecimalFormat("#.#");

    @Subscribe
    public void onRender(Render3DEvent event) {
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (!isValidEntity(player))
                continue;

            float partialTicks = event.getPartialTicks();
            double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks - mc.getRenderManager().renderPosX;
            double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks - mc.getRenderManager().renderPosY;
            double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks - mc.getRenderManager().renderPosZ;

            drawNametags(player, x, y, z);
        }
    }

    @Subscribe
    public void onNameTagRender(RenderPlayerTagEvent event) {
        event.setCancelled(true);
    }

    private boolean isValidEntity(Entity entity) {
        return entity != null && entity != mc.thePlayer && !entity.isDead;
    }

    public void drawNametags(EntityLivingBase entity, double x, double y, double z) {
        String entityName = entity.getDisplayName().getFormattedText();

        if (getNametagColor(entity) != 0xFFFFFFFF)
            entityName = StringUtils.stripControlCodes(entityName);

        if ((entity instanceof EntityPlayer) && ((EntityPlayer) entity).capabilities.isFlying)
            entityName = "\247a[F] \247r" + entityName;

        if ((entity instanceof EntityPlayer) && ((EntityPlayer) entity).capabilities.isCreativeMode)
            entityName = "\247a[C] \247r" + entityName;

        if (entity.getDistanceToEntity(mc.thePlayer) >= 64) {
            entityName = "\2472* \247r" + entityName;
        }

        double health = entity.getHealth() / 2;
        double maxHealth = entity.getMaxHealth() / 2;
        double percentage = 100 * (health / maxHealth);
        String healthColor;
        if (percentage > 75)
            healthColor = "2";
        else if (percentage > 50)
            healthColor = "e";
        else if (percentage > 25)
            healthColor = "6";
        else
            healthColor = "4";

        String healthDisplay = decimalFormat.format(Math.floor((health + (double)0.5F / 2) / 0.5F) * 0.5F);
        String maxHealthDisplay = decimalFormat.format(Math.floor((entity.getMaxHealth() + (double)0.5F / 2) / 0.5F) * 0.5F);
        entityName = String.format("%s \247%s%s", entityName, healthColor, healthDisplay);

        float distance = mc.thePlayer.getDistanceToEntity(entity);
        float var13 = (distance / 5 <= 2 ? 2.0F : distance / 5) * 1.0f;
        float var14 = 0.016666668F * var13;
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(x + 0.0F, y + entity.height + 0.5F, z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        if (mc.gameSettings.thirdPersonView == 2) {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, -1.0F, 0.0F, 0.0F);
        } else {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        }
        GlStateManager.scale(-var14, -var14, var14);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        int var17 = 0;
        if (entity.isSneaking()) {
            var17 += 4;
        }

        var17 -= distance / 5;
        if (var17 < -8) {
            var17 = -8;
        }

        int var18 = mc.fontRendererObj.getStringWidth(entityName) / 2;


        mc.fontRendererObj.drawStringWithShadow(entityName, -var18, var17, -1);
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            List<ItemStack> items = new ArrayList<>();
            if (player.getCurrentEquippedItem() != null) {
                items.add(player.getCurrentEquippedItem());
            }

            for (int index = 3; index >= 0; index--) {
                ItemStack stack = player.inventory.armorInventory[index];
                if (stack != null) {
                    items.add(stack);
                }
            }

            int offset = var18 - (items.size() - 1) * 9 - 9;
            int xPos = 0;
            for (ItemStack stack : items) {
                GlStateManager.pushMatrix();
                RenderHelper.enableStandardItemLighting();
                mc.getRenderItem().zLevel = -100.0F;
                mc.getRenderItem().func_175042_a(stack, -var18 + offset + xPos, var17 - 20);
                mc.getRenderItem().func_180453_a(mc.fontRendererObj, stack, -var18 + offset + xPos, var17 - 20, "");
                mc.getRenderItem().zLevel = 0.0F;
                RenderHelper.disableStandardItemLighting();
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                GlStateManager.disableLighting();
                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GlStateManager.disableDepth();
                GlStateManager.scale(0.50F, 0.50F, 0.50F);
                if (stack.getItem() == Items.golden_apple && stack.hasEffect()) {
                    mc.fontRendererObj.drawStringWithShadow("god", (-var18 + offset + xPos) * 2, (var17 - 20) * 2, 0xFFFF0000);
                } else {
                    NBTTagList enchants = stack.getEnchantmentTagList();

                    if (enchants != null) {
                        int encY = 0;
                        Enchantment[] important = new Enchantment[]{Enchantment.field_180310_c, Enchantment.unbreaking, Enchantment.field_180314_l, Enchantment.fireAspect, Enchantment.efficiency, Enchantment.field_180309_e, Enchantment.power, Enchantment.flame, Enchantment.punch, Enchantment.fortune, Enchantment.infinity, Enchantment.thorns};
                        if (enchants.tagCount() >= 6) {
                            mc.fontRendererObj.drawStringWithShadow("god", (-var18 + offset + xPos) * 2, (var17 - 20) * 2, 0xFFFF0000);
                        } else {
                            for (int index = 0; index < enchants.tagCount(); ++index) {
                                short id = enchants.getCompoundTagAt(index).getShort("id");
                                short level = enchants.getCompoundTagAt(index).getShort("lvl");
                                Enchantment enc = Enchantment.func_180306_c(id);
                                if (enc != null) {
                                    for (Enchantment importantEnchantment : important) {
                                        if (enc == importantEnchantment) {
                                            String encName = enc.getTranslatedName(level).substring(0, 1).toLowerCase();
                                            if (level > 99)
                                                encName = encName + "99+";
                                            else
                                                encName = encName + level;
                                            mc.fontRendererObj.drawStringWithShadow(encName, (-var18 + offset + xPos) * 2, (var17 - 20 + encY) * 2, 0xFFAAAAAA);
                                            encY += 5;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
                xPos += 18;
            }
        }
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public int getNametagColor(EntityLivingBase entity) {
        int color = 0xFFFFFFFF;
        if (entity.isInvisibleToPlayer(mc.thePlayer)) {
            color = 0xFFFFE600;
        } else if (entity.isSneaking()) {
            color = 0xFFFF0000;
        }
        return color;
    }

}
