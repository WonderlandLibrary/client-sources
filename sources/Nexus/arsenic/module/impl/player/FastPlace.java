package arsenic.module.impl.player;

import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.rangeproperty.RangeProperty;
import arsenic.module.property.impl.rangeproperty.RangeValue;
import net.minecraft.item.ItemBlock;

@ModuleInfo(name = "Fastplace", category = ModuleCategory.Player)
public class FastPlace extends Module {

    public final RangeProperty ticks = new RangeProperty("Tick Delay", new RangeValue(0, 4, 0, 4,1));
    public final BooleanProperty blocksOnly = new BooleanProperty("Blocks Only", true);

    public int getTickDelay() {
        if(!blocksOnly.getValue())
            return (int) ticks.getValue().getRandomInRange();
        if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)
            return (int) ticks.getValue().getRandomInRange();
        return 4;
    }

}
