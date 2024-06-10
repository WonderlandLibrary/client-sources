using System;
using System.Diagnostics;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;

namespace New_CandyClient.Memory
{
	// Token: 0x0200004B RID: 75
	internal static class Memory
	{
		// Token: 0x060001C1 RID: 449 RVA: 0x00013BF0 File Offset: 0x00011FF0
		public static bool OpenProcess(string name)
		{
			Process[] processesByName = Process.GetProcessesByName(name);
			if (processesByName.Length != 0)
			{
				Memory.process = processesByName[0];
				Memory.OpenProcess(Memory.process.Id, Enums.ProcessAccessFlags.All);
				return true;
			}
			return false;
		}

		// Token: 0x060001C2 RID: 450 RVA: 0x00013C2A File Offset: 0x0001202A
		public static void OpenProcess(int pId, Enums.ProcessAccessFlags processAccess = Enums.ProcessAccessFlags.All)
		{
			Memory.pHandle = WinAPI.OpenProcess(processAccess, false, pId);
		}

		// Token: 0x060001C3 RID: 451 RVA: 0x00013C39 File Offset: 0x00012039
		public static void CloseProcess(IntPtr phandle)
		{
			WinAPI.CloseHandle(phandle);
		}

		// Token: 0x060001C4 RID: 452 RVA: 0x00013C44 File Offset: 0x00012044
		public static T GetStructure<T>(byte[] bytes)
		{
			GCHandle gchandle = GCHandle.Alloc(bytes, GCHandleType.Pinned);
			T result = (T)((object)Marshal.PtrToStructure(gchandle.AddrOfPinnedObject(), typeof(T)));
			gchandle.Free();
			return result;
		}

		// Token: 0x060001C5 RID: 453 RVA: 0x00013C7C File Offset: 0x0001207C
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

		// Token: 0x060001C6 RID: 454 RVA: 0x00013CC0 File Offset: 0x000120C0
		public static T Read<T>(int address)
		{
			int num = Marshal.SizeOf(typeof(T));
			if (typeof(T) == typeof(bool))
			{
				num = 1;
			}
			byte[] array = new byte[num];
			uint num2 = 0U;
			WinAPI.ZwReadVirtualMemory(Memory.pHandle, (IntPtr)address, array, (uint)num, ref num2);
			return Memory.GetStructure<T>(array);
		}

		// Token: 0x060001C7 RID: 455 RVA: 0x00013D20 File Offset: 0x00012120
		public static void Write<T>(int address, T value)
		{
			int num = Marshal.SizeOf(typeof(T));
			byte[] array = new byte[num];
			IntPtr intPtr = Marshal.AllocHGlobal(num);
			Marshal.StructureToPtr<T>(value, intPtr, true);
			Marshal.Copy(intPtr, array, 0, num);
			Marshal.FreeHGlobal(intPtr);
			uint num2 = 0U;
			WinAPI.ZwWriteVirtualMemory(Memory.pHandle, (IntPtr)address, array, (IntPtr)num, ref num2);
		}

		// Token: 0x060001C8 RID: 456 RVA: 0x00013D80 File Offset: 0x00012180
		public static byte[] ReadBytes(int address, int length)
		{
			byte[] array = new byte[length];
			uint num = 0U;
			WinAPI.ZwReadVirtualMemory(Memory.pHandle, (IntPtr)address, array, (uint)length, ref num);
			return array;
		}

		// Token: 0x060001C9 RID: 457 RVA: 0x00013DAC File Offset: 0x000121AC
		public static void WriteBytes(int address, byte[] value)
		{
			uint num = 0U;
			WinAPI.ZwWriteVirtualMemory(Memory.pHandle, (IntPtr)address, value, (IntPtr)value.Length, ref num);
		}

		// Token: 0x060001CA RID: 458 RVA: 0x00013DD8 File Offset: 0x000121D8
		public static string ReadString(int address, int bufferSize, Encoding enc)
		{
			byte[] array = new byte[bufferSize];
			uint num = 0U;
			WinAPI.ZwReadVirtualMemory(Memory.pHandle, (IntPtr)address, array, (uint)bufferSize, ref num);
			string text = enc.GetString(array);
			if (text.Contains('\0'))
			{
				text = text.Substring(0, text.IndexOf('\0'));
			}
			return text;
		}

		// Token: 0x040003FF RID: 1023
		public static IntPtr pHandle;

		// Token: 0x04000400 RID: 1024
		public static Process process;
	}
}
