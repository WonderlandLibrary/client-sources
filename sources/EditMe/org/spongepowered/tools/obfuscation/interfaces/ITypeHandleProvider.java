package org.spongepowered.tools.obfuscation.interfaces;

import javax.lang.model.type.TypeMirror;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public interface ITypeHandleProvider {
   TypeHandle getTypeHandle(String var1);

   TypeHandle getSimulatedHandle(String var1, TypeMirror var2);
}
