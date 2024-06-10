/*   1:    */ package net.minecraft.client.multiplayer;
/*   2:    */ 
/*   3:    */ import net.minecraft.nbt.NBTTagCompound;
/*   4:    */ 
/*   5:    */ public class ServerData
/*   6:    */ {
/*   7:    */   public String serverName;
/*   8:    */   public String serverIP;
/*   9:    */   public String populationInfo;
/*  10:    */   public String serverMOTD;
/*  11:    */   public long pingToServer;
/*  12: 24 */   public int field_82821_f = 4;
/*  13: 27 */   public String gameVersion = "1.7.2";
/*  14:    */   public boolean field_78841_f;
/*  15:    */   public String field_147412_i;
/*  16: 30 */   private boolean field_78842_g = true;
/*  17:    */   private boolean acceptsTextures;
/*  18:    */   private boolean hideAddress;
/*  19:    */   private String field_147411_m;
/*  20:    */   private static final String __OBFID = "CL_00000890";
/*  21:    */   
/*  22:    */   public ServerData(String par1Str, String par2Str)
/*  23:    */   {
/*  24: 40 */     this.serverName = par1Str;
/*  25: 41 */     this.serverIP = par2Str;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public NBTTagCompound getNBTCompound()
/*  29:    */   {
/*  30: 49 */     NBTTagCompound var1 = new NBTTagCompound();
/*  31: 50 */     var1.setString("name", this.serverName);
/*  32: 51 */     var1.setString("ip", this.serverIP);
/*  33: 52 */     var1.setBoolean("hideAddress", this.hideAddress);
/*  34: 54 */     if (this.field_147411_m != null) {
/*  35: 56 */       var1.setString("icon", this.field_147411_m);
/*  36:    */     }
/*  37: 59 */     if (!this.field_78842_g) {
/*  38: 61 */       var1.setBoolean("acceptTextures", this.acceptsTextures);
/*  39:    */     }
/*  40: 64 */     return var1;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean func_147408_b()
/*  44:    */   {
/*  45: 69 */     return this.acceptsTextures;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean func_147410_c()
/*  49:    */   {
/*  50: 74 */     return this.field_78842_g;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setAcceptsTextures(boolean par1)
/*  54:    */   {
/*  55: 79 */     this.acceptsTextures = par1;
/*  56: 80 */     this.field_78842_g = false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isHidingAddress()
/*  60:    */   {
/*  61: 85 */     return this.hideAddress;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setHideAddress(boolean par1)
/*  65:    */   {
/*  66: 90 */     this.hideAddress = par1;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static ServerData getServerDataFromNBTCompound(NBTTagCompound par0NBTTagCompound)
/*  70:    */   {
/*  71: 98 */     ServerData var1 = new ServerData(par0NBTTagCompound.getString("name"), par0NBTTagCompound.getString("ip"));
/*  72: 99 */     var1.hideAddress = par0NBTTagCompound.getBoolean("hideAddress");
/*  73:101 */     if (par0NBTTagCompound.func_150297_b("icon", 8)) {
/*  74:103 */       var1.func_147407_a(par0NBTTagCompound.getString("icon"));
/*  75:    */     }
/*  76:106 */     if (par0NBTTagCompound.func_150297_b("acceptTextures", 99)) {
/*  77:108 */       var1.setAcceptsTextures(par0NBTTagCompound.getBoolean("acceptTextures"));
/*  78:    */     }
/*  79:111 */     return var1;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String func_147409_e()
/*  83:    */   {
/*  84:116 */     return this.field_147411_m;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void func_147407_a(String p_147407_1_)
/*  88:    */   {
/*  89:121 */     this.field_147411_m = p_147407_1_;
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.multiplayer.ServerData
 * JD-Core Version:    0.7.0.1
 */