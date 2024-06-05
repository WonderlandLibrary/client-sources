package alphentus.gui.clickguisigma.settings;

import alphentus.gui.clickguisigma.ModPanels;
import alphentus.settings.Setting;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class Element {

    public ModPanels modPanel;
    public Setting setting;
    public float x;
    public float y;
    public float width;
    public float height;

    public boolean comboextended;

    public void drawScreen(int mouseX, int mouseY) {
    }

    public void setInformations(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
    }

    public boolean isSettingVisible() {
        return setting.isVisible();
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }


}
