package net.futureclient.client.modules.world;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.world.noglitchblocks.Listener2;
import net.futureclient.client.modules.world.noglitchblocks.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public final class NoGlitchBlocks extends Ea
{
    private final Value<Boolean> place;
    private final Value<Boolean> destroy;
    
    public NoGlitchBlocks() {
        super("NoGlitchBlocks", new String[] { "NoGlitchBlocks", "NoGlitchyBlocks", "glitchyblocks", "noglitchyblocks", "ngb", "antidesync", "nodesync" }, true, -2525659, Category.WORLD);
        this.place = new Value<Boolean>(true, new String[] { "Place" });
        this.destroy = new Value<Boolean>(true, new String[] { "Destroy" });
        this.M(new Value[] { this.place, this.destroy });
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public static Value e(final NoGlitchBlocks noGlitchBlocks) {
        return noGlitchBlocks.place;
    }
    
    public static Value M(final NoGlitchBlocks noGlitchBlocks) {
        return noGlitchBlocks.destroy;
    }
    
    public static Minecraft getMinecraft() {
        return NoGlitchBlocks.D;
    }
}
