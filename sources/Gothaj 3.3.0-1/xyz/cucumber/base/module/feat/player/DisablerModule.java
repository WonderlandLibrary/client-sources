package xyz.cucumber.base.module.feat.player;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import god.buddy.aot.BCompiler;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventHit;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.events.ext.EventWorldChange;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.PLAYER,
   description = "Allows you to disable anticheats",
   name = "Disabler",
   priority = ArrayPriority.HIGH
)
@BCompiler(
   aot = BCompiler.AOT.AGGRESSIVE
)
public class DisablerModule extends Mod {
   public boolean disabled;
   public boolean move;
   public boolean attack;
   public int counter;
   public int balance;
   public int maxBalance;
   public double difference;
   public long lastSetBack;
   private int currentDelay = 5000;
   private int currentBuffer = 4;
   private int currentDec = -1;
   public Timer timer = new Timer();
   public Timer timer1 = new Timer();
   public ModeSettings mode = new ModeSettings(
      "Mode",
      new String[]{
         "Polar full",
         "Hypixel test",
         "Hypixel Motion",
         "Intave Cloud Check",
         "Intave old",
         "Timer Range",
         "Spectate 1",
         "Spectate 2",
         "Verus",
         "Minemen club",
         "Mushmc"
      }
   );
   public BooleanSettings expandScaffold = new BooleanSettings("Expand Scaffold", true);
   public BooleanSettings intaveReach = new BooleanSettings("Intave Reach", false);
   public BooleanSettings intaveReachStrong = new BooleanSettings("Intave Reach Strong", false);
   public BooleanSettings autoAura = new BooleanSettings("Auto Aura", false);
   public ArrayList<Packet> packets = new ArrayList<>();
   public ArrayList<Packet> packets1 = new ArrayList<>();
   public ArrayList<Packet> packets2 = new ArrayList<>();
   private final ArrayList<Packet<?>> funPackets = new ArrayList<>();
   private final ConcurrentHashMap<Packet<?>, Long> pingSpoofPackets = new ConcurrentHashMap<>();

   public DisablerModule() {
      this.addSettings(new ModuleSettings[]{this.mode, this.expandScaffold, this.intaveReach, this.intaveReachStrong, this.autoAura});
   }

