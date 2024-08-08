package net.futureclient.client;

import java.util.Iterator;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.events.Event;

public class Jh extends n<we>
{
    public final nh k;
    
    public Jh(final nh k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketPlayerListItem) {
            final SPacketPlayerListItem sPacketPlayerListItem = (SPacketPlayerListItem)eventPacket.M();
            try {
                Block_9: {
                    NetworkPlayerInfo networkPlayerInfo = null;
                    Block_8: {
                        for (final SPacketPlayerListItem.AddPlayerData addPlayerData : sPacketPlayerListItem.getEntries()) {
                            if (sPacketPlayerListItem.getAction().equals((Object)SPacketPlayerListItem.Action.REMOVE_PLAYER)) {
                                networkPlayerInfo = null;
                                final Iterator<NetworkPlayerInfo> iterator2 = (Iterator<NetworkPlayerInfo>)Minecraft.getMinecraft().player.connection.getPlayerInfoMap().iterator();
                                while (true) {
                                    while (iterator2.hasNext()) {
                                        final NetworkPlayerInfo networkPlayerInfo2;
                                        if ((networkPlayerInfo2 = iterator2.next()).getGameProfile().getId().equals(addPlayerData.getProfile().getId())) {
                                            final NetworkPlayerInfo networkPlayerInfo3 = networkPlayerInfo = networkPlayerInfo2;
                                            if (networkPlayerInfo3 != null) {
                                                break Block_8;
                                            }
                                            continue Block_9;
                                        }
                                    }
                                    final NetworkPlayerInfo networkPlayerInfo3 = networkPlayerInfo;
                                    continue;
                                }
                            }
                            if (sPacketPlayerListItem.getAction().equals((Object)SPacketPlayerListItem.Action.ADD_PLAYER)) {
                                break Block_9;
                            }
                        }
                        return;
                    }
                    pg.M().M().M(new tF(networkPlayerInfo.getGameProfile().getName()));
                    return;
                }
                final SPacketPlayerListItem.AddPlayerData addPlayerData;
                pg.M().M().M(new uF(new NetworkPlayerInfo(addPlayerData).getGameProfile().getName()));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
