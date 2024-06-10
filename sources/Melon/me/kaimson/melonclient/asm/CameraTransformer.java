package me.kaimson.melonclient.asm;

import net.minecraft.launchwrapper.*;
import me.kaimson.melonclient.launch.*;
import org.spongepowered.asm.lib.*;

public class CameraTransformer implements IClassTransformer
{
    public byte[] transform(final String name, final String transformedName, final byte[] bytes) {
        if (transformedName.equals("net.minecraft.client.renderer.EntityRenderer") || transformedName.equals("bfk")) {
            MelonClientTweaker.logger.info("Performing transformation...");
            final ClassReader reader = new ClassReader(bytes);
            final ClassWriter writer = new ClassWriter(2);
            final ClassVisitor visitor = new ClassVisitor(262144, writer) {
                @Override
                public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] ex) {
                    final MethodVisitor mv = super.visitMethod(access, name, desc, signature, ex);
                    if ((desc.equals("(F)V") && name.equals("f")) || name.equals("orientCamera")) {
                        MelonClientTweaker.logger.info("Started OrientCamera patcher!");
                        return new OrientCameraVisitor(mv);
                    }
                    if ((desc.equals("(FJ)V") && name.equals("a")) || name.equals("updateCameraAndRender")) {
                        MelonClientTweaker.logger.info("Started Camera patcher!");
                        return new CameraVisitor(mv);
                    }
                    return mv;
                }
            };
            reader.accept(visitor, 0);
            return writer.toByteArray();
        }
        return bytes;
    }
}
