package xyz.vergoclient.modules.impl.player;

import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventRender3D;
import xyz.vergoclient.event.impl.EventUpdate;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.modules.impl.combat.KillAura;
import xyz.vergoclient.util.main.RenderUtils;
import xyz.vergoclient.util.main.RotationUtils;
import net.minecraft.block.BlockBed;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BedBreaker extends Module implements OnEventInterface {

	public BedBreaker() {
		super("BedBreaker", Category.PLAYER);
	}

	@Override
	public void onEvent(Event e) {
		
		
		if (e instanceof EventRender3D && e.isPre()) {
			
			BedInfo info = findBed();
			
			BlockPos bed = info.pos;
			EnumFacing bedFace = info.face;
			
			if (bed == null || bedFace == null) {
				return;
			}
			
			for (int i = 0; i < 5; i++) {
				
				RenderUtils.drawLine(bed.getX(), bed.getY(), bed.getZ(), bed.getX() + 1, bed.getY(), bed.getZ());
				RenderUtils.drawLine(bed.getX(), bed.getY() + 0.5, bed.getZ(), bed.getX() + 1, bed.getY() + 0.5, bed.getZ());
				RenderUtils.drawLine(bed.getX(), bed.getY(), bed.getZ(), bed.getX(), bed.getY(), bed.getZ() + 1);
				RenderUtils.drawLine(bed.getX(), bed.getY() + 0.5, bed.getZ(), bed.getX(), bed.getY() + 0.5, bed.getZ() + 1);
				RenderUtils.drawLine(bed.getX(), bed.getY(), bed.getZ(), bed.getX(), bed.getY() + 0.5, bed.getZ());
				RenderUtils.drawLine(bed.getX(), bed.getY() + 0.5, bed.getZ(), bed.getX(), bed.getY() + 0.5, bed.getZ());
				RenderUtils.drawLine(bed.getX() + 1, bed.getY(), bed.getZ(), bed.getX() + 1, bed.getY() + 0.5, bed.getZ());
				RenderUtils.drawLine(bed.getX() + 1, bed.getY() + 0.5, bed.getZ(), bed.getX() + 1, bed.getY() + 0.5, bed.getZ());
				RenderUtils.drawLine(bed.getX(), bed.getY(), bed.getZ() + 1, bed.getX(), bed.getY() + 0.5, bed.getZ() + 1);
				RenderUtils.drawLine(bed.getX(), bed.getY() + 0.5, bed.getZ() + 1, bed.getX(), bed.getY() + 0.5, bed.getZ() + 1);
				RenderUtils.drawLine(bed.getX() + 1, bed.getY(), bed.getZ() + 1, bed.getX(), bed.getY(), bed.getZ() + 1);
				RenderUtils.drawLine(bed.getX() + 1, bed.getY() + 0.5, bed.getZ() + 1, bed.getX(), bed.getY() + 0.5, bed.getZ() + 1);
				RenderUtils.drawLine(bed.getX() + 1, bed.getY(), bed.getZ() + 1, bed.getX() + 1, bed.getY() + 0.5, bed.getZ() + 1);
				RenderUtils.drawLine(bed.getX() + 1, bed.getY() + 0.5, bed.getZ(), bed.getX() + 1, bed.getY() + 0.5, bed.getZ() + 1);
				RenderUtils.drawLine(bed.getX() + 1, bed.getY(), bed.getZ(), bed.getX() + 1, bed.getY(), bed.getZ() + 1);
				
			}
			
		}
		
		if (e instanceof EventUpdate && e.isPost()) {
			
			setInfo("Hypixel");
			
			BedInfo info = findBed();
			
			BlockPos bed = info.pos;
			EnumFacing bedFace = info.face;
			
			if (bed == null || bedFace == null) {
				return;
			}
			
			EventUpdate event = (EventUpdate)e;
			
			if ((Vergo.config.modKillAura.isDisabled() || KillAura.target == null)) {
				float[] rots = RotationUtils.getRotationFromPosition(bed.getX(), bed.getZ(), bed.getY());
				event.setYaw(rots[0]);
				event.setPitch(rots[1]);
				RenderUtils.setCustomYaw(rots[0]);
				RenderUtils.setCustomPitch(rots[1]);
			}
			
//			mc.playerController.curBlockDamageMP = 1.0f;
//			mc.playerController.curBlockDamageMP -= 0.05;
			mc.playerController.onPlayerDamageBlock(bed, bedFace);
			mc.thePlayer.swingItem();
			
		}
		
		
	}
	
	public static BedInfo lastBed = null;
	
	public static BedInfo findBed() {
		
		try {
			
			if (lastBed != null && mc.thePlayer.getDistance(lastBed.pos.getX(), lastBed.pos.getY(), lastBed.pos.getZ()) <= 4) {
				if (mc.theWorld.getBlockState(lastBed.pos).getBlock() instanceof BlockBed) {
					return lastBed;
				}
			}
			
		} catch (Exception e) {
			
		}
		
		BlockPos bed = null;
		EnumFacing bedFace = null;
		
		for (EnumFacing face1 : EnumFacing.VALUES) {
			
			BlockPos playerPos = mc.thePlayer.getPosition();
			if (mc.theWorld.getBlockState(playerPos.offset(face1)).getBlock() instanceof BlockBed) {
				
				bed = playerPos.offset(face1);
				bedFace = face1.getOpposite();
				break;
				
			}
			
			for (EnumFacing face2 : EnumFacing.VALUES) {
				
				BlockPos pos2 = playerPos.offset(face2);
				
				if (mc.theWorld.getBlockState(pos2).getBlock() instanceof BlockBed) {
					
					bed = pos2;
					bedFace = face2.getOpposite();
					break;
					
				}
				
				for (EnumFacing face3 : EnumFacing.VALUES) {
					
					BlockPos pos3 = pos2.offset(face3);
					
					if (mc.theWorld.getBlockState(pos3).getBlock() instanceof BlockBed) {
						
						bed = pos3;
						bedFace = face3.getOpposite();
						break;
						
					}
					
					for (EnumFacing face4 : EnumFacing.VALUES) {
						
						BlockPos pos4 = pos3.offset(face4);
						
						if (mc.theWorld.getBlockState(pos4).getBlock() instanceof BlockBed) {
							
							bed = pos4;
							bedFace = face4.getOpposite();
							break;
							
						}
						
						for (EnumFacing face5 : EnumFacing.VALUES) {
							
							BlockPos pos5 = pos4.offset(face5);
							
							if (mc.theWorld.getBlockState(pos5).getBlock() instanceof BlockBed) {
								
								bed = pos5;
								bedFace = face5.getOpposite();
								break;
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
		lastBed = new BedInfo(bed, bedFace);
		return lastBed;
		
		
	}
	
	public static class BedInfo {
		
		public BedInfo(BlockPos pos, EnumFacing face) {
			this.pos = pos;
			this.face = face;
		}
		
		public BlockPos pos;
		public EnumFacing face;
		
	}
	
}
