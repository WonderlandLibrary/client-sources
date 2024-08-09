package ru.FecuritySQ.event.imp;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.math.vector.Matrix4f;
import ru.FecuritySQ.event.Event;

public class EventRender extends Event {
    public final MatrixStack modelViewStack;
    public final Matrix4f projectionMatrix;

    public EventRender(MatrixStack stack, Matrix4f projection){
        this.modelViewStack = stack;
        this.projectionMatrix = projection;
    }


}
