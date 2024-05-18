package HORIZON-6-0-SKIDPROTECTION;

import io.netty.buffer.Unpooled;
import java.util.HashSet;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
    private static final Logger áˆºÑ¢Õ;
    private String ÂµÈ;
    public NetHandlerPlayServer HorizonCode_Horizon_È;
    public final MinecraftServer Â;
    public final ItemInWorldManager Ý;
    public double Ø­áŒŠá;
    public double Âµá€;
    public final List Ó;
    private final List á;
    private final StatisticsFile ˆÏ­;
    private float µÐƒÓ;
    private float ¥áŒŠà;
    private int ˆÂ;
    private boolean áŒŠÈ;
    private int ˆØ­áˆº;
    private int £Ô;
    private HorizonCode_Horizon_È ŠÏ;
    private boolean ˆ;
    private long ŠÑ¢Ó;
    private Entity áˆºá;
    private int Ï­Ó;
    public boolean à;
    public int Ø;
    public boolean áŒŠÆ;
    private static final String ŠáŒŠà¢ = "CL_00001440";
    
    static {
        áˆºÑ¢Õ = LogManager.getLogger();
    }
    
    public EntityPlayerMP(final MinecraftServer server, final WorldServer worldIn, final GameProfile profile, final ItemInWorldManager interactionManager) {
        super(worldIn, profile);
        this.ÂµÈ = "en_US";
        this.Ó = Lists.newLinkedList();
        this.á = Lists.newLinkedList();
        this.µÐƒÓ = Float.MIN_VALUE;
        this.¥áŒŠà = -1.0E8f;
        this.ˆÂ = -99999999;
        this.áŒŠÈ = true;
        this.ˆØ­áˆº = -99999999;
        this.£Ô = 60;
        this.ˆ = true;
        this.ŠÑ¢Ó = System.currentTimeMillis();
        this.áˆºá = null;
        interactionManager.Â = this;
        this.Ý = interactionManager;
        BlockPos var5 = worldIn.áŒŠà();
        if (!worldIn.£à.Å() && worldIn.ŒÏ().µà() != WorldSettings.HorizonCode_Horizon_È.Ø­áŒŠá) {
            int var6 = Math.max(5, server.ˆÐƒØ­à() - 6);
            final int var7 = MathHelper.Ý(worldIn.áŠ().HorizonCode_Horizon_È(var5.HorizonCode_Horizon_È(), var5.Ý()));
            if (var7 < var6) {
                var6 = var7;
            }
            if (var7 <= 1) {
                var6 = 1;
            }
            var5 = worldIn.ˆà(var5.Â(this.ˆáƒ.nextInt(var6 * 2) - var6, 0, this.ˆáƒ.nextInt(var6 * 2) - var6));
        }
        this.Â = server;
        this.ˆÏ­ = server.Œ().HorizonCode_Horizon_È((EntityPlayer)this);
        this.HorizonCode_Horizon_È(var5, this.Ô = 0.0f, 0.0f);
        while (!worldIn.HorizonCode_Horizon_È(this, this.£É()).isEmpty() && this.Çªà¢ < 255.0) {
            this.Ý(this.ŒÏ, this.Çªà¢ + 1.0, this.Ê);
        }
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        if (tagCompund.Â("playerGameType", 99)) {
            if (MinecraftServer.áƒ().£Õ()) {
                this.Ý.HorizonCode_Horizon_È(MinecraftServer.áƒ().á());
            }
            else {
                this.Ý.HorizonCode_Horizon_È(WorldSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(tagCompund.Ó("playerGameType")));
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("playerGameType", this.Ý.HorizonCode_Horizon_È().HorizonCode_Horizon_È());
    }
    
    @Override
    public void Ø­à(final int p_82242_1_) {
        super.Ø­à(p_82242_1_);
        this.ˆØ­áˆº = -1;
    }
    
    @Override
    public void ¥Æ(final int p_71013_1_) {
        super.¥Æ(p_71013_1_);
        this.ˆØ­áˆº = -1;
    }
    
    public void Ø() {
        this.Ï­Ï.HorizonCode_Horizon_È((ICrafting)this);
    }
    
    @Override
    public void ˆÕ() {
        super.ˆÕ();
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S42PacketCombatEvent(this.ÇŽØ(), S42PacketCombatEvent.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
    }
    
    @Override
    public void ÇªÈ() {
        super.ÇªÈ();
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S42PacketCombatEvent(this.ÇŽØ(), S42PacketCombatEvent.HorizonCode_Horizon_È.Â));
    }
    
    @Override
    public void á() {
        this.Ý.Ø­áŒŠá();
        --this.£Ô;
        if (this.ˆÉ > 0) {
            --this.ˆÉ;
        }
        this.Ï­Ï.Ý();
        if (!this.Ï­Ðƒà.ŠÄ && !this.Ï­Ï.HorizonCode_Horizon_È((EntityPlayer)this)) {
            this.ˆà();
            this.Ï­Ï = this.ŒÂ;
        }
        while (!this.á.isEmpty()) {
            final int var1 = Math.min(this.á.size(), Integer.MAX_VALUE);
            final int[] var2 = new int[var1];
            final Iterator var3 = this.á.iterator();
            int var4 = 0;
            while (var3.hasNext() && var4 < var1) {
                var2[var4++] = var3.next();
                var3.remove();
            }
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S13PacketDestroyEntities(var2));
        }
        if (!this.Ó.isEmpty()) {
            final ArrayList var5 = Lists.newArrayList();
            final Iterator var6 = this.Ó.iterator();
            final ArrayList var7 = Lists.newArrayList();
            while (var6.hasNext() && var5.size() < 10) {
                final ChunkCoordIntPair var8 = var6.next();
                if (var8 != null) {
                    if (!this.Ï­Ðƒà.Ó(new BlockPos(var8.HorizonCode_Horizon_È << 4, 0, var8.Â << 4))) {
                        continue;
                    }
                    final Chunk var9 = this.Ï­Ðƒà.HorizonCode_Horizon_È(var8.HorizonCode_Horizon_È, var8.Â);
                    if (!var9.áŒŠÆ()) {
                        continue;
                    }
                    var5.add(var9);
                    var7.addAll(((WorldServer)this.Ï­Ðƒà).HorizonCode_Horizon_È(var8.HorizonCode_Horizon_È * 16, 0, var8.Â * 16, var8.HorizonCode_Horizon_È * 16 + 16, 256, var8.Â * 16 + 16));
                    var6.remove();
                }
                else {
                    var6.remove();
                }
            }
            if (!var5.isEmpty()) {
                if (var5.size() == 1) {
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S21PacketChunkData(var5.get(0), true, 65535));
                }
                else {
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S26PacketMapChunkBulk(var5));
                }
                for (final TileEntity var11 : var7) {
                    this.HorizonCode_Horizon_È(var11);
                }
                for (final Chunk var9 : var5) {
                    this.ÇŽÉ().ÇŽá€().HorizonCode_Horizon_È(this, var9);
                }
            }
        }
        final Entity var12 = this.ŒÐƒà();
        if (var12 != this) {
            if (!var12.Œ()) {
                this.µÕ(this);
            }
            else {
                this.HorizonCode_Horizon_È(var12.ŒÏ, var12.Çªà¢, var12.Ê, var12.É, var12.áƒ);
                this.Â.Œ().Ø­áŒŠá(this);
                if (this.Çªà¢()) {
                    this.µÕ(this);
                }
            }
        }
    }
    
    public void ¥Æ() {
        try {
            super.á();
            for (int var1 = 0; var1 < this.Ø­Ñ¢Ï­Ø­áˆº.áŒŠÆ(); ++var1) {
                final ItemStack var2 = this.Ø­Ñ¢Ï­Ø­áˆº.á(var1);
                if (var2 != null && var2.HorizonCode_Horizon_È().á()) {
                    final Packet var3 = ((ItemMapBase)var2.HorizonCode_Horizon_È()).Ø­áŒŠá(var2, this.Ï­Ðƒà, this);
                    if (var3 != null) {
                        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var3);
                    }
                }
            }
            if (this.Ï­Ä() != this.¥áŒŠà || this.ˆÂ != this.ŠØ.HorizonCode_Horizon_È() || this.ŠØ.Ø­áŒŠá() == 0.0f != this.áŒŠÈ) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S06PacketUpdateHealth(this.Ï­Ä(), this.ŠØ.HorizonCode_Horizon_È(), this.ŠØ.Ø­áŒŠá()));
                this.¥áŒŠà = this.Ï­Ä();
                this.ˆÂ = this.ŠØ.HorizonCode_Horizon_È();
                this.áŒŠÈ = (this.ŠØ.Ø­áŒŠá() == 0.0f);
            }
            if (this.Ï­Ä() + this.Ñ¢È() != this.µÐƒÓ) {
                this.µÐƒÓ = this.Ï­Ä() + this.Ñ¢È();
                final Collection var4 = this.ÇŽÅ().HorizonCode_Horizon_È(IScoreObjectiveCriteria.à);
                for (final ScoreObjective var6 : var4) {
                    this.ÇŽÅ().Â(this.v_(), var6).HorizonCode_Horizon_È(Arrays.asList(this));
                }
            }
            if (this.ÇŽØ != this.ˆØ­áˆº) {
                this.ˆØ­áˆº = this.ÇŽØ;
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S1FPacketSetExperience(this.ŒÓ, this.ÇŽØ, this.áŒŠÉ));
            }
            if (this.Œ % 20 * 5 == 0 && !this.áˆºÕ().HorizonCode_Horizon_È(AchievementList.à¢)) {
                this.Æ();
            }
        }
        catch (Throwable var8) {
            final CrashReport var7 = CrashReport.HorizonCode_Horizon_È(var8, "Ticking player");
            final CrashReportCategory var9 = var7.HorizonCode_Horizon_È("Player being ticked");
            this.HorizonCode_Horizon_È(var9);
            throw new ReportedException(var7);
        }
    }
    
    protected void Æ() {
        final BiomeGenBase var1 = this.Ï­Ðƒà.Ý(new BlockPos(MathHelper.Ý(this.ŒÏ), 0, MathHelper.Ý(this.Ê)));
        final String var2 = var1.£Ï;
        JsonSerializableSet var3 = (JsonSerializableSet)this.áˆºÕ().Â((StatBase)AchievementList.à¢);
        if (var3 == null) {
            var3 = (JsonSerializableSet)this.áˆºÕ().HorizonCode_Horizon_È(AchievementList.à¢, new JsonSerializableSet());
        }
        var3.add((Object)var2);
        if (this.áˆºÕ().Â(AchievementList.à¢) && var3.size() >= BiomeGenBase.£á.size()) {
            final HashSet var4 = Sets.newHashSet((Iterable)BiomeGenBase.£á);
            for (final String var6 : var3) {
                final Iterator var7 = var4.iterator();
                while (var7.hasNext()) {
                    final BiomeGenBase var8 = var7.next();
                    if (var8.£Ï.equals(var6)) {
                        var7.remove();
                    }
                }
                if (var4.isEmpty()) {
                    break;
                }
            }
            if (var4.isEmpty()) {
                this.HorizonCode_Horizon_È(AchievementList.à¢);
            }
        }
    }
    
    @Override
    public void Â(final DamageSource cause) {
        if (this.Ï­Ðƒà.Çªà¢().Â("showDeathMessages")) {
            final Team var2 = this.Çªáˆºá();
            if (var2 != null && var2.áŒŠÆ() != Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                if (var2.áŒŠÆ() == Team.HorizonCode_Horizon_È.Ý) {
                    this.Â.Œ().HorizonCode_Horizon_È(this, this.ÇŽØ().Â());
                }
                else if (var2.áŒŠÆ() == Team.HorizonCode_Horizon_È.Ø­áŒŠá) {
                    this.Â.Œ().Â(this, this.ÇŽØ().Â());
                }
            }
            else {
                this.Â.Œ().HorizonCode_Horizon_È(this.ÇŽØ().Â());
            }
        }
        if (!this.Ï­Ðƒà.Çªà¢().Â("keepInventory")) {
            this.Ø­Ñ¢Ï­Ø­áˆº.ÂµÈ();
        }
        final Collection var3 = this.Ï­Ðƒà.à¢().HorizonCode_Horizon_È(IScoreObjectiveCriteria.Ø­áŒŠá);
        for (final ScoreObjective var5 : var3) {
            final Score var6 = this.ÇŽÅ().Â(this.v_(), var5);
            var6.HorizonCode_Horizon_È();
        }
        final EntityLivingBase var7 = this.ŒÓ();
        if (var7 != null) {
            final EntityList.HorizonCode_Horizon_È var8 = EntityList.HorizonCode_Horizon_È.get(EntityList.HorizonCode_Horizon_È(var7));
            if (var8 != null) {
                this.HorizonCode_Horizon_È(var8.Âµá€);
            }
            var7.HorizonCode_Horizon_È(this, this.ˆØ);
        }
        this.HorizonCode_Horizon_È(StatList.áŒŠà);
        this.Â(StatList.Ø);
        this.ÇŽØ().Âµá€();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        final boolean var3 = this.Â.Ä() && this.Ø­È() && "fall".equals(source.£à);
        if (!var3 && this.£Ô > 0 && source != DamageSource.áˆºÑ¢Õ) {
            return false;
        }
        if (source instanceof EntityDamageSource) {
            final Entity var4 = source.áˆºÑ¢Õ();
            if (var4 instanceof EntityPlayer && !this.Ø­áŒŠá((EntityPlayer)var4)) {
                return false;
            }
            if (var4 instanceof EntityArrow) {
                final EntityArrow var5 = (EntityArrow)var4;
                if (var5.Ý instanceof EntityPlayer && !this.Ø­áŒŠá((EntityPlayer)var5.Ý)) {
                    return false;
                }
            }
        }
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer other) {
        return this.Ø­È() && super.Ø­áŒŠá(other);
    }
    
    private boolean Ø­È() {
        return this.Â.ÇŽá€();
    }
    
    @Override
    public void áŒŠÆ(int dimensionId) {
        if (this.ÇªÔ == 1 && dimensionId == 1) {
            this.HorizonCode_Horizon_È(AchievementList.Ê);
            this.Ï­Ðƒà.Â(this);
            this.áŒŠÆ = true;
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(4, 0.0f));
        }
        else {
            if (this.ÇªÔ == 0 && dimensionId == 1) {
                this.HorizonCode_Horizon_È(AchievementList.Çªà¢);
                final BlockPos var2 = this.Â.HorizonCode_Horizon_È(dimensionId).Ø­Âµ();
                if (var2 != null) {
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2.HorizonCode_Horizon_È(), var2.Â(), var2.Ý(), 0.0f, 0.0f);
                }
                dimensionId = 1;
            }
            else {
                this.HorizonCode_Horizon_È(AchievementList.áŒŠà);
            }
            this.Â.Œ().HorizonCode_Horizon_È(this, dimensionId);
            this.ˆØ­áˆº = -1;
            this.¥áŒŠà = -1.0f;
            this.ˆÂ = -1;
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayerMP p_174827_1_) {
        return p_174827_1_.Ø­áŒŠá() ? (this.ŒÐƒà() == this) : (!this.Ø­áŒŠá() && super.HorizonCode_Horizon_È(p_174827_1_));
    }
    
    private void HorizonCode_Horizon_È(final TileEntity p_147097_1_) {
        if (p_147097_1_ != null) {
            final Packet var2 = p_147097_1_.£á();
            if (var2 != null) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2);
            }
        }
    }
    
    @Override
    public void Â(final Entity p_71001_1_, final int p_71001_2_) {
        super.Â(p_71001_1_, p_71001_2_);
        this.Ï­Ï.Ý();
    }
    
    @Override
    public Â HorizonCode_Horizon_È(final BlockPos p_180469_1_) {
        final Â var2 = super.HorizonCode_Horizon_È(p_180469_1_);
        if (var2 == EntityPlayer.Â.HorizonCode_Horizon_È) {
            final S0APacketUseBed var3 = new S0APacketUseBed(this, p_180469_1_);
            this.ÇŽÉ().ÇŽá€().HorizonCode_Horizon_È(this, var3);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, this.É, this.áƒ);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var3);
        }
        return var2;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean p_70999_1_, final boolean updateWorldFlag, final boolean setSpawn) {
        if (this.Ï­Ó()) {
            this.ÇŽÉ().ÇŽá€().Â(this, new S0BPacketAnimation(this, 2));
        }
        super.HorizonCode_Horizon_È(p_70999_1_, updateWorldFlag, setSpawn);
        if (this.HorizonCode_Horizon_È != null) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, this.É, this.áƒ);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity entityIn) {
        final Entity var2 = this.Æ;
        super.HorizonCode_Horizon_È(entityIn);
        if (entityIn != var2) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S1BPacketEntityAttach(0, this, this.Æ));
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, this.É, this.áƒ);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final double p_180433_1_, final boolean p_180433_3_, final Block p_180433_4_, final BlockPos p_180433_5_) {
    }
    
    public void HorizonCode_Horizon_È(final double p_71122_1_, final boolean p_71122_3_) {
        final int var4 = MathHelper.Ý(this.ŒÏ);
        final int var5 = MathHelper.Ý(this.Çªà¢ - 0.20000000298023224);
        final int var6 = MathHelper.Ý(this.Ê);
        BlockPos var7 = new BlockPos(var4, var5, var6);
        Block var8 = this.Ï­Ðƒà.Â(var7).Ý();
        if (var8.Ó() == Material.HorizonCode_Horizon_È) {
            final Block var9 = this.Ï­Ðƒà.Â(var7.Âµá€()).Ý();
            if (var9 instanceof BlockFence || var9 instanceof BlockWall || var9 instanceof BlockFenceGate) {
                var7 = var7.Âµá€();
                var8 = this.Ï­Ðƒà.Â(var7).Ý();
            }
        }
        super.HorizonCode_Horizon_È(p_71122_1_, p_71122_3_, var8, var7);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntitySign p_175141_1_) {
        p_175141_1_.HorizonCode_Horizon_È(this);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S36PacketSignEditorOpen(p_175141_1_.á()));
    }
    
    private void Ñ¢Õ() {
        this.Ï­Ó = this.Ï­Ó % 100 + 1;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IInteractionObject guiOwner) {
        this.Ñ¢Õ();
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2DPacketOpenWindow(this.Ï­Ó, guiOwner.Ø­áŒŠá(), guiOwner.Ý()));
        this.Ï­Ï = guiOwner.HorizonCode_Horizon_È(this.Ø­Ñ¢Ï­Ø­áˆº, this);
        this.Ï­Ï.Ø­áŒŠá = this.Ï­Ó;
        this.Ï­Ï.HorizonCode_Horizon_È((ICrafting)this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IInventory chestInventory) {
        if (this.Ï­Ï != this.ŒÂ) {
            this.ˆà();
        }
        if (chestInventory instanceof ILockableContainer) {
            final ILockableContainer var2 = (ILockableContainer)chestInventory;
            if (var2.Ó() && !this.HorizonCode_Horizon_È(var2.x_()) && !this.Ø­áŒŠá()) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S02PacketChat(new ChatComponentTranslation("container.isLocked", new Object[] { chestInventory.Ý() }), (byte)2));
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S29PacketSoundEffect("random.door_close", this.ŒÏ, this.Çªà¢, this.Ê, 1.0f, 1.0f));
                return;
            }
        }
        this.Ñ¢Õ();
        if (chestInventory instanceof IInteractionObject) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2DPacketOpenWindow(this.Ï­Ó, ((IInteractionObject)chestInventory).Ø­áŒŠá(), chestInventory.Ý(), chestInventory.áŒŠÆ()));
            this.Ï­Ï = ((IInteractionObject)chestInventory).HorizonCode_Horizon_È(this.Ø­Ñ¢Ï­Ø­áˆº, this);
        }
        else {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2DPacketOpenWindow(this.Ï­Ó, "minecraft:container", chestInventory.Ý(), chestInventory.áŒŠÆ()));
            this.Ï­Ï = new ContainerChest(this.Ø­Ñ¢Ï­Ø­áˆº, chestInventory, this);
        }
        this.Ï­Ï.Ø­áŒŠá = this.Ï­Ó;
        this.Ï­Ï.HorizonCode_Horizon_È((ICrafting)this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IMerchant villager) {
        this.Ñ¢Õ();
        this.Ï­Ï = new ContainerMerchant(this.Ø­Ñ¢Ï­Ø­áˆº, villager, this.Ï­Ðƒà);
        this.Ï­Ï.Ø­áŒŠá = this.Ï­Ó;
        this.Ï­Ï.HorizonCode_Horizon_È((ICrafting)this);
        final InventoryMerchant var2 = ((ContainerMerchant)this.Ï­Ï).HorizonCode_Horizon_È();
        final IChatComponent var3 = villager.Ý();
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2DPacketOpenWindow(this.Ï­Ó, "minecraft:villager", var3, var2.áŒŠÆ()));
        final MerchantRecipeList var4 = villager.Â(this);
        if (var4 != null) {
            final PacketBuffer var5 = new PacketBuffer(Unpooled.buffer());
            var5.writeInt(this.Ï­Ó);
            var4.HorizonCode_Horizon_È(var5);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S3FPacketCustomPayload("MC|TrList", var5));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityHorse p_110298_1_, final IInventory p_110298_2_) {
        if (this.Ï­Ï != this.ŒÂ) {
            this.ˆà();
        }
        this.Ñ¢Õ();
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2DPacketOpenWindow(this.Ï­Ó, "EntityHorse", p_110298_2_.Ý(), p_110298_2_.áŒŠÆ(), p_110298_1_.ˆá()));
        this.Ï­Ï = new ContainerHorseInventory(this.Ø­Ñ¢Ï­Ø­áˆº, p_110298_2_, p_110298_1_, this);
        this.Ï­Ï.Ø­áŒŠá = this.Ï­Ó;
        this.Ï­Ï.HorizonCode_Horizon_È((ICrafting)this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack bookStack) {
        final Item_1028566121 var2 = bookStack.HorizonCode_Horizon_È();
        if (var2 == Items.ÇŽÊ) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Container p_71111_1_, final int p_71111_2_, final ItemStack p_71111_3_) {
        if (!(p_71111_1_.HorizonCode_Horizon_È(p_71111_2_) instanceof SlotCrafting) && !this.à) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2FPacketSetSlot(p_71111_1_.Ø­áŒŠá, p_71111_2_, p_71111_3_));
        }
    }
    
    public void HorizonCode_Horizon_È(final Container p_71120_1_) {
        this.HorizonCode_Horizon_È(p_71120_1_, p_71120_1_.Â());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Container p_71110_1_, final List p_71110_2_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S30PacketWindowItems(p_71110_1_.Ø­áŒŠá, p_71110_2_));
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2FPacketSetSlot(-1, -1, this.Ø­Ñ¢Ï­Ø­áˆº.á()));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Container p_71112_1_, final int p_71112_2_, final int p_71112_3_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S31PacketWindowProperty(p_71112_1_.Ø­áŒŠá, p_71112_2_, p_71112_3_));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Container p_175173_1_, final IInventory p_175173_2_) {
        for (int var3 = 0; var3 < p_175173_2_.Âµá€(); ++var3) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S31PacketWindowProperty(p_175173_1_.Ø­áŒŠá, var3, p_175173_2_.HorizonCode_Horizon_È(var3)));
        }
    }
    
    public void ˆà() {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2EPacketCloseWindow(this.Ï­Ï.Ø­áŒŠá));
        this.Ï­Ðƒà();
    }
    
    public void Šáƒ() {
        if (!this.à) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2FPacketSetSlot(-1, -1, this.Ø­Ñ¢Ï­Ø­áˆº.á()));
        }
    }
    
    public void Ï­Ðƒà() {
        this.Ï­Ï.Â((EntityPlayer)this);
        this.Ï­Ï = this.ŒÂ;
    }
    
    public void HorizonCode_Horizon_È(final float p_110430_1_, final float p_110430_2_, final boolean p_110430_3_, final boolean p_110430_4_) {
        if (this.Æ != null) {
            if (p_110430_1_ >= -1.0f && p_110430_1_ <= 1.0f) {
                this.£áƒ = p_110430_1_;
            }
            if (p_110430_2_ >= -1.0f && p_110430_2_ <= 1.0f) {
                this.Ï­áˆºÓ = p_110430_2_;
            }
            this.ÐƒÂ = p_110430_3_;
            this.Âµá€(p_110430_4_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StatBase p_71064_1_, final int p_71064_2_) {
        if (p_71064_1_ != null) {
            this.ˆÏ­.HorizonCode_Horizon_È(this, p_71064_1_, p_71064_2_);
            for (final ScoreObjective var4 : this.ÇŽÅ().HorizonCode_Horizon_È(p_71064_1_.ÂµÈ())) {
                this.ÇŽÅ().Â(this.v_(), var4).HorizonCode_Horizon_È(p_71064_2_);
            }
            if (this.ˆÏ­.Âµá€()) {
                this.ˆÏ­.HorizonCode_Horizon_È(this);
            }
        }
    }
    
    @Override
    public void Â(final StatBase p_175145_1_) {
        if (p_175145_1_ != null) {
            this.ˆÏ­.Â(this, p_175145_1_, 0);
            for (final ScoreObjective var3 : this.ÇŽÅ().HorizonCode_Horizon_È(p_175145_1_.ÂµÈ())) {
                this.ÇŽÅ().Â(this.v_(), var3).Ý(0);
            }
            if (this.ˆÏ­.Âµá€()) {
                this.ˆÏ­.HorizonCode_Horizon_È(this);
            }
        }
    }
    
    public void Ñ¢á() {
        if (this.µÕ != null) {
            this.µÕ.HorizonCode_Horizon_È((Entity)this);
        }
        if (this.ÇŽÈ) {
            this.HorizonCode_Horizon_È(true, false, false);
        }
    }
    
    public void ŒÏ() {
        this.¥áŒŠà = -1.0E8f;
    }
    
    @Override
    public void Â(final IChatComponent p_146105_1_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S02PacketChat(p_146105_1_));
    }
    
    @Override
    protected void µÐƒáƒ() {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S19PacketEntityStatus(this, (byte)9));
        super.µÐƒáƒ();
    }
    
    @Override
    public void Â(final ItemStack p_71008_1_, final int p_71008_2_) {
        super.Â(p_71008_1_, p_71008_2_);
        if (p_71008_1_ != null && p_71008_1_.HorizonCode_Horizon_È() != null && p_71008_1_.HorizonCode_Horizon_È().Ý(p_71008_1_) == EnumAction.Â) {
            this.ÇŽÉ().ÇŽá€().Â(this, new S0BPacketAnimation(this, 3));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer p_71049_1_, final boolean p_71049_2_) {
        super.HorizonCode_Horizon_È(p_71049_1_, p_71049_2_);
        this.ˆØ­áˆº = -1;
        this.¥áŒŠà = -1.0f;
        this.ˆÂ = -1;
        this.á.addAll(((EntityPlayerMP)p_71049_1_).á);
    }
    
    @Override
    protected void Ý(final PotionEffect p_70670_1_) {
        super.Ý(p_70670_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S1DPacketEntityEffect(this.ˆá(), p_70670_1_));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final PotionEffect p_70695_1_, final boolean p_70695_2_) {
        super.HorizonCode_Horizon_È(p_70695_1_, p_70695_2_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S1DPacketEntityEffect(this.ˆá(), p_70695_1_));
    }
    
    @Override
    protected void Ø­áŒŠá(final PotionEffect p_70688_1_) {
        super.Ø­áŒŠá(p_70688_1_);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S1EPacketRemoveEntityEffect(this.ˆá(), p_70688_1_));
    }
    
    @Override
    public void áˆºÑ¢Õ(final double p_70634_1_, final double p_70634_3_, final double p_70634_5_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_70634_1_, p_70634_3_, p_70634_5_, this.É, this.áƒ);
    }
    
    @Override
    public void Â(final Entity p_71009_1_) {
        this.ÇŽÉ().ÇŽá€().Â(this, new S0BPacketAnimation(p_71009_1_, 4));
    }
    
    @Override
    public void Ý(final Entity p_71047_1_) {
        this.ÇŽÉ().ÇŽá€().Â(this, new S0BPacketAnimation(p_71047_1_, 5));
    }
    
    @Override
    public void Ø­à() {
        if (this.HorizonCode_Horizon_È != null) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S39PacketPlayerAbilities(this.áˆºáˆºáŠ));
            this.ÇªÂ();
        }
    }
    
    public WorldServer ÇŽÉ() {
        return (WorldServer)this.Ï­Ðƒà;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È gameType) {
        this.Ý.HorizonCode_Horizon_È(gameType);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(3, gameType.HorizonCode_Horizon_È()));
        if (gameType == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
            this.HorizonCode_Horizon_È((Entity)null);
        }
        else {
            this.µÕ(this);
        }
        this.Ø­à();
        this.ÇªÅ();
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return this.Ý.HorizonCode_Horizon_È() == WorldSettings.HorizonCode_Horizon_È.Âµá€;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent message) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S02PacketChat(message));
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int permissionLevel, final String command) {
        if ("seed".equals(command) && !this.Â.Ä()) {
            return true;
        }
        if ("tell".equals(command) || "help".equals(command) || "me".equals(command) || "trigger".equals(command)) {
            return true;
        }
        if (this.Â.Œ().Âµá€(this.áˆºà())) {
            final UserListOpsEntry var3 = (UserListOpsEntry)this.Â.Œ().£á().HorizonCode_Horizon_È(this.áˆºà());
            return (var3 != null) ? (var3.HorizonCode_Horizon_È() >= permissionLevel) : (this.Â.£à() >= permissionLevel);
        }
        return false;
    }
    
    public String ÐƒÇŽà() {
        String var1 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â().toString();
        var1 = var1.substring(var1.indexOf("/") + 1);
        var1 = var1.substring(0, var1.indexOf(":"));
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final C15PacketClientSettings p_147100_1_) {
        this.ÂµÈ = p_147100_1_.HorizonCode_Horizon_È();
        this.ŠÏ = p_147100_1_.Â();
        this.ˆ = p_147100_1_.Ý();
        this.É().Â(10, (byte)p_147100_1_.Ø­áŒŠá());
    }
    
    public HorizonCode_Horizon_È ¥Ê() {
        return this.ŠÏ;
    }
    
    public void HorizonCode_Horizon_È(final String p_175397_1_, final String p_175397_2_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S48PacketResourcePackSend(p_175397_1_, p_175397_2_));
    }
    
    @Override
    public BlockPos £á() {
        return new BlockPos(this.ŒÏ, this.Çªà¢ + 0.5, this.Ê);
    }
    
    public void ÐƒÓ() {
        this.ŠÑ¢Ó = MinecraftServer.Œà();
    }
    
    public StatisticsFile áˆºÕ() {
        return this.ˆÏ­;
    }
    
    public void Ø­à(final Entity p_152339_1_) {
        if (p_152339_1_ instanceof EntityPlayer) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S13PacketDestroyEntities(new int[] { p_152339_1_.ˆá() }));
        }
        else {
            this.á.add(p_152339_1_.ˆá());
        }
    }
    
    @Override
    protected void ÇªÂ() {
        if (this.Ø­áŒŠá()) {
            this.ÂµáˆºÂ();
            this.Ó(true);
        }
        else {
            super.ÇªÂ();
        }
        this.ÇŽÉ().ÇŽá€().HorizonCode_Horizon_È(this);
    }
    
    public Entity ŒÐƒà() {
        return (this.áˆºá == null) ? this : this.áˆºá;
    }
    
    public void µÕ(final Entity p_175399_1_) {
        final Entity var2 = this.ŒÐƒà();
        this.áˆºá = ((p_175399_1_ == null) ? this : p_175399_1_);
        if (var2 != this.áˆºá) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S43PacketCamera(this.áˆºá));
            this.áˆºÑ¢Õ(this.áˆºá.ŒÏ, this.áˆºá.Çªà¢, this.áˆºá.Ê);
        }
    }
    
    @Override
    public void ¥Æ(final Entity targetEntity) {
        if (this.Ý.HorizonCode_Horizon_È() == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
            this.µÕ(targetEntity);
        }
        else {
            super.¥Æ(targetEntity);
        }
    }
    
    public long ÐƒáˆºÄ() {
        return this.ŠÑ¢Ó;
    }
    
    public IChatComponent áˆºÉ() {
        return null;
    }
}
