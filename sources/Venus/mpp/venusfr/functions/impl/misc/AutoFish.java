/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.utils.math.StopWatch;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.util.Hand;

@FunctionRegister(name="AutoFish", type=Category.Misc)
public class AutoFish
extends Function {
    private final StopWatch delay = new StopWatch();
    private boolean isHooked = false;
    private boolean needToHook = false;

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        if (this.delay.isReached(600L) && this.isHooked) {
            AutoFish.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            this.isHooked = false;
            this.needToHook = true;
            this.delay.reset();
        }
        if (this.delay.isReached(300L) && this.needToHook) {
            AutoFish.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            this.needToHook = false;
            this.delay.reset();
        }
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        SPlaySoundEffectPacket sPlaySoundEffectPacket;
        IPacket<?> iPacket = eventPacket.getPacket();
        if (iPacket instanceof SPlaySoundEffectPacket && (sPlaySoundEffectPacket = (SPlaySoundEffectPacket)iPacket).getSound().getName().getPath().equals("entity.fishing_bobber.splash")) {
            this.isHooked = true;
            this.delay.reset();
        }
    }
}

