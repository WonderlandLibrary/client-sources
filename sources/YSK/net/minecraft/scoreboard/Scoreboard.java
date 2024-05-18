package net.minecraft.scoreboard;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import java.util.*;

public class Scoreboard
{
    private final Map<String, ScoreObjective> scoreObjectives;
    private final Map<String, Map<ScoreObjective, Score>> entitiesScoreObjectives;
    private static String[] field_178823_g;
    private static final String[] I;
    private final Map<String, ScorePlayerTeam> teams;
    private final ScoreObjective[] objectiveDisplaySlots;
    private final Map<String, ScorePlayerTeam> teamMemberships;
    private final Map<IScoreObjectiveCriteria, List<ScoreObjective>> scoreObjectiveCriterias;
    
    public boolean removePlayerFromTeams(final String s) {
        final ScorePlayerTeam playersTeam = this.getPlayersTeam(s);
        if (playersTeam != null) {
            this.removePlayerFromTeam(s, playersTeam);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void sendTeamUpdate(final ScorePlayerTeam scorePlayerTeam) {
    }
    
    public static int getObjectiveDisplaySlotNumber(final String s) {
        if (s.equalsIgnoreCase(Scoreboard.I[0x1 ^ 0x13])) {
            return "".length();
        }
        if (s.equalsIgnoreCase(Scoreboard.I[0xB3 ^ 0xA0])) {
            return " ".length();
        }
        if (s.equalsIgnoreCase(Scoreboard.I[0x85 ^ 0x91])) {
            return "  ".length();
        }
        if (s.startsWith(Scoreboard.I[0x64 ^ 0x71])) {
            final EnumChatFormatting valueByName = EnumChatFormatting.getValueByName(s.substring(Scoreboard.I[0x50 ^ 0x46].length()));
            if (valueByName != null && valueByName.getColorIndex() >= 0) {
                return valueByName.getColorIndex() + "   ".length();
            }
        }
        return -" ".length();
    }
    
    public Collection<ScoreObjective> getScoreObjectives() {
        return this.scoreObjectives.values();
    }
    
    public Collection<ScoreObjective> getObjectivesFromCriteria(final IScoreObjectiveCriteria scoreObjectiveCriteria) {
        final List<ScoreObjective> list = this.scoreObjectiveCriterias.get(scoreObjectiveCriteria);
        ArrayList list2;
        if (list == null) {
            list2 = Lists.newArrayList();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            list2 = Lists.newArrayList((Iterable)list);
        }
        return (Collection<ScoreObjective>)list2;
    }
    
    public void removeObjective(final ScoreObjective scoreObjective) {
        this.scoreObjectives.remove(scoreObjective.getName());
        int i = "".length();
        "".length();
        if (0 < -1) {
            throw null;
        }
        while (i < (0x82 ^ 0x91)) {
            if (this.getObjectiveInDisplaySlot(i) == scoreObjective) {
                this.setObjectiveInDisplaySlot(i, null);
            }
            ++i;
        }
        final List<ScoreObjective> list = this.scoreObjectiveCriterias.get(scoreObjective.getCriteria());
        if (list != null) {
            list.remove(scoreObjective);
        }
        final Iterator<Map<ScoreObjective, Score>> iterator = this.entitiesScoreObjectives.values().iterator();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().remove(scoreObjective);
        }
        this.func_96533_c(scoreObjective);
    }
    
    public ScorePlayerTeam createTeam(final String s) {
        if (s.length() > (0x2 ^ 0x12)) {
            throw new IllegalArgumentException(Scoreboard.I[0x17 ^ 0x11] + s + Scoreboard.I[0x1B ^ 0x1C]);
        }
        if (this.getTeam(s) != null) {
            throw new IllegalArgumentException(Scoreboard.I[0x31 ^ 0x39] + s + Scoreboard.I[0x7 ^ 0xE]);
        }
        final ScorePlayerTeam scorePlayerTeam = new ScorePlayerTeam(this, s);
        this.teams.put(s, scorePlayerTeam);
        this.broadcastTeamCreated(scorePlayerTeam);
        return scorePlayerTeam;
    }
    
    public Collection<ScorePlayerTeam> getTeams() {
        return this.teams.values();
    }
    
    public void removeTeam(final ScorePlayerTeam scorePlayerTeam) {
        this.teams.remove(scorePlayerTeam.getRegisteredName());
        final Iterator<String> iterator = scorePlayerTeam.getMembershipCollection().iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.teamMemberships.remove(iterator.next());
        }
        this.func_96513_c(scorePlayerTeam);
    }
    
