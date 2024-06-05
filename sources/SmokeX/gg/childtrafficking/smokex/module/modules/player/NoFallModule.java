// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import gg.childtrafficking.smokex.utils.player.MovementUtils;
import gg.childtrafficking.smokex.utils.system.StringUtils;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "NoFall", renderName = "No Fall", aliases = {}, description = "Prevents you from taking fall damage", category = ModuleCategory.PLAYER)
public final class NoFallModule extends Module
{
    private final EnumProperty<Mode> modeEnumProperty;
    private float fallDistance;
    public boolean nextTick;
    private final EventListener<EventUpdate> eventUpdateEventListener;
    
    public NoFallModule() {
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.WATCHDOG);
        this.fallDistance = 0.0f;
        this.eventUpdateEventListener = (event -> {
            this.setSuffix(StringUtils.upperSnakeCaseToPascal(this.modeEnumProperty.getValueAsString()));
            switch (this.modeEnumProperty.getValue()) {
                case WATCHDOG: {
                    if (event.isPre()) {
                        if (this.mc.thePlayer.fallDistance - this.fallDistance > 3.0f) {
                            event.setOnGround(true);
                            this.fallDistance += 3.0f;
                        }
                        if (MovementUtils.isOnGround()) {
                            this.fallDistance = 0.0f;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    private enum Mode
    {
        WATCHDOG;
    }
}
