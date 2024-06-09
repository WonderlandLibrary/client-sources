package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;

public class LayerBipedArmor extends LayerArmorBase
{
  private static final String __OBFID = "CL_00002417";
  
  public LayerBipedArmor(RendererLivingEntity p_i46116_1_)
  {
    super(p_i46116_1_);
  }
  
  protected void func_177177_a()
  {
    field_177189_c = new ModelBiped(0.5F);
    field_177186_d = new ModelBiped(1.0F);
  }
  
  protected void func_177195_a(ModelBiped p_177195_1_, int p_177195_2_)
  {
    func_177194_a(p_177195_1_);
    
    switch (p_177195_2_)
    {
    case 1: 
      bipedRightLeg.showModel = true;
      bipedLeftLeg.showModel = true;
      break;
    
    case 2: 
      bipedBody.showModel = true;
      bipedRightLeg.showModel = true;
      bipedLeftLeg.showModel = true;
      break;
    
    case 3: 
      bipedBody.showModel = true;
      bipedRightArm.showModel = true;
      bipedLeftArm.showModel = true;
      break;
    
    case 4: 
      bipedHead.showModel = true;
      bipedHeadwear.showModel = true;
    }
  }
  
  protected void func_177194_a(ModelBiped p_177194_1_)
  {
    p_177194_1_.func_178719_a(false);
  }
  
  protected void func_177179_a(ModelBase p_177179_1_, int p_177179_2_)
  {
    func_177195_a((ModelBiped)p_177179_1_, p_177179_2_);
  }
}
