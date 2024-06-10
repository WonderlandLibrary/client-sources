/*     */ package nightmare.module.player;
/*     */ 
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.event.EventTarget;
/*     */ import nightmare.event.impl.EventBoundingBox;
/*     */ import nightmare.event.impl.EventSendPacket;
/*     */ import nightmare.event.impl.EventUpdate;
/*     */ import nightmare.module.Category;
/*     */ import nightmare.module.Module;
/*     */ import nightmare.settings.Setting;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class Freecam
/*     */   extends Module {
/*     */   private EntityOtherPlayerMP freecamPlayer;
/*     */   private double oldX;
/*     */   private double oldY;
/*     */   private double oldZ;
/*  25 */   public float speed = 1.0F;
/*     */   
/*     */   public Freecam() {
/*  28 */     super("Freecam", 0, Category.PLAYER);
/*     */     
/*  30 */     Nightmare.instance.settingsManager.rSetting(new Setting("Speed", this, 1.0D, 1.0D, 5.0D, false));
/*     */   }
/*     */   
/*     */   @EventTarget
/*     */   public void onUpdate(EventUpdate event) {
/*  35 */     if (mc.field_71439_g.func_70051_ag()) {
/*  36 */       this.speed = 1.5F;
/*     */     } else {
/*  38 */       this.speed = 1.0F;
/*     */     } 
/*     */     
/*  41 */     mc.field_71439_g.field_70145_X = true;
/*  42 */     mc.field_71439_g.field_70143_R = 0.0F;
/*  43 */     mc.field_71439_g.field_70122_E = false;
/*  44 */     mc.field_71439_g.field_71075_bZ.field_75100_b = false;
/*  45 */     mc.field_71439_g.field_70159_w = 0.0D;
/*  46 */     mc.field_71439_g.field_70181_x = 0.0D;
/*  47 */     mc.field_71439_g.field_70179_y = 0.0D;
/*  48 */     float moveSpeed = (float)Nightmare.instance.settingsManager.getSettingByName(this, "Speed").getValDouble();
/*  49 */     mc.field_71439_g.field_70747_aH = moveSpeed;
/*  50 */     if (Keyboard.isKeyDown(mc.field_71474_y.field_74314_A.func_151463_i())) {
/*  51 */       mc.field_71439_g.field_70181_x += moveSpeed;
/*     */     }
/*  53 */     if (Keyboard.isKeyDown(mc.field_71474_y.field_74311_E.func_151463_i())) {
/*  54 */       mc.field_71439_g.field_70181_x -= moveSpeed;
/*     */     }
/*     */   }
/*     */   
/*     */   @EventTarget
/*     */   public void onPacket(EventSendPacket event) {
/*  60 */     if (event.getPacket() instanceof net.minecraft.network.play.client.C03PacketPlayer || event.getPacket() instanceof net.minecraft.network.play.client.C0BPacketEntityAction) {
/*  61 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventTarget
/*     */   public void onBoundingBox(EventBoundingBox event) {
/*  67 */     event.boundingBox = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  73 */     if (mc.field_71439_g == null || mc.field_71441_e == null || Nightmare.instance.moduleManager.getModuleByName("Blink").isToggled()) {
/*  74 */       setToggled(false);
/*     */       
/*     */       return;
/*     */     } 
/*  78 */     this.oldX = mc.field_71439_g.field_70165_t;
/*  79 */     this.oldY = mc.field_71439_g.field_70163_u;
/*  80 */     this.oldZ = mc.field_71439_g.field_70161_v;
/*  81 */     WorldClient worldClient = mc.field_71441_e;
/*  82 */     this.freecamPlayer = new EntityOtherPlayerMP((World)worldClient, mc.field_71439_g.func_146103_bH());
/*  83 */     EntityOtherPlayerMP entityOtherPlayerMP = this.freecamPlayer;
/*  84 */     entityOtherPlayerMP.func_71049_a((EntityPlayer)mc.field_71439_g, true);
/*  85 */     entityOtherPlayerMP = this.freecamPlayer;
/*  86 */     entityOtherPlayerMP.func_82149_j((Entity)mc.field_71439_g);
/*  87 */     entityOtherPlayerMP = this.freecamPlayer;
/*  88 */     entityOtherPlayerMP.field_70759_as = mc.field_71439_g.field_70759_as;
/*  89 */     mc.field_71441_e.func_73027_a(-69, (Entity)this.freecamPlayer);
/*  90 */     super.onEnable();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  96 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/*     */       return;
/*     */     }
/*     */     
/* 100 */     mc.field_71439_g.field_71075_bZ.field_75098_d = false;
/* 101 */     mc.field_71439_g.field_70145_X = false;
/* 102 */     mc.field_71439_g.field_71075_bZ.field_75100_b = false;
/* 103 */     EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/* 104 */     double oldX = this.oldX;
/* 105 */     double oldY = this.oldY;
/* 106 */     double oldZ = this.oldZ;
/* 107 */     float rotationYaw = mc.field_71439_g.field_70177_z;
/* 108 */     entityPlayerSP.func_70080_a(oldX, oldY, oldZ, rotationYaw, mc.field_71439_g.field_70125_A);
/* 109 */     mc.field_71441_e.func_73028_b(-69);
/* 110 */     this.freecamPlayer = null;
/* 111 */     mc.field_71438_f.func_72712_a();
/* 112 */     super.onDisable();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\player\Freecam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */