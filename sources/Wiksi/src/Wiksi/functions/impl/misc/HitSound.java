package src.Wiksi.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.AttackEvent;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;

import static java.lang.Math.*;
import static java.lang.Math.signum;
import static net.minecraft.util.math.MathHelper.wrapDegrees;

@FunctionRegister(name = "HitSound", type = Category.Misc)
public class HitSound extends Function {

    private final ModeSetting sound = new ModeSetting("Звук",
            "hit2",
            "bell", "metallic", "rust", "bubble", "moans", "crime","hit2"
    );
    SliderSetting volume = new SliderSetting("Громкость", 45.0f, 5.0f, 150.0f, 5.0f);

    public HitSound() {
        addSettings(sound, volume);
    }

    @Subscribe
    public void onPacket(AttackEvent e) {
        if (mc.player == null || mc.world == null) return;
        playSound(e.entity);
    }

    public void playSound(Entity e) {
        try {
            Clip clip = AudioSystem.getClip();
            InputStream is = mc.getResourceManager().getResource(new ResourceLocation("Wiksi/sounds/" + sound.get() + ".wav")).getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bis);
            if (audioInputStream == null) {
                System.out.println("Sound not found!");
                return;
            }
            clip.open(audioInputStream);
            clip.start();

            FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (e != null) {
                FloatControl balance = (FloatControl) clip.getControl(FloatControl.Type.BALANCE);
                Vector3d vec = e.getPositionVec().subtract(Minecraft.getInstance().player.getPositionVec());


                double yaw = wrapDegrees(toDegrees(atan2(vec.z, vec.x)) - 90);
                double delta = wrapDegrees(yaw - mc.player.rotationYaw);

                if (abs(delta) > 180) delta -= signum(delta) * 360;
                try {
                    balance.setValue((float) delta / 180);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            floatControl.setValue(-(mc.player.getDistance(e) * 5) - (volume.max / volume.get()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
