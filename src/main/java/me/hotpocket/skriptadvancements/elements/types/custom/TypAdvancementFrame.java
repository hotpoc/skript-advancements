package me.hotpocket.skriptadvancements.elements.types.custom;

import ch.njol.skript.registrations.Classes;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.hotpocket.skriptadvancements.utils.EnumClassInfo;

public class TypAdvancementFrame {

	static {
		if (Classes.getExactClassInfo(AdvancementFrameType.class) == null) {
			Classes.registerClass(new EnumClassInfo<>(AdvancementFrameType.class, "frame")
					.user("frames")
					.name("Advancement Frame")
					.description("Represents an advancement frame. (Challenge, Goal, Task)")
					.since("1.4")
			);
		}
	}
}
