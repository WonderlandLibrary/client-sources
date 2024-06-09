package winter.utils.zeb.util.shader;

public enum ShaderType
{
  VERTEX(
    35633),  FRAGMENT(
    35632);
  
  private int typeId;
  
  private ShaderType(int typeId)
  {
    this.typeId = typeId;
  }
  
  public int getTypeId()
  {
    return this.typeId;
  }
}
