/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.HashMap;

public class Windows
extends StoredObject {
    private final HashMap<Short, String> types = new HashMap();
    private final HashMap<Short, Item[]> brewingItems = new HashMap();

    public Windows(UserConnection user) {
        super(user);
    }

    public String get(short windowId) {
        return this.types.get(windowId);
    }

    public void put(short windowId, String type2) {
        this.types.put(windowId, type2);
    }

    public void remove(short windowId) {
        this.types.remove(windowId);
        this.brewingItems.remove(windowId);
    }

    /*
     * Exception decompiling
     */
    public Item[] getBrewingItems(short windowId) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * java.lang.UnsupportedOperationException
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.NewAnonymousArray.getDimSize(NewAnonymousArray.java:142)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.isNewArrayLambda(LambdaRewriter.java:463)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:417)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:175)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:106)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.rewriters.ExpressionRewriterHelper.applyForwards(ExpressionRewriterHelper.java:12)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriterToArgs(AbstractMemberFunctionInvokation.java:101)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriter(AbstractMemberFunctionInvokation.java:88)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:104)
         *     at org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredReturn.rewriteExpressions(StructuredReturn.java:99)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewrite(LambdaRewriter.java:89)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.rewriteLambdas(Op04StructuredStatement.java:1137)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:912)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:538)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at async.DecompilerRunnable.cfrDecompilation(DecompilerRunnable.java:295)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:256)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:26)
         *     at java.util.concurrent.FutureTask.run(FutureTask.java:266)
         *     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         *     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         *     at java.lang.Thread.run(Thread.java:750)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static void updateBrewingStand(UserConnection user, Item blazePowder, short windowId) {
        if (blazePowder != null && blazePowder.identifier() != 377) {
            return;
        }
        int amount = blazePowder == null ? 0 : blazePowder.amount();
        PacketWrapper openWindow = PacketWrapper.create(ClientboundPackets1_8.OPEN_WINDOW, user);
        openWindow.write(Type.UNSIGNED_BYTE, windowId);
        openWindow.write(Type.STRING, "minecraft:brewing_stand");
        Component title = ((TextComponent)((TextComponent)((TextComponent)Component.empty().append(Component.translatable("container.brewing"))).append(Component.text(": ", (TextColor)NamedTextColor.DARK_GRAY))).append(Component.text(amount + " ", (TextColor)NamedTextColor.DARK_RED))).append(Component.translatable("item.blazePowder.name", (TextColor)NamedTextColor.DARK_RED));
        openWindow.write(Type.COMPONENT, GsonComponentSerializer.colorDownsamplingGson().serializeToTree(title));
        openWindow.write(Type.UNSIGNED_BYTE, (short)420);
        PacketUtil.sendPacket(openWindow, Protocol1_8To1_9.class);
        Item[] items = user.get(Windows.class).getBrewingItems(windowId);
        for (int i = 0; i < items.length; ++i) {
            PacketWrapper setSlot = PacketWrapper.create(ClientboundPackets1_8.SET_SLOT, user);
            setSlot.write(Type.UNSIGNED_BYTE, windowId);
            setSlot.write(Type.SHORT, (short)i);
            setSlot.write(Type.ITEM, items[i]);
            PacketUtil.sendPacket(setSlot, Protocol1_8To1_9.class);
        }
    }
}

