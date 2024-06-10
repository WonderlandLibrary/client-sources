using System;
using dotClick.Properties;

namespace dotClick
{
	// Token: 0x02000010 RID: 16
	internal class StaticRefresher
	{
		// Token: 0x06000064 RID: 100 RVA: 0x000040FC File Offset: 0x000022FC
		public bool refreshStaticLeftDown()
		{
			bool result;
			if (Settings.Default.leftDown)
			{
				Settings.Default.leftDown = false;
				result = true;
			}
			else
			{
				result = false;
			}
			return result;
		}

		// Token: 0x06000065 RID: 101 RVA: 0x00004128 File Offset: 0x00002328
		public bool refreshStaticLeftUp()
		{
			bool result;
			if (Settings.Default.leftUp)
			{
				Settings.Default.leftUp = false;
				result = true;
			}
			else
			{
				result = false;
			}
			return result;
		}

		// Token: 0x06000066 RID: 102 RVA: 0x00004154 File Offset: 0x00002354
		public bool refreshStaticRightUp()
		{
			bool result;
			if (Settings.Default.rightUp)
			{
				Settings.Default.rightUp = false;
				result = true;
			}
			else
			{
				result = false;
			}
			return result;
		}

		// Token: 0x06000067 RID: 103 RVA: 0x00004180 File Offset: 0x00002380
		public bool refreshStaticRightDown()
		{
			bool result;
			if (Settings.Default.rightDown)
			{
				Settings.Default.rightDown = false;
				result = true;
			}
			else
			{
				result = false;
			}
			return result;
		}
	}
}
