/*   1:    */ package net.minecraft.client.entity;
/*   2:    */ 
/*   3:    */ import com.mojang.authlib.GameProfile;
/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.gui.GuiIngame;
/*   6:    */ import net.minecraft.client.gui.GuiNewChat;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.util.ChunkCoordinates;
/*  10:    */ import net.minecraft.util.DamageSource;
/*  11:    */ import net.minecraft.util.IChatComponent;
/*  12:    */ import net.minecraft.util.MathHelper;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class EntityOtherPlayerMP
/*  16:    */   extends AbstractClientPlayer
/*  17:    */ {
/*  18:    */   private boolean isItemInUse;
/*  19:    */   private int otherPlayerMPPosRotationIncrements;
/*  20:    */   private double otherPlayerMPX;
/*  21:    */   private double otherPlayerMPY;
/*  22:    */   private double otherPlayerMPZ;
/*  23:    */   private double otherPlayerMPYaw;
/*  24:    */   private double otherPlayerMPPitch;
/*  25:    */   private static final String __OBFID = "CL_00000939";
/*  26:    */   
/*  27:    */   public EntityOtherPlayerMP(World p_i45075_1_, GameProfile p_i45075_2_)
/*  28:    */   {
/*  29: 25 */     super(p_i45075_1_, p_i45075_2_);
/*  30: 26 */     this.yOffset = 0.0F;
/*  31: 27 */     this.stepHeight = 0.0F;
/*  32: 28 */     this.noClip = true;
/*  33: 29 */     this.field_71082_cx = 0.25F;
/*  34: 30 */     this.renderDistanceWeight = 10.0D;
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected void resetHeight()
/*  38:    */   {
/*  39: 38 */     this.yOffset = 0.0F;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  43:    */   {
/*  44: 46 */     return true;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
/*  48:    */   {
/*  49: 55 */     this.otherPlayerMPX = par1;
/*  50: 56 */     this.otherPlayerMPY = par3;
/*  51: 57 */     this.otherPlayerMPZ = par5;
/*  52: 58 */     this.otherPlayerMPYaw = par7;
/*  53: 59 */     this.otherPlayerMPPitch = par8;
/*  54: 60 */     this.otherPlayerMPPosRotationIncrements = par9;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void onUpdate()
/*  58:    */   {
/*  59: 68 */     this.field_71082_cx = 0.0F;
/*  60: 69 */     super.onUpdate();
/*  61: 70 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/*  62: 71 */     double var1 = this.posX - this.prevPosX;
/*  63: 72 */     double var3 = this.posZ - this.prevPosZ;
/*  64: 73 */     float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3) * 4.0F;
/*  65: 75 */     if (var5 > 1.0F) {
/*  66: 77 */       var5 = 1.0F;
/*  67:    */     }
/*  68: 80 */     this.limbSwingAmount += (var5 - this.limbSwingAmount) * 0.4F;
/*  69: 81 */     this.limbSwing += this.limbSwingAmount;
/*  70: 83 */     if ((!this.isItemInUse) && (isEating()) && (this.inventory.mainInventory[this.inventory.currentItem] != null))
/*  71:    */     {
/*  72: 85 */       ItemStack var6 = this.inventory.mainInventory[this.inventory.currentItem];
/*  73: 86 */       setItemInUse(this.inventory.mainInventory[this.inventory.currentItem], var6.getItem().getMaxItemUseDuration(var6));
/*  74: 87 */       this.isItemInUse = true;
/*  75:    */     }
/*  76: 89 */     else if ((this.isItemInUse) && (!isEating()))
/*  77:    */     {
/*  78: 91 */       clearItemInUse();
/*  79: 92 */       this.isItemInUse = false;
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public float getShadowSize()
/*  84:    */   {
/*  85: 98 */     return 0.0F;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void onLivingUpdate()
/*  89:    */   {
/*  90:107 */     super.updateEntityActionState();
/*  91:109 */     if (this.otherPlayerMPPosRotationIncrements > 0)
/*  92:    */     {
/*  93:111 */       double var1 = this.posX + (this.otherPlayerMPX - this.posX) / this.otherPlayerMPPosRotationIncrements;
/*  94:112 */       double var3 = this.posY + (this.otherPlayerMPY - this.posY) / this.otherPlayerMPPosRotationIncrements;
/*  95:113 */       double var5 = this.posZ + (this.otherPlayerMPZ - this.posZ) / this.otherPlayerMPPosRotationIncrements;
/*  96:116 */       for (double var7 = this.otherPlayerMPYaw - this.rotationYaw; var7 < -180.0D; var7 += 360.0D) {}
/*  97:121 */       while (var7 >= 180.0D) {
/*  98:123 */         var7 -= 360.0D;
/*  99:    */       }
/* 100:126 */       this.rotationYaw = ((float)(this.rotationYaw + var7 / this.otherPlayerMPPosRotationIncrements));
/* 101:127 */       this.rotationPitch = ((float)(this.rotationPitch + (this.otherPlayerMPPitch - this.rotationPitch) / this.otherPlayerMPPosRotationIncrements));
/* 102:128 */       this.otherPlayerMPPosRotationIncrements -= 1;
/* 103:129 */       setPosition(var1, var3, var5);
/* 104:130 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 105:    */     }
/* 106:133 */     this.prevCameraYaw = this.cameraYaw;
/* 107:134 */     float var9 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 108:135 */     float var2 = (float)Math.atan(-this.motionY * 0.2000000029802322D) * 15.0F;
/* 109:137 */     if (var9 > 0.1F) {
/* 110:139 */       var9 = 0.1F;
/* 111:    */     }
/* 112:142 */     if ((!this.onGround) || (getHealth() <= 0.0F)) {
/* 113:144 */       var9 = 0.0F;
/* 114:    */     }
/* 115:147 */     if ((this.onGround) || (getHealth() <= 0.0F)) {
/* 116:149 */       var2 = 0.0F;
/* 117:    */     }
/* 118:152 */     this.cameraYaw += (var9 - this.cameraYaw) * 0.4F;
/* 119:153 */     this.cameraPitch += (var2 - this.cameraPitch) * 0.8F;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
/* 123:    */   {
/* 124:161 */     if (par1 == 0) {
/* 125:163 */       this.inventory.mainInventory[this.inventory.currentItem] = par2ItemStack;
/* 126:    */     } else {
/* 127:167 */       this.inventory.armorInventory[(par1 - 1)] = par2ItemStack;
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public float getEyeHeight()
/* 132:    */   {
/* 133:173 */     return 1.82F;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void addChatMessage(IChatComponent p_145747_1_)
/* 137:    */   {
/* 138:184 */     Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(p_145747_1_);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean canCommandSenderUseCommand(int par1, String par2Str)
/* 142:    */   {
/* 143:192 */     return false;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public ChunkCoordinates getPlayerCoordinates()
/* 147:    */   {
/* 148:200 */     return new ChunkCoordinates(MathHelper.floor_double(this.posX + 0.5D), MathHelper.floor_double(this.posY + 0.5D), MathHelper.floor_double(this.posZ + 0.5D));
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.entity.EntityOtherPlayerMP
 * JD-Core Version:    0.7.0.1
 */