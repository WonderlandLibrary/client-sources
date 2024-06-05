package win.sightclient.module.render;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class XRay extends Module {

	public static ArrayList<Block> blocks;
	public XRay() {
		super("XRay", Category.RENDER);
		blocks = new ArrayList<Block>();
		blocks.add(Blocks.diamond_ore);
		blocks.add(Blocks.iron_ore);
		blocks.add(Blocks.coal_ore);
		blocks.add(Blocks.emerald_ore);
	}

}
