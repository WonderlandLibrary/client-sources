package ru.FecuritySQ.module.визуальные;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventAttack;
import ru.FecuritySQ.event.imp.EventHud;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.shader.ShaderUtil;
import ru.FecuritySQ.utils.Counter;
import ru.FecuritySQ.utils.RenderUtil;

public class DethMovie extends Module {
    private Entity target;
    MatrixStack stack = new MatrixStack();

    public DethMovie() {
        super(Category.Визуальные, GLFW.GLFW_KEY_0);
    }
    @Override
    public void event(Event event) {
        if (!this.isEnabled()) return;
        if(event instanceof EventAttack) {
            target = ((EventAttack) event).getTarget();
        }
        if (event instanceof EventUpdate && target != null && target instanceof PlayerEntity) {
            if (((PlayerEntity) target ).getHealth() <= 5) {
                target = null;
                System.out.println("Работает #1");
                Minecraft.getInstance().getTimer().setTicks(1);
                Counter.sleepVoid(()-> {    Minecraft.getInstance().getTimer().setTicks(20); }, 500);
                RenderUtil.drawImage(stack, new ResourceLocation("FecuritySQ/icons/fon.png"), 0, 0, mc.getMainWindow().getScaledWidth(), mc.getMainWindow().getScaledHeight());
            }
        }
    }
}
