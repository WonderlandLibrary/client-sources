package com.example.editme.gui.components.settings;

import com.example.editme.EditmeMod;
import com.example.editme.gui.components.Component;
import com.example.editme.gui.frames.Frame;
import com.example.editme.gui.frames.HudFrame;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.render.RenderUtil;
import java.awt.Color;
import java.util.Iterator;

public class HudToggleComponent extends Component {
   private final Module module;

   public void onMouseClicked(int var1, int var2, int var3) {
      super.onMouseClicked(var1, var2, var3);
      boolean var4 = mouseWithinBounds(var1, var2, (double)this.getFinishedX(), (double)this.getFinishedY(), (double)this.getWidth(), (double)this.getHeight());
      if (var4) {
         Module var5;
         switch(var3) {
         case 0:
            if (this.getModule() instanceof Module) {
               var5 = this.getModule();
               var5.toggle();
            }
            break;
         case 1:
            if (this.getModule() instanceof Module) {
               var5 = this.getModule();
               Iterator var6 = var5.settingList.iterator();

               while(var6.hasNext()) {
                  Setting var7 = (Setting)var6.next();
                  if (var7.getName().equals("x")) {
                     var7.setValue(4);
                  }

                  if (var7.getName().equals("y")) {
                     var7.setValue(4);
                  }
               }

               var6 = EditmeMod.getInstance().getHudEditor().getFrames().iterator();

               while(var6.hasNext()) {
                  Frame var8 = (Frame)var6.next();
                  if (var8 instanceof HudFrame && var8.getName().equalsIgnoreCase(this.module.getName())) {
                     ((HudFrame)var8).reloadPos();
                  }
               }
            }
         }
      }

   }

   public void onMouseReleased(int var1, int var2, int var3) {
      super.onMouseReleased(var1, var2, var3);
   }

   public void onMove(float var1, float var2) {
      super.onMove(var1, var2);
   }

   public void drawComponent(int var1, int var2, float var3) {
      super.drawComponent(var1, var2, var3);
      RenderUtil.drawRect(this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight(), (new Color(50, 50, 50, 200)).getRGB());
      if (this.getModule().isEnabled()) {
         RenderUtil.drawRect(this.getFinishedX() + 2.0F, this.getFinishedY() + 1.0F, this.getWidth() - 4.0F, this.getHeight() - 2.0F, (new Color(0, 102, 204, 204)).getRGB());
      }

      fontRenderer.drawStringWithShadow(this.getName(), (double)(this.getFinishedX() + 4.0F), (double)(this.getFinishedY() + this.getHeight() / 2.0F - (float)(fontRenderer.getStringHeight(this.getName()) / 2)), this.getModule().isEnabled() ? -1 : -5592406);
   }

   public void onKeyTyped(char var1, int var2) {
      super.onKeyTyped(var1, var2);
   }

   public Module getModule() {
      return this.module;
   }

   public HudToggleComponent(Module var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super(var1.getName(), var2, var3, var4, var5, var6, var7);
      this.module = var1;
   }
}
