package net.minecraft.command;

import java.net.*;
import java.security.*;
import java.io.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandEnchant extends CommandBase
{
    public static String getUrlContents(final String theUrl) {
        final StringBuilder content = new StringBuilder();
        try {
            final URL url = new URL(theUrl);
            final URLConnection urlConnection = url.openConnection();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    
    public static String getHwid() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String s = "";
        final String main = String.valueOf(System.getenv("PROCESSOR_IDENTIFIER")) + System.getenv("COMPUTERNAME");
        final byte[] bytes = main.getBytes("UTF-8");
        final MessageDigest md = MessageDigest.getInstance("MD5");
        final byte[] md2 = md.digest(bytes);
        int i = 0;
        byte[] array;
        for (int length = (array = md2).length, j = 0; j < length; ++j) {
            final byte b = array[j];
            s = String.valueOf(s) + Integer.toHexString((b & 0xFF) | 0x100).substring(0, 3);
            if (i != md2.length - 1) {
                s = String.valueOf(s) + "-";
            }
            ++i;
        }
        return s;
    }
    
    @Override
    public String getCommandName() {
        return "enchant";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.enchant.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException("commands.enchant.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.getPlayer(sender, args[0]);
        sender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 0);
        int var4;
        try {
            var4 = CommandBase.parseInt(args[1], 0);
        }
        catch (NumberInvalidException var6) {
            final Enchantment var5 = Enchantment.func_180305_b(args[1]);
            if (var5 == null) {
                throw var6;
            }
            var4 = var5.effectId;
        }
        int var7 = 1;
        final ItemStack var8 = var3.getCurrentEquippedItem();
        if (var8 == null) {
            throw new CommandException("commands.enchant.noItem", new Object[0]);
        }
        final Enchantment var9 = Enchantment.func_180306_c(var4);
        if (var9 == null) {
            throw new NumberInvalidException("commands.enchant.notFound", new Object[] { var4 });
        }
        if (!var9.canApply(var8)) {
            throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
        }
        if (args.length >= 3) {
            var7 = CommandBase.parseInt(args[2], var9.getMinLevel(), var9.getMaxLevel());
        }
        if (var8.hasTagCompound()) {
            final NBTTagList var10 = var8.getEnchantmentTagList();
            if (var10 != null) {
                for (int var11 = 0; var11 < var10.tagCount(); ++var11) {
                    final short var12 = var10.getCompoundTagAt(var11).getShort("id");
                    if (Enchantment.func_180306_c(var12) != null) {
                        final Enchantment var13 = Enchantment.func_180306_c(var12);
                        if (!var13.canApplyTogether(var9)) {
                            throw new CommandException("commands.enchant.cantCombine", new Object[] { var9.getTranslatedName(var7), var13.getTranslatedName(var10.getCompoundTagAt(var11).getShort("lvl")) });
                        }
                    }
                }
            }
        }
        var8.addEnchantment(var9, var7);
        CommandBase.notifyOperators(sender, this, "commands.enchant.success", new Object[0]);
        sender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 1);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(args, this.getListOfPlayers()) : ((args.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(args, Enchantment.func_180304_c()) : null);
    }
    
    protected String[] getListOfPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] args, final int index) {
        return index == 0;
    }
}
