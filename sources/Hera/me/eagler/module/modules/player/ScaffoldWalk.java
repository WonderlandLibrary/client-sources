/*     */ package me.eagler.module.modules.player;
/*     */ 
/*     */ import me.eagler.Client;
/*     */ import me.eagler.event.EventManager;
/*     */ import me.eagler.event.EventTarget;
/*     */ import me.eagler.event.events.PacketEvent;
/*     */ import me.eagler.module.Category;
/*     */ import me.eagler.module.Module;
/*     */ import me.eagler.setting.Setting;
/*     */ import me.eagler.utils.TimeHelper;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScaffoldWalk
/*     */   extends Module
/*     */ {
/*  30 */   public TimeHelper time = new TimeHelper();
/*  31 */   public TimeHelper time2 = new TimeHelper();
/*     */   
/*  33 */   public BlockPos blockPos = null;
/*     */   
/*  35 */   public EnumFacing facing = null;
/*     */   
/*  37 */   private int counter = 0;
/*     */   
/*     */   public ScaffoldWalk() {
/*  40 */     super("ScaffoldWalk", Category.Player);
/*     */     
/*  42 */     this.settingManager.addSetting(new Setting("ScaffoldDelay", this, 200.0D, 1.0D, 1000.0D, true));
/*  43 */     this.settingManager.addSetting(new Setting("Boost", this, 0.0D, 0.0D, 1.0D, false));
/*  44 */     this.settingManager.addSetting(new Setting("NoSprint", this, true));
/*  45 */     this.settingManager.addSetting(new Setting("Sneak", this, false));
/*  46 */     this.settingManager.addSetting(new Setting("CubeCraft", this, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  51 */     EventManager.register(this);
/*  52 */     EventManager.register(this);
/*     */     
/*  54 */     this.time.reset();
/*     */     
/*  56 */     this.time2.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  62 */     EventManager.unregister(this);
/*  63 */     this.mc.timer.timerSpeed = 1.0F;
/*  64 */     this.mc.gameSettings.keyBindSneak.pressed = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  70 */     if (Client.instance.getModuleManager().getModuleByName("Speed").isEnabled())
/*     */     {
/*  72 */       Client.instance.getModuleManager().getModuleByName("Speed").setEnabled(false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     long delay = (long)this.settingManager.getSettingByName("ScaffoldDelay").getValue();
/*     */     
/* 122 */     setExtraTag(delay);
/*     */     
/* 124 */     if (this.settingManager.getSettingByName("NoSprint").getBoolean()) {
/*     */       
/* 126 */       this.mc.gameSettings.keyBindSprint.pressed = false;
/* 127 */       this.mc.thePlayer.setSprinting(false);
/*     */     } 
/*     */ 
/*     */     
/* 131 */     if (this.settingManager.getSettingByName("Sneak").getBoolean()) {
/*     */       
/* 133 */       BlockPos blockPos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0D, this.mc.thePlayer.posZ);
/*     */       
/* 135 */       if (this.mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air) {
/*     */         
/* 137 */         this.mc.gameSettings.keyBindSneak.pressed = true;
/*     */       }
/*     */       else {
/*     */         
/* 141 */         this.mc.gameSettings.keyBindSneak.pressed = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 147 */     double boost = this.settingManager.getSettingByName("Boost").getValue();
/*     */     
/* 149 */     if (this.mc.gameSettings.keyBindForward.pressed) {
/*     */       
/* 151 */       this.mc.thePlayer.motionX *= 1.0D + boost;
/* 152 */       this.mc.thePlayer.motionZ *= 1.0D + boost;
/*     */       
/* 154 */       this.mc.thePlayer.onGround = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (this.mc.thePlayer != null && this.mc.theWorld != null) {
/*     */       
/* 162 */       BlockPos block = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0D, this.mc.thePlayer.posZ);
/*     */       
/* 164 */       if (this.mc.theWorld.getBlockState(block).getBlock() == Blocks.air) {
/*     */         
/* 166 */         setBlockData(block);
/*     */         
/* 168 */         if (this.settingManager.getSettingByName("CubeCraft").getBoolean())
/*     */         {
/* 170 */           if (!checkLooking(block)) {
/*     */             return;
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 180 */         if (this.time.hasReached(delay))
/*     */         {
/* 182 */           if (this.mc.thePlayer.getHeldItem() != null && 
/* 183 */             this.mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemBlock) {
/*     */             
/* 185 */             if (this.blockPos != null && this.facing != null) {
/*     */               
/* 187 */               this.mc.thePlayer.motionX *= 0.20000000298023224D;
/* 188 */               this.mc.thePlayer.motionZ *= 0.20000000298023224D;
/*     */               
/* 190 */               this.mc.thePlayer.swingItem();
/*     */               
/* 192 */               this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, 
/* 193 */                   this.mc.thePlayer.getHeldItem(), this.blockPos, this.facing, 
/* 194 */                   new Vec3(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ()));
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 199 */             this.time.reset();
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRender() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventTarget
/*     */   public void onPacket(PacketEvent e) {
/* 253 */     String direction = this.mc.getRenderViewEntity().getHorizontalFacing().name();
/*     */     
/* 255 */     if (e.getPacket() instanceof C03PacketPlayer) {
/*     */       
/* 257 */       C03PacketPlayer p = (C03PacketPlayer)e.getPacket();
/*     */       
/* 259 */       if (!this.mc.gameSettings.keyBindJump.pressed) {
/*     */         
/* 261 */         if (this.settingManager.getSettingByName("CubeCraft").getBoolean()) {
/*     */ 
/*     */           
/* 264 */           C03PacketPlayer.pitch = 88.5606F;
/*     */         }
/*     */         else {
/*     */           
/* 268 */           C03PacketPlayer.pitch = 82.0F;
/*     */         } 
/*     */ 
/*     */         
/* 272 */         if (direction.equalsIgnoreCase("NORTH"))
/*     */         {
/* 274 */           C03PacketPlayer.yaw = 360.0F;
/*     */         }
/* 276 */         else if (direction.equalsIgnoreCase("SOUTH"))
/*     */         {
/* 278 */           C03PacketPlayer.yaw = 180.0F;
/*     */         }
/* 280 */         else if (direction.equalsIgnoreCase("WEST"))
/*     */         {
/* 282 */           C03PacketPlayer.yaw = 270.0F;
/*     */         }
/* 284 */         else if (direction.equalsIgnoreCase("EAST"))
/*     */         {
/* 286 */           C03PacketPlayer.yaw = 90.0F;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 291 */         C03PacketPlayer.pitch = 90.0F;
/*     */       } 
/*     */ 
/*     */       
/* 295 */       e.setPacket((Packet)p);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockData(BlockPos block) {
/* 303 */     if (this.mc.theWorld.getBlockState(block.add(0, -1, 0)).getBlock() != Blocks.air) {
/*     */       
/* 305 */       this.blockPos = block.add(0, -1, 0);
/* 306 */       this.facing = EnumFacing.UP;
/*     */     } 
/*     */ 
/*     */     
/* 310 */     if (this.mc.theWorld.getBlockState(block.add(-1, 0, 0)).getBlock() != Blocks.air) {
/*     */       
/* 312 */       this.blockPos = block.add(-1, 0, 0);
/* 313 */       this.facing = EnumFacing.EAST;
/*     */     } 
/*     */ 
/*     */     
/* 317 */     if (this.mc.theWorld.getBlockState(block.add(1, 0, 0)).getBlock() != Blocks.air) {
/*     */       
/* 319 */       this.blockPos = block.add(1, 0, 0);
/* 320 */       this.facing = EnumFacing.WEST;
/*     */     } 
/*     */ 
/*     */     
/* 324 */     if (this.mc.theWorld.getBlockState(block.add(0, 0, -1)).getBlock() != Blocks.air) {
/*     */       
/* 326 */       this.blockPos = block.add(0, 0, -1);
/* 327 */       this.facing = EnumFacing.SOUTH;
/*     */     } 
/*     */ 
/*     */     
/* 331 */     if (this.mc.theWorld.getBlockState(block.add(0, 0, 1)).getBlock() != Blocks.air) {
/*     */       
/* 333 */       this.blockPos = block.add(0, 0, 1);
/* 334 */       this.facing = EnumFacing.NORTH;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkLooking(BlockPos block) {
/* 342 */     String direction = this.mc.getRenderViewEntity().getHorizontalFacing().name();
/*     */     
/* 344 */     if (direction.equalsIgnoreCase("NORTH")) {
/*     */       
/* 346 */       if (this.mc.thePlayer.posZ > block.getZ() + 0.944D)
/*     */       {
/* 348 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 352 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     if (direction.equalsIgnoreCase("WEST")) {
/*     */       
/* 360 */       if (this.mc.thePlayer.posX > block.getX() + 0.944D)
/*     */       {
/* 362 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 366 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     if (direction.equalsIgnoreCase("SOUTH")) {
/*     */       
/* 374 */       if (this.mc.thePlayer.posZ < block.getZ() + 0.05D)
/*     */       {
/* 376 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 380 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 386 */     if (direction.equalsIgnoreCase("WEST")) {
/*     */       
/* 388 */       if (this.mc.thePlayer.posX < block.getX() + 0.05D)
/*     */       {
/* 390 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 394 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 400 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\player\ScaffoldWalk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */