package club.bluezenith.module.modules.render.hud.arraylist;

import static java.util.Arrays.stream;

public enum Alignment {
    TOP_LEFT("top_left", false, true),
    BOTTOM_LEFT("bottom_left", true, true),
    TOP_RIGHT("top_right", false, false),
    BOTTOM_RIGHT("bottom_right", true, false);

    private final boolean isBottom, isLeft;
    private final String name;

    Alignment(String name, boolean isBottom, boolean isLeft) {
        this.name = name;
        this.isBottom = isBottom;
        this.isLeft = isLeft;
    }

    public static Alignment fromName(String name) {
        return stream(Alignment.values())
                .filter(alignment -> alignment.name.equals(name))
                .findFirst()
                .orElse(Alignment.TOP_LEFT);
    }

    public String getName() {
        return this.name;
    }

    public boolean isBottom() {
        return this.isBottom;
    }

    public boolean isTop() {
        return !this.isBottom;
    }

    public boolean isLeft() {
        return this.isLeft;
    }

    public boolean isRight() {
        return !this.isLeft;
    }
}
