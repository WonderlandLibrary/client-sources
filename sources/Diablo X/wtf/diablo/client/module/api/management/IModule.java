package wtf.diablo.client.module.api.management;

import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.AbstractSetting;

import java.util.List;

public interface IModule {
    ModuleMetaData getData();
    String getName();
    String getDescription();
    boolean isEnabled();
    int getKey();
    void toggle();
    void toggle(boolean toggled);
    String getDisplayName();
    List<AbstractSetting<?>> getSettingList();
    void setKey(int key);
    AbstractSetting<?> getSettingByName(final String name);
    ModuleCategoryEnum getModuleCategoryEnum();
}