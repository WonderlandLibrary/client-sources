package cn.Hanabi.command;

import org.jetbrains.annotations.*;
import ClassSub.*;
import java.util.*;

public class Checkuser extends Command
{
    
    
    public Checkuser() {
        super("checkuser", new String[0]);
    }
    
    @Override
    public void run(final String s, @NotNull final String[] array) {
        if (Class334.isDebugMode || Class334.isMod) {
            for (final Class194 class194 : Class194.userList) {
                Class200.tellPlayer("[LOCAL-USER]用户�?:" + class194.username + " 游戏�?:" + class194.inGamename + " 客户�?" + class194.userType.getClientName());
            }
        }
    }
    
    @Override
    public List<String> autocomplete(final int n, final String[] array) {
        return new ArrayList<String>();
    }
}
