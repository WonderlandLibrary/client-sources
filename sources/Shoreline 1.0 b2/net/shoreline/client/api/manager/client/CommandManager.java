package net.shoreline.client.api.manager.client;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.ArgumentParseException;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.impl.command.*;
import net.shoreline.client.impl.event.gui.chat.ChatInputEvent;
import net.shoreline.client.impl.event.gui.chat.ChatKeyInputEvent;
import net.shoreline.client.impl.event.gui.chat.ChatMessageEvent;
import net.shoreline.client.impl.event.gui.chat.ChatRenderEvent;
import net.shoreline.client.impl.event.keyboard.KeyboardInputEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.Globals;
import net.shoreline.client.util.chat.ChatUtil;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Command
 */
public class CommandManager implements Globals
{
    //
    private final List<Command> commands = new ArrayList<>();
    //
    private StringBuilder chatAutocomplete = new StringBuilder();
    // Command prefix, used to identify a command in the chat
    private String prefix = ".";
    private int prefixKey = GLFW.GLFW_KEY_PERIOD;

    /**
     * Registers commands to the CommandManager
     */
    public CommandManager()
    {
        Shoreline.EVENT_HANDLER.subscribe(this);
        register(
                new HelpCommand(),
                new ToggleCommand(),
                new FriendCommand(),
                new HClipCommand(),
                new VClipCommand(),
                new PrefixCommand(),
                new DrawnCommand(),
                new OpenFolderCommand(),
                new BindCommand(),
                new VanishCommand()
        );
        //
        for (Module module : Managers.MODULE.getModules())
        {
            register(new ModuleCommand(module));
        }
        Shoreline.info("Registered {} commands!", commands.size());
    }

    /**
     * Reflects arguments and assigns them to their respective commands
     */
    public void postInit()
    {
        // get args
        for (Command command : commands)
        {
            command.reflectConfigs();
        }
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onChatInput(ChatInputEvent event)
    {
        chatAutocomplete = new StringBuilder();
        final String text = event.getChatText().trim();
        if (!text.startsWith(prefix))
        {
            return;
        }
        String literal = text.substring(1);
        String[] args = literal.split(" ");
        //
        for (Command command : getCommands())
        {
            String name = command.getName();
            if (name.equals(args[0]))
            {
                chatAutocomplete.append(args[0]);
                chatAutocomplete.append(" ");
                for (int i = 1; i < args.length; i++)
                {
                    Argument<?> arg = command.getArg(i - 1);
                    if (arg == null)
                    {
                        // ChatUtil.error("Too many arguments!");
                        break;
                    }
                    arg.setLiteral(args[i]);
                    if (i < args.length - 1)
                    {
                        chatAutocomplete.append(arg.getLiteral());
                    }
                    else
                    {
                        chatAutocomplete.append(arg.getSuggestion());
                    }
                    chatAutocomplete.append(" ");
                }
                break;
            }
            else if (name.startsWith(args[0]))
            {
                chatAutocomplete.append(command.getName());
                break;
            }
        }
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onClientChatMessage(ChatMessageEvent.Client event)
    {
        String msg = event.getMessage().trim();
        if (msg.startsWith(prefix))
        {
            event.cancel();
            mc.inGameHud.getChatHud().addToMessageHistory(msg);
            //
            String literal = msg.substring(1);
            String[] args = literal.split(" ");
            for (Command command : getCommands())
            {
                String name = command.getName();
                if (name.equals(args[0]))
                {
                    if (!command.isValidArgLength(args.length))
                    {
                        ChatUtil.error("Invalid usage! Usage: %s",
                                command.getUsage());
                        return;
                    }
                    try
                    {
                        command.onCommandInput();
                        chatAutocomplete = new StringBuilder();
                    }
                    catch (ArgumentParseException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onKeyboardInput(KeyboardInputEvent event)
    {
        if (mc.player == null || mc.world == null
                || mc.currentScreen instanceof ChatScreen)
        {
            return;
        }
        if (event.getKeycode() == prefixKey)
        {
            event.cancel();
            mc.setScreen(new ChatScreen(""));
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onChatKeyInput(ChatKeyInputEvent event)
    {
        // autocomplete
        if (event.getKeycode() == GLFW.GLFW_KEY_TAB)
        {
            String msg = event.getChatText().trim();
            if (msg.startsWith(prefix))
            {
                event.cancel();
                String literal = msg.substring(1);
                String[] args = literal.split(" ");
                for (Command command : getCommands())
                {
                    String name = command.getName();
                    if (args.length > 1 && name.equals(args[0]))
                    {
                        Argument<?> tail = command.getArg(args.length - 2);
                        if (tail == null || tail.getSuggestions().isEmpty())
                        {
                            ChatUtil.error("Unable to autocomplete!");
                            break;
                        }
                        tail.completeLiteral();
                        event.setChatText(command.getLiteral(prefix));
                        break;
                    }
                    else if (name.startsWith(args[0]))
                    {
                        event.setChatText(prefix + name + " ");
                        break;
                    }
                }
            }
        }
    }

    /**
     *
     *
     * @param event
     */
    @EventListener
    public void onChatRender(ChatRenderEvent event)
    {
        mc.textRenderer.drawWithShadow(event.getMatrices(),
                chatAutocomplete.toString(), event.getX(), event.getY(), 0xff808080);
    }

    /**
     *
     *
     * @param commands
     */
    private void register(Command... commands)
    {
        for (Command command : commands)
        {
            register(command);
        }
    }

    /**
     *
     *
     * @param command
     */
    private void register(Command command)
    {
        commands.add(command);
    }

    /**
     *
     *
     * @return
     */
    public List<Command> getCommands()
    {
        return commands;
    }

    /**
     *
     * @param prefix
     * @param prefixKey
     */
    public void setPrefix(String prefix, int prefixKey)
    {
        this.prefix = prefix;
        this.prefixKey = prefixKey;
    }
}
