/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.stats;

import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.stats.StatBase;
import net.minecraft.util.IChatComponent;

public class StatCrafting
extends StatBase {
    private final Item field_150960_a;
    private static final String __OBFID = "CL_00001470";

    public StatCrafting(String p_i45910_1_, String p_i45910_2_, IChatComponent p_i45910_3_, Item p_i45910_4_) {
        super(String.valueOf(p_i45910_1_) + p_i45910_2_, p_i45910_3_);
        this.field_150960_a = p_i45910_4_;
        int var5 = Item.getIdFromItem(p_i45910_4_);
        if (var5 != 0) {
            IScoreObjectiveCriteria.INSTANCES.put(String.valueOf(p_i45910_1_) + var5, this.func_150952_k());
        }
    }

    public Item func_150959_a() {
        return this.field_150960_a;
    }
}

