using System;
using System.Drawing;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x0200000E RID: 14
	public class ThirteenControlBox : Control
	{
		// Token: 0x1400000E RID: 14
		// (add) Token: 0x06000182 RID: 386 RVA: 0x0000D7E0 File Offset: 0x0000B9E0
		// (remove) Token: 0x06000183 RID: 387 RVA: 0x0000D818 File Offset: 0x0000BA18
		public event ThirteenControlBox.ColorSchemeChangedEventHandler ColorSchemeChanged;

		// Token: 0x17000086 RID: 134
		// (get) Token: 0x06000184 RID: 388 RVA: 0x0000D84D File Offset: 0x0000BA4D
		// (set) Token: 0x06000185 RID: 389 RVA: 0x0000D858 File Offset: 0x0000BA58
		public ThirteenControlBox.ColorSchemes ColorScheme
		{
			get
			{
				return this._ColorScheme;
			}
			set
			{
				this._ColorScheme = value;
				ThirteenControlBox.ColorSchemeChangedEventHandler colorSchemeChangedEvent = this.ColorSchemeChangedEvent;
				if (colorSchemeChangedEvent != null)
				{
					colorSchemeChangedEvent();
				}
			}
		}

		// Token: 0x06000186 RID: 390 RVA: 0x0000D87C File Offset: 0x0000BA7C
		protected void OnColorSchemeChanged()
		{
			base.Invalidate();
			ThirteenControlBox.ColorSchemes colorScheme = this.ColorScheme;
			if (colorScheme != ThirteenControlBox.ColorSchemes.Light)
			{
				if (colorScheme == ThirteenControlBox.ColorSchemes.Dark)
				{
					this.BackColor = Color.FromArgb(50, 50, 50);
					this.ForeColor = Color.White;
					return;
				}
			}
			else
			{
				this.BackColor = Color.White;
				this.ForeColor = Color.Black;
			}
		}

		// Token: 0x17000087 RID: 135
		// (get) Token: 0x06000187 RID: 391 RVA: 0x0000D8D0 File Offset: 0x0000BAD0
		// (set) Token: 0x06000188 RID: 392 RVA: 0x0000D8D8 File Offset: 0x0000BAD8
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

		// Token: 0x06000189 RID: 393 RVA: 0x0000D8E8 File Offset: 0x0000BAE8
		public ThirteenControlBox()
		{
			this.ColorSchemeChanged += this.OnColorSchemeChanged;
			this.ButtonState = ThirteenControlBox.ButtonHover.None;
			this.DoubleBuffered = true;
			base.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
			base.SetStyle(ControlStyles.UserPaint, true);
			base.SetStyle(ControlStyles.ResizeRedraw, true);
			base.SetStyle(ControlStyles.OptimizedDoubleBuffer, true);
			this.ForeColor = Color.White;
			this.BackColor = Color.FromArgb(50, 50, 50);
			this.AccentColor = Color.DodgerBlue;
			this.ColorScheme = ThirteenControlBox.ColorSchemes.Dark;
			this.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
		}

		// Token: 0x0600018A RID: 394 RVA: 0x0000D97A File Offset: 0x0000BB7A
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Size = new Size(100, 25);
		}

		// Token: 0x0600018B RID: 395 RVA: 0x0000D994 File Offset: 0x0000BB94
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			int x = e.Location.X;
			int y = e.Location.Y;
			if (y > 0 && y < base.Height - 2)
			{
				if (x > 0 && x < 34)
				{
					this.ButtonState = ThirteenControlBox.ButtonHover.Minimize;
				}
				else if (x > 33 && x < 65)
				{
					this.ButtonState = ThirteenControlBox.ButtonHover.Maximize;
				}
				else if (x > 64 && x < base.Width)
				{
					this.ButtonState = ThirteenControlBox.ButtonHover.Close;
				}
				else
				{
					this.ButtonState = ThirteenControlBox.ButtonHover.None;
				}
			}
			else
			{
				this.ButtonState = ThirteenControlBox.ButtonHover.None;
			}
			base.Invalidate();
		}

		// Token: 0x0600018C RID: 396 RVA: 0x0000DA28 File Offset: 0x0000BC28
		protected override void OnPaint(PaintEventArgs e)
		{
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphics = Graphics.FromImage(bitmap);
			base.OnPaint(e);
			graphics.Clear(this.BackColor);
			switch (this.ButtonState)
			{
			case ThirteenControlBox.ButtonHover.Minimize:
				graphics.FillRectangle(new SolidBrush(this._AccentColor), new Rectangle(3, 0, 30, base.Height));
				break;
			case ThirteenControlBox.ButtonHover.Maximize:
				graphics.FillRectangle(new SolidBrush(this._AccentColor), new Rectangle(34, 0, 30, base.Height));
				break;
			case ThirteenControlBox.ButtonHover.Close:
				graphics.FillRectangle(new SolidBrush(this._AccentColor), new Rectangle(65, 0, 35, base.Height));
				break;
			}
			Font font = new Font("Marlett", 9.75f);
			graphics.DrawString("r", font, new SolidBrush(Color.FromArgb(200, 200, 200)), new Point(base.Width - 16, 7), new StringFormat
			{
				Alignment = StringAlignment.Center
			});
			FormWindowState windowState = base.Parent.FindForm().WindowState;
			if (windowState != FormWindowState.Normal)
			{
				if (windowState == FormWindowState.Maximized)
				{
					graphics.DrawString("2", font, new SolidBrush(Color.FromArgb(200, 200, 200)), new Point(51, 7), new StringFormat
					{
						Alignment = StringAlignment.Center
					});
				}
			}
			else
			{
				graphics.DrawString("1", font, new SolidBrush(Color.FromArgb(200, 200, 200)), new Point(51, 7), new StringFormat
				{
					Alignment = StringAlignment.Center
				});
			}
			graphics.DrawString("0", font, new SolidBrush(Color.FromArgb(200, 200, 200)), new Point(20, 7), new StringFormat
			{
				Alignment = StringAlignment.Center
			});
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}

		// Token: 0x0600018D RID: 397 RVA: 0x0000DC34 File Offset: 0x0000BE34
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			switch (this.ButtonState)
			{
			case ThirteenControlBox.ButtonHover.Minimize:
				base.Parent.FindForm().WindowState = FormWindowState.Minimized;
				return;
			case ThirteenControlBox.ButtonHover.Maximize:
				if (base.Parent.FindForm().WindowState == FormWindowState.Normal)
				{
					base.Parent.FindForm().WindowState = FormWindowState.Maximized;
					return;
				}
				base.Parent.FindForm().WindowState = FormWindowState.Normal;
				return;
			case ThirteenControlBox.ButtonHover.Close:
				base.Parent.FindForm().Close();
				return;
			default:
				return;
			}
		}

		// Token: 0x0600018E RID: 398 RVA: 0x0000DCBA File Offset: 0x0000BEBA
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.ButtonState = ThirteenControlBox.ButtonHover.None;
			base.Invalidate();
		}

		// Token: 0x040000D1 RID: 209
		private ThirteenControlBox.ColorSchemes _ColorScheme;

		// Token: 0x040000D2 RID: 210
		private Color _AccentColor;

		// Token: 0x040000D3 RID: 211
		private ThirteenControlBox.ButtonHover ButtonState;

		// Token: 0x02000048 RID: 72
		public enum ColorSchemes
		{
			// Token: 0x040001B2 RID: 434
			Light,
			// Token: 0x040001B3 RID: 435
			Dark
		}

		// Token: 0x02000049 RID: 73
		// (Invoke) Token: 0x0600037B RID: 891
		public delegate void ColorSchemeChangedEventHandler();

		// Token: 0x0200004A RID: 74
		public enum ButtonHover
		{
			// Token: 0x040001B5 RID: 437
			Minimize,
			// Token: 0x040001B6 RID: 438
			Maximize,
			// Token: 0x040001B7 RID: 439
			Close,
			// Token: 0x040001B8 RID: 440
			None
		}
	}
}
