package in.momin5.cookieclient.client.modules.misc;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import net.minecraft.client.gui.GuiMainMenu;

public class AutoDisconnect extends Module {

    public SettingNumber auhealth = register(new SettingNumber("Health",this,12,0,20,1));
    private double audhealth;

    public AutoDisconnect(){
        super("AutoDisconnect", Category.MISC);
    }

    public void onUpdate() {
        if(mc.player.getHealth() < audhealth) {
            this.toggle();
            mc.world.sendQuittingDisconnectingPacket();
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    @Override
    public void onEnable() {
        audhealth = auhealth.getValue();
    }

    public void onDisable(){
    }
}
