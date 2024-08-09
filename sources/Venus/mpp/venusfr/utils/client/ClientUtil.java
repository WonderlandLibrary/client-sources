/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.client;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.UUID;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import mpp.venusfr.ui.mainmenu.MainScreen;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.client.Vec2i;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

public final class ClientUtil
implements IMinecraft {
    private static Clip currentClip = null;
    private static boolean pvpMode;
    private static UUID uuid;
    private static boolean hasCheckedMM;
    private static float volumeMM;

    public static void updateBossInfo(SUpdateBossInfoPacket sUpdateBossInfoPacket) {
        if (sUpdateBossInfoPacket.getOperation() == SUpdateBossInfoPacket.Operation.ADD) {
            if (StringUtils.stripControlCodes(sUpdateBossInfoPacket.getName().getString()).toLowerCase().contains("pvp")) {
                pvpMode = true;
                uuid = sUpdateBossInfoPacket.getUniqueId();
            }
        } else if (sUpdateBossInfoPacket.getOperation() == SUpdateBossInfoPacket.Operation.REMOVE && sUpdateBossInfoPacket.getUniqueId().equals(uuid)) {
            pvpMode = false;
        }
    }

    public static boolean isConnectedToServer(String string) {
        return mc.getCurrentServerData() != null && ClientUtil.mc.getCurrentServerData().serverIP != null && ClientUtil.mc.getCurrentServerData().serverIP.contains(string);
    }

    public static boolean isPvP() {
        return pvpMode;
    }

    public static void playSound(String string, float f, boolean bl) {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
        try {
            currentClip = AudioSystem.getClip();
            InputStream inputStream = mc.getResourceManager().getResource(new ResourceLocation("venusfr/sounds/" + string + ".wav")).getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            if (audioInputStream == null) {
                System.out.println("Sound not found!");
                return;
            }
            currentClip.open(audioInputStream);
            currentClip.start();
            FloatControl floatControl = (FloatControl)currentClip.getControl(FloatControl.Type.MASTER_GAIN);
            float f2 = floatControl.getMinimum();
            float f3 = floatControl.getMaximum();
            float f4 = (float)((double)f2 * (1.0 - (double)f / 100.0) + (double)f3 * ((double)f / 100.0));
            floatControl.setValue(f4);
            if (bl) {
                currentClip.addLineListener(ClientUtil::lambda$playSound$0);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void stopSound() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }

    public static int calc(int n) {
        MainWindow mainWindow = mc.getMainWindow();
        return (int)((double)n * mainWindow.getGuiScaleFactor() / 2.0);
    }

    public static Vec2i getMouse(int n, int n2) {
        return new Vec2i((int)((double)n * Minecraft.getInstance().getMainWindow().getGuiScaleFactor() / 2.0), (int)((double)n2 * Minecraft.getInstance().getMainWindow().getGuiScaleFactor() / 2.0));
    }

    public static boolean isMainMenuOpen() {
        Minecraft minecraft = Minecraft.getInstance();
        Screen screen = minecraft.currentScreen;
        return screen instanceof MainScreen;
    }

    public static void MainMenu() {
        boolean bl = ClientUtil.isMainMenuOpen();
        if (bl) {
            if (!hasCheckedMM) {
                volumeMM = 90.0f;
                ClientUtil.playSound("MainMenu", volumeMM, true);
                hasCheckedMM = true;
            }
        } else if (hasCheckedMM) {
            volumeMM = 0.0f;
            ClientUtil.stopSound();
            hasCheckedMM = false;
        }
    }

    private ClientUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static void lambda$playSound$0(LineEvent lineEvent) {
        if (lineEvent.getType() == LineEvent.Type.STOP) {
            currentClip.setFramePosition(0);
            currentClip.start();
        }
    }

    static {
        hasCheckedMM = false;
        volumeMM = 0.0f;
    }
}

