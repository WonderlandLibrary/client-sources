using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x02000019 RID: 25
	[DefaultEvent("CheckedChanged")]
	internal class FlatCheckBox : Control
	{
		// Token: 0x0600014D RID: 333 RVA: 0x00003271 File Offset: 0x00001471
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x1700007A RID: 122
		// (get) Token: 0x0600014E RID: 334 RVA: 0x000033BB File Offset: 0x000015BB
		// (set) Token: 0x0600014F RID: 335 RVA: 0x000033C3 File Offset: 0x000015C3
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

		// Token: 0x14000003 RID: 3
		// (add) Token: 0x06000150 RID: 336 RVA: 0x0000749C File Offset: 0x0000569C
		// (remove) Token: 0x06000151 RID: 337 RVA: 0x000074D4 File Offset: 0x000056D4
		public event FlatCheckBox.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x06000152 RID: 338 RVA: 0x0000750C File Offset: 0x0000570C
		protected override void OnClick(EventArgs e)
		{
			this._Checked = !this._Checked;
			FlatCheckBox.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
			if (checkedChangedEvent != null)
			{
				checkedChangedEvent(this);
			}
			base.OnClick(e);
		}

        private void CheckedChangedEvent(object sender)
        {
            throw new NotImplementedException();
        }

        // Token: 0x1700007B RID: 123
        // (get) Token: 0x06000153 RID: 339 RVA: 0x000033D2 File Offset: 0x000015D2
        // (set) Token: 0x06000154 RID: 340 RVA: 0x000033DA File Offset: 0x000015DA
        [Category("Options")]
		public FlatCheckBox._Options Options
		{
			get
			{
				return this.O;
			}
			set
			{
				this.O = value;
			}
		}

		// Token: 0x06000155 RID: 341 RVA: 0x00003330 File Offset: 0x00001530
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 22;
		}

		// Token: 0x1700007C RID: 124
		// (get) Token: 0x06000156 RID: 342 RVA: 0x000033E3 File Offset: 0x000015E3
		// (set) Token: 0x06000157 RID: 343 RVA: 0x000033EB File Offset: 0x000015EB
		[Category("Colors")]
		public Color BaseColor
		{
			get
			{
				return this._BaseColor;
			}
			set
			{
				this._BaseColor = value;
			}
		}

		// Token: 0x1700007D RID: 125
		// (get) Token: 0x06000158 RID: 344 RVA: 0x000033F4 File Offset: 0x000015F4
		// (set) Token: 0x06000159 RID: 345 RVA: 0x000033FC File Offset: 0x000015FC
		[Category("Colors")]
		public Color BorderColor
		{
			get
			{
				return this._BorderColor;
			}
			set
			{
				this._BorderColor = value;
			}
		}

		// Token: 0x0600015A RID: 346 RVA: 0x00003405 File Offset: 0x00001605
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x0600015B RID: 347 RVA: 0x0000341B File Offset: 0x0000161B
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x0600015C RID: 348 RVA: 0x00003431 File Offset: 0x00001631
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x0600015D RID: 349 RVA: 0x00003447 File Offset: 0x00001647
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x0600015E RID: 350 RVA: 0x00007540 File Offset: 0x00005740
		public FlatCheckBox()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(60, 60, 60);
			this._BorderColor = Color.FromArgb(0, 170, 220);
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(50, 50, 50);
			this.Cursor = Cursors.Hand;
			this.Font = new Font("Segoe UI", 10f);
			base.Size = new Size(112, 22);
		}

		// Token: 0x0600015F RID: 351 RVA: 0x000075F0 File Offset: 0x000057F0
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			checked
			{
				this.W = base.Width - 1;
				this.H = base.Height - 1;
				Rectangle rect = new Rectangle(0, 2, base.Height - 5, base.Height - 5);
				Graphics g = Helpers.G;
				g.SmoothingMode = SmoothingMode.HighQuality;
				g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
				g.Clear(this.BackColor);
				FlatCheckBox._Options o = this.O;
				if (o != FlatCheckBox._Options.Style1)
				{
					if (o == FlatCheckBox._Options.Style2)
					{
						g.FillRectangle(new SolidBrush(this._BaseColor), rect);
						MouseState state = this.State;
						if (state != MouseState.Over)
						{
							if (state == MouseState.Down)
							{
								g.DrawRectangle(new Pen(this._BorderColor), rect);
								g.FillRectangle(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
							}
						}
						else
						{
							g.DrawRectangle(new Pen(this._BorderColor), rect);
							g.FillRectangle(new SolidBrush(Color.FromArgb(118, 213, 170)), rect);
						}
						if (this.Checked)
						{
							g.DrawString("ü", new Font("Wingdings", 18f), new SolidBrush(this._BorderColor), new Rectangle(5, 7, this.H - 9, this.H - 9), Helpers.CenterSF);
						}
						if (!base.Enabled)
						{
							g.FillRectangle(new SolidBrush(Color.FromArgb(54, 58, 61)), rect);
							g.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(48, 119, 91)), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
						}
						g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
					}
				}
				else
				{
					g.FillRectangle(new SolidBrush(this._BaseColor), rect);
					MouseState state2 = this.State;
					if (state2 != MouseState.Over)
					{
						if (state2 == MouseState.Down)
						{
							g.DrawRectangle(new Pen(this._BorderColor), rect);
						}
					}
					else
					{
						g.DrawRectangle(new Pen(this._BorderColor), rect);
					}
					if (this.Checked)
					{
						g.DrawString("ü", new Font("Wingdings", 18f), new SolidBrush(this._BorderColor), new Rectangle(5, 7, this.H - 9, this.H - 9), Helpers.CenterSF);
					}
					if (!base.Enabled)
					{
						g.FillRectangle(new SolidBrush(Color.FromArgb(54, 58, 61)), rect);
						g.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(140, 142, 143)), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
					}
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
				}
				base.OnPaint(e);
				Helpers.G.Dispose();
				e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
				e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
				Helpers.B.Dispose();
			}
		}

		// Token: 0x0400005D RID: 93
		private int W;

		// Token: 0x0400005E RID: 94
		private int H;

		// Token: 0x0400005F RID: 95
		private MouseState State;

		// Token: 0x04000060 RID: 96
		private FlatCheckBox._Options O;

		// Token: 0x04000061 RID: 97
		private bool _Checked;

		// Token: 0x04000063 RID: 99
		private Color _BaseColor;

		// Token: 0x04000064 RID: 100
		private Color _BorderColor;

		// Token: 0x04000065 RID: 101
		private Color _TextColor;

		// Token: 0x0200001A RID: 26
		// (Invoke) Token: 0x06000163 RID: 355
		public delegate void CheckedChangedEventHandler(object sender);

		// Token: 0x0200001B RID: 27
		[Flags]
		public enum _Options
		{
			// Token: 0x04000067 RID: 103
			Style1 = 0,
			// Token: 0x04000068 RID: 104
			Style2 = 1
		}
	}
}
