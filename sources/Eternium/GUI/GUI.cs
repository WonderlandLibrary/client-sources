using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Interop;
using System.Windows.Markup;
using System.Windows.Threading;
using Eternium_mcpe_client.Data;
using Eternium_mcpe_client.DllImports;
using Eternium_mcpe_client.Hotkeys;
using Eternium_mcpe_client.Input;

namespace Eternium_mcpe_client.GUI
{
	// Token: 0x02000012 RID: 18
	public class GUI : Window, IComponentConnector
	{
		// Token: 0x0600005B RID: 91 RVA: 0x00005D7C File Offset: 0x00003F7C
		public GUI()
		{
			this.InitializeComponent();
			this.SnapToWindowTimer = new DispatcherTimer();
			this.SnapToWindowTimer.Interval = TimeSpan.FromMilliseconds(10.0);
			this.SnapToWindowTimer.Tick += this.SnapToWindowTimer_Tick;
			this.SnapToWindowTimer.Start();
		}

		// Token: 0x0600005C RID: 92 RVA: 0x00005DF0 File Offset: 0x00003FF0
		protected override void OnSourceInitialized(EventArgs e)
		{
			base.OnSourceInitialized(e);
			this.Handle = new WindowInteropHelper(this).Handle;
			int windowLong = DllImports.GetWindowLong(this.Handle, -20);
			DllImports.SetWindowLong(this.Handle, -20, windowLong | 32);
		}

		// Token: 0x0600005D RID: 93 RVA: 0x00005E38 File Offset: 0x00004038
		public void SnapToWindowTimer_Tick(object sender, EventArgs e)
		{
			CommunicationData.Overlay.TargetWindowHandle = DllImports.FindWindow(null, Data.C_Data.TargetWindowName);
			DllImports.GetWindowRect(CommunicationData.Overlay.TargetWindowHandle, out this.rect);
			DllImports.GetWindowRect(CommunicationData.Overlay.TargetWindowHandle, out this.rect);
			base.Width = (double)(this.rect.right - this.rect.left);
			base.Height = (double)(this.rect.bottom - this.rect.top);
			base.Top = (double)this.rect.top;
			base.Left = (double)this.rect.left;
			IntPtr foregroundWindow = DllImports.GetForegroundWindow();
			bool flag = foregroundWindow == this.Handle;
			if (flag)
			{
				DllImports.SetForegroundWindow(CommunicationData.Overlay.TargetWindowHandle);
				base.Show();
			}
			else
			{
				bool flag2 = foregroundWindow == CommunicationData.Overlay.TargetWindowHandle;
				if (flag2)
				{
					base.Show();
					bool keyStateRightShiftPressed = CInput.GetKeyStateRightShiftPressed();
					if (keyStateRightShiftPressed)
					{
						bool keyStateDown = Hotkeys.GetKeyStateDown(DllImports.VirtualKeys.Control);
						if (keyStateDown)
						{
							DllImports.SetForegroundWindow(CommunicationData.Console.Handle);
							DllImports.SetForegroundWindow(CommunicationData.Console.Handle);
						}
						else
						{
							DllImports.SetForegroundWindow(CommunicationData.MainWindow.WindowHandle);
						}
					}
				}
				else
				{
					bool flag3 = foregroundWindow == CommunicationData.Console.Handle && CInput.GetKeyStateRightShiftPressed() && !Hotkeys.GetKeyStateDown(DllImports.VirtualKeys.Control);
					if (flag3)
					{
						DllImports.SetForegroundWindow(CommunicationData.Overlay.TargetWindowHandle);
						base.Hide();
					}
					else
					{
						base.Hide();
					}
				}
			}
		}

		// Token: 0x0600005E RID: 94 RVA: 0x00005FAC File Offset: 0x000041AC
		[DebuggerNonUserCode]
		[GeneratedCode("PresentationBuildTasks", "4.0.0.0")]
		public void InitializeComponent()
		{
			bool contentLoaded = this._contentLoaded;
			if (!contentLoaded)
			{
				this._contentLoaded = true;
				Uri resourceLocator = new Uri("/Eternium;component/gui/gui.overlay.xaml", UriKind.Relative);
				Application.LoadComponent(this, resourceLocator);
			}
		}

		// Token: 0x0600005F RID: 95 RVA: 0x00005FE2 File Offset: 0x000041E2
		[DebuggerNonUserCode]
		[GeneratedCode("PresentationBuildTasks", "4.0.0.0")]
		[EditorBrowsable(EditorBrowsableState.Never)]
		void IComponentConnector.Connect(int connectionId, object target)
		{
			this._contentLoaded = true;
		}

		// Token: 0x04000048 RID: 72
		private DllImports.SimpleRECT rect = default(DllImports.SimpleRECT);

		// Token: 0x04000049 RID: 73
		private DispatcherTimer SnapToWindowTimer;

		// Token: 0x0400004A RID: 74
		private IntPtr Handle;

		// Token: 0x0400004B RID: 75
		private bool _contentLoaded;
	}
}
