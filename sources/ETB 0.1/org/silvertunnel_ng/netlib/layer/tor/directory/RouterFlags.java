package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.io.IOException;
import java.util.BitSet;
import org.silvertunnel_ng.netlib.tool.ByteUtils;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamReader;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamWriter;




























public final class RouterFlags
{
  private static int INDEX_RUNNING = 0;
  



  private static int INDEX_AUTHORITY = 1;
  



  private static int INDEX_EXIT = 2;
  



  private static int INDEX_BAD_EXIT = 3;
  



  private static int INDEX_BAD_DIRECTORY = 4;
  


  private static int INDEX_FAST = 5;
  


  private static int INDEX_GUARD = 6;
  


  private static int INDEX_HIDDENSERVICE_DIRECTORY = 7;
  


  private static int INDEX_NAMED = 8;
  


  private static int INDEX_STABLE = 9;
  


  private static int INDEX_UNNAMED = 10;
  


  private static int INDEX_VALID = 11;
  


  private static int INDEX_V2DIR = 12;
  


  private static int INDEX_HIBERNATING = 13;
  
  private BitSet value = new BitSet(14);
  private static final String IDENTIFIER_AUTHORITY = "Authority";
  private static final String IDENTIFIER_RUNNING = "Running";
  private static final String IDENTIFIER_EXIT = "Exit";
  
  public RouterFlags()
  {
    value.set(0, 14, false);
  }
  
  private static final String IDENTIFIER_FAST = "Fast";
  private static final String IDENTIFIER_GUARD = "Guard";
  private static final String IDENTIFIER_STABLE = "Stable";
  private static final String IDENTIFIER_NAMED = "Named";
  private static final String IDENTIFIER_UNNAMED = "Unnamed";
  
  public RouterFlags(Boolean initialValue) { value.set(0, 14, initialValue.booleanValue()); }
  
  private static final String IDENTIFIER_DIRECTORY = "V2Dir";
  private static final String IDENTIFIER_VALID = "Valid";
  private static final String IDENTIFIER_HIDDENSERVICE_DIRECTORY = "HSDir";
  private static final String IDENTIFIER_HIBERNATING = "Hibernating";
  private static final String IDENTIFIER_BAD_DIRECTORY = "BadDirectory";
  private static final String IDENTIFIER_BAD_EXIT = "BadExit";
  public RouterFlags(String flags) {
    setAllFlags(flags);
  }
  




  public RouterFlags(byte[] data)
  {
    Boolean[] flags1 = ByteUtils.getBooleansFromByte(data[0]);
    value.set(INDEX_RUNNING, flags1[0].booleanValue());
    value.set(INDEX_EXIT, flags1[1].booleanValue());
    value.set(INDEX_AUTHORITY, flags1[2].booleanValue());
    value.set(INDEX_FAST, flags1[3].booleanValue());
    Boolean[] flags2 = ByteUtils.getBooleansFromByte(data[1]);
    value.set(INDEX_GUARD, flags2[0].booleanValue());
    value.set(INDEX_STABLE, flags2[1].booleanValue());
    value.set(INDEX_NAMED, flags2[2].booleanValue());
    value.set(INDEX_UNNAMED, flags2[3].booleanValue());
    Boolean[] flags3 = ByteUtils.getBooleansFromByte(data[2]);
    value.set(INDEX_V2DIR, flags3[0].booleanValue());
    value.set(INDEX_VALID, flags3[1].booleanValue());
    value.set(INDEX_HIDDENSERVICE_DIRECTORY, flags3[2].booleanValue());
    value.set(INDEX_BAD_DIRECTORY, flags3[3].booleanValue());
    Boolean[] flags4 = ByteUtils.getBooleansFromByte(data[3]);
    value.set(INDEX_BAD_EXIT, flags4[0].booleanValue());
    value.set(INDEX_HIBERNATING, flags4[1].booleanValue());
  }
  


