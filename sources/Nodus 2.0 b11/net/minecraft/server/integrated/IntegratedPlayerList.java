/*  1:   */ package net.minecraft.server.integrated;
/*  2:   */ 
/*  3:   */ import com.mojang.authlib.GameProfile;
/*  4:   */ import java.net.SocketAddress;
/*  5:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  6:   */ import net.minecraft.nbt.NBTTagCompound;
/*  7:   */ import net.minecraft.server.management.ServerConfigurationManager;
/*  8:   */ 
/*  9:   */ public class IntegratedPlayerList
/* 10:   */   extends ServerConfigurationManager
/* 11:   */ {
/* 12:   */   private NBTTagCompound hostPlayerData;
/* 13:   */   private static final String __OBFID = "CL_00001128";
/* 14:   */   
/* 15:   */   public IntegratedPlayerList(IntegratedServer par1IntegratedServer)
/* 16:   */   {
/* 17:20 */     super(par1IntegratedServer);
/* 18:21 */     this.viewDistance = 10;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected void writePlayerData(EntityPlayerMP par1EntityPlayerMP)
/* 22:   */   {
/* 23:29 */     if (par1EntityPlayerMP.getCommandSenderName().equals(getServerInstance().getServerOwner()))
/* 24:   */     {
/* 25:31 */       this.hostPlayerData = new NBTTagCompound();
/* 26:32 */       par1EntityPlayerMP.writeToNBT(this.hostPlayerData);
/* 27:   */     }
/* 28:35 */     super.writePlayerData(par1EntityPlayerMP);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String func_148542_a(SocketAddress p_148542_1_, GameProfile p_148542_2_)
/* 32:   */   {
/* 33:40 */     return (p_148542_2_.getName().equalsIgnoreCase(getServerInstance().getServerOwner())) && (getPlayerForUsername(p_148542_2_.getName()) != null) ? "That name is already taken." : super.func_148542_a(p_148542_1_, p_148542_2_);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public IntegratedServer getServerInstance()
/* 37:   */   {
/* 38:45 */     return (IntegratedServer)super.getServerInstance();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public NBTTagCompound getHostPlayerData()
/* 42:   */   {
/* 43:53 */     return this.hostPlayerData;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.integrated.IntegratedPlayerList
 * JD-Core Version:    0.7.0.1
 */