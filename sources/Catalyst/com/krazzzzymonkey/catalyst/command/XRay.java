// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.command;

import java.util.Arrays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import com.krazzzzymonkey.catalyst.managers.XRayManager;
import com.krazzzzymonkey.catalyst.xray.XRayData;
import com.krazzzzymonkey.catalyst.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.util.math.RayTraceResult;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class XRay extends Command
{
    private static final /* synthetic */ int[] llIIIlI;
    private static final /* synthetic */ String[] llIllI;
    
    private static String llIIIllI(final String llIllIIIIlllIll, final String llIllIIIIllllII) {
        try {
            final SecretKeySpec llIllIIIlIIIIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIllIIIIllllII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIllIIIIllllll = Cipher.getInstance("Blowfish");
            llIllIIIIllllll.init(XRay.llIIIlI[2], llIllIIIlIIIIII);
            return new String(llIllIIIIllllll.doFinal(Base64.getDecoder().decode(llIllIIIIlllIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIllIIIIlllllI) {
            llIllIIIIlllllI.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void runCommand(final String llIllIIIlIlIIII, final String[] llIllIIIlIIllll) {
        try {
            if (lIIllIIll(llIllIIIlIIllll[XRay.llIIIlI[0]].equalsIgnoreCase(XRay.llIllI[XRay.llIIIlI[1]]) ? 1 : 0)) {
                if (lIIllIIll(llIllIIIlIIllll[XRay.llIIIlI[1]].equalsIgnoreCase(XRay.llIllI[XRay.llIIIlI[2]]) ? 1 : 0) && lIIllIlII(Wrapper.INSTANCE.mc().objectMouseOver)) {
                    final RayTraceResult llIllIIIlIlIlII = Wrapper.INSTANCE.mc().objectMouseOver;
                    if (lIIllIlIl(llIllIIIlIlIlII.typeOfHit, RayTraceResult.Type.BLOCK)) {
                        final BlockPos llIllIIIlIllIIl = llIllIIIlIlIlII.getBlockPos();
                        final IBlockState llIllIIIlIllIII = Wrapper.INSTANCE.world().getBlockState(llIllIIIlIllIIl);
                        final int llIllIIIlIlIlll = Block.getIdFromBlock(llIllIIIlIllIII.getBlock());
                        final int llIllIIIlIlIllI = llIllIIIlIllIII.getBlock().getMetaFromState(llIllIIIlIllIII);
                        final XRayData llIllIIIlIlIlIl = new XRayData(llIllIIIlIlIlll, llIllIIIlIlIllI, Utils.random(XRay.llIIIlI[0], XRay.llIIIlI[3]), Utils.random(XRay.llIIIlI[0], XRay.llIIIlI[3]), Utils.random(XRay.llIIIlI[0], XRay.llIIIlI[3]));
                        XRayManager.addData(llIllIIIlIlIlIl);
                    }
                    "".length();
                    if (null != null) {
                        return;
                    }
                }
                else if (lIIllIIll(llIllIIIlIIllll[XRay.llIIIlI[1]].contains(XRay.llIllI[XRay.llIIIlI[4]]) ? 1 : 0)) {
                    final String[] llIllIIIlIlIIll = llIllIIIlIIllll[XRay.llIIIlI[1]].split(XRay.llIllI[XRay.llIIIlI[5]]);
                    XRayManager.add(new XRayData(Integer.parseInt(llIllIIIlIlIIll[XRay.llIIIlI[0]]), Integer.parseInt(llIllIIIlIlIIll[XRay.llIIIlI[1]]), Integer.parseInt(llIllIIIlIIllll[XRay.llIIIlI[2]]), Integer.parseInt(llIllIIIlIIllll[XRay.llIIIlI[4]]), Integer.parseInt(llIllIIIlIIllll[XRay.llIIIlI[5]])));
                    "".length();
                    if ((102 + 71 - 52 + 12 ^ 88 + 69 - 141 + 113) <= 0) {
                        return;
                    }
                }
                else {
                    XRayManager.add(new XRayData(Integer.parseInt(llIllIIIlIIllll[XRay.llIIIlI[1]]), XRay.llIIIlI[0], Integer.parseInt(llIllIIIlIIllll[XRay.llIIIlI[2]]), Integer.parseInt(llIllIIIlIIllll[XRay.llIIIlI[4]]), Integer.parseInt(llIllIIIlIIllll[XRay.llIIIlI[5]])));
                    "".length();
                    if ((18 + 82 - 3 + 37 ^ 76 + 106 - 71 + 19) == "  ".length()) {
                        return;
                    }
                }
            }
            else if (lIIllIIll(llIllIIIlIIllll[XRay.llIIIlI[0]].equalsIgnoreCase(XRay.llIllI[XRay.llIIIlI[6]]) ? 1 : 0)) {
                XRayManager.removeData(Integer.parseInt(llIllIIIlIIllll[XRay.llIIIlI[1]]));
                "".length();
                if ((0x97 ^ 0x93) < " ".length()) {
                    return;
                }
            }
            else if (lIIllIIll(llIllIIIlIIllll[XRay.llIIIlI[0]].equalsIgnoreCase(XRay.llIllI[XRay.llIIIlI[7]]) ? 1 : 0)) {
                XRayManager.clear();
            }
            "".length();
            if (" ".length() >= "  ".length()) {
                return;
            }
        }
        catch (Exception llIllIIIlIlIIlI) {
            ChatUtils.error(String.valueOf(new StringBuilder().append(XRay.llIllI[XRay.llIIIlI[8]]).append(this.getSyntax())));
        }
    }
    
    private static boolean lIIllIlII(final Object llIllIIIIIIllIl) {
        return llIllIIIIIIllIl != null;
    }
    
    private static void lIIllIIlI() {
        (llIIIlI = new int[12])[0] = ((0x2B ^ 0x35 ^ (0xF9 ^ 0xB3)) & (25 + 111 + 66 + 16 ^ 44 + 33 + 34 + 31 ^ -" ".length()));
        XRay.llIIIlI[1] = " ".length();
        XRay.llIIIlI[2] = "  ".length();
        XRay.llIIIlI[3] = 146 + 167 - 177 + 118;
        XRay.llIIIlI[4] = "   ".length();
        XRay.llIIIlI[5] = (0x56 ^ 0x52);
        XRay.llIIIlI[6] = (0x65 ^ 0x57 ^ (0x18 ^ 0x2F));
        XRay.llIIIlI[7] = (13 + 153 - 64 + 69 ^ 164 + 101 - 131 + 39);
        XRay.llIIIlI[8] = (0x44 ^ 0x20 ^ (0x47 ^ 0x24));
        XRay.llIIIlI[9] = (0x9B ^ 0x93);
        XRay.llIIIlI[10] = (0x7 ^ 0x45 ^ (0x35 ^ 0x7E));
        XRay.llIIIlI[11] = (0x6E ^ 0x21 ^ (0x57 ^ 0x12));
    }
    
    static {
        lIIllIIlI();
        llIIlIIl();
    }
    
    private static boolean lIIllIIll(final int llIllIIIIIIIlll) {
        return llIllIIIIIIIlll != 0;
    }
    
    public XRay() {
        super(XRay.llIllI[XRay.llIIIlI[0]]);
    }
    
    private static boolean lIIllIllI(final int llIllIIIIIlIIII, final int llIllIIIIIIllll) {
        return llIllIIIIIlIIII < llIllIIIIIIllll;
    }
    
    private static void llIIlIIl() {
        (llIllI = new String[XRay.llIIIlI[11]])[XRay.llIIIlI[0]] = llIIIlIl("OwcREw==", "Cupjm");
        XRay.llIllI[XRay.llIIIlI[1]] = llIIIllI("1tcD50LpXYk=", "UfOoW");
        XRay.llIllI[XRay.llIIIlI[2]] = llIIIlIl("GQcbCRw=", "thnzy");
        XRay.llIllI[XRay.llIIIlI[4]] = llIIIlIl("dg==", "LGYlD");
        XRay.llIllI[XRay.llIIIlI[5]] = llIIIllI("w2rEmJ4djTE=", "FkZko");
        XRay.llIllI[XRay.llIIIlI[6]] = llIIIlIl("JiQCFjcx", "TAoyA");
        XRay.llIllI[XRay.llIIIlI[7]] = llIIIllI("2jmbmlUT96Y=", "gjIyx");
        XRay.llIllI[XRay.llIIIlI[8]] = llIIlIII("u7cq14x/mV8=", "DGGei");
        XRay.llIllI[XRay.llIIIlI[9]] = llIIIlIl("Cjw2LEc/Dzk0ADcceQ==", "RnWUg");
        XRay.llIllI[XRay.llIIIlI[10]] = llIIIlIl("DioGHVEXPANETR88XQkUAjlZRE0EPQNaUUo/FQEUGGZHWBMaLQJaUQp4BgAVVjUIEQITeBtEAxM1CBIUVmQOAE9WJEcHHRM5FQ==", "vXgdq");
    }
    
    private static String llIIIlIl(String llIllIIIIIllIll, final String llIllIIIIIllIlI) {
        llIllIIIIIllIll = new String(Base64.getDecoder().decode(llIllIIIIIllIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIllIIIIIllllI = new StringBuilder();
        final char[] llIllIIIIIlllIl = llIllIIIIIllIlI.toCharArray();
        int llIllIIIIIlllII = XRay.llIIIlI[0];
        final float llIllIIIIIlIllI = (Object)llIllIIIIIllIll.toCharArray();
        final char llIllIIIIIlIlIl = (char)llIllIIIIIlIllI.length;
        short llIllIIIIIlIlII = (short)XRay.llIIIlI[0];
        while (lIIllIllI(llIllIIIIIlIlII, llIllIIIIIlIlIl)) {
            final char llIllIIIIlIIIIl = llIllIIIIIlIllI[llIllIIIIIlIlII];
            llIllIIIIIllllI.append((char)(llIllIIIIlIIIIl ^ llIllIIIIIlllIl[llIllIIIIIlllII % llIllIIIIIlllIl.length]));
            "".length();
            ++llIllIIIIIlllII;
            ++llIllIIIIIlIlII;
            "".length();
            if (null != null) {
                return null;
            }
        }
        return String.valueOf(llIllIIIIIllllI);
    }
    
    @Override
    public String getSyntax() {
        return XRay.llIllI[XRay.llIIIlI[10]];
    }
    
    @Override
    public String getDescription() {
        return XRay.llIllI[XRay.llIIIlI[9]];
    }
    
    private static boolean lIIllIlIl(final Object llIllIIIIIIlIlI, final Object llIllIIIIIIlIIl) {
        return llIllIIIIIIlIlI == llIllIIIIIIlIIl;
    }
    
    private static String llIIlIII(final String llIllIIIIllIIII, final String llIllIIIIlIllll) {
        try {
            final SecretKeySpec llIllIIIIllIIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIllIIIIlIllll.getBytes(StandardCharsets.UTF_8)), XRay.llIIIlI[9]), "DES");
            final Cipher llIllIIIIllIIlI = Cipher.getInstance("DES");
            llIllIIIIllIIlI.init(XRay.llIIIlI[2], llIllIIIIllIIll);
            return new String(llIllIIIIllIIlI.doFinal(Base64.getDecoder().decode(llIllIIIIllIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIllIIIIllIIIl) {
            llIllIIIIllIIIl.printStackTrace();
            return null;
        }
    }
}
