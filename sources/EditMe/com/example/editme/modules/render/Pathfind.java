package com.example.editme.modules.render;

import com.example.editme.commands.Command;
import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@Module.Info(
   name = "Zombie Pathfind",
   category = Module.Category.MISC
)
public class Pathfind extends Module {
   public static ArrayList points = new ArrayList();
   static PathPoint to = null;

   public void onWorldRender(RenderEvent var1) {
      if (!points.isEmpty()) {
         GL11.glDisable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glLineWidth(1.5F);
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         GlStateManager.func_179097_i();
         GL11.glBegin(1);
         PathPoint var2 = (PathPoint)points.get(0);
         GL11.glVertex3d((double)var2.field_75839_a - mc.func_175598_ae().field_78725_b + 0.5D, (double)var2.field_75837_b - mc.func_175598_ae().field_78726_c, (double)var2.field_75838_c - mc.func_175598_ae().field_78723_d + 0.5D);

         for(int var3 = 0; var3 < points.size() - 1; ++var3) {
            PathPoint var4 = (PathPoint)points.get(var3);
            GL11.glVertex3d((double)var4.field_75839_a - mc.func_175598_ae().field_78725_b + 0.5D, (double)var4.field_75837_b - mc.func_175598_ae().field_78726_c, (double)var4.field_75838_c - mc.func_175598_ae().field_78723_d + 0.5D);
            if (var3 != points.size() - 1) {
               GL11.glVertex3d((double)var4.field_75839_a - mc.func_175598_ae().field_78725_b + 0.5D, (double)var4.field_75837_b - mc.func_175598_ae().field_78726_c, (double)var4.field_75838_c - mc.func_175598_ae().field_78723_d + 0.5D);
            }
         }

         GL11.glEnd();
         GlStateManager.func_179126_j();
      }
   }

   private static Double lambda$onUpdate$0(PathPoint var0) {
      return mc.field_71439_g.func_70011_f((double)var0.field_75839_a, (double)var0.field_75837_b, (double)var0.field_75838_c);
   }

   public void onUpdate() {
      PathPoint var1 = (PathPoint)points.stream().min(Comparator.comparing(Pathfind::lambda$onUpdate$0)).orElse((Object)null);
      if (var1 != null) {
         if (mc.field_71439_g.func_70011_f((double)var1.field_75839_a, (double)var1.field_75837_b, (double)var1.field_75838_c) <= 0.8D) {
            Iterator var2 = points.iterator();

            while(var2.hasNext()) {
               if (var2.next() == var1) {
                  var2.remove();
                  break;
               }

               var2.remove();
            }

            if (points.size() <= 1 && to != null) {
               boolean var3 = createPath(to);
               boolean var4 = points.size() <= 4;
               if (var3 && var4 || var4) {
                  points.clear();
                  to = null;
                  if (var3) {
                     this.sendNotification("Arrived!");
                  } else {
                     this.sendNotification("Can't go on: pathfinder has hit dead end");
                  }
               }
            }

         }
      }
   }

   public static boolean createPath(PathPoint var0) {
      to = var0;
      Pathfind.AnchoredWalkNodeProcessor var1 = new Pathfind.AnchoredWalkNodeProcessor(new PathPoint((int)mc.field_71439_g.field_70165_t, (int)mc.field_71439_g.field_70163_u, (int)mc.field_71439_g.field_70161_v));
      EntityZombie var2 = new EntityZombie(mc.field_71441_e);
      var2.func_184644_a(PathNodeType.WATER, 16.0F);
      var2.field_70165_t = mc.field_71439_g.field_70165_t;
      var2.field_70163_u = mc.field_71439_g.field_70163_u;
      var2.field_70161_v = mc.field_71439_g.field_70161_v;
      PathFinder var3 = new PathFinder(var1);
      Path var4 = var3.func_186336_a(mc.field_71441_e, var2, new BlockPos(var0.field_75839_a, var0.field_75837_b, var0.field_75838_c), Float.MAX_VALUE);
      var2.func_184644_a(PathNodeType.WATER, 0.0F);
      if (var4 == null) {
         Command.sendChatMessage("[Pathfind] Failed to create path!");
         return false;
      } else {
         points = new ArrayList(Arrays.asList(var4.field_75884_a));
         return ((PathPoint)points.get(points.size() - 1)).func_75829_a(var0) <= 1.0F;
      }
   }

