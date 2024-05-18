package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.TimerUtil;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class SkipPhase extends Module
{
	private boolean noClip;
	public SkipPhase()
	{
		super("skipPhase", "Old", "Phases through blocks.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
	}
	
	TimerUtil time = new TimerUtil();
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if ((this.time.hasReached(75L)) && (Minecraft.player.isCollidedHorizontally) && ((!isInsideBlock()) || (Minecraft.player.isSneaking())) && (Minecraft.player.onGround))
		      {
		        float dir2 = Minecraft.player.rotationYaw;
		        if (Minecraft.player.moveForward < 0.0F) {
		          dir2 += 180.0F;
		        }
		        if (Minecraft.player.moveStrafing > 0.0F) {
		          dir2 -= 90.0F * (Minecraft.player.moveForward > 0.0F ? 0.5F : Minecraft.player.moveForward < 0.0F ? -0.5F : 1.0F);
		        }
		        if (Minecraft.player.moveStrafing < 0.0F) {
		          dir2 += 90.0F * (Minecraft.player.moveForward > 0.0F ? 0.5F : Minecraft.player.moveForward < 0.0F ? -0.5F : 1.0F);
		        }
		        double hOff3 = 0.25D;
		        float xD3 = (float)((float)Math.cos((dir2 + 90.0F) * 3.141592653589793D / 180.0D) * 0.25D);
		        float zD3 = (float)((float)Math.sin((dir2 + 90.0F) * 3.141592653589793D / 180.0D) * 0.25D);
		        double hOff4 = 0.3D;
		        float xD4 = (float)((float)Math.cos((dir2 + 90.0F) * 3.141592653589793D / 180.0D) * 0.3D);
		        float zD4 = (float)((float)Math.sin((dir2 + 90.0F) * 3.141592653589793D / 180.0D) * 0.3D);
		        for (int j = 0; j < 4; j++)
		        {
		        	Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 0.04D, Minecraft.player.posZ, true));
		          Minecraft.getMinecraft().player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX + xD4 * j, Minecraft.player.posY, Minecraft.player.posZ + zD4 * j, true));
		        }
		        Minecraft.player.setPositionAndUpdate(Minecraft.player.posX + xD3, Minecraft.player.posY, Minecraft.player.posZ + zD3);
		      }
		    }
		  }
		  
		  private boolean isInsideBlock()
		  {
		    for (int x = MathHelper.floor_double(Minecraft.player.boundingBox.minX); x < MathHelper.floor_double(Minecraft.player.boundingBox.maxX) + 1; x++) {
		      for (int y = MathHelper.floor_double(Minecraft.player.boundingBox.minY); y < MathHelper.floor_double(Minecraft.player.boundingBox.maxY) + 1; y++) {
		        for (int z = MathHelper.floor_double(Minecraft.player.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.player.boundingBox.maxZ) + 1; z++)
		        {
		          Minecraft.getMinecraft();Block localBlock = Minecraft.world.getBlockState(new BlockPos(x, y, z)).getBlock();
		        }
		      }
		    }
		    return false;
		  }
		}

