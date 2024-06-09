/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Misc;

import com.darkmagician6.eventapi.EventManager;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class NoFriends
extends Module {
    public NoFriends() {
        super("NoFriends", "NoFriends", 0, Category.MISC);
    }

    @Override
    public void onDisabled() {
        EventManager.unregister(this);
    }
}

