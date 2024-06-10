using System;

namespace uhssvc
{
	// Token: 0x02000017 RID: 23
	internal class ApplicationSettings
	{
		// Token: 0x17000010 RID: 16
		// (get) Token: 0x06000062 RID: 98 RVA: 0x00002462 File Offset: 0x00000662
		// (set) Token: 0x06000063 RID: 99 RVA: 0x00002469 File Offset: 0x00000669
		public static bool Status { get; set; }

		// Token: 0x17000011 RID: 17
		// (get) Token: 0x06000064 RID: 100 RVA: 0x00002471 File Offset: 0x00000671
		// (set) Token: 0x06000065 RID: 101 RVA: 0x00002478 File Offset: 0x00000678
		public static bool DeveloperMode { get; set; }

		// Token: 0x17000012 RID: 18
		// (get) Token: 0x06000066 RID: 102 RVA: 0x00002480 File Offset: 0x00000680
		// (set) Token: 0x06000067 RID: 103 RVA: 0x00002487 File Offset: 0x00000687
		public static string Hash { get; set; }

		// Token: 0x17000013 RID: 19
		// (get) Token: 0x06000068 RID: 104 RVA: 0x0000248F File Offset: 0x0000068F
		// (set) Token: 0x06000069 RID: 105 RVA: 0x00002496 File Offset: 0x00000696
		public static string Version { get; set; }

		// Token: 0x17000014 RID: 20
		// (get) Token: 0x0600006A RID: 106 RVA: 0x0000249E File Offset: 0x0000069E
		// (set) Token: 0x0600006B RID: 107 RVA: 0x000024A5 File Offset: 0x000006A5
		public static string Update_Link { get; set; }

		// Token: 0x17000015 RID: 21
		// (get) Token: 0x0600006C RID: 108 RVA: 0x000024AD File Offset: 0x000006AD
		// (set) Token: 0x0600006D RID: 109 RVA: 0x000024B4 File Offset: 0x000006B4
		public static bool Freemode { get; set; }

		// Token: 0x17000016 RID: 22
		// (get) Token: 0x0600006E RID: 110 RVA: 0x000024BC File Offset: 0x000006BC
		// (set) Token: 0x0600006F RID: 111 RVA: 0x000024C3 File Offset: 0x000006C3
		public static bool Login { get; set; }

		// Token: 0x17000017 RID: 23
		// (get) Token: 0x06000070 RID: 112 RVA: 0x000024CB File Offset: 0x000006CB
		// (set) Token: 0x06000071 RID: 113 RVA: 0x000024D2 File Offset: 0x000006D2
		public static string Name { get; set; }

		// Token: 0x17000018 RID: 24
		// (get) Token: 0x06000072 RID: 114 RVA: 0x000024DA File Offset: 0x000006DA
		// (set) Token: 0x06000073 RID: 115 RVA: 0x000024E1 File Offset: 0x000006E1
		public static bool Register { get; set; }
	}
}
