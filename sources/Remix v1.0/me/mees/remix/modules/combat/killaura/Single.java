package me.mees.remix.modules.combat.killaura;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.combat.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.utils.*;
import pw.stamina.causam.scan.method.model.*;

public class Single extends Mode<Killaura>
{
    public Single(final Killaura parent) {
        super(parent, "Single");
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        final Killaura killaura = (Killaura)this.parent;
        if (Killaura.target != null) {
            final long delay = (long)(1000.0 / MiscellaneousUtil.random(((Killaura)this.parent).findSettingByName("Min APS").doubleValue(), ((Killaura)this.parent).findSettingByName("Max APS").doubleValue()));
            if (((Killaura)this.parent).timer.hasTimeElapsed((double)delay)) {
                ((Killaura)this.parent).lastReportedCPS = (double)delay;
                ((Killaura)this.parent).attack();
                ((Killaura)this.parent).timer.reset();
            }
        }
    }
}
