/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl.feature;

import java.util.List;
import java.util.Locale;
import mpp.venusfr.command.Command;
import mpp.venusfr.command.CommandWithAdvice;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.Parameters;
import mpp.venusfr.command.Prefix;
import mpp.venusfr.command.impl.CommandException;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.utils.client.KeyStorage;
import mpp.venusfr.venusfr;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.glfw.GLFW;

public class BindCommand
implements Command,
CommandWithAdvice {
    private final Prefix prefix;
    private final Logger logger;

    @Override
    public void execute(Parameters parameters) {
        String string;
        switch (string = parameters.asString(0).orElse("")) {
            case "add": {
                this.addBindToFunction(parameters, this.logger);
                break;
            }
            case "remove": {
                this.removeBindFromFunction(parameters, this.logger);
                break;
            }
            case "clear": {
                this.clearAllBindings(this.logger);
                break;
            }
            case "list": {
                this.listBoundKeys(this.logger);
                break;
            }
            default: {
                throw new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0442\u0438\u043f \u043a\u043e\u043c\u0430\u043d\u0434\u044b:" + TextFormatting.GRAY + " add, remove, clear, list");
            }
        }
    }

    @Override
    public String name() {
        return "bind";
    }

    @Override
    public String description() {
        return "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0437\u0430\u0431\u0438\u043d\u0434\u0438\u0442\u044c \u0444\u0443\u043d\u043a\u0446\u0438\u044e \u043d\u0430 \u043e\u043f\u0440\u0435\u0434\u0435\u043b\u0435\u043d\u043d\u0443\u044e \u043a\u043b\u0430\u0432\u0438\u0448\u0443";
    }

    @Override
    public List<String> adviceMessage() {
        String string = this.prefix.get();
        return List.of((Object)(string + "bind add <function> <key> - \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043d\u043e\u0432\u044b\u0439 \u0431\u0438\u043d\u0434"), (Object)(string + "bind remove <function> <key> - \u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0431\u0438\u043d\u0434"), (Object)(string + "bind list - \u041f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u0431\u0438\u043d\u0434\u043e\u0432"), (Object)(string + "bind clear - \u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u0431\u0438\u043d\u0434\u043e\u0432"), (Object)("\u041f\u0440\u0438\u043c\u0435\u0440: " + TextFormatting.RED + string + "bind add KillAura R"));
    }

    private void addBindToFunction(Parameters parameters, Logger logger) {
        String string = parameters.asString(1).orElseThrow(BindCommand::lambda$addBindToFunction$0);
        String string2 = parameters.asString(2).orElseThrow(BindCommand::lambda$addBindToFunction$1);
        Function function = null;
        for (Function function2 : venusfr.getInstance().getFunctionRegistry().getFunctions()) {
            if (!function2.getName().toLowerCase(Locale.ROOT).equals(string.toLowerCase(Locale.ROOT))) continue;
            function = function2;
            break;
        }
        Integer n = KeyStorage.getKey(string2.toUpperCase());
        if (function == null) {
            logger.log(TextFormatting.RED + "\u0424\u0443\u043d\u043a\u0446\u0438\u044f " + string + " \u043d\u0435 \u0431\u044b\u043b\u0430 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
            return;
        }
        if (n == null) {
            logger.log(TextFormatting.RED + "\u041a\u043b\u0430\u0432\u0438\u0448\u0430 " + string2 + " \u043d\u0435 \u0431\u044b\u043b\u0430 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
            return;
        }
        function.setBind(n);
        logger.log(TextFormatting.GREEN + "\u0411\u0438\u043d\u0434 " + TextFormatting.RED + string2.toUpperCase() + TextFormatting.GREEN + " \u0431\u044b\u043b \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d \u0434\u043b\u044f \u0444\u0443\u043d\u043a\u0446\u0438\u0438 " + TextFormatting.RED + string);
    }

    private void removeBindFromFunction(Parameters parameters, Logger logger) {
        String string = parameters.asString(1).orElseThrow(BindCommand::lambda$removeBindFromFunction$2);
        String string2 = parameters.asString(2).orElseThrow(BindCommand::lambda$removeBindFromFunction$3);
        venusfr.getInstance().getFunctionRegistry().getFunctions().stream().filter(arg_0 -> BindCommand.lambda$removeBindFromFunction$4(string, arg_0)).forEach(arg_0 -> BindCommand.lambda$removeBindFromFunction$5(logger, string2, arg_0));
    }

    private void clearAllBindings(Logger logger) {
        venusfr.getInstance().getFunctionRegistry().getFunctions().forEach(BindCommand::lambda$clearAllBindings$6);
        logger.log(TextFormatting.GREEN + "\u0412\u0441\u0435 \u043a\u043b\u0430\u0432\u0438\u0448\u0438 \u0431\u044b\u043b\u0438 \u043e\u0442\u0432\u044f\u0437\u0430\u043d\u044b \u043e\u0442 \u043c\u043e\u0434\u0443\u043b\u0435\u0439");
    }

    private void listBoundKeys(Logger logger) {
        logger.log(TextFormatting.GRAY + "\u0421\u043f\u0438\u0441\u043e\u043a \u0432\u0441\u0435\u0445 \u043c\u043e\u0434\u0443\u043b\u0435\u0439 \u0441 \u043f\u0440\u0438\u0432\u044f\u0437\u0430\u043d\u043d\u044b\u043c\u0438 \u043a\u043b\u0430\u0432\u0438\u0448\u0430\u043c\u0438:");
        venusfr.getInstance().getFunctionRegistry().getFunctions().stream().filter(BindCommand::lambda$listBoundKeys$7).map(BindCommand::lambda$listBoundKeys$8).forEach(logger::log);
    }

    public BindCommand(Prefix prefix, Logger logger) {
        this.prefix = prefix;
        this.logger = logger;
    }

    private static String lambda$listBoundKeys$8(Function function) {
        String string = GLFW.glfwGetKeyName(function.getBind(), -1);
        string = string != null ? string : "";
        return String.format("%s [%s%s%s]", new Object[]{function.getName(), TextFormatting.GRAY, string, TextFormatting.WHITE});
    }

    private static boolean lambda$listBoundKeys$7(Function function) {
        return function.getBind() != 0;
    }

    private static void lambda$clearAllBindings$6(Function function) {
        function.setBind(0);
    }

    private static void lambda$removeBindFromFunction$5(Logger logger, String string, Function function) {
        function.setBind(0);
        logger.log(TextFormatting.GREEN + "\u041a\u043b\u0430\u0432\u0438\u0448\u0430 " + TextFormatting.RED + string.toUpperCase() + TextFormatting.GREEN + " \u0431\u044b\u043b\u0430 \u043e\u0442\u0432\u044f\u0437\u0430\u043d\u0430 \u043e\u0442 \u0444\u0443\u043d\u043a\u0446\u0438\u0438 " + TextFormatting.RED + function.getName());
    }

    private static boolean lambda$removeBindFromFunction$4(String string, Function function) {
        return function.getName().equalsIgnoreCase(string);
    }

    private static CommandException lambda$removeBindFromFunction$3() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u043a\u043d\u043e\u043f\u043a\u0443!");
    }

    private static CommandException lambda$removeBindFromFunction$2() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0444\u0443\u043d\u043a\u0446\u0438\u0438!");
    }

    private static CommandException lambda$addBindToFunction$1() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u043a\u043d\u043e\u043f\u043a\u0443!");
    }

    private static CommandException lambda$addBindToFunction$0() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0444\u0443\u043d\u043a\u0446\u0438\u0438!");
    }
}

