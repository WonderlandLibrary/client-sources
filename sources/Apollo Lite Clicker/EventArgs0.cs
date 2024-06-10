using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;

// Token: 0x02000003 RID: 3
internal class EventArgs0 : HandledEventArgs
{
	// Token: 0x17000005 RID: 5
	// (get) Token: 0x06000008 RID: 8 RVA: 0x00002124 File Offset: 0x00000324
	// (set) Token: 0x06000009 RID: 9 RVA: 0x00002138 File Offset: 0x00000338
	public Class6.Enum0 Enum0_0 { get; private set; }

	// Token: 0x17000006 RID: 6
	// (get) Token: 0x0600000A RID: 10 RVA: 0x0000214C File Offset: 0x0000034C
	// (set) Token: 0x0600000B RID: 11 RVA: 0x00002160 File Offset: 0x00000360
	public Class6.Struct6 Struct6_0 { get; private set; }

	// Token: 0x0600000C RID: 12 RVA: 0x00002174 File Offset: 0x00000374
	public EventArgs0(Class6.Struct6 struct6_1, Class6.Enum0 enum0_1)
	{
		this.Struct6_0 = struct6_1;
		this.Enum0_0 = enum0_1;
	}

	// Token: 0x04000003 RID: 3
	[CompilerGenerated]
	private Class6.Enum0 enum0_0;

	// Token: 0x04000004 RID: 4
	[CompilerGenerated]
	private Class6.Struct6 struct6_0;
}
