package me.hotpocket.skriptadvancements.elements.types.custom;

import ch.njol.skript.registrations.Classes;
import me.hotpocket.skriptadvancements.utils.EnumClassInfo;
import me.hotpocket.skriptadvancements.utils.advancement.VisibilityType;

public class TypVisibility {
    static {
        if (Classes.getExactClassInfo(VisibilityType.class) == null) {
            Classes.registerClass(new EnumClassInfo<>(VisibilityType.class, "visibility")
                    .user("visibilities")
                    .name("Advancement Visibility")
                    .description("Represents an advancement visibility. (Visible, Hidden, Parent Granted)")
                    .since("1.6")
            );
        }
    }
}
