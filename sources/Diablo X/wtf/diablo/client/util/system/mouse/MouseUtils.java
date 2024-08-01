package wtf.diablo.client.util.system.mouse;

public final class MouseUtils {

    private MouseUtils()
    {
    }

    public static boolean isHoveringCoords(final float x, final float y, final float width, final float height, final int mouseX, final int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height - 0.5f;
    }

    public static boolean isHoveringCoords(final double x, final double y, final double width, final double height, final double mouseX, final double mouseY) {
        return MouseUtils.isHoveringCoords((float) x, (float) y, (float) width, (float) height, (int) mouseX, (int) mouseY);
    }

}
