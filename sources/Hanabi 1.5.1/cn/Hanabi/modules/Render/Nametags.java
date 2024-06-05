package cn.Hanabi.modules.Render;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.events.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import cn.Hanabi.*;
import cn.Hanabi.modules.Combat.*;
import cn.Hanabi.modules.Player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import java.awt.*;
import cn.Hanabi.utils.fontmanager.*;
import ClassSub.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.*;
import org.lwjgl.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.opengl.*;
import java.nio.*;
import java.util.*;

public class Nametags extends Mod
{
    public static Map<EntityLivingBase, double[]> entityPositions;
    public Value<Boolean> invis;
    public Value<Boolean> armor;
    
    
    public Nametags() {
        super("Nametags", Category.RENDER);
        this.invis = new Value<Boolean>("Nametags_Invisible", false);
        this.armor = new Value<Boolean>("Nametags_Armor", false);
    }
    
    @EventTarget
    public void update(final EventRender eventRender) {
        try {
            this.updatePositions();
        }
        catch (Exception ex) {}
    }
    
    @EventTarget
    public void onRender2D(final EventRender2D eventRender2D) {
        GlStateManager.pushMatrix();
        final ScaledResolution scaledResolution = new ScaledResolution(Nametags.mc);
        for (final Entity entity : Nametags.entityPositions.keySet()) {
            if (entity != Nametags.mc.thePlayer && (this.invis.getValueState() || !entity.isInvisible())) {
                GlStateManager.pushMatrix();
                if (entity instanceof EntityPlayer) {
                    final double[] array = Nametags.entityPositions.get(entity);
                    if (array[3] < 0.0 || array[3] >= 1.0) {
                        GlStateManager.popMatrix();
                        continue;
                    }
                    final UnicodeFontRenderer wqy18 = Hanabi.INSTANCE.fontManager.wqy18;
                    GlStateManager.translate(array[0] / scaledResolution.getScaleFactor(), array[1] / scaledResolution.getScaleFactor(), 0.0);
                    this.scale();
                    GlStateManager.translate(0.0, -2.5, 0.0);
                    final String string = "Health: " + String.valueOf(Math.round(((EntityLivingBase)entity).getHealth() * 10.0f) / 10);
                    final String string2 = (AntiBot.isBot(entity) ? "§9[BOT]" : "") + (Teams.isOnSameTeam(entity) ? "§b[TEAM]" : "") + "§r" + entity.getDisplayName().getUnformattedText();
                    String string3 = "";
                    for (final String s : Class203.ignMap.keySet()) {
                        if (entity.getName().equalsIgnoreCase(s)) {
                            string3 = "§e[" + Class203.ignMap.get(s) + "]";
                        }
                    }
                    final String string4 = string2 + string3;
                    final float n = wqy18.getStringWidth(string4.replaceAll("§.", ""));
                    final float n2 = Hanabi.INSTANCE.fontManager.comfortaa12.getStringWidth(string);
                    final float n3 = ((n > n2) ? n : n2) + 8.0f;
                    Class246.drawRect(-n3 / 2.0f, -25.0f, n3 / 2.0f, 0.0f, Class15.getColor(0, 130));
                    final int n4 = (int)(array[0] + -n3 / 2.0f - 3.0) / 2 - 26;
                    final int n5 = (int)(array[0] + n3 / 2.0f + 3.0) / 2 + 20;
                    final int n6 = (int)(array[1] - 30.0) / 2;
                    final int n7 = (int)(array[1] + 11.0) / 2;
                    final int n8 = scaledResolution.getScaledHeight() / 2;
                    final int n9 = scaledResolution.getScaledWidth() / 2;
                    wqy18.drawStringWithColor(string4, -n3 / 2.0f + 4.0f, -22.0f, Class15.WHITE.c);
                    Hanabi.INSTANCE.fontManager.comfortaa12.drawString(string, -n3 / 2.0f + 4.0f, -10.0f, Class15.WHITE.c);
                    final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                    Class246.drawRect(-n3 / 2.0f, -1.0f, n3 / 2.0f - n3 / 2.0f * (1.0f - (float)Math.ceil(entityLivingBase.getHealth() + entityLivingBase.getAbsorptionAmount()) / (entityLivingBase.getMaxHealth() + entityLivingBase.getAbsorptionAmount())) * 2.0f, 0.0f, Class15.RED.c);
                    if (this.armor.getValueState()) {
                        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
                        for (int i = 0; i < 5; ++i) {
                            final ItemStack getEquipmentInSlot = ((EntityPlayer)entity).getEquipmentInSlot(i);
                            if (getEquipmentInSlot != null) {
                                list.add(getEquipmentInSlot);
                            }
                        }
                        int n10 = -(list.size() * 9);
                        for (final ItemStack itemStack : list) {
                            RenderHelper.enableGUIStandardItemLighting();
                            Nametags.mc.getRenderItem().renderItemIntoGUI(itemStack, n10 + 6, -42);
                            Nametags.mc.getRenderItem().renderItemOverlays(Nametags.mc.fontRendererObj, itemStack, n10, -42);
                            n10 += 3;
                            RenderHelper.disableStandardItemLighting();
                            if (itemStack != null) {
                                int n11 = 21;
                                final int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
                                final int getEnchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack);
                                final int getEnchantmentLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemStack);
                                if (getEnchantmentLevel > 0) {
                                    this.drawEnchantTag("Sh" + this.getColor(getEnchantmentLevel) + getEnchantmentLevel, n10, n11);
                                    n11 += 6;
                                }
                                if (getEnchantmentLevel2 > 0) {
                                    this.drawEnchantTag("Fir" + this.getColor(getEnchantmentLevel2) + getEnchantmentLevel2, n10, n11);
                                    n11 += 6;
                                }
                                if (getEnchantmentLevel3 > 0) {
                                    this.drawEnchantTag("Kb" + this.getColor(getEnchantmentLevel3) + getEnchantmentLevel3, n10, n11);
                                }
                                else if (itemStack.getItem() instanceof ItemArmor) {
                                    final int getEnchantmentLevel4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack);
                                    final int getEnchantmentLevel5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, itemStack);
                                    final int getEnchantmentLevel6 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack);
                                    if (getEnchantmentLevel4 > 0) {
                                        this.drawEnchantTag("P" + this.getColor(getEnchantmentLevel4) + getEnchantmentLevel4, n10, n11);
                                        n11 += 6;
                                    }
                                    if (getEnchantmentLevel5 > 0) {
                                        this.drawEnchantTag("Th" + this.getColor(getEnchantmentLevel5) + getEnchantmentLevel5, n10, n11);
                                        n11 += 6;
                                    }
                                    if (getEnchantmentLevel6 > 0) {
                                        this.drawEnchantTag("Unb" + this.getColor(getEnchantmentLevel6) + getEnchantmentLevel6, n10, n11);
                                    }
                                }
                                else if (itemStack.getItem() instanceof ItemBow) {
                                    final int getEnchantmentLevel7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
                                    final int getEnchantmentLevel8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
                                    final int getEnchantmentLevel9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack);
                                    if (getEnchantmentLevel7 > 0) {
                                        this.drawEnchantTag("Pow" + this.getColor(getEnchantmentLevel7) + getEnchantmentLevel7, n10, n11);
                                        n11 += 6;
                                    }
                                    if (getEnchantmentLevel8 > 0) {
                                        this.drawEnchantTag("Pun" + this.getColor(getEnchantmentLevel8) + getEnchantmentLevel8, n10, n11);
                                        n11 += 6;
                                    }
                                    if (getEnchantmentLevel9 > 0) {
                                        this.drawEnchantTag("Fir" + this.getColor(getEnchantmentLevel9) + getEnchantmentLevel9, n10, n11);
                                    }
                                }
                                else if (itemStack.getRarity() == EnumRarity.EPIC) {
                                    this.drawEnchantTag("§6§lGod", n10 - 2, n11);
                                }
                                final int n12 = (int)Math.round(255.0 - itemStack.getItemDamage() * 255.0 / itemStack.getMaxDamage());
                                new Color(255 - n12 << 16 | n12 << 8).brighter();
                                final float n13 = (float)(n10 * 1.05) - 2.0f;
                                if (itemStack.getMaxDamage() - itemStack.getItemDamage() > 0) {
                                    GlStateManager.pushMatrix();
                                    GlStateManager.disableDepth();
                                    GlStateManager.enableDepth();
                                    GlStateManager.popMatrix();
                                }
                                n10 += 12;
                            }
                        }
                    }
                }
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.popMatrix();
    }
    
    private void drawEnchantTag(final String text, int n, int n2) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        n *= (int)1.05;
        n2 -= 6;
        Hanabi.INSTANCE.fontManager.comfortaa10.drawStringWithColor(text, n + 9, -30 - n2, Class15.getColor(255));
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }
    
    private String getColor(final int n) {
        if (n != 1) {
            if (n == 2) {
                return "§a";
            }
            if (n == 3) {
                return "§3";
            }
            if (n == 4) {
                return "§4";
            }
            if (n >= 5) {
                return "§6";
            }
        }
        return "§f";
    }
    
    private void scale() {
        final float n = 1.0f * (Nametags.mc.gameSettings.smoothCamera ? 2.0f : 1.0f);
        GlStateManager.scale(n, n, n);
    }
    
    private void updatePositions() {
        Nametags.entityPositions.clear();
        final float renderPartialTicks = Class211.getTimer().renderPartialTicks;
        for (final Entity entity : Nametags.mc.theWorld.loadedEntityList) {
            if (entity != Nametags.mc.thePlayer && entity instanceof EntityPlayer && (!entity.isInvisible() || !this.invis.getValueState())) {
                final double n = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * renderPartialTicks - Nametags.mc.getRenderManager().viewerPosX;
                final double n2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * renderPartialTicks - Nametags.mc.getRenderManager().viewerPosY;
                final double n3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * renderPartialTicks - Nametags.mc.getRenderManager().viewerPosZ;
                final double n4 = n2 + (entity.height + 0.2);
                if (this.convertTo2D(n, n4, n3)[2] < 0.0 || this.convertTo2D(n, n4, n3)[2] >= 1.0) {
                    continue;
                }
                Nametags.entityPositions.put((EntityLivingBase)entity, new double[] { this.convertTo2D(n, n4, n3)[0], this.convertTo2D(n, n4, n3)[1], Math.abs(this.convertTo2D(n, n4 + 1.0, n3, entity)[1] - this.convertTo2D(n, n4, n3, entity)[1]), this.convertTo2D(n, n4, n3)[2] });
            }
        }
    }
    
    private double[] convertTo2D(final double n, final double n2, final double n3, final Entity entity) {
        final float renderPartialTicks = Class211.getTimer().renderPartialTicks;
        final float rotationYaw = Nametags.mc.thePlayer.rotationYaw;
        final float prevRotationYaw = Nametags.mc.thePlayer.prevRotationYaw;
        final float[] array = Class45.getRotationFromPosition(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * renderPartialTicks, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * renderPartialTicks, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * renderPartialTicks - 1.6);
        final Entity getRenderViewEntity = Nametags.mc.getRenderViewEntity();
        final Entity getRenderViewEntity2 = Nametags.mc.getRenderViewEntity();
        final float n4 = array[0];
        getRenderViewEntity2.prevRotationYaw = n4;
        getRenderViewEntity.rotationYaw = n4;
        final Minecraft mc = Nametags.mc;
        ((IEntityRenderer)Minecraft.getMinecraft().entityRenderer).runSetupCameraTransform(renderPartialTicks, 0);
        final double[] array2 = this.convertTo2D(n, n2, n3);
        Nametags.mc.getRenderViewEntity().rotationYaw = rotationYaw;
        Nametags.mc.getRenderViewEntity().prevRotationYaw = prevRotationYaw;
        final Minecraft mc2 = Nametags.mc;
        ((IEntityRenderer)Minecraft.getMinecraft().entityRenderer).runSetupCameraTransform(renderPartialTicks, 0);
        return array2;
    }
    
    private double[] convertTo2D(final double n, final double n2, final double n3) {
        final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(3);
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
        final FloatBuffer floatBuffer2 = BufferUtils.createFloatBuffer(16);
        final FloatBuffer floatBuffer3 = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(2982, floatBuffer2);
        GL11.glGetFloat(2983, floatBuffer3);
        GL11.glGetInteger(2978, intBuffer);
        if (GLU.gluProject((float)n, (float)n2, (float)n3, floatBuffer2, floatBuffer3, intBuffer, floatBuffer)) {
            return new double[] { floatBuffer.get(0), Display.getHeight() - floatBuffer.get(1), floatBuffer.get(2) };
        }
        return null;
    }
    
    static {
        Nametags.entityPositions = new HashMap<EntityLivingBase, double[]>();
    }
}
