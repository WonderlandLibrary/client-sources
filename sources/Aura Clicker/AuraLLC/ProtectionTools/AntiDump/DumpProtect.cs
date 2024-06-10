using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace AuraLLC.ProtectionTools.AntiDump
{
	// Token: 0x0200000E RID: 14
	internal class DumpProtect
	{
		// Token: 0x06000014 RID: 20
		[DllImport("kernel32.dll")]
		private static extern IntPtr ZeroMemory(IntPtr intptr_0, IntPtr intptr_1);

		// Token: 0x06000015 RID: 21
		[DllImport("kernel32.dll")]
		private static extern IntPtr VirtualProtect(IntPtr intptr_0, IntPtr intptr_1, IntPtr intptr_2, ref IntPtr intptr_3);

		// Token: 0x06000016 RID: 22 RVA: 0x000034CC File Offset: 0x000016CC
		private static void EraseSection(IntPtr intptr_0, int int_0)
		{
			IntPtr intptr_ = (IntPtr)int_0;
			IntPtr intptr_2 = (IntPtr)0;
			DumpProtect.VirtualProtect(intptr_0, intptr_, (IntPtr)64, ref intptr_2);
			DumpProtect.ZeroMemory(intptr_0, intptr_);
			IntPtr intPtr = (IntPtr)0;
			DumpProtect.VirtualProtect(intptr_0, intptr_, intptr_2, ref intPtr);
		}

		// Token: 0x06000017 RID: 23 RVA: 0x00003514 File Offset: 0x00001714
		public static void AntiDump()
		{
			IntPtr baseAddress = Process.GetCurrentProcess().MainModule.BaseAddress;
			int num = Marshal.ReadInt32((IntPtr)(baseAddress.ToInt32() + 60));
			short num2 = Marshal.ReadInt16((IntPtr)(baseAddress.ToInt32() + num + 6));
			DumpProtect.EraseSection(baseAddress, 30);
			for (int i = 0; i < DumpProtect.peheaderdwords.Length; i++)
			{
				DumpProtect.EraseSection((IntPtr)(baseAddress.ToInt32() + num + DumpProtect.peheaderdwords[i]), 4);
			}
			for (int j = 0; j < DumpProtect.peheaderwords.Length; j++)
			{
				DumpProtect.EraseSection((IntPtr)(baseAddress.ToInt32() + num + DumpProtect.peheaderwords[j]), 2);
			}
			for (int k = 0; k < DumpProtect.peheaderbytes.Length; k++)
			{
				DumpProtect.EraseSection((IntPtr)(baseAddress.ToInt32() + num + DumpProtect.peheaderbytes[k]), 1);
			}
			int l = 0;
			int num3 = 0;
			while (l <= (int)num2)
			{
				if (num3 == 0)
				{
					DumpProtect.EraseSection((IntPtr)(baseAddress.ToInt32() + num + 250 + 40 * l + 32), 2);
				}
				DumpProtect.EraseSection((IntPtr)(baseAddress.ToInt32() + num + 250 + 40 * l + DumpProtect.sectiontabledwords[num3]), 4);
				num3++;
				if (num3 == DumpProtect.sectiontabledwords.Length)
				{
					l++;
					num3 = 0;
				}
			}
		}

		// Token: 0x04000262 RID: 610
		private static int[] sectiontabledwords = new int[]
		{
			8,
			12,
			16,
			20,
			24,
			28,
			36
		};

		// Token: 0x04000263 RID: 611
		private static int[] peheaderbytes = new int[]
		{
			26,
			27
		};

		// Token: 0x04000264 RID: 612
		private static int[] peheaderwords = new int[]
		{
			4,
			22,
			24,
			64,
			66,
			68,
			70,
			72,
			74,
			76,
			92,
			94
		};

		// Token: 0x04000265 RID: 613
		private static int[] peheaderdwords = new int[]
		{
			0,
			8,
			12,
			16,
			22,
			28,
			32,
			40,
			44,
			52,
			60,
			76,
			80,
			84,
			88,
			96,
			100,
			104,
			108,
			112,
			116,
			260,
			264,
			268,
			272,
			276,
			284
		};
	}
}
