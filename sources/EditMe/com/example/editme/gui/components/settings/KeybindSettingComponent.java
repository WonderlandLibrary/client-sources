package com.example.editme.gui.components.settings;

import com.example.editme.gui.components.Component;
import com.example.editme.modules.Module;
import com.example.editme.util.client.Bind;
import com.example.editme.util.render.RenderUtil;
import java.awt.Color;

public class KeybindSettingComponent extends Component {
   private boolean binding;
   private final Module module;

   public void setBinding(boolean var1) {
      this.binding = var1;
   }

   public void onMove(float var1, float var2) {
      super.onMove(var1, var2);
   }

   public void onMouseClicked(int var1, int var2, int var3) {
      super.onMouseClicked(var1, var2, var3);
      boolean var4 = mouseWithinBounds(var1, var2, (double)(this.getFinishedX() + 5.0F), (double)(this.getFinishedY() + 1.0F), (double)(this.getWidth() - 10.0F), (double)(this.getHeight() - 2.0F));
      if (var4 && var3 == 0) {
         this.setBinding(!this.isBinding());
      }

   }

   public void onKeyTyped(char var1, int var2) {
      super.onKeyTyped(var1, var2);
      if (this.isBinding()) {
         if (var2 == 1) {
            return;
         }

         this.getModule().setBind(new Bind(false, false, false, var2));
         this.setBinding(false);
      }

   }

   public boolean isBinding() {
      return this.binding;
   }

   public void onMouseReleased(int var1, int var2, int var3) {
      super.onMouseReleased(var1, var2, var3);
   }

   public KeybindSettingComponent(Module var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super(var1.getName(), var2, var3, var4, var5, var6, var7);
      this.module = var1;
   }

   public Module getModule() {
      return this.module;
   }

   public void initialize() {
   }

   public void drawComponent(int var1, int var2, float var3) {
      super.drawComponent(var1, var2, var3);
      RenderUtil.drawRect(this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight(), (new Color(50, 50, 50, 200)).getRGB());
      fontRenderer.drawStringWithShadow(this.isBinding() ? "Press new key..." : String.valueOf((new StringBuilder()).append("Bind: ").append(this.getModule().getBind().toString())), (double)(this.getFinishedX() + 5.0F), (double)(this.getFinishedY() + 0.5F + this.getHeight() / 2.0F - (float)(fontRenderer.getStringHeight(this.getName()) / 2)), -1);
   }
}
