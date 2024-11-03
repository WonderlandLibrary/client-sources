package xyz.cucumber.base.utils.game;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.commands.cmds.FriendsCommand;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.feat.other.FriendsModule;
import xyz.cucumber.base.module.feat.other.ReverseFriendsModule;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.math.RotationUtils;

public class EntityUtils {
   public static Minecraft mc = Minecraft.getMinecraft();
   public static int size = 0;
   public static Timer timer = new Timer();

   public static EntityLivingBase getTarget(
      double range, String targetMode, String attackMode, int switchTimer, boolean teams, boolean troughWalls, boolean dead, boolean invisible
   ) {
      EntityLivingBase target = null;
      List<Entity> targets = mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
      targets = targets.stream()
         .filter(entity -> (double)mc.thePlayer.getDistanceToEntity(entity) < range && entity != mc.thePlayer && !(entity instanceof EntityArmorStand))
         .collect(Collectors.toList());
      targets.removeIf(entity -> !invisible && entity.isInvisible());
      targets.removeIf(entity -> !dead && ((EntityLivingBase)entity).getHealth() <= 0.0F);
      KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
      targets.removeIf(
         entity -> (double)(
                  Math.abs(RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw)
                           % 360.0F
                        > 180.0F
                     ? 360.0F
                        - Math.abs(
                              RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw
                           )
                           % 360.0F
                     : Math.abs(
                           RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw
                        )
                        % 360.0F
               )
               > ka.fov.getValue()
      );
      if (Client.INSTANCE.getModuleManager().getModule(ReverseFriendsModule.class).isEnabled()) {
         targets.removeIf(entity -> !ReverseFriendsModule.allowed.contains(entity.getName()));
      } else {
         if (Client.INSTANCE.getModuleManager().getModule(FriendsModule.class).isEnabled()) {
            for (String friend : FriendsCommand.friends) {
               targets.removeIf(entity -> entity.getName().equalsIgnoreCase(friend));
            }
         }

         targets.removeIf(entity -> teams && mc.thePlayer.isOnSameTeam((EntityLivingBase)entity));

         try {
            targets.removeIf(entity -> teams && entity instanceof EntityPlayer && isInSameTeam((EntityPlayer)entity));
         } catch (Exception var17) {
         }
      }

      targets.removeIf(entity -> !troughWalls && !mc.thePlayer.canEntityBeSeen(entity));
      String var19;
      switch ((var19 = ka.sort.getMode().toLowerCase()).hashCode()) {
         case -1314571118:
            if (var19.equals("strongest player")) {
               if (targets.size() > 1) {
                  boolean isPlayer = false;

                  for (int i = 0; i < targets.size(); i++) {
                     Entity ent = targets.get(i);
                     if (ent != null) {
                        if (!(ent instanceof EntityPlayer)) {
                           if (isPlayer) {
                              targets.remove(ent);
                           }
                        } else {
                           isPlayer = true;
                        }
                     }
                  }
               }

               targets.sort(Comparator.comparingDouble(entity -> getStrongestPlayerSort(entity)));
            }
            break;
         case -1221262756:
            if (var19.equals("health")) {
               targets.sort(
                  Comparator.comparingDouble(
                     entity -> (double)(entity instanceof EntityPlayer ? ((EntityPlayer)entity).getHealth() : mc.thePlayer.getDistanceToEntity(entity))
                  )
               );
            }
            break;
         case 109549001:
            if (var19.equals("smart")) {
               if (targets.size() > 1) {
                  boolean isPlayer = false;

                  for (int ix = 0; ix < targets.size(); ix++) {
                     Entity ent = targets.get(ix);
                     if (ent != null) {
                        if (!(ent instanceof EntityPlayer)) {
                           if (isPlayer) {
                              targets.remove(ent);
                           }
                        } else {
                           isPlayer = true;
                        }
                     }
                  }
               }

               targets.sort(Comparator.comparingDouble(entity -> getSmartSort(entity)));
            }
            break;
         case 288459765:
            if (var19.equals("distance")) {
               targets.sort(Comparator.comparingDouble(entity -> (double)mc.thePlayer.getDistanceToEntity(entity)));
            }
      }

      String var22;
      switch ((var22 = targetMode.toLowerCase()).hashCode()) {
         case -493567566:
            if (var22.equals("players")) {
               targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
            }
         default:
            if (!targets.isEmpty()) {
               String var24;
               switch ((var24 = attackMode.toLowerCase()).hashCode()) {
                  case 109935:
                     if (var24.equals("off")) {
                        target = (EntityLivingBase)targets.get(0);
                     }
                     break;
                  case 110364485:
                     if (var24.equals("timer")) {
                        if (timer.hasTimeElapsed((double)switchTimer, true)) {
                           size++;
                        }

                        if (targets.size() > 0 && size >= targets.size()) {
                           size = 0;
                        }

                        target = (EntityLivingBase)targets.get(size);
                     }
                     break;
                  case 203974718:
                     if (var24.equals("hurt time") && targets.size() > 0) {
                        targets.sort(Comparator.comparingDouble(entity -> (double)entity.hurtResistantTime));
                        target = (EntityLivingBase)targets.get(0);
                     }
               }
            }

            return target;
      }
   }

