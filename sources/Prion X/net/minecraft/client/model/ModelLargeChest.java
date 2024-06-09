package net.minecraft.client.model;

public class ModelLargeChest extends ModelChest
{
  private static final String __OBFID = "CL_00000841";
  
  public ModelLargeChest()
  {
    chestLid = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
    chestLid.addBox(0.0F, -5.0F, -14.0F, 30, 5, 14, 0.0F);
    chestLid.rotationPointX = 1.0F;
    chestLid.rotationPointY = 7.0F;
    chestLid.rotationPointZ = 15.0F;
    chestKnob = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
    chestKnob.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
    chestKnob.rotationPointX = 16.0F;
    chestKnob.rotationPointY = 7.0F;
    chestKnob.rotationPointZ = 15.0F;
    chestBelow = new ModelRenderer(this, 0, 19).setTextureSize(128, 64);
    chestBelow.addBox(0.0F, 0.0F, 0.0F, 30, 10, 14, 0.0F);
    chestBelow.rotationPointX = 1.0F;
    chestBelow.rotationPointY = 6.0F;
    chestBelow.rotationPointZ = 1.0F;
  }
}
