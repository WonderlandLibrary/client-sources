package mathax.client.gui.widgets;

public abstract class WHorizontalSeparator extends WWidget {
    protected String text;
    protected double textWidth;

    public WHorizontalSeparator(String text) {
        this.text = text;
    }

    @Override
    protected void onCalculateSize() {
        if (text != null) textWidth = theme.textWidth(text);

        width = 1;
        height = text != null ? theme.textHeight() : theme.scale(3);
    }
}
