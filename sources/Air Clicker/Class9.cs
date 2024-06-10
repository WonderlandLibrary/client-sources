using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x02000014 RID: 20
[StandardModule]
internal sealed class Class9
{
	// Token: 0x06000212 RID: 530 RVA: 0x00008960 File Offset: 0x00006B60
	internal static SizeF smethod_0(string string_0, Font font_0)
	{
		return Class9.graphics_1.MeasureString(string_0, font_0);
	}

	// Token: 0x06000213 RID: 531 RVA: 0x0000897C File Offset: 0x00006B7C
	internal static SizeF smethod_1(string string_0, Font font_0, int int_0)
	{
		return Class9.graphics_1.MeasureString(string_0, font_0, int_0, StringFormat.GenericTypographic);
	}

	// Token: 0x06000214 RID: 532 RVA: 0x000089A0 File Offset: 0x00006BA0
	internal static GraphicsPath smethod_2(int int_0, int int_1, int int_2, int int_3, int int_4)
	{
		Class9.rectangle_0 = new Rectangle(int_0, int_1, int_2, int_3);
		return Class9.smethod_3(Class9.rectangle_0, int_4);
	}

	// Token: 0x06000215 RID: 533 RVA: 0x000089CC File Offset: 0x00006BCC
	internal static GraphicsPath smethod_3(Rectangle rectangle_1, int int_0)
	{
		Class9.graphicsPath_0 = new GraphicsPath(FillMode.Winding);
		Class9.graphicsPath_0.AddArc(rectangle_1.X, rectangle_1.Y, int_0, int_0, 180f, 90f);
		checked
		{
			Class9.graphicsPath_0.AddArc(rectangle_1.Right - int_0, rectangle_1.Y, int_0, int_0, 270f, 90f);
			Class9.graphicsPath_0.AddArc(rectangle_1.Right - int_0, rectangle_1.Bottom - int_0, int_0, int_0, 0f, 90f);
			Class9.graphicsPath_0.AddArc(rectangle_1.X, rectangle_1.Bottom - int_0, int_0, int_0, 90f, 90f);
			Class9.graphicsPath_0.CloseFigure();
			return Class9.graphicsPath_0;
		}
	}

	// Token: 0x04000081 RID: 129
	internal static Graphics graphics_0;

	// Token: 0x04000082 RID: 130
	private static Bitmap bitmap_0 = new Bitmap(1, 1);

	// Token: 0x04000083 RID: 131
	private static Graphics graphics_1 = Graphics.FromImage(Class9.bitmap_0);

	// Token: 0x04000084 RID: 132
	private static GraphicsPath graphicsPath_0;

	// Token: 0x04000085 RID: 133
	private static Rectangle rectangle_0;
}
