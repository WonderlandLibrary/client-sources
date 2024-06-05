/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  org.apache.commons.lang3.RandomStringUtils
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.PacketHelper;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.module.impl.combat.KillAura;
import cc.diablo.setting.impl.ModeSetting;
import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.RandomStringUtils;

public class HitSound
extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Skeet", "Skeet", "Call of Duty");

    public HitSound() {
        super("HitSound", "Plays a aids sounds on hit", 0, Category.Misc);
        this.addSettings(this.mode);
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if (KillAura.target != null && KillAura.target.hurtTime > 9) {
            this.doSound(KillAura.target);
        }
    }

    public void doSound(EntityLivingBase target) {
        double x = target.posX;
        double y = target.posY;
        double z = target.posZ;
        switch (this.mode.getMode()) {
            case "Call Of Duty": {
                ChatHelper.addChat("TECHNO DOESNT KNOW WHAT A PENIS IS");
                mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("Client/audio/hitsound/callofduty.ogg"), (float)x, (float)y, (float)z));
                break;
            }
            case "Skeet": {
                if (HitSound.mc.thePlayer.ticksExisted % 5 == 0) {
                    PacketHelper.sendPacketNoEvent(new C01PacketChatMessage("Materweal gowrl!!!! diablo <dot> wtf " + RandomStringUtils.random((int)9, (String)"abcdefghijklmnopqrstuvwxyz1234567890")));
                }
                try {
                    AudioInputStream audioInputStream = null;
                    try {
                        audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("Client/audio/hitsound/skeet.ogg"));
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    }
                    catch (UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                ChatHelper.addChat("TECHNO DOESNT KNOW WHAT A PENIS IS");
                mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("Client/audio/hitsound/skeet.ogg"), (float)x, (float)y, (float)z));
            }
        }
    }
}

