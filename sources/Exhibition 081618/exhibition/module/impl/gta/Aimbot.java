package exhibition.module.impl.gta;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventPacket;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.RotationUtils;
import exhibition.util.TeamUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.Vec3;

public class Aimbot extends Module {
   public int ticks;
   public int lookDelay;
   private EntityPlayer target;
   public int buffer = 10;
   private Map playerPositions = new HashMap();
   public static boolean isFiring;
   private String ANTISPREAD = "ANTISPREAD";
   private String ANTIRECOIL = "ANTIRECOIL";
   private String YAWRECOIL = "YAWRECOIL";
   private String PITCHRECOIL = "PITCHRECOIL";
   private String SILENT = "SILENT";
   private String DELAY = "DELAY";
   private String AUTOFIRE = "AUTOFIRE";
   private String AIMSTEP = "AIMSTEP";
   private String FOV = "FOV";

   public Aimbot(ModuleData data) {
      super(data);
      this.settings.put(this.ANTISPREAD, new Setting(this.ANTISPREAD, true, "Reduces weapon spread."));
      this.settings.put(this.ANTIRECOIL, new Setting(this.ANTIRECOIL, true, "Reduces weapon recoil."));
      this.settings.put(this.SILENT, new Setting(this.SILENT, true, "Aims silently."));
      this.settings.put(this.DELAY, new Setting(this.DELAY, Integer.valueOf(3), "Tick delay before firing again. Good for high recoil weapons.", 1.0D, 0.0D, 10.0D));
      this.settings.put(this.AUTOFIRE, new Setting(this.AUTOFIRE, true, "Automatically fires for you."));
      this.settings.put(this.FOV, new Setting(this.FOV, Integer.valueOf(90), "FOV check for ragebot.", 15.0D, 0.0D, 360.0D));
      this.settings.put(this.YAWRECOIL, new Setting(this.YAWRECOIL, 1.5D, "Yaw recoil scale.", 0.1D, 0.0D, 3.0D));
      this.settings.put(this.PITCHRECOIL, new Setting(this.PITCHRECOIL, 1.5D, "Yaw recoil scale.", 0.1D, 0.0D, 3.0D));
   }