   @Override
   public void onEnable() {
      this.timer.reset();
      this.disabled = false;
      this.move = false;
      this.packets.clear();
      this.packets1.clear();
      this.funPackets.clear();
      String var1;
      switch ((var1 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -741918200:
            if (var1.equals("intave old") && this.autoAura.isEnabled()) {
               KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
               ka.Range.setValue(6.0);
               ka.interactRange.setValue(6.0);
            }
            break;
         case 560229671:
            if (var1.equals("verus experimental")) {
               Client.INSTANCE.getCommandManager().sendChatMessage("idk what to say");
            }
      }
   }

   @Override
   public void onDisable() {
      this.counter = 0;
      this.mc.timer.timerSpeed = 1.0F;
      this.balance = 0;

      try {
         while (this.packets.size() > 0) {
            this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(this.packets.get(0));
            this.packets.remove(this.packets.get(0));
         }

         while (this.packets1.size() > 0) {
            this.packets1.get(0).processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
            this.packets1.remove(this.packets1.get(0));
         }

         while (this.packets2.size() > 0) {
            this.packets2.get(0).processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
            this.packets2.remove(this.packets2.get(0));
         }
      } catch (Exception var3) {
      }

      String var1;
      switch ((var1 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -741918200:
            if (var1.equals("intave old") && this.autoAura.isEnabled()) {
               KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
               ka.Range.setValue(3.0);
               ka.interactRange.setValue(4.0);
            }
            break;
         case 560229671:
            if (var1.equals("verus experimental")) {
               Client.INSTANCE.getCommandManager().sendChatMessage("idk what to say");
            }
      }
   }

   @EventListener
   public void onSendPacket(EventSendPacket e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1840247646:
            if (var2.equals("timer range") && e.getPacket() instanceof C03PacketPlayer) {
               C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
               if (!packet.isMoving()
                  && !packet.getRotating()
                  && !this.mc.thePlayer.isUsingItem()
                  && !Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
                  e.setCancelled(true);
               }
            }
            break;
         case -1062808493:
            if (var2.equals("mushmc") && e.getPacket() instanceof C03PacketPlayer && this.timer.hasTimeElapsed(1200.0, true)) {
               C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
               packet.y -= 9.99999999E8;
               this.disabled = true;
            }
            break;
         case -863729103:
            if (var2.equals("polar full") && e.getPacket() instanceof C0FPacketConfirmTransaction) {
               e.setCancelled(true);
            }
            break;
         case -741918200:
            if (var2.equals("intave old")) {
               if (e.getPacket() instanceof C19PacketResourcePackStatus) {
                  e.setCancelled(true);
               }

               if (this.intaveReach.isEnabled()) {
                  if (e.getPacket() instanceof C02PacketUseEntity || e.getPacket() instanceof C0APacketAnimation) {
                     KillAuraModule killAura = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
                     TeamsModule teams = (TeamsModule)Client.INSTANCE.getModuleManager().getModule(TeamsModule.class);
                     EntityLivingBase entity = EntityUtils.getTargetBox(
                        8.0,
                        killAura.Targets.getMode(),
                        killAura.switchMode.getMode(),
                        500,
                        teams.isEnabled(),
                        killAura.TroughWalls.isEnabled(),
                        killAura.attackDead.isEnabled(),
                        killAura.attackInvisible.isEnabled()
                     );
                     if (entity == null) {
                        e.setCancelled(true);
                        return;
                     }

                     if (EntityUtils.getDistanceToEntityBox(entity) > 3.0 && killAura.isEnabled()) {
                        e.setCancelled(true);
                     }
                  }

                  if (e.getPacket() instanceof C03PacketPlayer && this.move) {
                     this.move = false;
                     this.attack = true;
                     KillAuraModule killAurax = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
                     TeamsModule teamsx = (TeamsModule)Client.INSTANCE.getModuleManager().getModule(TeamsModule.class);
                     EntityLivingBase entityx = EntityUtils.getTargetBox(
                        8.0,
                        killAurax.Targets.getMode(),
                        killAurax.switchMode.getMode(),
                        500,
                        teamsx.isEnabled(),
                        killAurax.TroughWalls.isEnabled(),
                        killAurax.attackDead.isEnabled(),
                        killAurax.attackInvisible.isEnabled()
                     );
                     if (entityx == null) {
                        return;
                     }

                     e.setCancelled(true);
                     float[] rots = RotationUtils.getRotationsFromPositionToPosition(
                        entityx.posX, entityx.posY, entityx.posZ, this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ
                     );
                     float yaw = rots[0];
                     float pitch = rots[1];

                     for (int i = 0; i < (this.intaveReachStrong.isEnabled() ? 20 : 2); i++) {
                        this.mc
                           .getNetHandler()
                           .getNetworkManager()
                           .sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(entityx.posX, entityx.posY, entityx.posZ, yaw, pitch, true));
                     }

                     this.attack = true;
                     this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0APacketAnimation());
                     this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C02PacketUseEntity(entityx, C02PacketUseEntity.Action.ATTACK));
                  }
               }
            }
            break;
         case -682800726:
            if (var2.equals("spectate 1")) {
               this.packets.add(e.getPacket());
               e.setCancelled(true);
            }
            break;
         case -682800725:
            if (var2.equals("spectate 2") && this.disabled) {
               this.packets.add(e.getPacket());
               e.setCancelled(true);
            }
            break;
         case 73446974:
            if (var2.equals("intave cloud check")) {
               if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
                  e.setCancelled(true);
                  this.packets.add(e.getPacket());
               }

