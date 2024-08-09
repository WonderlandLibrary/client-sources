package xyz.mathax.mathaxclient.gui.screens.modules.blockesp;

import net.minecraft.block.Block;
import xyz.mathax.mathaxclient.gui.WindowScreen;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.render.blockesp.BlockESPBlockData;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;

public class BlockESPBlockDataScreen extends WindowScreen {
    private final BlockESPBlockData blockData;
    private final Block block;

    private final BlockDataSetting<BlockESPBlockData> setting;

    public BlockESPBlockDataScreen(Theme theme, BlockESPBlockData blockData, Block block, BlockDataSetting<BlockESPBlockData> setting) {
        super(theme, "Configure Block");

        this.blockData = blockData;
        this.block = block;
        this.setting = setting;
    }

    @Override
    public void initWidgets() {
        Settings settings = new Settings();
        
        SettingGroup generalSettings = settings.createGroup("General");
        SettingGroup tracerSettings = settings.createGroup("Tracer");

        // General

        generalSettings.add(new EnumSetting.Builder<ShapeMode>()
                .name("Shape mode")
                .description("How the shape is rendered.")
                .defaultValue(ShapeMode.Lines)
                .onModuleEnabled(value -> value.set(blockData.shapeMode))
                .onChanged(value -> {
                    blockData.shapeMode = value;

                    changed(blockData, block, setting);
                })
                .build()
        );

        generalSettings.add(new ColorSetting.Builder()
                .name("Line color")
                .description("Color of lines.")
                .defaultValue(new SettingColor(0, 255, 200))
                .onModuleEnabled(value -> value.set(blockData.lineColor))
                .onChanged(value -> {
                    blockData.lineColor.set(value);

                    changed(blockData, block, setting);
                })
                .build()
        );

        generalSettings.add(new ColorSetting.Builder()
                .name("Side color")
                .description("Color of sides.")
                .defaultValue(new SettingColor(0, 255, 200, 25))
                .onModuleEnabled(value -> value.set(blockData.sideColor))
                .onChanged(value -> {
                    blockData.sideColor.set(value);

                    changed(blockData, block, setting);
                })
                .build()
        );

        // Tracer

        tracerSettings.add(new BoolSetting.Builder()
                .name("Tracer")
                .description("If tracer line is allowed to this block.")
                .defaultValue(true)
                .onModuleEnabled(value -> value.set(blockData.tracer))
                .onChanged(value -> {
                    blockData.tracer = value;

                    changed(blockData, block, setting);
                })
                .build()
        );

        tracerSettings.add(new ColorSetting.Builder()
                .name("Tracer color")
                .description("Color of tracer line.")
                .defaultValue(new SettingColor(0, 255, 200, 125))
                .onModuleEnabled(value -> value.set(blockData.tracerColor))
                .onChanged(value -> {
                    blockData.tracerColor = value;

                    changed(blockData, block, setting);
                })
                .build()
        );

        settings.onEnabled();
        add(theme.settings(settings)).expandX();
    }

    private void changed(BlockESPBlockData blockData, Block block, BlockDataSetting<BlockESPBlockData> setting) {
        if (!blockData.isChanged() && block != null && setting != null) {
            setting.get().put(block, blockData);
            setting.onChanged();
        }

        blockData.changed();
    }
}
