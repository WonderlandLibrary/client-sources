/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S08PacketPlayerPosLook
implements Packet {
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;
    public Set field_179835_f;
    private static final String __OBFID = "CL_00001273";

    public S08PacketPlayerPosLook() {
    }

    public S08PacketPlayerPosLook(double xIn, double yIn, double zIn, float yawIn, float pitchIn, Set p_i45993_9_) {
        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
        this.yaw = yawIn;
        this.pitch = pitchIn;
        this.field_179835_f = p_i45993_9_;
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
        this.yaw = data.readFloat();
        this.pitch = data.readFloat();
        this.field_179835_f = EnumFlags.func_180053_a(data.readUnsignedByte());
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeDouble(this.x);
        data.writeDouble(this.y);
        data.writeDouble(this.z);
        data.writeFloat(this.yaw);
        data.writeFloat(this.pitch);
        data.writeByte(EnumFlags.func_180056_a(this.field_179835_f));
    }

    public void func_180718_a(INetHandlerPlayClient p_180718_1_) {
        p_180718_1_.handlePlayerPosLook(this);
    }

    public double func_148932_c() {
        return this.x;
    }

    public double func_148928_d() {
        return this.y;
    }

    public double func_148933_e() {
        return this.z;
    }

    public float func_148931_f() {
        return this.yaw;
    }

    public float func_148930_g() {
        return this.pitch;
    }

    public Set func_179834_f() {
        return this.field_179835_f;
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.func_180718_a((INetHandlerPlayClient)handler);
    }

    public static enum EnumFlags {
        X("X", 0, 0),
        Y("Y", 1, 1),
        Z("Z", 2, 2),
        Y_ROT("Y_ROT", 3, 3),
        X_ROT("X_ROT", 4, 4);

        private int field_180058_f;
        private static final EnumFlags[] $VALUES;
        private static final String __OBFID = "CL_00002304";

        static {
            $VALUES = new EnumFlags[]{X, Y, Z, Y_ROT, X_ROT};
        }

        private EnumFlags(String p_i45992_1_, int p_i45992_2_, int p_i45992_3_) {
            this.field_180058_f = p_i45992_3_;
        }

        private int func_180055_a() {
            return 1 << this.field_180058_f;
        }

        private boolean func_180054_b(int p_180054_1_) {
            return (p_180054_1_ & this.func_180055_a()) == this.func_180055_a();
        }

        public static Set func_180053_a(int p_180053_0_) {
            EnumSet<EnumFlags> var1 = EnumSet.noneOf(EnumFlags.class);
            for (EnumFlags var5 : EnumFlags.values()) {
                if (!var5.func_180054_b(p_180053_0_)) continue;
                var1.add(var5);
            }
            return var1;
        }

        public static int func_180056_a(Set p_180056_0_) {
            int var1 = 0;
            for (EnumFlags var3 : p_180056_0_) {
                var1 |= var3.func_180055_a();
            }
            return var1;
        }
    }
}

