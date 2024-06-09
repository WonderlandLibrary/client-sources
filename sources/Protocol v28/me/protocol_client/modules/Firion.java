package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Firion extends Module{

	public Firion() {
		super("Firion", "firion", 0, Category.PLAYER, new String[]{""});
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	@EventTarget
	public void onEvent(EventPreMotionUpdates event){
	    if ((Wrapper.getPlayer().isBurning()) && (Wrapper.getPlayer().getActivePotionEffect(Potion.fireResistance) == null) && (this.mc.theWorld.getBlockState(new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - 1.0D, Wrapper.getPlayer().posZ)).getBlock().getMaterial() != Material.lava)) {
		      for (int i = 0; i < 32; i++)
		      {
		        Wrapper.sendPacket(new C03PacketPlayer(Wrapper.getPlayer().onGround));
		      }
		    }
	}
}
