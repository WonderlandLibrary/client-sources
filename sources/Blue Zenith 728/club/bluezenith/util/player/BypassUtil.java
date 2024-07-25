package club.bluezenith.util.player;

import club.bluezenith.BlueZenith;
import club.bluezenith.util.MinecraftInstance;

import static club.bluezenith.BlueZenith.scheduledExecutorService;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static net.minecraft.client.Minecraft.getMinecraft;

@SuppressWarnings("all")
public class BypassUtil extends MinecraftInstance {
    public static float bypass_value = 0.1536f;

    public static void NCPDisabler() {
        BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Disabler", "Disabled NoCheatPlus!", 1000);
        scheduledExecutorService.schedule(() -> getMinecraft().shutdown(), 700, MILLISECONDS);
    }
}
