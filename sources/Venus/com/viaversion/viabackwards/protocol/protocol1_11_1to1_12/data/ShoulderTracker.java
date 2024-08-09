/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;

public class ShoulderTracker
extends StoredObject {
    private int entityId;
    private String leftShoulder;
    private String rightShoulder;

    public ShoulderTracker(UserConnection userConnection) {
        super(userConnection);
    }

    public void update() {
        PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_12.CHAT_MESSAGE, null, this.getUser());
        packetWrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson(this.generateString()));
        packetWrapper.write(Type.BYTE, (byte)2);
        try {
            packetWrapper.scheduleSend(Protocol1_11_1To1_12.class);
        } catch (Exception exception) {
            ViaBackwards.getPlatform().getLogger().severe("Failed to send the shoulder indication");
            exception.printStackTrace();
        }
    }

    private String generateString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        if (this.leftShoulder == null) {
            stringBuilder.append("\u00a74\u00a7lNothing");
        } else {
            stringBuilder.append("\u00a72\u00a7l").append(this.getName(this.leftShoulder));
        }
        stringBuilder.append("\u00a78\u00a7l <- \u00a77\u00a7lShoulders\u00a78\u00a7l -> ");
        if (this.rightShoulder == null) {
            stringBuilder.append("\u00a74\u00a7lNothing");
        } else {
            stringBuilder.append("\u00a72\u00a7l").append(this.getName(this.rightShoulder));
        }
        return stringBuilder.toString();
    }

    private String getName(String string) {
        if (string.startsWith("minecraft:")) {
            string = string.substring(10);
        }
        String[] stringArray = string.split("_");
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : stringArray) {
            stringBuilder.append(string2.substring(0, 1).toUpperCase()).append(string2.substring(1)).append(" ");
        }
        return stringBuilder.toString();
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int n) {
        this.entityId = n;
    }

    public String getLeftShoulder() {
        return this.leftShoulder;
    }

    public void setLeftShoulder(String string) {
        this.leftShoulder = string;
    }

    public String getRightShoulder() {
        return this.rightShoulder;
    }

    public void setRightShoulder(String string) {
        this.rightShoulder = string;
    }

    public String toString() {
        return "ShoulderTracker{entityId=" + this.entityId + ", leftShoulder='" + this.leftShoulder + '\'' + ", rightShoulder='" + this.rightShoulder + '\'' + '}';
    }
}

