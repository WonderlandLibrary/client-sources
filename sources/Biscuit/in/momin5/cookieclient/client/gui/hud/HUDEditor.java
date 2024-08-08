package in.momin5.cookieclient.client.gui.hud;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import org.lwjgl.input.Keyboard;


public class HUDEditor extends Module {

    public HUDEditor(){
        super("HudEditor", Category.HUD);
    }

    public void onEnable(){
        CookieClient.clickGUI.enterHUDEditor();
    }

    public void onUpdate(){
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            this.setEnabled(false);
            CookieClient.clickGUI.enterGUI();
        }
    }

    public void onDisable(){
    }
}
