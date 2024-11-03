package xyz.cucumber.base.module.feat.combat;

import com.mojang.authlib.GameProfile;
import god.buddy.aot.BCompiler;
import java.util.ArrayList;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.AxisAlignedBB;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.Event;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventGameLoop;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.render.ColorUtils;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Allows you to hit player in old position",
   name = "Back Track",
   key = 0,
   priority = ArrayPriority.HIGH
)
@BCompiler(
   aot = BCompiler.AOT.AGGRESSIVE
)
public class BackTrackModule extends Mod {
   public static ArrayList<Packet> incomingPackets = new ArrayList<>();
   public static ArrayList<Packet> outgoingPackets = new ArrayList<>();
   public double lastRealX;
   public double lastRealY;
   public double lastRealZ;
   private WorldClient lastWorld;
   private EntityLivingBase entity;
   public Timer timer = new Timer();
   private KillAuraModule killAura;
   public BooleanSettings legit = new BooleanSettings("Legit", false);
   public BooleanSettings releaseOnHit = new BooleanSettings("Release on hit", () -> this.legit.isEnabled(), true);
   public NumberSettings delay = new NumberSettings("Delay", 400.0, 0.0, 1000.0, 10.0);
   public NumberSettings hitRange = new NumberSettings("Hit Range", 3.0, 0.0, 10.0, 0.1);
   public BooleanSettings onlyIfNeed = new BooleanSettings("Only If Need", true);
   public BooleanSettings esp = new BooleanSettings("ESP", true);
   public ModeSettings mode = new ModeSettings("ESP Mode", new String[]{"Hitbox", "Player"});
   public ColorSettings mainColor = new ColorSettings("Color", "Still", -1, -1, 255);
   public ColorSettings outlineColor = new ColorSettings("Outline Color", "Still", -1, -1, 255);

   public BackTrackModule() {
      this.addSettings(
         new ModuleSettings[]{
            this.legit, this.releaseOnHit, this.delay, this.hitRange, this.onlyIfNeed, this.esp, this.mode, this.mainColor, this.outlineColor
         }
      );
   }

   @Override
   public void onEnable() {
      this.killAura = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
      incomingPackets.clear();
      outgoingPackets.clear();
      this.setInfo(String.valueOf(this.delay.getValue()));
   }

   @EventListener
   public void onReceivePacket(EventReceivePacket e) {
      this.info = (int)this.delay.getValue() + " ms";
      if (this.mc.thePlayer != null && this.mc.theWorld != null && this.killAura != null && this.mc.getNetHandler().getNetworkManager().getNetHandler() != null
         )
       {
         if (Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
            incomingPackets.clear();
         } else {
            if (e.getPacket() instanceof S14PacketEntity) {
               S14PacketEntity packet = (S14PacketEntity)e.getPacket();
               Entity entity = this.mc.theWorld.getEntityByID(packet.entityId);
               if (entity instanceof EntityLivingBase) {
                  EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                  entityLivingBase.realPosX = entityLivingBase.realPosX + (double)packet.func_149062_c();
                  entityLivingBase.realPosY = entityLivingBase.realPosY + (double)packet.func_149061_d();
                  entityLivingBase.realPosZ = entityLivingBase.realPosZ + (double)packet.func_149064_e();
               }
            }

            if (e.getPacket() instanceof S18PacketEntityTeleport) {
               S18PacketEntityTeleport packet = (S18PacketEntityTeleport)e.getPacket();
               Entity entity = this.mc.theWorld.getEntityByID(packet.getEntityId());
               if (entity instanceof EntityLivingBase) {
                  EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                  entityLivingBase.realPosX = (double)packet.getX();
                  entityLivingBase.realPosY = (double)packet.getY();
                  entityLivingBase.realPosZ = (double)packet.getZ();
               }
            }

            this.entity = null;
            if (this.killAura.isEnabled()) {
               this.entity = EntityUtils.getTarget(
                  this.hitRange.getValue(),
                  "Players",
                  "Off",
                  500,
                  Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
                  this.killAura.TroughWalls.isEnabled(),
                  this.killAura.attackDead.isEnabled(),
                  this.killAura.attackInvisible.isEnabled()
               );
            }

            if (this.mc.theWorld != null && this.lastWorld != this.mc.theWorld) {
               this.resetIncomingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
               this.lastWorld = this.mc.theWorld;
            } else {
               if (this.entity != null && (!this.onlyIfNeed.isEnabled() || !(this.mc.thePlayer.getDistanceToEntity(this.entity) < 3.0F))) {
                  this.addIncomingPackets(e.getPacket(), e);
               } else {
                  this.resetIncomingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
               }
            }
         }
      } else {
         incomingPackets.clear();
      }
   }

