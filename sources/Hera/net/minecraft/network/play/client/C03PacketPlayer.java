/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ 
/*     */ 
/*     */ public class C03PacketPlayer
/*     */   implements Packet<INetHandlerPlayServer>
/*     */ {
/*     */   public static double x;
/*     */   public static double y;
/*     */   public static double z;
/*     */   public static float yaw;
/*     */   public static float pitch;
/*     */   public static boolean onGround;
/*     */   protected boolean moving;
/*     */   protected boolean rotating;
/*     */   
/*     */   public C03PacketPlayer() {}
/*     */   
/*     */   public C03PacketPlayer(boolean isOnGround) {
/*  25 */     onGround = isOnGround;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  33 */     handler.processPlayer(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  41 */     onGround = (buf.readUnsignedByte() != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  49 */     buf.writeByte(onGround ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getPositionX() {
/*  54 */     return x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getPositionY() {
/*  59 */     return y;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getPositionZ() {
/*  64 */     return z;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getYaw() {
/*  69 */     return yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPitch() {
/*  74 */     return pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnGround() {
/*  79 */     return onGround;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMoving() {
/*  84 */     return this.moving;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getRotating() {
/*  89 */     return this.rotating;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMoving(boolean isMoving) {
/*  94 */     this.moving = isMoving;
/*     */   }
/*     */   
/*     */   public static class C04PacketPlayerPosition
/*     */     extends C03PacketPlayer
/*     */   {
/*     */     public C04PacketPlayerPosition() {
/* 101 */       this.moving = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public C04PacketPlayerPosition(double posX, double posY, double posZ, boolean isOnGround) {
/* 106 */       x = posX;
/* 107 */       y = posY;
/* 108 */       z = posZ;
/* 109 */       onGround = isOnGround;
/* 110 */       this.moving = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 115 */       x = buf.readDouble();
/* 116 */       y = buf.readDouble();
/* 117 */       z = buf.readDouble();
/* 118 */       super.readPacketData(buf);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 123 */       buf.writeDouble(x);
/* 124 */       buf.writeDouble(y);
/* 125 */       buf.writeDouble(z);
/* 126 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class C05PacketPlayerLook
/*     */     extends C03PacketPlayer
/*     */   {
/*     */     public C05PacketPlayerLook() {
/* 134 */       this.rotating = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public C05PacketPlayerLook(float playerYaw, float playerPitch, boolean isOnGround) {
/* 139 */       yaw = playerYaw;
/* 140 */       pitch = playerPitch;
/* 141 */       onGround = isOnGround;
/* 142 */       this.rotating = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 147 */       yaw = buf.readFloat();
/* 148 */       pitch = buf.readFloat();
/* 149 */       super.readPacketData(buf);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 154 */       buf.writeFloat(yaw);
/* 155 */       buf.writeFloat(pitch);
/* 156 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class C06PacketPlayerPosLook
/*     */     extends C03PacketPlayer
/*     */   {
/*     */     public C06PacketPlayerPosLook() {
/* 164 */       this.moving = true;
/* 165 */       this.rotating = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public C06PacketPlayerPosLook(double playerX, double playerY, double playerZ, float playerYaw, float playerPitch, boolean playerIsOnGround) {
/* 170 */       x = playerX;
/* 171 */       y = playerY;
/* 172 */       z = playerZ;
/* 173 */       yaw = playerYaw;
/* 174 */       pitch = playerPitch;
/* 175 */       onGround = playerIsOnGround;
/* 176 */       this.rotating = true;
/* 177 */       this.moving = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 182 */       x = buf.readDouble();
/* 183 */       y = buf.readDouble();
/* 184 */       z = buf.readDouble();
/* 185 */       yaw = buf.readFloat();
/* 186 */       pitch = buf.readFloat();
/* 187 */       super.readPacketData(buf);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 192 */       buf.writeDouble(x);
/* 193 */       buf.writeDouble(y);
/* 194 */       buf.writeDouble(z);
/* 195 */       buf.writeFloat(yaw);
/* 196 */       buf.writeFloat(pitch);
/* 197 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\play\client\C03PacketPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */