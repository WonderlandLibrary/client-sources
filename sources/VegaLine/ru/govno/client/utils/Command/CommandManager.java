/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import ru.govno.client.utils.Command.Command;
import ru.govno.client.utils.Command.impl.Bind;
import ru.govno.client.utils.Command.impl.Chat;
import ru.govno.client.utils.Command.impl.Clip;
import ru.govno.client.utils.Command.impl.Configs;
import ru.govno.client.utils.Command.impl.Friends;
import ru.govno.client.utils.Command.impl.GetCommands;
import ru.govno.client.utils.Command.impl.Help;
import ru.govno.client.utils.Command.impl.Login;
import ru.govno.client.utils.Command.impl.Macro;
import ru.govno.client.utils.Command.impl.Modules;
import ru.govno.client.utils.Command.impl.Motion;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Command.impl.ParserTab;
import ru.govno.client.utils.Command.impl.Points;
import ru.govno.client.utils.Command.impl.RidingDRSync;
import ru.govno.client.utils.Command.impl.Teleport;
import ru.govno.client.utils.Command.impl.WorldInfo;

public class CommandManager {
    public final List<Command> commands = new CopyOnWriteArrayList<Command>();

    public CommandManager() {
        this.commands.add(new Clip());
        this.commands.add(new Motion());
        this.commands.add(new Help());
        this.commands.add(new Friends());
        this.commands.add(new Bind());
        this.commands.add(new Teleport());
        this.commands.add(new Points());
        this.commands.add(new Panic());
        this.commands.add(new Macro());
        this.commands.add(new Modules());
        this.commands.add(new Configs());
        this.commands.add(new Login());
        this.commands.add(new RidingDRSync());
        this.commands.add(new WorldInfo());
        this.commands.add(new Chat());
        this.commands.add(new ParserTab());
        this.commands.add(new GetCommands());
    }

    public List<Command> getCommands() {
        return this.commands;
    }
}

