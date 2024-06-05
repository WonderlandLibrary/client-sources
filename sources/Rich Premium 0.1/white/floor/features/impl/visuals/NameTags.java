package white.floor.features.impl.visuals;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import clickgui.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.Display;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.Event2D;
import white.floor.event.event.Event3D;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.combat.RotationHelper;
import white.floor.helpers.friend.FriendManager;

public class NameTags extends Feature {
    public static Map<EntityLivingBase, double[]> entityPositions = new HashMap<EntityLivingBase, double[]>();

    public NameTags() {
        super("NameTags", "123fsd", 0, Category.VISUALS);
        Main.settingsManager.rSetting(new Setting("Background Alpha", this, 50, 0, 255, true));

    }

    @EventTarget
    public void on3D(Event3D event) {
        try {
            this.updatePositions();
        } catch (Exception exception) {
            // empty catch block
        }
    }


    @EventTarget
    public void onRender2D(Event2D event) {
        ScaledResolution sr = new ScaledResolution(mc);
        GlStateManager.pushMatrix();

        for (Entity entity : entityPositions.keySet()) {
            if ((entity == Minecraft.player))
                continue;
            GlStateManager.pushMatrix();
            if (entity instanceof EntityPlayer) {
                double[] array = entityPositions.get(entity);
                if (array[3] < 0.0 || array[3] >= 1.0) {
                    GlStateManager.popMatrix();
                    continue;
                }
                GlStateManager.translate(array[0] / (double) sr.getScaleFactor(),
                        array[1] / (double) sr.getScaleFactor(), 0.0);
                this.scale();
                GlStateManager.translate(0.0, -1.5, 0.0);
                String prefix = "";
                if (FriendManager.isFriend(entity.getName())) {
                    prefix = TextFormatting.GRAY + "[" + TextFormatting.GREEN + "F" + TextFormatting.GRAY + "]";
                }
                String name = entity.getName();
                String string = Math.round(((EntityLivingBase) entity).getHealth() * 20.0D) / 20.0D + "";
                float n2 = mc.fontRendererObj.getStringWidth(string);

                DrawHelper.drawNewRect(-n2 - 24, -23.0, mc.fontRendererObj.getStringWidth(prefix + name + "[" + string + "]") - 37, -13.0, DrawHelper.getColor(20, Main.settingsManager.getSettingByName(Main.featureDirector.getModule(NameTags.class), "Background Alpha").getValInt()));
                mc.fontRendererObj.drawStringWithShadow(prefix + " " + ChatFormatting.GRAY + name + ChatFormatting.GRAY + " [" + ChatFormatting.GREEN + string + ChatFormatting.GRAY + "]", -46, (int) -22.0f, new Color(10, 239, 63).getRGB());

                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                    ArrayList<ItemStack> list = new ArrayList<ItemStack>();
                    for (int i = 0; i < 5; ++i) {
                        ItemStack getEquipmentInSlot = ((EntityPlayer) entity).getEquipmentInSlot(i);
                        if (getEquipmentInSlot == null)
                            continue;
                        GlStateManager.pushMatrix();
                        list.add(getEquipmentInSlot);
                    }
                    int n10 = -(list.size() * 9);
                    for (ItemStack itemStack : list) {
                        RenderHelper.enableGUIStandardItemLighting();
                        ItemStack stack1 = ((EntityPlayer) entityLivingBase).getEquipmentInSlot(4);
                        ItemStack stack2 = ((EntityPlayer) entityLivingBase).getEquipmentInSlot(3);
                        ItemStack stack3 = ((EntityPlayer) entityLivingBase).getEquipmentInSlot(2);
                        ItemStack stack4 = ((EntityPlayer) entityLivingBase).getEquipmentInSlot(1);
                        DrawHelper.renderItem(stack1, 18, -40);
                        DrawHelper.renderItem(stack2, 3, -40);
                        DrawHelper.renderItem(stack3, -12, -40);
                        DrawHelper.renderItem(stack4, -27, -40);
                        DrawHelper.renderItem(((EntityPlayer) entity).getHeldItemMainhand(), -44, -40);
                        DrawHelper.renderItem(((EntityPlayer) entity).getHeldItemOffhand(), 31, -40);

                        GlStateManager.popMatrix();

                        n10 += 3;
                        RenderHelper.disableStandardItemLighting();
                        if (itemStack == null)
                            continue;
                        int n11 = 21;
                        int getEnchantmentLevel = EnchantmentHelper
                                .getEnchantmentLevel(Enchantment.getEnchantmentByID(16), itemStack);
                        int getEnchantmentLevel2 = EnchantmentHelper
                                .getEnchantmentLevel(Enchantment.getEnchantmentByID(20), itemStack);
                        int getEnchantmentLevel3 = EnchantmentHelper
                                .getEnchantmentLevel(Enchantment.getEnchantmentByID(19), itemStack);
                        if (getEnchantmentLevel > 0) {
                            this.drawEnchantTag("Sh" + this.getColor(getEnchantmentLevel) + getEnchantmentLevel,
                                    n10 - 11, n11 + 4);
                            n11 += 6;
                            GlStateManager.pushMatrix();
                            GlStateManager.scale(0.9, 0.9, 1.1);
                            GlStateManager.popMatrix();
                        }
                        if (getEnchantmentLevel2 > 0) {
                            this.drawEnchantTag("Fir" + this.getColor(getEnchantmentLevel2) + getEnchantmentLevel2,
                                    n10 - 11, n11 + 5);
                            GlStateManager.pushMatrix();
                            n11 += 6;
                            GlStateManager.scale(0.9, 0.9, 1.1);
                            GlStateManager.popMatrix();
                        }
                        if (getEnchantmentLevel3 > 0) {
                            this.drawEnchantTag("Kb" + this.getColor(getEnchantmentLevel3) + getEnchantmentLevel3,
                                    n10 - 11, n11 + 6);
                        } else if (itemStack.getItem() instanceof ItemArmor) {
                            int getEnchantmentLevel4 = EnchantmentHelper
                                    .getEnchantmentLevel(Enchantment.getEnchantmentByID(0), itemStack);
                            int getEnchantmentLevel5 = EnchantmentHelper
                                    .getEnchantmentLevel(Enchantment.getEnchantmentByID(7), itemStack);
                            int getEnchantmentLevel6 = EnchantmentHelper
                                    .getEnchantmentLevel(Enchantment.getEnchantmentByID(34), itemStack);
                            if (getEnchantmentLevel4 > 0) {
                                GlStateManager.pushMatrix();
                                this.drawEnchantTag("P" + this.getColor(getEnchantmentLevel4) + getEnchantmentLevel4,
                                        n10 - 6, n11 + 4);
                                n11 += 6;
                                GlStateManager.scale(0.9, 0.9, 1.1);
                                GlStateManager.popMatrix();
                            }
                            if (getEnchantmentLevel5 > 0) {
                                this.drawEnchantTag("Th" + this.getColor(getEnchantmentLevel5) + getEnchantmentLevel5,
                                        n10, n11);
                                n11 += 6;
                            }
                            if (getEnchantmentLevel6 > 0) {
                                GlStateManager.pushMatrix();
                                GlStateManager.scale(0.9, 0.9, 1.1);
                                this.drawEnchantTag("U" + this.getColor(getEnchantmentLevel6) + getEnchantmentLevel6,
                                        n10 - 8, n11 + 11);
                                GlStateManager.popMatrix();
                            }
                        } else if (itemStack.getItem() instanceof ItemBow) {
                            int getEnchantmentLevel7 = EnchantmentHelper
                                    .getEnchantmentLevel(Enchantment.getEnchantmentByID(48), itemStack);
                            int getEnchantmentLevel8 = EnchantmentHelper
                                    .getEnchantmentLevel(Enchantment.getEnchantmentByID(49), itemStack);
                            int getEnchantmentLevel9 = EnchantmentHelper
                                    .getEnchantmentLevel(Enchantment.getEnchantmentByID(50), itemStack);
                            if (getEnchantmentLevel7 > 0) {
                                this.drawEnchantTag("Pw" + this.getColor(getEnchantmentLevel7) + getEnchantmentLevel7,
                                        n10, n11);
                                n11 += 6;
                            }
                            if (getEnchantmentLevel8 > 0) {
                                this.drawEnchantTag("Pn" + this.getColor(getEnchantmentLevel8) + getEnchantmentLevel8,
                                        n10, n11);
                                n11 += 6;
                            }
                            if (getEnchantmentLevel9 > 0) {
                                this.drawEnchantTag("Fa" + this.getColor(getEnchantmentLevel9) + getEnchantmentLevel9,
                                        n10, n11);
                            }
                        } else if (itemStack.getRarity() == EnumRarity.EPIC) {
                            this.drawEnchantTag("\u0412\u00a76\u0412\u00a7lGod", n10 - 2, n11);
                        }
                        int n12 = (int) Math.round(
                                255.0 - (double) itemStack.getItemDamage() * 255.0 / (double) itemStack.getMaxDamage());
                        new Color(255 - n12 << 16 | n12 << 8).brighter();
                        float n13 = (float) ((double) n10 * 1.05) - 2.0f;
                        if (itemStack.getMaxDamage() - itemStack.getItemDamage() > 0) {
                            GlStateManager.pushMatrix();
                            GlStateManager.disableDepth();
                            GlStateManager.enableDepth();
                            GlStateManager.popMatrix();
                        }
                        n10 += 12;
                    }
                }
            GlStateManager.popMatrix();
        }
        GL11.glPopMatrix();
        GlStateManager.enableBlend();
    }

