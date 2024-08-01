package wtf.diablo.client.module.impl.render;

import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.NumberSetting;

@ModuleMetaData(name = "Item Offset", description = "Offset your held item", category = ModuleCategoryEnum.RENDER)
public final class ItemOffsetModule extends AbstractModule {
    public final NumberSetting<Double> xOffset = new NumberSetting<>("X Offset", 0D, -3D, 3D, 0.05D);
    public final NumberSetting<Double> yOffset = new NumberSetting<>("Y Offset", 0D, -3D, 3D, 0.05D);
    public final NumberSetting<Double> zOffset = new NumberSetting<>("Z Offset", 0D, -3D, 3D, 0.05D);
    public final NumberSetting<Double> scale = new NumberSetting<>("Scale", 1D, 0.1D, 3D, 0.05D);


    public ItemOffsetModule() {
        this.registerSettings(xOffset, yOffset, zOffset, scale);
    }

    public double getXOffset() {
        return xOffset.getValue();
    }

    public double getYOffset() {
        return yOffset.getValue();
    }

    public double getZOffset() {
        return zOffset.getValue();
    }

    public double getScale() {
        return scale.getValue();
    }
}
