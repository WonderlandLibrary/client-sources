// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst;

import java.util.Iterator;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import com.krazzzzymonkey.catalyst.gui.click.ClickGuiScreen;
import com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui;
import com.krazzzzymonkey.catalyst.module.modules.combat.AntiBot;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import com.krazzzzymonkey.catalyst.module.Modules;
import com.krazzzzymonkey.catalyst.utils.system.Connection;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import com.krazzzzymonkey.catalyst.managers.HackManager;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class EventsHandler
{
    private /* synthetic */ boolean isInit;
    private static final /* synthetic */ String[] llI;
    private static final /* synthetic */ int[] llII;
    
    @SubscribeEvent
    public void onItemPickup(final EntityItemPickupEvent lllllIIIIllIIlI) {
        if (!llIlII(Wrapper.INSTANCE.player()) || llIIll(Wrapper.INSTANCE.world())) {
            return;
        }
        try {
            HackManager.onItemPickup(lllllIIIIllIIlI);
            "".length();
            if ("   ".length() < " ".length()) {
                return;
            }
        }
        catch (RuntimeException lllllIIIIllIlII) {
            lllllIIIIllIlII.printStackTrace();
            ChatUtils.error(EventsHandler.llI[EventsHandler.llII[2]]);
            ChatUtils.error(lllllIIIIllIlII.toString());
            Wrapper.INSTANCE.copy(lllllIIIIllIlII.toString());
        }
    }
    
    private static String lIIll(final String llllIlllllIlllI, final String llllIlllllIllIl) {
        try {
            final SecretKeySpec llllIllllllIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIlllllIllIl.getBytes(StandardCharsets.UTF_8)), EventsHandler.llII[8]), "DES");
            final Cipher llllIllllllIIII = Cipher.getInstance("DES");
            llllIllllllIIII.init(EventsHandler.llII[2], llllIllllllIIIl);
            return new String(llllIllllllIIII.doFinal(Base64.getDecoder().decode(llllIlllllIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIlllllIllll) {
            llllIlllllIllll.printStackTrace();
            return null;
        }
    }
    
    @SubscribeEvent
    public void onRenderWorldLast(final RenderWorldLastEvent lllllIIIIIIIlIl) {
        if (!llIlII(Wrapper.INSTANCE.player()) || !llIlII(Wrapper.INSTANCE.world()) || llIIlI(Wrapper.INSTANCE.mcSettings().hideGUI ? 1 : 0)) {
            return;
        }
        try {
            HackManager.onRenderWorldLast(lllllIIIIIIIlIl);
            "".length();
            if ((0x10 ^ 0x15) == 0x0) {
                return;
            }
        }
        catch (RuntimeException lllllIIIIIIlIII) {
            lllllIIIIIIlIII.printStackTrace();
            ChatUtils.error(EventsHandler.llI[EventsHandler.llII[9]]);
            ChatUtils.error(lllllIIIIIIlIII.toString());
            Wrapper.INSTANCE.copy(lllllIIIIIIlIII.toString());
        }
    }
    
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent lllllIIIIIIllIl) {
        if (!llIlII(Wrapper.INSTANCE.player()) || llIIll(Wrapper.INSTANCE.world())) {
            return;
        }
        try {
            HackManager.onLivingUpdate(lllllIIIIIIllIl);
            "".length();
            if (null != null) {
                return;
            }
        }
        catch (RuntimeException lllllIIIIIIllll) {
            lllllIIIIIIllll.printStackTrace();
            ChatUtils.error(EventsHandler.llI[EventsHandler.llII[8]]);
            ChatUtils.error(lllllIIIIIIllll.toString());
            Wrapper.INSTANCE.copy(lllllIIIIIIllll.toString());
        }
    }
    
    private static boolean llIlII(final Object llllIlllIlllllI) {
        return llllIlllIlllllI != null;
    }
    
    private static void llIIIl() {
        (llII = new int[13])[0] = ("   ".length() & ~"   ".length());
        EventsHandler.llII[1] = " ".length();
        EventsHandler.llII[2] = "  ".length();
        EventsHandler.llII[3] = "   ".length();
        EventsHandler.llII[4] = (0xB6 ^ 0xB2);
        EventsHandler.llII[5] = (0x1B ^ 0x1E);
        EventsHandler.llII[6] = (0x34 ^ 0x17 ^ (0x97 ^ 0xB2));
        EventsHandler.llII[7] = (27 + 50 + 54 + 27 ^ 105 + 125 - 97 + 20);
        EventsHandler.llII[8] = (0xB8 ^ 0xB0);
        EventsHandler.llII[9] = (0xAD ^ 0xA4);
        EventsHandler.llII[10] = (0x39 ^ 0x32 ^ " ".length());
        EventsHandler.llII[11] = (0xB6 ^ 0xBD);
        EventsHandler.llII[12] = (0x3E ^ 0x32);
    }
    
    private static boolean llIIlI(final int llllIlllIlllIlI) {
        return llllIlllIlllIlI != 0;
    }
    
    public boolean onPacket(final Object lllllIIIlIIlIll, final Connection.Side lllllIIIlIIlIlI) {
        boolean lllllIIIlIIlIIl = EventsHandler.llII[1] != 0;
        final int lllllIIIlIIIlIl = (int)HackManager.getHacks().iterator();
        while (llIIlI(((Iterator)lllllIIIlIIIlIl).hasNext() ? 1 : 0)) {
            final Modules lllllIIIlIIllIl = ((Iterator<Modules>)lllllIIIlIIIlIl).next();
            if (llIIlI(lllllIIIlIIllIl.isToggled() ? 1 : 0)) {
                if (llIIll(Wrapper.INSTANCE.world())) {
                    "".length();
                    if (-(0x9D ^ 0x99) > 0) {
                        return ((0xD3 ^ 0x8A) & ~(0xD ^ 0x54)) != 0x0;
                    }
                    continue;
                }
                else {
                    lllllIIIlIIlIIl &= lllllIIIlIIllIl.onPacket(lllllIIIlIIlIll, lllllIIIlIIlIlI);
                    "".length();
                    if (null != null) {
                        return ((0xD6 ^ 0xB2 ^ (0x37 ^ 0x19)) & (0xDD ^ 0xC3 ^ (0xD9 ^ 0x8D) ^ -" ".length())) != 0x0;
                    }
                    continue;
                }
            }
        }
        return lllllIIIlIIlIIl;
    }
    
    @SubscribeEvent
    public void onProjectileImpact(final ProjectileImpactEvent lllllIIIIlIlIlI) {
        if (!llIlII(Wrapper.INSTANCE.player()) || llIIll(Wrapper.INSTANCE.world())) {
            return;
        }
        try {
            HackManager.onProjectileImpact(lllllIIIIlIlIlI);
            "".length();
            if ("  ".length() <= " ".length()) {
                return;
            }
        }
        catch (RuntimeException lllllIIIIlIllIl) {
            lllllIIIIlIllIl.printStackTrace();
            ChatUtils.error(EventsHandler.llI[EventsHandler.llII[3]]);
            ChatUtils.error(lllllIIIIlIllIl.toString());
            Wrapper.INSTANCE.copy(lllllIIIIlIllIl.toString());
        }
    }
    
    @SubscribeEvent
    public void onAttackEntity(final AttackEntityEvent lllllIIIIlIIlII) {
        if (!llIlII(Wrapper.INSTANCE.player()) || llIIll(Wrapper.INSTANCE.world())) {
            return;
        }
        try {
            HackManager.onAttackEntity(lllllIIIIlIIlII);
            "".length();
            if (" ".length() == "  ".length()) {
                return;
            }
        }
        catch (RuntimeException lllllIIIIlIIllI) {
            lllllIIIIlIIllI.printStackTrace();
            ChatUtils.error(EventsHandler.llI[EventsHandler.llII[4]]);
            ChatUtils.error(lllllIIIIlIIllI.toString());
            Wrapper.INSTANCE.copy(lllllIIIIlIIllI.toString());
        }
    }
    
    @SubscribeEvent
    public void onRenderGameOverlay(final RenderGameOverlayEvent.Text llllIllllllllll) {
        if (!llIlII(Wrapper.INSTANCE.player()) || llIIll(Wrapper.INSTANCE.world())) {
            return;
        }
        try {
            HackManager.onRenderGameOverlay(llllIllllllllll);
            "".length();
            if (((0x78 ^ 0x5D) & ~(0x77 ^ 0x52)) >= "   ".length()) {
                return;
            }
        }
        catch (RuntimeException lllllIIIIIIIIIl) {
            lllllIIIIIIIIIl.printStackTrace();
            ChatUtils.error(EventsHandler.llI[EventsHandler.llII[10]]);
            ChatUtils.error(lllllIIIIIIIIIl.toString());
            Wrapper.INSTANCE.copy(lllllIIIIIIIIIl.toString());
        }
    }
    
    private static boolean llIlll(final int llllIllllIIIIIl, final int llllIllllIIIIII) {
        return llllIllllIIIIIl < llllIllllIIIIII;
    }
    
    @SubscribeEvent
    public void onCameraSetup(final EntityViewRenderEvent.CameraSetup lllllIIIIlllIIl) {
        if (!llIlII(Wrapper.INSTANCE.player()) || llIIll(Wrapper.INSTANCE.world())) {
            return;
        }
        try {
            HackManager.onCameraSetup(lllllIIIIlllIIl);
            "".length();
            if (-" ".length() == ("   ".length() & ~"   ".length())) {
                return;
            }
        }
        catch (RuntimeException lllllIIIIlllIll) {
            lllllIIIIlllIll.printStackTrace();
            ChatUtils.error(EventsHandler.llI[EventsHandler.llII[1]]);
            ChatUtils.error(lllllIIIIlllIll.toString());
            Wrapper.INSTANCE.copy(lllllIIIIlllIll.toString());
        }
    }
    
    private static String lIIlI(String llllIllllIllIIl, final String llllIllllIlllIl) {
        llllIllllIllIIl = new String(Base64.getDecoder().decode(llllIllllIllIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llllIllllIlllII = new StringBuilder();
        final char[] llllIllllIllIll = llllIllllIlllIl.toCharArray();
        int llllIllllIllIlI = EventsHandler.llII[0];
        final String llllIllllIlIlII = (Object)llllIllllIllIIl.toCharArray();
        final String llllIllllIlIIll = (String)llllIllllIlIlII.length;
        float llllIllllIlIIlI = EventsHandler.llII[0];
        while (llIlll((int)llllIllllIlIIlI, (int)llllIllllIlIIll)) {
            final char llllIllllIlllll = llllIllllIlIlII[llllIllllIlIIlI];
            llllIllllIlllII.append((char)(llllIllllIlllll ^ llllIllllIllIll[llllIllllIllIlI % llllIllllIllIll.length]));
            "".length();
            ++llllIllllIllIlI;
            ++llllIllllIlIIlI;
            "".length();
            if ((0x72 ^ 0x76) == -" ".length()) {
                return null;
            }
        }
        return String.valueOf(llllIllllIlllII);
    }
    
    private static void llIIII() {
        (llI = new String[EventsHandler.llII[12]])[EventsHandler.llII[0]] = lIIl("AwabPRxOwkp+6yGaebCtnz/crxr8nIPN3yfQdelwMas=", "aQUSD");
        EventsHandler.llI[EventsHandler.llII[1]] = lIIlI("PSMNPB8CMyYwFQomFyEZAWxDJxgsNw4tBA4FBjwDHw==", "oVcHv");
        EventsHandler.llI[EventsHandler.llII[2]] = lIIll("q0h27UXGeVTinHwD3Ruq/e7yt9gRkSmzBvZvUmvHup8=", "kcYTv");
        EventsHandler.llI[EventsHandler.llII[3]] = lIIl("4euuGZbB+MgdYgLE2SHgaaOXMYlbKDA6SaAnTCfFxXHeEz9au199vg==", "TFheK");
        EventsHandler.llI[EventsHandler.llII[4]] = lIIl("5CWKkf8Lqk+T1baNHNnLVOYMXF8rI4/sbkzIlwU5+tzw44nMJDV8SA==", "RpOvp");
        EventsHandler.llI[EventsHandler.llII[5]] = lIIl("P05Bk/U4+FyYSFXE0+MoKyHLES5A4J/fH0RpB5z/wdk=", "LRdKn");
        EventsHandler.llI[EventsHandler.llII[6]] = lIIl("uDMyWsPkXwvAEXMbJZrUKg==", "hqomN");
        EventsHandler.llI[EventsHandler.llII[7]] = lIIl("0kjfNFOTlzwTofm4/0vzpIgBxMvDzmbEkujlAGrxs9w=", "KmekI");
        EventsHandler.llI[EventsHandler.llII[8]] = lIIll("pBWM5GvPG5qoPblf69guV6vs3Pdnd4fUVUdOtDylMvzjDW5kvJfnxg==", "WauzM");
        EventsHandler.llI[EventsHandler.llII[9]] = lIIl("wcmd0IBUw0BQgJqPF4vfgvi332SUyPVkTvrDg4MFbbu6EO1rXJKXow==", "YumXT");
        EventsHandler.llI[EventsHandler.llII[10]] = lIIlI("Ix8aHgocDzESABQaAAMMH1BUBQ0jDxoOBgMtFQcGPhwRGA8QEw==", "qjtjc");
        EventsHandler.llI[EventsHandler.llII[11]] = lIIl("eW536ADw8yytH1r69c0DDamXp+iYwzu3axC3feXadHlNCVLyxbhzSw==", "wSNKG");
    }
    
    @SubscribeEvent
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock llllIllllllIlll) {
        if (!llIlII(Wrapper.INSTANCE.player()) || llIIll(Wrapper.INSTANCE.world())) {
            return;
        }
        try {
            HackManager.onLeftClickBlock(llllIllllllIlll);
            "".length();
            if (null != null) {
                return;
            }
        }
        catch (RuntimeException llllIlllllllIlI) {
            llllIlllllllIlI.printStackTrace();
            ChatUtils.error(EventsHandler.llI[EventsHandler.llII[11]]);
            ChatUtils.error(llllIlllllllIlI.toString());
            Wrapper.INSTANCE.copy(llllIlllllllIlI.toString());
        }
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent lllllIIIIIlIIll) {
        if (!llIlII(Wrapper.INSTANCE.player()) || llIIll(Wrapper.INSTANCE.world())) {
            AntiBot.bots.clear();
            this.isInit = (EventsHandler.llII[0] != 0);
            return;
        }
        try {
            if (llIllI(this.isInit ? 1 : 0)) {
                new Connection(this);
                "".length();
                ClickGui.setColor();
                this.isInit = (EventsHandler.llII[1] != 0);
            }
            if (llIllI((Wrapper.INSTANCE.mc().currentScreen instanceof ClickGuiScreen) ? 1 : 0)) {
                HackManager.getHack(EventsHandler.llI[EventsHandler.llII[6]]).setToggled((boolean)(EventsHandler.llII[0] != 0));
            }
            HackManager.onClientTick(lllllIIIIIlIIll);
            "".length();
            if ((0x82 ^ 0x87) <= 0) {
                return;
            }
        }
        catch (RuntimeException lllllIIIIIlIlll) {
            lllllIIIIIlIlll.printStackTrace();
            ChatUtils.error(EventsHandler.llI[EventsHandler.llII[7]]);
            ChatUtils.error(lllllIIIIIlIlll.toString());
            Wrapper.INSTANCE.copy(lllllIIIIIlIlll.toString());
        }
    }
    
    private static boolean llIIll(final Object llllIlllIllllII) {
        return llllIlllIllllII == null;
    }
    
    private static String lIIl(final String llllIllllIIIlll, final String llllIllllIIIllI) {
        try {
            final SecretKeySpec llllIllllIIllII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIllllIIIllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llllIllllIIlIll = Cipher.getInstance("Blowfish");
            llllIllllIIlIll.init(EventsHandler.llII[2], llllIllllIIllII);
            return new String(llllIllllIIlIll.doFinal(Base64.getDecoder().decode(llllIllllIIIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIllllIIlIlI) {
            llllIllllIIlIlI.printStackTrace();
            return null;
        }
    }
    
    static {
        llIIIl();
        llIIII();
    }
    
    private static boolean llIllI(final int llllIlllIlllIII) {
        return llllIlllIlllIII == 0;
    }
    
    public EventsHandler() {
        this.isInit = (EventsHandler.llII[0] != 0);
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent lllllIIIIllllll) {
        if (!llIlII(Wrapper.INSTANCE.player()) || llIIll(Wrapper.INSTANCE.world())) {
            return;
        }
        try {
            final int lllllIIIlIIIIlI = Keyboard.getEventKey();
            if (llIIlI(Keyboard.getEventKeyState() ? 1 : 0)) {
                HackManager.onKeyPressed(lllllIIIlIIIIlI);
            }
            "".length();
            if (((0x91 ^ 0xAA) & ~(0x78 ^ 0x43)) != 0x0) {
                return;
            }
        }
        catch (RuntimeException lllllIIIlIIIIIl) {
            lllllIIIlIIIIIl.printStackTrace();
            ChatUtils.error(EventsHandler.llI[EventsHandler.llII[0]]);
            ChatUtils.error(lllllIIIlIIIIIl.toString());
            Wrapper.INSTANCE.copy(lllllIIIlIIIIIl.toString());
        }
    }
    
    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent lllllIIIIIlllIl) {
        if (!llIlII(Wrapper.INSTANCE.player()) || llIIll(Wrapper.INSTANCE.world())) {
            return;
        }
        try {
            HackManager.onPlayerTick(lllllIIIIIlllIl);
            "".length();
            if (" ".length() <= ((74 + 88 - 112 + 130 ^ 102 + 23 - 16 + 29) & (77 + 4 - 53 + 150 ^ 3 + 63 + 25 + 49 ^ -" ".length()))) {
                return;
            }
        }
        catch (RuntimeException lllllIIIIIlllll) {
            lllllIIIIIlllll.printStackTrace();
            ChatUtils.error(EventsHandler.llI[EventsHandler.llII[5]]);
            ChatUtils.error(lllllIIIIIlllll.toString());
            Wrapper.INSTANCE.copy(lllllIIIIIlllll.toString());
        }
    }
}
