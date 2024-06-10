using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace AnyDesk
{
	// Token: 0x0200000C RID: 12
	public class DotNetScanMemory_SmoLL
	{
		// Token: 0x06000031 RID: 49
		[DllImport("kernel32.dll")]
		public static extern uint GetLastError();

		// Token: 0x06000032 RID: 50
		[DllImport("kernel32.dll")]
		public static extern int OpenProcess(uint dwDesiredAccess, bool bInheritHandle, int dwProcessId);

		// Token: 0x06000033 RID: 51
		[DllImport("kernel32.dll")]
		protected static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] buffer, uint size, int lpNumberOfBytesRead);

		// Token: 0x06000034 RID: 52
		[DllImport("kernel32.dll")]
		public static extern bool WriteProcessMemory(int hProcess, int lpBaseAddress, byte[] buffer, int size, int lpNumberOfBytesWritten);

		// Token: 0x06000035 RID: 53
		[DllImport("kernel32.dll", SetLastError = true)]
		protected static extern int VirtualQueryEx(IntPtr hProcess, IntPtr lpAddress, out DotNetScanMemory_SmoLL.MEMORY_BASIC_INFORMATION lpBuffer, int dwLength);

		// Token: 0x17000004 RID: 4
		// (get) Token: 0x06000036 RID: 54 RVA: 0x000020EE File Offset: 0x000002EE
		// (set) Token: 0x06000037 RID: 55 RVA: 0x000020F6 File Offset: 0x000002F6
		private List<DotNetScanMemory_SmoLL.MEMORY_BASIC_INFORMATION> MappedMemory { get; set; }

		// Token: 0x06000038 RID: 56 RVA: 0x000031F8 File Offset: 0x000013F8
		public static string GetSystemMessage(uint errorCode)
		{
			return new Win32Exception((int)errorCode).Message;
		}

		// Token: 0x06000039 RID: 57 RVA: 0x00003218 File Offset: 0x00001418
		protected void MemInfo(IntPtr pHandle)
		{
			IntPtr intPtr = (IntPtr)0;
			intPtr = (IntPtr)((long)this.InicioScan);
			while ((long)intPtr <= (long)this.FimScan)
			{
				DotNetScanMemory_SmoLL.MEMORY_BASIC_INFORMATION memory_BASIC_INFORMATION = default(DotNetScanMemory_SmoLL.MEMORY_BASIC_INFORMATION);
				bool flag = DotNetScanMemory_SmoLL.VirtualQueryEx(pHandle, intPtr, out memory_BASIC_INFORMATION, Marshal.SizeOf<DotNetScanMemory_SmoLL.MEMORY_BASIC_INFORMATION>(memory_BASIC_INFORMATION)) == 0;
				bool flag2 = flag;
				bool flag3 = flag2;
				if (flag3)
				{
					break;
				}
				bool flag4 = (memory_BASIC_INFORMATION.State & 4096U) != 0U && (memory_BASIC_INFORMATION.Protect & 256U) != 256U;
				bool flag5 = flag4;
				bool flag6 = flag5;
				if (flag6)
				{
					this.MappedMemory.Add(memory_BASIC_INFORMATION);
				}
				intPtr = new IntPtr(memory_BASIC_INFORMATION.BaseAddress.ToInt32() + (int)memory_BASIC_INFORMATION.RegionSize);
			}
		}

		// Token: 0x0600003A RID: 58 RVA: 0x000032E0 File Offset: 0x000014E0
		protected IntPtr ScanInBuff(IntPtr Address, byte[] Buff, string[] StrMask)
		{
			int num = Buff.Length;
			int num2 = StrMask.Length;
			int num3 = num2 - 1;
			byte[] array = new byte[num2];
			for (int i = 0; i < num2; i++)
			{
				bool flag = StrMask[i] == "??";
				bool flag2 = flag;
				bool flag3 = flag2;
				if (flag3)
				{
					array[i] = 0;
				}
				else
				{
					array[i] = Convert.ToByte(StrMask[i], 16);
				}
			}
			for (int j = 0; j <= num - num2 - 1; j++)
			{
				bool flag4 = Buff[j] == array[0];
				bool flag5 = flag4;
				bool flag6 = flag5;
				if (flag6)
				{
					int num4 = num3;
					while (StrMask[num4] == "??" || Buff[j + num4] == array[num4])
					{
						bool flag7 = num4 == 0;
						bool flag8 = flag7;
						bool flag9 = flag8;
						if (flag9)
						{
							bool stopTheFirst = this.StopTheFirst;
							bool flag10 = stopTheFirst;
							bool flag11 = flag10;
							if (flag11)
							{
								return new IntPtr(j);
							}
							bool flag12 = (long)(Address.ToInt32() + j) >= (long)this.InicioScan && (long)(Address.ToInt32() + j) <= (long)this.FimScan;
							bool flag13 = flag12;
							bool flag14 = flag13;
							if (flag14)
							{
								this.AddressList.Add((IntPtr)(Address.ToInt32() + j));
								break;
							}
							break;
						}
						else
						{
							num4--;
						}
					}
				}
			}
			return IntPtr.Zero;
		}

		// Token: 0x0600003B RID: 59 RVA: 0x00003468 File Offset: 0x00001668
		public Process GetPID(string ProcessName)
		{
			try
			{
				return Process.GetProcessesByName(ProcessName)[0];
			}
			catch
			{
			}
			return null;
		}

		// Token: 0x0600003C RID: 60 RVA: 0x0000349C File Offset: 0x0000169C
		public IntPtr[] ScanArray(Process P, string ArrayString)
		{
			EnablePrivileges.GoDebugPriv();
			IntPtr[] array = new IntPtr[1];
			Logs.DeleteLog();
			bool flag = P == null;
			bool flag2 = flag;
			bool flag3 = flag2;
			IntPtr[] result;
			if (flag3)
			{
				result = new IntPtr[1];
			}
			else
			{
				this.Attacked = Process.GetProcessById(P.Id);
				string[] array2 = ArrayString.Split(new char[]
				{
					" "[0]
				});
				for (int i = 0; i < array2.Length; i++)
				{
					bool flag4 = array2[i] == "?";
					bool flag5 = flag4;
					bool flag6 = flag5;
					if (flag6)
					{
						array2[i] = "??";
					}
				}
				this.MappedMemory = new List<DotNetScanMemory_SmoLL.MEMORY_BASIC_INFORMATION>();
				this.MemInfo(this.Attacked.Handle);
				for (int j = 0; j < this.MappedMemory.Count; j++)
				{
					byte[] array3 = new byte[this.MappedMemory[j].RegionSize];
					DotNetScanMemory_SmoLL.ReadProcessMemory(this.Attacked.Handle, this.MappedMemory[j].BaseAddress, array3, this.MappedMemory[j].RegionSize, 0);
					IntPtr value = IntPtr.Zero;
					bool flag7 = array3.Length != 0;
					bool flag8 = flag7;
					bool flag9 = flag8;
					if (flag9)
					{
						value = this.ScanInBuff(this.MappedMemory[j].BaseAddress, array3, array2);
					}
					bool flag10 = this.StopTheFirst && value != IntPtr.Zero;
					bool flag11 = flag10;
					bool flag12 = flag11;
					if (flag12)
					{
						array = new IntPtr[0];
						array[0] = (IntPtr)(this.MappedMemory[j].BaseAddress.ToInt32() + value.ToInt32());
						return array;
					}
				}
				bool flag13 = !this.StopTheFirst && this.AddressList.Count > 0;
				bool flag14 = flag13;
				bool flag15 = flag14;
				if (flag15)
				{
					array = new IntPtr[this.AddressList.Count];
					for (int k = 0; k < this.AddressList.Count; k++)
					{
						array[k] = this.AddressList[k];
					}
					this.AddressList.Clear();
					result = array;
				}
				else
				{
					result = array;
				}
			}
			return result;
		}

		// Token: 0x0600003D RID: 61 RVA: 0x00003708 File Offset: 0x00001908
		public bool WriteArray(IntPtr address, string ArrayString)
		{
			bool flag = this.Attacked == null;
			bool flag2 = flag;
			bool flag3 = flag2;
			bool result;
			if (flag3)
			{
				result = false;
			}
			else
			{
				string[] array = ArrayString.Split(new char[]
				{
					" "[0]
				});
				byte[] array2 = new byte[array.Length];
				for (int i = 0; i < array.Length; i++)
				{
					bool flag4 = array[i] == "?" || array[i] == "??";
					bool flag5 = flag4;
					bool flag6 = flag5;
					if (flag6)
					{
						array2[i] = 0;
					}
					else
					{
						array2[i] = Convert.ToByte(array[i], 16);
					}
				}
				result = DotNetScanMemory_SmoLL.WriteProcessMemory((int)this.Attacked.Handle, address.ToInt32(), array2, array2.Length, 0);
			}
			return result;
		}

		// Token: 0x0600003E RID: 62 RVA: 0x000037F0 File Offset: 0x000019F0
		public Process GetChrome()
		{
			foreach (Process process in Process.GetProcessesByName("chrome"))
			{
				try
				{
					foreach (object obj in process.Modules)
					{
						bool flag = ((ProcessModule)obj).FileName.Contains("pepflashplayer.dll");
						bool flag2 = flag;
						bool flag3 = flag2;
						if (flag3)
						{
							return process;
						}
					}
				}
				catch
				{
				}
			}
			return null;
		}

		// Token: 0x0400005C RID: 92
		private uint PROCESS_ALL_ACCESS = 127231U;

		// Token: 0x0400005D RID: 93
		public ulong InicioScan;

		// Token: 0x0400005E RID: 94
		public ulong FimScan = 0UL;

		// Token: 0x0400005F RID: 95
		private bool StopTheFirst;

		// Token: 0x04000060 RID: 96
		private Process Attacked;

		// Token: 0x04000061 RID: 97
		private List<IntPtr> AddressList = new List<IntPtr>();

		// Token: 0x0200000D RID: 13
		protected struct MEMORY_BASIC_INFORMATION
		{
			// Token: 0x04000062 RID: 98
			public IntPtr BaseAddress;

			// Token: 0x04000063 RID: 99
			public IntPtr AllocationBase;

			// Token: 0x04000064 RID: 100
			public uint AllocationProtect;

			// Token: 0x04000065 RID: 101
			public uint RegionSize;

			// Token: 0x04000066 RID: 102
			public uint State;

			// Token: 0x04000067 RID: 103
			public uint Protect;

			// Token: 0x04000068 RID: 104
			public uint Type;
		}

		// Token: 0x0200000E RID: 14
		private enum AllocationProtectEnum : uint
		{
			// Token: 0x0400006A RID: 106
			PAGE_EXECUTE = 16U,
			// Token: 0x0400006B RID: 107
			PAGE_EXECUTE_READ = 32U,
			// Token: 0x0400006C RID: 108
			PAGE_EXECUTE_READWRITE = 64U,
			// Token: 0x0400006D RID: 109
			PAGE_EXECUTE_WRITECOPY = 128U,
			// Token: 0x0400006E RID: 110
			PAGE_NOACCESS = 1U,
			// Token: 0x0400006F RID: 111
			PAGE_READONLY,
			// Token: 0x04000070 RID: 112
			PAGE_READWRITE = 4U,
			// Token: 0x04000071 RID: 113
			PAGE_WRITECOPY = 8U,
			// Token: 0x04000072 RID: 114
			PAGE_GUARD = 256U,
			// Token: 0x04000073 RID: 115
			PAGE_NOCACHE = 512U,
			// Token: 0x04000074 RID: 116
			PAGE_WRITECOMBINE = 1024U
		}

		// Token: 0x0200000F RID: 15
		private enum StateEnum : uint
		{
			// Token: 0x04000076 RID: 118
			MEM_COMMIT = 4096U,
			// Token: 0x04000077 RID: 119
			MEM_FREE = 65536U,
			// Token: 0x04000078 RID: 120
			MEM_RESERVE = 8192U
		}

		// Token: 0x02000010 RID: 16
		private enum TypeEnum : uint
		{
			// Token: 0x0400007A RID: 122
			MEM_IMAGE = 16777216U,
			// Token: 0x0400007B RID: 123
			MEM_MAPPED = 262144U,
			// Token: 0x0400007C RID: 124
			MEM_PRIVATE = 131072U
		}
	}
}
