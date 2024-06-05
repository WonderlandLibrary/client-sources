package zamorozka.modules.WORLD;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;

public class AutoTool extends Module {

	public AutoTool() {
		super("AutoTool", Keyboard.KEY_NONE, Category.WORLD);
	}
	
	public void onUpdate()
	  {
	    if (getState()) {
	    	if (this.mc.gameSettings.keyBindAttack.pressed) {
	        if (this.mc.objectMouseOver == null)
	            return;
	        BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
	        if (blockPos == null)
	            return;
	        updateTool(blockPos);
	    }
	    }
	  }

	    public void updateTool(BlockPos paramBlockPos) {
	        Block block = this.mc.world.getBlockState(paramBlockPos).getBlock();
	        float f = 1.0F;
	        byte b = -1;
	        for (byte b1 = 0; b1 < 9; b1++) {
	            ItemStack itemStack = this.mc.player.inventory.mainInventory.get(b1);
	            ItemStack current = this.mc.player.inventory.getCurrentItem();
	            if (itemStack != null && (itemStack.getStrVsBlock(block.getDefaultState())) > f && !(current.getStrVsBlock(block.getDefaultState()) > f)) {
	                f = itemStack.getStrVsBlock(block.getDefaultState());
	                b = b1;
	            }
	        }
	        if (b != -1) {
	            this.mc.player.inventory.currentItem = b;
	        }
	    }
	  }