package rina.turok.bope.bopemod.hacks.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventPacket;
import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.bope.bopemod.util.BopeUtilItem;
import rina.turok.bope.bopemod.util.BopeUtilMath;
import rina.turok.turok.draw.TurokRenderHelp;

public class BopeAutoCrystal extends BopeModule {
   BopeSetting spearate_1 = this.create("info", "AutoCrystalInfo1", "Render");
   BopeSetting rgb = this.create("RGB Effect", "AutoCrystalRGBEffect", false);
   BopeSetting color_r = this.create("R", "AutoCrystalColorR", 255, 0, 255);
   BopeSetting color_g = this.create("G", "AutoCrystalColorG", 255, 0, 255);
   BopeSetting color_b = this.create("B", "AutoCrystalColorB", 255, 0, 255);
   BopeSetting solid_a = this.create("Solid R", "AutoCrystalSolidA", 255, 0, 255);
   BopeSetting outline_a = this.create("Outline R", "AutoCrystalOutlineA", 255, 0, 255);
   BopeSetting spearate_2 = this.create("info", "AutoCrystalInfo2", "Settings");
   BopeSetting find_player = this.create("Player", "AutoCrystalPlayer", true);
   BopeSetting find_hostile = this.create("Hostile", "AutoCrystalHostile", true);
   BopeSetting double_place = this.create("Double Place", "AutoCrystalDoublePlace", true);
   BopeSetting auto_slot = this.create("Auto Switch", "AutoCrystalAutoSlot", true);
   BopeSetting anti_weakness = this.create("Anti Weakness", "AutoCrystalAntiWeakness", true);
   BopeSetting offhand_crystal = this.create("Offhand Crystal Hit", "AutoCrystalOffhandCrystalHit", false);
   BopeSetting ray_trace_util = this.create("Ray Trace", "AutoCrystalRayTrace", false);
   BopeSetting face_place_min = this.create("Minimum", "AutoCrystalMinimum", 2, 0, 36);
   BopeSetting speed_place = this.create("Place Delay", "AutoCrystalPlaceDelay", 8, 0, 10);
   BopeSetting speed_hit = this.create("Speed Hit", "AutoCrystalSpeedHit", 16, 0, 20);
   BopeSetting range_hit = this.create("Range Hit", "AutoCrystalRangeHit", 5, 0, 8);
   BopeSetting range_place = this.create("Range Place", "AutoCrystalRangePlace", 5, 0, 6);
   BopeSetting range_enemy = this.create("Range Enemy", "AutoCrystalRangeEnemy", 13, 0, 16);
   private BlockPos render;
   private Entity render_entity;
   private long system_time_hit = -1L;
   private long system_time_place = -1L;
   private long system_time_double = -1L;
   private long system_time_ms = -1L;
   private static boolean toggle_pitch = false;
   private boolean switch_cooldown = false;
   private boolean is_attacking = false;
   private int old_slot = -1;
   private int new_slot;
   private int places;
   private static boolean is_spoofing_angles;
   private static double yaw;
   private static double pitch;
   private static boolean offhand;
   private static boolean only_crystal_render;
   private static boolean can_break;
   @EventHandler
   private Listener<BopeEventPacket.SendPacket> listener = new Listener<>(event -> {
      Packet packet = event.get_packet();
      if (packet instanceof CPacketPlayer && is_spoofing_angles) {
         ((CPacketPlayer)packet).yaw = (float)yaw;
         ((CPacketPlayer)packet).pitch = (float)pitch;
      }
   });

   public BopeAutoCrystal() {
      super(BopeCategory.BOPE_COMBAT);
      this.name = "Auto Crystal";
      this.tag = "AutoCrystal";
      this.description = "Crystal boom.";
      this.release("B.O.P.E - module - B.O.P.E");
   }

   public void enable() {
      this.render = null;
      this.render_entity = null;
      this.reset_rotation();
   }

