package xyz.mathax.mathaxclient.init;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import xyz.mathax.mathaxclient.addons.AddonManager;
import xyz.mathax.mathaxclient.addons.MatHaxAddon;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class Init {
    private static final List<String> packages = new ArrayList<>();

    public static void registerPackages(String packageString) {
        packages.add(packageString);

        for (MatHaxAddon addon : AddonManager.ADDONS) {
            try {
                String addonPackage = addon.getPackage();
                if (addonPackage != null && !addonPackage.isBlank()) {
                    packages.add(addonPackage);
                }
            } catch (AbstractMethodError exception) {
                throw new RuntimeException("Addon \"%s\" is too old and cannot be ran.".formatted(addon.name), exception);
            }
        }
    }

    public static void init(Class<? extends Annotation> annotation) {
        for (String pkg : packages) {
            Reflections reflections = new Reflections(pkg, Scanners.MethodsAnnotated);
            Set<Method> initTasks = reflections.getMethodsAnnotatedWith(annotation);
            if (initTasks == null) {
                return;
            }

            Map<Class<?>, List<Method>> byClass = initTasks.stream().collect(Collectors.groupingBy(Method::getDeclaringClass));
            Set<Method> left = new HashSet<>(initTasks);
            for (Method method; (method = left.stream().findAny().orElse(null)) != null;) {
                reflectInit(method, annotation, left, byClass);
            }
        }
    }

    private static <T extends Annotation> void reflectInit(Method task, Class<T> annotation, Set<Method> left, Map<Class<?>, List<Method>> byClass) {
        left.remove(task);

        for (Class<?> clazz : getDependencies(task, annotation)) {
            for (Method method : byClass.getOrDefault(clazz, Collections.emptyList())) {
                if (left.contains(method)) {
                    reflectInit(method, annotation, left, byClass);
                }
            }
        }

        try {
            task.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        } catch (NullPointerException exception) {
            throw new RuntimeException("Method \"%s\" using Init annotations from non-static context.".formatted(task.getName()), exception);
        }
    }

    private static <T extends Annotation> Class<?>[] getDependencies(Method task, Class<T> annotation) {
        T init = task.getAnnotation(annotation);
        if (init instanceof PreInit pre) {
            return pre.dependencies();
        } else if (init instanceof PostInit post) {
            return post.dependencies();
        }

        return new Class<?>[]{};
    }
}