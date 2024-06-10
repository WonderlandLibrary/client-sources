using System;
using System.Runtime.InteropServices;

// Token: 0x02000002 RID: 2
public class EnablePrivileges
{
	// Token: 0x06000001 RID: 1
	[DllImport("advapi32.dll", SetLastError = true)]
	[return: MarshalAs(UnmanagedType.Bool)]
	private static extern bool OpenProcessToken(IntPtr ProcessHandle, uint DesiredAccess, out IntPtr TokenHandle);

	// Token: 0x06000002 RID: 2
	[DllImport("kernel32.dll", SetLastError = true)]
	private static extern IntPtr GetCurrentProcess();

	// Token: 0x06000003 RID: 3
	[DllImport("advapi32.dll", CharSet = CharSet.Auto, SetLastError = true)]
	[return: MarshalAs(UnmanagedType.Bool)]
	private static extern bool LookupPrivilegeValue(string lpSystemName, string lpName, out EnablePrivileges.LUID lpLuid);

	// Token: 0x06000004 RID: 4
	[DllImport("kernel32.dll", SetLastError = true)]
	private static extern bool CloseHandle(IntPtr hHandle);

	// Token: 0x06000005 RID: 5
	[DllImport("advapi32.dll", SetLastError = true)]
	[return: MarshalAs(UnmanagedType.Bool)]
	private static extern bool AdjustTokenPrivileges(IntPtr TokenHandle, [MarshalAs(UnmanagedType.Bool)] bool DisableAllPrivileges, ref EnablePrivileges.TOKEN_PRIVILEGES NewState, uint Zero, IntPtr Null1, IntPtr Null2);

	// Token: 0x06000006 RID: 6 RVA: 0x00002150 File Offset: 0x00000350
	public static void GoDebugPriv()
	{
		IntPtr intPtr;
		bool flag = !EnablePrivileges.OpenProcessToken(EnablePrivileges.GetCurrentProcess(), EnablePrivileges.TOKEN_ADJUST_PRIVILEGES | EnablePrivileges.TOKEN_QUERY, out intPtr);
		bool flag2 = !flag;
		bool flag3 = flag2;
		if (flag3)
		{
			EnablePrivileges.LUID luid;
			bool flag4 = !EnablePrivileges.LookupPrivilegeValue(null, "SeDebugPrivilege", out luid);
			bool flag5 = flag4;
			bool flag6 = flag5;
			if (flag6)
			{
				EnablePrivileges.CloseHandle(intPtr);
			}
			else
			{
				EnablePrivileges.TOKEN_PRIVILEGES token_PRIVILEGES;
				token_PRIVILEGES.PrivilegeCount = 1U;
				token_PRIVILEGES.Luid = luid;
				token_PRIVILEGES.Attributes = 2U;
				EnablePrivileges.AdjustTokenPrivileges(intPtr, false, ref token_PRIVILEGES, 0U, IntPtr.Zero, IntPtr.Zero);
				EnablePrivileges.CloseHandle(intPtr);
			}
		}
	}

	// Token: 0x04000001 RID: 1
	private static uint STANDARD_RIGHTS_REQUIRED = 983040U;

	// Token: 0x04000002 RID: 2
	private static uint STANDARD_RIGHTS_READ = 131072U;

	// Token: 0x04000003 RID: 3
	private static uint TOKEN_ASSIGN_PRIMARY = 1U;

	// Token: 0x04000004 RID: 4
	private static uint TOKEN_DUPLICATE = 2U;

	// Token: 0x04000005 RID: 5
	private static uint TOKEN_IMPERSONATE = 4U;

	// Token: 0x04000006 RID: 6
	private static uint TOKEN_QUERY = 8U;

	// Token: 0x04000007 RID: 7
	private static uint TOKEN_QUERY_SOURCE = 16U;

	// Token: 0x04000008 RID: 8
	private static uint TOKEN_ADJUST_PRIVILEGES = 32U;

