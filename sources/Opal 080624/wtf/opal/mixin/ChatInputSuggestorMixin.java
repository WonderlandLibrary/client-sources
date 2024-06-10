package wtf.opal.mixin;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.concurrent.CompletableFuture;
import net.minecraft.class_2172;
import net.minecraft.class_342;
import net.minecraft.class_4717;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import wtf.opal.x5;

@Mixin({class_4717.class})
public abstract class ChatInputSuggestorMixin {
  @Shadow
  private ParseResults<class_2172> field_21610;
  
  @Shadow
  @Final
  class_342 field_21599;
  
  @Shadow
  boolean field_21614;
  
  @Shadow
  private CompletableFuture<Suggestions> field_21611;
  
  @Shadow
  private class_4717.class_464 field_21612;
  
  @Shadow
  protected abstract void method_23937();
  
  @Inject(method = {"refresh"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;canRead()Z", remap = false)}, cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
  public void onRefresh(CallbackInfo paramCallbackInfo, String paramString, StringReader paramStringReader) {
    // Byte code:
    //   0: ldc '.'
    //   2: astore #4
    //   4: aload #4
    //   6: invokevirtual length : ()I
    //   9: istore #5
    //   11: aload_3
    //   12: iload #5
    //   14: invokevirtual canRead : (I)Z
    //   17: ifeq -> 179
    //   20: aload_3
    //   21: invokevirtual getString : ()Ljava/lang/String;
    //   24: aload #4
    //   26: aload_3
    //   27: invokevirtual getCursor : ()I
    //   30: invokevirtual startsWith : (Ljava/lang/String;I)Z
    //   33: ifeq -> 179
    //   36: goto -> 43
    //   39: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: aload_3
    //   44: aload_3
    //   45: invokevirtual getCursor : ()I
    //   48: iload #5
    //   50: iadd
    //   51: invokevirtual setCursor : (I)V
    //   54: aload_0
    //   55: getfield field_21610 : Lcom/mojang/brigadier/ParseResults;
    //   58: ifnonnull -> 95
    //   61: goto -> 68
    //   64: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   67: athrow
    //   68: aload_0
    //   69: getstatic wtf/opal/uw.h : Lcom/mojang/brigadier/CommandDispatcher;
    //   72: aload_3
    //   73: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   76: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   79: invokevirtual method_2875 : ()Lnet/minecraft/class_637;
    //   82: invokevirtual parse : (Lcom/mojang/brigadier/StringReader;Ljava/lang/Object;)Lcom/mojang/brigadier/ParseResults;
    //   85: putfield field_21610 : Lcom/mojang/brigadier/ParseResults;
    //   88: goto -> 95
    //   91: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   94: athrow
    //   95: aload_0
    //   96: getfield field_21599 : Lnet/minecraft/class_342;
    //   99: invokevirtual method_1881 : ()I
    //   102: istore #6
    //   104: iload #6
    //   106: iconst_1
    //   107: if_icmplt -> 175
    //   110: aload_0
    //   111: getfield field_21612 : Lnet/minecraft/class_4717$class_464;
    //   114: ifnull -> 138
    //   117: goto -> 124
    //   120: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   123: athrow
    //   124: aload_0
    //   125: getfield field_21614 : Z
    //   128: ifne -> 175
    //   131: goto -> 138
    //   134: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   137: athrow
    //   138: aload_0
    //   139: getstatic wtf/opal/uw.h : Lcom/mojang/brigadier/CommandDispatcher;
    //   142: aload_0
    //   143: getfield field_21610 : Lcom/mojang/brigadier/ParseResults;
    //   146: iload #6
    //   148: invokevirtual getCompletionSuggestions : (Lcom/mojang/brigadier/ParseResults;I)Ljava/util/concurrent/CompletableFuture;
    //   151: putfield field_21611 : Ljava/util/concurrent/CompletableFuture;
    //   154: aload_0
    //   155: getfield field_21611 : Ljava/util/concurrent/CompletableFuture;
    //   158: aload_0
    //   159: <illegal opcode> run : (Lwtf/opal/mixin/ChatInputSuggestorMixin;)Ljava/lang/Runnable;
    //   164: invokevirtual thenRun : (Ljava/lang/Runnable;)Ljava/util/concurrent/CompletableFuture;
    //   167: pop
    //   168: goto -> 175
    //   171: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   174: athrow
    //   175: aload_1
    //   176: invokevirtual cancel : ()V
    //   179: return
    // Exception table:
    //   from	to	target	type
    //   11	36	39	wtf/opal/x5
    //   20	61	64	wtf/opal/x5
    //   43	88	91	wtf/opal/x5
    //   104	117	120	wtf/opal/x5
    //   110	131	134	wtf/opal/x5
    //   124	168	171	wtf/opal/x5
  }
  
  private void lambda$onRefresh$0() {
    try {
      if (this.field_21611.isDone())
        method_23937(); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\ChatInputSuggestorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */