package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GuiLockIconButton extends GuiButton {
   private boolean field_175231_o = false;
   private static final String __OBFID = "CL_00001952";

   public boolean func_175230_c() {
      return this.field_175231_o;
   }

   public GuiLockIconButton(int var1, int var2, int var3) {
      super(var1, var2, var3, 20, 20, "");
   }

   public void func_175229_b(boolean var1) {
      this.field_175231_o = var1;
   }

   public void drawButton(Minecraft var1, int var2, int var3) {
      if (this.visible) {
         var1.getTextureManager().bindTexture(GuiButton.buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         boolean var4 = var2 >= this.xPosition && var3 >= this.yPosition && var2 < this.xPosition + this.width && var3 < this.yPosition + this.height;
         GuiLockIconButton.Icon var5;
         if (this.field_175231_o) {
            if (!this.enabled) {
               var5 = GuiLockIconButton.Icon.LOCKED_DISABLED;
            } else if (var4) {
               var5 = GuiLockIconButton.Icon.LOCKED_HOVER;
            } else {
               var5 = GuiLockIconButton.Icon.LOCKED;
            }
         } else if (!this.enabled) {
            var5 = GuiLockIconButton.Icon.UNLOCKED_DISABLED;
         } else if (var4) {
            var5 = GuiLockIconButton.Icon.UNLOCKED_HOVER;
         } else {
            var5 = GuiLockIconButton.Icon.UNLOCKED;
         }

         this.drawTexturedModalRect(this.xPosition, this.yPosition, var5.func_178910_a(), var5.func_178912_b(), this.width, this.height);
      }

   }

   static enum Icon {
      UNLOCKED_DISABLED("UNLOCKED_DISABLED", 5, 20, 186),
      LOCKED("LOCKED", 0, 0, 146);

      private static final GuiLockIconButton.Icon[] $VALUES = new GuiLockIconButton.Icon[]{LOCKED, LOCKED_HOVER, LOCKED_DISABLED, UNLOCKED, UNLOCKED_HOVER, UNLOCKED_DISABLED};
      private static final GuiLockIconButton.Icon[] ENUM$VALUES = new GuiLockIconButton.Icon[]{LOCKED, LOCKED_HOVER, LOCKED_DISABLED, UNLOCKED, UNLOCKED_HOVER, UNLOCKED_DISABLED};
      LOCKED_HOVER("LOCKED_HOVER", 1, 0, 166),
      UNLOCKED("UNLOCKED", 3, 20, 146);

      private final int field_178920_h;
      LOCKED_DISABLED("LOCKED_DISABLED", 2, 0, 186);

      private final int field_178914_g;
      UNLOCKED_HOVER("UNLOCKED_HOVER", 4, 20, 166);

      private static final String __OBFID = "CL_00001951";

      public int func_178912_b() {
         return this.field_178920_h;
      }

      public int func_178910_a() {
         return this.field_178914_g;
      }

      private Icon(String var3, int var4, int var5, int var6) {
         this.field_178914_g = var5;
         this.field_178920_h = var6;
      }
   }
}
