package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.RegistrySimple;

public class SoundRegistry
  extends RegistrySimple
{
  private Map field_148764_a;
  private static final String __OBFID = "CL_00001151";
  
  public SoundRegistry() {}
  
  protected Map createUnderlyingMap()
  {
    field_148764_a = Maps.newHashMap();
    return field_148764_a;
  }
  
  public void registerSound(SoundEventAccessorComposite p_148762_1_)
  {
    putObject(p_148762_1_.getSoundEventLocation(), p_148762_1_);
  }
  



  public void clearMap()
  {
    field_148764_a.clear();
  }
}
