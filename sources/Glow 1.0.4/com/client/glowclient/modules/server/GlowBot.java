package com.client.glowclient.modules.server;

import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import java.awt.*;
import net.minecraft.init.*;
import com.client.glowclient.*;
import com.client.glowclient.utils.*;

public class GlowBot extends ModuleContainer
{
    private static double K;
    public static StringValue prefix;
    private static BlockPos k;
    private static boolean H;
    private static boolean f;
    private static boolean M;
    public static BooleanValue private;
    private static boolean d;
    private static boolean L;
    private static double A;
    private static IBlockState B;
    private static boolean b;
    
    @SubscribeEvent
    public void M(final EventChat eventChat) {
        if (this.M(eventChat.getSender().D())) {
            if (eventChat.getMessage().toLowerCase().startsWith(new StringBuilder().insert(0, GlowBot.prefix.e()).append("help").toString())) {
                Wrapper.mc.player.sendChatMessage(new StringBuilder().insert(0, "(Prefix = ").append(GlowBot.prefix.e()).append(") List of commands: SetYaw SetPitch Interact Punch Break").toString());
            }
            final String replace;
            if (this.M(eventChat, "setpitch") && sd.D(replace = eventChat.getMessage().toLowerCase().replace(new StringBuilder().insert(0, GlowBot.prefix.e()).append("setpitch ").toString(), ""))) {
                final boolean m = true;
                GlowBot.A = Double.parseDouble(replace);
                GlowBot.M = m;
            }
            final String replace2;
            if (this.M(eventChat, "setyaw ") && sd.D(replace2 = eventChat.getMessage().toLowerCase().replace(new StringBuilder().insert(0, GlowBot.prefix.e()).append("setyaw ").toString(), ""))) {
                final boolean l = true;
                GlowBot.K = Double.parseDouble(replace2);
                GlowBot.L = l;
            }
            if (this.M(eventChat, "interact")) {
                GlowBot.d = true;
            }
            if (this.M(eventChat, "punch")) {
                GlowBot.f = true;
            }
            if (this.M(eventChat, "break")) {
                GlowBot.k = Wrapper.mc.objectMouseOver.getBlockPos();
                GlowBot.b = true;
            }
            if (this.M(eventChat, "jump")) {
                GlowBot.H = true;
            }
        }
    }
    
    public static double M(final Entity entity, final BlockPos blockPos) {
        final double n = entity.posX - blockPos.getX();
        final double n2 = entity.posY - blockPos.getY();
        final double n3 = entity.posZ - blockPos.getZ();
        final double n4 = n;
        final double n5 = n4 * n4;
        final double n6 = n2;
        final double n7 = n5 + n6 * n6;
        final double n8 = n3;
        return Math.sqrt(n7 + n8 * n8);
    }
    
    private boolean M(final String s) {
        return !GlowBot.private.M() || Va.M().M(s);
    }
    
    public GlowBot() {
        super(Category.SERVER, "GlowBot", false, -1, "WIP Multi Media bot");
    }
    
    @Override
    public boolean A() {
        return false;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        try {
            final Robot robot = new Robot();
            if (GlowBot.M) {
                Wrapper.mc.player.rotationPitch = (float)GlowBot.A;
                GlowBot.M = false;
            }
            if (GlowBot.L) {
                Wrapper.mc.player.rotationYaw = (float)GlowBot.K;
                GlowBot.L = false;
            }
            if (GlowBot.d) {
                final boolean d = false;
                final int n = 4;
                final Robot robot2 = robot;
                robot2.mousePress(4);
                robot2.mouseRelease(n);
                GlowBot.d = d;
            }
            if (GlowBot.f) {
                final boolean f = false;
                final int n2 = 16;
                final Robot robot3 = robot;
                robot3.mousePress(16);
                robot3.mouseRelease(n2);
                GlowBot.f = f;
            }
            if (GlowBot.b) {
                if (Wrapper.mc.world.getBlockState(GlowBot.k).getBlock() != Blocks.AIR && Wrapper.mc.objectMouseOver != null && M((Entity)Wrapper.mc.player, GlowBot.k) < 5.0) {
                    fa.D(GlowBot.k);
                    GlowBot.B = Wrapper.mc.world.getBlockState(GlowBot.k);
                }
                else {
                    GlowBot.b = false;
                }
                if (GlowBot.B != Wrapper.mc.world.getBlockState(GlowBot.k) && Wrapper.mc.world.getBlockState(GlowBot.k).getBlock() == Blocks.AIR) {
                    GlowBot.b = false;
                }
            }
            if (GlowBot.H) {
                if (!this.G.hasBeenSet()) {
                    this.G.reset();
                }
                if (this.G.delay(0.0)) {
                    robot.keyPress(32);
                }
                if (this.G.delay(100.0)) {
                    final boolean h = false;
                    robot.keyRelease(32);
                    this.G.destroy();
                    GlowBot.H = h;
                }
            }
        }
        catch (Exception ex) {}
    }
    
    private boolean M(final EventChat eventChat, final String s) {
        return eventChat.getMessage().toLowerCase().startsWith(new StringBuilder().insert(0, GlowBot.prefix.e()).append(s).toString());
    }
    
    static {
        GlowBot.private = ValueFactory.M("GlowBot", "Private", "Restrict access so only friended players can use it", true);
        GlowBot.prefix = ValueFactory.M("GlowBot", "Prefix", "Command Prefix", "+");
        GlowBot.M = false;
        GlowBot.A = 0.0;
        GlowBot.L = false;
        GlowBot.K = 0.0;
        GlowBot.d = false;
        GlowBot.f = false;
        GlowBot.b = false;
        GlowBot.H = false;
    }
}
