package net.minecraft.entity.ai.attributes;

public abstract interface IAttribute
{
  public abstract String getAttributeUnlocalizedName();
  
  public abstract double clampValue(double paramDouble);
  
  public abstract double getDefaultValue();
  
  public abstract boolean getShouldWatch();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.attributes.IAttribute
 * JD-Core Version:    0.7.0.1
 */