package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Comparator;
import com.google.common.collect.Lists;
import java.util.List;

public class CraftingManager
{
    private static final CraftingManager HorizonCode_Horizon_È;
    private final List Â;
    private static final String Ý = "CL_00000090";
    
    static {
        HorizonCode_Horizon_È = new CraftingManager();
    }
    
    public static CraftingManager HorizonCode_Horizon_È() {
        return CraftingManager.HorizonCode_Horizon_È;
    }
    
    private CraftingManager() {
        this.Â = Lists.newArrayList();
        new RecipesTools().HorizonCode_Horizon_È(this);
        new RecipesWeapons().HorizonCode_Horizon_È(this);
        new RecipesIngots().HorizonCode_Horizon_È(this);
        new RecipesFood().HorizonCode_Horizon_È(this);
        new RecipesCrafting().HorizonCode_Horizon_È(this);
        new RecipesArmor().HorizonCode_Horizon_È(this);
        new RecipesDyes().HorizonCode_Horizon_È(this);
        this.Â.add(new RecipesArmorDyes());
        this.Â.add(new RecipeBookCloning());
        this.Â.add(new RecipesMapCloning());
        this.Â.add(new RecipesMapExtending());
        this.Â.add(new RecipeFireworks());
        this.Â.add(new RecipeRepairItem());
        new RecipesBanners().HorizonCode_Horizon_È(this);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ˆà¢, 3), "###", '#', Items.¥Ï);
        this.Â(new ItemStack(Items.Ñ¢Ç, 1), Items.ˆà¢, Items.ˆà¢, Items.ˆà¢, Items.£áŒŠá);
        this.Â(new ItemStack(Items.ŒÓ, 1), Items.Ñ¢Ç, new ItemStack(Items.áŒŠÔ, 1, EnumDyeColor.£à.Ý()), Items.ÇŽÕ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.¥É, 3), "W#W", "W#W", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÂµÕ, 3), "W#W", "W#W", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Ý.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.£ÇªÓ, 3), "W#W", "W#W", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Â.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Š, 3), "W#W", "W#W", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ñ¢Ó, 3), "W#W", "W#W", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, 4 + BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ø­Ñ¢á€, 3), "W#W", "W#W", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, 4 + BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ï­Ó, 6, BlockWall.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()), "###", "###", '#', Blocks.Ó);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ï­Ó, 6, BlockWall.HorizonCode_Horizon_È.Â.Â()), "###", "###", '#', Blocks.áˆºáˆºÈ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ñ¢ÇŽÏ, 6), "###", "###", '#', Blocks.µÂ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ŠáˆºÂ, 1), "#W#", "#W#", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ŒÂ, 1), "#W#", "#W#", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Ý.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ø­Ñ¢Ï­Ø­áˆº, 1), "#W#", "#W#", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Â.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ï­Ï, 1), "#W#", "#W#", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ˆÐƒØ, 1), "#W#", "#W#", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, 4 + BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ŠØ, 1), "#W#", "#W#", '#', Items.áŒŠà, 'W', new ItemStack(Blocks.à, 1, 4 + BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ðƒà, 1), "###", "#X#", "###", '#', Blocks.à, 'X', Items.áŒŠÆ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.áˆºÕ, 2), "~~ ", "~O ", "  ~", '~', Items.ˆá, 'O', Items.£É);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Çªà¢, 1), "###", "#X#", "###", '#', Blocks.à, 'X', Items.ÇŽá);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ï­à, 1), "###", "XXX", "###", '#', Blocks.à, 'X', Items.Ñ¢Ç);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ˆà¢, 1), "##", "##", '#', Items.Ñ¢à);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.áŒŠá€, 6), "###", '#', Blocks.ˆà¢);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.£É, 1), "##", "##", '#', Items.áŒŠá€);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ä, 1), "##", "##", '#', Items.Šà);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.£Ø­à, 1), "##", "##", '#', Items.Ø­Ñ¢á€);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ø­È, 1), "##", "##", '#', Items.ÇªÅ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ŠÂµà, 1), "##", "##", '#', Items.ˆá);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ñ¢Â, 1), "X#X", "#X#", "X#X", 'X', Items.É, '#', Blocks.£á);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ø­Âµ, 6, BlockStoneSlab.HorizonCode_Horizon_È.Ø­áŒŠá.Â()), "###", '#', Blocks.Ó);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ø­Âµ, 6, BlockStoneSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()), "###", '#', new ItemStack(Blocks.Ý, BlockStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ø­Âµ, 6, BlockStoneSlab.HorizonCode_Horizon_È.Â.Â()), "###", '#', Blocks.ŒÏ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ø­Âµ, 6, BlockStoneSlab.HorizonCode_Horizon_È.Âµá€.Â()), "###", '#', Blocks.Ä);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ø­Âµ, 6, BlockStoneSlab.HorizonCode_Horizon_È.Ó.Â()), "###", '#', Blocks.£áƒ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ø­Âµ, 6, BlockStoneSlab.HorizonCode_Horizon_È.à.Â()), "###", '#', Blocks.µÂ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ø­Âµ, 6, BlockStoneSlab.HorizonCode_Horizon_È.Ø.Â()), "###", '#', Blocks.Ø­È);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.µØ, 6, BlockStoneSlabNew.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()), "###", '#', Blocks.áˆºÛ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇŽÊ, 6, 0), "###", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇŽÊ, 6, BlockPlanks.HorizonCode_Horizon_È.Ý.Â()), "###", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Ý.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇŽÊ, 6, BlockPlanks.HorizonCode_Horizon_È.Â.Â()), "###", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Â.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇŽÊ, 6, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â()), "###", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇŽÊ, 6, 4 + BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4), "###", '#', new ItemStack(Blocks.à, 1, 4 + BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇŽÊ, 6, 4 + BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4), "###", '#', new ItemStack(Blocks.à, 1, 4 + BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.áŒŠÏ, 3), "# #", "###", "# #", '#', Items.áŒŠà);
        this.HorizonCode_Horizon_È(new ItemStack(Items.Œà, 3), "##", "##", "##", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Items.Ðƒá, 3), "##", "##", "##", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Â.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Items.ˆÏ, 3), "##", "##", "##", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Ý.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Items.áˆºÇŽØ, 3), "##", "##", "##", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Items.ÇªÂµÕ, 3), "##", "##", "##", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Items.áŒŠÏ, 3), "##", "##", "##", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Ó.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.áˆºà, 2), "###", "###", '#', Blocks.à);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ŠÓ, 3), "##", "##", "##", '#', Items.áˆºÑ¢Õ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.áˆºÓ, 1), "##", "##", '#', Items.áˆºÑ¢Õ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.Ï­Ô, 3), "###", "###", " X ", '#', Blocks.à, 'X', Items.áŒŠà);
        this.HorizonCode_Horizon_È(new ItemStack(Items.µÐƒáƒ, 1), "AAA", "BEB", "CCC", 'A', Items.áˆº, 'B', Items.£Ø­à, 'C', Items.Âµà, 'E', Items.¥É);
        this.HorizonCode_Horizon_È(new ItemStack(Items.£Ø­à, 1), "#", '#', Items.¥Ï);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.à, 4, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()), "#", '#', new ItemStack(Blocks.¥Æ, 1, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.à, 4, BlockPlanks.HorizonCode_Horizon_È.Â.Â()), "#", '#', new ItemStack(Blocks.¥Æ, 1, BlockPlanks.HorizonCode_Horizon_È.Â.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.à, 4, BlockPlanks.HorizonCode_Horizon_È.Ý.Â()), "#", '#', new ItemStack(Blocks.¥Æ, 1, BlockPlanks.HorizonCode_Horizon_È.Ý.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.à, 4, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â()), "#", '#', new ItemStack(Blocks.¥Æ, 1, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.à, 4, 4 + BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4), "#", '#', new ItemStack(Blocks.Ø­à, 1, BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.à, 4, 4 + BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4), "#", '#', new ItemStack(Blocks.Ø­à, 1, BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4));
        this.HorizonCode_Horizon_È(new ItemStack(Items.áŒŠà, 4), "#", "#", '#', Blocks.à);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ï, 4), "X", "#", 'X', Items.Ø, '#', Items.áŒŠà);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ï, 4), "X", "#", 'X', new ItemStack(Items.Ø, 1, 1), '#', Items.áŒŠà);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ŠÄ, 4), "# #", " # ", '#', Blocks.à);
        this.HorizonCode_Horizon_È(new ItemStack(Items.Ñ¢ÇŽÏ, 3), "# #", " # ", '#', Blocks.Ï­Ðƒà);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.áŒŠáŠ, 16), "X X", "X#X", "X X", 'X', Items.áˆºÑ¢Õ, '#', Items.áŒŠà);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇŽÉ, 6), "X X", "X#X", "XRX", 'X', Items.ÂµÈ, 'R', Items.ÇŽá, '#', Items.áŒŠà);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ø­à¢, 6), "XSX", "X#X", "XSX", 'X', Items.áˆºÑ¢Õ, '#', Blocks.áˆº, 'S', Items.áŒŠà);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ˆá, 6), "X X", "X#X", "XRX", 'X', Items.áˆºÑ¢Õ, 'R', Items.ÇŽá, '#', Blocks.Û);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ÇªÔ, 1), "# #", "###", '#', Items.áˆºÑ¢Õ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.Ï­Ä, 1), "# #", "# #", "###", '#', Items.áˆºÑ¢Õ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ÇªáˆºÕ, 1), " B ", "###", '#', Blocks.Ó, 'B', Items.Çªà);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.áŒŠÕ, 1), "A", "B", 'A', Blocks.Ø­Æ, 'B', Blocks.Ï);
        this.HorizonCode_Horizon_È(new ItemStack(Items.Ðƒáƒ, 1), "A", "B", 'A', Blocks.ˆáƒ, 'B', Items.ÇªÔ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.Ðƒà, 1), "A", "B", 'A', Blocks.£Ó, 'B', Items.ÇªÔ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ÇŽ, 1), "A", "B", 'A', Blocks.Ñ¢Â, 'B', Items.ÇªÔ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ÇŽÅ, 1), "A", "B", 'A', Blocks.áˆºÉ, 'B', Items.ÇªÔ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ÇªØ­, 1), "# #", "###", '#', Blocks.à);
        this.HorizonCode_Horizon_È(new ItemStack(Items.áŒŠáŠ, 1), "# #", " # ", '#', Items.áˆºÑ¢Õ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.µÐƒÓ, 1), "# #", " # ", '#', Items.Šà);
        this.Â(new ItemStack(Items.Ø­áŒŠá, 1), new ItemStack(Items.áˆºÑ¢Õ, 1), new ItemStack(Items.Ï­Ï­Ï, 1));
        this.HorizonCode_Horizon_È(new ItemStack(Items.Ç, 1), "###", '#', Items.Âµà);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.áˆºÏ, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ŠÏ, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Ý.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.£Ô, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Â.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ˆ, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.à, 1, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ðƒ, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.à, 1, 4 + BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.£Ç, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.à, 1, 4 + BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4));
        this.HorizonCode_Horizon_È(new ItemStack(Items.ÂµÕ, 1), "  #", " #X", "# X", '#', Items.áŒŠà, 'X', Items.ˆá);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ŠÑ¢Ó, 1), "# ", " X", '#', Items.ÂµÕ, 'X', Items.¥áŒŠà).Ý();
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ˆÓ, 4), "#  ", "## ", "###", '#', Blocks.Ó);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Çªà, 4), "#  ", "## ", "###", '#', Blocks.Ä);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.¥Å, 4), "#  ", "## ", "###", '#', Blocks.£áƒ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇªÂ, 4), "#  ", "## ", "###", '#', Blocks.µÂ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.µÏ, 4), "#  ", "## ", "###", '#', Blocks.ŒÏ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ˆÇªÓ, 4), "#  ", "## ", "###", '#', Blocks.áˆºÛ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ñ¢Õ, 4), "#  ", "## ", "###", '#', Blocks.Ø­È);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ˆÐƒØ­à, 1), "###", "#X#", "###", '#', Items.áŒŠà, 'X', Blocks.ŠÂµà);
        this.HorizonCode_Horizon_È(new ItemStack(Items.µÏ, 1), "###", "#X#", "###", '#', Items.áŒŠà, 'X', Items.£áŒŠá);
        this.HorizonCode_Horizon_È(new ItemStack(Items.£Õ, 1, 0), "###", "#X#", "###", '#', Items.ÂµÈ, 'X', Items.Âµá€);
        this.HorizonCode_Horizon_È(new ItemStack(Items.£Õ, 1, 1), "###", "#X#", "###", '#', Blocks.ˆáŠ, 'X', Items.Âµá€);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ŠÏ, 1, 0), "###", "#X#", "###", '#', Items.Œáƒ, 'X', Items.¥áŒŠà);
        this.HorizonCode_Horizon_È(new ItemStack(Items.µÊ, 1), "###", "#X#", "###", '#', Items.Œáƒ, 'X', Items.ÐƒÂ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇªÔ, 1), "X", "#", '#', Blocks.Ó, 'X', Items.áŒŠà);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ˆÂ, 2), "I", "S", "#", '#', Blocks.à, 'S', Items.áŒŠà, 'I', Items.áˆºÑ¢Õ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.áˆº, 1), "X", "#", '#', Items.áŒŠà, 'X', Items.ÇŽá);
        this.HorizonCode_Horizon_È(new ItemStack(Items.ÂµÂ, 1), "#X#", "III", '#', Blocks.áˆº, 'X', Items.ÇŽá, 'I', new ItemStack(Blocks.Ý, 1, BlockStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Items.ˆÕ, 1), " # ", "#X#", "III", '#', Blocks.áˆº, 'X', Items.ÇªÅ, 'I', new ItemStack(Blocks.Ý, 1, BlockStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Items.Š, 1), " # ", "#X#", " # ", '#', Items.ÂµÈ, 'X', Items.ÇŽá);
        this.HorizonCode_Horizon_È(new ItemStack(Items.£ÇªÓ, 1), " # ", "#X#", " # ", '#', Items.áˆºÑ¢Õ, 'X', Items.ÇŽá);
        this.HorizonCode_Horizon_È(new ItemStack(Items.£Ô, 1), "###", "#X#", "###", '#', Items.ˆà¢, 'X', Items.£ÇªÓ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Šà, 1), "#", '#', new ItemStack(Blocks.Ý, 1, BlockStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ˆÕ, 1), "#", '#', Blocks.à);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Û, 1), "##", '#', new ItemStack(Blocks.Ý, 1, BlockStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇŽá, 1), "##", '#', Blocks.à);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.¥Ðƒá, 1), "##", '#', Items.áˆºÑ¢Õ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇŽÅ, 1), "##", '#', Items.ÂµÈ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Ñ¢á, 1), "###", "#X#", "#R#", '#', Blocks.Ó, 'X', Items.Ó, 'R', Items.ÇŽá);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.áŒŠÓ, 1), "###", "# #", "#R#", '#', Blocks.Ó, 'R', Items.ÇŽá);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Õ, 1), "TTT", "#X#", "#R#", '#', Blocks.Ó, 'X', Items.áˆºÑ¢Õ, 'R', Items.ÇŽá, 'T', Blocks.à);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇŽÕ, 1), "S", "P", 'S', Items.£É, 'P', Blocks.Õ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.áŒŠÕ, 1), "###", "XXX", '#', Blocks.ŠÂµà, 'X', Blocks.à);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.¥Âµá€, 1), " B ", "D#D", "###", '#', Blocks.ÇŽá€, 'B', Items.Ñ¢Ç, 'D', Items.áŒŠÆ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÇªÅ, 1), "III", " i ", "iii", 'I', Blocks.áŒŠ, 'i', Items.áˆºÑ¢Õ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.£áŒŠá), "##", "##", '#', Items.Ï­Ï);
        this.Â(new ItemStack(Items.¥áŠ, 1), Items.ˆÐƒØ, Items.¥Âµá€);
        this.Â(new ItemStack(Items.ÇŽØ, 3), Items.É, Items.¥Âµá€, Items.Ø);
        this.Â(new ItemStack(Items.ÇŽØ, 3), Items.É, Items.¥Âµá€, new ItemStack(Items.Ø, 1, 1));
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.ÐƒÓ), "GGG", "QQQ", "WWW", 'G', Blocks.Ï­Ðƒà, 'Q', Items.ÇªÅ, 'W', Blocks.ÇŽÊ);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.áˆºÉ), "I I", "ICI", " I ", 'I', Items.áˆºÑ¢Õ, 'C', Blocks.ˆáƒ);
        this.HorizonCode_Horizon_È(new ItemStack(Items.¥Ðƒá, 1), "///", " / ", "/_/", '/', Items.áŒŠà, '_', new ItemStack(Blocks.Ø­Âµ, 1, BlockStoneSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()));
        Collections.sort((List<Object>)this.Â, new Comparator() {
            private static final String Â = "CL_00000091";
            
            public int HorizonCode_Horizon_È(final IRecipe p_compare_1_, final IRecipe p_compare_2_) {
                return (p_compare_1_ instanceof ShapelessRecipes && p_compare_2_ instanceof ShapedRecipes) ? 1 : ((p_compare_2_ instanceof ShapelessRecipes && p_compare_1_ instanceof ShapedRecipes) ? -1 : ((p_compare_2_.HorizonCode_Horizon_È() < p_compare_1_.HorizonCode_Horizon_È()) ? -1 : ((p_compare_2_.HorizonCode_Horizon_È() > p_compare_1_.HorizonCode_Horizon_È()) ? 1 : 0)));
            }
            
            @Override
            public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                return this.HorizonCode_Horizon_È((IRecipe)p_compare_1_, (IRecipe)p_compare_2_);
            }
        });
    }
    
    public ShapedRecipes HorizonCode_Horizon_È(final ItemStack p_92103_1_, final Object... p_92103_2_) {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        if (p_92103_2_[var4] instanceof String[]) {
            final String[] var7 = (String[])p_92103_2_[var4++];
            for (int var8 = 0; var8 < var7.length; ++var8) {
                final String var9 = var7[var8];
                ++var6;
                var5 = var9.length();
                var3 = String.valueOf(var3) + var9;
            }
        }
        else {
            while (p_92103_2_[var4] instanceof String) {
                final String var10 = (String)p_92103_2_[var4++];
                ++var6;
                var5 = var10.length();
                var3 = String.valueOf(var3) + var10;
            }
        }
        final HashMap var11 = Maps.newHashMap();
        while (var4 < p_92103_2_.length) {
            final Character var12 = (Character)p_92103_2_[var4];
            ItemStack var13 = null;
            if (p_92103_2_[var4 + 1] instanceof Item_1028566121) {
                var13 = new ItemStack((Item_1028566121)p_92103_2_[var4 + 1]);
            }
            else if (p_92103_2_[var4 + 1] instanceof Block) {
                var13 = new ItemStack((Block)p_92103_2_[var4 + 1], 1, 32767);
            }
            else if (p_92103_2_[var4 + 1] instanceof ItemStack) {
                var13 = (ItemStack)p_92103_2_[var4 + 1];
            }
            var11.put(var12, var13);
            var4 += 2;
        }
        final ItemStack[] var14 = new ItemStack[var5 * var6];
        for (int var15 = 0; var15 < var5 * var6; ++var15) {
            final char var16 = var3.charAt(var15);
            if (var11.containsKey(var16)) {
                var14[var15] = var11.get(var16).áˆºÑ¢Õ();
            }
            else {
                var14[var15] = null;
            }
        }
        final ShapedRecipes var17 = new ShapedRecipes(var5, var6, var14, p_92103_1_);
        this.Â.add(var17);
        return var17;
    }
    
    public void Â(final ItemStack p_77596_1_, final Object... p_77596_2_) {
        final ArrayList var3 = Lists.newArrayList();
        for (final Object var6 : p_77596_2_) {
            if (var6 instanceof ItemStack) {
                var3.add(((ItemStack)var6).áˆºÑ¢Õ());
            }
            else if (var6 instanceof Item_1028566121) {
                var3.add(new ItemStack((Item_1028566121)var6));
            }
            else {
                if (!(var6 instanceof Block)) {
                    throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + var6.getClass().getName() + "!");
                }
                var3.add(new ItemStack((Block)var6));
            }
        }
        this.Â.add(new ShapelessRecipes(p_77596_1_, var3));
    }
    
    public void HorizonCode_Horizon_È(final IRecipe p_180302_1_) {
        this.Â.add(p_180302_1_);
    }
    
    public ItemStack HorizonCode_Horizon_È(final InventoryCrafting p_82787_1_, final World worldIn) {
        for (final IRecipe var4 : this.Â) {
            if (var4.HorizonCode_Horizon_È(p_82787_1_, worldIn)) {
                return var4.HorizonCode_Horizon_È(p_82787_1_);
            }
        }
        return null;
    }
    
    public ItemStack[] Â(final InventoryCrafting p_180303_1_, final World worldIn) {
        for (final IRecipe var4 : this.Â) {
            if (var4.HorizonCode_Horizon_È(p_180303_1_, worldIn)) {
                return var4.Â(p_180303_1_);
            }
        }
        final ItemStack[] var5 = new ItemStack[p_180303_1_.áŒŠÆ()];
        for (int var6 = 0; var6 < var5.length; ++var6) {
            var5[var6] = p_180303_1_.á(var6);
        }
        return var5;
    }
    
    public List Â() {
        return this.Â;
    }
}
