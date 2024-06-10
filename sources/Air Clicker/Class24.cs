using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x020000B0 RID: 176
[StandardModule]
internal sealed class Class24
{
	// Token: 0x06000843 RID: 2115 RVA: 0x00026968 File Offset: 0x00024B68
	public static GraphicsPath smethod_0(Rectangle rectangle_0, int int_0)
	{
		GraphicsPath graphicsPath = new GraphicsPath();
		checked
		{
			int num = int_0 * 2;
			graphicsPath.AddArc(new Rectangle(rectangle_0.X, rectangle_0.Y, num, num), -180f, 90f);
			graphicsPath.AddArc(new Rectangle(rectangle_0.Width - num + rectangle_0.X, rectangle_0.Y, num, num), -90f, 90f);
			graphicsPath.AddArc(new Rectangle(rectangle_0.Width - num + rectangle_0.X, rectangle_0.Height - num + rectangle_0.Y, num, num), 0f, 90f);
			graphicsPath.AddArc(new Rectangle(rectangle_0.X, rectangle_0.Height - num + rectangle_0.Y, num, num), 90f, 90f);
			graphicsPath.AddLine(new Point(rectangle_0.X, rectangle_0.Height - num + rectangle_0.Y), new Point(rectangle_0.X, int_0 + rectangle_0.Y));
			return graphicsPath;
		}
	}
}
