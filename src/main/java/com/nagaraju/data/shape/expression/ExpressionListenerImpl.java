package com.nagaraju.data.shape.expression;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.nagaraju.data.shape.antlr.ExpressionBaseListener;
import com.nagaraju.data.shape.antlr.ExpressionParser.ExprContext;
import com.nagaraju.data.shape.antlr.ExpressionParser.InvocationContext;
import com.nagaraju.data.shape.antlr.ExpressionParser.LiteralContext;
import com.nagaraju.data.shape.antlr.ExpressionParser.PathContext;

public class ExpressionListenerImpl extends ExpressionBaseListener {

	private Deque<Expression> queue = new ArrayDeque<>();

	private static final Map<String, Class<? extends Expression>> FUCNTIONS;

	static {
		Map<String, Class<? extends Expression>> functions = new HashMap<>();
		functions.put("element", ElementExpression.class);
		functions.put("size", SizeExpression.class);
		functions.put("date_parse", DateParseExpression.class);
		functions.put("after", AfterExpression.class);
		functions.put("this_month", ThisMonthExpression.class);
		FUCNTIONS = Collections.unmodifiableMap(functions);
	}

	private static final Map<String, Class<? extends Expression>> OPERATIONS;

	static {
		Map<String, Class<? extends Expression>> operations = new HashMap<>();
		operations.put("+", AdditionExpression.class);
		OPERATIONS = Collections.unmodifiableMap(operations);
	}

	public Expression getExpression() {
		return queue.pop();
	}

	@Override
	public void exitExpr(ExprContext ctx) {
		int size = ctx.OPERATOR().size();
		for (int i = 0; i < size; i++) {
			Expression right = queue.pop();
			Expression op = findOperations(OPERATIONS, ctx.OPERATOR(i).getText());
			Expression left = queue.pop();
			op.addArg(left, 0);
			op.addArg(right, 1);
			queue.push(op);
		}
	}

	@Override
	public void exitInvocation(InvocationContext ctx) {
		List<ExprContext> expr = ctx.expr();
		List<Expression> args = new ArrayList<>();
		expr.forEach(e -> args.add(0, queue.pop()));
		Expression inv = findOperations(FUCNTIONS, ctx.ID().getText());
		for (int i = 0; i < args.size(); i++) {
			inv.addArg(args.get(i), i);
		}
		queue.push(inv);
	}

	@Override
	public void exitLiteral(LiteralContext ctx) {
		if (ctx.SQSTR() != null) {
			String text = ctx.SQSTR().getText();
			queue.push(new StringExpression(text.substring(1, text.length() - 1)));
		} else if (ctx.BOOLEAN() != null) {
			queue.push(new BooleanExpression(Boolean.valueOf(ctx.BOOLEAN().getText())));
		} else if (ctx.NUMBER() != null) {
			queue.push(new NumberExpression(ctx.NUMBER().getText()));
		}
	}

	@Override
	public void exitPath(PathContext ctx) {
		List<TerminalNode> ids = ctx.ID();
		List<String> idStrs = new ArrayList<>();
		ids.forEach(id -> idStrs.add(id.getText()));
		queue.push(new PathExpression(idStrs.toArray(new String[idStrs.size()])));
	}

	private Expression findOperations(Map<String, Class<? extends Expression>> operations, String text) {
		Class<? extends Expression> functionCls = operations.get(text);
		if (functionCls == null) {
			throw new RuntimeException("Invalid operation/function: " + text);
		}
		try {
			return functionCls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Unable to create instance for class: " + functionCls, e);
		}
	}

}
