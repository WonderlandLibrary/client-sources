using System;
using apollo_lite.Properties;

// Token: 0x02000011 RID: 17
internal class Class12
{
	// Token: 0x0600007E RID: 126 RVA: 0x000077CC File Offset: 0x000059CC
	public bool method_0()
	{
		if (Settings.Default.leftDown)
		{
			Settings.Default.leftDown = false;
			return true;
		}
		return false;
	}

	// Token: 0x0600007F RID: 127 RVA: 0x000077F4 File Offset: 0x000059F4
	public bool method_1()
	{
		if (Settings.Default.leftUp)
		{
			Settings.Default.leftUp = false;
			return true;
		}
		return false;
	}

	// Token: 0x06000080 RID: 128 RVA: 0x0000781C File Offset: 0x00005A1C
	public bool method_2()
	{
		if (Settings.Default.rightUp)
		{
			Settings.Default.rightUp = false;
			return true;
		}
		return false;
	}

	// Token: 0x06000081 RID: 129 RVA: 0x00007844 File Offset: 0x00005A44
	public bool method_3()
	{
		if (Settings.Default.rightDown)
		{
			Settings.Default.rightDown = false;
			return true;
		}
		return false;
	}

	// Token: 0x06000082 RID: 130 RVA: 0x0000786C File Offset: 0x00005A6C
	public bool method_4()
	{
		if (Settings.Default.middleUp)
		{
			Settings.Default.middleUp = false;
			return true;
		}
		return false;
	}

	// Token: 0x06000083 RID: 131 RVA: 0x00007894 File Offset: 0x00005A94
	public bool method_5()
	{
		if (Settings.Default.middleDown)
		{
			Settings.Default.middleDown = false;
			return true;
		}
		return false;
	}
}
