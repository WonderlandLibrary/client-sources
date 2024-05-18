package net.minecraft.scoreboard;

import net.minecraft.server.*;
import net.minecraft.network.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import net.minecraft.server.management.*;

public class ServerScoreboard extends Scoreboard
{
    private ScoreboardSaveData scoreboardSaveData;
    private final Set<ScoreObjective> field_96553_b;
    private final MinecraftServer scoreboardMCServer;
    
    @Override
    public void func_96533_c(final ScoreObjective scoreObjective) {
        super.func_96533_c(scoreObjective);
        if (this.field_96553_b.contains(scoreObjective)) {
            this.getPlayerIterator(scoreObjective);
        }
        this.func_96551_b();
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
            if (4 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ServerScoreboard(final MinecraftServer scoreboardMCServer) {
        this.field_96553_b = (Set<ScoreObjective>)Sets.newHashSet();
        this.scoreboardMCServer = scoreboardMCServer;
    }
    
    public List<Packet> func_96548_f(final ScoreObjective scoreObjective) {
        final ArrayList arrayList = Lists.newArrayList();
        arrayList.add(new S3BPacketScoreboardObjective(scoreObjective, " ".length()));
        int i = "".length();
        "".length();
        if (1 < -1) {
            throw null;
        }
        while (i < (0x11 ^ 0x2)) {
            if (this.getObjectiveInDisplaySlot(i) == scoreObjective) {
                arrayList.add(new S3DPacketDisplayScoreboard(i, scoreObjective));
            }
            ++i;
        }
        return (List<Packet>)arrayList;
    }
    
    @Override
    public void func_96516_a(final String s) {
        super.func_96516_a(s);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(s));
        this.func_96551_b();
    }
    
    public void func_96549_e(final ScoreObjective scoreObjective) {
        final List<Packet> func_96550_d = this.func_96550_d(scoreObjective);
        final Iterator<EntityPlayerMP> iterator = this.scoreboardMCServer.getConfigurationManager().func_181057_v().iterator();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayerMP entityPlayerMP = iterator.next();
            final Iterator<Packet> iterator2 = (Iterator<Packet>)func_96550_d.iterator();
            "".length();
            if (3 < -1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                entityPlayerMP.playerNetServerHandler.sendPacket(iterator2.next());
            }
        }
        this.field_96553_b.add(scoreObjective);
    }
    
    public int func_96552_h(final ScoreObjective scoreObjective) {
        int length = "".length();
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < (0x86 ^ 0x95)) {
            if (this.getObjectiveInDisplaySlot(i) == scoreObjective) {
                ++length;
            }
            ++i;
        }
        return length;
    }
    
    @Override
    public void broadcastTeamCreated(final ScorePlayerTeam scorePlayerTeam) {
        super.broadcastTeamCreated(scorePlayerTeam);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, "".length()));
        this.func_96551_b();
    }
    
    @Override
    public void func_96513_c(final ScorePlayerTeam scorePlayerTeam) {
        super.func_96513_c(scorePlayerTeam);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, " ".length()));
        this.func_96551_b();
    }
    
    protected void func_96551_b() {
        if (this.scoreboardSaveData != null) {
            this.scoreboardSaveData.markDirty();
        }
    }
    
    @Override
    public void onScoreObjectiveAdded(final ScoreObjective scoreObjective) {
        super.onScoreObjectiveAdded(scoreObjective);
        this.func_96551_b();
    }
    
    @Override
    public void func_96536_a(final Score score) {
        super.func_96536_a(score);
        if (this.field_96553_b.contains(score.getObjective())) {
            this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(score));
        }
        this.func_96551_b();
    }
    
    @Override
    public boolean addPlayerToTeam(final String s, final String s2) {
        if (super.addPlayerToTeam(s, s2)) {
            final ScorePlayerTeam team = this.getTeam(s2);
            final ServerConfigurationManager configurationManager = this.scoreboardMCServer.getConfigurationManager();
            final ScorePlayerTeam scorePlayerTeam = team;
            final String[] array = new String[" ".length()];
            array["".length()] = s;
            configurationManager.sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, Arrays.asList(array), "   ".length()));
            this.func_96551_b();
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void getPlayerIterator(final ScoreObjective scoreObjective) {
        final List<Packet> func_96548_f = this.func_96548_f(scoreObjective);
        final Iterator<EntityPlayerMP> iterator = this.scoreboardMCServer.getConfigurationManager().func_181057_v().iterator();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayerMP entityPlayerMP = iterator.next();
            final Iterator<Packet> iterator2 = (Iterator<Packet>)func_96548_f.iterator();
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                entityPlayerMP.playerNetServerHandler.sendPacket(iterator2.next());
            }
        }
        this.field_96553_b.remove(scoreObjective);
    }
    
    @Override
    public void removePlayerFromTeam(final String s, final ScorePlayerTeam scorePlayerTeam) {
        super.removePlayerFromTeam(s, scorePlayerTeam);
        final ServerConfigurationManager configurationManager = this.scoreboardMCServer.getConfigurationManager();
        final String[] array = new String[" ".length()];
        array["".length()] = s;
        configurationManager.sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, Arrays.asList(array), 0x74 ^ 0x70));
        this.func_96551_b();
    }
    
    @Override
    public void setObjectiveInDisplaySlot(final int n, final ScoreObjective scoreObjective) {
        final ScoreObjective objectiveInDisplaySlot = this.getObjectiveInDisplaySlot(n);
        super.setObjectiveInDisplaySlot(n, scoreObjective);
        if (objectiveInDisplaySlot != scoreObjective && objectiveInDisplaySlot != null) {
            if (this.func_96552_h(objectiveInDisplaySlot) > 0) {
                this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(n, scoreObjective));
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                this.getPlayerIterator(objectiveInDisplaySlot);
            }
        }
        if (scoreObjective != null) {
            if (this.field_96553_b.contains(scoreObjective)) {
                this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(n, scoreObjective));
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                this.func_96549_e(scoreObjective);
            }
        }
        this.func_96551_b();
    }
    
    @Override
    public void func_178820_a(final String s, final ScoreObjective scoreObjective) {
        super.func_178820_a(s, scoreObjective);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(s, scoreObjective));
        this.func_96551_b();
    }
    
    @Override
    public void func_96532_b(final ScoreObjective scoreObjective) {
        super.func_96532_b(scoreObjective);
        if (this.field_96553_b.contains(scoreObjective)) {
            this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3BPacketScoreboardObjective(scoreObjective, "  ".length()));
        }
        this.func_96551_b();
    }
    
    @Override
    public void sendTeamUpdate(final ScorePlayerTeam scorePlayerTeam) {
        super.sendTeamUpdate(scorePlayerTeam);
        this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, "  ".length()));
        this.func_96551_b();
    }
    
    public List<Packet> func_96550_d(final ScoreObjective scoreObjective) {
        final ArrayList arrayList = Lists.newArrayList();
        arrayList.add(new S3BPacketScoreboardObjective(scoreObjective, "".length()));
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < (0xBC ^ 0xAF)) {
            if (this.getObjectiveInDisplaySlot(i) == scoreObjective) {
                arrayList.add(new S3DPacketDisplayScoreboard(i, scoreObjective));
            }
            ++i;
        }
        final Iterator<Score> iterator = this.getSortedScores(scoreObjective).iterator();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            arrayList.add(new S3CPacketUpdateScore(iterator.next()));
        }
        return (List<Packet>)arrayList;
    }
    
    public void func_96547_a(final ScoreboardSaveData scoreboardSaveData) {
        this.scoreboardSaveData = scoreboardSaveData;
    }
}
