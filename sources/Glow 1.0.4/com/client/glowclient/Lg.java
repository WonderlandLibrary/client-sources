package com.client.glowclient;

import net.minecraft.client.multiplayer.*;
import joptsimple.internal.*;
import com.mojang.authlib.*;
import com.google.common.util.concurrent.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.world.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.gameevent.*;
import com.client.glowclient.modules.*;
import org.lwjgl.input.*;
import java.util.function.*;
import net.minecraft.client.network.*;
import java.util.regex.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.*;
import com.client.glowclient.utils.*;
import net.minecraft.entity.*;
import com.client.glowclient.events.*;

public class Lg extends rF
{
    private static final La G;
    private static final Pattern[] d;
    private static final Pattern[] L;
    private static final Pattern[] A;
    public static ServerData B;
    private static final ZC b;
    
    @SubscribeEvent
    public void D(final EventServerPacket eventServerPacket) {
        try {
            final String unformattedText;
            if (eventServerPacket.getPacket() instanceof SPacketChat && !Strings.isNullOrEmpty(unformattedText = ((SPacketChat)eventServerPacket.getPacket()).getChatComponent().getUnformattedText())) {
                if (M(unformattedText, Lg.d, this::M)) {
                    return;
                }
                if (M(unformattedText, Lg.A, this::D)) {
                    return;
                }
                if (M(unformattedText, Lg.L, this::A)) {
                    return;
                }
            }
        }
        catch (Exception ex) {}
    }
    
