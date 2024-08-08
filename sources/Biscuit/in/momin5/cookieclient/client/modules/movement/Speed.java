package in.momin5.cookieclient.client.modules.movement;

import in.momin5.cookieclient.api.event.events.PlayerMoveEvent;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class Speed extends Module {

	public Speed() {
		super("Speed", "Makes you got at the speed of sound (or maybe even the speed of light).", Category.MOVEMENT);
	}

	private SettingMode mode = register(new SettingMode("Mode", this, "Vanilla", "Vanilla", "BunnyHop", "LowHop", "OnGround", "YPort"));
	private SettingNumber speed = register(new SettingNumber("Speed", this, 5f, 1f, 10f, 0.5f));

	@EventHandler
	public Listener<PlayerMoveEvent> playerMoveEventListener = new Listener<>(event -> {

	});

	@Override
	public void onUpdate() {

	}
}
