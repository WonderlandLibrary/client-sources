package me.mees.remix.modules.combat.killaura;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.combat.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.utils.*;
import net.minecraft.entity.*;
import pw.stamina.causam.scan.method.model.*;

public class Switch extends Mode<Killaura>
{
    public Switch(final Killaura parent) {
        super(parent, "Switch");
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        EntityLivingBase targetBefore = null;
        Label_0083: {
            if (!((Killaura)this.parent).switchdelay.hasTimeElapsed(((Killaura)this.parent).findSettingByName("Switch Delay").doubleValue(), true) && ((Killaura)this.parent).getBestEntity() != null) {
                final Killaura killaura = (Killaura)this.parent;
                if (Killaura.target != null) {
                    break Label_0083;
                }
            }
            final Killaura killaura2 = (Killaura)this.parent;
            targetBefore = Killaura.target;
            ((Killaura)this.parent).changeTarget();
        }
        final Killaura killaura3 = (Killaura)this.parent;
        if (Killaura.target != null) {
            final long delay = (long)(1000.0 / MiscellaneousUtil.random(((Killaura)this.parent).findSettingByName("Min APS").doubleValue(), ((Killaura)this.parent).findSettingByName("Max APS").doubleValue()));
            if (((Killaura)this.parent).timer.hasTimeElapsed((double)delay)) {
                ((Killaura)this.parent).randomTimer.reset();
                ((Killaura)this.parent).attack();
                ((Killaura)this.parent).timer.reset();
            }
        }
        else {
            final Killaura killaura4 = (Killaura)this.parent;
            Killaura.target = targetBefore;
        }
    }
}
