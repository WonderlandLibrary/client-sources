/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.event.EventState;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=3, xi=2)
public final class ChestAura$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[EventState.values().length];
        ChestAura$WhenMappings.$EnumSwitchMapping$0[EventState.PRE.ordinal()] = 1;
        ChestAura$WhenMappings.$EnumSwitchMapping$0[EventState.POST.ordinal()] = 2;
    }
}

