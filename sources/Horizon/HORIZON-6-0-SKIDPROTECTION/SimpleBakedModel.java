package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public class SimpleBakedModel implements IBakedModel
{
    protected final List HorizonCode_Horizon_È;
    protected final List Â;
    protected final boolean Ý;
    protected final boolean Ø­áŒŠá;
    protected final TextureAtlasSprite Âµá€;
    protected final ItemCameraTransforms Ó;
    private static final String à = "CL_00002386";
    
    public SimpleBakedModel(final List p_i46077_1_, final List p_i46077_2_, final boolean p_i46077_3_, final boolean p_i46077_4_, final TextureAtlasSprite p_i46077_5_, final ItemCameraTransforms p_i46077_6_) {
        this.HorizonCode_Horizon_È = p_i46077_1_;
        this.Â = p_i46077_2_;
        this.Ý = p_i46077_3_;
        this.Ø­áŒŠá = p_i46077_4_;
        this.Âµá€ = p_i46077_5_;
        this.Ó = p_i46077_6_;
    }
    
    @Override
    public List HorizonCode_Horizon_È(final EnumFacing p_177551_1_) {
        return this.Â.get(p_177551_1_.ordinal());
    }
    
    @Override
    public List HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public boolean Â() {
        return this.Ý;
    }
    
    @Override
    public boolean Ý() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return false;
    }
    
    @Override
    public TextureAtlasSprite Âµá€() {
        return this.Âµá€;
    }
    
    @Override
    public ItemCameraTransforms Ó() {
        return this.Ó;
    }
    
    public static class HorizonCode_Horizon_È
    {
        private final List HorizonCode_Horizon_È;
        private final List Â;
        private final boolean Ý;
        private TextureAtlasSprite Ø­áŒŠá;
        private boolean Âµá€;
        private ItemCameraTransforms Ó;
        private static final String à = "CL_00002385";
        
        public HorizonCode_Horizon_È(final ModelBlock p_i46074_1_) {
            this(p_i46074_1_.Â(), p_i46074_1_.Ý(), new ItemCameraTransforms(p_i46074_1_.à(), p_i46074_1_.Ø(), p_i46074_1_.áŒŠÆ(), p_i46074_1_.áˆºÑ¢Õ()));
        }
        
        public HorizonCode_Horizon_È(final IBakedModel p_i46075_1_, final TextureAtlasSprite p_i46075_2_) {
            this(p_i46075_1_.Â(), p_i46075_1_.Ý(), p_i46075_1_.Ó());
            this.Ø­áŒŠá = p_i46075_1_.Âµá€();
            for (final EnumFacing var6 : EnumFacing.values()) {
                this.HorizonCode_Horizon_È(p_i46075_1_, p_i46075_2_, var6);
            }
            this.HorizonCode_Horizon_È(p_i46075_1_, p_i46075_2_);
        }
        
        private void HorizonCode_Horizon_È(final IBakedModel p_177649_1_, final TextureAtlasSprite p_177649_2_, final EnumFacing p_177649_3_) {
            for (final BakedQuad var5 : p_177649_1_.HorizonCode_Horizon_È(p_177649_3_)) {
                this.HorizonCode_Horizon_È(p_177649_3_, new BreakingFour(var5, p_177649_2_));
            }
        }
        
        private void HorizonCode_Horizon_È(final IBakedModel p_177647_1_, final TextureAtlasSprite p_177647_2_) {
            for (final BakedQuad var4 : p_177647_1_.HorizonCode_Horizon_È()) {
                this.HorizonCode_Horizon_È(new BreakingFour(var4, p_177647_2_));
            }
        }
        
        private HorizonCode_Horizon_È(final boolean p_i46076_1_, final boolean p_i46076_2_, final ItemCameraTransforms p_i46076_3_) {
            this.HorizonCode_Horizon_È = Lists.newArrayList();
            this.Â = Lists.newArrayListWithCapacity(6);
            for (final EnumFacing var7 : EnumFacing.values()) {
                this.Â.add(Lists.newArrayList());
            }
            this.Ý = p_i46076_1_;
            this.Âµá€ = p_i46076_2_;
            this.Ó = p_i46076_3_;
        }
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È(final EnumFacing p_177650_1_, final BakedQuad p_177650_2_) {
            this.Â.get(p_177650_1_.ordinal()).add(p_177650_2_);
            return this;
        }
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È(final BakedQuad p_177648_1_) {
            this.HorizonCode_Horizon_È.add(p_177648_1_);
            return this;
        }
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È(final TextureAtlasSprite p_177646_1_) {
            this.Ø­áŒŠá = p_177646_1_;
            return this;
        }
        
        public IBakedModel HorizonCode_Horizon_È() {
            if (this.Ø­áŒŠá == null) {
                throw new RuntimeException("Missing particle!");
            }
            return new SimpleBakedModel(this.HorizonCode_Horizon_È, this.Â, this.Ý, this.Âµá€, this.Ø­áŒŠá, this.Ó);
        }
    }
}
