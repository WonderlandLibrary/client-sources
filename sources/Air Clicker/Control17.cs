using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x0200002B RID: 43
internal class Control17 : Control
{
	// Token: 0x060002AD RID: 685 RVA: 0x0000CB78 File Offset: 0x0000AD78
	public Control17()
	{
		this.int_0 = -1;
		this.string_2 = new string[]
		{
			"Shift",
			"Space",
			"Back"
		};
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		base.SetStyle(ControlStyles.Selectable, false);
		this.Font = new Font("Verdana", 8.25f);
		this.bitmap_0 = new Bitmap(1, 1);
		this.graphics_0 = Graphics.FromImage(this.bitmap_0);
		this.MinimumSize = new Size(386, 162);
		this.MaximumSize = new Size(386, 162);
		this.char_0 = "1234567890-=qwertyuiop[]asdfghjkl\\;'zxcvbnm,./`".ToCharArray();
		this.char_1 = "!@#$%^&*()_+QWERTYUIOP{}ASDFGHJKL|:\"ZXCVBNM<>?~".ToCharArray();
		this.method_0();
		this.pen_0 = new Pen(Color.FromArgb(45, 45, 45));
		this.pen_1 = new Pen(Color.FromArgb(65, 65, 65));
		this.pen_2 = new Pen(Color.FromArgb(24, 24, 24));
		this.solidBrush_0 = new SolidBrush(Color.FromArgb(100, 100, 100));
	}

	// Token: 0x170000E8 RID: 232
	// (get) Token: 0x060002AE RID: 686 RVA: 0x0000CCAC File Offset: 0x0000AEAC
	// (set) Token: 0x060002AF RID: 687 RVA: 0x0000CCC4 File Offset: 0x0000AEC4
	public Control Control_0
	{
		get
		{
			return this.control_0;
		}
		set
		{
			this.control_0 = value;
		}
	}

	// Token: 0x060002B0 RID: 688 RVA: 0x0000CCD0 File Offset: 0x0000AED0
	private void method_0()
	{
		this.rectangle_0 = new Rectangle[51];
		checked
		{
			this.pointF_0 = new PointF[this.char_1.Length - 1 + 1];
			this.pointF_1 = new PointF[this.char_0.Length - 1 + 1];
			int num = 0;
			do
			{
				int num2 = 0;
				do
				{
					int num3 = num * 12 + num2;
					Rectangle rectangle = new Rectangle(num2 * 32, num * 32, 32, 32);
					this.rectangle_0[num3] = rectangle;
					unchecked
					{
						if (num3 != 47 && !char.IsLetter(this.char_1[num3]))
						{
							SizeF sizeF = this.graphics_0.MeasureString(Conversions.ToString(this.char_1[num3]), this.Font);
							this.pointF_0[num3] = new PointF((float)rectangle.X + ((float)(rectangle.Width / 2) - sizeF.Width / 2f), (float)(checked(rectangle.Y + rectangle.Height)) - sizeF.Height - 2f);
							sizeF = this.graphics_0.MeasureString(Conversions.ToString(this.char_0[num3]), this.Font);
							this.pointF_1[num3] = new PointF((float)rectangle.X + ((float)(rectangle.Width / 2) - sizeF.Width / 2f), (float)(checked(rectangle.Y + rectangle.Height)) - sizeF.Height - 2f);
						}
					}
					num2++;
				}
				while (num2 <= 11);
				num++;
			}
			while (num <= 3);
			this.rectangle_0[48] = new Rectangle(0, 128, 64, 32);
			this.rectangle_0[49] = new Rectangle(this.rectangle_0[48].Right, 128, 256, 32);
			this.rectangle_0[50] = new Rectangle(this.rectangle_0[49].Right, 128, 64, 32);
		}
	}

