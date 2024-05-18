package me.valk;

import java.io.File;

import me.valk.agway.AgwayClient;
import me.valk.agway.gui.altmanager.AltManager;
import me.valk.agway.gui.altmanager.GuiAddAlt;
import me.valk.event.EventSystem;
import me.valk.event.Listener;
import me.valk.event.events.other.EventKeyPress;
import me.valk.event.events.screen.EventRenderChatMessage;
import me.valk.event.events.screen.EventRenderScreen;
import me.valk.manager.Manager;
import me.valk.manager.Managers;
import me.valk.manager.managers.CommandManager;
import me.valk.manager.managers.FriendManager;
import me.valk.manager.managers.ModuleManager;
import me.valk.module.Module;
import me.valk.overlay.VitalOverlay;
import me.valk.overlay.tabGui.TabGui;
import me.valk.utils.Wrapper;
import me.valk.utils.value.Value;
import net.minecraft.client.Minecraft;

public class Vital {
	public static boolean developerMode;
	private static Vital vital;
	private static Managers managers;
	private Client client;
	private TabGui tabGui;
	public static File clientDir;
	private static AltManager altManager;
	
	static {
		Vital.vital = new Vital();
	}

	public void onLaunch() {
		this.setClient(new AgwayClient());
		System.out.println("Launching Agway");
		(Vital.managers = new Managers()).setCommandManager(new CommandManager());
		Vital.managers.setModuleManager(new ModuleManager());
		for (final Module module : getManagers().getModuleManager().getContents()) {
			module.mc = Minecraft.getMinecraft();
			module.p = Minecraft.getMinecraft().thePlayer;
		}
		EventSystem.register(new Listener<EventKeyPress>() {
			@Override
            public void onEvent(EventKeyPress event) {
                for(Module module : Vital.getManagers().getModuleManager().getContents()){

                    if(event.getKey() == module.getData().getKeyBind().getKey()){
                        module.toggle();
                    }

                }
            }

        });
		for (Value value : client.getVals()) {
			getManagers().getOptionManager().addContent(value);
		}
		EventSystem.register(new Listener<EventRenderScreen>() {
			@Override
			public void onEvent(final EventRenderScreen event) {
				if (Wrapper.getMinecraft().gameSettings.showDebugInfo) {
					return;
				}
				for (final VitalOverlay overlay : Vital.getManagers().getOverlayManager().getContents()) {
					overlay.render();
				}
			}
		});
		final Manager[] managersToLoad = { getManagers().getFriendManager(),
				getManagers().getOptionManager() };
		Manager[] array;
		for (int length = (array = managersToLoad).length, i = 0; i < length; ++i) {
			final Manager manager = array[i];
			manager.load();
		}
		this.client.start();
		EventSystem.register(new Listener<EventRenderChatMessage>() {
			@Override
			public void onEvent(final EventRenderChatMessage event) {
				for (final FriendManager.Friend friend : Vital.managers.getFriendManager().getContents()) {
					event.setMessage(event.getMessage().replaceAll("(?i)" + friend.getName(),
							"§r§a" + friend.getNickname() + "§r"));
				}
			}
		});
	}

	public static Vital getVital() {
		return Vital.vital;
	}

	public static void start() {
		Vital.vital.onLaunch();
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public Client getClient() {
		return this.client;
	}

	public static Managers getManagers() {
		return Vital.managers;

	}

	public void setTabGui(final TabGui tabGui) {
		this.tabGui = tabGui;
	}

	public TabGui getTabGui() {
		return this.tabGui;
	}

	public static AltManager getAltManager() {
		return vital.altManager;
	}

}
