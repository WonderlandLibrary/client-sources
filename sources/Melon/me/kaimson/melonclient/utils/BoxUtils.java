package me.kaimson.melonclient.utils;

import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.config.*;

public class BoxUtils
{
    public static int getOffsetX(final Module module, final int width) {
        switch (ModuleConfig.INSTANCE.getPosition(module).getAnchorPoint()) {
            case TOP_CENTER:
            case CENTER:
            case BOTTOM_CENTER: {
                return width / 2;
            }
            case CENTER_RIGHT:
            case BOTTOM_RIGHT:
            case TOP_RIGHT: {
                return width;
            }
            default: {
                return 0;
            }
        }
    }
    
    public static int getOffsetY(final Module module, final int height) {
        switch (ModuleConfig.INSTANCE.getPosition(module).getAnchorPoint()) {
            case CENTER:
            case CENTER_RIGHT:
            case CENTER_LEFT: {
                return height / 2;
            }
            case BOTTOM_CENTER:
            case BOTTOM_RIGHT:
            case BOTTOM_LEFT: {
                return height;
            }
            default: {
                return 0;
            }
        }
    }
    
    public static int getBoxOffX(final Module module, final int x, final int width) {
        switch (ModuleConfig.INSTANCE.getPosition(module).getAnchorPoint()) {
            case TOP_CENTER:
            case CENTER:
            case BOTTOM_CENTER: {
                return x - width / 2;
            }
            case CENTER_RIGHT:
            case BOTTOM_RIGHT:
            case TOP_RIGHT: {
                return x - width;
            }
            default: {
                return x;
            }
        }
    }
    
    public static int getBoxOffY(final Module module, final int y, final int height) {
        switch (ModuleConfig.INSTANCE.getPosition(module).getAnchorPoint()) {
            case CENTER:
            case CENTER_RIGHT:
            case CENTER_LEFT: {
                return y - height / 2;
            }
            case BOTTOM_CENTER:
            case BOTTOM_RIGHT:
            case BOTTOM_LEFT: {
                return y - height;
            }
            default: {
                return y;
            }
        }
    }
}
