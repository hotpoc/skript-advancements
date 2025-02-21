package me.hotpocket.skriptadvancements.elements.expressions.vanilla;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.util.coll.CollectionUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.jetbrains.annotations.Nullable;

@Name("Advancement Message")
@Description("Allows you to get and set the advancement message in the advancement event.")
@Examples("set advancement message to \"%player% got the advancement %event-advancement%\"")
@Since("1.4")

public class ExprAdvancementMessage extends EventValueExpression<String> {

    static {
        if (Skript.classExists("net.kyori.adventure.text.Component"))
            Skript.registerExpression(ExprAdvancementMessage.class, String.class, ExpressionType.SIMPLE,
                    "[the] advancement message",
                    "advancement's message",
                    "[the] message of [the] advancement");
    }

    public ExprAdvancementMessage() {
        super(String.class);
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET,DELETE -> CollectionUtils.array(String.class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        PlayerAdvancementDoneEvent event = (PlayerAdvancementDoneEvent) e;
        assert delta[0] != null;
        switch (mode) {
            case SET -> event.message(Component.text((String) delta[0]));
            case DELETE -> event.message(null);
        }
    }

    @Override
    @Nullable
    protected String[] get(Event e) {
        PlayerAdvancementDoneEvent event = (PlayerAdvancementDoneEvent) e;
        return new String[]{Bukkit.getUnsafe().legacyComponentSerializer().serialize(event.message())};
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the advancement message";
    }
}