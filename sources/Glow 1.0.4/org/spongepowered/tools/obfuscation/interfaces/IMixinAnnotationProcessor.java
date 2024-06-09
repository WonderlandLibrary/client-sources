package org.spongepowered.tools.obfuscation.interfaces;

import javax.annotation.processing.*;
import org.spongepowered.asm.util.*;

public interface IMixinAnnotationProcessor extends Messager, IOptionProvider
{
    CompilerEnvironment getCompilerEnvironment();
    
    ProcessingEnvironment getProcessingEnvironment();
    
    IObfuscationManager getObfuscationManager();
    
    ITokenProvider getTokenProvider();
    
    ITypeHandleProvider getTypeProvider();
    
    IJavadocProvider getJavadocProvider();
    
    public enum CompilerEnvironment
    {
        JAVAC, 
        JDT;
        
        private static final CompilerEnvironment[] $VALUES;
        
        public static CompilerEnvironment[] values() {
            return CompilerEnvironment.$VALUES.clone();
        }
        
        public static CompilerEnvironment valueOf(final String s) {
            return Enum.valueOf(CompilerEnvironment.class, s);
        }
        
        static {
            $VALUES = new CompilerEnvironment[] { CompilerEnvironment.JAVAC, CompilerEnvironment.JDT };
        }
    }
}
