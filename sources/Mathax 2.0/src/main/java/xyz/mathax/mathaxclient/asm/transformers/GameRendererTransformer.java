package xyz.mathax.mathaxclient.asm.transformers;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.asm.AsmTransformer;
import xyz.mathax.mathaxclient.asm.Descriptor;
import xyz.mathax.mathaxclient.asm.MethodInfo;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class GameRendererTransformer extends AsmTransformer {
    private final MethodInfo getFovMethod;

    public GameRendererTransformer() {
        super(mapClassName("net/minecraft/class_757"));

        getFovMethod = new MethodInfo("net/minecraft/class_4184", null, new Descriptor("Lnet/minecraft/class_4184;", "F", "Z", "D"), true);
    }

    @Override
    public void transform(ClassNode klass) {
        // Modify GameRenderer.getFov()
        MethodNode method = getMethod(klass, getFovMethod);
        if (method == null) {
            throw new RuntimeException("[" + MatHax.NAME + "] Could not find method GameRenderer.getFov()");
        }

        int injectionCount = 0;
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof LdcInsnNode in && in.cst instanceof Double && (double) in.cst == 90) {
                InsnList insns = new InsnList();
                generateEventCall(insns, new LdcInsnNode(in.cst));

                method.instructions.insert(insn, insns);
                method.instructions.remove(insn);
                injectionCount++;
            } else if ((insn instanceof MethodInsnNode in1 && in1.name.equals("intValue") && insn.getNext() instanceof InsnNode _in && _in.getOpcode() == Opcodes.I2D) || (insn instanceof MethodInsnNode in2 && in2.owner.equals(klass.name) && in2.name.startsWith("redirect") && in2.name.endsWith("getFov"))) { // Wi Zoom compatibility
                InsnList insns = new InsnList();
                insns.add(new VarInsnNode(Opcodes.DSTORE, method.maxLocals));
                generateEventCall(insns, new VarInsnNode(Opcodes.DLOAD, method.maxLocals));
                method.instructions.insert(insn.getNext(), insns);
                injectionCount++;
            }
        }

        if (injectionCount < 2) {
            throw new RuntimeException("[" + MatHax.NAME + "] Failed to modify GameRenderer.getFov()");
        }
    }

    private void generateEventCall(InsnList insns, AbstractInsnNode loadPreviousFov) {
        insns.add(new FieldInsnNode(Opcodes.GETSTATIC, "xyz/mathax/mathaxclient/MatHax", "EVENT_BUS", "Lxyz/mathax/mathaxclient/eventbus/IEventBus;"));
        insns.add(loadPreviousFov);
        insns.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "xyz/mathax/mathaxclient/events/render/GetFovEvent", "get", "(D)Lxyz/mathax/mathaxclient/events/render/GetFovEvent;"));
        insns.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "xyz/mathax/mathaxclient/eventbus/IEventBus", "post", "(Ljava/lang/Object;)Ljava/lang/Object;"));
        insns.add(new TypeInsnNode(Opcodes.CHECKCAST, "xyz/mathax/mathaxclient/events/render/GetFovEvent"));
        insns.add(new FieldInsnNode(Opcodes.GETFIELD, "xyz/mathax/mathaxclient/events/render/GetFovEvent", "fov", "D"));
    }
}
