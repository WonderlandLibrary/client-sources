package dev.stephen.nexus.event.impl.render;

import dev.stephen.nexus.event.types.Event;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4fStack;

@Getter
public class EventRender3D implements Event {
    MatrixStack matrixStack;

    public EventRender3D(MatrixStack matrixStack) {
        this.matrixStack = matrixStack;
    }
}
