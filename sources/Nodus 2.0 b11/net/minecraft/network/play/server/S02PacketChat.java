/*  1:   */ package net.minecraft.network.play.server;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import net.minecraft.network.INetHandler;
/*  5:   */ import net.minecraft.network.Packet;
/*  6:   */ import net.minecraft.network.PacketBuffer;
/*  7:   */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  8:   */ import net.minecraft.util.IChatComponent;
/*  9:   */ import net.minecraft.util.IChatComponent.Serializer;
/* 10:   */ 
/* 11:   */ public class S02PacketChat
/* 12:   */   extends Packet
/* 13:   */ {
/* 14:   */   private IChatComponent field_148919_a;
/* 15:   */   private boolean field_148918_b;
/* 16:   */   private static final String __OBFID = "CL_00001289";
/* 17:   */   
/* 18:   */   public S02PacketChat()
/* 19:   */   {
/* 20:18 */     this.field_148918_b = true;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public S02PacketChat(IChatComponent p_i45179_1_)
/* 24:   */   {
/* 25:23 */     this(p_i45179_1_, true);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public S02PacketChat(IChatComponent p_i45180_1_, boolean p_i45180_2_)
/* 29:   */   {
/* 30:28 */     this.field_148918_b = true;
/* 31:29 */     this.field_148919_a = p_i45180_1_;
/* 32:30 */     this.field_148918_b = p_i45180_2_;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void readPacketData(PacketBuffer p_148837_1_)
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:38 */     this.field_148919_a = IChatComponent.Serializer.func_150699_a(p_148837_1_.readStringFromBuffer(32767));
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void writePacketData(PacketBuffer p_148840_1_)
/* 42:   */     throws IOException
/* 43:   */   {
/* 44:46 */     p_148840_1_.writeStringToBuffer(IChatComponent.Serializer.func_150696_a(this.field_148919_a));
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void processPacket(INetHandlerPlayClient p_148917_1_)
/* 48:   */   {
/* 49:51 */     p_148917_1_.handleChat(this);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String serialize()
/* 53:   */   {
/* 54:59 */     return String.format("message='%s'", new Object[] { this.field_148919_a });
/* 55:   */   }
/* 56:   */   
/* 57:   */   public IChatComponent func_148915_c()
/* 58:   */   {
/* 59:64 */     return this.field_148919_a;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public boolean func_148916_d()
/* 63:   */   {
/* 64:69 */     return this.field_148918_b;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void processPacket(INetHandler p_148833_1_)
/* 68:   */   {
/* 69:74 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S02PacketChat
 * JD-Core Version:    0.7.0.1
 */