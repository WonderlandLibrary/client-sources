package net.minecraft.data;

import com.google.gson.JsonElement;
import net.minecraft.block.Block;

import java.util.function.Supplier;

public interface IFinishedBlockState extends Supplier<JsonElement>
{
    Block func_230524_a_();
}
