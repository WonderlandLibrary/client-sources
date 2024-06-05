package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.CollideWithBlockEvent;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.MoveEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.PushBlockEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.exploits.Blink;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public final class Freecam extends AbstractModule {
   private EntityOtherPlayerMP freecamEntity;
   @Property("freecam-speed")
   private final FloatProperty freecamSpeed = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(4.0F)).minimum(Float.valueOf(1.0F))).maximum(Float.valueOf(5.0F));
   private static int[] z;

   public Freecam(ModuleManager var1) {
      super(var1, "Freecam", "Freecam", 0, EnumModuleType.MOVEMENT, "Ghost Walking");
      Manager.put(new Setting("FC_SPEED", "Speed", SettingType.SLIDER, this, this.freecamSpeed, 1.0D));
   }

   public void onDisable() {
      int[] var1 = a();
      if(this.freecamEntity != null) {
         this.mc.player.setPositionAndRotation(this.freecamEntity.posX, this.freecamEntity.posY, this.freecamEntity.posZ, this.freecamEntity.rotationYaw, this.freecamEntity.rotationPitch);
         this.mc.world.d(this.freecamEntity.getEntityID());
      }

      this.mc.player.noClip = false;
   }

   public void onEnable() {
      int[] var1 = a();
      if(this.mc.player != null) {
         this.checkModule(Blink.class);
         this.freecamEntity = new EntityOtherPlayerMP(this.mc.world, this.mc.player.getGameProfile());
         this.freecamEntity.inventory = this.mc.player.inventory;
         this.freecamEntity.inventoryContainer = this.mc.player.inventoryContainer;
         this.freecamEntity.setPositionAndRotation(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, this.mc.player.rotationYaw, this.mc.player.rotationPitch);
         this.freecamEntity.rotationYawHead = this.mc.player.rotationYawHead;
         this.freecamEntity.setEntityUniqueID(this.mc.player.getEntityUniqueID());
         this.mc.world.addEntityToWorld(this.freecamEntity.getEntityID(), this.freecamEntity);
      }
   }

   @EventTarget
   public void onEvent(MotionUpdateEvent var1) {
      if(var1.getState().equals(EventState.PRE)) {
         this.mc.player.noClip = true;
      }

   }

   @EventTarget
   public void onEvent(PacketEvent var1) {
      int[] var2 = a();
      if(var1.getDirection().equals(PacketDirection.OUTGOING) && var1.getPacket() instanceof C03PacketPlayer) {
         var1.setCancelled(true);
      }

   }

   @EventTarget
   public void onEvent(CollideWithBlockEvent var1) {
      var1.setBoundingBox((AxisAlignedBB)null);
   }

   @EventTarget
   public void onEvent(MoveEvent var1) {
      a();
      float var3 = ((Float)this.freecamSpeed.get()).floatValue();
      if(this.mc.player.movementInput().jump()) {
         var1.setY(this.mc.player.motionY = (double)var3);
      }

      if(this.mc.player.movementInput().sneak()) {
         var1.setY(this.mc.player.motionY = (double)(-var3));
      }

      var1.setY(this.mc.player.motionY = 0.0D);
      var1.setMoveSpeed((double)var3);
   }

   @EventTarget
   public void onEvent(PushBlockEvent var1) {
      var1.setCancelled(true);
   }

   public EntityOtherPlayerMP getFreecamEntity() {
      return this.freecamEntity;
   }

   public static void a(int[] var0) {
      z = var0;
   }

   public static int[] a() {
      return z;
   }

   static {
      a((int[])null);
   }
}
