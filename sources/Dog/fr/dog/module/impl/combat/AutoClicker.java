package fr.dog.module.impl.combat;

import fr.dog.Dog;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.util.player.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;

public class AutoClicker extends Module {

    public AutoClicker() {
        super("AutoClicker", ModuleCategory.COMBAT);
        this.registerProperties(teams, onlyOnSword);
    }
    public BooleanProperty teams = BooleanProperty.newInstance("Teams", true);
    public BooleanProperty onlyOnSword = BooleanProperty.newInstance("OnlyOnSword", true);



    @SubscribeEvent
    private void onUpdate(PlayerTickEvent event){
        if(mc.pointedEntity instanceof EntityPlayer entityPlayer) {
            if (PlayerUtil.isEntityTeamSameAsPlayer(entityPlayer) && teams.getValue()) {
                return;
            }
            if(Dog.getInstance().getModuleManager().getModule(AntiBot.class).isBot(entityPlayer)){
                return;
            }
            if (!mc.thePlayer.isBlocking()) {
                mc.clickMouse();
            }
        }
    }
}
