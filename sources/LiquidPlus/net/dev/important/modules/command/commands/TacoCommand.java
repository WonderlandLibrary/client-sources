/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.util.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package net.dev.important.modules.command.commands;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.dev.important.Client;
import net.dev.important.event.EventTarget;
import net.dev.important.event.Listenable;
import net.dev.important.event.Render2DEvent;
import net.dev.important.event.UpdateEvent;
import net.dev.important.modules.command.Command;
import net.dev.important.utils.ClientUtils;
import net.dev.important.utils.MinecraftInstance;
import net.dev.important.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u001b\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\tH\u0016\u00a2\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\rH\u0016J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0018H\u0007J!\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00110\u001a2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\tH\u0016\u00a2\u0006\u0002\u0010\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2={"Lnet/dev/important/modules/command/commands/TacoCommand;", "Lnet/dev/important/modules/command/Command;", "Lnet/dev/important/event/Listenable;", "()V", "image", "", "running", "", "tacoTextures", "", "Lnet/minecraft/util/ResourceLocation;", "[Lnet/minecraft/util/ResourceLocation;", "toggle", "", "execute", "", "args", "", "([Ljava/lang/String;)V", "handleEvents", "onRender2D", "event", "Lnet/dev/important/event/Render2DEvent;", "onUpdate", "Lnet/dev/important/event/UpdateEvent;", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"})
public final class TacoCommand
extends Command
implements Listenable {
    private boolean toggle;
    private int image;
    private float running;
    @NotNull
    private final ResourceLocation[] tacoTextures;

    public TacoCommand() {
        boolean $i$f$emptyArray = false;
        super("taco", new String[0]);
        ResourceLocation[] resourceLocationArray = new ResourceLocation[]{new ResourceLocation("liquidplus/taco/1.png"), new ResourceLocation("liquidplus/taco/2.png"), new ResourceLocation("liquidplus/taco/3.png"), new ResourceLocation("liquidplus/taco/4.png"), new ResourceLocation("liquidplus/taco/5.png"), new ResourceLocation("liquidplus/taco/6.png"), new ResourceLocation("liquidplus/taco/7.png"), new ResourceLocation("liquidplus/taco/8.png"), new ResourceLocation("liquidplus/taco/9.png"), new ResourceLocation("liquidplus/taco/10.png"), new ResourceLocation("liquidplus/taco/11.png"), new ResourceLocation("liquidplus/taco/12.png")};
        this.tacoTextures = resourceLocationArray;
        Client.INSTANCE.getEventManager().registerListener(this);
    }

    @Override
    public void execute(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        this.toggle = !this.toggle;
        ClientUtils.displayChatMessage(this.toggle ? "\u00a7aTACO TACO TACO. :)" : "\u00a7cYou made the little taco sad! :(");
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.toggle) {
            return;
        }
        this.running += 0.15f * (float)RenderUtils.deltaTime;
        ScaledResolution scaledResolution = new ScaledResolution(MinecraftInstance.mc);
        RenderUtils.drawImage(this.tacoTextures[this.image], (int)this.running, scaledResolution.func_78328_b() - 60, 64, 32);
        if ((float)scaledResolution.func_78326_a() <= this.running) {
            this.running = -64.0f;
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.toggle) {
            this.image = 0;
            return;
        }
        int n = this.image;
        this.image = n + 1;
        if (this.image >= this.tacoTextures.length) {
            this.image = 0;
        }
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    @Override
    @NotNull
    public List<String> tabComplete(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        return CollectionsKt.listOf("TACO");
    }
}

