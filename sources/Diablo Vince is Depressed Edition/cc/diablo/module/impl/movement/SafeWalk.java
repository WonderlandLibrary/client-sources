/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.movement;

import cc.diablo.event.impl.SafeWalkEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;

public class SafeWalk
extends Module {
    public SafeWalk() {
        super("Safe Walk", "No fall of :sex:", 0, Category.Movement);
    }

    @Subscribe
    public void onSafewalk(SafeWalkEvent e) {
        if (SafeWalk.mc.thePlayer.onGround) {
            e.setWalkSafely(true);
        }
    }
}

