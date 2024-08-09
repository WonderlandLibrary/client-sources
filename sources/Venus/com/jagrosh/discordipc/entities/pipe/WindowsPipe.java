/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.pipe.Pipe;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class WindowsPipe
extends Pipe {
    private RandomAccessFile file;

    WindowsPipe(IPCClient iPCClient, HashMap<String, Callback> hashMap, String string) {
        super(iPCClient, hashMap);
        try {
            this.file = new RandomAccessFile(string, "rw");
        } catch (FileNotFoundException fileNotFoundException) {
            // empty catch block
        }
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        this.file.write(byArray);
    }

    @Override
    public Packet read() throws IOException, JSONException {
        while (this.file.length() == 0L && this.status == PipeStatus.CONNECTED) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException interruptedException) {}
        }
        if (this.status == PipeStatus.DISCONNECTED) {
            throw new IOException("Disconnected!");
        }
        if (this.status == PipeStatus.CLOSED) {
            return new Packet(Packet.OpCode.CLOSE, null);
        }
        Packet.OpCode opCode = Packet.OpCode.values()[Integer.reverseBytes(this.file.readInt())];
        int n = Integer.reverseBytes(this.file.readInt());
        byte[] byArray = new byte[n];
        this.file.readFully(byArray);
        Packet packet = new Packet(opCode, new JSONObject(new String(byArray)));
        if (this.listener != null) {
            this.listener.onPacketReceived(this.ipcClient, packet);
        }
        if (packet.getJson().getString("evt").equals("READY")) {
            JSONObject jSONObject = packet.getJson().getJSONObject("data");
            JSONObject jSONObject2 = jSONObject.getJSONObject("user");
        }
        return packet;
    }

    @Override
    public void close() throws IOException {
        this.send(Packet.OpCode.CLOSE, new JSONObject(), null);
        this.status = PipeStatus.CLOSED;
        this.file.close();
    }
}

