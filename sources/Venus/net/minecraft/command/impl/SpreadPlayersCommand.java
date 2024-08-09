/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.Vec2Argument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class SpreadPlayersCommand {
    private static final Dynamic4CommandExceptionType SPREAD_TEAMS_FAILED = new Dynamic4CommandExceptionType(SpreadPlayersCommand::lambda$static$0);
    private static final Dynamic4CommandExceptionType SPREAD_ENTITIES_FAILED = new Dynamic4CommandExceptionType(SpreadPlayersCommand::lambda$static$1);

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("spreadplayers").requires(SpreadPlayersCommand::lambda$register$2)).then(Commands.argument("center", Vec2Argument.vec2()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("spreadDistance", FloatArgumentType.floatArg(0.0f)).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("maxRange", FloatArgumentType.floatArg(1.0f)).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("respectTeams", BoolArgumentType.bool()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.entities()).executes(SpreadPlayersCommand::lambda$register$3)))).then(Commands.literal("under").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("maxHeight", IntegerArgumentType.integer(0)).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("respectTeams", BoolArgumentType.bool()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.entities()).executes(SpreadPlayersCommand::lambda$register$4)))))))));
    }

    private static int func_241070_a_(CommandSource commandSource, Vector2f vector2f, float f, float f2, int n, boolean bl, Collection<? extends Entity> collection) throws CommandSyntaxException {
        Random random2 = new Random();
        double d = vector2f.x - f2;
        double d2 = vector2f.y - f2;
        double d3 = vector2f.x + f2;
        double d4 = vector2f.y + f2;
        Position[] positionArray = SpreadPlayersCommand.getPositions(random2, bl ? SpreadPlayersCommand.getNumberOfTeams(collection) : collection.size(), d, d2, d3, d4);
        SpreadPlayersCommand.func_241071_a_(vector2f, f, commandSource.getWorld(), random2, d, d2, d3, d4, n, positionArray, bl);
        double d5 = SpreadPlayersCommand.func_241072_a_(collection, commandSource.getWorld(), positionArray, n, bl);
        commandSource.sendFeedback(new TranslationTextComponent("commands.spreadplayers.success." + (bl ? "teams" : "entities"), positionArray.length, Float.valueOf(vector2f.x), Float.valueOf(vector2f.y), String.format(Locale.ROOT, "%.2f", d5)), false);
        return positionArray.length;
    }

    private static int getNumberOfTeams(Collection<? extends Entity> collection) {
        HashSet<Team> hashSet = Sets.newHashSet();
        for (Entity entity2 : collection) {
            if (entity2 instanceof PlayerEntity) {
                hashSet.add(entity2.getTeam());
                continue;
            }
            hashSet.add(null);
        }
        return hashSet.size();
    }

    private static void func_241071_a_(Vector2f vector2f, double d, ServerWorld serverWorld, Random random2, double d2, double d3, double d4, double d5, int n, Position[] positionArray, boolean bl) throws CommandSyntaxException {
        int n2;
        boolean bl2 = true;
        double d6 = 3.4028234663852886E38;
        for (n2 = 0; n2 < 10000 && bl2; ++n2) {
            bl2 = false;
            d6 = 3.4028234663852886E38;
            for (int i = 0; i < positionArray.length; ++i) {
                Position position = positionArray[i];
                int n3 = 0;
                Position position2 = new Position();
                for (int j = 0; j < positionArray.length; ++j) {
                    if (i == j) continue;
                    Position position3 = positionArray[j];
                    double d7 = position.getDistance(position3);
                    d6 = Math.min(d7, d6);
                    if (!(d7 < d)) continue;
                    ++n3;
                    position2.x += position3.x - position.x;
                    position2.z += position3.z - position.z;
                }
                if (n3 > 0) {
                    position2.x /= (double)n3;
                    position2.z /= (double)n3;
                    double d8 = position2.getMagnitude();
                    if (d8 > 0.0) {
                        position2.normalize();
                        position.subtract(position2);
                    } else {
                        position.computeCoords(random2, d2, d3, d4, d5);
                    }
                    bl2 = true;
                }
                if (!position.clampWithinRange(d2, d3, d4, d5)) continue;
                bl2 = true;
            }
            if (bl2) continue;
            for (Position position2 : positionArray) {
                if (position2.func_241074_b_(serverWorld, n)) continue;
                position2.computeCoords(random2, d2, d3, d4, d5);
                bl2 = true;
            }
        }
        if (d6 == 3.4028234663852886E38) {
            d6 = 0.0;
        }
        if (n2 >= 10000) {
            if (bl) {
                throw SPREAD_TEAMS_FAILED.create(positionArray.length, Float.valueOf(vector2f.x), Float.valueOf(vector2f.y), String.format(Locale.ROOT, "%.2f", d6));
            }
            throw SPREAD_ENTITIES_FAILED.create(positionArray.length, Float.valueOf(vector2f.x), Float.valueOf(vector2f.y), String.format(Locale.ROOT, "%.2f", d6));
        }
    }

    private static double func_241072_a_(Collection<? extends Entity> collection, ServerWorld serverWorld, Position[] positionArray, int n, boolean bl) {
        double d = 0.0;
        int n2 = 0;
        HashMap<Team, Position> hashMap = Maps.newHashMap();
        for (Entity entity2 : collection) {
            Position position;
            if (bl) {
                Team team;
                Team team2 = team = entity2 instanceof PlayerEntity ? entity2.getTeam() : null;
                if (!hashMap.containsKey(team)) {
                    hashMap.put(team, positionArray[n2++]);
                }
                position = (Position)hashMap.get(team);
            } else {
                position = positionArray[n2++];
            }
            entity2.teleportKeepLoaded((double)MathHelper.floor(position.x) + 0.5, position.getHighestNonAirBlock(serverWorld, n), (double)MathHelper.floor(position.z) + 0.5);
            double d2 = Double.MAX_VALUE;
            for (Position position2 : positionArray) {
                if (position == position2) continue;
                double d3 = position.getDistance(position2);
                d2 = Math.min(d3, d2);
            }
            d += d2;
        }
        return collection.size() < 2 ? 0.0 : d / (double)collection.size();
    }

    private static Position[] getPositions(Random random2, int n, double d, double d2, double d3, double d4) {
        Position[] positionArray = new Position[n];
        for (int i = 0; i < positionArray.length; ++i) {
            Position position = new Position();
            position.computeCoords(random2, d, d2, d3, d4);
            positionArray[i] = position;
        }
        return positionArray;
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return SpreadPlayersCommand.func_241070_a_((CommandSource)commandContext.getSource(), Vec2Argument.getVec2f(commandContext, "center"), FloatArgumentType.getFloat(commandContext, "spreadDistance"), FloatArgumentType.getFloat(commandContext, "maxRange"), IntegerArgumentType.getInteger(commandContext, "maxHeight"), BoolArgumentType.getBool(commandContext, "respectTeams"), EntityArgument.getEntities(commandContext, "targets"));
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return SpreadPlayersCommand.func_241070_a_((CommandSource)commandContext.getSource(), Vec2Argument.getVec2f(commandContext, "center"), FloatArgumentType.getFloat(commandContext, "spreadDistance"), FloatArgumentType.getFloat(commandContext, "maxRange"), 256, BoolArgumentType.getBool(commandContext, "respectTeams"), EntityArgument.getEntities(commandContext, "targets"));
    }

    private static boolean lambda$register$2(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$1(Object object, Object object2, Object object3, Object object4) {
        return new TranslationTextComponent("commands.spreadplayers.failed.entities", object, object2, object3, object4);
    }

    private static Message lambda$static$0(Object object, Object object2, Object object3, Object object4) {
        return new TranslationTextComponent("commands.spreadplayers.failed.teams", object, object2, object3, object4);
    }

    static class Position {
        private double x;
        private double z;

        Position() {
        }

        double getDistance(Position position) {
            double d = this.x - position.x;
            double d2 = this.z - position.z;
            return Math.sqrt(d * d + d2 * d2);
        }

        void normalize() {
            double d = this.getMagnitude();
            this.x /= d;
            this.z /= d;
        }

        float getMagnitude() {
            return MathHelper.sqrt(this.x * this.x + this.z * this.z);
        }

        public void subtract(Position position) {
            this.x -= position.x;
            this.z -= position.z;
        }

        public boolean clampWithinRange(double d, double d2, double d3, double d4) {
            boolean bl = false;
            if (this.x < d) {
                this.x = d;
                bl = true;
            } else if (this.x > d3) {
                this.x = d3;
                bl = true;
            }
            if (this.z < d2) {
                this.z = d2;
                bl = true;
            } else if (this.z > d4) {
                this.z = d4;
                bl = true;
            }
            return bl;
        }

        public int getHighestNonAirBlock(IBlockReader iBlockReader, int n) {
            BlockPos.Mutable mutable = new BlockPos.Mutable(this.x, (double)(n + 1), this.z);
            boolean bl = iBlockReader.getBlockState(mutable).isAir();
            mutable.move(Direction.DOWN);
            boolean bl2 = iBlockReader.getBlockState(mutable).isAir();
            while (mutable.getY() > 0) {
                mutable.move(Direction.DOWN);
                boolean bl3 = iBlockReader.getBlockState(mutable).isAir();
                if (!bl3 && bl2 && bl) {
                    return mutable.getY() + 1;
                }
                bl = bl2;
                bl2 = bl3;
            }
            return n + 1;
        }

        public boolean func_241074_b_(IBlockReader iBlockReader, int n) {
            BlockPos blockPos = new BlockPos(this.x, (double)(this.getHighestNonAirBlock(iBlockReader, n) - 1), this.z);
            BlockState blockState = iBlockReader.getBlockState(blockPos);
            Material material = blockState.getMaterial();
            return blockPos.getY() < n && !material.isLiquid() && material != Material.FIRE;
        }

        public void computeCoords(Random random2, double d, double d2, double d3, double d4) {
            this.x = MathHelper.nextDouble(random2, d, d3);
            this.z = MathHelper.nextDouble(random2, d2, d4);
        }
    }
}

