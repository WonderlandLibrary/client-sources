package mathax.client.settings;

import mathax.client.utils.misc.IChangeable;
import mathax.client.utils.misc.ICopyable;
import mathax.client.utils.misc.ISerializable;
import mathax.client.gui.GuiTheme;
import mathax.client.gui.WidgetScreen;
import net.minecraft.block.Block;

public interface IBlockData<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> {
    WidgetScreen createScreen(GuiTheme theme, Block block, BlockDataSetting<T> setting);
}
