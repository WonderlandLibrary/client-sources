using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using Microsoft.VisualBasic.CompilerServices;

namespace svchost
{
	// Token: 0x0200000F RID: 15
	[StandardModule]
	internal sealed class DrawHelpers
	{
		// Token: 0x06000107 RID: 263 RVA: 0x0000966C File Offset: 0x0000786C
		public static GraphicsPath RoundRectangle(Rectangle Rectangle, int Curve)
		{
			GraphicsPath P = new GraphicsPath();
			checked
			{
				int ArcRectangleWidth = Curve * 2;
				P.AddArc(new Rectangle(Rectangle.X, Rectangle.Y, ArcRectangleWidth, ArcRectangleWidth), -180f, 90f);
				P.AddArc(new Rectangle(Rectangle.Width - ArcRectangleWidth + Rectangle.X, Rectangle.Y, ArcRectangleWidth, ArcRectangleWidth), -90f, 90f);
				P.AddArc(new Rectangle(Rectangle.Width - ArcRectangleWidth + Rectangle.X, Rectangle.Height - ArcRectangleWidth + Rectangle.Y, ArcRectangleWidth, ArcRectangleWidth), 0f, 90f);
				P.AddArc(new Rectangle(Rectangle.X, Rectangle.Height - ArcRectangleWidth + Rectangle.Y, ArcRectangleWidth, ArcRectangleWidth), 90f, 90f);
				P.AddLine(new Point(Rectangle.X, Rectangle.Height - ArcRectangleWidth + Rectangle.Y), new Point(Rectangle.X, Curve + Rectangle.Y));
				return P;
			}
		}

		// Token: 0x06000108 RID: 264 RVA: 0x00009784 File Offset: 0x00007984
		public static GraphicsPath RoundRect(float x, float y, float w, float h, float r = 0.3f, bool TL = true, bool TR = true, bool BR = true, bool BL = true)
		{
			float d = Math.Min(w, h) * r;
			float xw = x + w;
			float yh = y + h;
			GraphicsPath RoundRect = new GraphicsPath();
			GraphicsPath graphicsPath = RoundRect;
			if (TL)
			{
				graphicsPath.AddArc(x, y, d, d, 180f, 90f);
			}
			else
			{
				graphicsPath.AddLine(x, y, x, y);
			}
			if (TR)
			{
				graphicsPath.AddArc(xw - d, y, d, d, 270f, 90f);
			}
			else
			{
				graphicsPath.AddLine(xw, y, xw, y);
			}
			if (BR)
			{
				graphicsPath.AddArc(xw - d, yh - d, d, d, 0f, 90f);
			}
			else
			{
				graphicsPath.AddLine(xw, yh, xw, yh);
			}
			if (BL)
			{
				graphicsPath.AddArc(x, yh - d, d, d, 90f, 90f);
			}
			else
			{
				graphicsPath.AddLine(x, yh, x, yh);
			}
			graphicsPath.CloseFigure();
			return RoundRect;
		}

		// Token: 0x02000010 RID: 16
		public enum MouseState : byte
		{
			// Token: 0x0400005C RID: 92
			None,
			// Token: 0x0400005D RID: 93
			Over,
			// Token: 0x0400005E RID: 94
			Down,
			// Token: 0x0400005F RID: 95
			Block
		}
	}
}
