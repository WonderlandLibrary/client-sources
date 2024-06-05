package net.minecraft.client.renderer.entity;

import net.minecraft.scoreboard.*;

static final class SwitchEnumVisible
{
    static final int[] field_178679_a;
    
    static {
        field_178679_a = new int[Team.EnumVisible.values().length];
        try {
            SwitchEnumVisible.field_178679_a[Team.EnumVisible.ALWAYS.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumVisible.field_178679_a[Team.EnumVisible.NEVER.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumVisible.field_178679_a[Team.EnumVisible.HIDE_FOR_OTHER_TEAMS.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumVisible.field_178679_a[Team.EnumVisible.HIDE_FOR_OWN_TEAM.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
