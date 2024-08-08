package in.momin5.cookieclient.client.modules.player;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.InputUpdateEvent;

public class NoSlow extends Module {

    public SettingBoolean webInnit = register( new SettingBoolean("Web", this, true));
    public SettingBoolean soulSand = register( new SettingBoolean("SoulSand", this, false));
    public SettingBoolean foodInnit = register( new SettingBoolean("Food", this, true));

    public NoSlow() {
        super("NoSlow", Category.PLAYER);

    }

    @Override
    public void onEnable() {
        if(nullCheck())
            return;
        CookieClient.EVENT_BUS.subscribe(this);
        Blocks.DIRT.setLightOpacity(10);
    }

    public void onDisable() {
        CookieClient.EVENT_BUS.subscribe(this);
    }

    @EventHandler
    private final Listener<InputUpdateEvent> updateEventListener = new Listener<>(event -> {
        if(mc.player.isHandActive() && !mc.player.isRiding() && foodInnit.isEnabled() && !mc.player.isSneaking()) {
            event.getMovementInput().moveForward *=5;
            event.getMovementInput().moveStrafe *=5;
        }
    });
}