   @EventListener
   public void onSendPacket(EventSendPacket e) {
      this.info = (int)this.delay.getValue() + " ms";
      if (this.mc.thePlayer != null && this.mc.theWorld != null && this.killAura != null && this.mc.getNetHandler().getNetworkManager().getNetHandler() != null
         )
       {
         if (Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
            outgoingPackets.clear();
         } else {
            this.entity = null;
            if (this.killAura.isEnabled()) {
               this.entity = EntityUtils.getTarget(
                  this.hitRange.getValue(),
                  "Players",
                  "Off",
                  500,
                  Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
                  this.killAura.TroughWalls.isEnabled(),
                  this.killAura.attackDead.isEnabled(),
                  this.killAura.attackInvisible.isEnabled()
               );
            }

            if (this.mc.theWorld != null && this.lastWorld != this.mc.theWorld) {
               this.resetOutgoingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
               this.lastWorld = this.mc.theWorld;
            } else {
               if (this.entity != null && (!this.onlyIfNeed.isEnabled() || !(this.mc.thePlayer.getDistanceToEntity(this.entity) < 3.0F))) {
                  this.addOutgoingPackets(e.getPacket(), e);
               } else {
                  this.resetOutgoingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
               }
            }
         }
      } else {
         outgoingPackets.clear();
      }
   }

   @EventListener
   public void onGameLoop(EventGameLoop e) {
      if (this.entity != null
         && this.entity.getEntityBoundingBox() != null
         && this.mc.thePlayer != null
         && this.mc.theWorld != null
         && this.entity.realPosX != 0.0
         && this.entity.realPosY != 0.0
         && this.entity.realPosZ != 0.0
         && this.entity.width != 0.0F
         && this.entity.height != 0.0F
         && this.entity.posX != 0.0
         && this.entity.posY != 0.0
         && this.entity.posZ != 0.0) {
         double realX = this.entity.realPosX / 32.0;
         double realY = this.entity.realPosY / 32.0;
         double realZ = this.entity.realPosZ / 32.0;
         if (!this.onlyIfNeed.isEnabled()) {
            if (this.mc.thePlayer.getDistanceToEntity(this.entity) > 3.0F
               && this.mc.thePlayer.getDistance(this.entity.posX, this.entity.posY, this.entity.posZ) >= this.mc.thePlayer.getDistance(realX, realY, realZ)) {
               this.resetIncomingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
               this.resetOutgoingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
            }
         } else if (this.mc.thePlayer.getDistance(this.entity.posX, this.entity.posY, this.entity.posZ) >= this.mc.thePlayer.getDistance(realX, realY, realZ)
            || this.mc.thePlayer.getDistance(realX, realY, realZ) < this.mc.thePlayer.getDistance(this.lastRealX, this.lastRealY, this.lastRealZ)) {
            this.resetIncomingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
            this.resetOutgoingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
         }

         if (this.legit.isEnabled() && this.releaseOnHit.isEnabled() && this.entity.hurtTime <= 1) {
            this.resetIncomingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
            this.resetOutgoingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
         }

         if (this.mc.thePlayer.getDistance(realX, realY, realZ) > this.hitRange.getValue() || this.timer.hasTimeElapsed(this.delay.getValue(), true)) {
            this.resetIncomingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
            this.resetOutgoingPackets(this.mc.getNetHandler().getNetworkManager().getNetHandler());
         }

         this.lastRealX = realX;
         this.lastRealY = realY;
         this.lastRealZ = realZ;
      }
   }

