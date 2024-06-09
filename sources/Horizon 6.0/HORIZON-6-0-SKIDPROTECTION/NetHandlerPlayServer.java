package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import java.util.concurrent.Callable;
import java.util.Set;
import java.util.Collections;
import com.google.common.util.concurrent.Futures;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayServer implements IUpdatePlayerListBox, INetHandlerPlayServer
{
    private static final Logger Ý;
    public final NetworkManager HorizonCode_Horizon_È;
    private final MinecraftServer Ø­áŒŠá;
    public EntityPlayerMP Â;
    private int Âµá€;
    private int Ó;
    private int à;
    private boolean Ø;
    private int áŒŠÆ;
    private long áˆºÑ¢Õ;
    private long ÂµÈ;
    private int á;
    private int ˆÏ­;
    private IntHashMap £á;
    private double Å;
    private double £à;
    private double µà;
    private boolean ˆà;
    private static final String ¥Æ = "CL_00001452";
    
    static {
        Ý = LogManager.getLogger();
    }
    
    public NetHandlerPlayServer(final MinecraftServer server, final NetworkManager networkManagerIn, final EntityPlayerMP playerIn) {
        this.£á = new IntHashMap();
        this.ˆà = true;
        this.Ø­áŒŠá = server;
        (this.HorizonCode_Horizon_È = networkManagerIn).HorizonCode_Horizon_È(this);
        this.Â = playerIn;
        playerIn.HorizonCode_Horizon_È = this;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Ø = false;
        ++this.Âµá€;
        this.Ø­áŒŠá.Â.HorizonCode_Horizon_È("keepAlive");
        if (this.Âµá€ - this.ÂµÈ > 40L) {
            this.ÂµÈ = this.Âµá€;
            this.áˆºÑ¢Õ = this.Ý();
            this.áŒŠÆ = (int)this.áˆºÑ¢Õ;
            this.HorizonCode_Horizon_È(new S00PacketKeepAlive(this.áŒŠÆ));
        }
        this.Ø­áŒŠá.Â.Â();
        if (this.á > 0) {
            --this.á;
        }
        if (this.ˆÏ­ > 0) {
            --this.ˆÏ­;
        }
        if (this.Â.ÐƒáˆºÄ() > 0L && this.Ø­áŒŠá.Ðƒá() > 0 && MinecraftServer.Œà() - this.Â.ÐƒáˆºÄ() > this.Ø­áŒŠá.Ðƒá() * 1000 * 60) {
            this.HorizonCode_Horizon_È("You have been idle for too long!");
        }
    }
    
    public NetworkManager Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final String reason) {
        final ChatComponentText var2 = new ChatComponentText(reason);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S40PacketDisconnect(var2), (GenericFutureListener)new GenericFutureListener() {
            private static final String Â = "CL_00001453";
            
            public void operationComplete(final Future p_operationComplete_1_) {
                NetHandlerPlayServer.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2);
            }
        }, new GenericFutureListener[0]);
        this.HorizonCode_Horizon_È.áŒŠÆ();
        Futures.getUnchecked((java.util.concurrent.Future)this.Ø­áŒŠá.HorizonCode_Horizon_È(new Runnable() {
            private static final String Â = "CL_00001454";
            
            @Override
            public void run() {
                NetHandlerPlayServer.this.HorizonCode_Horizon_È.áˆºÑ¢Õ();
            }
        }));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C0CPacketInput packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        this.Â.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), packetIn.Â(), packetIn.Ý(), packetIn.Ø­áŒŠá());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C03PacketPlayer packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        final WorldServer var2 = this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Â.ÇªÔ);
        this.Ø = true;
        if (!this.Â.áŒŠÆ) {
            final double var3 = this.Â.ŒÏ;
            final double var4 = this.Â.Çªà¢;
            final double var5 = this.Â.Ê;
            double var6 = 0.0;
            final double var7 = packetIn.HorizonCode_Horizon_È() - this.Å;
            final double var8 = packetIn.Â() - this.£à;
            final double var9 = packetIn.Ý() - this.µà;
            if (packetIn.à()) {
                var6 = var7 * var7 + var8 * var8 + var9 * var9;
                if (!this.ˆà && var6 < 0.25) {
                    this.ˆà = true;
                }
            }
            if (this.ˆà) {
                this.Ó = this.Âµá€;
                if (this.Â.Æ != null) {
                    float var10 = this.Â.É;
                    float var11 = this.Â.áƒ;
                    this.Â.Æ.ˆÉ();
                    final double var12 = this.Â.ŒÏ;
                    final double var13 = this.Â.Çªà¢;
                    final double var14 = this.Â.Ê;
                    if (packetIn.Ø()) {
                        var10 = packetIn.Ø­áŒŠá();
                        var11 = packetIn.Âµá€();
                    }
                    this.Â.ŠÂµà = packetIn.Ó();
                    this.Â.¥Æ();
                    this.Â.HorizonCode_Horizon_È(var12, var13, var14, var10, var11);
                    if (this.Â.Æ != null) {
                        this.Â.Æ.ˆÉ();
                    }
                    this.Ø­áŒŠá.Œ().Ø­áŒŠá(this.Â);
                    if (this.Â.Æ != null) {
                        if (var6 > 4.0) {
                            final Entity var15 = this.Â.Æ;
                            this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S18PacketEntityTeleport(var15));
                            this.HorizonCode_Horizon_È(this.Â.ŒÏ, this.Â.Çªà¢, this.Â.Ê, this.Â.É, this.Â.áƒ);
                        }
                        this.Â.Æ.áŒŠÏ = true;
                    }
                    if (this.ˆà) {
                        this.Å = this.Â.ŒÏ;
                        this.£à = this.Â.Çªà¢;
                        this.µà = this.Â.Ê;
                    }
                    var2.à(this.Â);
                    return;
                }
                if (this.Â.Ï­Ó()) {
                    this.Â.¥Æ();
                    this.Â.HorizonCode_Horizon_È(this.Å, this.£à, this.µà, this.Â.É, this.Â.áƒ);
                    var2.à(this.Â);
                    return;
                }
                final double var16 = this.Â.Çªà¢;
                this.Å = this.Â.ŒÏ;
                this.£à = this.Â.Çªà¢;
                this.µà = this.Â.Ê;
                double var12 = this.Â.ŒÏ;
                double var13 = this.Â.Çªà¢;
                double var14 = this.Â.Ê;
                float var17 = this.Â.É;
                float var18 = this.Â.áƒ;
                if (packetIn.à() && packetIn.Â() == -999.0) {
                    packetIn.HorizonCode_Horizon_È(false);
                }
                if (packetIn.à()) {
                    var12 = packetIn.HorizonCode_Horizon_È();
                    var13 = packetIn.Â();
                    var14 = packetIn.Ý();
                    if (Math.abs(packetIn.HorizonCode_Horizon_È()) > 3.0E7 || Math.abs(packetIn.Ý()) > 3.0E7) {
                        this.HorizonCode_Horizon_È("Illegal position");
                        return;
                    }
                }
                if (packetIn.Ø()) {
                    var17 = packetIn.Ø­áŒŠá();
                    var18 = packetIn.Âµá€();
                }
                this.Â.¥Æ();
                this.Â.HorizonCode_Horizon_È(this.Å, this.£à, this.µà, var17, var18);
                if (!this.ˆà) {
                    return;
                }
                double var19 = var12 - this.Â.ŒÏ;
                double var20 = var13 - this.Â.Çªà¢;
                double var21 = var14 - this.Â.Ê;
                final double var22 = Math.min(Math.abs(var19), Math.abs(this.Â.ÇŽÉ));
                final double var23 = Math.min(Math.abs(var20), Math.abs(this.Â.ˆá));
                final double var24 = Math.min(Math.abs(var21), Math.abs(this.Â.ÇŽÕ));
                double var25 = var22 * var22 + var23 * var23 + var24 * var24;
                if (var25 > 100.0 && (!this.Ø­áŒŠá.¥à() || !this.Ø­áŒŠá.ŠÂµà().equals(this.Â.v_()))) {
                    NetHandlerPlayServer.Ý.warn(String.valueOf(this.Â.v_()) + " moved too quickly! " + var19 + "," + var20 + "," + var21 + " (" + var22 + ", " + var23 + ", " + var24 + ")");
                    this.HorizonCode_Horizon_È(this.Å, this.£à, this.µà, this.Â.É, this.Â.áƒ);
                    return;
                }
                final float var26 = 0.0625f;
                final boolean var27 = var2.HorizonCode_Horizon_È(this.Â, this.Â.£É().Ø­áŒŠá(var26, var26, var26)).isEmpty();
                if (this.Â.ŠÂµà && !packetIn.Ó() && var20 > 0.0) {
                    this.Â.ŠÏ();
                }
                this.Â.HorizonCode_Horizon_È(var19, var20, var21);
                this.Â.ŠÂµà = packetIn.Ó();
                final double var28 = var20;
                var19 = var12 - this.Â.ŒÏ;
                var20 = var13 - this.Â.Çªà¢;
                if (var20 > -0.5 || var20 < 0.5) {
                    var20 = 0.0;
                }
                var21 = var14 - this.Â.Ê;
                var25 = var19 * var19 + var20 * var20 + var21 * var21;
                boolean var29 = false;
                if (var25 > 0.0625 && !this.Â.Ï­Ó() && !this.Â.Ý.Ý()) {
                    var29 = true;
                    NetHandlerPlayServer.Ý.warn(String.valueOf(this.Â.v_()) + " moved wrongly!");
                }
                this.Â.HorizonCode_Horizon_È(var12, var13, var14, var17, var18);
                this.Â.ÂµÈ(this.Â.ŒÏ - var3, this.Â.Çªà¢ - var4, this.Â.Ê - var5);
                if (!this.Â.ÇªÓ) {
                    final boolean var30 = var2.HorizonCode_Horizon_È(this.Â, this.Â.£É().Ø­áŒŠá(var26, var26, var26)).isEmpty();
                    if (var27 && (var29 || !var30) && !this.Â.Ï­Ó()) {
                        this.HorizonCode_Horizon_È(this.Å, this.£à, this.µà, var17, var18);
                        return;
                    }
                }
                final AxisAlignedBB var31 = this.Â.£É().Â(var26, var26, var26).HorizonCode_Horizon_È(0.0, -0.55, 0.0);
                if (!this.Ø­áŒŠá.Ô() && !this.Â.áˆºáˆºáŠ.Ý && !var2.Ý(var31)) {
                    if (var28 >= -0.03125) {
                        ++this.à;
                        if (this.à > 80) {
                            NetHandlerPlayServer.Ý.warn(String.valueOf(this.Â.v_()) + " was kicked for floating too long!");
                            this.HorizonCode_Horizon_È("Flying is not enabled on this server");
                            return;
                        }
                    }
                }
                else {
                    this.à = 0;
                }
                this.Â.ŠÂµà = packetIn.Ó();
                this.Ø­áŒŠá.Œ().Ø­áŒŠá(this.Â);
                this.Â.HorizonCode_Horizon_È(this.Â.Çªà¢ - var16, packetIn.Ó());
            }
            else if (this.Âµá€ - this.Ó > 20) {
                this.HorizonCode_Horizon_È(this.Å, this.£à, this.µà, this.Â.É, this.Â.áƒ);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final double x, final double y, final double z, final float yaw, final float pitch) {
        this.HorizonCode_Horizon_È(x, y, z, yaw, pitch, Collections.emptySet());
    }
    
    public void HorizonCode_Horizon_È(final double p_175089_1_, final double p_175089_3_, final double p_175089_5_, final float p_175089_7_, final float p_175089_8_, final Set p_175089_9_) {
        this.ˆà = false;
        this.Å = p_175089_1_;
        this.£à = p_175089_3_;
        this.µà = p_175089_5_;
        if (p_175089_9_.contains(S08PacketPlayerPosLook.HorizonCode_Horizon_È.HorizonCode_Horizon_È)) {
            this.Å += this.Â.ŒÏ;
        }
        if (p_175089_9_.contains(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Â)) {
            this.£à += this.Â.Çªà¢;
        }
        if (p_175089_9_.contains(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Ý)) {
            this.µà += this.Â.Ê;
        }
        float var10 = p_175089_7_;
        float var11 = p_175089_8_;
        if (p_175089_9_.contains(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Ø­áŒŠá)) {
            var10 = p_175089_7_ + this.Â.É;
        }
        if (p_175089_9_.contains(S08PacketPlayerPosLook.HorizonCode_Horizon_È.Âµá€)) {
            var11 = p_175089_8_ + this.Â.áƒ;
        }
        this.Â.HorizonCode_Horizon_È(this.Å, this.£à, this.µà, var10, var11);
        this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S08PacketPlayerPosLook(p_175089_1_, p_175089_3_, p_175089_5_, p_175089_7_, p_175089_8_, p_175089_9_));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C07PacketPlayerDigging packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        final WorldServer var2 = this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Â.ÇªÔ);
        final BlockPos var3 = packetIn.HorizonCode_Horizon_È();
        this.Â.ÐƒÓ();
        switch (NetHandlerPlayServer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[packetIn.Ý().ordinal()]) {
            case 1: {
                if (!this.Â.Ø­áŒŠá()) {
                    this.Â.HorizonCode_Horizon_È(false);
                }
            }
            case 2: {
                if (!this.Â.Ø­áŒŠá()) {
                    this.Â.HorizonCode_Horizon_È(true);
                }
            }
            case 3: {
                this.Â.áŒŠÔ();
            }
            case 4:
            case 5:
            case 6: {
                final double var4 = this.Â.ŒÏ - (var3.HorizonCode_Horizon_È() + 0.5);
                final double var5 = this.Â.Çªà¢ - (var3.Â() + 0.5) + 1.5;
                final double var6 = this.Â.Ê - (var3.Ý() + 0.5);
                final double var7 = var4 * var4 + var5 * var5 + var6 * var6;
                if (var7 > 36.0) {
                    return;
                }
                if (var3.Â() >= this.Ø­áŒŠá.ˆáƒ()) {
                    return;
                }
                if (packetIn.Ý() == C07PacketPlayerDigging.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                    if (!this.Ø­áŒŠá.HorizonCode_Horizon_È(var2, var3, this.Â) && var2.áŠ().HorizonCode_Horizon_È(var3)) {
                        this.Â.Ý.HorizonCode_Horizon_È(var3, packetIn.Â());
                    }
                    else {
                        this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S23PacketBlockChange(var2, var3));
                    }
                }
                else {
                    if (packetIn.Ý() == C07PacketPlayerDigging.HorizonCode_Horizon_È.Ý) {
                        this.Â.Ý.HorizonCode_Horizon_È(var3);
                    }
                    else if (packetIn.Ý() == C07PacketPlayerDigging.HorizonCode_Horizon_È.Â) {
                        this.Â.Ý.Âµá€();
                    }
                    if (var2.Â(var3).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                        this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S23PacketBlockChange(var2, var3));
                    }
                }
            }
            default: {
                throw new IllegalArgumentException("Invalid player action");
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C08PacketPlayerBlockPlacement packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        final WorldServer var2 = this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Â.ÇªÔ);
        ItemStack var3 = this.Â.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        boolean var4 = false;
        final BlockPos var5 = packetIn.HorizonCode_Horizon_È();
        final EnumFacing var6 = EnumFacing.HorizonCode_Horizon_È(packetIn.Â());
        this.Â.ÐƒÓ();
        if (packetIn.Â() == 255) {
            if (var3 == null) {
                return;
            }
            this.Â.Ý.HorizonCode_Horizon_È(this.Â, var2, var3);
        }
        else if (var5.Â() >= this.Ø­áŒŠá.ˆáƒ() - 1 && (var6 == EnumFacing.Â || var5.Â() >= this.Ø­áŒŠá.ˆáƒ())) {
            final ChatComponentTranslation var7 = new ChatComponentTranslation("build.tooHigh", new Object[] { this.Ø­áŒŠá.ˆáƒ() });
            var7.à().HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­);
            this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S02PacketChat(var7));
            var4 = true;
        }
        else {
            if (this.ˆà && this.Â.Âµá€(var5.HorizonCode_Horizon_È() + 0.5, var5.Â() + 0.5, var5.Ý() + 0.5) < 64.0 && !this.Ø­áŒŠá.HorizonCode_Horizon_È(var2, var5, this.Â) && var2.áŠ().HorizonCode_Horizon_È(var5)) {
                this.Â.Ý.HorizonCode_Horizon_È(this.Â, var2, var3, var5, var6, packetIn.Ø­áŒŠá(), packetIn.Âµá€(), packetIn.Ó());
            }
            var4 = true;
        }
        if (var4) {
            this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S23PacketBlockChange(var2, var5));
            this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S23PacketBlockChange(var2, var5.HorizonCode_Horizon_È(var6)));
        }
        var3 = this.Â.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var3 != null && var3.Â == 0) {
            this.Â.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[this.Â.Ø­Ñ¢Ï­Ø­áˆº.Ý] = null;
            var3 = null;
        }
        if (var3 == null || var3.á() == 0) {
            this.Â.à = true;
            this.Â.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[this.Â.Ø­Ñ¢Ï­Ø­áˆº.Ý] = ItemStack.Â(this.Â.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[this.Â.Ø­Ñ¢Ï­Ø­áˆº.Ý]);
            final Slot var8 = this.Â.Ï­Ï.HorizonCode_Horizon_È(this.Â.Ø­Ñ¢Ï­Ø­áˆº, this.Â.Ø­Ñ¢Ï­Ø­áˆº.Ý);
            this.Â.Ï­Ï.Ý();
            this.Â.à = false;
            if (!ItemStack.Â(this.Â.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá(), packetIn.Ý())) {
                this.HorizonCode_Horizon_È(new S2FPacketSetSlot(this.Â.Ï­Ï.Ø­áŒŠá, var8.Ý, this.Â.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá()));
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C18PacketSpectate p_175088_1_) {
        PacketThreadUtil.HorizonCode_Horizon_È(p_175088_1_, this, this.Â.ÇŽÉ());
        if (this.Â.Ø­áŒŠá()) {
            Entity var2 = null;
            for (final WorldServer var6 : this.Ø­áŒŠá.Ý) {
                if (var6 != null) {
                    var2 = p_175088_1_.HorizonCode_Horizon_È(var6);
                    if (var2 != null) {
                        break;
                    }
                }
            }
            if (var2 != null) {
                this.Â.µÕ(this.Â);
                this.Â.HorizonCode_Horizon_È((Entity)null);
                if (var2.Ï­Ðƒà != this.Â.Ï­Ðƒà) {
                    final WorldServer var7 = this.Â.ÇŽÉ();
                    final WorldServer var8 = (WorldServer)var2.Ï­Ðƒà;
                    this.Â.ÇªÔ = var2.ÇªÔ;
                    this.HorizonCode_Horizon_È(new S07PacketRespawn(this.Â.ÇªÔ, var7.ŠÂµà(), var7.ŒÏ().Ø­à(), this.Â.Ý.HorizonCode_Horizon_È()));
                    var7.Ó(this.Â);
                    this.Â.ˆáŠ = false;
                    this.Â.Â(var2.ŒÏ, var2.Çªà¢, var2.Ê, var2.É, var2.áƒ);
                    if (this.Â.Œ()) {
                        var7.HorizonCode_Horizon_È(this.Â, false);
                        var8.HorizonCode_Horizon_È(this.Â);
                        var8.HorizonCode_Horizon_È(this.Â, false);
                    }
                    this.Â.HorizonCode_Horizon_È(var8);
                    this.Ø­áŒŠá.Œ().HorizonCode_Horizon_È(this.Â, var7);
                    this.Â.áˆºÑ¢Õ(var2.ŒÏ, var2.Çªà¢, var2.Ê);
                    this.Â.Ý.HorizonCode_Horizon_È(var8);
                    this.Ø­áŒŠá.Œ().Â(this.Â, var8);
                    this.Ø­áŒŠá.Œ().Ó(this.Â);
                }
                else {
                    this.Â.áˆºÑ¢Õ(var2.ŒÏ, var2.Çªà¢, var2.Ê);
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C19PacketResourcePackStatus p_175086_1_) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent reason) {
        NetHandlerPlayServer.Ý.info(String.valueOf(this.Â.v_()) + " lost connection: " + reason);
        this.Ø­áŒŠá.ˆÓ();
        final ChatComponentTranslation var2 = new ChatComponentTranslation("multiplayer.player.left", new Object[] { this.Â.Ý() });
        var2.à().HorizonCode_Horizon_È(EnumChatFormatting.Å);
        this.Ø­áŒŠá.Œ().HorizonCode_Horizon_È(var2);
        this.Â.Ñ¢á();
        this.Ø­áŒŠá.Œ().Âµá€(this.Â);
        if (this.Ø­áŒŠá.¥à() && this.Â.v_().equals(this.Ø­áŒŠá.ŠÂµà())) {
            NetHandlerPlayServer.Ý.info("Stopping singleplayer server as player logged out");
            this.Ø­áŒŠá.Ø­à();
        }
    }
    
    public void HorizonCode_Horizon_È(final Packet packetIn) {
        if (packetIn instanceof S02PacketChat) {
            final S02PacketChat var2 = (S02PacketChat)packetIn;
            final EntityPlayer.HorizonCode_Horizon_È var3 = this.Â.¥Ê();
            if (var3 == EntityPlayer.HorizonCode_Horizon_È.Ý) {
                return;
            }
            if (var3 == EntityPlayer.HorizonCode_Horizon_È.Â && !var2.Â()) {
                return;
            }
        }
        try {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(packetIn);
        }
        catch (Throwable var5) {
            final CrashReport var4 = CrashReport.HorizonCode_Horizon_È(var5, "Sending packet");
            final CrashReportCategory var6 = var4.HorizonCode_Horizon_È("Packet being sent");
            var6.HorizonCode_Horizon_È("Packet class", new Callable() {
                private static final String Â = "CL_00002270";
                
                public String HorizonCode_Horizon_È() {
                    return packetIn.getClass().getCanonicalName();
                }
                
                @Override
                public Object call() {
                    return this.HorizonCode_Horizon_È();
                }
            });
            throw new ReportedException(var4);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C09PacketHeldItemChange packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        if (packetIn.HorizonCode_Horizon_È() >= 0 && packetIn.HorizonCode_Horizon_È() < InventoryPlayer.Ó()) {
            this.Â.Ø­Ñ¢Ï­Ø­áˆº.Ý = packetIn.HorizonCode_Horizon_È();
            this.Â.ÐƒÓ();
        }
        else {
            NetHandlerPlayServer.Ý.warn(String.valueOf(this.Â.v_()) + " tried to set an invalid carried item");
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C01PacketChatMessage packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        if (this.Â.¥Ê() == EntityPlayer.HorizonCode_Horizon_È.Ý) {
            final ChatComponentTranslation var4 = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
            var4.à().HorizonCode_Horizon_È(EnumChatFormatting.ˆÏ­);
            this.HorizonCode_Horizon_È(new S02PacketChat(var4));
        }
        else {
            this.Â.ÐƒÓ();
            String var5 = packetIn.HorizonCode_Horizon_È();
            var5 = StringUtils.normalizeSpace(var5);
            for (int var6 = 0; var6 < var5.length(); ++var6) {
                if (!ChatAllowedCharacters.HorizonCode_Horizon_È(var5.charAt(var6))) {
                    this.HorizonCode_Horizon_È("Illegal characters in chat");
                    return;
                }
            }
            if (var5.startsWith("/")) {
                this.Â(var5);
            }
            else {
                final ChatComponentTranslation var7 = new ChatComponentTranslation("chat.type.text", new Object[] { this.Â.Ý(), var5 });
                this.Ø­áŒŠá.Œ().HorizonCode_Horizon_È(var7, false);
            }
            this.á += 20;
            if (this.á > 200 && !this.Ø­áŒŠá.Œ().Âµá€(this.Â.áˆºà())) {
                this.HorizonCode_Horizon_È("disconnect.spam");
            }
        }
    }
    
    private void Â(final String command) {
        this.Ø­áŒŠá.Õ().HorizonCode_Horizon_È(this.Â, command);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C0APacketAnimation p_175087_1_) {
        PacketThreadUtil.HorizonCode_Horizon_È(p_175087_1_, this, this.Â.ÇŽÉ());
        this.Â.ÐƒÓ();
        this.Â.b_();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C0BPacketEntityAction packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        this.Â.ÐƒÓ();
        switch (NetHandlerPlayServer.HorizonCode_Horizon_È.Â[packetIn.HorizonCode_Horizon_È().ordinal()]) {
            case 1: {
                this.Â.Âµá€(true);
                break;
            }
            case 2: {
                this.Â.Âµá€(false);
                break;
            }
            case 3: {
                this.Â.Â(true);
                break;
            }
            case 4: {
                this.Â.Â(false);
                break;
            }
            case 5: {
                this.Â.HorizonCode_Horizon_È(false, true, true);
                this.ˆà = false;
                break;
            }
            case 6: {
                if (this.Â.Æ instanceof EntityHorse) {
                    ((EntityHorse)this.Â.Æ).Æ(packetIn.Â());
                    break;
                }
                break;
            }
            case 7: {
                if (this.Â.Æ instanceof EntityHorse) {
                    ((EntityHorse)this.Â.Æ).à(this.Â);
                    break;
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid client command!");
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C02PacketUseEntity packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        final WorldServer var2 = this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Â.ÇªÔ);
        final Entity var3 = packetIn.HorizonCode_Horizon_È(var2);
        this.Â.ÐƒÓ();
        if (var3 != null) {
            final boolean var4 = this.Â.µà(var3);
            double var5 = 36.0;
            if (!var4) {
                var5 = 9.0;
            }
            if (this.Â.Âµá€(var3) < var5) {
                if (packetIn.HorizonCode_Horizon_È() == C02PacketUseEntity.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                    this.Â.ˆà(var3);
                }
                else if (packetIn.HorizonCode_Horizon_È() == C02PacketUseEntity.HorizonCode_Horizon_È.Ý) {
                    var3.HorizonCode_Horizon_È(this.Â, packetIn.Â());
                }
                else if (packetIn.HorizonCode_Horizon_È() == C02PacketUseEntity.HorizonCode_Horizon_È.Â) {
                    if (var3 instanceof EntityItem || var3 instanceof EntityXPOrb || var3 instanceof EntityArrow || var3 == this.Â) {
                        this.HorizonCode_Horizon_È("Attempting to attack an invalid entity");
                        this.Ø­áŒŠá.Ø­áŒŠá("Player " + this.Â.v_() + " tried to attack an invalid entity");
                        return;
                    }
                    this.Â.¥Æ(var3);
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C16PacketClientStatus packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        this.Â.ÐƒÓ();
        final C16PacketClientStatus.HorizonCode_Horizon_È var2 = packetIn.HorizonCode_Horizon_È();
        switch (NetHandlerPlayServer.HorizonCode_Horizon_È.Ý[var2.ordinal()]) {
            case 1: {
                if (this.Â.áŒŠÆ) {
                    this.Â = this.Ø­áŒŠá.Œ().HorizonCode_Horizon_È(this.Â, 0, true);
                    break;
                }
                if (this.Â.ÇŽÉ().ŒÏ().¥Æ()) {
                    if (this.Ø­áŒŠá.¥à() && this.Â.v_().equals(this.Ø­áŒŠá.ŠÂµà())) {
                        this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È("You have died. Game over, man, it's game over!");
                        this.Ø­áŒŠá.áŒŠ();
                        break;
                    }
                    final UserListBansEntry var3 = new UserListBansEntry(this.Â.áˆºà(), null, "(You just lost the game)", null, "Death in Hardcore");
                    this.Ø­áŒŠá.Œ().áŒŠÆ().HorizonCode_Horizon_È(var3);
                    this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È("You have died. Game over, man, it's game over!");
                    break;
                }
                else {
                    if (this.Â.Ï­Ä() > 0.0f) {
                        return;
                    }
                    this.Â = this.Ø­áŒŠá.Œ().HorizonCode_Horizon_È(this.Â, 0, false);
                    break;
                }
                break;
            }
            case 2: {
                this.Â.áˆºÕ().HorizonCode_Horizon_È(this.Â);
                break;
            }
            case 3: {
                this.Â.HorizonCode_Horizon_È(AchievementList.Ó);
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C0DPacketCloseWindow packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        this.Â.Ï­Ðƒà();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C0EPacketClickWindow packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        this.Â.ÐƒÓ();
        if (this.Â.Ï­Ï.Ø­áŒŠá == packetIn.HorizonCode_Horizon_È() && this.Â.Ï­Ï.Ý(this.Â)) {
            if (this.Â.Ø­áŒŠá()) {
                final ArrayList var2 = Lists.newArrayList();
                for (int var3 = 0; var3 < this.Â.Ï­Ï.Ý.size(); ++var3) {
                    var2.add(this.Â.Ï­Ï.Ý.get(var3).HorizonCode_Horizon_È());
                }
                this.Â.HorizonCode_Horizon_È(this.Â.Ï­Ï, var2);
            }
            else {
                final ItemStack var4 = this.Â.Ï­Ï.HorizonCode_Horizon_È(packetIn.Â(), packetIn.Ý(), packetIn.Ó(), this.Â);
                if (ItemStack.Â(packetIn.Âµá€(), var4)) {
                    this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S32PacketConfirmTransaction(packetIn.HorizonCode_Horizon_È(), packetIn.Ø­áŒŠá(), true));
                    this.Â.à = true;
                    this.Â.Ï­Ï.Ý();
                    this.Â.Šáƒ();
                    this.Â.à = false;
                }
                else {
                    this.£á.HorizonCode_Horizon_È(this.Â.Ï­Ï.Ø­áŒŠá, (Object)packetIn.Ø­áŒŠá());
                    this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S32PacketConfirmTransaction(packetIn.HorizonCode_Horizon_È(), packetIn.Ø­áŒŠá(), false));
                    this.Â.Ï­Ï.HorizonCode_Horizon_È(this.Â, false);
                    final ArrayList var5 = Lists.newArrayList();
                    for (int var6 = 0; var6 < this.Â.Ï­Ï.Ý.size(); ++var6) {
                        var5.add(this.Â.Ï­Ï.Ý.get(var6).HorizonCode_Horizon_È());
                    }
                    this.Â.HorizonCode_Horizon_È(this.Â.Ï­Ï, var5);
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C11PacketEnchantItem packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        this.Â.ÐƒÓ();
        if (this.Â.Ï­Ï.Ø­áŒŠá == packetIn.HorizonCode_Horizon_È() && this.Â.Ï­Ï.Ý(this.Â) && !this.Â.Ø­áŒŠá()) {
            this.Â.Ï­Ï.Â(this.Â, packetIn.Â());
            this.Â.Ï­Ï.Ý();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C10PacketCreativeInventoryAction packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        if (this.Â.Ý.Ý()) {
            final boolean var2 = packetIn.HorizonCode_Horizon_È() < 0;
            final ItemStack var3 = packetIn.Â();
            if (var3 != null && var3.£á() && var3.Å().Â("BlockEntityTag", 10)) {
                final NBTTagCompound var4 = var3.Å().ˆÏ­("BlockEntityTag");
                if (var4.Ý("x") && var4.Ý("y") && var4.Ý("z")) {
                    final BlockPos var5 = new BlockPos(var4.Ó("x"), var4.Ó("y"), var4.Ó("z"));
                    final TileEntity var6 = this.Â.Ï­Ðƒà.HorizonCode_Horizon_È(var5);
                    if (var6 != null) {
                        final NBTTagCompound var7 = new NBTTagCompound();
                        var6.Â(var7);
                        var7.Å("x");
                        var7.Å("y");
                        var7.Å("z");
                        var3.HorizonCode_Horizon_È("BlockEntityTag", var7);
                    }
                }
            }
            final boolean var8 = packetIn.HorizonCode_Horizon_È() >= 1 && packetIn.HorizonCode_Horizon_È() < 36 + InventoryPlayer.Ó();
            final boolean var9 = var3 == null || var3.HorizonCode_Horizon_È() != null;
            final boolean var10 = var3 == null || (var3.Ø() >= 0 && var3.Â <= 64 && var3.Â > 0);
            if (var8 && var9 && var10) {
                if (var3 == null) {
                    this.Â.ŒÂ.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), (ItemStack)null);
                }
                else {
                    this.Â.ŒÂ.HorizonCode_Horizon_È(packetIn.HorizonCode_Horizon_È(), var3);
                }
                this.Â.ŒÂ.HorizonCode_Horizon_È(this.Â, true);
            }
            else if (var2 && var9 && var10 && this.ˆÏ­ < 200) {
                this.ˆÏ­ += 20;
                final EntityItem var11 = this.Â.HorizonCode_Horizon_È(var3, true);
                if (var11 != null) {
                    var11.à();
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C0FPacketConfirmTransaction packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        final Short var2 = (Short)this.£á.HorizonCode_Horizon_È(this.Â.Ï­Ï.Ø­áŒŠá);
        if (var2 != null && packetIn.Â() == var2 && this.Â.Ï­Ï.Ø­áŒŠá == packetIn.HorizonCode_Horizon_È() && !this.Â.Ï­Ï.Ý(this.Â) && !this.Â.Ø­áŒŠá()) {
            this.Â.Ï­Ï.HorizonCode_Horizon_È(this.Â, true);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C12PacketUpdateSign packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        this.Â.ÐƒÓ();
        final WorldServer var2 = this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Â.ÇªÔ);
        final BlockPos var3 = packetIn.HorizonCode_Horizon_È();
        if (var2.Ó(var3)) {
            final TileEntity var4 = var2.HorizonCode_Horizon_È(var3);
            if (!(var4 instanceof TileEntitySign)) {
                return;
            }
            final TileEntitySign var5 = (TileEntitySign)var4;
            if (!var5.HorizonCode_Horizon_È() || var5.Â() != this.Â) {
                this.Ø­áŒŠá.Ø­áŒŠá("Player " + this.Â.v_() + " just tried to change non-editable sign");
                return;
            }
            System.arraycopy(packetIn.Â(), 0, var5.Âµá€, 0, 4);
            var5.ŠÄ();
            var2.áŒŠÆ(var3);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C00PacketKeepAlive packetIn) {
        if (packetIn.HorizonCode_Horizon_È() == this.áŒŠÆ) {
            final int var2 = (int)(this.Ý() - this.áˆºÑ¢Õ);
            this.Â.Ø = (this.Â.Ø * 3 + var2) / 4;
        }
    }
    
    private long Ý() {
        return System.nanoTime() / 1000000L;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C13PacketPlayerAbilities packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        this.Â.áˆºáˆºáŠ.Â = (packetIn.Â() && this.Â.áˆºáˆºáŠ.Ý);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C14PacketTabComplete packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        final ArrayList var2 = Lists.newArrayList();
        for (final String var4 : this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Â, packetIn.HorizonCode_Horizon_È(), packetIn.Â())) {
            var2.add(var4);
        }
        this.Â.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S3APacketTabComplete(var2.toArray(new String[var2.size()])));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C15PacketClientSettings packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        this.Â.HorizonCode_Horizon_È(packetIn);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final C17PacketCustomPayload packetIn) {
        PacketThreadUtil.HorizonCode_Horizon_È(packetIn, this, this.Â.ÇŽÉ());
        if ("MC|BEdit".equals(packetIn.HorizonCode_Horizon_È())) {
            final PacketBuffer var2 = new PacketBuffer(Unpooled.wrappedBuffer((ByteBuf)packetIn.Â()));
            try {
                final ItemStack var3 = var2.Ø();
                if (var3 == null) {
                    return;
                }
                if (!ItemWritableBook.Â(var3.Å())) {
                    throw new IOException("Invalid book tag!");
                }
                final ItemStack var4 = this.Â.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
                if (var4 != null) {
                    if (var3.HorizonCode_Horizon_È() == Items.ŒÓ && var3.HorizonCode_Horizon_È() == var4.HorizonCode_Horizon_È()) {
                        var4.HorizonCode_Horizon_È("pages", var3.Å().Ý("pages", 8));
                    }
                    return;
                }
            }
            catch (Exception var5) {
                NetHandlerPlayServer.Ý.error("Couldn't handle book info", (Throwable)var5);
                return;
            }
            finally {
                var2.release();
            }
            var2.release();
            return;
        }
        if ("MC|BSign".equals(packetIn.HorizonCode_Horizon_È())) {
            final PacketBuffer var2 = new PacketBuffer(Unpooled.wrappedBuffer((ByteBuf)packetIn.Â()));
            try {
                final ItemStack var3 = var2.Ø();
                if (var3 == null) {
                    return;
                }
                if (!ItemEditableBook.Â(var3.Å())) {
                    throw new IOException("Invalid book tag!");
                }
                final ItemStack var4 = this.Â.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
                if (var4 != null) {
                    if (var3.HorizonCode_Horizon_È() == Items.ÇŽÊ && var4.HorizonCode_Horizon_È() == Items.ŒÓ) {
                        var4.HorizonCode_Horizon_È("author", new NBTTagString(this.Â.v_()));
                        var4.HorizonCode_Horizon_È("title", new NBTTagString(var3.Å().áˆºÑ¢Õ("title")));
                        var4.HorizonCode_Horizon_È("pages", var3.Å().Ý("pages", 8));
                        var4.HorizonCode_Horizon_È(Items.ÇŽÊ);
                    }
                    return;
                }
            }
            catch (Exception var6) {
                NetHandlerPlayServer.Ý.error("Couldn't sign book", (Throwable)var6);
                return;
            }
            finally {
                var2.release();
            }
            var2.release();
            return;
        }
        if ("MC|TrSel".equals(packetIn.HorizonCode_Horizon_È())) {
            try {
                final int var7 = packetIn.Â().readInt();
                final Container var8 = this.Â.Ï­Ï;
                if (var8 instanceof ContainerMerchant) {
                    ((ContainerMerchant)var8).Ø­áŒŠá(var7);
                }
            }
            catch (Exception var9) {
                NetHandlerPlayServer.Ý.error("Couldn't select trade", (Throwable)var9);
            }
        }
        else if ("MC|AdvCdm".equals(packetIn.HorizonCode_Horizon_È())) {
            if (!this.Ø­áŒŠá.ÇªÓ()) {
                this.Â.HorizonCode_Horizon_È(new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
            }
            else if (this.Â.HorizonCode_Horizon_È(2, "") && this.Â.áˆºáˆºáŠ.Ø­áŒŠá) {
                final PacketBuffer var2 = packetIn.Â();
                try {
                    final byte var10 = var2.readByte();
                    CommandBlockLogic var11 = null;
                    if (var10 == 0) {
                        final TileEntity var12 = this.Â.Ï­Ðƒà.HorizonCode_Horizon_È(new BlockPos(var2.readInt(), var2.readInt(), var2.readInt()));
                        if (var12 instanceof TileEntityCommandBlock) {
                            var11 = ((TileEntityCommandBlock)var12).HorizonCode_Horizon_È();
                        }
                    }
                    else if (var10 == 1) {
                        final Entity var13 = this.Â.Ï­Ðƒà.HorizonCode_Horizon_È(var2.readInt());
                        if (var13 instanceof EntityMinecartCommandBlock) {
                            var11 = ((EntityMinecartCommandBlock)var13).áŒŠÆ();
                        }
                    }
                    final String var14 = var2.Ý(var2.readableBytes());
                    final boolean var15 = var2.readBoolean();
                    if (var11 != null) {
                        var11.HorizonCode_Horizon_È(var14);
                        var11.HorizonCode_Horizon_È(var15);
                        if (!var15) {
                            var11.Â((IChatComponent)null);
                        }
                        var11.áˆºÑ¢Õ();
                        this.Â.HorizonCode_Horizon_È(new ChatComponentTranslation("advMode.setCommand.success", new Object[] { var14 }));
                    }
                }
                catch (Exception var16) {
                    NetHandlerPlayServer.Ý.error("Couldn't set command block", (Throwable)var16);
                    return;
                }
                finally {
                    var2.release();
                }
                var2.release();
            }
            else {
                this.Â.HorizonCode_Horizon_È(new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
            }
        }
        else if ("MC|Beacon".equals(packetIn.HorizonCode_Horizon_È())) {
            if (this.Â.Ï­Ï instanceof ContainerBeacon) {
                try {
                    final PacketBuffer var2 = packetIn.Â();
                    final int var17 = var2.readInt();
                    final int var18 = var2.readInt();
                    final ContainerBeacon var19 = (ContainerBeacon)this.Â.Ï­Ï;
                    final Slot var20 = var19.HorizonCode_Horizon_È(0);
                    if (var20.Â()) {
                        var20.HorizonCode_Horizon_È(1);
                        final IInventory var21 = var19.HorizonCode_Horizon_È();
                        var21.HorizonCode_Horizon_È(1, var17);
                        var21.HorizonCode_Horizon_È(2, var18);
                        var21.ŠÄ();
                    }
                }
                catch (Exception var22) {
                    NetHandlerPlayServer.Ý.error("Couldn't set beacon", (Throwable)var22);
                }
            }
        }
        else if ("MC|ItemName".equals(packetIn.HorizonCode_Horizon_È()) && this.Â.Ï­Ï instanceof ContainerRepair) {
            final ContainerRepair var23 = (ContainerRepair)this.Â.Ï­Ï;
            if (packetIn.Â() != null && packetIn.Â().readableBytes() >= 1) {
                final String var24 = ChatAllowedCharacters.HorizonCode_Horizon_È(packetIn.Â().Ý(32767));
                if (var24.length() <= 30) {
                    var23.HorizonCode_Horizon_È(var24);
                }
            }
            else {
                var23.HorizonCode_Horizon_È("");
            }
        }
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        static final int[] Ý;
        private static final String Ø­áŒŠá = "CL_00002269";
        
        static {
            Ý = new int[C16PacketClientStatus.HorizonCode_Horizon_È.values().length];
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.Ý[C16PacketClientStatus.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.Ý[C16PacketClientStatus.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.Ý[C16PacketClientStatus.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            Â = new int[C0BPacketEntityAction.HorizonCode_Horizon_È.values().length];
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.Â[C0BPacketEntityAction.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.Â[C0BPacketEntityAction.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.Â[C0BPacketEntityAction.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.Â[C0BPacketEntityAction.HorizonCode_Horizon_È.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.Â[C0BPacketEntityAction.HorizonCode_Horizon_È.Ý.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.Â[C0BPacketEntityAction.HorizonCode_Horizon_È.Ó.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.Â[C0BPacketEntityAction.HorizonCode_Horizon_È.à.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            HorizonCode_Horizon_È = new int[C07PacketPlayerDigging.HorizonCode_Horizon_È.values().length];
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[C07PacketPlayerDigging.HorizonCode_Horizon_È.Âµá€.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[C07PacketPlayerDigging.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[C07PacketPlayerDigging.HorizonCode_Horizon_È.Ó.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[C07PacketPlayerDigging.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError14) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[C07PacketPlayerDigging.HorizonCode_Horizon_È.Â.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError15) {}
            try {
                NetHandlerPlayServer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[C07PacketPlayerDigging.HorizonCode_Horizon_È.Ý.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError16) {}
        }
    }
}
