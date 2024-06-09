package HORIZON-6-0-SKIDPROTECTION;

import java.util.concurrent.Callable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Random;

public abstract class Entity implements ICommandSender
{
    private static final AxisAlignedBB HorizonCode_Horizon_È;
    private static int Â;
    public int ˆà;
    public double ¥Æ;
    public boolean Ø­à;
    public Entity µÕ;
    public Entity Æ;
    public boolean Šáƒ;
    public World Ï­Ðƒà;
    public double áŒŠà;
    public double ŠÄ;
    public double Ñ¢á;
    public double ŒÏ;
    public double Çªà¢;
    public double Ê;
    public double ÇŽÉ;
    public double ˆá;
    public double ÇŽÕ;
    public float É;
    public float áƒ;
    public float á€;
    public float Õ;
    public AxisAlignedBB à¢;
    public boolean ŠÂµà;
    public boolean ¥à;
    public boolean Âµà;
    public boolean Ç;
    public boolean È;
    protected boolean áŠ;
    private boolean Ý;
    public boolean ˆáŠ;
    public float áŒŠ;
    public float £ÂµÄ;
    public float Ø­Âµ;
    public float Ä;
    public float Ñ¢Â;
    public float Ï­à;
    private int Ø­áŒŠá;
    public double áˆºáˆºÈ;
    public double ÇŽá€;
    public double Ï;
    public float Ô;
    public boolean ÇªÓ;
    public float áˆºÏ;
    protected Random ˆáƒ;
    public int Œ;
    public int £Ï;
    private int Âµá€;
    protected boolean Ø­á;
    public int ˆÉ;
    protected boolean Ï­Ï­Ï;
    protected boolean £Â;
    protected DataWatcher £Ó;
    private double Ø;
    private double áŒŠÆ;
    public boolean ˆÐƒØ­à;
    public int £Õ;
    public int Ï­Ô;
    public int Œà;
    public int Ðƒá;
    public int ˆÏ;
    public int áˆºÇŽØ;
    public boolean ÇªÂµÕ;
    public boolean áŒŠÏ;
    public int áŒŠáŠ;
    protected boolean ˆÓ;
    protected int ¥Ä;
    public int ÇªÔ;
    protected int Û;
    private boolean áˆºÑ¢Õ;
    public UUID ŠÓ;
    private final CommandResultStats ÂµÈ;
    private static final String á = "CL_00001533";
    
    static {
        HorizonCode_Horizon_È = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public int ˆá() {
        return this.ˆà;
    }
    
    public void Ø­áŒŠá(final int id) {
        this.ˆà = id;
    }
    
    public void ÇŽÕ() {
        this.á€();
    }
    
    public Entity(final World worldIn) {
        this.ˆà = Entity.Â++;
        this.¥Æ = 1.0;
        this.à¢ = Entity.HorizonCode_Horizon_È;
        this.áŒŠ = 0.6f;
        this.£ÂµÄ = 1.8f;
        this.Ø­áŒŠá = 1;
        this.ˆáƒ = new Random();
        this.£Ï = 1;
        this.Ï­Ï­Ï = true;
        this.ŠÓ = MathHelper.HorizonCode_Horizon_È(this.ˆáƒ);
        this.ÂµÈ = new CommandResultStats();
        this.Ï­Ðƒà = worldIn;
        this.Ý(0.0, 0.0, 0.0);
        if (worldIn != null) {
            this.ÇªÔ = worldIn.£à.µà();
        }
        (this.£Ó = new DataWatcher(this)).HorizonCode_Horizon_È(0, (Object)(byte)0);
        this.£Ó.HorizonCode_Horizon_È(1, (Object)(short)300);
        this.£Ó.HorizonCode_Horizon_È(3, (Object)(byte)0);
        this.£Ó.HorizonCode_Horizon_È(2, "");
        this.£Ó.HorizonCode_Horizon_È(4, (Object)(byte)0);
        this.ÂµÈ();
    }
    
    protected abstract void ÂµÈ();
    
    public DataWatcher É() {
        return this.£Ó;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return p_equals_1_ instanceof Entity && ((Entity)p_equals_1_).ˆà == this.ˆà;
    }
    
    @Override
    public int hashCode() {
        return this.ˆà;
    }
    
    protected void áƒ() {
        if (this.Ï­Ðƒà != null) {
            while (this.Çªà¢ > 0.0 && this.Çªà¢ < 256.0) {
                this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
                if (this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É()).isEmpty()) {
                    break;
                }
                ++this.Çªà¢;
            }
            final double çžé = 0.0;
            this.ÇŽÕ = çžé;
            this.ˆá = çžé;
            this.ÇŽÉ = çžé;
            this.áƒ = 0.0f;
        }
    }
    
    public void á€() {
        this.ˆáŠ = true;
    }
    
    protected void HorizonCode_Horizon_È(final float width, final float height) {
        if (width != this.áŒŠ || height != this.£ÂµÄ) {
            final float var3 = this.áŒŠ;
            this.áŒŠ = width;
            this.£ÂµÄ = height;
            this.HorizonCode_Horizon_È(new AxisAlignedBB(this.£É().HorizonCode_Horizon_È, this.£É().Â, this.£É().Ý, this.£É().HorizonCode_Horizon_È + this.áŒŠ, this.£É().Â + this.£ÂµÄ, this.£É().Ý + this.áŒŠ));
            if (this.áŒŠ > var3 && !this.Ï­Ï­Ï && !this.Ï­Ðƒà.ŠÄ) {
                this.HorizonCode_Horizon_È(var3 - this.áŒŠ, 0.0, var3 - this.áŒŠ);
            }
        }
    }
    
    public void Â(final float yaw, final float pitch) {
        this.É = yaw % 360.0f;
        this.áƒ = pitch % 360.0f;
    }
    
    public void Ý(final double x, final double y, final double z) {
        this.ŒÏ = x;
        this.Çªà¢ = y;
        this.Ê = z;
        final float var7 = this.áŒŠ / 2.0f;
        final float var8 = this.£ÂµÄ;
        this.HorizonCode_Horizon_È(new AxisAlignedBB(x - var7, y, z - var7, x + var7, y + var8, z + var7));
    }
    
    public void Ý(final float yaw, final float pitch) {
        final float var3 = this.áƒ;
        final float var4 = this.É;
        this.É += (float)(yaw * 0.15);
        this.áƒ -= (float)(pitch * 0.15);
        final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
        if (!ModuleManager.HorizonCode_Horizon_È(NoPitchLimit.class).áˆºÑ¢Õ()) {
            this.áƒ = MathHelper.HorizonCode_Horizon_È(this.áƒ, -90.0f, 90.0f);
        }
        this.Õ += this.áƒ - var3;
        this.á€ += this.É - var4;
    }
    
    public void á() {
        this.Õ();
    }
    
    public void Õ() {
        this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("entityBaseTick");
        if (this.Æ != null && this.Æ.ˆáŠ) {
            this.Æ = null;
        }
        this.Ø­Âµ = this.Ä;
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        this.Õ = this.áƒ;
        this.á€ = this.É;
        if (!this.Ï­Ðƒà.ŠÄ && this.Ï­Ðƒà instanceof WorldServer) {
            this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("portal");
            final MinecraftServer var1 = ((WorldServer)this.Ï­Ðƒà).áˆºáˆºÈ();
            final int var2 = this.à¢();
            if (this.ˆÓ) {
                if (var1.ŠÄ()) {
                    if (this.Æ == null && this.¥Ä++ >= var2) {
                        this.¥Ä = var2;
                        this.áŒŠáŠ = this.Ï­Ô();
                        byte var3;
                        if (this.Ï­Ðƒà.£à.µà() == -1) {
                            var3 = 0;
                        }
                        else {
                            var3 = -1;
                        }
                        this.áŒŠÆ(var3);
                    }
                    this.ˆÓ = false;
                }
            }
            else {
                if (this.¥Ä > 0) {
                    this.¥Ä -= 4;
                }
                if (this.¥Ä < 0) {
                    this.¥Ä = 0;
                }
            }
            if (this.áŒŠáŠ > 0) {
                --this.áŒŠáŠ;
            }
            this.Ï­Ðƒà.Ï­Ðƒà.Â();
        }
        this.Ñ¢Â();
        this.Ø­Âµ();
        if (this.Ï­Ðƒà.ŠÄ) {
            this.Âµá€ = 0;
        }
        else if (this.Âµá€ > 0) {
            if (this.£Â) {
                this.Âµá€ -= 4;
                if (this.Âµá€ < 0) {
                    this.Âµá€ = 0;
                }
            }
            else {
                if (this.Âµá€ % 20 == 0) {
                    this.HorizonCode_Horizon_È(DamageSource.Ý, 1.0f);
                }
                --this.Âµá€;
            }
        }
        if (this.ÇŽá€()) {
            this.ŠÂµà();
            this.Ï­à *= 0.5f;
        }
        if (this.Çªà¢ < -64.0) {
            this.Âµà();
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.HorizonCode_Horizon_È(0, this.Âµá€ > 0);
        }
        this.Ï­Ï­Ï = false;
        this.Ï­Ðƒà.Ï­Ðƒà.Â();
    }
    
