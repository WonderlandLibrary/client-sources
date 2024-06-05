/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils.entity.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.utils.PlayerUtils;
import digital.rbq.utils.entity.ICheck;

public final class TeamsCheck
implements ICheck {
    private final BoolOption teams;

    public TeamsCheck(BoolOption teams) {
        this.teams = teams;
    }

    @Override
    public boolean validate(Entity entity) {
        return !(entity instanceof EntityPlayer) || !PlayerUtils.isOnSameTeam((EntityPlayer)entity) || this.teams.getValue() == false;
    }
}

