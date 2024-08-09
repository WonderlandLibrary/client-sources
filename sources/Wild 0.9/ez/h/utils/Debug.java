package ez.h.utils;

import java.util.concurrent.*;
import java.util.*;

public class Debug
{
    private static final Map<String, Object> DYNAMIC_VARIABLES;
    private static final Map<ExecuteLaterInfo, ExecuteCallback> LATER_EXECUTES;
    private static final Map<String, DebugDynamicMethod> DYNAMIC_METHODS;
    
    public static Object executeMethod(final String s, final Object... array) {
        if (!Debug.DYNAMIC_METHODS.containsKey(s)) {
            System.out.println("[DEBUG] \u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u0440\u0438 \u0432\u044b\u043f\u043e\u043b\u043d\u0435\u043d\u0438\u0438 \u043c\u0435\u0442\u043e\u0434\u0430! \u041c\u0435\u0442\u043e\u0434 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d!");
            return null;
        }
        return Debug.DYNAMIC_METHODS.get(s).invoke(array);
    }
    
    private static void startThread() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokedynamic   BootstrapMethod #0, run:()Ljava/lang/Runnable;
        //     9: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;)V
        //    12: invokevirtual   java/lang/Thread.start:()V
        //    15: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Unsupported node type: com.strobel.decompiler.ast.Lambda
        //     at com.strobel.decompiler.ast.Error.unsupportedNode(Error.java:32)
        //     at com.strobel.decompiler.ast.GotoRemoval.exit(GotoRemoval.java:612)
        //     at com.strobel.decompiler.ast.GotoRemoval.exit(GotoRemoval.java:586)
        //     at com.strobel.decompiler.ast.GotoRemoval.exit(GotoRemoval.java:598)
        //     at com.strobel.decompiler.ast.GotoRemoval.exit(GotoRemoval.java:586)
        //     at com.strobel.decompiler.ast.GotoRemoval.transformLeaveStatements(GotoRemoval.java:625)
        //     at com.strobel.decompiler.ast.GotoRemoval.removeGotosCore(GotoRemoval.java:57)
        //     at com.strobel.decompiler.ast.GotoRemoval.removeGotos(GotoRemoval.java:53)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:276)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        LATER_EXECUTES = new ConcurrentHashMap<ExecuteLaterInfo, ExecuteCallback>();
        startThread();
        DYNAMIC_VARIABLES = new HashMap<String, Object>();
        DYNAMIC_METHODS = new HashMap<String, DebugDynamicMethod>();
    }
    
    public static void createMethodIfNotExist(final String s, final DebugDynamicMethod debugDynamicMethod) {
        if (!Debug.DYNAMIC_METHODS.containsKey(s)) {
            Debug.DYNAMIC_METHODS.put(s, debugDynamicMethod);
        }
    }
    
    public static void executeLaterNotStack(final long n, final ExecuteCallback executeCallback) {
        if (!Debug.LATER_EXECUTES.containsValue(executeCallback)) {
            Debug.LATER_EXECUTES.put(new ExecuteLaterInfo(n), executeCallback);
        }
    }
    
    public static void executeLater(final long n, final ExecuteCallback executeCallback) {
        Debug.LATER_EXECUTES.put(new ExecuteLaterInfo(n), executeCallback);
    }
    
    public static void overwriteMethod(final String s, final DebugDynamicMethod debugDynamicMethod) {
        Debug.DYNAMIC_METHODS.put(s, debugDynamicMethod);
    }
    
    public static <T> void setVar(final String s, final T t) {
        if (Debug.DYNAMIC_VARIABLES.containsKey(s)) {
            Debug.DYNAMIC_VARIABLES.put(s, t);
        }
    }
    
    public static <T> T getOrCreateVar(final String s, final T t) {
        if (!Debug.DYNAMIC_VARIABLES.containsKey(s)) {
            Debug.DYNAMIC_VARIABLES.put(s, t);
        }
        return (T)Debug.DYNAMIC_VARIABLES.get(s);
    }
    
    public static class ExecuteLaterInfo
    {
        public long ms;
        
        public ExecuteLaterInfo(final long ms) {
            this.ms = ms;
        }
    }
    
    public interface ExecuteCallback
    {
        void execute();
    }
    
    public interface DebugDynamicMethod
    {
        Object invoke(final Object... p0);
    }
}
