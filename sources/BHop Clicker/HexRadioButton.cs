using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace shit_temple
{
	// Token: 0x02000010 RID: 16
	[DefaultEvent("CheckedChanged")]
	internal class HexRadioButton : Control
	{
		// Token: 0x06000049 RID: 73 RVA: 0x000024B1 File Offset: 0x000006B1
		public HexRadioButton()
		{
			this._State = MouseState.None;
		}

		// Token: 0x17000012 RID: 18
		// (get) Token: 0x0600004A RID: 74 RVA: 0x000024C0 File Offset: 0x000006C0
		// (set) Token: 0x0600004B RID: 75 RVA: 0x000032C4 File Offset: 0x000014C4
		public bool Checked
		{
			get
			{
				return this._Checked;
			}
			set
			{
				this._Checked = value;
				this.InvalidateControls();
				HexRadioButton.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
				if (checkedChangedEvent != null)
				{
					checkedChangedEvent(this);
				}
				base.Invalidate();
			}
		}

		// Token: 0x14000001 RID: 1
		// (add) Token: 0x0600004C RID: 76 RVA: 0x000032F8 File Offset: 0x000014F8
		// (remove) Token: 0x0600004D RID: 77 RVA: 0x00003330 File Offset: 0x00001530
		public event HexRadioButton.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x0600004E RID: 78 RVA: 0x000024C8 File Offset: 0x000006C8
		protected override void OnClick(EventArgs e)
		{
			if (!this._Checked)
			{
				this.Checked = true;
			}
			base.OnClick(e);
		}

		// Token: 0x0600004F RID: 79 RVA: 0x00003368 File Offset: 0x00001568
		private void InvalidateControls()
		{
			if (base.IsHandleCreated && this._Checked)
			{
				try
				{
					foreach (object obj in base.Parent.Controls)
					{
						Control control = (Control)obj;
						if (control != this && control is HexRadioButton)
						{
							((HexRadioButton)control).Checked = false;
							base.Invalidate();
						}
					}
				}
				finally
				{
					IEnumerator enumerator;
					if (enumerator is IDisposable)
					{
						(enumerator as IDisposable).Dispose();
					}
				}
			}
		}

		// Token: 0x06000050 RID: 80 RVA: 0x000024E0 File Offset: 0x000006E0
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			this.InvalidateControls();
		}

		// Token: 0x06000051 RID: 81 RVA: 0x000024EE File Offset: 0x000006EE
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 16;
		}

		// Token: 0x06000052 RID: 82 RVA: 0x000024FF File Offset: 0x000006FF
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this._State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000053 RID: 83 RVA: 0x00002515 File Offset: 0x00000715
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this._State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000054 RID: 84 RVA: 0x0000252B File Offset: 0x0000072B
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this._State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000055 RID: 85 RVA: 0x00002541 File Offset: 0x00000741
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this._State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000056 RID: 86 RVA: 0x000033F4 File Offset: 0x000015F4
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			Graphics graphics = e.Graphics;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.Clear(Color.FromArgb(30, 33, 40));
			graphics.FillEllipse(new SolidBrush(Color.FromArgb(47, 51, 60)), new Rectangle(0, 0, 15, 15));
			if (this.Checked)
			{
				graphics.FillEllipse(new SolidBrush(Color.FromArgb(236, 95, 75)), new Rectangle(4, 4, 7, 7));
			}
			graphics.DrawString(this.Text, new Font("Segoe UI", 9f), Brushes.White, new Point(18, -1));
		}

		// Token: 0x04000024 RID: 36
		private MouseState _State;

		// Token: 0x04000025 RID: 37
		private bool _Checked;

		// Token: 0x02000011 RID: 17
		// (Invoke) Token: 0x0600005A RID: 90
		public delegate void CheckedChangedEventHandler(object sender);
	}
}
