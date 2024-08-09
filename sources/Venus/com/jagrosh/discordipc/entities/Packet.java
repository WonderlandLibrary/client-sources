/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jagrosh.discordipc.entities;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class Packet {
    private final OpCode op;
    private final JSONObject data;

    public Packet(OpCode opCode, JSONObject jSONObject) {
        this.op = opCode;
        this.data = jSONObject;
    }

    public byte[] toBytes() {
        byte[] byArray = this.data.toString().getBytes(StandardCharsets.UTF_8);
        ByteBuffer byteBuffer = ByteBuffer.allocate(byArray.length + 8);
        byteBuffer.putInt(Integer.reverseBytes(this.op.ordinal()));
        byteBuffer.putInt(Integer.reverseBytes(byArray.length));
        byteBuffer.put(byArray);
        return byteBuffer.array();
    }

    public OpCode getOp() {
        return this.op;
    }

    public JSONObject getJson() {
        return this.data;
    }

    public String toString() {
        return "Pkt:" + this.getOp() + this.getJson().toString();
    }

    public static enum OpCode {
        HANDSHAKE,
        FRAME,
        CLOSE,
        PING,
        PONG;

    }
}

