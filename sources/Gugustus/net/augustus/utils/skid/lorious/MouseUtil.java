package net.augustus.utils.skid.lorious;

public class MouseUtil {
    public static boolean isHovered(int mouseX, int mouseY, double x, double y, double width, double height) {
        return (double)mouseX >= x && (double)mouseX <= x + width && (double)mouseY >= y && (double)mouseY <= y + height;
    }
}
