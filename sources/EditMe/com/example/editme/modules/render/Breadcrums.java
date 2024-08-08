package com.example.editme.modules.render;

import com.example.editme.events.PacketEvent;
import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.ReflectionFields;
import com.example.editme.util.render.GLUtil;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

@Module.Info(
   name = "Breadcrums",
   category = Module.Category.RENDER
)
public class Breadcrums extends Module {
   private List cords = new ArrayList();
   private float offset = 0.0F;
   private Setting Blue = this.register(SettingsManager.integerBuilder("Blue").withMinimum(0).withMaximum(255).withValue((int)255));
   private float color = 0.0F;
   private Setting Red = this.register(SettingsManager.integerBuilder("Red").withMinimum(0).withMaximum(255).withValue((int)255));
   private Setting Green = this.register(SettingsManager.integerBuilder("Green").withMinimum(0).withMaximum(255).withValue((int)0));
   @EventHandler
   private final Listener sendListener = new Listener(this::lambda$new$0, new Predicate[0]);

   public void onWorldRender(RenderEvent var1) {
      GL11.glPushMatrix();
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glDisable(2929);
      GL11.glEnable(2848);
      GL11.glBegin(3);
      Iterator var2 = this.cords.iterator();

      Vec3d var3;
      double var4;
      double var6;
      double var8;
      while(var2.hasNext()) {
         var3 = (Vec3d)var2.next();
         var4 = var3.field_72450_a - ReflectionFields.getRenderPosX();
         var6 = var3.field_72448_b - ReflectionFields.getRenderPosY();
         var8 = var3.field_72449_c - ReflectionFields.getRenderPosZ();
         GLUtil.glColor((long)(new Color((Integer)this.Red.getValue(), (Integer)this.Green.getValue(), (Integer)this.Blue.getValue())).getRGB());
         GL11.glVertex3d(var4, var6, var8);
      }

      GL11.glEnd();
      this.color -= this.color + this.offset * 0.1F;
      GL11.glDisable(2848);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GLUtil.enableGL3D(1.5F);
      GL11.glEnable(3042);
      GL11.glDisable(2929);
      var2 = this.cords.iterator();

      while(var2.hasNext()) {
         var3 = (Vec3d)var2.next();
         var4 = var3.field_72450_a - ReflectionFields.getRenderPosX();
         var6 = var3.field_72448_b - ReflectionFields.getRenderPosY();
         var8 = var3.field_72449_c - ReflectionFields.getRenderPosZ();
         GL11.glTranslated(var4, var6, var8);
         GL11.glColor4d(0.0D, 0.0D, 0.0D, 0.5D);
         GL11.glTranslated(-var4, -var6, -var8);
      }

      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GLUtil.disableGL3D();
      GL11.glPopMatrix();
      if (this.cords.size() > 1000) {
         this.cords.remove(0);
      } else if (this.offset < 10.0F) {
         this.offset += 0.1F;
      } else {
         this.offset = 0.0F;
      }

   }

   public static boolean isMoving(Entity var0) {
      return var0.field_70165_t != var0.field_70142_S || var0.field_70163_u != var0.field_70137_T || var0.field_70161_v != var0.field_70136_U || var0 == mc.field_71439_g && (mc.field_71474_y.field_74351_w.func_151468_f() || mc.field_71474_y.field_74368_y.func_151468_f() || mc.field_71474_y.field_74366_z.func_151468_f() || mc.field_71474_y.field_74370_x.func_151468_f() || mc.field_71474_y.field_74314_A.func_151468_f());
   }

   private void lambda$new$0(PacketEvent.Send var1) {
      if (var1.getPacket() instanceof CPacketPlayer && isMoving(mc.field_71439_g)) {
         CPacketPlayer var2 = (CPacketPlayer)var1.getPacket();
         this.cords.add(new Vec3d(var2.func_186997_a(mc.field_71439_g.field_70165_t), var2.func_186996_b(mc.field_71439_g.field_70163_u) < 0.0D ? mc.field_71439_g.field_70163_u : var2.func_186996_b(mc.field_71439_g.field_70163_u), var2.func_187000_c(mc.field_71439_g.field_70161_v)));
      }

   }
}
