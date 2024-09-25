/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.utils.PacketUtil;
import java.util.HashMap;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.type.Type;

public class Windows
extends StoredObject {
    private HashMap<Short, String> types = new HashMap();
    private HashMap<Short, Item[]> brewingItems = new HashMap();

    public Windows(UserConnection user) {
        super(user);
    }

    public String get(short windowId) {
        return this.types.get(windowId);
    }

    public void put(short windowId, String type) {
        this.types.put(windowId, type);
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
         * java.lang.UnsupportedOperationException
         * org.benf.cfr.reader.bytecode.analysis.parse.expression.NewAnonymousArray.getDimSize(NewAnonymousArray.java:136)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.isNewArrayLambda(LambdaRewriter.java:454)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:408)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:166)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:104)
         * org.benf.cfr.reader.bytecode.analysis.parse.rewriters.ExpressionRewriterHelper.applyForwards(ExpressionRewriterHelper.java:12)
         * org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriterToArgs(AbstractMemberFunctionInvokation.java:95)
         * org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriter(AbstractMemberFunctionInvokation.java:82)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:102)
         * org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredReturn.rewriteExpressions(StructuredReturn.java:91)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewrite(LambdaRewriter.java:87)
         * org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.rewriteLambdas(Op04StructuredStatement.java:1125)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:868)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:258)
         * org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:192)
         * org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         * org.benf.cfr.reader.entities.Method.analyse(Method.java:521)
         * org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
         * org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:922)
         * org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:253)
         * org.benf.cfr.reader.Driver.doJar(Driver.java:135)
         * org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
         * org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException(Decompilation failed);
    }

    public static void updateBrewingStand(UserConnection user, Item blazePowder, short windowId) {
        if (blazePowder != null && blazePowder.getIdentifier() != 377) {
            return;
        }
        byte amount = blazePowder == null ? (byte)0 : blazePowder.getAmount();
        PacketWrapper openWindow = new PacketWrapper(45, null, user);
        openWindow.write(Type.UNSIGNED_BYTE, windowId);
        openWindow.write(Type.STRING, "minecraft:brewing_stand");
        openWindow.write(Type.STRING, "[{\"translate\":\"container.brewing\"},{\"text\":\": \",\"color\":\"dark_gray\"},{\"text\":\"\u00a74" + amount + " \",\"color\":\"dark_red\"},{\"translate\":\"item.blazePowder.name\",\"color\":\"dark_red\"}]");
        openWindow.write(Type.UNSIGNED_BYTE, (short)420);
        PacketUtil.sendPacket(openWindow, Protocol1_8TO1_9.class);
        Item[] items = user.get(Windows.class).getBrewingItems(windowId);
        for (int i = 0; i < items.length; ++i) {
            PacketWrapper setSlot = new PacketWrapper(47, null, user);
            setSlot.write(Type.BYTE, (byte)windowId);
            setSlot.write(Type.SHORT, (short)i);
            setSlot.write(Type.ITEM, items[i]);
            PacketUtil.sendPacket(setSlot, Protocol1_8TO1_9.class);
        }
    }
}

