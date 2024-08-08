package lol.point.returnclient.module.impl.misc;

import lol.point.Return;
import lol.point.returnclient.events.impl.player.EventAttack;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.StringSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

@ModuleInfo(
        name = "HitSound",
        description = "plays a hit sound on attack",
        category = Category.MISC
)
public class HitSound extends Module {

    private final StringSetting soundStyle = new StringSetting("Sound Style", new String[]{"Call of Duty", "Street Fighter", "Realistic", "Meme", "Pop"});

    public HitSound() {
        addSettings(soundStyle);
    }

    public String getSuffix() {
        return soundStyle.value;
    }

    @Subscribe
    private final Listener<EventAttack> onAttack = new Listener<>(attackEvent -> {
        String soundFilePath = "assets/minecraft/return/hitsound/" + soundStyle.value.replace(" ", "") + ".wav";

        try {
            playSound(soundFilePath, attackEvent.target.posX, attackEvent.target.posY, attackEvent.target.posZ);
        } catch (Exception e) {
            Return.LOGGER.error("Error trying to play sound {}", e.getMessage());
        }
    });

    private void playSound(String filePath, double x, double y, double z) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        ResourceLocation resourceLocation = new ResourceLocation(filePath);
        if (this.getClass().getResource("/" + filePath) == null) {
            Return.LOGGER.error("File {} not found", filePath);
            return;
        }

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/" + filePath));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();

        mc.getSoundHandler().playSound(PositionedSoundRecord.create(resourceLocation, (float) x, (float) y, (float) z));
    }

    private static Clip clip;

    public static final URL ok = HitSound.class.getClassLoader().getResource("assets/minecraft/return/hitsound/Fake.wav");

    public static void play() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(ok));
            FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(6.0206F);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

    }
}
