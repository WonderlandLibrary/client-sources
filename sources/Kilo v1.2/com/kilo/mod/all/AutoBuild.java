package com.kilo.mod.all;

import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class AutoBuild extends Module {
	
	private boolean building;
	private final long ncpdelay = 80;
	
	public AutoBuild(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("NC+", "Use on servers using NC+", Interactable.TYPE.CHECKBOX, true, null, false);
		
		addOption("Pole", "Build a pole on the selected block face", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Wall", "Build a wall on the selected block face", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Floor", "Build a floor on the selected block face", Interactable.TYPE.CHECKBOX, false, null, false);
	}
	
	public void onPlayerBlockPlace(final ItemStack stack, final BlockPos pos, final EnumFacing face, final Vec3 hitVec) {
		building = true;
		new Thread() {
			@Override
			public void run() {
		        float var7 = (float)(hitVec.xCoord - (double)pos.getX());
		        float var8 = (float)(hitVec.yCoord - (double)pos.getY());
		        float var9 = (float)(hitVec.zCoord - (double)pos.getZ());
		        
		        float f = (MathHelper.floor_double((double)(mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
		    	if (Util.makeBoolean(getOptionValue("pole"))) {
	        		for(int y = 0; y < 6; y++){
    	        		BlockPos bp = new BlockPos(pos.getX(), pos.getY()+(y), pos.getZ());
    	    	        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(bp, face.getIndex(), stack, var7, var8, var9));
	    	        	try { sleep(Util.makeBoolean(getOptionValue("nc+"))?ncpdelay:10); } catch (Exception e) {}
	    			}
		    	}
		    	
		    	if (Util.makeBoolean(getOptionValue("wall"))) {
		    		if (f == 0 || f == 2){
		        		for(int x = -2; x <= 2; x++){
		        			for(int y = 0; y < 6; y++){
		                		BlockPos bp = new BlockPos(pos.getX()+x, pos.getY()+y, pos.getZ());
		    	    	        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(bp, face.getIndex(), stack, var7, var8, var9));
			    	        	try { sleep(Util.makeBoolean(getOptionValue("nc+"))?ncpdelay:10); } catch (Exception e) {}
		        			}
		        		}
		    		} else {
		    			for(int x = -2; x <= 2; x++){
		        			for(int y = 0; y < 6; y++){
		                		BlockPos bp = new BlockPos(pos.getX(), pos.getY()+y, pos.getZ()+x);
		    	    	        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(bp, face.getIndex(), stack, var7, var8, var9));
			    	        	try { sleep(Util.makeBoolean(getOptionValue("nc+"))?ncpdelay:10); } catch (Exception e) {}
		        			}
		        		}
		    		}
		    	}
		    	
				if (Util.makeBoolean(getOptionValue("floor"))) {
		    		for(int x = -2+(2*(int)var7)+(2*(int)(var7-1)); x <= 2+(2*(int)var7)+(2*(int)(var7-1)); x++){
		    			for(int z = -2+(2*(int)var9)+(2*(int)(var9-1)); z <= 2+(2*(int)var9)+(2*(int)(var9-1)); z++){
		            		BlockPos bp = new BlockPos(pos.getX()+x, pos.getY(), pos.getZ()+z);
			    	        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(bp, face.getIndex(), stack, var7, var8, var9));
		    	        	try { sleep(Util.makeBoolean(getOptionValue("nc+"))?ncpdelay:10); } catch (Exception e) {}
		    			}
		    		}
		    	}

				building = false;
			}
		}.start();
	}
}
