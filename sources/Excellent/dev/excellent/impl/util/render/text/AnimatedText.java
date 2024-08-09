package dev.excellent.impl.util.render.text;

import lombok.Data;

import java.util.List;

@Data
public class AnimatedText {

    private List<String> texts;
    public final StringBuilder output = new StringBuilder();
    private int delay;
    private int textIndex = 0;
    private int charIndex = 0;
    private boolean forward = true;
    private long lastUpdateTime = System.currentTimeMillis();

    public AnimatedText(List<String> texts, int delay) {
        this.texts = texts;
        this.delay = delay;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= 100) {
            lastUpdateTime = currentTime;
            if (forward) {
                if (charIndex < texts.get(textIndex).length()) {
                    output.append(texts.get(textIndex).charAt(charIndex));
                    charIndex++;
                } else {
                    forward = false;
                    lastUpdateTime = currentTime + delay;
                }
            } else {
                if (charIndex > 0) {
                    output.deleteCharAt(charIndex - 1);
                    charIndex--;
                } else {
                    forward = true;
                    textIndex = (textIndex + 1) % texts.size();
                }
            }
        }
    }
}