package xyz.mathax.mathaxclient.utils.tooltip;

import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;

public interface MatHaxTooltipData extends TooltipData {
    TooltipComponent getComponent();
}
