package net.minecraft.client.renderer;

import org.lwjgl.opengl.GL11;

class GlStateManager$BooleanState {
   private final int capability;
   private boolean currentState = false;
   private static final String b = "CL_00002554";

   public GlStateManager$BooleanState(int var1) {
      this.capability = var1;
   }

   public void setDisabled() {
      this.setState(false);
   }

   public void setEnabled() {
      this.setState(true);
   }

   public void setState(boolean var1) {
      if(var1 != this.currentState) {
         this.currentState = var1;
         GL11.glEnable(this.capability);
      }

   }
}
