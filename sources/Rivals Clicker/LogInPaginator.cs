using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace svchost
{
	// Token: 0x0200003D RID: 61
	[DefaultEvent("SelectedIndexChanged")]
	public class LogInPaginator : Control
	{
		// Token: 0x060003D6 RID: 982 RVA: 0x000145E0 File Offset: 0x000127E0
		public GraphicsPath RoundRectangle(Rectangle Rectangle, int Curve)
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

		// Token: 0x060003D7 RID: 983 RVA: 0x000146F8 File Offset: 0x000128F8
		public GraphicsPath RoundRect(float x, float y, float w, float h, float r = 0.3f, bool TL = true, bool TR = true, bool BR = true, bool BL = true)
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

		// Token: 0x14000008 RID: 8
		// (add) Token: 0x060003D8 RID: 984 RVA: 0x000147F0 File Offset: 0x000129F0
		// (remove) Token: 0x060003D9 RID: 985 RVA: 0x00014828 File Offset: 0x00012A28
		public event LogInPaginator.SelectedIndexChangedEventHandler SelectedIndexChanged;

		// Token: 0x17000154 RID: 340
		// (get) Token: 0x060003DA RID: 986 RVA: 0x00014860 File Offset: 0x00012A60
		// (set) Token: 0x060003DB RID: 987 RVA: 0x00003879 File Offset: 0x00001A79
		public int SelectedIndex
		{
			get
			{
				return this._SelectedIndex;
			}
			set
			{
				this._SelectedIndex = Math.Max(Math.Min(value, this.MaximumIndex), 0);
				base.Invalidate();
			}
		}

		// Token: 0x17000155 RID: 341
		// (get) Token: 0x060003DC RID: 988 RVA: 0x00014878 File Offset: 0x00012A78
		// (set) Token: 0x060003DD RID: 989 RVA: 0x0000389B File Offset: 0x00001A9B
		public int NumberOfPages
		{
			get
			{
				return this._NumberOfPages;
			}
			set
			{
				this._NumberOfPages = value;
				this._SelectedIndex = Math.Max(Math.Min(this._SelectedIndex, this.MaximumIndex), 0);
				base.Invalidate();
			}
		}

		// Token: 0x17000156 RID: 342
		// (get) Token: 0x060003DE RID: 990 RVA: 0x00014890 File Offset: 0x00012A90
		public int MaximumIndex
		{
			get
			{
				return checked(this.NumberOfPages - 1);
			}
		}

		// Token: 0x17000157 RID: 343
		// (get) Token: 0x060003DF RID: 991 RVA: 0x0000ACB4 File Offset: 0x00008EB4
		// (set) Token: 0x060003E0 RID: 992 RVA: 0x000038C9 File Offset: 0x00001AC9
		public override Font Font
		{
			get
			{
				return base.Font;
			}
			set
			{
				base.Font = value;
				base.Invalidate();
			}
		}

		// Token: 0x060003E1 RID: 993 RVA: 0x000148AC File Offset: 0x00012AAC
		private void InvalidateItems(PaintEventArgs e)
		{
			this.ItemWidth = checked(e.Graphics.MeasureString("000 ..", this.Font).ToSize().Width + 10);
		}

		// Token: 0x060003E2 RID: 994 RVA: 0x000148EC File Offset: 0x00012AEC
		protected override void OnMouseDown(MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			checked
			{
				if (flag)
				{
					int OldIndex = this._SelectedIndex;
					bool flag2 = this._SelectedIndex < 4;
					int NewIndex;
					if (flag2)
					{
						NewIndex = e.X / this.ItemWidth;
					}
					else
					{
						bool flag3 = this._SelectedIndex > 3 && this._SelectedIndex < this.MaximumIndex - 3;
						if (flag3)
						{
							NewIndex = e.X / this.ItemWidth;
							int num = NewIndex;
							bool flag4 = num == 2;
							if (flag4)
							{
								NewIndex = OldIndex;
							}
							else
							{
								flag4 = (num < 2);
								if (flag4)
								{
									NewIndex = OldIndex - (2 - NewIndex);
								}
								else
								{
									flag4 = (num > 2);
									if (flag4)
									{
										NewIndex = OldIndex + (NewIndex - 2);
									}
								}
							}
						}
						else
						{
							NewIndex = this.MaximumIndex - (4 - e.X / this.ItemWidth);
						}
					}
					bool flag5 = NewIndex < this._NumberOfPages && NewIndex != OldIndex;
					if (flag5)
					{
						this.SelectedIndex = NewIndex;
						LogInPaginator.SelectedIndexChangedEventHandler selectedIndexChangedEvent = this.SelectedIndexChangedEvent;
						if (selectedIndexChangedEvent != null)
						{
							selectedIndexChangedEvent(this, null);
						}
					}
				}
				base.OnMouseDown(e);
			}
		}

		// Token: 0x060003E3 RID: 995 RVA: 0x00014A00 File Offset: 0x00012C00
		public LogInPaginator()
		{
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.Selectable | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(54, 54, 54);
			base.Size = new Size(202, 26);
			this.B1 = new SolidBrush(Color.FromArgb(50, 50, 50));
			this.B2 = new SolidBrush(Color.FromArgb(55, 55, 55));
			this.P1 = new Pen(Color.FromArgb(35, 35, 35));
			this.P2 = new Pen(Color.FromArgb(255, 0, 0));
			this.P3 = new Pen(Color.FromArgb(35, 35, 35));
		}

		// Token: 0x060003E4 RID: 996 RVA: 0x00014AC0 File Offset: 0x00012CC0
		protected override void OnPaint(PaintEventArgs e)
		{
			this.InvalidateItems(e);
			Graphics g = e.Graphics;
			Graphics graphics = g;
			graphics.Clear(this.BackColor);
			graphics.SmoothingMode = SmoothingMode.AntiAlias;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			bool flag = this._SelectedIndex < 4;
			checked
			{
				if (flag)
				{
					int num = Math.Min(this.MaximumIndex, 4);
					for (int I = 0; I <= num; I++)
					{
						bool RightEllipse = I == 4 && this.MaximumIndex > 4;
						this.DrawBox(I * this.ItemWidth, I, false, RightEllipse, g);
					}
				}
				else
				{
					bool flag2 = this._SelectedIndex > 3 && this._SelectedIndex < this.MaximumIndex - 3;
					if (flag2)
					{
						int I2 = 0;
						do
						{
							bool LeftEllipse = I2 == 0;
							bool RightEllipse = I2 == 4;
							this.DrawBox(I2 * this.ItemWidth, this._SelectedIndex + I2 - 2, LeftEllipse, RightEllipse, g);
							I2++;
						}
						while (I2 <= 4);
					}
					else
					{
						int I3 = 0;
						do
						{
							bool LeftEllipse = I3 == 0 && this.MaximumIndex > 4;
							this.DrawBox(I3 * this.ItemWidth, this.MaximumIndex - (4 - I3), LeftEllipse, false, g);
							I3++;
						}
						while (I3 <= 4);
					}
				}
			}
		}

		// Token: 0x060003E5 RID: 997 RVA: 0x00014BF8 File Offset: 0x00012DF8
		private void DrawBox(int x, int index, bool leftEllipse, bool rightEllipse, Graphics g)
		{
			checked
			{
				this.R1 = new Rectangle(x, 0, this.ItemWidth - 4, base.Height - 1);
				this.GP1 = this.RoundRectangle(this.R1, 4);
				this.GP2 = this.RoundRectangle(new Rectangle(this.R1.X + 1, this.R1.Y + 1, this.R1.Width - 2, this.R1.Height - 2), 4);
				string T = Conversions.ToString(index + 1);
				if (leftEllipse)
				{
					T = ".. " + T;
				}
				if (rightEllipse)
				{
					T += " ..";
				}
				this.SZ1 = g.MeasureString(T, this.Font).ToSize();
				this.PT1 = new Point(this.R1.X + (this.R1.Width / 2 - this.SZ1.Width / 2), this.R1.Y + (this.R1.Height / 2 - this.SZ1.Height / 2));
				bool flag = index == this._SelectedIndex;
				if (flag)
				{
					g.FillPath(this.B1, this.GP1);
					Font F = new Font(this.Font, FontStyle.Underline);
					g.DrawString(T, F, Brushes.Black, (float)(this.PT1.X + 1), (float)(this.PT1.Y + 1));
					g.DrawString(T, F, Brushes.White, this.PT1);
					F.Dispose();
					g.DrawPath(this.P1, this.GP2);
					g.DrawPath(this.P2, this.GP1);
				}
				else
				{
					g.FillPath(this.B2, this.GP1);
					g.DrawString(T, this.Font, Brushes.Black, (float)(this.PT1.X + 1), (float)(this.PT1.Y + 1));
					g.DrawString(T, this.Font, Brushes.White, this.PT1);
					g.DrawPath(this.P3, this.GP2);
					g.DrawPath(this.P1, this.GP1);
				}
			}
		}

		// Token: 0x04000191 RID: 401
		private GraphicsPath GP1;

		// Token: 0x04000192 RID: 402
		private GraphicsPath GP2;

		// Token: 0x04000193 RID: 403
		private Rectangle R1;

		// Token: 0x04000194 RID: 404
		private Size SZ1;

		// Token: 0x04000195 RID: 405
		private Point PT1;

		// Token: 0x04000196 RID: 406
		private Pen P1;

		// Token: 0x04000197 RID: 407
		private Pen P2;

		// Token: 0x04000198 RID: 408
		private Pen P3;

		// Token: 0x04000199 RID: 409
		private SolidBrush B1;

		// Token: 0x0400019A RID: 410
		private SolidBrush B2;

		// Token: 0x0400019C RID: 412
		private int _SelectedIndex;

		// Token: 0x0400019D RID: 413
		private int _NumberOfPages;

		// Token: 0x0400019E RID: 414
		private int ItemWidth;

		// Token: 0x0200003E RID: 62
		// (Invoke) Token: 0x060003E9 RID: 1001
		public delegate void SelectedIndexChangedEventHandler(object sender, EventArgs e);
	}
}
