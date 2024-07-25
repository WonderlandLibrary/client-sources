package club.bluezenith.util.render.scrollable;

public interface ScrollableElement {
    void render(int mouseX, int mouseY, float partialTicks, boolean isHoveredOver);
    void mouseClicked(int mouseX, int mouseY, int mouseButton);
    void setPosition(float x, float y);
    void setDistanceBetweenNext(float height);
    boolean isSelected();
    float getDrawnElementHeight();
    float getDrawnElementWidth();
}
