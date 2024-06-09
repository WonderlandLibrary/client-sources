package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class CNuker extends Module
{
    Block blockType;
    BlockPos blockPos;
    private int e;
    private int f;
    private int g;

    public CNuker()
    {
        super("Nuker", "nuker", Keyboard.KEY_NONE, Category.WORLD, new String[]{"friend"});
    }
    @EventTarget
    public void onEvent(EventPreMotionUpdates event)
    {
        if (this.isToggled())
        {
        	if(Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
        	{
            for (int ix = -4; ix < 4; ++ix)
            {
                for (int iy = -4; iy < 4; ++iy)
                {
                    for (int iz = -4; iz < 4; ++iz)
                    {
                        int e = (int)Minecraft.getMinecraft().thePlayer.posX + ix;
                        int f = (int)Minecraft.getMinecraft().thePlayer.posY + iy;
                        int g = (int)Minecraft.getMinecraft().thePlayer.posZ + iz;
                        this.blockPos = new BlockPos(e, f, g);
                        this.blockType = mc.theWorld.getBlockState(blockPos).getBlock();

                        if (this.blockType != Block.getBlockFromName("air"))
                        {
                        	
                            C07PacketPlayerDigging.Action var2 = C07PacketPlayerDigging.Action.START_DESTROY_BLOCK;
                            C07PacketPlayerDigging.Action var3 = C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK;
                            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(var2, this.blockPos, EnumFacing.UP));
                            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(var3, this.blockPos, EnumFacing.UP));
                        }
                    }
                }
            }
        }
        }
    }
    public void onEnable()
	{
    	EventManager.register(this);
    }
	public void onDisable()
	{
	EventManager.unregister(this);	
	}
	public void runCmd(String s){
		try{
			String[] args = s.split(" ");
			if(args[0].equalsIgnoreCase("add")){
				String name = args[1];
				String alias = args[1];
				if(!Protocol.getFriendManager().isFriend(name)){
					Protocol.getFriendManager().addFriend(name, alias);
					Wrapper.tellPlayer("§7Added "+ Protocol.primColor + name + " §7as a friend");
				}else{
					Wrapper.tellPlayer(Protocol.primColor + name + " §7is already your friend");
				}
			}
			if(args[0].equalsIgnoreCase("del")){
				String name = args[1];
				if(Protocol.getFriendManager().isFriend(name)){
					Protocol.getFriendManager().removeFriend(name);
					Wrapper.tellPlayer("§7Removed " + Protocol.primColor + name + " §7from your friend list");
				}else{
					Wrapper.tellPlayer(Protocol.primColor + name + " §7is not your friend");
				}
			}
		}catch(Exception e){
			Wrapper.tellPlayer("\2477Invalid command for " + Protocol.primColor + "Friend\2477.");
			Wrapper.tellPlayer("\2477-" + Protocol.primColor + "friend \2477add <name>");
		}
	}
}
