package xyz.mathax.mathaxclient.gui.screens.settings;

import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.WindowScreen;
import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.widgets.containers.WTable;
import xyz.mathax.mathaxclient.gui.widgets.input.WTextBox;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;
import xyz.mathax.mathaxclient.settings.BlockDataSetting;
import xyz.mathax.mathaxclient.utils.settings.IBlockData;
import xyz.mathax.mathaxclient.utils.misc.IChangeable;
import xyz.mathax.mathaxclient.utils.misc.ICopyable;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.misc.Names;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class BlockDataSettingScreen extends WindowScreen {
    private static final List<Block> BLOCKS = new ArrayList<>(100);

    private final BlockDataSetting<?> setting;

    private WTable table;
    private String filterText = "";

    public BlockDataSettingScreen(Theme theme, BlockDataSetting<?> setting) {
        super(theme, "Configure Blocks");

        this.setting = setting;
    }

    @Override
    public void initWidgets() {
        WTextBox filter = add(theme.textBox("")).minWidth(400).expandX().widget();
        filter.setFocused(true);
        filter.action = () -> {
            filterText = filter.get().trim();

            table.clear();
            initTable();
        };

        table = add(theme.table()).expandX().widget();

        initTable();
    }

    public <T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> void initTable() {
        for (Block block : Registries.BLOCK) {
            T blockData = (T) setting.get().get(block);
            if (blockData != null && blockData.isChanged()) {
                BLOCKS.add(0, block);
            } else {
                BLOCKS.add(block);
            }
        }

        for (Block block : BLOCKS) {
            String name = Names.get(block);
            if (!StringUtils.containsIgnoreCase(name, filterText)) {
                continue;
            }

            T blockData = (T) setting.get().get(block);
            table.add(theme.itemWithLabel(block.asItem().getDefaultStack(), Names.get(block))).expandCellX();
            table.add(theme.label((blockData != null && blockData.isChanged()) ? "*" : " "));

            WButton edit = table.add(theme.button(GuiRenderer.EDIT)).widget();
            edit.action = () -> {
                T data = blockData;
                if (data == null) {
                    data = (T) setting.defaultData.get().copy();
                }

                mc.setScreen(data.createScreen(theme, block, (BlockDataSetting<T>) setting));
            };

            WButton reset = table.add(theme.button(GuiRenderer.RESET)).widget();
            reset.action = () -> {
                setting.get().remove(block);
                setting.onChanged();

                if (blockData != null && blockData.isChanged()) {
                    table.clear();
                    initTable();
                }
            };

            table.row();
        }

        BLOCKS.clear();
    }
}
