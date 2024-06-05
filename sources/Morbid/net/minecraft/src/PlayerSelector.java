package net.minecraft.src;

import net.minecraft.server.*;
import java.util.regex.*;
import java.util.*;

public class PlayerSelector
{
    private static final Pattern tokenPattern;
    private static final Pattern intListPattern;
    private static final Pattern keyValueListPattern;
    
    static {
        tokenPattern = Pattern.compile("^@([parf])(?:\\[([\\w=,!-]*)\\])?$");
        intListPattern = Pattern.compile("\\G([-!]?\\w*)(?:$|,)");
        keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?\\w*)(?:$|,)");
    }
    
    public static EntityPlayerMP matchOnePlayer(final ICommandSender par0ICommandSender, final String par1Str) {
        final EntityPlayerMP[] var2 = matchPlayers(par0ICommandSender, par1Str);
        return (var2 != null && var2.length == 1) ? var2[0] : null;
    }
    
    public static String matchPlayersAsString(final ICommandSender par0ICommandSender, final String par1Str) {
        final EntityPlayerMP[] var2 = matchPlayers(par0ICommandSender, par1Str);
        if (var2 != null && var2.length != 0) {
            final String[] var3 = new String[var2.length];
            for (int var4 = 0; var4 < var3.length; ++var4) {
                var3[var4] = var2[var4].getTranslatedEntityName();
            }
            return CommandBase.joinNiceString(var3);
        }
        return null;
    }
    
    public static EntityPlayerMP[] matchPlayers(final ICommandSender par0ICommandSender, final String par1Str) {
        final Matcher var2 = PlayerSelector.tokenPattern.matcher(par1Str);
        if (!var2.matches()) {
            return null;
        }
        final Map var3 = getArgumentMap(var2.group(2));
        final String var4 = var2.group(1);
        int var5 = getDefaultMinimumRange(var4);
        int var6 = getDefaultMaximumRange(var4);
        int var7 = getDefaultMinimumLevel(var4);
        int var8 = getDefaultMaximumLevel(var4);
        int var9 = getDefaultCount(var4);
        int var10 = EnumGameType.NOT_SET.getID();
        final ChunkCoordinates var11 = par0ICommandSender.getPlayerCoordinates();
        final Map var12 = func_96560_a(var3);
        String var13 = null;
        String var14 = null;
        if (var3.containsKey("rm")) {
            var5 = MathHelper.parseIntWithDefault(var3.get("rm"), var5);
        }
        if (var3.containsKey("r")) {
            var6 = MathHelper.parseIntWithDefault(var3.get("r"), var6);
        }
        if (var3.containsKey("lm")) {
            var7 = MathHelper.parseIntWithDefault(var3.get("lm"), var7);
        }
        if (var3.containsKey("l")) {
            var8 = MathHelper.parseIntWithDefault(var3.get("l"), var8);
        }
        if (var3.containsKey("x")) {
            var11.posX = MathHelper.parseIntWithDefault(var3.get("x"), var11.posX);
        }
        if (var3.containsKey("y")) {
            var11.posY = MathHelper.parseIntWithDefault(var3.get("y"), var11.posY);
        }
        if (var3.containsKey("z")) {
            var11.posZ = MathHelper.parseIntWithDefault(var3.get("z"), var11.posZ);
        }
        if (var3.containsKey("m")) {
            var10 = MathHelper.parseIntWithDefault(var3.get("m"), var10);
        }
        if (var3.containsKey("c")) {
            var9 = MathHelper.parseIntWithDefault(var3.get("c"), var9);
        }
        if (var3.containsKey("team")) {
            var14 = var3.get("team");
        }
        if (var3.containsKey("name")) {
            var13 = var3.get("name");
        }
        if (var4.equals("p") || var4.equals("a")) {
            final List var15 = MinecraftServer.getServer().getConfigurationManager().findPlayers(var11, var5, var6, var9, var10, var7, var8, var12, var13, var14);
            return (var15 != null && !var15.isEmpty()) ? var15.toArray(new EntityPlayerMP[0]) : new EntityPlayerMP[0];
        }
        if (!var4.equals("r")) {
            return null;
        }
        List var15 = MinecraftServer.getServer().getConfigurationManager().findPlayers(var11, var5, var6, 0, var10, var7, var8, var12, var13, var14);
        Collections.shuffle(var15);
        var15 = var15.subList(0, Math.min(var9, var15.size()));
        return (var15 != null && !var15.isEmpty()) ? var15.toArray(new EntityPlayerMP[0]) : new EntityPlayerMP[0];
    }
    
    public static Map func_96560_a(final Map par0Map) {
        final HashMap var1 = new HashMap();
        for (final String var3 : par0Map.keySet()) {
            if (var3.startsWith("score_") && var3.length() > "score_".length()) {
                final String var4 = var3.substring("score_".length());
                var1.put(var4, MathHelper.parseIntWithDefault(par0Map.get(var3), 1));
            }
        }
        return var1;
    }
    
    public static boolean matchesMultiplePlayers(final String par0Str) {
        final Matcher var1 = PlayerSelector.tokenPattern.matcher(par0Str);
        if (var1.matches()) {
            final Map var2 = getArgumentMap(var1.group(2));
            final String var3 = var1.group(1);
            int var4 = getDefaultCount(var3);
            if (var2.containsKey("c")) {
                var4 = MathHelper.parseIntWithDefault(var2.get("c"), var4);
            }
            return var4 != 1;
        }
        return false;
    }
    
    public static boolean hasTheseArguments(final String par0Str, final String par1Str) {
        final Matcher var2 = PlayerSelector.tokenPattern.matcher(par0Str);
        if (!var2.matches()) {
            return false;
        }
        final String var3 = var2.group(1);
        return par1Str == null || par1Str.equals(var3);
    }
    
    public static boolean hasArguments(final String par0Str) {
        return hasTheseArguments(par0Str, null);
    }
    
    private static final int getDefaultMinimumRange(final String par0Str) {
        return 0;
    }
    
    private static final int getDefaultMaximumRange(final String par0Str) {
        return 0;
    }
    
    private static final int getDefaultMaximumLevel(final String par0Str) {
        return Integer.MAX_VALUE;
    }
    
    private static final int getDefaultMinimumLevel(final String par0Str) {
        return 0;
    }
    
    private static final int getDefaultCount(final String par0Str) {
        return par0Str.equals("a") ? 0 : 1;
    }
    
    private static Map getArgumentMap(final String par0Str) {
        final HashMap var1 = new HashMap();
        if (par0Str == null) {
            return var1;
        }
        Matcher var2 = PlayerSelector.intListPattern.matcher(par0Str);
        int var3 = 0;
        int var4 = -1;
        while (var2.find()) {
            String var5 = null;
            switch (var3++) {
                case 0: {
                    var5 = "x";
                    break;
                }
                case 1: {
                    var5 = "y";
                    break;
                }
                case 2: {
                    var5 = "z";
                    break;
                }
                case 3: {
                    var5 = "r";
                    break;
                }
            }
            if (var5 != null && var2.group(1).length() > 0) {
                var1.put(var5, var2.group(1));
            }
            var4 = var2.end();
        }
        if (var4 < par0Str.length()) {
            var2 = PlayerSelector.keyValueListPattern.matcher((var4 == -1) ? par0Str : par0Str.substring(var4));
            while (var2.find()) {
                var1.put(var2.group(1), var2.group(2));
            }
        }
        return var1;
    }
}
