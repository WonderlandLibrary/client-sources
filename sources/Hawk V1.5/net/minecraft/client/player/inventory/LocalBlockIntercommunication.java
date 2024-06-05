package net.minecraft.client.player.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;

public class LocalBlockIntercommunication implements IInteractionObject {
   private IChatComponent field_175125_b;
   private String field_175126_a;
   private static final String __OBFID = "CL_00002571";

   public String getName() {
      return this.field_175125_b.getUnformattedText();
   }

   public boolean hasCustomName() {
      return true;
   }

   public LocalBlockIntercommunication(String var1, IChatComponent var2) {
      this.field_175126_a = var1;
      this.field_175125_b = var2;
   }

   public IChatComponent getDisplayName() {
      return this.field_175125_b;
   }

   public String getGuiID() {
      return this.field_175126_a;
   }

   public Container createContainer(InventoryPlayer var1, EntityPlayer var2) {
      throw new UnsupportedOperationException();
   }
}
