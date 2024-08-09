package src.Wiksi.functions.impl.misc;


import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

@Getter
@Setter
@FunctionRegister(name = "AuctionHelper", type = Category.Misc)
public class AhHelper extends Function {

    public BooleanSetting three = new BooleanSetting("Подсвечивать 3 слота", true );

    public AhHelper(){
        addSettings(three);
    }
    float x = 0;
    float y = 0;
    float x2 = 0;
    float y2 = 0;
    float x3 = 0;
    float y3 = 0;
    @Subscribe
    public void onUpdate(EventUpdate update){
        if(mc.currentScreen instanceof ChestScreen e) {
            if (e.getTitle().getString().contains("Аукцион") || e.getTitle().getString().contains("Поиск:")) {

                Container container = e.getContainer();
                Slot slot1 = null;
                Slot slot2 = null;
                Slot slot3 = null;
                int fsPrice = Integer.MAX_VALUE;
                int medPrice = Integer.MAX_VALUE;
                int thPrice = Integer.MAX_VALUE;

                boolean b = false;
                for (Slot slot : container.inventorySlots) {
                    if (slot.slotNumber > 44) {
                        continue;
                    }
                    int currentPrice = extractPriceFromStack(slot.getStack());
                    if (currentPrice != - 1 && currentPrice < fsPrice) {
                        fsPrice = currentPrice;
                        slot1 = slot;

                    }
                    if(three.get()) {
                        if (currentPrice != - 1 && currentPrice < medPrice && currentPrice > fsPrice) {
                            medPrice = currentPrice;
                            slot2 = slot;
                        }
                        if (currentPrice != - 1 && currentPrice < thPrice && currentPrice > medPrice) {
                            thPrice = currentPrice;
                            slot3 = slot;
                        }
                    } else {
                        setX2(0);
                        setX3(0);
                    }

                }

                if (slot1 != null) {
                    setX(slot1.xPos);
                    setY(slot1.yPos);
                }
                if (slot2 != null) {
                    setX2(slot2.xPos);
                    setY2(slot2.yPos);
                }
                if (slot3 != null) {
                    setX3(slot3.xPos);
                    setY3(slot3.yPos);
                }
            }
            else {
                setX(0);
                setX2(0);
                setX3(0);
            }
        }
        else{
            setX(0);
            setX2(0);
            setX3(0);
        }
    }

    protected int extractPriceFromStack(ItemStack stack) {
        CompoundNBT tag = stack.getTag();

        if (tag != null && tag.contains("display", 10)) {
            CompoundNBT display = tag.getCompound("display");

            if (display.contains("Lore", 9)) {
                ListNBT lore = display.getList("Lore", 8);

                for (int j = 0; j < lore.size(); ++j) {
                    JsonObject object = JsonParser.parseString(lore.getString(j)).getAsJsonObject();

                    if (object.has("extra")) {
                        JsonArray array = object.getAsJsonArray("extra");

                        if (array.size() > 2) {
                            JsonObject title = array.get(1).getAsJsonObject();

                            if (title.get("text").getAsString().trim().toLowerCase().contains("ценa")) {
                                String line = array.get(2).getAsJsonObject().get("text").getAsString().trim().substring(1).replaceAll(" ", "");

                                return Integer.parseInt(line);
                            }
                        }
                    }
                }
            }
        }

        return -1;
    }
}

