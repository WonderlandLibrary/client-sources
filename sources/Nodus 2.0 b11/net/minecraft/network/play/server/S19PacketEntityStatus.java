/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class S19PacketEntityStatus
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:   */   private int field_149164_a;
/* 15:   */   private byte field_149163_b;
/* 16:   */   private static final String __OBFID = "CL_00001299";
/* 17:   */   
/* 18:   */   public S19PacketEntityStatus() {}
/* 19:   */   
/* 20:   */   public S19PacketEntityStatus(Entity p_i45192_1_, byte p_i45192_2_)
/* 21:   */   {
/* 22:21 */     this.field_149164_a = p_i45192_1_.getEntityId();
/* 23:22 */     this.field_149163_b = p_i45192_2_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 27:   */     throws IOException
/* 28:   */   {
/* 29:30 */     this.field_149164_a = p_148837_1_.readInt();
/* 30:31 */     this.field_149163_b = p_148837_1_.readByte();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:39 */     p_148840_1_.writeInt(this.field_149164_a);
/* 37:40 */     p_148840_1_.writeByte(this.field_149163_b);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void processPacket(INetHandlerPlayClient p_149162_1_)
/* 41:   */   {
/* 42:45 */     p_149162_1_.handleEntityStatus(this);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Entity func_149161_a(World p_149161_1_)
/* 46:   */   {
/* 47:50 */     return p_149161_1_.getEntityByID(this.field_149164_a);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public byte func_149160_c()
/* 51:   */   {
/* 52:55 */     return this.field_149163_b;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void processPacket(INetHandler p_148833_1_)
/* 56:   */   {
/* 57:60 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S19PacketEntityStatus
 * JD-Core Version:    0.7.0.1
 */