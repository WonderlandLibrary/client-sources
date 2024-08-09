/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ipfilter;

import io.netty.handler.ipfilter.IpFilterRule;
import io.netty.handler.ipfilter.IpFilterRuleType;
import io.netty.util.internal.SocketUtils;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public final class IpSubnetFilterRule
implements IpFilterRule {
    private final IpFilterRule filterRule;

    public IpSubnetFilterRule(String string, int n, IpFilterRuleType ipFilterRuleType) {
        try {
            this.filterRule = IpSubnetFilterRule.selectFilterRule(SocketUtils.addressByName(string), n, ipFilterRuleType);
        } catch (UnknownHostException unknownHostException) {
            throw new IllegalArgumentException("ipAddress", unknownHostException);
        }
    }

    public IpSubnetFilterRule(InetAddress inetAddress, int n, IpFilterRuleType ipFilterRuleType) {
        this.filterRule = IpSubnetFilterRule.selectFilterRule(inetAddress, n, ipFilterRuleType);
    }

    private static IpFilterRule selectFilterRule(InetAddress inetAddress, int n, IpFilterRuleType ipFilterRuleType) {
        if (inetAddress == null) {
            throw new NullPointerException("ipAddress");
        }
        if (ipFilterRuleType == null) {
            throw new NullPointerException("ruleType");
        }
        if (inetAddress instanceof Inet4Address) {
            return new Ip4SubnetFilterRule((Inet4Address)inetAddress, n, ipFilterRuleType, null);
        }
        if (inetAddress instanceof Inet6Address) {
            return new Ip6SubnetFilterRule((Inet6Address)inetAddress, n, ipFilterRuleType, null);
        }
        throw new IllegalArgumentException("Only IPv4 and IPv6 addresses are supported");
    }

    @Override
    public boolean matches(InetSocketAddress inetSocketAddress) {
        return this.filterRule.matches(inetSocketAddress);
    }

    @Override
    public IpFilterRuleType ruleType() {
        return this.filterRule.ruleType();
    }

    private static final class Ip6SubnetFilterRule
    implements IpFilterRule {
        private static final BigInteger MINUS_ONE;
        private final BigInteger networkAddress;
        private final BigInteger subnetMask;
        private final IpFilterRuleType ruleType;
        static final boolean $assertionsDisabled;

        private Ip6SubnetFilterRule(Inet6Address inet6Address, int n, IpFilterRuleType ipFilterRuleType) {
            if (n < 0 || n > 128) {
                throw new IllegalArgumentException(String.format("IPv6 requires the subnet prefix to be in range of [0,128]. The prefix was: %d", n));
            }
            this.subnetMask = Ip6SubnetFilterRule.prefixToSubnetMask(n);
            this.networkAddress = Ip6SubnetFilterRule.ipToInt(inet6Address).and(this.subnetMask);
            this.ruleType = ipFilterRuleType;
        }

        @Override
        public boolean matches(InetSocketAddress inetSocketAddress) {
            InetAddress inetAddress = inetSocketAddress.getAddress();
            if (inetAddress instanceof Inet6Address) {
                BigInteger bigInteger = Ip6SubnetFilterRule.ipToInt((Inet6Address)inetAddress);
                return bigInteger.and(this.subnetMask).equals(this.networkAddress);
            }
            return true;
        }

        @Override
        public IpFilterRuleType ruleType() {
            return this.ruleType;
        }

        private static BigInteger ipToInt(Inet6Address inet6Address) {
            byte[] byArray = inet6Address.getAddress();
            if (!$assertionsDisabled && byArray.length != 16) {
                throw new AssertionError();
            }
            return new BigInteger(byArray);
        }

        private static BigInteger prefixToSubnetMask(int n) {
            return MINUS_ONE.shiftLeft(128 - n);
        }

        Ip6SubnetFilterRule(Inet6Address inet6Address, int n, IpFilterRuleType ipFilterRuleType, 1 var4_4) {
            this(inet6Address, n, ipFilterRuleType);
        }

        static {
            $assertionsDisabled = !IpSubnetFilterRule.class.desiredAssertionStatus();
            MINUS_ONE = BigInteger.valueOf(-1L);
        }
    }

    private static final class Ip4SubnetFilterRule
    implements IpFilterRule {
        private final int networkAddress;
        private final int subnetMask;
        private final IpFilterRuleType ruleType;
        static final boolean $assertionsDisabled = !IpSubnetFilterRule.class.desiredAssertionStatus();

        private Ip4SubnetFilterRule(Inet4Address inet4Address, int n, IpFilterRuleType ipFilterRuleType) {
            if (n < 0 || n > 32) {
                throw new IllegalArgumentException(String.format("IPv4 requires the subnet prefix to be in range of [0,32]. The prefix was: %d", n));
            }
            this.subnetMask = Ip4SubnetFilterRule.prefixToSubnetMask(n);
            this.networkAddress = Ip4SubnetFilterRule.ipToInt(inet4Address) & this.subnetMask;
            this.ruleType = ipFilterRuleType;
        }

        @Override
        public boolean matches(InetSocketAddress inetSocketAddress) {
            InetAddress inetAddress = inetSocketAddress.getAddress();
            if (inetAddress instanceof Inet4Address) {
                int n = Ip4SubnetFilterRule.ipToInt((Inet4Address)inetAddress);
                return (n & this.subnetMask) == this.networkAddress;
            }
            return true;
        }

        @Override
        public IpFilterRuleType ruleType() {
            return this.ruleType;
        }

        private static int ipToInt(Inet4Address inet4Address) {
            byte[] byArray = inet4Address.getAddress();
            if (!$assertionsDisabled && byArray.length != 4) {
                throw new AssertionError();
            }
            return (byArray[0] & 0xFF) << 24 | (byArray[1] & 0xFF) << 16 | (byArray[2] & 0xFF) << 8 | byArray[3] & 0xFF;
        }

        private static int prefixToSubnetMask(int n) {
            return (int)(-1L << 32 - n & 0xFFFFFFFFFFFFFFFFL);
        }

        Ip4SubnetFilterRule(Inet4Address inet4Address, int n, IpFilterRuleType ipFilterRuleType, 1 var4_4) {
            this(inet4Address, n, ipFilterRuleType);
        }
    }
}

