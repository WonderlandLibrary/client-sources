package me.dinozoid.strife.command;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.command.argument.Argument;
import me.dinozoid.strife.command.argument.implementations.MultiChoiceArgument;
import me.dinozoid.strife.util.player.ChatFormatting;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.system.MathUtil;
import me.dinozoid.strife.util.system.StringUtil;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Command {

    protected Minecraft mc = Minecraft.getMinecraft();
    private final CommandInfo annotation = getClass().getAnnotation(CommandInfo.class);
    private final String name = annotation.name();
    private final String description = annotation.description();
    private final String[] aliases = annotation.aliases();

    public List<Argument> requiredArguments(String[] args) {
        return arguments(args).stream().filter(Argument::required).collect(Collectors.toList());
    }
    public abstract boolean execute(String[] args, String label);
    public abstract List<Argument> arguments(String[] args);

    public boolean errorHandling(String[] args, String label) {
        boolean execute = false;
        if(args == null || args.length == 0) {
            if(arguments(args) == null) execute(args, label);
            else PlayerUtil.sendMessage(ChatFormatting.translateAlternateColorCodes('&', printableUsage(true, args)));
            return true;
        }
        if(args.length < requiredArguments(args).size()) {
            PlayerUtil.sendMessage(ChatFormatting.translateAlternateColorCodes('&', printableUsage(false, args)));
            return true;
        }
        boolean all = args.length > requiredArguments(args).size();
        List<Argument> arguments = all ? arguments(args) : requiredArguments(args);
        for(int i = 0; i < arguments.size(); i++) {
            Argument argument = arguments.get(i);
            String arg = args[i];
            Class type = argument.type();
            if (Integer.class.equals(type) || Double.class.equals(type) || Float.class.equals(type)) {
                if(StringUtil.isNumeric(arg)) {
                    argument.value(MathUtil.tryParseDouble(arg, 0));
                    execute = true;
                }
            } else if (String.class.equals(type)) {
                if (!StringUtil.isNumeric(arg)) {
                    argument.value(arg);
                    execute = true;
                }
            }
        }
        if(execute && !execute(args, label)) {
            PlayerUtil.sendMessageWithPrefix("&cAn error has occurred.");
            execute = false;
        }
        return execute;
    }

    public String printableUsage(boolean printAll, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("&7" + StrifeClient.CHAT_PREFIX).append(name.toLowerCase()).append(" ");
        if(arguments(args) != null) {
            for(Argument argument : printAll ? arguments(args) : requiredArguments(args)) {
                if(argument instanceof MultiChoiceArgument) {
                    StringBuilder multiChoiceStringBuilder = new StringBuilder();
                    List<Argument> choiceArguments = ((MultiChoiceArgument) argument).arguments();
                    for(int i = 0; i < choiceArguments.size(); i++) {
                        Argument multiArgument = choiceArguments.get(i);
                        if(!multiArgument.required()) continue;
                        multiChoiceStringBuilder.append(multiArgument.label().toLowerCase()).append(i < choiceArguments.size() - 1 ? "|" : "");
                    }
                    stringBuilder.append("<").append(multiChoiceStringBuilder).append("> ");
                } else stringBuilder.append("<").append(argument.label().toLowerCase()).append("> ");
            }
        }
        return stringBuilder.toString();
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public String[] aliases() {
        return aliases;
    }
}
