package wtf.shiyeno.util;

import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.util.render.ColorUtil;

public enum RoleType {
    Coder,
    Media,
    User;

    public static RoleType fromInteger(int x) {
        return switch (x) {
            case 0 -> Coder;
            case 1 -> Media;
            case 2 -> User;
            default -> null;
        };
    }
}