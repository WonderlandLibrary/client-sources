using System;
using System.Diagnostics;
using System.Globalization;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;

namespace WaveClient.SDK
{
	// Token: 0x02000009 RID: 9
	public class memory2
	{
		// Token: 0x06000034 RID: 52
		[DllImport("user32.dll")]
		public static extern bool GetAsyncKeyState(char vKey);

		// Token: 0x06000035 RID: 53
		[DllImport("kernel32", SetLastError = true)]
		public static extern int ReadProcessMemory(IntPtr hProcess, ulong lpBase, ref ulong lpBuffer, int nSize, int lpNumberOfBytesRead);

		// Token: 0x06000036 RID: 54
		[DllImport("kernel32", SetLastError = true)]
		public static extern int WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, ref IntPtr lpBuffer, int nSize, int lpNumberOfBytesWritten);

		// Token: 0x06000037 RID: 55
		[DllImport("kernel32", SetLastError = true)]
		public static extern int WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, ref byte lpBuffer, int nSize, int lpNumberOfBytesWritten);

		// Token: 0x06000038 RID: 56
		[DllImport("kernel32", SetLastError = true)]
		public static extern int VirtualProtectEx(IntPtr hProcess, IntPtr lpAddress, int dwSize, long flNewProtect, ref long lpflOldProtect);

		// Token: 0x06000039 RID: 57
		[DllImport("kernel32.dll")]
		public static extern IntPtr OpenProcess(int dwDesiredAccess, bool bInheritHandle, int dwProcessId);

		// Token: 0x0600003A RID: 58
		[DllImport("user32.dll")]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x0600003B RID: 59
		[DllImport("user32.dll", SetLastError = true)]
		private static extern bool GetWindowRect(IntPtr hWnd, out memory2.RECT lpRect);

		// Token: 0x0600003C RID: 60
		[DllImport("user32.dll", SetLastError = true)]
		private static extern bool GetWindow(IntPtr hWnd, uint uCmd);

		// Token: 0x0600003D RID: 61
		[DllImport("user32.dll", SetLastError = true)]
		private static extern bool IsWindowVisible(IntPtr hWnd);

		// Token: 0x0600003E RID: 62
		[DllImport("user32.dll")]
		private static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);

		// Token: 0x0600003F RID: 63
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		public static extern IntPtr VirtualAllocEx(IntPtr hProcess, IntPtr lpAddress, uint dwSize, memory2.AllocationType flAllocationType, memory2.MemoryProtection flProtect);

		// Token: 0x06000040 RID: 64
		[DllImport("kernel32")]
		public static extern IntPtr CreateRemoteThread(IntPtr hProcess, IntPtr lpThreadAttributes, uint dwStackSize, IntPtr lpStartAddress, IntPtr lpParameter, uint dwCreationFlags, out uint lpThreadId);

		// Token: 0x06000041 RID: 65
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		public static extern bool VirtualFreeEx(IntPtr hProcess, IntPtr lpAddress, UIntPtr dwSize, uint dwFreeType);

		// Token: 0x06000042 RID: 66 RVA: 0x00002FE8 File Offset: 0x000011E8
		public static void openGame()
		{
			Process process = Process.GetProcessesByName("Minecraft.Windows")[0];
			IntPtr intPtr = memory2.OpenProcess(2035711, false, process.Id);
			memory2.mcProcId = (uint)process.Id;
			memory2.mcProcHandle = intPtr;
			memory2.mcMainModule = process.MainModule;
			memory2.mcBaseAddress = memory2.mcMainModule.BaseAddress;
			memory2.mcProc = process;
		}

		// Token: 0x06000043 RID: 67 RVA: 0x00003043 File Offset: 0x00001243
		public static void openWindowHost()
		{
			Process[] processesByName = Process.GetProcessesByName("ApplicationFrameHost");
			memory2.mcWinHandle = processesByName[0].MainWindowHandle;
			memory2.mcWinProcId = (uint)processesByName[0].Id;
		}

		// Token: 0x06000044 RID: 68 RVA: 0x00003068 File Offset: 0x00001268
		public static memory2.RECT getMinecraftRect()
		{
			memory2.RECT result = default(memory2.RECT);
			memory2.GetWindowRect(memory2.mcWinHandle, out result);
			return result;
		}

		// Token: 0x06000045 RID: 69 RVA: 0x0000308C File Offset: 0x0000128C
		public static bool isMinecraftFocused()
		{
			StringBuilder stringBuilder = new StringBuilder("Minecraft".Length + 1);
			memory2.GetWindowText(memory2.GetForegroundWindow(), stringBuilder, "Minecraft".Length + 1);
			return stringBuilder.ToString().CompareTo("Minecraft") == 0;
		}

		// Token: 0x06000046 RID: 70 RVA: 0x000030D8 File Offset: 0x000012D8
		public static IntPtr isMinecraftFocusedInsert()
		{
			StringBuilder stringBuilder = new StringBuilder("Minecraft".Length + 1);
			memory2.GetWindowText(memory2.GetForegroundWindow(), stringBuilder, "Minecraft".Length + 1);
			if (stringBuilder.ToString() == "Minecraft")
			{
				return (IntPtr)(-1);
			}
			return (IntPtr)(-2);
		}

		// Token: 0x06000047 RID: 71 RVA: 0x00003130 File Offset: 0x00001330
		public static void unprotectMemory(IntPtr address, int bytesToUnprotect)
		{
			long num = 0L;
			memory2.VirtualProtectEx(memory2.mcProcHandle, address, bytesToUnprotect, 64L, ref num);
		}

		// Token: 0x06000048 RID: 72 RVA: 0x00003154 File Offset: 0x00001354
		public static byte[] ceByte2Bytes(string byteString)
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

		// Token: 0x06000049 RID: 73 RVA: 0x000031A4 File Offset: 0x000013A4
		public static int[] ceByte2Ints(string byteString)
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

		// Token: 0x0600004A RID: 74 RVA: 0x000031F8 File Offset: 0x000013F8
		public static ulong[] ceByte2uLong(string byteString)
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

		// Token: 0x0600004B RID: 75 RVA: 0x0000324C File Offset: 0x0000144C
		public static ulong baseEvaluatePointer(ulong offset, ulong[] offsets)
		{
			ulong num = 0UL;
			memory2.ReadProcessMemory(memory2.mcProcHandle, (ulong)((long)memory2.mcBaseAddress + (long)offset), ref num, 8, 0);
			for (int i = 0; i < offsets.Length - 1; i++)
			{
				memory2.ReadProcessMemory(memory2.mcProcHandle, num + offsets[i], ref num, 8, 0);
			}
			return num + offsets[offsets.Length - 1];
		}

		// Token: 0x0600004C RID: 76 RVA: 0x000032A4 File Offset: 0x000014A4
		public static ulong evaluatePointer(ulong addr, ulong[] offsets)
		{
			ulong num = 0UL;
			memory2.ReadProcessMemory(memory2.mcProcHandle, addr, ref num, 8, 0);
			for (int i = 0; i < offsets.Length - 1; i++)
			{
				memory2.ReadProcessMemory(memory2.mcProcHandle, num + offsets[i], ref num, 8, 0);
			}
			return num + offsets[offsets.Length - 1];
		}

		// Token: 0x0600004D RID: 77 RVA: 0x000032F4 File Offset: 0x000014F4
		public static int readBaseByte(int offset)
		{
			ulong num = 0UL;
			memory2.ReadProcessMemory(memory2.mcProcHandle, (ulong)((long)(memory2.mcBaseAddress + offset)), ref num, 1, 0);
			return (int)((byte)num);
		}

		// Token: 0x0600004E RID: 78 RVA: 0x00003328 File Offset: 0x00001528
		public static int readBaseInt(int offset)
		{
			ulong num = 0UL;
			memory2.ReadProcessMemory(memory2.mcProcHandle, (ulong)((long)(memory2.mcBaseAddress + offset)), ref num, 4, 0);
			return (int)num;
		}

		// Token: 0x0600004F RID: 79 RVA: 0x0000335C File Offset: 0x0000155C
		public static ulong readBaseInt64(int offset)
		{
			ulong result = 0UL;
			memory2.ReadProcessMemory(memory2.mcProcHandle, (ulong)((long)(memory2.mcBaseAddress + offset)), ref result, 8, 0);
			return result;
		}

		// Token: 0x06000050 RID: 80 RVA: 0x0000338C File Offset: 0x0000158C
		public static void writeBaseByte(int offset, byte value)
		{
			memory2.WriteProcessMemory(memory2.mcProcHandle, memory2.mcBaseAddress + offset, ref value, 1, 0);
		}

		// Token: 0x06000051 RID: 81 RVA: 0x000033A8 File Offset: 0x000015A8
		public static void writeBaseInt(int offset, int value)
		{
			byte[] bytes = BitConverter.GetBytes(value);
			int num = 0;
			foreach (byte value2 in bytes)
			{
				memory2.writeBaseByte(offset + num, value2);
				num++;
			}
		}

		// Token: 0x06000052 RID: 82 RVA: 0x000033E0 File Offset: 0x000015E0
		public static void writeBaseBytes(int offset, byte[] value)
		{
			int num = 0;
			foreach (byte value2 in value)
			{
				memory2.writeBaseByte(offset + num, value2);
				num++;
			}
		}

		// Token: 0x06000053 RID: 83 RVA: 0x00003410 File Offset: 0x00001610
		public static void writeBaseFloat(int offset, float value)
		{
			byte[] bytes = BitConverter.GetBytes(value);
			int num = 0;
			foreach (byte value2 in bytes)
			{
				memory2.writeBaseByte(offset + num, value2);
				num++;
			}
		}

		// Token: 0x06000054 RID: 84 RVA: 0x00003448 File Offset: 0x00001648
		public static void writeBaseInt64(int offset, ulong value)
		{
			byte[] bytes = BitConverter.GetBytes(value);
			int num = 0;
			foreach (byte value2 in bytes)
			{
				memory2.writeBaseByte(offset + num, value2);
				num++;
			}
		}

		// Token: 0x06000055 RID: 85 RVA: 0x00003480 File Offset: 0x00001680
		public static byte readByte(ulong address)
		{
			ulong num = 0UL;
			memory2.ReadProcessMemory(memory2.mcProcHandle, address, ref num, 1, 0);
			return (byte)num;
		}

		// Token: 0x06000056 RID: 86 RVA: 0x000034A4 File Offset: 0x000016A4
		public static int readInt(ulong address)
		{
			ulong num = 0UL;
			memory2.ReadProcessMemory(memory2.mcProcHandle, address, ref num, 4, 0);
			return (int)num;
		}

		// Token: 0x06000057 RID: 87 RVA: 0x000034C8 File Offset: 0x000016C8
		public static float readFloat(ulong address)
		{
			ulong value = 0UL;
			memory2.ReadProcessMemory(memory2.mcProcHandle, address, ref value, 4, 0);
			return BitConverter.ToSingle(BitConverter.GetBytes(value), 0);
		}

		// Token: 0x06000058 RID: 88 RVA: 0x000034F4 File Offset: 0x000016F4
		public static ulong readInt64(ulong address)
		{
			ulong result = 0UL;
			memory2.ReadProcessMemory(memory2.mcProcHandle, address, ref result, 8, 0);
			return result;
		}

		// Token: 0x06000059 RID: 89 RVA: 0x00003518 File Offset: 0x00001718
		public static string readString(ulong address, ulong length)
		{
			byte[] array = new byte[length];
			int num = 0;
			foreach (byte b in array)
			{
				byte b2 = memory2.readByte(address + (ulong)((long)num));
				if (b2 == 0)
				{
					break;
				}
				array[num] = b2;
				num++;
			}
			return new string(Encoding.Default.GetString(array).Take(num).ToArray<char>());
		}

		// Token: 0x0600005A RID: 90 RVA: 0x00003576 File Offset: 0x00001776
		public static void writeByte(ulong address, byte value)
		{
			memory2.WriteProcessMemory(memory2.mcProcHandle, (IntPtr)((long)address), ref value, 1, 0);
		}

		// Token: 0x0600005B RID: 91 RVA: 0x00003590 File Offset: 0x00001790
		public static void writeBytes(ulong address, byte[] value)
		{
			int num = 0;
			foreach (byte value2 in value)
			{
				memory2.writeByte(address + (ulong)((long)num), value2);
				num++;
			}
		}

		// Token: 0x0600005C RID: 92 RVA: 0x000035C4 File Offset: 0x000017C4
		public static void writeInt(ulong address, int value)
		{
			byte[] bytes = BitConverter.GetBytes(value);
			int num = 0;
			foreach (byte value2 in bytes)
			{
				memory2.writeByte(address + (ulong)((long)num), value2);
				num++;
			}
		}

		// Token: 0x0600005D RID: 93 RVA: 0x000035FC File Offset: 0x000017FC
		public static void writeFloat(ulong address, float value)
		{
			byte[] bytes = BitConverter.GetBytes(value);
			int num = 0;
			foreach (byte value2 in bytes)
			{
				memory2.writeByte(address + (ulong)((long)num), value2);
				num++;
			}
		}

		// Token: 0x0600005E RID: 94 RVA: 0x00003634 File Offset: 0x00001834
		public static void writeInt64(ulong address, ulong value)
		{
			byte[] bytes = BitConverter.GetBytes(value);
			int num = 0;
			foreach (byte value2 in bytes)
			{
				memory2.writeByte(address + (ulong)((long)num), value2);
				num++;
			}
		}

		// Token: 0x0600005F RID: 95 RVA: 0x0000366C File Offset: 0x0000186C
		public static void writeString(ulong address, string str)
		{
			byte[] bytes = Encoding.ASCII.GetBytes(str);
			int num = 0;
			foreach (byte value in bytes)
			{
				memory2.writeByte(address + (ulong)((long)num), value);
				num++;
			}
		}

		// Token: 0x04000041 RID: 65
		public static IntPtr mcProcHandle;

		// Token: 0x04000042 RID: 66
		public static ProcessModule mcMainModule;

		// Token: 0x04000043 RID: 67
		public static IntPtr mcBaseAddress;

		// Token: 0x04000044 RID: 68
		public static IntPtr mcWinHandle;

		// Token: 0x04000045 RID: 69
		public static uint mcProcId;

		// Token: 0x04000046 RID: 70
		public static uint mcWinProcId;

		// Token: 0x04000047 RID: 71
		public static Process mcProc;

		// Token: 0x0200002A RID: 42
		public struct RECT
		{
			// Token: 0x040000A4 RID: 164
			public int Left;

			// Token: 0x040000A5 RID: 165
			public int Top;

			// Token: 0x040000A6 RID: 166
			public int Right;

			// Token: 0x040000A7 RID: 167
			public int Bottom;
		}

		// Token: 0x0200002B RID: 43
		[Flags]
		public enum AllocationType
		{
			// Token: 0x040000A9 RID: 169
			Commit = 4096,
			// Token: 0x040000AA RID: 170
			Reserve = 8192,
			// Token: 0x040000AB RID: 171
			Decommit = 16384,
			// Token: 0x040000AC RID: 172
			Release = 32768,
			// Token: 0x040000AD RID: 173
			Reset = 524288,
			// Token: 0x040000AE RID: 174
			Physical = 4194304,
			// Token: 0x040000AF RID: 175
			TopDown = 1048576,
			// Token: 0x040000B0 RID: 176
			WriteWatch = 2097152,
			// Token: 0x040000B1 RID: 177
			LargePages = 536870912
		}

		// Token: 0x0200002C RID: 44
		[Flags]
		public enum MemoryProtection
		{
			// Token: 0x040000B3 RID: 179
			Execute = 16,
			// Token: 0x040000B4 RID: 180
			ExecuteRead = 32,
			// Token: 0x040000B5 RID: 181
			ExecuteReadWrite = 64,
			// Token: 0x040000B6 RID: 182
			ExecuteWriteCopy = 128,
			// Token: 0x040000B7 RID: 183
			NoAccess = 1,
			// Token: 0x040000B8 RID: 184
			ReadOnly = 2,
			// Token: 0x040000B9 RID: 185
			ReadWrite = 4,
			// Token: 0x040000BA RID: 186
			WriteCopy = 8,
			// Token: 0x040000BB RID: 187
			GuardModifierflag = 256,
			// Token: 0x040000BC RID: 188
			NoCacheModifierflag = 512,
			// Token: 0x040000BD RID: 189
			WriteCombineModifierflag = 1024
		}
	}
}
