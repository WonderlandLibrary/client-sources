/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 */
package skizzle.alts;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;
import skizzle.alts.Alt;
import skizzle.alts.AltManager;

public class AltLoginThread
extends Thread {
    public String password;
    public String username;
    public String status;
    public Alt alt;
    public Minecraft mc;

    public String getStatus() {
        AltLoginThread Nigga;
        return Nigga.status;
    }

    @Override
    public void run() {
        AltLoginThread Nigga;
        if (Nigga.password.equals("")) {
            Nigga.mc.session = new Session(Nigga.username, "", "", Qprot0.0("\u57ff\u71c4\u6c8c\u1f1c\u3126\ud24f"));
            Nigga.status = (Object)((Object)EnumChatFormatting.GREEN) + Qprot0.0("\u57de\u71c4\u6c81\u1f1a\u312d\ud24c\u8c6f\u3bb6\ueff5\u9a2c\u5825\uaf44") + Nigga.username + Qprot0.0("\u57b2\u7186\u6cc6\u1f12\u312e\ud24e\u8c23\u3bb6\ueff5\u9a67\u5825\uaf02\ud565\ucab9\u4514\ucbbe");
            return;
        }
        Nigga.status = (Object)((Object)EnumChatFormatting.YELLOW) + Qprot0.0("\u57de\u71c4\u6c81\u1f1a\u3121\ud246\u8c28\u3bff\ueff2\u9a6c\u582b\uaf42\ud52a");
        Session Nigga2 = Nigga.createSession(Nigga.username, Nigga.password);
        if (Nigga2 == null) {
            Nigga.status = (Object)((Object)EnumChatFormatting.RED) + Qprot0.0("\u57de\u71c4\u6c81\u1f14\u3126\ud208\u8c29\u3bbe\ueff2\u9a6e\u5860\uaf08\ud525");
        } else {
            Nigga.status = (Object)((Object)EnumChatFormatting.GREEN) + Qprot0.0("\u57de\u71c4\u6c81\u1f1a\u312d\ud24c\u8c6f\u3bb6\ueff5\u9a2c\u5825\uaf44") + Nigga2.getUsername() + ")";
            AltManager.lastAlt = new Alt(Nigga.username, Nigga.password);
            if (Nigga.alt != null && Nigga.alt.getMask().equals(Nigga.alt.getUsername())) {
                Nigga.alt.setMask(Nigga2.getUsername());
            }
            Nigga.mc.session = Nigga2;
        }
    }

    public AltLoginThread(String Nigga, String Nigga2) {
        super(Qprot0.0("\u57d3\u71c7\u6c92\ua7a4\u792d\ud247\u8c28\u3bb6\u570c\ud20b\u5851\uaf04\ud576\u7248\u0d39\ucbf3"));
        AltLoginThread Nigga3;
        Nigga3.mc = Minecraft.getMinecraft();
        Nigga3.username = Nigga;
        Nigga3.password = Nigga2;
        Nigga3.alt = null;
        Nigga3.status = (Object)((Object)EnumChatFormatting.GRAY) + Qprot0.0("\u57c5\u71ca\u6c8f\ua7f0\u7908\ud246\u8c28\u3bf1\u574c\ud205");
    }

    public static {
        throw throwable;
    }

    public void setStatus(String Nigga) {
        Nigga.status = Nigga;
    }

    public AltLoginThread(Alt Nigga) {
        super(Qprot0.0("\u57d3\u71c7\u6c92\ua7a4\u792d\ud247\u8c28\u3bb6\u570c\ud20b\u5851\uaf04\ud576\u7248\u0d39\ucbf3"));
        AltLoginThread Nigga2;
        Nigga2.mc = Minecraft.getMinecraft();
        Nigga2.username = Nigga.getUsername();
        Nigga2.password = Nigga.getPassword();
        Nigga2.alt = Nigga;
        Nigga2.status = (Object)((Object)EnumChatFormatting.GRAY) + Qprot0.0("\u57c5\u71ca\u6c8f\ua7f0\u7908\ud246\u8c28\u3bf1\u574c\ud205");
    }

    public Session createSession(String Nigga, String Nigga2) {
        YggdrasilAuthenticationService Nigga3 = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication Nigga4 = (YggdrasilUserAuthentication)Nigga3.createUserAuthentication(Agent.MINECRAFT);
        Nigga4.setUsername(Nigga);
        Nigga4.setPassword(Nigga2);
        try {
            Nigga4.logIn();
            return new Session(Nigga4.getSelectedProfile().getName(), Nigga4.getSelectedProfile().getId().toString(), Nigga4.getAuthenticatedToken(), Qprot0.0("\u57ff\u71c4\u6c8cm\u25b7\ud24f"));
        }
        catch (AuthenticationException Nigga5) {
            Nigga5.printStackTrace();
            return null;
        }
    }
}

