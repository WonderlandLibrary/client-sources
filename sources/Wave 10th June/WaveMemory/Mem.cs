using System;
using System.Diagnostics;
using System.Globalization;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Text;

namespace WaveMemory
{
	// Token: 0x02000004 RID: 4
	public class Mem
	{
		// Token: 0x06000003 RID: 3
		[DllImport("user32.dll")]
		public static extern short GetAsyncKeyState(char vKey);

		// Token: 0x06000004 RID: 4
		[DllImport("kernel32", SetLastError = true)]
		public static extern int ReadProcessMemory(IntPtr hProcess, ulong lpBase, ref ulong lpBuffer, int nSize, int lpNumberOfBytesRead);

		// Token: 0x06000005 RID: 5
		[DllImport("kernel32", SetLastError = true)]
		public unsafe static extern int WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, void* lpBuffer, int nSize, int lpNumberOfBytesWritten);

		// Token: 0x06000006 RID: 6
		[DllImport("kernel32.dll")]
		public static extern IntPtr OpenProcess(int dwDesiredAccess, bool bInheritHandle, int dwProcessId);

		// Token: 0x06000007 RID: 7
		[DllImport("user32.dll", SetLastError = true)]
		public static extern bool GetWindow(IntPtr hWnd, uint uCmd);

