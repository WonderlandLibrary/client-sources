package zamorozka.modules.WORLD;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.util.EnumFacing;
import zamorozka.ui.BlockUtils;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.ui.BlockUtils;
import zamorozka.ui.EntityUtils1;

public class SafeWalk extends Module
{
	  public SafeWalk()
	  {
	    super("SafeWalk", 0, Category.WORLD);
	  }
	 public void onUpdate(){

	 }
	  }
	