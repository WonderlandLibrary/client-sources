package HORIZON-6-0-SKIDPROTECTION;

import io.netty.buffer.ByteBuf;
import java.util.Date;
import java.util.concurrent.Callable;
import java.text.SimpleDateFormat;

public abstract class CommandBlockLogic implements ICommandSender
{
    private static final SimpleDateFormat HorizonCode_Horizon_È;
    private int Â;
    private boolean Ý;
    private IChatComponent Ø­áŒŠá;
    private String Âµá€;
    private String Ó;
    private final CommandResultStats à;
    private static final String Ø = "CL_00000128";
    
    static {
        HorizonCode_Horizon_È = new SimpleDateFormat("HH:mm:ss");
    }
    
    public CommandBlockLogic() {
        this.Ý = true;
        this.Ø­áŒŠá = null;
        this.Âµá€ = "";
        this.Ó = "@";
        this.à = new CommandResultStats();
    }
    
    public int à() {
        return this.Â;
    }
    
    public IChatComponent Ø() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound p_145758_1_) {
        p_145758_1_.HorizonCode_Horizon_È("Command", this.Âµá€);
        p_145758_1_.HorizonCode_Horizon_È("SuccessCount", this.Â);
        p_145758_1_.HorizonCode_Horizon_È("CustomName", this.Ó);
        p_145758_1_.HorizonCode_Horizon_È("TrackOutput", this.Ý);
        if (this.Ø­áŒŠá != null && this.Ý) {
            p_145758_1_.HorizonCode_Horizon_È("LastOutput", IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ø­áŒŠá));
        }
        this.à.Â(p_145758_1_);
    }
    
    public void Â(final NBTTagCompound p_145759_1_) {
        this.Âµá€ = p_145759_1_.áˆºÑ¢Õ("Command");
        this.Â = p_145759_1_.Ó("SuccessCount");
        if (p_145759_1_.Â("CustomName", 8)) {
            this.Ó = p_145759_1_.áˆºÑ¢Õ("CustomName");
        }
        if (p_145759_1_.Â("TrackOutput", 1)) {
            this.Ý = p_145759_1_.£á("TrackOutput");
        }
        if (p_145759_1_.Â("LastOutput", 8) && this.Ý) {
            this.Ø­áŒŠá = IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_145759_1_.áˆºÑ¢Õ("LastOutput"));
        }
        this.à.HorizonCode_Horizon_È(p_145759_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int permissionLevel, final String command) {
        return permissionLevel <= 2;
    }
    
    public void HorizonCode_Horizon_È(final String p_145752_1_) {
        this.Âµá€ = p_145752_1_;
        this.Â = 0;
    }
    
    public String áŒŠÆ() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn) {
        if (worldIn.ŠÄ) {
            this.Â = 0;
        }
        final MinecraftServer var2 = MinecraftServer.áƒ();
        if (var2 != null && var2.á€() && var2.ÇªÓ()) {
            final ICommandManager var3 = var2.Õ();
            try {
                this.Ø­áŒŠá = null;
                this.Â = var3.HorizonCode_Horizon_È(this, this.Âµá€);
                return;
            }
            catch (Throwable var5) {
                final CrashReport var4 = CrashReport.HorizonCode_Horizon_È(var5, "Executing command block");
                final CrashReportCategory var6 = var4.HorizonCode_Horizon_È("Command to be executed");
                var6.HorizonCode_Horizon_È("Command", new Callable() {
                    private static final String Â = "CL_00002154";
                    
                    public String HorizonCode_Horizon_È() {
                        return CommandBlockLogic.this.áŒŠÆ();
                    }
                    
                    @Override
                    public Object call() {
                        return this.HorizonCode_Horizon_È();
                    }
                });
                var6.HorizonCode_Horizon_È("Name", new Callable() {
                    private static final String Â = "CL_00002153";
                    
                    public String HorizonCode_Horizon_È() {
                        return CommandBlockLogic.this.v_();
                    }
                    
                    @Override
                    public Object call() {
                        return this.HorizonCode_Horizon_È();
                    }
                });
                throw new ReportedException(var4);
            }
        }
        this.Â = 0;
    }
    
    @Override
    public String v_() {
        return this.Ó;
    }
    
    @Override
    public IChatComponent Ý() {
        return new ChatComponentText(this.v_());
    }
    
    public void Â(final String p_145754_1_) {
        this.Ó = p_145754_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent message) {
        if (this.Ý && this.k_() != null && !this.k_().ŠÄ) {
            this.Ø­áŒŠá = new ChatComponentText("[" + CommandBlockLogic.HorizonCode_Horizon_È.format(new Date()) + "] ").HorizonCode_Horizon_È(message);
            this.áˆºÑ¢Õ();
        }
    }
    
    @Override
    public boolean g_() {
        final MinecraftServer var1 = MinecraftServer.áƒ();
        return var1 == null || !var1.á€() || var1.Ý[0].Çªà¢().Â("commandBlockOutput");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final CommandResultStats.HorizonCode_Horizon_È p_174794_1_, final int p_174794_2_) {
        this.à.HorizonCode_Horizon_È(this, p_174794_1_, p_174794_2_);
    }
    
    public abstract void áˆºÑ¢Õ();
    
    public abstract int ÂµÈ();
    
    public abstract void HorizonCode_Horizon_È(final ByteBuf p0);
    
    public void Â(final IChatComponent p_145750_1_) {
        this.Ø­áŒŠá = p_145750_1_;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_175573_1_) {
        this.Ý = p_175573_1_;
    }
    
    public boolean á() {
        return this.Ý;
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_175574_1_) {
        if (!p_175574_1_.áˆºáˆºáŠ.Ø­áŒŠá) {
            return false;
        }
        if (p_175574_1_.k_().ŠÄ) {
            p_175574_1_.HorizonCode_Horizon_È(this);
        }
        return true;
    }
    
    public CommandResultStats ˆÏ­() {
        return this.à;
    }
}
