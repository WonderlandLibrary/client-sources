package wtf.shiyeno.modules.impl.combat;

import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.network.play.client.CUseEntityPacket.Action;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SEntityStatusPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.events.impl.player.EventWorldChange;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.modules.settings.imp.MultiBoxSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;

@FunctionAnnotation(
        name = "HitSound",
        type = Type.Combat
)
public class HitSound extends Function {
    private final ModeSetting sound = new ModeSetting("Звук", "bell", new String[]{"bell", "metallic", "rust", "bubble", "bonk", "crime"});
    private final MultiBoxSetting triggers = new MultiBoxSetting("Тригеры", new BooleanOption[]{new BooleanOption("Удар", true), new BooleanOption("Выстрел", true)});
    SliderSetting volume = new SliderSetting("Громкость", 35.0F, 5.0F, 100.0F, 5.0F);
    Map<Entity, Long> targets = new HashMap();

    public HitSound() {
        this.addSettings(new Setting[]{this.sound, this.triggers, this.volume});
    }

    public void onEvent(Event event) {
        if (mc.player != null && mc.world != null) {
            if (event instanceof EventWorldChange) {
                this.targets.clear();
            }

            if (event instanceof EventPacket) {
                EventPacket e = (EventPacket)event;
                IPacket var4;
                if (this.triggers.get(1)) {
                    var4 = e.getPacket();
                    if (var4 instanceof SChangeGameStatePacket) {
                        SChangeGameStatePacket p = (SChangeGameStatePacket)var4;
                        if (p.func_241776_b_() == SChangeGameStatePacket.field_241770_g_) {
                            this.playSound((Entity)null);
                        }
                    }
                }

                if (!this.triggers.get(0)) {
                    return;
                }

                var4 = e.getPacket();
                if (var4 instanceof CUseEntityPacket) {
                    CUseEntityPacket p = (CUseEntityPacket)var4;
                    if (p.getAction() == Action.ATTACK) {
                        this.targets.put(p.getEntityFromWorld(mc.world), System.currentTimeMillis());
                    }
                }

                if (this.targets.isEmpty()) {
                    return;
                }

                var4 = e.getPacket();
                if (var4 instanceof SEntityStatusPacket) {
                    SEntityStatusPacket p = (SEntityStatusPacket)var4;
                    this.targets.forEach((entity, time) -> {
                        if (entity != null && entity.getEntityId() == p.entityId && time + 500L >= System.currentTimeMillis()) {
                            this.playSound(entity);
                        }

                    });
                }
            }

        }
    }

    public void playSound(Entity e) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(mc.getResourceManager().getResource(new ResourceLocation("shiyeno/sounds/" + this.sound.get() + ".wav")).getInputStream());
            if (audioInputStream == null) {
                System.out.println("Sound not found!");
                return;
            }

            clip.open(audioInputStream);
            clip.start();
            FloatControl floatControl = (FloatControl)clip.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
            if (e != null) {
                FloatControl balance = (FloatControl)clip.getControl(javax.sound.sampled.FloatControl.Type.BALANCE);
                Vector3d vec = e.getPositionVec().subtract(Minecraft.getInstance().player.getPositionVec());
                double yaw = MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec.z, vec.x)) - 90.0);
                double delta = MathHelper.wrapDegrees(yaw - (double)mc.player.rotationYaw);
                if (Math.abs(delta) > 180.0) {
                    delta -= Math.signum(delta) * 360.0;
                }

                try {
                    balance.setValue((float)delta / 180.0F);
                } catch (Exception var12) {
                    Exception ex = var12;
                    ex.printStackTrace();
                }
            }

            floatControl.setValue(-(mc.player.getDistance(e) * 5.0F) - this.volume.getMax() / this.volume.getValue().floatValue());
        } catch (Exception var13) {
        }
    }

    protected void onEnable() {
        super.onEnable();
        this.targets.clear();
    }

    protected void onDisable() {
        super.onDisable();
        this.targets.clear();
    }
}