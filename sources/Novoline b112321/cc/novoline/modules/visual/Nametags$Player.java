package cc.novoline.modules.visual;

import cc.novoline.Novoline;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.visual.HUD;
import cc.novoline.modules.visual.Nametags;
import cc.novoline.utils.PlayerUtils;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_18;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_20;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import net.Ls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

class Nametags$Player {
   private final EntityLivingBase entity;
   private double[] positions;
   final Nametags this$0;

   public Nametags$Player(Nametags var1, EntityLivingBase var2) {
      this.this$0 = var1;
      this.positions = new double[]{0.0D, 0.0D, 0.0D};
      this.entity = var2;
   }

   void render() {
      HUD.h();
      GL11.glPushMatrix();
      ScaledResolution var2 = new ScaledResolution(Minecraft.getInstance());
      PlayerManager$EnumPlayerType var3 = Novoline.getInstance().getPlayerManager().getType(this.entity.getName());
      float var4 = (float)(this.positions[0] / (double)var2.getScaleFactor());
      float var5 = (float)(this.positions[1] / (double)var2.getScaleFactor());
      float var6 = (float)(this.positions[2] / (double)var2.getScaleFactor());
      List var7 = (List)((Nametags)this.this$0.getModule(Nametags.class)).getContent().get();
      List var8 = (List)((Nametags)this.this$0.getModule(Nametags.class)).getAdditions().get();
      String var9 = var8.contains("Health")?(((Nametags)this.this$0.getModule(Nametags.class)).getHealthType().equalsIgnoreCase("Value")?" " + (int)(this.entity.getHealth() + this.entity.getAbsorptionAmount()):""):"";
      String var10 = var7.contains("Distance")?" " + (int)Minecraft.getInstance().player.getDistanceToEntity(this.entity) + "m":"";
      String var11 = this.entity.getDisplayName().getFormattedText();
      GL11.glTranslatef(var4, var5, var6);
      float var12 = 1.0F;
      switch(Minecraft.getInstance().gameSettings.guiScale) {
      case 0:
         var12 = 0.5F;
      case 1:
         var12 = 2.0F;
      case 3:
         var12 = 0.6666667F;
      case 2:
      }

      if(this.positions[2] >= 0.0D && this.positions[2] < 1.0D) {
         ScaledResolution var13 = new ScaledResolution(Nametags.access$100(this.this$0));
         double var14 = (double)var13.getScaleFactor() / Math.pow((double)var13.getScaleFactor(), 2.0D);
         GL11.glScaled(var14, var14, var14);
         GlStateManager.disableDepth();
         String var16 = (var3 == PlayerManager$EnumPlayerType.FRIEND?EnumChatFormatting.AQUA + "[FRIEND] ":(((KillAura)this.this$0.getModule(KillAura.class)).isEnabled() && ((KillAura)this.this$0.getModule(KillAura.class)).getFilters().contains("Teams") && PlayerUtils.inTeamWithMinecraftPlayer(this.entity)?"§a[TEAM]§c ":(var3 == PlayerManager$EnumPlayerType.TARGET?"§c[TARGET]§r ":""))) + EnumChatFormatting.RESET + var11 + EnumChatFormatting.GRAY + var10;
         float var17 = Math.abs(-(this.getStringWidth(var16) / 2.0F) - 3.0F - (this.getStringWidth(var16) / 2.0F + 4.0F));
         float var18 = (float)((int)(this.entity.getMaxHealth() + this.entity.getAbsorptionAmount()));
         float var19 = 100.0F / var18;
         float var20 = (float)((int)((this.entity.getHealth() + this.entity.getAbsorptionAmount()) * var19));
         float var21 = var17 / 100.0F;
         int var22 = var8.contains("Health") && ((Nametags)this.this$0.getModule(Nametags.class)).getHealthType().equalsIgnoreCase("Value")?5:0;
         float var23 = this.getStringWidth(var16) / 2.0F;
         ScaledResolution var24 = new ScaledResolution(Minecraft.getInstance());
         float var25 = (float)var24.getScaledWidth() / 2.0F;
         float var26 = (float)var24.getScaledHeight() / 2.0F;
         float var27 = (float)Fonts$SFBOLD$SFBOLD_20.SFBOLD_20.stringWidth(var16) / 2.0F * 0.5F;
         float var28 = 6.75F;
         float var29 = var25 / var12 + var27;
         float var30 = var25 / var12 - var27;
         float var31 = var26 / var12 - var28;
         float var32 = var26 / var12 + var28;
         if(var8.contains("Background")) {
            RenderUtils.drawRect(-var23 - 2.0F - (float)var22, -8.0F - this.getYOffset(), this.getStringWidth(var16 + var9) / 2.0F + (((Nametags)this.this$0.getModule(Nametags.class)).getHealthType().equalsIgnoreCase("Bar")?2.0F:this.getStringWidth(var9) - 3.0F - (float)var22), (float)(var8.contains("Health")?(((Nametags)this.this$0.getModule(Nametags.class)).getHealthType().equalsIgnoreCase("Bar")?6:5):5) - this.getYOffset(), (new Color(var3 == PlayerManager$EnumPlayerType.TARGET?100:0, var3 != PlayerManager$EnumPlayerType.FRIEND && (!((KillAura)this.this$0.getModule(KillAura.class)).isEnabled() || !((KillAura)this.this$0.getModule(KillAura.class)).getFilters().contains("Teams") || !PlayerUtils.inTeamWithMinecraftPlayer(this.entity))?0:90, var3 == PlayerManager$EnumPlayerType.FRIEND?120:(PlayerUtils.inTeamWithMinecraftPlayer(this.entity)?15:0), ((Integer)((Nametags)this.this$0.getModule(Nametags.class)).getBackgroundAlpha().get()).intValue())).getRGB());
         }

         if(var7.contains("Armor")) {
            this.renderArmor((EntityPlayer)this.entity);
         }

         this.drawString(var16, -var23 - (float)var22, -this.getYOffset() - 5.0F, 16777215);
         this.drawString(var9, var23 - (float)var22, -this.getYOffset() - 5.0F, this.getHealthColor());
         if(var8.contains("Health") && ((Nametags)this.this$0.getModule(Nametags.class)).getHealthType().equalsIgnoreCase("Bar")) {
            Gui.drawRect((double)(-var23 - 2.0F), (double)(5.0F - this.getYOffset()), (double)(-var23 - 5.0F + var20 * var21), (double)(6.0F - this.getYOffset()), this.getHealthColor());
         }

         GlStateManager.enableDepth();
         GL11.glPopMatrix();
      } else {
         GlStateManager.popMatrix();
      }
   }

