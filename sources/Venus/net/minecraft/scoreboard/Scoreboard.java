/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class Scoreboard {
    private final Map<String, ScoreObjective> scoreObjectives = Maps.newHashMap();
    private final Map<ScoreCriteria, List<ScoreObjective>> scoreObjectiveCriterias = Maps.newHashMap();
    private final Map<String, Map<ScoreObjective, Score>> entitiesScoreObjectives = Maps.newHashMap();
    private final ScoreObjective[] objectiveDisplaySlots = new ScoreObjective[19];
    private final Map<String, ScorePlayerTeam> teams = Maps.newHashMap();
    private final Map<String, ScorePlayerTeam> teamMemberships = Maps.newHashMap();
    private static String[] displaySlots;

    public boolean hasObjective(String string) {
        return this.scoreObjectives.containsKey(string);
    }

    public ScoreObjective getOrCreateObjective(String string) {
        return this.scoreObjectives.get(string);
    }

    @Nullable
    public ScoreObjective getObjective(@Nullable String string) {
        return this.scoreObjectives.get(string);
    }

    public ScoreObjective addObjective(String string, ScoreCriteria scoreCriteria, ITextComponent iTextComponent, ScoreCriteria.RenderType renderType) {
        if (string.length() > 16) {
            throw new IllegalArgumentException("The objective name '" + string + "' is too long!");
        }
        if (this.scoreObjectives.containsKey(string)) {
            throw new IllegalArgumentException("An objective with the name '" + string + "' already exists!");
        }
        ScoreObjective scoreObjective = new ScoreObjective(this, string, scoreCriteria, iTextComponent, renderType);
        this.scoreObjectiveCriterias.computeIfAbsent(scoreCriteria, Scoreboard::lambda$addObjective$0).add(scoreObjective);
        this.scoreObjectives.put(string, scoreObjective);
        this.onObjectiveAdded(scoreObjective);
        return scoreObjective;
    }

    public Collection<Score> getScores() {
        Collection<Map<ScoreObjective, Score>> collection = this.entitiesScoreObjectives.values();
        ArrayList<Score> arrayList = Lists.newArrayList();
        for (Map<ScoreObjective, Score> map : collection) {
            arrayList.addAll(map.values());
        }
        return arrayList;
    }

    public final void forAllObjectives(ScoreCriteria scoreCriteria, String string, Consumer<Score> consumer) {
        this.scoreObjectiveCriterias.getOrDefault(scoreCriteria, Collections.emptyList()).forEach(arg_0 -> this.lambda$forAllObjectives$1(consumer, string, arg_0));
    }

    public boolean entityHasObjective(String string, ScoreObjective scoreObjective) {
        Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(string);
        if (map == null) {
            return true;
        }
        Score score = map.get(scoreObjective);
        return score != null;
    }

    public Score getOrCreateScore(String string, ScoreObjective scoreObjective) {
        if (string.length() > 40) {
            throw new IllegalArgumentException("The player name '" + string + "' is too long!");
        }
        Map map = this.entitiesScoreObjectives.computeIfAbsent(string, Scoreboard::lambda$getOrCreateScore$2);
        return map.computeIfAbsent(scoreObjective, arg_0 -> this.lambda$getOrCreateScore$3(string, arg_0));
    }

    public Collection<Score> getSortedScores(ScoreObjective scoreObjective) {
        ArrayList<Score> arrayList = Lists.newArrayList();
        for (Map<ScoreObjective, Score> map : this.entitiesScoreObjectives.values()) {
            Score score = map.get(scoreObjective);
            if (score == null) continue;
            arrayList.add(score);
        }
        arrayList.sort(Score.SCORE_COMPARATOR);
        return arrayList;
    }

    public Collection<ScoreObjective> getScoreObjectives() {
        return this.scoreObjectives.values();
    }

    public Collection<String> func_197897_d() {
        return this.scoreObjectives.keySet();
    }

    public Collection<String> getObjectiveNames() {
        return Lists.newArrayList(this.entitiesScoreObjectives.keySet());
    }

    public void removeObjectiveFromEntity(String string, @Nullable ScoreObjective scoreObjective) {
        if (scoreObjective == null) {
            Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.remove(string);
            if (map != null) {
                this.onPlayerRemoved(string);
            }
        } else {
            Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(string);
            if (map != null) {
                Score score = map.remove(scoreObjective);
                if (map.size() < 1) {
                    Map<ScoreObjective, Score> map2 = this.entitiesScoreObjectives.remove(string);
                    if (map2 != null) {
                        this.onPlayerRemoved(string);
                    }
                } else if (score != null) {
                    this.onPlayerScoreRemoved(string, scoreObjective);
                }
            }
        }
    }

    public Map<ScoreObjective, Score> getObjectivesForEntity(String string) {
        Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(string);
        if (map == null) {
            map = Maps.newHashMap();
        }
        return map;
    }

    public void removeObjective(ScoreObjective scoreObjective) {
        this.scoreObjectives.remove(scoreObjective.getName());
        for (int i = 0; i < 19; ++i) {
            if (this.getObjectiveInDisplaySlot(i) != scoreObjective) continue;
            this.setObjectiveInDisplaySlot(i, null);
        }
        List<ScoreObjective> list = this.scoreObjectiveCriterias.get(scoreObjective.getCriteria());
        if (list != null) {
            list.remove(scoreObjective);
        }
        for (Map<ScoreObjective, Score> map : this.entitiesScoreObjectives.values()) {
            map.remove(scoreObjective);
        }
        this.onObjectiveRemoved(scoreObjective);
    }

    public void setObjectiveInDisplaySlot(int n, @Nullable ScoreObjective scoreObjective) {
        this.objectiveDisplaySlots[n] = scoreObjective;
    }

    @Nullable
    public ScoreObjective getObjectiveInDisplaySlot(int n) {
        return this.objectiveDisplaySlots[n];
    }

    public ScorePlayerTeam getTeam(String string) {
        return this.teams.get(string);
    }

    public ScorePlayerTeam createTeam(String string) {
        if (string.length() > 16) {
            throw new IllegalArgumentException("The team name '" + string + "' is too long!");
        }
        ScorePlayerTeam scorePlayerTeam = this.getTeam(string);
        if (scorePlayerTeam != null) {
            return null;
        }
        scorePlayerTeam = new ScorePlayerTeam(this, string);
        this.teams.put(string, scorePlayerTeam);
        this.onTeamAdded(scorePlayerTeam);
        return scorePlayerTeam;
    }

    public void removeTeam(ScorePlayerTeam scorePlayerTeam) {
        if (scorePlayerTeam == null) {
            return;
        }
        this.teams.remove(scorePlayerTeam.getName());
        for (String string : scorePlayerTeam.getMembershipCollection()) {
            this.teamMemberships.remove(string);
        }
        this.onTeamRemoved(scorePlayerTeam);
    }

    public boolean addPlayerToTeam(String string, ScorePlayerTeam scorePlayerTeam) {
        if (string.length() > 40) {
            throw new IllegalArgumentException("The player name '" + string + "' is too long!");
        }
        if (this.getPlayersTeam(string) != null) {
            this.removePlayerFromTeams(string);
        }
        this.teamMemberships.put(string, scorePlayerTeam);
        return scorePlayerTeam.getMembershipCollection().add(string);
    }

    public boolean removePlayerFromTeams(String string) {
        ScorePlayerTeam scorePlayerTeam = this.getPlayersTeam(string);
        if (scorePlayerTeam != null) {
            this.removePlayerFromTeam(string, scorePlayerTeam);
            return false;
        }
        return true;
    }

    public void removePlayerFromTeam(String string, ScorePlayerTeam scorePlayerTeam) {
        if (this.getPlayersTeam(string) == scorePlayerTeam) {
            this.teamMemberships.remove(string);
            scorePlayerTeam.getMembershipCollection().remove(string);
        }
    }

    public Collection<String> getTeamNames() {
        return this.teams.keySet();
    }

    public Collection<ScorePlayerTeam> getTeams() {
        return this.teams.values();
    }

    @Nullable
    public ScorePlayerTeam getPlayersTeam(String string) {
        return this.teamMemberships.get(string);
    }

    public void onObjectiveAdded(ScoreObjective scoreObjective) {
    }

    public void onObjectiveChanged(ScoreObjective scoreObjective) {
    }

    public void onObjectiveRemoved(ScoreObjective scoreObjective) {
    }

    public void onScoreChanged(Score score) {
    }

    public void onPlayerRemoved(String string) {
    }

    public void onPlayerScoreRemoved(String string, ScoreObjective scoreObjective) {
    }

    public void onTeamAdded(ScorePlayerTeam scorePlayerTeam) {
    }

    public void onTeamChanged(ScorePlayerTeam scorePlayerTeam) {
    }

    public void onTeamRemoved(ScorePlayerTeam scorePlayerTeam) {
    }

    public static String getObjectiveDisplaySlot(int n) {
        TextFormatting textFormatting;
        switch (n) {
            case 0: {
                return "list";
            }
            case 1: {
                return "sidebar";
            }
            case 2: {
                return "belowName";
            }
        }
        if (n >= 3 && n <= 18 && (textFormatting = TextFormatting.fromColorIndex(n - 3)) != null && textFormatting != TextFormatting.RESET) {
            return "sidebar.team." + textFormatting.getFriendlyName();
        }
        return null;
    }

    public static int getObjectiveDisplaySlotNumber(String string) {
        String string2;
        TextFormatting textFormatting;
        if ("list".equalsIgnoreCase(string)) {
            return 1;
        }
        if ("sidebar".equalsIgnoreCase(string)) {
            return 0;
        }
        if ("belowName".equalsIgnoreCase(string)) {
            return 1;
        }
        if (string.startsWith("sidebar.team.") && (textFormatting = TextFormatting.getValueByName(string2 = string.substring(13))) != null && textFormatting.getColorIndex() >= 0) {
            return textFormatting.getColorIndex() + 3;
        }
        return 1;
    }

    public static String[] getDisplaySlotStrings() {
        if (displaySlots == null) {
            displaySlots = new String[19];
            for (int i = 0; i < 19; ++i) {
                Scoreboard.displaySlots[i] = Scoreboard.getObjectiveDisplaySlot(i);
            }
        }
        return displaySlots;
    }

    public void removeEntity(Entity entity2) {
        if (entity2 != null && !(entity2 instanceof PlayerEntity) && !entity2.isAlive()) {
            String string = entity2.getCachedUniqueIdString();
            this.removeObjectiveFromEntity(string, null);
            this.removePlayerFromTeams(string);
        }
    }

    protected ListNBT func_197902_i() {
        ListNBT listNBT = new ListNBT();
        this.entitiesScoreObjectives.values().stream().map(Map::values).forEach(arg_0 -> Scoreboard.lambda$func_197902_i$6(listNBT, arg_0));
        return listNBT;
    }

    protected void func_197905_a(ListNBT listNBT) {
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT = listNBT.getCompound(i);
            ScoreObjective scoreObjective = this.getOrCreateObjective(compoundNBT.getString("Objective"));
            String string = compoundNBT.getString("Name");
            if (string.length() > 40) {
                string = string.substring(0, 40);
            }
            Score score = this.getOrCreateScore(string, scoreObjective);
            score.setScorePoints(compoundNBT.getInt("Score"));
            if (!compoundNBT.contains("Locked")) continue;
            score.setLocked(compoundNBT.getBoolean("Locked"));
        }
    }

    private static void lambda$func_197902_i$6(ListNBT listNBT, Collection collection) {
        collection.stream().filter(Scoreboard::lambda$func_197902_i$4).forEach(arg_0 -> Scoreboard.lambda$func_197902_i$5(listNBT, arg_0));
    }

    private static void lambda$func_197902_i$5(ListNBT listNBT, Score score) {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("Name", score.getPlayerName());
        compoundNBT.putString("Objective", score.getObjective().getName());
        compoundNBT.putInt("Score", score.getScorePoints());
        compoundNBT.putBoolean("Locked", score.isLocked());
        listNBT.add(compoundNBT);
    }

    private static boolean lambda$func_197902_i$4(Score score) {
        return score.getObjective() != null;
    }

    private Score lambda$getOrCreateScore$3(String string, ScoreObjective scoreObjective) {
        Score score = new Score(this, scoreObjective, string);
        score.setScorePoints(0);
        return score;
    }

    private static Map lambda$getOrCreateScore$2(String string) {
        return Maps.newHashMap();
    }

    private void lambda$forAllObjectives$1(Consumer consumer, String string, ScoreObjective scoreObjective) {
        consumer.accept(this.getOrCreateScore(string, scoreObjective));
    }

    private static List lambda$addObjective$0(ScoreCriteria scoreCriteria) {
        return Lists.newArrayList();
    }
}