    private static void I() {
        (I = new String[0x53 ^ 0x44])["".length()] = I("&\u0010\u0004s(\u0010\u0012\u000403\u001b\u000e\u0004s)\u0013\u0015\u0004s`", "rxaSG");
        Scoreboard.I[" ".length()] = I("hW%%p;\u0018#v< \u0019+w", "OwLVP");
        Scoreboard.I["  ".length()] = I("\u0003\nw8\n(\u00014#\u00014\u0001w \u00016\fw#\u0000'D96\u0005'Dp", "BdWWh");
        Scoreboard.I["   ".length()] = I("eU3\u001d\u0015'\u00146\bG'\r;\u0002\u00131T", "BuRqg");
        Scoreboard.I[0x5B ^ 0x5F] = I("\u0011./E\u0002)'3\u0000\u0000e(+\b\u0017ea", "EFJer");
        Scoreboard.I[0x41 ^ 0x44] = I("vs\u001b7x%<\u001dd4>=\u0015e", "QSrDX");
        Scoreboard.I[0x60 ^ 0x66] = I("0\u0005\tX0\u0001\f\u0001X*\u0005\u0000\tXc", "dmlxD");
        Scoreboard.I[0x7B ^ 0x7C] = I("Wb,\u0003J\u0004-*P\u0006\u001f,\"Q", "pBEpj");
        Scoreboard.I[0xB9 ^ 0xB1] = I("\u0002k\u0012\u001d\u0018.k\u0011\u0011\r+k\u0012\u0010\u001cc%\u0007\u0015\u001ccl", "CKfxy");
        Scoreboard.I[0xB3 ^ 0xBA] = I("Kc/\u0006&\t\"*\u0013t\t;'\u0019 \u001fb", "lCNjT");
        Scoreboard.I[0x31 ^ 0x3B] = I(";8\bq\u0004\u00031\u00144\u0006O>\f<\u0011Ow", "oPmQt");
        Scoreboard.I[0x6F ^ 0x64] = I("Iw\u0000\u0004d\u001a8\u0006W(\u00019\u000eV", "nWiwD");
        Scoreboard.I[0x12 ^ 0x1E] = I("\n\u0006\u000b<\u0016(J\u00036S?\u0003\u001e-\u0016(J\u0005+S;\u0004\u00051\u001b?\u0018J1\u0016;\u0007J*\u0001z\u0004\u00051S5\u0004J$\u001d#J\u001e \u00127DJ\u0006\u00124\u0004\u00051S(\u000f\u0007*\u0005?J\f7\u001c7J\u001e \u00127JM", "ZjjEs");
        Scoreboard.I[0x2A ^ 0x27] = I("I]", "nstVp");
        Scoreboard.I[0x64 ^ 0x6A] = I("\u0003\u0003 <", "ojSHC");
        Scoreboard.I[0x5E ^ 0x51] = I("\u0010\u000b \u001c/\u0002\u0010", "cbDyM");
        Scoreboard.I[0x24 ^ 0x34] = I("$#9\u0015?\b'8\u001f", "FFUzH");
        Scoreboard.I[0x6E ^ 0x7F] = I(";\u0019+\u0007\t)\u0002a\u0016\u000e)\u001da", "HpObk");
        Scoreboard.I[0x35 ^ 0x27] = I("\u000f>8\u001f", "cWKkS");
        Scoreboard.I[0x29 ^ 0x3A] = I("7\u0002\r\u0016\u000e%\u0019", "Dkisl");
        Scoreboard.I[0x1A ^ 0xE] = I("\u0005\u001f#\u0017.)\u001b\"\u001d", "gzOxY");
        Scoreboard.I[0x31 ^ 0x24] = I("##7'.18}6)1'}", "PJSBL");
        Scoreboard.I[0x9C ^ 0x8A] = I("\u001b$>&\u0007\t?t7\u0000\t t", "hMZCe");
    }
    
    public void func_181140_a(final Entity entity) {
        if (entity != null && !(entity instanceof EntityPlayer) && !entity.isEntityAlive()) {
            final String string = entity.getUniqueID().toString();
            this.removeObjectiveFromEntity(string, null);
            this.removePlayerFromTeams(string);
        }
    }
    
