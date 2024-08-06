package com.shroomclient.shroomclientnextgen.util;

import com.shroomclient.shroomclientnextgen.mixin.MouseAccessor;
import lombok.Setter;

public class MouseUtil {

    @Setter
    private static boolean realRightClick = false; // This gets set before it's accessed by the mixin anyway

    private static boolean overrideRightClick = false;
    private static boolean rightClick = false;

    public static boolean getRightClick() {
        return overrideRightClick ? rightClick : realRightClick;
    }

    public static void holdRightClick() {
        overrideRightClick = true;
        rightClick = true;

        ((MouseAccessor) C.mc.mouse).setRightButtonClicked(true);
    }

    public static void releaseRightClick() {
        overrideRightClick = false;
        rightClick = false;

        ((MouseAccessor) C.mc.mouse).setRightButtonClicked(realRightClick);
    }
}