   public static EntityLivingBase getTargetBox(
      double range, String targetMode, String attackMode, int switchTimer, boolean teams, boolean troughWalls, boolean dead, boolean invisible
   ) {
      EntityLivingBase target = null;
      List<Entity> targets = mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
      targets = targets.stream()
         .filter(entity -> getDistanceToEntityBox(entity) < range && entity != mc.thePlayer && !(entity instanceof EntityArmorStand))
         .collect(Collectors.toList());
      targets.removeIf(entity -> !invisible && entity.isInvisible());
      targets.removeIf(entity -> !dead && ((EntityLivingBase)entity).getHealth() <= 0.0F);
      KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
      targets.removeIf(
         entity -> (double)(
                  Math.abs(RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw)
                           % 360.0F
                        > 180.0F
                     ? 360.0F
                        - Math.abs(
                              RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw
                           )
                           % 360.0F
                     : Math.abs(
                           RotationUtils.getRotationFromPosition(entity.posX, entity.posY, entity.posZ)[0] - Minecraft.getMinecraft().thePlayer.rotationYaw
                        )
                        % 360.0F
               )
               > ka.fov.getValue()
      );
      if (Client.INSTANCE.getModuleManager().getModule(ReverseFriendsModule.class).isEnabled()) {
         targets.removeIf(entity -> !ReverseFriendsModule.allowed.contains(entity.getName()));
      } else {
         if (Client.INSTANCE.getModuleManager().getModule(FriendsModule.class).isEnabled()) {
            for (String friend : FriendsCommand.friends) {
               targets.removeIf(entity -> entity.getName().equalsIgnoreCase(friend));
            }
         }

         targets.removeIf(entity -> teams && mc.thePlayer.isOnSameTeam((EntityLivingBase)entity));

         try {
            targets.removeIf(entity -> teams && entity instanceof EntityPlayer && isInSameTeam((EntityPlayer)entity));
         } catch (Exception var17) {
         }
      }

      targets.removeIf(entity -> !troughWalls && !mc.thePlayer.canEntityBeSeen(entity));
      String var19;
      switch ((var19 = ka.sort.getMode().toLowerCase()).hashCode()) {
         case -1314571118:
            if (var19.equals("strongest player")) {
               if (targets.size() > 1) {
                  boolean isPlayer = false;

                  for (int i = 0; i < targets.size(); i++) {
                     Entity ent = targets.get(i);
                     if (ent != null) {
                        if (!(ent instanceof EntityPlayer)) {
                           if (isPlayer) {
                              targets.remove(ent);
                           }
                        } else {
                           isPlayer = true;
                        }
                     }
                  }
               }

               targets.sort(Comparator.comparingDouble(entity -> getStrongestPlayerSort(entity)));
            }
            break;
         case -1221262756:
            if (var19.equals("health")) {
               targets.sort(
                  Comparator.comparingDouble(
                     entity -> (double)(entity instanceof EntityPlayer ? ((EntityPlayer)entity).getHealth() : mc.thePlayer.getDistanceToEntity(entity))
                  )
               );
            }
            break;
         case 109549001:
            if (var19.equals("smart")) {
               if (targets.size() > 1) {
                  boolean isPlayer = false;

                  for (int ix = 0; ix < targets.size(); ix++) {
                     Entity ent = targets.get(ix);
                     if (ent != null) {
                        if (!(ent instanceof EntityPlayer)) {
                           if (isPlayer) {
                              targets.remove(ent);
                           }
                        } else {
                           isPlayer = true;
                        }
                     }
                  }
               }

               targets.sort(Comparator.comparingDouble(entity -> getSmartSort(entity)));
            }
            break;
         case 288459765:
            if (var19.equals("distance")) {
               targets.sort(Comparator.comparingDouble(entity -> (double)mc.thePlayer.getDistanceToEntity(entity)));
            }
      }

      String var22;
      switch ((var22 = targetMode.toLowerCase()).hashCode()) {
         case -493567566:
            if (var22.equals("players")) {
               targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());
            }
         default:
            if (!targets.isEmpty()) {
               String var24;
               switch ((var24 = attackMode.toLowerCase()).hashCode()) {
                  case 109935:
                     if (var24.equals("off")) {
                        target = (EntityLivingBase)targets.get(0);
                     }
                     break;
                  case 110364485:
                     if (var24.equals("timer")) {
                        if (timer.hasTimeElapsed((double)switchTimer, true)) {
                           size++;
                        }

                        if (targets.size() > 0 && size >= targets.size()) {
                           size = 0;
                        }

                        target = (EntityLivingBase)targets.get(size);
                     }
                     break;
                  case 203974718:
                     if (var24.equals("hurt time") && targets.size() > 0) {
                        targets.sort(Comparator.comparingDouble(entity -> (double)entity.hurtResistantTime));
                        target = (EntityLivingBase)targets.get(0);
                     }
               }
            }

            return target;
      }
   }

   public static boolean isInSameTeam(EntityPlayer player) {
      try {
         String[] name = mc.thePlayer.getDisplayName().getUnformattedText().split("");
         String[] parts = player.getDisplayName().getUnformattedText().split("");
         return Arrays.asList(name).contains("Â§")
            && Arrays.asList(parts).contains("Â§")
            && Arrays.asList(name).get(Arrays.asList(name).indexOf("Â§") + 1).equals(Arrays.asList(parts).get(Arrays.asList(parts).indexOf("Â§") + 1));
      } catch (Exception var4) {
         return false;
      }
   }

   public static double getDistanceToEntityBox(Entity entity) {
      Vec3 eyes = mc.thePlayer.getPositionEyes(1.0F);
      Vec3 pos = RotationUtils.getBestHitVec(entity);
      double xDist = Math.abs(pos.xCoord - eyes.xCoord);
      double yDist = Math.abs(pos.yCoord - eyes.yCoord);
      double zDist = Math.abs(pos.zCoord - eyes.zCoord);
      return Math.sqrt(Math.pow(xDist, 2.0) + Math.pow(yDist, 2.0) + Math.pow(zDist, 2.0));
   }

   public static double getDistanceToEntityBoxFromPosition(double posX, double posY, double posZ, Entity entity) {
      Vec3 eyes = mc.thePlayer.getPositionEyes(1.0F);
      Vec3 pos = RotationUtils.getBestHitVec(entity);
      double xDist = Math.abs(pos.xCoord - posX);
      double yDist = Math.abs(pos.yCoord - posY + (double)mc.thePlayer.getEyeHeight());
      double zDist = Math.abs(pos.zCoord - posZ);
      return Math.sqrt(Math.pow(xDist, 2.0) + Math.pow(yDist, 2.0) + Math.pow(zDist, 2.0));
   }

   public static double getSmartSort(Entity entity) {
      if (entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         double playerDamage = 0.0;
         double targetDamage = 0.0;
         if (mc.thePlayer.getHeldItem() != null) {
            playerDamage = (double)Math.max(0.0F, InventoryUtils.getItemDamage(mc.thePlayer.getHeldItem()));
         }

         if (player.getHeldItem() != null) {
            targetDamage = (double)Math.max(0.0F, InventoryUtils.getItemDamage(player.getHeldItem()));
         }

         playerDamage = playerDamage * 20.0 / (double)(player.getTotalArmorValue() * 4);
         if (mc.thePlayer.fallDistance > 0.0F) {
            playerDamage *= 1.5;
         }

         return playerDamage >= (double)player.getHealth() ? -1.0E8 : targetDamage * -1.0;
      } else {
         return (double)mc.thePlayer.getDistanceToEntity(entity);
      }
   }

   public static double getStrongestPlayerSort(Entity entity) {
      if (entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         double targetDamage = 0.0;
         if (player.getHeldItem() != null) {
            targetDamage = (double)Math.max(0.0F, InventoryUtils.getItemDamage(player.getHeldItem()));
         }

         return targetDamage * -1.0;
      } else {
         return (double)mc.thePlayer.getDistanceToEntity(entity);
      }
   }
}
