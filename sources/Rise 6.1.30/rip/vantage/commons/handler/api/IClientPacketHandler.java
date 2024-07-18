package rip.vantage.commons.handler.api;

import rip.vantage.commons.packet.impl.client.community.*;
import rip.vantage.commons.packet.impl.client.general.C2SPacketKeepAlive;
import rip.vantage.commons.packet.impl.client.protection.*;

public interface IClientPacketHandler extends IHandler {

    void handle(C2SPacketKeepAlive packet);

    void handle(C2SPacketAuthenticate packet);

    void handle(C2SPacketConvertConfig packet);
    void handle(C2SPacketTitleIRC packet);
    void handle(C2SPacketConfirmServer packet);
    void handle(C2SPacketTabIRC packet);
    void handle(C2SPacketAltLogin packet);
    void handle(C2SPacketCommunityInfo packet);
    void handle(C2SPacketLoadCloudConfig packet);
    void handle(C2SPacketServerState packet);

    void handle(C2SPacketIRCMessage packet);
    void handle(C2SPacketFilterEntities packet);
}
