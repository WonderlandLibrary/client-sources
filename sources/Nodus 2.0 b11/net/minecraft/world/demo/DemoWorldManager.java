/*   1:    */ package net.minecraft.world.demo;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.EntityPlayer;
/*   4:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*   5:    */ import net.minecraft.item.ItemStack;
/*   6:    */ import net.minecraft.network.NetHandlerPlayServer;
/*   7:    */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*   8:    */ import net.minecraft.server.management.ItemInWorldManager;
/*   9:    */ import net.minecraft.util.ChatComponentTranslation;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class DemoWorldManager
/*  13:    */   extends ItemInWorldManager
/*  14:    */ {
/*  15:    */   private boolean field_73105_c;
/*  16:    */   private boolean demoTimeExpired;
/*  17:    */   private int field_73104_e;
/*  18:    */   private int field_73102_f;
/*  19:    */   private static final String __OBFID = "CL_00001429";
/*  20:    */   
/*  21:    */   public DemoWorldManager(World par1World)
/*  22:    */   {
/*  23: 20 */     super(par1World);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void updateBlockRemoving()
/*  27:    */   {
/*  28: 25 */     super.updateBlockRemoving();
/*  29: 26 */     this.field_73102_f += 1;
/*  30: 27 */     long var1 = this.theWorld.getTotalWorldTime();
/*  31: 28 */     long var3 = var1 / 24000L + 1L;
/*  32: 30 */     if ((!this.field_73105_c) && (this.field_73102_f > 20))
/*  33:    */     {
/*  34: 32 */       this.field_73105_c = true;
/*  35: 33 */       this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 0.0F));
/*  36:    */     }
/*  37: 36 */     this.demoTimeExpired = (var1 > 120500L);
/*  38: 38 */     if (this.demoTimeExpired) {
/*  39: 40 */       this.field_73104_e += 1;
/*  40:    */     }
/*  41: 43 */     if (var1 % 24000L == 500L)
/*  42:    */     {
/*  43: 45 */       if (var3 <= 6L) {
/*  44: 47 */         this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day." + var3, new Object[0]));
/*  45:    */       }
/*  46:    */     }
/*  47: 50 */     else if (var3 == 1L)
/*  48:    */     {
/*  49: 52 */       if (var1 == 100L) {
/*  50: 54 */         this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 101.0F));
/*  51: 56 */       } else if (var1 == 175L) {
/*  52: 58 */         this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 102.0F));
/*  53: 60 */       } else if (var1 == 250L) {
/*  54: 62 */         this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 103.0F));
/*  55:    */       }
/*  56:    */     }
/*  57: 65 */     else if ((var3 == 5L) && (var1 % 24000L == 22000L)) {
/*  58: 67 */       this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day.warning", new Object[0]));
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void sendDemoReminder()
/*  63:    */   {
/*  64: 76 */     if (this.field_73104_e > 100)
/*  65:    */     {
/*  66: 78 */       this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.reminder", new Object[0]));
/*  67: 79 */       this.field_73104_e = 0;
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void onBlockClicked(int par1, int par2, int par3, int par4)
/*  72:    */   {
/*  73: 89 */     if (this.demoTimeExpired) {
/*  74: 91 */       sendDemoReminder();
/*  75:    */     } else {
/*  76: 95 */       super.onBlockClicked(par1, par2, par3, par4);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void uncheckedTryHarvestBlock(int par1, int par2, int par3)
/*  81:    */   {
/*  82:101 */     if (!this.demoTimeExpired) {
/*  83:103 */       super.uncheckedTryHarvestBlock(par1, par2, par3);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean tryHarvestBlock(int par1, int par2, int par3)
/*  88:    */   {
/*  89:112 */     return this.demoTimeExpired ? false : super.tryHarvestBlock(par1, par2, par3);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean tryUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
/*  93:    */   {
/*  94:120 */     if (this.demoTimeExpired)
/*  95:    */     {
/*  96:122 */       sendDemoReminder();
/*  97:123 */       return false;
/*  98:    */     }
/*  99:127 */     return super.tryUseItem(par1EntityPlayer, par2World, par3ItemStack);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean activateBlockOrUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 103:    */   {
/* 104:137 */     if (this.demoTimeExpired)
/* 105:    */     {
/* 106:139 */       sendDemoReminder();
/* 107:140 */       return false;
/* 108:    */     }
/* 109:144 */     return super.activateBlockOrUseItem(par1EntityPlayer, par2World, par3ItemStack, par4, par5, par6, par7, par8, par9, par10);
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.demo.DemoWorldManager
 * JD-Core Version:    0.7.0.1
 */