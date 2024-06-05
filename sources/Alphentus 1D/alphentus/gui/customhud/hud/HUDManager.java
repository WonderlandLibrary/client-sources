package alphentus.gui.customhud.hud;

import alphentus.gui.customhud.hud.Elements.*;
import alphentus.gui.customhud.settings.settings.SetValues;
import alphentus.init.Init;

public class HUDManager {

    DrawArrayList drawArrayList = new DrawArrayList();
    DrawEffects drawEffects = new DrawEffects();
    DrawHotBar drawHotBar = new DrawHotBar();
    DrawKeystrokes drawKeystrokes = new DrawKeystrokes();
    DrawTabGUI drawTabGUI = new DrawTabGUI();
    DrawWatermark drawWatermark = new DrawWatermark();

    SetValues setValues = Init.getInstance().setValues;

    public void setRenderElements() {
        if (setValues.visibleWatermark.isState()) {
            drawWatermark.drawWatermark();
        }
        if (setValues.visibleTabGui.isState()) {
            drawTabGUI.drawTabGUI();
        }
        if (setValues.visibleArrayList.isState()) {
            drawArrayList.drawArrayList();
        }
        if (setValues.visibleKeyStrokes.isState()) {
            drawKeystrokes.drawKeystrokes();
        }
        if (setValues.visibleHotBar.isState()) {
            drawHotBar.drawHotBar();
        }
        if (setValues.visibleEffects.isState()) {
            drawEffects.drawEffects();
        }

        controlSettingsVisibility();
    }

    private void controlSettingsVisibility() {
        if (setValues.chooseFont.getCurrentMode().equals("Vanilla")) {
            setValues.fontSize.setVisible(false);
        } else {
            setValues.fontSize.setVisible(true);
        }

        if (setValues.chooseColor.getCurrentMode().equals("Custom")) {
            setValues.colorRed.setVisible(true);
            setValues.colorGreen.setVisible(true);
            setValues.colorBlue.setVisible(true);
        } else {
            setValues.colorRed.setVisible(false);
            setValues.colorGreen.setVisible(false);
            setValues.colorBlue.setVisible(false);
        }

        if (setValues.chooseColor.getCurrentMode().equals("Rainbow")) {
            setValues.rainbowOffset.setVisible(true);
            setValues.rainbowSpeed.setVisible(true);
        } else {
            setValues.rainbowOffset.setVisible(false);
            setValues.rainbowSpeed.setVisible(false);
        }

        if (setValues.chooseRect.getCurrentMode().equals("None")) {
            setValues.rectThickness.setVisible(false);
        } else {
            setValues.rectThickness.setVisible(true);
        }

        if (setValues.background.isState()) {
            setValues.backgroundAlpha.setVisible(true);
            setValues.backgroundColor.setVisible(true);
        } else {
            setValues.backgroundAlpha.setVisible(false);
            setValues.backgroundColor.setVisible(false);
        }
    }

}