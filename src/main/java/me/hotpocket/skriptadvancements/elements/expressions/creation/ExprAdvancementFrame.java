package me.hotpocket.skriptadvancements.elements.expressions.creation;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import me.hotpocket.skriptadvancements.elements.sections.SecAdvancement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Creation - Advancement Frame")
@Description("Sets the frame of a custom advancement to any advancement frame.")
@Examples("set frame of advancement to task")
@Since("1.4")

public class ExprAdvancementFrame extends SimpleExpression<AdvancementFrameType> {

    static {
        Skript.registerExpression(ExprAdvancementFrame.class, AdvancementFrameType.class, ExpressionType.SIMPLE,
                "[the] frame [of [the] [last (created|made)] [custom] advancement]",
                "[the] [[last (created|made)] [custom] advancement[']s] frame");
    }

    @Override
    protected @Nullable AdvancementFrameType[] get(Event e) {
        return new AdvancementFrameType[]{getParser().getCurrentSection(SecAdvancement.class).getAdvancement().displayBuilder.getFrame()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends AdvancementFrameType> getReturnType() {
        return AdvancementFrameType.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the frame of the advancement";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return getParser().isCurrentSection(SecAdvancement.class);
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case SET-> CollectionUtils.array(AdvancementFrameType.class);
            default -> null;
        };
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        assert delta[0] != null;
        getParser().getCurrentSection(SecAdvancement.class).getAdvancement().displayBuilder.setFrame((AdvancementFrameType) delta[0]);
    }
}
