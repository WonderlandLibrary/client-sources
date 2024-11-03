package xyz.cucumber.base.module.feat.combat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventRenderRotation;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventUpdate;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Aura for hypixel",
   name = "Aura",
   key = 19,
   priority = ArrayPriority.HIGH
)
public class AuraModule extends Mod {
   private final NumberSettings range = new NumberSettings("Range", 3.2, 1.0, 6.0, 0.1);
   public static BooleanSettings autoblock = new BooleanSettings("Auto Block", true);
   private final BooleanSettings players = new BooleanSettings("Players", true);
   private final BooleanSettings animals = new BooleanSettings("Animals", false);
   private final BooleanSettings monsters = new BooleanSettings("Monsters", false);
   private final BooleanSettings invisible = new BooleanSettings("Invisible", true);
   private static final List<Packet<?>> packetList = new ArrayList<>();
   private static final Set<Class<?>> NON_CANCELABLE_PACKETS = new HashSet<>(
      Arrays.asList(
         C01PacketChatMessage.class,
         C14PacketTabComplete.class,
         C01PacketEncryptionResponse.class,
         C01PacketPing.class,
         C00PacketLoginStart.class,
         C00PacketServerQuery.class,
         C00Handshake.class,
         C00PacketKeepAlive.class
      )
   );
   private boolean isBlinking = false;
   private boolean isBlocking = false;
   public static EntityLivingBase target;
   private float targetYaw;
   private float targetPitch;
   private int autoBlockTick = 0;

   public AuraModule() {
      this.addSettings(new ModuleSettings[]{this.range, autoblock, this.players, this.animals, this.monsters, this.invisible});
   }

   public void startBlinking() {
      if (!this.mc.isIntegratedServerRunning()) {
         this.isBlinking = true;
      }
   }

   public void stopBlinking() {
      if (!this.mc.isIntegratedServerRunning()) {
         this.isBlinking = false;
         packetList.forEach(this.mc.thePlayer.sendQueue::addToSendQueue);
         packetList.clear();
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @Override
   public void onDisable() {
      if (this.isBlinking) {
         this.stopBlinking();
      }

      if (this.isBlocking) {
         this.unBlockSword();
      }

      super.onDisable();
   }

   @EventListener
   public void onTick(EventUpdate e) {
      if (this.check() && e.getType() != EventType.POST) {
         List<EntityLivingBase> possibilities = this.mc
            .theWorld
            .loadedEntityList
            .stream()
            .filter(entity -> entity instanceof EntityLivingBase && (double)this.mc.thePlayer.getDistanceToEntity(entity) < 5.0)
            .map(entity -> (EntityLivingBase)entity)
            .filter(entity -> {
               if (entity instanceof EntityArmorStand) {
                  return false;
               } else if (entity instanceof EntityPlayer && !this.players.isEnabled()) {
                  return false;
               } else if (entity instanceof EntityMob && !this.monsters.isEnabled()) {
                  return false;
               } else if (entity instanceof EntityAnimal && !this.animals.isEnabled()) {
                  return false;
               } else {
                  return entity.isInvisible() && !this.invisible.isEnabled() ? false : entity != this.mc.thePlayer && entity.deathTime == 0 && !entity.isDead;
               }
            })
            .sorted(Comparator.comparingDouble(entity -> (double)this.mc.thePlayer.getDistanceToEntity(entity)))
            .collect(Collectors.toList());
         target = possibilities.isEmpty() ? null : possibilities.get(0);
         float[] rots = target != null
            ? RotationUtils.getNormalAuraRotations(
               RotationUtils.serverYaw, RotationUtils.serverPitch, target, target.posX, target.posY, target.posZ, 180.0F, 180.0F, true
            )
            : new float[]{this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch};
         this.targetYaw = rots[0];
         this.targetPitch = rots[1];
         if (target != null
            && (double)this.mc.thePlayer.getDistanceToEntity(target) <= this.range.getValue()
            && autoblock.isEnabled()
            && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            this.autoBlockTick++;
            switch (this.autoBlockTick) {
               case 1:
                  this.mc.playerController.syncCurrentPlayItem();
                  if (this.isBlinking) {
                     this.stopBlinking();
                     this.isBlinking = false;
                  }

                  this.mc.thePlayer.swingItem();
                  this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                  this.blockSword(true);
                  break;
               case 2:
                  this.startBlinking();
                  this.isBlinking = true;
                  int oldSlot = this.mc.thePlayer.inventory.currentItem++;
                  this.mc.playerController.syncCurrentPlayItem();
                  this.isBlocking = false;
                  this.mc.thePlayer.inventory.currentItem = oldSlot;
                  this.autoBlockTick = 0;
            }
         } else {
            if (this.autoBlockTick == 1) {
               this.autoBlockTick = 0;
            }

            if (this.isBlinking) {
               this.stopBlinking();
            }

            if (this.isBlocking) {
               this.unBlockSword();
            }
         }
      }
   }

   @EventListener
   public void onMotion(EventMotion e) {
      if (target != null) {
         this.mc.thePlayer.rotationYawHead = this.targetYaw;
         this.mc.thePlayer.renderYawOffset = this.targetYaw;
         e.setYaw(this.targetYaw);
         e.setPitch(this.targetPitch);
      }
   }

   @EventListener
   public void onLook(EventLook e) {
      if (target != null) {
         e.setYaw(this.targetYaw);
         e.setPitch(this.targetPitch);
      }
   }

   @EventListener
   public void onRotationRender(EventRenderRotation e) {
      if (target != null) {
         e.setYaw(this.targetYaw);
         e.setPitch(this.targetPitch);
      }
   }

   @EventListener
   public void onPacketSendEvent(EventSendPacket event) {
      if (this.check()) {
         if (!this.mc.isIntegratedServerRunning()) {
            if (this.isBlinking && !event.isCancelled()) {
               if (NON_CANCELABLE_PACKETS.contains(event.getPacket().getClass())) {
                  return;
               }

               event.setCancelled(true);
               packetList.add(event.getPacket());
            }

            if (this.mc.thePlayer == null) {
               this.stopBlinking();
            }
         }
      }
   }

   public void blockSword(boolean interact) {
      boolean canBlock = !this.isBlocking
         && this.mc.thePlayer.inventory.getCurrentItem() != null
         && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword
         && this.check();
      if (canBlock) {
         if (interact) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.INTERACT));
         }

         this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getHeldItem()));
      }

      this.isBlocking = true;
   }

   public void unBlockSword() {
      boolean canBlock = this.isBlocking
         && this.mc.thePlayer.inventory.getCurrentItem() != null
         && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword
         && this.check();
      if (canBlock) {
         this.mc
            .thePlayer
            .sendQueue
            .addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      }

      this.isBlocking = false;
   }

   public boolean check() {
      return Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class) != null
            && Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()
         ? false
         : this.mc.theWorld != null && this.mc.thePlayer != null;
   }
}
