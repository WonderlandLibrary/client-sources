/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import mpp.venusfr.events.AttackEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name="HitSound", type=Category.Misc)
public class HitSound
extends Function {
    private final ModeSetting sound = new ModeSetting("\u0417\u0432\u0443\u043a", "bell", "bell", "metallic", "rust", "bubble", "bonk", "crime");
    SliderSetting volume = new SliderSetting("\u0413\u0440\u043e\u043c\u043a\u043e\u0441\u0442\u044c", 35.0f, 5.0f, 100.0f, 5.0f);

    public HitSound() {
        this.addSettings(this.sound, this.volume);
    }

    @Subscribe
    public void onPacket(AttackEvent attackEvent) {
        if (HitSound.mc.player == null || HitSound.mc.world == null) {
            return;
        }
        this.playSound(attackEvent.entity);
    }

    public void playSound(Entity entity2) {
        try {
            Clip clip = AudioSystem.getClip();
            InputStream inputStream = mc.getResourceManager().getResource(new ResourceLocation("venusfr/sounds/" + (String)this.sound.get() + ".wav")).getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            if (audioInputStream == null) {
                System.out.println("Sound not found!");
                return;
            }
            clip.open(audioInputStream);
            clip.start();
            FloatControl floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (entity2 != null) {
                FloatControl floatControl2 = (FloatControl)clip.getControl(FloatControl.Type.BALANCE);
                Vector3d vector3d = entity2.getPositionVec().subtract(Minecraft.getInstance().player.getPositionVec());
                double d = MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vector3d.z, vector3d.x)) - 90.0);
                double d2 = MathHelper.wrapDegrees(d - (double)HitSound.mc.player.rotationYaw);
                if (Math.abs(d2) > 180.0) {
                    d2 -= Math.signum(d2) * 360.0;
                }
                try {
                    floatControl2.setValue((float)d2 / 180.0f);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            floatControl.setValue(-(HitSound.mc.player.getDistance(entity2) * 5.0f) - this.volume.max / ((Float)this.volume.get()).floatValue());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