   @EventListener
   public void onRender3D(EventRender3D e) {
      if (this.entity != null
         && this.entity.getEntityBoundingBox() != null
         && this.mc.thePlayer != null
         && this.mc.theWorld != null
         && this.entity.realPosX != 0.0
         && this.entity.realPosY != 0.0
         && this.entity.realPosZ != 0.0
         && this.entity.width != 0.0F
         && this.entity.height != 0.0F
         && this.entity.posX != 0.0
         && this.entity.posY != 0.0
         && this.entity.posZ != 0.0
         && this.esp.isEnabled()) {
         boolean render = true;
         double realX = this.entity.realPosX / 32.0;
         double realY = this.entity.realPosY / 32.0;
         double realZ = this.entity.realPosZ / 32.0;
         if (!this.onlyIfNeed.isEnabled()) {
            if (this.mc.thePlayer.getDistanceToEntity(this.entity) > 3.0F
               && this.mc.thePlayer.getDistance(this.entity.posX, this.entity.posY, this.entity.posZ) >= this.mc.thePlayer.getDistance(realX, realY, realZ)) {
               render = false;
            }
         } else if (this.mc.thePlayer.getDistance(this.entity.posX, this.entity.posY, this.entity.posZ) >= this.mc.thePlayer.getDistance(realX, realY, realZ)
            || this.mc.thePlayer.getDistance(realX, realY, realZ) < this.mc.thePlayer.getDistance(this.lastRealX, this.lastRealY, this.lastRealZ)) {
            render = false;
         }

         if (this.legit.isEnabled() && this.releaseOnHit.isEnabled() && this.entity.hurtTime <= 1) {
            render = false;
         }

         if (this.mc.thePlayer.getDistance(realX, realY, realZ) > this.hitRange.getValue() || this.timer.hasTimeElapsed(this.delay.getValue(), false)) {
            render = false;
         }

         if (this.entity != null && this.entity != this.mc.thePlayer && !this.entity.isInvisible() && render) {
            if (this.entity == null || this.entity.width == 0.0F || this.entity.height == 0.0F) {
               return;
            }

            int color = ColorUtils.getColor(this.mainColor, (double)(System.nanoTime() / 10000000L), 1.0, 5.0);
            double var10000 = this.entity.realPosX / 32.0;
            this.mc.getRenderManager();
            double x = var10000 - RenderManager.renderPosX;
            var10000 = this.entity.realPosY / 32.0;
            this.mc.getRenderManager();
            double y = var10000 - RenderManager.renderPosY;
            var10000 = this.entity.realPosZ / 32.0;
            this.mc.getRenderManager();
            double z = var10000 - RenderManager.renderPosZ;
            String var16;
            switch ((var16 = this.mode.getMode().toLowerCase()).hashCode()) {
               case -1217012392:
                  if (var16.equals("hitbox")) {
                     GlStateManager.pushMatrix();
                     RenderUtils.start3D();
                     RenderUtils.color(color);
                     RenderUtils.renderHitbox(
                        new AxisAlignedBB(
                           x - (double)(this.entity.width / 2.0F),
                           y,
                           z - (double)(this.entity.width / 2.0F),
                           x + (double)(this.entity.width / 2.0F),
                           y + (double)this.entity.height,
                           z + (double)(this.entity.width / 2.0F)
                        ),
                        7
                     );
                     RenderUtils.color(ColorUtils.getColor(this.outlineColor, (double)(System.nanoTime() / 10000000L), 1.0, 5.0));
                     RenderUtils.renderHitbox(
                        new AxisAlignedBB(
                           x - (double)(this.entity.width / 2.0F),
                           y,
                           z - (double)(this.entity.width / 2.0F),
                           x + (double)(this.entity.width / 2.0F),
                           y + (double)this.entity.height,
                           z + (double)(this.entity.width / 2.0F)
                        ),
                        2
                     );
                     RenderUtils.stop3D();
                     GlStateManager.popMatrix();
                  }
                  break;
               case -985752863:
                  if (var16.equals("player")) {
                     float f = this.entity.prevRotationYaw + (this.entity.rotationYaw - this.entity.prevRotationYaw) * e.getPartialTicks();
                     EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP(
                        this.mc.theWorld, new GameProfile(EntityPlayer.getUUID(this.mc.thePlayer.getGameProfile()), "")
                     );
                     entityOtherPlayerMP.setPosition(this.entity.realPosX / 32.0, this.entity.realPosY / 32.0, this.entity.realPosZ / 32.0);
                     entityOtherPlayerMP.inventory = ((EntityOtherPlayerMP)this.entity).inventory;
                     entityOtherPlayerMP.inventoryContainer = ((EntityOtherPlayerMP)this.entity).inventoryContainer;
                     entityOtherPlayerMP.rotationYawHead = this.entity.rotationYawHead;
                     entityOtherPlayerMP.rotationYaw = this.entity.rotationYaw;
                     entityOtherPlayerMP.rotationPitch = this.entity.rotationPitch;
                     this.mc.theWorld.addEntityToWorld(-42069, entityOtherPlayerMP);
                  }
            }
         }
      }
   }

