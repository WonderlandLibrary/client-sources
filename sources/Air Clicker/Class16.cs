using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x02000038 RID: 56
[StandardModule]
internal sealed class Class16
{
	// Token: 0x0600033B RID: 827 RVA: 0x0000FD40 File Offset: 0x0000DF40
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

	// Token: 0x0600033C RID: 828 RVA: 0x0000FE50 File Offset: 0x0000E050
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
		if (bool_2)
		{
			graphicsPath2.AddArc(num2 - num, num3 - num, num, num, 0f, 90f);
		}
		else
		{
			graphicsPath2.AddLine(num2, num3, num2, num3);
		}
		if (!bool_3)
		{
			graphicsPath2.AddLine(float_0, num3, float_0, num3);
		}
		else
		{
			graphicsPath2.AddArc(float_0, num3 - num, num, num, 90f, 90f);
		}
		graphicsPath2.CloseFigure();
		return graphicsPath;
	}

	// Token: 0x0600033D RID: 829 RVA: 0x0000FF24 File Offset: 0x0000E124
	public static GraphicsPath smethod_2(int int_0, int int_1, bool bool_0)
	{
		GraphicsPath graphicsPath = new GraphicsPath();
		int num = 12;
		int num2 = 6;
		checked
		{
			if (bool_0)
			{
				graphicsPath.AddLine(int_0 + 1, int_1, int_0 + num + 1, int_1);
				graphicsPath.AddLine(int_0 + num, int_1, int_0 + num2, int_1 + num2 - 1);
			}
			else
			{
				graphicsPath.AddLine(int_0, int_1 + num2, int_0 + num, int_1 + num2);
				graphicsPath.AddLine(int_0 + num, int_1 + num2, int_0 + num2, int_1);
			}
			graphicsPath.CloseFigure();
			return graphicsPath;
		}
	}

	// Token: 0x040001AF RID: 431
	internal static Graphics graphics_0;

	// Token: 0x040001B0 RID: 432
	internal static Bitmap bitmap_0;

	// Token: 0x040001B1 RID: 433
	internal static Color color_0 = Color.FromArgb(35, 168, 109);

	// Token: 0x040001B2 RID: 434
	internal static StringFormat stringFormat_0 = new StringFormat
	{
		Alignment = StringAlignment.Near,
		LineAlignment = StringAlignment.Near
	};

	// Token: 0x040001B3 RID: 435
	internal static StringFormat stringFormat_1 = new StringFormat
	{
		Alignment = StringAlignment.Center,
		LineAlignment = StringAlignment.Center
	};
}
