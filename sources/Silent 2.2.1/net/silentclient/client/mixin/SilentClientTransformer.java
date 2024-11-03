package net.silentclient.client.mixin;

import net.minecraft.launchwrapper.IClassTransformer;
import net.silentclient.client.Client;
import org.objectweb.asm.*;

import java.lang.reflect.Modifier;

public class SilentClientTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null)
        {
            return null;
        }

        if(transformedName.equals("bfl$e") || transformedName.equals("bfl$r") || transformedName.equals("bnm$a") || transformedName.equals("ajd$a") || transformedName.equals("bha$a") || transformedName.equals("re$a")) {
            Client.logger.info("Transforming class: " + transformedName);
            ClassReader classreader = new ClassReader(bytes);
            ClassWriter classwriter = new ClassWriter(1);
            SilentClientTransformer.AccessTransformerVisitor visitor = new AccessTransformerVisitor(262144, classwriter);
            classreader.accept(visitor, 0);
            return classwriter.toByteArray();
        }

        return bytes;
    }

    public static String getClassName(final Class clazz) {
        return clazz.getName().replace(".", "/");
    }

    public static class AccessTransformerVisitor extends ClassVisitor {

        public AccessTransformerVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {
            super.visitInnerClass(name, outerName, innerName, Modifier.PUBLIC);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, Modifier.PUBLIC, name, signature, superName, interfaces);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return super.visitMethod(Modifier.PUBLIC, name, desc, signature, exceptions);
        }
    }
}
