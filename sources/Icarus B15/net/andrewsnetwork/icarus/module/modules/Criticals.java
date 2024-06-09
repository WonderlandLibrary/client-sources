// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.andrewsnetwork.icarus.event.events.SentPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.andrewsnetwork.icarus.event.events.Attacking;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.utilities.BlockHelper;
import net.minecraft.potion.Potion;
import net.minecraft.block.material.Material;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.module.Module;

public class Criticals extends Module
{
    private final Value<Boolean> falldamage;
    private final TimeHelper time;
    int attacked;
    int state;
    private double fallDist;
    private boolean cancelSomePackets;
    
    public Criticals() {
        super("Criticals", -7720156, Category.COMBAT);
        this.falldamage = new Value<Boolean>("criticals_Fall Damage", "falldamage", false, this);
        this.time = new TimeHelper();
        this.attacked = 0;
        this.state = 0;
    }
    
    public boolean isSafer() {
        return !Criticals.mc.thePlayer.isInWater() && !Criticals.mc.thePlayer.isInsideOfMaterial(Material.lava) && !Criticals.mc.thePlayer.isOnLadder() && !Criticals.mc.thePlayer.isPotionActive(Potion.blindness) && Criticals.mc.thePlayer.ridingEntity == null;
    }
    
    public boolean isSafe() {
        return !Criticals.mc.thePlayer.isInWater() && Criticals.mc.thePlayer.onGround && !Criticals.mc.thePlayer.isOnLadder() && !BlockHelper.isOnLiquid();
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
        if (e instanceof Attacking) {
            if (this.falldamage.getValue()) {
                return;
            }
            final Attacking event = (Attacking)e;
            if (event.getEntity() instanceof EntityLivingBase && this.isSafe()) {
                final EntityLivingBase entity = (EntityLivingBase)event.getEntity();
                if (entity.hurtTime < 5) {
                    Criticals.mc.thePlayer.onCriticalHit(entity);
                    Criticals.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + 0.05, Criticals.mc.thePlayer.posZ, false));
                    Criticals.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, false));
                    Criticals.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + 0.012511, Criticals.mc.thePlayer.posZ, false));
                    Criticals.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, false));
                }
            }
        }
        else if (e instanceof SentPacket && this.falldamage.getValue()) {
            final SentPacket event2 = (SentPacket)e;
            if (event2.getPacket() instanceof C03PacketPlayer) {
                final C03PacketPlayer player = (C03PacketPlayer)event2.getPacket();
                if (this.isSafer()) {
                    this.fallDist += Criticals.mc.thePlayer.fallDistance;
                }
                if (!this.isSafe() || this.fallDist >= 3.0) {
                    player.field_149474_g = true;
                    this.fallDist = 0.0;
                    Criticals.mc.thePlayer.fallDistance = 0.0f;
                    if (Criticals.mc.thePlayer.onGround && !BlockHelper.isOnLiquid() && !BlockHelper.isInLiquid()) {
                        Criticals.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + 1.01, Criticals.mc.thePlayer.posZ, false));
                        Criticals.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, false));
                        this.fallDist += 1.0099999904632568;
                    }
                }
                else {
                    player.field_149474_g = false;
                }
            }
        }
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        this.fallDist = 0.0;
    }
}
