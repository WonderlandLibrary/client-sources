package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;

@ModInfo(Ø­áŒŠá = Category.COMBAT, Ý = 16777215, Â = "A KillAura without AttackDelay", HorizonCode_Horizon_È = "RageAura")
public class RageAura extends Mod
{
    public static boolean Ý;
    public static boolean Ø­áŒŠá;
    public static boolean Âµá€;
    public boolean Ó;
    public static boolean à;
    public static double Ø;
    public boolean áŒŠÆ;
    public boolean áˆºÑ¢Õ;
    public boolean ÂµÈ;
    public static boolean á;
    public static boolean ˆÏ­;
    public static boolean £á;
    public EntityLivingBase Å;
    private ArrayList<EntityLivingBase> £à;
    private ArrayList<EntityLivingBase> µà;
    private TimeHelper ˆà;
    private TimeHelper ¥Æ;
    private EntityLivingBase Ø­à;
    private Random µÕ;
    private float Æ;
    private float Šáƒ;
    
    static {
        RageAura.Ý = true;
        RageAura.Ø­áŒŠá = true;
        RageAura.Âµá€ = true;
        RageAura.à = false;
        RageAura.Ø = 3.5;
        RageAura.á = true;
        RageAura.ˆÏ­ = false;
        RageAura.£á = false;
    }
    
    public RageAura() {
        this.Ó = false;
        this.áŒŠÆ = true;
        this.áˆºÑ¢Õ = true;
        this.ÂµÈ = true;
        this.Å = null;
        this.£à = new ArrayList<EntityLivingBase>();
        this.µà = new ArrayList<EntityLivingBase>();
        this.ˆà = new TimeHelper();
        this.¥Æ = new TimeHelper();
        this.Ø­à = null;
        this.µÕ = new Random();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.µà.clear();
        this.£à.clear();
    }
    
    @Override
    public void á() {
        this.µà.clear();
        this.£à.clear();
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate event) {
        if (!this.áˆºÑ¢Õ()) {
            return;
        }
        if (event.Ý() != EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            return;
        }
        if (this.Å == null || !this.Â(this.Å)) {
            double shouldLook = Double.NEGATIVE_INFINITY;
            EntityLivingBase highestWeightedTarget = null;
            this.µà.clear();
            this.£à.clear();
            for (final Object semiMultiWeight : this.Â.áŒŠÆ.Â) {
                if (semiMultiWeight instanceof EntityLivingBase) {
                    final EntityLivingBase el = (EntityLivingBase)semiMultiWeight;
                    if (!this.Â(el)) {
                        continue;
                    }
                    this.µà.add(el);
                }
            }
            for (final EntityLivingBase var13 : this.µà) {
                if (this.Ý(var13) && this.Ø­áŒŠá(var13) > shouldLook) {
                    shouldLook = this.Ø­áŒŠá(var13);
                    highestWeightedTarget = var13;
                }
            }
            if (highestWeightedTarget != null) {
                this.Å = highestWeightedTarget;
                for (int var14 = 0; var14 < 2; ++var14) {
                    double var15 = Double.NEGATIVE_INFINITY;
                    EntityLivingBase smHighestWeightedTarget = null;
                    for (final EntityLivingBase el2 : this.µà) {
                        if (this.Ý(el2) && !this.£à.contains(el2) && this.Ø­áŒŠá(el2) > var15) {
                            var15 = this.Ø­áŒŠá(el2);
                            smHighestWeightedTarget = el2;
                        }
                    }
                    this.£à.add(smHighestWeightedTarget);
                }
            }
        }
        if (!this.Â(this.Å)) {
            this.Å = null;
        }
        if (this.Å != null) {
            final float[] values = { this.HorizonCode_Horizon_È((Entity)this.Å) + this.Â.á.É, this.Â((Entity)this.Å) + this.Â.á.áƒ };
            event.HorizonCode_Horizon_È(values[0]);
            event.Â(values[1]);
        }
    }
    
