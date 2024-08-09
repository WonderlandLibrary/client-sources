/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl.feature;

import java.util.List;
import mpp.venusfr.command.Command;
import mpp.venusfr.command.CommandWithAdvice;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.Parameters;
import mpp.venusfr.command.Prefix;
import mpp.venusfr.command.friends.FriendStorage;
import mpp.venusfr.command.impl.CommandException;
import mpp.venusfr.utils.player.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;

public class FriendCommand
implements Command,
CommandWithAdvice {
    private final Prefix prefix;
    private final Logger logger;
    private final Minecraft mc;

    @Override
    public void execute(Parameters parameters) {
        String string;
        switch (string = (String)parameters.asString(0).orElseThrow()) {
            case "add": {
                this.addFriend(parameters, this.logger);
                break;
            }
            case "remove": {
                this.removeFriend(parameters, this.logger);
                break;
            }
            case "clear": {
                this.clearFriendList(this.logger);
                break;
            }
            case "list": {
                this.getFriendList(this.logger);
                break;
            }
            default: {
                throw new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0442\u0438\u043f \u043a\u043e\u043c\u0430\u043d\u0434\u044b:" + TextFormatting.GRAY + " add, remove, clear, list");
            }
        }
    }

    @Override
    public String name() {
        return "friend";
    }

    @Override
    public String description() {
        return "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0443\u043f\u0440\u0430\u0432\u043b\u044f\u0442\u044c \u0441\u043f\u0438\u0441\u043a\u043e\u043c \u0434\u0440\u0443\u0437\u0435\u0439";
    }

    @Override
    public List<String> adviceMessage() {
        String string = this.prefix.get();
        return List.of((Object)(string + "friend add <name> - \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0434\u0440\u0443\u0433\u0430 \u043f\u043e \u0438\u043c\u0435\u043d\u0438"), (Object)(string + "friend remove <name> - \u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0434\u0440\u0443\u0433\u0430 \u043f\u043e \u0438\u043c\u0435\u043d\u0438"), (Object)(string + "friend list - \u041f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439"), (Object)(string + "friend clear - \u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439"), (Object)("\u041f\u0440\u0438\u043c\u0435\u0440: " + TextFormatting.RED + string + "friend add 4iter"));
    }

    private void addFriend(Parameters parameters, Logger logger) {
        String string = parameters.asString(1).orElseThrow(FriendCommand::lambda$addFriend$0);
        if (!PlayerUtils.isNameValid(string)) {
            logger.log(TextFormatting.RED + "\u041d\u0435\u0434\u043e\u043f\u0443\u0441\u0442\u0438\u043c\u043e\u0435 \u0438\u043c\u044f.");
            return;
        }
        if (string.equalsIgnoreCase(this.mc.player.getName().getString())) {
            logger.log(TextFormatting.RED + "\u0412\u044b \u043d\u0435 \u043c\u043e\u0436\u0435\u0442\u0435 \u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0441\u0435\u0431\u044f \u0432 \u0434\u0440\u0443\u0437\u044c\u044f, \u043a\u0430\u043a \u0431\u044b \u0432\u0430\u043c \u043d\u0435 \u0445\u043e\u0442\u0435\u043b\u043e\u0441\u044c");
            return;
        }
        if (FriendStorage.isFriend(string)) {
            logger.log(TextFormatting.RED + "\u042d\u0442\u043e\u0442 \u0438\u0433\u0440\u043e\u043a \u0443\u0436\u0435 \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u0432\u0430\u0448\u0435\u043c \u0441\u043f\u0438\u0441\u043a\u0435 \u0434\u0440\u0443\u0437\u0435\u0439.");
            return;
        }
        FriendStorage.add(string);
        logger.log(TextFormatting.GRAY + "\u0412\u044b \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0434\u043e\u0431\u0430\u0432\u0438\u043b\u0438 " + TextFormatting.GRAY + string + TextFormatting.GRAY + " \u0432 \u0434\u0440\u0443\u0437\u044c\u044f!");
    }

    private void removeFriend(Parameters parameters, Logger logger) {
        String string = parameters.asString(1).orElseThrow(FriendCommand::lambda$removeFriend$1);
        if (FriendStorage.isFriend(string)) {
            FriendStorage.remove(string);
            logger.log(TextFormatting.GRAY + "\u0412\u044b \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0443\u0434\u0430\u043b\u0438\u043b\u0438 " + TextFormatting.GRAY + string + TextFormatting.GRAY + " \u0438\u0437 \u0434\u0440\u0443\u0437\u0435\u0439!");
            return;
        }
        logger.log(TextFormatting.RED + string + " \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d \u0432 \u0441\u043f\u0438\u0441\u043a\u0435 \u0434\u0440\u0443\u0437\u0435\u0439");
    }

    private void getFriendList(Logger logger) {
        if (FriendStorage.getFriends().isEmpty()) {
            logger.log(TextFormatting.RED + "\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439 \u043f\u0443\u0441\u0442\u043e\u0439.");
            return;
        }
        logger.log(TextFormatting.GRAY + "\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439:");
        for (String string : FriendStorage.getFriends()) {
            logger.log(TextFormatting.GRAY + string);
        }
    }

    private void clearFriendList(Logger logger) {
        if (FriendStorage.getFriends().isEmpty()) {
            logger.log(TextFormatting.RED + "\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439 \u043f\u0443\u0441\u0442\u043e\u0439.");
            return;
        }
        FriendStorage.clear();
        logger.log(TextFormatting.GRAY + "\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439 \u043e\u0447\u0438\u0449\u0435\u043d.");
    }

    public FriendCommand(Prefix prefix, Logger logger, Minecraft minecraft) {
        this.prefix = prefix;
        this.logger = logger;
        this.mc = minecraft;
    }

    private static CommandException lambda$removeFriend$1() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0438\u043c\u044f \u0434\u0440\u0443\u0433\u0430 \u0434\u043b\u044f \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0438\u044f/\u0443\u0434\u0430\u043b\u0435\u043d\u0438\u044f.");
    }

    private static CommandException lambda$addFriend$0() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0438\u043c\u044f \u0434\u0440\u0443\u0433\u0430 \u0434\u043b\u044f \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0438\u044f/\u0443\u0434\u0430\u043b\u0435\u043d\u0438\u044f.");
    }
}

