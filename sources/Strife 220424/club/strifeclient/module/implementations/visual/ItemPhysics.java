package club.strifeclient.module.implementations.visual;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.ModeSetting;

@ModuleInfo(name = "ItemPhysics", description = "ItemPhysics mod.", category = Category.VISUAL)
public final class ItemPhysics extends Module {
    public final ModeSetting<ItemRotateMode> rotateModeSetting = new ModeSetting<>("Rotate Mode", ItemRotateMode.PHYSICS);
    public enum ItemRotateMode implements SerializableEnum {
        NONE("None"), NATURAL("Natural"), PHYSICS("Physics");
        final String name;
        ItemRotateMode(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }
}
