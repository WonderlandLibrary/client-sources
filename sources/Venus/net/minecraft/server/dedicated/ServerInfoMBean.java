/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.dedicated;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.management.AttributeList;
import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ServerInfoMBean
implements DynamicMBean {
    private static final Logger field_233482_a_ = LogManager.getLogger();
    private final MinecraftServer field_233483_b_;
    private final MBeanInfo field_233484_c_;
    private final Map<String, Attribute> field_233485_d_ = Stream.of(new Attribute("tickTimes", this::func_233491_b_, "Historical tick times (ms)", long[].class), new Attribute("averageTickTime", this::func_233486_a_, "Current average tick time (ms)", Long.TYPE)).collect(Collectors.toMap(ServerInfoMBean::lambda$new$0, Function.identity()));

    private ServerInfoMBean(MinecraftServer minecraftServer) {
        this.field_233483_b_ = minecraftServer;
        MBeanAttributeInfo[] mBeanAttributeInfoArray = (MBeanAttributeInfo[])this.field_233485_d_.values().stream().map(ServerInfoMBean::lambda$new$1).toArray(ServerInfoMBean::lambda$new$2);
        this.field_233484_c_ = new MBeanInfo(ServerInfoMBean.class.getSimpleName(), "metrics for dedicated server", mBeanAttributeInfoArray, null, null, new MBeanNotificationInfo[0]);
    }

    public static void func_233490_a_(MinecraftServer minecraftServer) {
        try {
            ManagementFactory.getPlatformMBeanServer().registerMBean(new ServerInfoMBean(minecraftServer), new ObjectName("net.minecraft.server:type=Server"));
        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | MalformedObjectNameException | NotCompliantMBeanException jMException) {
            field_233482_a_.warn("Failed to initialise server as JMX bean", (Throwable)jMException);
        }
    }

    private float func_233486_a_() {
        return this.field_233483_b_.getTickTime();
    }

    private long[] func_233491_b_() {
        return this.field_233483_b_.tickTimeArray;
    }

    @Override
    @Nullable
    public Object getAttribute(String string) {
        Attribute attribute = this.field_233485_d_.get(string);
        return attribute == null ? null : attribute.field_233494_b_.get();
    }

    @Override
    public void setAttribute(javax.management.Attribute attribute) {
    }

    @Override
    public AttributeList getAttributes(String[] stringArray) {
        List<javax.management.Attribute> list = Arrays.stream(stringArray).map(this.field_233485_d_::get).filter(Objects::nonNull).map(ServerInfoMBean::lambda$getAttributes$3).collect(Collectors.toList());
        return new AttributeList(list);
    }

    @Override
    public AttributeList setAttributes(AttributeList attributeList) {
        return new AttributeList();
    }

    @Override
    @Nullable
    public Object invoke(String string, Object[] objectArray, String[] stringArray) {
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return this.field_233484_c_;
    }

    private static javax.management.Attribute lambda$getAttributes$3(Attribute attribute) {
        return new javax.management.Attribute(attribute.field_233493_a_, attribute.field_233494_b_.get());
    }

    private static MBeanAttributeInfo[] lambda$new$2(int n) {
        return new MBeanAttributeInfo[n];
    }

    private static MBeanAttributeInfo lambda$new$1(Attribute attribute) {
        return attribute.func_233497_a_();
    }

    private static String lambda$new$0(Attribute attribute) {
        return attribute.field_233493_a_;
    }

    static final class Attribute {
        private final String field_233493_a_;
        private final Supplier<Object> field_233494_b_;
        private final String field_233495_c_;
        private final Class<?> field_233496_d_;

        private Attribute(String string, Supplier<Object> supplier, String string2, Class<?> clazz) {
            this.field_233493_a_ = string;
            this.field_233494_b_ = supplier;
            this.field_233495_c_ = string2;
            this.field_233496_d_ = clazz;
        }

        private MBeanAttributeInfo func_233497_a_() {
            return new MBeanAttributeInfo(this.field_233493_a_, this.field_233496_d_.getSimpleName(), this.field_233495_c_, true, false, false);
        }
    }
}

