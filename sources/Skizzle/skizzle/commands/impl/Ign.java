/*
 * Decompiled with CFR 0.150.
 */
package skizzle.commands.impl;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import skizzle.commands.Command;

public class Ign
extends Command {
    @Override
    public void onCommand(String[] Nigga, String Nigga2) {
        Ign Nigga3;
        StringSelection Nigga4 = new StringSelection(Nigga3.mc.thePlayer.getName());
        Clipboard Nigga5 = Toolkit.getDefaultToolkit().getSystemClipboard();
        Nigga5.setContents(Nigga4, Nigga4);
        Nigga3.mc.thePlayer.skizzleMessage(Qprot0.0("\u5df2\u719c\u6688\u45ba\u117e\ud81b\u8c2c\u31f2\ub519\uba14\u5230\uaf0a\udf3f\u905f\u6578\uc1a8\u42a9\uebd0\uf511\u0e1c\ua47b\u01cf\u4de5\u0a32\ud6fe\ue0bc\u2f5e\u8899\ua043\u9bda\ubc45\u8817\ud04c\u4f35\u0b92\u7f8e\uddfe"));
    }

    public static {
        throw throwable;
    }

    public Ign() {
        super(Qprot0.0("\u5d9d\u71cc\u66c6"), Qprot0.0("\u5d97\u71c4\u66d8\ua7ed\u7f4e\ud81d\u8c6f\u31e8\u570d\ud414\u5231\uaf4c\udf03\u726a\u0b5c\uc1f1\u42fd\uebdc\u1740\u6013\ua47d\u01df\u4df3\ue82c\ub8ef\ue0bf\u2f17\u888a\u4253\uf5da\ubc54\u8807\ud047"), Qprot0.0("\u5dbd\u71cc\u66c6"), Qprot0.0("\u5dba\u71ca\u66c5\ua7e1"));
        Ign Nigga;
    }
}

