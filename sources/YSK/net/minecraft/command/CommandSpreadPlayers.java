package net.minecraft.command;

import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.material.*;

public class CommandSpreadPlayers extends CommandBase
{
    private static final String[] I;
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandSpreadPlayers.I[" ".length()];
    }
    
    private int func_110667_a(final List<Entity> list) {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<Entity> iterator = list.iterator();
        "".length();
        if (3 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Entity entity = iterator.next();
            if (entity instanceof EntityPlayer) {
                hashSet.add(((EntityPlayer)entity).getTeam());
                "".length();
                if (1 <= 0) {
                    throw null;
                }
                continue;
            }
            else {
                hashSet.add(null);
            }
        }
        return hashSet.size();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < (0x34 ^ 0x32)) {
            throw new WrongUsageException(CommandSpreadPlayers.I["  ".length()], new Object["".length()]);
        }
        int i = "".length();
        final BlockPos position = commandSender.getPosition();
        final double double1 = CommandBase.parseDouble(position.getX(), array[i++], " ".length() != 0);
        final double double2 = CommandBase.parseDouble(position.getZ(), array[i++], " ".length() != 0);
        final double double3 = CommandBase.parseDouble(array[i++], 0.0);
        final double double4 = CommandBase.parseDouble(array[i++], double3 + 1.0);
        final boolean boolean1 = CommandBase.parseBoolean(array[i++]);
        final ArrayList arrayList = Lists.newArrayList();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < array.length) {
            final String s = array[i++];
            if (PlayerSelector.hasArguments(s)) {
                final List<Entity> matchEntities = PlayerSelector.matchEntities(commandSender, s, (Class<? extends Entity>)Entity.class);
                if (matchEntities.size() == 0) {
                    throw new EntityNotFoundException();
                }
                arrayList.addAll(matchEntities);
                "".length();
                if (2 == 4) {
                    throw null;
                }
                continue;
            }
            else {
                final EntityPlayerMP playerByUsername = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(s);
                if (playerByUsername == null) {
                    throw new PlayerNotFoundException();
                }
                arrayList.add(playerByUsername);
            }
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, arrayList.size());
        if (arrayList.isEmpty()) {
            throw new EntityNotFoundException();
        }
        final StringBuilder sb = new StringBuilder(CommandSpreadPlayers.I["   ".length()]);
        String s2;
        if (boolean1) {
            s2 = CommandSpreadPlayers.I[0x56 ^ 0x52];
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            s2 = CommandSpreadPlayers.I[0x63 ^ 0x66];
        }
        final String string = sb.append(s2).toString();
        final Object[] array2 = new Object[0xF ^ 0xA];
        array2["".length()] = arrayList.size();
        array2[" ".length()] = double4;
        array2["  ".length()] = double1;
        array2["   ".length()] = double2;
        array2[0xA6 ^ 0xA2] = double3;
        commandSender.addChatMessage(new ChatComponentTranslation(string, array2));
        this.func_110669_a(commandSender, arrayList, new Position(double1, double2), double3, double4, ((EntityPlayerMP)arrayList.get("".length())).worldObj, boolean1);
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public String getCommandName() {
        return CommandSpreadPlayers.I["".length()];
    }
    
    private void func_110669_a(final ICommandSender commandSender, final List<Entity> list, final Position position, final double n, final double n2, final World world, final boolean b) throws CommandException {
        final Random random = new Random();
        final double n3 = position.field_111101_a - n2;
        final double n4 = position.field_111100_b - n2;
        final double n5 = position.field_111101_a + n2;
        final double n6 = position.field_111100_b + n2;
        final Random random2 = random;
        int n7;
        if (b) {
            n7 = this.func_110667_a(list);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            n7 = list.size();
        }
        final Position[] func_110670_a = this.func_110670_a(random2, n7, n3, n4, n5, n6);
        final int func_110668_a = this.func_110668_a(position, n, world, random, n3, n4, n5, n6, func_110670_a, b);
        final double func_110671_a = this.func_110671_a(list, world, func_110670_a, b);
        final StringBuilder sb = new StringBuilder(CommandSpreadPlayers.I[0x84 ^ 0x82]);
        String s;
        if (b) {
            s = CommandSpreadPlayers.I[0x4C ^ 0x4B];
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            s = CommandSpreadPlayers.I[0x3F ^ 0x37];
        }
        final String string = sb.append(s).toString();
        final Object[] array = new Object["   ".length()];
        array["".length()] = func_110670_a.length;
        array[" ".length()] = position.field_111101_a;
        array["  ".length()] = position.field_111100_b;
        CommandBase.notifyOperators(commandSender, this, string, array);
        if (func_110670_a.length > " ".length()) {
            final StringBuilder sb2 = new StringBuilder(CommandSpreadPlayers.I[0x43 ^ 0x4A]);
            String s2;
            if (b) {
                s2 = CommandSpreadPlayers.I[0x32 ^ 0x38];
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            else {
                s2 = CommandSpreadPlayers.I[0x7D ^ 0x76];
            }
            final String string2 = sb2.append(s2).toString();
            final Object[] array2 = new Object["  ".length()];
            final int length = "".length();
            final String s3 = CommandSpreadPlayers.I[0x5D ^ 0x51];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = func_110671_a;
            array2[length] = String.format(s3, array3);
            array2[" ".length()] = func_110668_a;
            commandSender.addChatMessage(new ChatComponentTranslation(string2, array2));
        }
    }
    
    static {
        I();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private int func_110668_a(final Position position, final double n, final World world, final Random random, final double n2, final double n3, final double n4, final double n5, final Position[] array, final boolean b) throws CommandException {
        int n6 = " ".length();
        double min = 3.4028234663852886E38;
        int length = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (length < 9156 + 9105 - 14169 + 5908 && n6 != 0) {
            n6 = "".length();
            min = 3.4028234663852886E38;
            int i = "".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
            while (i < array.length) {
                final Position position2 = array[i];
                int length2 = "".length();
                final Position position3 = new Position();
                int j = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (j < array.length) {
                    if (i != j) {
                        final Position position4 = array[j];
                        final double func_111099_a = position2.func_111099_a(position4);
                        min = Math.min(func_111099_a, min);
                        if (func_111099_a < n) {
                            ++length2;
                            final Position position5 = position3;
                            position5.field_111101_a += position4.field_111101_a - position2.field_111101_a;
                            final Position position6 = position3;
                            position6.field_111100_b += position4.field_111100_b - position2.field_111100_b;
                        }
                    }
                    ++j;
                }
                if (length2 > 0) {
                    final Position position7 = position3;
                    position7.field_111101_a /= length2;
                    final Position position8 = position3;
                    position8.field_111100_b /= length2;
                    if (position3.func_111096_b() > 0.0) {
                        position3.func_111095_a();
                        position2.func_111094_b(position3);
                        "".length();
                        if (3 <= 2) {
                            throw null;
                        }
                    }
                    else {
                        position2.func_111097_a(random, n2, n3, n4, n5);
                    }
                    n6 = " ".length();
                }
                if (position2.func_111093_a(n2, n3, n4, n5)) {
                    n6 = " ".length();
                }
                ++i;
            }
            if (n6 == 0) {
                final int length3 = array.length;
                int k = "".length();
                "".length();
                if (-1 == 4) {
                    throw null;
                }
                while (k < length3) {
                    final Position position9 = array[k];
                    if (!position9.func_111098_b(world)) {
                        position9.func_111097_a(random, n2, n3, n4, n5);
                        n6 = " ".length();
                    }
                    ++k;
                }
            }
            ++length;
        }
        if (length >= 3289 + 5266 + 169 + 1276) {
            final StringBuilder sb = new StringBuilder(CommandSpreadPlayers.I[0x36 ^ 0x3B]);
            String s;
            if (b) {
                s = CommandSpreadPlayers.I[0x91 ^ 0x9F];
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                s = CommandSpreadPlayers.I[0x3C ^ 0x33];
            }
            final String string = sb.append(s).toString();
            final Object[] array2 = new Object[0xB8 ^ 0xBC];
            array2["".length()] = array.length;
            array2[" ".length()] = position.field_111101_a;
            array2["  ".length()] = position.field_111100_b;
            final int length4 = "   ".length();
            final String s2 = CommandSpreadPlayers.I[0x55 ^ 0x45];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = min;
            array2[length4] = String.format(s2, array3);
            throw new CommandException(string, array2);
        }
        return length;
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> func_181043_b;
        if (array.length >= " ".length() && array.length <= "  ".length()) {
            func_181043_b = CommandBase.func_181043_b(array, "".length(), blockPos);
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            func_181043_b = null;
        }
        return func_181043_b;
    }
    
    private static void I() {
        (I = new String[0x71 ^ 0x60])["".length()] = I("\u0007\u0016\u0004\t*\u0010\u0016\u001a\r2\u0011\u0014\u0005", "tfvlK");
        CommandSpreadPlayers.I[" ".length()] = I("\u0015\u0007''\u0011\u0018\f9d\u0003\u0006\u001a/+\u0014\u0006\u0004+3\u0015\u0004\u001bd?\u0003\u0017\u000f/", "vhJJp");
        CommandSpreadPlayers.I["  ".length()] = I("\u00018.7\u001b\f30t\t\u0012%&;\u001e\u0012;\"#\u001f\u0010$m/\t\u00030&", "bWCZz");
        CommandSpreadPlayers.I["   ".length()] = I("\u0014?\u0005\u0003\u0000\u00194\u001b@\u0012\u0007\"\r\u000f\u0005\u0007<\t\u0017\u0004\u0005#F\u001d\u0011\u00055\t\n\b\u00197F", "wPhna");
        CommandSpreadPlayers.I[0xB6 ^ 0xB2] = I("$\n\u0014\u0005\u0003", "Pouhp");
        CommandSpreadPlayers.I[0x13 ^ 0x16] = I("\u001a60<\u0006\u0018)", "jZQEc");
        CommandSpreadPlayers.I[0x55 ^ 0x53] = I(";\u001c\u001c\u000b\u00196\u0017\u0002H\u000b(\u0001\u0014\u0007\u001c(\u001f\u0010\u001f\u001d*\u0000_\u0015\r;\u0010\u0014\u0015\u000bv", "Xsqfx");
        CommandSpreadPlayers.I[0xA2 ^ 0xA5] = I("\u00174\u0004\f\n", "cQeay");
        CommandSpreadPlayers.I[0x69 ^ 0x61] = I("\t\u0000\u000f\u0018&\u000b\u001f", "ylnaC");
        CommandSpreadPlayers.I[0x45 ^ 0x4C] = I("\u0012\u0005\u0001\u000e\u001b\u001f\u000e\u001fM\t\u0001\u0018\t\u0002\u001e\u0001\u0006\r\u001a\u001f\u0003\u0019B\n\u0014\u0017\u0005B", "qjlcz");
        CommandSpreadPlayers.I[0x37 ^ 0x3D] = I("\u0002\u0000\r7\u001f", "velZl");
        CommandSpreadPlayers.I[0x5D ^ 0x56] = I("**\u0018.\u001d(5", "ZFyWx");
        CommandSpreadPlayers.I[0x37 ^ 0x3B] = I("hj}\u0011", "MDOwM");
        CommandSpreadPlayers.I[0x59 ^ 0x54] = I("\u0010\u0004*7\f\u001d\u000f4t\u001e\u0003\u0019\";\t\u0003\u0007&#\b\u0001\u0018i<\f\u001a\u00072(\b]", "skGZm");
        CommandSpreadPlayers.I[0x43 ^ 0x4D] = I("\u0016\"\u0011\u0004)", "bGpiZ");
        CommandSpreadPlayers.I[0x5 ^ 0xA] = I("\u0018\n\u0006)\u0001\u001a\u0015", "hfgPd");
        CommandSpreadPlayers.I[0xB6 ^ 0xA6] = I("uOy\"", "PaKDN");
    }
    
    private Position[] func_110670_a(final Random random, final int n, final double n2, final double n3, final double n4, final double n5) {
        final Position[] array = new Position[n];
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < array.length) {
            final Position position = new Position();
            position.func_111097_a(random, n2, n3, n4, n5);
            array[i] = position;
            ++i;
        }
        return array;
    }
    
    private double func_110671_a(final List<Entity> list, final World world, final Position[] array, final boolean b) {
        double n = 0.0;
        int length = "".length();
        final HashMap hashMap = Maps.newHashMap();
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < list.size()) {
            final Entity entity = list.get(i);
            Position position;
            if (b) {
                Team team;
                if (entity instanceof EntityPlayer) {
                    team = ((EntityPlayer)entity).getTeam();
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                else {
                    team = null;
                }
                final Team team2 = team;
                if (!hashMap.containsKey(team2)) {
                    hashMap.put(team2, array[length++]);
                }
                position = hashMap.get(team2);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                position = array[length++];
            }
            entity.setPositionAndUpdate(MathHelper.floor_double(position.field_111101_a) + 0.5f, position.func_111092_a(world), MathHelper.floor_double(position.field_111100_b) + 0.5);
            double min = Double.MAX_VALUE;
            int j = "".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (j < array.length) {
                if (position != array[j]) {
                    min = Math.min(position.func_111099_a(array[j]), min);
                }
                ++j;
            }
            n += min;
            ++i;
        }
        return n / list.size();
    }
    
    static class Position
    {
        double field_111100_b;
        double field_111101_a;
        
        public void func_111097_a(final Random random, final double n, final double n2, final double n3, final double n4) {
            this.field_111101_a = MathHelper.getRandomDoubleInRange(random, n, n3);
            this.field_111100_b = MathHelper.getRandomDoubleInRange(random, n2, n4);
        }
        
        double func_111099_a(final Position position) {
            final double n = this.field_111101_a - position.field_111101_a;
            final double n2 = this.field_111100_b - position.field_111100_b;
            return Math.sqrt(n * n + n2 * n2);
        }
        
        public boolean func_111093_a(final double field_111101_a, final double field_111100_b, final double field_111101_a2, final double field_111100_b2) {
            int n = "".length();
            if (this.field_111101_a < field_111101_a) {
                this.field_111101_a = field_111101_a;
                n = " ".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else if (this.field_111101_a > field_111101_a2) {
                this.field_111101_a = field_111101_a2;
                n = " ".length();
            }
            if (this.field_111100_b < field_111100_b) {
                this.field_111100_b = field_111100_b;
                n = " ".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else if (this.field_111100_b > field_111100_b2) {
                this.field_111100_b = field_111100_b2;
                n = " ".length();
            }
            return n != 0;
        }
        
        Position(final double field_111101_a, final double field_111100_b) {
            this.field_111101_a = field_111101_a;
            this.field_111100_b = field_111100_b;
        }
        
        public boolean func_111098_b(final World world) {
            BlockPos down = new BlockPos(this.field_111101_a, 256.0, this.field_111100_b);
            "".length();
            if (1 < -1) {
                throw null;
            }
            while (down.getY() > 0) {
                down = down.down();
                final Material material = world.getBlockState(down).getBlock().getMaterial();
                if (material != Material.air) {
                    if (!material.isLiquid() && material != Material.fire) {
                        return " ".length() != 0;
                    }
                    return "".length() != 0;
                }
            }
            return "".length() != 0;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        Position() {
        }
        
        void func_111095_a() {
            final double n = this.func_111096_b();
            this.field_111101_a /= n;
            this.field_111100_b /= n;
        }
        
        public int func_111092_a(final World world) {
            BlockPos down = new BlockPos(this.field_111101_a, 256.0, this.field_111100_b);
            "".length();
            if (2 < 0) {
                throw null;
            }
            while (down.getY() > 0) {
                down = down.down();
                if (world.getBlockState(down).getBlock().getMaterial() != Material.air) {
                    return down.getY() + " ".length();
                }
            }
            return 126 + 210 - 103 + 24;
        }
        
        float func_111096_b() {
            return MathHelper.sqrt_double(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
        }
        
        public void func_111094_b(final Position position) {
            this.field_111101_a -= position.field_111101_a;
            this.field_111100_b -= position.field_111100_b;
        }
    }
}
