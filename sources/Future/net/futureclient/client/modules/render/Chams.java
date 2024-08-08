package net.futureclient.client.modules.render;

import net.futureclient.client.WI;
import net.minecraft.entity.player.EntityPlayer;
import net.futureclient.client.eD;
import net.futureclient.client.pg;
import net.futureclient.client.modules.combat.AntiBots;
import net.minecraft.entity.Entity;
import net.futureclient.client.qF;
import net.futureclient.client.modules.render.chams.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class Chams extends Ea
{
    private Value<Boolean> players;
    private Value<Boolean> self;
    private Value<Boolean> texture;
    private Value<Boolean> animals;
    private Value<Boolean> monsters;
    private Value<Boolean> xqz;
    
    public Chams() {
        super("Chams", new String[] { "Chams", "Cham", "Chammies" }, true, -14510046, Category.RENDER);
        this.self = new Value<Boolean>(false, new String[] { "Self", "self", "localplayer", "theplayer" });
        this.players = new Value<Boolean>(true, new String[] { "Players", "player", "human", "P" });
        this.monsters = new Value<Boolean>(true, new String[] { "Monsters", "Hostiles", "Mobs", "monstas", "H" });
        this.animals = new Value<Boolean>(true, new String[] { "Animals", "Neutrals", "ani", "animal", "N" });
        this.texture = new Value<Boolean>(false, new String[] { "Texture", "texture", "skin" });
        this.xqz = new Value<Boolean>(false, new String[] { "XQZ", "xqz", "throughwalls", "walls" });
        this.M(new Value[] { this.self, this.players, this.monsters, this.animals, this.texture, this.xqz });
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Value e(final Chams chams) {
        return chams.texture;
    }
    
    public static Value M(final Chams chams) {
        return chams.xqz;
    }
    
    public static void M(final Chams chams, final qF.EF qf) {
        chams.M(qf);
    }
    
    private boolean M(final Entity entity) {
        final AntiBots antiBots = (AntiBots)pg.M().M().M((Class)eD.class);
        return entity != null && (this.self.M() || !entity.equals((Object)Chams.D.player)) && ((this.players.M() && entity instanceof EntityPlayer && (!antiBots.M() || !antiBots.K.containsKey(entity.getEntityId()))) || (this.monsters.M() && (WI.C(entity) || WI.e(entity))) || (this.animals.M() && (WI.i(entity) || WI.g(entity))));
    }
    
    public static boolean M(final Chams chams, final Entity entity) {
        return chams.M(entity);
    }
    
    private void M(final qF.EF qf) {
        qf.M().render((Entity)qf.M(), qf.i(), qf.M(), qf.C(), qf.b(), qf.B(), qf.e());
    }
}
