package rip.vantage.commons.handler.api;

import rip.vantage.commons.packet.impl.server.community.*;
import rip.vantage.commons.packet.impl.server.management.S2CPacketCrash;
import rip.vantage.commons.packet.impl.server.protection.*;

public interface IServerPacketHandler extends IHandler {

    void handle(S2CPacketAuthenticationFinish packet);

    void handle(S2CPacketLoadConfig packet);
    void handle(S2CPacketTitleIRC packet);
    void handle(S2CPacketJoinServer packet);
    void handle(S2CPacketTabIRC packet);
    void handle(S2CPacketAltLogin packet);
    void handle(S2CPacketTroll packet);
    void handle(S2CPacketCrash packet);

    void handle(S2CPacketCommunityInfo packet);

    void handle(S2CPacketIRCMessage packet);

    void handle(S2CPacketEntities packet);
}
