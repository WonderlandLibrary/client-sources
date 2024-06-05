// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.visuals;

import net.minecraft.util.StringUtils;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Animations", renderName = "Animations", category = ModuleCategory.VISUALS)
public final class AnimationsModule extends Module
{
    public final EnumProperty<Mode> modeEnumProperty;
    public final NumberProperty<Integer> swingSpeed;
    public final NumberProperty<Integer> yOffset;
    public final NumberProperty<Float> itemSize;
    private EventListener<EventUpdate> eventUpdateEventListener;
    
    public AnimationsModule() {
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.EXHIBITION_OLD);
        this.swingSpeed = new NumberProperty<Integer>("Speed", 6, 1, 15, 1);
        this.yOffset = new NumberProperty<Integer>("YOffset", 0, 0, 20, 1);
        this.itemSize = new NumberProperty<Float>("Size", 1.0f, 0.1f, 2.0f, 0.1f);
        this.eventUpdateEventListener = (event -> this.setSuffix((this.modeEnumProperty.getValue() == Mode.EXHIBITION_OLD) ? "Exhibition Old" : StringUtils.upperSnakeCaseToPascal(this.modeEnumProperty.getValueAsString())));
    }
    
    public enum Mode
    {
        SMOKE, 
        VIRTUE, 
        EXHIBITION, 
        EXHIBITION_OLD;
    }
}
