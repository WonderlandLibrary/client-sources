package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;

public final class AutoSword extends ModBase
{
    public AutoSword() {
        super("AutoSword", "0", false, ".t sword");
        this.setDescription("Switch to sword while souping.");
        this.setEnabled(true);
    }
    
    @Override
    public void preMotionUpdate() {
        if (KillAura.curTarget != null) {
            this.getBestWeapon();
        }
    }
    
    private void getBestWeapon() {
        float damageModifier = 1.0f;
        int newItem = -1;
        for (int i = 0; i < 9; ++i) {
            this.getWrapper();
            final wm stack = MorbidWrapper.getPlayer().bK.a[i];
            if (stack != null) {
                final wm wm = stack;
                this.getWrapper();
                final float damage = wm.a((mp)MorbidWrapper.getPlayer());
                if (damage > damageModifier) {
                    newItem = i;
                    damageModifier = damage;
                }
            }
        }
        if (newItem > -1) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().bK.c != newItem) {
                this.getWrapper();
                MorbidWrapper.getPlayer().bK.c = newItem;
                this.getWrapper();
                MorbidWrapper.getController().e();
            }
        }
    }
}
