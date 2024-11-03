package net.silentclient.client.emotes.emoticons;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class Icon {
   public static final Icon DEFAULT = new Icon(new ResourceLocation("silentclient/emotes/icons/1.png"), 53, 85);
   public ResourceLocation icon;
   public int width;
   public int height;

   public Icon(ResourceLocation aj, int i, int j) {
      this.set(aj, i, j);
   }

   public void set(ResourceLocation aj, int i, int j) {
      this.icon = aj;
      this.width = i;
      this.height = j;
   }

   public void render(int i, int j) {
      if (this.isIconPresent()) {
         this.bindTexture();
         Gui.drawModalRectWithCustomSizedTexture(i, j, 0.0F, 0.0F, this.width, this.height, (float) this.width, (float) this.height);
      }
   }

   public void render(int i, int j, float f) {
      if (this.isIconPresent()) {
         int k = (int) ((float) this.width * f);
         int l = (int) ((float) this.height * f);
         this.bindTexture();
         Gui.drawModalRectWithCustomSizedTexture(i - k / 2, j, 0.0F, 0.0F, k, l, (float) k, (float) l);
      }
   }

   protected boolean isIconPresent() {
      return this.icon != null;
   }

   protected void bindTexture() {
   }
}
