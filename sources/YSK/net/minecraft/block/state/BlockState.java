package net.minecraft.block.state;

import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class BlockState
{
    private final ImmutableList<IProperty> properties;
    private static final Joiner COMMA_JOINER;
    private final Block block;
    private static final String[] I;
    private final ImmutableList<IBlockState> validStates;
    private static final Function<IProperty, String> GET_NAME_FUNC;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add(BlockState.I[" ".length()], Block.blockRegistry.getNameForObject(this.block)).add(BlockState.I["  ".length()], (Object)Iterables.transform((Iterable)this.properties, (Function)BlockState.GET_NAME_FUNC)).toString();
    }
    
    private List<Iterable<Comparable>> getAllowedValues() {
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < this.properties.size()) {
            arrayList.add(((IProperty)this.properties.get(i)).getAllowedValues());
            ++i;
        }
        return (List<Iterable<Comparable>>)arrayList;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("tu", "XUHiv");
        BlockState.I[" ".length()] = I("\u0017\u0006.4&", "ujAWM");
        BlockState.I["  ".length()] = I("';'%\f%=!0\u001a", "WIHUi");
    }
    
    public Collection<IProperty> getProperties() {
        return (Collection<IProperty>)this.properties;
    }
    
    public ImmutableList<IBlockState> getValidStates() {
        return this.validStates;
    }
    
    public BlockState(final Block block, final IProperty... array) {
        this.block = block;
        Arrays.sort(array, new Comparator<IProperty>(this) {
            final BlockState this$0;
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((IProperty)o, (IProperty)o2);
            }
            
            @Override
            public int compare(final IProperty property, final IProperty property2) {
                return property.getName().compareTo(property2.getName());
            }
        });
        this.properties = (ImmutableList<IProperty>)ImmutableList.copyOf((Object[])array);
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<List<Object>> iterator = Cartesian.cartesianProduct((Iterable<? extends Iterable<?>>)this.getAllowedValues()).iterator();
        "".length();
        if (2 < 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map<IProperty, Comparable> map = MapPopulator.createMap((Iterable<IProperty>)this.properties, (Iterable<Comparable>)iterator.next());
            final StateImplementation stateImplementation = new StateImplementation(block, ImmutableMap.copyOf((Map)map), null);
            linkedHashMap.put(map, stateImplementation);
            arrayList.add(stateImplementation);
        }
        final Iterator<StateImplementation> iterator2 = (Iterator<StateImplementation>)arrayList.iterator();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (iterator2.hasNext()) {
            iterator2.next().buildPropertyValueTable(linkedHashMap);
        }
        this.validStates = (ImmutableList<IBlockState>)ImmutableList.copyOf((Collection)arrayList);
    }
    
    static {
        I();
        COMMA_JOINER = Joiner.on(BlockState.I["".length()]);
        GET_NAME_FUNC = (Function)new Function<IProperty, String>() {
            private static final String[] I;
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public String apply(final IProperty property) {
                String name;
                if (property == null) {
                    name = BlockState$1.I["".length()];
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                else {
                    name = property.getName();
                }
                return name;
            }
            
            public Object apply(final Object o) {
                return this.apply((IProperty)o);
            }
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("w96;\u000eu", "KwcwB");
            }
        };
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public IBlockState getBaseState() {
        return (IBlockState)this.validStates.get("".length());
    }
    
    static class StateImplementation extends BlockStateBase
    {
        private final ImmutableMap<IProperty, Comparable> properties;
        private final Block block;
        private static final String[] I;
        private ImmutableTable<IProperty, Comparable, IBlockState> propertyValueTable;
        
        @Override
        public <T extends Comparable<T>> T getValue(final IProperty<T> property) {
            if (!this.properties.containsKey((Object)property)) {
                throw new IllegalArgumentException(StateImplementation.I["".length()] + property + StateImplementation.I[" ".length()] + this.block.getBlockState());
            }
            return property.getValueClass().cast(this.properties.get((Object)property));
        }
        
        @Override
        public ImmutableMap<IProperty, Comparable> getProperties() {
            return this.properties;
        }
        
        @Override
        public <T extends Comparable<T>, V extends T> IBlockState withProperty(final IProperty<T> property, final V v) {
            if (!this.properties.containsKey((Object)property)) {
                throw new IllegalArgumentException(StateImplementation.I["  ".length()] + property + StateImplementation.I["   ".length()] + this.block.getBlockState());
            }
            if (!property.getAllowedValues().contains(v)) {
                throw new IllegalArgumentException(StateImplementation.I[0x5 ^ 0x1] + property + StateImplementation.I[0x7C ^ 0x79] + v + StateImplementation.I[0x47 ^ 0x41] + Block.blockRegistry.getNameForObject(this.block) + StateImplementation.I[0xC3 ^ 0xC4]);
            }
            IBlockState blockState;
            if (this.properties.get((Object)property) == v) {
                blockState = this;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                blockState = (IBlockState)this.propertyValueTable.get((Object)property, (Object)v);
            }
            return blockState;
        }
        
        private Map<IProperty, Comparable> getPropertiesWithValue(final IProperty property, final Comparable comparable) {
            final HashMap hashMap = Maps.newHashMap((Map)this.properties);
            hashMap.put(property, comparable);
            return (Map<IProperty, Comparable>)hashMap;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        StateImplementation(final Block block, final ImmutableMap immutableMap, final StateImplementation stateImplementation) {
            this(block, (ImmutableMap<IProperty, Comparable>)immutableMap);
        }
        
        @Override
        public Block getBlock() {
            return this.block;
        }
        
        public void buildPropertyValueTable(final Map<Map<IProperty, Comparable>, StateImplementation> map) {
            if (this.propertyValueTable != null) {
                throw new IllegalStateException();
            }
            final HashBasedTable create = HashBasedTable.create();
            final Iterator iterator = this.properties.keySet().iterator();
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final IProperty<Comparable> property = iterator.next();
                final Iterator<Comparable> iterator2 = property.getAllowedValues().iterator();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    final Comparable comparable = iterator2.next();
                    if (comparable != this.properties.get((Object)property)) {
                        ((Table)create).put((Object)property, (Object)comparable, (Object)map.get(this.getPropertiesWithValue(property, comparable)));
                    }
                }
            }
            this.propertyValueTable = (ImmutableTable<IProperty, Comparable, IBlockState>)ImmutableTable.copyOf((Table)create);
        }
        
        private static void I() {
            (I = new String[0x54 ^ 0x5C])["".length()] = I("\"\u0006>\u001d(\u0015G7\u00163A\u0017\"\u001c7\u0004\u0015$\ng", "agPsG");
            StateImplementation.I[" ".length()] = I("V\t\u0012o\u000f\u0002H\u0005 \u0003\u0005H\u000f \u0012V\r\u0019&\u0015\u0002H\b!F", "vhaOf");
            StateImplementation.I["  ".length()] = I("$\r\u001d/&\u0013L\u0000$=G\u001c\u0001.9\u0002\u001e\u00078i", "glsAI");
            StateImplementation.I["   ".length()] = I("q\u001b+x\u001c%Z<7\u0010\"Z67\u0001q\u001f 1\u0006%Z16U", "QzXXu");
            StateImplementation.I[0xB4 ^ 0xB0] = I("!5(\u0005\t\u0016t5\u000e\u0012B$4\u0004\u0016\u0007&2\u0012F", "bTFkf");
            StateImplementation.I[0x91 ^ 0x94] = I("A\u001b\u0000S", "aoosr");
            StateImplementation.I[0x50 ^ 0x56] = I("f5\u000bR\u0013*5\u0006\u0019Q", "FZerq");
            StateImplementation.I[0x14 ^ 0x13] = I("ek*2u 8c(:=k\"(u('/)\",/c04%>&", "IKCFU");
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private StateImplementation(final Block block, final ImmutableMap<IProperty, Comparable> properties) {
            this.block = block;
            this.properties = properties;
        }
        
        static {
            I();
        }
        
        @Override
        public int hashCode() {
            return this.properties.hashCode();
        }
        
        @Override
        public Collection<IProperty> getPropertyNames() {
            return (Collection<IProperty>)Collections.unmodifiableCollection((Collection<? extends IProperty>)this.properties.keySet());
        }
    }
}
