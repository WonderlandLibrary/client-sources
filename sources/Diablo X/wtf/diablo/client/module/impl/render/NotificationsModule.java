package wtf.diablo.client.module.impl.render;

import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.BooleanSetting;

@ModuleMetaData(
        name = "Notifications",
        description = "Shows notifications",
        category = ModuleCategoryEnum.RENDER
)
public final class NotificationsModule extends AbstractModule
{
    private final BooleanSetting toggleNotifications = new BooleanSetting("Toggle Notifications", false);

    public NotificationsModule() {
        this.registerSettings(this.toggleNotifications);
    }

    public BooleanSetting getToggleNotifications() {
        return this.toggleNotifications;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.toggle(false);
    }
}