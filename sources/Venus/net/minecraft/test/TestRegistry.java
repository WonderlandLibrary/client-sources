/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.test.TestFunctionInfo;
import net.minecraft.world.server.ServerWorld;

public class TestRegistry {
    private static final Collection<TestFunctionInfo> field_229526_a_ = Lists.newArrayList();
    private static final Set<String> field_229527_b_ = Sets.newHashSet();
    private static final Map<String, Consumer<ServerWorld>> field_229528_c_ = Maps.newHashMap();
    private static final Collection<TestFunctionInfo> field_240547_d_ = Sets.newHashSet();

    public static Collection<TestFunctionInfo> func_229530_a_(String string) {
        return field_229526_a_.stream().filter(arg_0 -> TestRegistry.lambda$func_229530_a_$0(string, arg_0)).collect(Collectors.toList());
    }

    public static Collection<TestFunctionInfo> func_229529_a_() {
        return field_229526_a_;
    }

    public static Collection<String> func_229533_b_() {
        return field_229527_b_;
    }

    public static boolean func_229534_b_(String string) {
        return field_229527_b_.contains(string);
    }

    @Nullable
    public static Consumer<ServerWorld> func_229536_c_(String string) {
        return field_229528_c_.get(string);
    }

    public static Optional<TestFunctionInfo> func_229537_d_(String string) {
        return TestRegistry.func_229529_a_().stream().filter(arg_0 -> TestRegistry.lambda$func_229537_d_$1(string, arg_0)).findFirst();
    }

    public static TestFunctionInfo func_229538_e_(String string) {
        Optional<TestFunctionInfo> optional = TestRegistry.func_229537_d_(string);
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("Can't find the test function for " + string);
        }
        return optional.get();
    }

    private static boolean func_229532_a_(TestFunctionInfo testFunctionInfo, String string) {
        return testFunctionInfo.func_229657_a_().toLowerCase().startsWith(string.toLowerCase() + ".");
    }

    public static Collection<TestFunctionInfo> func_240549_c_() {
        return field_240547_d_;
    }

    public static void func_240548_a_(TestFunctionInfo testFunctionInfo) {
        field_240547_d_.add(testFunctionInfo);
    }

    public static void func_240550_d_() {
        field_240547_d_.clear();
    }

    private static boolean lambda$func_229537_d_$1(String string, TestFunctionInfo testFunctionInfo) {
        return testFunctionInfo.func_229657_a_().equalsIgnoreCase(string);
    }

    private static boolean lambda$func_229530_a_$0(String string, TestFunctionInfo testFunctionInfo) {
        return TestRegistry.func_229532_a_(testFunctionInfo, string);
    }
}

