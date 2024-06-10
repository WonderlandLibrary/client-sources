using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace Wave.Cmr.MemoryManagement
{
	// Token: 0x02000021 RID: 33
	public class Memory
	{
		// Token: 0x060000EF RID: 239
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr OpenProcess(Memory.ProcessAccessFlags processAccess, bool bInheritHandle, int processId);

		// Token: 0x060000F0 RID: 240
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr CreateToolhelp32Snapshot(Memory.SnapshotFlags dwFlags, int th32ProcessID);

		// Token: 0x060000F1 RID: 241
		[DllImport("kernel32.dll")]
		public static extern bool Module32First(IntPtr hSnapshot, ref Memory.MODULEENTRY32 lpme);

		// Token: 0x060000F2 RID: 242
		[DllImport("kernel32.dll")]
		public static extern bool Module32Next(IntPtr hSnapshot, ref Memory.MODULEENTRY32 lpme);

		// Token: 0x060000F3 RID: 243
		[DllImport("coredll.dll", CharSet = CharSet.Auto, SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool CloseHandle(IntPtr hObject);

		// Token: 0x060000F4 RID: 244
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [MarshalAs(UnmanagedType.AsAny)] object lpBuffer, int dwSize, out IntPtr lpNumberOfBytesWritten);

		// Token: 0x060000F5 RID: 245
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [MarshalAs(UnmanagedType.AsAny)] object lpBuffer, int dwSize, IntPtr lpNumberOfBytesWritten);

		// Token: 0x060000F6 RID: 246
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] lpBuffer, int nSize, out IntPtr lpNumberOfBytesRead);

		// Token: 0x060000F7 RID: 247
		[DllImport("kernel32.dll")]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [Out] byte[] lpBuffer, UIntPtr nSize, IntPtr lpNumberOfBytesRead);

		// Token: 0x060000F8 RID: 248
		[DllImport("kernel32.dll")]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [Out] byte[] lpBuffer, IntPtr nSize, out ulong lpNumberOfBytesRead);

		// Token: 0x060000F9 RID: 249
		[DllImport("kernel32.dll")]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [Out] IntPtr lpBuffer, IntPtr nSize, out ulong lpNumberOfBytesRead);

		// Token: 0x060000FA RID: 250
		[DllImport("kernel32.dll")]
		public static extern bool VirtualProtectEx(IntPtr hProcess, IntPtr lpAddress, UIntPtr dwSize, uint flNewProtect, out uint lpflOldProtect);

		// Token: 0x060000FB RID: 251
		[DllImport("kernel32.dll")]
		public static extern bool VirtualProtectEx(IntPtr hProcess, IntPtr lpAddress, uint dwSize, uint flNewProtect, out uint lpflOldProtect);

		// Token: 0x060000FC RID: 252 RVA: 0x0000393C File Offset: 0x00001B3C
		public static IntPtr GetModuleBaseAddress(Process proc, string modName)
		{
			IntPtr result = IntPtr.Zero;
			foreach (object obj in proc.Modules)
			{
				ProcessModule processModule = (ProcessModule)obj;
				if (processModule.ModuleName == modName)
				{
					result = processModule.BaseAddress;
					break;
				}
			}
			return result;
		}

		// Token: 0x060000FD RID: 253 RVA: 0x000039AC File Offset: 0x00001BAC
		public static IntPtr GetModuleBaseAddress(int procId, string modName)
		{
			IntPtr result = IntPtr.Zero;
			IntPtr intPtr = Memory.CreateToolhelp32Snapshot((Memory.SnapshotFlags)24U, procId);
			if (intPtr.ToInt64() != -1L)
			{
				Memory.MODULEENTRY32 moduleentry = default(Memory.MODULEENTRY32);
				moduleentry.dwSize = (uint)Marshal.SizeOf(typeof(Memory.MODULEENTRY32));
				if (Memory.Module32First(intPtr, ref moduleentry))
				{
					while (!moduleentry.szModule.Equals(modName))
					{
						if (!Memory.Module32Next(intPtr, ref moduleentry))
						{
							goto IL_63;
						}
					}
					result = moduleentry.modBaseAddr;
				}
			}
			IL_63:
			Memory.CloseHandle(intPtr);
			return result;
		}

		// Token: 0x060000FE RID: 254 RVA: 0x00003A24 File Offset: 0x00001C24
		public static IntPtr FindAddressWithPointer(IntPtr hProc, IntPtr ptr, int[] offsets)
		{
			byte[] array = new byte[IntPtr.Size];
			foreach (int offset in offsets)
			{
				IntPtr intPtr;
				Memory.ReadProcessMemory(hProc, ptr, array, array.Length, out intPtr);
				ptr = ((IntPtr.Size == 4) ? IntPtr.Add(new IntPtr(BitConverter.ToInt32(array, 0)), offset) : (ptr = IntPtr.Add(new IntPtr(BitConverter.ToInt64(array, 0)), offset)));
			}
			return ptr;
		}

		// Token: 0x1700000F RID: 15
		// (get) Token: 0x060000FF RID: 255 RVA: 0x00003A92 File Offset: 0x00001C92
		public Process GetProcID
		{
			get
			{
				return this.Proc;
			}
		}

		// Token: 0x06000100 RID: 256 RVA: 0x00003A9A File Offset: 0x00001C9A
		public Memory(string ProcessName)
		{
			this.ProcessName = ProcessName;
			this.ConnectToProcess();
		}

		// Token: 0x06000101 RID: 257 RVA: 0x00003AB0 File Offset: 0x00001CB0
		public void ConnectToProcess()
		{
			try
			{
				this.Proc = Process.GetProcessesByName(this.ProcessName)[0];
				this.hProc = Memory.OpenProcess(Memory.ProcessAccessFlags.All, false, this.Proc.Id);
			}
			catch
			{
			}
		}

		// Token: 0x06000102 RID: 258 RVA: 0x00003B04 File Offset: 0x00001D04
		public void WriteMemory(Pointer pointer, object value)
		{
			try
			{
				IntPtr moduleBaseAddress = Memory.GetModuleBaseAddress(this.Proc, pointer.ModuleBase);
				IntPtr lpBaseAddress = Memory.FindAddressWithPointer(this.hProc, moduleBaseAddress + pointer.PointerAddress, pointer.Offsets);
				IntPtr intPtr;
				Memory.WriteProcessMemory(this.hProc, lpBaseAddress, value, 4, out intPtr);
			}
			catch
			{
			}
		}

		// Token: 0x06000103 RID: 259 RVA: 0x00003B68 File Offset: 0x00001D68
		public void WriteMemory(int address, object value)
		{
			IntPtr intPtr;
			Memory.WriteProcessMemory(this.hProc, (IntPtr)address, value, 4, out intPtr);
		}

		// Token: 0x06000104 RID: 260 RVA: 0x00003B8C File Offset: 0x00001D8C
		public void WriteMemory(long address, object value)
		{
			IntPtr intPtr;
			Memory.WriteProcessMemory(this.hProc, (IntPtr)address, value, 4, out intPtr);
		}

		// Token: 0x06000105 RID: 261 RVA: 0x00003BB0 File Offset: 0x00001DB0
		public void PatchMemory(string ModuleBase, int Address, byte[] write)
		{
			try
			{
				IntPtr moduleBaseAddress = Memory.GetModuleBaseAddress(this.Proc, ModuleBase);
				uint flNewProtect;
				Memory.VirtualProtectEx(this.hProc, moduleBaseAddress + Address, (UIntPtr)((ulong)((long)write.Length)), 64U, out flNewProtect);
				IntPtr intPtr;
				Memory.WriteProcessMemory(this.hProc, moduleBaseAddress + Address, write, write.Length, out intPtr);
				Memory.VirtualProtectEx(this.hProc, moduleBaseAddress + Address, (UIntPtr)((ulong)((long)write.Length)), flNewProtect, out flNewProtect);
			}
			catch
			{
			}
		}

		// Token: 0x06000106 RID: 262 RVA: 0x00003C38 File Offset: 0x00001E38
		public void PatchMemory(int Address, byte[] write)
		{
			try
			{
				uint flNewProtect;
				Memory.VirtualProtectEx(this.hProc, (IntPtr)Address, (UIntPtr)((ulong)((long)write.Length)), 64U, out flNewProtect);
				Memory.WriteProcessMemory(this.hProc, (IntPtr)Address, write, write.Length, IntPtr.Zero);
				Memory.VirtualProtectEx(this.hProc, (IntPtr)Address, (UIntPtr)((ulong)((long)write.Length)), flNewProtect, out flNewProtect);
			}
			catch
			{
			}
		}

		// Token: 0x06000107 RID: 263 RVA: 0x00003CB4 File Offset: 0x00001EB4
		public void NopMemory(string ModuleBase, int Address, int length)
		{
			byte[] array = new byte[length];
			for (int i = 0; i < array.Length; i++)
			{
				array[i] = 144;
			}
			this.PatchMemory(ModuleBase, Address, array);
		}

		// Token: 0x06000108 RID: 264 RVA: 0x00003CE8 File Offset: 0x00001EE8
		public void NopMemory(int Address, int length)
		{
			byte[] array = new byte[length];
			for (int i = 0; i < array.Length; i++)
			{
				array[i] = 144;
			}
			this.PatchMemory(Address, array);
		}

		// Token: 0x06000109 RID: 265 RVA: 0x00003D1C File Offset: 0x00001F1C
		public byte[] ReadMemory(Pointer pointer, long length)
		{
			byte[] result;
			try
			{
				byte[] array = new byte[length];
				IntPtr moduleBaseAddress = Memory.GetModuleBaseAddress(this.Proc, pointer.ModuleBase);
				IntPtr lpBaseAddress = Memory.FindAddressWithPointer(this.hProc, moduleBaseAddress + pointer.PointerAddress, pointer.Offsets);
				Memory.ReadProcessMemory(this.hProc, lpBaseAddress, array, (UIntPtr)((ulong)length), IntPtr.Zero);
				result = array;
			}
			catch (Exception ex)
			{
				throw ex;
			}
			return result;
		}

		// Token: 0x0600010A RID: 266 RVA: 0x00003D90 File Offset: 0x00001F90
		public int ReadInt(Pointer pointer)
		{
			int result;
			try
			{
				byte[] array = new byte[4];
				IntPtr moduleBaseAddress = Memory.GetModuleBaseAddress(this.Proc, pointer.ModuleBase);
				IntPtr lpBaseAddress = Memory.FindAddressWithPointer(this.hProc, moduleBaseAddress + pointer.PointerAddress, pointer.Offsets);
				Memory.ReadProcessMemory(this.hProc, lpBaseAddress, array, (UIntPtr)4UL, IntPtr.Zero);
				result = BitConverter.ToInt32(array, 0);
			}
			catch (Exception ex)
			{
				throw ex;
			}
			return result;
		}

		// Token: 0x0600010B RID: 267 RVA: 0x00003E0C File Offset: 0x0000200C
		public float ReadFloat(Pointer pointer)
		{
			float result;
			try
			{
				byte[] array = new byte[4];
				IntPtr moduleBaseAddress = Memory.GetModuleBaseAddress(this.Proc, pointer.ModuleBase);
				IntPtr lpBaseAddress = Memory.FindAddressWithPointer(this.hProc, moduleBaseAddress + pointer.PointerAddress, pointer.Offsets);
				Memory.ReadProcessMemory(this.hProc, lpBaseAddress, array, (UIntPtr)4UL, IntPtr.Zero);
				result = BitConverter.ToSingle(array, 0);
			}
			catch (Exception ex)
			{
				throw ex;
			}
			return result;
		}

		// Token: 0x0600010C RID: 268 RVA: 0x00003E88 File Offset: 0x00002088
		public bool ReadBool(Pointer pointer)
		{
			bool result;
			try
			{
				byte[] array = new byte[4];
				IntPtr moduleBaseAddress = Memory.GetModuleBaseAddress(this.Proc, pointer.ModuleBase);
				IntPtr lpBaseAddress = Memory.FindAddressWithPointer(this.hProc, moduleBaseAddress + pointer.PointerAddress, pointer.Offsets);
				Memory.ReadProcessMemory(this.hProc, lpBaseAddress, array, (UIntPtr)4UL, IntPtr.Zero);
				result = BitConverter.ToBoolean(array, 0);
			}
			catch (Exception ex)
			{
				throw ex;
			}
			return result;
		}

		// Token: 0x0400005B RID: 91
		private string ProcessName;

		// Token: 0x0400005C RID: 92
		private Process Proc;

		// Token: 0x0400005D RID: 93
		private IntPtr hProc;

		// Token: 0x02000052 RID: 82
		[Flags]
		public enum ProcessAccessFlags : uint
		{
			// Token: 0x04000388 RID: 904
			All = 2035711U,
			// Token: 0x04000389 RID: 905
			Terminate = 1U,
			// Token: 0x0400038A RID: 906
			CreateThread = 2U,
			// Token: 0x0400038B RID: 907
			VirtualMemoryOperation = 8U,
			// Token: 0x0400038C RID: 908
			VirtualMemoryRead = 16U,
			// Token: 0x0400038D RID: 909
			VirtualMemoryWrite = 32U,
			// Token: 0x0400038E RID: 910
			DuplicateHandle = 64U,
			// Token: 0x0400038F RID: 911
			CreateProcess = 128U,
			// Token: 0x04000390 RID: 912
			SetQuota = 256U,
			// Token: 0x04000391 RID: 913
			SetInformation = 512U,
			// Token: 0x04000392 RID: 914
			QueryInformation = 1024U,
			// Token: 0x04000393 RID: 915
			QueryLimitedInformation = 4096U,
			// Token: 0x04000394 RID: 916
			Synchronize = 1048576U
		}

		// Token: 0x02000053 RID: 83
		public enum SnapshotFlags : uint
		{
			// Token: 0x04000396 RID: 918
			HeapList = 1U,
			// Token: 0x04000397 RID: 919
			Process,
			// Token: 0x04000398 RID: 920
			Thread = 4U,
			// Token: 0x04000399 RID: 921
			Module = 8U,
			// Token: 0x0400039A RID: 922
			Module32 = 16U,
			// Token: 0x0400039B RID: 923
			Inherit = 2147483648U,
			// Token: 0x0400039C RID: 924
			All = 31U,
			// Token: 0x0400039D RID: 925
			NoHeaps = 1073741824U
		}

		// Token: 0x02000054 RID: 84
		public struct MODULEENTRY32
		{
			// Token: 0x0400039E RID: 926
			internal uint dwSize;

			// Token: 0x0400039F RID: 927
			internal uint th32ModuleID;

			// Token: 0x040003A0 RID: 928
			internal uint th32ProcessID;

			// Token: 0x040003A1 RID: 929
			internal uint GlblcntUsage;

			// Token: 0x040003A2 RID: 930
			internal uint ProccntUsage;

			// Token: 0x040003A3 RID: 931
			internal IntPtr modBaseAddr;

			// Token: 0x040003A4 RID: 932
			internal uint modBaseSize;

			// Token: 0x040003A5 RID: 933
			internal IntPtr hModule;

			// Token: 0x040003A6 RID: 934
			[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 256)]
			internal string szModule;

			// Token: 0x040003A7 RID: 935
			[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 260)]
			internal string szExePath;
		}
	}
}
