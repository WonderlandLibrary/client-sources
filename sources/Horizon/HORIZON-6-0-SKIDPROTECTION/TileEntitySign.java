package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonParseException;

public class TileEntitySign extends TileEntity
{
    public final IChatComponent[] Âµá€;
    public int Ó;
    private boolean à;
    private EntityPlayer Ø;
    private final CommandResultStats áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000363";
    
    public TileEntitySign() {
        this.Âµá€ = new IChatComponent[] { new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("") };
        this.Ó = -1;
        this.à = true;
        this.áŒŠÆ = new CommandResultStats();
    }
    
    @Override
    public void Â(final NBTTagCompound compound) {
        super.Â(compound);
        for (int var2 = 0; var2 < 4; ++var2) {
            final String var3 = IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Âµá€[var2]);
            compound.HorizonCode_Horizon_È("Text" + (var2 + 1), var3);
        }
        this.áŒŠÆ.Â(compound);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound compound) {
        this.à = false;
        super.HorizonCode_Horizon_È(compound);
        final ICommandSender var2 = new ICommandSender() {
            private static final String Â = "CL_00002039";
            
            @Override
            public String v_() {
                return "Sign";
            }
            
            @Override
            public IChatComponent Ý() {
                return new ChatComponentText(this.v_());
            }
            
            @Override
            public void HorizonCode_Horizon_È(final IChatComponent message) {
            }
            
            @Override
            public boolean HorizonCode_Horizon_È(final int permissionLevel, final String command) {
                return true;
            }
            
            @Override
            public BlockPos £á() {
                return TileEntitySign.this.Â;
            }
            
            @Override
            public Vec3 z_() {
                return new Vec3(TileEntitySign.this.Â.HorizonCode_Horizon_È() + 0.5, TileEntitySign.this.Â.Â() + 0.5, TileEntitySign.this.Â.Ý() + 0.5);
            }
            
            @Override
            public World k_() {
                return TileEntitySign.this.HorizonCode_Horizon_È;
            }
            
            @Override
            public Entity l_() {
                return null;
            }
            
            @Override
            public boolean g_() {
                return false;
            }
            
            @Override
            public void HorizonCode_Horizon_È(final CommandResultStats.HorizonCode_Horizon_È p_174794_1_, final int p_174794_2_) {
            }
        };
        for (int var3 = 0; var3 < 4; ++var3) {
            final String var4 = compound.áˆºÑ¢Õ("Text" + (var3 + 1));
            try {
                final IChatComponent var5 = IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var4);
                try {
                    this.Âµá€[var3] = ChatComponentProcessor.HorizonCode_Horizon_È(var2, var5, null);
                }
                catch (CommandException var6) {
                    this.Âµá€[var3] = var5;
                }
            }
            catch (JsonParseException var7) {
                this.Âµá€[var3] = new ChatComponentText(var4);
            }
        }
        this.áŒŠÆ.HorizonCode_Horizon_È(compound);
    }
    
    @Override
    public Packet £á() {
        final IChatComponent[] var1 = new IChatComponent[4];
        System.arraycopy(this.Âµá€, 0, var1, 0, 4);
        return new S33PacketUpdateSign(this.HorizonCode_Horizon_È, this.Â, var1);
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.à;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_145913_1_) {
        if (!(this.à = p_145913_1_)) {
            this.Ø = null;
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_145912_1_) {
        this.Ø = p_145912_1_;
    }
    
    public EntityPlayer Â() {
        return this.Ø;
    }
    
    public boolean Â(final EntityPlayer p_174882_1_) {
        final ICommandSender var2 = new ICommandSender() {
            private static final String Â = "CL_00002038";
            
            @Override
            public String v_() {
                return p_174882_1_.v_();
            }
            
            @Override
            public IChatComponent Ý() {
                return p_174882_1_.Ý();
            }
            
            @Override
            public void HorizonCode_Horizon_È(final IChatComponent message) {
            }
            
            @Override
            public boolean HorizonCode_Horizon_È(final int permissionLevel, final String command) {
                return true;
            }
            
            @Override
            public BlockPos £á() {
                return TileEntitySign.this.Â;
            }
            
            @Override
            public Vec3 z_() {
                return new Vec3(TileEntitySign.this.Â.HorizonCode_Horizon_È() + 0.5, TileEntitySign.this.Â.Â() + 0.5, TileEntitySign.this.Â.Ý() + 0.5);
            }
            
            @Override
            public World k_() {
                return p_174882_1_.k_();
            }
            
            @Override
            public Entity l_() {
                return p_174882_1_;
            }
            
            @Override
            public boolean g_() {
                return false;
            }
            
            @Override
            public void HorizonCode_Horizon_È(final CommandResultStats.HorizonCode_Horizon_È p_174794_1_, final int p_174794_2_) {
                TileEntitySign.this.áŒŠÆ.HorizonCode_Horizon_È(this, p_174794_1_, p_174794_2_);
            }
        };
        for (int var3 = 0; var3 < this.Âµá€.length; ++var3) {
            final ChatStyle var4 = (this.Âµá€[var3] == null) ? null : this.Âµá€[var3].à();
            if (var4 != null && var4.Ø() != null) {
                final ClickEvent var5 = var4.Ø();
                if (var5.HorizonCode_Horizon_È() == ClickEvent.HorizonCode_Horizon_È.Ý) {
                    MinecraftServer.áƒ().Õ().HorizonCode_Horizon_È(var2, var5.Â());
                }
            }
        }
        return true;
    }
    
    public CommandResultStats Ý() {
        return this.áŒŠÆ;
    }
}
