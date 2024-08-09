/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.commands.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.systems.Globals;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;
import static meteordevelopment.meteorclient.MeteorClient.mc;
import static meteordevelopment.meteorclient.utils.player.MoveUtils.sendPositionPacket;

public class DamageCommand extends Command {
    private final static SimpleCommandExceptionType INVULNERABLE = new SimpleCommandExceptionType(Text.literal("You are invulnerable."));

    public DamageCommand() {
        super("damage", "Damages self", "dmg");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder
            .then(literal("NCPGlitch")
                .then(argument("amount", FloatArgumentType.floatArg())
                    .executes(context -> {
                        if (mc.player == null) return SINGLE_SUCCESS;
                        double x = mc.player.getX();
                        double y = mc.player.getY();
                        double z = mc.player.getZ();

                        int times = (int) (55 + context.getArgument("amount", Float.class) * 10.204);

                        for (int i = 0; i < times; i++) {
                            sendPositionPacket(x, y + 0.049, z, false);
                            sendPositionPacket(x, y, z, false);
                        }
                        sendPositionPacket(x, y, z, true);

                        return SINGLE_SUCCESS;
                    }))
            )
            .then(literal("NCP")
                .executes(context -> {
                    if (mc.player == null) return SINGLE_SUCCESS;
                    double x = mc.player.getX();
                    double y = mc.player.getY();
                    double z = mc.player.getZ();

                    for (int i = 0; i < 4; i++) {
                        for (double pos : Globals.jumpPositions) {
                            sendPositionPacket(x, y + pos, z, false);
                        }
                        sendPositionPacket(x, y, z, false);
                    }
                    sendPositionPacket(x, y, z, true);
                    return SINGLE_SUCCESS;
                })
            )
            .then(literal("VulcanOld")
                .executes(context -> {
                    if (mc.player == null) return SINGLE_SUCCESS;
                    double x = mc.player.getX();
                    double y = mc.player.getY();
                    double z = mc.player.getZ();

                    for (int i = 0; i < 6; i++) {
                        double position = y;
                        for (double pos : Globals.vulcanJumpPositions) {
                            position += pos;
                            sendPositionPacket(x, position, z, false);
                        }
                    }
                    sendPositionPacket(x, y, z, true);
                    return SINGLE_SUCCESS;
                })
            )
            .then(literal("VulcanNew")
                .executes(context -> {
                    if (mc.player == null) return SINGLE_SUCCESS;
                    double x = mc.player.getX();
                    double y = mc.player.getY();
                    double z = mc.player.getZ();

                    sendPositionPacket(x, y, z, true);
                    //sendPositionPacket(x, y - 0.0784, z, false);
                    sendPositionPacket(x, y - 0.0784, z, false);
                    sendPositionPacket(x, y, z, false);
                    sendPositionPacket(x, y + 0.41999998688697815, z, false);
                    sendPositionPacket(x, y + 0.7531999805212, z, false);
                    sendPositionPacket(x, y + 1, z, false);
                    sendPositionPacket(x, y + 1.4199999868869781, z, false);
                    sendPositionPacket(x, y + 1.7531999805212, z, false);
                    sendPositionPacket(x, y + 2, z, false);
                    sendPositionPacket(x, y + 2.419999986886978, z, false);
                    sendPositionPacket(x, y + 2.7531999805212, z, false);
                    sendPositionPacket(x, y + 3.00133597911214, z, false);
                    mc.player.setPosition(x, y + 3.00133597911214, z);
                    return SINGLE_SUCCESS;
                })
            )
        ;
    }

    private void damagePlayer(int amount) {
        Vec3d pos = mc.player.getPos();

        for(int i = 0; i < 80; i++) {
            sendPositionPacket(pos.x, pos.y + amount + 2.1, pos.z, false);
            sendPositionPacket(pos.x, pos.y + 0.05, pos.z, false);
        }

        sendPositionPacket(pos.x, pos.y, pos.z, true);
    }


}