   public void update() {
      EntityEnderCrystal crystal = (EntityEnderCrystal)this.mc.world.loadedEntityList.stream().filter((entityx) -> {
         return entityx instanceof EntityEnderCrystal;
      }).map((entityx) -> {
         return (EntityEnderCrystal)entityx;
      }).min(Comparator.comparing((c) -> {
         return this.mc.player.getDistance(c);
      })).orElse((EntityEnderCrystal) null);
      int crystal_slot;
      if (crystal != null && this.mc.player.getDistance(crystal) <= (float)this.range_hit.get_value(1)) {
         if (System.nanoTime() / 1000000L - this.system_time_hit >= (long)(400 - this.speed_hit.get_value(1) * 20)) {
            if (this.anti_weakness.get_value(true) && this.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
               if (this.is_attacking) {
                  this.old_slot = this.mc.player.inventory.currentItem;
                  this.is_attacking = false;
               }

               this.new_slot = -1;

               for(crystal_slot = 0; crystal_slot < 9; ++crystal_slot) {
                  ItemStack stack = this.mc.player.inventory.getStackInSlot(crystal_slot);
                  if (stack != ItemStack.EMPTY) {
                     if (stack.getItem() instanceof ItemSword) {
                        this.new_slot = crystal_slot;
                        break;
                     }

                     if (stack.getItem() instanceof ItemTool) {
                        this.new_slot = crystal_slot;
                        break;
                     }
                  }
               }

               if (this.new_slot != -1) {
                  this.mc.player.inventory.currentItem = this.new_slot;
                  this.switch_cooldown = true;
               }
            }

            ItemStack off_hand_item = this.mc.player.getHeldItemOffhand();
            if (off_hand_item != null && off_hand_item.getItem() == Items.SHIELD && off_hand_item.getItem() != Items.END_CRYSTAL) {
               this.mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
            }

            BopeUtilMath.calcule_look_at(crystal.posX, crystal.posY, crystal.posZ, this.mc.player);
            this.mc.playerController.attackEntity(this.mc.player, crystal);
            this.mc.player.swingArm(offhand && this.offhand_crystal.get_value(true) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            this.system_time_hit = System.nanoTime() / 1000000L;
         }

         if (!this.double_place.get_value(true)) {
            if (System.nanoTime() / 1000000L - this.system_time_double >= (long)(20 * this.speed_hit.get_value(1)) && System.nanoTime() / 1000000L - this.system_time_ms <= (long)(400 + (400 - this.speed_hit.get_value(1) * 20))) {
               this.system_time_double = System.nanoTime() / 1000000L;
               return;
            }

            if (System.nanoTime() / 1000000L - this.system_time_ms <= (long)(400 + (400 - this.speed_hit.get_value(1) * 20))) {
               return;
            }
         }
      } else {
         this.reset_rotation();
         if (this.old_slot != -1) {
            this.mc.player.inventory.currentItem = this.old_slot;
            this.old_slot = -1;
         }

         this.is_attacking = false;
      }

      crystal_slot = this.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? this.mc.player.inventory.currentItem : -1;
      if (crystal_slot == -1) {
         crystal_slot = BopeUtilItem.get_hotbar_item_slot(Items.END_CRYSTAL);
      }

      offhand = false;
      if (this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
         offhand = true;
      } else if (crystal_slot == -1) {
         return;
      }

      List blocks = this.find();
      List entities = new ArrayList();
      if (this.find_player.get_value(true)) {
         entities.addAll((Collection)this.mc.world.playerEntities.stream().filter((entity_player) -> {
            return !Bope.get_friend_manager().is_friend(entity_player.getName());
         }).collect(Collectors.toList()));
      }

      entities.addAll((Collection)this.mc.world.loadedEntityList.stream().filter((entityx) -> {
         return entityx instanceof EntityLivingBase && entityx instanceof IMob && this.find_hostile.get_value(true);
      }).collect(Collectors.toList()));
      BlockPos q = null;
      double damage = 0.5D;
      Iterator var8 = entities.iterator();

      label185:
      while(true) {
         Entity entity;
         do {
            do {
               do {
                  if (!var8.hasNext()) {
                     if (damage == 0.5D) {
                        this.render = null;
                        this.render_entity = null;
                        this.reset_rotation();
                        return;
                     }

                     this.render = q;
                     if (!offhand && this.mc.player.inventory.currentItem != crystal_slot) {
                        if (this.auto_slot.get_value(true)) {
                           this.mc.player.inventory.currentItem = crystal_slot;
                           this.reset_rotation();
                           this.switch_cooldown = true;
                        }

                        return;
                     }

                     BopeUtilMath.calcule_look_at((double)q.x + 0.5D, (double)q.y - 0.5D, (double)q.z + 0.5D, this.mc.player);
                     EnumFacing f;
                     if (this.ray_trace_util.get_value(true)) {
                        RayTraceResult result = this.mc.world.rayTraceBlocks(new Vec3d(this.mc.player.posX, this.mc.player.posY + (double)this.mc.player.getEyeHeight(), this.mc.player.posZ), new Vec3d((double)q.x + 0.5D, (double)q.y - 0.5D, (double)q.z + 0.5D));
                        if (result != null && result.sideHit != null) {
                           f = result.sideHit;
                        } else {
                           f = EnumFacing.UP;
                        }
                     } else {
                        f = EnumFacing.DOWN;
                     }

                     if (this.switch_cooldown) {
                        this.switch_cooldown = false;
                        return;
                     }

                     if (System.nanoTime() / 1000000L - this.system_time_place >= (long)(this.speed_place.get_value(1) * 2)) {
                        ItemStack off_hand_item = this.mc.player.getHeldItemOffhand();
                        if (off_hand_item != null && off_hand_item.getItem() == Items.SHIELD && off_hand_item.getItem() != Items.END_CRYSTAL) {
                           this.mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
                        }

                        this.mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(q, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
                        ++this.places;
                        this.system_time_place = System.nanoTime() / 1000000L;
                        this.system_time_ms = System.nanoTime() / 1000000L;
                     }

                     if (is_spoofing_angles) {
                        EntityPlayerSP var10000;
                        if (toggle_pitch) {
                           var10000 = this.mc.player;
                           var10000.rotationPitch = (float)((double)var10000.rotationPitch + 4.0E-4D);
                           toggle_pitch = false;
                        } else {
                           var10000 = this.mc.player;
                           var10000.rotationPitch = (float)((double)var10000.rotationPitch - 4.0E-4D);
                           toggle_pitch = true;
                        }
                     }

                     return;
                  }

                  entity = (Entity)var8.next();
               } while(entity == this.mc.player);
            } while(((EntityLivingBase)entity).getHealth() <= 0.0F);
         } while(((EntityLivingBase)entity).isDead);

         Iterator var10 = blocks.iterator();

         while(true) {
            BlockPos pos;
            double dm;
            double sf;
            do {
               do {
                  double sq;
                  do {
                     if (!var10.hasNext()) {
                        continue label185;
                     }

                     pos = (BlockPos)var10.next();
                     sq = entity.getDistanceSq(pos);
                  } while(sq > (double)(this.range_enemy.get_value(1) * this.range_enemy.get_value(1)));

                  dm = (double)this.calcule_damage((double)pos.x + 0.5D, (double)(pos.y + 1), (double)pos.z + 0.5D, entity);
               } while(dm <= damage);

               sf = (double)this.calcule_damage((double)pos.x + 0.5D, (double)(pos.y + 1), (double)pos.z + 0.5D, this.mc.player);
            } while(sf > dm && dm >= (double)((EntityLivingBase)entity).getHealth());

            if (sf - 0.5D <= (double)this.mc.player.getHealth() && dm >= (double)this.face_place_min.get_value(1)) {
               damage = dm;
               q = pos;
               this.render_entity = entity;
            }
         }
      }
   }

   public void render(BopeEventRender event) {
      float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0F};
      int color_rgb = Color.HSBtoRGB(tick_color[0], 1.0F, 1.0F);
      int r;
      int g;
      int b;
      if (this.rgb.get_value(true)) {
         r = color_rgb >> 16 & 255;
         g = color_rgb >> 8 & 255;
         b = color_rgb & 255;
         this.color_r.set_value(r);
         this.color_g.set_value(g);
         this.color_b.set_value(b);
      } else {
         r = this.color_r.get_value(1);
         g = this.color_g.get_value(2);
         b = this.color_b.get_value(3);
      }

      if (this.render != null) {
         TurokRenderHelp.prepare("quads");
         TurokRenderHelp.draw_cube(TurokRenderHelp.get_buffer_build(), (float)this.render.x, (float)this.render.y, (float)this.render.z, 1.0F, 1.0F, 1.0F, r, g, b, this.solid_a.get_value(1), "all");
         TurokRenderHelp.release();
         TurokRenderHelp.prepare("lines");
         TurokRenderHelp.draw_cube_line(TurokRenderHelp.get_buffer_build(), (float)this.render.x, (float)this.render.y, (float)this.render.z, 1.0F, 1.0F, 1.0F, r, g, b, this.outline_a.get_value(1), "all");
         TurokRenderHelp.release();
      }

   }

   private boolean canPlaceCrystal(BlockPos blockPos) {
      BlockPos boost = blockPos.add(0, 1, 0);
      BlockPos boost2 = blockPos.add(0, 2, 0);
      return (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK
              || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN)
              && mc.world.getBlockState(boost).getBlock() == Blocks.AIR
              && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR
              && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty()
              && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
   }

   public BlockPos get_player_as_block_pos() {
      return new BlockPos(Math.floor(this.mc.player.posX), Math.floor(this.mc.player.posY), Math.floor(this.mc.player.posZ));
   }

   private List find() {
      NonNullList positions = NonNullList.create();
      positions.addAll((Collection) get_sphere(get_player_as_block_pos(), range_place.get_value(1), range_place.get_value(1), false, true, 0).stream().filter(blockPos -> canPlaceCrystal((BlockPos) blockPos)).collect(Collectors.toList()));
      return positions;
   }


   public List get_sphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
      List sphere_list = new ArrayList();
      int cx = loc.getX();
      int cy = loc.getY();
      int cz = loc.getZ();

      for(int x = cx - (int)r; (float)x <= (float)cx + r; ++x) {
         for(int z = cz - (int)r; (float)z <= (float)cz + r; ++z) {
            for(int y = sphere ? cy - (int)r : cy; (float)y < (sphere ? (float)cy + r : (float)(cy + h)); ++y) {
               double dist = (double)((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0));
               if (dist < (double)(r * r) && (!hollow || dist >= (double)((r - 1.0F) * (r - 1.0F)))) {
                  BlockPos spheres = new BlockPos(x, y + plus_y, z);
                  sphere_list.add(spheres);
               }
            }
         }
      }

      return sphere_list;
   }

