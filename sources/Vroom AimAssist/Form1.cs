using Bleak;
using Models;
using Pipe;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Text.Json;

namespace gui_ioctl
{
    public partial class Form1 : Form
    {
        private static bool is_connected = false;
        private static Injector injector;
        private static Settings settings = new Settings( );
        private static PipeServer server = new PipeServer( "syntax_mc" );

        [DllImport( "user32.dll", SetLastError = true )]
        static extern IntPtr FindWindow( string lpClassName, string lpWindowName );
        [DllImport( "user32.dll", SetLastError = true )]
        static extern uint GetWindowThreadProcessId( IntPtr hWnd, out int lpdwProcessId );

        public Form1( )
        {
            InitializeComponent( );

            int pid = 0;
            var mc = FindWindow( "LWJGL", null );

            GetWindowThreadProcessId( mc, out pid );
            injector = new Injector( pid, Dll.Dll.dll_buffer, InjectionMethod.CreateThread, InjectionFlags.HideDllFromPeb | InjectionFlags.RandomiseDllName | InjectionFlags.RandomiseDllHeaders );


            server.Connect += client_connected;
            server.MessageReceived += message_received;
            server.Disconnect += client_disconnected;

            server.Open( );

            new Thread( ( ) => { injector.InjectDll( ); } ).Start( );

            
            void message_received( object sender, MessageEventArgs args )
            {
                Console.WriteLine( $"Client sent: {args.Message}" );
            }

            void client_disconnected( object sender, EventArgs args )
            {
                label1.Invoke( ( MethodInvoker )delegate {
                    label1.Text = "Server: Not Connected";
                } );

            }

            void client_connected( object sender, EventArgs args )
            {
                label1.Invoke( ( MethodInvoker )delegate {
                    label1.Text = "Server: Connected";
                } );
            }
        }

        private void aimassistToggle_CheckedChanged( object sender, EventArgs e )
        {
            settings.aim_assist.m_enabled = aimassistToggle.Checked;
            server.SendMessage( JsonSerializer.Serialize( new {
                feature = "aimassist",
                settings = settings.aim_assist
            } ) );
        }

        private void trackBar1_Scroll( object sender, EventArgs e )
        {
            settings.aim_assist.m_fov = trackBar1.Value;
            label3.Text = trackBar1.Value.ToString();  
        }

        private void trackBar1_MouseUp( object sender, MouseEventArgs e )
        {
            server.SendMessage( JsonSerializer.Serialize( new
            {
                feature = "aimassist",
                settings = settings.aim_assist
            } ) );
        }

        private void trackBar2_Scroll( object sender, EventArgs e )
        {
            settings.aim_assist.m_speed = trackBar2.Value;
            label5.Text = trackBar2.Value.ToString( );
        }

        private void trackBar2_MouseUp( object sender, MouseEventArgs e )
        {
            server.SendMessage( JsonSerializer.Serialize( new
            {
                feature = "aimassist",
                settings = settings.aim_assist
            } ) );
        }

        private void button1_Click( object sender, EventArgs e )
        {
            server.SendMessage( "selfdestruct" );

            injector.EjectDll( );
            server.Close( );

            Process.GetCurrentProcess( ).Kill( );
        }
    }
}