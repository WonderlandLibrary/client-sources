package wtf.opal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;

@Environment(EnvType.CLIENT)
public final class lg extends Record {
  private final class_2561 m;
  
  private final class_2561 a;
  
  private final int o;
  
  public lg(class_2561 paramclass_25611, class_2561 paramclass_25612, int paramInt) {
    this.m = paramclass_25611;
    this.a = paramclass_25612;
    this.o = paramInt;
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> toString : (Lwtf/opal/lg;)Ljava/lang/String;
    //   6: areturn
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lwtf/opal/lg;)I
    //   6: ireturn
  }
  
  public final boolean equals(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lwtf/opal/lg;Ljava/lang/Object;)Z
    //   7: ireturn
  }
  
  public class_2561 M() {
    return this.m;
  }
  
  public class_2561 a() {
    return this.a;
  }
  
  public int o() {
    return this.o;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */