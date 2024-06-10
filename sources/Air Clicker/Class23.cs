using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x0200008A RID: 138
[StandardModule]
internal sealed class Class23
{
	// Token: 0x06000602 RID: 1538 RVA: 0x0001DB4C File Offset: 0x0001BD4C
	public static GraphicsPath smethod_0(Rectangle rectangle_0, int int_2)
	{
		GraphicsPath graphicsPath = new GraphicsPath();
		checked
		{
			int num = int_2 * 2;
			graphicsPath.AddArc(new Rectangle(rectangle_0.X, rectangle_0.Y, num, num), -180f, 90f);
			graphicsPath.AddArc(new Rectangle(rectangle_0.Width - num + rectangle_0.X, rectangle_0.Y, num, num), -90f, 90f);
			graphicsPath.AddArc(new Rectangle(rectangle_0.Width - num + rectangle_0.X, rectangle_0.Height - num + rectangle_0.Y, num, num), 0f, 90f);
			graphicsPath.AddArc(new Rectangle(rectangle_0.X, rectangle_0.Height - num + rectangle_0.Y, num, num), 90f, 90f);
			graphicsPath.AddLine(new Point(rectangle_0.X, rectangle_0.Height - num + rectangle_0.Y), new Point(rectangle_0.X, int_2 + rectangle_0.Y));
			return graphicsPath;
		}
	}

	// Token: 0x06000603 RID: 1539 RVA: 0x0001DC5C File Offset: 0x0001BE5C
	public static GraphicsPath smethod_1(float float_0, float float_1, float float_2, float float_3, float float_4 = 0.3f, bool bool_0 = true, bool bool_1 = true, bool bool_2 = true, bool bool_3 = true)
	{
		float num = Math.Min(float_2, float_3) * float_4;
		float num2 = float_0 + float_2;
		float num3 = float_1 + float_3;
		GraphicsPath graphicsPath = new GraphicsPath();
		GraphicsPath graphicsPath2 = graphicsPath;
		if (!bool_0)
		{
			graphicsPath2.AddLine(float_0, float_1, float_0, float_1);
		}
		else
		{
			graphicsPath2.AddArc(float_0, float_1, num, num, 180f, 90f);
		}
		if (bool_1)
		{
			graphicsPath2.AddArc(num2 - num, float_1, num, num, 270f, 90f);
		}
		else
		{
			graphicsPath2.AddLine(num2, float_1, num2, float_1);
		}
		if (!bool_2)
		{
			graphicsPath2.AddLine(num2, num3, num2, num3);
		}
		else
		{
			graphicsPath2.AddArc(num2 - num, num3 - num, num, num, 0f, 90f);
		}
		if (bool_3)
		{
			graphicsPath2.AddArc(float_0, num3 - num, num, num, 90f, 90f);
		}
		else
		{
			graphicsPath2.AddLine(float_0, num3, float_0, num3);
		}
		graphicsPath2.CloseFigure();
		return graphicsPath;
	}

	// Token: 0x040002FA RID: 762
	private static int int_0;

	// Token: 0x040002FB RID: 763
	private static int int_1;

	// Token: 0x0200008B RID: 139
	public enum Enum9 : byte
	{
		// Token: 0x040002FD RID: 765
		None,
		// Token: 0x040002FE RID: 766
		Over,
		// Token: 0x040002FF RID: 767
		Down,
		// Token: 0x04000300 RID: 768
		Block
	}
}
