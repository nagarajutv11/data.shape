// Generated from Expression.g4 by ANTLR 4.1

    package com.nagaraju.data.shape.antlr;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#r}.
	 * @param ctx the parse tree
	 */
	void enterR(@NotNull ExpressionParser.RContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#r}.
	 * @param ctx the parse tree
	 */
	void exitR(@NotNull ExpressionParser.RContext ctx);
}