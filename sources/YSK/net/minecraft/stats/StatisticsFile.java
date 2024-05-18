package net.minecraft.stats;

import net.minecraft.server.*;
import com.google.common.collect.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import org.apache.logging.log4j.*;
import java.util.*;
import org.apache.commons.io.*;
import java.io.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.server.management.*;
import com.google.gson.*;

public class StatisticsFile extends StatFileWriter
{
    private static final String[] I;
    private final MinecraftServer mcServer;
    private int field_150885_f;
    private final Set<StatBase> field_150888_e;
    private final File statsFile;
    private boolean field_150886_g;
    private static final Logger logger;
    
    public void func_150877_d() {
        final Iterator<StatBase> iterator = this.statsData.keySet().iterator();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.field_150888_e.add(iterator.next());
        }
    }
    
    private static void I() {
        (I = new String[0x55 ^ 0x46])["".length()] = I("%)\"#3\ba#o%\u0003'3o$\u0012'#&$\u0012/4<w\u0000/;*w", "fFWOW");
        StatisticsFile.I[" ".length()] = I(")\b8=7\u0004@9q#\u000b\u0015>4s\u0019\u0013,%:\u0019\u0013$2 J\u0001$=6J", "jgMQS");
        StatisticsFile.I["  ".length()] = I("\u0007\u0006\u0007\u0015\u000e*N\u0006Y\u0019%\u001f\u0017Y\u00190\b\u0006\n", "Diryj");
        StatisticsFile.I["   ".length()] = I("\n<\r'x\u001d-\u001c6x\b7\u0004:3\u001f1\u000168\u001d", "iTlSV");
        StatisticsFile.I[0x33 ^ 0x37] = I("\u0002\u0012\u0000;i\u0015\u0003\u0011*i\u0000\u0019\t&\"\u0017\u001f\f*)\u0015T\u0015.,\u0004\u0014", "azaOG");
        StatisticsFile.I[0x76 ^ 0x73] = I("$\t\u001c\u0016*", "RhpcO");
        StatisticsFile.I[0x49 ^ 0x4F] = I("\u00022\u001f\u001e+", "tSskN");
        StatisticsFile.I[0x9F ^ 0x98] = I("\u00065%?-", "pTIJH");
        StatisticsFile.I[0x45 ^ 0x4D] = I("3(+\u0011$", "EIGdA");
        StatisticsFile.I[0xBF ^ 0xB6] = I("\u001b:\u001b\u0006(\u000e;\u0007", "kHtaZ");
        StatisticsFile.I[0x1E ^ 0x14] = I("\u0016&?\u001e(\u0003'#", "fTPyZ");
        StatisticsFile.I[0x9E ^ 0x95] = I("\u000b\u00040\t +\u000ef\u001b8#\u001e/\u001b8+\tf\u0018>-\r4\r?1J/\u0006l", "BjFhL");
        StatisticsFile.I[0x25 ^ 0x29] = I(".8\u0007\u0014#\u000e2Q\u0006;\u0006\"\u0018\u0006;\u000e5Q\u001c!G", "gVquO");
        StatisticsFile.I[0x7B ^ 0x76] = I("Jv2=9W\"V99\u001f!V%?\u0011\"V", "pVvRW");
        StatisticsFile.I[0x96 ^ 0x98] = I("y\u001d*", "YtYbt");
        StatisticsFile.I[0xA5 ^ 0xAA] = I("24\"73", "DUNBV");
        StatisticsFile.I[0x1C ^ 0xC] = I("\u0000\u000b\u000b\u000e\u0010\u0015\n\u0017", "pydib");
        StatisticsFile.I[0x13 ^ 0x2] = I("5\b\u0018\u001c+\u0018@\u0019P<\u0017\u0011\bP<\u0002\u0006\u0019\u0019<\u0002\u000e\u000eP", "vgmpO");
        StatisticsFile.I[0x44 ^ 0x56] = I("bY5!%7\u000bp 2*\u00101?>\"\u0010>4w(\u000b?4%=\n#", "XyPSW");
    }
    
    public StatisticsFile(final MinecraftServer mcServer, final File statsFile) {
        this.field_150888_e = (Set<StatBase>)Sets.newHashSet();
        this.field_150885_f = -(116 + 201 - 150 + 133);
        this.field_150886_g = ("".length() != 0);
        this.mcServer = mcServer;
        this.statsFile = statsFile;
    }
    
    public boolean func_150879_e() {
        return this.field_150886_g;
    }
    
    public static String dumpJson(final Map<StatBase, TupleIntJsonSerializable> map) {
        final JsonObject jsonObject = new JsonObject();
        final Iterator<Map.Entry<StatBase, TupleIntJsonSerializable>> iterator = map.entrySet().iterator();
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<StatBase, TupleIntJsonSerializable> entry = iterator.next();
            if (entry.getValue().getJsonSerializableValue() != null) {
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty(StatisticsFile.I[0x8D ^ 0x82], (Number)entry.getValue().getIntegerValue());
                try {
                    jsonObject2.add(StatisticsFile.I[0x51 ^ 0x41], entry.getValue().getJsonSerializableValue().getSerializableElement());
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                }
                catch (Throwable t) {
                    StatisticsFile.logger.warn(StatisticsFile.I[0xA8 ^ 0xB9] + entry.getKey().getStatName() + StatisticsFile.I[0x5F ^ 0x4D], t);
                }
                jsonObject.add(entry.getKey().statId, (JsonElement)jsonObject2);
                "".length();
                if (4 <= -1) {
                    throw null;
                }
                continue;
            }
            else {
                jsonObject.addProperty(entry.getKey().statId, (Number)entry.getValue().getIntegerValue());
            }
        }
        return jsonObject.toString();
    }
    
    public void func_150876_a(final EntityPlayerMP entityPlayerMP) {
        final int tickCounter = this.mcServer.getTickCounter();
        final HashMap hashMap = Maps.newHashMap();
        if (this.field_150886_g || tickCounter - this.field_150885_f > 201 + 3 - 53 + 149) {
            this.field_150885_f = tickCounter;
            final Iterator<StatBase> iterator = this.func_150878_c().iterator();
            "".length();
            if (4 < 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final StatBase statBase = iterator.next();
                hashMap.put(statBase, this.readStat(statBase));
            }
        }
        entityPlayerMP.playerNetServerHandler.sendPacket(new S37PacketStatistics(hashMap));
    }
    
    public Map<StatBase, TupleIntJsonSerializable> parseJson(final String s) {
        final JsonElement parse = new JsonParser().parse(s);
        if (!parse.isJsonObject()) {
            return (Map<StatBase, TupleIntJsonSerializable>)Maps.newHashMap();
        }
        final JsonObject asJsonObject = parse.getAsJsonObject();
        final HashMap hashMap = Maps.newHashMap();
        final Iterator iterator = asJsonObject.entrySet().iterator();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, V> entry = iterator.next();
            final StatBase oneShotStat = StatList.getOneShotStat(entry.getKey());
            if (oneShotStat != null) {
                final TupleIntJsonSerializable tupleIntJsonSerializable = new TupleIntJsonSerializable();
                if (((JsonElement)entry.getValue()).isJsonPrimitive() && ((JsonElement)entry.getValue()).getAsJsonPrimitive().isNumber()) {
                    tupleIntJsonSerializable.setIntegerValue(((JsonElement)entry.getValue()).getAsInt());
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
                else if (((JsonElement)entry.getValue()).isJsonObject()) {
                    final JsonObject asJsonObject2 = ((JsonElement)entry.getValue()).getAsJsonObject();
                    if (asJsonObject2.has(StatisticsFile.I[0xB7 ^ 0xB2]) && asJsonObject2.get(StatisticsFile.I[0x8D ^ 0x8B]).isJsonPrimitive() && asJsonObject2.get(StatisticsFile.I[0xB0 ^ 0xB7]).getAsJsonPrimitive().isNumber()) {
                        tupleIntJsonSerializable.setIntegerValue(asJsonObject2.getAsJsonPrimitive(StatisticsFile.I[0x92 ^ 0x9A]).getAsInt());
                    }
                    if (asJsonObject2.has(StatisticsFile.I[0x8D ^ 0x84]) && oneShotStat.func_150954_l() != null) {
                        try {
                            final IJsonSerializable jsonSerializableValue = (IJsonSerializable)oneShotStat.func_150954_l().getConstructor((Class<?>[])new Class["".length()]).newInstance(new Object["".length()]);
                            jsonSerializableValue.fromJson(asJsonObject2.get(StatisticsFile.I[0x2C ^ 0x26]));
                            tupleIntJsonSerializable.setJsonSerializableValue(jsonSerializableValue);
                            "".length();
                            if (2 == -1) {
                                throw null;
                            }
                        }
                        catch (Throwable t) {
                            StatisticsFile.logger.warn(StatisticsFile.I[0x66 ^ 0x6D] + this.statsFile, t);
                        }
                    }
                }
                hashMap.put(oneShotStat, tupleIntJsonSerializable);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                continue;
            }
            else {
                StatisticsFile.logger.warn(StatisticsFile.I[0x12 ^ 0x1E] + this.statsFile + StatisticsFile.I[0xAD ^ 0xA0] + entry.getKey() + StatisticsFile.I[0x82 ^ 0x8C]);
            }
        }
        return (Map<StatBase, TupleIntJsonSerializable>)hashMap;
    }
    
    public void sendAchievements(final EntityPlayerMP entityPlayerMP) {
        final HashMap hashMap = Maps.newHashMap();
        final Iterator<Achievement> iterator = AchievementList.achievementList.iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Achievement achievement = iterator.next();
            if (this.hasAchievementUnlocked(achievement)) {
                hashMap.put(achievement, this.readStat(achievement));
                this.field_150888_e.remove(achievement);
            }
        }
        entityPlayerMP.playerNetServerHandler.sendPacket(new S37PacketStatistics(hashMap));
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    public Set<StatBase> func_150878_c() {
        final HashSet hashSet = Sets.newHashSet((Iterable)this.field_150888_e);
        this.field_150888_e.clear();
        this.field_150886_g = ("".length() != 0);
        return (Set<StatBase>)hashSet;
    }
    
    public void saveStatFile() {
        try {
            FileUtils.writeStringToFile(this.statsFile, dumpJson(this.statsData));
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        catch (IOException ex) {
            StatisticsFile.logger.error(StatisticsFile.I["  ".length()], (Throwable)ex);
        }
    }
    
    @Override
    public void unlockAchievement(final EntityPlayer entityPlayer, final StatBase statBase, final int n) {
        int n2;
        if (statBase.isAchievement()) {
            n2 = this.readStat(statBase);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        super.unlockAchievement(entityPlayer, statBase, n);
        this.field_150888_e.add(statBase);
        if (statBase.isAchievement() && n3 == 0 && n > 0) {
            this.field_150886_g = (" ".length() != 0);
            if (this.mcServer.isAnnouncingPlayerAchievements()) {
                final ServerConfigurationManager configurationManager = this.mcServer.getConfigurationManager();
                final String s = StatisticsFile.I["   ".length()];
                final Object[] array = new Object["  ".length()];
                array["".length()] = entityPlayer.getDisplayName();
                array[" ".length()] = statBase.func_150955_j();
                configurationManager.sendChatMsg(new ChatComponentTranslation(s, array));
            }
        }
        if (statBase.isAchievement() && n3 > 0 && n == 0) {
            this.field_150886_g = (" ".length() != 0);
            if (this.mcServer.isAnnouncingPlayerAchievements()) {
                final ServerConfigurationManager configurationManager2 = this.mcServer.getConfigurationManager();
                final String s2 = StatisticsFile.I[0x8F ^ 0x8B];
                final Object[] array2 = new Object["  ".length()];
                array2["".length()] = entityPlayer.getDisplayName();
                array2[" ".length()] = statBase.func_150955_j();
                configurationManager2.sendChatMsg(new ChatComponentTranslation(s2, array2));
            }
        }
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void readStatFile() {
        if (this.statsFile.isFile()) {
            try {
                this.statsData.clear();
                this.statsData.putAll(this.parseJson(FileUtils.readFileToString(this.statsFile)));
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            catch (IOException ex) {
                StatisticsFile.logger.error(StatisticsFile.I["".length()] + this.statsFile, (Throwable)ex);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            catch (JsonParseException ex2) {
                StatisticsFile.logger.error(StatisticsFile.I[" ".length()] + this.statsFile, (Throwable)ex2);
            }
        }
    }
}
