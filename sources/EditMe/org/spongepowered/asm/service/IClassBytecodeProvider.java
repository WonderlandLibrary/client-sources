package org.spongepowered.asm.service;

import java.io.IOException;
import org.spongepowered.asm.lib.tree.ClassNode;

public interface IClassBytecodeProvider {
   byte[] getClassBytes(String var1, String var2) throws IOException;

   byte[] getClassBytes(String var1, boolean var2) throws ClassNotFoundException, IOException;

   ClassNode getClassNode(String var1) throws ClassNotFoundException, IOException;
}
