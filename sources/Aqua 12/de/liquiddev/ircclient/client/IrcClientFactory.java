// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.client;

import java.util.List;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import javax.net.ssl.SSLContext;
import de.liquiddev.ircclient.util.IrcUuid;
import de.liquiddev.ircclient.util.IrcServers;
import de.liquiddev.ircclient.api.IrcClient;

public class IrcClientFactory
{
    private static IrcClientFactory _ircIllIllIIlI;
    private final String _ircIllIllIIlI;
    
    static {
        IrcClientFactory._ircIllIllIIlI = new IrcClientFactory();
    }
    
    public IrcClientFactory() {
        this._ircIllIllIIlI = new _ircllIlIIIlII(this).toString();
    }
    
    public static IrcClientFactory getDefault() {
        return IrcClientFactory._ircIllIllIIlI;
    }
    
    public IrcClient createIrcClient(final ClientType clientType, final String loginToken, final String minecraftNickname, final String clientVersion) {
        return new SkidIrc(this.createDefaultSslContext(), IrcServers.getDefaultServers(), IrcServers.getDefaultPort(), IrcUuid.getUuid(clientType), clientType, loginToken, minecraftNickname, clientVersion);
    }
    
    public IrcClient createIrcClient(final String host, final ClientType clientType, final String loginToken, final String minecraftNickname, final String clientVersion) {
        return new SkidIrc(this.createDefaultSslContext(), host, IrcServers.getDefaultPort(), IrcUuid.getUuid(clientType), clientType, loginToken, minecraftNickname, clientVersion);
    }
    
    public SSLContext createDefaultSslContext() {
        try {
            Throwable t = null;
            try {
                final ByteArrayInputStream trustStream = new ByteArrayInputStream(Base64.getDecoder().decode(this._ircIllIllIIlI.getBytes()));
                try {
                    return this.createSslContext(new _ircllIIIlIlIl(this).toString(), new _irclIIlIIIllI(this).toString(), trustStream);
                }
                finally {
                    trustStream.close();
                }
            }
            finally {
                if (t == null) {
                    final Throwable exception;
                    t = exception;
                }
                else {
                    final Throwable exception;
                    if (t != exception) {
                        t.addSuppressed(exception);
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println(new _ircllIIllIlIl(this).toString());
            ex.printStackTrace();
            try {
                return SSLContext.getDefault();
            }
            catch (NoSuchAlgorithmException ex2) {
                ex2.printStackTrace();
                return null;
            }
        }
    }
    
    public SSLContext createSslContext(final String trsuStoreType, final String protocol, final InputStream trustStream) {
        final KeyStore instance;
        (instance = KeyStore.getInstance(trsuStoreType)).load(trustStream, null);
        final TrustManagerFactory instance2;
        (instance2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())).init(instance);
        final SSLContext instance3;
        (instance3 = SSLContext.getInstance(protocol)).init(null, instance2.getTrustManagers(), null);
        return instance3;
    }
    
    private static String _ircIllIllIIlI(final ClientType clientType) {
        return IrcUuid.getUuid(clientType);
    }
    
    private static List _ircIllIllIIlI() {
        return IrcServers.getDefaultServers();
    }
    
    private static int _ircIllIllIIlI() {
        return IrcServers.getDefaultPort();
    }
}
