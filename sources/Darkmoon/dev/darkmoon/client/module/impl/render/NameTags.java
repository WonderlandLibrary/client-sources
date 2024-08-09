package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import com.jhlabs.vecmath.Vector4f;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.impl.util.NameProtect;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ModuleAnnotation(name = "NameTags", category = Category.RENDER)
public class NameTags extends Module {
    @EventTarget
    public void onRender(EventRender2D event) {
        final RenderManager renderMng = mc.getRenderManager();
        final EntityRenderer entityRenderer = mc.entityRenderer;

        for (EntityPlayer entity : mc.world.playerEntities) {
            if (mc.player == entity && mc.gameSettings.thirdPersonView == 0) continue;

            DarkMoon.getInstance().getScaleMath().pushScale();
            if (RenderUtility.isInViewFrustum(entity)) {
                double x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.getPartialTicks());
                double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.getPartialTicks());
                double z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.getPartialTicks());
                double width = entity.width / 1.5;
                double height = entity.height + 0.2f - (entity.isSneaking() ? 0.2f : 0.0f);

                final AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                final Vec3d[] vectors = {new Vec3d(aabb.minX, aabb.minY, aabb.minZ), new Vec3d(aabb.minX, aabb.maxY, aabb.minZ), new Vec3d(aabb.maxX, aabb.minY, aabb.minZ), new Vec3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vec3d(aabb.minX, aabb.minY, aabb.maxZ), new Vec3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vec3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vec3d(aabb.maxX, aabb.maxY, aabb.maxZ)};
                entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);

                Vector4f position = null;
                for (Vec3d vector : vectors) {
                    vector = RenderUtility.project2D(2, vector.x - renderMng.viewerPosX, vector.y - renderMng.viewerPosY, vector.z - renderMng.viewerPosZ);
                    if (vector != null && vector.z >= 0.0 && vector.z < 1.0) {
                        if (position == null) {
                            position = new Vector4f((float) vector.x, (float) vector.y, (float) vector.z, 1.0f);
                        }
                        position.x = (float) Math.min(vector.x, position.x);
                        position.y = (float) Math.min(vector.y, position.y);
                        position.z = (float) Math.max(vector.x, position.z);
                        position.w = (float) Math.max(vector.y, position.w);
                    }
                }

                if (position != null) {
                    mc.entityRenderer.setupOverlayRendering(2);
                    double posX = position.x;
                    double posY = position.y;
                    double endPosX = position.z;
                    float center = (float) (posX + (endPosX - posX) / 2f);

                    String tagName;
                    if (DarkMoon.getInstance().getModuleManager().getModule(NameProtect.class).isEnabled()) {
                        if (DarkMoon.getInstance().getFriendManager().isFriend(entity.getName())) {
                            tagName = ChatFormatting.GREEN + "[F] " + ChatFormatting.RESET + entity.getDisplayName().getFormattedText().replace(entity.getName(), NameProtect.protectedNick).concat(" " +
                                    ColorUtility.getHealthStr(entity) + (int) entity.getHealth() + " HP");
                        } else {
                            tagName = entity.getDisplayName().getFormattedText().replace(mc.session.getUsername(), ChatFormatting.RED + NameProtect.protectedNick + ChatFormatting.RESET).concat(" " +
                                    ColorUtility.getHealthStr(entity) + (int) entity.getHealth() + " HP");
                        }
                    } else {
                        if (DarkMoon.getInstance().getFriendManager().isFriend(entity.getName())) {
                            tagName = ChatFormatting.GREEN + "[F] " + ChatFormatting.RESET + entity.getDisplayName().getFormattedText().concat(" " +
                                    ColorUtility.getHealthStr(entity) + (int) entity.getHealth() + " HP");
                        } else {
                            tagName = entity.getDisplayName().getFormattedText().concat(" " +
                                    ColorUtility.getHealthStr(entity) + (int) entity.getHealth() + " HP");
                        }
                    }


                    if (!entity.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + entity.getName()).getBytes(StandardCharsets.UTF_8)))) {
                        tagName = ChatFormatting.GRAY + "[" + ChatFormatting.RED + "BOT" + ChatFormatting.GRAY + "]" + ChatFormatting.RESET + " " + entity.getDisplayName().getFormattedText();
                    }

                    int bgWidth = Fonts.tenacityBold12.getStringWidth(tagName) + 5;
                   // RenderUtility.drawRoundedRect(center - bgWidth / 2f, (float) posY - 14.5f, bgWidth, Fonts.tenacityBold12.getFontHeight() + 4, 3, new Color(0, 0, 0).getRGB());
                    Fonts.tenacityBold12.drawString(tagName, center - Fonts.tenacityBold12.getStringWidth(tagName) / 2f, (float) posY - 12, -1);

                    List<ItemStack> stacks = new ArrayList<>(Arrays.asList(entity.getHeldItemMainhand(), entity.getHeldItemOffhand()));
                    entity.getArmorInventoryList().forEach(stacks::add);
                    stacks.removeIf(w -> w.getItem() instanceof ItemAir);
                    int totalSize = stacks.size() * 10;
                    int iterable = 0;
                    for (ItemStack stack : stacks) {
                        if (stack != null) {
                            GlStateManager.pushMatrix();
                            GlStateManager.translate(center + iterable * 20 - totalSize + 2, posY - 35, 0);
                            RenderUtility.drawItemStack(stack, 0, 0);
                            GlStateManager.popMatrix();
                            iterable++;

                            ArrayList<String> lines = new ArrayList<>();
                            getEnchantment(lines, stack);

                            int i = 0;
                            for (String s : lines) {
                                Fonts.nunito12.drawCenteredStringWithShadow(s,
                                        center + iterable * 20 - totalSize - 10,
                                        (float) posY - 37.5f - (6) - (i * 7),
                                        0xFFFFFFFF);
                                i++;
                            }
                        }
                    }

                    List<String> potions = new ArrayList<>();

                    for (PotionEffect potionEffect : entity.getActivePotionEffects()) {
                        String power = Hud.getPotionPower(potionEffect);
                        ChatFormatting potionColor = null;
                        if ((potionEffect.getDuration() < 200)) {
                            potionColor = ChatFormatting.RED;
                        } else if (potionEffect.getDuration() < 400) {
                            potionColor = ChatFormatting.GOLD;
                        } else if (potionEffect.getDuration() > 400) {
                            potionColor = ChatFormatting.GREEN;
                        }
                        if (potionEffect.getDuration() != 0) {
                            potions.add(I18n.format(potionEffect.getPotion().getName()) + " " + power + TextFormatting.GRAY + " " + potionColor + Hud.getDuration(potionEffect));
                        }
                    }

                    float startY = 0;

                    for (String s : potions) {
                        Fonts.nunito12.drawString(s, endPosX + 5, posY + 10 + startY, -1);
                        startY += 10;
                    }
                }

                mc.entityRenderer.setupOverlayRendering();
            }
            DarkMoon.getInstance().getScaleMath().popScale();
        }
    }

    public static void getEnchantment(ArrayList<String> list, ItemStack stack) {
        Item item = stack.getItem();
        int protection = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack);
        int thorns = EnchantmentHelper.getEnchantmentLevel(Enchantments.THORNS, stack);
        int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
        int mending = EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, stack);
        int feather = EnchantmentHelper.getEnchantmentLevel(Enchantments.FEATHER_FALLING, stack);
        int depth = EnchantmentHelper.getEnchantmentLevel(Enchantments.DEPTH_STRIDER, stack);
        int vanishing_curse = EnchantmentHelper.getEnchantmentLevel(Enchantments.VANISHING_CURSE, stack);
        int binding_curse = EnchantmentHelper.getEnchantmentLevel(Enchantments.BINDING_CURSE, stack);
        int sweeping = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING, stack);
        int sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
        int looting = EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, stack);
        int infinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack);
        int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
        int punch = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
        int flame = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack);
        int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack);
        int fireAspect = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);
        int efficiency = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        int silktouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack);
        int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
        int fireprot = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_PROTECTION, stack);
        int blastprot = EnchantmentHelper.getEnchantmentLevel(Enchantments.BLAST_PROTECTION, stack);

        if (item instanceof ItemAxe) {
            if (sharpness > 0) {
                list.add("Shr" + sharpness);
            }
            if (efficiency > 0) {
                list.add("Eff" + efficiency);
            }
            if (unbreaking > 0) {
                list.add("Unb" + unbreaking);
            }
        }
        if (item instanceof ItemArmor) {
            if (vanishing_curse > 0) {
                list.add("Vanish ");
            }
            if (fireprot > 0) {
                list.add("Firp" + fireprot);
            }
            if (blastprot > 0) {
                list.add("Bla" + blastprot);
            }
            if (binding_curse > 0) {
                list.add("§cBindi ");
            }
            if (depth > 0) {
                list.add("Dep" + depth);
            }
            if (feather > 0) {
                list.add("Fea" + feather);
            }
            if (protection > 0) {
                list.add("Pro" + protection);
            }
            if (thorns > 0) {
                list.add("Thr" + thorns);
            }
            if (mending > 0) {
                list.add("Men ");
            }
            if (unbreaking > 0) {
                list.add("Unb" + unbreaking);
            }
        }
        if (item instanceof ItemBow) {
            if (vanishing_curse > 0) {
                list.add("Vanish ");
            }
            if (binding_curse > 0) {
                list.add("Binding ");
            }
            if (infinity > 0) {
                list.add("Inf" + infinity);
            }
            if (power > 0) {
                list.add("Pow" + power);
            }
            if (punch > 0) {
                list.add("Pun" + punch);
            }
            if (mending > 0) {
                list.add("Men ");
            }
            if (flame > 0) {
                list.add("Fla" + flame);
            }
            if (unbreaking > 0) {
                list.add("Unb" + unbreaking);
            }
        }
        if (item instanceof ItemSword) {
            if (vanishing_curse > 0) {
                list.add("Vanish ");
            }
            if (looting > 0) {
                list.add("Loot" + looting);
            }
            if (binding_curse > 0) {
                list.add("Bindi ");
            }
            if (sweeping > 0) {
                list.add("Swe" + sweeping);
            }
            if (sharpness > 0) {
                list.add("Shr" + sharpness);
            }
            if (knockback > 0) {
                list.add("Kno" + knockback);
            }
            if (fireAspect > 0) {
                list.add("Fir" + fireAspect);
            }
            if (unbreaking > 0) {
                list.add("Unb" + unbreaking);
            }
            if (mending > 0) {
                list.add("Men ");
            }
        }
        if (item instanceof ItemTool) {
            if (unbreaking > 0) {
                list.add("Unb" + unbreaking);
            }
            if (mending > 0) {
                list.add("Men ");
            }
            if (vanishing_curse > 0) {
                list.add("Vanish ");
            }
            if (binding_curse > 0) {
                list.add("Binding ");
            }
            if (efficiency > 0) {
                list.add("Eff" + efficiency);
            }
            if (silktouch > 0) {
                list.add("Sil" + silktouch);
            }
            if (fortune > 0) {
                list.add("For" + fortune);
            }
        }
    }
}
