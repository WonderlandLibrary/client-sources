using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace Eternium_mcpe_client.MemoryManagement
{
	// Token: 0x0200000E RID: 14
	public class Memory
	{
		// Token: 0x06000033 RID: 51
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr OpenProcess(Memory.ProcessAccessFlags processAccess, bool bInheritHandle, int processId);

		// Token: 0x06000034 RID: 52
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr CreateToolhelp32Snapshot(Memory.SnapshotFlags dwFlags, int th32ProcessID);

		// Token: 0x06000035 RID: 53
		[DllImport("kernel32.dll")]
		public static extern bool Module32First(IntPtr hSnapshot, ref Memory.MODULEENTRY32 lpme);

		// Token: 0x06000036 RID: 54
		[DllImport("kernel32.dll")]
		public static extern bool Module32Next(IntPtr hSnapshot, ref Memory.MODULEENTRY32 lpme);

		// Token: 0x06000037 RID: 55
		[DllImport("coredll.dll", CharSet = CharSet.Auto, SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool CloseHandle(IntPtr hObject);

		// Token: 0x06000038 RID: 56
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [MarshalAs(UnmanagedType.AsAny)] object lpBuffer, int dwSize, out IntPtr lpNumberOfBytesWritten);

		// Token: 0x06000039 RID: 57
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [MarshalAs(UnmanagedType.AsAny)] object lpBuffer, int dwSize, IntPtr lpNumberOfBytesWritten);

		// Token: 0x0600003A RID: 58
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] lpBuffer, int nSize, out IntPtr lpNumberOfBytesRead);

		// Token: 0x0600003B RID: 59
		[DllImport("kernel32.dll")]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [Out] byte[] lpBuffer, UIntPtr nSize, IntPtr lpNumberOfBytesRead);

		// Token: 0x0600003C RID: 60
		[DllImport("kernel32.dll")]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [Out] byte[] lpBuffer, IntPtr nSize, out ulong lpNumberOfBytesRead);

		// Token: 0x0600003D RID: 61
		[DllImport("kernel32.dll")]
		public static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, [Out] IntPtr lpBuffer, IntPtr nSize, out ulong lpNumberOfBytesRead);

		// Token: 0x0600003E RID: 62
		[DllImport("kernel32.dll")]
		public static extern bool VirtualProtectEx(IntPtr hProcess, IntPtr lpAddress, UIntPtr dwSize, uint flNewProtect, out uint lpflOldProtect);

		// Token: 0x0600003F RID: 63
		[DllImport("kernel32.dll")]
		public static extern bool VirtualProtectEx(IntPtr hProcess, IntPtr lpAddress, uint dwSize, uint flNewProtect, out uint lpflOldProtect);

		// Token: 0x06000040 RID: 64 RVA: 0x00005578 File Offset: 0x00003778
		public static IntPtr GetModuleBaseAddress(Process proc, string modName)
		{
			IntPtr result = IntPtr.Zero;
			foreach (object obj in proc.Modules)
			{
				ProcessModule processModule = (ProcessModule)obj;
				bool flag = processModule.ModuleName == modName;
				if (flag)
				{
					result = processModule.BaseAddress;
					break;
				}
			}
			return result;
		}

		// Token: 0x06000041 RID: 65 RVA: 0x000055FC File Offset: 0x000037FC
		public static IntPtr GetModuleBaseAddress(int procId, string modName)
		{
			IntPtr result = IntPtr.Zero;
			IntPtr intPtr = Memory.CreateToolhelp32Snapshot((Memory.SnapshotFlags)24U, procId);
			bool flag = intPtr.ToInt64() != -1L;
			if (flag)
			{
				Memory.MODULEENTRY32 moduleentry = default(Memory.MODULEENTRY32);
				moduleentry.dwSize = (uint)Marshal.SizeOf(typeof(Memory.MODULEENTRY32));
				bool flag2 = Memory.Module32First(intPtr, ref moduleentry);
				if (flag2)
				{
					for (;;)
					{
						bool flag3 = moduleentry.szModule.Equals(modName);
						if (flag3)
						{
							break;
						}
						if (!Memory.Module32Next(intPtr, ref moduleentry))
						{
							goto IL_7C;
						}
					}
					result = moduleentry.modBaseAddr;
					IL_7C:;
				}
			}
			Memory.CloseHandle(intPtr);
			return result;
		}

		// Token: 0x06000042 RID: 66 RVA: 0x00005698 File Offset: 0x00003898
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

		// Token: 0x17000004 RID: 4
		// (get) Token: 0x06000043 RID: 67 RVA: 0x00005710 File Offset: 0x00003910
		public Process GetProcID
		{
			get
			{
				return this.Proc;
			}
		}

		// Token: 0x06000044 RID: 68 RVA: 0x00005728 File Offset: 0x00003928
		public Memory(string ProcessName)
		{
			this.ProcessName = ProcessName;
			this.ConnectToProcess();
		}

		// Token: 0x06000045 RID: 69 RVA: 0x00005740 File Offset: 0x00003940
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

		// Token: 0x06000046 RID: 70 RVA: 0x00005798 File Offset: 0x00003998
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

		// Token: 0x06000047 RID: 71 RVA: 0x00005800 File Offset: 0x00003A00
		public void WriteMemory(int address, object value)
		{
			IntPtr intPtr;
			Memory.WriteProcessMemory(this.hProc, (IntPtr)address, value, 4, out intPtr);
		}

		// Token: 0x06000048 RID: 72 RVA: 0x00005824 File Offset: 0x00003A24
		public void WriteMemory(long address, object value)
		{
			IntPtr intPtr;
			Memory.WriteProcessMemory(this.hProc, (IntPtr)address, value, 4, out intPtr);
		}

		// Token: 0x06000049 RID: 73 RVA: 0x00005848 File Offset: 0x00003A48
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

		// Token: 0x0600004A RID: 74 RVA: 0x000058D4 File Offset: 0x00003AD4
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

		// Token: 0x0600004B RID: 75 RVA: 0x00005954 File Offset: 0x00003B54
		public void NopMemory(string ModuleBase, int Address, int length)
		{
			byte[] array = new byte[length];
			for (int i = 0; i < array.Length; i++)
			{
				array[i] = 144;
			}
			this.PatchMemory(ModuleBase, Address, array);
		}

		// Token: 0x0600004C RID: 76 RVA: 0x00005990 File Offset: 0x00003B90
		public void NopMemory(int Address, int length)
		{
			byte[] array = new byte[length];
			for (int i = 0; i < array.Length; i++)
			{
				array[i] = 144;
			}
			this.PatchMemory(Address, array);
		}

		// Token: 0x0600004D RID: 77 RVA: 0x000059CC File Offset: 0x00003BCC
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

		// Token: 0x0600004E RID: 78 RVA: 0x00005A48 File Offset: 0x00003C48
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

		// Token: 0x0600004F RID: 79 RVA: 0x00005ACC File Offset: 0x00003CCC
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

		// Token: 0x06000050 RID: 80 RVA: 0x00005B50 File Offset: 0x00003D50
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

		// Token: 0x0400003D RID: 61
		private string ProcessName;

		// Token: 0x0400003E RID: 62
		private Process Proc;

		// Token: 0x0400003F RID: 63
		private IntPtr hProc;

		// Token: 0x0200001F RID: 31
		[Flags]
		public enum ProcessAccessFlags : uint
		{
			// Token: 0x04000087 RID: 135
			All = 2035711U,
			// Token: 0x04000088 RID: 136
			Terminate = 1U,
			// Token: 0x04000089 RID: 137
			CreateThread = 2U,
			// Token: 0x0400008A RID: 138
			VirtualMemoryOperation = 8U,
			// Token: 0x0400008B RID: 139
			VirtualMemoryRead = 16U,
			// Token: 0x0400008C RID: 140
			VirtualMemoryWrite = 32U,
			// Token: 0x0400008D RID: 141
			DuplicateHandle = 64U,
			// Token: 0x0400008E RID: 142
			CreateProcess = 128U,
			// Token: 0x0400008F RID: 143
			SetQuota = 256U,
			// Token: 0x04000090 RID: 144
			SetInformation = 512U,
			// Token: 0x04000091 RID: 145
			QueryInformation = 1024U,
			// Token: 0x04000092 RID: 146
			QueryLimitedInformation = 4096U,
			// Token: 0x04000093 RID: 147
			Synchronize = 1048576U
		}

		// Token: 0x02000020 RID: 32
		public enum SnapshotFlags : uint
		{
			// Token: 0x04000095 RID: 149
			HeapList = 1U,
			// Token: 0x04000096 RID: 150
			Process,
			// Token: 0x04000097 RID: 151
			Thread = 4U,
			// Token: 0x04000098 RID: 152
			Module = 8U,
			// Token: 0x04000099 RID: 153
			Module32 = 16U,
			// Token: 0x0400009A RID: 154
			Inherit = 2147483648U,
			// Token: 0x0400009B RID: 155
			All = 31U,
			// Token: 0x0400009C RID: 156
			NoHeaps = 1073741824U
		}

		// Token: 0x02000021 RID: 33
		public struct MODULEENTRY32
		{
			// Token: 0x0400009D RID: 157
			internal uint dwSize;

			// Token: 0x0400009E RID: 158
			internal uint th32ModuleID;

			// Token: 0x0400009F RID: 159
			internal uint th32ProcessID;

			// Token: 0x040000A0 RID: 160
			internal uint GlblcntUsage;

			// Token: 0x040000A1 RID: 161
			internal uint ProccntUsage;

			// Token: 0x040000A2 RID: 162
			internal IntPtr modBaseAddr;

			// Token: 0x040000A3 RID: 163
			internal uint modBaseSize;

			// Token: 0x040000A4 RID: 164
			internal IntPtr hModule;

			// Token: 0x040000A5 RID: 165
			[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 256)]
			internal string szModule;

			// Token: 0x040000A6 RID: 166
			[MarshalAs(UnmanagedType.ByValTStr, SizeConst = 260)]
			internal string szExePath;
		}
	}
}
