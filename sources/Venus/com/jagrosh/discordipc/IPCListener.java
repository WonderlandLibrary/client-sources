/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jagrosh.discordipc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.User;
import org.json.JSONObject;

public interface IPCListener {
    default public void onPacketSent(IPCClient iPCClient, Packet packet) {
    }

    default public void onPacketReceived(IPCClient iPCClient, Packet packet) {
    }

    default public void onActivityJoin(IPCClient iPCClient, String string) {
    }

    default public void onActivitySpectate(IPCClient iPCClient, String string) {
    }

    default public void onActivityJoinRequest(IPCClient iPCClient, String string, User user) {
    }

    default public void onReady(IPCClient iPCClient) {
    }

    default public void onClose(IPCClient iPCClient, JSONObject jSONObject) {
    }

    default public void onDisconnect(IPCClient iPCClient, Throwable throwable) {
    }
}

