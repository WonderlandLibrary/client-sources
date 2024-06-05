package net.minecraft.client.player.inventory;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class ContainerLocalMenu extends InventoryBasic implements ILockableContainer {
   private static final String __OBFID = "CL_00002570";
   private String field_174896_a;
   private Map field_174895_b = Maps.newHashMap();

   public String getGuiID() {
      return this.field_174896_a;
   }

   public LockCode getLockCode() {
      return LockCode.EMPTY_CODE;
   }

   public int getField(int var1) {
      return this.field_174895_b.containsKey(var1) ? (Integer)this.field_174895_b.get(var1) : 0;
   }

   public boolean isLocked() {
      return false;
   }

   public ContainerLocalMenu(String var1, IChatComponent var2, int var3) {
      super(var2, var3);
      this.field_174896_a = var1;
   }

   public Container createContainer(InventoryPlayer var1, EntityPlayer var2) {
      throw new UnsupportedOperationException();
   }

   public void setLockCode(LockCode var1) {
   }

   public int getFieldCount() {
      return this.field_174895_b.size();
   }

   public void setField(int var1, int var2) {
      this.field_174895_b.put(var1, var2);
   }
}
