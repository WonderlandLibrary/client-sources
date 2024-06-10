using System;
using System.Drawing;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x0200000D RID: 13
	public class Theme : ContainerControl
	{
		// Token: 0x1400000C RID: 12
		// (add) Token: 0x06000170 RID: 368 RVA: 0x0000D3A8 File Offset: 0x0000B5A8
		// (remove) Token: 0x06000171 RID: 369 RVA: 0x0000D3E0 File Offset: 0x0000B5E0
		public event Theme.ColorSchemeChangedEventHandler ColorSchemeChanged;

		// Token: 0x17000084 RID: 132
		// (get) Token: 0x06000172 RID: 370 RVA: 0x0000D415 File Offset: 0x0000B615
		// (set) Token: 0x06000173 RID: 371 RVA: 0x0000D420 File Offset: 0x0000B620
		public Theme.ColorSchemes ColorScheme
		{
			get
			{
				return this._ColorScheme;
			}
			set
			{
				this._ColorScheme = value;
				Theme.ColorSchemeChangedEventHandler colorSchemeChangedEvent = this.ColorSchemeChangedEvent;
				if (colorSchemeChangedEvent != null)
				{
					colorSchemeChangedEvent();
				}
			}
		}

		// Token: 0x06000174 RID: 372 RVA: 0x0000D444 File Offset: 0x0000B644
		protected void OnColorSchemeChanged()
		{
			base.Invalidate();
			Theme.ColorSchemes colorScheme = this.ColorScheme;
			if (colorScheme != Theme.ColorSchemes.Light)
			{
				if (colorScheme == Theme.ColorSchemes.Dark)
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

		// Token: 0x17000085 RID: 133
		// (get) Token: 0x06000175 RID: 373 RVA: 0x0000D498 File Offset: 0x0000B698
		// (set) Token: 0x06000176 RID: 374 RVA: 0x0000D4A0 File Offset: 0x0000B6A0
		public Color AccentColor
		{
			get
			{
				return this._AccentColor;
			}
			set
			{
				this._AccentColor = value;
				this.OnAccentColorChanged();
			}
		}

		// Token: 0x06000177 RID: 375 RVA: 0x0000D4B0 File Offset: 0x0000B6B0
		public Theme()
		{
			this.ColorSchemeChanged += this.OnColorSchemeChanged;
			this.AccentColorChanged += this.OnAccentColorChanged;
			this.MouseP = new Point(0, 0);
			this.Cap = false;
			this.pos = 0;
			this.DoubleBuffered = true;
			this.Font = new Font("Segoe UI Semilight", 9.75f);
			this.AccentColor = Color.DodgerBlue;
			this.ColorScheme = Theme.ColorSchemes.Dark;
			this.ForeColor = Color.White;
			this.BackColor = Color.FromArgb(50, 50, 50);
			this.MoveHeight = 32;
		}

		// Token: 0x1400000D RID: 13
		// (add) Token: 0x06000178 RID: 376 RVA: 0x0000D554 File Offset: 0x0000B754
		// (remove) Token: 0x06000179 RID: 377 RVA: 0x0000D58C File Offset: 0x0000B78C
		public event Theme.AccentColorChangedEventHandler AccentColorChanged;

		// Token: 0x0600017A RID: 378 RVA: 0x0000D5C4 File Offset: 0x0000B7C4
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			if (e.Button == MouseButtons.Left & new Rectangle(0, 0, base.Width, this.MoveHeight).Contains(e.Location))
			{
				this.Cap = true;
				this.MouseP = e.Location;
			}
		}

		// Token: 0x0600017B RID: 379 RVA: 0x0000D61C File Offset: 0x0000B81C
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			if (this.Cap)
			{
				base.Parent.Location = Control.MousePosition - (Size)this.MouseP;
			}
		}

		// Token: 0x0600017C RID: 380 RVA: 0x0000D64D File Offset: 0x0000B84D
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.Cap = false;
		}

		// Token: 0x0600017D RID: 381 RVA: 0x0000D65D File Offset: 0x0000B85D
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			this.Dock = DockStyle.Fill;
			base.Parent.FindForm().FormBorderStyle = FormBorderStyle.None;
		}

		// Token: 0x0600017E RID: 382 RVA: 0x0000D680 File Offset: 0x0000B880
		protected override void OnPaint(PaintEventArgs e)
		{
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphics = Graphics.FromImage(bitmap);
			base.OnPaint(e);
			graphics.Clear(this.BackColor);
			graphics.DrawLine(new Pen(this._AccentColor, 2f), new Point(0, 30), new Point(base.Width, 30));
			graphics.DrawString(this.Text, this.Font, new SolidBrush(this.ForeColor), new Rectangle(8, 6, base.Width - 1, base.Height - 1), StringFormat.GenericDefault);
			graphics.DrawLine(new Pen(this._AccentColor, 3f), new Point(8, 27), new Point((int)Math.Round((double)(8f + graphics.MeasureString(this.Text, this.Font).Width)), 27));
			graphics.DrawRectangle(new Pen(Color.FromArgb(100, 100, 100)), new Rectangle(0, 0, base.Width - 1, base.Height - 1));
			e.Graphics.DrawImage(bitmap, new Point(0, 0));
			graphics.Dispose();
			bitmap.Dispose();
		}

		// Token: 0x0600017F RID: 383 RVA: 0x0000D7BA File Offset: 0x0000B9BA
		protected void OnAccentColorChanged()
		{
			base.Invalidate();
		}

		// Token: 0x06000180 RID: 384 RVA: 0x0000D7C2 File Offset: 0x0000B9C2
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x06000181 RID: 385 RVA: 0x0000D7D1 File Offset: 0x0000B9D1
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Invalidate();
		}

		// Token: 0x040000C9 RID: 201
		private Theme.ColorSchemes _ColorScheme;

		// Token: 0x040000CA RID: 202
		private Color _AccentColor;

		// Token: 0x040000CC RID: 204
		private Point MouseP;

		// Token: 0x040000CD RID: 205
		private bool Cap;

		// Token: 0x040000CE RID: 206
		private int MoveHeight;

		// Token: 0x040000CF RID: 207
		private int pos;

		// Token: 0x02000045 RID: 69
		public enum ColorSchemes
		{
			// Token: 0x040001AF RID: 431
			Light,
			// Token: 0x040001B0 RID: 432
			Dark
		}

		// Token: 0x02000046 RID: 70
		// (Invoke) Token: 0x06000373 RID: 883
		public delegate void ColorSchemeChangedEventHandler();

		// Token: 0x02000047 RID: 71
		// (Invoke) Token: 0x06000377 RID: 887
		public delegate void AccentColorChangedEventHandler();
	}
}
