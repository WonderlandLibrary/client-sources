package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class EntitySilverfish extends EntityMob
{
    private Â Â;
    private static final String Ý = "CL_00001696";
    
    public EntitySilverfish(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.4f, 0.3f);
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(3, this.Â = new Â());
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new HorizonCode_Horizon_È());
        this.á.HorizonCode_Horizon_È(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.á.HorizonCode_Horizon_È(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    @Override
    public float Ðƒáƒ() {
        return 0.1f;
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(8.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.25);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(1.0);
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.silverfish.say";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.silverfish.hit";
    }
    
    @Override
    protected String µÊ() {
        return "mob.silverfish.kill";
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (source instanceof EntityDamageSource || source == DamageSource.á) {
            this.Â.Ø();
        }
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.HorizonCode_Horizon_È("mob.silverfish.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return null;
    }
    
    @Override
    public void á() {
        this.¥É = this.É;
        super.á();
    }
    
    @Override
    public float HorizonCode_Horizon_È(final BlockPos p_180484_1_) {
        return (this.Ï­Ðƒà.Â(p_180484_1_.Âµá€()).Ý() == Blocks.Ý) ? 10.0f : super.HorizonCode_Horizon_È(p_180484_1_);
    }
    
    @Override
    protected boolean w_() {
        return true;
    }
    
    @Override
    public boolean µà() {
        if (super.µà()) {
            final EntityPlayer var1 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this, 5.0);
            return var1 == null;
        }
        return false;
    }
    
    @Override
    public EnumCreatureAttribute ¥áŒŠà() {
        return EnumCreatureAttribute.Ý;
    }
    
    class HorizonCode_Horizon_È extends EntityAIWander
    {
        private EnumFacing Â;
        private boolean Ý;
        private static final String Ø­áŒŠá = "CL_00002205";
        
        public HorizonCode_Horizon_È() {
            super(EntitySilverfish.this, 1.0, 10);
            this.HorizonCode_Horizon_È(1);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            if (EntitySilverfish.this.Ñ¢Ó() != null) {
                return false;
            }
            if (!EntitySilverfish.this.Š().Ó()) {
                return false;
            }
            final Random var1 = EntitySilverfish.this.ˆÐƒØ();
            if (var1.nextInt(10) == 0) {
                this.Â = EnumFacing.HorizonCode_Horizon_È(var1);
                final BlockPos var2 = new BlockPos(EntitySilverfish.this.ŒÏ, EntitySilverfish.this.Çªà¢ + 0.5, EntitySilverfish.this.Ê).HorizonCode_Horizon_È(this.Â);
                final IBlockState var3 = EntitySilverfish.this.Ï­Ðƒà.Â(var2);
                if (BlockSilverfish.áŒŠÆ(var3)) {
                    return this.Ý = true;
                }
            }
            this.Ý = false;
            return super.HorizonCode_Horizon_È();
        }
        
        @Override
        public boolean Â() {
            return !this.Ý && super.Â();
        }
        
        @Override
        public void Âµá€() {
            if (!this.Ý) {
                super.Âµá€();
            }
            else {
                final World var1 = EntitySilverfish.this.Ï­Ðƒà;
                final BlockPos var2 = new BlockPos(EntitySilverfish.this.ŒÏ, EntitySilverfish.this.Çªà¢ + 0.5, EntitySilverfish.this.Ê).HorizonCode_Horizon_È(this.Â);
                final IBlockState var3 = var1.Â(var2);
                if (BlockSilverfish.áŒŠÆ(var3)) {
                    var1.HorizonCode_Horizon_È(var2, Blocks.ÐƒÂ.¥à().HorizonCode_Horizon_È(BlockSilverfish.Õ, BlockSilverfish.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var3)), 3);
                    EntitySilverfish.this.£Ø­à();
                    EntitySilverfish.this.á€();
                }
            }
        }
    }
    
    class Â extends EntityAIBase
    {
        private EntitySilverfish Â;
        private int Ý;
        private static final String Ø­áŒŠá = "CL_00002204";
        
        Â() {
            this.Â = EntitySilverfish.this;
        }
        
        public void Ø() {
            if (this.Ý == 0) {
                this.Ý = 20;
            }
        }
        
        @Override
        public boolean HorizonCode_Horizon_È() {
            return this.Ý > 0;
        }
        
        @Override
        public void Ø­áŒŠá() {
            --this.Ý;
            if (this.Ý <= 0) {
                final World var1 = this.Â.Ï­Ðƒà;
                final Random var2 = this.Â.ˆÐƒØ();
                final BlockPos var3 = new BlockPos(this.Â);
                for (int var4 = 0; var4 <= 5 && var4 >= -5; var4 = ((var4 <= 0) ? (1 - var4) : (0 - var4))) {
                    for (int var5 = 0; var5 <= 10 && var5 >= -10; var5 = ((var5 <= 0) ? (1 - var5) : (0 - var5))) {
                        for (int var6 = 0; var6 <= 10 && var6 >= -10; var6 = ((var6 <= 0) ? (1 - var6) : (0 - var6))) {
                            final BlockPos var7 = var3.Â(var5, var4, var6);
                            final IBlockState var8 = var1.Â(var7);
                            if (var8.Ý() == Blocks.ÐƒÂ) {
                                if (var1.Çªà¢().Â("mobGriefing")) {
                                    var1.Â(var7, true);
                                }
                                else {
                                    var1.HorizonCode_Horizon_È(var7, ((BlockSilverfish.HorizonCode_Horizon_È)var8.HorizonCode_Horizon_È(BlockSilverfish.Õ)).Ø­áŒŠá(), 3);
                                }
                                if (var2.nextBoolean()) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
