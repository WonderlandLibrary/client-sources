using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace svchost
{
	// Token: 0x0200001A RID: 26
	public class LogInRadialProgressBar : Control
	{
		// Token: 0x170000A2 RID: 162
		// (get) Token: 0x060001CE RID: 462 RVA: 0x0000C870 File Offset: 0x0000AA70
		// (set) Token: 0x060001CF RID: 463 RVA: 0x0000C888 File Offset: 0x0000AA88
		[Category("Control")]
		public int Maximum
		{
			get
			{
				return this._Maximum;
			}
			set
			{
				bool flag = value < this._Value;
				if (flag)
				{
					this._Value = value;
				}
				this._Maximum = value;
				base.Invalidate();
			}
		}

		// Token: 0x170000A3 RID: 163
		// (get) Token: 0x060001D0 RID: 464 RVA: 0x0000C8BC File Offset: 0x0000AABC
		// (set) Token: 0x060001D1 RID: 465 RVA: 0x0000C8E8 File Offset: 0x0000AAE8
		[Category("Control")]
		public int Value
		{
			get
			{
				int value = this._Value;
				int Value;
				if (value != 0)
				{
					Value = this._Value;
				}
				else
				{
					Value = 0;
				}
				return Value;
			}
			set
			{
				int num = value;
				bool flag = num > this._Maximum;
				if (flag)
				{
					value = this._Maximum;
					base.Invalidate();
				}
				this._Value = value;
				base.Invalidate();
			}
		}

		// Token: 0x060001D2 RID: 466 RVA: 0x00002D30 File Offset: 0x00000F30
		public void Increment(int Amount)
		{
			checked
			{
				this.Value += Amount;
			}
		}

		// Token: 0x170000A4 RID: 164
		// (get) Token: 0x060001D3 RID: 467 RVA: 0x0000C924 File Offset: 0x0000AB24
		// (set) Token: 0x060001D4 RID: 468 RVA: 0x00002D42 File Offset: 0x00000F42
		[Category("Colours")]
		public Color BorderColour
		{
			get
			{
				return this._BorderColour;
			}
			set
			{
				this._BorderColour = value;
			}
		}

		// Token: 0x170000A5 RID: 165
		// (get) Token: 0x060001D5 RID: 469 RVA: 0x0000C93C File Offset: 0x0000AB3C
		// (set) Token: 0x060001D6 RID: 470 RVA: 0x00002D4C File Offset: 0x00000F4C
		[Category("Colours")]
		public Color ProgressColour
		{
			get
			{
				return this._ProgressColour;
			}
			set
			{
				this._ProgressColour = value;
			}
		}

		// Token: 0x170000A6 RID: 166
		// (get) Token: 0x060001D7 RID: 471 RVA: 0x0000C954 File Offset: 0x0000AB54
		// (set) Token: 0x060001D8 RID: 472 RVA: 0x00002D56 File Offset: 0x00000F56
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._BaseColour;
			}
			set
			{
				this._BaseColour = value;
			}
		}

		// Token: 0x170000A7 RID: 167
		// (get) Token: 0x060001D9 RID: 473 RVA: 0x0000C96C File Offset: 0x0000AB6C
		// (set) Token: 0x060001DA RID: 474 RVA: 0x00002D60 File Offset: 0x00000F60
		[Category("Control")]
		public int StartingAngle
		{
			get
			{
				return this._StartingAngle;
			}
			set
			{
				this._StartingAngle = value;
			}
		}

		// Token: 0x170000A8 RID: 168
		// (get) Token: 0x060001DB RID: 475 RVA: 0x0000C984 File Offset: 0x0000AB84
		// (set) Token: 0x060001DC RID: 476 RVA: 0x00002D6A File Offset: 0x00000F6A
		[Category("Control")]
		public int RotationAngle
		{
			get
			{
				return this._RotationAngle;
			}
			set
			{
				this._RotationAngle = value;
			}
		}

		// Token: 0x060001DD RID: 477 RVA: 0x0000C99C File Offset: 0x0000AB9C
		public LogInRadialProgressBar()
		{
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._BaseColour = Color.FromArgb(42, 42, 42);
			this._ProgressColour = Color.FromArgb(255, 0, 0);
			this._Value = 0;
			this._Maximum = 100;
			this._StartingAngle = 110;
			this._RotationAngle = 255;
			this._Font = new Font("Segoe UI", 20f);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(78, 78);
			this.BackColor = Color.FromArgb(54, 54, 54);
		}

		// Token: 0x060001DE RID: 478 RVA: 0x0000CA54 File Offset: 0x0000AC54
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			Graphics graphics = G;
			graphics.TextRenderingHint = TextRenderingHint.AntiAliasGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(this.BackColor);
			int value = this._Value;
			bool flag = value == 0;
			checked
			{
				if (flag)
				{
					graphics.DrawArc(new Pen(new SolidBrush(this._BorderColour), 6f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this._StartingAngle - 3, this._RotationAngle + 5);
					graphics.DrawArc(new Pen(new SolidBrush(this._BaseColour), 4f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this._StartingAngle, this._RotationAngle);
					graphics.DrawString(Conversions.ToString(this._Value), this._Font, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 1.0))), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
				}
				else
				{
					flag = (value == this._Maximum);
					if (flag)
					{
						graphics.DrawArc(new Pen(new SolidBrush(this._BorderColour), 6f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this._StartingAngle - 3, this._RotationAngle + 5);
						graphics.DrawArc(new Pen(new SolidBrush(this._BaseColour), 4f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this._StartingAngle, this._RotationAngle);
						graphics.DrawArc(new Pen(new SolidBrush(this._ProgressColour), 4f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this._StartingAngle, this._RotationAngle);
						graphics.DrawString(Conversions.ToString(this._Value), this._Font, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 1.0))), new StringFormat
						{
							Alignment = StringAlignment.Center,
							LineAlignment = StringAlignment.Center
						});
					}
					else
					{
						graphics.DrawArc(new Pen(new SolidBrush(this._BorderColour), 6f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this._StartingAngle - 3, this._RotationAngle + 5);
						graphics.DrawArc(new Pen(new SolidBrush(this._BaseColour), 4f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this._StartingAngle, this._RotationAngle);
						graphics.DrawArc(new Pen(new SolidBrush(this._ProgressColour), 4f), 3, 3, base.Width - 3 - 4, base.Height - 3 - 3, this._StartingAngle, (int)Math.Round(unchecked((double)this._RotationAngle / (double)this._Maximum * (double)this._Value)));
						graphics.DrawString(Conversions.ToString(this._Value), this._Font, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round(unchecked((double)base.Height / 2.0 - 1.0))), new StringFormat
						{
							Alignment = StringAlignment.Center,
							LineAlignment = StringAlignment.Center
						});
					}
				}
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x040000B9 RID: 185
		private Color _BorderColour;

		// Token: 0x040000BA RID: 186
		private Color _BaseColour;

		// Token: 0x040000BB RID: 187
		private Color _ProgressColour;

		// Token: 0x040000BC RID: 188
		private int _Value;

		// Token: 0x040000BD RID: 189
		private int _Maximum;

		// Token: 0x040000BE RID: 190
		private int _StartingAngle;

		// Token: 0x040000BF RID: 191
		private int _RotationAngle;

		// Token: 0x040000C0 RID: 192
		private readonly Font _Font;
	}
}
