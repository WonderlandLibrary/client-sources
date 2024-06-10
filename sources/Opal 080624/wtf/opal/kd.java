package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.HashMap;

public final class kd extends k3<HashMap<String, ke>> {
  private boolean K;
  
  private static final long b = on.a(-1705244116620364081L, 2734718866161467580L, MethodHandles.lookup().lookupClass()).a(154001277117886L);
  
  public kd(String paramString, ke... paramVarArgs) {
    super(paramString);
    V(new Object[] { new HashMap<>() });
    Arrays.<ke>stream(paramVarArgs).forEach(this::lambda$new$0);
  }
  
  public kd(long paramLong, String paramString, u_ paramu_, ke... paramVarArgs) {
    super(l, paramString, paramu_);
    V(new Object[] { new HashMap<>() });
    Arrays.<ke>stream(paramVarArgs).forEach(this::lambda$new$1);
  }
  
  public ke N(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    return z().computeIfAbsent(str.toLowerCase(), kd::lambda$getProperty$2);
  }
  
  public void o(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    this.K = bool;
  }
  
  public boolean q(Object[] paramArrayOfObject) {
    return this.K;
  }
  
  private static ke lambda$getProperty$2(String paramString) {
    return null;
  }
  
  private void lambda$new$1(ke paramke) {
    z().put(paramke.r(new Object[0]).toLowerCase(), paramke);
  }
  
  private void lambda$new$0(ke paramke) {
    z().put(paramke.r(new Object[0]).toLowerCase(), paramke);
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\kd.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */