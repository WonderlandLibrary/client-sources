package net.minecraft.client.model;

public class ModelSign
  extends ModelBase
{
  public ModelRenderer signBoard = new ModelRenderer(this, 0, 0);
  
  public ModelRenderer signStick;
  
  private static final String __OBFID = "CL_00000854";
  
  public ModelSign()
  {
    signBoard.addBox(-12.0F, -14.0F, -1.0F, 24, 12, 2, 0.0F);
    signStick = new ModelRenderer(this, 0, 14);
    signStick.addBox(-1.0F, -2.0F, -1.0F, 2, 14, 2, 0.0F);
  }
  



  public void renderSign()
  {
    signBoard.render(0.0625F);
    signStick.render(0.0625F);
  }
}
