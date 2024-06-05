// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import com.mojang.authlib.exceptions.AuthenticationException;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import net.minecraft.util.Session;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.client.Minecraft;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "Login", description = "Log in to a mojangle account", usage = ".login <username>:<password> || .login <username> <password>", aliases = {})
public final class LoginCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        if (args.length < 1) {
            this.printUsage();
        }
        else if (args.length > 1) {
            try {
                final YggdrasilUserAuthentication userAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
                userAuthentication.setUsername(args[0]);
                userAuthentication.setPassword(args[1]);
                userAuthentication.logIn();
                final String username = userAuthentication.getSelectedProfile().getName();
                Minecraft.getMinecraft().session = new Session(username, UUIDTypeAdapter.fromUUID(userAuthentication.getSelectedProfile().getId()), userAuthentication.getAuthenticatedToken(), userAuthentication.getUserType().getName());
                ChatUtils.addChatMessage("Logged into " + username);
            }
            catch (final AuthenticationException ignored) {
                ignored.printStackTrace();
            }
        }
        else {
            try {
                final YggdrasilUserAuthentication userAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
                final String[] trol = args[0].split(":");
                if (trol.length > 1) {
                    userAuthentication.setUsername(trol[0]);
                    userAuthentication.setPassword(trol[1]);
                }
                userAuthentication.logIn();
                final String username2 = userAuthentication.getSelectedProfile().getName();
                Minecraft.getMinecraft().session = new Session(username2, UUIDTypeAdapter.fromUUID(userAuthentication.getSelectedProfile().getId()), userAuthentication.getAuthenticatedToken(), userAuthentication.getUserType().getName());
                ChatUtils.addChatMessage("Logged into " + username2);
            }
            catch (final AuthenticationException ignored) {
                ignored.printStackTrace();
            }
        }
    }
}
