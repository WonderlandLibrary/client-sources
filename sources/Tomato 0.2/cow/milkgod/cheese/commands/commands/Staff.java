/*
 * Decompiled with CFR 0_115.
 */
package cow.milkgod.cheese.commands.commands;

import com.darkmagician6.eventapi.EventTarget;
import cow.milkgod.cheese.commands.Command;
import cow.milkgod.cheese.events.EventChatSend;
import cow.milkgod.cheese.events.EventTick;
import cow.milkgod.cheese.people.StaffManager;
import cow.milkgod.cheese.utils.Logger;
import java.util.ArrayList;

public class Staff
extends Command {
    public static boolean smart = false;

    public Staff() {
        super("Staff", "U fucked up! ", new String[]{"st"}, "<smart | add | remove | clear>", "Manages the staff shit.");
    }

    @EventTarget
    public void onChatSend(EventTick event) {
        if (this.getState()) {
            block14 : {
                try {
                    String message = EventChatSend.getMessage().split(" ")[1];
                    if (message.equalsIgnoreCase("auto") || message.equalsIgnoreCase("smart")) {
                        smart = !smart;
                        Logger.logChat("Smart anti staff nigger pussy bitch shit has been" + (smart ? " \u00a7aenabled" : " \u00a7cdisabled\u00a77."));
                        break block14;
                    }
                    if (message.equalsIgnoreCase("add")) {
                        try {
                            String name = EventChatSend.getMessage().split(" ")[2];
                            if (!StaffManager.isStaff(name)) {
                                StaffManager.addStaffMember(name);
                                Logger.logChat("You have added \u00a7e" + name + " \u00a77to your Staff list.");
                                break block14;
                            }
                            Logger.logChat("That player is already on your Staff  \u00a77list or isn't  \u00a77online!");
                        }
                        catch (Exception e) {
                            Logger.logChat("U fucked up! Staff add <name>");
                        }
                        break block14;
                    }
                    if (message.equalsIgnoreCase("remove")) {
                        try {
                            String name = EventChatSend.getMessage().split(" ")[2];
                            if (StaffManager.isStaff(name)) {
                                StaffManager.deleteStaffMember(name);
                                Logger.logChat("You have removed \u00a7e" + name + "\u00a77 from your Staff list.");
                                break block14;
                            }
                            Logger.logChat(String.valueOf(String.valueOf(name)) + " is not a Staff Member!");
                        }
                        catch (Exception e) {
                            Logger.logChat("U fucked up! Staff remove <name>");
                        }
                        break block14;
                    }
                    if (message.equalsIgnoreCase("clear")) {
                        StaffManager.Smembers.clear();
                        Logger.logChat("Cleared Staff Members!");
                    } else {
                        Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                    }
                }
                catch (Exception e2) {
                    Logger.logChat(String.valueOf(String.valueOf(this.getErrorMessage())) + this.getArguments());
                }
            }
            this.Toggle();
        }
    }
}

