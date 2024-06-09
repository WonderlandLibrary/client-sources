package com.client.glowclient.sponge.mixinutils;

import net.minecraft.client.*;
import net.minecraftforge.fml.client.*;
import com.client.glowclient.modules.other.*;
import com.mojang.authlib.*;
import java.util.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.player.*;
import net.minecraft.client.network.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.network.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import io.netty.util.concurrent.*;
import javax.annotation.*;
import com.client.glowclient.events.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.server.*;
import com.client.glowclient.*;
import net.minecraft.world.chunk.*;
import com.client.glowclient.modules.server.*;
import net.minecraft.client.multiplayer.*;
import com.client.glowclient.modules.render.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import com.client.glowclient.modules.movement.*;

public class HookTranslator
{
    public static boolean v19;
    public static boolean v21;
    public static boolean v2;
    public static boolean v15;
    public static boolean v1;
    public static Minecraft mc;
    public static boolean v16;
    public static boolean v5;
    public static boolean v22;
    public static boolean v6;
    public static boolean v11;
    public static boolean v8;
    public static boolean v9;
    public static boolean v20;
    public static boolean v7;
    public static boolean v4;
    public static boolean v18;
    public static boolean v10;
    public static boolean v17;
    public static boolean v14;
    public static boolean v13;
    public static boolean v12;
    public static boolean v3;
    
    public static boolean m3() {
        return Glow.b && ModuleManager.M("NameChanger") != null && ModuleManager.M("NameChanger").k();
    }
    
    public static ResourceLocation m15() {
        return hE.B;
    }
    
    static {
        HookTranslator.mc = FMLClientHandler.instance().getClient();
        HookTranslator.v1 = false;
        HookTranslator.v2 = false;
        HookTranslator.v3 = false;
        HookTranslator.v4 = false;
        HookTranslator.v5 = false;
        HookTranslator.v6 = false;
        HookTranslator.v7 = false;
        HookTranslator.v8 = true;
        HookTranslator.v9 = false;
        HookTranslator.v10 = false;
        HookTranslator.v11 = false;
        HookTranslator.v12 = false;
        HookTranslator.v13 = false;
        HookTranslator.v14 = false;
        HookTranslator.v15 = false;
        HookTranslator.v16 = false;
        HookTranslator.v17 = false;
        HookTranslator.v18 = false;
        HookTranslator.v19 = false;
        HookTranslator.v20 = false;
        HookTranslator.v21 = false;
        HookTranslator.v22 = false;
    }
    
    public static float m31() {
        return (float)HUD.red.k();
    }
    
    public static void m17(final GuiIngameMenu guiIngameMenu, final List<GuiButton> list) {
        ib.M(guiIngameMenu, list);
    }
    
    public static void m22(final int n, final int n2, final int n3) {
        ib.M.mouseClicked(n, n2, n3);
    }
    
    public HookTranslator() {
        super();
    }
    
    public static String m4(final GameProfile gameProfile) {
        if (ModuleManager.M("NameChanger") != null) {
            if (ModuleManager.M("NameChanger").k()) {
                final Iterator<Sc> iterator = xc.M().iterator();
                while (iterator.hasNext()) {
                    final StringValue stringValue;
                    if ((stringValue = (StringValue)iterator.next()).M().equals("NameChanger") && gameProfile.getName().equals(stringValue.A())) {
                        return stringValue.e();
                    }
                }
            }
            return "";
        }
        return gameProfile.getName();
    }
    
    public static boolean m24() {
        return EnchColors.mode.e().equals("Rainbow");
    }
    
    public static void m18(final GuiIngameMenu guiIngameMenu, final GuiButton guiButton) {
        ib.M(guiIngameMenu, guiButton);
    }
    
    public static void m54() {
        Stencil.e();
    }
    
    public static float m26() {
        return (float)aa.M(1L, 1.0f).getGreen();
    }
    
    public static boolean m58() {
        return EntityESP.everything.M();
    }
    
    public static void m62() {
        Ob.M().M();
    }
    
    public static double m43() {
        return Reach.distance.k();
    }
    
    public static float m25() {
        return (float)aa.M(1L, 1.0f).getRed();
    }
    
    public static boolean m57() {
        return EntityESP.hostile.M();
    }
    
    public static float m27() {
        return (float)aa.M(1L, 1.0f).getBlue();
    }
    
    public static float m33() {
        return (float)HUD.blue.k();
    }
    
    public static void m50() {
        Stencil.A();
    }
    
    public static boolean m35() {
        return EnchColors.mode.e().equals("HUD");
    }
    
    public static void m38(final NetHandlerPlayClient netHandlerPlayClient, final SPacketBlockAction sPacketBlockAction) {
        ib.M(netHandlerPlayClient, sPacketBlockAction);
    }
    
    public static void m21(final char c, final int n) {
        ib.M.textboxKeyTyped(c, n);
        uc.k.setProperty("RandomSeed", ib.M.getText());
        uc.a();
    }
    
    public static void m41(final Packet<?> packet, final INetHandler netHandler) {
        final EventServerPacket eventServerPacket = new EventServerPacket(packet);
        MinecraftForge.EVENT_BUS.post((Event)eventServerPacket);
        if (eventServerPacket.isCanceled()) {
            return;
        }
        eventServerPacket.getPacket().processPacket(netHandler);
    }
    
    public static void m10(final GuiTextField guiTextField, final int n, final CallbackInfo callbackInfo) {
        uA.M(guiTextField, n, callbackInfo);
    }
    
