package net.minecraft.command;

import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.google.common.primitives.*;
import net.minecraft.server.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;

public abstract class CommandBase implements ICommand
{
    private static IAdminCommand theAdmin;
    private static final String[] I;
    
    @Override
    public int compareTo(final ICommand command) {
        return this.getCommandName().compareTo(command.getCommandName());
    }
    
    public static double parseDouble(final double n, final String s, final boolean b) throws NumberInvalidException {
        return parseDouble(n, s, -(16548056 + 29341332 - 31138673 + 15249285), 8799018 + 27577827 - 8206656 + 1829811, b);
    }
    
    public static List<String> getListOfStringsMatchingLastWord(final String[] array, final Collection<?> collection) {
        final String s = array[array.length - " ".length()];
        final ArrayList arrayList = Lists.newArrayList();
        if (!collection.isEmpty()) {
            final Iterator<String> iterator = Iterables.transform((Iterable)collection, Functions.toStringFunction()).iterator();
            "".length();
            if (-1 == 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final String s2 = iterator.next();
                if (doesStringStartWith(s, s2)) {
                    arrayList.add(s2);
                }
            }
            if (arrayList.isEmpty()) {
                final Iterator<?> iterator2 = collection.iterator();
                "".length();
                if (2 != 2) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    final Object next = iterator2.next();
                    if (next instanceof ResourceLocation && doesStringStartWith(s, ((ResourceLocation)next).getResourcePath())) {
                        arrayList.add(String.valueOf(next));
                    }
                }
            }
        }
        return (List<String>)arrayList;
    }
    
    public static List<String> func_181043_b(final String[] array, final int n, final BlockPos blockPos) {
        if (blockPos == null) {
            return null;
        }
        final int n2 = array.length - " ".length();
        String s;
        if (n2 == n) {
            s = Integer.toString(blockPos.getX());
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            if (n2 != n + " ".length()) {
                return null;
            }
            s = Integer.toString(blockPos.getZ());
        }
        final String[] array2 = new String[" ".length()];
        array2["".length()] = s;
        return (List<String>)Lists.newArrayList((Object[])array2);
    }
    
    public static int parseInt(final String s, final int n) throws NumberInvalidException {
        return parseInt(s, n, 1593389598 + 487162865 - 363066541 + 429997725);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return commandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
    }
    
    public static BlockPos parseBlockPos(final ICommandSender commandSender, final String[] array, final int n, final boolean b) throws NumberInvalidException {
        final BlockPos position = commandSender.getPosition();
        return new BlockPos(parseDouble(position.getX(), array[n], -(23943316 + 24861350 - 20704790 + 1900124), 3800109 + 2478809 + 5277969 + 18443113, b), parseDouble(position.getY(), array[n + " ".length()], "".length(), 38 + 106 - 78 + 190, "".length() != 0), parseDouble(position.getZ(), array[n + "  ".length()], -(28842232 + 12568108 - 30372594 + 18962254), 24915344 + 20946895 - 24832704 + 8970465, b));
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return null;
    }
    
    public static String getEntityName(final ICommandSender commandSender, final String s) throws EntityNotFoundException {
        try {
            return getPlayer(commandSender, s).getName();
        }
        catch (PlayerNotFoundException ex2) {
            try {
                return func_175768_b(commandSender, s).getUniqueID().toString();
            }
            catch (EntityNotFoundException ex) {
                if (PlayerSelector.hasArguments(s)) {
                    throw ex;
                }
                return s;
            }
        }
    }
    
    public static List<String> func_175771_a(final String[] array, final int n, final BlockPos blockPos) {
        if (blockPos == null) {
            return null;
        }
        final int n2 = array.length - " ".length();
        String s;
        if (n2 == n) {
            s = Integer.toString(blockPos.getX());
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else if (n2 == n + " ".length()) {
            s = Integer.toString(blockPos.getY());
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            if (n2 != n + "  ".length()) {
                return null;
            }
            s = Integer.toString(blockPos.getZ());
        }
        final String[] array2 = new String[" ".length()];
        array2["".length()] = s;
        return (List<String>)Lists.newArrayList((Object[])array2);
    }
    
    public static void notifyOperators(final ICommandSender commandSender, final ICommand command, final int n, final String s, final Object... array) {
        if (CommandBase.theAdmin != null) {
            CommandBase.theAdmin.notifyOperators(commandSender, command, n, s, array);
        }
    }
    
    public static EntityPlayerMP getCommandSenderAsPlayer(final ICommandSender commandSender) throws PlayerNotFoundException {
        if (commandSender instanceof EntityPlayerMP) {
            return (EntityPlayerMP)commandSender;
        }
        throw new PlayerNotFoundException(CommandBase.I[0x38 ^ 0x37], new Object["".length()]);
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return "".length() != 0;
    }
    
    public static long parseLong(final String s, final long n, final long n2) throws NumberInvalidException {
        final long long1 = parseLong(s);
        if (long1 < n) {
            final String s2 = CommandBase.I[0x92 ^ 0x96];
            final Object[] array = new Object["  ".length()];
            array["".length()] = long1;
            array[" ".length()] = n;
            throw new NumberInvalidException(s2, array);
        }
        if (long1 > n2) {
            final String s3 = CommandBase.I[0x98 ^ 0x9D];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = long1;
            array2[" ".length()] = n2;
            throw new NumberInvalidException(s3, array2);
        }
        return long1;
    }
    
    public static String buildString(final String[] array, final int n) {
        final StringBuilder sb = new StringBuilder();
        int i = n;
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < array.length) {
            if (i > n) {
                sb.append(CommandBase.I[0x34 ^ 0x27]);
            }
            sb.append(array[i]);
            ++i;
        }
        return sb.toString();
    }
    
    public static String getPlayerName(final ICommandSender commandSender, final String s) throws PlayerNotFoundException {
        try {
            return getPlayer(commandSender, s).getName();
        }
        catch (PlayerNotFoundException ex) {
            if (PlayerSelector.hasArguments(s)) {
                throw ex;
            }
            return s;
        }
    }
    
    public static Entity func_175768_b(final ICommandSender commandSender, final String s) throws EntityNotFoundException {
        return getEntity(commandSender, s, (Class<? extends Entity>)Entity.class);
    }
    
    public static double parseDouble(final String s) throws NumberInvalidException {
        try {
            final double double1 = Double.parseDouble(s);
            if (!Doubles.isFinite(double1)) {
                final String s2 = CommandBase.I[0x37 ^ 0x31];
                final Object[] array = new Object[" ".length()];
                array["".length()] = s;
                throw new NumberInvalidException(s2, array);
            }
            return double1;
        }
        catch (NumberFormatException ex) {
            final String s3 = CommandBase.I[0x2F ^ 0x28];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = s;
            throw new NumberInvalidException(s3, array2);
        }
    }
    
    public int getRequiredPermissionLevel() {
        return 0xB0 ^ 0xB4;
    }
    
    public static EntityPlayerMP getPlayer(final ICommandSender commandSender, final String s) throws PlayerNotFoundException {
        EntityPlayerMP entityPlayerMP = PlayerSelector.matchOnePlayer(commandSender, s);
        if (entityPlayerMP == null) {
            try {
                entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerByUUID(UUID.fromString(s));
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            catch (IllegalArgumentException ex) {}
        }
        if (entityPlayerMP == null) {
            entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(s);
        }
        if (entityPlayerMP == null) {
            throw new PlayerNotFoundException();
        }
        return entityPlayerMP;
    }
    
    public static IChatComponent getChatComponentFromNthArg(final ICommandSender commandSender, final String[] array, final int n) throws CommandException, PlayerNotFoundException {
        return getChatComponentFromNthArg(commandSender, array, n, "".length() != 0);
    }
    
    public static boolean doesStringStartWith(final String s, final String s2) {
        return s2.regionMatches(" ".length() != 0, "".length(), s, "".length(), s.length());
    }
    
    private static void I() {
        (I = new String[0x61 ^ 0x47])["".length()] = I("\u001a-=\u0017\"\u0017&#T$\u001c,5\b*\u001al>\u000f.W+>\f\"\u0015+4", "yBPzC");
        CommandBase.I[" ".length()] = I("\u0019\u0004\u00065#\u0014\u000f\u0018v%\u001f\u0005\u000e*+\u0019E\u0005-/T\u001f\u00047\u0011\u0017\n\u00074", "zkkXB");
        CommandBase.I["  ".length()] = I(";\u001a\u001a#\u00056\u0011\u0004`\u0003=\u001b\u0012<\r;[\u0019;\tv\u0001\u0018!&1\u0012", "XuwNd");
        CommandBase.I["   ".length()] = I("\u0007\u0016\u001e\u000b\u0012\n\u001d\u0000H\u0014\u0001\u0017\u0016\u0014\u001a\u0007W\u001d\u0013\u001eJ\u0010\u001d\u0010\u0012\b\u0010\u0017", "dysfs");
        CommandBase.I[0x32 ^ 0x36] = I(":\u0018\u00035 7\u0013\u001dv&<\u0019\u000b*(:Y\u0000-,w\u0003\u00017\u00124\u0016\u00024", "YwnXA");
        CommandBase.I[0x9B ^ 0x9E] = I(";\u0004/\u0018\r6\u000f1[\u000b=\u0005'\u0007\u0005;E,\u0000\u0001v\u001f-\u001a.1\f", "XkBul");
        CommandBase.I[0x72 ^ 0x74] = I("\u0000,7$\u000e\r')g\b\u0006-?;\u0006\u0000m4<\u0002M*4?\u000e\u000f*>", "cCZIo");
        CommandBase.I[0x5D ^ 0x5A] = I("\u0017(%\u0017\u0006\u001a#;T\u0000\u0011)-\b\u000e\u0017i&\u000f\nZ.&\f\u0006\u0018.,", "tGHzg");
        CommandBase.I[0x65 ^ 0x6D] = I("1*\u0003\u0005\u0002<!\u001dF\u00047+\u000b\u001a\n1k\n\u0007\u00160)\u000bF\u0017=*=\u0005\u0002>)", "REnhc");
        CommandBase.I[0x15 ^ 0x1C] = I("7.\u0007%#:%\u0019f%1/\u000f:+7o\u000e'76-\u000ff6;.(!%", "TAjHB");
        CommandBase.I[0x84 ^ 0x8E] = I("\u0019\u001b3*", "miFOX");
        CommandBase.I[0x5A ^ 0x51] = I("x", "IEnse");
        CommandBase.I[0xA7 ^ 0xAB] = I("#1\u001b;5", "EPwHP");
        CommandBase.I[0x1 ^ 0xC] = I("j", "ZnUWz");
        CommandBase.I[0x45 ^ 0x4B] = I("\",\u001e\u001b\u0013/'\u0000X\u0015$-\u0016\u0004\u001b\"m\u0011\u0019\u001d-&\u0012\u0018\\(-\u0005\u0017\u001e('", "ACsvr");
        CommandBase.I[0x70 ^ 0x7F] = I("\u0003\"%E?/>$E!*(3\f4#m'\r;9%p\u0015>;45\u0017r#\"%E%3>8E&5m \u0000 <\"\"\br.%9\u0016r;.$\f=4m?\u000b|", "ZMPeR");
        CommandBase.I[0xBF ^ 0xAF] = I("\b\u0006\"\u00174\u0005\r<T2\u000e\u0007*\b<\bG*\u0014!\u0002\u001d6T<\u0005\u001f.\u0016<\u000f<:\u00131", "kiOzU");
        CommandBase.I[0x12 ^ 0x3] = I("", "ejdOF");
        CommandBase.I[0xA5 ^ 0xB7] = I("f", "FrEui");
        CommandBase.I[0x82 ^ 0x91] = I("F", "fBwtQ");
        CommandBase.I[0xD5 ^ 0xC1] = I("\u000e", "pVZvT");
        CommandBase.I[0x30 ^ 0x25] = I("\u001b\u0006=\t\u0005\u0016\r#J\u0003\u001d\u00075\u0016\r\u001bG>\u0011\tV\u0000>\u0012\u0005\u0014\u00004", "xiPdd");
        CommandBase.I[0x50 ^ 0x46] = I("h", "FoDwn");
        CommandBase.I[0x97 ^ 0x80] = I("+-\u0018\u0001(&&\u0006B.-,\u0010\u001e +l\u0011\u0003<*.\u0010B='-&\u0001($.", "HBulI");
        CommandBase.I[0x7D ^ 0x65] = I("\r6)\u0017%\u0000=7T#\u000b7!\b-\rw \u00151\f5!T0\u00016\u0006\u0013#", "nYDzD");
        CommandBase.I[0xA0 ^ 0xB9] = I("?", "AeNFC");
        CommandBase.I[0x84 ^ 0x9E] = I("\u0007\u001b(=(\n\u00106~.\u0001\u001a \" \u0007Z+%$J\u001d+&(\b\u001d!", "dtEPI");
        CommandBase.I[0x78 ^ 0x63] = I("\u007f", "QOioT");
        CommandBase.I[0xA3 ^ 0xBF] = I(",9\u0003&\u0004!2\u001de\u0002*8\u000b9\f,x\n$\u0010-:\u000be\u0011 9=&\u0004#:", "OVnKe");
        CommandBase.I[0x1A ^ 0x7] = I("\u0011\u0001#\u000b8\u001c\n=H>\u0017\u0000+\u00140\u0011@*\t,\u0010\u0002+H-\u001d\u0001\f\u000f>", "rnNfY");
        CommandBase.I[0xC ^ 0x12] = I("\n\r?#.\u0007\u0006!`(\u0000\u00147`&\u001d\u0007?`!\u0006\u0016\u0014!:\u0007\u0006", "ibRNO");
        CommandBase.I[0x2D ^ 0x32] = I("\u0004\f&$7\t\u00078g1\u000e\u0015.g4\u000b\f(\"x\t\f?\u000f9\u0012\r/", "gcKIV");
        CommandBase.I[0x92 ^ 0xB2] = I("%\u0006!\u001c5(\r?_3/\u001f)_6*\u0006/\u001az(\u000687;3\u0007(", "FiLqT");
        CommandBase.I[0x84 ^ 0xA5] = I("h/\u0001\u001cN", "HNoxn");
        CommandBase.I[0x57 ^ 0x75] = I("fm", "JMUfd");
        CommandBase.I[0x9E ^ 0xBD] = I("", "hPGUW");
        CommandBase.I[0x95 ^ 0xB1] = I("V\r \u0013w", "vlNwW");
        CommandBase.I[0x64 ^ 0x41] = I("xQ", "TqnAY");
    }
    
    public static <T extends Entity> T getEntity(final ICommandSender commandSender, final String s, final Class<? extends T> clazz) throws EntityNotFoundException {
        Entity entity = PlayerSelector.matchOneEntity(commandSender, s, (Class<? extends Entity>)clazz);
        final MinecraftServer server = MinecraftServer.getServer();
        if (entity == null) {
            entity = server.getConfigurationManager().getPlayerByUsername(s);
        }
        if (entity == null) {
            try {
                final UUID fromString = UUID.fromString(s);
                entity = server.getEntityFromUuid(fromString);
                if (entity == null) {
                    entity = server.getConfigurationManager().getPlayerByUUID(fromString);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
            }
            catch (IllegalArgumentException ex) {
                throw new EntityNotFoundException(CommandBase.I[0x67 ^ 0x77], new Object["".length()]);
            }
        }
        if (entity != null && clazz.isAssignableFrom(entity.getClass())) {
            return (T)entity;
        }
        throw new EntityNotFoundException();
    }
    
    public static void notifyOperators(final ICommandSender commandSender, final ICommand command, final String s, final Object... array) {
        notifyOperators(commandSender, command, "".length(), s, array);
    }
    
    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }
    
    public static void setAdminCommander(final IAdminCommand theAdmin) {
        CommandBase.theAdmin = theAdmin;
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static long parseLong(final String s) throws NumberInvalidException {
        try {
            return Long.parseLong(s);
        }
        catch (NumberFormatException ex) {
            final String s2 = CommandBase.I["   ".length()];
            final Object[] array = new Object[" ".length()];
            array["".length()] = s;
            throw new NumberInvalidException(s2, array);
        }
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((ICommand)o);
    }
    
    public static String joinNiceStringFromCollection(final Collection<String> collection) {
        return joinNiceString(collection.toArray(new String[collection.size()]));
    }
    
    public static Item getItemByText(final ICommandSender commandSender, final String s) throws NumberInvalidException {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        final Item item = Item.itemRegistry.getObject(resourceLocation);
        if (item == null) {
            final String s2 = CommandBase.I[0x54 ^ 0x4A];
            final Object[] array = new Object[" ".length()];
            array["".length()] = resourceLocation;
            throw new NumberInvalidException(s2, array);
        }
        return item;
    }
    
    public static CoordinateArg parseCoordinate(final double n, String substring, final int n2, final int n3, final boolean b) throws NumberInvalidException {
        final boolean startsWith = substring.startsWith(CommandBase.I[0xAE ^ 0xBA]);
        if (startsWith && Double.isNaN(n)) {
            final String s = CommandBase.I[0x42 ^ 0x57];
            final Object[] array = new Object[" ".length()];
            array["".length()] = n;
            throw new NumberInvalidException(s, array);
        }
        double n4 = 0.0;
        if (!startsWith || substring.length() > " ".length()) {
            final boolean contains = substring.contains(CommandBase.I[0x6A ^ 0x7C]);
            if (startsWith) {
                substring = substring.substring(" ".length());
            }
            n4 += parseDouble(substring);
            if (!contains && !startsWith && b) {
                n4 += 0.5;
            }
        }
        if (n2 != 0 || n3 != 0) {
            if (n4 < n2) {
                final String s2 = CommandBase.I[0x39 ^ 0x2E];
                final Object[] array2 = new Object["  ".length()];
                array2["".length()] = n4;
                array2[" ".length()] = n2;
                throw new NumberInvalidException(s2, array2);
            }
            if (n4 > n3) {
                final String s3 = CommandBase.I[0x72 ^ 0x6A];
                final Object[] array3 = new Object["  ".length()];
                array3["".length()] = n4;
                array3[" ".length()] = n3;
                throw new NumberInvalidException(s3, array3);
            }
        }
        final double n5 = n4;
        double n6;
        if (startsWith) {
            n6 = n;
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            n6 = 0.0;
        }
        return new CoordinateArg(n5 + n6, n4, startsWith);
    }
    
    public static IChatComponent join(final List<IChatComponent> list) {
        final ChatComponentText chatComponentText = new ChatComponentText(CommandBase.I[0xA ^ 0x29]);
        int i = "".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i < list.size()) {
            if (i > 0) {
                if (i == list.size() - " ".length()) {
                    chatComponentText.appendText(CommandBase.I[0x4 ^ 0x20]);
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
                else if (i > 0) {
                    chatComponentText.appendText(CommandBase.I[0x74 ^ 0x51]);
                }
            }
            chatComponentText.appendSibling(list.get(i));
            ++i;
        }
        return chatComponentText;
    }
    
    public static IChatComponent getChatComponentFromNthArg(final ICommandSender commandSender, final String[] array, final int n, final boolean b) throws PlayerNotFoundException {
        final ChatComponentText chatComponentText = new ChatComponentText(CommandBase.I[0xB3 ^ 0xA2]);
        int i = n;
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (i < array.length) {
            if (i > n) {
                chatComponentText.appendText(CommandBase.I[0x54 ^ 0x46]);
            }
            IChatComponent chatComponent = new ChatComponentText(array[i]);
            if (b) {
                final IChatComponent matchEntitiesToChatComponent = PlayerSelector.matchEntitiesToChatComponent(commandSender, array[i]);
                if (matchEntitiesToChatComponent == null) {
                    if (PlayerSelector.hasArguments(array[i])) {
                        throw new PlayerNotFoundException();
                    }
                }
                else {
                    chatComponent = matchEntitiesToChatComponent;
                }
            }
            chatComponentText.appendSibling(chatComponent);
            ++i;
        }
        return chatComponentText;
    }
    
    public static List<Entity> func_175763_c(final ICommandSender commandSender, final String s) throws EntityNotFoundException {
        List<Entity> list;
        if (PlayerSelector.hasArguments(s)) {
            list = PlayerSelector.matchEntities(commandSender, s, (Class<? extends Entity>)Entity.class);
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            final Entity[] array = new Entity[" ".length()];
            array["".length()] = func_175768_b(commandSender, s);
            list = (List<Entity>)Lists.newArrayList((Object[])array);
        }
        return list;
    }
    
    public static int parseInt(final String s, final int n, final int n2) throws NumberInvalidException {
        final int int1 = parseInt(s);
        if (int1 < n) {
            final String s2 = CommandBase.I[" ".length()];
            final Object[] array = new Object["  ".length()];
            array["".length()] = int1;
            array[" ".length()] = n;
            throw new NumberInvalidException(s2, array);
        }
        if (int1 > n2) {
            final String s3 = CommandBase.I["  ".length()];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = int1;
            array2[" ".length()] = n2;
            throw new NumberInvalidException(s3, array2);
        }
        return int1;
    }
    
    public static boolean parseBoolean(final String s) throws CommandException {
        if (s.equals(CommandBase.I[0x8F ^ 0x85]) || s.equals(CommandBase.I[0x19 ^ 0x12])) {
            return " ".length() != 0;
        }
        if (!s.equals(CommandBase.I[0x21 ^ 0x2D]) && !s.equals(CommandBase.I[0x29 ^ 0x24])) {
            final String s2 = CommandBase.I[0x99 ^ 0x97];
            final Object[] array = new Object[" ".length()];
            array["".length()] = s;
            throw new CommandException(s2, array);
        }
        return "".length() != 0;
    }
    
    static {
        I();
    }
    
    public static Block getBlockByText(final ICommandSender commandSender, final String s) throws NumberInvalidException {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        if (!Block.blockRegistry.containsKey(resourceLocation)) {
            final String s2 = CommandBase.I[0xA6 ^ 0xB9];
            final Object[] array = new Object[" ".length()];
            array["".length()] = resourceLocation;
            throw new NumberInvalidException(s2, array);
        }
        final Block block = Block.blockRegistry.getObject(resourceLocation);
        if (block == null) {
            final String s3 = CommandBase.I[0x36 ^ 0x16];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = resourceLocation;
            throw new NumberInvalidException(s3, array2);
        }
        return block;
    }
    
    public static double parseDouble(final String s, final double n, final double n2) throws NumberInvalidException {
        final double double1 = parseDouble(s);
        if (double1 < n) {
            final String s2 = CommandBase.I[0xB1 ^ 0xB9];
            final Object[] array = new Object["  ".length()];
            array["".length()] = double1;
            array[" ".length()] = n;
            throw new NumberInvalidException(s2, array);
        }
        if (double1 > n2) {
            final String s3 = CommandBase.I[0x82 ^ 0x8B];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = double1;
            array2[" ".length()] = n2;
            throw new NumberInvalidException(s3, array2);
        }
        return double1;
    }
    
    public static List<String> getListOfStringsMatchingLastWord(final String[] array, final String... array2) {
        return getListOfStringsMatchingLastWord(array, Arrays.asList(array2));
    }
    
    public static double parseDouble(final String s, final double n) throws NumberInvalidException {
        return parseDouble(s, n, Double.MAX_VALUE);
    }
    
    public static CoordinateArg parseCoordinate(final double n, final String s, final boolean b) throws NumberInvalidException {
        return parseCoordinate(n, s, -(2649875 + 27220544 - 7740250 + 7869831), 17048997 + 28484101 - 23322592 + 7789494, b);
    }
    
    public static double parseDouble(final double n, String substring, final int n2, final int n3, final boolean b) throws NumberInvalidException {
        final boolean startsWith = substring.startsWith(CommandBase.I[0x90 ^ 0x89]);
        if (startsWith && Double.isNaN(n)) {
            final String s = CommandBase.I[0x9 ^ 0x13];
            final Object[] array = new Object[" ".length()];
            array["".length()] = n;
            throw new NumberInvalidException(s, array);
        }
        double n4;
        if (startsWith) {
            n4 = n;
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            n4 = 0.0;
        }
        double n5 = n4;
        if (!startsWith || substring.length() > " ".length()) {
            final boolean contains = substring.contains(CommandBase.I[0x9D ^ 0x86]);
            if (startsWith) {
                substring = substring.substring(" ".length());
            }
            n5 += parseDouble(substring);
            if (!contains && !startsWith && b) {
                n5 += 0.5;
            }
        }
        if (n2 != 0 || n3 != 0) {
            if (n5 < n2) {
                final String s2 = CommandBase.I[0x47 ^ 0x5B];
                final Object[] array2 = new Object["  ".length()];
                array2["".length()] = n5;
                array2[" ".length()] = n2;
                throw new NumberInvalidException(s2, array2);
            }
            if (n5 > n3) {
                final String s3 = CommandBase.I[0x88 ^ 0x95];
                final Object[] array3 = new Object["  ".length()];
                array3["".length()] = n5;
                array3[" ".length()] = n3;
                throw new NumberInvalidException(s3, array3);
            }
        }
        return n5;
    }
    
    public static int parseInt(final String s) throws NumberInvalidException {
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException ex) {
            final String s2 = CommandBase.I["".length()];
            final Object[] array = new Object[" ".length()];
            array["".length()] = s;
            throw new NumberInvalidException(s2, array);
        }
    }
    
    public static String joinNiceString(final Object[] array) {
        final StringBuilder sb = new StringBuilder();
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < array.length) {
            final String string = array[i].toString();
            if (i > 0) {
                if (i == array.length - " ".length()) {
                    sb.append(CommandBase.I[0x97 ^ 0xB6]);
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                }
                else {
                    sb.append(CommandBase.I[0x84 ^ 0xA6]);
                }
            }
            sb.append(string);
            ++i;
        }
        return sb.toString();
    }
    
    public static class CoordinateArg
    {
        private final boolean field_179632_c;
        private final double field_179633_a;
        private final double field_179631_b;
        
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
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public double func_179628_a() {
            return this.field_179633_a;
        }
        
        protected CoordinateArg(final double field_179633_a, final double field_179631_b, final boolean field_179632_c) {
            this.field_179633_a = field_179633_a;
            this.field_179631_b = field_179631_b;
            this.field_179632_c = field_179632_c;
        }
        
        public boolean func_179630_c() {
            return this.field_179632_c;
        }
        
        public double func_179629_b() {
            return this.field_179631_b;
        }
    }
}
