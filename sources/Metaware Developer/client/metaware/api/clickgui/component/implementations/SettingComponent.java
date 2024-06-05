package client.metaware.api.clickgui.component.implementations;


import client.metaware.api.clickgui.component.Component;
import client.metaware.api.properties.property.Property;

public abstract class SettingComponent<Type extends Property> extends Component {

    protected Type setting;

    public SettingComponent(Type setting, float x, float y, float width, float height) {
        this(setting, x, y, width, height, true);
    }

    public SettingComponent(Type setting, float x, float y, float width, float height, boolean visible) {
        super(x, y, width, height, visible);
        this.setting = setting;
    }

    public Type setting() {
        return setting;
    }
    public void setting(Type setting) {
        this.setting = setting;
    }
}