  public RouterFlags(ConvenientStreamReader convenientStreamReader)
    throws IOException
  {
    Boolean[] flags1 = ByteUtils.getBooleansFromByte(convenientStreamReader.readByte());
    value.set(INDEX_RUNNING, flags1[0].booleanValue());
    value.set(INDEX_EXIT, flags1[1].booleanValue());
    value.set(INDEX_AUTHORITY, flags1[2].booleanValue());
    value.set(INDEX_FAST, flags1[3].booleanValue());
    Boolean[] flags2 = ByteUtils.getBooleansFromByte(convenientStreamReader.readByte());
    value.set(INDEX_GUARD, flags2[0].booleanValue());
    value.set(INDEX_STABLE, flags2[1].booleanValue());
    value.set(INDEX_NAMED, flags2[2].booleanValue());
    value.set(INDEX_UNNAMED, flags2[3].booleanValue());
    Boolean[] flags3 = ByteUtils.getBooleansFromByte(convenientStreamReader.readByte());
    value.set(INDEX_V2DIR, flags3[0].booleanValue());
    value.set(INDEX_VALID, flags3[1].booleanValue());
    value.set(INDEX_HIDDENSERVICE_DIRECTORY, flags3[2].booleanValue());
    value.set(INDEX_BAD_DIRECTORY, flags3[3].booleanValue());
    Boolean[] flags4 = ByteUtils.getBooleansFromByte(convenientStreamReader.readByte());
    value.set(INDEX_BAD_EXIT, flags4[0].booleanValue());
    value.set(INDEX_HIBERNATING, flags4[1].booleanValue());
  }
  




  public void setAllFlags(String flags)
  {
    value.set(INDEX_RUNNING, flags.contains("Running"));
    value.set(INDEX_EXIT, flags.contains("Exit"));
    value.set(INDEX_AUTHORITY, flags.contains("Authority"));
    value.set(INDEX_FAST, flags.contains("Fast"));
    value.set(INDEX_GUARD, flags.contains("Guard"));
    value.set(INDEX_STABLE, flags.contains("Stable"));
    value.set(INDEX_NAMED, flags.contains("Named"));
    value.set(INDEX_UNNAMED, flags.contains("Unnamed"));
    value.set(INDEX_V2DIR, flags.contains("V2Dir"));
    value.set(INDEX_VALID, flags.contains("Valid"));
    value.set(INDEX_HIDDENSERVICE_DIRECTORY, flags.contains("HSDir"));
    value.set(INDEX_BAD_DIRECTORY, flags.contains("BadDirectory"));
    value.set(INDEX_BAD_EXIT, flags.contains("BadExit"));
  }
  

  protected void save(ConvenientStreamWriter convenientStreamWriter)
    throws IOException
  {
    convenientStreamWriter.writeByte(ByteUtils.getByteFromBooleans(Boolean.valueOf(isRunning()), new Boolean[] { Boolean.valueOf(isExit()), Boolean.valueOf(isAuthority()), Boolean.valueOf(isFast()) }));
    convenientStreamWriter.writeByte(ByteUtils.getByteFromBooleans(Boolean.valueOf(isGuard()), new Boolean[] { Boolean.valueOf(isStable()), Boolean.valueOf(isNamed()), Boolean.valueOf(isUnnamed()) }));
    convenientStreamWriter.writeByte(ByteUtils.getByteFromBooleans(Boolean.valueOf(isV2Dir()), new Boolean[] { Boolean.valueOf(isValid()), Boolean.valueOf(isHSDir()), Boolean.valueOf(isBadDirectory()) }));
    convenientStreamWriter.writeByte(ByteUtils.getByteFromBooleans(Boolean.valueOf(isBadExit()), new Boolean[] { Boolean.valueOf(isHibernating()) }));
  }
  


  public boolean isRunning()
  {
    return value.get(INDEX_RUNNING);
  }
  


  public void setRunning(Boolean running)
  {
    value.set(INDEX_RUNNING, running.booleanValue());
  }
  


  public boolean isHibernating()
  {
    return value.get(INDEX_HIBERNATING);
  }
  


  public void setHibernating(Boolean hibernating)
  {
    value.set(INDEX_HIBERNATING, hibernating.booleanValue());
  }
  


  public boolean isAuthority()
  {
    return value.get(INDEX_AUTHORITY);
  }
  


  public void setAuthority(Boolean authority)
  {
    value.set(INDEX_AUTHORITY, authority.booleanValue());
  }
  


  public boolean isExit()
  {
    return value.get(INDEX_EXIT);
  }
  


  public void setExit(Boolean exit)
  {
    value.set(INDEX_EXIT, exit.booleanValue());
  }
  


