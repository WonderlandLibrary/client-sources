package vestige.event.impl;

import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import vestige.event.Event;

public final class RenderItemEvent extends Event {
   private final EnumAction enumAction;
   private final boolean useItem;
   private final float animationProgression;
   private final float partialTicks;
   private final float swingProgress;
   private final ItemStack itemToRender;

   public RenderItemEvent(EnumAction enumAction, boolean useItem, float animationProgression, float partialTicks, float swingProgress, ItemStack itemToRender) {
      this.enumAction = enumAction;
      this.useItem = useItem;
      this.animationProgression = animationProgression;
      this.partialTicks = partialTicks;
      this.swingProgress = swingProgress;
      this.itemToRender = itemToRender;
   }

   public EnumAction getEnumAction() {
      return this.enumAction;
   }

   public boolean isUseItem() {
      return this.useItem;
   }

   public float getAnimationProgression() {
      return this.animationProgression;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public float getSwingProgress() {
      return this.swingProgress;
   }

   public ItemStack getItemToRender() {
      return this.itemToRender;
   }
}
