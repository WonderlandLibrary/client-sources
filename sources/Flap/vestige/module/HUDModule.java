package vestige.module;

import net.minecraft.client.gui.GuiChat;
import vestige.event.Listener;
import vestige.event.impl.RenderEvent;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.DoubleSetting;

public abstract class HUDModule extends Module {
   public final DoubleSetting posX;
   public final DoubleSetting posY;
   protected int width;
   protected int height;
   private boolean holdingMouse;
   protected AlignType alignType;

   public HUDModule(String name, Category category, double defaultX, double defaultY, int width, int height, AlignType alignType) {
      super(name, category);
      this.posX = new DoubleSetting("Pos X", () -> {
         return false;
      }, defaultX, 0.0D, 1000.0D, 0.5D);
      this.posY = new DoubleSetting("Pos Y", () -> {
         return false;
      }, defaultY, 0.0D, 1000.0D, 0.5D);
      this.width = width;
      this.height = height;
      this.alignType = alignType;
      this.listenType = EventListenType.MANUAL;
      this.startListening();
      this.addSettings(new AbstractSetting[]{this.posX, this.posY});
   }

   @Listener
   public final void onRender(RenderEvent event) {
      boolean inChat = mc.currentScreen instanceof GuiChat;
      if (this.isEnabled() || inChat) {
         this.renderModule(inChat);
      }

   }

   protected abstract void renderModule(boolean var1);

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public boolean isHoldingMouse() {
      return this.holdingMouse;
   }

   public void setHoldingMouse(boolean holdingMouse) {
      this.holdingMouse = holdingMouse;
   }

   public AlignType getAlignType() {
      return this.alignType;
   }
}
