grammar Bash;

line
    : setVariable
    | commands
    ;

commands
    : WS* command (WS* PIPE WS* command)*
    ;

setVariable
    : var=WORD WS* EQ WS* val=WORD
    ;

command
    : commandName (WS* args += block)*
    ;

block
    : BLOCK | WORD
    | QUOTED_WORD
    | DQUOTED_WORD
    ;

commandName
    : WORD
    ;

WORD: [a-zA-Z0-9]+;
PIPE: '|';
QUOTES: '"' | '\'';
WS: '\n' | ' ';
DLR: '$';
EQ: '=';
BLOCK: ~['": \n|=]+;
DQUOTED_WORD: '"' ~('"')* '"';
QUOTED_WORD: '\'' ~('\'')* '\'';