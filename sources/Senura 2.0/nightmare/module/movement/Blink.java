/*    */ package nightmare.module.movement;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ import net.minecraft.world.World;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventSendPacket;
/*    */ import nightmare.event.impl.EventTick;
/*    */ import nightmare.gui.notification.NotificationManager;
/*    */ import nightmare.gui.notification.NotificationType;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import nightmare.utils.TimerUtils;
/*    */ 
/*    */ 
/*    */ public class Blink
/*    */   extends Module
/*    */ {
/*    */   private EntityOtherPlayerMP blinkEntity;
/* 25 */   private final ArrayList<Packet<?>> packetList = new ArrayList<>();
/*    */   
/* 27 */   private TimerUtils timer = new TimerUtils();
/*    */   
/*    */   public Blink() {
/* 30 */     super("Blink", 0, Category.MOVEMENT);
/*    */     
/* 32 */     Nightmare.instance.settingsManager.rSetting(new Setting("AutoDisable", this, false));
/* 33 */     Nightmare.instance.settingsManager.rSetting(new Setting("Delay", this, 1.5D, 1.0D, 5.0D, false));
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onTick(EventTick event) {
/* 38 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "AutoDisable").getValBoolean() && 
/* 39 */       this.timer.delay(Nightmare.instance.settingsManager.getSettingByName(this, "Delay").getValDouble() * 1000.0D)) {
/* 40 */       setToggled(false);
/* 41 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @EventTarget
/*    */   private void onSendPacket(EventSendPacket event) {
/* 48 */     if (event.getPacket() instanceof C03PacketPlayer) {
/* 49 */       C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
/*    */       
/* 51 */       if (packet.func_149466_j()) {
/* 52 */         this.packetList.add(event.getPacket());
/* 53 */         event.setCancelled(true);
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     if (event.getPacket() instanceof net.minecraft.network.play.client.C0APacketAnimation || event.getPacket() instanceof net.minecraft.network.play.client.C02PacketUseEntity || event.getPacket() instanceof net.minecraft.network.play.client.C08PacketPlayerBlockPlacement) {
/* 58 */       event.setCancelled(true);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 64 */     super.onEnable();
/*    */     
/* 66 */     if (mc.field_71439_g == null || mc.field_71441_e == null || mc.func_71356_B() || Nightmare.instance.moduleManager.getModuleByName("Freecam").isToggled()) {
/* 67 */       NotificationManager.show(NotificationType.WARNING, "Module", "This module can use only Multiplayer", 5000);
/* 68 */       setToggled(false);
/*    */       
/*    */       return;
/*    */     } 
/* 72 */     this.blinkEntity = new EntityOtherPlayerMP((World)mc.field_71441_e, mc.field_71439_g.func_146103_bH());
/* 73 */     this.blinkEntity.func_82149_j((Entity)mc.field_71439_g);
/* 74 */     this.blinkEntity.func_70034_d(mc.field_71439_g.field_70759_as);
/* 75 */     mc.field_71441_e.func_73027_a(this.blinkEntity.func_145782_y(), (Entity)this.blinkEntity);
/* 76 */     this.timer.reset();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 82 */     super.onDisable();
/*    */     
/* 84 */     if (mc.field_71439_g == null || mc.field_71441_e == null || mc.func_71356_B()) {
/*    */       return;
/*    */     }
/*    */     
/* 88 */     mc.field_71441_e.func_73028_b(this.blinkEntity.func_145782_y());
/*    */     
/* 90 */     if (!this.packetList.isEmpty()) {
/* 91 */       this.packetList.forEach(this::sendPacket);
/* 92 */       this.packetList.clear();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void sendPacket(Packet<?> packet) {
/* 97 */     if (mc.field_71439_g != null)
/* 98 */       mc.func_147114_u().func_147298_b().func_179290_a(packet); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\movement\Blink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */