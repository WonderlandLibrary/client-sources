package net.andrewsnetwork.icarus.module.modules;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.events.RenderIn3D;
import net.andrewsnetwork.icarus.module.Module;
import net.andrewsnetwork.icarus.module.Module.Category;
import net.andrewsnetwork.icarus.module.modules.Teams;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.andrewsnetwork.icarus.utilities.NahrFont.FontType;
import net.andrewsnetwork.icarus.values.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class NameTags extends Module {
   public Value armor = new Value("nametags_Armor", "armor", Boolean.valueOf(true), this);

   public NameTags() {
      super("NameTags", Category.RENDER);
      this.setEnabled(true);
   }

   public void onEvent(Event event) {
      if(event instanceof EatMyAssYouFuckingDecompiler) {
         OutputStreamWriter render = new OutputStreamWriter(System.out);

         try {
            render.flush();
         } catch (IOException var13) {
            ;
         } finally {
            render = null;
         }
      }

      if(event instanceof RenderIn3D) {
         if(!Minecraft.isGuiEnabled()) {
            return;
         }

         RenderIn3D render1 = (RenderIn3D)event;
         Iterator var4 = mc.theWorld.playerEntities.iterator();

         while(var4.hasNext()) {
            EntityPlayer player = (EntityPlayer)var4.next();
            if(player != null && player != mc.thePlayer && player.isEntityAlive()) {
               double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)render1.getPartialTicks() - RenderManager.renderPosX;
               double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)render1.getPartialTicks() - RenderManager.renderPosY;
               double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)render1.getPartialTicks() - RenderManager.renderPosZ;
               GL11.glPolygonOffset(-120000.0F, -120000.0F);
               this.renderNametag(player, posX, posY, posZ);
               GL11.glPolygonOffset(120000.0F, 120000.0F);
            }
         }
      }

   }

   private String getHealthColor(int health) {
      String color;
      if(health > 10) {
         color = "d";
      } else if((double)health > 7.5D) {
         color = "2";
      } else if(health > 5) {
         color = "e";
      } else if((double)health > 2.5D) {
         color = "6";
      } else {
         color = "4";
      }

      return color;
   }

   private boolean isPotting(EntityPlayer player, ItemStack stack) {
      return stack == null?false:player.isUsingItem() && stack.getItem() instanceof ItemPotion;
   }

   private int getNametagColor(EntityPlayer player) {
      Teams teams = (Teams)Icarus.getModuleManager().getModuleByName("teams");
      int color = -1;
      if(Icarus.getFriendManager().isFriend(player.getName())) {
         color = -16724788;
      } else if(teams.isEnabled() && teams.getTabName(player).length() > 2 && teams.getTabName(mc.thePlayer).startsWith(teams.getTabName(player).substring(0, 2))) {
         color = -8043776;
      } else if(this.isPotting(player, player.getItemInUse())) {
         color = -1020415;
      } else if((mc.thePlayer.isSneaking() || Icarus.getModuleManager().getModuleByName("sneak") != null && Icarus.getModuleManager().getModuleByName("sneak").isEnabled()) && !player.canEntityBeSeen(mc.thePlayer)) {
         color = -13447886;
      } else if(player.isSneaking()) {
         color = -262144;
      }

      return color;
   }

   private String getNametagName(EntityPlayer player) {
      String name = player.getDisplayName().getFormattedText();
      if(Icarus.getFriendManager().isFriend(StringUtils.stripControlCodes(player.getName()))) {
         name = StringUtils.stripControlCodes(player.getName());
         name = StringUtils.stripControlCodes(Icarus.getFriendManager().replaceNames(name, false));
      }

      name = StringUtils.stripControlCodes(name);
      double health = Math.ceil((double)(player.getHealth() + player.getAbsorptionAmount())) / 2.0D;
      String hearts = "";
      if(Math.floor(health) == health) {
         hearts = String.valueOf((int)Math.floor(health));
      } else {
         hearts = String.valueOf(health);
      }

      name = name + " §f§" + this.getHealthColor((int)health) + hearts;
      return name;
   }

   private float getNametagSize(EntityPlayer player) {
      float dist = mc.thePlayer.getDistanceToEntity(player) / 4.0F;
      return dist <= 1.5F?1.5F:dist;
   }

   protected void renderNametag(EntityPlayer player, double x, double y, double z) {
      String name = this.getNametagName(player);
      FontRenderer var12 = mc.fontRendererObj;
      float var13 = this.getNametagSize(player);
      float var14 = 0.016666668F * var13;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x, (float)y + player.height + 0.5F, (float)z);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-var14, -var14, var14);
      GL11.glDisable(2896);
      GL11.glDepthMask(false);
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      Tessellator var15 = Tessellator.instance;
      byte var16 = 0;
      if(player.isSneaking()) {
         var16 = 4;
      }

      GL11.glDisable(3553);
      float var17 = (float)(mc.fontRendererObj.getStringWidth(StringUtils.stripControlCodes(Icarus.getFriendManager().replaceNames(name, true))) / 2);
      GL11.glPushMatrix();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      RenderHelper.drawBorderedRect((float)(-var12.getStringWidth(name) / 2 - 1), (float)(var16 - 5 + 3), (float)(-var12.getStringWidth(name) / 2 + mc.fontRendererObj.getStringWidth(StringUtils.stripControlCodes(name)) + 1), (float)(var16 - 5 + 14), 1.0F, -587202560, Integer.MIN_VALUE);
      mc.fontRendererObj.func_175063_a(name, (float)(-var12.getStringWidth(name) / 2), (float)var16, this.getNametagColor(player));
      if(((Boolean)this.armor.getValue()).booleanValue() && mc.thePlayer.getDistanceToEntity(player) <= 30.0F) {
         ArrayList items = new ArrayList();

         int offset;
         for(offset = 3; offset >= 0; --offset) {
            ItemStack xPos = player.inventory.armorInventory[offset];
            if(xPos != null) {
               items.add(xPos);
            }
         }

         if(player.getCurrentEquippedItem() != null) {
            items.add(player.getCurrentEquippedItem());
         }

         offset = (int)(var17 - (float)((items.size() - 1) * 9) - 9.0F);
         int var28 = 0;
         Iterator var19 = items.iterator();

         while(var19.hasNext()) {
            ItemStack stack = (ItemStack)var19.next();
            GL11.glPushMatrix();
            GL11.glDepthMask(true);
            GlStateManager.clear(256);
            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
            GlStateManager.disableDepth();
            GlStateManager.enableDepth();
            mc.getRenderItem().zLevel = -100.0F;
            mc.getRenderItem().func_180450_b(stack, (int)(-var17 + (float)offset + (float)var28), var16 - 20);
            mc.getRenderItem().func_175030_a(Minecraft.getMinecraft().fontRendererObj, stack, (int)(-var17 + (float)offset + (float)var28), var16 - 20);
            mc.getRenderItem().zLevel = 0.0F;
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.disableDepth();
            GlStateManager.enableAlpha();
            NBTTagList enchants = stack.getEnchantmentTagList();
            GL11.glPushMatrix();
            GL11.glDisable(2896);
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            if(stack.getItem() == Items.golden_apple && stack.hasEffect()) {
               RenderHelper.getNahrFont().drawString("god", (-var17 + (float)offset + (float)var28) * 2.0F, (float)((var16 - 20) * 2), FontType.SHADOW_THIN, -65536, -16777216);
            } else if(enchants != null) {
               int ency = 0;
               if(enchants.tagCount() >= 6) {
                  RenderHelper.getNahrFont().drawString("god", (-var17 + (float)offset + (float)var28) * 2.0F, (float)((var16 - 20) * 2), FontType.SHADOW_THIN, -65536, -16777216);
               } else {
                  try {
                     for(int index = 0; index < enchants.tagCount(); ++index) {
                        short id = enchants.getCompoundTagAt(index).getShort("id");
                        short level = enchants.getCompoundTagAt(index).getShort("lvl");
                        if(Enchantment.field_180311_a[id] != null) {
                           Enchantment enc = Enchantment.field_180311_a[id];
                           String encName = enc.getTranslatedName(level).substring(0, 2).toLowerCase();
                           if(level > 99) {
                              encName = encName + "99+";
                           } else {
                              encName = encName + level;
                           }

                           RenderHelper.getNahrFont().drawString(encName, (-var17 + (float)offset + (float)var28) * 2.0F + 2.0F, (float)((var16 - 20 + ency) * 2), FontType.SHADOW_THICK, -1, -16777216);
                           ency += mc.fontRendererObj.FONT_HEIGHT / 2 + 1;
                        }
                     }
                  } catch (Exception var27) {
                     ;
                  }
               }
            }

            GL11.glEnable(2896);
            GL11.glPopMatrix();
            var28 += 18;
            GlStateManager.enableDepth();
            GL11.glPopMatrix();
         }
      }

      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }
}
