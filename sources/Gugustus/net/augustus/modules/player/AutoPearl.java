package net.augustus.modules.player;

import java.awt.Color;
import java.util.ArrayList;

import net.augustus.Augustus;
import net.augustus.events.EventPreMotion;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.utils.ChatUtil;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.RotationUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.BlockPos;

public class AutoPearl extends Module {

	public RotationUtil rotationUtil = new RotationUtil();
	public ArrayList<BlockPos> previousBlocks = new ArrayList<BlockPos>();
	
	public AutoPearl() {
		super("AutoPearl", Color.gray, Categorys.PLAYER);
	}

	@EventTarget
	public void onEventPreMotion(EventPreMotion e) {
		if (this.isToggled()) {
			BlockPos bpos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
			if(mc.thePlayer.onGround) {
				if(MoveUtil.isMoving())
					previousBlocks.add(bpos);
			}else {
				previousBlocks.clear();
			}
			if(!mc.thePlayer.onGround) {
				boolean hasPearl = false;
				for (int i = 0; i < mc.thePlayer.inventory.getHotbarSize(); i++) {
					if (!hasPearl)
						mc.thePlayer.inventory.currentItem = i;
					if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemEnderPearl) {
						hasPearl = true;
						EntityEgg placeHolder = new EntityEgg(mc.theWorld);
						BlockPos blockPosToThrow = previousBlocks.get(previousBlocks.size() > 4 ? previousBlocks.size() - 2 : 1);
						placeHolder.posX = blockPosToThrow.getX();
						placeHolder.posY = blockPosToThrow.getY();
						placeHolder.posZ = blockPosToThrow.getZ();
						float[] rotations = rotationUtil.positionRotation(placeHolder.posX, placeHolder.posY, placeHolder.posZ, Augustus.getInstance().getYawPitchHelper().realYaw, Augustus.getInstance().getYawPitchHelper().realPitch, false);
						mc.thePlayer.rotationYaw = rotations[0];
						mc.thePlayer.rotationPitch = rotations[1];
						mc.thePlayer.rotationYawHead = rotations[0];
						mc.thePlayer.rotationPitchHead = rotations[1];
						ChatUtil.sendChat(rotations[0] + "Yaw");
					}
				}
			}
			ChatUtil.sendChat(previousBlocks.size() + "");
		}

	}
}
