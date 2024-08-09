/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.client;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.dto.RegionPingResult;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.util.Util;

public class Ping {
    public static List<RegionPingResult> func_224867_a(Region ... regionArray) {
        for (Region region : regionArray) {
            Ping.func_224868_a(region.field_224863_j);
        }
        ArrayList arrayList = Lists.newArrayList();
        for (Region region : regionArray) {
            arrayList.add(new RegionPingResult(region.field_224862_i, Ping.func_224868_a(region.field_224863_j)));
        }
        arrayList.sort(Comparator.comparingInt(RegionPingResult::func_230792_a_));
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static int func_224868_a(String string) {
        int n = 700;
        long l = 0L;
        Socket socket = null;
        for (int i = 0; i < 5; ++i) {
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(string, 80);
                socket = new Socket();
                long l2 = Ping.func_224865_b();
                socket.connect(inetSocketAddress, 700);
                l += Ping.func_224865_b() - l2;
                Ping.func_224866_a(socket);
                continue;
            } catch (Exception exception) {
                l += 700L;
                continue;
            } finally {
                Ping.func_224866_a(socket);
            }
        }
        return (int)((double)l / 5.0);
    }

    private static void func_224866_a(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
    }

    private static long func_224865_b() {
        return Util.milliTime();
    }

    public static List<RegionPingResult> func_224864_a() {
        return Ping.func_224867_a(Region.values());
    }

    static enum Region {
        US_EAST_1("us-east-1", "ec2.us-east-1.amazonaws.com"),
        US_WEST_2("us-west-2", "ec2.us-west-2.amazonaws.com"),
        US_WEST_1("us-west-1", "ec2.us-west-1.amazonaws.com"),
        EU_WEST_1("eu-west-1", "ec2.eu-west-1.amazonaws.com"),
        AP_SOUTHEAST_1("ap-southeast-1", "ec2.ap-southeast-1.amazonaws.com"),
        AP_SOUTHEAST_2("ap-southeast-2", "ec2.ap-southeast-2.amazonaws.com"),
        AP_NORTHEAST_1("ap-northeast-1", "ec2.ap-northeast-1.amazonaws.com"),
        SA_EAST_1("sa-east-1", "ec2.sa-east-1.amazonaws.com");

        private final String field_224862_i;
        private final String field_224863_j;

        private Region(String string2, String string3) {
            this.field_224862_i = string2;
            this.field_224863_j = string3;
        }
    }
}

