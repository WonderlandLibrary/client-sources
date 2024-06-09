// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.managers;

import java.util.Iterator;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui;
import com.krazzzzymonkey.catalyst.module.modules.gui.HUD;
import com.krazzzzymonkey.catalyst.module.modules.player.Disconnect;
import com.krazzzzymonkey.catalyst.module.modules.misc.FastBreak;
import com.krazzzzymonkey.catalyst.module.modules.player.AutoSwim;
import com.krazzzzymonkey.catalyst.module.modules.player.AutoWalk;
import com.krazzzzymonkey.catalyst.module.modules.render.AntiRain;
import com.krazzzzymonkey.catalyst.module.modules.render.PlayerRadar;
import com.krazzzzymonkey.catalyst.module.modules.misc.PluginsGetter;
import com.krazzzzymonkey.catalyst.module.modules.render.BlockOverlay;
import com.krazzzzymonkey.catalyst.module.modules.movement.Speed;
import com.krazzzzymonkey.catalyst.module.modules.player.Scaffold;
import com.krazzzzymonkey.catalyst.module.modules.player.Blink;
import com.krazzzzymonkey.catalyst.module.modules.combat.AutoArmor;
import com.krazzzzymonkey.catalyst.module.modules.player.AutoSprint;
import com.krazzzzymonkey.catalyst.module.modules.combat.Criticals;
import com.krazzzzymonkey.catalyst.module.modules.render.NightVision;
import com.krazzzzymonkey.catalyst.module.modules.render.StorageESP;
import com.krazzzzymonkey.catalyst.module.modules.render.ESP;
import com.krazzzzymonkey.catalyst.module.modules.render.Trajectories;
import com.krazzzzymonkey.catalyst.module.modules.render.InventoryViewer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import java.util.Arrays;
import com.krazzzzymonkey.catalyst.value.Mode;
import com.krazzzzymonkey.catalyst.value.ModeValue;
import com.krazzzzymonkey.catalyst.value.Value;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Comparator;
import java.util.List;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import com.krazzzzymonkey.catalyst.gui.click.theme.Theme;
import com.krazzzzymonkey.catalyst.gui.click.theme.dark.DarkTheme;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import java.util.ArrayList;
import com.krazzzzymonkey.catalyst.module.Modules;
import com.krazzzzymonkey.catalyst.gui.click.ClickGuiScreen;

public class HackManager
{
    private /* synthetic */ ClickGuiScreen guiScreen;
    private /* synthetic */ GuiManager guiManager;
    private static /* synthetic */ Modules toggleHack;
    private static /* synthetic */ ArrayList<Modules> hacks;
    