    @Handler
    public void Â(final EventUpdate evt) {
        if (!this.áˆºÑ¢Õ()) {
            return;
        }
        if (evt.Ý() != EventUpdate.HorizonCode_Horizon_È.Ý) {
            return;
        }
        if (this.Å != null && this.Ý(this.Å)) {
            if (RageAura.£á) {
                this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ý = EntityHelper.HorizonCode_Horizon_È(this.Å);
            }
            if (this.Â.á.£Ø­à()) {
                final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                if (!ModuleManager.HorizonCode_Horizon_È(NoSlow.class).áˆºÑ¢Õ()) {
                    this.Â.µÕ().HorizonCode_Horizon_È(new C07PacketPlayerDigging(C07PacketPlayerDigging.HorizonCode_Horizon_È.Ó, BlockPos.HorizonCode_Horizon_È, EnumFacing.HorizonCode_Horizon_È));
                }
            }
            if (RageAura.á) {
                this.Â.á.b_();
            }
            else {
                this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0APacketAnimation());
            }
            this.Â.Âµá€.HorizonCode_Horizon_È(this.Â.á, this.Å);
            for (int i = 0; i < 50; ++i) {
                if (!this.áˆºÑ¢Õ()) {
                    return;
                }
                this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0APacketAnimation());
                this.Â.µÕ().HorizonCode_Horizon_È(new C02PacketUseEntity(this.Å, C02PacketUseEntity.HorizonCode_Horizon_È.Â));
                this.Â.á.b_();
                this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0APacketAnimation());
            }
            if (this.Â.á.£Ø­à()) {
                final ModuleManager áˆºÏ2 = Horizon.à¢.áˆºÏ;
                if (!ModuleManager.HorizonCode_Horizon_È(NoSlow.class).áˆºÑ¢Õ()) {
                    this.Â.µÕ().HorizonCode_Horizon_È(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá(), 0.0f, 0.0f, 0.0f));
                }
            }
            this.Ø­à = this.Å;
            this.ˆà.Ø­áŒŠá();
        }
        else if (RageAura.ˆÏ­ && this.Â.á.áŒŠá() != null && this.Â.á.áŒŠá().HorizonCode_Horizon_È() instanceof ItemSword) {
            ((ItemSword)this.Â.á.áŒŠá().HorizonCode_Horizon_È()).HorizonCode_Horizon_È(this.Â.á.áŒŠá(), this.Â.áŒŠÆ, this.Â.á);
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketSend event) {
        if (!this.áˆºÑ¢Õ()) {
            return;
        }
        if (event.Ý() instanceof C03PacketPlayer) {
            final C03PacketPlayer packet = (C03PacketPlayer)event.Ý();
            if (packet.Ø()) {
                this.Æ = packet.Ø­áŒŠá();
                this.Šáƒ = packet.Âµá€();
            }
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventRender3D _event) {
        if (!this.áˆºÑ¢Õ()) {
            return;
        }
        final double d = this.Å.áˆºáˆºÈ + (this.Å.ŒÏ - this.Å.áˆºáˆºÈ) * this.Â.Ø.Ý;
        final double d2 = this.Å.ÇŽá€ + (this.Å.Çªà¢ - this.Å.ÇŽá€) * this.Â.Ø.Ý;
        final double d3 = this.Å.Ï + (this.Å.Ê - this.Å.Ï) * this.Â.Ø.Ý;
        final double n = d;
        this.Â.ÇªÓ();
        final double d4 = n - RenderManager.HorizonCode_Horizon_È;
        final double n2 = d2;
        this.Â.ÇªÓ();
        final double d5 = n2 - RenderManager.Â;
        final double n3 = d3;
        this.Â.ÇªÓ();
        this.HorizonCode_Horizon_È(d4, d5, n3 - RenderManager.Ý, this.Å, this.Å.£ÂµÄ - 0.1, this.Å.áŒŠ - 0.12);
    }
    
    public boolean HorizonCode_Horizon_È(final EntityLivingBase el) {
        boolean mobChecks = false;
        final boolean playerChecks = el instanceof EntityPlayer;
        if (el instanceof EntityMob) {
            mobChecks = true;
        }
        else if (el instanceof EntityWolf) {
            final EntityWolf teamChecks = (EntityWolf)el;
            mobChecks = teamChecks.Ø­È();
        }
        else if (el instanceof EntitySlime) {
            mobChecks = true;
        }
        final boolean animalChecks = el instanceof EntityAnimal;
        boolean var13 = false;
        ChatColor myCol = null;
        ChatColor enemyCol = null;
        if (el instanceof EntityPlayer) {
            for (final ChatColor col : ChatColor.values()) {
                if (col == ChatColor.áˆºÑ¢Õ || col == ChatColor.ˆÏ­) {
                    if (this.Â.á.Ý().áŒŠÆ().contains(col.toString()) && myCol == null) {
                        myCol = col;
                    }
                    if (el.Ý().áŒŠÆ().contains(col.toString()) && enemyCol == null) {
                        enemyCol = col;
                    }
                }
            }
            try {
                if (myCol != null && enemyCol != null) {
                    var13 = (myCol != enemyCol);
                }
                else if (this.Â.á.Çªáˆºá() != null) {
                    var13 = !this.Â.á.Ø­áŒŠá(el);
                }
                else if (this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Â[3].HorizonCode_Horizon_È() instanceof ItemBlock) {
                    var13 = !ItemStack.Â(this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Â[3], ((EntityPlayer)el).Ø­Ñ¢Ï­Ø­áˆº.Â[3]);
                }
            }
            catch (Exception ex) {}
        }
        return playerChecks ? (this.Ó ? var13 : RageAura.Ø­áŒŠá) : (mobChecks ? RageAura.Ý : (animalChecks && RageAura.Âµá€));
    }
    
    public boolean Â(final EntityLivingBase p) {
        return !this.Â.á.equals(p) && this.Ý((Entity)p) < RageAura.Ø * RageAura.Ø && !FriendManager.HorizonCode_Horizon_È.containsKey(p.v_()) && p.Œ() && !this.Âµá€(p) && (!(p instanceof EntityPlayer) || p.Œ > 40 || !this.ÂµÈ) && this.HorizonCode_Horizon_È(p) && !p.Ï­Ó();
    }
    
    private double Ý(final Entity e) {
        if (e instanceof EntityPlayer) {
            final EntityPlayer p = (EntityPlayer)e;
            final Location loc = this.HorizonCode_Horizon_È(p, this.Â.µÕ().HorizonCode_Horizon_È(this.Â.á.£áŒŠá()).Ý());
            if (loc != null) {
                return Math.min(this.Â.á.Âµá€(loc.HorizonCode_Horizon_È, loc.Â, loc.Ý), this.Â.á.Âµá€(e));
            }
        }
        return this.Â.á.Âµá€(e);
    }
    
    public boolean Ý(final EntityLivingBase p) {
        final double adjRange = this.Â.á.ÇªÂµÕ() ? (RageAura.Ø - 0.2) : RageAura.Ø;
        final float distYaw = this.HorizonCode_Horizon_È((Entity)p);
        final float distPitch = this.Â((Entity)p);
        final float distCombined = distYaw + distPitch;
        double maxAngle = 90.0;
        if (this.Æ != this.Â.á.É || this.Šáƒ != this.Â.á.áƒ) {
            maxAngle = 45.0;
        }
        boolean angleCheck = distCombined < maxAngle;
        angleCheck = (angleCheck || this.¥Æ.Â((this.µà.size() <= 5) ? 30L : 60L));
        if (angleCheck) {
            this.¥Æ.Ø­áŒŠá();
        }
        return this.Â(p) && this.Â.á.Âµá€(p) < adjRange * adjRange && (p != this.Ø­à || this.µà.size() <= 1 || this.ˆà.Â(200L)) && p.µà <= 4 && (angleCheck || this.µà.size() <= 1 || !this.áŒŠÆ);
    }
    
    public double Ø­áŒŠá(final EntityLivingBase el) {
        double weight = el.ÇŽÊ() - (el.Ï­Ä() + el.Ñ¢È() + el.áŒŠÉ() * 5);
        weight -= this.Â.á.Âµá€(el) / 2.0;
        if (el instanceof EntityPlayer) {
            weight += 50.0;
        }
        if (el instanceof EntityCreeper) {
            weight += 35.0;
        }
        else if (el instanceof EntitySkeleton) {
            weight += 25.0;
        }
        final float distYaw = this.HorizonCode_Horizon_È((Entity)el);
        final float distPitch = this.Â((Entity)el);
        final float distCombined = distYaw + distPitch;
        if (this.áŒŠÆ) {
            weight -= distCombined;
        }
        return weight;
    }
    
    public float HorizonCode_Horizon_È(final float a, final float b) {
        final float d = Math.abs(a - b) % 360.0f;
        final float r = (d > 180.0f) ? (360.0f - d) : d;
        return r;
    }
    
    public int HorizonCode_Horizon_È(final int min, final int max) {
        return this.µÕ.nextInt(max - min + 1) + max;
    }
    
    public void HorizonCode_Horizon_È(final double d, final double d1, final double d2, final EntityLivingBase ep, final double e, final double f) {
        if (!(ep instanceof EntityPlayerSP) && !ep.ˆáŠ) {
            GL11.glPushMatrix();
            GLUtil.HorizonCode_Horizon_È(3042, true);
            GLUtil.HorizonCode_Horizon_È(3553, false);
            GLUtil.HorizonCode_Horizon_È(2896, false);
            GLUtil.HorizonCode_Horizon_È(2929, false);
            GL11.glDepthMask(false);
            GL11.glLineWidth(1.8f);
            GL11.glBlendFunc(770, 771);
            GLUtil.HorizonCode_Horizon_È(2848, true);
            GL11.glColor4f(1.7f, 1.0f, 1.0f, 0.5f);
            RenderHelper_1118140819.Â(new AxisAlignedBB(d - f, d1 + e + 0.2, d2 - f, d + f, d1 + e + 0.25, d2 + f));
            GL11.glColor4f(0.7f, 0.7f, 0.7f, 0.9f);
            RenderHelper_1118140819.Ø­áŒŠá(new AxisAlignedBB(d - f, d1 + e + 0.2, d2 - f, d + f, d1 + e + 0.25, d2 + f));
            GL11.glDepthMask(true);
            GLUtil.HorizonCode_Horizon_È();
            GL11.glPopMatrix();
        }
    }
    
    public boolean Âµá€(final EntityLivingBase el) {
        if (el instanceof EntityPlayer) {
            final EntityPlayer p = (EntityPlayer)el;
            boolean hasArmour = false;
            for (final ItemStack stack : p.Ø­Ñ¢Ï­Ø­áˆº.Â) {
                if (stack != null) {
                    hasArmour = true;
                }
            }
            return el.áŒŠÏ() && el.Çª() == null && !hasArmour;
        }
        return el.áŒŠÏ() && el.Çª() == null;
    }
    
    public float HorizonCode_Horizon_È(final Entity entity) {
        final double deltaX = entity.ŒÏ - this.Â.á.ŒÏ;
        final double deltaZ = entity.Ê - this.Â.á.Ê;
        double yawToEntity;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.à(-(this.Â.á.É - (float)yawToEntity));
    }
    
    public float Â(final Entity entity) {
        final double deltaX = entity.ŒÏ - this.Â.á.ŒÏ;
        final double deltaZ = entity.Ê - this.Â.á.Ê;
        final double deltaY = entity.Çªà¢ - 1.6 + entity.Ðƒáƒ() - this.Â.á.Çªà¢;
        final double distanceXZ = MathHelper.HorizonCode_Horizon_È(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.à(this.Â.á.áƒ - (float)pitchToEntity);
    }
    
    public static void HorizonCode_Horizon_È(final AxisAlignedBB axisalignedbb) {
        final Tessellator tessellator = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer worldrender = Tessellator.HorizonCode_Horizon_È().Ý();
        worldrender.Â();
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.HorizonCode_Horizon_È, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ý);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Âµá€, axisalignedbb.Ó);
        worldrender.Â(axisalignedbb.Ø­áŒŠá, axisalignedbb.Â, axisalignedbb.Ó);
        tessellator.Â();
    }
    
    public Location HorizonCode_Horizon_È(final Entity e, final double milliseconds) {
        if (e == null) {
            return null;
        }
        if (e.ŒÏ == e.áˆºáˆºÈ && e.Çªà¢ == e.ÇŽá€ && e.Ê == e.Ï) {
            return new Location(e.ŒÏ, e.Çªà¢, e.Ê);
        }
        double ticks = milliseconds / 1000.0;
        ticks *= 20.0;
        return this.HorizonCode_Horizon_È(new Location(e.áˆºáˆºÈ, e.ÇŽá€, e.Ï), new Location(e.ŒÏ + e.ÇŽÉ, e.Çªà¢ + e.ˆá, e.Ê + e.ÇŽÕ), ticks);
    }
    
    public Location HorizonCode_Horizon_È(final Location from, final Location to, final double pct) {
        final double x = from.HorizonCode_Horizon_È + (to.HorizonCode_Horizon_È - from.HorizonCode_Horizon_È) * pct;
        final double y = from.Â + (to.Â - from.Â) * pct;
        final double z = from.Ý + (to.Ý - from.Ý) * pct;
        return new Location(x, y, z);
    }
}