   private void drawString(String var1, float var2, float var3, int var4) {
      int var5 = HUD.e();
      if(((String)Nametags.access$200(this.this$0).get()).equalsIgnoreCase("Client")) {
         Fonts$SFBOLD$SFBOLD_20.SFBOLD_20.drawString(var1, var2, var3, var4);
      }

      Nametags.access$300(this.this$0).fontRendererObj.drawString(var1, var2, var3, var4);
   }

   private float getStringWidth(String var1) {
      int var2 = HUD.e();
      return ((String)Nametags.access$200(this.this$0).get()).equalsIgnoreCase("Client")?(float)Fonts$SFBOLD$SFBOLD_20.SFBOLD_20.stringWidth(var1):(float)Nametags.access$400(this.this$0).fontRendererObj.d(var1);
   }

   private float getYOffset() {
      HUD.h();
      float var2 = Minecraft.getInstance().player.getDistanceToEntity(this.entity);
      return ((Nametags)this.this$0.getModule(Nametags.class)).getHealthType().equalsIgnoreCase("Bar")?(float)Math.max((double)this.getDistance() * (var2 >= 110.0F?0.058D:0.032D + (double)(4.0F / var2)), 1.0D):(float)Math.max((double)this.getDistance() * (var2 >= 110.0F?0.046D:0.02D + (double)(4.0F / var2)), 1.0D);
   }

