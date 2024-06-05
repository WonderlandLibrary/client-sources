package net.minecraft.src;

import java.util.concurrent.*;
import java.lang.reflect.*;
import java.util.*;

class CallableSuspiciousClasses implements Callable
{
    final CrashReport theCrashReport;
    
    CallableSuspiciousClasses(final CrashReport par1CrashReport) {
        this.theCrashReport = par1CrashReport;
    }
    
    public String callSuspiciousClasses() {
        final StringBuilder var1 = new StringBuilder();
        ArrayList var3;
        try {
            final Field var2 = ClassLoader.class.getDeclaredField("classes");
            var2.setAccessible(true);
            var3 = new ArrayList((Collection<? extends E>)var2.get(CrashReport.class.getClassLoader()));
        }
        catch (Exception ex) {
            return "";
        }
        boolean var4 = true;
        final boolean var5 = !CrashReport.class.getCanonicalName().equals("net.minecraft.CrashReport");
        final HashMap var6 = new HashMap();
        String var7 = "";
        Collections.sort((List<Object>)var3, new ComparatorClassSorter(this));
        for (final Class var9 : var3) {
            if (var9 != null) {
                final String var10 = var9.getCanonicalName();
                if (var10 == null || var10.startsWith("org.lwjgl.") || var10.startsWith("paulscode.") || var10.startsWith("org.bouncycastle.") || var10.startsWith("argo.") || var10.startsWith("com.jcraft.") || var10.startsWith("com.fasterxml.") || var10.equals("util.GLX")) {
                    continue;
                }
                if (var5) {
                    if (var10.length() <= 3 || var10.equals("net.minecraft.client.MinecraftApplet") || var10.equals("net.minecraft.client.Minecraft") || var10.equals("net.minecraft.client.ClientBrandRetriever")) {
                        continue;
                    }
                    if (var10.equals("net.minecraft.server.MinecraftServer")) {
                        continue;
                    }
                }
                else if (var10.startsWith("net.minecraft")) {
                    continue;
                }
                final Package var11 = var9.getPackage();
                final String var12 = (var11 == null) ? "" : var11.getName();
                if (var6.containsKey(var12)) {
                    final int var13 = var6.get(var12);
                    var6.put(var12, var13 + 1);
                    if (var13 == 3) {
                        if (!var4) {
                            var1.append(", ");
                        }
                        var1.append("...");
                        var4 = false;
                        continue;
                    }
                    if (var13 > 3) {
                        continue;
                    }
                }
                else {
                    var6.put(var12, 1);
                }
                if (var7 != var12 && var7.length() > 0) {
                    var1.append("], ");
                }
                if (!var4 && var7 == var12) {
                    var1.append(", ");
                }
                if (var7 != var12) {
                    var1.append("[");
                    var1.append(var12);
                    var1.append(".");
                }
                var1.append(var9.getSimpleName());
                var7 = var12;
                var4 = false;
            }
        }
        if (var4) {
            var1.append("No suspicious classes found.");
        }
        else {
            var1.append("]");
        }
        return var1.toString();
    }
    
    @Override
    public Object call() {
        return this.callSuspiciousClasses();
    }
}
