/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.entity.DataWatcher;
/*  6:   */ import net.minecraft.network.INetHandler;
/*  7:   */ import net.minecraft.network.Packet;
/*  8:   */ import net.minecraft.network.PacketBuffer;
/*  9:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/* 10:   */ 
/* 11:   */ public class S1CPacketEntityMetadata
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:   */   private int field_149379_a;
/* 15:   */   private List field_149378_b;
/* 16:   */   private static final String __OBFID = "CL_00001326";
/* 17:   */   
/* 18:   */   public S1CPacketEntityMetadata() {}
/* 19:   */   
/* 20:   */   public S1CPacketEntityMetadata(int p_i45217_1_, DataWatcher p_i45217_2_, boolean p_i45217_3_)
/* 21:   */   {
/* 22:21 */     this.field_149379_a = p_i45217_1_;
/* 23:23 */     if (p_i45217_3_) {
/* 24:25 */       this.field_149378_b = p_i45217_2_.getAllWatched();
/* 25:   */     } else {
/* 26:29 */       this.field_149378_b = p_i45217_2_.getChanged();
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:38 */     this.field_149379_a = p_148837_1_.readInt();
/* 34:39 */     this.field_149378_b = DataWatcher.readWatchedListFromPacketBuffer(p_148837_1_);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 38:   */     throws IOException
/* 39:   */   {
/* 40:47 */     p_148840_1_.writeInt(this.field_149379_a);
/* 41:48 */     DataWatcher.writeWatchedListToPacketBuffer(this.field_149378_b, p_148840_1_);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void processPacket(INetHandlerPlayClient p_149377_1_)
/* 45:   */   {
/* 46:53 */     p_149377_1_.handleEntityMetadata(this);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public List func_149376_c()
/* 50:   */   {
/* 51:58 */     return this.field_149378_b;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public int func_149375_d()
/* 55:   */   {
/* 56:63 */     return this.field_149379_a;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void processPacket(INetHandler p_148833_1_)
/* 60:   */   {
/* 61:68 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S1CPacketEntityMetadata
 * JD-Core Version:    0.7.0.1
 */