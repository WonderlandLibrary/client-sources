using Microsoft.VisualBasic.CompilerServices;
using System;
using System.Drawing;
using System.Drawing.Drawing2D;

namespace Xh0kO1ZCmA
{
	internal static class Helpers
	{
		internal static Graphics G;

		internal static Bitmap B;

		internal static Color _FlatColor;

		internal static StringFormat NearSF;

		internal static StringFormat CenterSF;

		static Helpers()
		{
			Helpers._FlatColor = Color.FromArgb(35, 168, 109);
			Helpers.NearSF = new StringFormat()
			{
				Alignment = StringAlignment.Near,
				LineAlignment = StringAlignment.Near
			};
			Helpers.CenterSF = new StringFormat()
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			};
		}

		public static GraphicsPath DrawArrow(int x, int y, bool flip)
		{
			GraphicsPath graphicsPath = new GraphicsPath();
			int num = 12;
			int num1 = 6;
			if (!flip)
			{
				graphicsPath.AddLine(x, checked(y + num1), checked(x + num), checked(y + num1));
				graphicsPath.AddLine(checked(x + num), checked(y + num1), checked(x + num1), y);
			}
			else
			{
				graphicsPath.AddLine(checked(x + 1), y, checked(checked(x + num) + 1), y);
				graphicsPath.AddLine(checked(x + num), y, checked(x + num1), checked(checked(y + num1) - 1));
			}
			graphicsPath.CloseFigure();
			return graphicsPath;
		}

		public static GraphicsPath RoundRec(System.Drawing.Rectangle Rectangle, int Curve)
		{
			GraphicsPath graphicsPath = new GraphicsPath();
			int curve = checked(Curve * 2);
			graphicsPath.AddArc(new System.Drawing.Rectangle(Rectangle.X, Rectangle.Y, curve, curve), -180f, 90f);
			graphicsPath.AddArc(new System.Drawing.Rectangle(checked(checked(Rectangle.Width - curve) + Rectangle.X), Rectangle.Y, curve, curve), -90f, 90f);
			graphicsPath.AddArc(new System.Drawing.Rectangle(checked(checked(Rectangle.Width - curve) + Rectangle.X), checked(checked(Rectangle.Height - curve) + Rectangle.Y), curve, curve), 0f, 90f);
			graphicsPath.AddArc(new System.Drawing.Rectangle(Rectangle.X, checked(checked(Rectangle.Height - curve) + Rectangle.Y), curve, curve), 90f, 90f);
			graphicsPath.AddLine(new Point(Rectangle.X, checked(checked(Rectangle.Height - curve) + Rectangle.Y)), new Point(Rectangle.X, checked(Curve + Rectangle.Y)));
			return graphicsPath;
		}

		public static GraphicsPath RoundRect(float x, float y, float w, float h, float r = 0.3f, bool TL = true, bool TR = true, bool BR = true, bool BL = true)
		{
			float single = Math.Min(w, h) * r;
			float single1 = x + w;
			float single2 = y + h;
			GraphicsPath graphicsPath = new GraphicsPath();
			GraphicsPath graphicsPath1 = graphicsPath;
			if (!TL)
			{
				graphicsPath1.AddLine(x, y, x, y);
			}
			else
			{
				graphicsPath1.AddArc(x, y, single, single, 180f, 90f);
			}
			if (!TR)
			{
				graphicsPath1.AddLine(single1, y, single1, y);
			}
			else
			{
				graphicsPath1.AddArc(single1 - single, y, single, single, 270f, 90f);
			}
			if (!BR)
			{
				graphicsPath1.AddLine(single1, single2, single1, single2);
			}
			else
			{
				graphicsPath1.AddArc(single1 - single, single2 - single, single, single, 0f, 90f);
			}
			if (!BL)
			{
				graphicsPath1.AddLine(x, single2, x, single2);
			}
			else
			{
				graphicsPath1.AddArc(x, single2 - single, single, single, 90f, 90f);
			}
			graphicsPath1.CloseFigure();
			graphicsPath1 = null;
			return graphicsPath;
		}
	}
}