using System;
using System.Drawing;

namespace WindowsApplication1
{
	// Token: 0x02000012 RID: 18
	internal class Bloom
	{
		// Token: 0x1700003D RID: 61
		// (get) Token: 0x060000B2 RID: 178 RVA: 0x0000CB28 File Offset: 0x0000AF28
		// (set) Token: 0x060000B3 RID: 179 RVA: 0x0000CB3C File Offset: 0x0000AF3C
		public string Name
		{
			get
			{
				return this._Name;
			}
			set
			{
				this._Name = value;
			}
		}

		// Token: 0x1700003E RID: 62
		// (get) Token: 0x060000B4 RID: 180 RVA: 0x0000CB48 File Offset: 0x0000AF48
		// (set) Token: 0x060000B5 RID: 181 RVA: 0x0000CB5C File Offset: 0x0000AF5C
		public Color Value
		{
			get
			{
				return this._Value;
			}
			set
			{
				this._Value = value;
			}
		}

		// Token: 0x060000B6 RID: 182 RVA: 0x0000CB68 File Offset: 0x0000AF68
		public Bloom()
		{
		}

		// Token: 0x060000B7 RID: 183 RVA: 0x0000CB70 File Offset: 0x0000AF70
		public Bloom(string name, Color value)
		{
			this._Name = name;
			this._Value = value;
		}

		// Token: 0x04000058 RID: 88
		private string _Name;

		// Token: 0x04000059 RID: 89
		private Color _Value;
	}
}
