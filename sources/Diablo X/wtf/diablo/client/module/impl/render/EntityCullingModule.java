package wtf.diablo.client.module.impl.render;

import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.NumberSetting;

@ModuleMetaData(name = "Entity Culling", description = "Prevents rendering entities at a certain distance", category = ModuleCategoryEnum.RENDER)
public final class EntityCullingModule extends AbstractModule {
    private final NumberSetting<Integer> distance = new NumberSetting<>("Distance", 32, 1, 256, 1);

    public EntityCullingModule() {
        this.registerSettings(distance);
    }

    public int getDistance() {
        return distance.getValue();
    }
}
