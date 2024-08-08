package net.futureclient.client.modules.render;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.render.norender.Listener8;
import net.futureclient.client.modules.render.norender.Listener7;
import net.futureclient.client.modules.render.norender.Listener6;
import net.futureclient.client.modules.render.norender.Listener5;
import net.futureclient.client.modules.render.norender.Listener4;
import net.futureclient.client.modules.render.norender.Listener3;
import net.futureclient.client.modules.render.norender.Listener2;
import net.futureclient.client.modules.render.norender.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.oC;
import net.futureclient.client.R;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class NoRender extends Ea
{
    public Value<Boolean> hurtCamera;
    public Value<Boolean> barriers;
    public Value<Boolean> blindness;
    private R<oC.YA> noItems;
    public Value<Boolean> skylight;
    public Value<Boolean> spawners;
    public Value<Boolean> totemAnimation;
    public Value<Boolean> fire;
    
    public NoRender() {
        super("NoRender", new String[] { "NoRender", "NoRend", "Render", "NoItems" }, false, -15257121, Category.RENDER);
        this.fire = new Value<Boolean>(false, new String[] { "Fire", "NoFire", "nf" });
        this.hurtCamera = new Value<Boolean>(false, new String[] { "HurtCamera", "NoHurtcam", "nh" });
        this.blindness = new Value<Boolean>(true, new String[] { "Blindness", "NoBlindness", "Blind", "nb", "Nausea", "NoNausea" });
        this.totemAnimation = new Value<Boolean>(false, new String[] { "TotemAnimation", "NoTotemAnimation", "NoTotem", "NoTotim", "NoTotimAnimation", "NoTotemAnime", "NoTotimAnime", "NoTot", "nta", "nt" });
        this.skylight = new Value<Boolean>(false, new String[] { "Skylight", "Sky", "Light", "SkylightUpdates", "NoSkylight" });
        this.spawners = new Value<Boolean>(false, new String[] { "Spawners", "Spawner", "Spawn" });
        this.barriers = new Value<Boolean>(false, new String[] { "Barriers", "Barrier", "Bar" });
        this.noItems = new R<oC.YA>(oC.YA.D, new String[] { "NoItems", "items", "item", "ni" });
        this.M(new Value[] { this.noItems, this.fire, this.hurtCamera, this.blindness, this.totemAnimation, this.skylight, this.spawners, this.barriers });
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this), new Listener5(this), new Listener6(this), new Listener7(this), new Listener8(this) });
    }
    
    public static Minecraft getMinecraft() {
        return NoRender.D;
    }
    
    public static Minecraft getMinecraft1() {
        return NoRender.D;
    }
    
    public static Minecraft getMinecraft2() {
        return NoRender.D;
    }
    
    public static Minecraft getMinecraft3() {
        return NoRender.D;
    }
    
    public static Minecraft getMinecraft4() {
        return NoRender.D;
    }
    
    public static R M(final NoRender noRender) {
        return noRender.noItems;
    }
}
