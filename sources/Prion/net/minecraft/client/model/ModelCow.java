package net.minecraft.client.model;

public class ModelCow extends ModelQuadruped
{
  private static final String __OBFID = "CL_00000836";
  
  public ModelCow()
  {
    super(12, 0.0F);
    head = new ModelRenderer(this, 0, 0);
    head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
    head.setRotationPoint(0.0F, 4.0F, -8.0F);
    head.setTextureOffset(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
    head.setTextureOffset(22, 0).addBox(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
    body = new ModelRenderer(this, 18, 4);
    body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
    body.setRotationPoint(0.0F, 5.0F, 2.0F);
    body.setTextureOffset(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1);
    leg1.rotationPointX -= 1.0F;
    leg2.rotationPointX += 1.0F;
    leg1.rotationPointZ += 0.0F;
    leg2.rotationPointZ += 0.0F;
    leg3.rotationPointX -= 1.0F;
    leg4.rotationPointX += 1.0F;
    leg3.rotationPointZ -= 1.0F;
    leg4.rotationPointZ -= 1.0F;
    childZOffset += 2.0F;
  }
}
