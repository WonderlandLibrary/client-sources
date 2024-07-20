/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import ru.govno.client.Client;
import ru.govno.client.utils.Command.Command;

public class WorldInfo
extends Command {
    public WorldInfo() {
        super("WorldInfo", new String[]{"world", "w"});
    }

    Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    @Override
    public void onCommand(String[] args) {
        try {
            if (this.mc().world == null) {
                Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77world is null", false);
            } else {
                if (args[1].equalsIgnoreCase("mapsize") || args[1].equalsIgnoreCase("mps") || args[1].equalsIgnoreCase("size")) {
                    int size = -1010011;
                    if (this.mc().world.getWorldBorder() != null) {
                        size = this.mc().world.getWorldBorder().getSize();
                    }
                    if (size == -1010011) {
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77map border is null" + size, false);
                    } else {
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77map size: " + size, false);
                    }
                }
                if (args[1].equalsIgnoreCase("biome") || args[1].equalsIgnoreCase("bm")) {
                    WorldClient worldClient = this.mc().world;
                    this.mc();
                    if (worldClient.getBiome(Minecraft.player.getPosition().down()) == null) {
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77biome is null", false);
                    } else {
                        WorldClient worldClient2 = this.mc().world;
                        this.mc();
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77biome is " + worldClient2.getBiome(Minecraft.player.getPosition().down()).getBiomeName(), false);
                    }
                }
                if (args[1].equalsIgnoreCase("rules") || args[1].equalsIgnoreCase("rs")) {
                    Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77world rules list:", false);
                    int number = 0;
                    for (String rule : this.mc().world.getGameRules().getRules()) {
                        boolean ruleIsTrue = this.mc().world.getGameRules().getBoolean(rule);
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77rule \u2116" + ++number + ": " + rule + " = " + ruleIsTrue, false);
                    }
                }
                if (args[1].equalsIgnoreCase("difficulty") || args[1].equalsIgnoreCase("dif")) {
                    Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77world difficulty is " + this.mc().world.getDifficulty().name(), false);
                }
                if (args[1].equalsIgnoreCase("seed") || args[1].equalsIgnoreCase("s")) {
                    long seed = this.mc().world.getSeed();
                    if (seed == 0L) {
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77world seed protected", false);
                    } else {
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77world seed is " + seed, false);
                    }
                }
                if (args[1].equalsIgnoreCase("height") || args[1].equalsIgnoreCase("h")) {
                    Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77world height is " + this.mc().world.getHeight(), false);
                }
                if (args[1].equalsIgnoreCase("spawnpoint") || args[1].equalsIgnoreCase("sp")) {
                    if (this.mc().world.getSpawnPoint() == null) {
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77spawnpoint is null", false);
                    } else {
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77spawnpoint coords: X=" + this.mc().world.getSpawnPoint().getX() + " ,Y=" + this.mc().world.getSpawnPoint().getY() + " ,Z=" + this.mc().world.getSpawnPoint().getZ(), false);
                    }
                }
                if (args[1].equalsIgnoreCase("enities") || args[1].equalsIgnoreCase("ents")) {
                    if (this.mc().world.getLoadedEntityList() == null) {
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77enities in world list is null", false);
                    } else {
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77enities in world list:", false);
                        int entInt = 0;
                        for (Entity entity : this.mc().world.getLoadedEntityList()) {
                            ++entInt;
                            Object hp = "";
                            if (entity instanceof EntityLivingBase) {
                                hp = " \u00a7r\u00a7a\u00a7nHP:" + ((EntityLivingBase)entity).getHealth();
                            }
                            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77obj \u2116" + entInt + ": " + entity.getDisplayName().getUnformattedText() + (String)hp, false);
                        }
                    }
                }
                if (args[1].equalsIgnoreCase("gc")) {
                    System.gc();
                    Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77system memory cleared sucessfully", false);
                }
                if (args[1].equalsIgnoreCase("type") || args[1].equalsIgnoreCase("t")) {
                    Object type2 = null;
                    if (this.mc().world.getWorldType() != null) {
                        type2 = this.mc().world.getWorldType().getWorldTypeName();
                        type2 = ((String)type2).substring(0, 1).toUpperCase() + ((String)type2).substring(1, ((String)type2).length());
                    }
                    if (type2 == null) {
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77not has world type info", false);
                    } else {
                        Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77world type is: " + type2, false);
                    }
                }
            }
        } catch (Exception formatException) {
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77use: world/w", false);
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77border size: mapsize/mps/size", false);
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77biome: biome/bm", false);
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77rules: rules/rs", false);
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77difficulty: difficulty/dif", false);
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77seed: seed/s", false);
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77height: height/h", false);
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77spawnpoint: spawnpoint/sp", false);
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77entities: enities/ents", false);
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77system gc: gc", false);
            Client.msg("\u00a7b\u00a7lWorldInfo:\u00a7r \u00a77world type: type/t", false);
        }
    }
}

