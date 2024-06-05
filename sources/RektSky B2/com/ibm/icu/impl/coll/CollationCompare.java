package com.ibm.icu.impl.coll;

public final class CollationCompare
{
    public static int compareUpToQuaternary(final CollationIterator left, final CollationIterator right, final CollationSettings settings) {
        final int options = settings.options;
        long variableTop;
        if ((options & 0xC) == 0x0) {
            variableTop = 0L;
        }
        else {
            variableTop = settings.variableTop + 1L;
        }
        boolean anyVariable = false;
        while (true) {
            long ce = left.nextCE();
            long leftPrimary = ce >>> 32;
            if (leftPrimary < variableTop && leftPrimary > 33554432L) {
                anyVariable = true;
                do {
                    left.setCurrentCE(ce & 0xFFFFFFFF00000000L);
                    while (true) {
                        ce = left.nextCE();
                        leftPrimary = ce >>> 32;
                        if (leftPrimary != 0L) {
                            break;
                        }
                        left.setCurrentCE(0L);
                    }
                } while (leftPrimary < variableTop && leftPrimary > 33554432L);
            }
            if (leftPrimary != 0L) {
                long rightPrimary;
                do {
                    long ce2 = right.nextCE();
                    rightPrimary = ce2 >>> 32;
                    if (rightPrimary < variableTop && rightPrimary > 33554432L) {
                        anyVariable = true;
                        do {
                            right.setCurrentCE(ce2 & 0xFFFFFFFF00000000L);
                            while (true) {
                                ce2 = right.nextCE();
                                rightPrimary = ce2 >>> 32;
                                if (rightPrimary != 0L) {
                                    break;
                                }
                                right.setCurrentCE(0L);
                            }
                        } while (rightPrimary < variableTop && rightPrimary > 33554432L);
                    }
                } while (rightPrimary == 0L);
                if (leftPrimary != rightPrimary) {
                    if (settings.hasReordering()) {
                        leftPrimary = settings.reorder(leftPrimary);
                        rightPrimary = settings.reorder(rightPrimary);
                    }
                    return (leftPrimary < rightPrimary) ? -1 : 1;
                }
                if (leftPrimary != 1L) {
                    continue;
                }
                Label_0614: {
                    if (CollationSettings.getStrength(options) >= 1) {
                        if ((options & 0x800) == 0x0) {
                            int leftIndex = 0;
                            int rightIndex = 0;
                            while (true) {
                                final int leftSecondary = (int)left.getCE(leftIndex++) >>> 16;
                                if (leftSecondary != 0) {
                                    int rightSecondary;
                                    do {
                                        rightSecondary = (int)right.getCE(rightIndex++) >>> 16;
                                    } while (rightSecondary == 0);
                                    if (leftSecondary != rightSecondary) {
                                        return (leftSecondary < rightSecondary) ? -1 : 1;
                                    }
                                    if (leftSecondary == 256) {
                                        break;
                                    }
                                    continue;
                                }
                            }
                        }
                        else {
                            int leftStart = 0;
                            int rightStart = 0;
                            while (true) {
                                int leftLimit;
                                long p;
                                for (leftLimit = leftStart; (p = left.getCE(leftLimit) >>> 32) > 33554432L || p == 0L; ++leftLimit) {}
                                int rightLimit;
                                for (rightLimit = rightStart; (p = right.getCE(rightLimit) >>> 32) > 33554432L || p == 0L; ++rightLimit) {}
                                int leftIndex2 = leftLimit;
                                int rightIndex2 = rightLimit;
                                while (true) {
                                    int leftSecondary2;
                                    for (leftSecondary2 = 0; leftSecondary2 == 0 && leftIndex2 > leftStart; leftSecondary2 = (int)left.getCE(--leftIndex2) >>> 16) {}
                                    int rightSecondary2;
                                    for (rightSecondary2 = 0; rightSecondary2 == 0 && rightIndex2 > rightStart; rightSecondary2 = (int)right.getCE(--rightIndex2) >>> 16) {}
                                    if (leftSecondary2 != rightSecondary2) {
                                        return (leftSecondary2 < rightSecondary2) ? -1 : 1;
                                    }
                                    if (leftSecondary2 != 0) {
                                        continue;
                                    }
                                    assert left.getCE(leftLimit) == right.getCE(rightLimit);
                                    if (p == 1L) {
                                        break Label_0614;
                                    }
                                    leftStart = leftLimit + 1;
                                    rightStart = rightLimit + 1;
                                    break;
                                }
                            }
                        }
                    }
                }
                if ((options & 0x400) != 0x0) {
                    final int strength = CollationSettings.getStrength(options);
                    int leftIndex3 = 0;
                    int rightIndex3 = 0;
                    while (true) {
                        int leftCase;
                        int leftLower32;
                        int rightCase;
                        if (strength == 0) {
                            long ce3;
                            do {
                                ce3 = left.getCE(leftIndex3++);
                                leftCase = (int)ce3;
                            } while (ce3 >>> 32 == 0L || leftCase == 0);
                            leftLower32 = leftCase;
                            leftCase &= 0xC000;
                            do {
                                ce3 = right.getCE(rightIndex3++);
                                rightCase = (int)ce3;
                            } while (ce3 >>> 32 == 0L || rightCase == 0);
                            rightCase &= 0xC000;
                        }
                        else {
                            do {
                                leftCase = (int)left.getCE(leftIndex3++);
                            } while ((leftCase & 0xFFFF0000) == 0x0);
                            leftLower32 = leftCase;
                            leftCase &= 0xC000;
                            do {
                                rightCase = (int)right.getCE(rightIndex3++);
                            } while ((rightCase & 0xFFFF0000) == 0x0);
                            rightCase &= 0xC000;
                        }
                        if (leftCase != rightCase) {
                            if ((options & 0x100) == 0x0) {
                                return (leftCase < rightCase) ? -1 : 1;
                            }
                            return (leftCase < rightCase) ? 1 : -1;
                        }
                        else {
                            if (leftLower32 >>> 16 == 256) {
                                break;
                            }
                            continue;
                        }
                    }
                }
                if (CollationSettings.getStrength(options) <= 1) {
                    return 0;
                }
                final int tertiaryMask = CollationSettings.getTertiaryMask(options);
                int leftIndex3 = 0;
                int rightIndex3 = 0;
                int anyQuaternaries = 0;
                while (true) {
                    final int leftLower32 = (int)left.getCE(leftIndex3++);
                    anyQuaternaries |= leftLower32;
                    assert (leftLower32 & 0xC0C0) == 0x0;
                    int leftTertiary = leftLower32 & tertiaryMask;
                    if (leftTertiary == 0) {
                        continue;
                    }
                    int rightTertiary;
                    int rightLower32;
                    do {
                        rightLower32 = (int)right.getCE(rightIndex3++);
                        anyQuaternaries |= rightLower32;
                        assert (rightLower32 & 0xC0C0) == 0x0;
                        rightTertiary = (rightLower32 & tertiaryMask);
                    } while (rightTertiary == 0);
                    if (leftTertiary != rightTertiary) {
                        if (CollationSettings.sortsTertiaryUpperCaseFirst(options)) {
                            if (leftTertiary > 256) {
                                if ((leftLower32 & 0xFFFF0000) != 0x0) {
                                    leftTertiary ^= 0xC000;
                                }
                                else {
                                    leftTertiary += 16384;
                                }
                            }
                            if (rightTertiary > 256) {
                                if ((rightLower32 & 0xFFFF0000) != 0x0) {
                                    rightTertiary ^= 0xC000;
                                }
                                else {
                                    rightTertiary += 16384;
                                }
                            }
                        }
                        return (leftTertiary < rightTertiary) ? -1 : 1;
                    }
                    if (leftTertiary != 256) {
                        continue;
                    }
                    if (CollationSettings.getStrength(options) <= 2) {
                        return 0;
                    }
                    if (!anyVariable && (anyQuaternaries & 0xC0) == 0x0) {
                        return 0;
                    }
                    leftIndex3 = 0;
                    rightIndex3 = 0;
                    while (true) {
                        final long ce3 = left.getCE(leftIndex3++);
                        long leftQuaternary = ce3 & 0xFFFFL;
                        if (leftQuaternary <= 256L) {
                            leftQuaternary = ce3 >>> 32;
                        }
                        else {
                            leftQuaternary |= 0xFFFFFF3FL;
                        }
                        if (leftQuaternary != 0L) {
                            long rightQuaternary;
                            do {
                                final long ce4 = right.getCE(rightIndex3++);
                                rightQuaternary = (ce4 & 0xFFFFL);
                                if (rightQuaternary <= 256L) {
                                    rightQuaternary = ce4 >>> 32;
                                }
                                else {
                                    rightQuaternary |= 0xFFFFFF3FL;
                                }
                            } while (rightQuaternary == 0L);
                            if (leftQuaternary != rightQuaternary) {
                                if (settings.hasReordering()) {
                                    leftQuaternary = settings.reorder(leftQuaternary);
                                    rightQuaternary = settings.reorder(rightQuaternary);
                                }
                                return (leftQuaternary < rightQuaternary) ? -1 : 1;
                            }
                            if (leftQuaternary == 1L) {
                                return 0;
                            }
                            continue;
                        }
                    }
                }
            }
        }
    }
}
