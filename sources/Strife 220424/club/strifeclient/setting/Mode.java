package club.strifeclient.setting;

import club.strifeclient.Client;
import club.strifeclient.util.misc.MinecraftUtil;
import lombok.Getter;

@Getter
public abstract class Mode<T extends ModeEnum<?>> extends MinecraftUtil {
    protected boolean selected;
    public void onSelect() {
        selected = true;
    }
    public void onDeselect() {
        selected = false;
    }
    public void onEnable() {
        Client.INSTANCE.getEventBus().register(this);
    }
    public void onDisable() {
        Client.INSTANCE.getEventBus().unregister(this);
    }
    public abstract T getRepresentation();
}
