/*   1:    */ package net.minecraft.world;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.EntityTracker;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*   9:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  10:    */ import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
/*  11:    */ import net.minecraft.network.play.server.S28PacketEffect;
/*  12:    */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*  13:    */ import net.minecraft.server.MinecraftServer;
/*  14:    */ import net.minecraft.server.management.PlayerManager;
/*  15:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  16:    */ 
/*  17:    */ public class WorldManager
/*  18:    */   implements IWorldAccess
/*  19:    */ {
/*  20:    */   private MinecraftServer mcServer;
/*  21:    */   private WorldServer theWorldServer;
/*  22:    */   private static final String __OBFID = "CL_00001433";
/*  23:    */   
/*  24:    */   public WorldManager(MinecraftServer par1MinecraftServer, WorldServer par2WorldServer)
/*  25:    */   {
/*  26: 23 */     this.mcServer = par1MinecraftServer;
/*  27: 24 */     this.theWorldServer = par2WorldServer;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {}
/*  31:    */   
/*  32:    */   public void onEntityCreate(Entity par1Entity)
/*  33:    */   {
/*  34: 38 */     this.theWorldServer.getEntityTracker().addEntityToTracker(par1Entity);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void onEntityDestroy(Entity par1Entity)
/*  38:    */   {
/*  39: 47 */     this.theWorldServer.getEntityTracker().removeEntityFromAllTrackingPlayers(par1Entity);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void playSound(String par1Str, double par2, double par4, double par6, float par8, float par9)
/*  43:    */   {
/*  44: 55 */     this.mcServer.getConfigurationManager().func_148541_a(par2, par4, par6, par8 > 1.0F ? 16.0F * par8 : 16.0D, this.theWorldServer.provider.dimensionId, new S29PacketSoundEffect(par1Str, par2, par4, par6, par8, par9));
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, double par3, double par5, double par7, float par9, float par10)
/*  48:    */   {
/*  49: 63 */     this.mcServer.getConfigurationManager().func_148543_a(par1EntityPlayer, par3, par5, par7, par9 > 1.0F ? 16.0F * par9 : 16.0D, this.theWorldServer.provider.dimensionId, new S29PacketSoundEffect(par2Str, par3, par5, par7, par9, par10));
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void markBlockRangeForRenderUpdate(int p_147585_1_, int p_147585_2_, int p_147585_3_, int p_147585_4_, int p_147585_5_, int p_147585_6_) {}
/*  53:    */   
/*  54:    */   public void markBlockForUpdate(int p_147586_1_, int p_147586_2_, int p_147586_3_)
/*  55:    */   {
/*  56: 78 */     this.theWorldServer.getPlayerManager().func_151250_a(p_147586_1_, p_147586_2_, p_147586_3_);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void markBlockForRenderUpdate(int p_147588_1_, int p_147588_2_, int p_147588_3_) {}
/*  60:    */   
/*  61:    */   public void playRecord(String par1Str, int par2, int par3, int par4) {}
/*  62:    */   
/*  63:    */   public void playAuxSFX(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
/*  64:    */   {
/*  65: 96 */     this.mcServer.getConfigurationManager().func_148543_a(par1EntityPlayer, par3, par4, par5, 64.0D, this.theWorldServer.provider.dimensionId, new S28PacketEffect(par2, par3, par4, par5, par6, false));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void broadcastSound(int par1, int par2, int par3, int par4, int par5)
/*  69:    */   {
/*  70:101 */     this.mcServer.getConfigurationManager().func_148540_a(new S28PacketEffect(par1, par2, par3, par4, par5, true));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void destroyBlockPartially(int p_147587_1_, int p_147587_2_, int p_147587_3_, int p_147587_4_, int p_147587_5_)
/*  74:    */   {
/*  75:110 */     Iterator var6 = this.mcServer.getConfigurationManager().playerEntityList.iterator();
/*  76:112 */     while (var6.hasNext())
/*  77:    */     {
/*  78:114 */       EntityPlayerMP var7 = (EntityPlayerMP)var6.next();
/*  79:116 */       if ((var7 != null) && (var7.worldObj == this.theWorldServer) && (var7.getEntityId() != p_147587_1_))
/*  80:    */       {
/*  81:118 */         double var8 = p_147587_2_ - var7.posX;
/*  82:119 */         double var10 = p_147587_3_ - var7.posY;
/*  83:120 */         double var12 = p_147587_4_ - var7.posZ;
/*  84:122 */         if (var8 * var8 + var10 * var10 + var12 * var12 < 1024.0D) {
/*  85:124 */           var7.playerNetServerHandler.sendPacket(new S25PacketBlockBreakAnim(p_147587_1_, p_147587_2_, p_147587_3_, p_147587_4_, p_147587_5_));
/*  86:    */         }
/*  87:    */       }
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void onStaticEntitiesChanged() {}
/*  92:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.WorldManager
 * JD-Core Version:    0.7.0.1
 */