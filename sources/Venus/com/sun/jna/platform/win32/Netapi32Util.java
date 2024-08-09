/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform.win32;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.DsGetDC;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.LMAccess;
import com.sun.jna.platform.win32.Netapi32;
import com.sun.jna.platform.win32.Ole32Util;
import com.sun.jna.platform.win32.Secur32Util;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
import java.util.ArrayList;

public abstract class Netapi32Util {
    public static String getDCName() {
        return Netapi32Util.getDCName(null, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getDCName(String string, String string2) {
        PointerByReference pointerByReference = new PointerByReference();
        try {
            int n = Netapi32.INSTANCE.NetGetDCName(string2, string, pointerByReference);
            if (0 != n) {
                throw new Win32Exception(n);
            }
            String string3 = pointerByReference.getValue().getString(0L, false);
            return string3;
        } finally {
            if (0 != Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue())) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }
        }
    }

    public static int getJoinStatus() {
        return Netapi32Util.getJoinStatus(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int getJoinStatus(String string) {
        PointerByReference pointerByReference = new PointerByReference();
        IntByReference intByReference = new IntByReference();
        try {
            int n = Netapi32.INSTANCE.NetGetJoinInformation(string, pointerByReference, intByReference);
            if (0 != n) {
                throw new Win32Exception(n);
            }
            int n2 = intByReference.getValue();
            return n2;
        } finally {
            int n;
            if (pointerByReference.getPointer() != null && 0 != (n = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue()))) {
                throw new Win32Exception(n);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getDomainName(String string) {
        PointerByReference pointerByReference = new PointerByReference();
        IntByReference intByReference = new IntByReference();
        try {
            int n = Netapi32.INSTANCE.NetGetJoinInformation(string, pointerByReference, intByReference);
            if (0 != n) {
                throw new Win32Exception(n);
            }
            String string2 = pointerByReference.getValue().getString(0L, false);
            return string2;
        } finally {
            int n;
            if (pointerByReference.getPointer() != null && 0 != (n = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue()))) {
                throw new Win32Exception(n);
            }
        }
    }

    public static LocalGroup[] getLocalGroups() {
        return Netapi32Util.getLocalGroups(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static LocalGroup[] getLocalGroups(String string) {
        PointerByReference pointerByReference = new PointerByReference();
        IntByReference intByReference = new IntByReference();
        IntByReference intByReference2 = new IntByReference();
        try {
            int n = Netapi32.INSTANCE.NetLocalGroupEnum(string, 1, pointerByReference, -1, intByReference, intByReference2, null);
            if (0 != n || pointerByReference.getValue() == Pointer.NULL) {
                throw new Win32Exception(n);
            }
            LMAccess.LOCALGROUP_INFO_1 lOCALGROUP_INFO_1 = new LMAccess.LOCALGROUP_INFO_1(pointerByReference.getValue());
            LMAccess.LOCALGROUP_INFO_1[] lOCALGROUP_INFO_1Array = (LMAccess.LOCALGROUP_INFO_1[])lOCALGROUP_INFO_1.toArray(intByReference.getValue());
            ArrayList<LocalGroup> arrayList = new ArrayList<LocalGroup>();
            for (LMAccess.LOCALGROUP_INFO_1 lOCALGROUP_INFO_12 : lOCALGROUP_INFO_1Array) {
                LocalGroup localGroup = new LocalGroup();
                localGroup.name = lOCALGROUP_INFO_12.lgrui1_name.toString();
                localGroup.comment = lOCALGROUP_INFO_12.lgrui1_comment.toString();
                arrayList.add(localGroup);
            }
            Object[] objectArray = arrayList.toArray(new LocalGroup[0]);
            return objectArray;
        } finally {
            int n;
            if (pointerByReference.getValue() != Pointer.NULL && 0 != (n = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue()))) {
                throw new Win32Exception(n);
            }
        }
    }

    public static Group[] getGlobalGroups() {
        return Netapi32Util.getGlobalGroups(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Group[] getGlobalGroups(String string) {
        PointerByReference pointerByReference = new PointerByReference();
        IntByReference intByReference = new IntByReference();
        IntByReference intByReference2 = new IntByReference();
        try {
            int n = Netapi32.INSTANCE.NetGroupEnum(string, 1, pointerByReference, -1, intByReference, intByReference2, null);
            if (0 != n || pointerByReference.getValue() == Pointer.NULL) {
                throw new Win32Exception(n);
            }
            LMAccess.GROUP_INFO_1 gROUP_INFO_1 = new LMAccess.GROUP_INFO_1(pointerByReference.getValue());
            LMAccess.GROUP_INFO_1[] gROUP_INFO_1Array = (LMAccess.GROUP_INFO_1[])gROUP_INFO_1.toArray(intByReference.getValue());
            ArrayList<LocalGroup> arrayList = new ArrayList<LocalGroup>();
            for (LMAccess.GROUP_INFO_1 gROUP_INFO_12 : gROUP_INFO_1Array) {
                LocalGroup localGroup = new LocalGroup();
                localGroup.name = gROUP_INFO_12.grpi1_name.toString();
                localGroup.comment = gROUP_INFO_12.grpi1_comment.toString();
                arrayList.add(localGroup);
            }
            Object[] objectArray = arrayList.toArray(new LocalGroup[0]);
            return objectArray;
        } finally {
            int n;
            if (pointerByReference.getValue() != Pointer.NULL && 0 != (n = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue()))) {
                throw new Win32Exception(n);
            }
        }
    }

    public static User[] getUsers() {
        return Netapi32Util.getUsers(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static User[] getUsers(String string) {
        PointerByReference pointerByReference = new PointerByReference();
        IntByReference intByReference = new IntByReference();
        IntByReference intByReference2 = new IntByReference();
        try {
            int n = Netapi32.INSTANCE.NetUserEnum(string, 1, 0, pointerByReference, -1, intByReference, intByReference2, null);
            if (0 != n || pointerByReference.getValue() == Pointer.NULL) {
                throw new Win32Exception(n);
            }
            LMAccess.USER_INFO_1 uSER_INFO_1 = new LMAccess.USER_INFO_1(pointerByReference.getValue());
            LMAccess.USER_INFO_1[] uSER_INFO_1Array = (LMAccess.USER_INFO_1[])uSER_INFO_1.toArray(intByReference.getValue());
            ArrayList<User> arrayList = new ArrayList<User>();
            for (LMAccess.USER_INFO_1 uSER_INFO_12 : uSER_INFO_1Array) {
                User user = new User();
                user.name = uSER_INFO_12.usri1_name.toString();
                arrayList.add(user);
            }
            Object[] objectArray = arrayList.toArray(new User[0]);
            return objectArray;
        } finally {
            int n;
            if (pointerByReference.getValue() != Pointer.NULL && 0 != (n = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue()))) {
                throw new Win32Exception(n);
            }
        }
    }

    public static Group[] getCurrentUserLocalGroups() {
        return Netapi32Util.getUserLocalGroups(Secur32Util.getUserNameEx(2));
    }

    public static Group[] getUserLocalGroups(String string) {
        return Netapi32Util.getUserLocalGroups(string, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Group[] getUserLocalGroups(String string, String string2) {
        PointerByReference pointerByReference = new PointerByReference();
        IntByReference intByReference = new IntByReference();
        IntByReference intByReference2 = new IntByReference();
        try {
            int n = Netapi32.INSTANCE.NetUserGetLocalGroups(string2, string, 0, 0, pointerByReference, -1, intByReference, intByReference2);
            if (n != 0) {
                throw new Win32Exception(n);
            }
            LMAccess.LOCALGROUP_USERS_INFO_0 lOCALGROUP_USERS_INFO_0 = new LMAccess.LOCALGROUP_USERS_INFO_0(pointerByReference.getValue());
            LMAccess.LOCALGROUP_USERS_INFO_0[] lOCALGROUP_USERS_INFO_0Array = (LMAccess.LOCALGROUP_USERS_INFO_0[])lOCALGROUP_USERS_INFO_0.toArray(intByReference.getValue());
            ArrayList<LocalGroup> arrayList = new ArrayList<LocalGroup>();
            for (LMAccess.LOCALGROUP_USERS_INFO_0 lOCALGROUP_USERS_INFO_02 : lOCALGROUP_USERS_INFO_0Array) {
                LocalGroup localGroup = new LocalGroup();
                localGroup.name = lOCALGROUP_USERS_INFO_02.lgrui0_name.toString();
                arrayList.add(localGroup);
            }
            Object[] objectArray = arrayList.toArray(new Group[0]);
            return objectArray;
        } finally {
            int n;
            if (pointerByReference.getValue() != Pointer.NULL && 0 != (n = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue()))) {
                throw new Win32Exception(n);
            }
        }
    }

    public static Group[] getUserGroups(String string) {
        return Netapi32Util.getUserGroups(string, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Group[] getUserGroups(String string, String string2) {
        PointerByReference pointerByReference = new PointerByReference();
        IntByReference intByReference = new IntByReference();
        IntByReference intByReference2 = new IntByReference();
        try {
            int n = Netapi32.INSTANCE.NetUserGetGroups(string2, string, 0, pointerByReference, -1, intByReference, intByReference2);
            if (n != 0) {
                throw new Win32Exception(n);
            }
            LMAccess.GROUP_USERS_INFO_0 gROUP_USERS_INFO_0 = new LMAccess.GROUP_USERS_INFO_0(pointerByReference.getValue());
            LMAccess.GROUP_USERS_INFO_0[] gROUP_USERS_INFO_0Array = (LMAccess.GROUP_USERS_INFO_0[])gROUP_USERS_INFO_0.toArray(intByReference.getValue());
            ArrayList<Group> arrayList = new ArrayList<Group>();
            for (LMAccess.GROUP_USERS_INFO_0 gROUP_USERS_INFO_02 : gROUP_USERS_INFO_0Array) {
                Group group = new Group();
                group.name = gROUP_USERS_INFO_02.grui0_name.toString();
                arrayList.add(group);
            }
            Object[] objectArray = arrayList.toArray(new Group[0]);
            return objectArray;
        } finally {
            int n;
            if (pointerByReference.getValue() != Pointer.NULL && 0 != (n = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue()))) {
                throw new Win32Exception(n);
            }
        }
    }

    public static DomainController getDC() {
        DsGetDC.PDOMAIN_CONTROLLER_INFO.ByReference byReference = new DsGetDC.PDOMAIN_CONTROLLER_INFO.ByReference();
        int n = Netapi32.INSTANCE.DsGetDcName(null, null, null, null, 0, byReference);
        if (0 != n) {
            throw new Win32Exception(n);
        }
        DomainController domainController = new DomainController();
        domainController.address = byReference.dci.DomainControllerAddress.toString();
        domainController.addressType = byReference.dci.DomainControllerAddressType;
        domainController.clientSiteName = byReference.dci.ClientSiteName.toString();
        domainController.dnsForestName = byReference.dci.DnsForestName.toString();
        domainController.domainGuid = byReference.dci.DomainGuid;
        domainController.domainName = byReference.dci.DomainName.toString();
        domainController.flags = byReference.dci.Flags;
        domainController.name = byReference.dci.DomainControllerName.toString();
        n = Netapi32.INSTANCE.NetApiBufferFree(byReference.dci.getPointer());
        if (0 != n) {
            throw new Win32Exception(n);
        }
        return domainController;
    }

    public static DomainTrust[] getDomainTrusts() {
        return Netapi32Util.getDomainTrusts(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DomainTrust[] getDomainTrusts(String string) {
        DsGetDC.PDS_DOMAIN_TRUSTS.ByReference byReference = new DsGetDC.PDS_DOMAIN_TRUSTS.ByReference();
        NativeLongByReference nativeLongByReference = new NativeLongByReference();
        int n = Netapi32.INSTANCE.DsEnumerateDomainTrusts(string, new NativeLong(63L), byReference, nativeLongByReference);
        if (0 != n) {
            throw new Win32Exception(n);
        }
        try {
            int n2 = nativeLongByReference.getValue().intValue();
            ArrayList<DomainTrust> arrayList = new ArrayList<DomainTrust>(n2);
            for (DsGetDC.DS_DOMAIN_TRUSTS dS_DOMAIN_TRUSTS : byReference.getTrusts(n2)) {
                DomainTrust domainTrust = new DomainTrust();
                domainTrust.DnsDomainName = dS_DOMAIN_TRUSTS.DnsDomainName.toString();
                domainTrust.NetbiosDomainName = dS_DOMAIN_TRUSTS.NetbiosDomainName.toString();
                domainTrust.DomainSid = dS_DOMAIN_TRUSTS.DomainSid;
                domainTrust.DomainSidString = Advapi32Util.convertSidToStringSid(dS_DOMAIN_TRUSTS.DomainSid);
                domainTrust.DomainGuid = dS_DOMAIN_TRUSTS.DomainGuid;
                domainTrust.DomainGuidString = Ole32Util.getStringFromGUID(dS_DOMAIN_TRUSTS.DomainGuid);
                DomainTrust.access$002(domainTrust, dS_DOMAIN_TRUSTS.Flags.intValue());
                arrayList.add(domainTrust);
            }
            Object[] objectArray = arrayList.toArray(new DomainTrust[0]);
            return objectArray;
        } finally {
            n = Netapi32.INSTANCE.NetApiBufferFree(byReference.getPointer());
            if (0 != n) {
                throw new Win32Exception(n);
            }
        }
    }

    public static UserInfo getUserInfo(String string) {
        return Netapi32Util.getUserInfo(string, Netapi32Util.getDCName());
    }

    public static UserInfo getUserInfo(String string, String string2) {
        PointerByReference pointerByReference = new PointerByReference();
        int n = -1;
        try {
            n = Netapi32.INSTANCE.NetUserGetInfo(Netapi32Util.getDCName(), string, 23, pointerByReference);
            if (n == 0) {
                LMAccess.USER_INFO_23 uSER_INFO_23 = new LMAccess.USER_INFO_23(pointerByReference.getValue());
                UserInfo userInfo = new UserInfo();
                userInfo.comment = uSER_INFO_23.usri23_comment.toString();
                userInfo.flags = uSER_INFO_23.usri23_flags;
                userInfo.fullName = uSER_INFO_23.usri23_full_name.toString();
                userInfo.name = uSER_INFO_23.usri23_name.toString();
                userInfo.sidString = Advapi32Util.convertSidToStringSid(uSER_INFO_23.usri23_user_sid);
                userInfo.sid = uSER_INFO_23.usri23_user_sid;
                UserInfo userInfo2 = userInfo;
                return userInfo2;
            }
            throw new Win32Exception(n);
        } finally {
            if (pointerByReference.getValue() != Pointer.NULL) {
                Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue());
            }
        }
    }

    public static class DomainTrust {
        public String NetbiosDomainName;
        public String DnsDomainName;
        public WinNT.PSID DomainSid;
        public String DomainSidString;
        public Guid.GUID DomainGuid;
        public String DomainGuidString;
        private int flags;

        public boolean isInForest() {
            return (this.flags & 1) != 0;
        }

        public boolean isOutbound() {
            return (this.flags & 2) != 0;
        }

        public boolean isRoot() {
            return (this.flags & 4) != 0;
        }

        public boolean isPrimary() {
            return (this.flags & 8) != 0;
        }

        public boolean isNativeMode() {
            return (this.flags & 0x10) != 0;
        }

        public boolean isInbound() {
            return (this.flags & 0x20) != 0;
        }

        static int access$002(DomainTrust domainTrust, int n) {
            domainTrust.flags = n;
            return domainTrust.flags;
        }
    }

    public static class DomainController {
        public String name;
        public String address;
        public int addressType;
        public Guid.GUID domainGuid;
        public String domainName;
        public String dnsForestName;
        public int flags;
        public String clientSiteName;
    }

    public static class LocalGroup
    extends Group {
        public String comment;
    }

    public static class UserInfo
    extends User {
        public String fullName;
        public String sidString;
        public WinNT.PSID sid;
        public int flags;
    }

    public static class User {
        public String name;
        public String comment;
    }

    public static class Group {
        public String name;
    }
}

