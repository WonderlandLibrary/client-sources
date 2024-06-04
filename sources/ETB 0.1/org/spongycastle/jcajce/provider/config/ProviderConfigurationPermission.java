package org.spongycastle.jcajce.provider.config;

import java.security.BasicPermission;
import java.security.Permission;
import java.util.StringTokenizer;
import org.spongycastle.util.Strings;

























public class ProviderConfigurationPermission
  extends BasicPermission
{
  private static final int THREAD_LOCAL_EC_IMPLICITLY_CA = 1;
  private static final int EC_IMPLICITLY_CA = 2;
  private static final int THREAD_LOCAL_DH_DEFAULT_PARAMS = 4;
  private static final int DH_DEFAULT_PARAMS = 8;
  private static final int ACCEPTABLE_EC_CURVES = 16;
  private static final int ADDITIONAL_EC_PARAMETERS = 32;
  private static final int ALL = 63;
  private static final String THREAD_LOCAL_EC_IMPLICITLY_CA_STR = "threadlocalecimplicitlyca";
  private static final String EC_IMPLICITLY_CA_STR = "ecimplicitlyca";
  private static final String THREAD_LOCAL_DH_DEFAULT_PARAMS_STR = "threadlocaldhdefaultparams";
  private static final String DH_DEFAULT_PARAMS_STR = "dhdefaultparams";
  private static final String ACCEPTABLE_EC_CURVES_STR = "acceptableeccurves";
  private static final String ADDITIONAL_EC_PARAMETERS_STR = "additionalecparameters";
  private static final String ALL_STR = "all";
  private final String actions;
  private final int permissionMask;
  
  public ProviderConfigurationPermission(String name)
  {
    super(name);
    actions = "all";
    permissionMask = 63;
  }
  
  public ProviderConfigurationPermission(String name, String actions)
  {
    super(name, actions);
    this.actions = actions;
    permissionMask = calculateMask(actions);
  }
  

  private int calculateMask(String actions)
  {
    StringTokenizer tok = new StringTokenizer(Strings.toLowerCase(actions), " ,");
    int mask = 0;
    
    while (tok.hasMoreTokens())
    {
      String s = tok.nextToken();
      
      if (s.equals("threadlocalecimplicitlyca"))
      {
        mask |= 0x1;
      }
      else if (s.equals("ecimplicitlyca"))
      {
        mask |= 0x2;
      }
      else if (s.equals("threadlocaldhdefaultparams"))
      {
        mask |= 0x4;
      }
      else if (s.equals("dhdefaultparams"))
      {
        mask |= 0x8;
      }
      else if (s.equals("acceptableeccurves"))
      {
        mask |= 0x10;
      }
      else if (s.equals("additionalecparameters"))
      {
        mask |= 0x20;
      }
      else if (s.equals("all"))
      {
        mask |= 0x3F;
      }
    }
    
    if (mask == 0)
    {
      throw new IllegalArgumentException("unknown permissions passed to mask");
    }
    
    return mask;
  }
  
  public String getActions()
  {
    return actions;
  }
  

  public boolean implies(Permission permission)
  {
    if (!(permission instanceof ProviderConfigurationPermission))
    {
      return false;
    }
    
    if (!getName().equals(permission.getName()))
    {
      return false;
    }
    
    ProviderConfigurationPermission other = (ProviderConfigurationPermission)permission;
    
    return (permissionMask & permissionMask) == permissionMask;
  }
  

  public boolean equals(Object obj)
  {
    if (obj == this)
    {
      return true;
    }
    
    if ((obj instanceof ProviderConfigurationPermission))
    {
      ProviderConfigurationPermission other = (ProviderConfigurationPermission)obj;
      
      return (permissionMask == permissionMask) && (getName().equals(other.getName()));
    }
    
    return false;
  }
  
  public int hashCode()
  {
    return getName().hashCode() + permissionMask;
  }
}
