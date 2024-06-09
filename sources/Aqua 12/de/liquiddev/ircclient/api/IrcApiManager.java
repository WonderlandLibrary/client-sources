// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.client.IrcRank;
import de.liquiddev.ircclient.client.IrcPlayer;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class IrcApiManager
{
    private List apis;
    private List packetListeners;
    private List customDataListeners;
    
    public IrcApiManager() {
        this.apis = new ArrayList();
        this.packetListeners = new ArrayList();
        this.customDataListeners = new ArrayList();
    }
    
    public void registerApi(final IrcApi api) {
        this.apis.add(api);
    }
    
    public void unregisterApi(final IrcApi api) {
        this.apis.remove(api);
    }
    
    public void registerPacketListener(final IrcPacketListener listener) {
        this.packetListeners.add(listener);
    }
    
    public void unregisterPacketListener(final IrcPacketListener listener) {
        this.packetListeners.remove(listener);
    }
    
    public void registerCustomDataListener(final CustomDataListener listener) {
        this.customDataListeners.add(listener);
    }
    
    public void unregisterCustomDataListener(final CustomDataListener listener) {
        this.customDataListeners.remove(listener);
    }
    
    public void onChatMessage(final String message) {
        for (final IrcApi ircApi : this.apis) {
            try {
                ircApi.onChatMessage(message);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    public void onPlayerChatMessage(final String playerName, final String clientName, final String chatMessage) {
        for (final IrcApi ircApi : this.apis) {
            try {
                ircApi.onPlayerChatMessage(playerName, clientName, chatMessage);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    public void onWhisperMessage(final String playerName, final String message, final boolean isFromMe) {
        for (final IrcApi ircApi : this.apis) {
            try {
                ircApi.onWhisperMessage(playerName, message, isFromMe);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    public void onLocalPlayerChatMessage(final String playerName, final String clientName, final String chatMessage) {
        for (final IrcApi ircApi : this.apis) {
            try {
                ircApi.onLocalPlayerChatMessage(playerName, clientName, chatMessage);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    public void onCustomData(final String playerName, final String tag, final byte[] data) {
        IrcPlayer byIrcNickname;
        if ((byIrcNickname = IrcPlayer.getByIrcNickname(playerName)) == null) {
            byIrcNickname = new IrcPlayer(playerName, new byte[0], new IrcApiManager$1(this).toString(), IrcRank.USER, new IrcApiManager$2(this).toString(), null, 0L, new IrcApiManager$3(this).toString(), null);
        }
        for (final CustomDataListener customDataListener : this.customDataListeners) {
            try {
                customDataListener.onCustomDataReceived(byIrcNickname, tag, data);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    public void onPacketOut(final IrcPacket packet) {
        for (final IrcPacketListener ircPacketListener : this.packetListeners) {
            try {
                ircPacketListener.onSend(packet);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    public void onPacketIn(final IrcPacket packet) {
        for (final IrcPacketListener ircPacketListener : this.packetListeners) {
            try {
                ircPacketListener.onReceived(packet);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
