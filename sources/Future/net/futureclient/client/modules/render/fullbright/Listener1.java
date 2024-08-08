package net.futureclient.client.modules.render.fullbright;

import net.futureclient.client.events.Event;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.futureclient.client.tA;
import net.futureclient.client.Ib;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.render.Fullbright;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final Fullbright k;
    
    public Listener1(final Fullbright k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        switch (Ib.k[((tA.Oc)Fullbright.M(this.k).M()).ordinal()]) {
            case 1: {
                Listener1 listener1 = null;
                Label_0169: {
                    if (Fullbright.M(this.k).M()) {
                        if (Fullbright.getMinecraft5().world.getLight(new BlockPos(Fullbright.getMinecraft1().player.posX, Fullbright.getMinecraft10().player.posY, Fullbright.getMinecraft7().player.posZ)) < 12.0f || !Fullbright.getMinecraft6().world.isDaytime()) {
                            if (Fullbright.M(this.k).e(300L)) {
                                listener1 = this;
                                this.k.M(true);
                                break Label_0169;
                            }
                        }
                        else {
                            this.k.M(false);
                        }
                    }
                    listener1 = this;
                }
                if (listener1.k.M()) {
                    if (Fullbright.getMinecraft2().gameSettings.gammaSetting < 11.0f) {
                        final GameSettings gameSettings = Fullbright.getMinecraft4().gameSettings;
                        gameSettings.gammaSetting += 0.5f;
                    }
                    if (Fullbright.getMinecraft8().gameSettings.gammaSetting > 11.0f) {
                        Fullbright.getMinecraft14().gameSettings.gammaSetting = 11.0f;
                    }
                }
                else if (Fullbright.getMinecraft11().gameSettings.gammaSetting > 1.0f) {
                    final GameSettings gameSettings2 = Fullbright.getMinecraft9().gameSettings;
                    gameSettings2.gammaSetting -= 0.5f;
                }
                if (Fullbright.getMinecraft().gameSettings.gammaSetting == 1.0f && !this.k.M() && !Fullbright.M(this.k).M()) {
                    Fullbright.M(this.k);
                }
                Fullbright.getMinecraft12().player.removePotionEffect(MobEffects.NIGHT_VISION);
            }
            case 2:
                if (this.k.M()) {
                    Fullbright.getMinecraft13().gameSettings.gammaSetting = 1.0f;
                    Fullbright.getMinecraft3().player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 1215, 0));
                    return;
                }
                Fullbright.e(this.k);
                break;
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
