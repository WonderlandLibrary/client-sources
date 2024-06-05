/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.core.registry.impl;

import digital.rbq.command.CommandManager;
import digital.rbq.config.ConfigManager;
import digital.rbq.core.registry.Registry;
import digital.rbq.file.FileManager;
import digital.rbq.friend.FriendManager;
import digital.rbq.module.ModuleManager;

public final class ManagerRegistry
implements Registry {
    public final ConfigManager configManager = new ConfigManager();
    public final FileManager fileManager = new FileManager();
    public final FriendManager friendManager = new FriendManager();
    public final ModuleManager moduleManager = new ModuleManager();
    public final CommandManager commandManager = new CommandManager();
}

