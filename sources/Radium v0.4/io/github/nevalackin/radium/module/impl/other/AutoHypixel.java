package io.github.nevalackin.radium.module.impl.other;

import io.github.nevalackin.radium.RadiumClient;
import io.github.nevalackin.radium.event.impl.packet.PacketReceiveEvent;
import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.module.impl.combat.KillAura;
import io.github.nevalackin.radium.module.impl.movement.Flight;
import io.github.nevalackin.radium.module.impl.movement.LongJump;
import io.github.nevalackin.radium.module.impl.movement.Speed;
import io.github.nevalackin.radium.module.impl.player.InventoryManager;
import io.github.nevalackin.radium.notification.Notification;
import io.github.nevalackin.radium.notification.NotificationType;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.utils.ServerUtils;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(label = "Auto Hypixel", category = ModuleCategory.OTHER)
public final class AutoHypixel extends Module {

    private final Property<Boolean> autoDisableProperty = new Property<>("Auto Disable", true);

    private List<Module> movementModules = new ArrayList<>();

    private PingSpoof pingSpoof;
    private InventoryManager inventoryManager;
    private KillAura killAura;

    @Override
    public void onEnable() {
        pingSpoof = ModuleManager.getInstance(PingSpoof.class);
        inventoryManager = ModuleManager.getInstance(InventoryManager.class);
        killAura = ModuleManager.getInstance(KillAura.class);

        movementModules = Arrays.asList(
                ModuleManager.getInstance(Speed.class),
                ModuleManager.getInstance(Flight.class),
                ModuleManager.getInstance(LongJump.class));
    }

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (pingSpoof != null && ServerUtils.isOnHypixel() && !pingSpoof.isEnabled()) {
            pingSpoof.toggle();
            RadiumClient.getInstance().getNotificationManager().add(new Notification("Bypass",
                    "You must use Ping Spoof on hypixel", NotificationType.WARNING));
        }
    }

    @Listener
    public void onPacketReceiveEvent(PacketReceiveEvent event) {
        if (autoDisableProperty.getValue()) {
            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                if (movementModules != null) {
                    boolean msg = false;
                    for (Module module : movementModules)
                        if (module.isEnabled()) {
                            module.toggle();
                            msg = true;
                        }

                    if (msg)
                        RadiumClient.getInstance().getNotificationManager().add(new Notification("Flag",
                                "Disabling modules to prevent flags", NotificationType.WARNING));
                }
            } else if (event.getPacket() instanceof S07PacketRespawn) {
//                RadiumClient.getInstance().getNotificationManager().add(new Notification("Changed Lobbies",
//                        "Disabled some modules on lobby change", NotificationType.INFO));
//
//                if (inventoryManager != null && inventoryManager.isEnabled())
//                    inventoryManager.toggle();
//
//                if (killAura != null && killAura.isEnabled())
//                    killAura.toggle();
            }
        }
    }
}
