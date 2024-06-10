using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x0200001E RID: 30
	public class LogInButton : Control
	{
		// Token: 0x060001FF RID: 511 RVA: 0x00002E37 File Offset: 0x00001037
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = DrawHelpers.MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000200 RID: 512 RVA: 0x00002E50 File Offset: 0x00001050
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = DrawHelpers.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000201 RID: 513 RVA: 0x00002E69 File Offset: 0x00001069
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = DrawHelpers.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000202 RID: 514 RVA: 0x00002E82 File Offset: 0x00001082
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = DrawHelpers.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x170000B0 RID: 176
		// (get) Token: 0x06000203 RID: 515 RVA: 0x0000D320 File Offset: 0x0000B520
		// (set) Token: 0x06000204 RID: 516 RVA: 0x00002E9B File Offset: 0x0000109B
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

		// Token: 0x170000B1 RID: 177
		// (get) Token: 0x06000205 RID: 517 RVA: 0x0000D338 File Offset: 0x0000B538
		// (set) Token: 0x06000206 RID: 518 RVA: 0x00002EA5 File Offset: 0x000010A5
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

		// Token: 0x170000B2 RID: 178
		// (get) Token: 0x06000207 RID: 519 RVA: 0x0000D350 File Offset: 0x0000B550
		// (set) Token: 0x06000208 RID: 520 RVA: 0x00002EAF File Offset: 0x000010AF
		[Category("Colours")]
		public Color FontColour
		{
			get
			{
				return this._FontColour;
			}
			set
			{
				this._FontColour = value;
			}
		}

		// Token: 0x170000B3 RID: 179
		// (get) Token: 0x06000209 RID: 521 RVA: 0x0000D368 File Offset: 0x0000B568
		// (set) Token: 0x0600020A RID: 522 RVA: 0x00002EB9 File Offset: 0x000010B9
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._MainColour;
			}
			set
			{
				this._MainColour = value;
			}
		}

		// Token: 0x170000B4 RID: 180
		// (get) Token: 0x0600020B RID: 523 RVA: 0x0000D380 File Offset: 0x0000B580
		// (set) Token: 0x0600020C RID: 524 RVA: 0x00002EC3 File Offset: 0x000010C3
		[Category("Colours")]
		public Color HoverColour
		{
			get
			{
				return this._HoverColour;
			}
			set
			{
				this._HoverColour = value;
			}
		}

		// Token: 0x170000B5 RID: 181
		// (get) Token: 0x0600020D RID: 525 RVA: 0x0000D398 File Offset: 0x0000B598
		// (set) Token: 0x0600020E RID: 526 RVA: 0x00002ECD File Offset: 0x000010CD
		[Category("Colours")]
		public Color PressedColour
		{
			get
			{
				return this._PressedColour;
			}
			set
			{
				this._PressedColour = value;
			}
		}

		// Token: 0x0600020F RID: 527 RVA: 0x0000D3B0 File Offset: 0x0000B5B0
		public LogInButton()
		{
			this._Font = new Font("Segoe UI", 9f);
			this._ProgressColour = Color.FromArgb(0, 191, 255);
			this._BorderColour = Color.FromArgb(25, 25, 25);
			this._FontColour = Color.FromArgb(255, 255, 255);
			this._MainColour = Color.FromArgb(42, 42, 42);
			this._HoverColour = Color.FromArgb(52, 52, 52);
			this._PressedColour = Color.FromArgb(47, 47, 47);
			this.State = DrawHelpers.MouseState.None;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(75, 30);
			this.BackColor = Color.Transparent;
		}

		// Token: 0x06000210 RID: 528 RVA: 0x0000D48C File Offset: 0x0000B68C
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			Graphics graphics = G;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			graphics.Clear(this.BackColor);
			checked
			{
				switch (this.State)
				{
				case DrawHelpers.MouseState.None:
					graphics.FillRectangle(new SolidBrush(this._MainColour), new Rectangle(0, 0, base.Width, base.Height));
					graphics.DrawRectangle(new Pen(this._BorderColour, 2f), new Rectangle(0, 0, base.Width, base.Height));
					graphics.DrawString(this.Text, this._Font, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round((double)base.Height / 2.0)), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
					break;
				case DrawHelpers.MouseState.Over:
					graphics.FillRectangle(new SolidBrush(this._HoverColour), new Rectangle(0, 0, base.Width, base.Height));
					graphics.DrawRectangle(new Pen(this._BorderColour, 1f), new Rectangle(1, 1, base.Width - 2, base.Height - 2));
					graphics.DrawString(this.Text, this._Font, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round((double)base.Height / 2.0)), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
					break;
				case DrawHelpers.MouseState.Down:
					graphics.FillRectangle(new SolidBrush(this._PressedColour), new Rectangle(0, 0, base.Width, base.Height));
					graphics.DrawRectangle(new Pen(this._BorderColour, 1f), new Rectangle(1, 1, base.Width - 2, base.Height - 2));
					graphics.DrawString(this.Text, this._Font, Brushes.White, new Point((int)Math.Round((double)base.Width / 2.0), (int)Math.Round((double)base.Height / 2.0)), new StringFormat
					{
						Alignment = StringAlignment.Center,
						LineAlignment = StringAlignment.Center
					});
					break;
				}
			}
		}

		// Token: 0x040000CA RID: 202
		private readonly Font _Font;

		// Token: 0x040000CB RID: 203
		private Color _ProgressColour;

		// Token: 0x040000CC RID: 204
		private Color _BorderColour;

		// Token: 0x040000CD RID: 205
		private Color _FontColour;

		// Token: 0x040000CE RID: 206
		private Color _MainColour;

		// Token: 0x040000CF RID: 207
		private Color _HoverColour;

		// Token: 0x040000D0 RID: 208
		private Color _PressedColour;

		// Token: 0x040000D1 RID: 209
		private DrawHelpers.MouseState State;
	}
}
