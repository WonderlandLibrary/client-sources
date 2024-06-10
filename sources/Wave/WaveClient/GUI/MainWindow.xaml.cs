using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Interop;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Navigation;
using System.Windows.Shapes;
using Wave.Cmr;
using Wave.Cmr.Win32API;

namespace WaveClient.GUI
{
	// Token: 0x0200000B RID: 11
	public partial class MainWindow : Window
	{
		// Token: 0x17000004 RID: 4
		// (get) Token: 0x0600003B RID: 59 RVA: 0x00002B95 File Offset: 0x00000D95
		// (set) Token: 0x0600003C RID: 60 RVA: 0x00002B9D File Offset: 0x00000D9D
		private CompositionTarget WindowCompositionTarget { get; set; }

		// Token: 0x17000005 RID: 5
		// (get) Token: 0x0600003D RID: 61 RVA: 0x00002BA6 File Offset: 0x00000DA6
		// (set) Token: 0x0600003E RID: 62 RVA: 0x00002BAE File Offset: 0x00000DAE
		private double CachedMinWidth { get; set; }

		// Token: 0x17000006 RID: 6
		// (get) Token: 0x0600003F RID: 63 RVA: 0x00002BB7 File Offset: 0x00000DB7
		// (set) Token: 0x06000040 RID: 64 RVA: 0x00002BBF File Offset: 0x00000DBF
		private double CachedMinHeight { get; set; }

		// Token: 0x17000007 RID: 7
		// (get) Token: 0x06000041 RID: 65 RVA: 0x00002BC8 File Offset: 0x00000DC8
		// (set) Token: 0x06000042 RID: 66 RVA: 0x00002BD0 File Offset: 0x00000DD0
		private Win32.POINT CachedMinTrackSize { get; set; }

		// Token: 0x06000043 RID: 67 RVA: 0x00002BDC File Offset: 0x00000DDC
		private IntPtr WindowProc(IntPtr hwnd, int msg, IntPtr wParam, IntPtr lParam, ref bool handled)
		{
			if (msg == 36)
			{
				Win32.MINMAXINFO minmaxinfo = (Win32.MINMAXINFO)Marshal.PtrToStructure(lParam, typeof(Win32.MINMAXINFO));
				IntPtr intPtr = Win32.MonitorFromWindow(hwnd, 2);
				if (intPtr != IntPtr.Zero)
				{
					Win32.MONITORINFO monitorinfo = new Win32.MONITORINFO();
					Win32.GetMonitorInfo(intPtr, monitorinfo);
					Win32.RECT rcWork = monitorinfo.rcWork;
					Win32.RECT rcMonitor = monitorinfo.rcMonitor;
					minmaxinfo.ptMaxPosition.x = Math.Abs(rcWork.left - rcMonitor.left);
					minmaxinfo.ptMaxPosition.y = Math.Abs(rcWork.top - rcMonitor.top);
					minmaxinfo.ptMaxSize.x = Math.Abs(rcWork.right - rcWork.left);
					minmaxinfo.ptMaxSize.y = Math.Abs(rcWork.bottom - rcWork.top);
					if (!this.CachedMinTrackSize.Equals(minmaxinfo.ptMinTrackSize) || (this.CachedMinHeight != base.MinHeight && this.CachedMinWidth != base.MinWidth))
					{
						minmaxinfo.ptMinTrackSize.x = (int)((this.CachedMinWidth = base.MinWidth) * this.WindowCompositionTarget.TransformToDevice.M11);
						minmaxinfo.ptMinTrackSize.y = (int)((this.CachedMinHeight = base.MinHeight) * this.WindowCompositionTarget.TransformToDevice.M22);
						this.CachedMinTrackSize = minmaxinfo.ptMinTrackSize;
					}
				}
				Marshal.StructureToPtr<Win32.MINMAXINFO>(minmaxinfo, lParam, true);
				handled = true;
			}
			return IntPtr.Zero;
		}

		// Token: 0x06000044 RID: 68 RVA: 0x00002D7C File Offset: 0x00000F7C
		public MainWindow()
		{
			this.InitializeComponent();
			base.SourceInitialized += delegate(object s, EventArgs e)
			{
				this.WindowCompositionTarget = PresentationSource.FromVisual(this).CompositionTarget;
				HwndSource.FromHwnd(new WindowInteropHelper(this).Handle).AddHook(new HwndSourceHook(this.WindowProc));
			};
			this.WelcomeScreen();
		}

		// Token: 0x06000045 RID: 69 RVA: 0x00002DA2 File Offset: 0x00000FA2
		private void Control_Close_MouseUp(object sender, MouseButtonEventArgs e)
		{
			base.Close();
			cmr.ExitApplication();
		}

		// Token: 0x06000046 RID: 70 RVA: 0x00002DAF File Offset: 0x00000FAF
		private void Control_Maximize_MouseUp(object sender, MouseButtonEventArgs e)
		{
			if (base.WindowState != WindowState.Maximized)
			{
				base.WindowState = WindowState.Maximized;
				return;
			}
			base.WindowState = WindowState.Normal;
		}

		// Token: 0x06000047 RID: 71 RVA: 0x00002DC9 File Offset: 0x00000FC9
		private void Control_Minimize_MouseUp(object sender, MouseButtonEventArgs e)
		{
			base.WindowState = WindowState.Minimized;
		}

		// Token: 0x06000048 RID: 72 RVA: 0x00002DD4 File Offset: 0x00000FD4
		public void WelcomeScreen()
		{
			DoubleAnimation animation = new DoubleAnimation(0.0, 1.0, new Duration(TimeSpan.FromMilliseconds(2500.0)));
			this.Content.BeginAnimation(UIElement.OpacityProperty, animation);
		}

		// Token: 0x06000049 RID: 73 RVA: 0x00002E20 File Offset: 0x00001020
		private void NavigationFrame_Navigated(object sender, NavigatingCancelEventArgs e)
		{
			ThicknessAnimation thicknessAnimation = new ThicknessAnimation();
			thicknessAnimation.Duration = TimeSpan.FromSeconds(1.0);
			thicknessAnimation.EasingFunction = new QuadraticEase
			{
				EasingMode = EasingMode.EaseOut
			};
			thicknessAnimation.DecelerationRatio = 0.7;
			thicknessAnimation.To = new Thickness?(new Thickness(0.0, 0.0, 0.0, 0.0));
			if (e.NavigationMode == NavigationMode.New)
			{
				thicknessAnimation.From = new Thickness?(new Thickness(500.0, 500.0, 0.0, 0.0));
			}
			else if (e.NavigationMode == NavigationMode.Back)
			{
				thicknessAnimation.From = new Thickness?(new Thickness(0.0, 0.0, 500.0, 500.0));
			}
			DoubleAnimation doubleAnimation = new DoubleAnimation();
			doubleAnimation.To = new double?((double)1);
			doubleAnimation.From = new double?(0.0);
			thicknessAnimation.EasingFunction = new QuadraticEase
			{
				EasingMode = EasingMode.EaseOut
			};
			this.NavigationFrame.BeginAnimation(FrameworkElement.MarginProperty, thicknessAnimation);
			this.NavigationFrame.BeginAnimation(UIElement.OpacityProperty, doubleAnimation);
		}
	}
}
