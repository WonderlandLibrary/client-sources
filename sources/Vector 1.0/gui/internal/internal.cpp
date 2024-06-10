
#include "internal.hpp"
#include "misc/stb/stb_ttyp.h"

auto gui::glfw::get_position(GLFW__* glfw) -> gui::vec2
{
	__int32 pos_x = 0, pos_y = 0;

	glfwGetWindowPos(glfw, &pos_x, &pos_y);

	return gui::vec2{ (double)pos_x, (double)pos_y };
}

auto gui::glfw::set_position(GLFW__* glfw, const gui::vec2 pos) -> void
{
	glfwSetWindowPos(glfw, pos.x, pos.y);
}

auto gui::glfw::set_interval(const __int32 interval) -> void
{
	glfwSwapInterval(interval);
}

auto gui::misc::is_inside(const gui::vec2 point, const gui::vec2 x, const gui::vec2 y) -> __int8
{
	return point.x >= x.x && point.y >= x.y && point.x <= y.x && point.y <= y.y;
}

auto gui::misc::get_baked() -> void*
{
	static stbtt_bakedchar baked_char_data[96];

	return baked_char_data;
}

auto gui::misc::get_text_height() -> double
{
	return 18.;
}

auto gui::misc::text_vector(std::string txt) -> gui::vec2
{
	static auto once = [&]() -> __int8
	{
		gui::draw::text_simple("", { DBL_MIN, DBL_MIN }, { 0., 0., 0., 0. } /*load font*/);

		return 0;
	};

	static auto call_once = once();

	auto pos_x = 0.f, pos_y = 0.f;

	for (unsigned __int64 x = 0, s = txt.length(); x < s; ++x)
	{
		stbtt_aligned_quad quad;

		stbtt_GetBakedQuad((stbtt_bakedchar*)gui::misc::get_baked(),
			512, 512, txt[x] - 32, &pos_x, &pos_y, &quad, 1);
	}

	return { (double)pos_x, gui::misc::get_text_height() };
}

auto gui::draw::line_simple(const gui::vec2 x, const gui::vec2 y, const gui::vec4 color) -> void
{
	if (gui::draw::buffering)
	{
		gui::draw::buffered_items.emplace_back(gui::draw::render_item
		{
			{ false, x, y, color }, {}, {}, 0
		});

		return;
	}

	glColor4d(color.x, color.y, color.z, color.a);

	glLineWidth(1.f);

	glTranslated(+.375, +.375, 0.);

	glEnable(GL_BLEND);
	glBlendFunc(
		GL_SRC_ALPHA,
		GL_ONE_MINUS_SRC_ALPHA
	);
	glBegin(GL_LINES);

	glVertex2d(round(x.x), round(x.y));
	glVertex2d(round(y.x), round(y.y));

	glEnd();
	glDisable(GL_BLEND);

	glTranslated(-.375, -.375, 0.);
}

auto gui::draw::line_smooth(const gui::vec2 x, const gui::vec2 y, const gui::vec4 color) -> void
{
	if (gui::draw::buffering)
	{
		gui::draw::buffered_items.emplace_back(gui::draw::render_item
		{
			{ true, x, y, color }, {}, {}, 0
		});

		return;
	}

	glColor4d(color.x, color.y, color.z, color.a);

	glLineWidth(1.f);

	glTranslated(+.375, +.375, 0.);

	glEnable(GL_BLEND);
	glBlendFunc(
		GL_SRC_ALPHA,
		GL_ONE_MINUS_SRC_ALPHA
	);
	glEnable(GL_LINE_SMOOTH);
	glBegin(GL_LINES);

	glVertex2d(round(x.x), round(x.y));
	glVertex2d(round(y.x), round(y.y));

	glEnd();
	glDisable(GL_LINE_SMOOTH);
	glDisable(GL_BLEND);

	glTranslated(-.375, -.375, 0.);
}

