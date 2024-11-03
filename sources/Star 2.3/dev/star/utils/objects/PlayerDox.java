package dev.star.utils.objects;

import dev.star.utils.misc.DoxUtil;
import lombok.Getter;
import net.minecraft.entity.EntityLivingBase;


@Getter
public class PlayerDox {
    private final EntityLivingBase player;
    private final String state, liscenseNumber, DOB, expirationDate, topAddress, bottomAddress;
    private final boolean male;

    public PlayerDox(EntityLivingBase entity) {
        this.player = entity;
        this.state = DoxUtil.getRandomState();
        liscenseNumber = DoxUtil.randomAlphaNumeric(9).toUpperCase();
        DOB = DoxUtil.randomDOB();
        expirationDate = DoxUtil.randomExpirationDate(DOB);
        this.topAddress = DoxUtil.getTopAddress();
        this.bottomAddress = DoxUtil.getBottomAddress(state);
        male = Math.random() > .2;
    }

}