   public float calcule_damage(double pos_x, double pos_y, double pos_z, Entity entity) {
      float double_explosion_size = 12.0F;
      double distanced_size = entity.getDistance(pos_x, pos_y, pos_z) / (double)double_explosion_size;
      Vec3d vec3d = new Vec3d(pos_x, pos_y, pos_z);
      double block_desinty = (double)entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
      double v = (1.0D - distanced_size) * block_desinty;
      float damage = (float)((int)((v * v + v) / 2.0D * 7.0D * (double)double_explosion_size + 1.0D));
      double finald = 1.0D;
      if (entity instanceof EntityLivingBase) {
         finald = (double)this.get_blast_reduction((EntityLivingBase)entity, this.get_damage_multiplied(damage), new Explosion(this.mc.world, (Entity)null, pos_x, pos_y, pos_z, 6.0F, false, true));
      }

      return (float)finald;
   }

   public float get_blast_reduction(EntityLivingBase entity, float damage, Explosion explosion) {
      if (entity instanceof EntityPlayer) {
         EntityPlayer ep = (EntityPlayer)entity;
         DamageSource ds = DamageSource.causeExplosionDamage(explosion);
         damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
         int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
         float f = MathHelper.clamp((float)k, 0.0F, 20.0F);
         damage *= 1.0F - f / 25.0F;
         if (entity.isPotionActive(Potion.getPotionById(11))) {
            damage -= damage / 4.0F;
         }

         damage = Math.max(damage - ep.getAbsorptionAmount(), 0.0F);
         return damage;
      } else {
         damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
         return damage;
      }
   }

   private float get_damage_multiplied(float damage) {
      int diff = this.mc.world.getDifficulty().getId();
      return damage * (diff == 0 ? 0.0F : (diff == 2 ? 1.0F : (diff == 1 ? 0.5F : 1.5F)));
   }

   public float calcule_damage(EntityEnderCrystal crystal, Entity entity) {
      return this.calcule_damage(crystal.posX, crystal.posY, crystal.posZ, entity);
   }

   public void set_angles(double new_yaw, double new_pitch) {
      yaw = new_yaw;
      pitch = new_pitch;
      is_spoofing_angles = true;
   }

   public void reset_rotation() {
      if (is_spoofing_angles) {
         yaw = (double)this.mc.player.rotationYaw;
         pitch = (double)this.mc.player.rotationPitch;
         is_spoofing_angles = false;
      }

   }

   public int get_ping() {
      if (this.mc.player != null && this.mc.getConnection() != null && this.mc.getConnection().getPlayerInfo(this.mc.player.getName()) != null) {
         return this.mc.getConnection().getPlayerInfo(this.mc.player.getName()).getResponseTime();
      } else {
         return -1;
      }
   }
}
