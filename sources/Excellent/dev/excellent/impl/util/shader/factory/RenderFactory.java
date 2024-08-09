package dev.excellent.impl.util.shader.factory;

import lombok.experimental.UtilityClass;
import net.mojang.blaze3d.systems.IRenderCall;

import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class RenderFactory {
    private final List<IRenderCall> TASKS = new LinkedList<>();
    private final List<IRenderCall> GUI_TASKS = new LinkedList<>();

    public void addTask(IRenderCall task) {
        if (task == null) return;
        TASKS.add(task);
    }

    public void renderAllTasks() {
        if (!TASKS.isEmpty()) {
            processQueue(TASKS);
        }
    }

    public void addGuiTask(IRenderCall task) {
        if (task == null) return;
        GUI_TASKS.add(task);
    }

    public void renderAllGuiTasks() {
        if (!GUI_TASKS.isEmpty()) {
            processQueue(GUI_TASKS);
        }
    }

    private void processQueue(List<IRenderCall> queue) {
        for (IRenderCall renderCall : queue) {
            renderCall.execute();
        }
        queue.clear();
    }
}
