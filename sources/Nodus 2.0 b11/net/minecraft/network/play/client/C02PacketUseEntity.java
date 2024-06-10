/*  1:   */ package net.minecraft.network.play.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.network.INetHandler;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.PacketBuffer;
/*  8:   */ import net.minecraft.network.play.INetHandlerPlayServer;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class C02PacketUseEntity
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:   */   private int field_149567_a;
/* 15:   */   private Action field_149566_b;
/* 16:   */   private static final String __OBFID = "CL_00001357";
/* 17:   */   
/* 18:   */   public C02PacketUseEntity() {}
/* 19:   */   
/* 20:   */   public C02PacketUseEntity(Entity p_i45251_1_, Action p_i45251_2_)
/* 21:   */   {
/* 22:21 */     this.field_149567_a = p_i45251_1_.getEntityId();
/* 23:22 */     this.field_149566_b = p_i45251_2_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 27:   */     throws IOException
/* 28:   */   {
/* 29:30 */     this.field_149567_a = p_148837_1_.readInt();
/* 30:31 */     this.field_149566_b = Action.field_151421_c[(p_148837_1_.readByte() % Action.field_151421_c.length)];
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:39 */     p_148840_1_.writeInt(this.field_149567_a);
/* 37:40 */     p_148840_1_.writeByte(this.field_149566_b.field_151418_d);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void processPacket(INetHandlerPlayServer p_149563_1_)
/* 41:   */   {
/* 42:45 */     p_149563_1_.processUseEntity(this);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Entity func_149564_a(World p_149564_1_)
/* 46:   */   {
/* 47:50 */     return p_149564_1_.getEntityByID(this.field_149567_a);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public Action func_149565_c()
/* 51:   */   {
/* 52:55 */     return this.field_149566_b;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void processPacket(INetHandler p_148833_1_)
/* 56:   */   {
/* 57:60 */     processPacket((INetHandlerPlayServer)p_148833_1_);
/* 58:   */   }
/* 59:   */   
/* 60:   */   public static enum Action
/* 61:   */   {
/* 62:65 */     INTERACT("INTERACT", 0, 0),  ATTACK("ATTACK", 1, 1);
/* 63:   */     
/* 64:   */     private static final Action[] field_151421_c;
/* 65:   */     private final int field_151418_d;
/* 66:   */     private static final Action[] $VALUES;
/* 67:   */     private static final String __OBFID = "CL_00001358";
/* 68:   */     
/* 69:   */     private Action(String p_i45250_1_, int p_i45250_2_, int p_i45250_3_)
/* 70:   */     {
/* 71:75 */       this.field_151418_d = p_i45250_3_;
/* 72:   */     }
/* 73:   */     
/* 74:   */     static
/* 75:   */     {
/* 76:67 */       field_151421_c = new Action[values().length];
/* 77:   */       
/* 78:   */ 
/* 79:70 */       $VALUES = new Action[] { INTERACT, ATTACK };
/* 80:   */       
/* 81:   */ 
/* 82:   */ 
/* 83:   */ 
/* 84:   */ 
/* 85:   */ 
/* 86:   */ 
/* 87:   */ 
/* 88:79 */       Action[] var0 = values();
/* 89:80 */       int var1 = var0.length;
/* 90:82 */       for (int var2 = 0; var2 < var1; var2++)
/* 91:   */       {
/* 92:84 */         Action var3 = var0[var2];
/* 93:85 */         field_151421_c[var3.field_151418_d] = var3;
/* 94:   */       }
/* 95:   */     }
/* 96:   */   }
/* 97:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.client.C02PacketUseEntity
 * JD-Core Version:    0.7.0.1
 */