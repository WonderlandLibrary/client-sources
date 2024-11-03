package xyz.cucumber.base.module.feat.combat;

import de.florianmichael.viamcp.fixes.AttackOrder;
import god.buddy.aot.BCompiler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
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
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.RandomUtils;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.commands.cmds.FriendsCommand;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventAttack;
import xyz.cucumber.base.events.ext.EventClick;
import xyz.cucumber.base.events.ext.EventGameLoop;
import xyz.cucumber.base.events.ext.EventJump;
import xyz.cucumber.base.events.ext.EventLook;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMoveFlying;
import xyz.cucumber.base.events.ext.EventRenderRotation;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.events.ext.EventTimeDelay;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.movement.NoFallModule;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.feat.player.AntiFireModule;
import xyz.cucumber.base.module.feat.player.AutoArmorModule;
import xyz.cucumber.base.module.feat.player.AutoHealModule;
import xyz.cucumber.base.module.feat.player.InvManagerModule;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.module.feat.player.SmoothRotationModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.game.InventoryUtils;
import xyz.cucumber.base.utils.math.FastNoiseLite;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Automatically attack targets around you",
   name = "Kill Aura",
   key = 19,
   priority = ArrayPriority.HIGH
)
public class KillAuraModule extends Mod {
   FastNoiseLite noise = new FastNoiseLite();
   public EntityLivingBase target;
   public float fakePolarYaw;
   public float fakePolarPitch;
   public int attackTimes;
   public double yawSpeed;
   public double pitchSpeed;
   public double randomCPS;
   public Timer clickTimer = new Timer();
   public Timer cpsRandomizationTimer = new Timer();
   public Timer rotationRandomizationTimer = new Timer();
   public Timer polarRotationTimer = new Timer();
   public int intaveBlockTicks;
   public int attackTick;
   public int polar2Ticks;
   public boolean allowedToWork = false;
   public boolean blockingStatus = false;
   public boolean canSnapRotation;
   public boolean wasMaxTurn;
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
   private int autoBlockTick = 0;
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Custom", "Hypixel"});
   public ModeSettings Targets = new ModeSettings("Targets", new String[]{"Everything", "Players"});
   public ModeSettings sort = new ModeSettings("Sort", new String[]{"Distance", "health", "Smart", "Strongest player"});
   public ModeSettings autoBlock = new ModeSettings(
      "Auto Block", () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"), new String[]{"Vanilla", "Legit", "Fake", "None"}
   );
   public ModeSettings rotationMode = new ModeSettings(
      "Rotations",
      () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"),
      new String[]{"Normal", "Polar noise", "Polar sin", "Polar snap", "Polar snap legit", "Snap", "None"}
   );
   NumberSettings polarMinSpeed = new NumberSettings("Polar min speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), 0.0, 5.0, 180.0, 1.0);
   NumberSettings polarMaxSpeed = new NumberSettings(
      "Polar max speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), 180.0, 5.0, 180.0, 1.0
   );
   NumberSettings polarRandomization = new NumberSettings(
      "Polar randomization", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), 7.0, 0.0, 10.0, 1.0
   );
   NumberSettings polarRandomizationSpeed = new NumberSettings(
      "Polar random speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), 7.0, 0.0, 10.0, 1.0
   );
   BooleanSettings polarSmooth = new BooleanSettings("Polar smooth", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), true);
   BooleanSettings polarDoubleNoise = new BooleanSettings("Polar double noise", () -> this.rotationMode.getMode().equalsIgnoreCase("Polar noise"), true);
   public BooleanSettings fakeRotations = new BooleanSettings("Fake rotations", true);
   public NumberSettings minYawSpeed = new NumberSettings("Min Yaw Speed", () -> this.rotationMode.getMode().contains("Normal"), 50.0, 10.0, 180.0, 1.0);
   public NumberSettings maxYawSpeed = new NumberSettings("Max Yaw Speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Normal"), 50.0, 10.0, 180.0, 1.0);
   public NumberSettings minPitchSpeed = new NumberSettings(
      "Min Pitch Speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Normal"), 50.0, 10.0, 180.0, 1.0
   );
   public NumberSettings maxPitchSpeed = new NumberSettings(
      "Max Pitch Speed", () -> this.rotationMode.getMode().equalsIgnoreCase("Normal"), 50.0, 10.0, 180.0, 1.0
   );
   public BooleanSettings newCombat = new BooleanSettings("New Combat", () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"), false);
   public ModeSettings cpsMode = new ModeSettings(
      "CPS Mode", () -> !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"), new String[]{"Normal", "Advanced"}
   );
   public NumberSettings minCPS = new NumberSettings(
      "Min CPS",
      () -> this.cpsMode.getMode().equalsIgnoreCase("Normal") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"),
      13.0,
      1.0,
      20.0,
      1.0
   );
   public NumberSettings maxCPS = new NumberSettings(
      "Max CPS",
      () -> this.cpsMode.getMode().equalsIgnoreCase("Normal") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"),
      13.0,
      1.0,
      20.0,
      1.0
   );
   public NumberSettings minReduceCPS = new NumberSettings(
      "Min Reduce CPS",
      () -> this.cpsMode.getMode().equalsIgnoreCase("Advanced") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"),
      20.0,
      1.0,
      40.0,
      1.0
   );
   public NumberSettings maxReduceCPS = new NumberSettings(
      "Max Reduce CPS",
      () -> this.cpsMode.getMode().equalsIgnoreCase("Advanced") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"),
      20.0,
      1.0,
      40.0,
      1.0
   );
   public NumberSettings minNormalCPS = new NumberSettings(
      "Min Normal CPS",
      () -> this.cpsMode.getMode().equalsIgnoreCase("Advanced") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"),
      20.0,
      0.0,
      40.0,
      1.0
   );
   public NumberSettings maxNormalCPS = new NumberSettings(
      "Max Normal CPS",
      () -> this.cpsMode.getMode().equalsIgnoreCase("Advanced") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"),
      20.0,
      0.0,
      40.0,
      1.0
   );
   public BooleanSettings forceHit = new BooleanSettings(
      "Force Hit",
      () -> this.cpsMode.getMode().equalsIgnoreCase("Advanced") && !this.newCombat.isEnabled() && !this.mode.getMode().equalsIgnoreCase("Hypixel"),
      true
   );
   public BooleanSettings swingInRange = new BooleanSettings("Swing In Range", () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"), true);
   public ModeSettings MoveFix = new ModeSettings("Move Fix", new String[]{"Legit", "Silent", "Off"});
   public NumberSettings Range = new NumberSettings("Range", () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"), 4.0, 3.0, 8.0, 0.01);
   public NumberSettings interactRange = new NumberSettings("Interact Range", () -> !this.mode.getMode().equalsIgnoreCase("Hypixel"), 4.0, 3.0, 8.0, 0.01);
   public BooleanSettings raytrace = new BooleanSettings("Ray Trace", true);
   public ModeSettings switchMode = new ModeSettings("Switch", new String[]{"Timer", "Hurt time", "Off"});
   public NumberSettings switchDelay = new NumberSettings("Switch Delay", () -> this.switchMode.getMode().equalsIgnoreCase("Timer"), 100.0, 10.0, 1000.0, 1.0);
   public NumberSettings fov = new NumberSettings("Fov", 360.0, 10.0, 360.0, 10.0);
   public BooleanSettings disableOnDeath = new BooleanSettings("Disable on Death", true);
   public BooleanSettings TroughWalls = new BooleanSettings("Trough Walls", false);
   public BooleanSettings closedInventory = new BooleanSettings("Closed Inventory", false);
   public BooleanSettings attackInvisible = new BooleanSettings("Attack Invisible", false);
   public BooleanSettings attackDead = new BooleanSettings("Attack Dead", false);

   public KillAuraModule() {
      this.noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
      this.addSettings(new ModuleSettings[]{this.mode, this.Targets, this.sort});
      this.addSettings(new ModuleSettings[]{this.autoBlock});
      this.addSettings(new ModuleSettings[]{this.Range});
      this.addSettings(new ModuleSettings[]{this.interactRange});
      this.addSettings(
         new ModuleSettings[]{
            this.rotationMode,
            this.polarMinSpeed,
            this.polarMaxSpeed,
            this.polarRandomization,
            this.polarRandomizationSpeed,
            this.polarSmooth,
            this.polarDoubleNoise
         }
      );
      this.addSettings(new ModuleSettings[]{this.fakeRotations});
      this.addSettings(new ModuleSettings[]{this.minYawSpeed});
      this.addSettings(new ModuleSettings[]{this.maxYawSpeed});
      this.addSettings(new ModuleSettings[]{this.minPitchSpeed});
      this.addSettings(new ModuleSettings[]{this.maxPitchSpeed});
      this.addSettings(new ModuleSettings[]{this.newCombat});
      this.addSettings(new ModuleSettings[]{this.cpsMode});
      this.addSettings(new ModuleSettings[]{this.minCPS, this.maxCPS});
      this.addSettings(new ModuleSettings[]{this.minReduceCPS, this.maxReduceCPS, this.minNormalCPS, this.maxNormalCPS});
      this.addSettings(new ModuleSettings[]{this.forceHit});
      this.addSettings(new ModuleSettings[]{this.swingInRange});
      this.addSettings(new ModuleSettings[]{this.MoveFix});
      this.addSettings(new ModuleSettings[]{this.raytrace});
      this.addSettings(new ModuleSettings[]{this.TroughWalls});
      this.addSettings(new ModuleSettings[]{this.switchMode});
      this.addSettings(new ModuleSettings[]{this.switchDelay});
      this.addSettings(new ModuleSettings[]{this.fov});
      this.addSettings(new ModuleSettings[]{this.disableOnDeath});
      this.addSettings(new ModuleSettings[]{this.closedInventory});
      this.addSettings(new ModuleSettings[]{this.attackInvisible});
      this.addSettings(new ModuleSettings[]{this.attackDead});
   }

   @Override
   public void onDisable() {
      this.allowedToWork = false;
      this.mc.timer.timerSpeed = 1.0F;
      this.target = null;
      this.unBlock();
      SmoothRotationModule smoothRotation = (SmoothRotationModule)Client.INSTANCE.getModuleManager().getModule(SmoothRotationModule.class);
      if (!smoothRotation.isEnabled() || !smoothRotation.ka.isEnabled()) {
         RotationUtils.customRots = false;
         RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
         RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
      }

      if (this.mode.getMode().equalsIgnoreCase("Hypixel")) {
         if (this.isBlinking) {
            this.stopBlinking();
         }

         if (this.isBlocking) {
            this.unBlockSword();
         }
      }
   }

   @Override
   public void onEnable() {
      this.intaveBlockTicks = 0;
      this.target = null;
      RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
      RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
      this.allowedToWork = false;
      this.blockingStatus = false;
      this.canSnapRotation = false;
      if (this.canWork()) {
         this.calculateCPS();
         this.calculateRots();
      }

      this.attackTimes = 0;
      this.attackTick = 0;
   }

   @EventListener
   public void onSendPacket(EventSendPacket e) {
      if (this.mode.getMode().equalsIgnoreCase("Hypixel")) {
         if (this.mc.isIntegratedServerRunning()) {
            return;
         }

         if (this.isBlinking && !e.isCancelled()) {
            if (NON_CANCELABLE_PACKETS.contains(e.getPacket().getClass())) {
               return;
            }

            e.setCancelled(true);
            packetList.add(e.getPacket());
         }

         if (this.mc.thePlayer == null) {
            this.stopBlinking();
         }
      }
   }

   @EventListener
   public void update(EventTick e) {
      this.setInfo(this.switchMode.getMode().equalsIgnoreCase("Off") ? "Single" : "Switch");
      if ((this.mc.thePlayer.getHealth() <= 0.0F || this.mc.thePlayer.ticksExisted <= 5) && this.disableOnDeath.isEnabled()) {
         this.toggle();
      }
   }

   @EventListener
   public void onGameLoop(EventGameLoop e) {
      this.calculateCPS();
   }

   @EventListener
   public void onClick(EventClick e) {
      if (this.canWork()) {
         this.calculateCPS();
         this.calculateRots();
         this.attackLoop();
      }

      this.attackTick++;
   }

   @EventListener
   public void onTimeDelay(EventTimeDelay e) {
      if (this.attackTick != 0) {
         this.attackTick = 0;
      } else {
         if (this.canWork()) {
            this.calculateCPS();
            this.calculateRots();
            this.attackLoop();
         }
      }
   }

   @EventListener
   public void onRotationRender(EventRenderRotation e) {
      label39:
      if (this.allowedToWork && RotationUtils.customRots) {
         String var2;
         switch ((var2 = this.rotationMode.getMode().toLowerCase()).hashCode()) {
            case -1995796987:
               if (!var2.equals("polar snap legit")) {
                  break label39;
               }
               break;
            case -1039745817:
               if (var2.equals("normal")) {
                  e.setYaw(RotationUtils.serverYaw);
                  e.setPitch(RotationUtils.serverPitch);
               }
               break label39;
            case -998591560:
               if (!var2.equals("polar noise")) {
                  break label39;
               }
               break;
            case -863348884:
               if (!var2.equals("polar snap")) {
                  break label39;
               }
               break;
            case -443492106:
               if (!var2.equals("polar sin")) {
                  break label39;
               }
               break;
            case 3534794:
               if (var2.equals("snap") && this.canSnapRotation) {
                  e.setYaw(RotationUtils.serverYaw);
                  e.setPitch(RotationUtils.serverPitch);
               }
            default:
               break label39;
         }

         if (this.fakeRotations.isEnabled()) {
            e.setYaw(this.fakePolarYaw);
            e.setYaw(this.fakePolarPitch);
         } else {
            e.setYaw(RotationUtils.serverYaw);
            e.setPitch(RotationUtils.serverPitch);
         }
      }

      this.block(this.target, "Render");
   }

   @EventListener
   public void onLook(EventLook e) {
      if (this.allowedToWork && RotationUtils.customRots) {
         label47: {
            String var3;
            switch ((var3 = this.rotationMode.getMode().toLowerCase()).hashCode()) {
               case -1995796987:
                  if (!var3.equals("polar snap legit")) {
                     return;
                  }
                  break;
               case -1039745817:
                  if (!var3.equals("normal")) {
                     return;
                  }
                  break label47;
               case -998591560:
                  if (!var3.equals("polar noise")) {
                     return;
                  }
                  break;
               case -863348884:
                  if (!var3.equals("polar snap")) {
                     return;
                  }
                  break;
               case -443492106:
                  if (!var3.equals("polar sin")) {
                     return;
                  }
                  break;
               case 3534794:
                  if (!var3.equals("snap")) {
                     return;
                  }
                  break label47;
               default:
                  return;
            }

            e.setYaw(RotationUtils.serverYaw);
            e.setPitch(RotationUtils.serverPitch);
            return;
         }

         e.setYaw(RotationUtils.serverYaw);
         e.setPitch(RotationUtils.serverPitch);
      }
   }

   @EventListener
   public void onJump(EventJump e) {
      if (this.allowedToWork && !this.MoveFix.getMode().equalsIgnoreCase("Off") && RotationUtils.customRots) {
         String var2;
         switch ((var2 = this.rotationMode.getMode().toLowerCase()).hashCode()) {
            case -1995796987:
               if (!var2.equals("polar snap legit")) {
                  return;
               }
               break;
            case -1039745817:
               if (var2.equals("normal")) {
                  e.setYaw(RotationUtils.serverYaw);
               }

               return;
            case -998591560:
               if (!var2.equals("polar noise")) {
                  return;
               }
               break;
            case -863348884:
               if (!var2.equals("polar snap")) {
                  return;
               }
               break;
            case -443492106:
               if (!var2.equals("polar sin")) {
                  return;
               }
               break;
            case 3534794:
               if (var2.equals("snap") && this.canSnapRotation) {
                  e.setYaw(RotationUtils.serverYaw);
               }

               return;
            default:
               return;
         }

         e.setYaw(RotationUtils.serverYaw);
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMotion(EventMotion e) {
      if (this.allowedToWork && RotationUtils.customRots) {
         if (e.getType() == EventType.PRE) {
            String var2;
            switch ((var2 = this.rotationMode.getMode().toLowerCase()).hashCode()) {
               case -1995796987:
                  if (!var2.equals("polar snap legit")) {
                     return;
                  }
                  break;
               case -1039745817:
                  if (var2.equals("normal")) {
                     e.setYaw(RotationUtils.serverYaw);
                     e.setPitch(RotationUtils.serverPitch);
                  }

                  return;
               case -998591560:
                  if (!var2.equals("polar noise")) {
                     return;
                  }
                  break;
               case -863348884:
                  if (!var2.equals("polar snap")) {
                     return;
                  }
                  break;
               case -443492106:
                  if (!var2.equals("polar sin")) {
                     return;
                  }
                  break;
               case 3534794:
                  if (var2.equals("snap") && this.canSnapRotation) {
                     e.setYaw(RotationUtils.serverYaw);
                     e.setPitch(RotationUtils.serverPitch);
                  }

                  return;
               default:
                  return;
            }

            e.setYaw(RotationUtils.serverYaw);
            e.setPitch(RotationUtils.serverPitch);
         } else if (this.allowedToWork) {
            this.block(this.target, "Post");
         }
      }
   }

   @EventListener
   public void onMove(EventMoveFlying e) {
      if (this.allowedToWork && RotationUtils.customRots) {
         String var2;
         switch ((var2 = this.MoveFix.getMode().toLowerCase()).hashCode()) {
            case -902327211:
               if (var2.equals("silent") && !this.mc.isSingleplayer()) {
                  e.setCancelled(true);
                  MovementUtils.silentMoveFix(e);
               }
               break;
            case 102851513:
               if (var2.equals("legit")) {
                  if (this.rotationMode.getMode().equalsIgnoreCase("Snap")) {
                     if (this.canSnapRotation) {
                        e.setYaw(RotationUtils.serverYaw);
                     }
                  } else {
                     e.setYaw(RotationUtils.serverYaw);
                  }
               }
         }
      }
   }

   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void attackLoop() {
      if (this.mode.getMode().equalsIgnoreCase("Hypixel")) {
         this.hypixelAttackLoop();
      } else if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         this.block(this.target, "Before");
         MovingObjectPosition ray = this.mc.thePlayer.rayTraceCustom(3.0, this.mc.timer.renderPartialTicks, RotationUtils.serverYaw, RotationUtils.serverPitch);
         if (this.attackTimes > 0) {
            int attacks = this.attackTimes;
            if (!((double)this.mc.thePlayer.getDistanceToEntity(this.target) <= this.Range.getValue())
               && !(EntityUtils.getDistanceToEntityBox(this.target) <= this.Range.getValue())
               && this.mc.objectMouseOver.entityHit != this.target
               && ray.entityHit != this.target
               && RotationUtils.rayTrace(this.Range.getValue(), new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch}) != this.target) {
               boolean shouldReset = true;
               if ((
                     (double)this.mc.thePlayer.getDistanceToEntity(this.target) <= this.interactRange.getValue()
                        || EntityUtils.getDistanceToEntityBox(this.target) <= this.interactRange.getValue()
                  )
                  && this.swingInRange.isEnabled()
                  && !this.newCombat.isEnabled()) {
                  for (int i = 0; i < attacks; i++) {
                     this.mc.clickMouseEvent();
                     this.attackTimes--;
                     shouldReset = false;
                  }
               }

               if (shouldReset) {
                  this.attackTimes = 0;
               }
            } else if (!this.rotationMode.getMode().equalsIgnoreCase("None")) {
               for (int i = 0; i < attacks; i++) {
                  if (this.raytrace.isEnabled()) {
                     Entity rayTracedEntity = RotationUtils.rayTrace(this.Range.getValue(), new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                     if (rayTracedEntity == null) {
                        rayTracedEntity = ray.entityHit;
                     }

                     if (rayTracedEntity == null) {
                        rayTracedEntity = this.mc.objectMouseOver.entityHit;
                     }

                     if (rayTracedEntity != null) {
                        if (rayTracedEntity instanceof EntityPlayer && EntityUtils.isInSameTeam((EntityPlayer)rayTracedEntity)) {
                           return;
                        }

                        if (rayTracedEntity instanceof EntityPlayer && FriendsCommand.friends.contains(rayTracedEntity.getName())) {
                           return;
                        }

                        if (rayTracedEntity != null && this.target != null) {
                           EventAttack event = new EventAttack(this.target);
                           Client.INSTANCE.getEventBus().call(event);
                           this.block(rayTracedEntity, "Before Attack");
                           AttackOrder.sendLegitFixedKillAuraAttack(this.mc.thePlayer, rayTracedEntity);
                           event.setType(EventType.POST);
                           Client.INSTANCE.getEventBus().call(event);
                           this.block(this.target, "After Attack");
                        } else if (this.swingInRange.isEnabled() && !this.newCombat.isEnabled()) {
                           this.mc.clickMouseEvent();
                        }
                     }
                  }
               }
            } else {
               for (int ix = 0; ix < attacks; ix++) {
                  this.block(this.target, "Before Attack");
                  EventAttack event = new EventAttack(this.target);
                  Client.INSTANCE.getEventBus().call(event);
                  AttackOrder.sendLegitFixedKillAuraAttack(this.mc.thePlayer, this.target);
                  event.setType(EventType.POST);
                  Client.INSTANCE.getEventBus().call(event);
                  this.block(this.target, "After Attack");
               }
            }

            this.attackTimes = 0;
         }

         this.block(this.target, "After");
      }
   }

   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void hypixelAttackLoop() {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         this.minYawSpeed.setValue(180.0);
         this.maxYawSpeed.setValue(180.0);
         this.minPitchSpeed.setValue(180.0);
         this.maxPitchSpeed.setValue(180.0);
         this.Range.setValue(3.2);
         this.interactRange.setValue(3.2);
         this.newCombat.setEnabled(false);
         this.rotationMode.setMode("Normal");
         this.swingInRange.setEnabled(false);
         MovingObjectPosition ray = this.mc.thePlayer.rayTraceCustom(3.0, this.mc.timer.renderPartialTicks, RotationUtils.serverYaw, RotationUtils.serverPitch);
         if ((
               (double)this.mc.thePlayer.getDistanceToEntity(this.target) <= this.Range.getValue()
                  || EntityUtils.getDistanceToEntityBox(this.target) <= this.Range.getValue()
                  || this.mc.objectMouseOver.entityHit == this.target
                  || ray.entityHit == this.target
                  || RotationUtils.rayTrace(this.Range.getValue(), new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch}) == this.target
            )
            && this.target != null) {
            this.autoBlockTick++;
            switch (this.autoBlockTick) {
               case 1:
                  this.mc.playerController.syncCurrentPlayItem();
                  if (this.isBlinking) {
                     this.stopBlinking();
                     this.isBlinking = false;
                  }

                  EventAttack event = new EventAttack(this.target);
                  Client.INSTANCE.getEventBus().call(event);
                  this.mc.thePlayer.swingItem();
                  this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.ATTACK));
                  event.setType(EventType.POST);
                  Client.INSTANCE.getEventBus().call(event);
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

         if (this.mc.thePlayer.getHeldItem() == null || !(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {
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

   public void calculateCPS() {
      if (this.newCombat.isEnabled()) {
         double delay = 4.0;
         if (this.mc.thePlayer.getHeldItem() != null) {
            Item item = this.mc.thePlayer.getHeldItem().getItem();
            if (item instanceof ItemSpade || item == Items.golden_axe || item == Items.diamond_axe || item == Items.wooden_hoe || item == Items.golden_hoe) {
               delay = 20.0;
            }

            if (item == Items.wooden_axe || item == Items.stone_axe) {
               delay = 25.0;
            }

            if (item instanceof ItemSword) {
               delay = 12.0;
            }

            if (item instanceof ItemPickaxe) {
               delay = 17.0;
            }

            if (item == Items.iron_axe) {
               delay = 22.0;
            }

            if (item == Items.stone_hoe) {
               delay = 10.0;
            }

            if (item == Items.iron_hoe) {
               delay = 7.0;
            }
         }

         delay *= 50.0;
         if (this.clickTimer.hasTimeElapsed(delay, true)) {
            this.attackTimes++;
         }
      } else if (this.cpsMode.getMode().equalsIgnoreCase("Advanced")) {
         this.advancedClick();
      } else {
         this.normalClick();
      }
   }

   public void normalClick() {
      if (this.clickTimer.hasTimeElapsed(this.calculateCPS(this.minCPS.getValue(), this.maxCPS.getValue()), true)) {
         this.attackTimes++;
      }
   }

   public void advancedClick() {
      int perfectHitHurtTime = 2;
      boolean stop = false;
      if (this.forceHit.isEnabled()) {
         Entity rayTracedEntity = RotationUtils.rayTrace(this.Range.getValue(), new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
         if (this.raytrace.isEnabled()) {
            if ((rayTracedEntity == this.target || this.mc.objectMouseOver.entityHit == this.target)
               && this.target != null
               && rayTracedEntity instanceof EntityLivingBase) {
               EntityLivingBase entity = (EntityLivingBase)rayTracedEntity;
               if (entity.hurtTime <= perfectHitHurtTime) {
                  this.attackTimes = 1;
                  stop = true;
               }
            }
         } else if (this.target != null && this.target.hurtTime <= perfectHitHurtTime) {
            this.attackTimes = 1;
            stop = true;
         }
      }

      if (!stop) {
         if (this.mc.thePlayer.hurtTime == 0) {
            if (this.clickTimer.hasTimeElapsed(this.calculateCPS(this.minNormalCPS.getValue(), this.maxNormalCPS.getValue()), true)) {
               this.attackTimes++;
            }
         } else if (this.clickTimer.hasTimeElapsed(this.calculateCPS(this.minReduceCPS.getValue(), this.maxReduceCPS.getValue()), true)) {
            this.attackTimes++;
         }
      }
   }

   public double calculateCPS(double min, double max) {
      double minValue = min;
      double maxValue = max;
      if (min == 0.0 && max == 0.0) {
         return 100000.0;
      } else {
         if (min > max) {
            minValue = max;
         }

         if (max < minValue) {
            maxValue = minValue;
         }

         if (this.cpsRandomizationTimer.hasTimeElapsed(150.0, true)) {
            this.randomCPS = (double)Math.round(minValue + new Random().nextDouble() * (maxValue - minValue));
         }

         double delay = 1000.0 / this.randomCPS;
         delay -= 3.0;
         return (double)Math.round(delay);
      }
   }

   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void calculateRots() {
      boolean wasSet = RotationUtils.customRots;
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         label117: {
            String var2;
            switch ((var2 = this.rotationMode.getMode().toLowerCase()).hashCode()) {
               case -1995796987:
                  if (!var2.equals("polar snap legit")) {
                     break label117;
                  }

                  float[] rotsx = new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch};
                  float turnx = (float)RandomUtils.nextInt(10, 60);
                  double randomizationx = 3.0;
                  double randomizationSpeedx = 10.0;
                  if (this.mc.objectMouseOver.entityHit == null && EntityUtils.getDistanceToEntityBox(this.target) <= 3.0) {
                     rotsx = RotationUtils.getNormalAuraRotations(
                        RotationUtils.serverYaw,
                        RotationUtils.serverPitch,
                        this.target,
                        this.target.posX,
                        this.target.posY,
                        this.target.posZ,
                        turnx,
                        turnx,
                        false
                     );
                     float smoothness = 2.0F;
                     rotsx[0] = (RotationUtils.serverYaw * smoothness + (rotsx[0] - RotationUtils.serverYaw)) / smoothness;
                     rotsx[1] = (RotationUtils.serverPitch * smoothness + (rotsx[1] - RotationUtils.serverPitch)) / smoothness;
                  }

                  float noiseX1x = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeedx);
                  float noiseY1x = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeedx) + 50.0F;
                  float noiseX2x = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeedx) + 50.0F;
                  float noiseY2x = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeedx) + 100.0F;
                  float noiseYawx = (float)((double)this.noise.GetNoise(noiseX1x, noiseY1x) * randomizationx);
                  float noisePitchx = (float)((double)this.noise.GetNoise(noiseX2x, noiseY2x) * randomizationx);
                  rotsx[0] += noiseYawx;
                  rotsx[1] += noisePitchx;
                  rotsx = RotationUtils.getFixedRotation(rotsx, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                  RotationUtils.serverYaw = rotsx[0];
                  RotationUtils.serverPitch = rotsx[1];
                  RotationUtils.customRots = true;
                  float[] fakeRots = RotationUtils.getInstantTargetRotation(this.target, this.target.posX, this.target.posY + 0.5, this.target.posZ, false);
                  this.fakePolarYaw = fakeRots[0];
                  this.fakePolarPitch = fakeRots[1];
                  break;
               case -1039745817:
                  if (var2.equals("normal")) {
                     double minYaw = this.minYawSpeed.getValue() / 2.0;
                     double maxYaw = this.maxYawSpeed.getValue() / 2.0;
                     double minPitch = this.minPitchSpeed.getValue() / 2.0;
                     double maxPitch = this.maxPitchSpeed.getValue() / 2.0;
                     if (minYaw > maxYaw) {
                        minYaw = maxYaw;
                     }

                     if (maxYaw < minYaw) {
                        maxYaw = minYaw;
                     }

                     if (minPitch > maxPitch) {
                        minPitch = maxPitch;
                     }

                     if (maxPitch < minPitch) {
                        maxPitch = minPitch;
                     }

                     if (this.rotationRandomizationTimer.hasTimeElapsed(150.0, true)) {
                        this.yawSpeed = minYaw + new Random().nextDouble() * (maxYaw - minYaw);
                        this.pitchSpeed = minPitch + new Random().nextDouble() * (maxPitch - minPitch);
                     }

                     if (this.yawSpeed < minYaw) {
                        this.yawSpeed = minYaw;
                     }

                     if (this.yawSpeed > maxYaw) {
                        this.yawSpeed = maxYaw;
                     }

                     if (this.pitchSpeed < minPitch) {
                        this.pitchSpeed = minPitch;
                     }

                     if (this.pitchSpeed > maxPitch) {
                        this.pitchSpeed = maxPitch;
                     }

                     float[] rotsx = RotationUtils.getNormalAuraRotations(
                        RotationUtils.serverYaw,
                        RotationUtils.serverPitch,
                        this.target,
                        this.target.posX,
                        this.target.posY,
                        this.target.posZ,
                        (float)this.yawSpeed,
                        (float)this.pitchSpeed,
                        true
                     );
                     rotsx = RotationUtils.getFixedRotation(rotsx, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                     RotationUtils.serverYaw = rotsx[0];
                     RotationUtils.serverPitch = rotsx[1];
                     RotationUtils.customRots = true;
                  }
                  break label117;
               case -998591560:
                  if (var2.equals("polar noise")) {
                     float minSpeed = (float)Math.min(this.polarMinSpeed.getValue(), this.polarMaxSpeed.getValue());
                     float maxSpeed = (float)Math.max(this.polarMinSpeed.getValue(), this.polarMaxSpeed.getValue());
                     float turn = RandomUtils.nextFloat(minSpeed, maxSpeed);
                     double randomization = this.polarRandomization.getValue();
                     double randomizationSpeed = this.polarRandomizationSpeed.getValue();
                     float[] rots = RotationUtils.getNormalAuraRotations(
                        RotationUtils.serverYaw,
                        RotationUtils.serverPitch,
                        this.target,
                        this.target.posX,
                        this.target.posY,
                        this.target.posZ,
                        turn,
                        turn,
                        false
                     );
                     float noiseX1 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed);
                     float noiseY1 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 50.0F;
                     float noiseX2 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 50.0F;
                     float noiseY2 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 100.0F;
                     float noiseYaw = (float)((double)this.noise.GetNoise(noiseX1, noiseY1) * randomization);
                     float noisePitch = (float)((double)this.noise.GetNoise(noiseX2, noiseY2) * randomization);
                     noiseYaw *= Math.max(1.0F, Math.min(3.0F, this.mc.thePlayer.getDistanceToEntity(this.target)) / 3.0F);
                     noisePitch *= Math.max(1.0F, Math.min(3.0F, this.mc.thePlayer.getDistanceToEntity(this.target)) / 3.0F);
                     rots[0] += noiseYaw;
                     rots[1] += noisePitch;
                     if (this.polarDoubleNoise.isEnabled()) {
                        float noiseX12 = (float)(this.mc.thePlayer.ticksExisted * 18);
                        float noiseY12 = (float)(this.mc.thePlayer.ticksExisted * 18) + 150.0F;
                        float noiseX22 = (float)(this.mc.thePlayer.ticksExisted * 18) + 150.0F;
                        float noiseY22 = (float)(this.mc.thePlayer.ticksExisted * 18) + 200.0F;
                        float noiseYaw2 = this.noise.GetNoise(noiseX12, noiseY12) * 2.0F;
                        float noisePitch2 = this.noise.GetNoise(noiseX22, noiseY22) * 2.0F;
                        rots[0] += noiseYaw2;
                        rots[1] += noisePitch2;
                     }

                     if (this.polarSmooth.isEnabled()) {
                        float smoothness = 2.0F;
                        rots[0] = (RotationUtils.serverYaw * smoothness + (rots[0] - RotationUtils.serverYaw)) / smoothness;
                        rots[1] = (RotationUtils.serverPitch * smoothness + (rots[1] - RotationUtils.serverPitch)) / smoothness;
                     }

                     rots = RotationUtils.getFixedRotation(rots, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                     RotationUtils.serverYaw = rots[0];
                     RotationUtils.serverPitch = rots[1];
                     RotationUtils.customRots = true;
                     float[] fakeRots = RotationUtils.getInstantTargetRotation(this.target, this.target.posX, this.target.posY + 0.5, this.target.posZ, false);
                     this.fakePolarYaw = fakeRots[0];
                     this.fakePolarPitch = fakeRots[1];
                  }
                  break label117;
               case -863348884:
                  if (var2.equals("polar snap")) {
                     if (this.mc.objectMouseOver.entityHit == null && EntityUtils.getDistanceToEntityBox(this.target) <= 3.0) {
                        float turn = 180.0F;
                        double randomization = 7.0;
                        double randomizationSpeed = 9.0;
                        float[] rots = RotationUtils.getNormalAuraRotations(
                           RotationUtils.serverYaw,
                           RotationUtils.serverPitch,
                           this.target,
                           this.target.posX,
                           this.target.posY,
                           this.target.posZ,
                           turn,
                           turn,
                           false
                        );
                        float noiseX1 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed);
                        float noiseY1 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 50.0F;
                        float noiseX2 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 50.0F;
                        float noiseY2 = (float)((double)this.mc.thePlayer.ticksExisted * randomizationSpeed) + 100.0F;
                        float noiseYaw = (float)((double)this.noise.GetNoise(noiseX1, noiseY1) * randomization);
                        float noisePitch = (float)((double)this.noise.GetNoise(noiseX2, noiseY2) * randomization);
                        rots[0] += noiseYaw;
                        rots[1] += noisePitch;
                        rots = RotationUtils.getFixedRotation(rots, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                        RotationUtils.serverYaw = rots[0];
                        RotationUtils.serverPitch = rots[1];
                     }

                     RotationUtils.customRots = true;
                     float[] fakeRots = RotationUtils.getInstantTargetRotation(this.target, this.target.posX, this.target.posY + 0.5, this.target.posZ, false);
                     this.fakePolarYaw = fakeRots[0];
                     this.fakePolarPitch = fakeRots[1];
                  }
                  break label117;
               case -443492106:
                  if (!var2.equals("polar sin")) {
                     break label117;
                  }
                  break;
               case 3534794:
                  if (var2.equals("snap")) {
                     if (this.attackTimes > 0) {
                        this.canSnapRotation = true;
                     } else {
                        this.canSnapRotation = false;
                     }

                     if (this.canSnapRotation) {
                        float[] rots = RotationUtils.getInstantTargetRotation(this.target, this.target.posX, this.target.posY, this.target.posZ, true);
                        rots = RotationUtils.getFixedRotation(rots, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
                        RotationUtils.serverYaw = rots[0];
                        RotationUtils.serverPitch = rots[1];
                        RotationUtils.customRots = true;
                     } else {
                        RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
                        RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
                        RotationUtils.customRots = false;
                     }
                  }
               default:
                  break label117;
            }

            float[] rotsx = RotationUtils.getNormalAuraRotations(
               (float)((double)RotationUtils.serverYaw - Math.sin((double)(this.mc.thePlayer.ticksExisted / 2)) * 4.0),
               (float)((double)RotationUtils.serverPitch - Math.cos((double)(this.mc.thePlayer.ticksExisted / 2)) * 4.0),
               this.target,
               this.target.posX,
               this.target.posY,
               this.target.posZ,
               55.0F,
               40.0F,
               true
            );
            rotsx[0] += (float)(Math.sin((double)(this.mc.thePlayer.ticksExisted / 2)) * 4.0);
            rotsx[1] += (float)(Math.cos((double)(this.mc.thePlayer.ticksExisted / 2)) * 4.0);
            rotsx = RotationUtils.getFixedRotation(rotsx, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
            RotationUtils.serverYaw = rotsx[0];
            RotationUtils.serverPitch = rotsx[1];
            RotationUtils.customRots = true;
            float[] fakeRots = RotationUtils.getInstantTargetRotation(this.target, this.target.posX, this.target.posY + 0.5, this.target.posZ, false);
            this.fakePolarYaw = fakeRots[0];
            this.fakePolarPitch = fakeRots[1];
         }

         if (RotationUtils.serverPitch > 90.0F) {
            RotationUtils.serverPitch = 90.0F;
         }

         if (RotationUtils.serverPitch < -90.0F) {
            RotationUtils.serverPitch = -90.0F;
         }

         if (!wasSet && RotationUtils.customRots && this.rotationMode.getMode().contains("Polar")) {
            RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
            RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
         }
      }
   }

   public boolean canWork() {
      if (this.closedInventory.isEnabled()) {
         InvManagerModule invManager = (InvManagerModule)Client.INSTANCE.getModuleManager().getModule(InvManagerModule.class);
         AutoArmorModule autoArmor = (AutoArmorModule)Client.INSTANCE.getModuleManager().getModule(AutoArmorModule.class);
         AutoHealModule autoHeal = (AutoHealModule)Client.INSTANCE.getModuleManager().getModule(AutoHealModule.class);
         AntiFireModule antiFire = (AntiFireModule)Client.INSTANCE.getModuleManager().getModule(AntiFireModule.class);
         NoFallModule noFall = (NoFallModule)Client.INSTANCE.getModuleManager().getModule(NoFallModule.class);
         ScaffoldModule scaffold = (ScaffoldModule)Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class);
         AnnoyerModule annoyer = (AnnoyerModule)Client.INSTANCE.getModuleManager().getModule(AnnoyerModule.class);
         AutoRodModule autoRod = (AutoRodModule)Client.INSTANCE.getModuleManager().getModule(AutoRodModule.class);
         if (this.mc.currentScreen != null) {
            if (this.allowedToWork) {
               RotationUtils.customRots = false;
               this.unBlock();
            }

            this.allowedToWork = false;
            this.attackTimes = 0;
            return false;
         }

         if ((
                  invManager.mode.getMode().equalsIgnoreCase("Spoof")
                     || autoArmor.mode.getMode().equalsIgnoreCase("Spoof")
                     || autoHeal.mode.getMode().equalsIgnoreCase("Spoof")
               )
               && InventoryUtils.isInventoryOpen
            || autoHeal.cancelAura
            || antiFire.canWork
            || noFall.canWork
            || annoyer.cancel
            || autoRod.cancel
            || scaffold.isEnabled()) {
            if (this.allowedToWork) {
               RotationUtils.customRots = false;
               this.unBlock();
            }

            this.allowedToWork = false;
            this.attackTimes = 0;
            return false;
         }
      }

      this.target = EntityUtils.getTarget(
         Math.max(this.Range.getValue(), this.interactRange.getValue()),
         this.Targets.getMode(),
         this.switchMode.getMode(),
         (int)this.switchDelay.getValue(),
         Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
         this.TroughWalls.isEnabled(),
         this.attackInvisible.isEnabled(),
         this.attackDead.isEnabled()
      );
      if (this.target == null) {
         this.target = EntityUtils.getTargetBox(
            Math.max(this.Range.getValue(), this.interactRange.getValue()),
            this.Targets.getMode(),
            this.switchMode.getMode(),
            (int)this.switchDelay.getValue(),
            Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
            this.TroughWalls.isEnabled(),
            this.attackInvisible.isEnabled(),
            this.attackDead.isEnabled()
         );
      }

      if (this.target == null) {
         if (this.allowedToWork) {
            if (!Client.INSTANCE.getModuleManager().getModule(SmoothRotationModule.class).isEnabled()) {
               RotationUtils.customRots = false;
            }

            this.unBlock();
         }

         this.allowedToWork = false;
         this.attackTimes = 0;
         return false;
      } else {
         if (!this.allowedToWork) {
            RotationUtils.serverYaw = this.mc.thePlayer.rotationYaw;
            RotationUtils.serverPitch = this.mc.thePlayer.rotationPitch;
         }

         this.allowedToWork = true;
         return true;
      }
   }

   public void unBlock() {
      if (this.mode.getMode().equalsIgnoreCase("Hypixel")) {
         if (this.autoBlockTick == 1) {
            this.autoBlockTick = 0;
         }

         if (this.isBlinking) {
            this.stopBlinking();
         }

         if (this.isBlocking) {
            this.unBlockSword();
         }
      } else if (!this.autoBlock.getMode().equalsIgnoreCase("Fake")) {
         this.mc.gameSettings.keyBindUseItem.pressed = false;
         this.blockingStatus = false;
      }
   }

   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void block(Entity ent, String timing) {
      if (this.mc.thePlayer != null && this.mc.theWorld != null && this.allowedToWork) {
         if (this.mc.thePlayer.getHeldItem() != null
            && this.mc.thePlayer.getHeldItem().getItem() != null
            && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            String var3;
            switch ((var3 = this.autoBlock.getMode().toLowerCase()).hashCode()) {
               case 3135317:
                  if (var3.equals("fake")) {
                     if (!this.blockingStatus && this.mc.gameSettings.keyBindUseItem.pressed) {
                        this.mc.gameSettings.keyBindUseItem.pressed = false;
                     }

                     this.mc.thePlayer.getHeldItem().useItemRightClick(this.mc.theWorld, this.mc.thePlayer);
                     this.blockingStatus = true;
                  }
                  break;
               case 102851513:
                  if (var3.equals("legit") && timing.equalsIgnoreCase("Before")) {
                     if (this.mc.thePlayer.hurtTime >= 6) {
                        this.mc.gameSettings.keyBindUseItem.pressed = true;
                        this.blockingStatus = true;
                     } else if (this.mc.thePlayer.hurtTime > 0) {
                        this.mc.gameSettings.keyBindUseItem.pressed = false;
                        this.blockingStatus = false;
                     }
                  }
                  break;
               case 233102203:
                  if (var3.equals("vanilla")) {
                     this.mc.gameSettings.keyBindUseItem.pressed = true;
                     this.blockingStatus = true;
                  }
            }
         }
      }
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

   public void blockSword(boolean interact) {
      boolean canBlock = !this.isBlocking
         && this.mc.thePlayer.inventory.getCurrentItem() != null
         && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
      if (canBlock) {
         if (interact) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.INTERACT));
         }

         this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getHeldItem()));
      }

      this.isBlocking = true;
   }

   public void unBlockSword() {
      boolean canBlock = this.isBlocking
         && this.mc.thePlayer.inventory.getCurrentItem() != null
         && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
      if (canBlock) {
         this.mc
            .thePlayer
            .sendQueue
            .addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      }

      this.isBlocking = false;
   }
}
