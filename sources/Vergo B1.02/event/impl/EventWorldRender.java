package xyz.vergoclient.event.impl;

import net.minecraft.world.World;
import xyz.vergoclient.event.Event;

public class EventWorldRender extends Event {

    public final World world;

    public EventWorldRender(World world) {
        this.world = world;
    }

    public static class Load extends EventWorldRender {
        public Load(World world) {
            super(world);
        }
    }

    public static class Unload extends EventWorldRender {
        public Unload(World world) {
            super(world);
        }
    }

}
