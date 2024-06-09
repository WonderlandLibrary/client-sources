package me.travis.wurstplus.gui.rgui.component;

public class AlignedComponent extends AbstractComponent {
    Alignment alignment;

    public static enum Alignment {
        LEFT(0), CENTER(1), RIGHT(2);

        int index;
        Alignment(int index) {
            this.index = index;
        }
        public int getIndex() {
            return index;
        }
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }
}
