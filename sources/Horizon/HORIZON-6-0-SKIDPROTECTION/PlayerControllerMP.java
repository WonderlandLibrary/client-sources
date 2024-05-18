package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.ArrayList;
import java.io.File;

public class PlayerControllerMP
{
    private final Minecraft HorizonCode_Horizon_È;
    private final NetHandlerPlayClient Â;
    private BlockPos Ý;
    private ItemStack Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    private int à;
    private boolean Ø;
    private WorldSettings.HorizonCode_Horizon_È áŒŠÆ;
    private int áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00000881";
    
    public PlayerControllerMP(final Minecraft mcIn, final NetHandlerPlayClient p_i45062_2_) {
        this.Ý = new BlockPos(-1, -1, -1);
        this.áŒŠÆ = WorldSettings.HorizonCode_Horizon_È.Â;
        this.HorizonCode_Horizon_È = mcIn;
        this.Â = p_i45062_2_;
    }
    
    public static void HorizonCode_Horizon_È(final Minecraft mcIn, final PlayerControllerMP p_178891_1_, final BlockPos p_178891_2_, final EnumFacing p_178891_3_) {
        if (!mcIn.áŒŠÆ.HorizonCode_Horizon_È(mcIn.á, p_178891_2_, p_178891_3_)) {
            p_178891_1_.HorizonCode_Horizon_È(p_178891_2_, p_178891_3_);
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_78748_1_) {
        this.áŒŠÆ.HorizonCode_Horizon_È(p_78748_1_.áˆºáˆºáŠ);
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.áŒŠÆ == WorldSettings.HorizonCode_Horizon_È.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final WorldSettings.HorizonCode_Horizon_È p_78746_1_) {
        (this.áŒŠÆ = p_78746_1_).HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.á.áˆºáˆºáŠ);
    }
    
    public void Â(final EntityPlayer playerIn) {
        playerIn.É = -180.0f;
    }
    
    public boolean Â() {
        return this.áŒŠÆ.Âµá€();
    }
    
    public boolean HorizonCode_Horizon_È(final BlockPos p_178888_1_, final EnumFacing p_178888_2_) {
        if (this.áŒŠÆ.Ý()) {
            if (this.áŒŠÆ == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
                return false;
            }
            if (!this.HorizonCode_Horizon_È.á.ŠáˆºÂ()) {
                final Block var3 = this.HorizonCode_Horizon_È.áŒŠÆ.Â(p_178888_1_).Ý();
                final ItemStack var4 = this.HorizonCode_Horizon_È.á.áŒŠá();
                if (var4 == null) {
                    return false;
                }
                if (!var4.Ý(var3)) {
                    return false;
                }
            }
        }
        if (this.áŒŠÆ.Ø­áŒŠá() && this.HorizonCode_Horizon_È.á.Çª() != null && this.HorizonCode_Horizon_È.á.Çª().HorizonCode_Horizon_È() instanceof ItemSword) {
            return false;
        }
        final WorldClient var5 = this.HorizonCode_Horizon_È.áŒŠÆ;
        final IBlockState var6 = var5.Â(p_178888_1_);
        final Block var7 = var6.Ý();
        if (var7.Ó() == Material.HorizonCode_Horizon_È) {
            return false;
        }
        var5.Â(2001, p_178888_1_, Block.HorizonCode_Horizon_È(var6));
        final boolean var8 = var5.Ø(p_178888_1_);
        if (var8) {
            var7.Â(var5, p_178888_1_, var6);
        }
        this.Ý = new BlockPos(this.Ý.HorizonCode_Horizon_È(), -1, this.Ý.Ý());
        if (!this.áŒŠÆ.Ø­áŒŠá()) {
            final ItemStack var9 = this.HorizonCode_Horizon_È.á.áŒŠá();
            if (var9 != null) {
                var9.HorizonCode_Horizon_È(var5, var7, p_178888_1_, this.HorizonCode_Horizon_È.á);
                if (var9.Â == 0) {
                    this.HorizonCode_Horizon_È.á.ˆØ();
                }
            }
        }
        return var8;
    }
    
