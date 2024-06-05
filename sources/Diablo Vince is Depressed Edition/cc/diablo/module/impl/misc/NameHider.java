/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import net.minecraft.client.network.NetworkPlayerInfo;

public class NameHider
extends Module {
    public static ArrayList<String> names = new ArrayList();

    public NameHider() {
        super("NameHider", "Hides your name", 0, Category.Misc);
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        for (NetworkPlayerInfo networkplayerinfo : mc.getNetHandler().getPlayerInfoMap()) {
            if (names.contains(networkplayerinfo.getGameProfile().getName())) continue;
            names.add(networkplayerinfo.getGameProfile().getName());
        }
    }
}

