package com.example.editme.modules.combat;

import com.example.editme.events.EditmeEvent;
import com.example.editme.events.PacketEvent;
import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.modules.misc.KillStreak;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.EntityUtil;
import com.example.editme.util.client.Friends;
import com.example.editme.util.client.MathUtil;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.render.EditmeTessellator;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.network.play.client.CPacketUseEntity.Action;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Explosion;

@Module.Info(
   name = "AutoCrystal",
   category = Module.Category.COMBAT
)
public class AutoCrystal extends Module {
   private Setting antiEatInterrupt = this.register(SettingsManager.b("Anti Eat Interrupt", false));
   private Setting selfDamageRatio;
   private Setting multiPlaceSpeed;
   private Entity renderEnt;
   @EventHandler
   private Listener onPacketReceiveEvent;
   private boolean switchCooldown;
   private static double pitch;
   private Setting multiPlace;
   private Setting placeWallsRange;
   private EntityPlayer target;
   private Setting antiBreakStuck;
   private long multiPlaceSystemTime;
   private Setting doBreak;
   private Setting autoSwitch = this.register(SettingsManager.b("Auto Switch", true));
   private Setting minDamage = this.register(SettingsManager.integerBuilder("MinDamage").withMinimum(0).withMaximum(20).withValue((int)4).build());
   private static boolean togglePitch = false;
   private Setting facePlaceHealth;
   private Setting antiMineInterrupt = this.register(SettingsManager.b("Anti Mine Interrupt", false));
   private int placements;
   private final List placeLocations;
   private Setting doFacePlace;
   private Setting Blue;
   private Setting overrideYPlaceRange;
   private Setting placeRange;
   private long antiStuckSystemTime;
   private Setting breakSpeed;
   private Setting breakRange;
   private Setting pSilent = this.register(SettingsManager.b("pSilent", false));
   private Setting placeDelay;
   private long placeSystemTime;
   private static boolean isSpoofingAngles;
   private Setting enemyRange = this.register(SettingsManager.integerBuilder("Enemy Range").withMinimum(1).withMaximum(13).withValue((int)9).build());
   public boolean isActive;
   private Setting rainbow;
   private Setting maxSelfDamageMode;
   private Setting antiSurround = this.register(SettingsManager.b("Surround Fucker", false));
   private int newSlot;
   private Setting antiDesync = this.register(SettingsManager.b("Anti Desync", true));
   private Setting antiWeakness = this.register(SettingsManager.b("Anti Weakness", true));
   private Setting yPlaceRange;
   private static double yaw;
   private Setting maxBreakAttempts;
   private Setting doDamageRender;
   private final String cercle;
   private Setting place;
   private BlockPos render;
   private Setting surroundPriority = this.register(SettingsManager.b("Surround Priority", false));
   @EventHandler
   private Listener onPacketReceiveEventpSilent;
   private Setting Red;
   private Setting antiSuicide = this.register(SettingsManager.b("Anti Suicide", true));
   private Setting breakWallsRange;
   private double renderDamage;
   private Setting maxSelfDamage;
   private long breakSystemTime;
   private Setting doRender;
   private Setting Green;
   @EventHandler
   private Listener onPacketSendEvent;
   private final Packet[] packet;
   private final HashMap crystalBreakTracker;
   private Setting rayTrace;

   public void onDisable() {
      this.render = null;
      resetRotation();
   }

   private static boolean isPlayerEating() {
      return mc.field_71439_g != null && mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemFood && mc.field_71439_g.func_184587_cr();
   }

   private void lookAtPacket(double var1, double var3, double var5, EntityPlayer var7) {
      double[] var8 = EntityUtil.calculateLookAt(var1, var3, var5, var7);
      setYawAndPitch((float)var8[0], (float)var8[1]);
   }

   public static BlockPos getPlayerPos() {
      return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
   }

   private boolean lambda$new$0(Integer var1) {
      return ((AutoCrystal.MaxSelfDamageModes)this.maxSelfDamageMode.getValue()).equals(AutoCrystal.MaxSelfDamageModes.ABSOLUTE);
   }

   private void breakCrystal(EntityEnderCrystal var1) {
      boolean var2 = true;
      if ((Boolean)this.antiWeakness.getValue() && mc.field_71439_g.func_70644_a(MobEffects.field_76437_t) && (mc.field_71439_g.func_184614_ca() == ItemStack.field_190927_a || !(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) && !(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemTool))) {
         for(int var3 = 0; var3 < 9; ++var3) {
            ItemStack var4 = mc.field_71439_g.field_71071_by.func_70301_a(var3);
            if (var4 != ItemStack.field_190927_a && (var4.func_77973_b() instanceof ItemTool || var4.func_77973_b() instanceof ItemSword)) {
               int var5 = mc.field_71439_g.field_71071_by.field_70461_c;
               mc.field_71439_g.field_71071_by.field_70461_c = var3;
               mc.field_71442_b.func_78765_e();
               break;
            }
         }
      }

      if (!this.crystalBreakTracker.containsKey(var1.func_110124_au())) {
         this.crystalBreakTracker.put(var1.func_110124_au(), 0);
      }

      this.lookAtPacket(var1.field_70165_t, var1.field_70163_u, var1.field_70161_v, mc.field_71439_g);
      mc.field_71442_b.func_78764_a(mc.field_71439_g, var1);
      mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
      this.crystalBreakTracker.put(var1.func_110124_au(), (Integer)this.crystalBreakTracker.get(var1.func_110124_au()) + 1);
      this.breakSystemTime = System.nanoTime() / 1000000L;
   }