	// Token: 0x04000009 RID: 9
	private static uint TOKEN_ADJUST_GROUPS = 64U;

	// Token: 0x0400000A RID: 10
	private static uint TOKEN_ADJUST_DEFAULT = 128U;

	// Token: 0x0400000B RID: 11
	private static uint TOKEN_ADJUST_SESSIONID = 256U;

	// Token: 0x0400000C RID: 12
	private static uint TOKEN_READ = EnablePrivileges.STANDARD_RIGHTS_READ | EnablePrivileges.TOKEN_QUERY;

	// Token: 0x0400000D RID: 13
	private static uint TOKEN_ALL_ACCESS = EnablePrivileges.STANDARD_RIGHTS_REQUIRED | EnablePrivileges.TOKEN_ASSIGN_PRIMARY | EnablePrivileges.TOKEN_DUPLICATE | EnablePrivileges.TOKEN_IMPERSONATE | EnablePrivileges.TOKEN_QUERY | EnablePrivileges.TOKEN_QUERY_SOURCE | EnablePrivileges.TOKEN_ADJUST_PRIVILEGES | EnablePrivileges.TOKEN_ADJUST_GROUPS | EnablePrivileges.TOKEN_ADJUST_DEFAULT | EnablePrivileges.TOKEN_ADJUST_SESSIONID;

	// Token: 0x0400000E RID: 14
	public const string SE_ASSIGNPRIMARYTOKEN_NAME = "SeAssignPrimaryTokenPrivilege";

	// Token: 0x0400000F RID: 15
	public const string SE_AUDIT_NAME = "SeAuditPrivilege";

	// Token: 0x04000010 RID: 16
	public const string SE_BACKUP_NAME = "SeBackupPrivilege";

	// Token: 0x04000011 RID: 17
	public const string SE_CHANGE_NOTIFY_NAME = "SeChangeNotifyPrivilege";

	// Token: 0x04000012 RID: 18
	public const string SE_CREATE_GLOBAL_NAME = "SeCreateGlobalPrivilege";

	// Token: 0x04000013 RID: 19
	public const string SE_CREATE_PAGEFILE_NAME = "SeCreatePagefilePrivilege";

	// Token: 0x04000014 RID: 20
	public const string SE_CREATE_PERMANENT_NAME = "SeCreatePermanentPrivilege";

	// Token: 0x04000015 RID: 21
	public const string SE_CREATE_SYMBOLIC_LINK_NAME = "SeCreateSymbolicLinkPrivilege";

	// Token: 0x04000016 RID: 22
	public const string SE_CREATE_TOKEN_NAME = "SeCreateTokenPrivilege";

	// Token: 0x04000017 RID: 23
	public const string SE_DEBUG_NAME = "SeDebugPrivilege";

	// Token: 0x04000018 RID: 24
	public const string SE_ENABLE_DELEGATION_NAME = "SeEnableDelegationPrivilege";

	// Token: 0x04000019 RID: 25
	public const string SE_IMPERSONATE_NAME = "SeImpersonatePrivilege";

	// Token: 0x0400001A RID: 26
	public const string SE_INC_BASE_PRIORITY_NAME = "SeIncreaseBasePriorityPrivilege";

	// Token: 0x0400001B RID: 27
	public const string SE_INCREASE_QUOTA_NAME = "SeIncreaseQuotaPrivilege";

	// Token: 0x0400001C RID: 28
	public const string SE_INC_WORKING_SET_NAME = "SeIncreaseWorkingSetPrivilege";

	// Token: 0x0400001D RID: 29
	public const string SE_LOAD_DRIVER_NAME = "SeLoadDriverPrivilege";

	// Token: 0x0400001E RID: 30
	public const string SE_LOCK_MEMORY_NAME = "SeLockMemoryPrivilege";

