using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace Wave.Cmr.MemoryManagement
{
	// Token: 0x02000023 RID: 35
	public class Memory
	{
		// Token: 0x06000110 RID: 272
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr OpenProcess(Memory.ProcessAccessFlags processAccess, bool bInheritHandle, int processId);

		// Token: 0x06000111 RID: 273
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr CreateToolhelp32Snapshot(Memory.SnapshotFlags dwFlags, int th32ProcessID);

		// Token: 0x06000112 RID: 274
		[DllImport("kernel32.dll")]
		public static extern bool Module32First(IntPtr hSnapshot, ref Memory.MODULEENTRY32 lpme);

		// Token: 0x06000113 RID: 275
		[DllImport("kernel32.dll")]
		public static extern bool Module32Next(IntPtr hSnapshot, ref Memory.MODULEENTRY32 lpme);

		// Token: 0x06000114 RID: 276
		[DllImport("coredll.dll", CharSet = CharSet.Auto, SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool CloseHandle(IntPtr hObject);

		// Token: 0x06000115 RID: 277
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [MarshalAs(UnmanagedType.AsAny)] object lpBuffer, int dwSize, out IntPtr lpNumberOfBytesWritten);

		// Token: 0x06000116 RID: 278
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [MarshalAs(UnmanagedType.AsAny)] object lpBuffer, int dwSize, IntPtr lpNumberOfBytesWritten);

		// Token: 0x06000117 RID: 279
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] lpBuffer, int nSize, out IntPtr lpNumberOfBytesRead);

		// Token: 0x06000118 RID: 280
		[DllImport("kernel32.dll")]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [Out] byte[] lpBuffer, UIntPtr nSize, IntPtr lpNumberOfBytesRead);

		// Token: 0x06000119 RID: 281
		[DllImport("kernel32.dll")]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [Out] byte[] lpBuffer, IntPtr nSize, out ulong lpNumberOfBytesRead);

		// Token: 0x0600011A RID: 282
		[DllImport("kernel32.dll")]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [Out] IntPtr lpBuffer, IntPtr nSize, out ulong lpNumberOfBytesRead);

		// Token: 0x0600011B RID: 283
		[DllImport("kernel32.dll")]
		public static extern bool VirtualProtectEx(IntPtr hProcess, IntPtr lpAddress, UIntPtr dwSize, uint flNewProtect, out uint lpflOldProtect);

		// Token: 0x0600011C RID: 284
		[DllImport("kernel32.dll")]
		public static extern bool VirtualProtectEx(IntPtr hProcess, IntPtr lpAddress, uint dwSize, uint flNewProtect, out uint lpflOldProtect);

		// Token: 0x0600011D RID: 285 RVA: 0x00004248 File Offset: 0x00002448
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

		// Token: 0x0600011E RID: 286 RVA: 0x000042B8 File Offset: 0x000024B8
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

		// Token: 0x0600011F RID: 287 RVA: 0x00004330 File Offset: 0x00002530
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
		// (get) Token: 0x06000120 RID: 288 RVA: 0x0000439E File Offset: 0x0000259E
		public Process GetProcID
		{
			get
			{
				return this.Proc;
			}
		}

		// Token: 0x06000121 RID: 289 RVA: 0x000043A6 File Offset: 0x000025A6
		public Memory(string ProcessName)
		{
			this.ProcessName = ProcessName;
			this.ConnectToProcess();
		}

		// Token: 0x06000122 RID: 290 RVA: 0x000043BC File Offset: 0x000025BC
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

		// Token: 0x06000123 RID: 291 RVA: 0x00004410 File Offset: 0x00002610
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

		// Token: 0x06000124 RID: 292 RVA: 0x00004474 File Offset: 0x00002674
		public void WriteMemory(int address, object value)
		{
			IntPtr intPtr;
			Memory.WriteProcessMemory(this.hProc, (IntPtr)address, value, 4, out intPtr);
		}

		// Token: 0x06000125 RID: 293 RVA: 0x00004498 File Offset: 0x00002698
		public void WriteMemory(long address, object value)
		{
			IntPtr intPtr;
			Memory.WriteProcessMemory(this.hProc, (IntPtr)address, value, 4, out intPtr);
		}

		// Token: 0x06000126 RID: 294 RVA: 0x000044BC File Offset: 0x000026BC
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

		// Token: 0x06000127 RID: 295 RVA: 0x00004544 File Offset: 0x00002744
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

		// Token: 0x06000128 RID: 296 RVA: 0x000045C0 File Offset: 0x000027C0
		public void NopMemory(string ModuleBase, int Address, int length)
		{
			byte[] array = new byte[length];
			for (int i = 0; i < array.Length; i++)
			{
				array[i] = 144;
			}
			this.PatchMemory(ModuleBase, Address, array);
		}

		// Token: 0x06000129 RID: 297 RVA: 0x000045F4 File Offset: 0x000027F4
		public void NopMemory(int Address, int length)
		{
			byte[] array = new byte[length];
			for (int i = 0; i < array.Length; i++)
			{
				array[i] = 144;
			}
			this.PatchMemory(Address, array);
		}

		// Token: 0x0600012A RID: 298 RVA: 0x00004628 File Offset: 0x00002828
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

		// Token: 0x0600012B RID: 299 RVA: 0x0000469C File Offset: 0x0000289C
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

		// Token: 0x0600012C RID: 300 RVA: 0x00004718 File Offset: 0x00002918
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

		// Token: 0x0600012D RID: 301 RVA: 0x00004794 File Offset: 0x00002994
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

		// Token: 0x04000087 RID: 135
		private string ProcessName;

		// Token: 0x04000088 RID: 136
		private Process Proc;

		// Token: 0x04000089 RID: 137
		private IntPtr hProc;

		// Token: 0x02000056 RID: 86
		[Flags]
		public enum ProcessAccessFlags : uint
		{
			// Token: 0x040003D4 RID: 980
			All = 2035711U,
			// Token: 0x040003D5 RID: 981
			Terminate = 1U,
			// Token: 0x040003D6 RID: 982
			CreateThread = 2U,
			// Token: 0x040003D7 RID: 983
			VirtualMemoryOperation = 8U,
			// Token: 0x040003D8 RID: 984
			VirtualMemoryRead = 16U,
			// Token: 0x040003D9 RID: 985
			VirtualMemoryWrite = 32U,
			// Token: 0x040003DA RID: 986
			DuplicateHandle = 64U,
			// Token: 0x040003DB RID: 987
			CreateProcess = 128U,
			// Token: 0x040003DC RID: 988
			SetQuota = 256U,
			// Token: 0x040003DD RID: 989
			SetInformation = 512U,
			// Token: 0x040003DE RID: 990
			QueryInformation = 1024U,
			// Token: 0x040003DF RID: 991
			QueryLimitedInformation = 4096U,
			// Token: 0x040003E0 RID: 992
			Synchronize = 1048576U
		}

		// Token: 0x02000057 RID: 87
		public enum SnapshotFlags : uint
		{
			// Token: 0x040003E2 RID: 994
			HeapList = 1U,
			// Token: 0x040003E3 RID: 995
			Process,
			// Token: 0x040003E4 RID: 996
			Thread = 4U,
			// Token: 0x040003E5 RID: 997
			Module = 8U,
			// Token: 0x040003E6 RID: 998
			Module32 = 16U,
			// Token: 0x040003E7 RID: 999
			Inherit = 2147483648U,
			// Token: 0x040003E8 RID: 1000
			All = 31U,
			// Token: 0x040003E9 RID: 1001
			NoHeaps = 1073741824U
		}

		// Token: 0x02000058 RID: 88
		public struct MODULEENTRY32
		{
			// Token: 0x040003EA RID: 1002
			internal uint dwSize;

			// Token: 0x040003EB RID: 1003
			internal uint th32ModuleID;

			// Token: 0x040003EC RID: 1004
			internal uint th32ProcessID;

			// Token: 0x040003ED RID: 1005
			internal uint GlblcntUsage;

			// Token: 0x040003EE RID: 1006
			internal uint ProccntUsage;

			// Token: 0x040003EF RID: 1007
			internal IntPtr modBaseAddr;

			// Token: 0x040003F0 RID: 1008
			internal uint modBaseSize;

			// Token: 0x040003F1 RID: 1009
			internal IntPtr hModule;

			// Token: 0x040003F2 RID: 1010
			[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 256)]
			internal string szModule;

			// Token: 0x040003F3 RID: 1011
			[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 260)]
			internal string szExePath;
		}
	}
}