    private void drawEnchantTag(String text, int n, int n2) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();

        Fonts.neverlose500_16.drawStringWithShadow(text, (n *= 1) + 9, -30 - (n2 -= 6), -1);

        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    private String getColor(int n) {
        if (n != 1) {
            if (n == 2) {
                return "";
            }
            if (n == 3) {
                return "";
            }
            if (n == 4) {
                return "";
            }
            if (n >= 5) {
                return "";
            }
        }
        return "";
    }

    private void updatePositions() {
        entityPositions.clear();
        float pTicks = mc.timer.renderPartialTicks;
        for (Object o : mc.world.loadedEntityList) {
            Entity ent = (Entity) o;
            if (ent == Minecraft.player || !(ent instanceof EntityPlayer))
                continue;
            double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double) pTicks
                    - mc.getRenderManager().viewerPosX;
            double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double) pTicks
                    - mc.getRenderManager().viewerPosY;
            double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double) pTicks
                    - mc.getRenderManager().viewerPosZ;
            if (!(this.convertTo2D(x, y += (double) ent.height + 0.2, z)[2] >= 0.0)
                    || !(this.convertTo2D(x, y, z)[2] < 1.0))
                continue;
            entityPositions.put((EntityPlayer) ent,
                    new double[]{this.convertTo2D(x, y, z)[0], this.convertTo2D(x, y, z)[1],
                            Math.abs(this.mods2d(x, y + 1.0, z, ent)[1] - this.mods2d(x, y, z, ent)[1]),
                            this.convertTo2D(x, y, z)[2]});
        }
    }

    private double[] mods2d(double x, double y, double z, Entity ent) {
        float pTicks = mc.timer.renderPartialTicks;
        float prevYaw = Minecraft.player.rotationYaw;
        float prevPrevYaw = Minecraft.player.prevRotationYaw;
        float[] rotations = RotationHelper.getRotationFromPosition(
                ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double) pTicks,
                ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double) pTicks,
                ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double) pTicks - 1.6);
        mc.getRenderViewEntity().rotationYaw = mc.getRenderViewEntity().prevRotationYaw = rotations[0];
        mc.entityRenderer.setupCameraTransform(pTicks, 0);
        double[] convertedPoints = this.convertTo2D(x, y, z);
        mc.getRenderViewEntity().rotationYaw = prevYaw;
        mc.getRenderViewEntity().prevRotationYaw = prevPrevYaw;
        mc.entityRenderer.setupCameraTransform(pTicks, 0);
        return convertedPoints;
    }

    private double[] convertTo2D(double x, double y, double z) {
        FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport, screenCoords);
        if (result) {
            return new double[]{screenCoords.get(0), (float) Display.getHeight() - screenCoords.get(1),
                    screenCoords.get(2)};
        }
        return null;
    }

    private void scale() {
        float scale = 1.0f * (mc.gameSettings.smoothCamera ? 2.0f : 1.0f);
        GlStateManager.scale(scale, scale, scale);
    }
}