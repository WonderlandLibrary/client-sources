/*   1:    */ package net.minecraft.network.play.server;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.network.INetHandler;
/*   8:    */ import net.minecraft.network.Packet;
/*   9:    */ import net.minecraft.network.PacketBuffer;
/*  10:    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  11:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  12:    */ import net.minecraft.world.chunk.Chunk;
/*  13:    */ import org.apache.logging.log4j.LogManager;
/*  14:    */ import org.apache.logging.log4j.Logger;
/*  15:    */ 
/*  16:    */ public class S22PacketMultiBlockChange
/*  17:    */   extends Packet
/*  18:    */ {
/*  19: 18 */   private static final Logger logger = ;
/*  20:    */   private ChunkCoordIntPair field_148925_b;
/*  21:    */   private byte[] field_148926_c;
/*  22:    */   private int field_148924_d;
/*  23:    */   private static final String __OBFID = "CL_00001290";
/*  24:    */   
/*  25:    */   public S22PacketMultiBlockChange() {}
/*  26:    */   
/*  27:    */   public S22PacketMultiBlockChange(int p_i45181_1_, short[] p_i45181_2_, Chunk p_i45181_3_)
/*  28:    */   {
/*  29: 28 */     this.field_148925_b = new ChunkCoordIntPair(p_i45181_3_.xPosition, p_i45181_3_.zPosition);
/*  30: 29 */     this.field_148924_d = p_i45181_1_;
/*  31: 30 */     int var4 = 4 * p_i45181_1_;
/*  32:    */     try
/*  33:    */     {
/*  34: 34 */       ByteArrayOutputStream var5 = new ByteArrayOutputStream(var4);
/*  35: 35 */       DataOutputStream var6 = new DataOutputStream(var5);
/*  36: 37 */       for (int var7 = 0; var7 < p_i45181_1_; var7++)
/*  37:    */       {
/*  38: 39 */         int var8 = p_i45181_2_[var7] >> 12 & 0xF;
/*  39: 40 */         int var9 = p_i45181_2_[var7] >> 8 & 0xF;
/*  40: 41 */         int var10 = p_i45181_2_[var7] & 0xFF;
/*  41: 42 */         var6.writeShort(p_i45181_2_[var7]);
/*  42: 43 */         var6.writeShort((short)((Block.getIdFromBlock(p_i45181_3_.func_150810_a(var8, var10, var9)) & 0xFFF) << 4 | p_i45181_3_.getBlockMetadata(var8, var10, var9) & 0xF));
/*  43:    */       }
/*  44: 46 */       this.field_148926_c = var5.toByteArray();
/*  45: 48 */       if (this.field_148926_c.length != var4) {
/*  46: 50 */         throw new RuntimeException("Expected length " + var4 + " doesn't match received length " + this.field_148926_c.length);
/*  47:    */       }
/*  48:    */     }
/*  49:    */     catch (IOException var11)
/*  50:    */     {
/*  51: 55 */       logger.error("Couldn't create bulk block update packet", var11);
/*  52: 56 */       this.field_148926_c = null;
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void readPacketData(PacketBuffer p_148837_1_)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59: 65 */     this.field_148925_b = new ChunkCoordIntPair(p_148837_1_.readInt(), p_148837_1_.readInt());
/*  60: 66 */     this.field_148924_d = (p_148837_1_.readShort() & 0xFFFF);
/*  61: 67 */     int var2 = p_148837_1_.readInt();
/*  62: 69 */     if (var2 > 0)
/*  63:    */     {
/*  64: 71 */       this.field_148926_c = new byte[var2];
/*  65: 72 */       p_148837_1_.readBytes(this.field_148926_c);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void writePacketData(PacketBuffer p_148840_1_)
/*  70:    */     throws IOException
/*  71:    */   {
/*  72: 81 */     p_148840_1_.writeInt(this.field_148925_b.chunkXPos);
/*  73: 82 */     p_148840_1_.writeInt(this.field_148925_b.chunkZPos);
/*  74: 83 */     p_148840_1_.writeShort((short)this.field_148924_d);
/*  75: 85 */     if (this.field_148926_c != null)
/*  76:    */     {
/*  77: 87 */       p_148840_1_.writeInt(this.field_148926_c.length);
/*  78: 88 */       p_148840_1_.writeBytes(this.field_148926_c);
/*  79:    */     }
/*  80:    */     else
/*  81:    */     {
/*  82: 92 */       p_148840_1_.writeInt(0);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void processPacket(INetHandlerPlayClient p_148923_1_)
/*  87:    */   {
/*  88: 98 */     p_148923_1_.handleMultiBlockChange(this);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String serialize()
/*  92:    */   {
/*  93:106 */     return String.format("xc=%d, zc=%d, count=%d", new Object[] { Integer.valueOf(this.field_148925_b.chunkXPos), Integer.valueOf(this.field_148925_b.chunkZPos), Integer.valueOf(this.field_148924_d) });
/*  94:    */   }
/*  95:    */   
/*  96:    */   public ChunkCoordIntPair func_148920_c()
/*  97:    */   {
/*  98:111 */     return this.field_148925_b;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public byte[] func_148921_d()
/* 102:    */   {
/* 103:116 */     return this.field_148926_c;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int func_148922_e()
/* 107:    */   {
/* 108:121 */     return this.field_148924_d;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void processPacket(INetHandler p_148833_1_)
/* 112:    */   {
/* 113:126 */     processPacket((INetHandlerPlayClient)p_148833_1_);
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.play.server.S22PacketMultiBlockChange
 * JD-Core Version:    0.7.0.1
 */