   private int getHealthColor() {
      float var1 = this.entity.getHealth();
      float var2 = this.entity.getMaxHealth();
      float var3 = Math.max(0.0F, Math.min(var1, var2) / var2);
      return Color.HSBtoRGB(var3 / 3.0F, 1.0F, 1.0F) | -16777216;
   }

   private int getDistance() {
      int var1 = (int)Math.abs(Minecraft.getInstance().player.posX - this.entity.posX);
      int var2 = (int)Math.abs(Minecraft.getInstance().player.posY - this.entity.posY);
      int var3 = (int)Math.abs(Minecraft.getInstance().player.posZ - this.entity.posZ);
      return (int)Math.sqrt((double)(var1 * var1 + var2 * var2 + var3 * var3));
   }

   private double[] convertTo2D(double var1, double var3, double var5) {
      FloatBuffer var8 = BufferUtils.createFloatBuffer(3);
      IntBuffer var9 = BufferUtils.createIntBuffer(16);
      HUD.h();
      FloatBuffer var10 = BufferUtils.createFloatBuffer(16);
      FloatBuffer var11 = BufferUtils.createFloatBuffer(16);
      GL11.glGetFloat(2982, var10);
      GL11.glGetFloat(2983, var11);
      GL11.glGetInteger(2978, var9);
      boolean var12 = Ls.a((float)var1, (float)var3, (float)var5, var10, var11, var9, var8);
      return var12?new double[]{(double)var8.get(0), (double)((float)Display.getHeight() - var8.get(1)), (double)var8.get(2)}:null;
   }

   private void renderArmor(EntityPlayer var1) {
      ItemStack[] var3 = var1.inventory.armorInventory;
      int var5 = 0;
      HUD.e();
      int var7 = var3.length;
      int var8 = 0;
      if(var8 < var7) {
         ItemStack var9 = var3[var8];
         if(var9 != null) {
            var5 -= 8;
         }

         ++var8;
      }

      if(var1.getCurrentEquippedItem() != null) {
         var5 = var5 - 8;
         ItemStack var6 = var1.getCurrentEquippedItem().copy();
         if(var6.hasEffect() && (var6.getItem() instanceof ItemTool || var6.getItem() instanceof ItemArmor)) {
            var6.stackSize = 1;
         }

         this.renderItemStack(var6, var5, -25.0F - this.getYOffset() * 1.5F);
         var5 = var5 + 16;
      }

      var3 = var1.inventory.armorInventory;
      int var13 = 3;
      ItemStack var4 = var3[var13];
      this.renderItemStack(var4, var5, -25.0F - this.getYOffset() * 1.5F);
      var5 = var5 + 16;
      --var13;
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderItemStack(ItemStack var1, int var2, float var3) {
      GlStateManager.pushMatrix();
      GlStateManager.depthMask(true);
      GlStateManager.clear(256);
      RenderHelper.enableStandardItemLighting();
      Minecraft.getInstance().getRenderItem().zLevel = -150.0F;
      GlStateManager.disableDepth();
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
      GlStateManager.scale(0.7D, 0.7D, 0.7D);
      Minecraft.getInstance().getRenderItem().renderItemAndEffectIntoGUI(var1, (float)var2, var3);
      Minecraft.getInstance().getRenderItem().renderItemOverlaysCR(Fonts$SFBOLD$SFBOLD_18.SFBOLD_18, var1, var2, (int)var3);
      Minecraft.getInstance().getRenderItem().zLevel = 0.0F;
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableCull();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
      float var4 = 0.5F;
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GlStateManager.popMatrix();
   }

   static EntityLivingBase access$500(Nametags$Player var0) {
      return var0.entity;
   }

   static double[] access$602(Nametags$Player var0, double[] var1) {
      return var0.positions = var1;
   }

   static double[] access$700(Nametags$Player var0, double var1, double var3, double var5) {
      return var0.convertTo2D(var1, var3, var5);
   }
}