   public boolean isVisibleFOV(EntityLivingBase e, EntityLivingBase e2, float fov) {
      return (Math.abs(RotationUtils.getRotations(e)[0] - e2.rotationYaw) % 360.0F > 180.0F ? 360.0F - Math.abs(RotationUtils.getRotations(e)[0] - e2.rotationYaw) % 360.0F : Math.abs(RotationUtils.getRotations(e)[0] - e2.rotationYaw) % 360.0F) <= fov;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class, EventPacket.class}
   )
   public void onEvent(Event event) {
      if (isFiring) {
         isFiring = false;
      }

      if (event instanceof EventMotionUpdate && mc.thePlayer.isEntityAlive()) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre()) {
            double targetWeight = Double.NEGATIVE_INFINITY;
            this.target = null;
            Iterator var5 = mc.theWorld.getLoadedEntityList().iterator();

            Object o;
            EntityPlayer p;
            while(var5.hasNext()) {
               o = var5.next();
               if (o instanceof EntityPlayer) {
                  p = (EntityPlayer)o;
                  if (p != mc.thePlayer && !FriendManager.isFriend(p.getName()) && p.ticksExisted > 20 && !p.isInvisible() && !TeamUtils.isTeam(mc.thePlayer, p) && mc.thePlayer.canEntityBeSeen(p) && this.isVisibleFOV(mc.thePlayer, p, ((Number)((Setting)this.settings.get(this.FOV)).getValue()).floatValue())) {
                     if (this.target == null) {
                        this.target = p;
                        targetWeight = this.getTargetWeight(p);
                     } else if (this.getTargetWeight(p) > targetWeight) {
                        this.target = p;
                        targetWeight = this.getTargetWeight(p);
                     }
                  }
               }
            }

            Object[] var13 = this.playerPositions.keySet().toArray();
            int var15 = var13.length;

            for(int var17 = 0; var17 < var15; ++var17) {
               o = var13[var17];
               EntityPlayer player = (EntityPlayer)o;
               if (!mc.theWorld.playerEntities.contains(player)) {
                  this.playerPositions.remove(player);
               }
            }

            var5 = mc.theWorld.playerEntities.iterator();

            while(true) {
               List previousPositions;
               do {
                  if (!var5.hasNext()) {
                     if (this.target != null) {
                        boolean recoil = ((Boolean)((Setting)this.settings.get(this.ANTIRECOIL)).getValue()).booleanValue();
                        if (recoil && this.ticks >= 30) {
                           this.ticks = 0;
                        }

                        EntityLivingBase simulated = (EntityLivingBase)this.predictPlayerMovement(this.target);
                        float[] rotations = RotationUtils.getRotations(simulated);
                        if (recoil) {
                           float yaw = rotations[0] + ((Number)((Setting)this.settings.get(this.YAWRECOIL)).getValue()).floatValue() * (float)this.ticks;
                           float pitch = rotations[1] + ((Number)((Setting)this.settings.get(this.PITCHRECOIL)).getValue()).floatValue() * (float)this.ticks;
                           em.setYaw(yaw);
                           em.setPitch(pitch);
                        } else {
                           em.setYaw(rotations[0]);
                           em.setPitch(rotations[1]);
                        }

                        ++this.lookDelay;
                        if ((float)this.lookDelay >= ((Number)((Setting)this.settings.get(this.DELAY)).getValue()).floatValue()) {
                           isFiring = true;
                           boolean nospread = ((Boolean)((Setting)this.settings.get(this.ANTISPREAD)).getValue()).booleanValue();
                           if (nospread) {
                              mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                           }

                           if (((Boolean)((Setting)this.settings.get(this.AUTOFIRE)).getValue()).booleanValue() && mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemHoe) {
                              mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
                           }

                           if (nospread) {
                              mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                           }

                           this.lookDelay = 0;
                           return;
                        }
                     } else {
                        --this.ticks;
                        if (this.ticks <= 0) {
                           this.ticks = 0;
                           return;
                        }
                     }

                     return;
                  }

                  o = var5.next();
                  p = (EntityPlayer)o;
                  this.playerPositions.putIfAbsent(p, new ArrayList());
                  previousPositions = (List)this.playerPositions.get(p);
                  previousPositions.add(new Vec3(p.posX, p.posY, p.posZ));
               } while(previousPositions.size() <= this.buffer);

               int i = 0;

               for(Iterator var10 = (new ArrayList(previousPositions)).iterator(); var10.hasNext(); ++i) {
                  Vec3 position = (Vec3)var10.next();
                  if (i < previousPositions.size() - this.buffer) {
                     previousPositions.remove(previousPositions.get(i));
                  }
               }
            }
         }
      } else if (event instanceof EventPacket) {
         EventPacket ep = (EventPacket)event;
         if (ep.isOutgoing() && ep.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            ++this.ticks;
         }
      }

   }

   public void updateAngles(float yaw, float pitch) {
      if (mc.gameSettings.thirdPersonView != 0) {
         AntiAim.rotationPitch = pitch;
         mc.thePlayer.rotationYawHead = yaw;
         mc.thePlayer.renderYawOffset = yaw;
      }
   }

   public double getTargetWeight(EntityPlayer p) {
      double weight = (double)(-mc.thePlayer.getDistanceToEntity(p));
      if (p.lastTickPosX == p.posX && p.lastTickPosY == p.posY && p.lastTickPosZ == p.posZ) {
         weight += 200.0D;
      }

      weight -= (double)(p.getDistanceToEntity(mc.thePlayer) / 5.0F);
      return weight;
   }

   private Entity predictPlayerMovement(EntityPlayer target) {
      int pingTicks = 0;

      try {
         pingTicks = (int)Math.ceil((double)mc.getNetHandler().func_175102_a(mc.thePlayer.getUniqueID()).getResponseTime() / 50.0D);
      } catch (Exception var4) {
         ;
      }

      return this.predictPlayerLocation(target, pingTicks);
   }

   public Entity predictPlayerLocation(EntityPlayer player, int ticks) {
      if (this.playerPositions.containsKey(player)) {
         List previousPositions = (List)this.playerPositions.get(player);
         if (previousPositions.size() > 1) {
            Vec3 origin = (Vec3)previousPositions.get(0);
            List deltas = new ArrayList();
            Vec3 previous = origin;

            Vec3 position;
            for(Iterator var7 = previousPositions.iterator(); var7.hasNext(); previous = position) {
               position = (Vec3)var7.next();
               deltas.add(new Vec3(position.xCoord - previous.xCoord, position.yCoord - previous.yCoord, position.zCoord - previous.zCoord));
            }

            double x = 0.0D;
            double y = 0.0D;
            double z = 0.0D;

            Vec3 delta;
            for(Iterator var13 = deltas.iterator(); var13.hasNext(); z += delta.zCoord * 1.5D) {
               delta = (Vec3)var13.next();
               x += delta.xCoord * 1.5D;
               y += delta.yCoord;
            }

            x /= (double)deltas.size();
            y /= (double)deltas.size();
            z /= (double)deltas.size();
            EntityPlayer simulated = new EntityOtherPlayerMP(mc.theWorld, player.getGameProfile());
            simulated.noClip = false;
            simulated.setPosition(player.posX, player.posY + 0.5D, player.posZ);

            for(int i = 0; i < ticks; ++i) {
               simulated.moveEntity(x, y, z);
            }

            return simulated;
         }
      }

      return player;
   }
}
