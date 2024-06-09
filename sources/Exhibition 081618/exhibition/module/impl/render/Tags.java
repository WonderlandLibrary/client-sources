package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventNametagRender;
import exhibition.event.impl.EventRender3D;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.RenderingUtil;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import org.lwjgl.opengl.GL11;

public class Tags extends Module {
   public static String ARMOR = "ARMOR";

   public Tags(ModuleData data) {
      super(data);
      this.settings.put(ARMOR, new Setting(ARMOR, true, "Show armor."));
   }

   @RegisterEvent(
      events = {EventRender3D.class, EventNametagRender.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventRender3D) {
         EventRender3D er = (EventRender3D)event;
         Iterator var3 = mc.theWorld.playerEntities.iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            EntityPlayer player = (EntityPlayer)o;
            if (!player.isInvisible() && !(player instanceof EntityPlayerSP)) {
               double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)er.renderPartialTicks - RenderManager.renderPosX;
               double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)er.renderPartialTicks - RenderManager.renderPosY;
               double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)er.renderPartialTicks - RenderManager.renderPosZ;
               this.renderNametag(player, x, y, z);
            }
         }
      }

      if (event instanceof EventNametagRender) {
         event.setCancelled(true);
      }

   }

   public void renderNametag(EntityPlayer player, double x, double y, double z) {
      double tempY = y + (player.isSneaking() ? 0.5D : 0.7D);
      double size = (double)this.getSize(player) * -0.015D;
      GlStateManager.pushMatrix();
      GL11.glEnable(3042);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.translate((float)x, (float)tempY + 1.6F, (float)z);
      GL11.glNormal3f(0.0F, 2.0F, 0.0F);
      GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      float var10001 = mc.gameSettings.thirdPersonView == 2 ? -1.0F : 1.0F;
      GlStateManager.rotate(RenderManager.playerViewX, var10001, 0.0F, 0.0F);
      GL11.glScaled(size, size, size);
      GlStateManager.disableLighting();
      int width = mc.fontRendererObj.getStringWidth(player.getName() + " " + this.getHealth(player)) / 2;
      GlStateManager.enableTextures();
      RenderingUtil.rectangle((double)(-width - 2), (double)(-(mc.fontRendererObj.FONT_HEIGHT - 6)), (double)(width + 2), (double)(mc.fontRendererObj.FONT_HEIGHT + 1), -1728052224);
      GlStateManager.enableTextures();
      int color = -1;
      String str = player.getName();
      if (FriendManager.isFriend(str)) {
         color = 6083583;
         str = FriendManager.getAlias(str);
      }

      mc.fontRendererObj.drawStringWithShadow(str, (float)(-mc.fontRendererObj.getStringWidth(player.getName() + " " + this.getHealth(player)) / 2), 0.0F, color);
      float health = player.getHealth();
      float[] fractions = new float[]{0.0F, 0.5F, 1.0F};
      Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
      float progress = health / player.getMaxHealth();
      Color customColor = health >= 0.0F ? ESP2D.blendColors(fractions, colors, progress).brighter() : Color.RED;
      mc.fontRendererObj.drawStringWithShadow((int)health + "", (float)((mc.fontRendererObj.getStringWidth(player.getName() + " " + this.getHealth(player)) - mc.fontRendererObj.getStringWidth(this.getHealth(player)) * 2) / 2), 0.0F, customColor.getRGB());
      GlStateManager.disableBlend();
      if (((Boolean)((Setting)this.settings.get(ARMOR)).getValue()).booleanValue()) {
         this.renderArmor(player);
      }

      GlStateManager.enableBlend();
      GL11.glColor3d(1.0D, 1.0D, 1.0D);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glPopMatrix();
   }

   public void renderArmor(EntityPlayer player) {
      int xOffset = 0;
      ItemStack[] renderStack = player.inventory.armorInventory;
      int index = renderStack.length;

      for(int var5 = 0; var5 < index; ++var5) {
         ItemStack armourStack = renderStack[var5];
         if (armourStack != null) {
            xOffset -= 8;
         }
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

      for(index = 3; index >= 0; --index) {
         ItemStack armourStack = renderStack[index];
         if (armourStack != null) {
            this.renderItemStack(armourStack, xOffset, -20);
            xOffset += 16;
         }
      }

   }

   private int getHealthColorHEX(EntityPlayer e) {
      int health = Math.round(20.0F * (e.getHealth() / e.getMaxHealth()));
      int color;
      switch(health) {
      case -1:
      case 0:
      case 1:
         color = 16190746;
         break;
      case 2:
      case 3:
         color = 16711680;
         break;
      case 4:
      case 5:
         color = 15031100;
         break;
      case 6:
      case 7:
         color = 16286040;
         break;
      case 8:
      case 9:
         color = 16285719;
         break;
      case 10:
      case 11:
         color = 15313687;
         break;
      case 12:
      case 13:
         color = 16633879;
         break;
      case 14:
      case 15:
         color = 12844472;
         break;
      case 16:
      case 17:
         color = 10026904;
         break;
      case 18:
      case 19:
         color = 9108247;
         break;
      default:
         color = -11746281;
      }

      return color;
   }

   private String getHealth(EntityPlayer e) {
      String hp = "";
      DecimalFormat numberFormat = new DecimalFormat("#.0");
      double abs = (double)(2.0F * (e.getAbsorptionAmount() / 4.0F));
      double health = (10.0D + abs) * (double)(e.getHealth() / e.getMaxHealth());
      health = Double.valueOf(numberFormat.format(health)).doubleValue();
      if (Math.floor(health) == health) {
         hp = String.valueOf((int)health);
      } else {
         hp = String.valueOf(health);
      }

      return hp;
   }

   private float getSize(EntityPlayer player) {
      Entity ent = mc.thePlayer;
      double dist = (double)(ent.getDistanceToEntity(player) / 5.0F);
      float size = dist <= 2.0D ? 1.3F : (float)dist;
      return size;
   }

   private void renderItemStack(ItemStack stack, int x, int y) {
      GlStateManager.pushMatrix();
      GlStateManager.disableAlpha();
      GlStateManager.clear(256);
      mc.getRenderItem().zLevel = -150.0F;
      mc.getRenderItem().func_180450_b(stack, x, y);
      mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack, x, y);
      mc.getRenderItem().zLevel = 0.0F;
      GlStateManager.disableBlend();
      GlStateManager.scale(0.5D, 0.5D, 0.5D);
      GlStateManager.disableDepth();
      GlStateManager.disableLighting();
      this.renderEnchantText(stack, x, y);
      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GlStateManager.enableAlpha();
      GlStateManager.popMatrix();
   }

   private void renderEnchantText(ItemStack stack, int x, int y) {
      int enchantmentY = y - 24;
      if (stack.getEnchantmentTagList() != null && stack.getEnchantmentTagList().tagCount() >= 6) {
         mc.fontRendererObj.drawStringWithShadow("god", (float)(x * 2), (float)enchantmentY, 16711680);
      } else {
         int unbreakingLevel3;
         int efficiencyLevel;
         int fortuneLevel;
         int silkTouch;
         if (stack.getItem() instanceof ItemArmor) {
            unbreakingLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
            efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180308_g.effectId, stack);
            fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack);
            silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack);
            int thornsLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
            int unbreakingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (unbreakingLevel3 > 0) {
               mc.fontRendererObj.drawStringWithShadow("pr" + unbreakingLevel3, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (efficiencyLevel > 0) {
               mc.fontRendererObj.drawStringWithShadow("pp" + efficiencyLevel, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (fortuneLevel > 0) {
               mc.fontRendererObj.drawStringWithShadow("bp" + fortuneLevel, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (silkTouch > 0) {
               mc.fontRendererObj.drawStringWithShadow("fp" + silkTouch, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (thornsLevel > 0) {
               mc.fontRendererObj.drawStringWithShadow("t" + thornsLevel, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (unbreakingLevel > 0) {
               mc.fontRendererObj.drawStringWithShadow("u" + unbreakingLevel, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }
         }

         if (stack.getItem() instanceof ItemBow) {
            unbreakingLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
            silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (unbreakingLevel3 > 0) {
               mc.fontRendererObj.drawStringWithShadow("po" + unbreakingLevel3, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (efficiencyLevel > 0) {
               mc.fontRendererObj.drawStringWithShadow("pu" + efficiencyLevel, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (fortuneLevel > 0) {
               mc.fontRendererObj.drawStringWithShadow("f" + fortuneLevel, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (silkTouch > 0) {
               mc.fontRendererObj.drawStringWithShadow("u" + silkTouch, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }
         }

         if (stack.getItem() instanceof ItemSword) {
            unbreakingLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
            efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
            fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
            silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (unbreakingLevel3 > 0) {
               mc.fontRendererObj.drawStringWithShadow("sh" + unbreakingLevel3, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (efficiencyLevel > 0) {
               mc.fontRendererObj.drawStringWithShadow("kn" + efficiencyLevel, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (fortuneLevel > 0) {
               mc.fontRendererObj.drawStringWithShadow("f" + fortuneLevel, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (silkTouch > 0) {
               mc.fontRendererObj.drawStringWithShadow("ub" + silkTouch, (float)(x * 2), (float)enchantmentY, -1052689);
            }
         }

         if (stack.getItem() instanceof ItemTool) {
            unbreakingLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
            fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack);
            silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, stack);
            if (efficiencyLevel > 0) {
               mc.fontRendererObj.drawStringWithShadow("eff" + efficiencyLevel, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (fortuneLevel > 0) {
               mc.fontRendererObj.drawStringWithShadow("fo" + fortuneLevel, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (silkTouch > 0) {
               mc.fontRendererObj.drawStringWithShadow("st" + silkTouch, (float)(x * 2), (float)enchantmentY, -1052689);
               enchantmentY += 8;
            }

            if (unbreakingLevel3 > 0) {
               mc.fontRendererObj.drawStringWithShadow("ub" + unbreakingLevel3, (float)(x * 2), (float)enchantmentY, -1052689);
            }
         }

         if (stack.getItem() == Items.golden_apple && stack.hasEffect()) {
            mc.fontRendererObj.drawStringWithShadow("god", (float)(x * 2), (float)enchantmentY, -1052689);
         }

      }
   }
}
