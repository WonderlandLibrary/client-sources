package net.minecraft.network;

import net.minecraft.network.play.client.*;

static final class SwitchAction
{
    static final int[] field_180224_a;
    static final int[] field_180222_b;
    static final int[] field_180223_c;
    
    static {
        field_180223_c = new int[C16PacketClientStatus.EnumState.values().length];
        try {
            SwitchAction.field_180223_c[C16PacketClientStatus.EnumState.PERFORM_RESPAWN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchAction.field_180223_c[C16PacketClientStatus.EnumState.REQUEST_STATS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchAction.field_180223_c[C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        field_180222_b = new int[C0BPacketEntityAction.Action.values().length];
        try {
            SwitchAction.field_180222_b[C0BPacketEntityAction.Action.START_SNEAKING.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchAction.field_180222_b[C0BPacketEntityAction.Action.STOP_SNEAKING.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchAction.field_180222_b[C0BPacketEntityAction.Action.START_SPRINTING.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchAction.field_180222_b[C0BPacketEntityAction.Action.STOP_SPRINTING.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchAction.field_180222_b[C0BPacketEntityAction.Action.STOP_SLEEPING.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            SwitchAction.field_180222_b[C0BPacketEntityAction.Action.RIDING_JUMP.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            SwitchAction.field_180222_b[C0BPacketEntityAction.Action.OPEN_INVENTORY.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        field_180224_a = new int[C07PacketPlayerDigging.Action.values().length];
        try {
            SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.DROP_ITEM.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
        try {
            SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.DROP_ALL_ITEMS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError12) {}
        try {
            SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.RELEASE_USE_ITEM.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError13) {}
        try {
            SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.START_DESTROY_BLOCK.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError14) {}
        try {
            SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError15) {}
        try {
            SwitchAction.field_180224_a[C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError16) {}
    }
}
