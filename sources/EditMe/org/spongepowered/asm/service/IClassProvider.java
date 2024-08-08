package org.spongepowered.asm.service;

import java.net.URL;

public interface IClassProvider {
   URL[] getClassPath();

   Class findClass(String var1) throws ClassNotFoundException;

   Class findClass(String var1, boolean var2) throws ClassNotFoundException;

   Class findAgentClass(String var1, boolean var2) throws ClassNotFoundException;
}
