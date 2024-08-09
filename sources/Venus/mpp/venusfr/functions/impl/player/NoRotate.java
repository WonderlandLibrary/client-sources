/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerPacket;

@FunctionRegister(name="NoRotate", type=Category.Player)
public class NoRotate
extends Function {
    private float targetYaw;
    private float targetPitch;
    private boolean isPacketSent;

    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        IPacket<?> iPacket;
        if (eventPacket.isSend() && this.isPacketSent && (iPacket = eventPacket.getPacket()) instanceof CPlayerPacket) {
            CPlayerPacket cPlayerPacket = (CPlayerPacket)iPacket;
            cPlayerPacket.setRotation(this.targetYaw, this.targetPitch);
            this.isPacketSent = false;
        }
    }

    public void sendRotationPacket(float f, float f2) {
        this.targetYaw = f;
        this.targetPitch = f2;
        this.isPacketSent = true;
    }
}

