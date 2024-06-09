// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.module;

import java.util.Iterator;
import java.util.Arrays;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import com.krazzzzymonkey.catalyst.value.BooleanValue;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import com.krazzzzymonkey.catalyst.value.Mode;
import com.krazzzzymonkey.catalyst.value.ModeValue;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.krazzzzymonkey.catalyst.gui.click.ClickGuiScreen;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import com.krazzzzymonkey.catalyst.utils.system.Connection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import com.krazzzzymonkey.catalyst.value.Value;
import java.util.ArrayList;

public class Modules
{
    private /* synthetic */ String name;
    private /* synthetic */ boolean show;
    private /* synthetic */ boolean toggled;
    private /* synthetic */ ModuleCategory category;
    private /* synthetic */ int key;
    private static final /* synthetic */ String[] lIIIIll;
    private /* synthetic */ ArrayList<Value> values;
    private static final /* synthetic */ int[] lIIIlIl;
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public void setName(final String lllIIIIIIlIlIIl) {
        this.name = lllIIIIIIlIlIIl;
    }
    
    public void onRenderWorldLast(final RenderWorldLastEvent lllIIIIIlIIIlll) {
    }
    
    public ModuleCategory getCategory() {
        return this.category;
    }
    
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent lllIIIIIlIIlIIl) {
    }
    
    private static void lllIIlll() {
        (lIIIIll = new String[Modules.lIIIlIl[6]])[Modules.lIIIlIl[0]] = lllIIlII("5DEe55Ic5cM=", "vpiOh");
        Modules.lIIIIll[Modules.lIIIlIl[1]] = lllIIlIl("JhMhNA9YVg==", "bvCAh");
        Modules.lIIIIll[Modules.lIIIlIl[3]] = lllIIlIl("Jy0XJzVZaA==", "cHuRR");
        Modules.lIIIIll[Modules.lIIIlIl[4]] = lllIIlII("ledWJrC4hs4=", "wQxMe");
        Modules.lIIIIll[Modules.lIIIlIl[5]] = lllIIllI("a0+0kxdfR2Ul79X/1LW80w==", "zZhoT");
    }
    
    public void onRenderGameOverlay(final RenderGameOverlayEvent.Text lllIIIIIlIIIlIl) {
    }
    
    public void onItemPickup(final EntityItemPickupEvent lllIIIIIlIIllIl) {
    }
    
    public ArrayList<Value> getValues() {
        return this.values;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setShow(final boolean lllIIIIIIIIIlll) {
        this.show = lllIIIIIIIIIlll;
    }
    
    public void setToggled(final boolean lllIIIIIIIlIIII) {
        this.toggled = lllIIIIIIIlIIII;
    }
    
    private static boolean lllIlllI(final int llIllllllIIlIll) {
        return llIllllllIIlIll == 0;
    }
    
    public void debug() {
        ChatUtils.message(Modules.lIIIIll[Modules.lIIIlIl[5]]);
    }
    
    public void onAttackEntity(final AttackEntityEvent lllIIIIIlIIllll) {
    }
    
    private static String lllIIlIl(String llIllllllIllIll, final String llIllllllIlllll) {
        llIllllllIllIll = new String(Base64.getDecoder().decode(llIllllllIllIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIllllllIllllI = new StringBuilder();
        final char[] llIllllllIlllIl = llIllllllIlllll.toCharArray();
        int llIllllllIlllII = Modules.lIIIlIl[0];
        final String llIllllllIlIllI = (Object)llIllllllIllIll.toCharArray();
        final boolean llIllllllIlIlIl = llIllllllIlIllI.length != 0;
        boolean llIllllllIlIlII = Modules.lIIIlIl[0] != 0;
        while (lllIllII(llIllllllIlIlII ? 1 : 0, llIllllllIlIlIl ? 1 : 0)) {
            final char llIlllllllIIIIl = llIllllllIlIllI[llIllllllIlIlII];
            llIllllllIllllI.append((char)(llIlllllllIIIIl ^ llIllllllIlllIl[llIllllllIlllII % llIllllllIlllIl.length]));
            "".length();
            ++llIllllllIlllII;
            ++llIllllllIlIlII;
            "".length();
            if ((0x49 ^ 0x4C) == 0x0) {
                return null;
            }
        }
        return String.valueOf(llIllllllIllllI);
    }
    
    private static void lllIlIll() {
        (lIIIlIl = new int[8])[0] = ((0xF0 ^ 0xB1) & ~(0xDC ^ 0x9D));
        Modules.lIIIlIl[1] = " ".length();
        Modules.lIIIlIl[2] = -" ".length();
        Modules.lIIIlIl[3] = "  ".length();
        Modules.lIIIlIl[4] = "   ".length();
        Modules.lIIIlIl[5] = (18 + 4 + 93 + 34 ^ 88 + 62 - 39 + 34);
        Modules.lIIIlIl[6] = (0x49 ^ 0x4C);
        Modules.lIIIlIl[7] = (0x4F ^ 0x47);
    }
    
    public void debug(final double lllIIIIIIlllIII) {
        ChatUtils.message(String.valueOf(new StringBuilder().append(Modules.lIIIIll[Modules.lIIIlIl[3]]).append(lllIIIIIIlllIII)));
    }
    
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock lllIIIIIlIIIIll) {
    }
    
    public boolean onPacket(final Object lllIIIIIlIllIII, final Connection.Side lllIIIIIlIlIlll) {
        return Modules.lIIIlIl[1] != 0;
    }
    
    public Modules(final String lllIIIIlIlIlIlI, final ModuleCategory lllIIIIlIlIlIIl) {
        this.values = new ArrayList<Value>();
        this.name = lllIIIIlIlIlIlI;
        this.category = lllIIIIlIlIlIIl;
        this.toggled = (Modules.lIIIlIl[0] != 0);
        this.show = (Modules.lIIIlIl[1] != 0);
        this.key = Modules.lIIIlIl[2];
    }
    
    public void toggle() {
        int toggled;
        if (lllIlllI(this.toggled ? 1 : 0)) {
            toggled = Modules.lIIIlIl[1];
            "".length();
            if ((0x1E ^ 0x1A) == "   ".length()) {
                return;
            }
        }
        else {
            toggled = Modules.lIIIlIl[0];
        }
        this.toggled = (toggled != 0);
        if (lllIllIl(this.toggled ? 1 : 0)) {
            this.onEnable();
            "".length();
            if ((0x7D ^ 0x79) == 0x0) {
                return;
            }
        }
        else {
            this.onDisable();
        }
        RenderUtils.splashTickPos = Modules.lIIIlIl[0];
        if (lllIlllI(RenderUtils.isSplash ? 1 : 0) && lllIlllI((Wrapper.INSTANCE.mc().currentScreen instanceof ClickGuiScreen) ? 1 : 0)) {
            RenderUtils.isSplash = (Modules.lIIIlIl[1] != 0);
        }
    }
    
    public void setCategory(final ModuleCategory lllIIIIIIlIIIlI) {
        this.category = lllIIIIIIlIIIlI;
    }
    
    public boolean isToggledMode(final String lllIIIIlIIIIlII) {
        final float lllIIIIlIIIIIll = (float)this.values.iterator();
        while (lllIllIl(((Iterator)lllIIIIlIIIIIll).hasNext() ? 1 : 0)) {
            final Value lllIIIIlIIIlIII = ((Iterator<Value>)lllIIIIlIIIIIll).next();
            if (lllIllIl((lllIIIIlIIIlIII instanceof ModeValue) ? 1 : 0)) {
                final ModeValue lllIIIIlIIIlIIl = (ModeValue)lllIIIIlIIIlIII;
                final boolean lllIIIIlIIIIIII = (Object)lllIIIIlIIIlIIl.getModes();
                final float lllIIIIIlllllll = lllIIIIlIIIIIII.length;
                boolean lllIIIIIllllllI = Modules.lIIIlIl[0] != 0;
                while (lllIllII(lllIIIIIllllllI ? 1 : 0, (int)lllIIIIIlllllll)) {
                    final Mode lllIIIIlIIIlIlI = lllIIIIlIIIIIII[lllIIIIIllllllI];
                    if (lllIllIl(lllIIIIlIIIlIlI.getName().equalsIgnoreCase(lllIIIIlIIIIlII) ? 1 : 0) && lllIllIl(lllIIIIlIIIlIlI.isToggled() ? 1 : 0)) {
                        return Modules.lIIIlIl[1] != 0;
                    }
                    ++lllIIIIIllllllI;
                    "".length();
                    if (-" ".length() == "   ".length()) {
                        return ((12 + 61 + 40 + 45 ^ 77 + 17 + 21 + 19) & (29 + 3 + 50 + 53 ^ 24 + 116 - 11 + 30 ^ -" ".length())) != 0x0;
                    }
                }
            }
            "".length();
            if ("  ".length() != "  ".length()) {
                return ((0x79 ^ 0x60) & ~(0xB1 ^ 0xA8)) != 0x0;
            }
        }
        return Modules.lIIIlIl[0] != 0;
    }
    
    public void onProjectileImpact(final ProjectileImpactEvent lllIIIIIlIIlIll) {
    }
    
    public void setValues(final ArrayList<Value> lllIIIIIllIIIll) {
        final char lllIIIIIllIIIlI = (char)lllIIIIIllIIIll.iterator();
        while (lllIllIl(((Iterator)lllIIIIIllIIIlI).hasNext() ? 1 : 0)) {
            final Value lllIIIIIllIIlll = ((Iterator<Value>)lllIIIIIllIIIlI).next();
            final long lllIIIIIllIIIII = (long)this.values.iterator();
            while (lllIllIl(((Iterator)lllIIIIIllIIIII).hasNext() ? 1 : 0)) {
                final Value lllIIIIIllIlIII = ((Iterator<Value>)lllIIIIIllIIIII).next();
                if (lllIllIl(lllIIIIIllIIlll.getName().equalsIgnoreCase(lllIIIIIllIlIII.getName()) ? 1 : 0)) {
                    lllIIIIIllIlIII.setValue(lllIIIIIllIIlll.getValue());
                }
                "".length();
                if (null != null) {
                    return;
                }
            }
            "".length();
            if ("  ".length() <= " ".length()) {
                return;
            }
        }
    }
    
    public void debug(final float lllIIIIIIllllII) {
        ChatUtils.message(String.valueOf(new StringBuilder().append(Modules.lIIIIll[Modules.lIIIlIl[1]]).append(lllIIIIIIllllII)));
    }
    
    private static boolean lllIllII(final int llIllllllIlIIII, final int llIllllllIIllll) {
        return llIllllllIlIIII < llIllllllIIllll;
    }
    
    public boolean isToggledValue(final String lllIIIIIlllIIlI) {
        final Exception lllIIIIIlllIIIl = (Exception)this.values.iterator();
        while (lllIllIl(((Iterator)lllIIIIIlllIIIl).hasNext() ? 1 : 0)) {
            final Value lllIIIIIlllIllI = ((Iterator<Value>)lllIIIIIlllIIIl).next();
            if (lllIllIl((lllIIIIIlllIllI instanceof BooleanValue) ? 1 : 0)) {
                final BooleanValue lllIIIIIlllIlll = (BooleanValue)lllIIIIIlllIllI;
                if (lllIllIl(lllIIIIIlllIlll.getName().equalsIgnoreCase(lllIIIIIlllIIlI) ? 1 : 0) && lllIllIl(((boolean)lllIIIIIlllIlll.getValue()) ? 1 : 0)) {
                    return Modules.lIIIlIl[1] != 0;
                }
            }
            "".length();
            if (-" ".length() != -" ".length()) {
                return ((136 + 73 - 108 + 94 ^ 118 + 33 - 142 + 152) & (162 + 132 - 74 + 19 ^ 17 + 117 - 10 + 17 ^ -" ".length())) != 0x0;
            }
        }
        return Modules.lIIIlIl[0] != 0;
    }
    
    public void onEnable() {
    }
    
    public void onCameraSetup(final EntityViewRenderEvent.CameraSetup lllIIIIIlIlIIIl) {
    }
    
    public void onPlayerTick(final TickEvent.PlayerTickEvent lllIIIIIlIlIlIl) {
    }
    
    public boolean isShow() {
        return this.show;
    }
    
    public void debug(final int lllIIIIIIllllll) {
        ChatUtils.message(String.valueOf(new StringBuilder().append(Modules.lIIIIll[Modules.lIIIlIl[0]]).append(lllIIIIIIllllll)));
    }
    
    static {
        lllIlIll();
        lllIIlll();
    }
    
    public void debug(final long lllIIIIIIllIIll) {
        ChatUtils.message(String.valueOf(new StringBuilder().append(Modules.lIIIIll[Modules.lIIIlIl[4]]).append(lllIIIIIIllIIll)));
    }
    
    public void setKey(final int lllIIIIIIIllIIl) {
        this.key = lllIIIIIIIllIIl;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void addValue(final Value... lllIIIIlIIlllIl) {
        final long lllIIIIlIIllIlI = lllIIIIlIIlllIl;
        final char lllIIIIlIIllIIl = (char)lllIIIIlIIllIlI.length;
        Exception lllIIIIlIIllIII = (Exception)Modules.lIIIlIl[0];
        while (lllIllII((int)lllIIIIlIIllIII, lllIIIIlIIllIIl)) {
            final Value lllIIIIlIIlllll = lllIIIIlIIllIlI[lllIIIIlIIllIII];
            this.getValues().add(lllIIIIlIIlllll);
            "".length();
            ++lllIIIIlIIllIII;
            "".length();
            if (((0x55 ^ 0x79 ^ (0xC4 ^ 0xC0)) & (((0x8A ^ 0xA2) & ~(0x6B ^ 0x43)) ^ (0x2 ^ 0x2A) ^ -" ".length())) != 0x0) {
                return;
            }
        }
    }
    
    public void onClientTick(final TickEvent.ClientTickEvent lllIIIIIlIlIIll) {
    }
    
    private static String lllIIllI(final String llIlllllllIlllI, final String llIlllllllIllll) {
        try {
            final SecretKeySpec llIllllllllIIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlllllllIllll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIllllllllIIlI = Cipher.getInstance("Blowfish");
            llIllllllllIIlI.init(Modules.lIIIlIl[3], llIllllllllIIll);
            return new String(llIllllllllIIlI.doFinal(Base64.getDecoder().decode(llIlllllllIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIllllllllIIIl) {
            llIllllllllIIIl.printStackTrace();
            return null;
        }
    }
    
    public void onDisable() {
    }
    
    private static String lllIIlII(final String llIllllllllllIl, final String llIlllllllllIlI) {
        try {
            final SecretKeySpec lllIIIIIIIIIIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIlllllllllIlI.getBytes(StandardCharsets.UTF_8)), Modules.lIIIlIl[7]), "DES");
            final Cipher llIllllllllllll = Cipher.getInstance("DES");
            llIllllllllllll.init(Modules.lIIIlIl[3], lllIIIIIIIIIIII);
            return new String(llIllllllllllll.doFinal(Base64.getDecoder().decode(llIllllllllllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlllllllllllI) {
            llIlllllllllllI.printStackTrace();
            return null;
        }
    }
    
    private static boolean lllIllIl(final int llIllllllIIllIl) {
        return llIllllllIIllIl != 0;
    }
}
