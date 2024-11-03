package net.silentclient.client.mods.hud;

import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.event.impl.EntityAttackEvent;
import net.silentclient.client.event.impl.EntityDamageEvent;
import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

public class ComboCounterMod extends HudMod {
	private long hitTime = -1;
	private int combo;
	private int possibleTarget;
	
	public ComboCounterMod() {
		super("Combo Counter", ModCategory.MODS, "silentclient/icons/mods/combo.png");
	}
	
	@EventTarget
	public void onTick(ClientTickEvent event) {
		if((System.currentTimeMillis() - hitTime) > 2000) {
			combo = 0;
		}
	}
	
	@EventTarget
	public void onAttackEntity(EntityAttackEvent event) {
		possibleTarget = event.getVictim().getEntityId();
	}
	
	@EventTarget
	public void onDamageEntity(EntityDamageEvent event) {
		if(event.getEntity().getEntityId() == possibleTarget) {
			combo++;
			possibleTarget = -1;
			hitTime = System.currentTimeMillis();
		}
		else if(event.getEntity() == mc.thePlayer) {
			combo = 0;
		}
	}
	
	@Override
	public String getText() {
		return "00 " + getPostText();
	}
	
	@Override
	public String getTextForRender() {
		if(combo == 0) {
			return "No " + getPostText();
		} else {
			return combo + " " + getPostText();
		}
	}
	
	@Override
	public String getDefautPostText() {
		return "Combo";
	}
}