    public int à¢() {
        return 0;
    }
    
    protected void ŠÂµà() {
        if (!this.£Â) {
            this.HorizonCode_Horizon_È(DamageSource.Ø­áŒŠá, 4.0f);
            this.Âµá€(15);
        }
    }
    
    public void Âµá€(final int seconds) {
        int var2 = seconds * 20;
        var2 = EnchantmentProtection.HorizonCode_Horizon_È(this, var2);
        if (this.Âµá€ < var2) {
            this.Âµá€ = var2;
        }
    }
    
    public void ¥à() {
        this.Âµá€ = 0;
    }
    
    protected void Âµà() {
        this.á€();
    }
    
    public boolean Ø­áŒŠá(final double x, final double y, final double z) {
        final AxisAlignedBB var7 = this.£É().Ý(x, y, z);
        return this.Â(var7);
    }
    
    private boolean Â(final AxisAlignedBB p_174809_1_) {
        return this.Ï­Ðƒà.HorizonCode_Horizon_È(this, p_174809_1_).isEmpty() && !this.Ï­Ðƒà.Ø­áŒŠá(p_174809_1_);
    }
    
    public void HorizonCode_Horizon_È(double x, double y, double z) {
        if (this.ÇªÓ) {
            this.HorizonCode_Horizon_È(this.£É().Ý(x, y, z));
            this.Ø();
        }
        else {
            this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("move");
            final double var7 = this.ŒÏ;
            final double var8 = this.Çªà¢;
            final double var9 = this.Ê;
            if (this.áŠ) {
                this.áŠ = false;
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                if (!ModuleManager.HorizonCode_Horizon_È(NoWeb.class).áˆºÑ¢Õ()) {
                    x *= 0.25;
                    y *= 0.05000000074505806;
                    z *= 0.25;
                }
                else {
                    x *= (this.ÇªÂµÕ() ? 0.4 : 0.53);
                    y *= (this.ÇªÂµÕ() ? 0.4 : 0.53);
                    z *= (this.ÇªÂµÕ() ? 0.4 : 0.53);
                }
                final ModuleManager áˆºÏ2 = Horizon.à¢.áˆºÏ;
                if (!ModuleManager.HorizonCode_Horizon_È(NoSlow.class).áˆºÑ¢Õ()) {
                    this.ÇŽÉ = 0.0;
                    this.ˆá = 0.0;
                    this.ÇŽÕ = 0.0;
                }
            }
            double var10 = x;
            final double var11 = y;
            double var12 = z;
            final boolean var13 = this.ŠÂµà && this.Çªà¢() && this instanceof EntityPlayer;
            final EventSafeWalk walk = new EventSafeWalk();
            walk.Â();
            if (var13 || walk.HorizonCode_Horizon_È()) {
                final double var14 = 0.05;
                while (x != 0.0) {
                    if (!this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É().Ý(x, -1.0, 0.0)).isEmpty()) {
                        break;
                    }
                    if (x < var14 && x >= -var14) {
                        x = 0.0;
                    }
                    else if (x > 0.0) {
                        x -= var14;
                    }
                    else {
                        x += var14;
                    }
                    var10 = x;
                }
                while (z != 0.0) {
                    if (!this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É().Ý(0.0, -1.0, z)).isEmpty()) {
                        break;
                    }
                    if (z < var14 && z >= -var14) {
                        z = 0.0;
                    }
                    else if (z > 0.0) {
                        z -= var14;
                    }
                    else {
                        z += var14;
                    }
                    var12 = z;
                }
                while (x != 0.0 && z != 0.0 && this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É().Ý(x, -1.0, z)).isEmpty()) {
                    if (x < var14 && x >= -var14) {
                        x = 0.0;
                    }
                    else if (x > 0.0) {
                        x -= var14;
                    }
                    else {
                        x += var14;
                    }
                    var10 = x;
                    if (z < var14 && z >= -var14) {
                        z = 0.0;
                    }
                    else if (z > 0.0) {
                        z -= var14;
                    }
                    else {
                        z += var14;
                    }
                    var12 = z;
                }
            }
            final List var15 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É().HorizonCode_Horizon_È(x, y, z));
            final AxisAlignedBB var16 = this.£É();
            for (final AxisAlignedBB var18 : var15) {
                y = var18.Â(this.£É(), y);
            }
            this.HorizonCode_Horizon_È(this.£É().Ý(0.0, y, 0.0));
            final boolean var19 = this.ŠÂµà || (var11 != y && var11 < 0.0);
            for (final AxisAlignedBB var21 : var15) {
                x = var21.HorizonCode_Horizon_È(this.£É(), x);
            }
            this.HorizonCode_Horizon_È(this.£É().Ý(x, 0.0, 0.0));
            for (final AxisAlignedBB var21 : var15) {
                z = var21.Ý(this.£É(), z);
            }
            this.HorizonCode_Horizon_È(this.£É().Ý(0.0, 0.0, z));
            if (this.Ô > 0.0f && var19 && (var10 != x || var12 != z)) {
                final double var22 = x;
                final double var23 = y;
                final double var24 = z;
                final AxisAlignedBB var25 = this.£É();
                this.HorizonCode_Horizon_È(var16);
                y = this.Ô;
                final List var26 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É().HorizonCode_Horizon_È(var10, y, var12));
                AxisAlignedBB var27 = this.£É();
                final AxisAlignedBB var28 = var27.HorizonCode_Horizon_È(var10, 0.0, var12);
                double var29 = y;
                for (final AxisAlignedBB var31 : var26) {
                    var29 = var31.Â(var28, var29);
                }
                var27 = var27.Ý(0.0, var29, 0.0);
                double var32 = var10;
                for (final AxisAlignedBB var34 : var26) {
                    var32 = var34.HorizonCode_Horizon_È(var27, var32);
                }
                var27 = var27.Ý(var32, 0.0, 0.0);
                double var35 = var12;
                for (final AxisAlignedBB var37 : var26) {
                    var35 = var37.Ý(var27, var35);
                }
                var27 = var27.Ý(0.0, 0.0, var35);
                AxisAlignedBB var38 = this.£É();
                double var39 = y;
                for (final AxisAlignedBB var41 : var26) {
                    var39 = var41.Â(var38, var39);
                }
                var38 = var38.Ý(0.0, var39, 0.0);
                double var42 = var10;
                for (final AxisAlignedBB var44 : var26) {
                    var42 = var44.HorizonCode_Horizon_È(var38, var42);
                }
                var38 = var38.Ý(var42, 0.0, 0.0);
                double var45 = var12;
                for (final AxisAlignedBB var47 : var26) {
                    var45 = var47.Ý(var38, var45);
                }
                var38 = var38.Ý(0.0, 0.0, var45);
                final double var48 = var32 * var32 + var35 * var35;
                final double var49 = var42 * var42 + var45 * var45;
                if (var48 > var49) {
                    x = var32;
                    z = var35;
                    this.HorizonCode_Horizon_È(var27);
                }
                else {
                    x = var42;
                    z = var45;
                    this.HorizonCode_Horizon_È(var38);
                }
                y = -this.Ô;
                for (final AxisAlignedBB var51 : var26) {
                    y = var51.Â(this.£É(), y);
                }
                this.HorizonCode_Horizon_È(this.£É().Ý(0.0, y, 0.0));
                if (var22 * var22 + var24 * var24 >= x * x + z * z) {
                    x = var22;
                    y = var23;
                    z = var24;
                    this.HorizonCode_Horizon_È(var25);
                }
            }
            this.Ï­Ðƒà.Ï­Ðƒà.Â();
            this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("rest");
            this.Ø();
            this.¥à = (var10 != x || var12 != z);
            this.Âµà = (var11 != y);
            this.ŠÂµà = (this.Âµà && var11 < 0.0);
            this.Ç = (this.¥à || this.Âµà);
            final int var52 = MathHelper.Ý(this.ŒÏ);
            final int var53 = MathHelper.Ý(this.Çªà¢ - 0.20000000298023224);
            final int var54 = MathHelper.Ý(this.Ê);
            BlockPos var55 = new BlockPos(var52, var53, var54);
            Block var56 = this.Ï­Ðƒà.Â(var55).Ý();
            if (var56.Ó() == Material.HorizonCode_Horizon_È) {
                final Block var57 = this.Ï­Ðƒà.Â(var55.Âµá€()).Ý();
                if (var57 instanceof BlockFence || var57 instanceof BlockWall || var57 instanceof BlockFenceGate) {
                    var56 = var57;
                    var55 = var55.Âµá€();
                }
            }
            this.HorizonCode_Horizon_È(y, this.ŠÂµà, var56, var55);
            if (var10 != x) {
                this.ÇŽÉ = 0.0;
            }
            if (var12 != z) {
                this.ÇŽÕ = 0.0;
            }
            if (var11 != y) {
                var56.HorizonCode_Horizon_È(this.Ï­Ðƒà, this);
            }
            if (this.áˆºÑ¢Õ() && !var13 && this.Æ == null) {
                final double var58 = this.ŒÏ - var7;
                double var59 = this.Çªà¢ - var8;
                final double var60 = this.Ê - var9;
                if (var56 != Blocks.áŒŠÏ) {
                    var59 = 0.0;
                }
                if (var56 != null && this.ŠÂµà) {
                    var56.HorizonCode_Horizon_È(this.Ï­Ðƒà, var55, this);
                }
                this.Ä += (float)(MathHelper.HorizonCode_Horizon_È(var58 * var58 + var60 * var60) * 0.6);
                this.Ñ¢Â += (float)(MathHelper.HorizonCode_Horizon_È(var58 * var58 + var59 * var59 + var60 * var60) * 0.6);
                if (this.Ñ¢Â > this.Ø­áŒŠá && var56.Ó() != Material.HorizonCode_Horizon_È) {
                    this.Ø­áŒŠá = (int)this.Ñ¢Â + 1;
                    if (this.£ÂµÄ()) {
                        float var61 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ * 0.20000000298023224 + this.ˆá * this.ˆá + this.ÇŽÕ * this.ÇŽÕ * 0.20000000298023224) * 0.35f;
                        if (var61 > 1.0f) {
                            var61 = 1.0f;
                        }
                        this.HorizonCode_Horizon_È(this.Ç(), var61, 1.0f + (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.4f);
                    }
                    this.HorizonCode_Horizon_È(var55, var56);
                }
            }
            try {
                this.È();
            }
            catch (Throwable var63) {
                final CrashReport var62 = CrashReport.HorizonCode_Horizon_È(var63, "Checking entity block collision");
                final CrashReportCategory var64 = var62.HorizonCode_Horizon_È("Entity being checked for collision");
                this.HorizonCode_Horizon_È(var64);
                throw new ReportedException(var62);
            }
            final boolean var65 = this.áŒŠ();
            if (this.Ï­Ðƒà.Âµá€(this.£É().Ø­áŒŠá(0.001, 0.001, 0.001))) {
                this.Ó(1);
                if (!var65) {
                    ++this.Âµá€;
                    if (this.Âµá€ == 0) {
                        this.Âµá€(8);
                    }
                }
            }
            else if (this.Âµá€ <= 0) {
                this.Âµá€ = -this.£Ï;
            }
            if (var65 && this.Âµá€ > 0) {
                this.HorizonCode_Horizon_È("random.fizz", 0.7f, 1.6f + (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.4f);
                this.Âµá€ = -this.£Ï;
            }
            this.Ï­Ðƒà.Ï­Ðƒà.Â();
        }
    }
    
    private void Ø() {
        this.ŒÏ = (this.£É().HorizonCode_Horizon_È + this.£É().Ø­áŒŠá) / 2.0;
        this.Çªà¢ = this.£É().Â;
        this.Ê = (this.£É().Ý + this.£É().Ó) / 2.0;
    }
    
    protected String Ç() {
        return "game.neutral.swim";
    }
    
    protected void È() {
        final BlockPos var1 = new BlockPos(this.£É().HorizonCode_Horizon_È + 0.001, this.£É().Â + 0.001, this.£É().Ý + 0.001);
        final BlockPos var2 = new BlockPos(this.£É().Ø­áŒŠá - 0.001, this.£É().Âµá€ - 0.001, this.£É().Ó - 0.001);
        if (this.Ï­Ðƒà.HorizonCode_Horizon_È(var1, var2)) {
            for (int var3 = var1.HorizonCode_Horizon_È(); var3 <= var2.HorizonCode_Horizon_È(); ++var3) {
                for (int var4 = var1.Â(); var4 <= var2.Â(); ++var4) {
                    for (int var5 = var1.Ý(); var5 <= var2.Ý(); ++var5) {
                        final BlockPos var6 = new BlockPos(var3, var4, var5);
                        final IBlockState var7 = this.Ï­Ðƒà.Â(var6);
                        try {
                            var7.Ý().HorizonCode_Horizon_È(this.Ï­Ðƒà, var6, var7, this);
                        }
                        catch (Throwable var9) {
                            final CrashReport var8 = CrashReport.HorizonCode_Horizon_È(var9, "Colliding entity with block");
                            final CrashReportCategory var10 = var8.HorizonCode_Horizon_È("Block being collided with");
                            CrashReportCategory.HorizonCode_Horizon_È(var10, var6, var7);
                            throw new ReportedException(var8);
                        }
                    }
                }
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        Block.Â var3 = p_180429_2_.ˆá;
        if (this.Ï­Ðƒà.Â(p_180429_1_.Ø­áŒŠá()).Ý() == Blocks.áŒŠá€) {
            var3 = Blocks.áŒŠá€.ˆá;
            this.HorizonCode_Horizon_È(var3.Ý(), var3.Ø­áŒŠá() * 0.15f, var3.Âµá€());
        }
        else if (!p_180429_2_.Ó().HorizonCode_Horizon_È()) {
            this.HorizonCode_Horizon_È(var3.Ý(), var3.Ø­áŒŠá() * 0.15f, var3.Âµá€());
        }
    }
    
    public void HorizonCode_Horizon_È(final String name, final float volume, final float pitch) {
        if (!this.áŠ()) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, name, volume, pitch);
        }
    }
    
    public boolean áŠ() {
        return this.£Ó.HorizonCode_Horizon_È(4) == 1;
    }
    
    public void Ø­áŒŠá(final boolean p_174810_1_) {
        this.£Ó.Â(4, (byte)(byte)(p_174810_1_ ? 1 : 0));
    }
    
    protected boolean áˆºÑ¢Õ() {
        return true;
    }
    
    protected void HorizonCode_Horizon_È(final double p_180433_1_, final boolean p_180433_3_, final Block p_180433_4_, final BlockPos p_180433_5_) {
        if (p_180433_3_) {
            if (this.Ï­à > 0.0f) {
                if (p_180433_4_ != null) {
                    p_180433_4_.HorizonCode_Horizon_È(this.Ï­Ðƒà, p_180433_5_, this, this.Ï­à);
                }
                else {
                    this.Ø­áŒŠá(this.Ï­à, 1.0f);
                }
                this.Ï­à = 0.0f;
            }
        }
        else if (p_180433_1_ < 0.0) {
            this.Ï­à -= (float)p_180433_1_;
        }
    }
    
    public AxisAlignedBB t_() {
        return null;
    }
    
    protected void Ó(final int amount) {
        if (!this.£Â) {
            this.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È, amount);
        }
    }
    
    public final boolean ˆáŠ() {
        return this.£Â;
    }
    
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
        if (this.µÕ != null) {
            this.µÕ.Ø­áŒŠá(distance, damageMultiplier);
        }
    }
    
    public boolean áŒŠ() {
        return this.Ø­á || this.Ï­Ðƒà.ŒÏ(new BlockPos(this.ŒÏ, this.Çªà¢, this.Ê)) || this.Ï­Ðƒà.ŒÏ(new BlockPos(this.ŒÏ, this.Çªà¢ + this.£ÂµÄ, this.Ê));
    }
    
    public boolean £ÂµÄ() {
        return this.Ø­á;
    }
    
    public boolean Ø­Âµ() {
        if (this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£É().Â(0.0, -0.4000000059604645, 0.0).Ø­áŒŠá(0.001, 0.001, 0.001), Material.Ø, this)) {
            if (!this.Ø­á && !this.Ï­Ï­Ï) {
                this.Ä();
            }
            this.Ï­à = 0.0f;
            this.Ø­á = true;
            this.Âµá€ = 0;
        }
        else {
            this.Ø­á = false;
        }
        return this.Ø­á;
    }
    
    protected void Ä() {
        float var1 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ * 0.20000000298023224 + this.ˆá * this.ˆá + this.ÇŽÕ * this.ÇŽÕ * 0.20000000298023224) * 0.2f;
        if (var1 > 1.0f) {
            var1 = 1.0f;
        }
        this.HorizonCode_Horizon_È(this.áˆºáˆºÈ(), var1, 1.0f + (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.4f);
        final float var2 = MathHelper.Ý(this.£É().Â);
        for (int var3 = 0; var3 < 1.0f + this.áŒŠ * 20.0f; ++var3) {
            final float var4 = (this.ˆáƒ.nextFloat() * 2.0f - 1.0f) * this.áŒŠ;
            final float var5 = (this.ˆáƒ.nextFloat() * 2.0f - 1.0f) * this.áŒŠ;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Âµá€, this.ŒÏ + var4, var2 + 1.0f, this.Ê + var5, this.ÇŽÉ, this.ˆá - this.ˆáƒ.nextFloat() * 0.2f, this.ÇŽÕ, new int[0]);
        }
        for (int var3 = 0; var3 < 1.0f + this.áŒŠ * 20.0f; ++var3) {
            final float var4 = (this.ˆáƒ.nextFloat() * 2.0f - 1.0f) * this.áŒŠ;
            final float var5 = (this.ˆáƒ.nextFloat() * 2.0f - 1.0f) * this.áŒŠ;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Ó, this.ŒÏ + var4, var2 + 1.0f, this.Ê + var5, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, new int[0]);
        }
    }
    
    public void Ñ¢Â() {
        if (this.ÇªÂµÕ() && !this.£ÂµÄ()) {
            this.Ï­à();
        }
    }
    
    protected void Ï­à() {
        final int var1 = MathHelper.Ý(this.ŒÏ);
        final int var2 = MathHelper.Ý(this.Çªà¢ - 0.20000000298023224);
        final int var3 = MathHelper.Ý(this.Ê);
        final BlockPos var4 = new BlockPos(var1, var2, var3);
        final IBlockState var5 = this.Ï­Ðƒà.Â(var4);
        final Block var6 = var5.Ý();
        if (var6.ÂµÈ() != -1) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.à¢, this.ŒÏ + (this.ˆáƒ.nextFloat() - 0.5) * this.áŒŠ, this.£É().Â + 0.1, this.Ê + (this.ˆáƒ.nextFloat() - 0.5) * this.áŒŠ, -this.ÇŽÉ * 4.0, 1.5, -this.ÇŽÕ * 4.0, Block.HorizonCode_Horizon_È(var5));
        }
    }
    
    protected String áˆºáˆºÈ() {
        return "game.neutral.swim.splash";
    }
    
    public boolean HorizonCode_Horizon_È(final Material materialIn) {
        final double var2 = this.Çªà¢ + this.Ðƒáƒ();
        final BlockPos var3 = new BlockPos(this.ŒÏ, var2, this.Ê);
        final IBlockState var4 = this.Ï­Ðƒà.Â(var3);
        final Block var5 = var4.Ý();
        if (var5.Ó() == materialIn) {
            final float var6 = BlockLiquid.Âµá€(var4.Ý().Ý(var4)) - 0.11111111f;
            final float var7 = var3.Â() + 1 - var6;
            final boolean var8 = var2 < var7;
            return (var8 || !(this instanceof EntityPlayer)) && var8;
        }
        return false;
    }
    
    public boolean ÇŽá€() {
        return this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£É().Â(-0.10000000149011612, -0.4000000059604645, -0.10000000149011612), Material.áŒŠÆ);
    }
    
    public void Â(float strafe, float forward, final float friction) {
        float var4 = strafe * strafe + forward * forward;
        if (var4 >= 1.0E-4f) {
            var4 = MathHelper.Ý(var4);
            if (var4 < 1.0f) {
                var4 = 1.0f;
            }
            var4 = friction / var4;
            strafe *= var4;
            forward *= var4;
            final float var5 = MathHelper.HorizonCode_Horizon_È(this.É * 3.1415927f / 180.0f);
            final float var6 = MathHelper.Â(this.É * 3.1415927f / 180.0f);
            this.ÇŽÉ += strafe * var6 - forward * var5;
            this.ÇŽÕ += forward * var6 + strafe * var5;
        }
    }
    
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        final BlockPos var2 = new BlockPos(this.ŒÏ, 0.0, this.Ê);
        if (this.Ï­Ðƒà.Ó(var2)) {
            final double var3 = (this.£É().Âµá€ - this.£É().Â) * 0.66;
            final int var4 = MathHelper.Ý(this.Çªà¢ + var3);
            return this.Ï­Ðƒà.HorizonCode_Horizon_È(var2.Â(var4), 0);
        }
        return 0;
    }
    
    public float Â(final float p_70013_1_) {
        final BlockPos var2 = new BlockPos(this.ŒÏ, 0.0, this.Ê);
        if (this.Ï­Ðƒà.Ó(var2)) {
            final double var3 = (this.£É().Âµá€ - this.£É().Â) * 0.66;
            final int var4 = MathHelper.Ý(this.Çªà¢ + var3);
            return this.Ï­Ðƒà.£à(var2.Â(var4));
        }
        return 0.0f;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn) {
        this.Ï­Ðƒà = worldIn;
    }
    
    public void HorizonCode_Horizon_È(final double x, final double y, final double z, final float yaw, final float pitch) {
        this.ŒÏ = x;
        this.áŒŠà = x;
        this.Çªà¢ = y;
        this.ŠÄ = y;
        this.Ê = z;
        this.Ñ¢á = z;
        this.É = yaw;
        this.á€ = yaw;
        this.áƒ = pitch;
        this.Õ = pitch;
        final double var9 = this.á€ - yaw;
        if (var9 < -180.0) {
            this.á€ += 360.0f;
        }
        if (var9 >= 180.0) {
            this.á€ -= 360.0f;
        }
        this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
        this.Â(yaw, pitch);
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_174828_1_, final float p_174828_2_, final float p_174828_3_) {
        this.Â(p_174828_1_.HorizonCode_Horizon_È() + 0.5, p_174828_1_.Â(), p_174828_1_.Ý() + 0.5, p_174828_2_, p_174828_3_);
    }
    
    public void Â(final double x, final double y, final double z, final float yaw, final float pitch) {
        this.ŒÏ = x;
        this.áŒŠà = x;
        this.áˆºáˆºÈ = x;
        this.Çªà¢ = y;
        this.ŠÄ = y;
        this.ÇŽá€ = y;
        this.Ê = z;
        this.Ñ¢á = z;
        this.Ï = z;
        this.É = yaw;
        this.áƒ = pitch;
        this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
    }
    
    public float Ø­áŒŠá(final Entity entityIn) {
        final float var2 = (float)(this.ŒÏ - entityIn.ŒÏ);
        final float var3 = (float)(this.Çªà¢ - entityIn.Çªà¢);
        final float var4 = (float)(this.Ê - entityIn.Ê);
        return MathHelper.Ý(var2 * var2 + var3 * var3 + var4 * var4);
    }
    
    public double Âµá€(final double x, final double y, final double z) {
        final double var7 = this.ŒÏ - x;
        final double var8 = this.Çªà¢ - y;
        final double var9 = this.Ê - z;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public double Â(final BlockPos p_174818_1_) {
        return p_174818_1_.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
    }
    
    public double Ý(final BlockPos p_174831_1_) {
        return p_174831_1_.Ø­áŒŠá(this.ŒÏ, this.Çªà¢, this.Ê);
    }
    
    public double Ó(final double x, final double y, final double z) {
        final double var7 = this.ŒÏ - x;
        final double var8 = this.Çªà¢ - y;
        final double var9 = this.Ê - z;
        return MathHelper.HorizonCode_Horizon_È(var7 * var7 + var8 * var8 + var9 * var9);
    }
    
    public double Âµá€(final Entity entityIn) {
        final double var2 = this.ŒÏ - entityIn.ŒÏ;
        final double var3 = this.Çªà¢ - entityIn.Çªà¢;
        final double var4 = this.Ê - entityIn.Ê;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer entityIn) {
    }
    
    public void Ó(final Entity entityIn) {
        if (entityIn.µÕ != this && entityIn.Æ != this && !entityIn.ÇªÓ && !this.ÇªÓ) {
            double var2 = entityIn.ŒÏ - this.ŒÏ;
            double var3 = entityIn.Ê - this.Ê;
            double var4 = MathHelper.HorizonCode_Horizon_È(var2, var3);
            if (var4 >= 0.009999999776482582) {
                var4 = MathHelper.HorizonCode_Horizon_È(var4);
                var2 /= var4;
                var3 /= var4;
                double var5 = 1.0 / var4;
                if (var5 > 1.0) {
                    var5 = 1.0;
                }
                var2 *= var5;
                var3 *= var5;
                var2 *= 0.05000000074505806;
                var3 *= 0.05000000074505806;
                var2 *= 1.0f - this.áˆºÏ;
                var3 *= 1.0f - this.áˆºÏ;
                if (this.µÕ == null) {
                    this.à(-var2, 0.0, -var3);
                }
                if (entityIn.µÕ == null) {
                    entityIn.à(var2, 0.0, var3);
                }
            }
        }
    }
    
    public void à(final double x, final double y, final double z) {
        this.ÇŽÉ += x;
        this.ˆá += y;
        this.ÇŽÕ += z;
        this.áŒŠÏ = true;
    }
    
    protected void Ï() {
        this.È = true;
    }
    
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        this.Ï();
        return false;
    }
    
    public Vec3 Ó(final float p_70676_1_) {
        if (p_70676_1_ == 1.0f) {
            return this.Âµá€(this.áƒ, this.É);
        }
        final float var2 = this.Õ + (this.áƒ - this.Õ) * p_70676_1_;
        final float var3 = this.á€ + (this.É - this.á€) * p_70676_1_;
        return this.Âµá€(var2, var3);
    }
    
    protected final Vec3 Âµá€(final float p_174806_1_, final float p_174806_2_) {
        final float var3 = MathHelper.Â(-p_174806_2_ * 0.017453292f - 3.1415927f);
        final float var4 = MathHelper.HorizonCode_Horizon_È(-p_174806_2_ * 0.017453292f - 3.1415927f);
        final float var5 = -MathHelper.Â(-p_174806_1_ * 0.017453292f);
        final float var6 = MathHelper.HorizonCode_Horizon_È(-p_174806_1_ * 0.017453292f);
        return new Vec3(var4 * var5, var6, var3 * var5);
    }
    
    public Vec3 à(final float p_174824_1_) {
        if (p_174824_1_ == 1.0f) {
            return new Vec3(this.ŒÏ, this.Çªà¢ + this.Ðƒáƒ(), this.Ê);
        }
        final double var2 = this.áŒŠà + (this.ŒÏ - this.áŒŠà) * p_174824_1_;
        final double var3 = this.ŠÄ + (this.Çªà¢ - this.ŠÄ) * p_174824_1_ + this.Ðƒáƒ();
        final double var4 = this.Ñ¢á + (this.Ê - this.Ñ¢á) * p_174824_1_;
        return new Vec3(var2, var3, var4);
    }
    
    public MovingObjectPosition HorizonCode_Horizon_È(final double p_174822_1_, final float p_174822_3_) {
        final Vec3 var4 = this.à(p_174822_3_);
        final Vec3 var5 = this.Ó(p_174822_3_);
        final Vec3 var6 = var4.Â(var5.HorizonCode_Horizon_È * p_174822_1_, var5.Â * p_174822_1_, var5.Ý * p_174822_1_);
        return this.Ï­Ðƒà.HorizonCode_Horizon_È(var4, var6, false, false, true);
    }
    
    public boolean Ô() {
        return false;
    }
    
    public boolean £à() {
        return false;
    }
    
    public void HorizonCode_Horizon_È(final Entity entityIn, final int amount) {
    }
    
    public boolean Ø(final double x, final double y, final double z) {
        final double var7 = this.ŒÏ - x;
        final double var8 = this.Çªà¢ - y;
        final double var9 = this.Ê - z;
        final double var10 = var7 * var7 + var8 * var8 + var9 * var9;
        return this.HorizonCode_Horizon_È(var10);
    }
    
    public boolean HorizonCode_Horizon_È(final double distance) {
        double var3 = this.£É().HorizonCode_Horizon_È();
        var3 *= 64.0 * this.¥Æ;
        return distance < var3 * var3;
    }
    
    public boolean Ý(final NBTTagCompound tagCompund) {
        final String var2 = this.áˆºÏ();
        if (!this.ˆáŠ && var2 != null) {
            tagCompund.HorizonCode_Horizon_È("id", var2);
            this.Âµá€(tagCompund);
            return true;
        }
        return false;
    }
    
    public boolean Ø­áŒŠá(final NBTTagCompound tagCompund) {
        final String var2 = this.áˆºÏ();
        if (!this.ˆáŠ && var2 != null && this.µÕ == null) {
            tagCompund.HorizonCode_Horizon_È("id", var2);
            this.Âµá€(tagCompund);
            return true;
        }
        return false;
    }
    
    public void Âµá€(final NBTTagCompound tagCompund) {
        try {
            tagCompund.HorizonCode_Horizon_È("Pos", this.HorizonCode_Horizon_È(new double[] { this.ŒÏ, this.Çªà¢, this.Ê }));
            tagCompund.HorizonCode_Horizon_È("Motion", this.HorizonCode_Horizon_È(new double[] { this.ÇŽÉ, this.ˆá, this.ÇŽÕ }));
            tagCompund.HorizonCode_Horizon_È("Rotation", this.HorizonCode_Horizon_È(new float[] { this.É, this.áƒ }));
            tagCompund.HorizonCode_Horizon_È("FallDistance", this.Ï­à);
            tagCompund.HorizonCode_Horizon_È("Fire", (short)this.Âµá€);
            tagCompund.HorizonCode_Horizon_È("Air", (short)this.ˆÓ());
            tagCompund.HorizonCode_Horizon_È("OnGround", this.ŠÂµà);
            tagCompund.HorizonCode_Horizon_È("Dimension", this.ÇªÔ);
            tagCompund.HorizonCode_Horizon_È("Invulnerable", this.áˆºÑ¢Õ);
            tagCompund.HorizonCode_Horizon_È("PortalCooldown", this.áŒŠáŠ);
            tagCompund.HorizonCode_Horizon_È("UUIDMost", this.£áŒŠá().getMostSignificantBits());
            tagCompund.HorizonCode_Horizon_È("UUIDLeast", this.£áŒŠá().getLeastSignificantBits());
            if (this.Šà() != null && this.Šà().length() > 0) {
                tagCompund.HorizonCode_Horizon_È("CustomName", this.Šà());
                tagCompund.HorizonCode_Horizon_È("CustomNameVisible", this.áŒŠá€());
            }
            this.ÂµÈ.Â(tagCompund);
            if (this.áŠ()) {
                tagCompund.HorizonCode_Horizon_È("Silent", this.áŠ());
            }
            this.HorizonCode_Horizon_È(tagCompund);
            if (this.Æ != null) {
                final NBTTagCompound var2 = new NBTTagCompound();
                if (this.Æ.Ý(var2)) {
                    tagCompund.HorizonCode_Horizon_È("Riding", var2);
                }
            }
        }
        catch (Throwable var4) {
            final CrashReport var3 = CrashReport.HorizonCode_Horizon_È(var4, "Saving entity NBT");
            final CrashReportCategory var5 = var3.HorizonCode_Horizon_È("Entity being saved");
            this.HorizonCode_Horizon_È(var5);
            throw new ReportedException(var3);
        }
    }
    
    public void Ó(final NBTTagCompound tagCompund) {
        try {
            final NBTTagList var2 = tagCompund.Ý("Pos", 6);
            final NBTTagList var3 = tagCompund.Ý("Motion", 6);
            final NBTTagList var4 = tagCompund.Ý("Rotation", 5);
            this.ÇŽÉ = var3.Ø­áŒŠá(0);
            this.ˆá = var3.Ø­áŒŠá(1);
            this.ÇŽÕ = var3.Ø­áŒŠá(2);
            if (Math.abs(this.ÇŽÉ) > 10.0) {
                this.ÇŽÉ = 0.0;
            }
            if (Math.abs(this.ˆá) > 10.0) {
                this.ˆá = 0.0;
            }
            if (Math.abs(this.ÇŽÕ) > 10.0) {
                this.ÇŽÕ = 0.0;
            }
            final double ø­áŒŠá = var2.Ø­áŒŠá(0);
            this.ŒÏ = ø­áŒŠá;
            this.áˆºáˆºÈ = ø­áŒŠá;
            this.áŒŠà = ø­áŒŠá;
            final double ø­áŒŠá2 = var2.Ø­áŒŠá(1);
            this.Çªà¢ = ø­áŒŠá2;
            this.ÇŽá€ = ø­áŒŠá2;
            this.ŠÄ = ø­áŒŠá2;
            final double ø­áŒŠá3 = var2.Ø­áŒŠá(2);
            this.Ê = ø­áŒŠá3;
            this.Ï = ø­áŒŠá3;
            this.Ñ¢á = ø­áŒŠá3;
            final float âµá€ = var4.Âµá€(0);
            this.É = âµá€;
            this.á€ = âµá€;
            final float âµá€2 = var4.Âµá€(1);
            this.áƒ = âµá€2;
            this.Õ = âµá€2;
            this.Ï­à = tagCompund.Ø("FallDistance");
            this.Âµá€ = tagCompund.Âµá€("Fire");
            this.Ø(tagCompund.Âµá€("Air"));
            this.ŠÂµà = tagCompund.£á("OnGround");
            this.ÇªÔ = tagCompund.Ó("Dimension");
            this.áˆºÑ¢Õ = tagCompund.£á("Invulnerable");
            this.áŒŠáŠ = tagCompund.Ó("PortalCooldown");
            if (tagCompund.Â("UUIDMost", 4) && tagCompund.Â("UUIDLeast", 4)) {
                this.ŠÓ = new UUID(tagCompund.à("UUIDMost"), tagCompund.à("UUIDLeast"));
            }
            else if (tagCompund.Â("UUID", 8)) {
                this.ŠÓ = UUID.fromString(tagCompund.áˆºÑ¢Õ("UUID"));
            }
            this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
            this.Â(this.É, this.áƒ);
            if (tagCompund.Â("CustomName", 8) && tagCompund.áˆºÑ¢Õ("CustomName").length() > 0) {
                this.à(tagCompund.áˆºÑ¢Õ("CustomName"));
            }
            this.Ø(tagCompund.£á("CustomNameVisible"));
            this.ÂµÈ.HorizonCode_Horizon_È(tagCompund);
            this.Ø­áŒŠá(tagCompund.£á("Silent"));
            this.Â(tagCompund);
            if (this.ÇªÓ()) {
                this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
            }
        }
        catch (Throwable var6) {
            final CrashReport var5 = CrashReport.HorizonCode_Horizon_È(var6, "Loading entity NBT");
            final CrashReportCategory var7 = var5.HorizonCode_Horizon_È("Entity being loaded");
            this.HorizonCode_Horizon_È(var7);
            throw new ReportedException(var5);
        }
    }
    
    protected boolean ÇªÓ() {
        return true;
    }
    
    protected final String áˆºÏ() {
        return EntityList.Â(this);
    }
    
    protected abstract void Â(final NBTTagCompound p0);
    
    protected abstract void HorizonCode_Horizon_È(final NBTTagCompound p0);
    
    public void ˆáƒ() {
    }
    
    protected NBTTagList HorizonCode_Horizon_È(final double... numbers) {
        final NBTTagList var2 = new NBTTagList();
        for (final double var5 : numbers) {
            var2.HorizonCode_Horizon_È(new NBTTagDouble(var5));
        }
        return var2;
    }
    
    protected NBTTagList HorizonCode_Horizon_È(final float... numbers) {
        final NBTTagList var2 = new NBTTagList();
        for (final float var5 : numbers) {
            var2.HorizonCode_Horizon_È(new NBTTagFloat(var5));
        }
        return var2;
    }
    
    public EntityItem HorizonCode_Horizon_È(final Item_1028566121 itemIn, final int size) {
        return this.HorizonCode_Horizon_È(itemIn, size, 0.0f);
    }
    
    public EntityItem HorizonCode_Horizon_È(final Item_1028566121 itemIn, final int size, final float p_145778_3_) {
        return this.HorizonCode_Horizon_È(new ItemStack(itemIn, size, 0), p_145778_3_);
    }
    
    public EntityItem HorizonCode_Horizon_È(final ItemStack itemStackIn, final float offsetY) {
        if (itemStackIn.Â != 0 && itemStackIn.HorizonCode_Horizon_È() != null) {
            final EntityItem var3 = new EntityItem(this.Ï­Ðƒà, this.ŒÏ, this.Çªà¢ + offsetY, this.Ê, itemStackIn);
            var3.ˆà();
            this.Ï­Ðƒà.HorizonCode_Horizon_È(var3);
            return var3;
        }
        return null;
    }
    
    public boolean Œ() {
        return !this.ˆáŠ;
    }
    
    public boolean £Ï() {
        if (this.ÇªÓ) {
            return false;
        }
        for (int var1 = 0; var1 < 8; ++var1) {
            final double var2 = this.ŒÏ + ((var1 >> 0) % 2 - 0.5f) * this.áŒŠ * 0.8f;
            final double var3 = this.Çªà¢ + ((var1 >> 1) % 2 - 0.5f) * 0.1f;
            final double var4 = this.Ê + ((var1 >> 2) % 2 - 0.5f) * this.áŒŠ * 0.8f;
            if (this.Ï­Ðƒà.Â(new BlockPos(var2, var3 + this.Ðƒáƒ(), var4)).Ý().áŒŠÆ()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean b_(final EntityPlayer playerIn) {
        return false;
    }
    
    public AxisAlignedBB à(final Entity entityIn) {
        return null;
    }
    
    public void Ø­á() {
        if (this.Æ.ˆáŠ) {
            this.Æ = null;
        }
        else {
            this.ÇŽÉ = 0.0;
            this.ˆá = 0.0;
            this.ÇŽÕ = 0.0;
            this.á();
            if (this.Æ != null) {
                this.Æ.ˆÉ();
                this.áŒŠÆ += this.Æ.É - this.Æ.á€;
                this.Ø += this.Æ.áƒ - this.Æ.Õ;
                while (this.áŒŠÆ >= 180.0) {
                    this.áŒŠÆ -= 360.0;
                }
                while (this.áŒŠÆ < -180.0) {
                    this.áŒŠÆ += 360.0;
                }
                while (this.Ø >= 180.0) {
                    this.Ø -= 360.0;
                }
                while (this.Ø < -180.0) {
                    this.Ø += 360.0;
                }
                double var1 = this.áŒŠÆ * 0.5;
                double var2 = this.Ø * 0.5;
                final float var3 = 10.0f;
                if (var1 > var3) {
                    var1 = var3;
                }
                if (var1 < -var3) {
                    var1 = -var3;
                }
                if (var2 > var3) {
                    var2 = var3;
                }
                if (var2 < -var3) {
                    var2 = -var3;
                }
                this.áŒŠÆ -= var1;
                this.Ø -= var2;
            }
        }
    }
    
    public void ˆÉ() {
        if (this.µÕ != null) {
            this.µÕ.Ý(this.ŒÏ, this.Çªà¢ + this.£Â() + this.µÕ.Ï­Ï­Ï(), this.Ê);
        }
    }
    
    public double Ï­Ï­Ï() {
        return 0.0;
    }
    
    public double £Â() {
        return this.£ÂµÄ * 0.75;
    }
    
    public void HorizonCode_Horizon_È(final Entity entityIn) {
        this.Ø = 0.0;
        this.áŒŠÆ = 0.0;
        if (entityIn == null) {
            if (this.Æ != null) {
                this.Â(this.Æ.ŒÏ, this.Æ.£É().Â + this.Æ.£ÂµÄ, this.Æ.Ê, this.É, this.áƒ);
                this.Æ.µÕ = null;
            }
            this.Æ = null;
        }
        else {
            if (this.Æ != null) {
                this.Æ.µÕ = null;
            }
            if (entityIn != null) {
                for (Entity var2 = entityIn.Æ; var2 != null; var2 = var2.Æ) {
                    if (var2 == this) {
                        return;
                    }
                }
            }
            this.Æ = entityIn;
            entityIn.µÕ = this;
        }
    }
    
    public void HorizonCode_Horizon_È(final double p_180426_1_, double p_180426_3_, final double p_180426_5_, final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
        this.Ý(p_180426_1_, p_180426_3_, p_180426_5_);
        this.Â(p_180426_7_, p_180426_8_);
        final List var11 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É().Ø­áŒŠá(0.03125, 0.0, 0.03125));
        if (!var11.isEmpty()) {
            double var12 = 0.0;
            for (final AxisAlignedBB var14 : var11) {
                if (var14.Âµá€ > var12) {
                    var12 = var14.Âµá€;
                }
            }
            p_180426_3_ += var12 - this.£É().Â;
            this.Ý(p_180426_1_, p_180426_3_, p_180426_5_);
        }
    }
    
    public float £Ó() {
        try {
            final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
            final Mod hitbox = ModuleManager.HorizonCode_Horizon_È(HitboxExtend.class);
            if (hitbox.áˆºÑ¢Õ()) {
                return Horizon.à¢.áˆºÑ¢Õ;
            }
        }
        catch (Exception ex) {}
        return 0.1f;
    }
    
    public Vec3 ˆÐƒØ­à() {
        return null;
    }
    
    public void £Õ() {
        if (this.áŒŠáŠ > 0) {
            this.áŒŠáŠ = this.Ï­Ô();
        }
        else {
            final double var1 = this.áŒŠà - this.ŒÏ;
            final double var2 = this.Ñ¢á - this.Ê;
            if (!this.Ï­Ðƒà.ŠÄ && !this.ˆÓ) {
                int var3;
                if (MathHelper.Âµá€((float)var1) > MathHelper.Âµá€((float)var2)) {
                    var3 = ((var1 > 0.0) ? EnumFacing.Âµá€.Ý() : EnumFacing.Ó.Ý());
                }
                else {
                    var3 = ((var2 > 0.0) ? EnumFacing.Ý.Ý() : EnumFacing.Ø­áŒŠá.Ý());
                }
                this.Û = var3;
            }
            this.ˆÓ = true;
        }
    }
    
    public int Ï­Ô() {
        return 300;
    }
    
    public void áŒŠÆ(final double x, final double y, final double z) {
        this.ÇŽÉ = x;
        this.ˆá = y;
        this.ÇŽÕ = z;
    }
    
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
    }
    
    public void Œà() {
    }
    
    public ItemStack[] Ðƒá() {
        return null;
    }
    
    public void HorizonCode_Horizon_È(final int slotIn, final ItemStack itemStackIn) {
    }
    
    public boolean ˆÏ() {
        final boolean var1 = this.Ï­Ðƒà != null && this.Ï­Ðƒà.ŠÄ;
        return !this.£Â && (this.Âµá€ > 0 || (var1 && this.à(0)));
    }
    
    public boolean áˆºÇŽØ() {
        return this.Æ != null;
    }
    
    public boolean Çªà¢() {
        return this.à(1);
    }
    
    public void Âµá€(final boolean sneaking) {
        this.HorizonCode_Horizon_È(1, sneaking);
    }
    
    public boolean ÇªÂµÕ() {
        return this.à(3);
    }
    
    public void Â(final boolean sprinting) {
        this.HorizonCode_Horizon_È(3, sprinting);
    }
    
    public boolean áŒŠÏ() {
        return this.à(5);
    }
    
    public boolean Ý(final EntityPlayer playerIn) {
        return !playerIn.Ø­áŒŠá() && this.áŒŠÏ();
    }
    
    public void Ó(final boolean invisible) {
        this.HorizonCode_Horizon_È(5, invisible);
    }
    
    public boolean áŒŠáŠ() {
        return this.à(4);
    }
    
    public void à(final boolean eating) {
        this.HorizonCode_Horizon_È(4, eating);
    }
    
    protected boolean à(final int flag) {
        return (this.£Ó.HorizonCode_Horizon_È(0) & 1 << flag) != 0x0;
    }
    
    protected void HorizonCode_Horizon_È(final int flag, final boolean set) {
        final byte var3 = this.£Ó.HorizonCode_Horizon_È(0);
        if (set) {
            this.£Ó.Â(0, (byte)(var3 | 1 << flag));
        }
        else {
            this.£Ó.Â(0, (byte)(var3 & ~(1 << flag)));
        }
    }
    
    public int ˆÓ() {
        return this.£Ó.Â(1);
    }
    
    public void Ø(final int air) {
        this.£Ó.Â(1, (short)air);
    }
    
    public void HorizonCode_Horizon_È(final EntityLightningBolt lightningBolt) {
        this.HorizonCode_Horizon_È(DamageSource.Â, 5.0f);
        ++this.Âµá€;
        if (this.Âµá€ == 0) {
            this.Âµá€(8);
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityLivingBase entityLivingIn) {
    }
    
    protected boolean Â(final double x, final double y, final double z) {
        final BlockPos var7 = new BlockPos(x, y, z);
        final double var8 = x - var7.HorizonCode_Horizon_È();
        final double var9 = y - var7.Â();
        final double var10 = z - var7.Ý();
        final List var11 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£É());
        if (var11.isEmpty() && !this.Ï­Ðƒà.Ø­à(var7)) {
            return false;
        }
        byte var12 = 3;
        double var13 = 9999.0;
        if (!this.Ï­Ðƒà.Ø­à(var7.Ø()) && var8 < var13) {
            var13 = var8;
            var12 = 0;
        }
        if (!this.Ï­Ðƒà.Ø­à(var7.áŒŠÆ()) && 1.0 - var8 < var13) {
            var13 = 1.0 - var8;
            var12 = 1;
        }
        if (!this.Ï­Ðƒà.Ø­à(var7.Ø­áŒŠá()) && 1.0 - var9 < var13) {
            var13 = 1.0 - var9;
            var12 = 3;
        }
        if (!this.Ï­Ðƒà.Ø­à(var7.Ó()) && var10 < var13) {
            var13 = var10;
            var12 = 4;
        }
        if (!this.Ï­Ðƒà.Ø­à(var7.à()) && 1.0 - var10 < var13) {
            var13 = 1.0 - var10;
            var12 = 5;
        }
        final float var14 = this.ˆáƒ.nextFloat() * 0.2f + 0.1f;
        if (var12 == 0) {
            this.ÇŽÉ = -var14;
        }
        if (var12 == 1) {
            this.ÇŽÉ = var14;
        }
        if (var12 == 3) {
            this.ˆá = var14;
        }
        if (var12 == 4) {
            this.ÇŽÕ = -var14;
        }
        if (var12 == 5) {
            this.ÇŽÕ = var14;
        }
        return true;
    }
    
    public void ¥Ä() {
        this.áŠ = true;
        this.Ï­à = 0.0f;
    }
    
    @Override
    public String v_() {
        if (this.j_()) {
            return this.Šà();
        }
        String var1 = EntityList.Â(this);
        if (var1 == null) {
            var1 = "generic";
        }
        return StatCollector.HorizonCode_Horizon_È("entity." + var1 + ".name");
    }
    
    public Entity[] ÇªÔ() {
        return null;
    }
    
    public boolean Ø(final Entity entityIn) {
        return this == entityIn;
    }
    
    public float Û() {
        return 0.0f;
    }
    
    public void Ø(final float rotation) {
    }
    
    public boolean Å() {
        return true;
    }
    
    public boolean áŒŠÆ(final Entity entityIn) {
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", this.getClass().getSimpleName(), this.v_(), this.ˆà, (this.Ï­Ðƒà == null) ? "~NULL~" : this.Ï­Ðƒà.ŒÏ().áˆºÑ¢Õ(), this.ŒÏ, this.Çªà¢, this.Ê);
    }
    
    public boolean HorizonCode_Horizon_È(final DamageSource p_180431_1_) {
        return this.áˆºÑ¢Õ && p_180431_1_ != DamageSource.áˆºÑ¢Õ && !p_180431_1_.µÕ();
    }
    
    public void áˆºÑ¢Õ(final Entity entityIn) {
        this.Â(entityIn.ŒÏ, entityIn.Çªà¢, entityIn.Ê, entityIn.É, entityIn.áƒ);
    }
    
    public void ÂµÈ(final Entity p_180432_1_) {
        final NBTTagCompound var2 = new NBTTagCompound();
        p_180432_1_.Âµá€(var2);
        this.Ó(var2);
        this.áŒŠáŠ = p_180432_1_.áŒŠáŠ;
        this.Û = p_180432_1_.Û;
    }
    
    public void áŒŠÆ(final int dimensionId) {
        if (!this.Ï­Ðƒà.ŠÄ && !this.ˆáŠ) {
            this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("changeDimension");
            final MinecraftServer var2 = MinecraftServer.áƒ();
            final int var3 = this.ÇªÔ;
            final WorldServer var4 = var2.HorizonCode_Horizon_È(var3);
            WorldServer var5 = var2.HorizonCode_Horizon_È(dimensionId);
            this.ÇªÔ = dimensionId;
            if (var3 == 1 && dimensionId == 1) {
                var5 = var2.HorizonCode_Horizon_È(0);
                this.ÇªÔ = 0;
            }
            this.Ï­Ðƒà.Â(this);
            this.ˆáŠ = false;
            this.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("reposition");
            var2.Œ().HorizonCode_Horizon_È(this, var3, var4, var5);
            this.Ï­Ðƒà.Ï­Ðƒà.Ý("reloading");
            final Entity var6 = EntityList.HorizonCode_Horizon_È(EntityList.Â(this), var5);
            if (var6 != null) {
                var6.ÂµÈ(this);
                if (var3 == 1 && dimensionId == 1) {
                    final BlockPos var7 = this.Ï­Ðƒà.ˆà(var5.áŒŠà());
                    var6.HorizonCode_Horizon_È(var7, var6.É, var6.áƒ);
                }
                var5.HorizonCode_Horizon_È(var6);
            }
            this.ˆáŠ = true;
            this.Ï­Ðƒà.Ï­Ðƒà.Â();
            var4.áŒŠ();
            var5.áŒŠ();
            this.Ï­Ðƒà.Ï­Ðƒà.Â();
        }
    }
    
    public float HorizonCode_Horizon_È(final Explosion p_180428_1_, final World worldIn, final BlockPos p_180428_3_, final IBlockState p_180428_4_) {
        return p_180428_4_.Ý().HorizonCode_Horizon_È(this);
    }
    
    public boolean HorizonCode_Horizon_È(final Explosion p_174816_1_, final World worldIn, final BlockPos p_174816_3_, final IBlockState p_174816_4_, final float p_174816_5_) {
        return true;
    }
    
    public int ŠÓ() {
        return 3;
    }
    
    public int ÇŽá() {
        return this.Û;
    }
    
    public boolean Ñ¢à() {
        return false;
    }
    
    public void HorizonCode_Horizon_È(final CrashReportCategory category) {
        category.HorizonCode_Horizon_È("Entity Type", new Callable() {
            private static final String Â = "CL_00001534";
            
            public String HorizonCode_Horizon_È() {
                return String.valueOf(EntityList.Â(Entity.this)) + " (" + Entity.this.getClass().getCanonicalName() + ")";
            }
        });
        category.HorizonCode_Horizon_È("Entity ID", this.ˆà);
        category.HorizonCode_Horizon_È("Entity Name", new Callable() {
            private static final String Â = "CL_00001535";
            
            public String HorizonCode_Horizon_È() {
                return Entity.this.v_();
            }
        });
        category.HorizonCode_Horizon_È("Entity's Exact location", String.format("%.2f, %.2f, %.2f", this.ŒÏ, this.Çªà¢, this.Ê));
        category.HorizonCode_Horizon_È("Entity's Block location", CrashReportCategory.HorizonCode_Horizon_È(MathHelper.Ý(this.ŒÏ), MathHelper.Ý(this.Çªà¢), MathHelper.Ý(this.Ê)));
        category.HorizonCode_Horizon_È("Entity's Momentum", String.format("%.2f, %.2f, %.2f", this.ÇŽÉ, this.ˆá, this.ÇŽÕ));
        category.HorizonCode_Horizon_È("Entity's Rider", new Callable() {
            private static final String Â = "CL_00002259";
            
            public String HorizonCode_Horizon_È() {
                return Entity.this.µÕ.toString();
            }
            
            @Override
            public Object call() {
                return this.HorizonCode_Horizon_È();
            }
        });
        category.HorizonCode_Horizon_È("Entity's Vehicle", new Callable() {
            private static final String Â = "CL_00002258";
            
            public String HorizonCode_Horizon_È() {
                return Entity.this.Æ.toString();
            }
            
            @Override
            public Object call() {
                return this.HorizonCode_Horizon_È();
            }
        });
    }
    
    public boolean ÇªØ­() {
        return this.ˆÏ();
    }
    
    public UUID £áŒŠá() {
        return this.ŠÓ;
    }
    
    public boolean áˆº() {
        return true;
    }
    
    @Override
    public IChatComponent Ý() {
        final ChatComponentText var1 = new ChatComponentText(this.v_());
        var1.à().HorizonCode_Horizon_È(this.Ñ¢Ç());
        var1.à().HorizonCode_Horizon_È(this.£áŒŠá().toString());
        return var1;
    }
    
    public void à(final String p_96094_1_) {
        this.£Ó.Â(2, p_96094_1_);
    }
    
    public String Šà() {
        return this.£Ó.Âµá€(2);
    }
    
    public boolean j_() {
        return this.£Ó.Âµá€(2).length() > 0;
    }
    
    public void Ø(final boolean p_174805_1_) {
        this.£Ó.Â(3, (byte)(byte)(p_174805_1_ ? 1 : 0));
    }
    
    public boolean áŒŠá€() {
        return this.£Ó.HorizonCode_Horizon_È(3) == 1;
    }
    
    public void áˆºÑ¢Õ(final double p_70634_1_, final double p_70634_3_, final double p_70634_5_) {
        this.Â(p_70634_1_, p_70634_3_, p_70634_5_, this.É, this.áƒ);
    }
    
    public boolean ¥Ï() {
        return this.áŒŠá€();
    }
    
    public void áˆºÑ¢Õ(final int p_145781_1_) {
    }
    
    public EnumFacing ˆà¢() {
        return EnumFacing.Â(MathHelper.Ý(this.É * 4.0f / 360.0f + 0.5) & 0x3);
    }
    
    protected HoverEvent Ñ¢Ç() {
        final NBTTagCompound var1 = new NBTTagCompound();
        final String var2 = EntityList.Â(this);
        var1.HorizonCode_Horizon_È("id", this.£áŒŠá().toString());
        if (var2 != null) {
            var1.HorizonCode_Horizon_È("type", var2);
        }
        var1.HorizonCode_Horizon_È("name", this.v_());
        return new HoverEvent(HoverEvent.HorizonCode_Horizon_È.Ø­áŒŠá, new ChatComponentText(var1.toString()));
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayerMP p_174827_1_) {
        return true;
    }
    
    public AxisAlignedBB £É() {
        return this.à¢;
    }
    
    public void HorizonCode_Horizon_È(final AxisAlignedBB p_174826_1_) {
        this.à¢ = p_174826_1_;
    }
    
    public float Ðƒáƒ() {
        return this.£ÂµÄ * 0.85f;
    }
    
    public boolean Ðƒà() {
        return this.Ý;
    }
    
    public void áŒŠÆ(final boolean p_174821_1_) {
        this.Ý = p_174821_1_;
    }
    
    public boolean Â(final int p_174820_1_, final ItemStack p_174820_2_) {
        return false;
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
        return new BlockPos(this.ŒÏ, this.Çªà¢ + 0.5, this.Ê);
    }
    
    @Override
    public Vec3 z_() {
        return new Vec3(this.ŒÏ, this.Çªà¢, this.Ê);
    }
    
    @Override
    public World k_() {
        return this.Ï­Ðƒà;
    }
    
    @Override
    public Entity l_() {
        return this;
    }
    
    @Override
    public boolean g_() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final CommandResultStats.HorizonCode_Horizon_È p_174794_1_, final int p_174794_2_) {
        this.ÂµÈ.HorizonCode_Horizon_È(this, p_174794_1_, p_174794_2_);
    }
    
    public CommandResultStats ¥É() {
        return this.ÂµÈ;
    }
    
    public void á(final Entity p_174817_1_) {
        this.ÂµÈ.HorizonCode_Horizon_È(p_174817_1_.¥É());
    }
    
    public NBTTagCompound £ÇªÓ() {
        return null;
    }
    
    public void à(final NBTTagCompound p_174834_1_) {
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_174825_1_, final Vec3 p_174825_2_) {
        return false;
    }
    
    public boolean ÂµÕ() {
        return false;
    }
    
    protected void HorizonCode_Horizon_È(final EntityLivingBase p_174815_1_, final Entity p_174815_2_) {
        if (p_174815_2_ instanceof EntityLivingBase) {
            EnchantmentHelper.HorizonCode_Horizon_È((EntityLivingBase)p_174815_2_, p_174815_1_);
        }
        EnchantmentHelper.Â(p_174815_1_, p_174815_2_);
    }
}
