package studio.dreamys.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingClassAdapter;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.function.BiConsumer;

public class ClassTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (transformedName.equals("net.minecraft.entity.EntityLivingBase")) {
            System.out.println("Found EntityLivingBase: " + name);
            ClassReader classReader = new ClassReader(bytes);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 0);
            for (MethodNode method : classNode.methods) {
                String mappedMethodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, method.name, method.desc);
                if (mappedMethodName.equals("func_70676_i") || mappedMethodName.equals("getLook")) {
                    System.out.println("Found getLook: " + method.name);
                    String entity = FMLDeobfuscatingRemapper.INSTANCE.unmap("net/minecraft/entity/Entity");
                    System.out.println("Found Entity: " + entity);
                    String entityPlayerSP = FMLDeobfuscatingRemapper.INSTANCE.unmap("net/minecraft/client/entity/EntityPlayerSP");
                    System.out.println("Found EntityPlayerSP: " + entityPlayerSP);
                    InsnList insnList = new InsnList();
                    insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    insnList.add(new TypeInsnNode(Opcodes.INSTANCEOF, entityPlayerSP));
                    LabelNode label = new LabelNode();
                    insnList.add(new JumpInsnNode(Opcodes.IFEQ, label));
                    insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    insnList.add(new VarInsnNode(Opcodes.FLOAD, 1));
                    //noinspection deprecation
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, entity, method.name, method.desc));
                    insnList.add(new InsnNode(Opcodes.ARETURN));
                    insnList.add(label);
                    method.instructions.insertBefore(method.instructions.getFirst(), insnList);
                    break;
                }
            }
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        if (name.equals("CapeUtils")) {
            // Use our CapeImageBuffer instead of OptiFine's
            return transformCapeUtils(bytes);
        }
        else if (name.equals("studio.dreamys.asm.CapeImageBuffer")) {
            // Redirect our stub calls to optifine
            return transformMethods(bytes, this::transformCapeImageBuffer);
        }
        else if (transformedName.equals("net.minecraft.client.resources.AbstractResourcePack")) {
            return transformMethods(bytes, this::transformAbstractResourcePack);
        }
        else if (transformedName.equals("net.minecraft.client.Minecraft")) {
            // Remove System.gc calls (they all happen in this class)
            return transformMethods(bytes, this::transformMinecraft);
        }
        return bytes;
    }

    private byte[] transformMethods(byte[] bytes, BiConsumer<ClassNode, MethodNode> transformer) {
        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        classNode.methods.forEach(m -> transformer.accept(classNode, m));

        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    private byte[] transformCapeUtils(byte[] bytes) {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        RemappingClassAdapter adapter = new RemappingClassAdapter(classWriter, new Remapper() {
            @Override
            public String map(String typeName) {
                if (typeName.equals("CapeUtils$1")) {
                    return "studio.dreamys.asm.CapeImageBuffer".replace('.', '/');
                }
                return typeName;
            }
        });

        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(adapter, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    private void transformCapeImageBuffer(ClassNode clazz, MethodNode method) {
        Iterator<AbstractInsnNode> iter = method.instructions.iterator();
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if (insn instanceof MethodInsnNode) {
                MethodInsnNode methodInsn = (MethodInsnNode) insn;
                if (methodInsn.name.equals("parseCape")) {
                    methodInsn.owner = "CapeUtils";
                }
                else if (methodInsn.name.equals("setLocationOfCape")) {
                    methodInsn.setOpcode(Opcodes.INVOKEVIRTUAL);
                    methodInsn.owner = "net/minecraft/client/entity/AbstractClientPlayer";
                    methodInsn.desc = "(Lnet/minecraft/util/ResourceLocation;)V";
                }
            }
        }
    }

    private void transformAbstractResourcePack(ClassNode clazz, MethodNode method) {
        if ((method.name.equals("getPackImage") || method.name.equals("func_110586_a")) && method.desc.equals("()Ljava/awt/image/BufferedImage;")) {
            Iterator<AbstractInsnNode> iter = method.instructions.iterator();
            while (iter.hasNext()) {
                AbstractInsnNode insn = iter.next();
                if (insn.getOpcode() == Opcodes.ARETURN) {
                    method.instructions.insertBefore(insn, new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "studio.dreamys.asm.ResourcePackImageScaler".replace('.', '/'),
                        "scalePackImage",
                        "(Ljava/awt/image/BufferedImage;)Ljava/awt/image/BufferedImage;",
                        false));
                }
            }
        }
    }

    private void transformMinecraft(ClassNode clazz, MethodNode method) {
        Iterator<AbstractInsnNode> iter = method.instructions.iterator();
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if (insn.getOpcode() == Opcodes.INVOKESTATIC) {
                MethodInsnNode methodInsn = (MethodInsnNode) insn;
                if (methodInsn.owner.equals("java/lang/System") && methodInsn.name.equals("gc")) {
                    iter.remove();
                }
            }
        }
    }
}
