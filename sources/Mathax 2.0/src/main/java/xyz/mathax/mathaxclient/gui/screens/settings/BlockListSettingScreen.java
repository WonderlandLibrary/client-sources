package xyz.mathax.mathaxclient.gui.screens.settings;

import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.widgets.WWidget;
import xyz.mathax.mathaxclient.mixin.IdentifierAccessor;
import xyz.mathax.mathaxclient.settings.BlockListSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.utils.misc.Names;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Predicate;

public class BlockListSettingScreen extends LeftRightListSettingScreen<Block> {
    private static final Identifier ID = new Identifier("minecraft", "");

    public BlockListSettingScreen(Theme theme, Setting<List<Block>> setting) {
        super(theme, "Select Blocks", setting, setting.get(), Registries.BLOCK);
    }

    @Override
    protected boolean includeValue(Block value) {
        Predicate<Block> filter = ((BlockListSetting) setting).filter;
        if (filter == null) {
            return value != Blocks.AIR;
        }

        return filter.test(value);
    }

    @Override
    protected WWidget getValueWidget(Block value) {
        return theme.itemWithLabel(value.asItem().getDefaultStack(), getValueName(value));
    }

    @Override
    protected String getValueName(Block value) {
        return Names.get(value);
    }

    @Override
    protected boolean skipValue(Block value) {
        return Registries.BLOCK.getId(value).getPath().endsWith("_wall_banner");
    }

    @Override
    protected Block getAdditionalValue(Block value) {
        String path = Registries.BLOCK.getId(value).getPath();
        if (!path.endsWith("_banner")) {
            return null;
        }

        ((IdentifierAccessor) ID).setPath(path.substring(0, path.length() - 6) + "wall_banner");
        return Registries.BLOCK.get(ID);
    }
}
