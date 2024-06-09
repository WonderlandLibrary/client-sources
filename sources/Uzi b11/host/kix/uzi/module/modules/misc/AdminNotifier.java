package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.Uzi;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.Logger;
import host.kix.uzi.utilities.minecraft.Stopwatch;

/**
 * Created by Kix on 5/31/2017.
 * Made for the eclipse project.
 */
public class AdminNotifier extends Module {

    private Stopwatch stopwatch = new Stopwatch();

    public AdminNotifier() {
        super("AdminNotifier", 0, Category.MISC);
    }

    @SubscribeEvent
    public void updateGame(UpdateEvent event) {
        mc.theWorld.playerEntities
                .stream()
                .filter(player -> Uzi.getInstance().getAdminManager().get(player.getName()).isPresent())
                .forEach(player -> {
                    if (mc.thePlayer.getDistanceToEntity(player) < 10) {
                        if (stopwatch.hasCompleted(4000)) {
                            Logger.logToChat("An admin is near.");
                            stopwatch.reset();
                        }
                    }
                });
    }

}
