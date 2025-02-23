package me.hotpocket.skriptadvancements.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypAdvancement {
	static {
		if (Classes.getExactClassInfo(Advancement.class) == null) {
			Classes.registerClass(new ClassInfo<>(Advancement.class, "advancement")
					.user("advancements?")
					.name("Advancement")
					.description("Represents an advancement.")
					.since("1.4")
					.parser(new Parser<Advancement>() {
						private final Pattern pattern = Pattern.compile("\\\"([a-z0-9_/:]+)\\\"");

						@Override
						public Advancement parse(String expr, ParseContext ctx) {
							if (ctx == ParseContext.COMMAND) {
								if (expr.contains(":")) {
									return Bukkit.getAdvancement(new NamespacedKey(expr.split(":")[0], expr.split(":")[1]));
								} else {
									return Bukkit.getAdvancement(NamespacedKey.minecraft(expr));
								}
							}
							Matcher m = pattern.matcher(expr);
							if (m.matches())
								if (m.group(1).contains(":")) {
									return Bukkit.getAdvancement(new NamespacedKey(m.group(1).split(":")[0], m.group(1).split(":")[1]));
								} else {
									return Bukkit.getAdvancement(NamespacedKey.minecraft(m.group(1)));
								}
							return null;
						}

						@Override
						public boolean canParse(ParseContext context) {
							return true;
						}

						@Override
						public String toString(Advancement advancement, int flags) {
							return "" + advancement.getKey().getKey();
						}

						@Override
						public String toVariableNameString(Advancement advancement) {
							return "" + advancement.getKey().getKey();
						}
					}));
		}
	}
}
