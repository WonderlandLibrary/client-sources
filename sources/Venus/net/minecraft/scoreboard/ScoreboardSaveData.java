/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.scoreboard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardSaveData
extends WorldSavedData {
    private static final Logger LOGGER = LogManager.getLogger();
    private Scoreboard scoreboard;
    private CompoundNBT delayedInitNbt;

    public ScoreboardSaveData() {
        super("scoreboard");
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        if (this.delayedInitNbt != null) {
            this.read(this.delayedInitNbt);
        }
    }

    @Override
    public void read(CompoundNBT compoundNBT) {
        if (this.scoreboard == null) {
            this.delayedInitNbt = compoundNBT;
        } else {
            this.readObjectives(compoundNBT.getList("Objectives", 10));
            this.scoreboard.func_197905_a(compoundNBT.getList("PlayerScores", 10));
            if (compoundNBT.contains("DisplaySlots", 1)) {
                this.readDisplayConfig(compoundNBT.getCompound("DisplaySlots"));
            }
            if (compoundNBT.contains("Teams", 0)) {
                this.readTeams(compoundNBT.getList("Teams", 10));
            }
        }
    }

    protected void readTeams(ListNBT listNBT) {
        for (int i = 0; i < listNBT.size(); ++i) {
            Object object;
            CompoundNBT compoundNBT = listNBT.getCompound(i);
            String string = compoundNBT.getString("Name");
            if (string.length() > 16) {
                string = string.substring(0, 16);
            }
            ScorePlayerTeam scorePlayerTeam = this.scoreboard.createTeam(string);
            IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("DisplayName"));
            if (iFormattableTextComponent != null) {
                scorePlayerTeam.setDisplayName(iFormattableTextComponent);
            }
            if (compoundNBT.contains("TeamColor", 1)) {
                scorePlayerTeam.setColor(TextFormatting.getValueByName(compoundNBT.getString("TeamColor")));
            }
            if (compoundNBT.contains("AllowFriendlyFire", 0)) {
                scorePlayerTeam.setAllowFriendlyFire(compoundNBT.getBoolean("AllowFriendlyFire"));
            }
            if (compoundNBT.contains("SeeFriendlyInvisibles", 0)) {
                scorePlayerTeam.setSeeFriendlyInvisiblesEnabled(compoundNBT.getBoolean("SeeFriendlyInvisibles"));
            }
            if (compoundNBT.contains("MemberNamePrefix", 1) && (object = ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("MemberNamePrefix"))) != null) {
                scorePlayerTeam.setPrefix((ITextComponent)object);
            }
            if (compoundNBT.contains("MemberNameSuffix", 1) && (object = ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("MemberNameSuffix"))) != null) {
                scorePlayerTeam.setSuffix((ITextComponent)object);
            }
            if (compoundNBT.contains("NameTagVisibility", 1) && (object = Team.Visible.getByName(compoundNBT.getString("NameTagVisibility"))) != null) {
                scorePlayerTeam.setNameTagVisibility((Team.Visible)((Object)object));
            }
            if (compoundNBT.contains("DeathMessageVisibility", 1) && (object = Team.Visible.getByName(compoundNBT.getString("DeathMessageVisibility"))) != null) {
                scorePlayerTeam.setDeathMessageVisibility((Team.Visible)((Object)object));
            }
            if (compoundNBT.contains("CollisionRule", 1) && (object = Team.CollisionRule.getByName(compoundNBT.getString("CollisionRule"))) != null) {
                scorePlayerTeam.setCollisionRule((Team.CollisionRule)((Object)object));
            }
            this.loadTeamPlayers(scorePlayerTeam, compoundNBT.getList("Players", 8));
        }
    }

    protected void loadTeamPlayers(ScorePlayerTeam scorePlayerTeam, ListNBT listNBT) {
        for (int i = 0; i < listNBT.size(); ++i) {
            this.scoreboard.addPlayerToTeam(listNBT.getString(i), scorePlayerTeam);
        }
    }

    protected void readDisplayConfig(CompoundNBT compoundNBT) {
        for (int i = 0; i < 19; ++i) {
            if (!compoundNBT.contains("slot_" + i, 1)) continue;
            String string = compoundNBT.getString("slot_" + i);
            ScoreObjective scoreObjective = this.scoreboard.getObjective(string);
            this.scoreboard.setObjectiveInDisplaySlot(i, scoreObjective);
        }
    }

    protected void readObjectives(ListNBT listNBT) {
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT = listNBT.getCompound(i);
            ScoreCriteria.func_216390_a(compoundNBT.getString("CriteriaName")).ifPresent(arg_0 -> this.lambda$readObjectives$0(compoundNBT, arg_0));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        if (this.scoreboard == null) {
            LOGGER.warn("Tried to save scoreboard without having a scoreboard...");
            return compoundNBT;
        }
        compoundNBT.put("Objectives", this.objectivesToNbt());
        compoundNBT.put("PlayerScores", this.scoreboard.func_197902_i());
        compoundNBT.put("Teams", this.teamsToNbt());
        this.fillInDisplaySlots(compoundNBT);
        return compoundNBT;
    }

    protected ListNBT teamsToNbt() {
        ListNBT listNBT = new ListNBT();
        for (ScorePlayerTeam scorePlayerTeam : this.scoreboard.getTeams()) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("Name", scorePlayerTeam.getName());
            compoundNBT.putString("DisplayName", ITextComponent.Serializer.toJson(scorePlayerTeam.getDisplayName()));
            if (scorePlayerTeam.getColor().getColorIndex() >= 0) {
                compoundNBT.putString("TeamColor", scorePlayerTeam.getColor().getFriendlyName());
            }
            compoundNBT.putBoolean("AllowFriendlyFire", scorePlayerTeam.getAllowFriendlyFire());
            compoundNBT.putBoolean("SeeFriendlyInvisibles", scorePlayerTeam.getSeeFriendlyInvisiblesEnabled());
            compoundNBT.putString("MemberNamePrefix", ITextComponent.Serializer.toJson(scorePlayerTeam.getPrefix()));
            compoundNBT.putString("MemberNameSuffix", ITextComponent.Serializer.toJson(scorePlayerTeam.getSuffix()));
            compoundNBT.putString("NameTagVisibility", scorePlayerTeam.getNameTagVisibility().internalName);
            compoundNBT.putString("DeathMessageVisibility", scorePlayerTeam.getDeathMessageVisibility().internalName);
            compoundNBT.putString("CollisionRule", scorePlayerTeam.getCollisionRule().name);
            ListNBT listNBT2 = new ListNBT();
            for (String string : scorePlayerTeam.getMembershipCollection()) {
                listNBT2.add(StringNBT.valueOf(string));
            }
            compoundNBT.put("Players", listNBT2);
            listNBT.add(compoundNBT);
        }
        return listNBT;
    }

    protected void fillInDisplaySlots(CompoundNBT compoundNBT) {
        CompoundNBT compoundNBT2 = new CompoundNBT();
        boolean bl = false;
        for (int i = 0; i < 19; ++i) {
            ScoreObjective scoreObjective = this.scoreboard.getObjectiveInDisplaySlot(i);
            if (scoreObjective == null) continue;
            compoundNBT2.putString("slot_" + i, scoreObjective.getName());
            bl = true;
        }
        if (bl) {
            compoundNBT.put("DisplaySlots", compoundNBT2);
        }
    }

    protected ListNBT objectivesToNbt() {
        ListNBT listNBT = new ListNBT();
        for (ScoreObjective scoreObjective : this.scoreboard.getScoreObjectives()) {
            if (scoreObjective.getCriteria() == null) continue;
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("Name", scoreObjective.getName());
            compoundNBT.putString("CriteriaName", scoreObjective.getCriteria().getName());
            compoundNBT.putString("DisplayName", ITextComponent.Serializer.toJson(scoreObjective.getDisplayName()));
            compoundNBT.putString("RenderType", scoreObjective.getRenderType().getId());
            listNBT.add(compoundNBT);
        }
        return listNBT;
    }

    private void lambda$readObjectives$0(CompoundNBT compoundNBT, ScoreCriteria scoreCriteria) {
        String string = compoundNBT.getString("Name");
        if (string.length() > 16) {
            string = string.substring(0, 16);
        }
        IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("DisplayName"));
        ScoreCriteria.RenderType renderType = ScoreCriteria.RenderType.byId(compoundNBT.getString("RenderType"));
        this.scoreboard.addObjective(string, scoreCriteria, iFormattableTextComponent, renderType);
    }
}

