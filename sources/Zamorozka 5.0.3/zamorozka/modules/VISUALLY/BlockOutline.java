package zamorozka.modules.VISUALLY;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.BlockUtilis;
import zamorozka.ui.RenderUtils2;
import zamorozka.ui.RenderingUtils;

public class BlockOutline extends Module {

	public BlockOutline() {
		super("BlockOutline", 0, Category.VISUALLY);
	}

	public void onRender() {
		try {
			if (this.mc.objectMouseOver.getBlockPos() != null && this.mc.world.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock() != null
					&& mc.world.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock() != Block.getBlockById(9) && mc.world.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock() != Block.getBlockById(11)
					&& this.mc.world.getBlockState(this.mc.objectMouseOver.getBlockPos()).getBlock() != Block.getBlockById(0))
				RenderUtils2.blockEspBox(mc.objectMouseOver.getBlockPos(), 255, 255, 255);
		} catch (Exception exception) {
		}
		super.onRender();
	}

	public void onDisable() {
		super.onDisable();
	}

	private int blockY() {
		BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
		if (blockpos != null)
			return blockpos.getY();
		return -1;
	}

	private int blockX() {
		BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
		if (blockpos != null)
			return blockpos.getX();
		return -1;
	}

	private int blockZ() {
		BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
		if (blockpos != null)
			return blockpos.getZ();
		return -1;
	}
}
