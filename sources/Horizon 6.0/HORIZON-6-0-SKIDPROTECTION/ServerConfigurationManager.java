package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.ArrayList;
import java.util.UUID;
import java.net.SocketAddress;
import java.util.HashSet;
import com.google.common.collect.Sets;
import java.util.Iterator;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import java.util.List;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.Logger;
import java.io.File;

public abstract class ServerConfigurationManager
{
    public static final File HorizonCode_Horizon_È;
    public static final File Â;
    public static final File Ý;
    public static final File Ø­áŒŠá;
    private static final Logger Ø;
    private static final SimpleDateFormat áŒŠÆ;
    private final MinecraftServer áˆºÑ¢Õ;
    public final List Âµá€;
    public final Map Ó;
    private final UserListBans ÂµÈ;
    private final BanList á;
    private final UserListOps ˆÏ­;
    private final UserListWhitelist £á;
    private final Map Å;
    private IPlayerFileData £à;
    private boolean µà;
    protected int à;
    private int ˆà;
    private WorldSettings.HorizonCode_Horizon_È ¥Æ;
    private boolean Ø­à;
    private int µÕ;
    private static final String Æ = "CL_00001423";
    
    static {
        HorizonCode_Horizon_È = new File("banned-players.json");
        Â = new File("banned-ips.json");
        Ý = new File("ops.json");
        Ø­áŒŠá = new File("whitelist.json");
        Ø = LogManager.getLogger();
        áŒŠÆ = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    }
    
    public ServerConfigurationManager(final MinecraftServer server) {
        this.Âµá€ = Lists.newArrayList();
        this.Ó = Maps.newHashMap();
        this.ÂµÈ = new UserListBans(ServerConfigurationManager.HorizonCode_Horizon_È);
        this.á = new BanList(ServerConfigurationManager.Â);
        this.ˆÏ­ = new UserListOps(ServerConfigurationManager.Ý);
        this.£á = new UserListWhitelist(ServerConfigurationManager.Ø­áŒŠá);
        this.Å = Maps.newHashMap();
        this.áˆºÑ¢Õ = server;
        this.ÂµÈ.HorizonCode_Horizon_È(false);
        this.á.HorizonCode_Horizon_È(false);
        this.à = 8;
    }
    
