package me.sopt.pathstuff;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class BlockUtils2 {
	  public static IBlockState getState(final BlockPos pos) {
	        return Minecraft.theMinecraft.theWorld.getBlockState(pos);
	    }
	    
	    public static Block getBlock(final BlockPos pos) {
	        return getState(pos).getBlock();
	    }
	    
	    public static Material getMaterial(final BlockPos pos) {
	        return getState(pos).getBlock().getMaterial();
	    }
	    
	    public static boolean canBeClicked(final BlockPos pos) {
	        return getBlock(pos).canCollideCheck(getState(pos), false);
	    }

	    public static void faceBlockClientHorizontally(final BlockPos blockPos) {
	        final double diffX = blockPos.getX() + 0.5 - Minecraft.theMinecraft.thePlayer.posX;
	        final double diffZ = blockPos.getZ() + 0.5 - Minecraft.theMinecraft.thePlayer.posZ;
	        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
	        Minecraft.theMinecraft.thePlayer.rotationYaw += MathHelper.wrapAngleTo180_float(yaw - Minecraft.theMinecraft.thePlayer.rotationYaw);
	    }
}