    public static void m53() {
        Stencil.D();
    }
    
    public static void m37(final NetHandlerPlayClient netHandlerPlayClient, final SPacketChat sPacketChat) {
        ib.M(netHandlerPlayClient, sPacketChat);
    }
    
    public static NetworkManager m16() {
        return FMLClientHandler.instance().getClientToServerNetworkManager();
    }
    
    public static float m30() {
        return (float)EnchColors.blue.k();
    }
    
    public static void m2(final RenderChunk renderChunk, final BlockRenderLayer blockRenderLayer) {
        gg.M(renderChunk, blockRenderLayer);
    }
    
    public static boolean m8() {
        return NoSlow.mode.e().equals("AAC");
    }
    
    public static void m44(final EntityBoat entityBoat, final float n) {
        gg.M(entityBoat, n);
    }
    
    public static void m42(final NetworkManager networkManager, final Packet<?> packet, @Nullable final GenericFutureListener<? extends Future<? super Void>>[] array) {
        final EventClientPacket eventClientPacket = new EventClientPacket(packet);
        MinecraftForge.EVENT_BUS.post((Event)eventClientPacket);
        if (eventClientPacket.isCanceled()) {
            return;
        }
        networkManager.dispatchPacket((Packet)eventClientPacket.getPacket(), (GenericFutureListener[])array);
    }
    
    public static void m40(final NetHandlerPlayClient netHandlerPlayClient, final SPacketCustomPayload sPacketCustomPayload) {
        ib.M(netHandlerPlayClient, sPacketCustomPayload);
    }
    
    public static float m28() {
        return (float)EnchColors.red.k();
    }
    
    public static void m19() {
        ib.k();
    }
    
    public static double m14() {
        return SignMod.length.M();
    }
    
    public static void m1(final Block block, final IBlockState blockState, final World world, final BlockPos blockPos, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, @Nullable final Entity entity, final boolean b) {
        gg.M(block, blockState, world, blockPos, axisAlignedBB, list, entity, b);
    }
    
    public static void m52() {
        Stencil.M();
    }
    
    public static void m60(final WorldClient worldClient, final int n, final int n2, final boolean b) {
        ib.M(worldClient, n, n2, b);
    }
    
    public static int m46() {
        return ga.M(aa.M(1L, 1.0f).getRed(), aa.M(1L, 1.0f).getGreen(), aa.M(1L, 1.0f).getBlue(), 255);
    }
    
    public static boolean m23() {
        return ModuleManager.M("EnchColors").k();
    }
    
    public static int m48() {
        return ga.M(HUD.red.M(), HUD.green.M(), HUD.blue.M(), 255);
    }
    
    public static boolean m66() throws Exception {
        return Ob.D("https://raw.githubusercontent.com/GlowClient/GlowClient.github.io/master/hwid.txt") && Ob.M("https://raw.githubusercontent.com/GlowClient/GlowClient.github.io/master/Enabled.txt");
    }
    
    public static int m47() {
        return ga.M(EnchColors.red.M(), EnchColors.green.M(), EnchColors.blue.M(), 255);
    }
    
    public static void m39(final NetHandlerPlayClient netHandlerPlayClient, final SPacketMaps sPacketMaps) {
        ib.M(netHandlerPlayClient, sPacketMaps);
    }
    
    public static void m63() {
        Lg.B();
    }
    
    public static void m36(final Chunk chunk) {
        gg.M(chunk);
    }
    
    public static double m12() {
        return AutoReconnect.delay.k();
    }
    
    public static void m51() {
        Stencil.E();
    }
    
    public static void m59(final WorldClient worldClient) {
        ib.M(worldClient);
    }
    
    public static ServerData m64() {
        return Lg.B;
    }
    
    public static void m20() {
        ib.M.updateCursorCounter();
    }
    
    public static boolean m55() {
        return EntityESP.players.M();
    }
    
    public static boolean m56() {
        return EntityESP.passive.M();
    }
    
    public static boolean m13() {
        return ModuleManager.M("AutoReconnect").k();
    }
    
    public static double m9() {
        return CameraClip.zoom.k();
    }
    
    public static void m65(final BlockPos blockPos, final IBlockState blockState, final IBlockAccess blockAccess, final BufferBuilder bufferBuilder) {
        gg.M(blockPos, blockState, blockAccess, bufferBuilder);
    }
    
    public static void m61(final WorldClient worldClient, final int n) {
        ib.M(worldClient, n);
    }
    
    public static boolean m34() {
        return EnchColors.mode.e().equals("RGB");
    }
    
    public static void m6() {
        gg.M();
    }
    
    public static void m7() {
        gg.D();
    }
    
    public static double m45() {
        return Speed.G;
    }
    
    public static boolean m49() {
        return ModuleManager.M("EntityESP").k() && EntityESP.mode.e().equals("Outline");
    }
    
    public static float m32() {
        return (float)HUD.green.k();
    }
    
    public static ServerData m11() {
        return AutoReconnect.A;
    }
    
    public static float m29() {
        return (float)EnchColors.green.k();
    }
    
    public static String m5(final String s) {
        final Iterator<Sc> iterator = xc.M().iterator();
        while (iterator.hasNext()) {
            final StringValue stringValue;
            if ((stringValue = (StringValue)iterator.next()).M().equals("NameChanger") && s.equals(stringValue.A())) {
                return stringValue.e();
            }
        }
        return "";
    }
}
