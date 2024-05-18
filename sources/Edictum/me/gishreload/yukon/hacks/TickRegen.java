package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import me.gishreload.yukon.*;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;

public class TickRegen extends Module{
//	private static Property<Float> health;
  //  private static Property<Integer> packets;
	public TickRegen() {
		super("TickRegen", 0, Category.COMBAT);
	//	health = new Property<Float>(this, "regen_health", Float.valueOf(18.0f));
    //    packets = new Property<Integer>(this, "regen_packet_amount", 100);
	}
	
	public void onEnable(){
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eTickRegen \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	public void onDisable(){
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eTickRegen \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
		super.onDisable();
	}
	
	@Override
	public void onUpdate(){
		if(this.isToggled()){
			 int index = 0;
		 //       while (index < packets.getValue()) {
		 //           if (Wrapper.mc.thePlayer.onGround && Wrapper.mc.thePlayer.getHealth() <= health.getValue().floatValue() && Wrapper.mc.thePlayer.getFoodStats().getFoodLevel() > 17) {
		 //               Wrapper.mc.getConnection().sendPacket(new CPacketPlayer(Wrapper.mc.thePlayer.onGround));
		            }
		    //        ++index;
		            super.onUpdate();
		}
}
