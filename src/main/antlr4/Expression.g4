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

expr:  	operend (operator operend)? ;

operend
	:	'(' expr ')'
	|	literal
	|   invocation
	;

invocation:	function ('(' (expr (',' expr)*)? ')')? ;

function
	:	'$element'
	|	'$size'
	;

literal
	:	numberLit
	|	stringLit
	|	booleanLit
	;

operator
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

booleanLit: 'true' | 'false' ;

numberLit: NEW_DIGIT ('.' NEW_DIGIT)? ;

stringLit: '\'' STRING_CHAR* '\'';

NEW_DIGIT: [0-9]+;
STRING_CHAR : ~[;\r\n"'];
WS  :   (' '|'\t')+ {skip();} ;