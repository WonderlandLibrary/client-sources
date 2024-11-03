package net.silentclient.client.mods.hud;

import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EntityAttackEvent;
import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

import java.text.DecimalFormat;

public class ReachDisplayMod extends HudMod {
	
	public static final DecimalFormat FORMAT = new DecimalFormat("0.##");

	private double distance = 0;
	private long hitTime = -1;
	
	public ReachDisplayMod() {
		super("Reach Display", ModCategory.MODS, "silentclient/icons/mods/reachdisplay.png");
	}
	
	@Override
	public String getText() {
		return "0,00 " + getPostText();
	}
	
	@Override
	public String getTextForRender() {
		if((System.currentTimeMillis() - hitTime) > 2000) {
			distance = 0;
		}
		
		return FORMAT.format(distance) + " " + getPostText();
	}
	
	@EventTarget
	public void totallyNoReachHax(EntityAttackEvent event) {
		if(mc.objectMouseOver != null && mc.objectMouseOver.hitVec != null) {
			distance = mc.objectMouseOver.hitVec.distanceTo(mc.thePlayer.getPositionEyes(1.0F));
			hitTime = System.currentTimeMillis();
		}
	}
	
	@Override
	public String getDefautPostText() {
		return "blocks";
	}

}
