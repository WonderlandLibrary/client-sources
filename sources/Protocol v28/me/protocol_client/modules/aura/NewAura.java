package me.protocol_client.modules.aura;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.modules.aura.types.MultiAura;
import me.protocol_client.modules.aura.types.SwitchAura;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import me.protocol_client.thanks_slicky.properties.Value;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketSent;
import events.EventPostMotionUpdates;
import events.EventPreMotionUpdates;

public class NewAura extends Module {

	public NewAura() {
		super("Aura", "aura", Keyboard.KEY_R, Category.COMBAT, new String[] { "ka", "aura", "killaura", "killa" });
	}

	public final Value<Boolean>			switcha		= new Value<>("aura_switch", true);
	public final Value<Boolean>			multi		= new Value<>("aura_multi", false);
	public final Value<Boolean>			player		= new Value<>("aura_player", true);
	public final Value<Boolean>			mob			= new Value<>("aura_mob", false);
	public final Value<Boolean>			block		= new Value<>("aura_block", true);
	public final Value<Boolean>			lockview	= new Value<>("aura_lockview", false);
	public final Value<Boolean>			crits		= new Value<>("aura_criticals", false);
	public final Value<Boolean>			invisible	= new Value<>("aura_invisible", true);
	public final Value<Boolean>			wallcheck	= new Value<>("aura_walls", true);
	public final Value<Boolean>			dura		= new Value<>("aura_dura", false);

	public final ClampedValue<Float>	range		= new ClampedValue<>("aura_range", 3.9f, 1f, 6f);
	public final ClampedValue<Float>	blockrange	= new ClampedValue<>("aura_block_range", 8f, 1f, 10f);
	public final ClampedValue<Float>	delay		= new ClampedValue<>("aura_aps", 10.5f, 1f, 20f);
	public final ClampedValue<Float>	existed		= new ClampedValue<>("aura_existed", 30f, 0f, 100f);

	public void onEnable() {
		Protocol.getAura().getCurrent().littleshits.clear();
		Protocol.getAura().getCurrent().meme = null;
		Protocol.getAura().getCurrent().yaw = Wrapper.getPlayer().rotationYaw;
		Protocol.getAura().getCurrent().pitch = Wrapper.getPlayer().rotationPitch;
		EventManager.register(this);
	}

	public void onDisable() {
		Protocol.getAura().getCurrent().littleshits.clear();
		Protocol.getAura().getCurrent().meme = null;
		Protocol.getAura().getCurrent().lastTarget = null;
		EventManager.unregister(this);
		Protocol.getAura().getCurrent().tTicks = 0;
		Protocol.getAura().getCurrent().critTicks = 0;
		Protocol.getAura().getCurrent().switchTicks = 0;
		Protocol.getAura().getCurrent().yaw = Wrapper.getPlayer().rotationYaw;
		Protocol.getAura().getCurrent().pitch = Wrapper.getPlayer().rotationPitch;
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		if (switcha.getValue()) {
			setDisplayName("Aura [Switch] [" + Protocol.getAura().getCurrent().littleshits.size() + "]");
			if(!(Protocol.getAura().getCurrent() instanceof SwitchAura)){
			Protocol.getAura().setCurrent(new SwitchAura());
			}
		}
		if (multi.getValue()) {
			setDisplayName("Aura [Multi]");
			if(!(Protocol.getAura().getCurrent() instanceof MultiAura)){
				Protocol.getAura().setCurrent(new MultiAura());
				}
		}
		Protocol.getAura().getCurrent().onUpdate(event);
	}
	@EventTarget
	public void onPostUpdate(EventPostMotionUpdates event){
		Protocol.getAura().getCurrent().afterUpdate(event);
	}
	 @EventTarget
	 public void onPacketSent(EventPacketSent event) {
		 Protocol.getAura().getCurrent().onPacketOut(event);
	 }
}