    public static void onRenderGameOverlay(final RenderGameOverlayEvent.Text llIIIIIIlIIlIll) {
        final byte llIIIIIIlIIlIIl = (byte)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIIlIIlIIl).hasNext() ? 1 : 0)) {
            final Modules llIIIIIIlIIllII = ((Iterator<Modules>)llIIIIIIlIIlIIl).next();
            if (lIIIlIlIll(llIIIIIIlIIllII.isToggled() ? 1 : 0)) {
                llIIIIIIlIIllII.onRenderGameOverlay(llIIIIIIlIIlIll);
            }
            "".length();
            if (" ".length() == -" ".length()) {
                return;
            }
        }
    }
    
    public static void onClientTick(final TickEvent.ClientTickEvent llIIIIIIllIIIlI) {
        final int llIIIIIIllIIIIl = (int)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIIllIIIIl).hasNext() ? 1 : 0)) {
            final Modules llIIIIIIllIIlII = ((Iterator<Modules>)llIIIIIIllIIIIl).next();
            if (lIIIlIlIll(llIIIIIIllIIlII.isToggled() ? 1 : 0)) {
                llIIIIIIllIIlII.onClientTick(llIIIIIIllIIIlI);
            }
            "".length();
            if (((134 + 60 - 11 + 11 ^ 56 + 102 - 112 + 91) & (0xAD ^ 0xB1 ^ (0x6A ^ 0x3D) ^ -" ".length())) != ((0x8C ^ 0xAC ^ (0xAE ^ 0xA5)) & (0xF ^ 0x6D ^ (0xD ^ 0x44) ^ -" ".length()))) {
                return;
            }
        }
    }
    
    private static boolean lIIIlIllIl(final int llIIIIIIIllllII) {
        return llIIIIIIIllllII == 0;
    }
    
    private static boolean lIIIlIllll(final int llIIIIIIlIIIlIl, final int llIIIIIIlIIIlII) {
        return llIIIIIIlIIIlIl == llIIIIIIlIIIlII;
    }
    
    private static boolean lIIIlIlllI(final Object llIIIIIIlIIIIlI) {
        return llIIIIIIlIIIIlI != null;
    }
    
    public ClickGuiScreen getGui() {
        if (lIIIlIlIlI(this.guiManager)) {
            this.guiManager = new GuiManager();
            this.guiScreen = new ClickGuiScreen();
            ClickGuiScreen.clickGui = this.guiManager;
            this.guiManager.Initialization();
            this.guiManager.setTheme(new DarkTheme());
        }
        return this.guiManager;
    }
    
    public static void onCameraSetup(final EntityViewRenderEvent.CameraSetup llIIIIIlIIIlIlI) {
        final char llIIIIIlIIIlIIl = (char)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIlIIIlIIl).hasNext() ? 1 : 0)) {
            final Modules llIIIIIlIIIllII = ((Iterator<Modules>)llIIIIIlIIIlIIl).next();
            if (lIIIlIlIll(llIIIIIlIIIllII.isToggled() ? 1 : 0)) {
                llIIIIIlIIIllII.onCameraSetup(llIIIIIlIIIlIlI);
            }
            "".length();
            if (null != null) {
                return;
            }
        }
    }
    
    public static Modules getHack(final String llIIIIIlIllIIII) {
        Modules llIIIIIlIlIllll = null;
        final byte llIIIIIlIlIllII = (byte)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIlIlIllII).hasNext() ? 1 : 0)) {
            final Modules llIIIIIlIllIIIl = ((Iterator<Modules>)llIIIIIlIlIllII).next();
            if (lIIIlIlIll(llIIIIIlIllIIIl.getName().equals(llIIIIIlIllIIII) ? 1 : 0)) {
                llIIIIIlIlIllll = llIIIIIlIllIIIl;
            }
            "".length();
            if (-" ".length() >= 0) {
                return null;
            }
        }
        return llIIIIIlIlIllll;
    }
    
    public static void onLivingUpdate(final LivingEvent.LivingUpdateEvent llIIIIIIlIllIlI) {
        final int llIIIIIIlIllIIl = (int)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIIlIllIIl).hasNext() ? 1 : 0)) {
            final Modules llIIIIIIlIlllII = ((Iterator<Modules>)llIIIIIIlIllIIl).next();
            if (lIIIlIlIll(llIIIIIIlIlllII.isToggled() ? 1 : 0)) {
                llIIIIIIlIlllII.onLivingUpdate(llIIIIIIlIllIlI);
            }
            "".length();
            if (((0x78 ^ 0x4E ^ (0x31 ^ 0x44)) & (166 + 76 - 225 + 185 ^ 102 + 75 - 53 + 13 ^ -" ".length())) != 0x0) {
                return;
            }
        }
    }
    
    public static void addHack(final Modules llIIIIIlIlIIIIl) {
        HackManager.hacks.add(llIIIIIlIlIIIIl);
        "".length();
    }
    
    public void setGuiManager(final GuiManager llIIIIIlIlllIll) {
        this.guiManager = llIIIIIlIlllIll;
    }
    
    public static void onItemPickup(final EntityItemPickupEvent llIIIIIIlllIIlI) {
        final String llIIIIIIlllIIIl = (String)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIIlllIIIl).hasNext() ? 1 : 0)) {
            final Modules llIIIIIIlllIlII = ((Iterator<Modules>)llIIIIIIlllIIIl).next();
            if (lIIIlIlIll(llIIIIIIlllIlII.isToggled() ? 1 : 0)) {
                llIIIIIIlllIlII.onItemPickup(llIIIIIIlllIIlI);
            }
            "".length();
            if (null != null) {
                return;
            }
        }
    }
    
    private static boolean lIIIlIlIlI(final Object llIIIIIIlIIIIII) {
        return llIIIIIIlIIIIII == null;
    }
    
    public static void onKeyPressed(final int llIIIIIlIIllIll) {
        if (lIIIlIlllI(Wrapper.INSTANCE.mc().currentScreen)) {
            return;
        }
        final long llIIIIIlIIllIIl = (long)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIlIIllIIl).hasNext() ? 1 : 0)) {
            final Modules llIIIIIlIIlllII = ((Iterator<Modules>)llIIIIIlIIllIIl).next();
            if (lIIIlIllll(llIIIIIlIIlllII.getKey(), llIIIIIlIIllIll)) {
                llIIIIIlIIlllII.toggle();
                HackManager.toggleHack = llIIIIIlIIlllII;
            }
            "".length();
            if ("  ".length() < -" ".length()) {
                return;
            }
        }
    }
    
    public static List<Modules> getSortedHacks() {
        final List<Modules> llIIIIIlIlIIllI = new ArrayList<Modules>();
        final long llIIIIIlIlIIlII = (long)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIlIlIIlII).hasNext() ? 1 : 0)) {
            final Modules llIIIIIlIlIIlll = ((Iterator<Modules>)llIIIIIlIlIIlII).next();
            if (lIIIlIlIll(llIIIIIlIlIIlll.isToggled() ? 1 : 0)) {
                if (lIIIlIllIl(llIIIIIlIlIIlll.isShow() ? 1 : 0)) {
                    "".length();
                    if ((" ".length() & ~" ".length()) != 0x0) {
                        return null;
                    }
                    continue;
                }
                else {
                    llIIIIIlIlIIllI.add(llIIIIIlIlIIlll);
                    "".length();
                }
            }
            "".length();
            if (null != null) {
                return null;
            }
        }
        llIIIIIlIlIIllI.sort(new Comparator<Modules>() {
            private static final /* synthetic */ int[] lllIIIIl;
            private static final /* synthetic */ String[] llIlllIl;
            
            private static void lIlIIlIIIl() {
                (llIlllIl = new String[HackManager$1.lllIIIIl[4]])[HackManager$1.lllIIIIl[0]] = lIlIIIlIIl("883RtOW7AXTbTvlQdSfCfg==", "uqQMx");
                HackManager$1.llIlllIl[HackManager$1.lllIIIIl[1]] = lIlIIIlIlI("bQ==", "MihrR");
                HackManager$1.llIlllIl[HackManager$1.lllIIIIl[2]] = lIlIIIlIll("jm4FWQrl1b9tRisE5TCWVQ==", "kVVvb");
                HackManager$1.llIlllIl[HackManager$1.lllIIIIl[3]] = lIlIIIlIIl("vVufQNmD1fY=", "lthlj");
            }
            
            private static boolean lIlIIllIII(final int lIllIlIIllIIllI) {
                return lIllIlIIllIIllI == 0;
            }
            
            static {
                lIlIIlIllI();
                lIlIIlIIIl();
            }
            
            private static void lIlIIlIllI() {
                (lllIIIIl = new int[6])[0] = ((0x68 ^ 0x1 ^ (0x56 ^ 0x2)) & (0x4 ^ 0x6F ^ (0x11 ^ 0x47) ^ -" ".length()));
                HackManager$1.lllIIIIl[1] = " ".length();
                HackManager$1.lllIIIIl[2] = "  ".length();
                HackManager$1.lllIIIIl[3] = "   ".length();
                HackManager$1.lllIIIIl[4] = (((0x39 ^ 0x70) & ~(0x4C ^ 0x5)) ^ (0xB0 ^ 0xB4));
                HackManager$1.lllIIIIl[5] = (90 + 122 - 206 + 165 ^ 88 + 82 - 36 + 29);
            }
            
            private static String lIlIIIlIIl(final String lIllIlIlIIllIII, final String lIllIlIlIIlIlIl) {
                try {
                    final SecretKeySpec lIllIlIlIIllIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIllIlIlIIlIlIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                    final Cipher lIllIlIlIIllIlI = Cipher.getInstance("Blowfish");
                    lIllIlIlIIllIlI.init(HackManager$1.lllIIIIl[2], lIllIlIlIIllIll);
                    return new String(lIllIlIlIIllIlI.doFinal(Base64.getDecoder().decode(lIllIlIlIIllIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                }
                catch (Exception lIllIlIlIIllIIl) {
                    lIllIlIlIIllIIl.printStackTrace();
                    return null;
                }
            }
            
            @Override
            public int compare(final Modules lIllIlIlIllIllI, final Modules lIllIlIlIllIlIl) {
                String lIllIlIlIllIlII = lIllIlIlIllIllI.getName();
                String lIllIlIlIllIIll = lIllIlIlIllIlIl.getName();
                short lIllIlIlIlIllIl = (short)lIllIlIlIllIllI.getValues().iterator();
                while (lIlIIlIlll(((Iterator)lIllIlIlIlIllIl).hasNext() ? 1 : 0)) {
                    final Value lIllIlIlIlllIll = ((Iterator<Value>)lIllIlIlIlIllIl).next();
                    if (lIlIIlIlll((lIllIlIlIlllIll instanceof ModeValue) ? 1 : 0)) {
                        final ModeValue lIllIlIlIllllII = (ModeValue)lIllIlIlIlllIll;
                        if (lIlIIllIII(lIllIlIlIllllII.getModeName().equals(HackManager$1.llIlllIl[HackManager$1.lllIIIIl[0]]) ? 1 : 0)) {
                            final Exception lIllIlIlIlIlIlI = (Object)lIllIlIlIllllII.getModes();
                            final boolean lIllIlIlIlIlIIl = lIllIlIlIlIlIlI.length != 0;
                            short lIllIlIlIlIlIII = (short)HackManager$1.lllIIIIl[0];
                            while (lIlIIllIIl(lIllIlIlIlIlIII, lIllIlIlIlIlIIl ? 1 : 0)) {
                                final Mode lIllIlIlIllllIl = lIllIlIlIlIlIlI[lIllIlIlIlIlIII];
                                if (lIlIIlIlll(lIllIlIlIllllIl.isToggled() ? 1 : 0)) {
                                    lIllIlIlIllIlII = String.valueOf(new StringBuilder().append(lIllIlIlIllIlII).append(HackManager$1.llIlllIl[HackManager$1.lllIIIIl[1]]).append(lIllIlIlIllllIl.getName()));
                                }
                                ++lIllIlIlIlIlIII;
                                "".length();
                                if (null != null) {
                                    return (0xFA ^ 0x80 ^ (0xAD ^ 0x91)) & (0x6B ^ 0x3E ^ (0x18 ^ 0xB) ^ -" ".length());
                                }
                            }
                        }
                    }
                    "".length();
                    if ("   ".length() >= (0x78 ^ 0x7C)) {
                        return (0x1D ^ 0x12) & ~(0xA0 ^ 0xAF);
                    }
                }
                lIllIlIlIlIllIl = (short)lIllIlIlIllIlIl.getValues().iterator();
                while (lIlIIlIlll(((Iterator)lIllIlIlIlIllIl).hasNext() ? 1 : 0)) {
                    final Value lIllIlIlIlllIII = ((Iterator<Value>)lIllIlIlIlIllIl).next();
                    if (lIlIIlIlll((lIllIlIlIlllIII instanceof ModeValue) ? 1 : 0)) {
                        final ModeValue lIllIlIlIlllIIl = (ModeValue)lIllIlIlIlllIII;
                        if (lIlIIllIII(lIllIlIlIlllIIl.getModeName().equals(HackManager$1.llIlllIl[HackManager$1.lllIIIIl[2]]) ? 1 : 0)) {
                            final Exception lIllIlIlIlIlIlI = (Object)lIllIlIlIlllIIl.getModes();
                            final boolean lIllIlIlIlIlIIl = lIllIlIlIlIlIlI.length != 0;
                            short lIllIlIlIlIlIII = (short)HackManager$1.lllIIIIl[0];
                            while (lIlIIllIIl(lIllIlIlIlIlIII, lIllIlIlIlIlIIl ? 1 : 0)) {
                                final Mode lIllIlIlIlllIlI = lIllIlIlIlIlIlI[lIllIlIlIlIlIII];
                                if (lIlIIlIlll(lIllIlIlIlllIlI.isToggled() ? 1 : 0)) {
                                    lIllIlIlIllIIll = String.valueOf(new StringBuilder().append(lIllIlIlIllIIll).append(HackManager$1.llIlllIl[HackManager$1.lllIIIIl[3]]).append(lIllIlIlIlllIlI.getName()));
                                }
                                ++lIllIlIlIlIlIII;
                                "".length();
                                if ((0x29 ^ 0x66 ^ (0xD2 ^ 0x99)) == 0x0) {
                                    return (0x38 ^ 0x42 ^ (0x23 ^ 0xD)) & (0x82 ^ 0xBD ^ (0x8 ^ 0x63) ^ -" ".length());
                                }
                            }
                        }
                    }
                    "".length();
                    if ("   ".length() == (65 + 81 - 52 + 71 ^ 33 + 35 + 59 + 34)) {
                        return (0x91 ^ 0x96 ^ (0x64 ^ 0x2A)) & (19 + 185 - 124 + 163 ^ 156 + 117 - 113 + 26 ^ -" ".length());
                    }
                }
                final int lIllIlIlIllIIlI = Wrapper.INSTANCE.fontRenderer().getStringWidth(lIllIlIlIllIIll) - Wrapper.INSTANCE.fontRenderer().getStringWidth(lIllIlIlIllIlII);
                int compareTo;
                if (lIlIIlIlll(lIllIlIlIllIIlI)) {
                    compareTo = lIllIlIlIllIIlI;
                    "".length();
                    if ("   ".length() < 0) {
                        return (0x59 ^ 0x6 ^ (0xFC ^ 0x8B)) & (214 + 23 - 69 + 65 ^ 177 + 76 - 205 + 145 ^ -" ".length());
                    }
                }
                else {
                    compareTo = lIllIlIlIllIIll.compareTo(lIllIlIlIllIlII);
                }
                return compareTo;
            }
            
            private static String lIlIIIlIlI(String lIllIlIlIIIIIll, final String lIllIlIlIIIIlll) {
                lIllIlIlIIIIIll = new String(Base64.getDecoder().decode(lIllIlIlIIIIIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                final StringBuilder lIllIlIlIIIIllI = new StringBuilder();
                final char[] lIllIlIlIIIIlIl = lIllIlIlIIIIlll.toCharArray();
                int lIllIlIlIIIIlII = HackManager$1.lllIIIIl[0];
                final long lIllIlIIllllllI = (Object)lIllIlIlIIIIIll.toCharArray();
                final Exception lIllIlIIlllllIl = (Exception)lIllIlIIllllllI.length;
                String lIllIlIIlllllII = (String)HackManager$1.lllIIIIl[0];
                while (lIlIIllIIl((int)lIllIlIIlllllII, (int)lIllIlIIlllllIl)) {
                    final char lIllIlIlIIIlIIl = lIllIlIIllllllI[lIllIlIIlllllII];
                    lIllIlIlIIIIllI.append((char)(lIllIlIlIIIlIIl ^ lIllIlIlIIIIlIl[lIllIlIlIIIIlII % lIllIlIlIIIIlIl.length]));
                    "".length();
                    ++lIllIlIlIIIIlII;
                    ++lIllIlIIlllllII;
                    "".length();
                    if (-" ".length() > 0) {
                        return null;
                    }
                }
                return String.valueOf(lIllIlIlIIIIllI);
            }
            
            private static String lIlIIIlIll(final String lIllIlIIlllIIIl, final String lIllIlIIlllIIlI) {
                try {
                    final SecretKeySpec lIllIlIIlllIllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllIlIIlllIIlI.getBytes(StandardCharsets.UTF_8)), HackManager$1.lllIIIIl[5]), "DES");
                    final Cipher lIllIlIIlllIlIl = Cipher.getInstance("DES");
                    lIllIlIIlllIlIl.init(HackManager$1.lllIIIIl[2], lIllIlIIlllIllI);
                    return new String(lIllIlIIlllIlIl.doFinal(Base64.getDecoder().decode(lIllIlIIlllIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                }
                catch (Exception lIllIlIIlllIlII) {
                    lIllIlIIlllIlII.printStackTrace();
                    return null;
                }
            }
            
            private static boolean lIlIIllIIl(final int lIllIlIIllIlIll, final int lIllIlIIllIlIlI) {
                return lIllIlIIllIlIll < lIllIlIIllIlIlI;
            }
            
            private static boolean lIlIIlIlll(final int lIllIlIIllIlIII) {
                return lIllIlIIllIlIII != 0;
            }
        });
        return llIIIIIlIlIIllI;
    }
    
    public static Modules getToggleHack() {
        return HackManager.toggleHack;
    }
    
    public static void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock llIIIIIlIIlIIll) {
        final int llIIIIIlIIlIIIl = (int)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIlIIlIIIl).hasNext() ? 1 : 0)) {
            final Modules llIIIIIlIIlIlII = ((Iterator<Modules>)llIIIIIlIIlIIIl).next();
            if (lIIIlIlIll(llIIIIIlIIlIlII.isToggled() ? 1 : 0)) {
                llIIIIIlIIlIlII.onLeftClickBlock(llIIIIIlIIlIIll);
            }
            "".length();
            if (((0xFD ^ 0xBB) & ~(0x7B ^ 0x3D)) != 0x0) {
                return;
            }
        }
    }
    
    public static void onAttackEntity(final AttackEntityEvent llIIIIIlIIIIIll) {
        final float llIIIIIlIIIIIIl = (float)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIlIIIIIIl).hasNext() ? 1 : 0)) {
            final Modules llIIIIIlIIIIlII = ((Iterator<Modules>)llIIIIIlIIIIIIl).next();
            if (lIIIlIlIll(llIIIIIlIIIIlII.isToggled() ? 1 : 0)) {
                llIIIIIlIIIIlII.onAttackEntity(llIIIIIlIIIIIll);
            }
            "".length();
            if (" ".length() != " ".length()) {
                return;
            }
        }
    }
    
    public HackManager() {
        HackManager.hacks = new ArrayList<Modules>();
        addHack(new InventoryViewer());
        addHack(new Trajectories());
        addHack(new ESP());
        addHack(new StorageESP());
        addHack(new NightVision());
        addHack(new Criticals());
        addHack(new AutoSprint());
        addHack(new AutoArmor());
        addHack(new Blink());
        addHack(new Scaffold());
        addHack(new Speed());
        addHack(new BlockOverlay());
        addHack(new PluginsGetter());
        addHack(new PlayerRadar());
        addHack(new AntiRain());
        addHack(new AutoWalk());
        addHack(new AutoSwim());
        addHack(new FastBreak());
        addHack(new Disconnect());
        addHack(new HUD());
        addHack(new ClickGui());
    }
    
    public static ArrayList<Modules> getHacks() {
        return HackManager.hacks;
    }
    
    public static void onPlayerTick(final TickEvent.PlayerTickEvent llIIIIIIllIlIll) {
        final float llIIIIIIllIlIIl = (float)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIIllIlIIl).hasNext() ? 1 : 0)) {
            final Modules llIIIIIIllIllII = ((Iterator<Modules>)llIIIIIIllIlIIl).next();
            if (lIIIlIlIll(llIIIIIIllIllII.isToggled() ? 1 : 0)) {
                llIIIIIIllIllII.onPlayerTick(llIIIIIIllIlIll);
            }
            "".length();
            if (null != null) {
                return;
            }
        }
    }
    
    static {
        HackManager.toggleHack = null;
    }
    
    public static void onProjectileImpact(final ProjectileImpactEvent llIIIIIIllllIll) {
        final byte llIIIIIIllllIIl = (byte)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIIllllIIl).hasNext() ? 1 : 0)) {
            final Modules llIIIIIIlllllII = ((Iterator<Modules>)llIIIIIIllllIIl).next();
            if (lIIIlIlIll(llIIIIIIlllllII.isToggled() ? 1 : 0)) {
                llIIIIIIlllllII.onProjectileImpact(llIIIIIIllllIll);
            }
            "".length();
            if (-"  ".length() > 0) {
                return;
            }
        }
    }
    
    public static void onRenderWorldLast(final RenderWorldLastEvent llIIIIIIlIlIIll) {
        final byte llIIIIIIlIlIIIl = (byte)getHacks().iterator();
        while (lIIIlIlIll(((Iterator)llIIIIIIlIlIIIl).hasNext() ? 1 : 0)) {
            final Modules llIIIIIIlIlIlII = ((Iterator<Modules>)llIIIIIIlIlIIIl).next();
            if (lIIIlIlIll(llIIIIIIlIlIlII.isToggled() ? 1 : 0)) {
                llIIIIIIlIlIlII.onRenderWorldLast(llIIIIIIlIlIIll);
            }
            "".length();
            if (((0x66 ^ 0x51 ^ (0xA3 ^ 0x9C)) & (0x51 ^ 0x38 ^ (0x15 ^ 0x74) ^ -" ".length())) != ((0x5E ^ 0x47 ^ (0xAA ^ 0x99)) & (81 + 63 - 137 + 173 ^ 107 + 73 - 94 + 72 ^ -" ".length()))) {
                return;
            }
        }
    }
    
    private static boolean lIIIlIlIll(final int llIIIIIIIlllllI) {
        return llIIIIIIIlllllI != 0;
    }
}
