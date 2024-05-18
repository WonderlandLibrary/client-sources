package us.dev.direkt.gui.tab.handling;

import com.google.common.collect.Sets;
import us.dev.api.factory.ClassFactory;
import us.dev.api.factory.exceptions.FactoryException;
import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.handling.handlers.*;
import us.dev.direkt.gui.tab.tab.Tab;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by Foundry on 12/29/2015.
 */
public class Tabs {
    private Tabs() {
    }

    private static Set<TabFactory> factoryRegistry = Sets.newHashSet();

    private static void registerFactory(Class<? extends TabFactory> handler) {
        try {
            TabFactory factory = ClassFactory.create(handler);
            factoryRegistry.add(factory);
        } catch (FactoryException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> TabFactory<Tab<T>> getFactoryFor(Class<T> clazz) {
        for (TabFactory factory : factoryRegistry) {
            if (factory.getHandledType().isAssignableFrom(clazz)) {
                return (TabFactory<Tab<T>>) factory;
            }
        }
        System.err.println("NO FACTORY FOUND FOR CLASS: " + clazz);
        throw new UnsupportedOperationException("There is no handler for the object: " + clazz.getSimpleName());
    }

    public static <T> Tab<T> newTab(TabHandler handler, T stateObject, TabBlock container) {
        return newTab(handler, stateObject, null, null, container);
    }

    public static <T> Tab<T> newTab(TabHandler handler, T stateObject, Tab<?> parent, TabBlock container) {
        return newTab(handler, stateObject, parent, null, container);
    }

    public static <T> Tab<T> newTab(TabHandler handler, T stateObject, TabBlock children, TabBlock container) {
        return newTab(handler, stateObject, null, children, container);
    }

    @SuppressWarnings("unchecked")
    public static <T> Tab<T> newTab(TabHandler handler, T stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        return ((TabFactory<T>) getFactoryFor(stateObject.getClass())).parse(handler, stateObject, parent, children, container);
    }

    public static TabBlock newTabBlock() {
        return new TabBlock();
    }

    public static <T> Collector<T, TabBlock, TabBlock> toTabBlock(TabHandler handler) {
        return new Collector<T, TabBlock, TabBlock>() {
            @Override
            public Supplier<TabBlock> supplier() {
                return TabBlock::new;
            }

            @Override
            public BiConsumer<TabBlock, T> accumulator() {
                return (block, t) -> block.appendTab(Tabs.newTab(handler, t, block));
            }

            @Override
            public BinaryOperator<TabBlock> combiner() {
                return (left, right) -> {
                    right.forEach(left::appendTab);
                    return left;
                };
            }

            @Override
            public Function<TabBlock, TabBlock> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Collector.Characteristics> characteristics() {
                return EnumSet.of(Collector.Characteristics.UNORDERED);
            }
        };
    }

    static {
        registerFactory(PropertyTabFactory.class);
        registerFactory(ModCategoryTabFactory.class);
        registerFactory(ModuleTabFactory.class);
    }
}
