package arsenic.utils.io;

import java.util.Arrays;

public enum MouseButton {
    LEFT(0), MIDDLE(2), RIGHT(1), INVALID(-1);

    private final int code;

    MouseButton(int code) {
        this.code = code;
    }

    public final int getCode() { return code; }

    public static MouseButton getButton(int code) {
        return Arrays.stream(MouseButton.values()).filter(mb -> mb.getCode() == code).findFirst()
                .orElse(MouseButton.INVALID);
    }

}
