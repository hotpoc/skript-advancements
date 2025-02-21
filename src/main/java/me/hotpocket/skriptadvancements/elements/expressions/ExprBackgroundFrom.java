package me.hotpocket.skriptadvancements.elements.expressions;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.hotpocket.skriptadvancements.utils.CustomUtils;

import javax.annotation.Nullable;

public class ExprBackgroundFrom extends SimplePropertyExpression<ItemType, String> {

	static {
		register(ExprBackgroundFrom.class, String.class, "background", "itemtype");
	}

	@Override
	public @Nullable String convert(ItemType from) {
		return CustomUtils.getTexture(from.getMaterial());
	}

	@Override
	protected String getPropertyName() {
		return "background from item";
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

}
