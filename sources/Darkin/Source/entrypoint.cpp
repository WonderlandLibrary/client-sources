//hayden was here


#include "../includes.hpp"
#include "Includes/window.hpp"
#include "../ui.hpp"
#include "../icons.hpp"
#include "../threads.hpp"
#include "../auto.hpp"

#ifdef __APPLE__
#define GL_SILENCE_DEPRECATION
#endif
#include <GLFW/glfw3.h>
#if defined(_MSC_VER) && (_MSC_VER >= 1900) && !defined(IMGUI_DISABLE_WIN32_FUNCTIONS)
#pragma comment(lib, "legacy_stdio_definitions")
#endif

const auto str_title = " ";

int main( int, char ** )
{

	ShowWindow( GetConsoleWindow( ), SW_HIDE );
	SetConsoleTitleA( " " );

	CreateThread( nullptr, 0, ( LPTHREAD_START_ROUTINE ) &hook::hookmouse, nullptr, 0, 0 );
	CreateThread( nullptr, 0, ( LPTHREAD_START_ROUTINE ) &c::t, nullptr, 0, 0 );
	CreateThread( nullptr, 0, ( LPTHREAD_START_ROUTINE ) &thread::ac::keybind_check, nullptr, 0, 0 );
	CreateThread( nullptr, 0, ( LPTHREAD_START_ROUTINE ) &thread::fix::slider, nullptr, 0, 0 );

	if ( !glfwInit( ) )
		return 1;
	glfwWindowHint( GLFW_RESIZABLE, GLFW_FALSE );
	GLFWwindow *window = glfwCreateWindow( 450, 300, str_title, NULL, NULL );
	if ( window == NULL )
		return 1;
	glfwMakeContextCurrent( window );
	glfwSwapInterval( 1 );

	ImGui::CreateContext( );
	ImGuiIO &io = ImGui::GetIO( ); ( void ) io;
	io.IniFilename = nullptr;
	ImGui::StyleColorsDark( );
	ImGui_ImplGlfw_InitForOpenGL( window, true );
	ImGui_ImplOpenGL2_Init( );
	ImGui::StyleColorsDark( );

	ImVec4 clear_color = ImVec4( 0.45f, 0.55f, 0.60f, 1.00f );

	ImGui::ColorSet( );

	fonts fnt;
	ImFont *font_principal = io.Fonts->AddFontFromMemoryCompressedTTF( fnt.getFontBytes( ), fnt.getFontSize( ), 15.0f );
	ImFont *font_big = io.Fonts->AddFontFromMemoryCompressedTTF( fnt.getFontBytes( ), fnt.getFontSize( ), 17.0f );
	ImFont *icons = io.Fonts->AddFontFromMemoryCompressedTTF( Icons_compressed_data, Icons_compressed_size, 100.0f );
	
	while ( !glfwWindowShouldClose( window ) )
	{
		glfwPollEvents( );
		ImGui_ImplOpenGL2_NewFrame( );
		ImGui_ImplGlfw_NewFrame( );
		ImGui::NewFrame( );
		ImGui::PushFont( font_principal );
		ImGui::SetNextWindowPos( ImVec2( -3, -15 ) );
		ImGui::SetNextWindowSize( ImVec2( 500, 350 ) );
		ImGui::Begin( xorstr( "background" ), nullptr, ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoMove | ImGuiWindowFlags_NoResize );

		ui::tab_1( icons );

		switch ( cfg::utils::switchTab )
		{
			case 1:
				ui::ac::tab_2( nullptr );
				break;
			case 2:
				ui::tab_3( nullptr );
				break;
		}

		ImGui::End( );
		ImGui::PopFont( );
		ImGui::Render( );

		int display_w, display_h;
		glfwGetFramebufferSize( window, &display_w, &display_h );
		glViewport( 0, 0, display_w, display_h );
		glClearColor( clear_color.x, clear_color.y, clear_color.z, clear_color.w );
		glClear( GL_COLOR_BUFFER_BIT );

		ImGui_ImplOpenGL2_RenderDrawData( ImGui::GetDrawData( ) );

		glfwMakeContextCurrent( window );
		glfwSwapBuffers( window );
	}

	// Cleanup
	ImGui_ImplOpenGL2_Shutdown( );
	ImGui_ImplGlfw_Shutdown( );
	ImGui::DestroyContext( );

	glfwDestroyWindow( window );
	glfwTerminate( );

	return 0;
}
