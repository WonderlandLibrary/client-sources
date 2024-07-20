/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketVehicleMove;
import ru.govno.client.Client;
import ru.govno.client.utils.Command.Command;

public class RidingDRSync
extends Command {
    private Entity ridingEntity;

    public RidingDRSync() {
        super("RidingDRSync", new String[]{"entity", "ent", "riding", "ride"});
    }

    private final Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    @Override
    public void onCommand(String[] args) {
        try {
            if (args[1].equalsIgnoreCase("getHui")) {
                // empty if block
            }
            if (args[1].equalsIgnoreCase("desync") || args[1].equalsIgnoreCase("des")) {
                this.mc();
                if (Minecraft.player.getRidingEntity() != null) {
                    this.mc();
                    this.ridingEntity = Minecraft.player.getRidingEntity();
                    this.mc();
                    Minecraft.player.dismountRidingEntity();
                    this.mc().world.removeEntity(this.ridingEntity);
                    Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77desync is successful", false);
                } else {
                    Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77riding entity is null", false);
                }
                return;
            }
            if (args[1].equalsIgnoreCase("resync") || args[1].equalsIgnoreCase("res")) {
                if (this.ridingEntity != null) {
                    this.mc().world.addEntityToWorld(this.ridingEntity.getEntityId(), this.ridingEntity);
                    this.mc();
                    Minecraft.player.startRiding(this.ridingEntity);
                    this.mc();
                    Minecraft.player.ridingEntity = this.ridingEntity;
                    Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77resync is successful", false);
                    this.ridingEntity = null;
                } else {
                    Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77old riding entity is null", false);
                }
                return;
            }
            if (args[1].equalsIgnoreCase("dismount") || args[1].equalsIgnoreCase("dis")) {
                this.mc();
                if (Minecraft.player.getRidingEntity() != null) {
                    this.mc();
                    if (Minecraft.player.isSneaking()) {
                        this.mc();
                        if (!Minecraft.player.isAirBorne) {
                            NetHandlerPlayClient netHandlerPlayClient = this.mc().getConnection();
                            this.mc();
                            netHandlerPlayClient.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        }
                    }
                    NetHandlerPlayClient netHandlerPlayClient = this.mc().getConnection();
                    this.mc();
                    netHandlerPlayClient.sendPacket(new CPacketVehicleMove(Minecraft.player.getRidingEntity()));
                    NetHandlerPlayClient netHandlerPlayClient2 = this.mc().getConnection();
                    this.mc();
                    netHandlerPlayClient2.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
                    this.mc();
                    Minecraft.player.dismountRidingEntity();
                    Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77dismount entity is successful", false);
                } else {
                    Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77riding entity is null", false);
                }
                return;
            }
        } catch (Exception formatException) {
            Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77use: entity/ent/riding/ride", false);
            Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77desync: desync/des", false);
            Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77resync: resync/res", false);
            Client.msg("\u00a79\u00a7lRiding:\u00a7r \u00a77dismount: dismount/dis", false);
        }
    }
}

