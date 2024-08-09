/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.border.WorldBorder;

public class SWorldBorderPacket
implements IPacket<IClientPlayNetHandler> {
    private Action action;
    private int size;
    private double centerX;
    private double centerZ;
    private double targetSize;
    private double diameter;
    private long timeUntilTarget;
    private int warningTime;
    private int warningDistance;

    public SWorldBorderPacket() {
    }

    public SWorldBorderPacket(WorldBorder worldBorder, Action action) {
        this.action = action;
        this.centerX = worldBorder.getCenterX();
        this.centerZ = worldBorder.getCenterZ();
        this.diameter = worldBorder.getDiameter();
        this.targetSize = worldBorder.getTargetSize();
        this.timeUntilTarget = worldBorder.getTimeUntilTarget();
        this.size = worldBorder.getSize();
        this.warningDistance = worldBorder.getWarningDistance();
        this.warningTime = worldBorder.getWarningTime();
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.action = packetBuffer.readEnumValue(Action.class);
        switch (1.$SwitchMap$net$minecraft$network$play$server$SWorldBorderPacket$Action[this.action.ordinal()]) {
            case 1: {
                this.targetSize = packetBuffer.readDouble();
                break;
            }
            case 2: {
                this.diameter = packetBuffer.readDouble();
                this.targetSize = packetBuffer.readDouble();
                this.timeUntilTarget = packetBuffer.readVarLong();
                break;
            }
            case 3: {
                this.centerX = packetBuffer.readDouble();
                this.centerZ = packetBuffer.readDouble();
                break;
            }
            case 4: {
                this.warningDistance = packetBuffer.readVarInt();
                break;
            }
            case 5: {
                this.warningTime = packetBuffer.readVarInt();
                break;
            }
            case 6: {
                this.centerX = packetBuffer.readDouble();
                this.centerZ = packetBuffer.readDouble();
                this.diameter = packetBuffer.readDouble();
                this.targetSize = packetBuffer.readDouble();
                this.timeUntilTarget = packetBuffer.readVarLong();
                this.size = packetBuffer.readVarInt();
                this.warningDistance = packetBuffer.readVarInt();
                this.warningTime = packetBuffer.readVarInt();
            }
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.action);
        switch (1.$SwitchMap$net$minecraft$network$play$server$SWorldBorderPacket$Action[this.action.ordinal()]) {
            case 1: {
                packetBuffer.writeDouble(this.targetSize);
                break;
            }
            case 2: {
                packetBuffer.writeDouble(this.diameter);
                packetBuffer.writeDouble(this.targetSize);
                packetBuffer.writeVarLong(this.timeUntilTarget);
                break;
            }
            case 3: {
                packetBuffer.writeDouble(this.centerX);
                packetBuffer.writeDouble(this.centerZ);
                break;
            }
            case 4: {
                packetBuffer.writeVarInt(this.warningDistance);
                break;
            }
            case 5: {
                packetBuffer.writeVarInt(this.warningTime);
                break;
            }
            case 6: {
                packetBuffer.writeDouble(this.centerX);
                packetBuffer.writeDouble(this.centerZ);
                packetBuffer.writeDouble(this.diameter);
                packetBuffer.writeDouble(this.targetSize);
                packetBuffer.writeVarLong(this.timeUntilTarget);
                packetBuffer.writeVarInt(this.size);
                packetBuffer.writeVarInt(this.warningDistance);
                packetBuffer.writeVarInt(this.warningTime);
            }
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleWorldBorder(this);
    }

    public void apply(WorldBorder worldBorder) {
        switch (1.$SwitchMap$net$minecraft$network$play$server$SWorldBorderPacket$Action[this.action.ordinal()]) {
            case 1: {
                worldBorder.setTransition(this.targetSize);
                break;
            }
            case 2: {
                worldBorder.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
                break;
            }
            case 3: {
                worldBorder.setCenter(this.centerX, this.centerZ);
                break;
            }
            case 4: {
                worldBorder.setWarningDistance(this.warningDistance);
                break;
            }
            case 5: {
                worldBorder.setWarningTime(this.warningTime);
                break;
            }
            case 6: {
                worldBorder.setCenter(this.centerX, this.centerZ);
                if (this.timeUntilTarget > 0L) {
                    worldBorder.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
                } else {
                    worldBorder.setTransition(this.targetSize);
                }
                worldBorder.setSize(this.size);
                worldBorder.setWarningDistance(this.warningDistance);
                worldBorder.setWarningTime(this.warningTime);
            }
        }
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    public static enum Action {
        SET_SIZE,
        LERP_SIZE,
        SET_CENTER,
        INITIALIZE,
        SET_WARNING_TIME,
        SET_WARNING_BLOCKS;

    }
}

