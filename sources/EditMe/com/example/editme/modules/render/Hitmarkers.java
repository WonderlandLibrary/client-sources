package com.example.editme.modules.render;

import com.example.editme.modules.Module;
import com.example.editme.util.render.CrosshairRenderer;
import java.awt.Color;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.opengl.GL11;

@Module.Info(
   name = "HitMarkers",
   category = Module.Category.RENDER
)
public class Hitmarkers extends Module {
   private int hitting = 0;
   @EventHandler
   private Listener mouseEvent = new Listener(this::lambda$new$0, new Predicate[0]);

   private void draw(int var1, int var2) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)var1, (float)var2, 0.0F);
      GL11.glRotatef(45.0F, (float)var1, (float)var2, 8000.0F);
      GL11.glTranslatef((float)(-var1), (float)(-var2), 0.0F);
      this.renderCrosshair(var1, var2, new Color(255, 255, 255));
      GL11.glPopMatrix();
   }

   private void lambda$new$0(MouseEvent var1) {
      if (mc.field_71439_g != null) {
         if (var1.getButton() == 0 & mc.field_71476_x.field_72308_g != null) {
            this.hitting = 5;
         }

      }
   }

   public void onRender() {
      if (this.hitting > 0) {
         ScaledResolution var1 = new ScaledResolution(mc);
         this.draw(var1.func_78326_a() / 2, var1.func_78328_b() / 2);
         --this.hitting;
      }

   }

   private void renderCrosshair(int var1, int var2, Color var3) {
      float var4 = 1.0F;
      CrosshairRenderer.drawFilledRectangle((float)var1 - var4, (float)(var2 - 5 - 10), (float)var1 + var4, (float)(var2 - 5), var3, true);
      CrosshairRenderer.drawFilledRectangle((float)var1 - var4, (float)(var2 + 5), (float)var1 + var4, (float)(var2 + 5 + 10), var3, true);
      CrosshairRenderer.drawFilledRectangle((float)(var1 - 5 - 10), (float)var2 - var4, (float)(var1 - 5), (float)var2 + var4, var3, true);
      CrosshairRenderer.drawFilledRectangle((float)(var1 + 5), (float)var2 - var4, (float)(var1 + 5 + 10), (float)var2 + var4, var3, true);
   }
}
