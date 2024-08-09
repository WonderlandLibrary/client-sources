/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;

public class SUpdateBossInfoPacket
implements IPacket<IClientPlayNetHandler> {
    private UUID uniqueId;
    private Operation operation;
    private ITextComponent name;
    private float percent;
    private BossInfo.Color color;
    private BossInfo.Overlay overlay;
    private boolean darkenSky;
    private boolean playEndBossMusic;
    private boolean createFog;

    public SUpdateBossInfoPacket() {
    }

    public SUpdateBossInfoPacket(Operation operation, BossInfo bossInfo) {
        this.operation = operation;
        this.uniqueId = bossInfo.getUniqueId();
        this.name = bossInfo.getName();
        this.percent = bossInfo.getPercent();
        this.color = bossInfo.getColor();
        this.overlay = bossInfo.getOverlay();
        this.darkenSky = bossInfo.shouldDarkenSky();
        this.playEndBossMusic = bossInfo.shouldPlayEndBossMusic();
        this.createFog = bossInfo.shouldCreateFog();
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.uniqueId = packetBuffer.readUniqueId();
        this.operation = packetBuffer.readEnumValue(Operation.class);
        switch (1.$SwitchMap$net$minecraft$network$play$server$SUpdateBossInfoPacket$Operation[this.operation.ordinal()]) {
            case 1: {
                this.name = packetBuffer.readTextComponent();
                this.percent = packetBuffer.readFloat();
                this.color = packetBuffer.readEnumValue(BossInfo.Color.class);
                this.overlay = packetBuffer.readEnumValue(BossInfo.Overlay.class);
                this.setFlags(packetBuffer.readUnsignedByte());
            }
            default: {
                break;
            }
            case 3: {
                this.percent = packetBuffer.readFloat();
                break;
            }
            case 4: {
                this.name = packetBuffer.readTextComponent();
                break;
            }
            case 5: {
                this.color = packetBuffer.readEnumValue(BossInfo.Color.class);
                this.overlay = packetBuffer.readEnumValue(BossInfo.Overlay.class);
                break;
            }
            case 6: {
                this.setFlags(packetBuffer.readUnsignedByte());
            }
        }
    }

    private void setFlags(int n) {
        this.darkenSky = (n & 1) > 0;
        this.playEndBossMusic = (n & 2) > 0;
        this.createFog = (n & 4) > 0;
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeUniqueId(this.uniqueId);
        packetBuffer.writeEnumValue(this.operation);
        switch (1.$SwitchMap$net$minecraft$network$play$server$SUpdateBossInfoPacket$Operation[this.operation.ordinal()]) {
            case 1: {
                packetBuffer.writeTextComponent(this.name);
                packetBuffer.writeFloat(this.percent);
                packetBuffer.writeEnumValue(this.color);
                packetBuffer.writeEnumValue(this.overlay);
                packetBuffer.writeByte(this.getFlags());
            }
            default: {
                break;
            }
            case 3: {
                packetBuffer.writeFloat(this.percent);
                break;
            }
            case 4: {
                packetBuffer.writeTextComponent(this.name);
                break;
            }
            case 5: {
                packetBuffer.writeEnumValue(this.color);
                packetBuffer.writeEnumValue(this.overlay);
                break;
            }
            case 6: {
                packetBuffer.writeByte(this.getFlags());
            }
        }
    }

    private int getFlags() {
        int n = 0;
        if (this.darkenSky) {
            n |= 1;
        }
        if (this.playEndBossMusic) {
            n |= 2;
        }
        if (this.createFog) {
            n |= 4;
        }
        return n;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleUpdateBossInfo(this);
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public ITextComponent getName() {
        return this.name;
    }

    public float getPercent() {
        return this.percent;
    }

    public BossInfo.Color getColor() {
        return this.color;
    }

    public BossInfo.Overlay getOverlay() {
        return this.overlay;
    }

    public boolean shouldDarkenSky() {
        return this.darkenSky;
    }

    public boolean shouldPlayEndBossMusic() {
        return this.playEndBossMusic;
    }

    public boolean shouldCreateFog() {
        return this.createFog;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    public static enum Operation {
        ADD,
        REMOVE,
        UPDATE_PCT,
        UPDATE_NAME,
        UPDATE_STYLE,
        UPDATE_PROPERTIES;

    }
}

