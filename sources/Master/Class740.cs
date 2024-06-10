using System;
using System.Runtime.InteropServices;

namespace Client
{
	// Token: 0x02000302 RID: 770
	internal static class Class740
	{
		// Token: 0x060037AA RID: 14250
		[DllImport("kernel32", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern IntPtr CreateFile(string string_0, int int_5, int int_6, IntPtr intptr_3, int int_7, int int_8, IntPtr intptr_4);

		// Token: 0x060037AB RID: 14251
		[DllImport("kernel32", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern IntPtr CreateFileMapping(IntPtr intptr_3, IntPtr intptr_4, Class740.Enum2 enum2_0, int int_5, int int_6, string string_0);

		// Token: 0x060037AC RID: 14252
		[DllImport("kernel32", SetLastError = true)]
		public static extern IntPtr MapViewOfFile(IntPtr intptr_3, Class740.Enum3 enum3_0, int int_5, int int_6, IntPtr intptr_4);

		// Token: 0x060037AD RID: 14253
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool UnmapViewOfFile(IntPtr intptr_3);

		// Token: 0x060037AE RID: 14254
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool CloseHandle(IntPtr intptr_3);

		// Token: 0x060037AF RID: 14255
		[DllImport("kernel32", SetLastError = true)]
		public static extern uint GetFileSize(IntPtr intptr_3, IntPtr intptr_4);

		// Token: 0x060037B0 RID: 14256
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool VirtualProtect(IntPtr intptr_3, UIntPtr uintptr_0, Class740.Enum2 enum2_0, out Class740.Enum2 enum2_1);

		// Token: 0x060037B1 RID: 14257
		[DllImport("kernel32")]
		public static extern bool IsDebuggerPresent();

		// Token: 0x060037B2 RID: 14258
		[DllImport("kernel32")]
		public static extern bool CheckRemoteDebuggerPresent();

		// Token: 0x060037B3 RID: 14259
		[DllImport("ntdll")]
		public static extern int NtQueryInformationProcess(IntPtr intptr_3, int int_5, byte[] byte_0, uint uint_0, out uint uint_1);

		// Token: 0x040000EB RID: 235
		public const int int_0 = -2147483648;

		// Token: 0x040000EC RID: 236
		public const int int_1 = 3;

		// Token: 0x040000ED RID: 237
		public const int int_2 = 128;

		// Token: 0x040000EE RID: 238
		public const int int_3 = 1;

		// Token: 0x040000EF RID: 239
		public const int int_4 = 2;

		// Token: 0x040000F0 RID: 240
		public static readonly IntPtr intptr_0 = new IntPtr(-1);

		// Token: 0x040000F1 RID: 241
		public static readonly IntPtr intptr_1 = IntPtr.Zero;

		// Token: 0x040000F2 RID: 242
		public static readonly IntPtr intptr_2 = new IntPtr(-1);

		// Token: 0x02000303 RID: 771
		public enum Enum2 : uint
		{
			// Token: 0x040000F4 RID: 244
			const_0 = 1U,
			// Token: 0x040000F5 RID: 245
			const_1,
			// Token: 0x040000F6 RID: 246
			const_2 = 4U,
			// Token: 0x040000F7 RID: 247
			const_3 = 8U,
			// Token: 0x040000F8 RID: 248
			const_4 = 16U,
			// Token: 0x040000F9 RID: 249
			const_5 = 32U,
			// Token: 0x040000FA RID: 250
			const_6 = 64U,
			// Token: 0x040000FB RID: 251
			const_7 = 256U
		}

		// Token: 0x02000304 RID: 772
		public enum Enum3 : uint
		{
			// Token: 0x040000FD RID: 253
			const_0 = 1U,
			// Token: 0x040000FE RID: 254
			const_1,
			// Token: 0x040000FF RID: 255
			const_2 = 4U,
			// Token: 0x04000100 RID: 256
			const_3 = 31U
		}
	}
}
