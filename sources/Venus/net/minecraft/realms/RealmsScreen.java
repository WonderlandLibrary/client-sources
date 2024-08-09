/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.IScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsNarratorHelper;

public abstract class RealmsScreen
extends Screen {
    public RealmsScreen() {
        super(NarratorChatListener.EMPTY);
    }

    protected static int func_239562_k_(int n) {
        return 40 + n * 13;
    }

    @Override
    public void tick() {
        for (Widget widget : this.buttons) {
            if (!(widget instanceof IScreen)) continue;
            ((IScreen)((Object)widget)).tick();
        }
    }

    public void func_231411_u_() {
        Stream stream = this.children.stream();
        Objects.requireNonNull(RealmsLabel.class);
        Stream<IGuiEventListener> stream2 = stream.filter(RealmsLabel.class::isInstance);
        Objects.requireNonNull(RealmsLabel.class);
        List<String> list = stream2.map(RealmsLabel.class::cast).map(RealmsLabel::func_231399_a_).collect(Collectors.toList());
        RealmsNarratorHelper.func_239549_a_(list);
    }
}

