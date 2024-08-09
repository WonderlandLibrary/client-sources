package ru.FecuritySQ.module.визуальные;

import net.minecraft.network.play.server.SUpdateTimePacket;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionMode;
import ru.FecuritySQ.option.imp.OptionNumric;

public class ChunkAnimator extends Module {

    public OptionNumric time = new OptionNumric("Время", 500, 100F, 2000F, 1F);

    String[] modes = {"Линейный", "Четверка", "Кубический", "Кварта", "Экспо"};
    public OptionMode mode = new OptionMode("Режим", modes, 0);

    public ChunkAnimator() {
        super(Category.Визуальные, GLFW.GLFW_KEY_0);
        addOption(time);
        addOption(mode);
    }
}