		// Token: 0x06000008 RID: 8
		[DllImport("user32.dll")]
		public static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);

		// Token: 0x06000009 RID: 9
		[DllImport("user32.dll")]
		public static extern IntPtr GetForegroundWindow();

		// Token: 0x0600000A RID: 10
		[DllImport("user32.dll", SetLastError = true)]
		public static extern bool GetWindowRect(IntPtr hWnd, out Mem.FOURSIDE lpRect);

		// Token: 0x0600000B RID: 11
		[DllImport("user32.dll")]
		public static extern uint MapVirtualKeyA(uint uCode, uint uMapType);

		// Token: 0x0600000C RID: 12
		[DllImport("user32.dll")]
		public static extern short VkKeyScan(char ch);

		// Token: 0x0600000D RID: 13 RVA: 0x00002058 File Offset: 0x00000258
		public static void openMc()
		{
			Process process = Process.GetProcessesByName("Minecraft.Windows")[0];
			IntPtr intPtr = Mem.OpenProcess(2035711, false, process.Id);
			Mem.mcProcessId = (uint)process.Id;
			Mem.mcProcess = process;
			Mem.mcProcessHandle = intPtr;
			Mem.mcMainModule = process.MainModule;
			Mem.mcBaseAddress = Mem.mcMainModule.BaseAddress;
		}

		// Token: 0x0600000E RID: 14 RVA: 0x000020B3 File Offset: 0x000002B3
		public static void openWindowH()
		{
			Process[] processesByName = Process.GetProcessesByName("ApplicationFrameHost");
			Mem.mcWindowHandle = processesByName[0].MainWindowHandle;
			Mem.mcWindowProcessId = (uint)processesByName[0].Id;
		}

		// Token: 0x0600000F RID: 15 RVA: 0x000020D8 File Offset: 0x000002D8
		public static Mem.FOURSIDE getMcRect()
		{
			Mem.FOURSIDE result = default(Mem.FOURSIDE);
			Mem.GetWindowRect(Mem.mcWindowHandle, out result);
			return result;
		}

		// Token: 0x06000010 RID: 16 RVA: 0x000020FC File Offset: 0x000002FC
		public static bool isMcFocused()
		{
			StringBuilder stringBuilder = new StringBuilder("Minecraft".Length + 1);
			Mem.GetWindowText(Mem.GetForegroundWindow(), stringBuilder, "Minecraft".Length + 1);
			return stringBuilder.ToString().CompareTo("Minecraft") == 0;
		}

		// Token: 0x06000011 RID: 17 RVA: 0x00002148 File Offset: 0x00000348
		public static IntPtr isMcFocused1()
		{
			StringBuilder stringBuilder = new StringBuilder("Minecraft".Length + 1);
			Mem.GetWindowText(Mem.GetForegroundWindow(), stringBuilder, "Minecraft".Length + 1);
			if (stringBuilder.ToString() == "Minecraft")
			{
				return (IntPtr)(-1);
			}
			return (IntPtr)(-2);
		}

		// Token: 0x06000012 RID: 18 RVA: 0x000021A0 File Offset: 0x000003A0
		public static byte[] CheatEngineByteTobytes(string byteString)
		{
			string[] array = byteString.Split(new char[]
			{
				' '
			});
			byte[] array2 = new byte[array.Length];
			int num = 0;
			foreach (string value in array)
			{
				array2[num] = Convert.ToByte(value, 16);
				num++;
			}
			return array2;
		}

		// Token: 0x06000013 RID: 19 RVA: 0x000021F0 File Offset: 0x000003F0
		public static int[] CheatEngineByteToIntegers(string byteString)
		{
			string[] array = byteString.Split(new char[]
			{
				' '
			});
			int[] array2 = new int[array.Length];
			int num = 0;
			foreach (string s in array)
			{
				array2[num] = int.Parse(s, NumberStyles.HexNumber);
				num++;
			}
			return array2;
		}

		// Token: 0x06000014 RID: 20 RVA: 0x00002244 File Offset: 0x00000444
		public static ulong[] CheatEngineByteToUlongs(string byteString)
		{
			string[] array = byteString.Split(new char[]
			{
				' '
			});
			ulong[] array2 = new ulong[array.Length];
			int num = 0;
			foreach (string s in array)
			{
				array2[num] = ulong.Parse(s, NumberStyles.HexNumber);
				num++;
			}
			return array2;
		}

		// Token: 0x06000015 RID: 21 RVA: 0x00002298 File Offset: 0x00000498
		public static ulong EvaluatePointer(ulong addr, ulong[] offsets)
		{
			ulong num = 0UL;
			Mem.ReadProcessMemory(Mem.mcProcessHandle, addr, ref num, 8, 0);
			for (int i = 0; i < offsets.Length - 1; i++)
			{
				Mem.ReadProcessMemory(Mem.mcProcessHandle, num + offsets[i], ref num, 8, 0);
			}
			return num + offsets[offsets.Length - 1];
		}

		// Token: 0x06000016 RID: 22 RVA: 0x000022E8 File Offset: 0x000004E8
		public static ulong BaseEvaluatePointer(ulong offset, ulong[] offsets)
		{
			ulong num = 0UL;
			Mem.ReadProcessMemory(Mem.mcProcessHandle, (ulong)((long)Mem.mcBaseAddress + (long)offset), ref num, 8, 0);
			for (int i = 0; i < offsets.Length - 1; i++)
			{
				Mem.ReadProcessMemory(Mem.mcProcessHandle, num + offsets[i], ref num, 8, 0);
			}
			return num + offsets[offsets.Length - 1];
		}

		// Token: 0x06000017 RID: 23 RVA: 0x00002340 File Offset: 0x00000540
		public unsafe static void Write<[IsUnmanaged] T>(ulong address, T value) where T : struct, ValueType
		{
			Mem.WriteProcessMemory(Mem.mcProcessHandle, (IntPtr)((long)address), (void*)(&value), sizeof(T), 0);
		}

		// Token: 0x06000018 RID: 24 RVA: 0x0000235D File Offset: 0x0000055D
		public static void WriteBase<[IsUnmanaged] T>(ulong offset, T value) where T : struct, ValueType
		{
			Mem.Write<T>((ulong)((long)Mem.mcBaseAddress + (long)offset), value);
		}

		// Token: 0x06000019 RID: 25 RVA: 0x00002374 File Offset: 0x00000574
		public static void WriteBaseBytes(ulong offset, byte[] value)
		{
			uint num = 0U;
			while ((ulong)num < (ulong)((long)value.Length))
			{
				Mem.Write<byte>((ulong)((long)Mem.mcBaseAddress + (long)offset + (long)((ulong)num)), value[(int)num]);
				num += 1U;
			}
		}

		// Token: 0x0600001A RID: 26 RVA: 0x000023A8 File Offset: 0x000005A8
		public static void WriteBytes(ulong address, byte[] value)
		{
			uint num = 0U;
			while ((ulong)num < (ulong)((long)value.Length))
			{
				Mem.Write<byte>(address + (ulong)num, value[(int)num]);
				num += 1U;
			}
		}

		// Token: 0x0600001B RID: 27 RVA: 0x000023D4 File Offset: 0x000005D4
		public static void WriteString(ulong address, string str)
		{
			uint num = 0U;
			while ((ulong)num < (ulong)((long)str.Length))
			{
				Mem.Write<byte>(address + (ulong)num, (byte)str[(int)num]);
				num += 1U;
			}
			Mem.Write<byte>(address + (ulong)str.Length, 0);
		}

		// Token: 0x0600001C RID: 28 RVA: 0x00002414 File Offset: 0x00000614
		public static void WriteWideString(ulong address, string str)
		{
			uint num = 0U;
			while ((ulong)num < (ulong)((long)str.Length))
			{
				Mem.Write<short>(address + (ulong)num, (short)str[(int)num]);
				num += 1U;
			}
			Mem.Write<byte>(address + (ulong)str.Length, 0);
		}

		// Token: 0x0600001D RID: 29 RVA: 0x00002454 File Offset: 0x00000654
		public unsafe static T Read<[IsUnmanaged] T>(ulong address) where T : struct, ValueType
		{
			ulong num = 0UL;
			Mem.ReadProcessMemory(Mem.mcProcessHandle, address, ref num, sizeof(T), 0);
			return *(T*)(&num);
		}

		// Token: 0x0600001E RID: 30 RVA: 0x00002484 File Offset: 0x00000684
		public static string szRead(ulong address)
		{
			string text = "";
			uint num = 0U;
			for (;;)
			{
				byte b = Mem.Read<byte>(address + (ulong)num);
				num += 1U;
				if (b == 0)
				{
					break;
				}
				string str = text;
				char c = (char)b;
				text = str + c.ToString();
			}
			return text;
		}

		// Token: 0x0600001F RID: 31 RVA: 0x000024BF File Offset: 0x000006BF
		public static string szPtrRead(ulong address)
		{
			return Mem.szRead(Mem.Read<ulong>(address));
		}

		// Token: 0x04000001 RID: 1
		public static Process mcProcess;

		// Token: 0x04000002 RID: 2
		public static ProcessModule mcMainModule;

		// Token: 0x04000003 RID: 3
		public static uint mcProcessId;

		// Token: 0x04000004 RID: 4
		public static uint mcWindowProcessId;

		// Token: 0x04000005 RID: 5
		public static IntPtr mcBaseAddress;

		// Token: 0x04000006 RID: 6
		public static IntPtr mcWindowHandle;

		// Token: 0x04000007 RID: 7
		public static IntPtr mcProcessHandle;

		// Token: 0x02000027 RID: 39
		public struct FOURSIDE
		{
			// Token: 0x0400006C RID: 108
			public int Left;

			// Token: 0x0400006D RID: 109
			public int Top;

			// Token: 0x0400006E RID: 110
			public int Right;

			// Token: 0x0400006F RID: 111
			public int Bottom;
		}
	}
}
