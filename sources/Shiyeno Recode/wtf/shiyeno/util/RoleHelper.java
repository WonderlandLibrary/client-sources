package wtf.shiyeno.util;

import wtf.shiyeno.util.render.ColorUtil;

public class RoleHelper {
    private RoleType _type;

    public RoleHelper(RoleType type){
        _type = type;
    }

    public int getRoleColor() {
        return switch (_type) {
            case Coder -> ColorUtil.rgba(46, 204, 113, 255);
            case Media -> ColorUtil.rgba(241, 196, 15, 255);
            default -> ColorUtil.rgba(233, 30, 99, 255);
        };
    }
}