    public void setObjectiveInDisplaySlot(final int n, final ScoreObjective scoreObjective) {
        this.objectiveDisplaySlots[n] = scoreObjective;
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void func_96513_c(final ScorePlayerTeam scorePlayerTeam) {
    }
    
    public ScoreObjective addScoreObjective(final String s, final IScoreObjectiveCriteria scoreObjectiveCriteria) {
        if (s.length() > (0xA ^ 0x1A)) {
            throw new IllegalArgumentException(Scoreboard.I["".length()] + s + Scoreboard.I[" ".length()]);
        }
        if (this.getObjective(s) != null) {
            throw new IllegalArgumentException(Scoreboard.I["  ".length()] + s + Scoreboard.I["   ".length()]);
        }
        final ScoreObjective scoreObjective = new ScoreObjective(this, s, scoreObjectiveCriteria);
        List<ScoreObjective> arrayList = this.scoreObjectiveCriterias.get(scoreObjectiveCriteria);
        if (arrayList == null) {
            arrayList = (List<ScoreObjective>)Lists.newArrayList();
            this.scoreObjectiveCriterias.put(scoreObjectiveCriteria, arrayList);
        }
        arrayList.add(scoreObjective);
        this.scoreObjectives.put(s, scoreObjective);
        this.onScoreObjectiveAdded(scoreObjective);
        return scoreObjective;
    }
    
    public ScorePlayerTeam getPlayersTeam(final String s) {
        return this.teamMemberships.get(s);
    }
    
    public ScorePlayerTeam getTeam(final String s) {
        return this.teams.get(s);
    }
    
    public ScoreObjective getObjectiveInDisplaySlot(final int n) {
        return this.objectiveDisplaySlots[n];
    }
    
    public void func_178820_a(final String s, final ScoreObjective scoreObjective) {
    }
    
    public Score getValueFromObjective(final String s, final ScoreObjective scoreObjective) {
        if (s.length() > (0x32 ^ 0x1A)) {
            throw new IllegalArgumentException(Scoreboard.I[0x40 ^ 0x44] + s + Scoreboard.I[0x87 ^ 0x82]);
        }
        Map<ScoreObjective, Score> hashMap = this.entitiesScoreObjectives.get(s);
        if (hashMap == null) {
            hashMap = (Map<ScoreObjective, Score>)Maps.newHashMap();
            this.entitiesScoreObjectives.put(s, hashMap);
        }
        Score score = hashMap.get(scoreObjective);
        if (score == null) {
            score = new Score(this, scoreObjective, s);
            hashMap.put(scoreObjective, score);
        }
        return score;
    }
    
    public void func_96532_b(final ScoreObjective scoreObjective) {
    }
    
    public ScoreObjective getObjective(final String s) {
        return this.scoreObjectives.get(s);
    }
    
    public boolean entityHasObjective(final String s, final ScoreObjective scoreObjective) {
        final Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(s);
        if (map == null) {
            return "".length() != 0;
        }
        if (map.get(scoreObjective) != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public Collection<Score> getScores() {
        final Collection<Map<ScoreObjective, Score>> values = this.entitiesScoreObjectives.values();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Map<ScoreObjective, Score>> iterator = values.iterator();
        "".length();
        if (1 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            arrayList.addAll(iterator.next().values());
        }
        return (Collection<Score>)arrayList;
    }
    
    public void broadcastTeamCreated(final ScorePlayerTeam scorePlayerTeam) {
    }
    
    public Collection<String> getTeamNames() {
        return this.teams.keySet();
    }
    
    public void func_96533_c(final ScoreObjective scoreObjective) {
    }
    
    static {
        I();
        Scoreboard.field_178823_g = null;
    }
    
    public Map<ScoreObjective, Score> getObjectivesForEntity(final String s) {
        Map<ScoreObjective, Score> hashMap = this.entitiesScoreObjectives.get(s);
        if (hashMap == null) {
            hashMap = (Map<ScoreObjective, Score>)Maps.newHashMap();
        }
        return hashMap;
    }
    
    public static String[] getDisplaySlotStrings() {
        if (Scoreboard.field_178823_g == null) {
            Scoreboard.field_178823_g = new String[0x5E ^ 0x4D];
            int i = "".length();
            "".length();
            if (-1 == 2) {
                throw null;
            }
            while (i < (0x8C ^ 0x9F)) {
                Scoreboard.field_178823_g[i] = getObjectiveDisplaySlot(i);
                ++i;
            }
        }
        return Scoreboard.field_178823_g;
    }
    
    public Collection<Score> getSortedScores(final ScoreObjective scoreObjective) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Map<ScoreObjective, Score>> iterator = this.entitiesScoreObjectives.values().iterator();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Score score = iterator.next().get(scoreObjective);
            if (score != null) {
                arrayList.add(score);
            }
        }
        Collections.sort((List<Object>)arrayList, (Comparator<? super Object>)Score.scoreComparator);
        return (Collection<Score>)arrayList;
    }
    
    public void func_96516_a(final String s) {
    }
    
    public void func_96536_a(final Score score) {
    }
    
    public void onScoreObjectiveAdded(final ScoreObjective scoreObjective) {
    }
    
    public Scoreboard() {
        this.scoreObjectives = (Map<String, ScoreObjective>)Maps.newHashMap();
        this.scoreObjectiveCriterias = (Map<IScoreObjectiveCriteria, List<ScoreObjective>>)Maps.newHashMap();
        this.entitiesScoreObjectives = (Map<String, Map<ScoreObjective, Score>>)Maps.newHashMap();
        this.objectiveDisplaySlots = new ScoreObjective[0x3 ^ 0x10];
        this.teams = (Map<String, ScorePlayerTeam>)Maps.newHashMap();
        this.teamMemberships = (Map<String, ScorePlayerTeam>)Maps.newHashMap();
    }
    
    public void removeObjectiveFromEntity(final String s, final ScoreObjective scoreObjective) {
        if (scoreObjective == null) {
            if (this.entitiesScoreObjectives.remove(s) != null) {
                this.func_96516_a(s);
                "".length();
                if (-1 == 3) {
                    throw null;
                }
            }
        }
        else {
            final Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(s);
            if (map != null) {
                final Score score = map.remove(scoreObjective);
                if (map.size() < " ".length()) {
                    if (this.entitiesScoreObjectives.remove(s) != null) {
                        this.func_96516_a(s);
                        "".length();
                        if (3 == 4) {
                            throw null;
                        }
                    }
                }
                else if (score != null) {
                    this.func_178820_a(s, scoreObjective);
                }
            }
        }
    }
    
    public static String getObjectiveDisplaySlot(final int n) {
        switch (n) {
            case 0: {
                return Scoreboard.I[0xCB ^ 0xC5];
            }
            case 1: {
                return Scoreboard.I[0xBA ^ 0xB5];
            }
            case 2: {
                return Scoreboard.I[0xAA ^ 0xBA];
            }
            default: {
                if (n >= "   ".length() && n <= (0x93 ^ 0x81)) {
                    final EnumChatFormatting func_175744_a = EnumChatFormatting.func_175744_a(n - "   ".length());
                    if (func_175744_a != null && func_175744_a != EnumChatFormatting.RESET) {
                        return Scoreboard.I[0x7B ^ 0x6A] + func_175744_a.getFriendlyName();
                    }
                }
                return null;
            }
        }
    }
    
    public Collection<String> getObjectiveNames() {
        return this.entitiesScoreObjectives.keySet();
    }
    
    public boolean addPlayerToTeam(final String s, final String s2) {
        if (s.length() > (0x9E ^ 0xB6)) {
            throw new IllegalArgumentException(Scoreboard.I[0xB4 ^ 0xBE] + s + Scoreboard.I[0x76 ^ 0x7D]);
        }
        if (!this.teams.containsKey(s2)) {
            return "".length() != 0;
        }
        final ScorePlayerTeam team = this.getTeam(s2);
        if (this.getPlayersTeam(s) != null) {
            this.removePlayerFromTeams(s);
        }
        this.teamMemberships.put(s, team);
        team.getMembershipCollection().add(s);
        return " ".length() != 0;
    }
    
    public void removePlayerFromTeam(final String s, final ScorePlayerTeam scorePlayerTeam) {
        if (this.getPlayersTeam(s) != scorePlayerTeam) {
            throw new IllegalStateException(Scoreboard.I[0x70 ^ 0x7C] + scorePlayerTeam.getRegisteredName() + Scoreboard.I[0xD ^ 0x0]);
        }
        this.teamMemberships.remove(s);
        scorePlayerTeam.getMembershipCollection().remove(s);
    }
}
