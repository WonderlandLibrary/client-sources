package net.minecraft.command;

import java.util.regex.*;
import net.minecraft.server.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;

public class PlayerSelector
{
    private static final Pattern intListPattern;
    private static final Pattern keyValueListPattern;
    private static final Set<String> WORLD_BINDING_ARGS;
    private static final String[] I;
    private static final Pattern tokenPattern;
    
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
            if (4 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static Map<String, String> getArgumentMap(final String s) {
        final HashMap hashMap = Maps.newHashMap();
        if (s == null) {
            return (Map<String, String>)hashMap;
        }
        int length = "".length();
        int end = -" ".length();
        final Matcher matcher = PlayerSelector.intListPattern.matcher(s);
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (matcher.find()) {
            String s2 = null;
            switch (length++) {
                case 0: {
                    s2 = PlayerSelector.I[0x3F ^ 0x78];
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    s2 = PlayerSelector.I[0x4A ^ 0x2];
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    s2 = PlayerSelector.I[0x60 ^ 0x29];
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    s2 = PlayerSelector.I[0x8C ^ 0xC6];
                    break;
                }
            }
            if (s2 != null && matcher.group(" ".length()).length() > 0) {
                hashMap.put(s2, matcher.group(" ".length()));
            }
            end = matcher.end();
        }
        if (end < s.length()) {
            final Pattern keyValueListPattern = PlayerSelector.keyValueListPattern;
            String substring;
            if (end == -" ".length()) {
                substring = s;
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                substring = s.substring(end);
            }
            final Matcher matcher2 = keyValueListPattern.matcher(substring);
            "".length();
            if (4 == 1) {
                throw null;
            }
            while (matcher2.find()) {
                hashMap.put(matcher2.group(" ".length()), matcher2.group("  ".length()));
            }
        }
        return (Map<String, String>)hashMap;
    }
    
    private static List<Predicate<Entity>> func_179657_e(final Map<String, String> map) {
        final ArrayList arrayList = Lists.newArrayList();
        final Map<String, Integer> func_96560_a = func_96560_a(map);
        if (func_96560_a != null && func_96560_a.size() > 0) {
            arrayList.add(new Predicate<Entity>(func_96560_a) {
                private static final String[] I;
                private final Map val$map;
                
                private static void I() {
                    (I = new String[" ".length()])["".length()] = I("'\u0004:,", "xiSBd");
                }
                
                public boolean apply(final Entity entity) {
                    final Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension("".length()).getScoreboard();
                    final Iterator<Map.Entry<String, V>> iterator = this.val$map.entrySet().iterator();
                    "".length();
                    if (2 == 0) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final Map.Entry<String, V> entry = iterator.next();
                        String substring = entry.getKey();
                        int n = "".length();
                        if (substring.endsWith(PlayerSelector$6.I["".length()]) && substring.length() > (0x4F ^ 0x4B)) {
                            n = " ".length();
                            substring = substring.substring("".length(), substring.length() - (0xA2 ^ 0xA6));
                        }
                        final ScoreObjective objective = scoreboard.getObjective(substring);
                        if (objective == null) {
                            return "".length() != 0;
                        }
                        String s;
                        if (entity instanceof EntityPlayerMP) {
                            s = entity.getName();
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                        }
                        else {
                            s = entity.getUniqueID().toString();
                        }
                        final String s2 = s;
                        if (!scoreboard.entityHasObjective(s2, objective)) {
                            return "".length() != 0;
                        }
                        final int scorePoints = scoreboard.getValueFromObjective(s2, objective).getScorePoints();
                        if (scorePoints < (int)entry.getValue() && n != 0) {
                            return "".length() != 0;
                        }
                        if (scorePoints > (int)entry.getValue() && n == 0) {
                            return "".length() != 0;
                        }
                    }
                    return " ".length() != 0;
                }
                
                static {
                    I();
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
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
                        if (1 < 0) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
        }
        return (List<Predicate<Entity>>)arrayList;
    }
    
    private static List<Predicate<Entity>> func_179648_b(final Map<String, String> map) {
        final ArrayList arrayList = Lists.newArrayList();
        final int intWithDefault = parseIntWithDefault(map, PlayerSelector.I[0xA2 ^ 0xB6], -" ".length());
        final int intWithDefault2 = parseIntWithDefault(map, PlayerSelector.I[0xB4 ^ 0xA1], -" ".length());
        if (intWithDefault > -" ".length() || intWithDefault2 > -" ".length()) {
            arrayList.add(new Predicate<Entity>(intWithDefault, intWithDefault2) {
                private final int val$j;
                private final int val$i;
                
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
                        if (4 < -1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
                
                public boolean apply(final Entity entity) {
                    if (!(entity instanceof EntityPlayerMP)) {
                        return "".length() != 0;
                    }
                    final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entity;
                    if ((this.val$i <= -" ".length() || entityPlayerMP.experienceLevel >= this.val$i) && (this.val$j <= -" ".length() || entityPlayerMP.experienceLevel <= this.val$j)) {
                        return " ".length() != 0;
                    }
                    return "".length() != 0;
                }
            });
        }
        return (List<Predicate<Entity>>)arrayList;
    }
    
    private static boolean func_179665_h(final Map<String, String> map) {
        final Iterator<String> iterator = PlayerSelector.WORLD_BINDING_ARGS.iterator();
        "".length();
        if (4 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (map.containsKey(iterator.next())) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    private static <T extends Entity> List<T> func_179658_a(List<T> list, final Map<String, String> map, final ICommandSender commandSender, final Class<? extends T> clazz, final String s, final BlockPos blockPos) {
        final String s2 = PlayerSelector.I[0x2A ^ 0x1C];
        int n;
        if (!s.equals(PlayerSelector.I[0x97 ^ 0xA0]) && !s.equals(PlayerSelector.I[0x13 ^ 0x2B])) {
            n = " ".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int intWithDefault = parseIntWithDefault(map, s2, n);
        if (!s.equals(PlayerSelector.I[0x7B ^ 0x42]) && !s.equals(PlayerSelector.I[0x6C ^ 0x56]) && !s.equals(PlayerSelector.I[0x1A ^ 0x21])) {
            if (s.equals(PlayerSelector.I[0x5D ^ 0x61])) {
                Collections.shuffle(list);
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else if (blockPos != null) {
            Collections.sort(list, new Comparator<Entity>(blockPos) {
                private final BlockPos val$p_179658_5_;
                
                @Override
                public int compare(final Object o, final Object o2) {
                    return this.compare((Entity)o, (Entity)o2);
                }
                
                @Override
                public int compare(final Entity entity, final Entity entity2) {
                    return ComparisonChain.start().compare(entity.getDistanceSq(this.val$p_179658_5_), entity2.getDistanceSq(this.val$p_179658_5_)).result();
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
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
        }
        final Entity commandSenderEntity = commandSender.getCommandSenderEntity();
        if (commandSenderEntity != null && clazz.isAssignableFrom(commandSenderEntity.getClass()) && intWithDefault == " ".length() && list.contains(commandSenderEntity) && !PlayerSelector.I[0x40 ^ 0x7D].equals(s)) {
            final Entity[] array = new Entity[" ".length()];
            array["".length()] = commandSenderEntity;
            list = (List<T>)Lists.newArrayList((Object[])array);
        }
        if (intWithDefault != 0) {
            if (intWithDefault < 0) {
                Collections.reverse(list);
            }
            list = list.subList("".length(), Math.min(Math.abs(intWithDefault), list.size()));
        }
        return list;
    }
    
    private static String func_179651_b(final Map<String, String> map, final String s) {
        return map.get(s);
    }
    
    private static <T extends Entity> boolean isEntityTypeValid(final ICommandSender commandSender, final Map<String, String> map) {
        final String func_179651_b = func_179651_b(map, PlayerSelector.I[0xB3 ^ 0xBF]);
        String substring;
        if (func_179651_b != null && func_179651_b.startsWith(PlayerSelector.I[0x76 ^ 0x7B])) {
            substring = func_179651_b.substring(" ".length());
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            substring = func_179651_b;
        }
        final String s = substring;
        if (s != null && !EntityList.isStringValidEntityName(s)) {
            final String s2 = PlayerSelector.I[0x2 ^ 0xC];
            final Object[] array = new Object[" ".length()];
            array["".length()] = s;
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(s2, array);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private static List<Predicate<Entity>> func_179647_f(final Map<String, String> map) {
        final ArrayList arrayList = Lists.newArrayList();
        String s = func_179651_b(map, PlayerSelector.I[0x3B ^ 0x22]);
        int n;
        if (s != null && s.startsWith(PlayerSelector.I[0x8F ^ 0x95])) {
            n = " ".length();
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        if (n2 != 0) {
            s = s.substring(" ".length());
        }
        if (s != null) {
            arrayList.add(new Predicate<Entity>(s, n2) {
                private final boolean val$flag;
                private final String val$s_f;
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
                
                public boolean apply(final Entity entity) {
                    return entity.getName().equals(this.val$s_f) ^ this.val$flag;
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
                        if (1 == 4) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
        }
        return (List<Predicate<Entity>>)arrayList;
    }
    
    private static List<Predicate<Entity>> func_179649_c(final Map<String, String> map) {
        final ArrayList arrayList = Lists.newArrayList();
        final int intWithDefault = parseIntWithDefault(map, PlayerSelector.I[0xB ^ 0x1D], WorldSettings.GameType.NOT_SET.getID());
        if (intWithDefault != WorldSettings.GameType.NOT_SET.getID()) {
            arrayList.add(new Predicate<Entity>(intWithDefault) {
                private final int val$i;
                
                public boolean apply(final Entity entity) {
                    if (!(entity instanceof EntityPlayerMP)) {
                        return "".length() != 0;
                    }
                    if (((EntityPlayerMP)entity).theItemInWorldManager.getGameType().getID() == this.val$i) {
                        return " ".length() != 0;
                    }
                    return "".length() != 0;
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
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
            });
        }
        return (List<Predicate<Entity>>)arrayList;
    }
    
    private static int parseIntWithDefault(final Map<String, String> map, final String s, final int n) {
        int intWithDefault;
        if (map.containsKey(s)) {
            intWithDefault = MathHelper.parseIntWithDefault(map.get(s), n);
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            intWithDefault = n;
        }
        return intWithDefault;
    }
    
    public static boolean hasArguments(final String s) {
        return PlayerSelector.tokenPattern.matcher(s).matches();
    }
    
    private static List<World> getWorlds(final ICommandSender commandSender, final Map<String, String> map) {
        final ArrayList arrayList = Lists.newArrayList();
        if (func_179665_h(map)) {
            arrayList.add(commandSender.getEntityWorld());
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            Collections.addAll(arrayList, MinecraftServer.getServer().worldServers);
        }
        return (List<World>)arrayList;
    }
    
    public static Map<String, Integer> func_96560_a(final Map<String, String> map) {
        final HashMap hashMap = Maps.newHashMap();
        final Iterator<String> iterator = map.keySet().iterator();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            if (s.startsWith(PlayerSelector.I[0x7A ^ 0x3B]) && s.length() > PlayerSelector.I[0x81 ^ 0xC3].length()) {
                hashMap.put(s.substring(PlayerSelector.I[0x56 ^ 0x15].length()), MathHelper.parseIntWithDefault(map.get(s), " ".length()));
            }
        }
        return (Map<String, Integer>)hashMap;
    }
    
    public static EntityPlayerMP matchOnePlayer(final ICommandSender commandSender, final String s) {
        return matchOneEntity(commandSender, s, (Class<? extends EntityPlayerMP>)EntityPlayerMP.class);
    }
    
    private static List<Predicate<Entity>> func_179659_d(final Map<String, String> map) {
        final ArrayList arrayList = Lists.newArrayList();
        String s = func_179651_b(map, PlayerSelector.I[0x23 ^ 0x34]);
        int n;
        if (s != null && s.startsWith(PlayerSelector.I[0x83 ^ 0x9B])) {
            n = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        if (n2 != 0) {
            s = s.substring(" ".length());
        }
        if (s != null) {
            arrayList.add(new Predicate<Entity>(s, n2) {
                private final String val$s_f;
                private static final String[] I;
                private final boolean val$flag;
                
                private static void I() {
                    (I = new String[" ".length()])["".length()] = I("", "AFvlW");
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
                        if (false) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                public boolean apply(final Entity entity) {
                    if (!(entity instanceof EntityLivingBase)) {
                        return "".length() != 0;
                    }
                    final Team team = ((EntityLivingBase)entity).getTeam();
                    String registeredName;
                    if (team == null) {
                        registeredName = PlayerSelector$5.I["".length()];
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else {
                        registeredName = team.getRegisteredName();
                    }
                    return registeredName.equals(this.val$s_f) ^ this.val$flag;
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
                
                static {
                    I();
                }
            });
        }
        return (List<Predicate<Entity>>)arrayList;
    }
    
    public static <T extends Entity> T matchOneEntity(final ICommandSender commandSender, final String s, final Class<? extends T> clazz) {
        final List<Entity> matchEntities = matchEntities(commandSender, s, (Class<? extends Entity>)clazz);
        Entity entity;
        if (matchEntities.size() == " ".length()) {
            entity = matchEntities.get("".length());
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            entity = null;
        }
        return (T)entity;
    }
    
    public static int func_179650_a(int n) {
        n %= 334 + 29 - 230 + 227;
        if (n >= 43 + 105 - 71 + 83) {
            n -= 360;
        }
        if (n < 0) {
            n += 360;
        }
        return n;
    }
    
    private static AxisAlignedBB func_179661_a(final BlockPos blockPos, final int n, final int n2, final int n3) {
        int n4;
        if (n < 0) {
            n4 = " ".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final int n5 = n4;
        int n6;
        if (n2 < 0) {
            n6 = " ".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            n6 = "".length();
        }
        final int n7 = n6;
        int n8;
        if (n3 < 0) {
            n8 = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n8 = "".length();
        }
        final int n9 = n8;
        final int x = blockPos.getX();
        int length;
        if (n5 != 0) {
            length = n;
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        final int n10 = x + length;
        final int y = blockPos.getY();
        int length2;
        if (n7 != 0) {
            length2 = n2;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            length2 = "".length();
        }
        final int n11 = y + length2;
        final int z = blockPos.getZ();
        int length3;
        if (n9 != 0) {
            length3 = n3;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            length3 = "".length();
        }
        final int n12 = z + length3;
        final int x2 = blockPos.getX();
        int length4;
        if (n5 != 0) {
            length4 = "".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            length4 = n;
        }
        final int n13 = x2 + length4 + " ".length();
        final int y2 = blockPos.getY();
        int length5;
        if (n7 != 0) {
            length5 = "".length();
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            length5 = n2;
        }
        final int n14 = y2 + length5 + " ".length();
        final int z2 = blockPos.getZ();
        int length6;
        if (n9 != 0) {
            length6 = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            length6 = n3;
        }
        return new AxisAlignedBB(n10, n11, n12, n13, n14, z2 + length6 + " ".length());
    }
    
    private static List<Predicate<Entity>> func_180698_a(final Map<String, String> map, final BlockPos blockPos) {
        final ArrayList arrayList = Lists.newArrayList();
        final int intWithDefault = parseIntWithDefault(map, PlayerSelector.I[0xAC ^ 0xB7], -" ".length());
        final int intWithDefault2 = parseIntWithDefault(map, PlayerSelector.I[0x80 ^ 0x9C], -" ".length());
        if (blockPos != null && (intWithDefault >= 0 || intWithDefault2 >= 0)) {
            arrayList.add(new Predicate<Entity>(blockPos, intWithDefault, intWithDefault * intWithDefault, intWithDefault2, intWithDefault2 * intWithDefault2) {
                private final int val$k;
                private final BlockPos val$p_180698_1_;
                private final int val$j;
                private final int val$i;
                private final int val$l;
                
                public boolean apply(final Entity entity) {
                    final int n = (int)entity.getDistanceSqToCenter(this.val$p_180698_1_);
                    if ((this.val$i < 0 || n >= this.val$k) && (this.val$j < 0 || n <= this.val$l)) {
                        return " ".length() != 0;
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
                        if (3 == 0) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
            });
        }
        return (List<Predicate<Entity>>)arrayList;
    }
    
    private static BlockPos func_179664_b(final Map<String, String> map, final BlockPos blockPos) {
        return new BlockPos(parseIntWithDefault(map, PlayerSelector.I[0x7 ^ 0x39], blockPos.getX()), parseIntWithDefault(map, PlayerSelector.I[0x3A ^ 0x5], blockPos.getY()), parseIntWithDefault(map, PlayerSelector.I[0x25 ^ 0x65], blockPos.getZ()));
    }
    
    public static <T extends Entity> List<T> matchEntities(final ICommandSender commandSender, final String s, final Class<? extends T> clazz) {
        final Matcher matcher = PlayerSelector.tokenPattern.matcher(s);
        if (!matcher.matches() || !commandSender.canCommandSenderUseCommand(" ".length(), PlayerSelector.I[0x44 ^ 0x4F])) {
            return Collections.emptyList();
        }
        final Map<String, String> argumentMap = getArgumentMap(matcher.group("  ".length()));
        if (!isEntityTypeValid(commandSender, argumentMap)) {
            return Collections.emptyList();
        }
        final String group = matcher.group(" ".length());
        final BlockPos func_179664_b = func_179664_b(argumentMap, commandSender.getPosition());
        final List<World> worlds = getWorlds(commandSender, argumentMap);
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<World> iterator = worlds.iterator();
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final World world = iterator.next();
            if (world != null) {
                final ArrayList arrayList2 = Lists.newArrayList();
                arrayList2.addAll(func_179663_a(argumentMap, group));
                arrayList2.addAll(func_179648_b(argumentMap));
                arrayList2.addAll(func_179649_c(argumentMap));
                arrayList2.addAll(func_179659_d(argumentMap));
                arrayList2.addAll(func_179657_e(argumentMap));
                arrayList2.addAll(func_179647_f(argumentMap));
                arrayList2.addAll(func_180698_a(argumentMap, func_179664_b));
                arrayList2.addAll(func_179662_g(argumentMap));
                arrayList.addAll(filterResults(argumentMap, (Class<? extends Entity>)clazz, (List<Predicate<Entity>>)arrayList2, group, world, func_179664_b));
            }
        }
        return func_179658_a((List<T>)arrayList, argumentMap, commandSender, clazz, group, func_179664_b);
    }
    
    public static boolean matchesMultiplePlayers(final String s) {
        final Matcher matcher = PlayerSelector.tokenPattern.matcher(s);
        if (!matcher.matches()) {
            return "".length() != 0;
        }
        final Map<String, String> argumentMap = getArgumentMap(matcher.group("  ".length()));
        final String group = matcher.group(" ".length());
        int n;
        if (!PlayerSelector.I[0xD8 ^ 0x9C].equals(group) && !PlayerSelector.I[0x30 ^ 0x75].equals(group)) {
            n = " ".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        if (parseIntWithDefault(argumentMap, PlayerSelector.I[0xD2 ^ 0x94], n) != " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static <T extends Entity> List<T> filterResults(final Map<String, String> map, final Class<? extends T> clazz, final List<Predicate<Entity>> list, final String s, final World world, final BlockPos blockPos) {
        final ArrayList arrayList = Lists.newArrayList();
        final String func_179651_b = func_179651_b(map, PlayerSelector.I[0xAF ^ 0x8A]);
        String substring;
        if (func_179651_b != null && func_179651_b.startsWith(PlayerSelector.I[0x34 ^ 0x12])) {
            substring = func_179651_b.substring(" ".length());
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            substring = func_179651_b;
        }
        final String s2 = substring;
        int n;
        if (s.equals(PlayerSelector.I[0x6A ^ 0x4D])) {
            n = "".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final int n2 = n;
        int n3;
        if (s.equals(PlayerSelector.I[0xBD ^ 0x95]) && s2 != null) {
            n3 = " ".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        final int n4 = n3;
        final int intWithDefault = parseIntWithDefault(map, PlayerSelector.I[0xB0 ^ 0x99], "".length());
        final int intWithDefault2 = parseIntWithDefault(map, PlayerSelector.I[0x2C ^ 0x6], "".length());
        final int intWithDefault3 = parseIntWithDefault(map, PlayerSelector.I[0x17 ^ 0x3C], "".length());
        final int intWithDefault4 = parseIntWithDefault(map, PlayerSelector.I[0xD ^ 0x21], -" ".length());
        final Predicate and = Predicates.and((Iterable)list);
        final Predicate and2 = Predicates.and((Predicate)EntitySelectors.selectAnything, and);
        if (blockPos != null) {
            int n5;
            if (world.playerEntities.size() < world.loadedEntityList.size() / (0x86 ^ 0x96)) {
                n5 = " ".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                n5 = "".length();
            }
            final int n6 = n5;
            if (!map.containsKey(PlayerSelector.I[0x4E ^ 0x63]) && !map.containsKey(PlayerSelector.I[0x2E ^ 0x0]) && !map.containsKey(PlayerSelector.I[0x9B ^ 0xB4])) {
                if (intWithDefault4 >= 0) {
                    final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos.getX() - intWithDefault4, blockPos.getY() - intWithDefault4, blockPos.getZ() - intWithDefault4, blockPos.getX() + intWithDefault4 + " ".length(), blockPos.getY() + intWithDefault4 + " ".length(), blockPos.getZ() + intWithDefault4 + " ".length());
                    if (n2 != 0 && n6 != 0 && n4 == 0) {
                        arrayList.addAll(world.getPlayers((Class<? extends Entity>)clazz, (com.google.common.base.Predicate<? super Entity>)and2));
                        "".length();
                        if (2 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        arrayList.addAll(world.getEntitiesWithinAABB((Class<? extends Entity>)clazz, axisAlignedBB, (com.google.common.base.Predicate<? super Entity>)and2));
                        "".length();
                        if (3 <= 0) {
                            throw null;
                        }
                    }
                }
                else if (s.equals(PlayerSelector.I[0xB5 ^ 0x85])) {
                    arrayList.addAll(world.getPlayers((Class<? extends Entity>)clazz, (com.google.common.base.Predicate<? super Entity>)and));
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (!s.equals(PlayerSelector.I[0x71 ^ 0x40]) && (!s.equals(PlayerSelector.I[0x22 ^ 0x10]) || n4 != 0)) {
                    arrayList.addAll(world.getEntities((Class<? extends Entity>)clazz, (com.google.common.base.Predicate<? super Entity>)and2));
                    "".length();
                    if (1 < 1) {
                        throw null;
                    }
                }
                else {
                    arrayList.addAll(world.getPlayers((Class<? extends Entity>)clazz, (com.google.common.base.Predicate<? super Entity>)and2));
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
            }
            else {
                final AxisAlignedBB func_179661_a = func_179661_a(blockPos, intWithDefault, intWithDefault2, intWithDefault3);
                if (n2 != 0 && n6 != 0 && n4 == 0) {
                    arrayList.addAll(world.getPlayers((Class<? extends Entity>)clazz, (com.google.common.base.Predicate<? super Entity>)Predicates.and(and2, (Predicate)new Predicate<Entity>(func_179661_a) {
                        private final AxisAlignedBB val$axisalignedbb;
                        
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
                                if (2 != 2) {
                                    throw null;
                                }
                            }
                            return sb.toString();
                        }
                        
                        public boolean apply(final Object o) {
                            return this.apply((Entity)o);
                        }
                        
                        public boolean apply(final Entity entity) {
                            int n;
                            if (entity.posX >= this.val$axisalignedbb.minX && entity.posY >= this.val$axisalignedbb.minY && entity.posZ >= this.val$axisalignedbb.minZ) {
                                if (entity.posX < this.val$axisalignedbb.maxX && entity.posY < this.val$axisalignedbb.maxY && entity.posZ < this.val$axisalignedbb.maxZ) {
                                    n = " ".length();
                                    "".length();
                                    if (-1 != -1) {
                                        throw null;
                                    }
                                }
                                else {
                                    n = "".length();
                                    "".length();
                                    if (3 < 2) {
                                        throw null;
                                    }
                                }
                            }
                            else {
                                n = "".length();
                            }
                            return n != 0;
                        }
                    })));
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    arrayList.addAll(world.getEntitiesWithinAABB((Class<? extends Entity>)clazz, func_179661_a, (com.google.common.base.Predicate<? super Entity>)and2));
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                }
            }
        }
        else if (s.equals(PlayerSelector.I[0x8C ^ 0xBF])) {
            arrayList.addAll(world.getPlayers((Class<? extends Entity>)clazz, (com.google.common.base.Predicate<? super Entity>)and));
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else if (!s.equals(PlayerSelector.I[0x26 ^ 0x12]) && (!s.equals(PlayerSelector.I[0x42 ^ 0x77]) || n4 != 0)) {
            arrayList.addAll(world.getEntities((Class<? extends Entity>)clazz, (com.google.common.base.Predicate<? super Entity>)and2));
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else {
            arrayList.addAll(world.getPlayers((Class<? extends Entity>)clazz, (com.google.common.base.Predicate<? super Entity>)and2));
        }
        return (List<T>)arrayList;
    }
    
    private static List<Predicate<Entity>> func_179663_a(final Map<String, String> map, final String s) {
        final ArrayList arrayList = Lists.newArrayList();
        String s2 = func_179651_b(map, PlayerSelector.I[0x5C ^ 0x53]);
        int n;
        if (s2 != null && s2.startsWith(PlayerSelector.I[0xD ^ 0x1D])) {
            n = " ".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final boolean b = n != 0;
        if (b) {
            s2 = s2.substring(" ".length());
        }
        int n2;
        if (s.equals(PlayerSelector.I[0xB4 ^ 0xA5])) {
            n2 = "".length();
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        final int n3 = n2;
        int n4;
        if (s.equals(PlayerSelector.I[0xB1 ^ 0xA3]) && s2 != null) {
            n4 = " ".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final int n5 = n4;
        if ((s2 == null || !s.equals(PlayerSelector.I[0x79 ^ 0x6A])) && n5 == 0) {
            if (n3 != 0) {
                arrayList.add(new Predicate<Entity>() {
                    public boolean apply(final Entity entity) {
                        return entity instanceof EntityPlayer;
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
                            if (2 < -1) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    public boolean apply(final Object o) {
                        return this.apply((Entity)o);
                    }
                });
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
        }
        else {
            arrayList.add(new Predicate<Entity>(s2, b) {
                private final boolean val$flag;
                private final String val$s_f;
                
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
                        if (-1 >= 3) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                public boolean apply(final Entity entity) {
                    return EntityList.isStringEntityName(entity, this.val$s_f) ^ this.val$flag;
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
            });
        }
        return (List<Predicate<Entity>>)arrayList;
    }
    
    private static List<Predicate<Entity>> func_179662_g(final Map<String, String> map) {
        final ArrayList arrayList = Lists.newArrayList();
        if (map.containsKey(PlayerSelector.I[0x14 ^ 0x9]) || map.containsKey(PlayerSelector.I[0x4B ^ 0x55])) {
            arrayList.add(new Predicate<Entity>(func_179650_a(parseIntWithDefault(map, PlayerSelector.I[0x6A ^ 0x75], "".length())), func_179650_a(parseIntWithDefault(map, PlayerSelector.I[0x92 ^ 0xB2], 7 + 337 - 320 + 335))) {
                private final int val$j;
                private final int val$i;
                
                public boolean apply(final Entity entity) {
                    final int func_179650_a = PlayerSelector.func_179650_a((int)Math.floor(entity.rotationYaw));
                    int n;
                    if (this.val$i > this.val$j) {
                        if (func_179650_a < this.val$i && func_179650_a > this.val$j) {
                            n = "".length();
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                        }
                        else {
                            n = " ".length();
                            "".length();
                            if (false) {
                                throw null;
                            }
                        }
                    }
                    else if (func_179650_a >= this.val$i && func_179650_a <= this.val$j) {
                        n = " ".length();
                        "".length();
                        if (1 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                    }
                    return n != 0;
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
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
                        if (1 <= -1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
        }
        if (map.containsKey(PlayerSelector.I[0x19 ^ 0x38]) || map.containsKey(PlayerSelector.I[0x1A ^ 0x38])) {
            arrayList.add(new Predicate<Entity>(func_179650_a(parseIntWithDefault(map, PlayerSelector.I[0xA3 ^ 0x80], "".length())), func_179650_a(parseIntWithDefault(map, PlayerSelector.I[0x9D ^ 0xB9], 165 + 130 - 219 + 283))) {
                private final int val$k;
                private final int val$l;
                
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
                        if (1 <= 0) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                public boolean apply(final Entity entity) {
                    final int func_179650_a = PlayerSelector.func_179650_a((int)Math.floor(entity.rotationPitch));
                    int n;
                    if (this.val$k > this.val$l) {
                        if (func_179650_a < this.val$k && func_179650_a > this.val$l) {
                            n = "".length();
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else {
                            n = " ".length();
                            "".length();
                            if (0 < 0) {
                                throw null;
                            }
                        }
                    }
                    else if (func_179650_a >= this.val$k && func_179650_a <= this.val$l) {
                        n = " ".length();
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                    }
                    return n != 0;
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Entity)o);
                }
            });
        }
        return (List<Predicate<Entity>>)arrayList;
    }
    
    public static IChatComponent matchEntitiesToChatComponent(final ICommandSender commandSender, final String s) {
        final List<Entity> matchEntities = matchEntities(commandSender, s, (Class<? extends Entity>)Entity.class);
        if (matchEntities.isEmpty()) {
            return null;
        }
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Entity> iterator = matchEntities.iterator();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getDisplayName());
        }
        return CommandBase.join(arrayList);
    }
    
    private static void I() {
        (I = new String[0x49 ^ 0x2])["".length()] = I("+%[.5\u0014\u0017\u0016(l]ZI)\u001e]>/\u0002xYD^(o\\9.\\zQ", "uesuE");
        PlayerSelector.I[" ".length()] = I("\u00153M\u0019Oh)Z\u0019>>Y8hKaK_f\u001ee]", "IteBb");
        PlayerSelector.I["  ".length()] = I("\u000f-^=-xCKI\u0001~K+^\u0001\u000f\u001d[<pzBI[~/F_", "SjvaZ");
        PlayerSelector.I["   ".length()] = I("\r", "uPnvs");
        PlayerSelector.I[0x58 ^ 0x5C] = I("#", "ZwEtl");
        PlayerSelector.I[0x6D ^ 0x68] = I("(", "RpphS");
        PlayerSelector.I[0x65 ^ 0x63] = I("--", "IUVgt");
        PlayerSelector.I[0xA3 ^ 0xA4] = I("3=", "WDNOM");
        PlayerSelector.I[0x77 ^ 0x7F] = I("6 ", "RZZGw");
        PlayerSelector.I[0x1B ^ 0x12] = I("'\u0018", "UuKEC");
        PlayerSelector.I[0x2F ^ 0x25] = I("\u001d", "oFkxF");
        PlayerSelector.I[0x2B ^ 0x20] = I("!", "aNWBF");
        PlayerSelector.I[0x46 ^ 0x4A] = I("\u0001\u0013\u0000.", "ujpKe");
        PlayerSelector.I[0x5C ^ 0x51] = I("Y", "xQyla");
        PlayerSelector.I[0x95 ^ 0x9B] = I("\u00159:>\u001b\u00182$}\u001d\u001382!\u0013\u0015x2=\u000e\u001f\".}\u0013\u0018 6?\u0013\u0012\u0002.#\u001f", "vVWSz");
        PlayerSelector.I[0x3A ^ 0x35] = I("\u001e\u0000\u0014\u001f", "jydzy");
        PlayerSelector.I[0x42 ^ 0x52] = I("O", "nJiCn");
        PlayerSelector.I[0xB2 ^ 0xA3] = I("0", "UmUQc");
        PlayerSelector.I[0xBA ^ 0xA8] = I("\u0004", "vchzj");
        PlayerSelector.I[0x85 ^ 0x96] = I("\u0001", "dXEmP");
        PlayerSelector.I[0x14 ^ 0x0] = I("4\u0005", "XhzlH");
        PlayerSelector.I[0x68 ^ 0x7D] = I("\u0002", "nVtou");
        PlayerSelector.I[0x67 ^ 0x71] = I("$", "ImGAY");
        PlayerSelector.I[0xAB ^ 0xBC] = I("\u0000\u0016%\u001d", "tsDpM");
        PlayerSelector.I[0x68 ^ 0x70] = I("p", "QUJin");
        PlayerSelector.I[0xB3 ^ 0xAA] = I(";\u00079\u0000", "UfTeB");
        PlayerSelector.I[0x69 ^ 0x73] = I("Y", "xBwdN");
        PlayerSelector.I[0x1F ^ 0x4] = I("0,", "BALWZ");
        PlayerSelector.I[0x1D ^ 0x1] = I(":", "HKhnX");
        PlayerSelector.I[0x1E ^ 0x3] = I("'\u0000\u0000", "UymTg");
        PlayerSelector.I[0x49 ^ 0x57] = I("\u0019 ", "kYyhl");
        PlayerSelector.I[0x3 ^ 0x1C] = I("\u001a\u000e\u000e", "hwcln");
        PlayerSelector.I[0x44 ^ 0x64] = I("\u001a/", "hVfPZ");
        PlayerSelector.I[0x91 ^ 0xB0] = I("\u001e\u0019\u0002", "laoVm");
        PlayerSelector.I[0x16 ^ 0x34] = I("\u001f\u0012", "mjvIF");
        PlayerSelector.I[0x25 ^ 0x6] = I("\u0000\u000b\u0001", "rslBj");
        PlayerSelector.I[0xE7 ^ 0xC3] = I("\u0000\u0010", "rhAKD");
        PlayerSelector.I[0x61 ^ 0x44] = I("\u0003\u0017\u0006!", "wnvDo");
        PlayerSelector.I[0x41 ^ 0x67] = I("U", "tdOrk");
        PlayerSelector.I[0x28 ^ 0xF] = I("\u001c", "ytmed");
        PlayerSelector.I[0x6F ^ 0x47] = I("=", "OJVeO");
        PlayerSelector.I[0x8E ^ 0xA7] = I("\u000f\u0014", "klEnS");
        PlayerSelector.I[0xEA ^ 0xC0] = I("=>", "YGRYQ");
        PlayerSelector.I[0xAA ^ 0x81] = I(".(", "JRUVd");
        PlayerSelector.I[0x99 ^ 0xB5] = I("0", "BYWiT");
        PlayerSelector.I[0xB3 ^ 0x9E] = I("\u0012;", "vCSJc");
        PlayerSelector.I[0x4A ^ 0x64] = I(")6", "MOKPI");
        PlayerSelector.I[0x83 ^ 0xAC] = I("\u0006\u0010", "bjpJN");
        PlayerSelector.I[0x8F ^ 0xBF] = I("\u0018", "yzXLc");
        PlayerSelector.I[0x4C ^ 0x7D] = I("\u0011", "ayZiO");
        PlayerSelector.I[0x51 ^ 0x63] = I("0", "BOVwW");
        PlayerSelector.I[0xB7 ^ 0x84] = I("4", "UymfL");
        PlayerSelector.I[0x4F ^ 0x7B] = I("\u001c", "lIeDL");
        PlayerSelector.I[0x51 ^ 0x64] = I("<", "NKSgl");
        PlayerSelector.I[0xB8 ^ 0x8E] = I("9", "ZuvMu");
        PlayerSelector.I[0x9B ^ 0xAC] = I("\u000b", "jRTgi");
        PlayerSelector.I[0x62 ^ 0x5A] = I("\u0012", "wUeHg");
        PlayerSelector.I[0xE ^ 0x37] = I("\u0007", "wvhtY");
        PlayerSelector.I[0x1D ^ 0x27] = I(".", "OsgQq");
        PlayerSelector.I[0x13 ^ 0x28] = I("&", "CUmfH");
        PlayerSelector.I[0x23 ^ 0x1F] = I(" ", "RKMnV");
        PlayerSelector.I[0x2A ^ 0x17] = I("=", "OimLX");
        PlayerSelector.I[0x4A ^ 0x74] = I(" ", "XhJtV");
        PlayerSelector.I[0x7 ^ 0x38] = I("8", "ARwSM");
        PlayerSelector.I[0x69 ^ 0x29] = I("1", "KTrRH");
        PlayerSelector.I[0x3F ^ 0x7E] = I("!\u0017* \u0003\r", "RtERf");
        PlayerSelector.I[0xDB ^ 0x99] = I(":\u0013>\u0006\u0013\u0016", "IpQtv");
        PlayerSelector.I[0x3B ^ 0x78] = I("=\u00119\u00070\u0011", "NrVuU");
        PlayerSelector.I[0xCB ^ 0x8F] = I(")", "HSvcf");
        PlayerSelector.I[0xF5 ^ 0xB0] = I("&", "CsgRI");
        PlayerSelector.I[0x77 ^ 0x31] = I(" ", "CtbJT");
        PlayerSelector.I[0xB ^ 0x4C] = I("-", "UBKKC");
        PlayerSelector.I[0x38 ^ 0x70] = I("5", "LyjAS");
        PlayerSelector.I[0xD5 ^ 0x9C] = I(".", "TyKDI");
        PlayerSelector.I[0xE3 ^ 0xA9] = I("\u0007", "uIAID");
    }
    
    static {
        I();
        tokenPattern = Pattern.compile(PlayerSelector.I["".length()]);
        intListPattern = Pattern.compile(PlayerSelector.I[" ".length()]);
        keyValueListPattern = Pattern.compile(PlayerSelector.I["  ".length()]);
        final String[] array = new String[0x40 ^ 0x48];
        array["".length()] = PlayerSelector.I["   ".length()];
        array[" ".length()] = PlayerSelector.I[0xA1 ^ 0xA5];
        array["  ".length()] = PlayerSelector.I[0x39 ^ 0x3C];
        array["   ".length()] = PlayerSelector.I[0x32 ^ 0x34];
        array[0x57 ^ 0x53] = PlayerSelector.I[0x2C ^ 0x2B];
        array[0xB1 ^ 0xB4] = PlayerSelector.I[0x30 ^ 0x38];
        array[0x72 ^ 0x74] = PlayerSelector.I[0x30 ^ 0x39];
        array[0xB7 ^ 0xB0] = PlayerSelector.I[0x27 ^ 0x2D];
        WORLD_BINDING_ARGS = Sets.newHashSet((Object[])array);
    }
}
