package net.shoreline.client.impl.command;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.arguments.ConfigArgument;
import net.shoreline.client.api.command.arg.arguments.StringArgument;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.macro.Macro;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.util.KeyboardUtil;
import net.shoreline.client.util.chat.ChatUtil;
import net.shoreline.client.util.ClientIdentifier;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class ModuleCommand extends Command
{
    //
    Argument<Config<?>> configArgument = new ConfigArgument("Config", "The " +
            "setting to configure");
    Argument<String> valueArgument = new StringArgument("Value", "The new " +
            "value to set the config");

    /**
     *
     * @param module
     */
    public ModuleCommand(Module module)
    {
        super(module.getName(), "Configures the module");
        // DO NOT REMOVE THIS - linus
        configArgument.setContainer(module);
    }

    /**
     * Runs when the command is inputted in chat
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onCommandInput()
    {
        Config<?> config = configArgument.getValue();
        if (config != null)
        {
            String value = valueArgument.getValue();
            if (value == null)
            {
                return;
            }
            // parse value
            try
            {
                if (config.getValue() instanceof Integer)
                {
                    ((Config<Integer>) config).setValue(Integer.parseInt(value));
                }
                else if (config.getValue() instanceof Float)
                {
                    ((Config<Float>) config).setValue(Float.parseFloat(value));
                }
                else if (config.getValue() instanceof Double)
                {
                    ((Config<Double>) config).setValue(Double.parseDouble(value));
                }
            }
            catch (NumberFormatException e)
            {
                ChatUtil.error("Not a number!");
                // e.printStackTrace();
            }
            if (config.getValue() instanceof Boolean)
            {
                ((Config<Boolean>) config).setValue(Boolean.parseBoolean(value));
            }
            else if (config.getValue() instanceof Enum<?>)
            {
                String[] values = Arrays.stream(((Enum<?>) config.getValue()).getClass()
                        .getEnumConstants()).map(Enum::name).toArray(String[]::new);
                // TODO: FIX THIS!
                int ix = -1;
                for (int i = 0; i < values.length; i++)
                {
                    if (values[i].equalsIgnoreCase(value))
                    {
                        ix = i;
                        break;
                    }
                }
                if (ix == -1)
                {
                    ChatUtil.error("Not a valid mode!");
                    return;
                }
                Enum<?> val = Enum.valueOf(((Enum<?>) config.getValue())
                        .getClass(), values[ix]);
                ((Config<Enum<?>>) config).setValue(val);
            }
            else if (config.getValue() instanceof List<?>)
            {
                if (value.startsWith("item"))
                {
                    Item item = Registries.ITEM.get(ClientIdentifier.toId(value));
                    ((Config<List<Item>>) config).getValue().add(item);
                }
                else if (value.startsWith("block"))
                {
                    Block block = Registries.BLOCK.get(ClientIdentifier.toId(value));
                    ((Config<List<Block>>) config).getValue().add(block);
                }
                ChatUtil.clientSendMessage("%s was added to §7%s§f!", value,
                        config.getName());
                return;
            }
            else if (config.getValue() instanceof Macro macro)
            {
                if (config.getName().equalsIgnoreCase("Keybind"))
                {
                    ChatUtil.error("Use the 'bind' command to keybind modules!");
                    return;
                }
                ((Config<Macro>) config).setValue(new Macro(config.getId(),
                        KeyboardUtil.getKeyCode(value), macro.getRunnable()));
            }
            else if (config.getValue() instanceof String)
            {
                ((Config<String>) config).setValue(value);
            }
            ChatUtil.clientSendMessage("§7%s§f was set to %s!", config.getName(), value);
        }
    }
}
