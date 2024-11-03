package com.minus.commands;

import com.minus.Minus;
import com.minus.commands.implementations.fun.Say;
import com.minus.event.events.client.EventOnChatSend;
import com.minus.utils.ChatUtils;
import lombok.Getter;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import me.zero.alpine.listener.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class CommandManager implements Subscriber {
    private final List<Command> commands = new ArrayList<>();
    public CommandManager(){
        commands.addAll(Arrays.asList(
                new Say()
        ));
        Minus.instance.getEventBus().subscribe(this);
    }
    @Subscribe
    public final Listener<EventOnChatSend> chatSendListener = new Listener<>(e -> {
        if (e.getInput() != null | !e.getInput().isEmpty() |  e.getInput().startsWith(".") | e.getInput().length() > 1 | !e.getInput().startsWith("..")){
            e.cancel();
            final String rawCommand = e.getInput().substring(1);
            final String[] processedCommand = rawCommand.split(" ");
            for (Command c : commands) {
                for (String s : c.getCommandInfo().names()) {
                    if (s.equalsIgnoreCase(processedCommand[0])) {
                        c.onCall(processedCommand);
                        return;
                    }
                }
            }
            ChatUtils.addMessage("Command not found. Please contact helpers in our discord server >.<");
        }
    });
}
