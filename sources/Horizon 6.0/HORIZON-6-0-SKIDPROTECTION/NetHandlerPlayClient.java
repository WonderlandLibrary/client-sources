package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import java.util.Collection;
import java.io.IOException;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.FutureCallback;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import io.netty.buffer.Unpooled;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.Random;
import java.util.Map;
import com.mojang.authlib.GameProfile;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayClient implements INetHandlerPlayClient
{
    private static final Logger Â;
    private final NetworkManager Ý;
    private final GameProfile Ø­áŒŠá;
    private final GuiScreen Âµá€;
    private Minecraft Ó;
    private WorldClient à;
    private boolean Ø;
    private final Map áŒŠÆ;
    public int HorizonCode_Horizon_È;
    private boolean áˆºÑ¢Õ;
    private final Random ÂµÈ;
    private static final String á = "CL_00000878";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public NetHandlerPlayClient(final Minecraft mcIn, final GuiScreen p_i46300_2_, final NetworkManager p_i46300_3_, final GameProfile p_i46300_4_) {
        this.áŒŠÆ = Maps.newHashMap();
        this.HorizonCode_Horizon_È = 20;
        this.áˆºÑ¢Õ = false;
        this.ÂµÈ = new Random();
        this.Ó = mcIn;
        this.Âµá€ = p_i46300_2_;
        this.Ý = p_i46300_3_;
        this.Ø­áŒŠá = p_i46300_4_;
    }
    
    public void HorizonCode_Horizon_È() {
        this.à = null;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S01PacketJoinGame packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        this.Ó.Âµá€ = new PlayerControllerMP(this.Ó, this);
        this.à = new WorldClient(this, new WorldSettings(0L, packetIn.Ý(), false, packetIn.Â(), packetIn.à()), packetIn.Ø­áŒŠá(), packetIn.Âµá€(), this.Ó.ÇŽÕ);
        this.Ó.ŠÄ.ÇŽÊ = packetIn.Âµá€();
        this.Ó.HorizonCode_Horizon_È(this.à);
        this.Ó.á.ÇªÔ = packetIn.Ø­áŒŠá();
        this.Ó.HorizonCode_Horizon_È(new GuiDownloadTerrain(this));
        this.Ó.á.Ø­áŒŠá(packetIn.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È = packetIn.Ó();
        this.Ó.á.áˆºÑ¢Õ(packetIn.Ø());
        this.Ó.Âµá€.HorizonCode_Horizon_È(packetIn.Ý());
        this.Ó.ŠÄ.Ý();
        this.Ý.HorizonCode_Horizon_È(new C17PacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).HorizonCode_Horizon_È(ClientBrandRetriever.HorizonCode_Horizon_È())));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S0EPacketSpawnObject packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final double var2 = packetIn.Â() / 32.0;
        final double var3 = packetIn.Ý() / 32.0;
        final double var4 = packetIn.Ø­áŒŠá() / 32.0;
        Object var5 = null;
        if (packetIn.áˆºÑ¢Õ() == 10) {
            var5 = EntityMinecart.HorizonCode_Horizon_È(this.à, var2, var3, var4, EntityMinecart.HorizonCode_Horizon_È.HorizonCode_Horizon_È(packetIn.ÂµÈ()));
        }
        else if (packetIn.áˆºÑ¢Õ() == 90) {
            final Entity var6 = this.à.HorizonCode_Horizon_È(packetIn.ÂµÈ());
            if (var6 instanceof EntityPlayer) {
                var5 = new EntityFishHook(this.à, var2, var3, var4, (EntityPlayer)var6);
            }
            packetIn.à(0);
        }
        else if (packetIn.áˆºÑ¢Õ() == 60) {
            var5 = new EntityArrow(this.à, var2, var3, var4);
        }
        else if (packetIn.áˆºÑ¢Õ() == 61) {
            var5 = new EntitySnowball(this.à, var2, var3, var4);
        }
        else if (packetIn.áˆºÑ¢Õ() == 71) {
            var5 = new EntityItemFrame(this.à, new BlockPos(MathHelper.Ý(var2), MathHelper.Ý(var3), MathHelper.Ý(var4)), EnumFacing.Â(packetIn.ÂµÈ()));
            packetIn.à(0);
        }
        else if (packetIn.áˆºÑ¢Õ() == 77) {
            var5 = new EntityLeashKnot(this.à, new BlockPos(MathHelper.Ý(var2), MathHelper.Ý(var3), MathHelper.Ý(var4)));
            packetIn.à(0);
        }
        else if (packetIn.áˆºÑ¢Õ() == 65) {
            var5 = new EntityEnderPearl(this.à, var2, var3, var4);
        }
        else if (packetIn.áˆºÑ¢Õ() == 72) {
            var5 = new EntityEnderEye(this.à, var2, var3, var4);
        }
        else if (packetIn.áˆºÑ¢Õ() == 76) {
            var5 = new EntityFireworkRocket(this.à, var2, var3, var4, null);
        }
        else if (packetIn.áˆºÑ¢Õ() == 63) {
            var5 = new EntityLargeFireball(this.à, var2, var3, var4, packetIn.Âµá€() / 8000.0, packetIn.Ó() / 8000.0, packetIn.à() / 8000.0);
            packetIn.à(0);
        }
        else if (packetIn.áˆºÑ¢Õ() == 64) {
            var5 = new EntitySmallFireball(this.à, var2, var3, var4, packetIn.Âµá€() / 8000.0, packetIn.Ó() / 8000.0, packetIn.à() / 8000.0);
            packetIn.à(0);
        }
        else if (packetIn.áˆºÑ¢Õ() == 66) {
            var5 = new EntityWitherSkull(this.à, var2, var3, var4, packetIn.Âµá€() / 8000.0, packetIn.Ó() / 8000.0, packetIn.à() / 8000.0);
            packetIn.à(0);
        }
        else if (packetIn.áˆºÑ¢Õ() == 62) {
            var5 = new EntityEgg(this.à, var2, var3, var4);
        }
        else if (packetIn.áˆºÑ¢Õ() == 73) {
            var5 = new EntityPotion(this.à, var2, var3, var4, packetIn.ÂµÈ());
            packetIn.à(0);
        }
        else if (packetIn.áˆºÑ¢Õ() == 75) {
            var5 = new EntityExpBottle(this.à, var2, var3, var4);
            packetIn.à(0);
        }
        else if (packetIn.áˆºÑ¢Õ() == 1) {
            var5 = new EntityBoat(this.à, var2, var3, var4);
        }
        else if (packetIn.áˆºÑ¢Õ() == 50) {
            var5 = new EntityTNTPrimed(this.à, var2, var3, var4, null);
        }
        else if (packetIn.áˆºÑ¢Õ() == 78) {
            var5 = new EntityArmorStand(this.à, var2, var3, var4);
        }
        else if (packetIn.áˆºÑ¢Õ() == 51) {
            var5 = new EntityEnderCrystal(this.à, var2, var3, var4);
        }
        else if (packetIn.áˆºÑ¢Õ() == 2) {
            var5 = new EntityItem(this.à, var2, var3, var4);
        }
        else if (packetIn.áˆºÑ¢Õ() == 70) {
            var5 = new EntityFallingBlock(this.à, var2, var3, var4, Block.Â(packetIn.ÂµÈ() & 0xFFFF));
            packetIn.à(0);
        }
        if (var5 != null) {
            ((Entity)var5).Ðƒá = packetIn.Â();
            ((Entity)var5).ˆÏ = packetIn.Ý();
            ((Entity)var5).áˆºÇŽØ = packetIn.Ø­áŒŠá();
            ((Entity)var5).áƒ = packetIn.Ø() * 360 / 256.0f;
            ((Entity)var5).É = packetIn.áŒŠÆ() * 360 / 256.0f;
            final Entity[] var7 = ((Entity)var5).ÇªÔ();
            if (var7 != null) {
                final int var8 = packetIn.HorizonCode_Horizon_È() - ((Entity)var5).ˆá();
                for (int var9 = 0; var9 < var7.length; ++var9) {
                    var7[var9].Ø­áŒŠá(var7[var9].ˆá() + var8);
                }
            }
            ((Entity)var5).Ø­áŒŠá(packetIn.HorizonCode_Horizon_È());
            this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), (Entity)var5);
            if (packetIn.ÂµÈ() > 0) {
                if (packetIn.áˆºÑ¢Õ() == 60) {
                    final Entity var10 = this.à.HorizonCode_Horizon_È(packetIn.ÂµÈ());
                    if (var10 instanceof EntityLivingBase && var5 instanceof EntityArrow) {
                        ((EntityArrow)var5).Ý = var10;
                    }
                }
                ((Entity)var5).áŒŠÆ(packetIn.Âµá€() / 8000.0, packetIn.Ó() / 8000.0, packetIn.à() / 8000.0);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S11PacketSpawnExperienceOrb packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final EntityXPOrb var2 = new EntityXPOrb(this.à, packetIn.Â(), packetIn.Ý(), packetIn.Ø­áŒŠá(), packetIn.Âµá€());
        var2.Ðƒá = packetIn.Â();
        var2.ˆÏ = packetIn.Ý();
        var2.áˆºÇŽØ = packetIn.Ø­áŒŠá();
        var2.É = 0.0f;
        var2.áƒ = 0.0f;
        var2.Ø­áŒŠá(packetIn.HorizonCode_Horizon_È());
        this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), var2);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S2CPacketSpawnGlobalEntity packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final double var2 = packetIn.Â() / 32.0;
        final double var3 = packetIn.Ý() / 32.0;
        final double var4 = packetIn.Ø­áŒŠá() / 32.0;
        EntityLightningBolt var5 = null;
        if (packetIn.Âµá€() == 1) {
            var5 = new EntityLightningBolt(this.à, var2, var3, var4);
        }
        if (var5 != null) {
            var5.Ðƒá = packetIn.Â();
            var5.ˆÏ = packetIn.Ý();
            var5.áˆºÇŽØ = packetIn.Ø­áŒŠá();
            var5.É = 0.0f;
            var5.áƒ = 0.0f;
            var5.Ø­áŒŠá(packetIn.HorizonCode_Horizon_È());
            this.à.Âµá€(var5);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S10PacketSpawnPainting packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final EntityPainting var2 = new EntityPainting(this.à, packetIn.Â(), packetIn.Ý(), packetIn.Ø­áŒŠá());
        this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), var2);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S12PacketEntityVelocity packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
        if (!ModuleManager.HorizonCode_Horizon_È(NoVelocity.class).áˆºÑ¢Õ()) {
            final Entity var2 = this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
            if (var2 != null) {
                var2.áŒŠÆ(packetIn.Â() / 8000.0, packetIn.Ý() / 8000.0, packetIn.Ø­áŒŠá() / 8000.0);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S1CPacketEntityMetadata packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Entity var2 = this.à.HorizonCode_Horizon_È(packetIn.Â());
        if (var2 != null && packetIn.HorizonCode_Horizon_È() != null) {
            var2.É().HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S0CPacketSpawnPlayer packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final double var2 = packetIn.Ø­áŒŠá() / 32.0;
        final double var3 = packetIn.Âµá€() / 32.0;
        final double var4 = packetIn.Ó() / 32.0;
        final float var5 = packetIn.à() * 360 / 256.0f;
        final float var6 = packetIn.Ø() * 360 / 256.0f;
        final EntityOtherPlayerMP entityOtherPlayerMP3;
        final EntityOtherPlayerMP entityOtherPlayerMP2;
        final EntityOtherPlayerMP entityOtherPlayerMP;
        final EntityOtherPlayerMP var7 = entityOtherPlayerMP = (entityOtherPlayerMP2 = (entityOtherPlayerMP3 = new EntityOtherPlayerMP(this.Ó.áŒŠÆ, this.HorizonCode_Horizon_È(packetIn.Ý()).HorizonCode_Horizon_È())));
        final int ø­áŒŠá = packetIn.Ø­áŒŠá();
        entityOtherPlayerMP.Ðƒá = ø­áŒŠá;
        final double n = ø­áŒŠá;
        entityOtherPlayerMP2.áˆºáˆºÈ = n;
        entityOtherPlayerMP3.áŒŠà = n;
        final EntityOtherPlayerMP entityOtherPlayerMP4 = var7;
        final EntityOtherPlayerMP entityOtherPlayerMP5 = var7;
        final EntityOtherPlayerMP entityOtherPlayerMP6 = var7;
        final int âµá€ = packetIn.Âµá€();
        entityOtherPlayerMP6.ˆÏ = âµá€;
        final double n2 = âµá€;
        entityOtherPlayerMP5.ÇŽá€ = n2;
        entityOtherPlayerMP4.ŠÄ = n2;
        final EntityOtherPlayerMP entityOtherPlayerMP7 = var7;
        final EntityOtherPlayerMP entityOtherPlayerMP8 = var7;
        final EntityOtherPlayerMP entityOtherPlayerMP9 = var7;
        final int ó = packetIn.Ó();
        entityOtherPlayerMP9.áˆºÇŽØ = ó;
        final double n3 = ó;
        entityOtherPlayerMP8.Ï = n3;
        entityOtherPlayerMP7.Ñ¢á = n3;
        final int var8 = packetIn.áŒŠÆ();
        if (var8 == 0) {
            var7.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[var7.Ø­Ñ¢Ï­Ø­áˆº.Ý] = null;
        }
        else {
            var7.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[var7.Ø­Ñ¢Ï­Ø­áˆº.Ý] = new ItemStack(Item_1028566121.HorizonCode_Horizon_È(var8), 1, 0);
        }
        var7.HorizonCode_Horizon_È(var2, var3, var4, var5, var6);
        this.à.HorizonCode_Horizon_È(packetIn.Â(), var7);
        final List var9 = packetIn.HorizonCode_Horizon_È();
        if (var9 != null) {
            var7.É().HorizonCode_Horizon_È(var9);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S18PacketEntityTeleport packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Entity var2 = this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
        if (var2 != null) {
            var2.Ðƒá = packetIn.Â();
            var2.ˆÏ = packetIn.Ý();
            var2.áˆºÇŽØ = packetIn.Ø­áŒŠá();
            final double var3 = var2.Ðƒá / 32.0;
            final double var4 = var2.ˆÏ / 32.0 + 0.015625;
            final double var5 = var2.áˆºÇŽØ / 32.0;
            final float var6 = packetIn.Âµá€() * 360 / 256.0f;
            final float var7 = packetIn.Ó() * 360 / 256.0f;
            if (Math.abs(var2.ŒÏ - var3) < 0.03125 && Math.abs(var2.Çªà¢ - var4) < 0.015625 && Math.abs(var2.Ê - var5) < 0.03125) {
                var2.HorizonCode_Horizon_È(var2.ŒÏ, var2.Çªà¢, var2.Ê, var6, var7, 3, true);
            }
            else {
                var2.HorizonCode_Horizon_È(var3, var4, var5, var6, var7, 3, true);
            }
            var2.ŠÂµà = packetIn.à();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S09PacketHeldItemChange packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        if (packetIn.HorizonCode_Horizon_È() >= 0 && packetIn.HorizonCode_Horizon_È() < InventoryPlayer.Ó()) {
            this.Ó.á.Ø­Ñ¢Ï­Ø­áˆº.Ý = packetIn.HorizonCode_Horizon_È();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S14PacketEntity packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Entity var2 = packetIn.HorizonCode_Horizon_È(this.à);
        if (var2 != null) {
            final Entity entity = var2;
            entity.Ðƒá += packetIn.HorizonCode_Horizon_È();
            final Entity entity2 = var2;
            entity2.ˆÏ += packetIn.Â();
            final Entity entity3 = var2;
            entity3.áˆºÇŽØ += packetIn.Ý();
            final double var3 = var2.Ðƒá / 32.0;
            final double var4 = var2.ˆÏ / 32.0;
            final double var5 = var2.áˆºÇŽØ / 32.0;
            final float var6 = packetIn.Ó() ? (packetIn.Ø­áŒŠá() * 360 / 256.0f) : var2.É;
            final float var7 = packetIn.Ó() ? (packetIn.Âµá€() * 360 / 256.0f) : var2.áƒ;
            var2.HorizonCode_Horizon_È(var3, var4, var5, var6, var7, 3, false);
            var2.ŠÂµà = packetIn.à();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S19PacketEntityHeadLook packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Entity var2 = packetIn.HorizonCode_Horizon_È(this.à);
        if (var2 != null) {
            final float var3 = packetIn.HorizonCode_Horizon_È() * 360 / 256.0f;
            var2.Ø(var3);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S13PacketDestroyEntities packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        for (int var2 = 0; var2 < packetIn.HorizonCode_Horizon_È().length; ++var2) {
            this.à.Â(packetIn.HorizonCode_Horizon_È()[var2]);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S08PacketPlayerPosLook packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final EntityPlayerSP var2 = this.Ó.á;
        double var3 = packetIn.HorizonCode_Horizon_È();
        double var4 = packetIn.Â();
        double var5 = packetIn.Ý();
        float var6 = packetIn.Ø­áŒŠá();
        float var7 = packetIn.Âµá€();
        if (packetIn.Ó().contains(S08PacketPlayerPosLook.HorizonCode_Horizon_È.HorizonCode_Horizon_È)) {
            var3 += var2.ŒÏ;
        }
        else {
            var2.ÇŽÉ = 0.0;
        }
        if (packetIn.Ó().contains(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Â)) {
            var4 += var2.Çªà¢;
        }
        else {
            var2.ˆá = 0.0;
        }
        if (packetIn.Ó().contains(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Ý)) {
            var5 += var2.Ê;
        }
        else {
            var2.ÇŽÕ = 0.0;
        }
        if (packetIn.Ó().contains(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Âµá€)) {
            var7 += var2.áƒ;
        }
        if (packetIn.Ó().contains(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Ø­áŒŠá)) {
            var6 += var2.É;
        }
        var2.HorizonCode_Horizon_È(var3, var4, var5, var6, var7);
        this.Ý.HorizonCode_Horizon_È(new C03PacketPlayer.Ý(var2.ŒÏ, var2.£É().Â, var2.Ê, var2.É, var2.áƒ, false));
        if (!this.Ø) {
            this.Ó.á.áŒŠà = this.Ó.á.ŒÏ;
            this.Ó.á.ŠÄ = this.Ó.á.Çªà¢;
            this.Ó.á.Ñ¢á = this.Ó.á.Ê;
            this.Ø = true;
            this.Ó.HorizonCode_Horizon_È((GuiScreen)null);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S22PacketMultiBlockChange packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        for (final S22PacketMultiBlockChange.HorizonCode_Horizon_È var5 : packetIn.HorizonCode_Horizon_È()) {
            this.à.HorizonCode_Horizon_È(var5.HorizonCode_Horizon_È(), var5.Ý());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S21PacketChunkData packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        if (packetIn.Âµá€()) {
            if (packetIn.Ø­áŒŠá() == 0) {
                this.à.HorizonCode_Horizon_È(packetIn.Â(), packetIn.Ý(), false);
                return;
            }
            this.à.HorizonCode_Horizon_È(packetIn.Â(), packetIn.Ý(), true);
        }
        this.à.HorizonCode_Horizon_È(packetIn.Â() << 4, 0, packetIn.Ý() << 4, (packetIn.Â() << 4) + 15, 256, (packetIn.Ý() << 4) + 15);
        final Chunk var2 = this.à.HorizonCode_Horizon_È(packetIn.Â(), packetIn.Ý());
        var2.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), packetIn.Ø­áŒŠá(), packetIn.Âµá€());
        this.à.Â(packetIn.Â() << 4, 0, packetIn.Ý() << 4, (packetIn.Â() << 4) + 15, 256, (packetIn.Ý() << 4) + 15);
        if (!packetIn.Âµá€() || !(this.à.£à instanceof WorldProviderSurface)) {
            var2.á();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S23PacketBlockChange packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        this.à.HorizonCode_Horizon_È(packetIn.Â(), packetIn.HorizonCode_Horizon_È());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S40PacketDisconnect packetIn) {
        this.Ý.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent reason) {
        this.Ó.HorizonCode_Horizon_È((WorldClient)null);
        if (this.Âµá€ != null) {
            if (this.Âµá€ instanceof GuiScreenRealmsProxy) {
                this.Ó.HorizonCode_Horizon_È(new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.Âµá€).Ó(), "disconnect.lost", reason).HorizonCode_Horizon_È());
            }
            else {
                this.Ó.HorizonCode_Horizon_È(new GuiDisconnected(this.Âµá€, "disconnect.lost", reason));
            }
        }
        else {
            this.Ó.HorizonCode_Horizon_È(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", reason));
        }
    }
    
    public void HorizonCode_Horizon_È(final Packet p_147297_1_) {
        final EventPacketSend send = new EventPacketSend();
        send.HorizonCode_Horizon_È(p_147297_1_);
        if (send.HorizonCode_Horizon_È()) {
            return;
        }
        this.Ý.HorizonCode_Horizon_È(send.Ý());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S0DPacketCollectItem packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Entity var2 = this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
        Object var3 = this.à.HorizonCode_Horizon_È(packetIn.Â());
        if (var3 == null) {
            var3 = this.Ó.á;
        }
        if (var2 != null) {
            if (var2 instanceof EntityXPOrb) {
                this.à.HorizonCode_Horizon_È(var2, "random.orb", 0.2f, ((this.ÂµÈ.nextFloat() - this.ÂµÈ.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
            else {
                this.à.HorizonCode_Horizon_È(var2, "random.pop", 0.2f, ((this.ÂµÈ.nextFloat() - this.ÂµÈ.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
            this.Ó.Å.HorizonCode_Horizon_È(new EntityPickupFX(this.à, var2, (Entity)var3, 0.5f));
            this.à.Â(packetIn.HorizonCode_Horizon_È());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S02PacketChat packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        if (packetIn.Ý() == 2) {
            this.Ó.Šáƒ.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), false);
        }
        else {
            this.Ó.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S0BPacketAnimation packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Entity var2 = this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
        if (var2 != null) {
            if (packetIn.Â() == 0) {
                final EntityLivingBase var3 = (EntityLivingBase)var2;
                var3.b_();
            }
            else if (packetIn.Â() == 1) {
                var2.Œà();
            }
            else if (packetIn.Â() == 2) {
                final EntityPlayer var4 = (EntityPlayer)var2;
                var4.HorizonCode_Horizon_È(false, false, false);
            }
            else if (packetIn.Â() == 4) {
                this.Ó.Å.HorizonCode_Horizon_È(var2, EnumParticleTypes.áˆºÑ¢Õ);
            }
            else if (packetIn.Â() == 5) {
                this.Ó.Å.HorizonCode_Horizon_È(var2, EnumParticleTypes.ÂµÈ);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S0APacketUseBed packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        packetIn.HorizonCode_Horizon_È(this.à).HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S0FPacketSpawnMob packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final double var2 = packetIn.Ø­áŒŠá() / 32.0;
        final double var3 = packetIn.Âµá€() / 32.0;
        final double var4 = packetIn.Ó() / 32.0;
        final float var5 = packetIn.áˆºÑ¢Õ() * 360 / 256.0f;
        final float var6 = packetIn.ÂµÈ() * 360 / 256.0f;
        final EntityLivingBase var7 = (EntityLivingBase)EntityList.HorizonCode_Horizon_È(packetIn.Ý(), this.Ó.áŒŠÆ);
        var7.Ðƒá = packetIn.Ø­áŒŠá();
        var7.ˆÏ = packetIn.Âµá€();
        var7.áˆºÇŽØ = packetIn.Ó();
        var7.ÂµÕ = packetIn.á() * 360 / 256.0f;
        final Entity[] var8 = var7.ÇªÔ();
        if (var8 != null) {
            final int var9 = packetIn.Â() - var7.ˆá();
            for (int var10 = 0; var10 < var8.length; ++var10) {
                var8[var10].Ø­áŒŠá(var8[var10].ˆá() + var9);
            }
        }
        var7.Ø­áŒŠá(packetIn.Â());
        var7.HorizonCode_Horizon_È(var2, var3, var4, var5, var6);
        var7.ÇŽÉ = packetIn.à() / 8000.0f;
        var7.ˆá = packetIn.Ø() / 8000.0f;
        var7.ÇŽÕ = packetIn.áŒŠÆ() / 8000.0f;
        this.à.HorizonCode_Horizon_È(packetIn.Â(), var7);
        final List var11 = packetIn.HorizonCode_Horizon_È();
        if (var11 != null) {
            var7.É().HorizonCode_Horizon_È(var11);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S03PacketTimeUpdate packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        this.Ó.áŒŠÆ.Â(packetIn.HorizonCode_Horizon_È());
        this.Ó.áŒŠÆ.HorizonCode_Horizon_È(packetIn.Â());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S05PacketSpawnPosition packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        this.Ó.á.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), true);
        this.Ó.áŒŠÆ.ŒÏ().HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S1BPacketEntityAttach packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        Object var2 = this.à.HorizonCode_Horizon_È(packetIn.Â());
        final Entity var3 = this.à.HorizonCode_Horizon_È(packetIn.Ý());
        if (packetIn.HorizonCode_Horizon_È() == 0) {
            boolean var4 = false;
            if (packetIn.Â() == this.Ó.á.ˆá()) {
                var2 = this.Ó.á;
                if (var3 instanceof EntityBoat) {
                    ((EntityBoat)var3).HorizonCode_Horizon_È(false);
                }
                var4 = (((Entity)var2).Æ == null && var3 != null);
            }
            else if (var3 instanceof EntityBoat) {
                ((EntityBoat)var3).HorizonCode_Horizon_È(true);
            }
            if (var2 == null) {
                return;
            }
            ((Entity)var2).HorizonCode_Horizon_È(var3);
            if (var4) {
                final GameSettings var5 = this.Ó.ŠÄ;
                this.Ó.Šáƒ.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("mount.onboard", GameSettings.HorizonCode_Horizon_È(var5.ŒÂ.áŒŠÆ())), false);
            }
        }
        else if (packetIn.HorizonCode_Horizon_È() == 1 && var2 instanceof EntityLiving) {
            if (var3 != null) {
                ((EntityLiving)var2).HorizonCode_Horizon_È(var3, false);
            }
            else {
                ((EntityLiving)var2).HorizonCode_Horizon_È(false, false);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S19PacketEntityStatus packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Entity var2 = packetIn.HorizonCode_Horizon_È(this.à);
        if (var2 != null) {
            if (packetIn.HorizonCode_Horizon_È() == 21) {
                this.Ó.£ÂµÄ().HorizonCode_Horizon_È(new GuardianSound((EntityGuardian)var2));
            }
            else {
                var2.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S06PacketUpdateHealth packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        this.Ó.á.b_(packetIn.HorizonCode_Horizon_È());
        this.Ó.á.ŠÏ­áˆºá().HorizonCode_Horizon_È(packetIn.Â());
        this.Ó.á.ŠÏ­áˆºá().Â(packetIn.Ý());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S1FPacketSetExperience packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        this.Ó.á.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), packetIn.Â(), packetIn.Ý());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S07PacketRespawn packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        if (packetIn.HorizonCode_Horizon_È() != this.Ó.á.ÇªÔ) {
            this.Ø = false;
            final Scoreboard var2 = this.à.à¢();
            (this.à = new WorldClient(this, new WorldSettings(0L, packetIn.Ý(), false, this.Ó.áŒŠÆ.ŒÏ().¥Æ(), packetIn.Ø­áŒŠá()), packetIn.HorizonCode_Horizon_È(), packetIn.Â(), this.Ó.ÇŽÕ)).HorizonCode_Horizon_È(var2);
            this.Ó.HorizonCode_Horizon_È(this.à);
            this.Ó.á.ÇªÔ = packetIn.HorizonCode_Horizon_È();
            this.Ó.HorizonCode_Horizon_È(new GuiDownloadTerrain(this));
        }
        this.Ó.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
        this.Ó.Âµá€.HorizonCode_Horizon_È(packetIn.Ý());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S27PacketExplosion packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Explosion var2 = new Explosion(this.Ó.áŒŠÆ, null, packetIn.Ø­áŒŠá(), packetIn.Âµá€(), packetIn.Ó(), packetIn.à(), packetIn.Ø());
        var2.HorizonCode_Horizon_È(true);
        final EntityPlayerSPOverwrite á = this.Ó.á;
        á.ÇŽÉ += packetIn.HorizonCode_Horizon_È();
        final EntityPlayerSPOverwrite á2 = this.Ó.á;
        á2.ˆá += packetIn.Â();
        final EntityPlayerSPOverwrite á3 = this.Ó.á;
        á3.ÇŽÕ += packetIn.Ý();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S2DPacketOpenWindow packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final EntityPlayerSP var2 = this.Ó.á;
        if ("minecraft:container".equals(packetIn.Â())) {
            var2.HorizonCode_Horizon_È(new InventoryBasic(packetIn.Ý(), packetIn.Ø­áŒŠá()));
            var2.Ï­Ï.Ø­áŒŠá = packetIn.HorizonCode_Horizon_È();
        }
        else if ("minecraft:villager".equals(packetIn.Â())) {
            var2.HorizonCode_Horizon_È(new NpcMerchant(var2, packetIn.Ý()));
            var2.Ï­Ï.Ø­áŒŠá = packetIn.HorizonCode_Horizon_È();
        }
        else if ("EntityHorse".equals(packetIn.Â())) {
            final Entity var3 = this.à.HorizonCode_Horizon_È(packetIn.Âµá€());
            if (var3 instanceof EntityHorse) {
                var2.HorizonCode_Horizon_È((EntityHorse)var3, new AnimalChest(packetIn.Ý(), packetIn.Ø­áŒŠá()));
                var2.Ï­Ï.Ø­áŒŠá = packetIn.HorizonCode_Horizon_È();
            }
        }
        else if (!packetIn.Ó()) {
            var2.HorizonCode_Horizon_È(new LocalBlockIntercommunication(packetIn.Â(), packetIn.Ý()));
            var2.Ï­Ï.Ø­áŒŠá = packetIn.HorizonCode_Horizon_È();
        }
        else {
            final ContainerLocalMenu var4 = new ContainerLocalMenu(packetIn.Â(), packetIn.Ý(), packetIn.Ø­áŒŠá());
            var2.HorizonCode_Horizon_È((IInventory)var4);
            var2.Ï­Ï.Ø­áŒŠá = packetIn.HorizonCode_Horizon_È();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S2FPacketSetSlot packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final EntityPlayerSP var2 = this.Ó.á;
        if (packetIn.HorizonCode_Horizon_È() == -1) {
            var2.Ø­Ñ¢Ï­Ø­áˆº.Â(packetIn.Ý());
        }
        else {
            boolean var3 = false;
            if (this.Ó.¥Æ instanceof GuiContainerCreative) {
                final GuiContainerCreative var4 = (GuiContainerCreative)this.Ó.¥Æ;
                var3 = (var4.Ó() != CreativeTabs.ˆÏ­.HorizonCode_Horizon_È());
            }
            if (packetIn.HorizonCode_Horizon_È() == 0 && packetIn.Â() >= 36 && packetIn.Â() < 45) {
                final ItemStack var5 = var2.ŒÂ.HorizonCode_Horizon_È(packetIn.Â()).HorizonCode_Horizon_È();
                if (packetIn.Ý() != null && (var5 == null || var5.Â < packetIn.Ý().Â)) {
                    packetIn.Ý().Ý = 5;
                }
                var2.ŒÂ.HorizonCode_Horizon_È(packetIn.Â(), packetIn.Ý());
            }
            else if (packetIn.HorizonCode_Horizon_È() == var2.Ï­Ï.Ø­áŒŠá && (packetIn.HorizonCode_Horizon_È() != 0 || !var3)) {
                var2.Ï­Ï.HorizonCode_Horizon_È(packetIn.Â(), packetIn.Ý());
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S32PacketConfirmTransaction packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        Container var2 = null;
        final EntityPlayerSP var3 = this.Ó.á;
        if (packetIn.HorizonCode_Horizon_È() == 0) {
            var2 = var3.ŒÂ;
        }
        else if (packetIn.HorizonCode_Horizon_È() == var3.Ï­Ï.Ø­áŒŠá) {
            var2 = var3.Ï­Ï;
        }
        if (var2 != null && !packetIn.Ý()) {
            this.HorizonCode_Horizon_È(new C0FPacketConfirmTransaction(packetIn.HorizonCode_Horizon_È(), packetIn.Â(), true));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S30PacketWindowItems packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final EntityPlayerSP var2 = this.Ó.á;
        if (packetIn.HorizonCode_Horizon_È() == 0) {
            var2.ŒÂ.HorizonCode_Horizon_È(packetIn.Â());
        }
        else if (packetIn.HorizonCode_Horizon_È() == var2.Ï­Ï.Ø­áŒŠá) {
            var2.Ï­Ï.HorizonCode_Horizon_È(packetIn.Â());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S36PacketSignEditorOpen packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        Object var2 = this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
        if (!(var2 instanceof TileEntitySign)) {
            var2 = new TileEntitySign();
            ((TileEntity)var2).HorizonCode_Horizon_È(this.à);
            ((TileEntity)var2).HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
        }
        this.Ó.á.HorizonCode_Horizon_È((TileEntitySign)var2);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S33PacketUpdateSign packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        boolean var2 = false;
        if (this.Ó.áŒŠÆ.Ó(packetIn.HorizonCode_Horizon_È())) {
            final TileEntity var3 = this.Ó.áŒŠÆ.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
            if (var3 instanceof TileEntitySign) {
                final TileEntitySign var4 = (TileEntitySign)var3;
                if (var4.HorizonCode_Horizon_È()) {
                    System.arraycopy(packetIn.Â(), 0, var4.Âµá€, 0, 4);
                    var4.ŠÄ();
                }
                var2 = true;
            }
        }
        if (!var2 && this.Ó.á != null) {
            this.Ó.á.HorizonCode_Horizon_È(new ChatComponentText("Unable to locate sign at " + packetIn.HorizonCode_Horizon_È().HorizonCode_Horizon_È() + ", " + packetIn.HorizonCode_Horizon_È().Â() + ", " + packetIn.HorizonCode_Horizon_È().Ý()));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S35PacketUpdateTileEntity packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        if (this.Ó.áŒŠÆ.Ó(packetIn.HorizonCode_Horizon_È())) {
            final TileEntity var2 = this.Ó.áŒŠÆ.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
            final int var3 = packetIn.Â();
            if ((var3 == 1 && var2 instanceof TileEntityMobSpawner) || (var3 == 2 && var2 instanceof TileEntityCommandBlock) || (var3 == 3 && var2 instanceof TileEntityBeacon) || (var3 == 4 && var2 instanceof TileEntitySkull) || (var3 == 5 && var2 instanceof TileEntityFlowerPot) || (var3 == 6 && var2 instanceof TileEntityBanner)) {
                var2.HorizonCode_Horizon_È(packetIn.Ý());
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S31PacketWindowProperty packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final EntityPlayerSP var2 = this.Ó.á;
        if (var2.Ï­Ï != null && var2.Ï­Ï.Ø­áŒŠá == packetIn.HorizonCode_Horizon_È()) {
            var2.Ï­Ï.HorizonCode_Horizon_È(packetIn.Â(), packetIn.Ý());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S04PacketEntityEquipment packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Entity var2 = this.à.HorizonCode_Horizon_È(packetIn.Â());
        if (var2 != null) {
            var2.HorizonCode_Horizon_È(packetIn.Ý(), packetIn.HorizonCode_Horizon_È());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S2EPacketCloseWindow packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        this.Ó.á.¥Æ();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S24PacketBlockAction packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        this.Ó.áŒŠÆ.Ý(packetIn.HorizonCode_Horizon_È(), packetIn.Ø­áŒŠá(), packetIn.Â(), packetIn.Ý());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S25PacketBlockBreakAnim packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        this.Ó.áŒŠÆ.Ý(packetIn.HorizonCode_Horizon_È(), packetIn.Â(), packetIn.Ý());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S26PacketMapChunkBulk packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        for (int var2 = 0; var2 < packetIn.HorizonCode_Horizon_È(); ++var2) {
            final int var3 = packetIn.HorizonCode_Horizon_È(var2);
            final int var4 = packetIn.Â(var2);
            this.à.HorizonCode_Horizon_È(var3, var4, true);
            this.à.HorizonCode_Horizon_È(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
            final Chunk var5 = this.à.HorizonCode_Horizon_È(var3, var4);
            var5.HorizonCode_Horizon_È(packetIn.Ý(var2), packetIn.Ø­áŒŠá(var2), true);
            this.à.Â(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
            if (!(this.à.£à instanceof WorldProviderSurface)) {
                var5.á();
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S2BPacketChangeGameState packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final EntityPlayerSP var2 = this.Ó.á;
        final int var3 = packetIn.HorizonCode_Horizon_È();
        final float var4 = packetIn.Â();
        final int var5 = MathHelper.Ø­áŒŠá(var4 + 0.5f);
        if (var3 >= 0 && var3 < S2BPacketChangeGameState.HorizonCode_Horizon_È.length && S2BPacketChangeGameState.HorizonCode_Horizon_È[var3] != null) {
            var2.Â(new ChatComponentTranslation(S2BPacketChangeGameState.HorizonCode_Horizon_È[var3], new Object[0]));
        }
        if (var3 == 1) {
            this.à.ŒÏ().Â(true);
            this.à.ÂµÈ(0.0f);
        }
        else if (var3 == 2) {
            this.à.ŒÏ().Â(false);
            this.à.ÂµÈ(1.0f);
        }
        else if (var3 == 3) {
            this.Ó.Âµá€.HorizonCode_Horizon_È(WorldSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var5));
        }
        else if (var3 == 4) {
            this.Ó.HorizonCode_Horizon_È(new GuiWinGame());
        }
        else if (var3 == 5) {
            final GameSettings var6 = this.Ó.ŠÄ;
            if (var4 == 0.0f) {
                this.Ó.HorizonCode_Horizon_È(new GuiScreenDemo());
            }
            else if (var4 == 101.0f) {
                this.Ó.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(new ChatComponentTranslation("demo.help.movement", new Object[] { GameSettings.HorizonCode_Horizon_È(var6.ÇªÉ.áŒŠÆ()), GameSettings.HorizonCode_Horizon_È(var6.ŠÏ­áˆºá.áŒŠÆ()), GameSettings.HorizonCode_Horizon_È(var6.ÇŽà.áŒŠÆ()), GameSettings.HorizonCode_Horizon_È(var6.ŠáˆºÂ.áŒŠÆ()) }));
            }
            else if (var4 == 102.0f) {
                this.Ó.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(new ChatComponentTranslation("demo.help.jump", new Object[] { GameSettings.HorizonCode_Horizon_È(var6.Ø­Ñ¢Ï­Ø­áˆº.áŒŠÆ()) }));
            }
            else if (var4 == 103.0f) {
                this.Ó.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(new ChatComponentTranslation("demo.help.inventory", new Object[] { GameSettings.HorizonCode_Horizon_È(var6.Ï­Ï.áŒŠÆ()) }));
            }
        }
        else if (var3 == 6) {
            this.à.HorizonCode_Horizon_È(var2.ŒÏ, var2.Çªà¢ + var2.Ðƒáƒ(), var2.Ê, "random.successful_hit", 0.18f, 0.45f, false);
        }
        else if (var3 == 7) {
            this.à.ÂµÈ(var4);
        }
        else if (var3 == 8) {
            this.à.áŒŠÆ(var4);
        }
        else if (var3 == 10) {
            this.à.HorizonCode_Horizon_È(EnumParticleTypes.Ç, var2.ŒÏ, var2.Çªà¢, var2.Ê, 0.0, 0.0, 0.0, new int[0]);
            this.à.HorizonCode_Horizon_È(var2.ŒÏ, var2.Çªà¢, var2.Ê, "mob.guardian.curse", 1.0f, 1.0f, false);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S34PacketMaps packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final MapData var2 = ItemMap.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), this.Ó.áŒŠÆ);
        packetIn.HorizonCode_Horizon_È(var2);
        this.Ó.µÕ.áŒŠÆ().HorizonCode_Horizon_È(var2);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S28PacketEffect packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        if (packetIn.HorizonCode_Horizon_È()) {
            this.Ó.áŒŠÆ.HorizonCode_Horizon_È(packetIn.Â(), packetIn.Ø­áŒŠá(), packetIn.Ý());
        }
        else {
            this.Ó.áŒŠÆ.Â(packetIn.Â(), packetIn.Ø­áŒŠá(), packetIn.Ý());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S37PacketStatistics packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        boolean var2 = false;
        for (final Map.Entry var4 : packetIn.HorizonCode_Horizon_È().entrySet()) {
            final StatBase var5 = var4.getKey();
            final int var6 = var4.getValue();
            if (var5.Ø­áŒŠá() && var6 > 0) {
                if (this.áˆºÑ¢Õ && this.Ó.á.c_().HorizonCode_Horizon_È(var5) == 0) {
                    final Achievement var7 = (Achievement)var5;
                    this.Ó.Æ.HorizonCode_Horizon_È(var7);
                    this.Ó.Ä().HorizonCode_Horizon_È(new MetadataAchievement(var7), 0L);
                    if (var5 == AchievementList.Ó) {
                        this.Ó.ŠÄ.µÐƒáƒ = false;
                        this.Ó.ŠÄ.Â();
                    }
                }
                var2 = true;
            }
            this.Ó.á.c_().Â(this.Ó.á, var5, var6);
        }
        if (!this.áˆºÑ¢Õ && !var2 && this.Ó.ŠÄ.µÐƒáƒ) {
            this.Ó.Æ.Â(AchievementList.Ó);
        }
        this.áˆºÑ¢Õ = true;
        if (this.Ó.¥Æ instanceof IProgressMeter) {
            ((IProgressMeter)this.Ó.¥Æ).Â();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S1DPacketEntityEffect packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Entity var2 = this.à.HorizonCode_Horizon_È(packetIn.Â());
        if (var2 instanceof EntityLivingBase) {
            final PotionEffect var3 = new PotionEffect(packetIn.Ý(), packetIn.Âµá€(), packetIn.Ø­áŒŠá(), false, packetIn.Ó());
            var3.Â(packetIn.HorizonCode_Horizon_È());
            ((EntityLivingBase)var2).HorizonCode_Horizon_È(var3);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S42PacketCombatEvent p_175098_1_) {
        PacketThreadUtil.HorizonCode_Horizon_È(p_175098_1_, this, this.Ó);
        final Entity var2 = this.à.HorizonCode_Horizon_È(p_175098_1_.Ý);
        final EntityLivingBase var3 = (var2 instanceof EntityLivingBase) ? ((EntityLivingBase)var2) : null;
        if (p_175098_1_.HorizonCode_Horizon_È == S42PacketCombatEvent.HorizonCode_Horizon_È.Â) {
            final long var4 = 1000 * p_175098_1_.Ø­áŒŠá / 20;
            final MetadataCombat var5 = new MetadataCombat(this.Ó.á, var3);
            this.Ó.Ä().HorizonCode_Horizon_È(var5, 0L - var4, 0L);
        }
        else if (p_175098_1_.HorizonCode_Horizon_È == S42PacketCombatEvent.HorizonCode_Horizon_È.Ý) {
            final Entity var6 = this.à.HorizonCode_Horizon_È(p_175098_1_.Â);
            if (var6 instanceof EntityPlayer) {
                final MetadataPlayerDeath var7 = new MetadataPlayerDeath((EntityLivingBase)var6, var3);
                var7.HorizonCode_Horizon_È(p_175098_1_.Âµá€);
                this.Ó.Ä().HorizonCode_Horizon_È(var7, 0L);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S41PacketServerDifficulty p_175101_1_) {
        PacketThreadUtil.HorizonCode_Horizon_È(p_175101_1_, this, this.Ó);
        this.Ó.áŒŠÆ.ŒÏ().HorizonCode_Horizon_È(p_175101_1_.Â());
        this.Ó.áŒŠÆ.ŒÏ().Âµá€(p_175101_1_.HorizonCode_Horizon_È());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S43PacketCamera p_175094_1_) {
        PacketThreadUtil.HorizonCode_Horizon_È(p_175094_1_, this, this.Ó);
        final Entity var2 = p_175094_1_.HorizonCode_Horizon_È(this.à);
        if (var2 != null) {
            this.Ó.HorizonCode_Horizon_È(var2);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S44PacketWorldBorder p_175093_1_) {
        PacketThreadUtil.HorizonCode_Horizon_È(p_175093_1_, this, this.Ó);
        p_175093_1_.HorizonCode_Horizon_È(this.à.áŠ());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S45PacketTitle p_175099_1_) {
        PacketThreadUtil.HorizonCode_Horizon_È(p_175099_1_, this, this.Ó);
        final S45PacketTitle.HorizonCode_Horizon_È var2 = p_175099_1_.HorizonCode_Horizon_È();
        String var3 = null;
        String var4 = null;
        final String var5 = (p_175099_1_.Â() != null) ? p_175099_1_.Â().áŒŠÆ() : "";
        switch (NetHandlerPlayClient.HorizonCode_Horizon_È.HorizonCode_Horizon_È[var2.ordinal()]) {
            case 1: {
                var3 = var5;
                break;
            }
            case 2: {
                var4 = var5;
                break;
            }
            case 3: {
                this.Ó.Šáƒ.HorizonCode_Horizon_È("", "", -1, -1, -1);
                this.Ó.Šáƒ.HorizonCode_Horizon_È();
                return;
            }
        }
        this.Ó.Šáƒ.HorizonCode_Horizon_È(var3, var4, p_175099_1_.Ý(), p_175099_1_.Ø­áŒŠá(), p_175099_1_.Âµá€());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S46PacketSetCompressionLevel p_175100_1_) {
        if (!this.Ý.Ý()) {
            this.Ý.HorizonCode_Horizon_È(p_175100_1_.HorizonCode_Horizon_È());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S47PacketPlayerListHeaderFooter p_175096_1_) {
        this.Ó.Šáƒ.Ø().Â((p_175096_1_.HorizonCode_Horizon_È().áŒŠÆ().length() == 0) ? null : p_175096_1_.HorizonCode_Horizon_È());
        this.Ó.Šáƒ.Ø().HorizonCode_Horizon_È((p_175096_1_.Â().áŒŠÆ().length() == 0) ? null : p_175096_1_.Â());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S1EPacketRemoveEntityEffect packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Entity var2 = this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
        if (var2 instanceof EntityLivingBase) {
            ((EntityLivingBase)var2).£á(packetIn.Â());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S38PacketPlayerListItem packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        for (final S38PacketPlayerListItem.Â var3 : packetIn.HorizonCode_Horizon_È()) {
            if (packetIn.Â() == S38PacketPlayerListItem.HorizonCode_Horizon_È.Âµá€) {
                this.áŒŠÆ.remove(var3.HorizonCode_Horizon_È().getId());
            }
            else {
                NetworkPlayerInfo var4 = this.áŒŠÆ.get(var3.HorizonCode_Horizon_È().getId());
                if (packetIn.Â() == S38PacketPlayerListItem.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                    var4 = new NetworkPlayerInfo(var3);
                    this.áŒŠÆ.put(var4.HorizonCode_Horizon_È().getId(), var4);
                }
                if (var4 == null) {
                    continue;
                }
                switch (NetHandlerPlayClient.HorizonCode_Horizon_È.Â[packetIn.Â().ordinal()]) {
                    default: {
                        continue;
                    }
                    case 1: {
                        var4.HorizonCode_Horizon_È(var3.Ý());
                        var4.HorizonCode_Horizon_È(var3.Â());
                        continue;
                    }
                    case 2: {
                        var4.HorizonCode_Horizon_È(var3.Ý());
                        continue;
                    }
                    case 3: {
                        var4.HorizonCode_Horizon_È(var3.Â());
                        continue;
                    }
                    case 4: {
                        var4.HorizonCode_Horizon_È(var3.Ø­áŒŠá());
                        continue;
                    }
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S00PacketKeepAlive packetIn) {
        this.HorizonCode_Horizon_È(new C00PacketKeepAlive(packetIn.HorizonCode_Horizon_È()));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S39PacketPlayerAbilities packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final EntityPlayerSP var2 = this.Ó.á;
        var2.áˆºáˆºáŠ.Â = packetIn.Â();
        var2.áˆºáˆºáŠ.Ø­áŒŠá = packetIn.Ø­áŒŠá();
        var2.áˆºáˆºáŠ.HorizonCode_Horizon_È = packetIn.HorizonCode_Horizon_È();
        var2.áˆºáˆºáŠ.Ý = packetIn.Ý();
        var2.áˆºáˆºáŠ.HorizonCode_Horizon_È(packetIn.Âµá€());
        var2.áˆºáˆºáŠ.Â(packetIn.Ó());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S3APacketTabComplete packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final String[] var2 = packetIn.HorizonCode_Horizon_È();
        if (this.Ó.¥Æ instanceof GuiChat) {
            final GuiChat var3 = (GuiChat)this.Ó.¥Æ;
            var3.HorizonCode_Horizon_È(var2);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S29PacketSoundEffect packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        this.Ó.áŒŠÆ.HorizonCode_Horizon_È(packetIn.Â(), packetIn.Ý(), packetIn.Ø­áŒŠá(), packetIn.HorizonCode_Horizon_È(), packetIn.Âµá€(), packetIn.Ó(), false);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S48PacketResourcePackSend p_175095_1_) {
        final String var2 = p_175095_1_.HorizonCode_Horizon_È();
        final String var3 = p_175095_1_.Â();
        if (var2.startsWith("level://")) {
            final String var4 = var2.substring("level://".length());
            final File var5 = new File(this.Ó.ŒÏ, "saves");
            final File var6 = new File(var5, var4);
            if (var6.isFile()) {
                this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.Ø­áŒŠá));
                Futures.addCallback(this.Ó.Ç().HorizonCode_Horizon_È(var6), (FutureCallback)new FutureCallback() {
                    private static final String Â = "CL_00000879";
                    
                    public void onSuccess(final Object p_onSuccess_1_) {
                        NetHandlerPlayClient.this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
                    }
                    
                    public void onFailure(final Throwable p_onFailure_1_) {
                        NetHandlerPlayClient.this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.Ý));
                    }
                });
            }
            else {
                this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.Ý));
            }
        }
        else if (this.Ó.Çªà¢() != null && this.Ó.Çªà¢().Â() == ServerData.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.Ø­áŒŠá));
            Futures.addCallback(this.Ó.Ç().HorizonCode_Horizon_È(var2, var3), (FutureCallback)new FutureCallback() {
                private static final String Â = "CL_00002624";
                
                public void onSuccess(final Object p_onSuccess_1_) {
                    NetHandlerPlayClient.this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
                }
                
                public void onFailure(final Throwable p_onFailure_1_) {
                    NetHandlerPlayClient.this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.Ý));
                }
            });
        }
        else if (this.Ó.Çªà¢() != null && this.Ó.Çªà¢().Â() != ServerData.HorizonCode_Horizon_È.Ý) {
            this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.Â));
        }
        else {
            this.Ó.HorizonCode_Horizon_È(new Runnable() {
                private static final String Â = "CL_00002623";
                
                @Override
                public void run() {
                    NetHandlerPlayClient.this.Ó.HorizonCode_Horizon_È(new GuiYesNo(new GuiYesNoCallback() {
                        private static final String Â = "CL_00002622";
                        
                        @Override
                        public void HorizonCode_Horizon_È(final boolean result, final int id) {
                            NetHandlerPlayClient.HorizonCode_Horizon_È(NetHandlerPlayClient.this, Minecraft.áŒŠà());
                            if (result) {
                                if (NetHandlerPlayClient.this.Ó.Çªà¢() != null) {
                                    NetHandlerPlayClient.this.Ó.Çªà¢().HorizonCode_Horizon_È(ServerData.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
                                }
                                NetHandlerPlayClient.this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.Ø­áŒŠá));
                                Futures.addCallback(NetHandlerPlayClient.this.Ó.Ç().HorizonCode_Horizon_È(var2, var3), (FutureCallback)new FutureCallback() {
                                    private static final String Â = "CL_00002621";
                                    
                                    public void onSuccess(final Object p_onSuccess_1_) {
                                        NetHandlerPlayClient.this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
                                    }
                                    
                                    public void onFailure(final Throwable p_onFailure_1_) {
                                        NetHandlerPlayClient.this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.Ý));
                                    }
                                });
                            }
                            else {
                                if (NetHandlerPlayClient.this.Ó.Çªà¢() != null) {
                                    NetHandlerPlayClient.this.Ó.Çªà¢().HorizonCode_Horizon_È(ServerData.HorizonCode_Horizon_È.Â);
                                }
                                NetHandlerPlayClient.this.Ý.HorizonCode_Horizon_È(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.HorizonCode_Horizon_È.Â));
                            }
                            ServerList.Â(NetHandlerPlayClient.this.Ó.Çªà¢());
                            NetHandlerPlayClient.this.Ó.HorizonCode_Horizon_È((GuiScreen)null);
                        }
                    }, I18n.HorizonCode_Horizon_È("multiplayer.texturePrompt.line1", new Object[0]), I18n.HorizonCode_Horizon_È("multiplayer.texturePrompt.line2", new Object[0]), 0));
                }
            });
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S49PacketUpdateEntityNBT p_175097_1_) {
        PacketThreadUtil.HorizonCode_Horizon_È(p_175097_1_, this, this.Ó);
        final Entity var2 = p_175097_1_.HorizonCode_Horizon_È(this.à);
        if (var2 != null) {
            var2.à(p_175097_1_.HorizonCode_Horizon_È());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S3FPacketCustomPayload packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        if ("MC|TrList".equals(packetIn.HorizonCode_Horizon_È())) {
            final PacketBuffer var2 = packetIn.Â();
            try {
                final int var3 = var2.readInt();
                final GuiScreen var4 = this.Ó.¥Æ;
                if (var4 != null && var4 instanceof GuiMerchant && var3 == this.Ó.á.Ï­Ï.Ø­áŒŠá) {
                    final IMerchant var5 = ((GuiMerchant)var4).Ó();
                    final MerchantRecipeList var6 = MerchantRecipeList.Â(var2);
                    var5.HorizonCode_Horizon_È(var6);
                }
            }
            catch (IOException var7) {
                NetHandlerPlayClient.Â.error("Couldn't load trade info", (Throwable)var7);
                return;
            }
            finally {
                var2.release();
            }
            var2.release();
        }
        else if ("MC|Brand".equals(packetIn.HorizonCode_Horizon_È())) {
            this.Ó.á.Ý(packetIn.Â().Ý(32767));
        }
        else if ("MC|BOpen".equals(packetIn.HorizonCode_Horizon_È())) {
            final ItemStack var8 = this.Ó.á.áŒŠá();
            if (var8 != null && var8.HorizonCode_Horizon_È() == Items.ÇŽÊ) {
                this.Ó.HorizonCode_Horizon_È(new GuiScreenBook(this.Ó.á, var8, false));
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S3BPacketScoreboardObjective packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Scoreboard var2 = this.à.à¢();
        if (packetIn.Ý() == 0) {
            final ScoreObjective var3 = var2.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), IScoreObjectiveCriteria.Â);
            var3.HorizonCode_Horizon_È(packetIn.Â());
            var3.HorizonCode_Horizon_È(packetIn.Ø­áŒŠá());
        }
        else {
            final ScoreObjective var3 = var2.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
            if (packetIn.Ý() == 1) {
                var2.Â(var3);
            }
            else if (packetIn.Ý() == 2) {
                var3.HorizonCode_Horizon_È(packetIn.Â());
                var3.HorizonCode_Horizon_È(packetIn.Ø­áŒŠá());
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S3CPacketUpdateScore packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Scoreboard var2 = this.à.à¢();
        final ScoreObjective var3 = var2.HorizonCode_Horizon_È(packetIn.Â());
        if (packetIn.Ø­áŒŠá() == S3CPacketUpdateScore.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final Score var4 = var2.Â(packetIn.HorizonCode_Horizon_È(), var3);
            var4.Ý(packetIn.Ý());
        }
        else if (packetIn.Ø­áŒŠá() == S3CPacketUpdateScore.HorizonCode_Horizon_È.Â) {
            if (StringUtils.Â(packetIn.Â())) {
                var2.Ý(packetIn.HorizonCode_Horizon_È(), null);
            }
            else if (var3 != null) {
                var2.Ý(packetIn.HorizonCode_Horizon_È(), var3);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S3DPacketDisplayScoreboard packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Scoreboard var2 = this.à.à¢();
        if (packetIn.Â().length() == 0) {
            var2.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), null);
        }
        else {
            final ScoreObjective var3 = var2.HorizonCode_Horizon_È(packetIn.Â());
            var2.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), var3);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S3EPacketTeams packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Scoreboard var2 = this.à.à¢();
        ScorePlayerTeam var3;
        if (packetIn.Ó() == 0) {
            var3 = var2.Ø­áŒŠá(packetIn.HorizonCode_Horizon_È());
        }
        else {
            var3 = var2.Ý(packetIn.HorizonCode_Horizon_È());
        }
        if (packetIn.Ó() == 0 || packetIn.Ó() == 2) {
            var3.HorizonCode_Horizon_È(packetIn.Â());
            var3.Â(packetIn.Ý());
            var3.Ý(packetIn.Ø­áŒŠá());
            var3.HorizonCode_Horizon_È(EnumChatFormatting.HorizonCode_Horizon_È(packetIn.Ø()));
            var3.HorizonCode_Horizon_È(packetIn.à());
            final Team.HorizonCode_Horizon_È var4 = Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È(packetIn.áŒŠÆ());
            if (var4 != null) {
                var3.HorizonCode_Horizon_È(var4);
            }
        }
        if (packetIn.Ó() == 0 || packetIn.Ó() == 3) {
            for (final String var6 : packetIn.Âµá€()) {
                var2.HorizonCode_Horizon_È(var6, packetIn.HorizonCode_Horizon_È());
            }
        }
        if (packetIn.Ó() == 4) {
            for (final String var6 : packetIn.Âµá€()) {
                var2.HorizonCode_Horizon_È(var6, var3);
            }
        }
        if (packetIn.Ó() == 1) {
            var2.HorizonCode_Horizon_È(var3);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S2APacketParticles packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        if (packetIn.áˆºÑ¢Õ() == 0) {
            final double var2 = packetIn.áŒŠÆ() * packetIn.Ó();
            final double var3 = packetIn.áŒŠÆ() * packetIn.à();
            final double var4 = packetIn.áŒŠÆ() * packetIn.Ø();
            try {
                this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), packetIn.Â(), packetIn.Ý(), packetIn.Ø­áŒŠá(), packetIn.Âµá€(), var2, var3, var4, packetIn.ÂµÈ());
            }
            catch (Throwable var12) {
                NetHandlerPlayClient.Â.warn("Could not spawn particle effect " + packetIn.HorizonCode_Horizon_È());
            }
        }
        else {
            for (int var5 = 0; var5 < packetIn.áˆºÑ¢Õ(); ++var5) {
                final double var6 = this.ÂµÈ.nextGaussian() * packetIn.Ó();
                final double var7 = this.ÂµÈ.nextGaussian() * packetIn.à();
                final double var8 = this.ÂµÈ.nextGaussian() * packetIn.Ø();
                final double var9 = this.ÂµÈ.nextGaussian() * packetIn.áŒŠÆ();
                final double var10 = this.ÂµÈ.nextGaussian() * packetIn.áŒŠÆ();
                final double var11 = this.ÂµÈ.nextGaussian() * packetIn.áŒŠÆ();
                try {
                    this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), packetIn.Â(), packetIn.Ý() + var6, packetIn.Ø­áŒŠá() + var7, packetIn.Âµá€() + var8, var9, var10, var11, packetIn.ÂµÈ());
                }
                catch (Throwable var13) {
                    NetHandlerPlayClient.Â.warn("Could not spawn particle effect " + packetIn.HorizonCode_Horizon_È());
                    return;
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final S20PacketEntityProperties packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Ó);
        final Entity var2 = this.à.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È());
        if (var2 != null) {
            if (!(var2 instanceof EntityLivingBase)) {
                throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + var2 + ")");
            }
            final BaseAttributeMap var3 = ((EntityLivingBase)var2).µÐƒÓ();
            for (final S20PacketEntityProperties.HorizonCode_Horizon_È var5 : packetIn.Â()) {
                IAttributeInstance var6 = var3.HorizonCode_Horizon_È(var5.HorizonCode_Horizon_È());
                if (var6 == null) {
                    var6 = var3.Â(new RangedAttribute(null, var5.HorizonCode_Horizon_È(), 0.0, Double.MIN_NORMAL, Double.MAX_VALUE));
                }
                var6.HorizonCode_Horizon_È(var5.Â());
                var6.Ø­áŒŠá();
                for (final AttributeModifier var8 : var5.Ý()) {
                    var6.Â(var8);
                }
            }
        }
    }
    
    public NetworkManager Â() {
        return this.Ý;
    }
    
    public Collection Ý() {
        return this.áŒŠÆ.values();
    }
    
    public NetworkPlayerInfo HorizonCode_Horizon_È(final UUID p_175102_1_) {
        return this.áŒŠÆ.get(p_175102_1_);
    }
    
    public NetworkPlayerInfo HorizonCode_Horizon_È(final String p_175104_1_) {
        for (final NetworkPlayerInfo var3 : this.áŒŠÆ.values()) {
            if (var3.HorizonCode_Horizon_È().getName().equals(p_175104_1_)) {
                return var3;
            }
        }
        return null;
    }
    
    public GameProfile Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final NetHandlerPlayClient netHandlerPlayClient, final Minecraft ó) {
        netHandlerPlayClient.Ó = ó;
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        private static final String Ý = "CL_00002620";
        
        static {
            Â = new int[S38PacketPlayerListItem.HorizonCode_Horizon_È.values().length];
            try {
                NetHandlerPlayClient.HorizonCode_Horizon_È.Â[S38PacketPlayerListItem.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                NetHandlerPlayClient.HorizonCode_Horizon_È.Â[S38PacketPlayerListItem.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                NetHandlerPlayClient.HorizonCode_Horizon_È.Â[S38PacketPlayerListItem.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                NetHandlerPlayClient.HorizonCode_Horizon_È.Â[S38PacketPlayerListItem.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            HorizonCode_Horizon_È = new int[S45PacketTitle.HorizonCode_Horizon_È.values().length];
            try {
                NetHandlerPlayClient.HorizonCode_Horizon_È.HorizonCode_Horizon_È[S45PacketTitle.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                NetHandlerPlayClient.HorizonCode_Horizon_È.HorizonCode_Horizon_È[S45PacketTitle.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                NetHandlerPlayClient.HorizonCode_Horizon_È.HorizonCode_Horizon_È[S45PacketTitle.HorizonCode_Horizon_È.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
        }
    }
}
