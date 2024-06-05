// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.misc;

import net.minecraft.entity.EntityLivingBase;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.combat.KillauraModule;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "AutoLogGlitch", renderName = "Auto Log Glitch", description = "Automatically log to save lives", category = ModuleCategory.MISC)
public final class AutoLogGlitchModule extends Module
{
    private final NumberProperty<Float> healthProperty;
    private final EventListener<EventReceivePacket> eventReceivePacketEventListener;
    private final EventListener<EventUpdate> eventUpdateEventListener;
    
    public AutoLogGlitchModule() {
        this.healthProperty = new NumberProperty<Float>("Health", 5.0f, 1.0f, 20.0f, 0.1f);
        this.eventReceivePacketEventListener = (event -> {
            if (event.getPacket() instanceof S2BPacketChangeGameState && ((S2BPacketChangeGameState)event.getPacket()).getGameState() == 6) {
                this.mc.getNetHandler().getNetworkManager().closeChannel(new ChatComponentText("§c[AUTOLOGGLITCH] Disconnected!"));
            }
            return;
        });
        this.eventUpdateEventListener = (event -> {
            final EntityLivingBase target = ModuleManager.getInstance(KillauraModule.class).getTarget();
            if (event.isPre() && target != null && target.hurtResistantTime > 19 && this.mc.thePlayer.getHealth() < this.healthProperty.getValue()) {
                this.mc.getNetHandler().getNetworkManager().closeChannel(new ChatComponentText("§c[AUTOLOGGLITCH] Disconnected!"));
            }
        });
    }
}
