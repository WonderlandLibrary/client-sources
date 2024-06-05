package me.mees.remix.modules.combat;

import me.satisfactory.base.module.*;
import me.satisfactory.base.utils.*;
import pw.stamina.causam.scan.method.model.*;
import me.satisfactory.base.events.*;

public final class EventTest extends Module
{
    public EventTest() {
        super("EventTest", 0, Category.COMBAT);
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        MiscellaneousUtil.sendInfo("onUpdate");
    }
    
    @Subscriber
    public void onRender(final Event2DRender event) {
        MiscellaneousUtil.sendInfo("onRender");
    }
    
    @Subscriber
    public void onMove(final EventMove event) {
        MiscellaneousUtil.sendInfo("onMove");
    }
    
    @Subscriber
    public void onMotion(final EventMotion event) {
        MiscellaneousUtil.sendInfo("onMotion");
    }
}
