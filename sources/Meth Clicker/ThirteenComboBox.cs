using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace Meth
{
	// Token: 0x02000012 RID: 18
	public class ThirteenComboBox : ComboBox
	{
		// Token: 0x1700008D RID: 141
		// (get) Token: 0x060001AB RID: 427 RVA: 0x0000E528 File Offset: 0x0000C728
		// (set) Token: 0x060001AC RID: 428 RVA: 0x0000E530 File Offset: 0x0000C730
		public ThirteenComboBox.ColorSchemes ColorScheme
		{
			get
			{
				return this._ColorScheme;
			}
			set
			{
				this._ColorScheme = value;
				base.Invalidate();
			}
		}

		// Token: 0x1700008E RID: 142
		// (get) Token: 0x060001AD RID: 429 RVA: 0x0000E53F File Offset: 0x0000C73F
		// (set) Token: 0x060001AE RID: 430 RVA: 0x0000E547 File Offset: 0x0000C747
		public Color AccentColor
		{
			get
			{
				return this._AccentColor;
			}
			set
			{
				this._AccentColor = value;
				base.Invalidate();
			}
		}

		// Token: 0x1700008F RID: 143
		// (get) Token: 0x060001AF RID: 431 RVA: 0x0000E556 File Offset: 0x0000C756
		// (set) Token: 0x060001B0 RID: 432 RVA: 0x0000E560 File Offset: 0x0000C760
		private int StartIndex
		{
			get
			{
				return this._StartIndex;
			}
			set
			{
				this._StartIndex = value;
				try
				{
					base.SelectedIndex = value;
				}
				catch (Exception ex)
				{
				}
				base.Invalidate();
			}
		}

		// Token: 0x060001B1 RID: 433 RVA: 0x0000E5A0 File Offset: 0x0000C7A0
		public void ReplaceItem(object sender, DrawItemEventArgs e)
		{
			e.DrawBackground();
			try
			{
				if ((e.State & DrawItemState.Selected) == DrawItemState.Selected)
				{
					e.Graphics.FillRectangle(new SolidBrush(this._AccentColor), e.Bounds);
				}
				else
				{
					ThirteenComboBox.ColorSchemes colorScheme = this.ColorScheme;
					if (colorScheme != ThirteenComboBox.ColorSchemes.Light)
					{
						if (colorScheme == ThirteenComboBox.ColorSchemes.Dark)
						{
							e.Graphics.FillRectangle(new SolidBrush(Color.FromArgb(35, 35, 35)), e.Bounds);
						}
					}
					else
					{
						e.Graphics.FillRectangle(new SolidBrush(Color.White), e.Bounds);
					}
				}
				ThirteenComboBox.ColorSchemes colorScheme2 = this.ColorScheme;
				if (colorScheme2 != ThirteenComboBox.ColorSchemes.Light)
				{
					if (colorScheme2 == ThirteenComboBox.ColorSchemes.Dark)
					{
						e.Graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), e.Font, Brushes.White, e.Bounds);
					}
				}
				else
				{
					e.Graphics.DrawString(base.GetItemText(RuntimeHelpers.GetObjectValue(base.Items[e.Index])), e.Font, Brushes.Black, e.Bounds);
				}
			}
			catch (Exception ex)
			{
			}
		}

		// Token: 0x060001B2 RID: 434 RVA: 0x0000E6DC File Offset: 0x0000C8DC
		protected void DrawTriangle(Color Clr, Point FirstPoint, Point SecondPoint, Point ThirdPoint, Graphics G)
		{
			List<Point> list = new List<Point>();
			list.Add(FirstPoint);
			list.Add(SecondPoint);
			list.Add(ThirdPoint);
			G.FillPolygon(new SolidBrush(Clr), list.ToArray());
		}

		// Token: 0x060001B3 RID: 435 RVA: 0x0000E718 File Offset: 0x0000C918
		public ThirteenComboBox()
		{
			base.DrawItem += this.ReplaceItem;
			this._StartIndex = 0;
			base.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
			base.SetStyle(ControlStyles.ResizeRedraw, true);
			base.SetStyle(ControlStyles.UserPaint, true);
			base.SetStyle(ControlStyles.DoubleBuffer, true);
			base.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
			base.DrawMode = DrawMode.OwnerDrawFixed;
			this.BackColor = Color.FromArgb(50, 50, 50);
			this.ForeColor = Color.White;
			this.AccentColor = Color.DodgerBlue;
			this.ColorScheme = ThirteenComboBox.ColorSchemes.Dark;
			base.DropDownStyle = ComboBoxStyle.DropDownList;
			this.Font = new Font("Segoe UI Semilight", 9.75f);
			this.StartIndex = 0;
			this.DoubleBuffered = true;
		}

		// Token: 0x060001B4 RID: 436 RVA: 0x0000E7D8 File Offset: 0x0000C9D8
		protected override void OnPaint(PaintEventArgs e)
		{
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphics = Graphics.FromImage(bitmap);
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			ThirteenComboBox.ColorSchemes colorScheme = this.ColorScheme;
			if (colorScheme != ThirteenComboBox.ColorSchemes.Light)
			{
				if (colorScheme == ThirteenComboBox.ColorSchemes.Dark)
				{
					graphics.Clear(Color.FromArgb(50, 50, 50));
					graphics.DrawLine(new Pen(Color.White, 2f), new Point(base.Width - 18, 10), new Point(base.Width - 14, 14));
					graphics.DrawLine(new Pen(Color.White, 2f), new Point(base.Width - 14, 14), new Point(base.Width - 10, 10));
					graphics.DrawLine(new Pen(Color.White), new Point(base.Width - 14, 15), new Point(base.Width - 14, 14));
				}
			}
			else
			{
				graphics.Clear(Color.White);
				graphics.DrawLine(new Pen(Color.FromArgb(100, 100, 100), 2f), new Point(base.Width - 18, 10), new Point(base.Width - 14, 14));
				graphics.DrawLine(new Pen(Color.FromArgb(100, 100, 100), 2f), new Point(base.Width - 14, 14), new Point(base.Width - 10, 10));
				graphics.DrawLine(new Pen(Color.FromArgb(100, 100, 100)), new Point(base.Width - 14, 15), new Point(base.Width - 14, 14));
			}
			graphics.DrawRectangle(new Pen(Color.FromArgb(100, 100, 100)), new Rectangle(0, 0, base.Width - 1, base.Height - 1));
			try
			{
				ThirteenComboBox.ColorSchemes colorScheme2 = this.ColorScheme;
				if (colorScheme2 != ThirteenComboBox.ColorSchemes.Light)
				{
					if (colorScheme2 == ThirteenComboBox.ColorSchemes.Dark)
					{
						graphics.DrawString(this.Text, this.Font, Brushes.White, new Rectangle(7, 0, base.Width - 1, base.Height - 1), new StringFormat
						{
							LineAlignment = StringAlignment.Center,
							Alignment = StringAlignment.Near
						});
					}
				}
				else
				{
					graphics.DrawString(this.Text, this.Font, Brushes.Black, new Rectangle(7, 0, base.Width - 1, base.Height - 1), new StringFormat
					{
						LineAlignment = StringAlignment.Center,
						Alignment = StringAlignment.Near
					});
				}
			}
			catch (Exception ex)
			{
			}
			NewLateBinding.LateCall(e.Graphics, null, "DrawImage", new object[]
			{
				bitmap.Clone(),
				0,
				0
			}, null, null, null, true);
			graphics.Dispose();
			bitmap.Dispose();
		}

		// Token: 0x040000DF RID: 223
		private ThirteenComboBox.ColorSchemes _ColorScheme;

		// Token: 0x040000E0 RID: 224
		private Color _AccentColor;

		// Token: 0x040000E1 RID: 225
		private int _StartIndex;

		// Token: 0x02000052 RID: 82
		public enum ColorSchemes
		{
			// Token: 0x040001C7 RID: 455
			Light,
			// Token: 0x040001C8 RID: 456
			Dark
		}
	}
}
