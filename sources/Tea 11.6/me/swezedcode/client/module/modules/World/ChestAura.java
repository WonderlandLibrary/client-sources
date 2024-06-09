package me.swezedcode.client.module.modules.World;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.Wrapper;
import me.swezedcode.client.utils.events.EventPostMotionUpdates;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.timer.TimerUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class ChestAura extends Module {

	public ChestAura() {
		super("ChestAura", Keyboard.KEY_NONE, 0xFFFFFFFF, ModCategory.World);
		this.timer = new TimerUtils();
		this.sB = false;
	}

	public static final List<BlockPos> oB;
	private final TimerUtils timer;
	private boolean sB;
	BlockPos gP;

	static {
		oB = new CopyOnWriteArrayList<BlockPos>();
	}

	@EventListener
	public void a(final EventPreMotionUpdates e) {
		if (this.gP != null && !(Minecraft.currentScreen instanceof GuiContainer) && !oB.contains(this.gP) && this.sB) {
			Minecraft.thePlayer.swingItem();
			if (Minecraft.playerController.blockHitDelay > 1) {
				Minecraft.playerController.blockHitDelay = 1;
			}
			final EnumFacing direction = this.d(this.gP);
			if (direction != null && this.timer.hasReached(400L)) {
				Minecraft.playerController.func_178890_a(Minecraft.thePlayer, Wrapper.mc.theWorld,
						Minecraft.thePlayer.getCurrentEquippedItem(), this.gP, direction,
						new Vec3(this.gP.getX(), this.gP.getY(), this.gP.getZ()));
				oB.add(this.gP);
				timer.rt();
			}
		}
	}

	@EventListener
	public void b(final EventPostMotionUpdates e) {
		if (Minecraft.currentScreen instanceof GuiContainer) {
			timer.rt();
		}
		int r;
		for (int y = r = 3; y >= -r; --y) {
			for (int x = -r; x <= r; ++x) {
				for (int z = -r; z <= r; ++z) {
					final BlockPos p = new BlockPos(Minecraft.thePlayer.posX - 0.5 + x,
							Minecraft.thePlayer.posY - 0.5 + y, Minecraft.thePlayer.posZ - 0.5 + z);
					Minecraft.getMinecraft();
					final Block b = Wrapper.mc.theWorld.getBlockState(p).getBlock();
					if (this.d(p) != null && p != null && !(Minecraft.currentScreen instanceof GuiContainer)
							&& Minecraft.thePlayer
									.getDistance(Minecraft.thePlayer.posX + x, Minecraft.thePlayer.posY + y,
											Minecraft.thePlayer.posZ + z) < Minecraft.playerController
													.getBlockReachDistance() - 0.5
							&& (b instanceof BlockChest || b instanceof BlockEnderChest)) {
						this.sB = true;
						final float[] rotation = c(p.getX(), p.getY(), p.getZ());
						this.gP = p;
						return;
					}
				}
			}
		}
	}

	public static float[] c(final double x, final double y, final double z) {
		double var41 = x - Minecraft.thePlayer.posX + 0.5;
		final double var51 = z - Minecraft.thePlayer.posZ + 0.5;
		final double var61 = y - (Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight() - 1.0);
		var41 = MathHelper.sqrt_double(var41 * var41 + var51 * var51);
		final float var71 = (float) (Math.atan2(var51, var41) * 180.0 / 3.141592653589793) - 90.0f;
		return new float[] { var71, (float) (-(Math.atan2(var61, var41) * 180.0 / 3.141592653589793)) };
	}

	private EnumFacing d(final BlockPos pos) {
		EnumFacing d = null;
		if (!Wrapper.mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isSolidFullCube()) {
			d = EnumFacing.UP;
		} else if (!Wrapper.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock().isSolidFullCube()) {
			d = EnumFacing.DOWN;
		} else if (!Wrapper.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().isSolidFullCube()) {
			d = EnumFacing.EAST;
		} else if (!Wrapper.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().isSolidFullCube()) {
			d = EnumFacing.WEST;
		} else if (!Wrapper.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
			d = EnumFacing.SOUTH;
		} else if (!Wrapper.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isSolidFullCube()) {
			d = EnumFacing.NORTH;
		}
		return d;
	}
}