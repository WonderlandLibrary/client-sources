/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Lists;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ import net.minecraft.client.multiplayer.ServerList;
/*  8:   */ import net.minecraft.client.network.LanServerDetector.LanServer;
/*  9:   */ 
/* 10:   */ public class ServerSelectionList
/* 11:   */   extends GuiListExtended
/* 12:   */ {
/* 13:   */   private final GuiMultiplayer field_148200_k;
/* 14:13 */   private final List field_148198_l = Lists.newArrayList();
/* 15:14 */   private final List field_148199_m = Lists.newArrayList();
/* 16:15 */   private final GuiListExtended.IGuiListEntry field_148196_n = new ServerListEntryLanScan();
/* 17:16 */   private int field_148197_o = -1;
/* 18:   */   private static final String __OBFID = "CL_00000819";
/* 19:   */   
/* 20:   */   public ServerSelectionList(GuiMultiplayer p_i45049_1_, Minecraft p_i45049_2_, int p_i45049_3_, int p_i45049_4_, int p_i45049_5_, int p_i45049_6_, int p_i45049_7_)
/* 21:   */   {
/* 22:21 */     super(p_i45049_2_, p_i45049_3_, p_i45049_4_, p_i45049_5_, p_i45049_6_, p_i45049_7_);
/* 23:22 */     this.field_148200_k = p_i45049_1_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public GuiListExtended.IGuiListEntry func_148180_b(int p_148180_1_)
/* 27:   */   {
/* 28:27 */     if (p_148180_1_ < this.field_148198_l.size()) {
/* 29:29 */       return (GuiListExtended.IGuiListEntry)this.field_148198_l.get(p_148180_1_);
/* 30:   */     }
/* 31:33 */     p_148180_1_ -= this.field_148198_l.size();
/* 32:35 */     if (p_148180_1_ == 0) {
/* 33:37 */       return this.field_148196_n;
/* 34:   */     }
/* 35:41 */     p_148180_1_--;
/* 36:42 */     return (GuiListExtended.IGuiListEntry)this.field_148199_m.get(p_148180_1_);
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected int getSize()
/* 40:   */   {
/* 41:49 */     return this.field_148198_l.size() + 1 + this.field_148199_m.size();
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void func_148192_c(int p_148192_1_)
/* 45:   */   {
/* 46:54 */     this.field_148197_o = p_148192_1_;
/* 47:   */   }
/* 48:   */   
/* 49:   */   protected boolean isSelected(int p_148131_1_)
/* 50:   */   {
/* 51:59 */     return p_148131_1_ == this.field_148197_o;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public int func_148193_k()
/* 55:   */   {
/* 56:64 */     return this.field_148197_o;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void func_148195_a(ServerList p_148195_1_)
/* 60:   */   {
/* 61:69 */     this.field_148198_l.clear();
/* 62:71 */     for (int var2 = 0; var2 < p_148195_1_.countServers(); var2++) {
/* 63:73 */       this.field_148198_l.add(new ServerListEntryNormal(this.field_148200_k, p_148195_1_.getServerData(var2)));
/* 64:   */     }
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void func_148194_a(List p_148194_1_)
/* 68:   */   {
/* 69:79 */     this.field_148199_m.clear();
/* 70:80 */     Iterator var2 = p_148194_1_.iterator();
/* 71:82 */     while (var2.hasNext())
/* 72:   */     {
/* 73:84 */       LanServerDetector.LanServer var3 = (LanServerDetector.LanServer)var2.next();
/* 74:85 */       this.field_148199_m.add(new ServerListEntryLanDetected(this.field_148200_k, var3));
/* 75:   */     }
/* 76:   */   }
/* 77:   */   
/* 78:   */   protected int func_148137_d()
/* 79:   */   {
/* 80:91 */     return super.func_148137_d() + 30;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public int func_148139_c()
/* 84:   */   {
/* 85:96 */     return super.func_148139_c() + 85;
/* 86:   */   }
/* 87:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.ServerSelectionList
 * JD-Core Version:    0.7.0.1
 */