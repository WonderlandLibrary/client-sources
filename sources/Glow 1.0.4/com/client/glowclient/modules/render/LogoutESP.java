package com.client.glowclient.modules.render;

import com.client.glowclient.utils.*;
import org.lwjgl.opengl.*;
import com.client.glowclient.modules.other.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import net.minecraft.network.play.server.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.*;
import com.mojang.authlib.*;
import net.minecraftforge.event.world.*;
import com.client.glowclient.modules.*;
import java.util.*;
import com.client.glowclient.events.*;
import net.minecraft.client.renderer.vertex.*;

public class LogoutESP extends ModuleContainer
{
    private final Set<Te> b;
    
    private static boolean M(final UUID uuid, final Te te) {
        return te.b.equals(uuid);
    }
    
    public void M(final String s, double n, double n2, final double n3, final double n4, final String s2, final String s3, final String s4) {
        final ca ca = new ca();
        float n5;
        if ((n5 = (float)n4 / 4.0f) < 1.6f) {
            n5 = 1.6f;
        }
        if (n5 > 10.0) {
            n5 = 10.0f;
        }
        final RenderManager renderManager = Wrapper.mc.getRenderManager();
        final float n6 = n5 * 0.01f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)n, (float)n2 + 1.4f, (float)n3);
        final float n7 = 1.0f;
        final float n8 = 0.0f;
        GL11.glNormal3f(n8, n7, n8);
        final float n9 = -renderManager.playerViewY;
        final float n10 = 1.0f;
        final float n11 = 0.0f;
        GL11.glRotatef(n9, n11, n10, n11);
        float n12;
        if (Wrapper.mc.gameSettings.thirdPersonView == 2) {
            n12 = n6;
            final float n13 = -renderManager.playerViewX;
            final float n14 = 1.0f;
            final float n15 = 0.0f;
            GL11.glRotatef(n13, n14, n15, n15);
        }
        else {
            final float playerViewX = renderManager.playerViewX;
            final float n16 = 1.0f;
            final float n17 = 0.0f;
            GL11.glRotatef(playerViewX, n16, n17, n17);
            n12 = n6;
        }
        GL11.glScalef(-n12, -n6, n6 / 100.0f);
        fd.M(2896, false);
        fd.M(2929, false);
        final int n18 = (int)Ia.M(HUD.F, new StringBuilder().insert(0, s).append("_Logout_(").append(s2).append(",").append(s3).append(",").append(s4).append(")").toString(), 1.0);
        fd.M(3042, true);
        GL11.glBlendFunc(770, 771);
        final int n19 = 9999;
        final int n20 = 1;
        ba.M(n19, n19, n20, n20, Integer.MIN_VALUE);
        n = -n18 / 2;
        n2 = -Ia.M(HUD.F) - 1.0;
        this.b.forEach((Consumer<? super Object>)PD::M);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        fd.M();
        final float n21 = 1.0f;
        GL11.glColor4f(n21, n21, n21, n21);
        GL11.glPopMatrix();
    }
    
    private float M(final Entity entity) {
        final float n = (float)(entity.posX - Wrapper.mc.player.posX);
        final float n2 = (float)(entity.posZ - Wrapper.mc.player.posZ);
        final float n3 = n;
        final float n4 = n3 * n3;
        final float n5 = n2;
        return MathHelper.sqrt(n4 + n5 * n5);
    }
    
    private boolean M(final SPacketPlayerListItem sPacketPlayerListItem, final SPacketPlayerListItem$AddPlayerData sPacketPlayerListItem$AddPlayerData) {
        final String m;
        return (!Strings.isNullOrEmpty(m = this.M(sPacketPlayerListItem$AddPlayerData.getProfile())) && !this.M(m)) || sPacketPlayerListItem.getAction().equals((Object)SPacketPlayerListItem$Action.REMOVE_PLAYER);
    }
    
    private void M(final SPacketPlayerListItem sPacketPlayerListItem, final SPacketPlayerListItem$AddPlayerData sPacketPlayerListItem$AddPlayerData) {
        this.M(sPacketPlayerListItem$AddPlayerData.getProfile());
        final UUID id = sPacketPlayerListItem$AddPlayerData.getProfile().getId();
        switch (YE.b[sPacketPlayerListItem.getAction().ordinal()]) {
            case 1: {
                while (false) {}
                this.b.removeIf((Predicate<? super Object>)PD::M);
            }
            case 2: {
                final EntityPlayer playerEntityByUUID;
                if ((playerEntityByUUID = Wrapper.mc.world.getPlayerEntityByUUID(id)) != null) {
                    final AxisAlignedBB entityBoundingBox = playerEntityByUUID.getEntityBoundingBox();
                    this.b.add(new Te(this, new Vec3d[] { new Vec3d(entityBoundingBox.minX, entityBoundingBox.minY, entityBoundingBox.minZ), new Vec3d(entityBoundingBox.maxX, entityBoundingBox.maxY, entityBoundingBox.maxZ) }, id, playerEntityByUUID.getName(), null));
                    break;
                }
                break;
            }
        }
    }
    
    private static boolean M(final Te te) {
        Wrapper.mc.player.getDistance((te.A[0].x + te.A[1].x) / 2.0, te.A[0].y, (te.A[0].z + te.A[1].z) / 2.0);
        return false;
    }
    
    private static void M(final String s, String string, final String s2, final String s3, final ca ca, final double n, final double n2, final Te te) {
        string = new StringBuilder().insert(0, s).append("_Logout_(").append(string).append(",").append(s2).append(",").append(s3).append(")").toString();
        if (HUD.F == null) {
            ca.K().M(HUD.F).D(ga.K).M(string, n, n2, true);
            return;
        }
        ca.K().M(HUD.F).D(ga.K).M(string, n + 1.0, n2 + 1.0, true).D(ga.K).M(string, n, n2);
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        this.b.removeIf(PD::M);
    }
    
    @SubscribeEvent
    public void M(final WorldEvent$Unload worldEvent$Unload) {
        this.b.clear();
    }
    
    private void M(double n, double n2, double n3, final Te te) {
        Ma.M(te.A[1].x - 0.6 - n, te.A[1].y - 1.8 - n2, te.A[1].z - 0.6 - n3, 0.0, 0.41, 1.0);
        n = te.A[1].x - 0.6 - n;
        n2 = te.A[1].y - 1.8 - n2;
        n3 = te.A[1].z - 0.6 - n3;
        this.M(te.B, n + 0.3, n2 + 0.45, n3 + 0.3, Wrapper.mc.player.getDistance((te.A[0].x + te.A[1].x) / 2.0, te.A[0].y, (te.A[0].z + te.A[1].z) / 2.0), String.format("%.0f", te.A[1].x - 0.6), String.format("%.0f", te.A[1].y), String.format("%.0f", te.A[1].z - 0.6));
    }
    
    @SubscribeEvent
    public void M(final EventServerPacket eventServerPacket) {
        if (eventServerPacket.getPacket() instanceof SPacketPlayerListItem) {
            final SPacketPlayerListItem sPacketPlayerListItem;
            if (!(sPacketPlayerListItem = (SPacketPlayerListItem)eventServerPacket.getPacket()).getAction().equals((Object)SPacketPlayerListItem$Action.REMOVE_PLAYER)) {
                if (!sPacketPlayerListItem.getAction().equals((Object)SPacketPlayerListItem$Action.ADD_PLAYER)) {
                    return;
                }
            }
            try {
                sPacketPlayerListItem.getEntries().stream().filter(Objects::nonNull).filter(this::M).forEach(this::M);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private String M(final GameProfile gameProfile) {
        if (Objects.nonNull(gameProfile)) {
            return gameProfile.getName();
        }
        return "";
    }
    
    @SubscribeEvent
    public void M(final WorldEvent$Load worldEvent$Load) {
        this.b.clear();
    }
    
    private boolean M(final String s) {
        return Objects.nonNull(Wrapper.mc.player) && Wrapper.mc.player.getDisplayName().getUnformattedText().equals(s);
    }
    
    public LogoutESP() {
        super(Category.RENDER, "LogoutESP", false, -1, "Draws outline at player logout spots");
        this.b = new HashSet<Te>();
    }
    
    @Override
    public boolean A() {
        return false;
    }
    
    @SubscribeEvent
    public void M(final EventRenderWorld eventRenderWorld) {
        try {
            final double renderPosX = Wrapper.mc.getRenderManager().renderPosX;
            final double renderPosY = Wrapper.mc.getRenderManager().renderPosY;
            final double renderPosZ = Wrapper.mc.getRenderManager().renderPosZ;
            eventRenderWorld.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            this.b.forEach((Consumer<? super Object>)this::M);
            eventRenderWorld.getTessellator().draw();
        }
        catch (Exception ex) {}
    }
}
