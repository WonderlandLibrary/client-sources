#include "main.h"

int gui::tab = 1;
bool gui::rgb = false;
ImVec4 gui::clear_col = ImColor(45, 150, 255);
bool gui::r = true;
bool gui::g = false;
bool gui::b = false;
int gui::red = 255;
int gui::green = 0;
int gui::blue = 0;


bool client::cleanstrings = true;
bool client::selfdelete = false;
std::string client::version = "1.1";

bool modules::clicker::enabled = false;
float modules::clicker::cps = 12.0f;
float modules::clicker::chance = 90.0f;
bool modules::clicker::sounds = false;
char modules::clicker::soundfile[128] = "";
int modules::clicker::bind = 0;
bool modules::clicker::bind_edit = false;
std::string modules::clicker::bind_text = "[NONE]";

bool modules::reach::enabled = false;
float modules::reach::blocks = 3.0f;
int modules::reach::bind = 0;
bool modules::reach::bind_edit = false;
std::string modules::reach::bind_text = "[NONE]";

bool modules::velocity::enabled = false;
float modules::velocity::amount = 100.0f;
int modules::velocity::bind = 0;
bool modules::velocity::bind_edit = false;
std::string modules::velocity::bind_text = "[NONE]";