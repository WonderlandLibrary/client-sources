package com.client.glowclient.modules.server;

import com.client.glowclient.utils.*;
import com.client.glowclient.events.*;
import net.minecraft.init.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.nbt.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.client.glowclient.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import com.client.glowclient.modules.*;

public class RandomBook extends ModuleContainer
{
    public static NumberValue templateDouble;
    public static StringValue templateString;
    public static nB templateString;
    private boolean B;
    public static BooleanValue templateBoolean;
    
    static {
        final String s = "Template";
        final String s2 = "TemplateDouble";
        final String s3 = "Template Description";
        final double n = 0.0;
        RandomBook.templateDouble = ValueFactory.M(s, s2, s3, n, n, n, 0.0);
        RandomBook.templateBoolean = ValueFactory.M("Template", "TemplateBoolean", "Template Description", true);
        RandomBook.templateString = ValueFactory.M("Template", "TemplateString", "Template Description", "TempValue");
        RandomBook.templateString = ValueFactory.M("Template", "TemplateString", "Template Description", "Value1", "Value1", "Value2");
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        try {
            if (this.B) {
                final EntityPlayerSP player;
                final ItemStack heldItemMainhand;
                if ((heldItemMainhand = (player = RandomBook.B.player).getHeldItemMainhand()).getItem() != Items.WRITABLE_BOOK) {
                    qd.D("NoBook");
                }
                final String s = new Random().ints(128, 1112063).map(Bg::M).limit(10500L).mapToObj((IntFunction<?>)Bg::M).collect((Collector<? super Object, ?, String>)Collectors.joining());
                final NBTTagList list = new NBTTagList();
                int n;
                int i = n = 0;
                while (i < 50) {
                    final NBTTagList list2 = list;
                    final String s2 = s;
                    final int n2 = n * 210;
                    final int n3 = (n + 1) * 210;
                    ++n;
                    list2.appendTag((NBTBase)new NBTTagString(s2.substring(n2, n3)));
                    i = n;
                }
                if (heldItemMainhand.hasTagCompound()) {
                    heldItemMainhand.getTagCompound().setTag("pages", (NBTBase)list);
                }
                else {
                    heldItemMainhand.setTagInfo("pages", (NBTBase)list);
                }
                final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                final boolean b = false;
                packetBuffer.writeItemStack(heldItemMainhand);
                final String s3 = "\u001e\u0015.\u0003(\u0013>\u00068\f!\u0019m\u0003?\u0005,\u0014(\u0004m\u0012,\u000e)\u000f @/\u000f\"\u000b";
                player.connection.sendPacket((Packet)new CPacketCustomPayload("MC|BEdit", packetBuffer));
                qd.D(IA.M(s3));
                this.B = b;
            }
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void D() {
        qd.D("Left click a book and quill to fill it with randomized characters");
    }
    
    @SubscribeEvent
    public void M(final MouseEvent mouseEvent) {
        if (RandomBook.B.player.getHeldItemMainhand().getItem() == Items.WRITABLE_BOOK && mouseEvent.getButton() == 0 && mouseEvent.isButtonstate()) {
            this.B = true;
        }
    }
    
    private static String M(final int n) {
        return String.valueOf((char)n);
    }
    
    private void d() throws InterruptedException {
        Thread.sleep(1000L);
    }
    
    private static int M(final int n) {
        if (n < 55296) {
            return n;
        }
        return n + 2048;
    }
    
    public RandomBook() {
        final boolean b = false;
        super(Category.SERVER, "RandomBook", false, -1, "Template Description");
        this.B = b;
    }
}
