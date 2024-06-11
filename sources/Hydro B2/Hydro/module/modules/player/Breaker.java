package Hydro.module.modules.player;

import java.util.ArrayList;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.event.Event;
import Hydro.event.events.EventUpdate;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.Timer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Breaker extends Module {
	
	Timer timer = new Timer();

	public Breaker() {
		super("Breaker", 0, true, Category.PLAYER, "Break beds in a certain radius around you");
		ArrayList<String> options = new ArrayList<>();
		options.add("Bed");
		options.add("Cake");
		options.add("Egg");
		Client.instance.settingsManager.rSetting(new Setting("BreakerBlock", "Block", this, "Bed", options));
		Client.instance.settingsManager.rSetting(new Setting("BreakerRange", "Range", this, 4.3, 1, 8, false));
	}

	@Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate && event.isPre()) {
            int n = (int) Client.instance.settingsManager.getSettingByName("BreakerRange").getValDouble();
            for (int i = -n; i < n; ++i) {
                for (int j = n; j > -n; --j) {
                    for (int k = -n; k < n; ++k) {
                        int n2 = (int)this.mc.thePlayer.posX + i;
                        int n3 = (int)this.mc.thePlayer.posY + j;
                        int n4 = (int)this.mc.thePlayer.posZ + k;
                        BlockPos blockPos = new BlockPos(n2, n3, n4);
                        Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
                        
                        if(Client.instance.settingsManager.getSettingByName("BreakerBlock").getValString().equals("Bed")) {
                        	setDisplayName("Bed");
                        	if(block instanceof BlockBed) {
                        		if(timer.hasTimeElapsed(250, true)) {
                        			mc.thePlayer.swingItem();
                                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                                    if(timer.hasTimeElapsed(500, true)) {
                                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                                    }
                        		}
                            	
                            }
                        }
                        
                        if(Client.instance.settingsManager.getSettingByName("BreakerBlock").getValString().equals("Cake")) {
                        	setDisplayName("Cake");
                        	if(block instanceof BlockCake) {
                        		if(timer.hasTimeElapsed(250, true)) {
                        			mc.thePlayer.swingItem();
                                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                                    if(timer.hasTimeElapsed(500, true)) {
                                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                                    }   
                        		}
                        	}
                        }
                        
                        if(Client.instance.settingsManager.getSettingByName("BreakerBlock").getValString().equals("Egg")) {
                        	setDisplayName("Egg");
                        	if(block instanceof BlockDragonEgg) {
                        		if(timer.hasTimeElapsed(250, true)) {
                        			mc.thePlayer.swingItem();
                                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                                    if(timer.hasTimeElapsed(500, true)) {
                                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                                    }   
                        		}
                            	                         
                        	}
                        }
                       
                    }
                }
            }
        }
    }
	
}
