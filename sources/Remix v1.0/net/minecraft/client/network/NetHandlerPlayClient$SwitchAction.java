package net.minecraft.client.network;

import net.minecraft.network.play.server.*;

static final class SwitchAction
{
    static final int[] field_178885_a;
    static final int[] field_178884_b;
    
    static {
        field_178884_b = new int[S38PacketPlayerListItem.Action.values().length];
        try {
            SwitchAction.field_178884_b[S38PacketPlayerListItem.Action.ADD_PLAYER.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchAction.field_178884_b[S38PacketPlayerListItem.Action.UPDATE_GAME_MODE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchAction.field_178884_b[S38PacketPlayerListItem.Action.UPDATE_LATENCY.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchAction.field_178884_b[S38PacketPlayerListItem.Action.UPDATE_DISPLAY_NAME.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        field_178885_a = new int[S45PacketTitle.Type.values().length];
        try {
            SwitchAction.field_178885_a[S45PacketTitle.Type.TITLE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchAction.field_178885_a[S45PacketTitle.Type.SUBTITLE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchAction.field_178885_a[S45PacketTitle.Type.RESET.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
    }
}
