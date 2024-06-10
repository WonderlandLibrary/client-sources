using System;
using System.Runtime.InteropServices;

// Token: 0x02000044 RID: 68
internal static class 251C72B3
{
	// Token: 0x06000247 RID: 583
	[DllImport("kernel32", CharSet = CharSet.Auto, EntryPoint = "CreateFile", SetLastError = true)]
	public static extern IntPtr 7C8842AF(string 2A643925, int 705233F1, int 33520561, IntPtr 51CC4B36, int 0FA50A41, int 74E94EED, IntPtr 3698135F);

	// Token: 0x06000248 RID: 584
	[DllImport("kernel32", CharSet = CharSet.Auto, EntryPoint = "CreateFileMapping", SetLastError = true)]
	public static extern IntPtr 52F310EC(IntPtr 22E51F30, IntPtr 146E7647, 251C72B3.2B4E74E4 4C35576F, int 50631090, int 1E1A5DB7, string 2EE3714C);

	// Token: 0x06000249 RID: 585
	[DllImport("kernel32", EntryPoint = "MapViewOfFile", SetLastError = true)]
	public static extern IntPtr 4F4F2FE8(IntPtr 16777733, 251C72B3.446C4CDC 63FA31A3, int 09E005F3, int 6A0C40BA, IntPtr 4FF353EC);

	// Token: 0x0600024A RID: 586
	[DllImport("kernel32", EntryPoint = "UnmapViewOfFile", SetLastError = true)]
	public static extern bool 49896AE9(IntPtr 30C626DA);

	// Token: 0x0600024B RID: 587
	[DllImport("kernel32", EntryPoint = "CloseHandle", SetLastError = true)]
	public static extern bool 41315395(IntPtr 09EB0F40);

	// Token: 0x0600024C RID: 588
	[DllImport("kernel32", EntryPoint = "GetFileSize", SetLastError = true)]
	public static extern uint 4F632AFC(IntPtr 385403CE, IntPtr 37725BC0);

	// Token: 0x0600024D RID: 589
	[DllImport("kernel32", EntryPoint = "VirtualProtect", SetLastError = true)]
	public static extern bool 3F7B344F(IntPtr 6CD44C8C, UIntPtr 0DB71DAF, 251C72B3.2B4E74E4 560B0C18, out 251C72B3.2B4E74E4 44C161C9);

	// Token: 0x0600024E RID: 590
	[DllImport("kernel32", EntryPoint = "IsDebuggerPresent")]
	public static extern bool 23B35C2F();

	// Token: 0x0600024F RID: 591
	[DllImport("kernel32", EntryPoint = "CheckRemoteDebuggerPresent")]
	public static extern bool 34F14CFA();

	// Token: 0x06000250 RID: 592
	[DllImport("ntdll", EntryPoint = "NtQueryInformationProcess")]
	public static extern int 63227656(IntPtr 6F572BA6, int 4F030684, byte[] 3BE505FF, uint 2F300A96, out uint 6B244A25);

	// Token: 0x0400015C RID: 348
	public const int 605024B7 = -2147483648;

	// Token: 0x0400015D RID: 349
	public const int 217A713A = 3;

	// Token: 0x0400015E RID: 350
	public const int 041408A8 = 128;

	// Token: 0x0400015F RID: 351
	public const int 39354B9E = 1;

	// Token: 0x04000160 RID: 352
	public const int 4B667D74 = 2;

	// Token: 0x04000161 RID: 353
	public static readonly IntPtr 0FC46958 = new IntPtr(-1);

	// Token: 0x04000162 RID: 354
	public static readonly IntPtr 4D4528F6 = IntPtr.Zero;

	// Token: 0x04000163 RID: 355
	public static readonly IntPtr 332761B7 = new IntPtr(-1);

	// Token: 0x0200006B RID: 107
	public enum 2B4E74E4 : uint
	{
		// Token: 0x040001C2 RID: 450
		51550FA4 = 1U,
		// Token: 0x040001C3 RID: 451
		0EE1661B,
		// Token: 0x040001C4 RID: 452
		17497E1E = 4U,
		// Token: 0x040001C5 RID: 453
		3DB2392C = 8U,
		// Token: 0x040001C6 RID: 454
		14C861C8 = 16U,
		// Token: 0x040001C7 RID: 455
		3D7E7BA7 = 32U,
		// Token: 0x040001C8 RID: 456
		41015664 = 64U,
		// Token: 0x040001C9 RID: 457
		77157EE7 = 256U
	}

	// Token: 0x0200006C RID: 108
	public enum 446C4CDC : uint
	{
		// Token: 0x040001CB RID: 459
		12B90D71 = 1U,
		// Token: 0x040001CC RID: 460
		1FD6236D,
		// Token: 0x040001CD RID: 461
		61062514 = 4U,
		// Token: 0x040001CE RID: 462
		208A5CB1 = 31U
	}
}
