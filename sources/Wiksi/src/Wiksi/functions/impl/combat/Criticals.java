/// by Greufs ///

package src.Wiksi.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.Hand;
import src.Wiksi.events.AttackEvent;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;

@FunctionRegister(name = "Criticals", type = Category.Combat)
public class Criticals extends Function {
    private final Minecraft mc = Minecraft.getInstance();

    public Criticals() {
    }

    @Subscribe
    public void onPlayerUpdate(EventUpdate var1) {
        Minecraft var10000 = this.mc;
        if (Minecraft.player != null) {
            var10000 = this.mc;
            if (null != Minecraft.world) {
                return;
            }
        }

    }

    @Subscribe
    public void onPlayerAttack(AttackEvent var1) {
        Minecraft var10000 = this.mc;
        if (Minecraft.player != null) {
            var10000 = this.mc;
            if (Minecraft.world != null) {
                var10000 = this.mc;
                if (Minecraft.player.isOnGround()) {
                    var10000 = this.mc;
                        var10000 = this.mc;
                        if (!Minecraft.player.isInWater()) {
                            var10000 = this.mc;
                            ClientPlayNetHandler var2 = Minecraft.player.connection;
                            Minecraft var10003 = this.mc;
                            double var3 = Minecraft.player.getPosX();
                            Minecraft var10004 = this.mc;
                            double var4 = Minecraft.player.getPosY() + 0.1;
                            Minecraft var10005 = this.mc;
                            var2.sendPacket(new CPlayerPacket.PositionPacket(var3, var4, Minecraft.player.getPosZ(), false));
                            var10000 = this.mc;
                            var2 = Minecraft.player.connection;
                            var10003 = this.mc;
                            var3 = Minecraft.player.getPosX();
                            var10004 = this.mc;
                            var4 = Minecraft.player.getPosY();
                            var10005 = this.mc;
                            var2.sendPacket(new CPlayerPacket.PositionPacket(var3, var4, Minecraft.player.getPosZ(), false));
                            var10000 = this.mc;
                            var2 = Minecraft.player.connection;
                            var10003 = this.mc;
                            var3 = Minecraft.player.getPosX();
                            var10004 = this.mc;
                            var4 = Minecraft.player.getPosY() + 0.01;
                            var10005 = this.mc;
                            var2.sendPacket(new CPlayerPacket.PositionPacket(var3, var4, Minecraft.player.getPosZ(), false));
                            var10000 = this.mc;
                            var2 = Minecraft.player.connection;
                            var10003 = this.mc;
                            var3 = Minecraft.player.getPosX();
                            var10004 = this.mc;
                            var4 = Minecraft.player.getPosY();
                            var10005 = this.mc;
                            var2.sendPacket(new CPlayerPacket.PositionPacket(var3, var4, Minecraft.player.getPosZ(), false));
                            var10000 = this.mc;
                            Minecraft.player.swingArm(Hand.MAIN_HAND);
                        }
                    }
                }

                return;
            }
        }

    }
