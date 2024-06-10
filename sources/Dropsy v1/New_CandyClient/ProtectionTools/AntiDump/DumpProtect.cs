using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace New_CandyClient.ProtectionTools.AntiDump
{
	// Token: 0x02000037 RID: 55
	internal class DumpProtect
	{
		// Token: 0x0600016E RID: 366
		[DllImport("kernel32.dll")]
		private static extern IntPtr ZeroMemory(IntPtr addr, IntPtr size);

		// Token: 0x0600016F RID: 367
		[DllImport("kernel32.dll")]
		private static extern IntPtr VirtualProtect(IntPtr lpAddress, IntPtr dwSize, IntPtr flNewProtect, ref IntPtr lpflOldProtect);

		// Token: 0x06000170 RID: 368 RVA: 0x00012CB4 File Offset: 0x000110B4
		private static void EraseSection(IntPtr address, int size)
		{
			IntPtr intPtr = (IntPtr)size;
			IntPtr flNewProtect = (IntPtr)0;
			DumpProtect.VirtualProtect(address, intPtr, (IntPtr)64, ref flNewProtect);
			DumpProtect.ZeroMemory(address, intPtr);
			IntPtr intPtr2 = (IntPtr)0;
			DumpProtect.VirtualProtect(address, intPtr, flNewProtect, ref intPtr2);
		}

		// Token: 0x06000171 RID: 369 RVA: 0x00012CFC File Offset: 0x000110FC
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

		// Token: 0x040003B7 RID: 951
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

		// Token: 0x040003B8 RID: 952
		private static int[] peheaderbytes = new int[]
		{
			26,
			27
		};

		// Token: 0x040003B9 RID: 953
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

		// Token: 0x040003BA RID: 954
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
