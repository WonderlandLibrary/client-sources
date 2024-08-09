package xyz.mathax.mathaxclient.systems.modules.render.blockesp;

import net.minecraft.block.Block;
import org.json.JSONObject;
import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.renderer.ShapeMode;
import xyz.mathax.mathaxclient.settings.BlockDataSetting;
import xyz.mathax.mathaxclient.gui.screens.modules.blockesp.BlockESPBlockDataScreen;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.utils.gui.IScreenFactory;
import xyz.mathax.mathaxclient.utils.misc.IChangeable;
import xyz.mathax.mathaxclient.utils.misc.ICopyable;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import xyz.mathax.mathaxclient.utils.settings.IBlockData;

public class BlockESPBlockData implements ICopyable<BlockESPBlockData>, ISerializable<BlockESPBlockData>, IChangeable, IBlockData<BlockESPBlockData>, IScreenFactory {
    public ShapeMode shapeMode;
    public SettingColor lineColor;
    public SettingColor sideColor;

    public boolean tracer;
    public SettingColor tracerColor;

    private boolean changed;

    public BlockESPBlockData(ShapeMode shapeMode, SettingColor lineColor, SettingColor sideColor, boolean tracer, SettingColor tracerColor) {
        this.shapeMode = shapeMode;
        this.lineColor = lineColor;
        this.sideColor = sideColor;

        this.tracer = tracer;
        this.tracerColor = tracerColor;
    }

    @Override
    public WidgetScreen createScreen(Theme theme, Block block, BlockDataSetting<BlockESPBlockData> setting) {
        return new BlockESPBlockDataScreen(theme, this, block, setting);
    }

    @Override
    public WidgetScreen createScreen(Theme theme) {
        return new BlockESPBlockDataScreen(theme, this, null, null);
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    public void changed() {
        changed = true;
    }

    public void tickRainbow() {
        lineColor.update();
        sideColor.update();
        tracerColor.update();
    }

    @Override
    public BlockESPBlockData set(BlockESPBlockData value) {
        shapeMode = value.shapeMode;
        lineColor.set(value.lineColor);
        sideColor.set(value.sideColor);

        tracer = value.tracer;
        tracerColor.set(value.tracerColor);

        changed = value.changed;

        return this;
    }

    @Override
    public BlockESPBlockData copy() {
        return new BlockESPBlockData(shapeMode, new SettingColor(lineColor), new SettingColor(sideColor), tracer, new SettingColor(tracerColor));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("shape-mode", shapeMode.name());
        json.put("line-color", lineColor.toJson());
        json.put("side-color", sideColor.toJson());
        json.put("tracer", tracer);
        json.put("tracer-color", tracerColor.toJson());
        json.put("changed", changed);
        return json;
    }

    @Override
    public BlockESPBlockData fromJson(JSONObject json) {
        shapeMode = ShapeMode.valueOf(json.getString("shape-mode"));
        lineColor.fromJson(json.getJSONObject("line-color"));
        sideColor.fromJson(json.getJSONObject("side-color"));

        tracer = json.has("tracer") && json.getBoolean("tracer");
        tracerColor.fromJson(json.getJSONObject("tracer-color"));

        changed = json.has("changed") && json.getBoolean("changed");

        return this;
    }
}