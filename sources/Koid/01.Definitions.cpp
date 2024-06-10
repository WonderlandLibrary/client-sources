
#include "conector.h"

HANDLE thread_handle_binds;
HANDLE thread_handle_clicker;
HANDLE thread_handle_reach;
HANDLE thread_handle_speed;
HANDLE thread_handle_jitter;
HANDLE thread_handle_inventory;
HANDLE thread_handle_velocity;

bool cucklord_logged = false;
int cucklord_version = 3;
char cucklord_username[MAX_PATH];
char cucklord_password[MAX_PATH];

int cucklord_tab_main = 1;
std::vector<const char*>cucklord_tab_main_vector = { "COMBAT", "MOVEMENT", "DESTRUCT" };
int cucklord_tab_misc = 1;


bool cucklord_incorrect_user = false;
bool cucklord_incorrect_pass = false;
bool cucklord_incorrect_hwid = false;
bool cucklord_serverside_mode = false;
bool cucklord_titlebar_clicked = false;
bool cucklord_titlebar_moving = false;
int cucklord_horizontal = 0;
int cucklord_vertical = 0;

float cucklord_clicker_value = 10.0f;
bool cucklord_clicker_enabled = false;
char cucklord_clicker_window[MAX_PATH] = "Minecraft";
float cucklord_clicker_jitter_value = 0;
bool cucklord_clicker_inventory = false;
bool cucklord_clicker_minecraftonly = false;
bool cucklord_clicker_bind_pressed = false;
std::string cucklord_clicker_bind_text = "BIND";


float cucklord_reach_value = 3.0f;
float cucklord_reach_chance_value = 100;
bool cucklord_reach_enabled = false;
bool cucklord_reach_bind_pressed = false;
std::string cucklord_reach_bind_text = "BIND";


float cucklord_velocity_value = 100.0f;
bool cucklord_velocity_enabled = false;
bool cucklord_velocity_bind_pressed = false;
std::string cucklord_velocity_bind_text = "BIND";

bool cucklord_inventory_status = false;

bool cucklord_speed_tpmode = false;
float cucklord_speed_value = 0.00f;
bool cucklord_speed_enabled = false;
bool cucklord_speed_bind_pressed = false;
std::string cucklord_speed_bind_text = "BIND";


bool cucklord_hide_enabled = false;
bool cucklord_hide_bind_pressed = false;
std::string cucklord_hide_bind_text = "BIND";


int cucklord_priority_value = 2;
const char* cucklord_priority_value_char[] = { "LOW", "MID", "HIGH" };
int cucklord_priority_sleeper = 500;

std::vector<const char*>cucklord_destruct_options_text = { " STRING CLEAN", " SELF DELETE" };
bool cucklord_destruct_clean_strings = true; bool cucklord_destruct_selfdelete = false;