   private static class AnchoredWalkNodeProcessor extends WalkNodeProcessor {
      PathPoint from;

      public boolean func_186323_c() {
         return true;
      }

      public boolean func_186322_e() {
         return true;
      }

      public AnchoredWalkNodeProcessor(PathPoint var1) {
         this.from = var1;
      }

      public PathPoint func_186318_b() {
         return this.from;
      }

      protected PathNodeType func_189553_b(IBlockAccess var1, int var2, int var3, int var4) {
         BlockPos var5 = new BlockPos(var2, var3, var4);
         IBlockState var6 = var1.func_180495_p(var5);
         Block var7 = var6.func_177230_c();
         Material var8 = var6.func_185904_a();
         PathNodeType var9 = var7.getAiPathNodeType(var6, var1, var5);
         if (var9 != null) {
            return var9;
         } else if (var8 == Material.field_151579_a) {
            return PathNodeType.OPEN;
         } else if (var7 != Blocks.field_150415_aT && var7 != Blocks.field_180400_cw && var7 != Blocks.field_150392_bi) {
            if (var7 == Blocks.field_150480_ab) {
               return PathNodeType.DAMAGE_FIRE;
            } else if (var7 == Blocks.field_150434_aF) {
               return PathNodeType.DAMAGE_CACTUS;
            } else if (var7 instanceof BlockDoor && var8 == Material.field_151575_d && !(Boolean)var6.func_177229_b(BlockDoor.field_176519_b)) {
               return PathNodeType.DOOR_WOOD_CLOSED;
            } else if (var7 instanceof BlockDoor && var8 == Material.field_151573_f && !(Boolean)var6.func_177229_b(BlockDoor.field_176519_b)) {
               return PathNodeType.DOOR_IRON_CLOSED;
            } else if (var7 instanceof BlockDoor && (Boolean)var6.func_177229_b(BlockDoor.field_176519_b)) {
               return PathNodeType.DOOR_OPEN;
            } else if (var7 instanceof BlockRailBase) {
               return PathNodeType.RAIL;
            } else if (var7 instanceof BlockFence || var7 instanceof BlockWall || var7 instanceof BlockFenceGate && !(Boolean)var6.func_177229_b(BlockFenceGate.field_176466_a)) {
               return PathNodeType.FENCE;
            } else if (var8 == Material.field_151586_h) {
               return PathNodeType.WALKABLE;
            } else if (var8 == Material.field_151587_i) {
               return PathNodeType.LAVA;
            } else {
               return var7.func_176205_b(var1, var5) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
            }
         } else {
            return PathNodeType.TRAPDOOR;
         }
      }

      public PathNodeType func_186330_a(IBlockAccess var1, int var2, int var3, int var4) {
         PathNodeType var5 = this.func_189553_b(var1, var2, var3, var4);
         if (var5 == PathNodeType.OPEN && var3 >= 1) {
            Block var6 = var1.func_180495_p(new BlockPos(var2, var3 - 1, var4)).func_177230_c();
            PathNodeType var7 = this.func_189553_b(var1, var2, var3 - 1, var4);
            var5 = var7 != PathNodeType.WALKABLE && var7 != PathNodeType.OPEN && var7 != PathNodeType.LAVA ? PathNodeType.WALKABLE : PathNodeType.OPEN;
            if (var7 == PathNodeType.DAMAGE_FIRE || var6 == Blocks.field_189877_df) {
               var5 = PathNodeType.DAMAGE_FIRE;
            }

            if (var7 == PathNodeType.DAMAGE_CACTUS) {
               var5 = PathNodeType.DAMAGE_CACTUS;
            }
         }

         var5 = this.func_193578_a(var1, var2, var3, var4, var5);
         return var5;
      }
   }
}
