package src.Wiksi.utils.client;

import src.Wiksi.ui.mainmenu.MainScreen;
import lombok.experimental.UtilityClass;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.UUID;


@UtilityClass
public class ClientUtil implements IMinecraft {
    private static final String discordID = "1182950999292383292";

    public static boolean legitMode;
    private static Clip currentClip = null;
    private static boolean pvpMode;
    private static UUID uuid;



    public static void sendMessage(String message) {
    }

    public void updateBossInfo(SUpdateBossInfoPacket packet) {
        if (packet.getOperation() == SUpdateBossInfoPacket.Operation.ADD) {
            if (StringUtils.stripControlCodes(packet.getName().getString()).toLowerCase().contains("pvp")) {
                pvpMode = true;
                uuid = packet.getUniqueId();
            }
        } else if (packet.getOperation() == SUpdateBossInfoPacket.Operation.REMOVE) {
            if (packet.getUniqueId().equals(uuid))
                pvpMode = false;
        }
    }
    public boolean isConnectedToServer(String ip) {
        return mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP != null && mc.getCurrentServerData().serverIP.contains(ip);
    }
    public static boolean isPvP() {
        return pvpMode;
    }

    public void playSound(String sound, float value, boolean nonstop) {

        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
        try {
            currentClip = AudioSystem.getClip();
            InputStream is = mc.getResourceManager().getResource(new ResourceLocation("Wiksi/sounds/" + sound + ".wav")).getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bis);
            if (audioInputStream == null) {
                System.out.println("Sound not found!");
                return;
            }

          currentClip.open(audioInputStream);
            currentClip.start();
            FloatControl floatControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = floatControl.getMinimum();
            float max = floatControl.getMaximum();
            float volumeInDecibels = (float) (min * (1 - (value / 100.0)) + max * (value / 100.0));
            floatControl.setValue(volumeInDecibels);
            if (nonstop) {
                currentClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        currentClip.setFramePosition(0);
                        currentClip.start();
                    }
                });
            }
        } catch (Exception exception) {
            // Обработка исключения
            exception.printStackTrace();
        }
    }

    public void stopSound() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }

    public int calc(int value) {
        MainWindow rs = mc.getMainWindow();
        return (int) (value * rs.getGuiScaleFactor() / 2);
    }

    public Vec2i getMouse(int mouseX, int mouseY) {
        return new Vec2i((int) (mouseX * Minecraft.getInstance().getMainWindow().getGuiScaleFactor() / 2), (int) (mouseY * Minecraft.getInstance().getMainWindow().getGuiScaleFactor() / 2));
    }
    private static boolean hasCheckedMM = false;
    private static float volumeMM = 0;
    public static boolean isMainMenuOpen() {
        Minecraft mc = Minecraft.getInstance();
        Screen currentScreen = mc.currentScreen;

        return currentScreen instanceof MainScreen;
    }
    public static void MainMenu() {
        boolean isMainMenuOpen = isMainMenuOpen();

        if (isMainMenuOpen) {
            if (!hasCheckedMM) {
                volumeMM = 80;
                ClientUtil.playSound("MainMenu", volumeMM, true);
                hasCheckedMM = true;
            }
        } else {
            if (hasCheckedMM) {
                volumeMM = 0;
                ClientUtil.stopSound();
                hasCheckedMM = false;
            }
        }
    }
}
