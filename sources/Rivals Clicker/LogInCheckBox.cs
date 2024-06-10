using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000016 RID: 22
	[DefaultEvent("CheckedChanged")]
	public class LogInCheckBox : Control
	{
		// Token: 0x17000091 RID: 145
		// (get) Token: 0x06000193 RID: 403 RVA: 0x0000BE00 File Offset: 0x0000A000
		// (set) Token: 0x06000194 RID: 404 RVA: 0x00002BAB File Offset: 0x00000DAB
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._BackColour;
			}
			set
			{
				this._BackColour = value;
			}
		}

		// Token: 0x17000092 RID: 146
		// (get) Token: 0x06000195 RID: 405 RVA: 0x0000BE18 File Offset: 0x0000A018
		// (set) Token: 0x06000196 RID: 406 RVA: 0x00002BB5 File Offset: 0x00000DB5
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

		// Token: 0x17000093 RID: 147
		// (get) Token: 0x06000197 RID: 407 RVA: 0x0000BE30 File Offset: 0x0000A030
		// (set) Token: 0x06000198 RID: 408 RVA: 0x00002BBF File Offset: 0x00000DBF
		[Category("Colours")]
		public Color CheckedColour
		{
			get
			{
				return this._CheckedColour;
			}
			set
			{
				this._CheckedColour = value;
			}
		}

		// Token: 0x17000094 RID: 148
		// (get) Token: 0x06000199 RID: 409 RVA: 0x0000BE48 File Offset: 0x0000A048
		// (set) Token: 0x0600019A RID: 410 RVA: 0x00002BC9 File Offset: 0x00000DC9
		[Category("Colours")]
		public Color FontColour
		{
			get
			{
				return this._TextColour;
			}
			set
			{
				this._TextColour = value;
			}
		}

		// Token: 0x0600019B RID: 411 RVA: 0x00002BD3 File Offset: 0x00000DD3
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x17000095 RID: 149
		// (get) Token: 0x0600019C RID: 412 RVA: 0x0000BE60 File Offset: 0x0000A060
		// (set) Token: 0x0600019D RID: 413 RVA: 0x00002BE5 File Offset: 0x00000DE5
		public bool Checked
		{
			get
			{
				return this._Checked;
			}
			set
			{
				this._Checked = value;
				base.Invalidate();
			}
		}

		// Token: 0x14000001 RID: 1
		// (add) Token: 0x0600019E RID: 414 RVA: 0x0000BE78 File Offset: 0x0000A078
		// (remove) Token: 0x0600019F RID: 415 RVA: 0x0000BEB0 File Offset: 0x0000A0B0
		public event LogInCheckBox.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x060001A0 RID: 416 RVA: 0x0000BEE8 File Offset: 0x0000A0E8
		protected override void OnClick(EventArgs e)
		{
			this._Checked = !this._Checked;
			LogInCheckBox.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
			if (checkedChangedEvent != null)
			{
				checkedChangedEvent(this);
			}
			base.OnClick(e);
		}

		// Token: 0x060001A1 RID: 417 RVA: 0x00002BF6 File Offset: 0x00000DF6
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 22;
		}

		// Token: 0x060001A2 RID: 418 RVA: 0x00002C0A File Offset: 0x00000E0A
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = DrawHelpers.MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060001A3 RID: 419 RVA: 0x00002C23 File Offset: 0x00000E23
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = DrawHelpers.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001A4 RID: 420 RVA: 0x00002C3C File Offset: 0x00000E3C
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = DrawHelpers.MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001A5 RID: 421 RVA: 0x00002C55 File Offset: 0x00000E55
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = DrawHelpers.MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060001A6 RID: 422 RVA: 0x0000BF20 File Offset: 0x0000A120
		public LogInCheckBox()
		{
			this.State = DrawHelpers.MouseState.None;
			this._CheckedColour = Color.FromArgb(173, 173, 174);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._BackColour = Color.FromArgb(42, 42, 42);
			this._TextColour = Color.FromArgb(255, 255, 255);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Cursor = Cursors.Hand;
			base.Size = new Size(100, 22);
		}

		// Token: 0x060001A7 RID: 423 RVA: 0x0000BFC4 File Offset: 0x0000A1C4
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Rectangle Base = new Rectangle(0, 0, 20, 20);
			Graphics graphics = g;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(Color.FromArgb(54, 54, 54));
			graphics.FillRectangle(new SolidBrush(this._BackColour), Base);
			graphics.DrawRectangle(new Pen(this._BorderColour), new Rectangle(1, 1, 18, 18));
			DrawHelpers.MouseState state = this.State;
			if (state == DrawHelpers.MouseState.Over)
			{
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(50, 49, 51)), Base);
				graphics.DrawRectangle(new Pen(this._BorderColour), new Rectangle(1, 1, 18, 18));
			}
			bool @checked = this.Checked;
			if (@checked)
			{
				Point[] P = new Point[]
				{
					new Point(4, 11),
					new Point(6, 8),
					new Point(9, 12),
					new Point(15, 3),
					new Point(17, 6),
					new Point(9, 16)
				};
				graphics.FillPolygon(new SolidBrush(this._CheckedColour), P);
			}
			graphics.DrawString(this.Text, this.Font, new SolidBrush(this._TextColour), new Rectangle(24, 1, base.Width, checked(base.Height - 2)), new StringFormat
			{
				Alignment = StringAlignment.Near,
				LineAlignment = StringAlignment.Center
			});
			graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		}

		// Token: 0x040000A4 RID: 164
		private bool _Checked;

		// Token: 0x040000A5 RID: 165
		private DrawHelpers.MouseState State;

		// Token: 0x040000A6 RID: 166
		private Color _CheckedColour;

		// Token: 0x040000A7 RID: 167
		private Color _BorderColour;

		// Token: 0x040000A8 RID: 168
		private Color _BackColour;

		// Token: 0x040000A9 RID: 169
		private Color _TextColour;

		// Token: 0x02000017 RID: 23
		// (Invoke) Token: 0x060001AB RID: 427
		public delegate void CheckedChangedEventHandler(object sender);
	}
}
