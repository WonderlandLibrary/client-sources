package net.augustus.modules;

import net.augustus.utils.ColorUtil;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.skid.lorious.ColorUtils;

import java.awt.*;

public enum Categorys {
    MOVEMENT,
    COMBAT,
    PLAYER,
    MISC,
    RENDER,
    WORLD;

    public Color color;

    Categorys() {
        color = new Color(RandomUtil.nextInt(0, 255), RandomUtil.nextInt(0, 255), RandomUtil.nextInt(0, 255));
    }

    public Color getColor() {
        return color;
    }
}
;