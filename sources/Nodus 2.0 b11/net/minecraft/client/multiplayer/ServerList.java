/*   1:    */ package net.minecraft.client.multiplayer;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.nbt.CompressedStreamTools;
/*   9:    */ import net.minecraft.nbt.NBTTagCompound;
/*  10:    */ import net.minecraft.nbt.NBTTagList;
/*  11:    */ import org.apache.logging.log4j.LogManager;
/*  12:    */ import org.apache.logging.log4j.Logger;
/*  13:    */ 
/*  14:    */ public class ServerList
/*  15:    */ {
/*  16: 16 */   private static final Logger logger = ;
/*  17:    */   private final Minecraft mc;
/*  18: 22 */   private final List servers = new ArrayList();
/*  19:    */   private static final String __OBFID = "CL_00000891";
/*  20:    */   
/*  21:    */   public ServerList(Minecraft par1Minecraft)
/*  22:    */   {
/*  23: 27 */     this.mc = par1Minecraft;
/*  24: 28 */     loadServerList();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void loadServerList()
/*  28:    */   {
/*  29:    */     try
/*  30:    */     {
/*  31: 39 */       this.servers.clear();
/*  32: 40 */       NBTTagCompound var1 = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));
/*  33: 42 */       if (var1 == null) {
/*  34: 44 */         return;
/*  35:    */       }
/*  36: 47 */       NBTTagList var2 = var1.getTagList("servers", 10);
/*  37: 49 */       for (int var3 = 0; var3 < var2.tagCount(); var3++) {
/*  38: 51 */         this.servers.add(ServerData.getServerDataFromNBTCompound(var2.getCompoundTagAt(var3)));
/*  39:    */       }
/*  40:    */     }
/*  41:    */     catch (Exception var4)
/*  42:    */     {
/*  43: 56 */       logger.error("Couldn't load server list", var4);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void saveServerList()
/*  48:    */   {
/*  49:    */     try
/*  50:    */     {
/*  51: 68 */       NBTTagList var1 = new NBTTagList();
/*  52: 69 */       Iterator var2 = this.servers.iterator();
/*  53: 71 */       while (var2.hasNext())
/*  54:    */       {
/*  55: 73 */         ServerData var3 = (ServerData)var2.next();
/*  56: 74 */         var1.appendTag(var3.getNBTCompound());
/*  57:    */       }
/*  58: 77 */       NBTTagCompound var5 = new NBTTagCompound();
/*  59: 78 */       var5.setTag("servers", var1);
/*  60: 79 */       CompressedStreamTools.safeWrite(var5, new File(this.mc.mcDataDir, "servers.dat"));
/*  61:    */     }
/*  62:    */     catch (Exception var4)
/*  63:    */     {
/*  64: 83 */       logger.error("Couldn't save server list", var4);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ServerData getServerData(int par1)
/*  69:    */   {
/*  70: 92 */     return (ServerData)this.servers.get(par1);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void removeServerData(int par1)
/*  74:    */   {
/*  75:100 */     this.servers.remove(par1);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void addServerData(ServerData par1ServerData)
/*  79:    */   {
/*  80:108 */     this.servers.add(par1ServerData);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int countServers()
/*  84:    */   {
/*  85:116 */     return this.servers.size();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void swapServers(int par1, int par2)
/*  89:    */   {
/*  90:124 */     ServerData var3 = getServerData(par1);
/*  91:125 */     this.servers.set(par1, getServerData(par2));
/*  92:126 */     this.servers.set(par2, var3);
/*  93:127 */     saveServerList();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void func_147413_a(int p_147413_1_, ServerData p_147413_2_)
/*  97:    */   {
/*  98:132 */     this.servers.set(p_147413_1_, p_147413_2_);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void func_147414_b(ServerData p_147414_0_)
/* 102:    */   {
/* 103:137 */     ServerList var1 = new ServerList(Minecraft.getMinecraft());
/* 104:138 */     var1.loadServerList();
/* 105:140 */     for (int var2 = 0; var2 < var1.countServers(); var2++)
/* 106:    */     {
/* 107:142 */       ServerData var3 = var1.getServerData(var2);
/* 108:144 */       if ((var3.serverName.equals(p_147414_0_.serverName)) && (var3.serverIP.equals(p_147414_0_.serverIP)))
/* 109:    */       {
/* 110:146 */         var1.func_147413_a(var2, p_147414_0_);
/* 111:147 */         break;
/* 112:    */       }
/* 113:    */     }
/* 114:151 */     var1.saveServerList();
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.multiplayer.ServerList
 * JD-Core Version:    0.7.0.1
 */