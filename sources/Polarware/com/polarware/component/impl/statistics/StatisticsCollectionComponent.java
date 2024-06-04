package com.polarware.component.impl.statistics;

import com.polarware.Client;
import com.polarware.component.Component;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.impl.exploit.LightningTrackerModule;
import com.polarware.module.impl.exploit.StaffDetectorModule;
import com.polarware.module.impl.other.*;
import com.polarware.module.impl.player.InventorySyncModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.ModuleToggleEvent;
import com.polarware.event.impl.other.ServerJoinEvent;
import com.polarware.event.impl.other.ServerKickEvent;
import com.polarware.value.Value;
import com.polarware.value.impl.ModeValue;
import util.time.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class StatisticsCollectionComponent extends Component {
    private final StopWatch serverTime = new StopWatch();
    private boolean saved;
    private final ArrayList<Module> modules = new ArrayList<>();

    private final Class<?>[] exempted = {
            RichPresenceModule.class,
            SecurityFeaturesModule.class,
            LightningTrackerModule.class,
            StaffDetectorModule.class,
            AntiAFKModule.class,
            CheatDetectorModule.class,
            ClickSoundsModule.class,
            DebuggerModule.class,
            MurderMysteryModule.class,
            PlayerNotifierModule.class,
            SpotifyModule.class,
            TranslatorModule.class,
            InventorySyncModule.class,
    };

    @EventLink()
    public final Listener<ServerJoinEvent> onServerJoin = event -> {
        saved = false;
        serverTime.reset();
        modules.clear();

        Client.INSTANCE.getModuleManager().getAll().forEach(module -> {
            if (module.isEnabled()) modules.add(module);
        });
    };

    @EventLink()
    public final Listener<ModuleToggleEvent> onModuleToggle = event -> {
        if (modules.stream().noneMatch(module -> Objects.equals(module.getDisplayName(), event.getModule().getDisplayName()))) {
            modules.add(event.getModule());
        }
    };

    @EventLink()
    public final Listener<ServerKickEvent> onServerKick = event -> {
        if (event.message.stream().anyMatch(line -> line.toLowerCase().contains("banned") ||
                line.toLowerCase().contains("kicked") || line.toLowerCase().contains("cheating"))) {

            saveData(true);
        }
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (mc.thePlayer.ticksExisted > 50 && serverTime.finished(50 * 20 * 60 * 30)) {
            saveData(false);
        }
    };

    public void saveData(boolean banned) {
        if (saved) return;
        saved = true;

        List<String> list = new ArrayList<>();

        for (Module module : modules) {
            if (module.getModuleInfo().category() == Category.RENDER || Arrays.stream(exempted).anyMatch(clazz -> clazz.equals(module.getClass()))) {
                continue;
            }

            String moduleData = module.getDisplayName();
            String valueData = "";

            for (Value<?> value : module.getAllValues()) {
                if (value instanceof ModeValue && (value.hideIf == null || !value.hideIf.getAsBoolean())) {
                    valueData += value.getName() + ": " + ((ModeValue) value).getValue().getName() + " ";
                }
            }

            list.add(moduleData + " " + valueData);
        }

//        Client.INSTANCE.getNetworkManager().getCommunication().write(new ClientModuleData(banned, list, LastConnectionComponent.ip));

    }

}
