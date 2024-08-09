package xyz.mathax.mathaxclient.gui.widgets;

import java.util.ArrayList;
import java.util.List;

public abstract class WMultiLabel extends WLabel {
    protected List<String> lines = new ArrayList<>(2);

    protected double maxWidth;

    public WMultiLabel(String text, boolean title, double maxWidth) {
        super(text, title);

        this.maxWidth = maxWidth;
    }

    @Override
    protected void onCalculateSize() {
        lines.clear();

        String[] words = text.split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        double spaceWidth = theme.textWidth(" ", 1, title, false);
        double maxWidth = theme.scale(this.maxWidth);

        double lineWidth = 0;
        double maxLineWidth = 0;

        int iInLine = 0;

        for (int i = 0; i < words.length; i++) {
            double wordWidth = theme.textWidth(words[i], words[i].length(), title, false);

            double toAdd = wordWidth;
            if (iInLine > 0) {
                toAdd += spaceWidth;
            }

            if (lineWidth + toAdd > maxWidth) {
                lines.add(stringBuilder.toString());
                stringBuilder.setLength(0);

                lineWidth = 0;
                iInLine = 0;

                i--;
            } else {
                if (iInLine > 0) {
                    stringBuilder.append(' ');
                    lineWidth += spaceWidth;
                }

                stringBuilder.append(words[i]);
                lineWidth += wordWidth;

                maxLineWidth = Math.max(maxLineWidth, lineWidth);
                iInLine++;
            }
        }

        if (stringBuilder.length() > 0) {
            lines.add(stringBuilder.toString());
        }

        width = maxLineWidth;
        height = theme.textHeight(title) * lines.size();
    }

    @Override
    public void set(String text) {
        if (!text.equals(this.text)) {
            invalidate();
        }

        this.text = text;
    }
}
