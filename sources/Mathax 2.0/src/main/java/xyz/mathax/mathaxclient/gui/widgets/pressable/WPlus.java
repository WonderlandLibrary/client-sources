package xyz.mathax.mathaxclient.gui.widgets.pressable;

public abstract class WPlus extends WPressable {
    @Override
    protected void onCalculateSize() {
        double pad = pad();
        double textHeight = theme.textHeight();
        width = pad + textHeight + pad;
        height = pad + textHeight + pad;
    }
}
