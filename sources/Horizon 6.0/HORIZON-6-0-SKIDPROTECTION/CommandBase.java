package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Iterables;
import com.google.common.base.Functions;
import java.util.Arrays;
import java.util.Collection;
import com.google.common.collect.Lists;
import java.util.UUID;
import com.google.common.primitives.Doubles;
import java.util.Collections;
import java.util.List;

public abstract class CommandBase implements ICommand
{
    private static IAdminCommand HorizonCode_Horizon_È;
    private static final String Â = "CL_00001739";
    
    public int HorizonCode_Horizon_È() {
        return 4;
    }
    
    @Override
    public List Â() {
        return Collections.emptyList();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ICommandSender sender) {
        return sender.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(), this.Ý());
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return null;
    }
    
    public static int HorizonCode_Horizon_È(final String input) throws NumberInvalidException {
        try {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException var2) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
        }
    }
    
    public static int HorizonCode_Horizon_È(final String input, final int min) throws NumberInvalidException {
        return HorizonCode_Horizon_È(input, min, Integer.MAX_VALUE);
    }
    
    public static int HorizonCode_Horizon_È(final String input, final int min, final int max) throws NumberInvalidException {
        final int var3 = HorizonCode_Horizon_È(input);
        if (var3 < min) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { var3, min });
        }
        if (var3 > max) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { var3, max });
        }
        return var3;
    }
    
    public static long Â(final String input) throws NumberInvalidException {
        try {
            return Long.parseLong(input);
        }
        catch (NumberFormatException var2) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
        }
    }
    
    public static long HorizonCode_Horizon_È(final String input, final long min, final long max) throws NumberInvalidException {
        final long var5 = Â(input);
        if (var5 < min) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { var5, min });
        }
        if (var5 > max) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { var5, max });
        }
        return var5;
    }
    
    public static BlockPos HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final int p_175757_2_, final boolean p_175757_3_) throws NumberInvalidException {
        final BlockPos var4 = sender.£á();
        return new BlockPos(Â(var4.HorizonCode_Horizon_È(), args[p_175757_2_], -30000000, 30000000, p_175757_3_), Â(var4.Â(), args[p_175757_2_ + 1], 0, 256, false), Â(var4.Ý(), args[p_175757_2_ + 2], -30000000, 30000000, p_175757_3_));
    }
    
    public static double Ý(final String input) throws NumberInvalidException {
        try {
            final double var1 = Double.parseDouble(input);
            if (!Doubles.isFinite(var1)) {
                throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
            }
            return var1;
        }
        catch (NumberFormatException var2) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
        }
    }
    
    public static double HorizonCode_Horizon_È(final String input, final double min) throws NumberInvalidException {
        return HorizonCode_Horizon_È(input, min, Double.MAX_VALUE);
    }
    
    public static double HorizonCode_Horizon_È(final String input, final double min, final double max) throws NumberInvalidException {
        final double var5 = Ý(input);
        if (var5 < min) {
            throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { var5, min });
        }
        if (var5 > max) {
            throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { var5, max });
        }
        return var5;
    }
    
    public static boolean Ø­áŒŠá(final String input) throws CommandException {
        if (input.equals("true") || input.equals("1")) {
            return true;
        }
        if (!input.equals("false") && !input.equals("0")) {
            throw new CommandException("commands.generic.boolean.invalid", new Object[] { input });
        }
        return false;
    }
    
    public static EntityPlayerMP Â(final ICommandSender sender) throws PlayerNotFoundException {
        if (sender instanceof EntityPlayerMP) {
            return (EntityPlayerMP)sender;
        }
        throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
    }
    
    public static EntityPlayerMP HorizonCode_Horizon_È(final ICommandSender sender, final String username) throws PlayerNotFoundException {
        EntityPlayerMP var2 = PlayerSelector.HorizonCode_Horizon_È(sender, username);
        if (var2 == null) {
            try {
                var2 = MinecraftServer.áƒ().Œ().HorizonCode_Horizon_È(UUID.fromString(username));
            }
            catch (IllegalArgumentException ex) {}
        }
        if (var2 == null) {
            var2 = MinecraftServer.áƒ().Œ().HorizonCode_Horizon_È(username);
        }
        if (var2 == null) {
            throw new PlayerNotFoundException();
        }
        return var2;
    }
    
    public static Entity Â(final ICommandSender p_175768_0_, final String p_175768_1_) throws EntityNotFoundException {
        return HorizonCode_Horizon_È(p_175768_0_, p_175768_1_, Entity.class);
    }
    
    public static Entity HorizonCode_Horizon_È(final ICommandSender p_175759_0_, final String p_175759_1_, final Class p_175759_2_) throws EntityNotFoundException {
        Object var3 = PlayerSelector.HorizonCode_Horizon_È(p_175759_0_, p_175759_1_, p_175759_2_);
        final MinecraftServer var4 = MinecraftServer.áƒ();
        if (var3 == null) {
            var3 = var4.Œ().HorizonCode_Horizon_È(p_175759_1_);
        }
        if (var3 == null) {
            try {
                final UUID var5 = UUID.fromString(p_175759_1_);
                var3 = var4.HorizonCode_Horizon_È(var5);
                if (var3 == null) {
                    var3 = var4.Œ().HorizonCode_Horizon_È(var5);
                }
            }
            catch (IllegalArgumentException var6) {
                throw new EntityNotFoundException("commands.generic.entity.invalidUuid", new Object[0]);
            }
        }
        if (var3 != null && p_175759_2_.isAssignableFrom(var3.getClass())) {
            return (Entity)var3;
        }
        throw new EntityNotFoundException();
    }
    
    public static List Ý(final ICommandSender p_175763_0_, final String p_175763_1_) throws EntityNotFoundException {
        return PlayerSelector.Â(p_175763_1_) ? PlayerSelector.Â(p_175763_0_, p_175763_1_, Entity.class) : Lists.newArrayList((Object[])new Entity[] { Â(p_175763_0_, p_175763_1_) });
    }
    
    public static String Ø­áŒŠá(final ICommandSender sender, final String query) throws PlayerNotFoundException {
        try {
            return HorizonCode_Horizon_È(sender, query).v_();
        }
        catch (PlayerNotFoundException var3) {
            if (PlayerSelector.Â(query)) {
                throw var3;
            }
            return query;
        }
    }
    
    public static String Âµá€(final ICommandSender p_175758_0_, final String p_175758_1_) throws EntityNotFoundException {
        try {
            return HorizonCode_Horizon_È(p_175758_0_, p_175758_1_).v_();
        }
        catch (PlayerNotFoundException var5) {
            try {
                return Â(p_175758_0_, p_175758_1_).£áŒŠá().toString();
            }
            catch (EntityNotFoundException var4) {
                if (PlayerSelector.Â(p_175758_1_)) {
                    throw var4;
                }
                return p_175758_1_;
            }
        }
    }
    
    public static IChatComponent HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final int p_147178_2_) throws CommandException {
        return Â(sender, args, p_147178_2_, false);
    }
    
    public static IChatComponent Â(final ICommandSender sender, final String[] args, final int index, final boolean p_147176_3_) throws PlayerNotFoundException {
        final ChatComponentText var4 = new ChatComponentText("");
        for (int var5 = index; var5 < args.length; ++var5) {
            if (var5 > index) {
                var4.Â(" ");
            }
            Object var6 = new ChatComponentText(args[var5]);
            if (p_147176_3_) {
                final IChatComponent var7 = PlayerSelector.Â(sender, args[var5]);
                if (var7 == null) {
                    if (PlayerSelector.Â(args[var5])) {
                        throw new PlayerNotFoundException();
                    }
                }
                else {
                    var6 = var7;
                }
            }
            var4.HorizonCode_Horizon_È((IChatComponent)var6);
        }
        return var4;
    }
    
    public static String HorizonCode_Horizon_È(final String[] p_180529_0_, final int p_180529_1_) {
        final StringBuilder var2 = new StringBuilder();
        for (int var3 = p_180529_1_; var3 < p_180529_0_.length; ++var3) {
            if (var3 > p_180529_1_) {
                var2.append(" ");
            }
            final String var4 = p_180529_0_[var3];
            var2.append(var4);
        }
        return var2.toString();
    }
    
    public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final double p_175770_0_, final String p_175770_2_, final boolean p_175770_3_) throws NumberInvalidException {
        return HorizonCode_Horizon_È(p_175770_0_, p_175770_2_, -30000000, 30000000, p_175770_3_);
    }
    
    public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final double p_175767_0_, String p_175767_2_, final int p_175767_3_, final int p_175767_4_, final boolean p_175767_5_) throws NumberInvalidException {
        final boolean var6 = p_175767_2_.startsWith("~");
        if (var6 && Double.isNaN(p_175767_0_)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { p_175767_0_ });
        }
        double var7 = 0.0;
        if (!var6 || p_175767_2_.length() > 1) {
            final boolean var8 = p_175767_2_.contains(".");
            if (var6) {
                p_175767_2_ = p_175767_2_.substring(1);
            }
            var7 += Ý(p_175767_2_);
            if (!var8 && !var6 && p_175767_5_) {
                var7 += 0.5;
            }
        }
        if (p_175767_3_ != 0 || p_175767_4_ != 0) {
            if (var7 < p_175767_3_) {
                throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { var7, p_175767_3_ });
            }
            if (var7 > p_175767_4_) {
                throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { var7, p_175767_4_ });
            }
        }
        return new HorizonCode_Horizon_È(var7 + (var6 ? p_175767_0_ : 0.0), var7, var6);
    }
    
    public static double Â(final double p_175761_0_, final String p_175761_2_, final boolean p_175761_3_) throws NumberInvalidException {
        return Â(p_175761_0_, p_175761_2_, -30000000, 30000000, p_175761_3_);
    }
    
    public static double Â(final double base, String input, final int min, final int max, final boolean centerBlock) throws NumberInvalidException {
        final boolean var6 = input.startsWith("~");
        if (var6 && Double.isNaN(base)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { base });
        }
        double var7 = var6 ? base : 0.0;
        if (!var6 || input.length() > 1) {
            final boolean var8 = input.contains(".");
            if (var6) {
                input = input.substring(1);
            }
            var7 += Ý(input);
            if (!var8 && !var6 && centerBlock) {
                var7 += 0.5;
            }
        }
        if (min != 0 || max != 0) {
            if (var7 < min) {
                throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { var7, min });
            }
            if (var7 > max) {
                throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { var7, max });
            }
        }
        return var7;
    }
    
    public static Item_1028566121 Ó(final ICommandSender sender, final String id) throws NumberInvalidException {
        final ResourceLocation_1975012498 var2 = new ResourceLocation_1975012498(id);
        final Item_1028566121 var3 = (Item_1028566121)Item_1028566121.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2);
        if (var3 == null) {
            throw new NumberInvalidException("commands.give.notFound", new Object[] { var2 });
        }
        return var3;
    }
    
    public static Block à(final ICommandSender sender, final String id) throws NumberInvalidException {
        final ResourceLocation_1975012498 var2 = new ResourceLocation_1975012498(id);
        if (!Block.HorizonCode_Horizon_È.Ý(var2)) {
            throw new NumberInvalidException("commands.give.notFound", new Object[] { var2 });
        }
        final Block var3 = (Block)Block.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2);
        if (var3 == null) {
            throw new NumberInvalidException("commands.give.notFound", new Object[] { var2 });
        }
        return var3;
    }
    
    public static String HorizonCode_Horizon_È(final Object[] elements) {
        final StringBuilder var1 = new StringBuilder();
        for (int var2 = 0; var2 < elements.length; ++var2) {
            final String var3 = elements[var2].toString();
            if (var2 > 0) {
                if (var2 == elements.length - 1) {
                    var1.append(" and ");
                }
                else {
                    var1.append(", ");
                }
            }
            var1.append(var3);
        }
        return var1.toString();
    }
    
    public static IChatComponent HorizonCode_Horizon_È(final List components) {
        final ChatComponentText var1 = new ChatComponentText("");
        for (int var2 = 0; var2 < components.size(); ++var2) {
            if (var2 > 0) {
                if (var2 == components.size() - 1) {
                    var1.Â(" and ");
                }
                else if (var2 > 0) {
                    var1.Â(", ");
                }
            }
            var1.HorizonCode_Horizon_È(components.get(var2));
        }
        return var1;
    }
    
    public static String HorizonCode_Horizon_È(final Collection strings) {
        return HorizonCode_Horizon_È((Object[])strings.toArray(new String[strings.size()]));
    }
    
    public static List HorizonCode_Horizon_È(final String[] p_175771_0_, final int p_175771_1_, final BlockPos p_175771_2_) {
        if (p_175771_2_ == null) {
            return null;
        }
        String var3;
        if (p_175771_0_.length - 1 == p_175771_1_) {
            var3 = Integer.toString(p_175771_2_.HorizonCode_Horizon_È());
        }
        else if (p_175771_0_.length - 1 == p_175771_1_ + 1) {
            var3 = Integer.toString(p_175771_2_.Â());
        }
        else {
            if (p_175771_0_.length - 1 != p_175771_1_ + 2) {
                return null;
            }
            var3 = Integer.toString(p_175771_2_.Ý());
        }
        return Lists.newArrayList((Object[])new String[] { var3 });
    }
    
    public static boolean HorizonCode_Horizon_È(final String original, final String region) {
        return region.regionMatches(true, 0, original, 0, original.length());
    }
    
    public static List HorizonCode_Horizon_È(final String[] args, final String... possibilities) {
        return HorizonCode_Horizon_È(args, Arrays.asList(possibilities));
    }
    
    public static List HorizonCode_Horizon_È(final String[] p_175762_0_, final Collection p_175762_1_) {
        final String var2 = p_175762_0_[p_175762_0_.length - 1];
        final ArrayList var3 = Lists.newArrayList();
        if (!p_175762_1_.isEmpty()) {
            for (final String var5 : Iterables.transform((Iterable)p_175762_1_, Functions.toStringFunction())) {
                if (HorizonCode_Horizon_È(var2, var5)) {
                    var3.add(var5);
                }
            }
            if (var3.isEmpty()) {
                for (final Object var6 : p_175762_1_) {
                    if (var6 instanceof ResourceLocation_1975012498 && HorizonCode_Horizon_È(var2, ((ResourceLocation_1975012498)var6).Â())) {
                        var3.add(String.valueOf(var6));
                    }
                }
            }
        }
        return var3;
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return false;
    }
    
    public static void HorizonCode_Horizon_È(final ICommandSender sender, final ICommand command, final String msgFormat, final Object... msgParams) {
        HorizonCode_Horizon_È(sender, command, 0, msgFormat, msgParams);
    }
    
    public static void HorizonCode_Horizon_È(final ICommandSender sender, final ICommand command, final int p_152374_2_, final String msgFormat, final Object... msgParams) {
        if (CommandBase.HorizonCode_Horizon_È != null) {
            CommandBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È(sender, command, p_152374_2_, msgFormat, msgParams);
        }
    }
    
    public static void HorizonCode_Horizon_È(final IAdminCommand command) {
        CommandBase.HorizonCode_Horizon_È = command;
    }
    
    public int HorizonCode_Horizon_È(final ICommand p_compareTo_1_) {
        return this.Ý().compareTo(p_compareTo_1_.Ý());
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.HorizonCode_Horizon_È((ICommand)p_compareTo_1_);
    }
    
    public static class HorizonCode_Horizon_È
    {
        private final double HorizonCode_Horizon_È;
        private final double Â;
        private final boolean Ý;
        private static final String Ø­áŒŠá = "CL_00002365";
        
        protected HorizonCode_Horizon_È(final double p_i46051_1_, final double p_i46051_3_, final boolean p_i46051_5_) {
            this.HorizonCode_Horizon_È = p_i46051_1_;
            this.Â = p_i46051_3_;
            this.Ý = p_i46051_5_;
        }
        
        public double HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public double Â() {
            return this.Â;
        }
        
        public boolean Ý() {
            return this.Ý;
        }
    }
}
