package alphentus.mod;

import alphentus.init.Init;
import alphentus.utils.RenderUtils;
import alphentus.utils.Translate;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/**
 * @author avox
 * @since on 29/07/2020.
 */
public class Mod {


    private final String moduleName;
    private String infoName;

    // ANIMATION
    Translate translate;

    private boolean state;
    private boolean visible;

    private int keybind;
    private final ModCategory modCategory;

    public float animationDavid;

    public Minecraft mc = Minecraft.getMinecraft();

    public Mod (String moduleName, int keybind, boolean visible, ModCategory modCategory) {
        this.moduleName = moduleName;
        this.keybind = keybind;
        this.modCategory = modCategory;
        this.visible = visible;
        this.infoName = "";
        this.translate = new Translate(0, 0);
        animationDavid = 0;
    }

    public void setKeybind (int keybind) {
        this.keybind = keybind;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setState (Boolean state) {
        if (state) {
            this.state = true;
            this.translate = new Translate(0, 0);
            this.animationDavid = 0;
            if (Minecraft.getMinecraft().thePlayer != null)
                onEnable();
        } else {
            this.state = false;
            this.translate = new Translate(0, 0);
            this.animationDavid = 0;
            if (Minecraft.getMinecraft().thePlayer != null)
                onDisable();
        }
    }

    public void onEnable () {

    }

    public void onDisable () {

    }

    public Translate getTranslate () {
        return translate;
    }

    public void setTranslate (Translate translate) {
        this.translate = translate;
    }

    public void setInfoName (String infoName) {
        this.infoName = infoName;
    }

    public String getModuleName () { return moduleName; }

    public String getInfoName () {
        return infoName;
    }

    public boolean getState () {
        return state;
    }

    public boolean isVisible () {
        return visible;
    }

    public int getKeybind () {
        return keybind;
    }

    public ModCategory getModCategory () {
        return modCategory;
    }

    public void message(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText("§b" + Init.getInstance().CLIENT_NAME + " §7> §8" + message));
    }

    public void message(double message) {
        mc.thePlayer.addChatMessage(new ChatComponentText("§b" + Init.getInstance().CLIENT_NAME + " §7> §8" + message));
    }



    public void jump() {
        if (mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
            mc.thePlayer.jump();
        }
    }

    public void systemMessage(String message) {
        // System.out.println(message);
    }
}
