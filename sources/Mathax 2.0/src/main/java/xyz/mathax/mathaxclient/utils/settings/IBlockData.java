package xyz.mathax.mathaxclient.utils.settings;

import xyz.mathax.mathaxclient.settings.BlockDataSetting;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.utils.misc.IChangeable;
import xyz.mathax.mathaxclient.utils.misc.ICopyable;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import net.minecraft.block.Block;

public interface IBlockData<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> {
    WidgetScreen createScreen(Theme theme, Block block, BlockDataSetting<T> setting);
}
