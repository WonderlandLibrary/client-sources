using System;
using System.Diagnostics;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;

namespace AnyDesk
{
	// Token: 0x02000016 RID: 22
	internal static class Memory
	{
		// Token: 0x06000041 RID: 65 RVA: 0x000038AC File Offset: 0x00001AAC
		public static bool OpenProcess(string name)
		{
			Process[] processesByName = Process.GetProcessesByName(name);
			bool flag = processesByName.Length != 0;
			bool flag2 = flag;
			bool flag3 = flag2;
			bool result;
			if (flag3)
			{
				Memory.process = processesByName[0];
				Memory.OpenProcess(Memory.process.Id, Enums.ProcessAccessFlags.All);
				result = true;
			}
			else
			{
				result = false;
			}
			return result;
		}

		// Token: 0x06000042 RID: 66 RVA: 0x00002126 File Offset: 0x00000326
		public static void OpenProcess(int pId, Enums.ProcessAccessFlags processAccess = Enums.ProcessAccessFlags.All)
		{
			Memory.pHandle = WinAPI.OpenProcess(processAccess, false, pId);
		}

		// Token: 0x06000043 RID: 67 RVA: 0x00003900 File Offset: 0x00001B00
		public static void CloseProcess(IntPtr phandle)
		{
			bool flag = WinAPI.CloseHandle(phandle);
		}

		// Token: 0x06000044 RID: 68 RVA: 0x00003918 File Offset: 0x00001B18
		public static T GetStructure<T>(byte[] bytes)
		{
			GCHandle gchandle = GCHandle.Alloc(bytes, GCHandleType.Pinned);
			T result = (T)((object)Marshal.PtrToStructure(gchandle.AddrOfPinnedObject(), typeof(T)));
			gchandle.Free();
			return result;
		}

		// Token: 0x06000045 RID: 69 RVA: 0x00003958 File Offset: 0x00001B58
		public static byte[] GetStructBytes<T>(T str)
		{
			int num = Marshal.SizeOf(typeof(T));
			byte[] array = new byte[num];
			IntPtr intPtr = Marshal.AllocHGlobal(num);
			Marshal.StructureToPtr<T>(str, intPtr, true);
			Marshal.Copy(intPtr, array, 0, num);
			Marshal.FreeHGlobal(intPtr);
			return array;
		}

		// Token: 0x06000046 RID: 70 RVA: 0x000039A4 File Offset: 0x00001BA4
		public static T Read<T>(int address)
		{
			int num = Marshal.SizeOf(typeof(T));
			bool flag = typeof(T) == typeof(bool);
			bool flag2 = flag;
			bool flag3 = flag2;
			if (flag3)
			{
				num = 1;
			}
			byte[] array = new byte[num];
			uint num2 = 0U;
			bool flag4 = WinAPI.ReadProcessMemory(Memory.pHandle, (IntPtr)address, array, (uint)num, ref num2);
			return Memory.GetStructure<T>(array);
		}

		// Token: 0x06000047 RID: 71 RVA: 0x00003A18 File Offset: 0x00001C18
		public static void Write<T>(int address, T value)
		{
			int num = Marshal.SizeOf(typeof(T));
			byte[] array = new byte[num];
			IntPtr intPtr = Marshal.AllocHGlobal(num);
			Marshal.StructureToPtr<T>(value, intPtr, true);
			Marshal.Copy(intPtr, array, 0, num);
			Marshal.FreeHGlobal(intPtr);
			uint num2 = 0U;
			WinAPI.WriteProcessMemory(Memory.pHandle, (IntPtr)address, array, (IntPtr)num, ref num2);
		}

		// Token: 0x06000048 RID: 72 RVA: 0x00003A7C File Offset: 0x00001C7C
		public static byte[] ReadBytes(int address, int length)
		{
			byte[] array = new byte[length];
			uint num = 0U;
			bool flag = WinAPI.ReadProcessMemory(Memory.pHandle, (IntPtr)address, array, (uint)length, ref num);
			return array;
		}

		// Token: 0x06000049 RID: 73 RVA: 0x00003AB0 File Offset: 0x00001CB0
		public static void WriteBytes(int address, byte[] value)
		{
			uint num = 0U;
			WinAPI.WriteProcessMemory(Memory.pHandle, (IntPtr)address, value, (IntPtr)value.Length, ref num);
		}

		// Token: 0x0600004A RID: 74 RVA: 0x00003ADC File Offset: 0x00001CDC
		public static string ReadString(int address, int bufferSize, Encoding enc)
		{
			byte[] array = new byte[bufferSize];
			uint num = 0U;
			bool flag = WinAPI.ReadProcessMemory(Memory.pHandle, (IntPtr)address, array, (uint)bufferSize, ref num);
			string text = enc.GetString(array);
			bool flag2 = text.Contains('\0');
			bool flag3 = flag2;
			bool flag4 = flag3;
			if (flag4)
			{
				text = text.Substring(0, text.IndexOf('\0'));
			}
			return text;
		}

		// Token: 0x04000096 RID: 150
		public static IntPtr pHandle;

		// Token: 0x04000097 RID: 151
		public static Process process;

		// Token: 0x04000098 RID: 152
		public static int client = 0;

		// Token: 0x04000099 RID: 153
		public static int client_size = 0;

		// Token: 0x0400009A RID: 154
		public static int engine = 0;

		// Token: 0x0400009B RID: 155
		public static int engine_size = 0;

		// Token: 0x0400009C RID: 156
		private const uint INFINITE = 4294967295U;

		// Token: 0x0400009D RID: 157
		private const uint WAIT_ABANDONED = 128U;

		// Token: 0x0400009E RID: 158
		private const uint WAIT_OBJECT_0 = 0U;

		// Token: 0x0400009F RID: 159
		private const uint WAIT_TIMEOUT = 258U;
	}
}
