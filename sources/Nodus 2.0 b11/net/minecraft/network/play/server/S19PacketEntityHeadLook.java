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
/* 11:   */ public class S19PacketEntityHeadLook
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:   */   private int field_149384_a;
/* 15:   */   private byte field_149383_b;
/* 16:   */   private static final String __OBFID = "CL_00001323";
/* 17:   */   
/* 18:   */   public S19PacketEntityHeadLook() {}
/* 19:   */   
/* 20:   */   public S19PacketEntityHeadLook(Entity p_i45214_1_, byte p_i45214_2_)
/* 21:   */   {
/* 22:21 */     this.field_149384_a = p_i45214_1_.getEntityId();
/* 23:22 */     this.field_149383_b = p_i45214_2_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 27:   */     throws IOException
/* 28:   */   {
/* 29:30 */     this.field_149384_a = p_148837_1_.readInt();
/* 30:31 */     this.field_149383_b = p_148837_1_.readByte();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:39 */     p_148840_1_.writeInt(this.field_149384_a);
/* 37:40 */     p_148840_1_.writeByte(this.field_149383_b);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void processPacket(INetHandlerPlayClient p_149382_1_)
/* 41:   */   {
/* 42:45 */     p_149382_1_.handleEntityHeadLook(this);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String serialize()
/* 46:   */   {
/* 47:53 */     return String.format("id=%d, rot=%d", new Object[] { Integer.valueOf(this.field_149384_a), Byte.valueOf(this.field_149383_b) });
/* 48:   */   }
/* 49:   */   
/* 50:   */   public Entity func_149381_a(World p_149381_1_)
/* 51:   */   {
/* 52:58 */     return p_149381_1_.getEntityByID(this.field_149384_a);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public byte func_149380_c()
/* 56:   */   {
/* 57:63 */     return this.field_149383_b;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void processPacket(INetHandler p_148833_1_)
/* 61:   */   {
/* 62:68 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S19PacketEntityHeadLook
 * JD-Core Version:    0.7.0.1
 */