    public boolean Â(final BlockPos p_180511_1_, final EnumFacing p_180511_2_) {
        if (this.áŒŠÆ.Ý()) {
            if (this.áŒŠÆ == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
                return false;
            }
            if (!this.HorizonCode_Horizon_È.á.ŠáˆºÂ()) {
                final Block var3 = this.HorizonCode_Horizon_È.áŒŠÆ.Â(p_180511_1_).Ý();
                final ItemStack var4 = this.HorizonCode_Horizon_È.á.áŒŠá();
                if (var4 == null) {
                    return false;
                }
                if (!var4.Ý(var3)) {
                    return false;
                }
            }
        }
        if (!this.HorizonCode_Horizon_È.áŒŠÆ.áŠ().HorizonCode_Horizon_È(p_180511_1_)) {
            return false;
        }
        if (this.áŒŠÆ.Ø­áŒŠá()) {
            this.Â.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.HorizonCode_Horizon_È, p_180511_1_, p_180511_2_));
            HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this, p_180511_1_, p_180511_2_);
            this.à = 5;
        }
        else if (!this.Ø || !this.HorizonCode_Horizon_È(p_180511_1_)) {
            if (this.Ø) {
                this.Â.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.Â, this.Ý, p_180511_2_));
            }
            this.Â.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.HorizonCode_Horizon_È, p_180511_1_, p_180511_2_));
            final Block var3 = this.HorizonCode_Horizon_È.áŒŠÆ.Â(p_180511_1_).Ý();
            final boolean var5 = var3.Ó() != Material.HorizonCode_Horizon_È;
            if (var5 && this.Âµá€ == 0.0f) {
                var3.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.áŒŠÆ, p_180511_1_, this.HorizonCode_Horizon_È.á);
            }
            if (var5 && var3.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.á, this.HorizonCode_Horizon_È.á.Ï­Ðƒà, p_180511_1_) >= 1.0f) {
                this.HorizonCode_Horizon_È(p_180511_1_, p_180511_2_);
            }
            else {
                this.Ø = true;
                this.Ý = p_180511_1_;
                this.Ø­áŒŠá = this.HorizonCode_Horizon_È.á.Çª();
                this.Âµá€ = 0.0f;
                this.Ó = 0.0f;
                this.HorizonCode_Horizon_È.áŒŠÆ.Ý(this.HorizonCode_Horizon_È.á.ˆá(), this.Ý, (int)(this.Âµá€ * 10.0f) - 1);
            }
        }
        return true;
    }
    
    public void Ý() {
        if (this.Ø) {
            this.Â.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.Â, this.Ý, EnumFacing.HorizonCode_Horizon_È));
            this.Ø = false;
            this.Âµá€ = 0.0f;
            this.HorizonCode_Horizon_È.áŒŠÆ.Ý(this.HorizonCode_Horizon_È.á.ˆá(), this.Ý, -1);
        }
    }
    
    public boolean Ý(final BlockPos p_180512_1_, final EnumFacing p_180512_2_) {
        this.ˆÏ­();
        if (this.à > 0) {
            --this.à;
            return true;
        }
        if (this.áŒŠÆ.Ø­áŒŠá() && this.HorizonCode_Horizon_È.áŒŠÆ.áŠ().HorizonCode_Horizon_È(p_180512_1_)) {
            this.à = 5;
            this.Â.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.HorizonCode_Horizon_È, p_180512_1_, p_180512_2_));
            HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this, p_180512_1_, p_180512_2_);
            return true;
        }
        if (!this.HorizonCode_Horizon_È(p_180512_1_)) {
            return this.Â(p_180512_1_, p_180512_2_);
        }
        final Block var3 = this.HorizonCode_Horizon_È.áŒŠÆ.Â(p_180512_1_).Ý();
        if (var3.Ó() == Material.HorizonCode_Horizon_È) {
            return this.Ø = false;
        }
        this.Âµá€ += var3.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.á, this.HorizonCode_Horizon_È.á.Ï­Ðƒà, p_180512_1_);
        if (this.Ó % 4.0f == 0.0f) {
            this.HorizonCode_Horizon_È.£ÂµÄ().HorizonCode_Horizon_È(new PositionedSoundRecord(new ResourceLocation_1975012498(var3.ˆá.Ý()), (var3.ˆá.Ø­áŒŠá() + 1.0f) / 8.0f, var3.ˆá.Âµá€() * 0.5f, p_180512_1_.HorizonCode_Horizon_È() + 0.5f, p_180512_1_.Â() + 0.5f, p_180512_1_.Ý() + 0.5f));
        }
        ++this.Ó;
        if (this.Âµá€ >= 1.0f) {
            this.Ø = false;
            this.Â.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.Ý, p_180512_1_, p_180512_2_));
            this.HorizonCode_Horizon_È(p_180512_1_, p_180512_2_);
            this.Âµá€ = 0.0f;
            this.Ó = 0.0f;
            this.à = 5;
        }
        this.HorizonCode_Horizon_È.áŒŠÆ.Ý(this.HorizonCode_Horizon_È.á.ˆá(), this.Ý, (int)(this.Âµá€ * 10.0f) - 1);
        return true;
    }
    
    public float Ø­áŒŠá() {
        final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
        if (ModuleManager.HorizonCode_Horizon_È(Teleport.class).áˆºÑ¢Õ()) {
            return 50.0f;
        }
        return this.áŒŠÆ.Ø­áŒŠá() ? 5.0f : 4.5f;
    }
    
    public void Âµá€() {
        this.ˆÏ­();
        if (this.Â.Â().Âµá€()) {
            this.Â.Â().HorizonCode_Horizon_È();
        }
        else {
            this.Â.Â().áˆºÑ¢Õ();
        }
    }
    
    private boolean HorizonCode_Horizon_È(final BlockPos p_178893_1_) {
        final ItemStack var2 = this.HorizonCode_Horizon_È.á.Çª();
        boolean var3 = this.Ø­áŒŠá == null && var2 == null;
        if (this.Ø­áŒŠá != null && var2 != null) {
            var3 = (var2.HorizonCode_Horizon_È() == this.Ø­áŒŠá.HorizonCode_Horizon_È() && ItemStack.HorizonCode_Horizon_È(var2, this.Ø­áŒŠá) && (var2.Ø­áŒŠá() || var2.Ø() == this.Ø­áŒŠá.Ø()));
        }
        return p_178893_1_.equals(this.Ý) && var3;
    }
    
    private void ˆÏ­() {
        final int var1 = this.HorizonCode_Horizon_È.á.Ø­Ñ¢Ï­Ø­áˆº.Ý;
        if (var1 != this.áˆºÑ¢Õ) {
            this.áˆºÑ¢Õ = var1;
            this.Â.HorizonCode_Horizon_È(new C09PacketHeldItemChange(this.áˆºÑ¢Õ));
        }
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayerSP p_178890_1_, final WorldClient p_178890_2_, final ItemStack p_178890_3_, final BlockPos p_178890_4_, final EnumFacing p_178890_5_, final Vec3 p_178890_6_) {
        this.ˆÏ­();
        final float var7 = (float)(p_178890_6_.HorizonCode_Horizon_È - p_178890_4_.HorizonCode_Horizon_È());
        final float var8 = (float)(p_178890_6_.Â - p_178890_4_.Â());
        final float var9 = (float)(p_178890_6_.Ý - p_178890_4_.Ý());
        boolean var10 = false;
        if (!this.HorizonCode_Horizon_È.áŒŠÆ.áŠ().HorizonCode_Horizon_È(p_178890_4_)) {
            return false;
        }
        if (this.áŒŠÆ != WorldSettings.HorizonCode_Horizon_È.Âµá€) {
            final IBlockState var11 = p_178890_2_.Â(p_178890_4_);
            if ((!p_178890_1_.Çªà¢() || p_178890_1_.Çª() == null) && var11.Ý().HorizonCode_Horizon_È(p_178890_2_, p_178890_4_, var11, p_178890_1_, p_178890_5_, var7, var8, var9)) {
                var10 = true;
            }
            if (!var10 && p_178890_3_ != null && p_178890_3_.HorizonCode_Horizon_È() instanceof ItemBlock) {
                final ItemBlock var12 = (ItemBlock)p_178890_3_.HorizonCode_Horizon_È();
                if (!var12.HorizonCode_Horizon_È(p_178890_2_, p_178890_4_, p_178890_5_, p_178890_1_, p_178890_3_)) {
                    return false;
                }
            }
        }
        this.Â.HorizonCode_Horizon_È(new C08PacketPlayerBlockPlacement(p_178890_4_, p_178890_5_.Â(), p_178890_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá(), var7, var8, var9));
        if (var10 || this.áŒŠÆ == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
            return true;
        }
        if (p_178890_3_ == null) {
            return false;
        }
        if (this.áŒŠÆ.Ø­áŒŠá()) {
            final int var13 = p_178890_3_.Ø();
            final int var14 = p_178890_3_.Â;
            final boolean var15 = p_178890_3_.HorizonCode_Horizon_È(p_178890_1_, p_178890_2_, p_178890_4_, p_178890_5_, var7, var8, var9);
            p_178890_3_.Â(var13);
            p_178890_3_.Â = var14;
            return var15;
        }
        return p_178890_3_.HorizonCode_Horizon_È(p_178890_1_, p_178890_2_, p_178890_4_, p_178890_5_, var7, var8, var9);
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn, final World worldIn, final ItemStack itemStackIn) {
        if (this.áŒŠÆ == WorldSettings.HorizonCode_Horizon_È.Âµá€) {
            return false;
        }
        this.ˆÏ­();
        this.Â.HorizonCode_Horizon_È(new C08PacketPlayerBlockPlacement(playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá()));
        final int var4 = itemStackIn.Â;
        final ItemStack var5 = itemStackIn.HorizonCode_Horizon_È(worldIn, playerIn);
        if (var5 == itemStackIn && (var5 == null || var5.Â == var4)) {
            return false;
        }
        playerIn.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý] = var5;
        if (var5.Â == 0) {
            playerIn.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý] = null;
        }
        return true;
    }
    
    public EntityPlayerSPOverwrite HorizonCode_Horizon_È(final World worldIn, final StatFileWriter p_178892_2_) {
        return new EntityPlayerSPOverwrite(this.HorizonCode_Horizon_È, worldIn, this.Â, p_178892_2_);
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer playerIn, final Entity targetEntity) {
        final EventAttack event = new EventAttack();
        event.Ý();
        event.HorizonCode_Horizon_È(EventAttack.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        event.HorizonCode_Horizon_È(targetEntity);
        final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
        final Mod m = ModuleManager.HorizonCode_Horizon_È(KeepSprint.class);
        if (!event.HorizonCode_Horizon_È()) {
            if (m.áˆºÑ¢Õ()) {
                this.Â.HorizonCode_Horizon_È(new C02PacketUseEntity(targetEntity, C02PacketUseEntity.HorizonCode_Horizon_È.Â));
            }
            else {
                this.ˆÏ­();
                this.Â.HorizonCode_Horizon_È(new C02PacketUseEntity(targetEntity, C02PacketUseEntity.HorizonCode_Horizon_È.Â));
                if (this.áŒŠÆ != WorldSettings.HorizonCode_Horizon_È.Âµá€) {
                    playerIn.¥Æ(targetEntity);
                }
                event.Ý();
                event.HorizonCode_Horizon_È(EventAttack.HorizonCode_Horizon_È.Â);
            }
            event.Ý();
            event.HorizonCode_Horizon_È(EventAttack.HorizonCode_Horizon_È.Â);
            if (targetEntity instanceof EntityLivingBase) {
                final EntityLivingBase baseE = (EntityLivingBase)targetEntity;
                final ModuleManager áˆºÏ2 = Horizon.à¢.áˆºÏ;
                if (ModuleManager.HorizonCode_Horizon_È(SCHLITZ.class).áˆºÑ¢Õ()) {
                    new Thread("Kill Thread") {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(200L);
                            }
                            catch (InterruptedException ex) {}
                            if (baseE.ˆáŠ) {
                                return;
                            }
                            if (baseE.ˆáŠ || baseE == null || baseE.Ï­Ä() <= 0.5) {
                                final File f = new File(Horizon.à¢.áŒŠ, "SCHLITZ");
                                if (!f.exists()) {
                                    f.mkdir();
                                }
                                final ArrayList<File> files = new ArrayList<File>();
                                File[] listFiles;
                                for (int length = (listFiles = f.listFiles()).length, i = 0; i < length; ++i) {
                                    final File fls = listFiles[i];
                                    if (fls.isFile()) {
                                        files.add(fls);
                                    }
                                }
                                if (Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È.isRunning()) {
                                    return;
                                }
                                Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("SCHLITZ!", 0);
                                final File playFile = files.get(new Random().nextInt(files.size()));
                                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(playFile.getPath());
                            }
                        }
                    }.start();
                }
            }
        }
    }
    
    public boolean Â(final EntityPlayer playerIn, final Entity targetEntity) {
        this.ˆÏ­();
        this.Â.HorizonCode_Horizon_È(new C02PacketUseEntity(targetEntity, C02PacketUseEntity.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        return this.áŒŠÆ != WorldSettings.HorizonCode_Horizon_È.Âµá€ && playerIn.ˆà(targetEntity);
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_178894_1_, final Entity p_178894_2_, final MovingObjectPosition p_178894_3_) {
        this.ˆÏ­();
        final Vec3 var4 = new Vec3(p_178894_3_.Ý.HorizonCode_Horizon_È - p_178894_2_.ŒÏ, p_178894_3_.Ý.Â - p_178894_2_.Çªà¢, p_178894_3_.Ý.Ý - p_178894_2_.Ê);
        this.Â.HorizonCode_Horizon_È(new C02PacketUseEntity(p_178894_2_, var4));
        return this.áŒŠÆ != WorldSettings.HorizonCode_Horizon_È.Âµá€ && p_178894_2_.HorizonCode_Horizon_È(p_178894_1_, var4);
    }
    
    public ItemStack HorizonCode_Horizon_È(final int windowId, final int slotId, final int p_78753_3_, final int p_78753_4_, final EntityPlayer playerIn) {
        final short var6 = playerIn.Ï­Ï.HorizonCode_Horizon_È(playerIn.Ø­Ñ¢Ï­Ø­áˆº);
        final ItemStack var7 = playerIn.Ï­Ï.HorizonCode_Horizon_È(slotId, p_78753_3_, p_78753_4_, playerIn);
        this.Â.HorizonCode_Horizon_È(new C0EPacketClickWindow(windowId, slotId, p_78753_3_, p_78753_4_, var7, var6));
        return var7;
    }
    
    public void HorizonCode_Horizon_È(final int p_78756_1_, final int p_78756_2_) {
        this.Â.HorizonCode_Horizon_È(new C11PacketEnchantItem(p_78756_1_, p_78756_2_));
    }
    
    public void HorizonCode_Horizon_È(final ItemStack itemStackIn, final int slotId) {
        if (this.áŒŠÆ.Ø­áŒŠá()) {
            this.Â.HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(slotId, itemStackIn));
        }
    }
    
    public void HorizonCode_Horizon_È(final ItemStack itemStackIn) {
        if (this.áŒŠÆ.Ø­áŒŠá() && itemStackIn != null) {
            this.Â.HorizonCode_Horizon_È(new C10PacketCreativeInventoryAction(-1, itemStackIn));
        }
    }
    
    public void Ý(final EntityPlayer playerIn) {
        this.ˆÏ­();
        this.Â.HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.Ó, BlockPos.HorizonCode_Horizon_È, EnumFacing.HorizonCode_Horizon_È));
        playerIn.áŒŠÔ();
    }
    
    public boolean Ó() {
        return this.áŒŠÆ.Âµá€();
    }
    
    public boolean à() {
        return !this.áŒŠÆ.Ø­áŒŠá();
    }
    
    public boolean Ø() {
        return this.áŒŠÆ.Ø­áŒŠá();
    }
    
    public boolean áŒŠÆ() {
        return this.áŒŠÆ.Ø­áŒŠá();
    }
    
    public boolean áˆºÑ¢Õ() {
        return this.HorizonCode_Horizon_È.á.áˆºÇŽØ() && this.HorizonCode_Horizon_È.á.Æ instanceof EntityHorse;
    }
    
    public boolean ÂµÈ() {
        return this.áŒŠÆ == WorldSettings.HorizonCode_Horizon_È.Âµá€;
    }
    
    public WorldSettings.HorizonCode_Horizon_È á() {
        return this.áŒŠÆ;
    }
}
