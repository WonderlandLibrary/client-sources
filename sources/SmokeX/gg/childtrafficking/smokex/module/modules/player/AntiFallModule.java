// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import gg.childtrafficking.smokex.utils.system.StringUtils;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "AntiFall", renderName = "Anti Fall", aliases = {}, description = "Prevents you from falling into the void", category = ModuleCategory.PLAYER)
public final class AntiFallModule extends Module
{
    private final EnumProperty<Mode> modeEnumProperty;
    private final NumberProperty<Float> distance;
    private boolean falling;
    private EventListener<EventUpdate> eventUpdateEventListener;
    
    public AntiFallModule() {
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.WATCHDOG);
        this.distance = new NumberProperty<Float>("Distance", 5.0f, 1.0f, 10.0f, 0.1f);
        this.falling = false;
        this.eventUpdateEventListener = (event -> {
            this.setSuffix(StringUtils.upperSnakeCaseToPascal(this.modeEnumProperty.getValueAsString()));
            switch (this.modeEnumProperty.getValue()) {
                case WATCHDOG: {
                    if (this.mc.thePlayer.fallDistance > this.distance.getValue() && !PlayerUtils.isBlockUnder()) {
                        if (!this.falling) {
                            this.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + 5.0 + 0.004 * Math.random(), this.mc.thePlayer.posY, this.mc.thePlayer.posZ + 5.0 + 0.004 * Math.random(), false));
                        }
                        this.mc.thePlayer.motionX = 0.0;
                        this.mc.thePlayer.motionY = 0.0;
                        this.mc.thePlayer.motionZ = 0.0;
                        this.falling = true;
                        break;
                    }
                    else {
                        this.falling = false;
                        break;
                    }
                    break;
                }
            }
        });
    }
    
    private enum Mode
    {
        WATCHDOG;
    }
}
