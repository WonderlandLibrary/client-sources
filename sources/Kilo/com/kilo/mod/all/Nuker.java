package com.kilo.mod.all;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.toolbar.dropdown.SettingsRel;
import com.kilo.mod.util.NukerHandler;
import com.kilo.util.Util;

public class Nuker extends Module {
	
	private boolean breaking = false;
	private List<Block> selected = new CopyOnWriteArrayList<Block>();
	private Map<BlockPos, EnumFacing> blocks = new HashMap<BlockPos, EnumFacing>();
	
	public Nuker(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Range", "Maximum nuker range", Interactable.TYPE.SLIDER, 1f, new float[] {1, 7}, true);
		addOption("Delay", "Delay between breaking blocks", Interactable.TYPE.SLIDER, 0.1f, new float[] {0, 1}, true);
		addOption("Selective", "Nuker the selected blocks", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Edit", "Toggle blocks to be nuked", Interactable.TYPE.SETTINGS, SettingsRel.NUKER, null, false);
	}
	
	public void onPlayerPostUpdate() {
		if (!breaking) {
			breaking = true;
			new Thread() {
				@Override
				public void run() {
					for(int i = -7; i < 7; i++) {
						for(int j = -7; j < 7; j++) {
							for(int k = -7; k < 7; k++) {
								int a = (int)mc.thePlayer.posX+i;
								int b = (int)mc.thePlayer.posY+j;
								int c = (int)mc.thePlayer.posZ+k;
								
								BlockPos bp = new BlockPos(a, b, c);
								if (mc.theWorld.getBlockState(bp) == null || mc.theWorld.getBlockState(bp).getBlock().getMaterial() == Material.air) {
									continue;
								}
								
								float[] lookAt = Util.getRotationToBlockPos(bp);
								
						        Vec3 var4 = getPosVector();
						        Vec3 var5 = getLookVector(lookAt[1], lookAt[0]);
						        
						        float p_174822_1_ = Util.makeFloat(getOptionValue("range"));
						        Vec3 var6 = var4.addVector(var5.xCoord * p_174822_1_, var5.yCoord * p_174822_1_, var5.zCoord * p_174822_1_);
						        MovingObjectPosition mop = mc.thePlayer.worldObj.rayTraceBlocks(var4, var6, false, false, true);
								
								if (mop != null && mop.getBlockPos() != null) {
									if (Util.makeBoolean(getOptionValue("selective"))) {
										if (NukerHandler.canNuke(mc.theWorld.getBlockState(mop.getBlockPos()).getBlock(), mop.getBlockPos())) {
											blocks.put(mop.getBlockPos(), mop.sideHit);
										}
									} else {
										blocks.put(mop.getBlockPos(), mop.sideHit);
									}
								}
							}
						}
					}
					
					for(BlockPos bp : blocks.keySet()) {
						if (Minecraft.getMinecraft().thePlayer != null) {
							Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, bp, blocks.get(bp)));
							Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, bp, blocks.get(bp)));
							try {
								sleep((long)(Util.makeFloat(getOptionValue("delay"))*1000L));
							} catch (Exception e) {}
						}
					}
					
					blocks.clear();
					breaking = false;
				}
			}.start();
		}
	}
	
    private Vec3 getLookVector(float p_174806_1_, float p_174806_2_) {
        float var3 = MathHelper.cos(-p_174806_2_ * 0.017453292F - (float)Math.PI);
        float var4 = MathHelper.sin(-p_174806_2_ * 0.017453292F - (float)Math.PI);
        float var5 = -MathHelper.cos(-p_174806_1_ * 0.017453292F);
        float var6 = MathHelper.sin(-p_174806_1_ * 0.017453292F);
        return new Vec3((double)(var4 * var5), (double)var6, (double)(var3 * var5));
    }
    
    private Vec3 getPosVector() {
        return new Vec3(mc.thePlayer.posX, mc.thePlayer.posY+mc.thePlayer.height, mc.thePlayer.posZ);
    }
}
