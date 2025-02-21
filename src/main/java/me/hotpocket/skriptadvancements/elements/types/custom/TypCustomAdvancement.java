package me.hotpocket.skriptadvancements.elements.types.custom;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import me.hotpocket.skriptadvancements.SkriptAdvancements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypCustomAdvancement {
    static {
        if (Classes.getExactClassInfo(Advancement.class) == null) {
            Classes.registerClass(new ClassInfo<>(Advancement.class, "customadvancement")
                    .user("customadvancements?")
                    .name("Custom Advancement")
                    .description("Represents an advancement made with skript-advancements.")
                    .since("1.4")
                    .parser(new Parser<Advancement>() {
                        private final Pattern pattern = Pattern.compile("\\\"([a-z0-9_/:]+)\\\"");

                        @Override
                        public Advancement parse(String expr, ParseContext ctx) {
                            if (ctx == ParseContext.COMMAND) {
                                return UltimateAdvancementAPI.getInstance(SkriptAdvancements.getInstance()).getAdvancement(ctx.name().split("/")[0], ctx.name().split("/")[1]);
                            }
                            Matcher m = pattern.matcher(expr);
                            if (m.matches())
                                return UltimateAdvancementAPI.getInstance(SkriptAdvancements.getInstance()).getAdvancement(m.group(1).split("/")[0], m.group(1).split("/")[1]);
                            return null;
                        }

                        @Override
                        public boolean canParse(ParseContext context) {
                            return true;
                        }

                        @Override
                        public String toString(Advancement advancement, int flags) {
                            return advancement.getKey().getNamespace() + "/" + advancement.getKey().getKey();
                        }

                        @Override
                        public String toVariableNameString(Advancement advancement) {
                            return advancement.getKey().getNamespace() + "/" + advancement.getKey().getKey();
                        }
                    }));
        }
    }
}