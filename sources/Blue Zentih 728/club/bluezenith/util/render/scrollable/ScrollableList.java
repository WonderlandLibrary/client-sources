package club.bluezenith.util.render.scrollable;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface ScrollableList<T extends ScrollableElement> {
    void setContents(List<T> contents);
    void render(int mouseX, int mouseY, float partialTicks, boolean updateScroll);
    void mouseClicked(int mouseX, int mouseY, int mouseButton);
    void setDistanceBetweenElements(float distance);
    void setPosition(float x, float y);
    void setBounds(float width, float height);
    default void onGuiInit() {}
    default void setItemVisibilityPredicate(Predicate<T> predicate) {}
    default void sortContents(Comparator<T> comparator) {}
    default void keyTyped(int code, char key) {}
}