  public boolean isBadExit()
  {
    return value.get(INDEX_BAD_EXIT);
  }
  


  public void setBadExit(Boolean badExit)
  {
    value.set(INDEX_BAD_EXIT, badExit.booleanValue());
  }
  


  public boolean isBadDirectory()
  {
    return value.get(INDEX_BAD_DIRECTORY);
  }
  


  public void setBadDirectory(Boolean badDirectory)
  {
    value.set(INDEX_BAD_DIRECTORY, badDirectory.booleanValue());
  }
  


  public boolean isFast()
  {
    return value.get(INDEX_FAST);
  }
  


  public void setFast(Boolean fast)
  {
    value.set(INDEX_FAST, fast.booleanValue());
  }
  


  public boolean isGuard()
  {
    return value.get(INDEX_GUARD);
  }
  


  public void setGuard(Boolean guard)
  {
    value.set(INDEX_GUARD, guard.booleanValue());
  }
  


  public boolean isHSDir()
  {
    return value.get(INDEX_HIDDENSERVICE_DIRECTORY);
  }
  


  public void setHSDir(Boolean hSDir)
  {
    value.set(INDEX_HIDDENSERVICE_DIRECTORY, hSDir.booleanValue());
  }
  


  public boolean isNamed()
  {
    return value.get(INDEX_NAMED);
  }
  


  public void setNamed(Boolean named)
  {
    value.set(INDEX_NAMED, named.booleanValue());
  }
  


  public boolean isStable()
  {
    return value.get(INDEX_STABLE);
  }
  


  public void setStable(Boolean stable)
  {
    value.set(INDEX_STABLE, stable.booleanValue());
  }
  


  public boolean isUnnamed()
  {
    return value.get(INDEX_UNNAMED);
  }
  


  public void setUnnamed(Boolean unnamed)
  {
    value.set(INDEX_UNNAMED, unnamed.booleanValue());
  }
  


  public boolean isValid()
  {
    return value.get(INDEX_VALID);
  }
  


  public void setValid(Boolean valid)
  {
    value.set(INDEX_VALID, valid.booleanValue());
  }
  


  public boolean isV2Dir()
  {
    return value.get(INDEX_V2DIR);
  }
  


  public void setV2Dir(Boolean v2Dir)
  {
    value.set(INDEX_V2DIR, v2Dir.booleanValue());
  }
  
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if ((o == null) || (getClass() != o.getClass())) { return false;
    }
    RouterFlags that = (RouterFlags)o;
    
    if (value != null ? !value.equals(value) : value != null) { return false;
    }
    return true;
  }
  
  public int hashCode()
  {
    return value != null ? value.hashCode() : 0;
  }
  





  protected boolean match(RouterFlags ruleFlags)
  {
    for (int i = 0; i < value.size(); i++) {
      if ((value.get(i)) && (!value.get(i))) {
        return false;
      }
    }
    return true;
  }
  
  public String toString()
  {
    StringBuilder buffer = new StringBuilder();
    if (isAuthority()) {
      buffer.append("Authority");
      buffer.append(' ');
    }
    if (isBadDirectory()) {
      buffer.append("BadDirectory");
      buffer.append(' ');
    }
    if (isBadExit()) {
      buffer.append("BadExit");
      buffer.append(' ');
    }
    if (isExit()) {
      buffer.append("Exit");
      buffer.append(' ');
    }
    if (isFast()) {
      buffer.append("Fast");
      buffer.append(' ');
    }
    if (isGuard()) {
      buffer.append("Guard");
      buffer.append(' ');
    }
    if (isHibernating()) {
      buffer.append("Hibernating");
      buffer.append(' ');
    }
    if (isHSDir()) {
      buffer.append("HSDir");
      buffer.append(' ');
    }
    if (isNamed()) {
      buffer.append("Named");
      buffer.append(' ');
    }
    if (isStable()) {
      buffer.append("Stable");
      buffer.append(' ');
    }
    if (isRunning()) {
      buffer.append("Running");
      buffer.append(' ');
    }
    if (isUnnamed()) {
      buffer.append("Unnamed");
      buffer.append(' ');
    }
    if (isValid()) {
      buffer.append("Valid");
      buffer.append(' ');
    }
    if (isV2Dir()) {
      buffer.append("V2Dir");
      buffer.append(' ');
    }
    return buffer.toString().trim();
  }
}