    public void HorizonCode_Horizon_È(final NetworkManager netManager, final EntityPlayerMP playerIn) {
        final GameProfile var3 = playerIn.áˆºà();
        final PlayerProfileCache var4 = this.áˆºÑ¢Õ.áŒŠÏ();
        final GameProfile var5 = var4.HorizonCode_Horizon_È(var3.getId());
        final String var6 = (var5 == null) ? var3.getName() : var5.getName();
        var4.HorizonCode_Horizon_È(var3);
        final NBTTagCompound var7 = this.Â(playerIn);
        playerIn.HorizonCode_Horizon_È(this.áˆºÑ¢Õ.HorizonCode_Horizon_È(playerIn.ÇªÔ));
        playerIn.Ý.HorizonCode_Horizon_È((WorldServer)playerIn.Ï­Ðƒà);
        String var8 = "local";
        if (netManager.Â() != null) {
            var8 = netManager.Â().toString();
        }
        ServerConfigurationManager.Ø.info(String.valueOf(playerIn.v_()) + "[" + var8 + "] logged in with entity id " + playerIn.ˆá() + " at (" + playerIn.ŒÏ + ", " + playerIn.Çªà¢ + ", " + playerIn.Ê + ")");
        final WorldServer var9 = this.áˆºÑ¢Õ.HorizonCode_Horizon_È(playerIn.ÇªÔ);
        final WorldInfo var10 = var9.ŒÏ();
        final BlockPos var11 = var9.áŒŠà();
        this.HorizonCode_Horizon_È(playerIn, null, var9);
        final NetHandlerPlayServer var12 = new NetHandlerPlayServer(this.áˆºÑ¢Õ, netManager, playerIn);
        var12.HorizonCode_Horizon_È(new S01PacketJoinGame(playerIn.ˆá(), playerIn.Ý.HorizonCode_Horizon_È(), var10.¥Æ(), var9.£à.µà(), var9.ŠÂµà(), this.ˆà(), var10.Ø­à(), var9.Çªà¢().Â("reducedDebugInfo")));
        var12.HorizonCode_Horizon_È(new S3FPacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).HorizonCode_Horizon_È(this.Ý().É())));
        var12.HorizonCode_Horizon_È(new S41PacketServerDifficulty(var10.Ï­Ðƒà(), var10.áŒŠà()));
        var12.HorizonCode_Horizon_È(new S05PacketSpawnPosition(var11));
        var12.HorizonCode_Horizon_È(new S39PacketPlayerAbilities(playerIn.áˆºáˆºáŠ));
        var12.HorizonCode_Horizon_È(new S09PacketHeldItemChange(playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý));
        playerIn.áˆºÕ().Ø­áŒŠá();
        playerIn.áˆºÕ().Â(playerIn);
        this.HorizonCode_Horizon_È((ServerScoreboard)var9.à¢(), playerIn);
        this.áˆºÑ¢Õ.ˆÓ();
        ChatComponentTranslation var13;
        if (!playerIn.v_().equalsIgnoreCase(var6)) {
            var13 = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[] { playerIn.Ý(), var6 });
        }
        else {
            var13 = new ChatComponentTranslation("multiplayer.player.joined", new Object[] { playerIn.Ý() });
        }
        var13.à().HorizonCode_Horizon_È(EnumChatFormatting.Å);
        this.HorizonCode_Horizon_È(var13);
        this.Ý(playerIn);
        var12.HorizonCode_Horizon_È(playerIn.ŒÏ, playerIn.Çªà¢, playerIn.Ê, playerIn.É, playerIn.áƒ);
        this.Â(playerIn, var9);
        if (this.áˆºÑ¢Õ.£ÂµÄ().length() > 0) {
            playerIn.HorizonCode_Horizon_È(this.áˆºÑ¢Õ.£ÂµÄ(), this.áˆºÑ¢Õ.Ø­Âµ());
        }
        for (final PotionEffect var15 : playerIn.ÇŽÈ()) {
            var12.HorizonCode_Horizon_È(new S1DPacketEntityEffect(playerIn.ˆá(), var15));
        }
        playerIn.Ø();
        if (var7 != null && var7.Â("Riding", 10)) {
            final Entity var16 = EntityList.HorizonCode_Horizon_È(var7.ˆÏ­("Riding"), var9);
            if (var16 != null) {
                var16.Šáƒ = true;
                var9.HorizonCode_Horizon_È(var16);
                playerIn.HorizonCode_Horizon_È(var16);
                var16.Šáƒ = false;
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final ServerScoreboard scoreboardIn, final EntityPlayerMP playerIn) {
        final HashSet var3 = Sets.newHashSet();
        for (final ScorePlayerTeam var5 : scoreboardIn.Âµá€()) {
            playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S3EPacketTeams(var5, 0));
        }
        for (int var6 = 0; var6 < 19; ++var6) {
            final ScoreObjective var7 = scoreboardIn.HorizonCode_Horizon_È(var6);
            if (var7 != null && !var3.contains(var7)) {
                final List var8 = scoreboardIn.Ó(var7);
                for (final Packet var10 : var8) {
                    playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var10);
                }
                var3.add(var7);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final WorldServer[] p_72364_1_) {
        this.£à = p_72364_1_[0].Ñ¢á().Âµá€();
        p_72364_1_[0].áŠ().HorizonCode_Horizon_È(new IBorderListener() {
            private static final String Â = "CL_00002267";
            
            @Override
            public void HorizonCode_Horizon_È(final WorldBorder border, final double newSize) {
                ServerConfigurationManager.this.HorizonCode_Horizon_È(new S44PacketWorldBorder(border, S44PacketWorldBorder.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
            }
            
            @Override
            public void HorizonCode_Horizon_È(final WorldBorder border, final double p_177692_2_, final double p_177692_4_, final long p_177692_6_) {
                ServerConfigurationManager.this.HorizonCode_Horizon_È(new S44PacketWorldBorder(border, S44PacketWorldBorder.HorizonCode_Horizon_È.Â));
            }
            
            @Override
            public void HorizonCode_Horizon_È(final WorldBorder border, final double x, final double z) {
                ServerConfigurationManager.this.HorizonCode_Horizon_È(new S44PacketWorldBorder(border, S44PacketWorldBorder.HorizonCode_Horizon_È.Ý));
            }
            
            @Override
            public void HorizonCode_Horizon_È(final WorldBorder border, final int p_177691_2_) {
                ServerConfigurationManager.this.HorizonCode_Horizon_È(new S44PacketWorldBorder(border, S44PacketWorldBorder.HorizonCode_Horizon_È.Âµá€));
            }
            
            @Override
            public void Â(final WorldBorder border, final int p_177690_2_) {
                ServerConfigurationManager.this.HorizonCode_Horizon_È(new S44PacketWorldBorder(border, S44PacketWorldBorder.HorizonCode_Horizon_È.Ó));
            }
            
            @Override
            public void Â(final WorldBorder border, final double p_177696_2_) {
            }
            
            @Override
            public void Ý(final WorldBorder border, final double p_177695_2_) {
            }
        });
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayerMP playerIn, final WorldServer worldIn) {
        final WorldServer var3 = playerIn.ÇŽÉ();
        if (worldIn != null) {
            worldIn.Ô().Ý(playerIn);
        }
        var3.Ô().HorizonCode_Horizon_È(playerIn);
        var3.ÇŽÉ.Ý((int)playerIn.ŒÏ >> 4, (int)playerIn.Ê >> 4);
    }
    
    public int Ø­áŒŠá() {
        return PlayerManager.Â(this.Ø­à());
    }
    
    public NBTTagCompound Â(final EntityPlayerMP playerIn) {
        final NBTTagCompound var2 = this.áˆºÑ¢Õ.Ý[0].ŒÏ().áŒŠÆ();
        NBTTagCompound var3;
        if (playerIn.v_().equals(this.áˆºÑ¢Õ.ŠÂµà()) && var2 != null) {
            playerIn.Ó(var2);
            var3 = var2;
            ServerConfigurationManager.Ø.debug("loading single player");
        }
        else {
            var3 = this.£à.Â(playerIn);
        }
        return var3;
    }
    
    protected void HorizonCode_Horizon_È(final EntityPlayerMP playerIn) {
        this.£à.HorizonCode_Horizon_È(playerIn);
        final StatisticsFile var2 = this.Å.get(playerIn.£áŒŠá());
        if (var2 != null) {
            var2.Â();
        }
    }
    
    public void Ý(final EntityPlayerMP playerIn) {
        this.Âµá€.add(playerIn);
        this.Ó.put(playerIn.£áŒŠá(), playerIn);
        this.HorizonCode_Horizon_È(new S38PacketPlayerListItem(S38PacketPlayerListItem.HorizonCode_Horizon_È.HorizonCode_Horizon_È, new EntityPlayerMP[] { playerIn }));
        final WorldServer var2 = this.áˆºÑ¢Õ.HorizonCode_Horizon_È(playerIn.ÇªÔ);
        var2.HorizonCode_Horizon_È(playerIn);
        this.HorizonCode_Horizon_È(playerIn, null);
        for (int var3 = 0; var3 < this.Âµá€.size(); ++var3) {
            final EntityPlayerMP var4 = this.Âµá€.get(var3);
            playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S38PacketPlayerListItem(S38PacketPlayerListItem.HorizonCode_Horizon_È.HorizonCode_Horizon_È, new EntityPlayerMP[] { var4 }));
        }
    }
    
    public void Ø­áŒŠá(final EntityPlayerMP playerIn) {
        playerIn.ÇŽÉ().Ô().Ø­áŒŠá(playerIn);
    }
    
    public void Âµá€(final EntityPlayerMP playerIn) {
        playerIn.HorizonCode_Horizon_È(StatList.Ó);
        this.HorizonCode_Horizon_È(playerIn);
        final WorldServer var2 = playerIn.ÇŽÉ();
        if (playerIn.Æ != null) {
            var2.Ó(playerIn.Æ);
            ServerConfigurationManager.Ø.debug("removing player mount");
        }
        var2.Â(playerIn);
        var2.Ô().Ý(playerIn);
        this.Âµá€.remove(playerIn);
        this.Ó.remove(playerIn.£áŒŠá());
        this.Å.remove(playerIn.£áŒŠá());
        this.HorizonCode_Horizon_È(new S38PacketPlayerListItem(S38PacketPlayerListItem.HorizonCode_Horizon_È.Âµá€, new EntityPlayerMP[] { playerIn }));
    }
    
    public String HorizonCode_Horizon_È(final SocketAddress address, final GameProfile profile) {
        if (this.ÂµÈ.HorizonCode_Horizon_È(profile)) {
            final UserListBansEntry var5 = (UserListBansEntry)this.ÂµÈ.HorizonCode_Horizon_È(profile);
            String var6 = "You are banned from this server!\nReason: " + var5.Â();
            if (var5.HorizonCode_Horizon_È() != null) {
                var6 = String.valueOf(var6) + "\nYour ban will be removed on " + ServerConfigurationManager.áŒŠÆ.format(var5.HorizonCode_Horizon_È());
            }
            return var6;
        }
        if (!this.Ø­áŒŠá(profile)) {
            return "You are not white-listed on this server!";
        }
        if (this.á.HorizonCode_Horizon_È(address)) {
            final IPBanEntry var7 = this.á.Â(address);
            String var6 = "Your IP address is banned from this server!\nReason: " + var7.Â();
            if (var7.HorizonCode_Horizon_È() != null) {
                var6 = String.valueOf(var6) + "\nYour ban will be removed on " + ServerConfigurationManager.áŒŠÆ.format(var7.HorizonCode_Horizon_È());
            }
            return var6;
        }
        return (this.Âµá€.size() >= this.à) ? "The server is full!" : null;
    }
    
    public EntityPlayerMP HorizonCode_Horizon_È(final GameProfile profile) {
        final UUID var2 = EntityPlayer.HorizonCode_Horizon_È(profile);
        final ArrayList var3 = Lists.newArrayList();
        for (int var4 = 0; var4 < this.Âµá€.size(); ++var4) {
            final EntityPlayerMP var5 = this.Âµá€.get(var4);
            if (var5.£áŒŠá().equals(var2)) {
                var3.add(var5);
            }
        }
        final Iterator var6 = var3.iterator();
        while (var6.hasNext()) {
            final EntityPlayerMP var5 = var6.next();
            var5.HorizonCode_Horizon_È.HorizonCode_Horizon_È("You logged in from another location");
        }
        Object var7;
        if (this.áˆºÑ¢Õ.áŠ()) {
            var7 = new DemoWorldManager(this.áˆºÑ¢Õ.HorizonCode_Horizon_È(0));
        }
        else {
            var7 = new ItemInWorldManager(this.áˆºÑ¢Õ.HorizonCode_Horizon_È(0));
        }
        return new EntityPlayerMP(this.áˆºÑ¢Õ, this.áˆºÑ¢Õ.HorizonCode_Horizon_È(0), profile, (ItemInWorldManager)var7);
    }
    
    public EntityPlayerMP HorizonCode_Horizon_È(final EntityPlayerMP playerIn, final int dimension, final boolean conqueredEnd) {
        playerIn.ÇŽÉ().ÇŽá€().Â(playerIn);
        playerIn.ÇŽÉ().ÇŽá€().Â((Entity)playerIn);
        playerIn.ÇŽÉ().Ô().Ý(playerIn);
        this.Âµá€.remove(playerIn);
        this.áˆºÑ¢Õ.HorizonCode_Horizon_È(playerIn.ÇªÔ).Ó(playerIn);
        final BlockPos var4 = playerIn.ÇŽÄ();
        final boolean var5 = playerIn.ˆÈ();
        playerIn.ÇªÔ = dimension;
        Object var6;
        if (this.áˆºÑ¢Õ.áŠ()) {
            var6 = new DemoWorldManager(this.áˆºÑ¢Õ.HorizonCode_Horizon_È(playerIn.ÇªÔ));
        }
        else {
            var6 = new ItemInWorldManager(this.áˆºÑ¢Õ.HorizonCode_Horizon_È(playerIn.ÇªÔ));
        }
        final EntityPlayerMP var7 = new EntityPlayerMP(this.áˆºÑ¢Õ, this.áˆºÑ¢Õ.HorizonCode_Horizon_È(playerIn.ÇªÔ), playerIn.áˆºà(), (ItemInWorldManager)var6);
        var7.HorizonCode_Horizon_È = playerIn.HorizonCode_Horizon_È;
        var7.HorizonCode_Horizon_È(playerIn, conqueredEnd);
        var7.Ø­áŒŠá(playerIn.ˆá());
        var7.á(playerIn);
        final WorldServer var8 = this.áˆºÑ¢Õ.HorizonCode_Horizon_È(playerIn.ÇªÔ);
        this.HorizonCode_Horizon_È(var7, playerIn, var8);
        if (var4 != null) {
            final BlockPos var9 = EntityPlayer.HorizonCode_Horizon_È(this.áˆºÑ¢Õ.HorizonCode_Horizon_È(playerIn.ÇªÔ), var4, var5);
            if (var9 != null) {
                var7.Â(var9.HorizonCode_Horizon_È() + 0.5f, var9.Â() + 0.1f, var9.Ý() + 0.5f, 0.0f, 0.0f);
                var7.HorizonCode_Horizon_È(var4, var5);
            }
            else {
                var7.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(0, 0.0f));
            }
        }
        var8.ÇŽÉ.Ý((int)var7.ŒÏ >> 4, (int)var7.Ê >> 4);
        while (!var8.HorizonCode_Horizon_È(var7, var7.£É()).isEmpty() && var7.Çªà¢ < 256.0) {
            var7.Ý(var7.ŒÏ, var7.Çªà¢ + 1.0, var7.Ê);
        }
        var7.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S07PacketRespawn(var7.ÇªÔ, var7.Ï­Ðƒà.ŠÂµà(), var7.Ï­Ðƒà.ŒÏ().Ø­à(), var7.Ý.HorizonCode_Horizon_È()));
        final BlockPos var9 = var8.áŒŠà();
        var7.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var7.ŒÏ, var7.Çªà¢, var7.Ê, var7.É, var7.áƒ);
        var7.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S05PacketSpawnPosition(var9));
        var7.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S1FPacketSetExperience(var7.ŒÓ, var7.ÇŽØ, var7.áŒŠÉ));
        this.Â(var7, var8);
        var8.Ô().HorizonCode_Horizon_È(var7);
        var8.HorizonCode_Horizon_È(var7);
        this.Âµá€.add(var7);
        this.Ó.put(var7.£áŒŠá(), var7);
        var7.Ø();
        var7.áˆºÑ¢Õ(var7.Ï­Ä());
        return var7;
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayerMP playerIn, final int dimension) {
        final int var3 = playerIn.ÇªÔ;
        final WorldServer var4 = this.áˆºÑ¢Õ.HorizonCode_Horizon_È(playerIn.ÇªÔ);
        playerIn.ÇªÔ = dimension;
        final WorldServer var5 = this.áˆºÑ¢Õ.HorizonCode_Horizon_È(playerIn.ÇªÔ);
        playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S07PacketRespawn(playerIn.ÇªÔ, playerIn.Ï­Ðƒà.ŠÂµà(), playerIn.Ï­Ðƒà.ŒÏ().Ø­à(), playerIn.Ý.HorizonCode_Horizon_È()));
        var4.Ó(playerIn);
        playerIn.ˆáŠ = false;
        this.HorizonCode_Horizon_È(playerIn, var3, var4, var5);
        this.HorizonCode_Horizon_È(playerIn, var4);
        playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(playerIn.ŒÏ, playerIn.Çªà¢, playerIn.Ê, playerIn.É, playerIn.áƒ);
        playerIn.Ý.HorizonCode_Horizon_È(var5);
        this.Â(playerIn, var5);
        this.Ó(playerIn);
        for (final PotionEffect var7 : playerIn.ÇŽÈ()) {
            playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S1DPacketEntityEffect(playerIn.ˆá(), var7));
        }
    }
    
    public void HorizonCode_Horizon_È(final Entity entityIn, final int p_82448_2_, final WorldServer p_82448_3_, final WorldServer p_82448_4_) {
        double var5 = entityIn.ŒÏ;
        double var6 = entityIn.Ê;
        final double var7 = 8.0;
        final float var8 = entityIn.É;
        p_82448_3_.Ï­Ðƒà.HorizonCode_Horizon_È("moving");
        if (entityIn.ÇªÔ == -1) {
            var5 = MathHelper.HorizonCode_Horizon_È(var5 / var7, p_82448_4_.áŠ().Ø­áŒŠá() + 16.0, p_82448_4_.áŠ().Ó() - 16.0);
            var6 = MathHelper.HorizonCode_Horizon_È(var6 / var7, p_82448_4_.áŠ().Âµá€() + 16.0, p_82448_4_.áŠ().à() - 16.0);
            entityIn.Â(var5, entityIn.Çªà¢, var6, entityIn.É, entityIn.áƒ);
            if (entityIn.Œ()) {
                p_82448_3_.HorizonCode_Horizon_È(entityIn, false);
            }
        }
        else if (entityIn.ÇªÔ == 0) {
            var5 = MathHelper.HorizonCode_Horizon_È(var5 * var7, p_82448_4_.áŠ().Ø­áŒŠá() + 16.0, p_82448_4_.áŠ().Ó() - 16.0);
            var6 = MathHelper.HorizonCode_Horizon_È(var6 * var7, p_82448_4_.áŠ().Âµá€() + 16.0, p_82448_4_.áŠ().à() - 16.0);
            entityIn.Â(var5, entityIn.Çªà¢, var6, entityIn.É, entityIn.áƒ);
            if (entityIn.Œ()) {
                p_82448_3_.HorizonCode_Horizon_È(entityIn, false);
            }
        }
        else {
            BlockPos var9;
            if (p_82448_2_ == 1) {
                var9 = p_82448_4_.áŒŠà();
            }
            else {
                var9 = p_82448_4_.Ø­Âµ();
            }
            var5 = var9.HorizonCode_Horizon_È();
            entityIn.Çªà¢ = var9.Â();
            var6 = var9.Ý();
            entityIn.Â(var5, entityIn.Çªà¢, var6, 90.0f, 0.0f);
            if (entityIn.Œ()) {
                p_82448_3_.HorizonCode_Horizon_È(entityIn, false);
            }
        }
        p_82448_3_.Ï­Ðƒà.Â();
        if (p_82448_2_ != 1) {
            p_82448_3_.Ï­Ðƒà.HorizonCode_Horizon_È("placing");
            var5 = MathHelper.HorizonCode_Horizon_È((int)var5, -29999872, 29999872);
            var6 = MathHelper.HorizonCode_Horizon_È((int)var6, -29999872, 29999872);
            if (entityIn.Œ()) {
                entityIn.Â(var5, entityIn.Çªà¢, var6, entityIn.É, entityIn.áƒ);
                p_82448_4_.ÇªÓ().HorizonCode_Horizon_È(entityIn, var8);
                p_82448_4_.HorizonCode_Horizon_È(entityIn);
                p_82448_4_.HorizonCode_Horizon_È(entityIn, false);
            }
            p_82448_3_.Ï­Ðƒà.Â();
        }
        entityIn.HorizonCode_Horizon_È(p_82448_4_);
    }
    
    public void Âµá€() {
        if (++this.µÕ > 600) {
            this.HorizonCode_Horizon_È(new S38PacketPlayerListItem(S38PacketPlayerListItem.HorizonCode_Horizon_È.Ý, this.Âµá€));
            this.µÕ = 0;
        }
    }
    
    public void HorizonCode_Horizon_È(final Packet packetIn) {
        for (int var2 = 0; var2 < this.Âµá€.size(); ++var2) {
            this.Âµá€.get(var2).HorizonCode_Horizon_È.HorizonCode_Horizon_È(packetIn);
        }
    }
    
    public void HorizonCode_Horizon_È(final Packet packetIn, final int dimension) {
        for (int var3 = 0; var3 < this.Âµá€.size(); ++var3) {
            final EntityPlayerMP var4 = this.Âµá€.get(var3);
            if (var4.ÇªÔ == dimension) {
                var4.HorizonCode_Horizon_È.HorizonCode_Horizon_È(packetIn);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_177453_1_, final IChatComponent p_177453_2_) {
        final Team var3 = p_177453_1_.Çªáˆºá();
        if (var3 != null) {
            final Collection var4 = var3.Ý();
            for (final String var6 : var4) {
                final EntityPlayerMP var7 = this.HorizonCode_Horizon_È(var6);
                if (var7 != null && var7 != p_177453_1_) {
                    var7.HorizonCode_Horizon_È(p_177453_2_);
                }
            }
        }
    }
    
    public void Â(final EntityPlayer p_177452_1_, final IChatComponent p_177452_2_) {
        final Team var3 = p_177452_1_.Çªáˆºá();
        if (var3 == null) {
            this.HorizonCode_Horizon_È(p_177452_2_);
        }
        else {
            for (int var4 = 0; var4 < this.Âµá€.size(); ++var4) {
                final EntityPlayerMP var5 = this.Âµá€.get(var4);
                if (var5.Çªáˆºá() != var3) {
                    var5.HorizonCode_Horizon_È(p_177452_2_);
                }
            }
        }
    }
    
    public String Ó() {
        String var1 = "";
        for (int var2 = 0; var2 < this.Âµá€.size(); ++var2) {
            if (var2 > 0) {
                var1 = String.valueOf(var1) + ", ";
            }
            var1 = String.valueOf(var1) + this.Âµá€.get(var2).v_();
        }
        return var1;
    }
    
    public String[] à() {
        final String[] var1 = new String[this.Âµá€.size()];
        for (int var2 = 0; var2 < this.Âµá€.size(); ++var2) {
            var1[var2] = this.Âµá€.get(var2).v_();
        }
        return var1;
    }
    
    public GameProfile[] Ø() {
        final GameProfile[] var1 = new GameProfile[this.Âµá€.size()];
        for (int var2 = 0; var2 < this.Âµá€.size(); ++var2) {
            var1[var2] = this.Âµá€.get(var2).áˆºà();
        }
        return var1;
    }
    
    public UserListBans áŒŠÆ() {
        return this.ÂµÈ;
    }
    
    public BanList áˆºÑ¢Õ() {
        return this.á;
    }
    
    public void Â(final GameProfile profile) {
        this.ˆÏ­.HorizonCode_Horizon_È(new UserListOpsEntry(profile, this.áˆºÑ¢Õ.£à()));
    }
    
    public void Ý(final GameProfile profile) {
        this.ˆÏ­.Â(profile);
    }
    
    public boolean Ø­áŒŠá(final GameProfile profile) {
        return !this.µà || this.ˆÏ­.Ø­áŒŠá(profile) || this.£á.Ø­áŒŠá(profile);
    }
    
    public boolean Âµá€(final GameProfile profile) {
        return this.ˆÏ­.Ø­áŒŠá(profile) || (this.áˆºÑ¢Õ.¥à() && this.áˆºÑ¢Õ.Ý[0].ŒÏ().µÕ() && this.áˆºÑ¢Õ.ŠÂµà().equalsIgnoreCase(profile.getName())) || this.Ø­à;
    }
    
    public EntityPlayerMP HorizonCode_Horizon_È(final String username) {
        for (final EntityPlayerMP var3 : this.Âµá€) {
            if (var3.v_().equalsIgnoreCase(username)) {
                return var3;
            }
        }
        return null;
    }
    
    public void HorizonCode_Horizon_È(final double x, final double y, final double z, final double radius, final int dimension, final Packet packetIn) {
        this.HorizonCode_Horizon_È(null, x, y, z, radius, dimension, packetIn);
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_148543_1_, final double x, final double y, final double z, final double radius, final int dimension, final Packet p_148543_11_) {
        for (int var12 = 0; var12 < this.Âµá€.size(); ++var12) {
            final EntityPlayerMP var13 = this.Âµá€.get(var12);
            if (var13 != p_148543_1_ && var13.ÇªÔ == dimension) {
                final double var14 = x - var13.ŒÏ;
                final double var15 = y - var13.Çªà¢;
                final double var16 = z - var13.Ê;
                if (var14 * var14 + var15 * var15 + var16 * var16 < radius * radius) {
                    var13.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_148543_11_);
                }
            }
        }
    }
    
    public void ÂµÈ() {
        for (int var1 = 0; var1 < this.Âµá€.size(); ++var1) {
            this.HorizonCode_Horizon_È(this.Âµá€.get(var1));
        }
    }
    
    public void Ó(final GameProfile profile) {
        this.£á.HorizonCode_Horizon_È(new UserListWhitelistEntry(profile));
    }
    
    public void à(final GameProfile profile) {
        this.£á.Â(profile);
    }
    
    public UserListWhitelist á() {
        return this.£á;
    }
    
    public String[] ˆÏ­() {
        return this.£á.Â();
    }
    
    public UserListOps £á() {
        return this.ˆÏ­;
    }
    
    public String[] Å() {
        return this.ˆÏ­.Â();
    }
    
    public void £à() {
    }
    
    public void Â(final EntityPlayerMP playerIn, final WorldServer worldIn) {
        final WorldBorder var3 = this.áˆºÑ¢Õ.Ý[0].áŠ();
        playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S44PacketWorldBorder(var3, S44PacketWorldBorder.HorizonCode_Horizon_È.Ø­áŒŠá));
        playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S03PacketTimeUpdate(worldIn.Šáƒ(), worldIn.Ï­Ðƒà(), worldIn.Çªà¢().Â("doDaylightCycle")));
        if (worldIn.ˆá()) {
            playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(1, 0.0f));
            playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(7, worldIn.áˆºÑ¢Õ(1.0f)));
            playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(8, worldIn.Ø(1.0f)));
        }
    }
    
    public void Ó(final EntityPlayerMP playerIn) {
        playerIn.HorizonCode_Horizon_È(playerIn.ŒÂ);
        playerIn.ŒÏ();
        playerIn.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S09PacketHeldItemChange(playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý));
    }
    
    public int µà() {
        return this.Âµá€.size();
    }
    
    public int ˆà() {
        return this.à;
    }
    
    public String[] ¥Æ() {
        return this.áˆºÑ¢Õ.Ý[0].Ñ¢á().Âµá€().Â();
    }
    
    public void HorizonCode_Horizon_È(final boolean whitelistEnabled) {
        this.µà = whitelistEnabled;
    }
    
    public List Â(final String address) {
        final ArrayList var2 = Lists.newArrayList();
        for (final EntityPlayerMP var4 : this.Âµá€) {
            if (var4.ÐƒÇŽà().equals(address)) {
                var2.add(var4);
            }
        }
        return var2;
    }
    
    public int Ø­à() {
        return this.ˆà;
    }
    
    public MinecraftServer Ý() {
        return this.áˆºÑ¢Õ;
    }
    
    public NBTTagCompound Â() {
        return null;
    }
    
    public void HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È p_152604_1_) {
        this.¥Æ = p_152604_1_;
    }
    
    private void HorizonCode_Horizon_È(final EntityPlayerMP p_72381_1_, final EntityPlayerMP p_72381_2_, final World worldIn) {
        if (p_72381_2_ != null) {
            p_72381_1_.Ý.HorizonCode_Horizon_È(p_72381_2_.Ý.HorizonCode_Horizon_È());
        }
        else if (this.¥Æ != null) {
            p_72381_1_.Ý.HorizonCode_Horizon_È(this.¥Æ);
        }
        p_72381_1_.Ý.Â(worldIn.ŒÏ().µà());
    }
    
    public void Â(final boolean p_72387_1_) {
        this.Ø­à = p_72387_1_;
    }
    
    public void µÕ() {
        for (int var1 = 0; var1 < this.Âµá€.size(); ++var1) {
            this.Âµá€.get(var1).HorizonCode_Horizon_È.HorizonCode_Horizon_È("Server closed");
        }
    }
    
    public void HorizonCode_Horizon_È(final IChatComponent component, final boolean isChat) {
        this.áˆºÑ¢Õ.HorizonCode_Horizon_È(component);
        final int var3 = isChat ? 1 : 0;
        this.HorizonCode_Horizon_È(new S02PacketChat(component, (byte)var3));
    }
    
    public void HorizonCode_Horizon_È(final IChatComponent component) {
        this.HorizonCode_Horizon_È(component, true);
    }
    
    public StatisticsFile HorizonCode_Horizon_È(final EntityPlayer playerIn) {
        final UUID var2 = playerIn.£áŒŠá();
        StatisticsFile var3 = (var2 == null) ? null : this.Å.get(var2);
        if (var3 == null) {
            final File var4 = new File(this.áˆºÑ¢Õ.HorizonCode_Horizon_È(0).Ñ¢á().Ó(), "stats");
            final File var5 = new File(var4, String.valueOf(var2.toString()) + ".json");
            if (!var5.exists()) {
                final File var6 = new File(var4, String.valueOf(playerIn.v_()) + ".json");
                if (var6.exists() && var6.isFile()) {
                    var6.renameTo(var5);
                }
            }
            var3 = new StatisticsFile(this.áˆºÑ¢Õ, var5);
            var3.HorizonCode_Horizon_È();
            this.Å.put(var2, var3);
        }
        return var3;
    }
    
    public void HorizonCode_Horizon_È(final int distance) {
        this.ˆà = distance;
        if (this.áˆºÑ¢Õ.Ý != null) {
            for (final WorldServer var5 : this.áˆºÑ¢Õ.Ý) {
                if (var5 != null) {
                    var5.Ô().HorizonCode_Horizon_È(distance);
                }
            }
        }
    }
    
    public EntityPlayerMP HorizonCode_Horizon_È(final UUID p_177451_1_) {
        return this.Ó.get(p_177451_1_);
    }
}
