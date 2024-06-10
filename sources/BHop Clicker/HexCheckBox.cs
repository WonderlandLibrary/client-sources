using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace shit_temple
{
	// Token: 0x02000012 RID: 18
	[DefaultEvent("CheckedChanged")]
	internal class HexCheckBox : Control
	{
		// Token: 0x0600005B RID: 91 RVA: 0x00002557 File Offset: 0x00000757
		public HexCheckBox()
		{
			this._State = MouseState.None;
		}

		// Token: 0x0600005C RID: 92 RVA: 0x00002566 File Offset: 0x00000766
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x17000013 RID: 19
		// (get) Token: 0x0600005D RID: 93 RVA: 0x00002575 File Offset: 0x00000775
		// (set) Token: 0x0600005E RID: 94 RVA: 0x0000257D File Offset: 0x0000077D
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

		// Token: 0x14000002 RID: 2
		// (add) Token: 0x0600005F RID: 95 RVA: 0x000034A8 File Offset: 0x000016A8
		// (remove) Token: 0x06000060 RID: 96 RVA: 0x000034E0 File Offset: 0x000016E0
		public event HexCheckBox.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x06000061 RID: 97 RVA: 0x00003518 File Offset: 0x00001718
		protected override void OnClick(EventArgs e)
		{
			this._Checked = !this._Checked;
			HexCheckBox.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
			if (checkedChangedEvent != null)
			{
				checkedChangedEvent(this);
			}
			base.OnClick(e);
		}

		// Token: 0x06000062 RID: 98 RVA: 0x000024EE File Offset: 0x000006EE
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 16;
		}

		// Token: 0x06000063 RID: 99 RVA: 0x0000258C File Offset: 0x0000078C
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this._State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000064 RID: 100 RVA: 0x000025A2 File Offset: 0x000007A2
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this._State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000065 RID: 101 RVA: 0x000025B8 File Offset: 0x000007B8
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this._State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000066 RID: 102 RVA: 0x000025CE File Offset: 0x000007CE
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this._State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000067 RID: 103 RVA: 0x0000354C File Offset: 0x0000174C
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			Graphics graphics = e.Graphics;
			graphics.Clear(Color.FromArgb(30, 33, 40));
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(47, 51, 60)), new Rectangle(0, 0, 15, 15));
			if (this.Checked)
			{
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(236, 95, 75)), new Rectangle(4, 4, 7, 7));
			}
			graphics.DrawString(this.Text, new Font("Segoe UI", 9f), Brushes.White, new Point(18, -1));
		}

		// Token: 0x04000027 RID: 39
		private MouseState _State;

		// Token: 0x04000028 RID: 40
		private bool _Checked;

		// Token: 0x02000013 RID: 19
		// (Invoke) Token: 0x0600006B RID: 107
		public delegate void CheckedChangedEventHandler(object sender);
	}
}
