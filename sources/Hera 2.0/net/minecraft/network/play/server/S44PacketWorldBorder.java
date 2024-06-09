/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ 
/*     */ 
/*     */ public class S44PacketWorldBorder
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private Action action;
/*     */   private int size;
/*     */   private double centerX;
/*     */   private double centerZ;
/*     */   private double targetSize;
/*     */   private double diameter;
/*     */   private long timeUntilTarget;
/*     */   private int warningTime;
/*     */   private int warningDistance;
/*     */   
/*     */   public S44PacketWorldBorder() {}
/*     */   
/*     */   public S44PacketWorldBorder(WorldBorder border, Action actionIn) {
/*  27 */     this.action = actionIn;
/*  28 */     this.centerX = border.getCenterX();
/*  29 */     this.centerZ = border.getCenterZ();
/*  30 */     this.diameter = border.getDiameter();
/*  31 */     this.targetSize = border.getTargetSize();
/*  32 */     this.timeUntilTarget = border.getTimeUntilTarget();
/*  33 */     this.size = border.getSize();
/*  34 */     this.warningDistance = border.getWarningDistance();
/*  35 */     this.warningTime = border.getWarningTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  43 */     this.action = (Action)buf.readEnumValue(Action.class);
/*     */     
/*  45 */     switch (this.action) {
/*     */       
/*     */       case SET_SIZE:
/*  48 */         this.targetSize = buf.readDouble();
/*     */         break;
/*     */       
/*     */       case LERP_SIZE:
/*  52 */         this.diameter = buf.readDouble();
/*  53 */         this.targetSize = buf.readDouble();
/*  54 */         this.timeUntilTarget = buf.readVarLong();
/*     */         break;
/*     */       
/*     */       case SET_CENTER:
/*  58 */         this.centerX = buf.readDouble();
/*  59 */         this.centerZ = buf.readDouble();
/*     */         break;
/*     */       
/*     */       case SET_WARNING_BLOCKS:
/*  63 */         this.warningDistance = buf.readVarIntFromBuffer();
/*     */         break;
/*     */       
/*     */       case SET_WARNING_TIME:
/*  67 */         this.warningTime = buf.readVarIntFromBuffer();
/*     */         break;
/*     */       
/*     */       case null:
/*  71 */         this.centerX = buf.readDouble();
/*  72 */         this.centerZ = buf.readDouble();
/*  73 */         this.diameter = buf.readDouble();
/*  74 */         this.targetSize = buf.readDouble();
/*  75 */         this.timeUntilTarget = buf.readVarLong();
/*  76 */         this.size = buf.readVarIntFromBuffer();
/*  77 */         this.warningDistance = buf.readVarIntFromBuffer();
/*  78 */         this.warningTime = buf.readVarIntFromBuffer();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  87 */     buf.writeEnumValue(this.action);
/*     */     
/*  89 */     switch (this.action) {
/*     */       
/*     */       case SET_SIZE:
/*  92 */         buf.writeDouble(this.targetSize);
/*     */         break;
/*     */       
/*     */       case LERP_SIZE:
/*  96 */         buf.writeDouble(this.diameter);
/*  97 */         buf.writeDouble(this.targetSize);
/*  98 */         buf.writeVarLong(this.timeUntilTarget);
/*     */         break;
/*     */       
/*     */       case SET_CENTER:
/* 102 */         buf.writeDouble(this.centerX);
/* 103 */         buf.writeDouble(this.centerZ);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_BLOCKS:
/* 107 */         buf.writeVarIntToBuffer(this.warningDistance);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_TIME:
/* 111 */         buf.writeVarIntToBuffer(this.warningTime);
/*     */         break;
/*     */       
/*     */       case null:
/* 115 */         buf.writeDouble(this.centerX);
/* 116 */         buf.writeDouble(this.centerZ);
/* 117 */         buf.writeDouble(this.diameter);
/* 118 */         buf.writeDouble(this.targetSize);
/* 119 */         buf.writeVarLong(this.timeUntilTarget);
/* 120 */         buf.writeVarIntToBuffer(this.size);
/* 121 */         buf.writeVarIntToBuffer(this.warningDistance);
/* 122 */         buf.writeVarIntToBuffer(this.warningTime);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 131 */     handler.handleWorldBorder(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_179788_a(WorldBorder border) {
/* 136 */     switch (this.action) {
/*     */       
/*     */       case SET_SIZE:
/* 139 */         border.setTransition(this.targetSize);
/*     */         break;
/*     */       
/*     */       case LERP_SIZE:
/* 143 */         border.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
/*     */         break;
/*     */       
/*     */       case SET_CENTER:
/* 147 */         border.setCenter(this.centerX, this.centerZ);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_BLOCKS:
/* 151 */         border.setWarningDistance(this.warningDistance);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_TIME:
/* 155 */         border.setWarningTime(this.warningTime);
/*     */         break;
/*     */       
/*     */       case null:
/* 159 */         border.setCenter(this.centerX, this.centerZ);
/*     */         
/* 161 */         if (this.timeUntilTarget > 0L) {
/*     */           
/* 163 */           border.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
/*     */         }
/*     */         else {
/*     */           
/* 167 */           border.setTransition(this.targetSize);
/*     */         } 
/*     */         
/* 170 */         border.setSize(this.size);
/* 171 */         border.setWarningDistance(this.warningDistance);
/* 172 */         border.setWarningTime(this.warningTime);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum Action {
/* 178 */     SET_SIZE,
/* 179 */     LERP_SIZE,
/* 180 */     SET_CENTER,
/* 181 */     INITIALIZE,
/* 182 */     SET_WARNING_TIME,
/* 183 */     SET_WARNING_BLOCKS;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\play\server\S44PacketWorldBorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */