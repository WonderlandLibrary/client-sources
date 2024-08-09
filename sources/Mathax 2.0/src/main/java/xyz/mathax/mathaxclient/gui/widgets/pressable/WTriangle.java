package xyz.mathax.mathaxclient.gui.widgets.pressable;

public abstract class WTriangle extends WPressable {
    public double rotation;

    @Override
    protected void onCalculateSize() {
        double textHeight = theme.textHeight();
        width = textHeight;
        height = textHeight;
    }
}