	// Token: 0x0400001F RID: 31
	public const string SE_MACHINE_ACCOUNT_NAME = "SeMachineAccountPrivilege";

	// Token: 0x04000020 RID: 32
	public const string SE_MANAGE_VOLUME_NAME = "SeManageVolumePrivilege";

	// Token: 0x04000021 RID: 33
	public const string SE_PROF_SINGLE_PROCESS_NAME = "SeProfileSingleProcessPrivilege";

	// Token: 0x04000022 RID: 34
	public const string SE_RELABEL_NAME = "SeRelabelPrivilege";

	// Token: 0x04000023 RID: 35
	public const string SE_REMOTE_SHUTDOWN_NAME = "SeRemoteShutdownPrivilege";

	// Token: 0x04000024 RID: 36
	public const string SE_RESTORE_NAME = "SeRestorePrivilege";

	// Token: 0x04000025 RID: 37
	public const string SE_SECURITY_NAME = "SeSecurityPrivilege";

	// Token: 0x04000026 RID: 38
	public const string SE_SHUTDOWN_NAME = "SeShutdownPrivilege";

	// Token: 0x04000027 RID: 39
	public const string SE_SYNC_AGENT_NAME = "SeSyncAgentPrivilege";

	// Token: 0x04000028 RID: 40
	public const string SE_SYSTEM_ENVIRONMENT_NAME = "SeSystemEnvironmentPrivilege";

	// Token: 0x04000029 RID: 41
	public const string SE_SYSTEM_PROFILE_NAME = "SeSystemProfilePrivilege";

	// Token: 0x0400002A RID: 42
	public const string SE_SYSTEMTIME_NAME = "SeSystemtimePrivilege";

	// Token: 0x0400002B RID: 43
	public const string SE_TAKE_OWNERSHIP_NAME = "SeTakeOwnershipPrivilege";

	// Token: 0x0400002C RID: 44
	public const string SE_TCB_NAME = "SeTcbPrivilege";

	// Token: 0x0400002D RID: 45
	public const string SE_TIME_ZONE_NAME = "SeTimeZonePrivilege";

	// Token: 0x0400002E RID: 46
	public const string SE_TRUSTED_CREDMAN_ACCESS_NAME = "SeTrustedCredManAccessPrivilege";

	// Token: 0x0400002F RID: 47
	public const string SE_UNDOCK_NAME = "SeUndockPrivilege";

	// Token: 0x04000030 RID: 48
	public const string SE_UNSOLICITED_INPUT_NAME = "SeUnsolicitedInputPrivilege";

	// Token: 0x04000031 RID: 49
	public const uint SE_PRIVILEGE_ENABLED_BY_DEFAULT = 1U;

	// Token: 0x04000032 RID: 50
	public const uint SE_PRIVILEGE_ENABLED = 2U;

	// Token: 0x04000033 RID: 51
	public const uint SE_PRIVILEGE_REMOVED = 4U;

	// Token: 0x04000034 RID: 52
	public const uint SE_PRIVILEGE_USED_FOR_ACCESS = 2147483648U;

	// Token: 0x02000003 RID: 3
	public struct LUID
	{
		// Token: 0x04000035 RID: 53
		public uint LowPart;

		// Token: 0x04000036 RID: 54
		public int HighPart;
	}

	// Token: 0x02000004 RID: 4
	public struct TOKEN_PRIVILEGES
	{
		// Token: 0x04000037 RID: 55
		public uint PrivilegeCount;

		// Token: 0x04000038 RID: 56
		public EnablePrivileges.LUID Luid;

		// Token: 0x04000039 RID: 57
		public uint Attributes;
	}

	// Token: 0x02000005 RID: 5
	public struct LUID_AND_ATTRIBUTES
	{
		// Token: 0x0400003A RID: 58
		public EnablePrivileges.LUID Luid;

		// Token: 0x0400003B RID: 59
		public uint Attributes;
	}
}
