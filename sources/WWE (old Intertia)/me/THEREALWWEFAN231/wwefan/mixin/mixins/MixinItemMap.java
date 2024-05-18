package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.client.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.exploits.*;
import wwe.*;
import net.minecraft.nbt.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { ItemMap.class }, priority = 9999)
public class MixinItemMap
{
    public MixinItemMap() {
        super();
    }
    
    @Inject(method = { "addInformation" }, at = { @At("RETURN") }, cancellable = true)
    public void addInformation(final ItemStack itemStack, @Nullable final World world, final List<String> list, final ITooltipFlag tooltipFlag, final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<TreasureMapCoords>)ceSU.class).IuiN() && itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("Decorations", 9)) {
            final NBTTagList tagList = itemStack.getTagCompound().getTagList("Decorations", 10);
            for (int i = 0; i < tagList.tagCount(); ++i) {
                final NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
                list.add(compoundTag.getDouble("x") + " : " + compoundTag.getDouble("z"));
            }
        }
    }
}
