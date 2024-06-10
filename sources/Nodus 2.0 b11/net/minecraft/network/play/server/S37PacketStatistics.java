/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Maps;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import java.util.Map;
/*  7:   */ import java.util.Map.Entry;
/*  8:   */ import java.util.Set;
/*  9:   */ import net.minecraft.network.INetHandler;
/* 10:   */ import net.minecraft.network.Packet;
/* 11:   */ import net.minecraft.network.PacketBuffer;
/* 12:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/* 13:   */ import net.minecraft.stats.StatBase;
/* 14:   */ import net.minecraft.stats.StatList;
/* 15:   */ 
/* 16:   */ public class S37PacketStatistics
/* 17:   */   extends Packet
/* 18:   */ {
/* 19:   */   private Map field_148976_a;
/* 20:   */   private static final String __OBFID = "CL_00001283";
/* 21:   */   
/* 22:   */   public S37PacketStatistics() {}
/* 23:   */   
/* 24:   */   public S37PacketStatistics(Map p_i45173_1_)
/* 25:   */   {
/* 26:24 */     this.field_148976_a = p_i45173_1_;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void processPacket(INetHandlerPlayClient p_148975_1_)
/* 30:   */   {
/* 31:29 */     p_148975_1_.handleStatistics(this);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 35:   */     throws IOException
/* 36:   */   {
/* 37:37 */     int var2 = p_148837_1_.readVarIntFromBuffer();
/* 38:38 */     this.field_148976_a = Maps.newHashMap();
/* 39:40 */     for (int var3 = 0; var3 < var2; var3++)
/* 40:   */     {
/* 41:42 */       StatBase var4 = StatList.func_151177_a(p_148837_1_.readStringFromBuffer(32767));
/* 42:43 */       int var5 = p_148837_1_.readVarIntFromBuffer();
/* 43:45 */       if (var4 != null) {
/* 44:47 */         this.field_148976_a.put(var4, Integer.valueOf(var5));
/* 45:   */       }
/* 46:   */     }
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 50:   */     throws IOException
/* 51:   */   {
/* 52:57 */     p_148840_1_.writeVarIntToBuffer(this.field_148976_a.size());
/* 53:58 */     Iterator var2 = this.field_148976_a.entrySet().iterator();
/* 54:60 */     while (var2.hasNext())
/* 55:   */     {
/* 56:62 */       Map.Entry var3 = (Map.Entry)var2.next();
/* 57:63 */       p_148840_1_.writeStringToBuffer(((StatBase)var3.getKey()).statId);
/* 58:64 */       p_148840_1_.writeVarIntToBuffer(((Integer)var3.getValue()).intValue());
/* 59:   */     }
/* 60:   */   }
/* 61:   */   
/* 62:   */   public String serialize()
/* 63:   */   {
/* 64:73 */     return String.format("count=%d", new Object[] { Integer.valueOf(this.field_148976_a.size()) });
/* 65:   */   }
/* 66:   */   
/* 67:   */   public Map func_148974_c()
/* 68:   */   {
/* 69:78 */     return this.field_148976_a;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void processPacket(INetHandler p_148833_1_)
/* 73:   */   {
/* 74:83 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S37PacketStatistics
 * JD-Core Version:    0.7.0.1
 */