    private void A(final GameProfile gameProfile, final String s) {
        r.M(gameProfile.getName(), (FutureCallback<R>)new oE(this, s));
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void M(final RenderGameOverlayEvent$Text renderGameOverlayEvent$Text) {
        if (renderGameOverlayEvent$Text.getType().equals((Object)RenderGameOverlayEvent$ElementType.TEXT)) {
            MinecraftForge.EVENT_BUS.post((Event)new EventRenderScreen(renderGameOverlayEvent$Text.getPartialTicks()));
        }
    }
    
    @SubscribeEvent
    public void M(final LivingEvent$LivingUpdateEvent livingEvent$LivingUpdateEvent) {
        if (Wrapper.mc.world != null && livingEvent$LivingUpdateEvent.getEntityLiving().equals((Object)Wrapper.mc.player)) {
            final EventUpdate eventUpdate = new EventUpdate(livingEvent$LivingUpdateEvent.getEntityLiving());
            MinecraftForge.EVENT_BUS.post((Event)eventUpdate);
            livingEvent$LivingUpdateEvent.setCanceled(((Event)eventUpdate).isCanceled());
        }
    }
    
    private void D(final GameProfile gameProfile, final String s) {
        r.M(gameProfile.getName(), (FutureCallback<R>)new se(this, s));
    }
    
    @SubscribeEvent
    public void M(final EventServerPacket eventServerPacket) {
        if (eventServerPacket.getPacket() instanceof SPacketPlayerListItem && Wrapper.mc.world != null && System.currentTimeMillis() > -1L) {
            final SPacketPlayerListItem sPacketPlayerListItem = (SPacketPlayerListItem)eventServerPacket.getPacket();
            sPacketPlayerListItem.getEntries().stream().filter(Objects::nonNull).filter(Lg::D).filter(Lg::M).forEach(this::M);
        }
    }
    
    @SubscribeEvent
    public void D(final WorldEvent$Unload worldEvent$Unload) {
        B();
    }
    
    @SubscribeEvent
    public void M(final WorldEvent$Load worldEvent$Load) {
        if (Wrapper.mc.world != null) {
            worldEvent$Load.getWorld().addEventListener((IWorldEventListener)Lg.b);
            MinecraftForge.EVENT_BUS.post((Event)new EventWorld(worldEvent$Load.getWorld()));
        }
    }
    
    private void M(final GameProfile gameProfile, final String s) {
        r.M(gameProfile.getName(), (FutureCallback<R>)new ef(this, s));
    }
    
    static {
        b = new ZC();
        G = new La();
        d = new Pattern[] { Pattern.compile("<(.*?)> (.*)") };
        A = new Pattern[] { Pattern.compile("(.*?) whispers to you: (.*)"), Pattern.compile("(.*?) whispers: (.*)") };
        L = new Pattern[] { Pattern.compile("[Tt]o (.*?): (.*)") };
    }
    
    private static boolean D(final SPacketPlayerListItem$AddPlayerData sPacketPlayerListItem$AddPlayerData) {
        return sPacketPlayerListItem$AddPlayerData.getProfile() != null;
    }
    
    @SubscribeEvent
    public void M(final GuiOpenEvent guiOpenEvent) {
        B();
    }
    
    public static void B() {
        final ServerData currentServerData;
        if ((currentServerData = Wrapper.mc.getCurrentServerData()) != null) {
            Lg.B = currentServerData;
        }
    }
    
    @SubscribeEvent
    public void M(final WorldEvent$Unload worldEvent$Unload) {
        if (Wrapper.mc.world != null) {
            MinecraftForge.EVENT_BUS.post((Event)new EventWorld(worldEvent$Unload.getWorld()));
        }
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        final Iterator<NF> iterator = ModuleManager.M().iterator();
        while (iterator.hasNext()) {
            if (((Module)iterator.next()).k) {
                y.M();
            }
        }
    }
    
    @SubscribeEvent
    public void M(final Cd cd) {
        final Packet<?> packet;
        if ((packet = cd.getPacket()) instanceof CPacketPlayer) {
            final Iterator<NF> iterator = ModuleManager.M().iterator();
            while (iterator.hasNext()) {
                if (((Module)iterator.next()).k) {
                    ((CPacketPlayer)packet).yaw = y.b;
                    ((CPacketPlayer)packet).pitch = y.A;
                }
            }
        }
    }
    
    private static boolean M(final SPacketPlayerListItem$AddPlayerData sPacketPlayerListItem$AddPlayerData) {
        return !Strings.isNullOrEmpty(sPacketPlayerListItem$AddPlayerData.getProfile().getName());
    }
    
    private void M(final SPacketPlayerListItem sPacketPlayerListItem, final SPacketPlayerListItem$AddPlayerData sPacketPlayerListItem$AddPlayerData) {
        r.M(sPacketPlayerListItem$AddPlayerData.getProfile().getName(), (FutureCallback<R>)new yE(this, sPacketPlayerListItem, sPacketPlayerListItem$AddPlayerData));
    }
    
    @SubscribeEvent
    public void M(final InputEvent$KeyInputEvent inputEvent$KeyInputEvent) {
        final Iterator<NF> iterator = ModuleManager.M().iterator();
        while (iterator.hasNext()) {
            final Module module;
            if ((module = (Module)iterator.next()) instanceof ModuleContainer && module.M() != -1 && Keyboard.isKeyDown(module.M()) && Keyboard.getEventKey() == module.M() && Keyboard.getEventKeyState()) {
                ((ModuleContainer)module).B();
            }
        }
    }
    
    private static boolean M(final String s, Pattern[] array, final BiConsumer<GameProfile, String> biConsumer) {
        try {
            String group2 = null;
            Block_6: {
                for (int length = (array = array).length, i = 0; i < length; ++i) {
                    final Matcher matcher;
                    if ((matcher = array[i].matcher(s)).find()) {
                        final Matcher matcher2 = matcher;
                        final String group = matcher2.group(1);
                        group2 = matcher2.group(2);
                        if (!Strings.isNullOrEmpty(group)) {
                            for (final NetworkPlayerInfo networkPlayerInfo : Wrapper.mc.player.connection.getPlayerInfoMap()) {
                                if (String.CASE_INSENSITIVE_ORDER.compare(group, networkPlayerInfo.getGameProfile().getName()) == 0) {
                                    break Block_6;
                                }
                            }
                        }
                    }
                }
                return false;
            }
            NetworkPlayerInfo networkPlayerInfo = null;
            biConsumer.accept(networkPlayerInfo.getGameProfile(), group2);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
        return false;
    }
    
    @SubscribeEvent
    public void M(final RenderWorldLastEvent renderWorldLastEvent) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1.0f);
        final EventRenderWorld eventRenderWorld;
        (eventRenderWorld = new EventRenderWorld(Lg.G, EntityUtils.D((Entity)Wrapper.mc.player, renderWorldLastEvent.getPartialTicks()))).resetTranslation();
        MinecraftForge.EVENT_BUS.post((Event)eventRenderWorld);
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
    
    public Lg() {
        super("EventMod", "Registers specific events branching off of other events");
    }
}
