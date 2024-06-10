package Snipeware.module.impl.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.lwjgl.input.Keyboard;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.packet.EventPacketSend;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.player.EventMove;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;
import Snipeware.util.other.TimeHelper;
import Snipeware.value.impl.EnumValue;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3d;

public class NoFall extends Module {
	
	private EnumValue<Mode> nofallMode = new EnumValue("NoFall Mode", Mode.Watchdog);

	public NoFall() {
		super("NoFall", Keyboard.KEY_NONE, ModuleCategory.PLAYER);
		addValues(nofallMode);
	}
    private double lastX, lastY, lastZ;
    private double lastFallDist = 0;
    public static boolean smooth = false;
    private boolean canNegate = true;
    private TimeHelper Delay = new TimeHelper();
    private ArrayList<Packet> packetList = new ArrayList<>();
	
	private enum Mode {
		Watchdog, Edit, 
	}
	
	public void onEnable() {
		Delay.reset();
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}

    private boolean isOverGlass(double x, double y, double z) {
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)) != Blocks.glass && mc.theWorld.getBlockState(new BlockPos(x, y, z)) != Blocks.stained_glass;
    }

    private boolean isInCage() {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        for (int i = 0; i < 1; ++i) {
            if (isOverGlass(x - 1, y + i, z) || isOverGlass(x + 1, y + i, z) || isOverGlass(x, y + i, z - 1) || isOverGlass(x, y + i, z + 1)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isBlockUnder() {
        if (mc.thePlayer.posY < 0)
            return false;
        for (int offset = 0; offset < (int) mc.thePlayer.posY + 2; offset += 2) {
            AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, -offset, 0);
            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
	@Handler
	public void onReceivePacket(final EventPacketReceive event) {
		switch (nofallMode.getValue()) {
		case Watchdog:
	       break;
		}
	}
    
	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		setSuffix(nofallMode.getValueAsString());
		if (!Client.INSTANCE.getModuleManager().getModule("Flight").isEnabled() && mc.thePlayer.fallDistance > 3) {
			switch (nofallMode.getValue()) {
			case Watchdog:
				if(canNegate) {
					event.setOnGround(true);
				}
			break;
			case Edit:
				event.setOnGround(true);
				break;
			}
		}
	}
	
	@Handler
	public void eventmove(EventMove event) {
	
	}
	
	@Handler
	public void onPacketSend(EventPacketSend event) {
		switch (nofallMode.getValue()) {
		case Watchdog:
			if(mc.thePlayer.onGround) {
				Delay.reset();
			}
			if(mc.thePlayer.fallDistance < 22) {
				if (!Client.INSTANCE.getModuleManager().getModule("Flight").isEnabled() && mc.thePlayer.fallDistance > 3) {
					BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
					if(mc.theWorld.getBlockState(blockPos).getBlock() != Blocks.air) {
						if(Delay.reach(200 * mc.thePlayer.fallDistance)) {
							canNegate = false;
						}else {
							canNegate = true;
						}
						
						if(mc.thePlayer.onGround) {
								if(event.getPacket() instanceof C03PacketPlayer) {
									final Packet p = event.getPacket();
									event.setCancelled(true);
									mc.thePlayer.motionY = 0;
									packetList.add(p);
								}
							}
							if(canNegate) {
								mc.thePlayer.posY -= 0.5f;
								try {
									for (Packet packets : packetList) {
										mc.getNetHandler().addToSendQueue(packets);
									}
									packetList.clear();
								}
								catch (final ConcurrentModificationException e) {
									e.printStackTrace();
								}
							}
				}
				}	
			}
		}
	}

    public double getBlockHeight() {
        return mc.thePlayer.posY - Math.round(mc.thePlayer.posY);
    }
    

}
