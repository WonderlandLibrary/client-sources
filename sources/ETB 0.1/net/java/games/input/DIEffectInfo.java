package net.java.games.input;






final class DIEffectInfo
{
  private final IDirectInputDevice device;
  




  private final byte[] guid;
  




  private final int guid_id;
  




  private final int effect_type;
  




  private final int static_params;
  




  private final int dynamic_params;
  




  private final String name;
  





  public DIEffectInfo(IDirectInputDevice device, byte[] guid, int guid_id, int effect_type, int static_params, int dynamic_params, String name)
  {
    this.device = device;
    this.guid = guid;
    this.guid_id = guid_id;
    this.effect_type = effect_type;
    this.static_params = static_params;
    this.dynamic_params = dynamic_params;
    this.name = name;
  }
  
  public final byte[] getGUID() {
    return guid;
  }
  
  public final int getGUIDId() {
    return guid_id;
  }
  
  public final int getDynamicParams() {
    return dynamic_params;
  }
  
  public final int getEffectType() {
    return effect_type;
  }
  
  public final String getName() {
    return name;
  }
}