	// Token: 0x060002B1 RID: 689 RVA: 0x0000CEE0 File Offset: 0x0000B0E0
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class9.graphics_0 = e.Graphics;
		Class9.graphics_0.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		Class9.graphics_0.Clear(this.BackColor);
		Class9.graphics_0.DrawRectangle(this.pen_0, 0, 0, 385, 161);
		checked
		{
			int num = this.rectangle_0.Length - 1;
			for (int i = 0; i <= num; i++)
			{
				Rectangle rect = this.rectangle_0[i];
				int num2 = 0;
				if (i == this.int_0)
				{
					num2 = 1;
					this.graphicsPath_0 = new GraphicsPath();
					this.graphicsPath_0.AddRectangle(rect);
					this.pathGradientBrush_0 = new PathGradientBrush(this.graphicsPath_0);
					this.pathGradientBrush_0.CenterColor = Color.FromArgb(60, 60, 60);
					this.pathGradientBrush_0.SurroundColors = new Color[]
					{
						Color.FromArgb(55, 55, 55)
					};
					this.pathGradientBrush_0.FocusScales = new PointF(0.8f, 0.5f);
					Class9.graphics_0.FillPath(this.pathGradientBrush_0, this.graphicsPath_0);
				}
				else
				{
					this.linearGradientBrush_0 = new LinearGradientBrush(rect, Color.FromArgb(60, 60, 60), Color.FromArgb(55, 55, 55), 90f);
					Class9.graphics_0.FillRectangle(this.linearGradientBrush_0, rect);
				}
				if (i != 47)
				{
					unchecked
					{
						if (i - 48 > 2)
						{
							if (!this.bool_0)
							{
								checked
								{
									Class9.graphics_0.DrawString(Conversions.ToString(this.char_0[i]), this.Font, Brushes.Black, (float)(rect.X + 3 + num2 + 1), (float)(rect.Y + 2 + num2 + 1));
									Class9.graphics_0.DrawString(Conversions.ToString(this.char_0[i]), this.Font, Brushes.WhiteSmoke, (float)(rect.X + 3 + num2), (float)(rect.Y + 2 + num2));
								}
								if (!char.IsLetter(this.char_1[i]))
								{
									this.pointF_2 = this.pointF_0[i];
									Class9.graphics_0.DrawString(Conversions.ToString(this.char_1[i]), this.Font, this.solidBrush_0, this.pointF_2.X + (float)num2, this.pointF_2.Y + (float)num2);
								}
							}
							else
							{
								checked
								{
									Class9.graphics_0.DrawString(Conversions.ToString(this.char_1[i]), this.Font, Brushes.Black, (float)(rect.X + 3 + num2 + 1), (float)(rect.Y + 2 + num2 + 1));
									Class9.graphics_0.DrawString(Conversions.ToString(this.char_1[i]), this.Font, Brushes.WhiteSmoke, (float)(rect.X + 3 + num2), (float)(rect.Y + 2 + num2));
								}
								if (!char.IsLetter(this.char_0[i]))
								{
									this.pointF_2 = this.pointF_1[i];
									Class9.graphics_0.DrawString(Conversions.ToString(this.char_0[i]), this.Font, this.solidBrush_0, this.pointF_2.X + (float)num2, this.pointF_2.Y + (float)num2);
								}
							}
						}
						else
						{
							this.sizeF_0 = Class9.graphics_0.MeasureString(this.string_2[checked(i - 48)], this.Font);
							Class9.graphics_0.DrawString(this.string_2[checked(i - 48)], this.Font, Brushes.Black, (float)rect.X + ((float)(rect.Width / 2) - this.sizeF_0.Width / 2f) + (float)num2 + 1f, (float)rect.Y + ((float)(rect.Height / 2) - this.sizeF_0.Height / 2f) + (float)num2 + 1f);
							Class9.graphics_0.DrawString(this.string_2[checked(i - 48)], this.Font, Brushes.WhiteSmoke, (float)rect.X + ((float)(rect.Width / 2) - this.sizeF_0.Width / 2f) + (float)num2, (float)rect.Y + ((float)(rect.Height / 2) - this.sizeF_0.Height / 2f) + (float)num2);
						}
					}
				}
				else
				{
					this.method_1(Color.Black, rect.X + num2 + 1, rect.Y + num2 + 1);
					this.method_1(Color.FromArgb(235, 235, 235), rect.X + num2, rect.Y + num2);
				}
				Class9.graphics_0.DrawRectangle(this.pen_1, rect.X + 1 + num2, rect.Y + 1 + num2, rect.Width - 2, rect.Height - 2);
				Class9.graphics_0.DrawRectangle(this.pen_2, rect.X + num2, rect.Y + num2, rect.Width, rect.Height);
				if (i == this.int_0)
				{
					Class9.graphics_0.DrawLine(this.pen_0, rect.X, rect.Y, rect.Right, rect.Y);
					Class9.graphics_0.DrawLine(this.pen_0, rect.X, rect.Y, rect.X, rect.Bottom);
				}
			}
		}
	}

	// Token: 0x060002B2 RID: 690 RVA: 0x0000D440 File Offset: 0x0000B640
	private void method_1(Color color_0, int int_1, int int_2)
	{
		Rectangle rect = checked(new Rectangle(int_1 + 8, int_2 + 8, 16, 16));
		Class9.graphics_0.SmoothingMode = SmoothingMode.AntiAlias;
		Pen pen = new Pen(color_0, 1f);
		AdjustableArrowCap adjustableArrowCap = new AdjustableArrowCap(3f, 2f);
		pen.CustomEndCap = adjustableArrowCap;
		Class9.graphics_0.DrawArc(pen, rect, 0f, 290f);
		pen.Dispose();
		adjustableArrowCap.Dispose();
		Class9.graphics_0.SmoothingMode = SmoothingMode.None;
	}

	// Token: 0x060002B3 RID: 691 RVA: 0x0000D4BC File Offset: 0x0000B6BC
	protected virtual void OnMouseDown(MouseEventArgs e)
	{
		checked
		{
			int num = e.Y / 32 * 12 + e.X / 32;
			if (num <= 47)
			{
				this.int_0 = num;
			}
			else
			{
				int num2 = this.rectangle_0.Length - 1;
				for (int i = 48; i <= num2; i++)
				{
					if (this.rectangle_0[i].Contains(e.X, e.Y))
					{
						this.int_0 = i;
						break;
					}
				}
			}
			this.method_2();
			base.Invalidate();
		}
	}

	// Token: 0x060002B4 RID: 692 RVA: 0x0000D53C File Offset: 0x0000B73C
	protected virtual void OnMouseUp(MouseEventArgs e)
	{
		this.int_0 = -1;
		base.Invalidate();
	}

	// Token: 0x060002B5 RID: 693 RVA: 0x0000D54C File Offset: 0x0000B74C
	private void method_2()
	{
		if (this.control_0 != null && this.int_0 != -1)
		{
			switch (this.int_0)
			{
			case 47:
				this.control_0.Text = string.Empty;
				break;
			case 48:
				this.bool_0 = !this.bool_0;
				break;
			case 49:
			{
				Control control;
				(control = this.control_0).Text = control.Text + " ";
				break;
			}
			case 50:
				if (this.control_0.Text.Length != 0)
				{
					this.control_0.Text = this.control_0.Text.Remove(checked(this.control_0.Text.Length - 1));
				}
				break;
			default:
				if (!this.bool_0)
				{
					Control control;
					(control = this.control_0).Text = control.Text + Conversions.ToString(this.char_0[this.int_0]);
				}
				else
				{
					Control control;
					(control = this.control_0).Text = control.Text + Conversions.ToString(this.char_1[this.int_0]);
				}
				break;
			}
		}
	}

	// Token: 0x04000140 RID: 320
	private Bitmap bitmap_0;

	// Token: 0x04000141 RID: 321
	private Graphics graphics_0;

	// Token: 0x04000142 RID: 322
	private const string string_0 = "1234567890-=qwertyuiop[]asdfghjkl\\;'zxcvbnm,./`";

	// Token: 0x04000143 RID: 323
	private const string string_1 = "!@#$%^&*()_+QWERTYUIOP{}ASDFGHJKL|:\"ZXCVBNM<>?~";

	// Token: 0x04000144 RID: 324
	private Control control_0;

	// Token: 0x04000145 RID: 325
	private bool bool_0;

	// Token: 0x04000146 RID: 326
	private int int_0;

	// Token: 0x04000147 RID: 327
	private Rectangle[] rectangle_0;

	// Token: 0x04000148 RID: 328
	private char[] char_0;

	// Token: 0x04000149 RID: 329
	private char[] char_1;

	// Token: 0x0400014A RID: 330
	private string[] string_2;

	// Token: 0x0400014B RID: 331
	private PointF[] pointF_0;

	// Token: 0x0400014C RID: 332
	private PointF[] pointF_1;

	// Token: 0x0400014D RID: 333
	private GraphicsPath graphicsPath_0;

	// Token: 0x0400014E RID: 334
	private SizeF sizeF_0;

	// Token: 0x0400014F RID: 335
	private PointF pointF_2;

	// Token: 0x04000150 RID: 336
	private Pen pen_0;

	// Token: 0x04000151 RID: 337
	private Pen pen_1;

	// Token: 0x04000152 RID: 338
	private Pen pen_2;

	// Token: 0x04000153 RID: 339
	private SolidBrush solidBrush_0;

	// Token: 0x04000154 RID: 340
	private PathGradientBrush pathGradientBrush_0;

	// Token: 0x04000155 RID: 341
	private LinearGradientBrush linearGradientBrush_0;
}
