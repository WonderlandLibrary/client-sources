package me.kaimson.melonclient.asm;

import org.spongepowered.asm.lib.*;

public class CameraVisitor extends MethodVisitor implements Opcodes
{
    private boolean secondTimeMet;
    
    public CameraVisitor(final MethodVisitor mv) {
        super(262144, mv);
        this.secondTimeMet = false;
    }
    
    @Override
    public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
        if ((opcode == 180 && desc.equals("Z") && name.equals("w")) || name.equals("inGameHasFocus")) {
            if (this.secondTimeMet) {
                this.visitMethodInsn(184, "me/kaimson/melonclient/features/modules/PerspectiveModule", "overrideMouse", "()Z", false);
            }
            else {
                this.secondTimeMet = true;
                super.visitFieldInsn(opcode, owner, name, desc);
            }
        }
        else {
            super.visitFieldInsn(opcode, owner, name, desc);
        }
    }
}