auto gui::draw::quad_simple(const gui::vec2 x, const gui::vec2 y, const gui::vec4 color) -> void
{
	if (gui::draw::buffering)
	{
		gui::draw::buffered_items.emplace_back(gui::draw::render_item
		{
			{}, { false, x, y, color }, {}, 1
		});

		return;
	}

	*(double*)&y.x -= 1., *(double*)&y.y -= 1.;

	gui::draw::line_simple({ x.x, x.y }, { y.x, x.y }, color);
	gui::draw::line_simple({ x.x, y.y }, { y.x, y.y }, color);
	gui::draw::line_simple({ x.x, x.y }, { x.x, y.y }, color);
	gui::draw::line_simple({ y.x, x.y }, { y.x, y.y + 1. }, color);
}

auto gui::draw::quad_filled(const gui::vec2 x, const gui::vec2 y, const gui::vec4 color) -> void
{
	if (gui::draw::buffering)
	{
		gui::draw::buffered_items.emplace_back(gui::draw::render_item
		{
			{}, { true, x, y, color }, {}, 1
		});

		return;
	}

	glColor4d(color.x, color.y, color.z, color.a);

	glTranslated(+.375, +.375, 0.);

	glEnable(GL_BLEND);
	glBegin(GL_QUADS);

	glVertex2d(round(x.x), round(x.y));
	glVertex2d(round(y.x), round(x.y));
	glVertex2d(round(y.x), round(y.y));
	glVertex2d(round(x.x), round(y.y));

	glEnd();
	glDisable(GL_BLEND);

	glTranslated(-.375, -.375, 0.);
}

auto gui::draw::text_simple(std::string txt, const gui::vec2 pos, const gui::vec4 color) -> void
{
	if (gui::draw::buffering)
	{
		gui::draw::buffered_items.emplace_back(gui::draw::render_item
		{
			{}, {}, { txt, pos, color }, 2
		});

		return;
	}

	static unsigned __int32 font = 0 /*verdana.ttf*/;

	static auto once = [&]() -> __int8
	{
		static unsigned char buffer[1 << 20];

		_iobuf* font_file = nullptr;

		fopen_s(&font_file, "C:/Windows/Fonts/verdana.ttf", "rb");

		fread(buffer, 1, 1 << 20, font_file);

		static unsigned char bitmap[512 * 512];

		stbtt_BakeFontBitmap(buffer, 0, gui::misc::get_text_height(), bitmap, 512, 512, 32, 96,
			(stbtt_bakedchar*)gui::misc::get_baked());

		glGenTextures(1, &font), glBindTexture(GL_TEXTURE_2D, font);

		glTexImage2D(
			GL_TEXTURE_2D,
			0,
			GL_ALPHA,
			512,
			512,
			0,
			GL_ALPHA,
			GL_UNSIGNED_BYTE,
			bitmap
		);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

		return 0;
	};
	
	static auto call_once = once();

	auto x_pos = (float)round(pos.x), y_pos = (float)round(pos.y);

	glColor4d(color.x, color.y, color.z, color.a);

	glEnable(GL_BLEND);
	glEnable(GL_TEXTURE_2D);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glBindTexture(GL_TEXTURE_2D, font);
	glBegin(GL_QUADS);

	for (unsigned __int64 x = 0, s = txt.length(); x < s; ++x)
	{
		stbtt_aligned_quad quad;

		stbtt_GetBakedQuad((stbtt_bakedchar*)gui::misc::get_baked(),
			512, 512, txt[x] - 32, &x_pos, &y_pos, &quad, 1);

		glTexCoord2f(quad.s0, quad.t1); glVertex2f(quad.x0, quad.y1);
		glTexCoord2f(quad.s1, quad.t1); glVertex2f(quad.x1, quad.y1);
		glTexCoord2f(quad.s1, quad.t0); glVertex2f(quad.x1, quad.y0);
		glTexCoord2f(quad.s0, quad.t0); glVertex2f(quad.x0, quad.y0);
	}

	glEnd();
	glDisable(GL_TEXTURE_2D);
	glDisable(GL_BLEND);
}

auto gui::draw::render_buffered_items() -> void
{
	if (gui::draw::buffered_items.size() > 0)
	{
		for (auto& item : gui::draw::buffered_items)
		{
			if (item.type == 0)
			{
				item.line.render();
			}

			if (item.type == 1)
			{
				item.quad.render();
			}

			if (item.type == 2)
			{
				item.text.render();
			}
		}

		gui::draw::buffered_items.clear();
	}
}