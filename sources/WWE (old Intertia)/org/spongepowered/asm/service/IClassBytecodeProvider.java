package org.spongepowered.asm.service;

import java.io.*;
import org.spongepowered.asm.lib.tree.*;

public interface IClassBytecodeProvider
{
    byte[] getClassBytes(final String p0, final String p1) throws IOException;
    
    byte[] getClassBytes(final String p0, final boolean p1) throws ClassNotFoundException, IOException;
    
    ClassNode getClassNode(final String p0) throws ClassNotFoundException, IOException;
}
