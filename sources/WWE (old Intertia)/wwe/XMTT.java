package wwe;

import net.minecraft.util.*;
import java.math.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.*;
import wwe.utils.*;
import com.mojang.authlib.exceptions.*;
import javax.crypto.*;
import java.security.*;
import com.mojang.authlib.minecraft.*;
import java.util.*;

public class XMTT
{
    public XMTT() {
        super();
    }
    
    public static void GAPX(final ArrayList<Object> list) {
        final SecretKey newSharedKey = CryptManager.createNewSharedKey();
        final String s = list.get(0);
        final PublicKey decodePublicKey = CryptManager.decodePublicKey((byte[])(byte[])(Object)list.get(1));
        final String string = new BigInteger(CryptManager.getServerIdHash(s, decodePublicKey, newSharedKey)).toString(16);
        final MinecraftSessionService minecraftSessionService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()).createMinecraftSessionService();
        try {
            minecraftSessionService.joinServer(Wrapper.mc.getSession().getProfile(), Wrapper.mc.getSession().getToken(), string);
            if (XMTT.\u200c) {
                throw null;
            }
        }
        catch (AuthenticationException ex) {
            System.out.println("We have a AuthenticationException exeption but were not going print it, changed are, its a invalid token");
        }
        vlwv.kXkt.DKWU(new bAHg(newSharedKey, decodePublicKey, (byte[])(Object)list.get(2)));
    }
    
    public static void OIIt(final ArrayList<Object> list) {
        final Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            if (XMTT.\u200c) {
                throw null;
            }
        }
    }
}
