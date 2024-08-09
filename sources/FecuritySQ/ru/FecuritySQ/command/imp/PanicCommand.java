package ru.FecuritySQ.command.imp;


import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.command.Command;
import ru.FecuritySQ.command.CommandAbstract;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.utils.RenderUtil;


@Command(name = "panic", description = "Отключает все функции в чите")
public class PanicCommand extends CommandAbstract {
    static Minecraft mc = Minecraft.getInstance();
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length == 1) {
            for (Module feature : FecuritySQ.get().getModuleList()) {
                if (feature.isEnabled()) {
                    feature.toggle();
                }
                mc.player.sendChatMessage(".bind clear");
                mc.player.sendChatMessage(".bind add ClickGui Home");
            }
            RenderUtil.addChatMessage(TextFormatting.GREEN + "Усюпешно выключены все модули, ClientGui на HOME и все бинды очищеные");
        } else {
            error();
        }
    }

    @Override
    public void error() {
        sendMessage(TextFormatting.GRAY + "Ошибка в использовании" + TextFormatting.WHITE + ":");
        sendMessage(TextFormatting.RED + ".panic");
    }
}