   private boolean lambda$new$9(Integer var1) {
      return (Boolean)this.place.getValue() && (Boolean)this.rayTrace.getValue();
   }

   private boolean canPlaceAntiSurroundCrystal(Vec3d var1) {
      BlockPos var2 = new BlockPos(var1.field_72450_a, var1.field_72448_b, var1.field_72449_c);
      BlockPos var3 = var2.func_177982_a(0, 1, 0);
      BlockPos var4 = var2.func_177982_a(0, 2, 0);
      return (mc.field_71441_e.func_180495_p(var2).func_177230_c() == Blocks.field_150357_h || mc.field_71441_e.func_180495_p(var2).func_177230_c() == Blocks.field_150343_Z) && mc.field_71441_e.func_180495_p(var3).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(var4).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(var3)).isEmpty() && mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(var4)).isEmpty();
   }

   public List getSphere(BlockPos var1, float var2, int var3, boolean var4, boolean var5, int var6) {
      ArrayList var7 = new ArrayList();
      int var8 = var1.func_177958_n();
      int var9 = var1.func_177956_o();
      int var10 = var1.func_177952_p();

      for(int var11 = var8 - (int)var2; (float)var11 <= (float)var8 + var2; ++var11) {
         for(int var12 = var10 - (int)var2; (float)var12 <= (float)var10 + var2; ++var12) {
            for(int var13 = var5 ? var9 - (int)var2 : var9; (float)var13 < (var5 ? (float)var9 + var2 : (float)(var9 + var3)); ++var13) {
               double var14 = (double)((var8 - var11) * (var8 - var11) + (var10 - var12) * (var10 - var12) + (var5 ? (var9 - var13) * (var9 - var13) : 0));
               if (var14 < (double)(var2 * var2) && (!var4 || var14 >= (double)((var2 - 1.0F) * (var2 - 1.0F)))) {
                  BlockPos var16 = new BlockPos(var11, var13 + var6, var12);
                  var7.add(var16);
               }
            }
         }
      }

      return var7;
   }

   private boolean lambda$new$17(Integer var1) {
      return (Boolean)this.doRender.getValue();
   }

   private boolean lambda$new$2(Integer var1) {
      return (Boolean)this.doBreak.getValue();
   }

   public static float calculateDamage(EntityEnderCrystal var0, Entity var1) {
      return calculateDamage(var0.field_70165_t, var0.field_70163_u, var0.field_70161_v, var1);
   }

   private boolean lambda$new$11(Integer var1) {
      return (Boolean)this.place.getValue() && (Boolean)this.overrideYPlaceRange.getValue();
   }

   public static boolean canBlockBeSeen(BlockPos var0) {
      return mc.field_71441_e.func_147447_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d((double)var0.func_177958_n(), (double)var0.func_177956_o(), (double)var0.func_177952_p()), false, true, false) == null;
   }

   private boolean lambda$new$6(Integer var1) {
      return (Boolean)this.place.getValue();
   }

   public void onWorldRender(RenderEvent var1) {
      if (this.render != null && (Boolean)this.doRender.getValue()) {
         float[] var2 = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
         int var3 = Color.HSBtoRGB(var2[0], 1.0F, 1.0F);
         int var4 = var3 >> 16 & 255;
         int var5 = var3 >> 8 & 255;
         int var6 = var3 & 255;
         if ((Boolean)this.rainbow.getValue()) {
            EditmeTessellator.prepare(7);
            EditmeTessellator.drawBox(this.render, var4, var5, var6, 77, 63);
         } else {
            EditmeTessellator.prepare(7);
            EditmeTessellator.drawBox(this.render, (Integer)this.Red.getValue(), (Integer)this.Green.getValue(), (Integer)this.Blue.getValue(), 77, 63);
         }

         EditmeTessellator.release();
      }

   }

   private boolean lambda$new$8(Boolean var1) {
      return (Boolean)this.place.getValue();
   }

   private Vec3d findAntiSurroundBlock(Vec3d var1) {
      if (this.canPlaceAntiSurroundCrystal(var1.func_178787_e(AutoCrystal.SurroundOffsets.NORTH2)) && !this.isExplosionProof(var1.func_178787_e(AutoCrystal.SurroundOffsets.NORTH1).func_72441_c(0.0D, 1.0D, 0.0D))) {
         return var1.func_178787_e(AutoCrystal.SurroundOffsets.NORTH2);
      } else if (this.canPlaceAntiSurroundCrystal(var1.func_178787_e(AutoCrystal.SurroundOffsets.NORTH1))) {
         return var1.func_178787_e(AutoCrystal.SurroundOffsets.NORTH1);
      } else if (this.canPlaceAntiSurroundCrystal(var1.func_178787_e(AutoCrystal.SurroundOffsets.EAST2)) && !this.isExplosionProof(var1.func_178787_e(AutoCrystal.SurroundOffsets.EAST1).func_72441_c(0.0D, 1.0D, 0.0D))) {
         return var1.func_178787_e(AutoCrystal.SurroundOffsets.EAST2);
      } else if (this.canPlaceAntiSurroundCrystal(var1.func_178787_e(AutoCrystal.SurroundOffsets.EAST1))) {
         return var1.func_178787_e(AutoCrystal.SurroundOffsets.EAST1);
      } else if (this.canPlaceAntiSurroundCrystal(var1.func_178787_e(AutoCrystal.SurroundOffsets.SOUTH2)) && !this.isExplosionProof(var1.func_178787_e(AutoCrystal.SurroundOffsets.SOUTH1).func_72441_c(0.0D, 1.0D, 0.0D))) {
         return var1.func_178787_e(AutoCrystal.SurroundOffsets.SOUTH2);
      } else if (this.canPlaceAntiSurroundCrystal(var1.func_178787_e(AutoCrystal.SurroundOffsets.SOUTH1))) {
         return var1.func_178787_e(AutoCrystal.SurroundOffsets.SOUTH1);
      } else if (this.canPlaceAntiSurroundCrystal(var1.func_178787_e(AutoCrystal.SurroundOffsets.WEST2)) && !this.isExplosionProof(var1.func_178787_e(AutoCrystal.SurroundOffsets.WEST1).func_72441_c(0.0D, 1.0D, 0.0D))) {
         return var1.func_178787_e(AutoCrystal.SurroundOffsets.WEST2);
      } else {
         return this.canPlaceAntiSurroundCrystal(var1.func_178787_e(AutoCrystal.SurroundOffsets.WEST1)) ? var1.func_178787_e(AutoCrystal.SurroundOffsets.WEST1) : null;
      }
   }

   public void onUpdate() {
      if (ModuleManager.isModuleEnabled("Surround") && (Boolean)this.surroundPriority.getValue()) {
         if (mc.field_71441_e.func_175623_d(new BlockPos(mc.field_71439_g.field_70165_t + 1.0D, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v))) {
            return;
         }

         if (mc.field_71441_e.func_175623_d(new BlockPos(mc.field_71439_g.field_70165_t - 1.0D, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v))) {
            return;
         }

         if (mc.field_71441_e.func_175623_d(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v + 1.0D))) {
            return;
         }

         if (mc.field_71441_e.func_175623_d(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v - 1.0D))) {
            return;
         }
      }

      if (!(Boolean)this.antiEatInterrupt.getValue() || !isPlayerEating()) {
         if (!(Boolean)this.antiMineInterrupt.getValue() || !(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemPickaxe)) {
            EntityEnderCrystal var1 = (EntityEnderCrystal)mc.field_71441_e.field_72996_f.stream().filter(AutoCrystal::lambda$onUpdate$22).map(AutoCrystal::lambda$onUpdate$23).min(Comparator.comparing(AutoCrystal::lambda$onUpdate$24)).filter(this::lambda$onUpdate$25).orElse((Object)null);
            if ((Boolean)this.doBreak.getValue()) {
               if (var1 != null && mc.field_71439_g.func_70032_d(var1) <= (float)(Integer)this.breakRange.getValue()) {
                  if (!mc.field_71439_g.func_70685_l(var1) && mc.field_71439_g.func_70032_d(var1) > (float)(Integer)this.breakWallsRange.getValue()) {
                     return;
                  }

                  if (System.nanoTime() / 1000000L - this.breakSystemTime >= (long)(420 - (Integer)this.breakSpeed.getValue() * 20)) {
                     this.breakCrystal(var1);
                  }

                  if ((Boolean)this.multiPlace.getValue()) {
                     if (System.nanoTime() / 1000000L - this.multiPlaceSystemTime >= (long)(20 * (Integer)this.multiPlaceSpeed.getValue()) && System.nanoTime() / 1000000L - this.antiStuckSystemTime <= (long)(400 + (400 - (Integer)this.breakSpeed.getValue() * 20))) {
                        this.multiPlaceSystemTime = System.nanoTime() / 1000000L;
                        return;
                     }
                  } else if (System.nanoTime() / 1000000L - this.antiStuckSystemTime <= (long)(400 + (400 - (Integer)this.breakSpeed.getValue() * 20))) {
                     return;
                  }
               } else {
                  resetRotation();
               }
            }

            int var2 = mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP ? mc.field_71439_g.field_71071_by.field_70461_c : -1;
            if (var2 == -1) {
               for(int var3 = 0; var3 < 9; ++var3) {
                  if (mc.field_71439_g.field_71071_by.func_70301_a(var3).func_77973_b() == Items.field_185158_cP) {
                     var2 = var3;
                     break;
                  }
               }
            }

            boolean var21 = false;
            if (mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) {
               var21 = true;
            } else if (var2 == -1) {
               return;
            }

            Entity var4 = null;
            Object var5 = null;
            BlockPos var6 = null;
            List var7 = this.findCrystalBlocks();
            ArrayList var8 = new ArrayList();
            var8.addAll((Collection)mc.field_71441_e.field_73010_i.stream().filter(AutoCrystal::lambda$onUpdate$26).collect(Collectors.toList()));
            double var9 = 0.5D;
            Iterator var11 = var8.iterator();

            label236:
            while(true) {
               Entity var12;
               do {
                  do {
                     do {
                        if (!var11.hasNext()) {
                           if (var9 == 0.5D) {
                              this.render = null;
                              this.renderEnt = null;
                              resetRotation();
                              return;
                           }

                           this.render = var6;
                           this.renderEnt = var4;
                           if ((Boolean)this.place.getValue()) {
                              if (this.placeCrystal(var2, var21, var6)) {
                                 return;
                              }

                              if (var4 instanceof EntityPlayer) {
                                 KillStreak.addTargetedPlayer(var4.func_70005_c_());
                              }
                           }

                           if (isSpoofingAngles) {
                              EntityPlayerSP var22;
                              if (togglePitch) {
                                 var22 = mc.field_71439_g;
                                 var22.field_70125_A += 4.0E-4F;
                                 togglePitch = false;
                              } else {
                                 var22 = mc.field_71439_g;
                                 var22.field_70125_A -= 4.0E-4F;
                                 togglePitch = true;
                              }
                           }

                           return;
                        }

                        var12 = (Entity)var11.next();
                     } while(var12 == mc.field_71439_g);
                  } while(((EntityLivingBase)var12).func_110143_aJ() <= 0.0F);
               } while(mc.field_71439_g.func_70068_e(var12) > (double)((Integer)this.enemyRange.getValue() * (Integer)this.enemyRange.getValue()));

               if ((Boolean)this.antiSurround.getValue()) {
                  this.findAntiSurroundBlock(var12.func_174791_d().func_72441_c(0.0D, -1.0D, 0.0D));
               }

               Iterator var13 = var7.iterator();

               while(true) {
                  BlockPos var14;
                  double var17;
                  double var19;
                  do {
                     do {
                        do {
                           do {
                              while(true) {
                                 double var15;
                                 do {
                                    do {
                                       do {
                                          if (!var13.hasNext()) {
                                             continue label236;
                                          }

                                          var14 = (BlockPos)var13.next();
                                       } while((Boolean)this.rayTrace.getValue() && !canBlockBeSeen(var14) && mc.field_71439_g.func_70011_f((double)var14.func_177958_n(), (double)var14.func_177956_o(), (double)var14.func_177952_p()) > (double)(Integer)this.placeWallsRange.getValue());
                                    } while((Boolean)this.overrideYPlaceRange.getValue() && ((double)var14.func_177956_o() >= mc.field_71439_g.field_70163_u && (double)var14.func_177956_o() - mc.field_71439_g.field_70163_u > (double)(Integer)this.yPlaceRange.getValue() || (double)var14.func_177956_o() <= mc.field_71439_g.field_70163_u && mc.field_71439_g.field_70163_u - (double)var14.func_177956_o() > (double)(Integer)this.yPlaceRange.getValue()));

                                    var15 = var12.func_174818_b(var14);
                                 } while(var15 > 56.2D);

                                 var17 = (double)calculateDamage((double)var14.field_177962_a + 0.5D, (double)(var14.field_177960_b + 1), (double)var14.field_177961_c + 0.5D, var12);
                                 var19 = (double)calculateDamage((double)var14.field_177962_a + 0.5D, (double)(var14.field_177960_b + 1), (double)var14.field_177961_c + 0.5D, mc.field_71439_g);
                                 if ((Boolean)this.doFacePlace.getValue()) {
                                    if (var17 >= (double)(Integer)this.minDamage.getValue() || ((EntityLivingBase)var12).func_110143_aJ() + ((EntityLivingBase)var12).func_110139_bj() <= (float)(Integer)this.facePlaceHealth.getValue()) {
                                       break;
                                    }
                                 } else {
                                    if (var17 < (double)(Integer)this.minDamage.getValue()) {
                                       continue;
                                    }
                                    break;
                                 }
                              }
                           } while(var17 <= var9);
                        } while((Boolean)this.antiSuicide.getValue() && ((double)(mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj()) - var19 <= 7.0D || var19 > var17));
                     } while(((AutoCrystal.MaxSelfDamageModes)this.maxSelfDamageMode.getValue()).equals(AutoCrystal.MaxSelfDamageModes.ABSOLUTE) && var19 > (double)(Integer)this.maxSelfDamage.getValue());
                  } while(((AutoCrystal.MaxSelfDamageModes)this.maxSelfDamageMode.getValue()).equals(AutoCrystal.MaxSelfDamageModes.RATIO) && var17 * (Double)this.selfDamageRatio.getValue() < var19);

                  var9 = var17;
                  var6 = var14;
                  var4 = var12;
               }
            }
         }
      }
   }

   private static float getNametagSize(BlockPos var0) {
      ScaledResolution var1 = new ScaledResolution(Minecraft.func_71410_x());
      double var2 = (double)var1.func_78325_e() / Math.pow((double)var1.func_78325_e(), 2.0D);
      return (float)((double)((float)var2) + mc.field_71439_g.func_70011_f((double)var0.func_177958_n(), (double)var0.func_177956_o(), (double)var0.func_177952_p()) / 7.0D);
   }

   private boolean canPlaceCrystal(BlockPos var1) {
      BlockPos var2 = var1.func_177982_a(0, 1, 0);
      BlockPos var3 = var1.func_177982_a(0, 2, 0);
      return (mc.field_71441_e.func_180495_p(var1).func_177230_c() == Blocks.field_150357_h || mc.field_71441_e.func_180495_p(var1).func_177230_c() == Blocks.field_150343_Z) && mc.field_71441_e.func_180495_p(var2).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(var3).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(var2)).isEmpty() && mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(var3)).isEmpty();
   }

   private boolean lambda$new$12(Integer var1) {
      return (Boolean)this.doFacePlace.getValue();
   }

   private static Entity lambda$onUpdate$23(Entity var0) {
      return var0;
   }

   private boolean lambda$onUpdate$25(Entity var1) {
      return this.shouldBreak(var1);
   }

   private boolean lambda$new$7(Integer var1) {
      return (Boolean)this.place.getValue();
   }

   private boolean lambda$new$4(Integer var1) {
      return (Boolean)this.doBreak.getValue();
   }

   private boolean lambda$new$15(Boolean var1) {
      return (Boolean)this.doRender.getValue();
   }

   private boolean lambda$new$13(Integer var1) {
      return (Boolean)this.multiPlace.getValue();
   }

   private static void resetRotation() {
      if (isSpoofingAngles) {
         yaw = (double)mc.field_71439_g.field_70177_z;
         pitch = (double)mc.field_71439_g.field_70125_A;
         isSpoofingAngles = false;
      }

   }

   public AutoCrystal() {
      this.maxSelfDamageMode = this.register(SettingsManager.e("Max Self Damage Mode", AutoCrystal.MaxSelfDamageModes.ABSOLUTE));
      this.maxSelfDamage = this.register(SettingsManager.integerBuilder("Max Self Damage").withMinimum(0).withMaximum(20).withValue((int)4).withVisibility(this::lambda$new$0).build());
      this.selfDamageRatio = this.register(SettingsManager.doubleBuilder("Self Damage %").withMinimum(0.1D).withMaximum(1.0D).withValue((Number)0.5D).withVisibility(this::lambda$new$1).build());
      this.doBreak = this.register(SettingsManager.b("Break Crystals", true));
      this.breakSpeed = this.register(SettingsManager.integerBuilder("APS").withMinimum(0).withMaximum(20).withValue((int)15).withVisibility(this::lambda$new$2).build());
      this.breakRange = this.register(SettingsManager.integerBuilder("Break Range").withMinimum(1).withMaximum(6).withValue((int)6).withVisibility(this::lambda$new$3).build());
      this.breakWallsRange = this.register(SettingsManager.integerBuilder("Break through Walls Range").withMinimum(1).withMaximum(6).withValue((int)3).withVisibility(this::lambda$new$4).build());
      this.antiBreakStuck = this.register(SettingsManager.b("Anti Break Stuck", true));
      this.maxBreakAttempts = this.register(SettingsManager.integerBuilder("Max Break Attempts").withMinimum(1).withMaximum(30).withValue((int)20).withVisibility(this::lambda$new$5).build());
      this.place = this.register(SettingsManager.b("Place Crystals", true));
      this.placeDelay = this.register(SettingsManager.integerBuilder("Place Delay").withMinimum(0).withMaximum(50).withValue((int)0).withVisibility(this::lambda$new$6).build());
      this.placeRange = this.register(SettingsManager.integerBuilder("Place Range").withMinimum(1).withMaximum(6).withValue((int)6).withVisibility(this::lambda$new$7).build());
      this.rayTrace = this.register(SettingsManager.booleanBuilder("Ray Trace").withValue(false).withVisibility(this::lambda$new$8).build());
      this.placeWallsRange = this.register(SettingsManager.integerBuilder("Place through Walls Range").withMinimum(1).withMaximum(6).withValue((int)5).withVisibility(this::lambda$new$9).build());
      this.overrideYPlaceRange = this.register(SettingsManager.booleanBuilder("Override Y Range").withValue(false).withVisibility(this::lambda$new$10).build());
      this.yPlaceRange = this.register(SettingsManager.integerBuilder("Y Place Range").withMinimum(1).withMaximum(6).withValue((int)4).withVisibility(this::lambda$new$11).build());
      this.doFacePlace = this.register(SettingsManager.b("Face Place", false));
      this.facePlaceHealth = this.register(SettingsManager.integerBuilder("Face Place Health").withMinimum(1).withMaximum(20).withValue((int)4).withVisibility(this::lambda$new$12).build());
      this.multiPlace = this.register(SettingsManager.b("MultiPlace", false));
      this.multiPlaceSpeed = this.register(SettingsManager.integerBuilder("MultiPlaceSpeed").withMinimum(1).withMaximum(10).withValue((int)5).withVisibility(this::lambda$new$13).build());
      this.doRender = this.register(SettingsManager.b("Render", false));
      this.doDamageRender = this.register(SettingsManager.booleanBuilder("Damage").withValue(false).withVisibility(this::lambda$new$14));
      this.rainbow = this.register(SettingsManager.booleanBuilder("Cycle").withValue(false).withVisibility(this::lambda$new$15));
      this.Red = this.register(SettingsManager.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue((int)255).withVisibility(this::lambda$new$16).build());
      this.Green = this.register(SettingsManager.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue((int)255).withVisibility(this::lambda$new$17).build());
      this.Blue = this.register(SettingsManager.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue((int)255).withVisibility(this::lambda$new$18).build());
      this.isActive = false;
      this.placeSystemTime = -1L;
      this.breakSystemTime = -1L;
      this.multiPlaceSystemTime = -1L;
      this.antiStuckSystemTime = -1L;
      this.switchCooldown = false;
      this.placements = 0;
      this.packet = new Packet[1];
      this.crystalBreakTracker = new HashMap();
      this.placeLocations = new CopyOnWriteArrayList();
      this.cercle = "https://www.youtube.com/watch?v=z05IO3gWlog";
      this.onPacketSendEvent = new Listener(this::lambda$new$19, new Predicate[0]);
      this.onPacketReceiveEvent = new Listener(this::lambda$new$20, new Predicate[0]);
      this.onPacketReceiveEventpSilent = new Listener(this::lambda$new$21, new Predicate[0]);
   }

   private void lambda$new$19(PacketEvent.Send var1) {
      this.packet[0] = var1.getPacket();
      if (this.packet[0] instanceof CPacketPlayer && isSpoofingAngles) {
         ((CPacketPlayer)this.packet[0]).field_149476_e = (float)yaw;
         ((CPacketPlayer)this.packet[0]).field_149473_f = (float)pitch;
      }

   }

   private boolean placeCrystal(int var1, boolean var2, BlockPos var3) {
      if (!var2 && mc.field_71439_g.field_71071_by.field_70461_c != var1) {
         if ((Boolean)this.autoSwitch.getValue()) {
            mc.field_71439_g.field_71071_by.field_70461_c = var1;
            resetRotation();
            this.switchCooldown = true;
         }

         return true;
      } else {
         this.lookAtPacket((double)var3.field_177962_a + 0.5D, (double)var3.field_177960_b - 0.5D, (double)var3.field_177961_c + 0.5D, mc.field_71439_g);
         RayTraceResult var4 = mc.field_71441_e.func_72933_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + (double)mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d((double)var3.field_177962_a + 0.5D, (double)var3.field_177960_b - 0.5D, (double)var3.field_177961_c + 0.5D));
         EnumFacing var5;
         if (var4 != null && var4.field_178784_b != null) {
            var5 = var4.field_178784_b;
         } else {
            var5 = EnumFacing.UP;
         }

         if (this.switchCooldown) {
            this.switchCooldown = false;
            return true;
         } else {
            if (System.nanoTime() / 1000000L - this.placeSystemTime >= (long)((Integer)this.placeDelay.getValue() * 2)) {
               mc.field_71439_g.field_71174_a.func_147297_a(new CPacketPlayerTryUseItemOnBlock(var3, var5, var2 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
               this.placeLocations.add(new AutoCrystal.PlaceLocation(var3.func_177958_n(), var3.func_177956_o(), var3.func_177952_p()));
               ++this.placements;
               this.antiStuckSystemTime = System.nanoTime() / 1000000L;
               this.placeSystemTime = System.nanoTime() / 1000000L;
            }

            return false;
         }
      }
   }

   private boolean lambda$new$18(Integer var1) {
      return (Boolean)this.doRender.getValue();
   }

   private void lambda$new$20(PacketEvent.Receive var1) {
      if (var1.getPacket() instanceof SPacketSoundEffect && (Boolean)this.antiDesync.getValue()) {
         SPacketSoundEffect var2 = (SPacketSoundEffect)var1.getPacket();
         if (var2.func_186977_b() == SoundCategory.BLOCKS && var2.func_186978_a() == SoundEvents.field_187539_bB) {
            Iterator var3 = mc.field_71441_e.field_72996_f.iterator();

            while(var3.hasNext()) {
               Entity var4 = (Entity)var3.next();
               if (var4 instanceof EntityEnderCrystal && var4.func_70011_f(var2.func_149207_d(), var2.func_149211_e(), var2.func_149210_f()) <= 6.0D) {
                  var4.func_70106_y();
               }
            }
         }
      }

   }

   private boolean lambda$new$1(Double var1) {
      return ((AutoCrystal.MaxSelfDamageModes)this.maxSelfDamageMode.getValue()).equals(AutoCrystal.MaxSelfDamageModes.RATIO);
   }

   private boolean lambda$new$3(Integer var1) {
      return (Boolean)this.doBreak.getValue();
   }

   private void lambda$new$21(PacketEvent.Send var1) {
      if (var1.getPacket() instanceof SPacketSpawnObject) {
         SPacketSpawnObject var2 = (SPacketSpawnObject)var1.getPacket();
         if (var1.getEra() == EditmeEvent.Era.PRE && var2.func_148993_l() == 51) {
            Iterator var3 = this.placeLocations.iterator();

            while(var3.hasNext()) {
               AutoCrystal.PlaceLocation var4 = (AutoCrystal.PlaceLocation)var3.next();
               if (!var4.placed && var4.func_185332_f((int)var2.func_186880_c(), (int)var2.func_186882_d() - 1, (int)var2.func_186881_e()) <= 1.0D) {
                  var4.placed = true;
                  if ((Boolean)this.pSilent.getValue() && !mc.field_71439_g.func_70644_a(MobEffects.field_76437_t)) {
                     var1.cancel();
                     CPacketUseEntity var5 = new CPacketUseEntity();
                     var5.field_149567_a = var2.func_149001_c();
                     var5.field_149566_b = Action.ATTACK;
                     float[] var6 = MathUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), new Vec3d(var2.func_186880_c() + 0.5D, var2.func_186882_d() + 0.5D, var2.func_186881_e() + 0.5D));
                     mc.field_71439_g.field_71174_a.func_147297_a(new CPacketAnimation(EnumHand.MAIN_HAND));
                     mc.field_71439_g.field_71174_a.func_147297_a(new Rotation(var6[0], var6[1], mc.field_71439_g.field_70122_E));
                     mc.field_71439_g.field_71174_a.func_147297_a(var5);
                  }
               }
            }
         }
      }

   }

   private boolean shouldBreak(Entity var1) {
      if (!(Boolean)this.antiBreakStuck.getValue()) {
         return true;
      } else if (!this.crystalBreakTracker.containsKey(var1.func_110124_au())) {
         this.crystalBreakTracker.put(var1.func_110124_au(), 0);
         return true;
      } else {
         return (Integer)this.crystalBreakTracker.get(var1.func_110124_au()) <= (Integer)this.maxBreakAttempts.getValue();
      }
   }

   private boolean lambda$new$14(Boolean var1) {
      return (Boolean)this.doRender.getValue();
   }

   private boolean lambda$new$10(Boolean var1) {
      return (Boolean)this.place.getValue();
   }

   private boolean lambda$new$5(Integer var1) {
      return (Boolean)this.doBreak.getValue();
   }

   public static float calculateDamage(double var0, double var2, double var4, Entity var6) {
      float var7 = 12.0F;
      double var8 = var6.func_70011_f(var0, var2, var4) / 12.0D;
      Vec3d var10 = new Vec3d(var0, var2, var4);
      double var11 = (double)var6.field_70170_p.func_72842_a(var10, var6.func_174813_aQ());
      double var13 = (1.0D - var8) * var11;
      float var15 = (float)((int)((var13 * var13 + var13) / 2.0D * 7.0D * 12.0D + 1.0D));
      double var16 = 1.0D;
      if (var6 instanceof EntityLivingBase) {
         var16 = (double)getBlastReduction((EntityLivingBase)var6, getDamageMultiplied(var15), new Explosion(mc.field_71441_e, (Entity)null, var0, var2, var4, 6.0F, false, true));
      }

      return (float)var16;
   }

   private List findCrystalBlocks() {
      NonNullList var1 = NonNullList.func_191196_a();
      var1.addAll((Collection)this.getSphere(getPlayerPos(), ((Integer)this.placeRange.getValue()).floatValue(), (Integer)this.placeRange.getValue(), false, true, 0).stream().filter(this::canPlaceCrystal).collect(Collectors.toList()));
      return var1;
   }

   private static float getDamageMultiplied(float var0) {
      int var1 = mc.field_71441_e.func_175659_aa().func_151525_a();
      return var0 * (var1 == 0 ? 0.0F : (var1 == 2 ? 1.0F : (var1 == 1 ? 0.5F : 1.5F)));
   }

   private boolean lambda$new$16(Integer var1) {
      return (Boolean)this.doRender.getValue();
   }

   public static float getBlastReduction(EntityLivingBase var0, float var1, Explosion var2) {
      if (var0 instanceof EntityPlayer) {
         EntityPlayer var3 = (EntityPlayer)var0;
         DamageSource var4 = DamageSource.func_94539_a(var2);
         var1 = CombatRules.func_189427_a(var1, (float)var3.func_70658_aO(), (float)var3.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
         int var5 = EnchantmentHelper.func_77508_a(var3.func_184193_aE(), var4);
         float var6 = MathHelper.func_76131_a((float)var5, 0.0F, 20.0F);
         var1 *= 1.0F - var6 / 25.0F;
         if (var0.func_70644_a(Potion.func_188412_a(11))) {
            var1 -= var1 / 4.0F;
         }

         return var1;
      } else {
         var1 = CombatRules.func_189427_a(var1, (float)var0.func_70658_aO(), (float)var0.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
         return var1;
      }
   }

   private boolean nextToTarget(BlockPos var1, Entity var2) {
      if ((int)var2.field_70163_u == var1.func_177956_o() + 1) {
         if ((int)var2.field_70165_t + 1 == var1.func_177958_n()) {
            return true;
         }

         if ((int)var2.field_70165_t - 1 == var1.func_177958_n()) {
            return true;
         }

         if ((int)var2.field_70161_v + 1 == var1.func_177952_p()) {
            return true;
         }

         if ((int)var2.field_70161_v - 1 == var1.func_177952_p()) {
            return true;
         }
      }

      return false;
   }

   private static boolean lambda$onUpdate$26(EntityPlayer var0) {
      return !Friends.isFriend(var0.func_70005_c_());
   }

   private static boolean lambda$onUpdate$22(Entity var0) {
      return var0 instanceof EntityEnderCrystal;
   }

   private static Float lambda$onUpdate$24(Entity var0) {
      return mc.field_71439_g.func_70032_d(var0);
   }

   private static void setYawAndPitch(float var0, float var1) {
      yaw = (double)var0;
      pitch = (double)var1;
      isSpoofingAngles = true;
   }

   private boolean isExplosionProof(Vec3d var1) {
      BlockPos var2 = new BlockPos(var1.field_72450_a, var1.field_72448_b, var1.field_72449_c);
      Block var3 = mc.field_71441_e.func_180495_p(var2).func_177230_c();
      if (var3 == Blocks.field_150357_h) {
         return true;
      } else if (var3 == Blocks.field_150343_Z) {
         return true;
      } else if (var3 == Blocks.field_150467_bQ) {
         return true;
      } else if (var3 == Blocks.field_150477_bB) {
         return true;
      } else {
         return var3 == Blocks.field_180401_cv;
      }
   }

   private static class SurroundOffsets {
      private static final Vec3d EAST1 = new Vec3d(1.0D, 0.0D, 0.0D);
      private static final Vec3d NORTH1 = new Vec3d(0.0D, 0.0D, -1.0D);
      private static final Vec3d NORTH2 = new Vec3d(0.0D, 0.0D, -2.0D);
      private static final Vec3d WEST1 = new Vec3d(-1.0D, 0.0D, 0.0D);
      private static final Vec3d SOUTH2 = new Vec3d(0.0D, 0.0D, 2.0D);
      private static final Vec3d EAST2 = new Vec3d(2.0D, 0.0D, 0.0D);
      private static final Vec3d WEST2 = new Vec3d(-2.0D, 0.0D, 0.0D);
      private static final Vec3d SOUTH1 = new Vec3d(0.0D, 0.0D, 1.0D);

      static Vec3d access$100() {
         return NORTH1;
      }

      static Vec3d access$700() {
         return WEST1;
      }

      static Vec3d access$000() {
         return NORTH2;
      }

      static Vec3d access$200() {
         return EAST2;
      }

      static Vec3d access$300() {
         return EAST1;
      }

      static Vec3d access$500() {
         return SOUTH1;
      }

      static Vec3d access$600() {
         return WEST2;
      }

      static Vec3d access$400() {
         return SOUTH2;
      }
   }

   private static enum MaxSelfDamageModes {
      ABSOLUTE;

      private static final AutoCrystal.MaxSelfDamageModes[] $VALUES = new AutoCrystal.MaxSelfDamageModes[]{ABSOLUTE, RATIO};
      RATIO;
   }

   private static final class PlaceLocation extends Vec3i {
      private boolean placed;

      private PlaceLocation(int var1, int var2, int var3) {
         super(var1, var2, var3);
         this.placed = false;
      }

      static boolean access$900(AutoCrystal.PlaceLocation var0) {
         return var0.placed;
      }

      PlaceLocation(int var1, int var2, int var3, Object var4) {
         this(var1, var2, var3);
      }

      static boolean access$902(AutoCrystal.PlaceLocation var0, boolean var1) {
         return var0.placed = var1;
      }
   }
}
