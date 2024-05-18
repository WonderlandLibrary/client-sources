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
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraftforge.fml.common.*;

public class MHOv
{
    public MHOv() {
        super();
    }
    
    public static void ZGAN(final ArrayList<Object> list) {
        vlwv.kXkt.DKWU(new Citq(list.get(0)));
    }
    
    public static void PGUO(final ArrayList<Object> list) {
        final SecretKey newSharedKey = CryptManager.createNewSharedKey();
        final String s = list.get(0);
        final PublicKey decodePublicKey = CryptManager.decodePublicKey((byte[])(byte[])(Object)list.get(1));
        final String string = new BigInteger(CryptManager.getServerIdHash(s, decodePublicKey, newSharedKey)).toString(16);
        final MinecraftSessionService minecraftSessionService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()).createMinecraftSessionService();
        try {
            minecraftSessionService.joinServer(Wrapper.mc.getSession().getProfile(), Wrapper.mc.getSession().getToken(), string);
        }
        catch (AuthenticationException ex) {
            System.out.println("We have a AuthenticationException exeption but were not going print it, changed are, its a invalid token");
        }
        vlwv.kXkt.DKWU(new bAHg(newSharedKey, decodePublicKey, (byte[])(Object)list.get(2)));
    }
    
    public static void JbkI(final ArrayList<Object> list) {
        if (list.get(0) != 0) {
            vlwv.kXkt.Ycaq.add((String)list.get(1));
            int n = 50;
            for (int i = 0; i < vlwv.kXkt.Ycaq.size(); ++i) {
                final String s = vlwv.kXkt.Ycaq.get(i);
                for (int j = 0; j < vlwv.kXkt.SoAu.size(); ++j) {
                    final cJGk cjGk = vlwv.kXkt.SoAu.get(j);
                    if (cjGk.URwX.equals(s)) {
                        vlwv.kXkt.CkvS.add(new Kdwt(cjGk, null, 5, n));
                        n += 35;
                        break;
                    }
                }
            }
        }
        hSNE.Seqo(new mIcn.wwe/eoOk(list.get(0), (String)list.get(1)));
    }
    
    public static void Elgt(final ArrayList<Object> list) {
        final String s = list.get(0);
        final UUID uuid = (UUID)list.get(1);
        final String s2 = list.get(2);
        final String s3 = list.get(3);
        final int intValue = (int)list.get(4);
        final int intValue2 = (int)list.get(5);
        for (int i = 0; i < vlwv.kXkt.SoAu.size(); ++i) {
            if (vlwv.kXkt.SoAu.get(i).URwX.equals(s)) {
                vlwv.kXkt.SoAu.remove(i);
                --i;
            }
        }
        vlwv.kXkt.SoAu.add(new cJGk(s, uuid, s2, s3, intValue, intValue2));
        for (int j = 0; j < vlwv.kXkt.CkvS.size(); ++j) {
            final Kdwt kdwt = vlwv.kXkt.CkvS.get(j);
            for (int k = 0; k < vlwv.kXkt.SoAu.size(); ++k) {
                final cJGk ukmj = vlwv.kXkt.SoAu.get(k);
                if (kdwt.uKMJ.umix.equals(ukmj.umix)) {
                    final cJGk ukmj2 = kdwt.uKMJ;
                    kdwt.uKMJ = ukmj;
                    kdwt.uKMJ.ocQD = ukmj2.ocQD;
                    kdwt.uKMJ.kBoK = ukmj2.kBoK;
                    break;
                }
            }
        }
        if (Wrapper.mc.currentScreen instanceof zROp) {
            final zROp zrOp = (zROp)Wrapper.mc.currentScreen;
            for (int l = 0; l < vlwv.kXkt.SoAu.size(); ++l) {
                final cJGk ukmj3 = vlwv.kXkt.SoAu.get(l);
                if (zrOp.iBKN != null && zrOp.iBKN.uKMJ.umix.equals(ukmj3.umix)) {
                    final cJGk ukmj4 = zrOp.iBKN.uKMJ;
                    zrOp.iBKN.uKMJ = ukmj3;
                    zrOp.iBKN.uKMJ.ocQD = ukmj4.ocQD;
                    zrOp.iBKN.uKMJ.kBoK = ukmj4.kBoK;
                    break;
                }
            }
        }
        hSNE.Seqo(new mIcn.wwe/XvGl(s, uuid, s2, s3, intValue, intValue2));
    }
    
    public static void GoaD(final ArrayList<Object> list) {
        vlwv.kXkt.jdqo = list.get(0);
        vlwv.kXkt.svnA = (String)list.get(1);
    }
    
    public static void Ujlm(final ArrayList<Object> list) {
        if (list.get(4)) {
            MjBb.cZyl(new TYDL("New chat message from, " + (String)list.get(1)));
        }
        for (final Kdwt kdwt : vlwv.kXkt.CkvS) {
            if (kdwt.uKMJ.umix.toString().equals(list.get(1)) || (Wrapper.mc.getSession().getProfile().getId().toString().equals(list.get(1)) && kdwt.uKMJ.umix.toString().equals(list.get(0)))) {
                kdwt.TXHL.add(new iZvY(((String)list.get(1)).equals(Wrapper.mc.getSession().getProfile().getId().toString()) ? null : kdwt.uKMJ, (String)list.get(2), (Long)(Object)list.get(3)));
                break;
            }
        }
    }
    
    public static void iOWJ(final ArrayList<Object> list) {
        MjBb.cZyl(new TYDL("New friend request from " + list.get(0)));
    }
    
    public static void KZvi(final ArrayList<Object> list) throws Exception {
        FMLLog.log.info("Socket got disconnected, " + list.get(0));
        vlwv.kXkt.svnA = list.get(0);
        vlwv.kXkt.eCZB();
    }
    
    public static void uBan(final ArrayList<Object> list) throws Exception {
        hSNE.Seqo(new mIcn.wwe/OxHg(list.get(0), (Character)(Object)list.get(1)));
    }
    
    public static void hMgJ(final ArrayList<Object> list) throws Exception {
        final int intValue = list.get(1);
        if (Wrapper.mc.world != null) {
            Wrapper.mc.ingameGUI.displayTitle((String)list.get(0), "", 10, 70, 20);
        }
        hSNE.Seqo(new mIcn.wwe/fYTJ((String)list.get(0), intValue));
    }
    
    public static void QoYT(final ArrayList<Object> list) throws Exception {
        hSNE.Seqo(new mIcn.wwe/goVx(list.get(0)));
    }
    
    public static void WSND(final ArrayList<Object> list) throws Exception {
        hSNE.Seqo(new mIcn.wwe/ulen(list.get(0)));
    }
}
