using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;

namespace FlatUI
{
	// Token: 0x02000014 RID: 20
	public static class Helpers
	{
		// Token: 0x060000E3 RID: 227 RVA: 0x00006268 File Offset: 0x00004268
		public static GraphicsPath RoundRec(Rectangle Rectangle, int Curve)
		{
		}

		// Token: 0x060000E4 RID: 228 RVA: 0x00006380 File Offset: 0x00004380
		public static GraphicsPath RoundRect(float x, float y, float w, float h, double r = 0.3, bool TL = true, bool TR = true, bool BR = true, bool BL = true)
		{
		}

		// Token: 0x060000E5 RID: 229 RVA: 0x00006484 File Offset: 0x00004484
		public static GraphicsPath DrawArrow(int x, int y, bool flip)
		{
		}

		// Token: 0x060000E6 RID: 230 RVA: 0x000064FC File Offset: 0x000044FC
		public static FlatColors GetColors(Control control)
		{
		}

		// Token: 0x0400007A RID: 122
		public static Color FlatColor;

		// Token: 0x0400007B RID: 123
		public static readonly StringFormat NearSF;

		// Token: 0x0400007C RID: 124
		public static readonly StringFormat CenterSF;
	}
}
