// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.combat;

import xyz.niggfaclient.module.ModuleManager;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Criticals", description = "Makes you always get a critical hit on your opponent", cat = Category.COMBAT)
public class Criticals extends Module
{
    private final EnumProperty<Mode> mode;
    private boolean hasAttacked;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public Criticals() {
        this.mode = new EnumProperty<Mode>("Mode", Mode.Packet);
        int i;
        double[] array;
        int length;
        int j = 0;
        double positions;
        this.motionEventListener = (e -> {
            this.setDisplayName(this.getName() + " §7" + this.mode.getValue());
            if (this.shouldCrit()) {
                switch (this.mode.getValue()) {
                    case Packet: {
                        for (i = 0; i < 8; ++i) {
                            array = new double[] { 0.006253453, 0.002253453, 0.001253453 };
                            for (length = array.length; j < length; ++j) {
                                positions = array[j];
                                PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + positions, this.mc.thePlayer.posZ, false));
                            }
                        }
                        break;
                    }
                }
            }
            return;
        });
        C02PacketUseEntity c02;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.SEND) {
                if (this.mode.getValue() == Mode.NoGround && e.getPacket() instanceof C03PacketPlayer) {
                    ((C03PacketPlayer)e.getPacket()).onGround = false;
                }
                if (e.getPacket() instanceof C02PacketUseEntity) {
                    c02 = (C02PacketUseEntity)e.getPacket();
                    if (c02 != null) {
                        this.hasAttacked = (c02.getAction().equals(C02PacketUseEntity.Action.ATTACK) && this.mc.objectMouseOver.entityHit != null);
                    }
                }
                else {
                    this.hasAttacked = false;
                }
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mode.getValue() == Mode.NoGround) {
            this.mc.thePlayer.jump();
        }
        this.hasAttacked = false;
    }
    
    private boolean shouldCrit() {
        final KillAura kaModule = ModuleManager.getModule(KillAura.class);
        return !this.mc.thePlayer.isInLiquid() && !this.mc.thePlayer.isOnLadder() && (this.hasAttacked || (kaModule.isEnabled() && kaModule.target != null)) && this.mc.thePlayer != null && this.mc.theWorld != null && !this.mc.thePlayer.isSpectator();
    }
    
    public enum Mode
    {
        Packet, 
        NoGround;
    }
}
