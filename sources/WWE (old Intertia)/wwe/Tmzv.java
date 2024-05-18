package wwe;

import wwe.utils.*;
import net.minecraft.init.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.nbt.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.command.*;

public class Tmzv extends HAxG
{
    public Tmzv() {
        super("randombook");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        if (Wrapper.mc.player.getHeldItemMainhand().getItem() != Items.WRITABLE_BOOK) {
            HAxG.TuzD("You have to hold a writable book");
            return;
        }
        if (array.length < 1) {
            HAxG.TuzD(this.ZlRY());
            return;
        }
        int ibdf;
        if (array.length > 1) {
            ibdf = iBDF(array[1], 1, 50);
            if (Tmzv.\u2009\u2004\u2001\u200a\u2006\u2002\u2003) {
                throw null;
            }
        }
        else {
            ibdf = 50;
        }
        final int n = ibdf;
        Random random;
        if (array.length > 2) {
            random = new Random(Long.parseLong(array[2]));
            if (Tmzv.\u2009\u2004\u2001\u200a\u2006\u2002\u2003) {
                throw null;
            }
        }
        else {
            random = new Random();
        }
        final Random random2 = random;
        final String s2 = array[0];
        int n2 = -1;
        switch (s2.hashCode()) {
            case 3143043: {
                if (!s2.equals("fill")) {
                    break;
                }
                n2 = 0;
                if (Tmzv.\u2009\u2004\u2001\u200a\u2006\u2002\u2003) {
                    throw null;
                }
                break;
            }
            case -938285885: {
                if (!s2.equals("random")) {
                    break;
                }
                n2 = 1;
                if (Tmzv.\u2009\u2004\u2001\u200a\u2006\u2002\u2003) {
                    throw null;
                }
                break;
            }
            case 93106001: {
                if (s2.equals("ascii")) {
                    n2 = 2;
                    break;
                }
                break;
            }
        }
        IntStream intStream = null;
        switch (n2) {
            case 0: {
                intStream = IntStream.generate(Tmzv::CMtu);
                if (Tmzv.\u2009\u2004\u2001\u200a\u2006\u2002\u2003) {
                    throw null;
                }
                break;
            }
            case 1: {
                intStream = random2.ints(128, 1112063).map(Tmzv::JYwF);
                if (Tmzv.\u2009\u2004\u2001\u200a\u2006\u2002\u2003) {
                    throw null;
                }
                break;
            }
            case 2: {
                intStream = random2.ints(32, 127);
                if (Tmzv.\u2009\u2004\u2001\u200a\u2006\u2002\u2003) {
                    throw null;
                }
                break;
            }
            default: {
                HAxG.TuzD("Either got to pick, fill, random or ascii");
                return;
            }
        }
        final String s3 = intStream.limit(0x45CD60DFCD806DACL ^ 0x45CD60DFCD8044A8L).mapToObj((IntFunction<?>)Tmzv::dgZm).collect((Collector<? super Object, ?, String>)Collectors.joining());
        final NBTTagList list = new NBTTagList();
        int i = 0;
        while (i < n) {
            list.appendTag((NBTBase)new NBTTagString(s3.substring(i * 210, (i + 1) * 210)));
            ++i;
            if (Tmzv.\u2009\u2004\u2001\u200a\u2006\u2002\u2003) {
                throw null;
            }
        }
        if (Wrapper.mc.player.getHeldItemMainhand().hasTagCompound()) {
            Wrapper.mc.player.getHeldItemMainhand().getTagCompound().setTag("pages", (NBTBase)list);
            if (Tmzv.\u2009\u2004\u2001\u200a\u2006\u2002\u2003) {
                throw null;
            }
        }
        else {
            Wrapper.mc.player.getHeldItemMainhand().setTagInfo("pages", (NBTBase)list);
        }
        final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeItemStack(Wrapper.mc.player.getHeldItemMainhand());
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketCustomPayload("MC|BEdit", packetBuffer));
        HAxG.TuzD("Successfully edited your book");
    }
    
    @Override
    public String UiIp() {
        return "Puts random unicode characters in the book your holding";
    }
    
    @Override
    public String ZlRY() {
        return "randombook fill/random/ascii howManyPages";
    }
    
    public static int iBDF(final String s, final int n, final int n2) {
        int int1 = 0;
        try {
            int1 = CommandBase.parseInt(s);
            if (Tmzv.\u2009\u2004\u2001\u200a\u2006\u2002\u2003) {
                throw null;
            }
        }
        catch (NumberInvalidException ex) {
            ex.printStackTrace();
        }
        if (int1 < n) {
            return n;
        }
        if (int1 > n2) {
            return n2;
        }
        return int1;
    }
    
    private static String dgZm(final int n) {
        return String.valueOf((char)n);
    }
    
    private static int JYwF(final int n) {
        int n2;
        if (n < 55296) {
            n2 = n;
            if (Tmzv.\u2009\u2004\u2001\u200a\u2006\u2002\u2003) {
                throw null;
            }
        }
        else {
            n2 = n + 2048;
        }
        return n2;
    }
    
    private static int CMtu() {
        return 1114111;
    }
}
