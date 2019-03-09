// define a grammar called Hello
grammar Expression;
@header {
    package com.nagaraju.data.shape.antlr;
}
r   : 'hello' ID;
ID  : [a-z]+ ;
WS  : [ \t\r\n]+ -> skip ;