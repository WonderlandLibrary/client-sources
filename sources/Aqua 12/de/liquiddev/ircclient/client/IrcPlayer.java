// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.nio.ByteBuffer;
import de.liquiddev.ircclient.util._ircIllIllIIlI;
import java.util.HashMap;

public class IrcPlayer
{
    private static HashMap _ircIllIllIIlI;
    private String _ircIllIllIIlI;
    private byte[] _ircIllIllIIlI;
    private String _irclllllIIlII;
    private IrcRank _ircIllIllIIlI;
    private String _ircIllIlIlIII;
    private long _ircIllIllIIlI;
    private long _irclllllIIlII;
    private String _irclIIlIlllll;
    private String _irclllIIlIlIl;
    private String _ircIIlIIIlIlI;
    
    static {
        IrcPlayer._ircIllIllIIlI = new HashMap();
    }
    
    public static IrcPlayer getByIngameName(final String name) {
        return getByHash(de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI(name));
    }
    
    public static IrcPlayer getByHash(final byte[] hash) {
        final ByteBuffer wrap = ByteBuffer.wrap(hash);
        synchronized (IrcPlayer._ircIllIllIIlI) {
            // monitorexit(IrcPlayer._ircIllIllIIlI)
            return IrcPlayer._ircIllIllIIlI.get(wrap);
        }
    }
    
    public static IrcPlayer getByIrcNickname(String name) {
        name = name.toUpperCase();
        final String s;
        final Predicate<? super IrcPlayer> predicate = ircPlayer -> s.equals(ircPlayer.getIrcNick().toUpperCase());
        synchronized (IrcPlayer._ircIllIllIIlI) {
            // monitorexit(IrcPlayer._ircIllIllIIlI)
            return IrcPlayer._ircIllIllIIlI.values().stream().filter(predicate).findAny().orElse(null);
        }
    }
    
    public static boolean isClientUser(final String ingameName) {
        final ByteBuffer wrap = ByteBuffer.wrap(de.liquiddev.ircclient.util._ircIllIllIIlI._ircIllIllIIlI(ingameName));
        synchronized (IrcPlayer._ircIllIllIIlI) {
            // monitorexit(IrcPlayer._ircIllIllIIlI)
            return IrcPlayer._ircIllIllIIlI.containsKey(wrap);
        }
    }
    
    public static Collection listPlayers() {
        synchronized (IrcPlayer._ircIllIllIIlI) {
            // monitorexit(IrcPlayer._ircIllIllIIlI)
            return new ArrayList(IrcPlayer._ircIllIllIIlI.values());
        }
    }
    
    public IrcPlayer(final String ircNick, final byte[] hash, final String serverIp, final IrcRank rank, final String clientName, final String extra, final long playtime, final String clientVersion, final String capeLocation) {
        this._ircIllIllIIlI = hash;
        this._ircIllIllIIlI = ircNick;
        this._irclllllIIlII = serverIp;
        this._ircIllIllIIlI = rank;
        this._ircIllIlIlIII = clientName;
        this._ircIllIllIIlI = playtime;
        this._irclllllIIlII = System.currentTimeMillis();
        this._irclIIlIlllll = extra;
        this._irclllIIlIlIl = clientVersion;
        this._ircIIlIIIlIlI = capeLocation;
    }
    
    public void register() {
        final ByteBuffer wrap = ByteBuffer.wrap(this._ircIllIllIIlI);
        synchronized (IrcPlayer._ircIllIllIIlI) {
            IrcPlayer._ircIllIllIIlI.put(wrap, this);
        }
        // monitorexit(IrcPlayer._ircIllIllIIlI)
    }
    
    public void unregister() {
        final ByteBuffer wrap = ByteBuffer.wrap(this._ircIllIllIIlI);
        synchronized (IrcPlayer._ircIllIllIIlI) {
            IrcPlayer._ircIllIllIIlI.remove(wrap);
        }
        // monitorexit(IrcPlayer._ircIllIllIIlI)
    }
    
    public String getIrcNick() {
        return this._ircIllIllIIlI;
    }
    
    public String getServerIp() {
        return this._irclllllIIlII;
    }
    
    public IrcRank getRank() {
        return this._ircIllIllIIlI;
    }
    
    public String getClientName() {
        return this._ircIllIlIlIII;
    }
    
    public long getPlaytime() {
        return this._ircIllIllIIlI + (System.currentTimeMillis() - this._irclllllIIlII);
    }
    
    public long getUpdateTimestamp() {
        return this._irclllllIIlII;
    }
    
    public String getExtra() {
        return this._irclIIlIlllll;
    }
    
    public String getClientVersion() {
        return this._irclllIIlIlIl;
    }
    
    public String getCapeLocation() {
        return this._ircIIlIIIlIlI;
    }
    
    public boolean hasCape() {
        return this._ircIIlIIIlIlI != null;
    }
    
    @Override
    public String toString() {
        return String.valueOf(new _irclIIlllIIII(this).toString()) + this._ircIllIllIIlI + new _irclIIIlllIlI(this).toString() + this._irclllllIIlII + new _irclIIlIlIIII(this).toString() + this._ircIllIllIIlI + new _irclIIlllIIlI(this).toString() + this._ircIllIlIlIII + new _irclIlIlIlIII(this).toString() + this._irclllIIlIlIl + new _ircllIIlIIlll(this).toString();
    }
}
