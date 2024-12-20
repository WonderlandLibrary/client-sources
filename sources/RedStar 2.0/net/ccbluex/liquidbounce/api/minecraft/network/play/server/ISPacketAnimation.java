package net.ccbluex.liquidbounce.api.minecraft.network.play.server;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;

@Metadata(mv={1, 1, 16}, bv={1, 0, 3}, k=1, d1={"\u0000\n\n\n\u0000\n\b\n\b\bf\u000020R0X¦¢\bR0X¦¢\b¨\b"}, d2={"Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketAnimation;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "animationType", "", "getAnimationType", "()I", "entityID", "getEntityID", "Pride"})
public interface ISPacketAnimation
extends IPacket {
    public int getAnimationType();

    public int getEntityID();
}
