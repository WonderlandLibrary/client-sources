// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.commands;

import net.minecraft.client.triton.management.command.Com;
import net.minecraft.client.triton.management.command.Command;
import net.minecraft.client.triton.management.enemies.EnemyManager;
import net.minecraft.client.triton.utils.ClientUtils;

@Com(names = { "Enemy", "e" })
public class Enemy extends Command
{
    @Override
    public void runCommand(final String[] args) {
        if (args.length < 3) {
            ClientUtils.sendMessage(this.getHelp());
            return;
        }
        if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("a")) {
            String alias = args[2];
            if (args.length > 3) {
                alias = args[3];
                if (alias.startsWith("\"") && args[args.length - 1].endsWith("\"")) {
                    alias = alias.substring(1, alias.length());
                    for (int i = 4; i < args.length; ++i) {
                        alias = String.valueOf(alias) + " " + args[i].replace("\"", "");
                    }
                }
            }
            if (EnemyManager.isEnemy(args[2]) && args.length < 3) {
                ClientUtils.sendMessage(String.valueOf(args[2]) + " is already an Enemy.");
                return;
            }
            EnemyManager.removeEnemy(args[2]);
            EnemyManager.addEnemy(args[2], alias);
            ClientUtils.sendMessage("Added " + args[2] + ((args.length > 3) ? (" as " + alias) : ""));
        }
        else if (args[1].equalsIgnoreCase("del") || args[1].equalsIgnoreCase("d")) {
            if (EnemyManager.isEnemy(args[2])) {
                EnemyManager.removeEnemy(args[2]);
                ClientUtils.sendMessage("Removed Enemy: " + args[2]);
            }
            else {
                ClientUtils.sendMessage(String.valueOf(args[2]) + " is not an Enemy.");
            }
        }
        else {
            ClientUtils.sendMessage(this.getHelp());
        }
    }
    
    @Override
    public String getHelp() {
        return "Enemy - Enemy <f>  (add <a> | del <d>) (name) [alias | \"alias w/ spaces\"].";
    }
}
