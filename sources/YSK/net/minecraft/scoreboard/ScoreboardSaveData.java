package net.minecraft.scoreboard;

import net.minecraft.world.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class ScoreboardSaveData extends WorldSavedData
{
    private static final Logger logger;
    private static final String[] I;
    private NBTTagCompound delayedInitNbt;
    private Scoreboard theScoreboard;
    
    protected void readDisplayConfig(final NBTTagCompound nbtTagCompound) {
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < (0x32 ^ 0x21)) {
            if (nbtTagCompound.hasKey(ScoreboardSaveData.I[0x31 ^ 0x27] + i, 0xB7 ^ 0xBF)) {
                this.theScoreboard.setObjectiveInDisplaySlot(i, this.theScoreboard.getObjective(nbtTagCompound.getString(ScoreboardSaveData.I[0x85 ^ 0x92] + i)));
            }
            ++i;
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setScoreboard(final Scoreboard theScoreboard) {
        this.theScoreboard = theScoreboard;
        if (this.delayedInitNbt != null) {
            this.readFromNBT(this.delayedInitNbt);
        }
    }
    
    protected void readObjectives(final NBTTagList list) {
        int i = "".length();
        "".length();
        if (2 == 1) {
            throw null;
        }
        while (i < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(i);
            final IScoreObjectiveCriteria scoreObjectiveCriteria = IScoreObjectiveCriteria.INSTANCES.get(compoundTag.getString(ScoreboardSaveData.I[0x4E ^ 0x56]));
            if (scoreObjectiveCriteria != null) {
                String s = compoundTag.getString(ScoreboardSaveData.I[0xAF ^ 0xB6]);
                if (s.length() > (0x52 ^ 0x42)) {
                    s = s.substring("".length(), 0x3A ^ 0x2A);
                }
                final ScoreObjective addScoreObjective = this.theScoreboard.addScoreObjective(s, scoreObjectiveCriteria);
                addScoreObjective.setDisplayName(compoundTag.getString(ScoreboardSaveData.I[0x4F ^ 0x55]));
                addScoreObjective.setRenderType(IScoreObjectiveCriteria.EnumRenderType.func_178795_a(compoundTag.getString(ScoreboardSaveData.I[0x9D ^ 0x86])));
            }
            ++i;
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        if (this.theScoreboard == null) {
            ScoreboardSaveData.logger.warn(ScoreboardSaveData.I[0x2D ^ 0xC]);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            nbtTagCompound.setTag(ScoreboardSaveData.I[0x22 ^ 0x0], this.objectivesToNbt());
            nbtTagCompound.setTag(ScoreboardSaveData.I[0x98 ^ 0xBB], this.scoresToNbt());
            nbtTagCompound.setTag(ScoreboardSaveData.I[0x2A ^ 0xE], this.func_96496_a());
            this.func_96497_d(nbtTagCompound);
        }
    }
    
    protected NBTTagList objectivesToNbt() {
        final NBTTagList list = new NBTTagList();
        final Iterator<ScoreObjective> iterator = this.theScoreboard.getScoreObjectives().iterator();
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ScoreObjective scoreObjective = iterator.next();
            if (scoreObjective.getCriteria() != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setString(ScoreboardSaveData.I[0x8A ^ 0xBB], scoreObjective.getName());
                nbtTagCompound.setString(ScoreboardSaveData.I[0x33 ^ 0x1], scoreObjective.getCriteria().getName());
                nbtTagCompound.setString(ScoreboardSaveData.I[0x61 ^ 0x52], scoreObjective.getDisplayName());
                nbtTagCompound.setString(ScoreboardSaveData.I[0xA ^ 0x3E], scoreObjective.getRenderType().func_178796_a());
                list.appendTag(nbtTagCompound);
            }
        }
        return list;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    protected void func_96502_a(final ScorePlayerTeam scorePlayerTeam, final NBTTagList list) {
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < list.tagCount()) {
            this.theScoreboard.addPlayerToTeam(list.getStringTagAt(i), scorePlayerTeam.getRegisteredName());
            ++i;
        }
    }
    
    private static void I() {
        (I = new String[0xB6 ^ 0x8F])["".length()] = I("\u0011\u0014\u00075(\u0000\u0018\t5)", "bwhGM");
        ScoreboardSaveData.I[" ".length()] = I("81\f05\u0003:\u00100%", "wSfUV");
        ScoreboardSaveData.I["  ".length()] = I("\u001f\")\b<=\u001d+\u001e+*=", "ONHqY");
        ScoreboardSaveData.I["   ".length()] = I(" *:>+\u0005:\u001a\"(\u00100", "dCING");
        ScoreboardSaveData.I[0x41 ^ 0x45] = I("\b/7(5-?\u00174685", "LFDXY");
        ScoreboardSaveData.I[0x9B ^ 0x9E] = I(" !0)%", "tDQDV");
        ScoreboardSaveData.I[0x5 ^ 0x3] = I("\u00165\u00069\u0006", "BPgTu");
        ScoreboardSaveData.I[0x7F ^ 0x78] = I("\u001d\u0000<\u0012", "SaQwR");
        ScoreboardSaveData.I[0x6A ^ 0x62] = I("\u0015\u001d ?!0\r\u001d. 4", "QtSOM");
        ScoreboardSaveData.I[0x47 ^ 0x4E] = I("\u00052.$\u0000>; ;", "QWOIC");
        ScoreboardSaveData.I[0x86 ^ 0x8C] = I("\u0015\u0010\f\u000f\u0010.\u0019\u0002\u0010", "AumbS");
        ScoreboardSaveData.I[0xB8 ^ 0xB3] = I("\u0017\u0003\u0010\u000f\u0013?", "Gquiz");
        ScoreboardSaveData.I[0xAB ^ 0xA7] = I("\u00158\u000e>\u000b>", "FMhXb");
        ScoreboardSaveData.I[0xE ^ 0x3] = I("\u0018=\u0007\u0019\u001f\u001f#\u0002\u0013\u0006==\u00120\u0001+4", "YQkvh");
        ScoreboardSaveData.I[0x42 ^ 0x4C] = I("\u0003\"*\f\u0013\u0004</\u0006\n&\"?%\r0+", "BNFcd");
        ScoreboardSaveData.I[0xAB ^ 0xA4] = I("\u0001.\u0016\u0017!;.\u001d5?+\u0002\u001d':!\"\u0011=6!", "RKsQS");
        ScoreboardSaveData.I[0x85 ^ 0x95] = I(")\u000f1\b9\u0013\u000f:*'\u0003#:8\"\t\u00036\".\t", "zjTNK");
        ScoreboardSaveData.I[0x39 ^ 0x28] = I("\u001b\u0019\u001d\u0011\u00014\u001f&\u001d&<\u001a\u0019\u0018<!\u0001", "UxptU");
        ScoreboardSaveData.I[0x9C ^ 0x8E] = I(";.\u0003=!\u0014(81\u0006\u001c-\u00074\u001c\u00016", "uOnXu");
        ScoreboardSaveData.I[0x48 ^ 0x5B] = I("(\u001c1\u001c\u0005!\u001c#\u001b\f\u000b\u001c\u0006\u0001\u001e\u0005\u001b9\u0004\u0004\u0018\u0000", "lyPhm");
        ScoreboardSaveData.I[0x0 ^ 0x14] = I("\u0000-\u0002\"\f\t-\u0010%\u0005#-5?\u0017-*\n:\r01", "DHcVd");
        ScoreboardSaveData.I[0x98 ^ 0x8D] = I("\u001b\u001e\u0005\u0012*9\u0001", "KrdkO");
        ScoreboardSaveData.I[0x96 ^ 0x80] = I("\n\u0016\u00027=", "yzmCb");
        ScoreboardSaveData.I[0x11 ^ 0x6] = I("\u0001)\b'&", "rEgSy");
        ScoreboardSaveData.I[0x66 ^ 0x7E] = I("\u000b:\u0003\f\u0007:!\u000b6\u0003%-", "HHjxb");
        ScoreboardSaveData.I[0x68 ^ 0x71] = I("67/\u0012", "xVBwF");
        ScoreboardSaveData.I[0x3A ^ 0x20] = I("\u0011,\"3\t4<\u001f\"\b0", "UEQCe");
        ScoreboardSaveData.I[0x35 ^ 0x2E] = I("\u0003\r\u0018\u0017\u0007#<\u000f\u0003\u0007", "Qhvsb");
        ScoreboardSaveData.I[0x2E ^ 0x32] = I("5\u0003/$\t\u000e\b3$", "zaEAj");
        ScoreboardSaveData.I[0x38 ^ 0x25] = I("\u00179\u0002\u0013", "YXova");
        ScoreboardSaveData.I[0x59 ^ 0x47] = I("\u000b\"\u001d;6", "XArIS");
        ScoreboardSaveData.I[0x84 ^ 0x9B] = I("&\u001f&\u00064\u000e", "jpEmQ");
        ScoreboardSaveData.I[0x7D ^ 0x5D] = I("$\u0016\u0016>\u0017\f", "hyuUr");
        ScoreboardSaveData.I[0x56 ^ 0x77] = I("$&*\u00116P ,T!\u0011\"&T!\u0013;1\u00110\u001f51\u0010r\u0007=7\u001c=\u0005 c\u001c3\u0006=-\u0013r\u0011t0\u0017=\u00021!\u001b3\u00020mZ|", "pTCtR");
        ScoreboardSaveData.I[0x1C ^ 0x3E] = I("+*\u0003\n1\u0010!\u001f\n!", "dHioR");
        ScoreboardSaveData.I[0x36 ^ 0x15] = I("\n.955(\u0011;#\"?1", "ZBXLP");
        ScoreboardSaveData.I[0xAF ^ 0x8B] = I("7\u0006#\u0003\u0014", "ccBng");
        ScoreboardSaveData.I[0x97 ^ 0xB2] = I("(\u0000+*", "faFOa");
        ScoreboardSaveData.I[0x9B ^ 0xBD] = I("\u000e\n\u0000\u0003++\u001a=\u0012*/", "JcssG");
        ScoreboardSaveData.I[0x7F ^ 0x58] = I(";\u0004&5\u0007\u0000\r(*", "oaGXD");
        ScoreboardSaveData.I[0x1A ^ 0x32] = I("8\u001a\u0011\r\u0000\u0010", "hhtki");
        ScoreboardSaveData.I[0x37 ^ 0x1E] = I("$\f\u0003'\u0007\u000f", "wyeAn");
        ScoreboardSaveData.I[0x31 ^ 0x1B] = I("\r*<\u0018\r\n49\u0012\u0014(*)1\u0013>#", "LFPwz");
        ScoreboardSaveData.I[0x59 ^ 0x72] = I("80\u0010\u0012\"\u00020\u001b0<\u0012\u001c\u001b\"9\u0018<\u001785\u0018", "kUuTP");
        ScoreboardSaveData.I[0xB ^ 0x27] = I("\u0005,>1\u0000**\u0005='\"/:8=?4", "KMSTT");
        ScoreboardSaveData.I[0x37 ^ 0x1A] = I("\u00074\u0012\u0004\n\u000e4\u0000\u0003\u0003$4%\u0019\u0011*3\u001a\u001c\u000b7(", "CQspb");
        ScoreboardSaveData.I[0xE9 ^ 0xC7] = I("5=#/0\u0017\"", "eQBVU");
        ScoreboardSaveData.I[0x7A ^ 0x55] = I("\u0005\u0019\u0006\u001e\u000b", "vuijT");
        ScoreboardSaveData.I[0x82 ^ 0xB2] = I("/%6\u0000\u000e\n5\u0016\u001c\r\u001f?", "kLEpb");
        ScoreboardSaveData.I[0x92 ^ 0xA3] = I("9\u000f\u0015\u0000", "wnxeH");
        ScoreboardSaveData.I[0xAE ^ 0x9C] = I("378\u001d\u0016\u0002,0'\u0012\u001d ", "pEQis");
        ScoreboardSaveData.I[0x3F ^ 0xC] = I("\r+5%;(;\b4:,", "IBFUW");
        ScoreboardSaveData.I[0x1 ^ 0x35] = I(" \u001d\u00004.\u0000,\u0017 .", "rxnPK");
        ScoreboardSaveData.I[0x83 ^ 0xB6] = I("\u00079\u00065", "IXkPZ");
        ScoreboardSaveData.I[0x92 ^ 0xA4] = I(" \n\u001e\u000f\u0007\u001b\u0001\u0002\u000f", "ohtjd");
        ScoreboardSaveData.I[0x9C ^ 0xAB] = I("\u0012\u0004$\u00100", "AgKbU");
        ScoreboardSaveData.I[0x9A ^ 0xA2] = I("8 \u000b! \u0010", "tOhJE");
    }
    
    protected void readTeams(final NBTTagList list) {
        int i = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(i);
            String s = compoundTag.getString(ScoreboardSaveData.I[0x3B ^ 0x3C]);
            if (s.length() > (0xA7 ^ 0xB7)) {
                s = s.substring("".length(), 0x22 ^ 0x32);
            }
            final ScorePlayerTeam team = this.theScoreboard.createTeam(s);
            String teamName = compoundTag.getString(ScoreboardSaveData.I[0x46 ^ 0x4E]);
            if (teamName.length() > (0x65 ^ 0x45)) {
                teamName = teamName.substring("".length(), 0xAD ^ 0x8D);
            }
            team.setTeamName(teamName);
            if (compoundTag.hasKey(ScoreboardSaveData.I[0x43 ^ 0x4A], 0x71 ^ 0x79)) {
                team.setChatFormat(EnumChatFormatting.getValueByName(compoundTag.getString(ScoreboardSaveData.I[0x7C ^ 0x76])));
            }
            team.setNamePrefix(compoundTag.getString(ScoreboardSaveData.I[0x73 ^ 0x78]));
            team.setNameSuffix(compoundTag.getString(ScoreboardSaveData.I[0xA ^ 0x6]));
            if (compoundTag.hasKey(ScoreboardSaveData.I[0x10 ^ 0x1D], 0x7D ^ 0x1E)) {
                team.setAllowFriendlyFire(compoundTag.getBoolean(ScoreboardSaveData.I[0x88 ^ 0x86]));
            }
            if (compoundTag.hasKey(ScoreboardSaveData.I[0x44 ^ 0x4B], 0x0 ^ 0x63)) {
                team.setSeeFriendlyInvisiblesEnabled(compoundTag.getBoolean(ScoreboardSaveData.I[0xB3 ^ 0xA3]));
            }
            if (compoundTag.hasKey(ScoreboardSaveData.I[0x77 ^ 0x66], 0x9A ^ 0x92)) {
                final Team.EnumVisible func_178824_a = Team.EnumVisible.func_178824_a(compoundTag.getString(ScoreboardSaveData.I[0xB3 ^ 0xA1]));
                if (func_178824_a != null) {
                    team.setNameTagVisibility(func_178824_a);
                }
            }
            if (compoundTag.hasKey(ScoreboardSaveData.I[0x36 ^ 0x25], 0xBF ^ 0xB7)) {
                final Team.EnumVisible func_178824_a2 = Team.EnumVisible.func_178824_a(compoundTag.getString(ScoreboardSaveData.I[0x63 ^ 0x77]));
                if (func_178824_a2 != null) {
                    team.setDeathMessageVisibility(func_178824_a2);
                }
            }
            this.func_96502_a(team, compoundTag.getTagList(ScoreboardSaveData.I[0x68 ^ 0x7D], 0x92 ^ 0x9A));
            ++i;
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound delayedInitNbt) {
        if (this.theScoreboard == null) {
            this.delayedInitNbt = delayedInitNbt;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            this.readObjectives(delayedInitNbt.getTagList(ScoreboardSaveData.I[" ".length()], 0x40 ^ 0x4A));
            this.readScores(delayedInitNbt.getTagList(ScoreboardSaveData.I["  ".length()], 0x15 ^ 0x1F));
            if (delayedInitNbt.hasKey(ScoreboardSaveData.I["   ".length()], 0x3B ^ 0x31)) {
                this.readDisplayConfig(delayedInitNbt.getCompoundTag(ScoreboardSaveData.I[0x4D ^ 0x49]));
            }
            if (delayedInitNbt.hasKey(ScoreboardSaveData.I[0x7B ^ 0x7E], 0x15 ^ 0x1C)) {
                this.readTeams(delayedInitNbt.getTagList(ScoreboardSaveData.I[0x45 ^ 0x43], 0x14 ^ 0x1E));
            }
        }
    }
    
    protected void readScores(final NBTTagList list) {
        int i = "".length();
        "".length();
        if (2 == 3) {
            throw null;
        }
        while (i < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(i);
            final ScoreObjective objective = this.theScoreboard.getObjective(compoundTag.getString(ScoreboardSaveData.I[0x85 ^ 0x99]));
            String s = compoundTag.getString(ScoreboardSaveData.I[0x3F ^ 0x22]);
            if (s.length() > (0x1D ^ 0x35)) {
                s = s.substring("".length(), 0x74 ^ 0x5C);
            }
            final Score valueFromObjective = this.theScoreboard.getValueFromObjective(s, objective);
            valueFromObjective.setScorePoints(compoundTag.getInteger(ScoreboardSaveData.I[0x5F ^ 0x41]));
            if (compoundTag.hasKey(ScoreboardSaveData.I[0x3B ^ 0x24])) {
                valueFromObjective.setLocked(compoundTag.getBoolean(ScoreboardSaveData.I[0x8 ^ 0x28]));
            }
            ++i;
        }
    }
    
    public ScoreboardSaveData(final String s) {
        super(s);
    }
    
    protected NBTTagList func_96496_a() {
        final NBTTagList list = new NBTTagList();
        final Iterator<ScorePlayerTeam> iterator = this.theScoreboard.getTeams().iterator();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ScorePlayerTeam scorePlayerTeam = iterator.next();
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setString(ScoreboardSaveData.I[0xA9 ^ 0x8C], scorePlayerTeam.getRegisteredName());
            nbtTagCompound.setString(ScoreboardSaveData.I[0x84 ^ 0xA2], scorePlayerTeam.getTeamName());
            if (scorePlayerTeam.getChatFormat().getColorIndex() >= 0) {
                nbtTagCompound.setString(ScoreboardSaveData.I[0x43 ^ 0x64], scorePlayerTeam.getChatFormat().getFriendlyName());
            }
            nbtTagCompound.setString(ScoreboardSaveData.I[0x25 ^ 0xD], scorePlayerTeam.getColorPrefix());
            nbtTagCompound.setString(ScoreboardSaveData.I[0x27 ^ 0xE], scorePlayerTeam.getColorSuffix());
            nbtTagCompound.setBoolean(ScoreboardSaveData.I[0x91 ^ 0xBB], scorePlayerTeam.getAllowFriendlyFire());
            nbtTagCompound.setBoolean(ScoreboardSaveData.I[0x1B ^ 0x30], scorePlayerTeam.getSeeFriendlyInvisiblesEnabled());
            nbtTagCompound.setString(ScoreboardSaveData.I[0x3D ^ 0x11], scorePlayerTeam.getNameTagVisibility().field_178830_e);
            nbtTagCompound.setString(ScoreboardSaveData.I[0x5D ^ 0x70], scorePlayerTeam.getDeathMessageVisibility().field_178830_e);
            final NBTTagList list2 = new NBTTagList();
            final Iterator<String> iterator2 = scorePlayerTeam.getMembershipCollection().iterator();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (iterator2.hasNext()) {
                list2.appendTag(new NBTTagString(iterator2.next()));
            }
            nbtTagCompound.setTag(ScoreboardSaveData.I[0x4B ^ 0x65], list2);
            list.appendTag(nbtTagCompound);
        }
        return list;
    }
    
    protected NBTTagList scoresToNbt() {
        final NBTTagList list = new NBTTagList();
        final Iterator<Score> iterator = this.theScoreboard.getScores().iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Score score = iterator.next();
            if (score.getObjective() != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setString(ScoreboardSaveData.I[0x55 ^ 0x60], score.getPlayerName());
                nbtTagCompound.setString(ScoreboardSaveData.I[0xF0 ^ 0xC6], score.getObjective().getName());
                nbtTagCompound.setInteger(ScoreboardSaveData.I[0x5C ^ 0x6B], score.getScorePoints());
                nbtTagCompound.setBoolean(ScoreboardSaveData.I[0x7B ^ 0x43], score.isLocked());
                list.appendTag(nbtTagCompound);
            }
        }
        return list;
    }
    
    protected void func_96497_d(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        int n = "".length();
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < (0xA7 ^ 0xB4)) {
            final ScoreObjective objectiveInDisplaySlot = this.theScoreboard.getObjectiveInDisplaySlot(i);
            if (objectiveInDisplaySlot != null) {
                nbtTagCompound2.setString(ScoreboardSaveData.I[0x27 ^ 0x8] + i, objectiveInDisplaySlot.getName());
                n = " ".length();
            }
            ++i;
        }
        if (n != 0) {
            nbtTagCompound.setTag(ScoreboardSaveData.I[0x5D ^ 0x6D], nbtTagCompound2);
        }
    }
    
    public ScoreboardSaveData() {
        this(ScoreboardSaveData.I["".length()]);
    }
}
