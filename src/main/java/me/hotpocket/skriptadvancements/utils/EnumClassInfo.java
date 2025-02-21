package me.hotpocket.skriptadvancements.utils;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.util.EnumUtils;
import ch.njol.skript.util.Utils;

import javax.annotation.Nullable;

public class EnumClassInfo<T extends Enum<T>> extends ClassInfo<T> {

    // Credit: _tud

    private final EnumUtils<T> enumUtils;

    public EnumClassInfo(Class<T> clazz, String codeName) {
        super(clazz, codeName);
        enumUtils = new EnumUtils<T>(clazz, Utils.toEnglishPlural(codeName));
        usage(enumUtils.getAllNames());
        serializer(new EnumSerializer<>(clazz));
        defaultExpression(new EventValueExpression<>(clazz));
        parser(new Parser<T>() {
            @Override
            public @Nullable T parse(String s, ParseContext context) {
                return enumUtils.parse(s);
            }

            @Override
            public String toString(T o, int flags) {
                return enumUtils.toString(o, flags);
            }

            @Override
            public String toVariableNameString(T o) {
                return codeName + ": " + o.name().toLowerCase().replace('_', ' ');
            }
        });
    }

    public EnumUtils<T> getEnumUtils() {
        return enumUtils;
    }

    public EnumClassInfo(Class<T> clazz, String codeName, String languageNode) {
        super(clazz, codeName);
        enumUtils = new EnumUtils<T>(clazz, languageNode);
        usage(enumUtils.getAllNames());
        serializer(new EnumSerializer<>(clazz));
        defaultExpression(new EventValueExpression<>(clazz));
        parser(new Parser<T>() {
            @Override
            public @Nullable T parse(String s, ParseContext context) {
                return enumUtils.parse(s);
            }

            @Override
            public String toString(T o, int flags) {
                return enumUtils.toString(o, flags);
            }

            @Override
            public String toVariableNameString(T o) {
                return codeName + ": " + o.name().toLowerCase().replace('_', ' ');
            }
        });
    }
}
