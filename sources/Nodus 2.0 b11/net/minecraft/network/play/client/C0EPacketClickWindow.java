/*   1:    */ package net.minecraft.network.play.client;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import net.minecraft.item.Item;
/*   5:    */ import net.minecraft.item.ItemStack;
/*   6:    */ import net.minecraft.network.INetHandler;
/*   7:    */ import net.minecraft.network.Packet;
/*   8:    */ import net.minecraft.network.PacketBuffer;
/*   9:    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  10:    */ 
/*  11:    */ public class C0EPacketClickWindow
/*  12:    */   extends Packet
/*  13:    */ {
/*  14:    */   private int field_149554_a;
/*  15:    */   private int field_149552_b;
/*  16:    */   private int field_149553_c;
/*  17:    */   private short field_149550_d;
/*  18:    */   private ItemStack field_149551_e;
/*  19:    */   private int field_149549_f;
/*  20:    */   private static final String __OBFID = "CL_00001353";
/*  21:    */   
/*  22:    */   public C0EPacketClickWindow() {}
/*  23:    */   
/*  24:    */   public C0EPacketClickWindow(int p_i45246_1_, int p_i45246_2_, int p_i45246_3_, int p_i45246_4_, ItemStack p_i45246_5_, short p_i45246_6_)
/*  25:    */   {
/*  26: 25 */     this.field_149554_a = p_i45246_1_;
/*  27: 26 */     this.field_149552_b = p_i45246_2_;
/*  28: 27 */     this.field_149553_c = p_i45246_3_;
/*  29: 28 */     this.field_149551_e = (p_i45246_5_ != null ? p_i45246_5_.copy() : null);
/*  30: 29 */     this.field_149550_d = p_i45246_6_;
/*  31: 30 */     this.field_149549_f = p_i45246_4_;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void processPacket(INetHandlerPlayServer p_149545_1_)
/*  35:    */   {
/*  36: 35 */     p_149545_1_.processClickWindow(this);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 43 */     this.field_149554_a = p_148837_1_.readByte();
/*  43: 44 */     this.field_149552_b = p_148837_1_.readShort();
/*  44: 45 */     this.field_149553_c = p_148837_1_.readByte();
/*  45: 46 */     this.field_149550_d = p_148837_1_.readShort();
/*  46: 47 */     this.field_149549_f = p_148837_1_.readByte();
/*  47: 48 */     this.field_149551_e = p_148837_1_.readItemStackFromBuffer();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53: 56 */     p_148840_1_.writeByte(this.field_149554_a);
/*  54: 57 */     p_148840_1_.writeShort(this.field_149552_b);
/*  55: 58 */     p_148840_1_.writeByte(this.field_149553_c);
/*  56: 59 */     p_148840_1_.writeShort(this.field_149550_d);
/*  57: 60 */     p_148840_1_.writeByte(this.field_149549_f);
/*  58: 61 */     p_148840_1_.writeItemStackToBuffer(this.field_149551_e);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String serialize()
/*  62:    */   {
/*  63: 69 */     return this.field_149551_e != null ? String.format("id=%d, slot=%d, button=%d, type=%d, itemid=%d, itemcount=%d, itemaux=%d", new Object[] { Integer.valueOf(this.field_149554_a), Integer.valueOf(this.field_149552_b), Integer.valueOf(this.field_149553_c), Integer.valueOf(this.field_149549_f), Integer.valueOf(Item.getIdFromItem(this.field_149551_e.getItem())), Integer.valueOf(this.field_149551_e.stackSize), Integer.valueOf(this.field_149551_e.getItemDamage()) }) : String.format("id=%d, slot=%d, button=%d, type=%d, itemid=-1", new Object[] { Integer.valueOf(this.field_149554_a), Integer.valueOf(this.field_149552_b), Integer.valueOf(this.field_149553_c), Integer.valueOf(this.field_149549_f) });
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int func_149548_c()
/*  67:    */   {
/*  68: 74 */     return this.field_149554_a;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int func_149544_d()
/*  72:    */   {
/*  73: 79 */     return this.field_149552_b;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int func_149543_e()
/*  77:    */   {
/*  78: 84 */     return this.field_149553_c;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public short func_149547_f()
/*  82:    */   {
/*  83: 89 */     return this.field_149550_d;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public ItemStack func_149546_g()
/*  87:    */   {
/*  88: 94 */     return this.field_149551_e;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int func_149542_h()
/*  92:    */   {
/*  93: 99 */     return this.field_149549_f;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void processPacket(INetHandler p_148833_1_)
/*  97:    */   {
/*  98:104 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C0EPacketClickWindow
 * JD-Core Version:    0.7.0.1
 */