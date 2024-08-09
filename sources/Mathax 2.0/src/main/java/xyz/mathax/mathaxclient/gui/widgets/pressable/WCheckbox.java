package xyz.mathax.mathaxclient.gui.widgets.pressable;

public abstract class WCheckbox extends WPressable {
    public boolean checked;

    public WCheckbox(boolean checked) {
        this.checked = checked;
    }

    @Override
    protected void onCalculateSize() {
        double pad = pad();
        double textHeight = theme.textHeight();
        width = pad + textHeight + pad;
        height = pad + textHeight + pad;
    }

    @Override
    protected void onPressed(int button) {
        checked = !checked;
    }
}
