package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Map;

public class ItemModelMesher
{
    private final Map HorizonCode_Horizon_È;
    private final Map Â;
    private final Map Ý;
    private final ModelManager Ø­áŒŠá;
    private static final String Âµá€ = "CL_00002536";
    
    public ItemModelMesher(final ModelManager p_i46250_1_) {
        this.HorizonCode_Horizon_È = Maps.newHashMap();
        this.Â = Maps.newHashMap();
        this.Ý = Maps.newHashMap();
        this.Ø­áŒŠá = p_i46250_1_;
    }
    
    public TextureAtlasSprite HorizonCode_Horizon_È(final Item_1028566121 p_178082_1_) {
        return this.HorizonCode_Horizon_È(p_178082_1_, 0);
    }
    
    public TextureAtlasSprite HorizonCode_Horizon_È(final Item_1028566121 p_178087_1_, final int p_178087_2_) {
        return this.HorizonCode_Horizon_È(new ItemStack(p_178087_1_, 1, p_178087_2_)).Âµá€();
    }
    
    public IBakedModel HorizonCode_Horizon_È(final ItemStack p_178089_1_) {
        final Item_1028566121 var2 = p_178089_1_.HorizonCode_Horizon_È();
        IBakedModel var3 = this.Â(var2, this.Â(p_178089_1_));
        if (var3 == null) {
            final ItemMeshDefinition var4 = this.Ý.get(var2);
            if (var4 != null) {
                var3 = this.Ø­áŒŠá.HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È(p_178089_1_));
            }
        }
        if (var3 == null) {
            var3 = this.Ø­áŒŠá.HorizonCode_Horizon_È();
        }
        return var3;
    }
    
    protected int Â(final ItemStack p_178084_1_) {
        return p_178084_1_.Ø­áŒŠá() ? 0 : p_178084_1_.Ø();
    }
    
    protected IBakedModel Â(final Item_1028566121 p_178088_1_, final int p_178088_2_) {
        return this.Â.get(this.Ý(p_178088_1_, p_178088_2_));
    }
    
    private int Ý(final Item_1028566121 p_178081_1_, final int p_178081_2_) {
        return Item_1028566121.HorizonCode_Horizon_È(p_178081_1_) << 16 | p_178081_2_;
    }
    
    public void HorizonCode_Horizon_È(final Item_1028566121 p_178086_1_, final int p_178086_2_, final ModelResourceLocation p_178086_3_) {
        this.HorizonCode_Horizon_È.put(this.Ý(p_178086_1_, p_178086_2_), p_178086_3_);
        this.Â.put(this.Ý(p_178086_1_, p_178086_2_), this.Ø­áŒŠá.HorizonCode_Horizon_È(p_178086_3_));
    }
    
    public void HorizonCode_Horizon_È(final Item_1028566121 p_178080_1_, final ItemMeshDefinition p_178080_2_) {
        this.Ý.put(p_178080_1_, p_178080_2_);
    }
    
    public ModelManager HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public void Â() {
        this.Â.clear();
        for (final Map.Entry var2 : this.HorizonCode_Horizon_È.entrySet()) {
            this.Â.put(var2.getKey(), this.Ø­áŒŠá.HorizonCode_Horizon_È(var2.getValue()));
        }
    }
}
