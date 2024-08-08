package com.example.editme.modules.render;

import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.util.client.TrajectoryCalculator;
import com.example.editme.util.color.HueCycler;
import com.example.editme.util.render.EditmeTessellator;
import com.example.editme.util.render.GeometryMasks;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

@Module.Info(
   name = "Trajectories",
   category = Module.Category.RENDER
)
public class Trajectories extends Module {
   HueCycler cycler = new HueCycler(100);
   ArrayList positions = new ArrayList();

   public void onWorldRender(RenderEvent var1) {
      try {
         mc.field_71441_e.field_72996_f.stream().filter(Trajectories::lambda$onWorldRender$0).map(Trajectories::lambda$onWorldRender$1).forEach(this::lambda$onWorldRender$2);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void lambda$onWorldRender$2(EntityLivingBase var1) {
      this.positions.clear();
      TrajectoryCalculator.ThrowingType var2 = TrajectoryCalculator.getThrowType(var1);
      if (var2 != TrajectoryCalculator.ThrowingType.NONE) {
         TrajectoryCalculator.FlightPath var3 = new TrajectoryCalculator.FlightPath(var1, var2);

         while(!var3.isCollided()) {
            var3.onUpdate();
            this.positions.add(var3.position);
         }

         BlockPos var4 = null;
         if (var3.getCollidingTarget() != null) {
            var4 = var3.getCollidingTarget().func_178782_a();
         }

         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glDisable(2929);
         if (var4 != null) {
            EditmeTessellator.prepare(7);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
            EditmeTessellator.drawBox(var4, 872415231, (Integer)GeometryMasks.FACEMAP.get(var3.getCollidingTarget().field_178784_b));
            EditmeTessellator.release();
         }

         if (!this.positions.isEmpty()) {
            GL11.glDisable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glLineWidth(2.0F);
            if (var4 != null) {
               GL11.glColor3f(1.0F, 1.0F, 1.0F);
            } else {
               this.cycler.setNext();
            }

            GL11.glBegin(1);
            Vec3d var5 = (Vec3d)this.positions.get(0);
            GL11.glVertex3d(var5.field_72450_a - mc.func_175598_ae().field_78725_b, var5.field_72448_b - mc.func_175598_ae().field_78726_c, var5.field_72449_c - mc.func_175598_ae().field_78723_d);
            Iterator var6 = this.positions.iterator();

            while(var6.hasNext()) {
               Vec3d var7 = (Vec3d)var6.next();
               GL11.glVertex3d(var7.field_72450_a - mc.func_175598_ae().field_78725_b, var7.field_72448_b - mc.func_175598_ae().field_78726_c, var7.field_72449_c - mc.func_175598_ae().field_78723_d);
               GL11.glVertex3d(var7.field_72450_a - mc.func_175598_ae().field_78725_b, var7.field_72448_b - mc.func_175598_ae().field_78726_c, var7.field_72449_c - mc.func_175598_ae().field_78723_d);
               if (var4 == null) {
                  this.cycler.setNext();
               }
            }

            GL11.glEnd();
            GL11.glEnable(3042);
            GL11.glEnable(3553);
            this.cycler.reset();
         }
      }
   }

   private static EntityLivingBase lambda$onWorldRender$1(Entity var0) {
      return (EntityLivingBase)var0;
   }

   private static boolean lambda$onWorldRender$0(Entity var0) {
      return var0 instanceof EntityLivingBase;
   }
}
