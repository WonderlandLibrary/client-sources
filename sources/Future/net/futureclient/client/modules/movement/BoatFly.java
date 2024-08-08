package net.futureclient.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.movement.boatfly.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class BoatFly extends Ea
{
    public NumberValue upSpeed;
    private Value<Boolean> fixYaw;
    public NumberValue downSpeed;
    
    public BoatFly() {
        super("BoatFly", new String[] { "BoatFly", "HorseFly" }, true, -4545351, Category.MOVEMENT);
        this.upSpeed = new NumberValue(2.0f, 0.1f, 10.0f, new String[] { "UpSpeed", "FlyUpSpeed", "UpSped", "Up" });
        this.downSpeed = new NumberValue(0.001f, 0.0f, 1.0f, new String[] { "DownSpeed", "DownSped", "Down" });
        this.fixYaw = new Value<Boolean>(true, new String[] { "FixYaw", "FixAngles", "YawFix", "AngleFix" });
        this.M(new Value[] { this.fixYaw, this.upSpeed, this.downSpeed });
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return BoatFly.D;
    }
    
    public static Minecraft getMinecraft1() {
        return BoatFly.D;
    }
    
    public static Minecraft getMinecraft2() {
        return BoatFly.D;
    }
    
    public static Minecraft getMinecraft3() {
        return BoatFly.D;
    }
    
    public static Minecraft getMinecraft4() {
        return BoatFly.D;
    }
    
    public static Minecraft getMinecraft5() {
        return BoatFly.D;
    }
    
    public static Value M(final BoatFly boatFly) {
        return boatFly.fixYaw;
    }
    
    public static Minecraft getMinecraft6() {
        return BoatFly.D;
    }
    
    public static Minecraft getMinecraft7() {
        return BoatFly.D;
    }
}
