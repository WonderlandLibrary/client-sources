package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class ServerScoreboard extends Scoreboard
{
    private final MinecraftServer field_96555_a;
    private final Set field_96553_b;
    private ScoreboardSaveData field_96554_c;
    
    public ServerScoreboard(final MinecraftServer par1MinecraftServer) {
        this.field_96553_b = new HashSet();
        this.field_96555_a = par1MinecraftServer;
    }
    
    @Override
    public void func_96536_a(final Score par1Score) {
        super.func_96536_a(par1Score);
        if (this.field_96553_b.contains(par1Score.func_96645_d())) {
            this.field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet207SetScore(par1Score, 0));
        }
        this.func_96551_b();
    }
    
    @Override
    public void func_96516_a(final String par1Str) {
        super.func_96516_a(par1Str);
        this.field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet207SetScore(par1Str));
        this.func_96551_b();
    }
    
    @Override
    public void func_96530_a(final int par1, final ScoreObjective par2ScoreObjective) {
        final ScoreObjective var3 = this.func_96539_a(par1);
        super.func_96530_a(par1, par2ScoreObjective);
        if (var3 != par2ScoreObjective && var3 != null) {
            if (this.func_96552_h(var3) > 0) {
                this.field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet208SetDisplayObjective(par1, par2ScoreObjective));
            }
            else {
                this.func_96546_g(var3);
            }
        }
        if (par2ScoreObjective != null) {
            if (this.field_96553_b.contains(par2ScoreObjective)) {
                this.field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet208SetDisplayObjective(par1, par2ScoreObjective));
            }
            else {
                this.func_96549_e(par2ScoreObjective);
            }
        }
        this.func_96551_b();
    }
    
    @Override
    public void func_96521_a(final String par1Str, final ScorePlayerTeam par2ScorePlayerTeam) {
        super.func_96521_a(par1Str, par2ScorePlayerTeam);
        this.field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet209SetPlayerTeam(par2ScorePlayerTeam, Arrays.asList(par1Str), 3));
        this.func_96551_b();
    }
    
    @Override
    public void removePlayerFromTeam(final String par1Str, final ScorePlayerTeam par2ScorePlayerTeam) {
        super.removePlayerFromTeam(par1Str, par2ScorePlayerTeam);
        this.field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet209SetPlayerTeam(par2ScorePlayerTeam, Arrays.asList(par1Str), 4));
        this.func_96551_b();
    }
    
    @Override
    public void func_96522_a(final ScoreObjective par1ScoreObjective) {
        super.func_96522_a(par1ScoreObjective);
        this.func_96551_b();
    }
    
    @Override
    public void func_96532_b(final ScoreObjective par1ScoreObjective) {
        super.func_96532_b(par1ScoreObjective);
        if (this.field_96553_b.contains(par1ScoreObjective)) {
            this.field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet206SetObjective(par1ScoreObjective, 2));
        }
        this.func_96551_b();
    }
    
    @Override
    public void func_96533_c(final ScoreObjective par1ScoreObjective) {
        super.func_96533_c(par1ScoreObjective);
        if (this.field_96553_b.contains(par1ScoreObjective)) {
            this.func_96546_g(par1ScoreObjective);
        }
        this.func_96551_b();
    }
    
    @Override
    public void func_96523_a(final ScorePlayerTeam par1ScorePlayerTeam) {
        super.func_96523_a(par1ScorePlayerTeam);
        this.field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet209SetPlayerTeam(par1ScorePlayerTeam, 0));
        this.func_96551_b();
    }
    
    @Override
    public void func_96538_b(final ScorePlayerTeam par1ScorePlayerTeam) {
        super.func_96538_b(par1ScorePlayerTeam);
        this.field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet209SetPlayerTeam(par1ScorePlayerTeam, 2));
        this.func_96551_b();
    }
    
    @Override
    public void func_96513_c(final ScorePlayerTeam par1ScorePlayerTeam) {
        super.func_96513_c(par1ScorePlayerTeam);
        this.field_96555_a.getConfigurationManager().sendPacketToAllPlayers(new Packet209SetPlayerTeam(par1ScorePlayerTeam, 1));
        this.func_96551_b();
    }
    
    public void func_96547_a(final ScoreboardSaveData par1ScoreboardSaveData) {
        this.field_96554_c = par1ScoreboardSaveData;
    }
    
    protected void func_96551_b() {
        if (this.field_96554_c != null) {
            this.field_96554_c.markDirty();
        }
    }
    
    public List func_96550_d(final ScoreObjective par1ScoreObjective) {
        final ArrayList var2 = new ArrayList();
        var2.add(new Packet206SetObjective(par1ScoreObjective, 0));
        for (int var3 = 0; var3 < 3; ++var3) {
            if (this.func_96539_a(var3) == par1ScoreObjective) {
                var2.add(new Packet208SetDisplayObjective(var3, par1ScoreObjective));
            }
        }
        for (final Score var5 : this.func_96534_i(par1ScoreObjective)) {
            var2.add(new Packet207SetScore(var5, 0));
        }
        return var2;
    }
    
    public void func_96549_e(final ScoreObjective par1ScoreObjective) {
        final List var2 = this.func_96550_d(par1ScoreObjective);
        for (final EntityPlayerMP var4 : this.field_96555_a.getConfigurationManager().playerEntityList) {
            for (final Packet var6 : var2) {
                var4.playerNetServerHandler.sendPacketToPlayer(var6);
            }
        }
        this.field_96553_b.add(par1ScoreObjective);
    }
    
    public List func_96548_f(final ScoreObjective par1ScoreObjective) {
        final ArrayList var2 = new ArrayList();
        var2.add(new Packet206SetObjective(par1ScoreObjective, 1));
        for (int var3 = 0; var3 < 3; ++var3) {
            if (this.func_96539_a(var3) == par1ScoreObjective) {
                var2.add(new Packet208SetDisplayObjective(var3, par1ScoreObjective));
            }
        }
        return var2;
    }
    
    public void func_96546_g(final ScoreObjective par1ScoreObjective) {
        final List var2 = this.func_96548_f(par1ScoreObjective);
        for (final EntityPlayerMP var4 : this.field_96555_a.getConfigurationManager().playerEntityList) {
            for (final Packet var6 : var2) {
                var4.playerNetServerHandler.sendPacketToPlayer(var6);
            }
        }
        this.field_96553_b.remove(par1ScoreObjective);
    }
    
    public int func_96552_h(final ScoreObjective par1ScoreObjective) {
        int var2 = 0;
        for (int var3 = 0; var3 < 3; ++var3) {
            if (this.func_96539_a(var3) == par1ScoreObjective) {
                ++var2;
            }
        }
        return var2;
    }
}
