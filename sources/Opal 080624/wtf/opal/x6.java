package wtf.opal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.List;

public abstract class x6 {
  private final String B;
  
  private final String T;
  
  private final List<String> o;
  
  private static boolean E;
  
  private static final long a = on.a(-789266640724041174L, -744180870862143168L, MethodHandles.lookup().lookupClass()).a(127761423885082L);
  
  protected x6(long paramLong, String paramString1, String paramString2, String... paramVarArgs) {
    boolean bool = r();
    try {
      this.B = paramString1;
      this.T = paramString2;
      this.o = List.of((Object[])paramVarArgs);
      if (!bool)
        d.p(new d[5]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  protected x6(String paramString1, char paramChar1, String paramString2, char paramChar2, int paramInt) {
    this(l2, paramString1, paramString2, new String[0]);
  }
  
  public final String o() {
    return this.B;
  }
  
  public final String s(Object[] paramArrayOfObject) {
    return this.T;
  }
  
  public final List Q(Object[] paramArrayOfObject) {
    return this.o;
  }
  
  protected abstract void Z(Object[] paramArrayOfObject);
  
  protected static RequiredArgumentBuilder Y(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    ArgumentType argumentType = (ArgumentType)paramArrayOfObject[1];
    return RequiredArgumentBuilder.argument(str, argumentType);
  }
  
  protected static LiteralArgumentBuilder i(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    return LiteralArgumentBuilder.literal(str);
  }
  
  public final void L(Object[] paramArrayOfObject) {
    CommandDispatcher commandDispatcher = (CommandDispatcher)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x49D2008E32F4L;
    (new Object[3])[2] = this.B;
    (new Object[3])[1] = commandDispatcher;
    new Object[3];
    R(new Object[] { Long.valueOf(l2) });
    Iterator<String> iterator = this.o.iterator();
    boolean bool = r();
    while (iterator.hasNext()) {
      String str = iterator.next();
      (new Object[3])[2] = str;
      (new Object[3])[1] = commandDispatcher;
      new Object[3];
      R(new Object[] { Long.valueOf(l2) });
      if (!bool)
        break; 
    } 
  }
  
  public void R(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    CommandDispatcher commandDispatcher = (CommandDispatcher)paramArrayOfObject[1];
    String str = (String)paramArrayOfObject[2];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x19D76AFD13CL;
    LiteralArgumentBuilder literalArgumentBuilder = LiteralArgumentBuilder.literal(str);
    new Object[2];
    Z(new Object[] { null, Long.valueOf(l2), literalArgumentBuilder });
    commandDispatcher.register(literalArgumentBuilder);
  }
  
  public static void b(boolean paramBoolean) {
    E = paramBoolean;
  }
  
  public static boolean r() {
    return E;
  }
  
  public static boolean V() {
    boolean bool = r();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  static {
    if (!r())
      b(true); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\x6.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */