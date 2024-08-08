package me.xatzdevelopments.xatz.client.modules;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.Colors;
import me.xatzdevelopments.xatz.client.RotationUtils;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.modules.advancednametags.RenderUtils;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.gui.custom.clickgui.CheckBtnSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class AdvancedNameTags extends Module {
	
	   public static Map entityPositions = new HashMap();
	   private boolean hideInvisibles;
	   private double gradualFOVModifier;
	   private Character formatChar = new Character('§');
	
	@Override
	public ModSetting[] getModSettings() {
		CheckBtnSetting box1 = new CheckBtnSetting("Armor", "narmor");
		CheckBtnSetting box2 = new CheckBtnSetting("Health", "nhealth");
		CheckBtnSetting box3 = new CheckBtnSetting("Invisibles", "ninvisibles");
		return new ModSetting[] { box1, box2, box3 };
	}

	public AdvancedNameTags() {
		super("AdNameTags", Keyboard.KEY_NONE, Category.RENDER, "Makes Nametags better");
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public void onUpdate() {

		super.onUpdate();
	}
	
	@Override
	public void onRender() {
    GlStateManager.pushMatrix();
    ScaledResolution scaledRes = new ScaledResolution(this.mc);
    Iterator var4 = entityPositions.keySet().iterator();

    while(true) {
       while(true) {
          Entity ent;
          do {
             if (!var4.hasNext()) {
                GlStateManager.popMatrix();
                return;
             }

             ent = (Entity)var4.next();
          } while((ent == this.mc.thePlayer || !ClientSettings.ninvisibles) && ent.isInvisible());

          GlStateManager.pushMatrix();
          if (ent instanceof EntityPlayer) {
             String str = ent.getDisplayName().getFormattedText();
             String name = ent.getName();
             double[] renderPositions = (double[])entityPositions.get(ent);
             if (renderPositions[3] < 0.0D || renderPositions[3] >= 1.0D) {
                GlStateManager.popMatrix();
                continue;
             }

             FontRenderer font = this.mc.fontRendererObj;
             GlStateManager.translate(renderPositions[0] / (double)scaledRes.getScaleFactor(), renderPositions[1] / (double)scaledRes.getScaleFactor(), 0.0D);
             this.scale();
             String healthInfo = (int)((EntityLivingBase)ent).getHealth() + "";
             GlStateManager.translate(0.0D, -2.5D, 0.0D);
             float strWidth = (float)font.getStringWidth(str);
             float strWidth2 = (float)font.getStringWidth(healthInfo);
             RenderUtils.rectangle((double)(-strWidth / 2.0F - 1.0F), -10.0D, (double)(strWidth / 2.0F + 1.0F), 0.0D, Colors.getColor(0, 130));
             int x3 = (int)(renderPositions[0] + (double)(-strWidth / 2.0F) - 3.0D) / 2 - 26;
             int x4 = (int)(renderPositions[0] + (double)(strWidth / 2.0F) + 3.0D) / 2 + 20;
             int y1 = (int)(renderPositions[1] + -30.0D) / 2;
             int y2 = (int)(renderPositions[1] + 11.0D) / 2;
             int mouseY = scaledRes.getScaledHeight() / 2;
             int mouseX = scaledRes.getScaledWidth() / 2;
             font.drawStringWithShadow(str, -strWidth / 2.0F, -7.0F, Colors.getColor(255, 50, 50));
             boolean healthOption = !ClientSettings.nhealth;
             boolean armor = !ClientSettings.narmor;
             boolean hovered = x3 < mouseX && mouseX < x4 && y1 < mouseY && mouseY < y2;
             if (!healthOption || hovered) {
                float health = ((EntityPlayer)ent).getHealth();
                float[] fractions = new float[]{0.0F, 0.5F, 1.0F};
                Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
                float progress = health * 5.0F * 0.01F;
                Color customColor = this.blendColors(fractions, colors, progress).brighter();

                try {
                   RenderUtils.rectangle((double)(strWidth / 2.0F + 2.0F), -10.0D, (double)(strWidth / 2.0F + 1.0F + strWidth2), 0.0D, Colors.getColor(0, 130));
                   font.drawStringWithShadow(healthInfo, strWidth / 2.0F + 2.0F, -7.0F, customColor.getRGB());
                } catch (Exception var41) {
                }
             }

             if (hovered || !armor) {
                List itemsToRender = new ArrayList();

                int x;
                for(x = 0; x < 5; ++x) {
                   ItemStack stack = ((EntityPlayer)ent).getEquipmentInSlot(x);
                   if (stack != null) {
                      itemsToRender.add(stack);
                   }
                }

                x = -(itemsToRender.size() * 9);
                Iterator var45 = itemsToRender.iterator();

                label136:
                while(true) {
                   ItemStack stack;
                   do {
                      if (!var45.hasNext()) {
                         break label136;
                      }

                      stack = (ItemStack)var45.next();
                      RenderHelper.enableGUIStandardItemLighting();
                      this.mc.getRenderItem().renderItemIntoGUI(stack, x, -27);
                      this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, stack, x, -27);
                      x += 3;
                      RenderHelper.disableStandardItemLighting();
                   } while(stack == null);

                   int y = 21;
                   int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
                   int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
                   int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
                   if (sLevel > 0) {
                      drawEnchantTag("Sh" + this.getColor(sLevel) + sLevel, x, y);
                      y -= 9;
                   }

                   if (fLevel > 0) {
                      drawEnchantTag("Fir" + this.getColor(fLevel) + fLevel, x, y);
                      y -= 9;
                   }

                   int powLevel;
                   int punLevel;
                   if (kLevel > 0) {
                      drawEnchantTag("Kb" + this.getColor(kLevel) + kLevel, x, y);
                   } else {
                      int fireLevel;
                      if (stack.getItem() instanceof ItemArmor) {
                         powLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
                         punLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
                         fireLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
                         if (powLevel > 0) {
                            drawEnchantTag("P" + this.getColor(powLevel) + powLevel, x, y);
                            y -= 9;
                         }

                         if (punLevel > 0) {
                            drawEnchantTag("Th" + this.getColor(punLevel) + punLevel, x, y);
                            y -= 9;
                         }

                         if (fireLevel > 0) {
                            drawEnchantTag("Unb" + this.getColor(fireLevel) + fireLevel, x, y);
                         }
                      } else if (stack.getItem() instanceof ItemBow) {
                         powLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
                         punLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
                         fireLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
                         if (powLevel > 0) {
                            drawEnchantTag("Pow" + this.getColor(powLevel) + powLevel, x, y);
                            y -= 9;
                         }

                         if (punLevel > 0) {
                            drawEnchantTag("Pun" + this.getColor(punLevel) + punLevel, x, y);
                            y -= 9;
                         }

                         if (fireLevel > 0) {
                            drawEnchantTag("Fir" + this.getColor(fireLevel) + fireLevel, x, y);
                         }
                      } else if (stack.getRarity() == EnumRarity.EPIC) {
                         drawEnchantTag("Â§6Â§lGod", x, y);
                      }
                   }

                   powLevel = (int)Math.round(255.0D - (double)stack.getItemDamage() * 255.0D / (double)stack.getMaxDamage());
                   punLevel = 255 - powLevel << 16 | powLevel << 8;
                   Color customColor = (new Color(punLevel)).brighter();
                   float x2 = (float)((double)x * 1.75D);
                   if (stack.getMaxDamage() - stack.getItemDamage() > 0) {
                      GlStateManager.pushMatrix();
                      GlStateManager.disableDepth();
                      GL11.glScalef(0.57F, 0.57F, 0.57F);
                      font.drawStringWithShadow("" + (stack.getMaxDamage() - stack.getItemDamage()), x2, -54.0F, customColor.getRGB());
                      GlStateManager.enableDepth();
                      GlStateManager.popMatrix();
                   }

                   y = -53;

                   for(Iterator var34 = ((EntityPlayer)ent).getActivePotionEffects().iterator(); var34.hasNext(); y -= 8) {
                      Object o = var34.next();
                      PotionEffect pot = (PotionEffect)o;
                      String potName = StringUtils.capitalize(pot.getEffectName().substring(pot.getEffectName().lastIndexOf(".") + 1));
                      int XD = pot.getDuration() / 20;
                      SimpleDateFormat df = new SimpleDateFormat("m:ss");
                      String time = df.format(XD * 1000);
                      font.drawStringWithShadow(XD > 0 ? potName + " " + time : "", -30.0F, (float)y, -1);
                   }

                   x += 12;
                }
             }
          }

          GlStateManager.popMatrix();
       }
    }
 }

/* @Override
 public void renderNameTag(NameTagEvent event) {
    event.setCancelled(true); 
 } */

 private String getColor(int level) {
    if (level != 1) {
       if (level == 2) {
          return "Â§a";
       }

       if (level == 3) {
          return "Â§3";
       }

       if (level == 4) {
          return "Â§4";
       }

       if (level >= 5) {
          return "Â§6";
       }
    }

    return "Â§f";
 }

 private static void drawEnchantTag(String text, int x, int y) {
    GlStateManager.pushMatrix();
    GlStateManager.disableDepth();
    x = (int)((double)x * 1.75D);
    y -= 4;
    GL11.glScalef(0.57F, 0.57F, 0.57F);
    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, (float)x, (float)(-30 - y), Colors.getColor(255));
    GlStateManager.enableDepth();
    GlStateManager.popMatrix();
 }

 private void scale() {
    float scale = 1.0F;
    GlStateManager.scale(scale, scale, scale);
 }

 private void updatePositions() {
    entityPositions.clear();
    float pTicks = this.mc.timer.renderPartialTicks;
    Iterator var2 = this.mc.theWorld.loadedEntityList.iterator();

    while(true) {
       Entity ent;
       do {
          do {
             do {
                if (!var2.hasNext()) {
                   return;
                }

                Object o = var2.next();
                ent = (Entity)o;
             } while(ent == this.mc.thePlayer);
          } while(!(ent instanceof EntityPlayer));
       } while(ent.isInvisible() && this.hideInvisibles);

       double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - this.mc.getRenderManager().viewerPosX;
       double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)pTicks - this.mc.getRenderManager().viewerPosY;
       double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - this.mc.getRenderManager().viewerPosZ;
       y += (double)ent.height + 0.2D;
       if (this.convertTo2D(x, y, z)[2] >= 0.0D && this.convertTo2D(x, y, z)[2] < 1.0D) {
          entityPositions.put((EntityPlayer)ent, new double[]{this.convertTo2D(x, y, z)[0], this.convertTo2D(x, y, z)[1], Math.abs(this.convertTo2D(x, y + 1.0D, z, ent)[1] - this.convertTo2D(x, y, z, ent)[1]), this.convertTo2D(x, y, z)[2]});
       }
    }
 }

 private double[] convertTo2D(double x, double y, double z, Entity ent) {
    float pTicks = this.mc.timer.renderPartialTicks;
    float prevYaw = this.mc.thePlayer.rotationYaw;
    float prevPrevYaw = this.mc.thePlayer.prevRotationYaw;
    float[] rotations = RotationUtils.getRotationFromPosition(ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks, ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks, ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)pTicks - 1.6D);
    this.mc.getRenderViewEntity().rotationYaw = this.mc.getRenderViewEntity().prevRotationYaw = rotations[0];
    this.mc.entityRenderer.setupCameraTransform(pTicks, 0);
    double[] convertedPoints = this.convertTo2D(x, y, z);
    this.mc.getRenderViewEntity().rotationYaw = prevYaw;
    this.mc.getRenderViewEntity().prevRotationYaw = prevPrevYaw;
    this.mc.entityRenderer.setupCameraTransform(pTicks, 0);
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
    boolean result = GLU.gluProject((float)x, (float)y, (float)z, modelView, projection, viewport, screenCoords);
    return result ? new double[]{(double)screenCoords.get(0), (double)((float)Display.getHeight() - screenCoords.get(1)), (double)screenCoords.get(2)} : null;
 }

 public Color blendColors(float[] fractions, Color[] colors, float progress) {
    Color color = null;
    if (fractions != null) {
       if (colors != null) {
          if (fractions.length == colors.length) {
             int[] indicies = this.getFractionIndicies(fractions, progress);
             if (indicies[0] >= 0 && indicies[0] < fractions.length && indicies[1] >= 0 && indicies[1] < fractions.length) {
                float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
                Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
                float max = range[1] - range[0];
                float value = progress - range[0];
                float weight = value / max;
                color = this.blend(colorRange[0], colorRange[1], (double)(1.0F - weight));
                return color;
             } else {
                return colors[0];
             }
          } else {
             throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
          }
       } else {
          throw new IllegalArgumentException("Colours can't be null");
       }
    } else {
       throw new IllegalArgumentException("Fractions can't be null");
    }
 }

 public int[] getFractionIndicies(float[] fractions, float progress) {
    int[] range = new int[2];

    int startPoint;
    for(startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
    }

    if (startPoint >= fractions.length) {
       startPoint = fractions.length - 1;
    }

    range[0] = startPoint - 1;
    range[1] = startPoint;
    return range;
 }

 public Color blend(Color color1, Color color2, double ratio) {
    float r = (float)ratio;
    float ir = 1.0F - r;
    float[] rgb1 = new float[3];
    float[] rgb2 = new float[3];
    color1.getColorComponents(rgb1);
    color2.getColorComponents(rgb2);
    float red = rgb1[0] * r + rgb2[0] * ir;
    float green = rgb1[1] * r + rgb2[1] * ir;
    float blue = rgb1[2] * r + rgb2[2] * ir;
    if (red < 0.0F) {
       red = 0.0F;
    } else if (red > 255.0F) {
       red = 255.0F;
    }

    if (green < 0.0F) {
       green = 0.0F;
    } else if (green > 255.0F) {
       green = 255.0F;
    }

    if (blue < 0.0F) {
       blue = 0.0F;
    } else if (blue > 255.0F) {
       blue = 255.0F;
    }

    Color color = null;

    try {
       color = new Color(red, green, blue);
    } catch (IllegalArgumentException var15) {
       NumberFormat nf = NumberFormat.getNumberInstance();
       System.out.println(nf.format((double)red) + "; " + nf.format((double)green) + "; " + nf.format((double)blue));
       var15.printStackTrace();
    }

    return color;
 }
}
