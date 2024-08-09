/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.haproxy;

public enum HAProxyProxiedProtocol {
    UNKNOWN(0, AddressFamily.AF_UNSPEC, TransportProtocol.UNSPEC),
    TCP4(17, AddressFamily.AF_IPv4, TransportProtocol.STREAM),
    TCP6(33, AddressFamily.AF_IPv6, TransportProtocol.STREAM),
    UDP4(18, AddressFamily.AF_IPv4, TransportProtocol.DGRAM),
    UDP6(34, AddressFamily.AF_IPv6, TransportProtocol.DGRAM),
    UNIX_STREAM(49, AddressFamily.AF_UNIX, TransportProtocol.STREAM),
    UNIX_DGRAM(50, AddressFamily.AF_UNIX, TransportProtocol.DGRAM);

    private final byte byteValue;
    private final AddressFamily addressFamily;
    private final TransportProtocol transportProtocol;

    private HAProxyProxiedProtocol(byte by, AddressFamily addressFamily, TransportProtocol transportProtocol) {
        this.byteValue = by;
        this.addressFamily = addressFamily;
        this.transportProtocol = transportProtocol;
    }

    public static HAProxyProxiedProtocol valueOf(byte by) {
        switch (by) {
            case 17: {
                return TCP4;
            }
            case 33: {
                return TCP6;
            }
            case 0: {
                return UNKNOWN;
            }
            case 18: {
                return UDP4;
            }
            case 34: {
                return UDP6;
            }
            case 49: {
                return UNIX_STREAM;
            }
            case 50: {
                return UNIX_DGRAM;
            }
        }
        throw new IllegalArgumentException("unknown transport protocol + address family: " + (by & 0xFF));
    }

    public byte byteValue() {
        return this.byteValue;
    }

    public AddressFamily addressFamily() {
        return this.addressFamily;
    }

    public TransportProtocol transportProtocol() {
        return this.transportProtocol;
    }

    public static enum TransportProtocol {
        UNSPEC(0),
        STREAM(1),
        DGRAM(2);

        private static final byte TRANSPORT_MASK = 15;
        private final byte transportByte;

        private TransportProtocol(byte by) {
            this.transportByte = by;
        }

        public static TransportProtocol valueOf(byte by) {
            int n = by & 0xF;
            switch ((byte)n) {
                case 1: {
                    return STREAM;
                }
                case 0: {
                    return UNSPEC;
                }
                case 2: {
                    return DGRAM;
                }
            }
            throw new IllegalArgumentException("unknown transport protocol: " + n);
        }

        public byte byteValue() {
            return this.transportByte;
        }
    }

    public static enum AddressFamily {
        AF_UNSPEC(0),
        AF_IPv4(16),
        AF_IPv6(32),
        AF_UNIX(48);

        private static final byte FAMILY_MASK = -16;
        private final byte byteValue;

        private AddressFamily(byte by) {
            this.byteValue = by;
        }

        public static AddressFamily valueOf(byte by) {
            int n = by & 0xFFFFFFF0;
            switch ((byte)n) {
                case 16: {
                    return AF_IPv4;
                }
                case 32: {
                    return AF_IPv6;
                }
                case 0: {
                    return AF_UNSPEC;
                }
                case 48: {
                    return AF_UNIX;
                }
            }
            throw new IllegalArgumentException("unknown address family: " + n);
        }

        public byte byteValue() {
            return this.byteValue;
        }
    }
}

