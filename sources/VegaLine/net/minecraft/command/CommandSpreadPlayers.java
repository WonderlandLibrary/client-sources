/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class CommandSpreadPlayers
extends CommandBase {
    @Override
    public String getCommandName() {
        return "spreadplayers";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.spreadplayers.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 6) {
            throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
        }
        int i = 0;
        BlockPos blockpos = sender.getPosition();
        double d0 = CommandSpreadPlayers.parseDouble(blockpos.getX(), args[i++], true);
        double d1 = CommandSpreadPlayers.parseDouble(blockpos.getZ(), args[i++], true);
        double d2 = CommandSpreadPlayers.parseDouble(args[i++], 0.0);
        double d3 = CommandSpreadPlayers.parseDouble(args[i++], d2 + 1.0);
        boolean flag = CommandSpreadPlayers.parseBoolean(args[i++]);
        ArrayList<Entity> list = Lists.newArrayList();
        while (i < args.length) {
            String s;
            if (EntitySelector.hasArguments(s = args[i++])) {
                List<Entity> list1 = EntitySelector.matchEntities(sender, s, Entity.class);
                if (list1.isEmpty()) {
                    throw new EntityNotFoundException("commands.generic.selector.notFound", s);
                }
                list.addAll(list1);
                continue;
            }
            EntityPlayerMP entityplayer = server.getPlayerList().getPlayerByUsername(s);
            if (entityplayer == null) {
                throw new PlayerNotFoundException("commands.generic.player.notFound", s);
            }
            list.add(entityplayer);
        }
        sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
        if (list.isEmpty()) {
            throw new EntityNotFoundException("commands.spreadplayers.noop");
        }
        sender.addChatMessage(new TextComponentTranslation("commands.spreadplayers.spreading." + (flag ? "teams" : "players"), list.size(), d3, d0, d1, d2));
        this.spread(sender, list, new Position(d0, d1), d2, d3, ((Entity)list.get((int)0)).world, flag);
    }

    private void spread(ICommandSender sender, List<Entity> p_110669_2_, Position pos, double spreadDistance, double maxRange, World worldIn, boolean respectTeams) throws CommandException {
        Random random = new Random();
        double d0 = pos.x - maxRange;
        double d1 = pos.z - maxRange;
        double d2 = pos.x + maxRange;
        double d3 = pos.z + maxRange;
        Position[] acommandspreadplayers$position = this.createInitialPositions(random, respectTeams ? this.getNumberOfTeams(p_110669_2_) : p_110669_2_.size(), d0, d1, d2, d3);
        int i = this.spreadPositions(pos, spreadDistance, worldIn, random, d0, d1, d2, d3, acommandspreadplayers$position, respectTeams);
        double d4 = this.setPlayerPositions(p_110669_2_, worldIn, acommandspreadplayers$position, respectTeams);
        CommandSpreadPlayers.notifyCommandListener(sender, (ICommand)this, "commands.spreadplayers.success." + (respectTeams ? "teams" : "players"), acommandspreadplayers$position.length, pos.x, pos.z);
        if (acommandspreadplayers$position.length > 1) {
            sender.addChatMessage(new TextComponentTranslation("commands.spreadplayers.info." + (respectTeams ? "teams" : "players"), String.format("%.2f", d4), i));
        }
    }

    private int getNumberOfTeams(List<Entity> p_110667_1_) {
        HashSet<Team> set = Sets.newHashSet();
        for (Entity entity : p_110667_1_) {
            if (entity instanceof EntityPlayer) {
                set.add(entity.getTeam());
                continue;
            }
            set.add(null);
        }
        return set.size();
    }

    private int spreadPositions(Position p_110668_1_, double p_110668_2_, World worldIn, Random random, double minX, double minZ, double maxX, double maxZ, Position[] p_110668_14_, boolean respectTeams) throws CommandException {
        int i;
        boolean flag = true;
        double d0 = 3.4028234663852886E38;
        for (i = 0; i < 10000 && flag; ++i) {
            flag = false;
            d0 = 3.4028234663852886E38;
            for (int j = 0; j < p_110668_14_.length; ++j) {
                Position commandspreadplayers$position = p_110668_14_[j];
                int k = 0;
                Position commandspreadplayers$position1 = new Position();
                for (int l = 0; l < p_110668_14_.length; ++l) {
                    if (j == l) continue;
                    Position commandspreadplayers$position2 = p_110668_14_[l];
                    double d1 = commandspreadplayers$position.dist(commandspreadplayers$position2);
                    d0 = Math.min(d1, d0);
                    if (!(d1 < p_110668_2_)) continue;
                    ++k;
                    commandspreadplayers$position1.x += commandspreadplayers$position2.x - commandspreadplayers$position.x;
                    commandspreadplayers$position1.z += commandspreadplayers$position2.z - commandspreadplayers$position.z;
                }
                if (k > 0) {
                    commandspreadplayers$position1.x /= (double)k;
                    commandspreadplayers$position1.z /= (double)k;
                    double d2 = commandspreadplayers$position1.getLength();
                    if (d2 > 0.0) {
                        commandspreadplayers$position1.normalize();
                        commandspreadplayers$position.moveAway(commandspreadplayers$position1);
                    } else {
                        commandspreadplayers$position.randomize(random, minX, minZ, maxX, maxZ);
                    }
                    flag = true;
                }
                if (!commandspreadplayers$position.clamp(minX, minZ, maxX, maxZ)) continue;
                flag = true;
            }
            if (flag) continue;
            for (Position commandspreadplayers$position3 : p_110668_14_) {
                if (commandspreadplayers$position3.isSafe(worldIn)) continue;
                commandspreadplayers$position3.randomize(random, minX, minZ, maxX, maxZ);
                flag = true;
            }
        }
        if (i >= 10000) {
            throw new CommandException("commands.spreadplayers.failure." + (respectTeams ? "teams" : "players"), p_110668_14_.length, p_110668_1_.x, p_110668_1_.z, String.format("%.2f", d0));
        }
        return i;
    }

    private double setPlayerPositions(List<Entity> p_110671_1_, World worldIn, Position[] p_110671_3_, boolean p_110671_4_) {
        double d0 = 0.0;
        int i = 0;
        HashMap<Team, Position> map = Maps.newHashMap();
        for (int j = 0; j < p_110671_1_.size(); ++j) {
            Position commandspreadplayers$position;
            Entity entity = p_110671_1_.get(j);
            if (p_110671_4_) {
                Team team;
                Team team2 = team = entity instanceof EntityPlayer ? entity.getTeam() : null;
                if (!map.containsKey(team)) {
                    map.put(team, p_110671_3_[i++]);
                }
                commandspreadplayers$position = (Position)map.get(team);
            } else {
                commandspreadplayers$position = p_110671_3_[i++];
            }
            entity.setPositionAndUpdate((float)MathHelper.floor(commandspreadplayers$position.x) + 0.5f, commandspreadplayers$position.getSpawnY(worldIn), (double)MathHelper.floor(commandspreadplayers$position.z) + 0.5);
            double d2 = Double.MAX_VALUE;
            for (Position commandspreadplayers$position1 : p_110671_3_) {
                if (commandspreadplayers$position == commandspreadplayers$position1) continue;
                double d1 = commandspreadplayers$position.dist(commandspreadplayers$position1);
                d2 = Math.min(d1, d2);
            }
            d0 += d2;
        }
        return d0 /= (double)p_110671_1_.size();
    }

    private Position[] createInitialPositions(Random p_110670_1_, int p_110670_2_, double p_110670_3_, double p_110670_5_, double p_110670_7_, double p_110670_9_) {
        Position[] acommandspreadplayers$position = new Position[p_110670_2_];
        for (int i = 0; i < acommandspreadplayers$position.length; ++i) {
            Position commandspreadplayers$position = new Position();
            commandspreadplayers$position.randomize(p_110670_1_, p_110670_3_, p_110670_5_, p_110670_7_, p_110670_9_);
            acommandspreadplayers$position[i] = commandspreadplayers$position;
        }
        return acommandspreadplayers$position;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return args.length >= 1 && args.length <= 2 ? CommandSpreadPlayers.getTabCompletionCoordinateXZ(args, 0, pos) : Collections.emptyList();
    }

    static class Position {
        double x;
        double z;

        Position() {
        }

        Position(double xIn, double zIn) {
            this.x = xIn;
            this.z = zIn;
        }

        double dist(Position pos) {
            double d0 = this.x - pos.x;
            double d1 = this.z - pos.z;
            return Math.sqrt(d0 * d0 + d1 * d1);
        }

        void normalize() {
            double d0 = this.getLength();
            this.x /= d0;
            this.z /= d0;
        }

        float getLength() {
            return MathHelper.sqrt(this.x * this.x + this.z * this.z);
        }

        public void moveAway(Position pos) {
            this.x -= pos.x;
            this.z -= pos.z;
        }

        public boolean clamp(double p_111093_1_, double p_111093_3_, double p_111093_5_, double p_111093_7_) {
            boolean flag = false;
            if (this.x < p_111093_1_) {
                this.x = p_111093_1_;
                flag = true;
            } else if (this.x > p_111093_5_) {
                this.x = p_111093_5_;
                flag = true;
            }
            if (this.z < p_111093_3_) {
                this.z = p_111093_3_;
                flag = true;
            } else if (this.z > p_111093_7_) {
                this.z = p_111093_7_;
                flag = true;
            }
            return flag;
        }

        public int getSpawnY(World worldIn) {
            BlockPos blockpos = new BlockPos(this.x, 256.0, this.z);
            while (blockpos.getY() > 0) {
                if (worldIn.getBlockState(blockpos = blockpos.down()).getMaterial() == Material.AIR) continue;
                return blockpos.getY() + 1;
            }
            return 257;
        }

        public boolean isSafe(World worldIn) {
            BlockPos blockpos = new BlockPos(this.x, 256.0, this.z);
            while (blockpos.getY() > 0) {
                Material material = worldIn.getBlockState(blockpos = blockpos.down()).getMaterial();
                if (material == Material.AIR) continue;
                return !material.isLiquid() && material != Material.FIRE;
            }
            return false;
        }

        public void randomize(Random rand, double p_111097_2_, double p_111097_4_, double p_111097_6_, double p_111097_8_) {
            this.x = MathHelper.nextDouble(rand, p_111097_2_, p_111097_6_);
            this.z = MathHelper.nextDouble(rand, p_111097_4_, p_111097_8_);
        }
    }
}

