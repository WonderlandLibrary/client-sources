package me.kaimson.melonclient.asm;

import org.spongepowered.asm.lib.*;
import java.util.*;
import com.google.common.collect.*;
import me.kaimson.melonclient.launch.*;

public class OrientCameraVisitor extends MethodVisitor implements Opcodes
{
    private final HashMap<String, String> obfList;
    
    public OrientCameraVisitor(final MethodVisitor mv) {
        super(262144, mv);
        (this.obfList = (HashMap<String, String>)Maps.newHashMap()).put("y", "getCameraYaw");
        this.obfList.put("z", "getCameraPitch");
        this.obfList.put("A", "getCameraYaw");
        this.obfList.put("B", "getCameraPitch");
        this.obfList.put("rotationYaw", "getCameraYaw");
        this.obfList.put("rotationPitch", "getCameraPitch");
        this.obfList.put("prevRotationYaw", "getCameraYaw");
        this.obfList.put("prevRotationPitch", "getCameraPitch");
    }
    
    @Override
    public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
        super.visitFieldInsn(opcode, owner, name, desc);
        if (((opcode == 180 && desc.equals("F") && owner.equals("pk")) || owner.equals("net/minecraft/entity/Entity")) && this.obfList.containsKey(name)) {
            MelonClientTweaker.logger.debug("Replaced {} with {}", new Object[] { name, this.obfList.get(name) });
            this.visitInsn(11);
            this.visitInsn(106);
            this.visitMethodInsn(184, "me/kaimson/melonclient/features/modules/PerspectiveModule", this.obfList.get(name), "()F", false);
            this.visitInsn(98);
        }
    }
}
