package wtf.resolute.evented.interfaces;

import net.minecraft.client.Minecraft;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.moduled.Module;

public class EventManager {
    /**
     * Вызывает событие и передает его всем активным модулям для обработки.
     *
     * @param event событие для вызова.
     */
    public static void call(final Event event) {
        if (Minecraft.getInstance().player == null || Minecraft.getInstance().world == null) {
            return;
        }

        if (event.isCancel()) {
            return;
        }

        // Перебор всех активных модулей и вызов события
     //   if (!ClientUtil.legitMode) {
        //    callEvent(event);
     //   }
    }

    /**
     * Вызывает указанное событие и передает его всем активным модулям для обработки.
     *
     * @param event событие для вызова.
     */
    private static void callEvent(Event event) {
        for (final Module module : ResoluteInfo.getInstance().getFunctionRegistry().getFunctions()) {
            if (!module.isState())
                continue;

        }
    }
}
