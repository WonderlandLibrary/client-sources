package lunadevs.luna.module.player;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class AutoArmor extends Module{
	
	  private int[] bestArmor;
	  int delay;

	public AutoArmor() {
		super("AutoArmor", 0, Category.PLAYER, true);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
	    if ((mc.thePlayer.capabilities.isCreativeMode) || (
	    	      ((mc.currentScreen instanceof GuiContainer)) && 
	    	      (!(mc.currentScreen instanceof GuiInventory)))) {
	    	      return;
	    	    }
	    	    this.delay += 1;
	    	    this.bestArmor = new int[4];
	    	    for (int i = 0; i < this.bestArmor.length; i++) {
	    	      this.bestArmor[i] = -1;
	    	    }
	    	    for (int i = 0; i < 36; i++)
	    	    {
	    	      ItemStack itemstack = mc.thePlayer.inventory.getStackInSlot(i);
	    	      if ((itemstack != null) && 
	    	        ((itemstack.getItem() instanceof ItemArmor)))
	    	      {
	    	        ItemArmor armor = (ItemArmor)itemstack.getItem();
	    	        if (armor.damageReduceAmount > this.bestArmor[(3 - armor.armorType)]) {
	    	          this.bestArmor[(3 - armor.armorType)] = i;
	    	        }
	    	      }
	    	    }
	    	    for (int i = 0; i < 4; i++)
	    	    {
	    	      ItemStack itemstack = mc.thePlayer.inventory.armorItemInSlot(i);
	    	      ItemArmor currentArmor;
	    	      if ((itemstack != null) && 
	    	        ((itemstack.getItem() instanceof ItemArmor))) {
	    	        currentArmor = (ItemArmor)itemstack.getItem();
	    	      } else {
	    	        currentArmor = null;
	    	      }
	    	      ItemArmor bestArmor;
	    	      try
	    	      {
	    	        bestArmor = 
	    	          (ItemArmor)mc.thePlayer.inventory.getStackInSlot(
	    	          this.bestArmor[i]).getItem();
	    	      }
	    	      catch (Exception e)
	    	      {
	    	        bestArmor = null;
	    	      }
	    	      if ((bestArmor != null) && 
	    	        ((currentArmor == null) || (bestArmor.damageReduceAmount > currentArmor.damageReduceAmount)) && (
	    	        (mc.thePlayer.inventory.getFirstEmptyStack() != -1) || 
	    	        (currentArmor == null))) {
	    	        if (this.delay >= 4)
	    	        {
	    	          mc.playerController.windowClick(0, 8 - i, 0, 1, 
	    	            mc.thePlayer);
	    	          mc.playerController.windowClick(0, 
	    	            this.bestArmor[i] < 9 ? 36 + this.bestArmor[i] : 
	    	            this.bestArmor[i], 0, 1, 
	    	            Minecraft.getMinecraft().thePlayer);
	    	          this.delay = 0;
	    	        }
	    	      }
	    	    }
		super.onUpdate();
	}
	
	@Override
	public String getValue() {
		return "Best";
	}

}
