package in.momin5.cookieclient.client.modules.misc;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import in.momin5.cookieclient.api.util.utils.misc.Timer;
import net.minecraftforge.common.MinecraftForge;

public class AutoRespawn extends Module {

    SettingNumber delay = register(new SettingNumber("Delay",this,100,0,500,10));
    Timer timer = new Timer();
    public AutoRespawn() {
        super("AutoRespawn", Category.MISC);
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
        timer.reset();
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        timer.reset();
    }


    @Override
    public void onUpdate() {
        if(mc.player.isDead && timer.hasPassed((int) delay.value)) {
            mc.player.respawnPlayer();
            timer.reset();
        }
    }
}
