/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  9:   */ 
/* 10:   */ public class C0APacketAnimation
/* 11:   */   extends Packet
/* 12:   */ {
/* 13:   */   private int field_149424_a;
/* 14:   */   private int field_149423_b;
/* 15:   */   private static final String __OBFID = "CL_00001345";
/* 16:   */   
/* 17:   */   public C0APacketAnimation() {}
/* 18:   */   
/* 19:   */   public C0APacketAnimation(Entity p_i45238_1_, int p_i45238_2_)
/* 20:   */   {
/* 21:20 */     this.field_149424_a = p_i45238_1_.getEntityId();
/* 22:21 */     this.field_149423_b = p_i45238_2_;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 26:   */     throws IOException
/* 27:   */   {
/* 28:29 */     this.field_149424_a = p_148837_1_.readInt();
/* 29:30 */     this.field_149423_b = p_148837_1_.readByte();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:38 */     p_148840_1_.writeInt(this.field_149424_a);
/* 36:39 */     p_148840_1_.writeByte(this.field_149423_b);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void processPacket(INetHandlerPlayServer p_149422_1_)
/* 40:   */   {
/* 41:44 */     p_149422_1_.processAnimation(this);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String serialize()
/* 45:   */   {
/* 46:52 */     return String.format("id=%d, type=%d", new Object[] { Integer.valueOf(this.field_149424_a), Integer.valueOf(this.field_149423_b) });
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int func_149421_d()
/* 50:   */   {
/* 51:57 */     return this.field_149423_b;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void processPacket(INetHandler p_148833_1_)
/* 55:   */   {
/* 56:62 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C0APacketAnimation
 * JD-Core Version:    0.7.0.1
 */