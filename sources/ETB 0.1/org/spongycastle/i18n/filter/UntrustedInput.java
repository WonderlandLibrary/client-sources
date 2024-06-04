package org.spongycastle.i18n.filter;






public class UntrustedInput
{
  protected Object input;
  




  public UntrustedInput(Object input)
  {
    this.input = input;
  }
  




  public Object getInput()
  {
    return input;
  }
  




  public String getString()
  {
    return input.toString();
  }
  
  public String toString()
  {
    return input.toString();
  }
}
