// define a grammar called Hello
grammar Expression;
@header {
package com.nagaraju.data.shape.antlr;
import java.util.HashMap;
}

@members {
/** Map variable name to Integer object holding value */
HashMap memory = new HashMap();
}

prog:   expr ;

expr:  	operend (OPERATOR operend)* ;

operend
	:	'(' expr ')'
	|	literal
	|	path
	|   invocation
	;

invocation:	'$' ID ('(' (expr (',' expr)*)? ')')? ;

path: ID ('.' ID)*;

literal
	:	NUMBER
	|	SQSTR
	|	BOOLEAN
	;

OPERATOR
	:	'+'
	|	'-'
	|	'/'
	|	'*'
	|	'<'
	|	'>'
	|	'<='
	|	'>='
	|	'&'
	|	'|'
	|	'='
	;

BOOLEAN: 'true' | 'false' ;

NUMBER: NEW_DIGIT ('.' NEW_DIGIT)? ;

SQSTR : SQUOTE (~['] | '\\\'')* SQUOTE;

fragment SQUOTE: '\'';

NEW_DIGIT: [0-9]+;

ID: [a-zA-Z0-9_]+;

WS  :   (' '|'\t')+ {skip();} ;