               try {
                  while (this.packets.size() > 50) {
                     this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(this.packets.get(0));
                     this.packets.remove(0);
                  }
               } catch (Exception var10) {
               }
            }
            break;
         case 112097665:
            if (var2.equals("verus")) {
               if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
                  e.setCancelled(true);
                  this.packets.add(e.getPacket());
               }

               while (this.packets.size() > 200) {
                  this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(this.packets.get(0));
                  this.packets.remove(0);
                  if (!this.disabled) {
                     this.disabled = true;
                     Client.INSTANCE.getCommandManager().sendChatMessage("Verus is now disabled");
                  }
               }

               if (this.mc.thePlayer.isUsingItem()) {
                  if (!this.move && this.mc.thePlayer.isSprinting()) {
                     this.move = true;
                     this.mc
                        .getNetHandler()
                        .getNetworkManager()
                        .sendPacketNoEvent(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                     if (e.getPacket() instanceof C0BPacketEntityAction) {
                        C0BPacketEntityAction packet = (C0BPacketEntityAction)e.getPacket();
                        if (packet.getAction() == C0BPacketEntityAction.Action.START_SPRINTING
                           || packet.getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
                           e.setCancelled(true);
                        }
                     }
                  }
               } else {
                  this.move = false;
               }
            }
            break;
         case 560229671:
            if (var2.equals("verus experimental") && e.getPacket() instanceof C03PacketPlayer) {
               if (this.mc.thePlayer.isRiding()) {
                  ((C03PacketPlayer)e.getPacket()).onGround = false;
               }

               if (this.disabled) {
                  ((C03PacketPlayer)e.getPacket()).onGround = false;
               }
            }
            break;
         case 2065543613:
            if (var2.equals("hypixel test")) {
               if (this.timer.hasTimeElapsed(2000.0, false) && !this.funPackets.isEmpty()) {
                  for (Packet packet : this.funPackets) {
                     ;
                  }

                  this.timer.reset();
               }

               if (e.getPacket() instanceof C0FPacketConfirmTransaction
                  || e.getPacket() instanceof C00PacketKeepAlive && !this.timer.hasTimeElapsed(2000.0, false)) {
                  this.funPackets.add(e.getPacket());
                  e.setCancelled(true);
               }
            }
      }
   }

   @EventListener
   public void onReceivePacket(EventReceivePacket e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1062808493:
            if (var2.equals("mushmc") && e.getPacket() instanceof S08PacketPlayerPosLook && this.disabled) {
               this.disabled = false;
               S08PacketPlayerPosLook var10 = (S08PacketPlayerPosLook)e.getPacket();
            }
            break;
         case -682800726:
            if (var2.equals("spectate 1")) {
               if (e.getPacket() instanceof S08PacketPlayerPosLook && !this.move) {
                  S08PacketPlayerPosLook p = (S08PacketPlayerPosLook)e.getPacket();
                  e.setCancelled(true);
                  this.mc.thePlayer.setPositionAndRotation(p.x, p.y, p.z, p.yaw, p.pitch);
                  if (ViaLoadingBase.getInstance().getTargetVersion().isNewerThanOrEqualTo(ProtocolVersion.v1_16)) {
                     this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(p.x, p.y, p.z, p.yaw, p.pitch, false));
                  } else {
                     this.mc
                        .thePlayer
                        .sendQueue
                        .addToSendQueue(
                           new C03PacketPlayer.C06PacketPlayerPosLook(
                              this.mc.thePlayer.posX,
                              this.mc.thePlayer.getEntityBoundingBox().minY,
                              this.mc.thePlayer.posZ,
                              this.mc.thePlayer.rotationYaw,
                              this.mc.thePlayer.rotationPitch,
                              false
                           )
                        );
                  }

                  e.setCancelled(true);
                  this.move = true;
                  return;
               }

               if (e.getPacket() instanceof S39PacketPlayerAbilities && this.mc.thePlayer.capabilities.allowFlying) {
                  if (!this.disabled) {
                     this.timer.reset();
                  }

                  this.disabled = true;
                  e.setCancelled(true);
                  this.packets2.add(e.getPacket());
                  return;
               }

               e.setCancelled(true);
               this.packets1.add(e.getPacket());
            }
            break;
         case 560229671:
            if (var2.equals("verus experimental")) {
               if (e.getPacket() instanceof S07PacketRespawn || e.getPacket() instanceof S01PacketJoinGame) {
                  this.packets.clear();
                  this.counter = 0;
                  this.disabled = false;
               }

               if (e.getPacket() instanceof S13PacketDestroyEntities) {
                  if (((S13PacketDestroyEntities)e.getPacket()).getEntityIDs().length != 100) {
                     int[] var13;
                     for (int entityID : var13 = ((S13PacketDestroyEntities)e.getPacket()).getEntityIDs()) {
                        if (entityID == this.mc.thePlayer.ridingEntity.getEntityId()) {
                           this.mc.timer.timerSpeed = 1.0F;
                           Client.INSTANCE.getCommandManager().sendChatMessage("Verus has been disabled");
                           this.disabled = true;
                        }
                     }
                  } else {
                     int[] var6;
                     for (int entityIDx : var6 = ((S13PacketDestroyEntities)e.getPacket()).getEntityIDs()) {
                        if (entityIDx == this.mc.thePlayer.ridingEntity.getEntityId()) {
                           Client.INSTANCE.getCommandManager().sendChatMessage("Verus has been disabled");

                           for (int i = 0; i < 20; i++) {
                              Client.INSTANCE.getCommandManager().sendChatMessage("Verus disabler have not enabled successfully");
                           }
                        }
                     }
                  }
               }

               if (e.getPacket() instanceof S1BPacketEntityAttach
                  && ((S1BPacketEntityAttach)e.getPacket()).getEntityId() == this.mc.thePlayer.getEntityId()
                  && ((S1BPacketEntityAttach)e.getPacket()).getVehicleEntityId() > 0) {
                  this.counter = 0;
               }
            }
      }
   }

   @EventListener
   public void onTick(EventTick e) {
      this.setInfo(this.mode.getMode());
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -682800726:
            if (var2.equals("spectate 1") && this.timer.hasTimeElapsed(5000.0, false)) {
               this.move = false;
               this.disabled = false;

               try {
                  while (this.packets.size() > 0) {
                     this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(this.packets.get(0));
                     this.packets.remove(this.packets.get(0));
                  }

                  while (this.packets1.size() > 0) {
                     this.packets1.get(0).processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
                     this.packets1.remove(this.packets1.get(0));
                  }

                  while (this.packets2.size() > 0) {
                     this.packets2.get(0).processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
                     this.packets2.remove(this.packets2.get(0));
                  }
               } catch (Exception var15) {
               }
            }
            break;
         case 521375265:
            if (var2.equals("hypixel motion") && this.mc.thePlayer.ticksExisted < 150) {
               this.mc
                  .thePlayer
                  .setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + Math.random() / Math.random() * Math.random() / 3.0, this.mc.thePlayer.posZ);
            }
            break;
         case 560229671:
            if (var2.equals("verus experimental")) {
               this.counter++;
               if (this.mc.thePlayer.ridingEntity != null) {
                  for (Entity entity : this.mc.theWorld.getLoadedEntityList()) {
                     if (entity instanceof EntityBoat) {
                        double deltaX = entity.posX - this.mc.thePlayer.posX;
                        double deltaY = entity.posY - this.mc.thePlayer.posY;
                        double deltaZ = entity.posZ - this.mc.thePlayer.posZ;
                        if (!(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) >= 5.0)) {
                           this.mc.timer.timerSpeed = 0.3F;
                           if (entity == this.mc.thePlayer.ridingEntity) {
                              this.mc.timer.timerSpeed = 0.3F;
                           } else {
                              int item = -1;
                              double highest = 0.0;

                              for (int i = 36; i < 45; i++) {
                                 if (this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null
                                    && (double)InventoryUtils.getItemDamage(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack()) > highest) {
                                    highest = (double)InventoryUtils.getItemDamage(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack());
                                    item = i - 36;
                                 }
                              }

                              if (highest == 0.0) {
                                 item = -1;
                              }

                              if (item == -1) {
                                 Client.INSTANCE.getCommandManager().sendChatMessage("You need to hold better weapon");
                                 return;
                              }

                              if (item != this.mc.thePlayer.inventory.currentItem) {
                                 this.mc.thePlayer.inventory.currentItem = item;
                                 return;
                              }

                              if (this.mc.thePlayer.getCurrentEquippedItem() == null
                                 || (double)InventoryUtils.getItemDamage(this.mc.thePlayer.getCurrentEquippedItem()) < 4.0) {
                                 Client.INSTANCE.getCommandManager().sendChatMessage("You need to hold better weapon");
                                 return;
                              }

                              RotationUtils.customRots = true;
                              RotationUtils.serverPitch = 90.0F;
                              this.mc.thePlayer.swingItem();
                              this.mc.playerController.attackEntity(this.mc.thePlayer, this.mc.thePlayer.ridingEntity);
                              this.mc.thePlayer.swingItem();
                              this.mc.playerController.attackEntity(this.mc.thePlayer, entity);
                           }
                        }
                     }
                  }
               }
            }
      }
   }

   @EventListener
   public void onWorldChange(EventWorldChange e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -682800726:
            if (var2.equals("spectate 1")) {
               this.packets.clear();
               this.packets1.clear();
               this.packets2.clear();
               this.toggle();
            }
            break;
         case -682800725:
            if (var2.equals("spectate 2")) {
               this.packets.clear();
               this.packets1.clear();
               this.packets2.clear();
               this.toggle();
            }
            break;
         case 112097665:
            if (var2.equals("verus")) {
               this.packets.clear();
               this.disabled = false;
            }
            break;
         case 560229671:
            if (!var2.equals("verus experimental")) {
            }
      }
   }

   @EventListener
   public void onHit(EventHit e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -741918200:
            if (var2.equals("intave old") && this.intaveReach.isEnabled()) {
               KillAuraModule killAura = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
               TeamsModule teams = (TeamsModule)Client.INSTANCE.getModuleManager().getModule(TeamsModule.class);
               EntityLivingBase entity = EntityUtils.getTargetBox(
                  8.0,
                  killAura.Targets.getMode(),
                  killAura.switchMode.getMode(),
                  500,
                  teams.isEnabled(),
                  killAura.TroughWalls.isEnabled(),
                  killAura.attackDead.isEnabled(),
                  killAura.attackInvisible.isEnabled()
               );
               if (entity == null) {
                  return;
               }

               if (EntityUtils.getDistanceToEntityBox(entity) > 3.0 && killAura.isEnabled() && entity.hurtTime <= 1 && this.mc.thePlayer.onGround) {
                  this.move = true;
               }
            }
            break;
         case 560229671:
            if (!var2.equals("verus experimental")) {
            }
      }
   }

   private boolean intaveIncoming(Packet packet) {
      return packet instanceof S00PacketKeepAlive
         || packet instanceof S32PacketConfirmTransaction
         || packet instanceof S08PacketPlayerPosLook
         || packet instanceof S12PacketEntityVelocity
         || packet instanceof S27PacketExplosion;
   }

   private boolean intaveOutgoing(Packet packet) {
      return packet instanceof C03PacketPlayer
         || packet instanceof C0FPacketConfirmTransaction
         || packet instanceof C00PacketKeepAlive
         || packet instanceof C0BPacketEntityAction;
   }

   private int getNullSlot() {
      int item = -1;

      for (int i = 36; i < 45; i++) {
         if (this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null
            && !(this.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock)) {
            item = i - 36;
         }
      }

      return item;
   }

   private boolean isInventory(C0FPacketConfirmTransaction packet) {
      return packet.uid > 0 && packet.uid < 100;
   }
}
