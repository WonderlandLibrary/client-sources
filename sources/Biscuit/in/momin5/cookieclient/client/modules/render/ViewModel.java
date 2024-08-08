package in.momin5.cookieclient.client.modules.render;

import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.event.events.RenderFirstPersonEvent;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.common.MinecraftForge;

public class ViewModel extends Module {

    public SettingNumber mainRightX = register(new SettingNumber("Main RightX",this,0,-3,0,0.1));
    public SettingNumber mainRightY = register(new SettingNumber("Main RightY",this,0,-3,0,0.1));
    public SettingNumber mainRightZ = register(new SettingNumber("Main RightZ",this,0,-3,0,0.1));

    public SettingNumber mainLeftX = register(new SettingNumber("Main LeftX",this,0,-3,0,0.1));
    public SettingNumber mainLeftY = register(new SettingNumber("Main LeftY",this,0,-3,0,0.1));
    public SettingNumber mainLeftZ = register(new SettingNumber("Main LeftZ",this,0,-3,0,0.1));

    public SettingNumber scaleRightX = register(new SettingNumber("Scale RightX",this,1.0,-5.0,10,0.1));
    public SettingNumber scaleRightY = register(new SettingNumber("Scale RightY",this,1.0,-5.0,10,0.1));
    public SettingNumber scaleRightZ = register(new SettingNumber("Scale RightZ",this,1.0,-5.0,10,0.1));

    public SettingNumber scaleLeftX = register(new SettingNumber("Scale LeftX",this,1.0,-5.0,10,0.1));
    public SettingNumber scaleLeftY = register(new SettingNumber("Scale LeftY",this,1.0,-5.0,10,0.1));
    public SettingNumber scaleLeftZ = register(new SettingNumber("Scale LeftZ",this,1.0,-5.0,10,0.1));

    public SettingBoolean cancelEating = register(new SettingBoolean("CancelEatingRender",this,false));

    public ViewModel() {
        super("ViewModel", Category.RENDER);
    }

    @Override
    public void onEnable() {
        CookieClient.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        CookieClient.EVENT_BUS.unsubscribe(this);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @EventHandler
    private final Listener<RenderFirstPersonEvent> listener = new Listener<>(vent -> {
        if(vent.getHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.translate(mainRightX.value,mainRightY.value,mainRightZ.value);
            GlStateManager.scale(scaleRightX.value,scaleRightY.value,scaleRightZ.value);
        }else if(vent.getHandSide() == EnumHandSide.LEFT) {
            GlStateManager.translate(mainLeftX.value,mainLeftY.value,mainLeftZ.value);
            GlStateManager.scale(scaleLeftX.value,scaleLeftY.value,scaleLeftZ.value);
        }
    });


}
