// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.potion.PotionEffect;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.module.Module;

public class Zoot extends Module
{
    private final Value<Boolean> fire;
    private final Value<Boolean> potion;
    
    public Zoot() {
        super("Zoot", -18751, Category.COMBAT);
        this.fire = new Value<Boolean>("zoot_Anti Fire", "fire", false, this);
        this.potion = new Value<Boolean>("zoot_Anti Potion", "potion", true, this);
    }
    
    @Override
    public void onEvent(final Event e) {
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (e instanceof PreMotion) {
            if (this.potion.getValue()) {
                Potion[] potionTypes;
                for (int length = (potionTypes = Potion.potionTypes).length, j = 0; j < length; ++j) {
                    final Potion potion = potionTypes[j];
                    if (potion != null) {
                        if (potion.isBadEffect()) {
                            final PotionEffect effect = Zoot.mc.thePlayer.getActivePotionEffect(potion);
                            if (effect != null) {
                                if (effect.getDuration() < 10000 && Zoot.mc.thePlayer.onGround) {
                                    for (int i = 0; i < effect.getDuration() / 20; ++i) {
                                        Zoot.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(Zoot.mc.thePlayer.onGround));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (this.fire.getValue() && Zoot.mc.thePlayer.isBurning() && Zoot.mc.thePlayer.getActivePotionEffect(Potion.fireResistance) == null) {
                for (int x = 0; x < 120; ++x) {
                    Zoot.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(Zoot.mc.thePlayer.onGround));
                }
            }
        }
    }
}
