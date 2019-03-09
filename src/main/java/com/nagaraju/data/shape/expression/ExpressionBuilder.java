package com.nagaraju.data.shape.expression;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.stereotype.Service;

import com.nagaraju.data.shape.antlr.ExpressionLexer;
import com.nagaraju.data.shape.antlr.ExpressionParser;

@Service
public class ExpressionBuilder {

	public Expression build(String exp) {
		CharStream charStream = new ANTLRInputStream(exp);
		ExpressionLexer lexer = new ExpressionLexer(charStream);
		TokenStream tokens = new CommonTokenStream(lexer);
		ExpressionParser parser = new ExpressionParser(tokens);
		ExpressionListenerImpl listener = new ExpressionListenerImpl();
		ParseTreeWalker.DEFAULT.walk(listener, parser.prog());
		return listener.getExpression();
	}
}