   private void resetIncomingPackets(INetHandler netHandler) {
      if (incomingPackets.size() > 0) {
         for (; incomingPackets.size() != 0; incomingPackets.remove(incomingPackets.get(0))) {
            try {
               incomingPackets.get(0).processPacket(netHandler);
            } catch (Exception var3) {
            }
         }

         this.timer.reset();
      }
   }

   private void addIncomingPackets(Packet packet, Event event) {
      if (event != null && packet != null) {
         synchronized (incomingPackets) {
            if (this.blockPacketIncoming(packet)) {
               incomingPackets.add(packet);
               event.setCancelled(true);
            }
         }
      }
   }

   private void resetOutgoingPackets(INetHandler netHandler) {
      if (outgoingPackets.size() > 0) {
         for (; outgoingPackets.size() != 0; outgoingPackets.remove(outgoingPackets.get(0))) {
            try {
               this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(outgoingPackets.get(0));
            } catch (Exception var3) {
            }
         }

         this.timer.reset();
      }
   }

   private void addOutgoingPackets(Packet packet, Event event) {
      if (event != null && packet != null) {
         synchronized (outgoingPackets) {
            if (this.blockPacketsOutgoing(packet)) {
               outgoingPackets.add(packet);
               event.setCancelled(true);
            }
         }
      }
   }

   private boolean isEntityPacket(Packet packet) {
      return packet instanceof S14PacketEntity
         || packet instanceof S19PacketEntityHeadLook
         || packet instanceof S18PacketEntityTeleport
         || packet instanceof S0FPacketSpawnMob;
   }

   private boolean blockPacketIncoming(Packet packet) {
      return packet instanceof S03PacketTimeUpdate
         || packet instanceof S00PacketKeepAlive
         || packet instanceof S12PacketEntityVelocity
         || packet instanceof S27PacketExplosion
         || packet instanceof S32PacketConfirmTransaction
         || packet instanceof S08PacketPlayerPosLook
         || packet instanceof S01PacketPong
         || this.isEntityPacket(packet);
   }

   private boolean blockPacketsOutgoing(Packet packet) {
      return !this.legit.isEnabled()
         ? false
         : packet instanceof C03PacketPlayer
            || packet instanceof C02PacketUseEntity
            || packet instanceof C0FPacketConfirmTransaction
            || packet instanceof C08PacketPlayerBlockPlacement
            || packet instanceof C09PacketHeldItemChange
            || packet instanceof C07PacketPlayerDigging
            || packet instanceof C0APacketAnimation
            || packet instanceof C01PacketPing
            || packet instanceof C00PacketKeepAlive
            || packet instanceof C0BPacketEntityAction;
   }
}
