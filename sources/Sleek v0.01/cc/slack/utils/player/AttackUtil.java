package cc.slack.utils.player;

import cc.slack.Slack;
import cc.slack.features.modules.impl.other.AntiBot;
import cc.slack.features.modules.impl.other.Targets;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.MathUtil;
import cc.slack.utils.other.TimeUtil;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class AttackUtil {
   public static boolean inCombat = false;
   public static Entity combatTarget = null;
   public static final TimeUtil combatTimer = new TimeUtil();
   public static final long combatDuration = 600L;
   public static int patternIndex = 0;
   private static double devRand1 = 0.0D;
   private static double devRand2 = 0.0D;
   private static double devRand3 = 0.0D;
   private static double devRand4 = 0.0D;
   private static int devRandTick = 0;

   public static long getAttackDelay(int cps, double rand, AttackUtil.AttackPattern pattern) {
      switch(pattern) {
      case OLD:
         return (long)(1000.0D / getOldRandomization((double)cps, rand));
      case NEW:
         return (long)(1000.0D / getNewRandomization((double)cps, rand));
      case EXTRA:
         return (long)(1000.0D / getExtraRandomization((double)cps, rand));
      case PATTERN1:
         return (long)(1000.0D / getPattern1Randomization((double)cps, rand));
      case PATTERN2:
         return (long)(1000.0D / getPattern2Randomization((double)cps, rand));
      default:
         return 0L;
      }
   }

   public static double getOldRandomization(double cps, double sigma) {
      double rnd = MathHelper.getRandomDoubleInRange(new Random(), 0.0D, 1.0D);
      double normal = Math.sqrt(-2.0D * (Math.log(rnd) / Math.log(2.718281828459045D))) * Math.sin(6.283185307179586D * rnd);
      return cps + sigma * normal;
   }

   public static double getNewRandomization(double cps, double rand) {
      double rnd = MathHelper.getRandomDoubleInRange(new Random(), 0.0D, 1.0D);
      double normal = Math.sqrt(-2.0D * (Math.log(rnd) / Math.log(2.718281828459045D))) * Math.sin(6.283185307179586D * rnd);
      return cps + rand * normal + (cps + (new SecureRandom()).nextDouble() * rand) / 4.0D;
   }

   public static double getExtraRandomization(double cps, double rand) {
      if (devRandTick % 30 == 0) {
         devRand1 = MathHelper.getRandomDoubleInRange(new Random(), 0.0D, 1.0D);
         devRand2 = MathHelper.getRandomDoubleInRange(new Random(), 0.0D, 1.0D);
         devRand3 = MathHelper.getRandomDoubleInRange(new Random(), 0.0D, 1.0D);
         devRand4 = MathHelper.getRandomDoubleInRange(new Random(), 0.0D, 1.0D);
      }

      double randOffset1 = 0.0D;
      double randOffset2 = 0.0D;
      switch(MathHelper.getRandomIntegerInRange(new Random(), 1, 4)) {
      case 1:
         randOffset1 = devRand1;
         break;
      case 2:
         randOffset1 = devRand2;
         break;
      case 3:
         randOffset1 = devRand3;
         break;
      case 4:
         randOffset1 = devRand4;
      }

      switch(MathHelper.getRandomIntegerInRange(new Random(), 1, 4)) {
      case 1:
         randOffset2 = devRand1;
         break;
      case 2:
         randOffset2 = devRand2;
         break;
      case 3:
         randOffset2 = devRand3;
         break;
      case 4:
         randOffset2 = devRand4;
      }

      double rand1 = getNewRandomization(cps + (-0.3D + randOffset1 * 0.6D) * rand, rand * (0.5D + randOffset1 * 0.3D));
      double rand2 = getOldRandomization(cps + randOffset2 * rand, rand * (0.2D + randOffset2 * 0.4D));
      return (3.0D * rand1 + rand2) / 4.0D;
   }

   public static double getPattern1Randomization(double cps, double rand) {
      int[] recordedTimings = new int[]{17, 79, 83, 24, 81, 86, 30, 67, 97, 25, 79, 101, 33, 68, 129, 69, 96, 24, 77, 90, 27, 81, 104, 30, 68, 94, 29, 80, 96, 25, 72, 114, 25, 79, 100, 26, 75, 117, 22, 77, 134, 16, 75, 126, 22, 76, 119, 21, 94, 120, 32, 56, 119, 27, 80, 151, 68, 134, 30, 68, 115, 25, 92, 128, 28, 78, 134, 28, 71, 133, 33, 50, 136, 32, 65, 137, 26, 63, 143, 23, 78, 134, 29, 71, 136, 32, 68, 146, 31, 62, 138, 165, 17, 67, 97, 25, 78, 100, 34, 67, 32, 68, 116, 105, 28, 53, 105, 32, 79, 115, 25, 61, 103, 24, 60, 109, 29, 74, 16, 98, 18, 97, 18, 69, 25, 85, 110, 30, 82, 83, 26, 83, 124, 22, 62, 134, 20, 77, 92, 27, 88, 112, 23, 89, 122, 33, 67, 19, 88, 30, 79, 134, 108, 108, 27, 78, 128, 23, 76, 134, 27, 66, 140, 25, 79, 120, 44, 62, 124, 31, 82, 108, 41, 85, 117, 27, 93, 129, 19, 76, 136, 110, 142, 20, 67, 112, 34, 71, 114, 23, 93, 105, 47, 58, 112, 27, 66, 117, 22, 108, 111, 29, 83, 123, 27, 90, 127, 22, 92, 134, 26, 93, 131, 34, 83, 115, 24, 94, 119, 47, 73, 113, 30, 92, 115, 27, 92, 128, 30, 72, 125, 115, 128, 30, 66, 135, 29, 57, 127, 106, 145, 18, 68, 162, 70, 131, 21, 70, 127, 23, 61, 148, 86, 105, 30, 95, 102, 32, 82, 125, 42, 50, 150, 100, 124, 29, 64};
      ++patternIndex;
      if (patternIndex >= recordedTimings.length) {
         patternIndex = 0;
      }

      return 1000.0D / MathUtil.interpolate(1000.0D / (cps + rand), 1000.0D / cps, (double)recordedTimings[patternIndex] / 150.0D);
   }

   public static double getPattern2Randomization(double cps, double rand) {
      int[] recordedTimings = new int[]{52, 87, 74, 73, 80, 78, 88, 78, 72, 93, 88, 72, 74, 76, 93, 77, 96, 68, 82, 75, 73, 76, 90, 177, 66, 74, 76, 91, 86, 88, 90, 94, 84, 85, 80, 83, 147, 103, 93, 80, 109, 76, 84, 177, 82, 110, 88, 83, 90, 110, 63, 87, 83, 74, 93, 83, 100, 73, 72, 122, 83, 117, 83, 84, 88, 82, 87, 78, 115, 80, 86, 97, 87, 92, 92, 93, 93, 95, 87, 97, 68, 108, 70, 68, 87, 71, 94, 67, 96, 75, 81, 81, 93, 141, 87, 78, 96, 80, 91, 121, 78, 96, 88, 132, 73, 92, 83, 95, 155, 89, 88, 76, 85, 95, 88, 75, 83, 73, 90, 79, 125, 89, 94, 150, 103, 71, 78, 98, 167, 77, 103, 87, 84, 82, 88, 96, 166, 95, 67, 83, 83, 67, 83, 78, 105, 73, 94, 99, 72, 93, 85, 84, 100, 86, 83, 100, 67, 83, 85, 85, 98, 65, 66, 84, 84, 99, 67, 101, 83, 82, 117, 116, 84, 66, 83, 101, 67, 83, 168, 83, 65, 134, 50, 84, 82, 84, 83, 83, 101, 99, 83, 102, 65, 67, 68, 66, 83, 67, 152, 128, 68, 79, 76, 93, 74, 100, 88, 71, 75, 93, 72, 70, 83, 100, 84, 65, 96, 88, 71, 78, 84, 84, 68, 87, 157, 65, 88, 68, 97, 68, 113, 57, 93, 83, 72, 69, 78, 67, 84, 67, 151, 100, 83, 83, 67, 91, 161, 72, 73, 100, 84, 87, 95, 87, 80, 83, 67, 83, 67, 93, 90, 84, 72, 94, 125, 81, 111, 83, 70, 80, 153, 91, 73, 100, 83, 186};
      ++patternIndex;
      if (patternIndex >= recordedTimings.length) {
         patternIndex = 0;
      }

      return 1000.0D / MathUtil.interpolate(1000.0D / (cps + rand), 1000.0D / cps, (double)recordedTimings[patternIndex] / 164.0D);
   }

   public static EntityLivingBase getTarget(double range, String sort) {
      Targets targets = (Targets)Slack.getInstance().getModuleManager().getInstance(Targets.class);
      return getTarget(range, sort, (Boolean)targets.teams.getValue(), (Boolean)targets.mobsTarget.getValue(), (Boolean)targets.animalTarget.getValue(), (Boolean)targets.playerTarget.getValue());
   }

   public static EntityLivingBase getTarget(double range, String sort, boolean team, boolean mobs, boolean animals, boolean players) {
      if (mc.getPlayer() != null && mc.getWorld() != null) {
         List<EntityLivingBase> targets = new ArrayList();
         Iterator var8 = ((List)mc.getWorld().getLoadedEntityList().stream().filter(Objects::nonNull).collect(Collectors.toList())).iterator();

         while(true) {
            Entity entity;
            do {
               do {
                  do {
                     do {
                        do {
                           do {
                              do {
                                 do {
                                    do {
                                       if (!var8.hasNext()) {
                                          String var10 = sort.toLowerCase();
                                          byte var11 = -1;
                                          switch(var10.hashCode()) {
                                          case -1221262756:
                                             if (var10.equals("health")) {
                                                var11 = 2;
                                             }
                                             break;
                                          case 101581:
                                             if (var10.equals("fov")) {
                                                var11 = 0;
                                             }
                                             break;
                                          case 288459765:
                                             if (var10.equals("distance")) {
                                                var11 = 1;
                                             }
                                             break;
                                          case 2028239653:
                                             if (var10.equals("hurt ticks")) {
                                                var11 = 3;
                                             }
                                          }

                                          switch(var11) {
                                          case 0:
                                             targets.sort(Comparator.comparingDouble(RotationUtil::getRotationDifference));
                                             break;
                                          case 1:
                                             targets.sort(Comparator.comparingDouble((entityx) -> {
                                                return (double)entityx.getDistanceToEntity(mc.getPlayer());
                                             }));
                                             break;
                                          case 2:
                                             targets.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
                                             break;
                                          case 3:
                                             targets.sort(Comparator.comparingDouble(EntityLivingBase::getHurtTime));
                                          }

                                          return targets.isEmpty() ? null : (EntityLivingBase)targets.get(0);
                                       }

                                       entity = (Entity)var8.next();
                                    } while(!(entity instanceof EntityLivingBase));
                                 } while(entity == mc.getPlayer());
                              } while(entity instanceof EntityArmorStand);
                           } while(mobs && !(entity instanceof EntityMob));
                        } while(animals && !(entity instanceof EntityAnimal));
                     } while(players && !(entity instanceof EntityPlayer));
                  } while(entity instanceof EntityPlayer && !team && PlayerUtil.isOnSameTeam((EntityPlayer)entity));
               } while((double)mc.getPlayer().getDistanceToEntity(entity) > range);
            } while(((AntiBot)Slack.getInstance().getModuleManager().getInstance(AntiBot.class)).isToggle() && ((AntiBot)Slack.getInstance().getModuleManager().getInstance(AntiBot.class)).isBot((EntityLivingBase)entity));

            targets.add((EntityLivingBase)entity);
         }
      } else {
         return null;
      }
   }

   public static enum AttackPattern {
      OLD,
      NEW,
      EXTRA,
      PATTERN1,
      PATTERN2;
   }
}
