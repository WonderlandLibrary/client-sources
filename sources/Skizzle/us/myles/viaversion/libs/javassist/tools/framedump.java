/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.tools;

import us.myles.viaversion.libs.javassist.ClassPool;
import us.myles.viaversion.libs.javassist.CtClass;
import us.myles.viaversion.libs.javassist.bytecode.analysis.FramePrinter;

public class framedump {
    private framedump() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java javassist.tools.framedump <fully-qualified class name>");
            return;
        }
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.get(args[0]);
        System.out.println("Frame Dump of " + clazz.getName() + ":");
        FramePrinter.print(clazz, System.out);
    }
}

