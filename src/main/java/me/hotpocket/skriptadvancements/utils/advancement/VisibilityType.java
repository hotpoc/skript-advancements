package me.hotpocket.skriptadvancements.utils.advancement;

import me.hotpocket.skriptadvancements.utils.advancement.AdvancementTypes.*;

public enum VisibilityType {
    VISIBLE(VisibleMultiParentsAdvancement.class, VisibleAdvancement.class),
    HIDDEN(HiddenMultiParentsAdvancement.class, HiddenAdvancement.class),
    PARENT_GRANTED(ParentGrantedMultiParentsAdvancement.class, ParentGrantedAdvancement.class);

    final Class<?> clazz;
    final Class<?> clazz2;

    VisibilityType(Class<?> clazz, Class<?> clazz2) {
        this.clazz = clazz;
        this.clazz2 = clazz2;
    }

    public Class<?> getMultiAdvancementClass() {
        return clazz;
    }

    public Class<?> getAdvancementClass() { return clazz2; }
}
