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
   private String guiID;
   private Map field_174895_b = Maps.newHashMap();

   public ContainerLocalMenu(String var1, IChatComponent var2, int var3) {
      super(var2, var3);
      this.guiID = var1;
   }

   public int getField(int var1) {
      return this.field_174895_b.containsKey(Integer.valueOf(var1))?((Integer)this.field_174895_b.get(Integer.valueOf(var1))).intValue():0;
   }

   public void setField(int var1, int var2) {
      this.field_174895_b.put(Integer.valueOf(var1), Integer.valueOf(var2));
   }

   public int getFieldCount() {
      return this.field_174895_b.size();
   }

   public boolean isLocked() {
      return false;
   }

   public void setLockCode(LockCode var1) {
   }

   public LockCode getLockCode() {
      return LockCode.EMPTY_CODE;
   }

   public String getGuiID() {
      return this.guiID;
   }

   public Container createContainer(InventoryPlayer var1, EntityPlayer var2) {
      throw new UnsupportedOperationException();
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
