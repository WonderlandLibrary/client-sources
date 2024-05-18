// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils.system;

import io.netty.channel.ChannelPipeline;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandlerContext;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.EventsHandler;
import io.netty.channel.ChannelDuplexHandler;

public class Connection extends ChannelDuplexHandler
{
    private static final /* synthetic */ int[] llIlIlIl;
    private /* synthetic */ EventsHandler eventHandler;
    private static final /* synthetic */ String[] llIlIIII;
    
    private static boolean lIIllllIll(final int lIllIllllIlIIIl) {
        return lIllIllllIlIIIl == 0;
    }
    
    private static String lIIlllIIIl(final String lIllIllllIlIllI, final String lIllIllllIlIlIl) {
        try {
            final SecretKeySpec lIllIllllIllIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIllIllllIlIlIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lIllIllllIllIlI = Cipher.getInstance("Blowfish");
            lIllIllllIllIlI.init(Connection.llIlIlIl[2], lIllIllllIllIll);
            return new String(lIllIllllIllIlI.doFinal(Base64.getDecoder().decode(lIllIllllIlIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIllllIllIIl) {
            lIllIllllIllIIl.printStackTrace();
            return null;
        }
    }
    
    public void channelRead(final ChannelHandlerContext lIllIllllllIIII, final Object lIllIlllllIllll) throws Exception {
        if (lIIllllIll(this.eventHandler.onPacket(lIllIlllllIllll, Side.IN) ? 1 : 0)) {
            return;
        }
        super.channelRead(lIllIllllllIIII, lIllIlllllIllll);
    }
    
    private static void lIIllllIlI() {
        (llIlIlIl = new int[4])[0] = ((0x94 ^ 0x89) & ~(0xDF ^ 0xC2));
        Connection.llIlIlIl[1] = " ".length();
        Connection.llIlIlIl[2] = "  ".length();
        Connection.llIlIlIl[3] = "   ".length();
    }
    
    public void write(final ChannelHandlerContext lIllIlllllIIIlI, final Object lIllIlllllIIlIl, final ChannelPromise lIllIlllllIIlII) throws Exception {
        if (lIIllllIll(this.eventHandler.onPacket(lIllIlllllIIlIl, Side.OUT) ? 1 : 0)) {
            return;
        }
        super.write(lIllIlllllIIIlI, lIllIlllllIIlIl, lIllIlllllIIlII);
    }
    
    private static void lIIlllIlIl() {
        (llIlIIII = new String[Connection.llIlIlIl[3]])[Connection.llIlIlIl[0]] = lIIlllIIIl("2nS4hkUI86LsU4TYr+XQ1A==", "cKExE");
        Connection.llIlIIII[Connection.llIlIlIl[1]] = lIIlllIIIl("1ExdxCAgOy2Pd21o8WgxlA==", "HtRLs");
        Connection.llIlIIII[Connection.llIlIlIl[2]] = lIIlllIIIl("GL8oRM0GyEwckuS2rJqyvF2a+wtiPL+/qj0fJeO6UwU=", "ymXSH");
    }
    
    static {
        lIIllllIlI();
        lIIlllIlIl();
    }
    
    public Connection(final EventsHandler lIllIlllllllIII) {
        this.eventHandler = lIllIlllllllIII;
        try {
            final ChannelPipeline lIllIlllllllIll = Wrapper.INSTANCE.mc().getConnection().getNetworkManager().channel().pipeline();
            lIllIlllllllIll.addBefore(Connection.llIlIIII[Connection.llIlIlIl[0]], Connection.llIlIIII[Connection.llIlIlIl[1]], (ChannelHandler)this);
            "".length();
            "".length();
            if (((0xD5 ^ 0xB1 ^ (0x7D ^ 0x30)) & (0x8C ^ 0xC7 ^ (0xA2 ^ 0xC0) ^ -" ".length())) > 0) {
                throw null;
            }
        }
        catch (Exception lIllIlllllllIlI) {
            ChatUtils.error(Connection.llIlIIII[Connection.llIlIlIl[2]]);
            lIllIlllllllIlI.printStackTrace();
        }
    }
    
    public enum Side
    {
        private static final /* synthetic */ int[] lllIlII;
        
        OUT;
        
        private static final /* synthetic */ String[] lllIIlI;
        
        IN;
        
        private static String lIlllIlII(String llIlIIlIlllIIlI, final String llIlIIlIlllIIIl) {
            llIlIIlIlllIIlI = new String(Base64.getDecoder().decode(llIlIIlIlllIIlI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            final StringBuilder llIlIIlIlllIlIl = new StringBuilder();
            final char[] llIlIIlIlllIlII = llIlIIlIlllIIIl.toCharArray();
            int llIlIIlIlllIIll = Side.lllIlII[0];
            final byte llIlIIlIllIllIl = (Object)llIlIIlIlllIIlI.toCharArray();
            final Exception llIlIIlIllIllII = (Exception)llIlIIlIllIllIl.length;
            char llIlIIlIllIlIll = (char)Side.lllIlII[0];
            while (lIllllllI(llIlIIlIllIlIll, (int)llIlIIlIllIllII)) {
                final char llIlIIlIllllIII = llIlIIlIllIllIl[llIlIIlIllIlIll];
                llIlIIlIlllIlIl.append((char)(llIlIIlIllllIII ^ llIlIIlIlllIlII[llIlIIlIlllIIll % llIlIIlIlllIlII.length]));
                "".length();
                ++llIlIIlIlllIIll;
                ++llIlIIlIllIlIll;
                "".length();
                if ("  ".length() > "  ".length()) {
                    return null;
                }
            }
            return String.valueOf(llIlIIlIlllIlIl);
        }
        
        private static void lIlllllIl() {
            (lllIlII = new int[3])[0] = ((0x2A ^ 0x36) & ~(0x9 ^ 0x15));
            Side.lllIlII[1] = " ".length();
            Side.lllIlII[2] = "  ".length();
        }
        
        private static boolean lIllllllI(final int llIlIIlIllIIlll, final int llIlIIlIllIIllI) {
            return llIlIIlIllIIlll < llIlIIlIllIIllI;
        }
        
        private static String lIlllIlIl(final String llIlIIllIIIIlIl, final String llIlIIllIIIIllI) {
            try {
                final SecretKeySpec llIlIIllIIIlIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlIIllIIIIllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                final Cipher llIlIIllIIIlIIl = Cipher.getInstance("Blowfish");
                llIlIIllIIIlIIl.init(Side.lllIlII[2], llIlIIllIIIlIlI);
                return new String(llIlIIllIIIlIIl.doFinal(Base64.getDecoder().decode(llIlIIllIIIIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llIlIIllIIIlIII) {
                llIlIIllIIIlIII.printStackTrace();
                return null;
            }
        }
        
        static {
            lIlllllIl();
            lIllllIIl();
            final Side[] $values = new Side[Side.lllIlII[2]];
            $values[Side.lllIlII[0]] = Side.IN;
            $values[Side.lllIlII[1]] = Side.OUT;
            $VALUES = $values;
        }
        
        private static void lIllllIIl() {
            (lllIIlI = new String[Side.lllIlII[2]])[Side.lllIlII[0]] = lIlllIlII("EAI=", "YLrwR");
            Side.lllIIlI[Side.lllIlII[1]] = lIlllIlIl("dTGhpDaVo7c=", "HmiiG");
        }
    }
}
