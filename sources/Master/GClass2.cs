using System;

namespace Client
{
	// Token: 0x020002DD RID: 733
	public class GClass2
	{
		// Token: 0x060035B3 RID: 13747 RVA: 0x000021B1 File Offset: 0x000003B1
		public GClass2()
		{
			this.uint_0 = 2122669477U;
		}

		// Token: 0x060035B4 RID: 13748 RVA: 0x00020874 File Offset: 0x0001EA74
		public uint method_0(uint uint_1)
		{
			uint num = uint_1 ^ this.uint_0;
			this.uint_0 = (GClass3.smethod_0(this.uint_0, 7) ^ num);
			return num;
		}

		// Token: 0x040000B0 RID: 176
		private uint uint_0;
	}
}
