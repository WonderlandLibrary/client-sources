/*     */ package me.eagler.module.modules.player;
/*     */ 
/*     */ import me.eagler.module.Category;
/*     */ import me.eagler.module.Module;
/*     */ import me.eagler.setting.Setting;
/*     */ import me.eagler.utils.TimeHelper;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C0APacketAnimation;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ public class BedDestroyer
/*     */   extends Module
/*     */ {
/*  18 */   private TimeHelper time = new TimeHelper();
/*     */   
/*     */   public static BlockPos pos;
/*     */   
/*     */   private int x;
/*     */   private int z;
/*     */   private int y;
/*     */   
/*     */   public BedDestroyer() {
/*  27 */     super("BedDestroyer", Category.Player);
/*     */     
/*  29 */     this.settingManager.addSetting(new Setting("Radius", this, 5.0D, 1.0D, 6.0D, true));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  34 */     pos = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  40 */     int radius = (int)this.settingManager.getSettingByName("Radius").getValue();
/*     */     
/*  42 */     setExtraTag(radius);
/*     */     
/*  44 */     int startX = this.mc.thePlayer.getPosition().getX() - radius;
/*  45 */     int startY = this.mc.thePlayer.getPosition().getY() - radius;
/*  46 */     int startZ = this.mc.thePlayer.getPosition().getZ() - radius;
/*     */     
/*  48 */     int endX = this.mc.thePlayer.getPosition().getX() + radius;
/*  49 */     int endY = this.mc.thePlayer.getPosition().getY() + radius;
/*  50 */     int endZ = this.mc.thePlayer.getPosition().getZ() + radius;
/*     */     
/*  52 */     this.x = startX;
/*     */     
/*  54 */     while (this.x < endX) {
/*     */       
/*  56 */       this.z = startZ;
/*     */       
/*  58 */       while (this.z < endZ) {
/*     */         
/*  60 */         this.y = startY;
/*     */         
/*  62 */         while (this.y < endY) {
/*     */           
/*  64 */           BlockPos blockPos = new BlockPos(this.x, this.y, this.z);
/*     */           
/*  66 */           if (this.mc.theWorld.getBlockState(blockPos) != null) {
/*     */             
/*  68 */             int id = Block.getIdFromBlock(this.mc.theWorld.getBlockState(blockPos).getBlock());
/*     */             
/*  70 */             if (id == 26) {
/*     */               
/*  72 */               pos = blockPos;
/*     */               
/*  74 */               if (this.time.hasReached(1000L)) {
/*     */                 
/*  76 */                 smashBlock(blockPos);
/*  77 */                 this.time.reset();
/*     */ 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/*  86 */           this.y++;
/*     */         } 
/*     */         
/*  89 */         this.z++;
/*     */       } 
/*     */       
/*  92 */       this.x++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void smashBlock(BlockPos pos) {
/*  98 */     this.mc.thePlayer.sendQueue.addToSendQueue(
/*  99 */         (Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
/* 100 */     this.mc.thePlayer.sendQueue.addToSendQueue(
/* 101 */         (Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
/* 102 */     this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\player\BedDestroyer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */