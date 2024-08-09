package wtf.resolute.utiled.client;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import lombok.experimental.UtilityClass;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

import javax.sound.sampled.*;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.lang.Math.signum;
import static net.minecraft.client.Minecraft.player;
import static net.minecraft.util.math.MathHelper.wrapDegrees;

@UtilityClass
public class ClientUtil implements IMinecraft {

    private static Clip currentClip = null;
    private static boolean pvpMode;
    private static UUID uuid;

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
    private static AudioInputStream stream;
    private static final List<Clip> CLIPS_LIST = new ArrayList<>();
    public boolean isPvP() {
        return pvpMode;
    }
    public static void playSound2(final String location, double volume) {
        List<Clip> mutableClips = new ArrayList<>(CLIPS_LIST);
        mutableClips.stream().filter(Objects::nonNull).filter(Line::isOpen).filter(clip -> !clip.isRunning()).forEach(Clip::close);
        mutableClips.stream().filter(Objects::nonNull).filter(clip -> !(clip.isOpen() && clip.isRunning())).forEach(Clip::stop);
        mutableClips.removeIf(clip -> !clip.isRunning());
        try {
            InputStream is = mc.getResourceManager().getResource(new ResourceLocation("resolute/sounds/" + location + ".wav")).getInputStream();
            stream = AudioSystem.getAudioInputStream(new BufferedInputStream(ClientUtil.class.getResourceAsStream(is.toString())));
        } catch (final Exception ignored) {
        }
        assert stream != null;
        try {
            mutableClips.add(AudioSystem.getClip());
        } catch (final Exception exception) {
            System.out.println("Client:SoundUtil:" + exception.getMessage());
        }
        mutableClips.stream().filter(Objects::nonNull).filter(clip -> !clip.isOpen()).forEach(clip -> {
            try {
                clip.open(stream);
            } catch (final Exception ignored) {
            }
        });
        mutableClips.stream().filter(Objects::nonNull).filter(Clip::isOpen).forEach(clip -> {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            int dbValue = (int) (Math.log((volume < 0.D ? 0.D : Math.min(volume, 1.D)) * .5D) / Math.log(10.D) * 20.D);
            volumeControl.setValue(dbValue);
        });
        mutableClips.stream().filter(Objects::nonNull).filter(Clip::isOpen).filter(clip -> !clip.isRunning()).forEach(Clip::start);
    }
    public void playSound(String sound, float value, boolean nonstop) {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
        try {
            currentClip = AudioSystem.getClip();
            InputStream is = mc.getResourceManager().getResource(new ResourceLocation("resolute/sounds/" + sound + ".wav")).getInputStream();
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
    static IPCClient client = new IPCClient(1219675471822848040L);



        private static RichPresence.Builder builder = new RichPresence.Builder();

        public static void startRPC() {
            client.setListener(new IPCListener() {
                @Override
                public void onPacketReceived(IPCClient client, Packet packet) {
                    IPCListener.super.onPacketReceived(client, packet);
                }

                @Override
                public void onReady(IPCClient client) {
                    updateRichPresence(mc.session.getUsername());
                }
            });

            try {
                client.connect();
            } catch (NoDiscordClientException e) {
                System.out.println("DiscordRPC: " + e.getMessage());
            }
        }

        public static void updateRichPresence(String username) {
            builder.setDetails("Version: 0.4 Recode")
                    .setStartTimestamp(OffsetDateTime.now())
                    .setLargeImage("https://i.imgur.com/dfDI4ht.gif", "https://discord.gg/V2xANsZNc8");
            client.sendRichPresence(builder.build());
        }



    public void stopSound() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }
    public boolean isConnectedToServer(String ip) {
        return mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP != null && mc.getCurrentServerData().serverIP.contains(ip);
    }
    public int calc(int value) {
        MainWindow rs = mc.getMainWindow();
        return (int) (value * rs.getGuiScaleFactor() / 2);
    }

    public Vec2i getMouse(int mouseX, int mouseY) {
        return new Vec2i((int) (mouseX * Minecraft.getInstance().getMainWindow().getGuiScaleFactor() / 2), (int) (mouseY * Minecraft.getInstance().getMainWindow().getGuiScaleFactor() / 2));
    }

}
