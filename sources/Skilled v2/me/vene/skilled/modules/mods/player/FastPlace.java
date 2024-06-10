 package me.vene.skilled.modules.mods.player;
 
 import java.lang.reflect.Field;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.item.ItemBlock;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class FastPlace extends Module
{
    private Field delayField;
    private Minecraft mc;
    private NumberValue delayValue;
    private BooleanValue onlyBlocks;
    
    public FastPlace() {
        super(StringRegistry.register("FastPlace"), 0, Category.P);
        this.mc = Minecraft.getMinecraft();
        this.delayValue = new NumberValue(StringRegistry.register("Delay"), 4.0, 0.0, 5.0);
        this.onlyBlocks = new BooleanValue(StringRegistry.register("Only Blocks"), false);
        this.addValue(this.delayValue);
        this.addOption(this.onlyBlocks);
        try {
            this.delayField = this.mc.getClass().getDeclaredField(new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '1', '4', '6', '7', '_', 'a', 'c' }));
        }
        catch (Exception e) {
            try {
                this.delayField = this.mc.getClass().getDeclaredField(new String(new char[] { 'r', 'i', 'g', 'h', 't', 'C', 'l', 'i', 'c', 'k', 'D', 'e', 'l', 'a', 'y', 'T', 'i', 'm', 'e', 'r' }));
            }
            catch (Exception ex) {}
        }
        if (this.delayField != null) {
            this.delayField.setAccessible(true);
        }
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        if (this.mc.theWorld == null) {
            return;
        }
        if (this.mc.thePlayer == null) {
            return;
        }
        try {
            if (this.onlyBlocks.getState() && (this.mc.thePlayer.getCurrentEquippedItem() == null || !(this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock))) {
                return;
            }
            try {
                if (this.delayField.getInt(Minecraft.getMinecraft()) > this.delayValue.getValue()) {
                    this.delayField.set(Minecraft.getMinecraft(), (int)this.delayValue.getValue());
                }
            }
            catch (IllegalAccessException ex) {}
        }
        catch (Exception ex2) {}
    }
}
