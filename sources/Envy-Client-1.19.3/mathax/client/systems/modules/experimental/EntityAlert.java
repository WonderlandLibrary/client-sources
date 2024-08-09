package mathax.client.systems.modules.experimental;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import mathax.client.settings.EntityTypeListSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;

public class EntityAlert extends Module {

    public EntityAlert() {
        super(Categories.Experimental, Items.AIR, "entity-alert", "Alerts you when an entity is nearby.");
    }
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Object2BooleanMap<EntityType<?>>> entities = sgGeneral.add(new EntityTypeListSetting.Builder()
        .name("Entities")
        .description("Entities to look for")
        .build()
    );

    public void onTick() {
        assert mc.world != null;
        mc.world.getEntities().forEach(entity -> {
            if (entities.get().getBoolean(entity.getType())) {
                info("Entity Found!");
            }
        });
    }
}
