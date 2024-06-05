package alphentus.mod.mods.hud;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.KeyStrokesUtil;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 04/08/2020.
 */
public class KeyStrokes extends Mod {

    public Setting blur = new Setting("Blur", true, this);
    public Setting animation = new Setting("Animation", true, this);

    private final KeyStrokesUtil keyW = new KeyStrokesUtil(25, 29, Keyboard.getKeyIndex("W"));
    private final KeyStrokesUtil keyA = new KeyStrokesUtil(3, 50, Keyboard.getKeyIndex("A"));
    private final KeyStrokesUtil keyS = new KeyStrokesUtil(25, 50, Keyboard.getKeyIndex("S"));
    private final KeyStrokesUtil keyD = new KeyStrokesUtil(47, 50, Keyboard.getKeyIndex("D"));

    public KeyStrokes () {
        super("KeyStrokes", Keyboard.KEY_NONE, false, ModCategory.HUD);
        Init.getInstance().settingManager.addSetting(blur);
        Init.getInstance().settingManager.addSetting(animation);
    }



    @EventTarget
    public void event (Event event) {

        if(event.getType() == Type.RENDER2D){
            if (Init.getInstance().modManager.getModuleByClass(HUD.class).isCustom()) {
                blur.setVisible(true);
                animation.setVisible(true);
            } else {
                blur.setVisible(false);
                animation.setVisible(false);
            }
        }

        if (!getState())
            return;

        if (event.getType() != Type.RENDER2D)
            return;


        if (Init.getInstance().modManager.getModuleByClass(TabGUI.class).getState()) {
            keyW.setY(105);
            keyS.setY(125);
            keyA.setY(125);
            keyD.setY(125);
        }else{
            keyW.setY(29);
            keyA.setY(50);
            keyS.setY(50);
            keyD.setY(50);
        }

        keyW.draw();
        keyS.draw();
        keyA.draw();
        keyD.draw();

    }

}
