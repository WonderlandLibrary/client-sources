package me.swezedcode.client.utils.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import me.swezedcode.client.utils.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class PlayerUtils {

	public static AxisAlignedBB boundingBox;

	public static Minecraft mc = Minecraft.getMinecraft();

	public static boolean isMoving(){
		return Wrapper.getPlayer().moveForward != 0 || Wrapper.getPlayer().moveStrafing != 0;
	}
	
	public static boolean isInLiquid() {
		if (Wrapper.getPlayer() == null) {
			return false;
		}
		boolean inLiquid = false;
		int y = (int) boundingBox.minY;
		for (int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper.floor_double(boundingBox.maxX)
				+ 1; x++) {
			for (int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper.floor_double(boundingBox.maxZ)
					+ 1; z++) {
				Block block = Wrapper.getWorld().getBlockState(new BlockPos(x, y, z)).getBlock();
				if ((block != null) && (!(block instanceof BlockAir))) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}
					inLiquid = true;
				}
			}
		}
		return inLiquid;
	}
	
	public static void checkIP() {
		URL whatismyip = null;
		try {
			whatismyip = new URL("http://checkip.amazonaws.com/");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
			                whatismyip.openStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Wrapper.ip = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isOnLiquid() {
		if (Wrapper.getPlayer() == null) {
			return false;
		}
		boolean onLiquid = false;
		int y = (int) boundingBox.offset(0.0D, -0.01D, 0.0D).minY;
		for (int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper.floor_double(boundingBox.maxX)
				+ 1; x++) {
			for (int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper.floor_double(boundingBox.maxZ)
					+ 1; z++) {
				Block block = Wrapper.getWorld().getBlockState(new BlockPos(x, y, z)).getBlock();
				if ((block != null) && (!(block instanceof BlockAir))) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}
					onLiquid = true;
				}
			}
		}
		return onLiquid;
	}

	public static boolean isInsideBlock() {
		for (int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper.floor_double(boundingBox.maxX)
				+ 1; x++) {
			for (int y = MathHelper.floor_double(boundingBox.minY); y < MathHelper.floor_double(boundingBox.maxY)
					+ 1; y++) {
				for (int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper.floor_double(boundingBox.maxZ)
						+ 1; z++) {
					Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
					if ((block != null) && (!(block instanceof BlockAir))) {
						AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z),
								mc.theWorld.getBlockState(new BlockPos(x, y, z)));
						if ((boundingBox != null) && (boundingBox.intersectsWith(boundingBox)))
							return true;
					}
				}
			}
		}
		return false;
	}

}
