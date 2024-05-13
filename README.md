# Project LFT
Formal Languages and Translators Project, UniTO Department of Computer Science, Year 2022 - 2023

## Exercise 1 - DFA
### 1.1 - Three Zeroes
The purpose of this exercise is the implementation of a Java method capable of discriminating strings from the language recognized by a deterministic finite automaton (DFA). The first automaton we consider is defined over the alphabet {0, 1} and recognizes strings containing at least three consecutive zeros.

The automaton is implemented in the `scan` method, which accepts a string `s` and returns a boolean value indicating whether the string belongs to the language recognized by the automaton. The state of the automaton is represented by an integer variable `state`, while the variable `i` holds the index of the next character in the string `s` to be analyzed. The main body of the method is a loop that, by examining the content of the string `s` one character at a time, changes the state of the automaton according to its transition function. Note that the implementation assigns the value `-1` to the `state` variable if a symbol other than `0` and `1` is encountered. This value is not a valid state but represents an unrecoverable error condition.

### 1.2 - Not Three Zeroes
The automaton for this exercise is complementary to the automaton of exercise 1.1, allowing discrimination of all strings `s` containing three consecutive zeros. In this case, the final state of the automaton is state `0`, unlike the previous exercise where the final state was state `3`.

### 1.3 - Identifiers
This automaton recognizes the language of identifiers in a Java-like language: an identifier is a non-empty sequence of letters, numbers, and the underscore symbol `_` that does not start with a number and cannot consist solely of the `_` symbol. Compile and test its functionality on a significant set of examples. \
_Examples of accepted strings:_ "`x`", "`flag1`", "`x2y2`", "`x_1`", "`lft_lab`", "`_temp`", "`x_1_y_2`", "`x___`", "`__5`" \
_Examples of rejected strings:_ "`5`", "`221B`", "`123`", "`9_to_5`", "` `"

### 1.4 - Student ID Number and Surname
This automaton recognizes the language of strings containing a student ID number followed (immediately) by a surname, where the combination of ID number and surname corresponds to students from either group 2 or group 3 of the Formal Languages and Translators laboratory. Recall the rules for dividing students into groups: \
• Group T1: Surnames whose initial letter falls between A and K, and the ID number is odd; \
• Group T2: Surnames whose initial letter falls between A and K, and the ID number is even; \
• Group T3: Surnames whose initial letter falls between L and Z, and the ID number is odd; \
• Group T4: Surnames whose initial letter falls between L and Z, and the ID number is even. \
For example, "`123456Bianchi`" and "`654321Rossi`" are strings in the language, while "`654321Bianchi`" and "`123456Rossi`" are not. In the context of this exercise, an ID number does not have a fixed number of digits (but must consist of at least one digit). A surname corresponds to a sequence of letters and must consist of at least one letter. Therefore, the automaton should accept strings like "`2Bianchi`" and "`122B`" but not "`654322`" and "`Rossi`".

### 1.5 - Student ID Number and Surname (with Spaces)
The automaton recognizes combinations of ID numbers and surnames of students from group 2 or group 3 of the laboratory, where the ID number and surname can be separated by a sequence of spaces and can be preceded and/or followed by potentially empty sequences of spaces. For example, the automaton should accept the string "`654321 Rossi`" and "` 123456 Bianchi `" (where, in the second example, there are spaces before the first character and after the last character), but not "`1234 56Bianchi`" and "`123456Bia nchi`". For this exercise, compound surnames (with an arbitrary number of parts) can be accepted: for example, the string "`123456De Gasperi`" should be accepted.

### 1.6 - Surname and Student ID Number
This automaton, similar to Exercise 1.4, recognizes the language of strings containing the student ID number and surname from group 2 or group 3 of the laboratory, but where the surname precedes the ID number (in other words, the positions of the surname and ID number are reversed compared to Exercise 1.4).

### 1.7 - Three Ending 'A's
This automaton over the alphabet `{a, b}` recognizes the language of strings such that `a` occurs at least once in the last three positions of the string. The DFA should also accept strings containing fewer than three symbols (but at least one of the symbols must be `a`). \
_Examples of accepted strings:_ "`abb`", "`bbaba`", "`baaaaaaa`", "`aaaaaaa`", "`a`", "`ba`", "`bba`", "`aa`", "`bbbababab`". \
_Examples of rejected strings:_ "`abbbbbb`", "`bbabbbbbbbb`", "`b`".

### 1.8 - Name Variations
The automaton recognizes the language of strings containing your name (`Dario`, in this case) and all strings obtained after substituting one character of the name with any other character. For example, for a student named `Dario`, the DFA should accept the string "`Dario`" (the correctly spelled name), as well as strings like "`Mario`", "`Dadio`", "`D4rio`", "`Dari0`", and "`Dar*o`" (the name after substituting one character), but not "`Eva`", "`Luca`", "`Pietro`", or "`D*r*o`".

### 1.9 - Floating-Point Numbers
The automaton recognizes the language of floating-point numeric constants using scientific notation where the symbol `e` denotes exponential notation with base `10`. The DFA's alphabet includes the following elements: numeric digits `0,1,...,9`, the `.` (dot) symbol preceding an optional decimal part, the `+` (plus) and `-` (minus) signs to indicate positivity or negativity, and the `e` symbol. \
Accepted strings follow the standard rules for writing numeric constants. In particular, a numeric constant consists of two segments, the second of which is optional: the first segment is a sequence of numeric digits that (1) may be preceded by a `+` or `-` sign, (2) may be followed by a dot `.`, which in turn must be followed by a non-empty sequence of numeric digits; the second segment starts with the `e` symbol, which is followed

### 1.10 - Comments
The automaton recognizes the language of strings (over the alphabet `{/, *, a}`) that contain "comments" delimited by `/*` and `*/`, with the possibility of having strings before and after as specified below. The idea is that comments (including multiple) can be embedded within a sequence of alphabet symbols. Therefore, the only constraint is that the automaton must accept strings in which an occurrence of the sequence `/*` is followed (even if not immediately) by an occurrence of the sequence `*/`. Strings in the language may not have any occurrence of the sequence `/*` (in the case of sequences of symbols without comments).\
_Examples of accepted strings:_ "`aaa/****/aa`", "`aa/*a*a*/`", "`aaaa`", "`/****/`", "`/*aa*/`", "`*/a`", "`a/**/***a`", "`a/**/***/a`", "`a/**/aa/***/a`".\
_Examples of rejected strings:_ "`aaa/*/aa`", "`a/**//***a`", "`aa/*aa`".

## Exercise 2 - Lexer
The exercises in this section concern the implementation of a lexical analyzer for a simple programming language. The purpose of a lexical analyzer is to read text and obtain a corresponding sequence of tokens, where each token corresponds to a lexical unit, such as a number, an identifier, a relational operator, a keyword, etc. In the following sections, the implemented lexical analyzer will be used to provide input to syntactic analysis and translation programs.
The language tokens are described as illustrated in _Table 1_. The first column contains the various token categories, the second presents descriptions of possible token lexemes, while the third column describes the token names, expressed as numeric constants.
Identifiers correspond to the regular expression:

`(a+...+z+A+...+Z)(a+...+z+A+...+Z+0+...+9)∗`

and numbers correspond to the regular expression `0 + (1 + ... + 9)(0 + ... + 9)∗`.

| **Token**                 | **Pattern**                         | **Name** |
|---------------------------|-------------------------------------|----------|
| Numeri                    | Constant                            | 256      |
| Indentificatore           | Letter followed by letter or number | 257      |
| Relop                     | Relational Operator                 | 258      |
| Assegnamento              | `assign`                            | 259      |
| To                        | `to`                                | 260      |
| Conditional               | `conditional`                       | 261      |
| Option                    | `option`                            | 262      |
| Do                        | `do`                                | 263      |
| Else                      | `else`                              | 264      |
| While                     | `while`                             | 265      |
| Begin                     | `begin`                             | 266      |
| End                       | `end`                               | 267      |
| Print                     | `print`                             | 268      |
| Read                      | `read`                              | 269      |
| Disgiunzione              | `\|\|`                              | 270      |
| Congiunzione              | `&&`                                | 271      |
| Negazione                 | `!`                                 | 33       |
| Parentesi tonda sinistra  | `(`                                 | 40       |
| Parentesi tonda destra    | `)`                                 | 41       |
| Parentesi quadra sinistra | `[`                                 | 91       |
| Parentesi quadra destra   | `]`                                 | 93       |
| Parentesi graffa sinistra | `{`                                 | 123      |
| Parentesi graffa destra   | `}`                                 | 125      |
| Somma                     | `+`                                 | 43       |
| Sottrazione               | `-`                                 | 45       |
| Moltiplicazione           | `*`                                 | 42       |
| Divisione                 | `/`                                 | 47       |
| Punto e virgola           | `;`                                 | 59       |
| Virgola                   | `,`                                 | 44       |
| EOF                       | End of Input                        | -1       |



The lexical analyzer disregards all characters recognized as "spaces" (including tabs and line breaks) but signals the presence of illegal characters, such as `#` or `@`.

The output of the lexical analyzer takes the form `⟨token0⟩⟨token1⟩...⟨tokenn⟩`. For example:
- For the input `assign 300 to d;`, the output will be `⟨259, assign⟩ ⟨256, 300⟩ ⟨260, to⟩ ⟨257, d⟩ ⟨59⟩ ⟨−1⟩;`
- For the input `print(*{d t})`, the output will be `⟨268,print⟩ ⟨40⟩ ⟨42⟩ ⟨123⟩ ⟨257,d⟩ ⟨257,t⟩ ⟨125⟩ ⟨41⟩ ⟨−1⟩;`
- For the input `conditional option (> x y) assign 0 to x else print(y)`, the output will be `⟨261,conditional⟩ ⟨262,option⟩ ⟨40⟩ ⟨258,>⟩ ⟨257,x⟩ ⟨257,y⟩ ⟨41⟩ ⟨259,assign⟩ ⟨256, 0⟩ ⟨260, to⟩ ⟨257, x⟩ ⟨264, else⟩ ⟨268, print⟩ ⟨40⟩ ⟨257, y⟩ ⟨41⟩ ⟨−1⟩;`
- For the input `while (dog<=printread) assign dog+1 to dog`, the output will be `⟨265,while⟩ ⟨40⟩ ⟨257,dog⟩ ⟨258,<=⟩ ⟨257,printread⟩ ⟨41⟩ ⟨259,assign⟩ ⟨257,dog⟩ ⟨43⟩ ⟨256, 1⟩ ⟨260, to⟩ ⟨257, dog⟩ ⟨−1⟩`.

In general, tokens from _Table 1_ have an attribute. For instance, the attribute of the token `⟨256, 300⟩` is the number 300, while the attribute of the token `⟨259, assign⟩` is the string `assign`. Note, however, that some tokens in _Table 1_ have no attribute. For example, the "times" sign (`*`) is represented by the token `⟨42⟩`, and the right parenthesis (`)`) is represented by the token `⟨41⟩`.

Note: The lexical analyzer is not designed to recognize the structure of language commands. Therefore, it accepts "incorrect" commands such as:
- `5+;)`
- `(34+26( - (2+15-( 27`
- `else 5 == print < end`

Other errors, such as unexpected symbols or illicit sequences (e.g., in the case of input `17&5` or `|||`), are detected.

### Supporting Classes
The following classes are used to implement the lexical analyzer:
- A `Tag` class is defined using the integer constants in the `Name` column of _Table 1_ to represent token names. For tokens corresponding to a single character (except `<` and `>`, which correspond to "Relop" or relational operators), the ASCII code of the character is used. For example, the name in _Table 1_ for the plus sign (`+`) is 43, which is the ASCII code for `+`.
- A `Token` class is defined to represent tokens.
### Handling Extended Identifiers

We defined a new form of identifiers: an identifier consists of a non-empty sequence of letters, numbers, and underscores (_). The sequence must not start with a number and cannot consist solely of the underscore symbol. Specifically, identifiers correspond to the regular expression:

```
(a+...+Z + (_(_)*(a+...+z+0...+9)))(a+...+z+0...+9+_)*
```

(where `a+...+Z` represents the regular expression `a+...+z + A+...+Z`). We extend the `lexical_scan` method to handle identifiers that match this new definition.

### Handling Comments

We enhanced the `lexical_scan` method to accommodate comments in the input file. Comments can be written in two ways:

- Comments delimited with `/*` and `*/`
- Comments starting with `//` and ending with a newline or EOF

The program ignore comments during lexical analysis, meaning that no token are generated for parts of the input that contain comments. For example, consider the following input:

```
/* calculate speed */
assign 300 to d; // distance
assign 10 to t; // time
print(* d t)
```

The output of the lexical analysis program is like this:

```
⟨259, assign⟩ ⟨256, 300⟩ ⟨260, to⟩ ⟨257, d⟩ ⟨59⟩ ⟨259, assign⟩ ⟨256, 10⟩ ⟨260, to⟩ ⟨257, t⟩ ⟨59⟩ ⟨268, print⟩ ⟨40⟩ ⟨42⟩ ⟨257, d⟩ ⟨257, t⟩ ⟨41⟩ ⟨−1⟩
```

Additionally, comments may contain symbols that do not correspond to any token pattern (e.g., `/*@#?*/` or `/* calculate speed */`). If a `/* ... */` comment is opened but not closed before the end of the file (e.g., `assign 300 to d /* distance`), it results in an error.

Consecutive comments not separated by any token are also supported, for example:

```
assign 300 to d /* distance *//* from Turin to Lyon */
```

Furthermore, if the `*/` symbol pair appears outside a comment, it is treated by the lexer as the multiplication sign followed by the division sign (e.g., for the input `x*/y`, the output will be `⟨257, x⟩ ⟨42⟩ ⟨47⟩ ⟨257, y⟩ ⟨−1⟩`). In this scenario, the `*/` sequence is interpreted as a combination of the two specified tokens.

## Exercise 3 - Syntax Analysis
### Ex 3.1 - Mathematical Parser

A recursive descent parser was implemented to parse simple arithmetic expressions written in infix notation. These expressions consist only of non-negative numbers (sequences of decimal digits), addition and subtraction operators (+ and -), multiplication and division operators (* and /), and parentheses symbols ( and ). The parser recognizes expressions generated by the grammar:

```
G_expr = ({⟨start⟩,⟨expr⟩,⟨exprp⟩,⟨term⟩,⟨termp⟩,⟨fact⟩}, {+, -, *, /, (, ), NUM, EOF}, P, ⟨start⟩)
```
where P is the following set of productions:
```
⟨start⟩   ::=     ⟨expr⟩ EOF

⟨expr⟩    ::=     ⟨term⟩ ⟨exprp⟩

⟨exprp⟩   ::=     + ⟨term⟩ ⟨exprp⟩
           |      - ⟨term⟩ ⟨exprp⟩
           |      ε

⟨term⟩    ::=     ⟨fact⟩ ⟨termp⟩

⟨termp⟩   ::=     * ⟨fact⟩ ⟨termp⟩
           |      / ⟨fact⟩ ⟨termp⟩
           |      ε

⟨fact⟩    ::=     ( ⟨expr⟩ )
           |      NUM
```

Note that `::=` is used instead of `→` to indicate a production, for example, ⟨start⟩ ::= ⟨expr⟩ EOF is a production with head ⟨start⟩ and body ⟨expr⟩ EOF.

The program utilizes the lexical analyzer developed previously. The set of tokens corresponding to the grammar in this section is a subset of the tokens set corresponding to the lexical rules from Section 2. When the input matches the grammar, the output consists of the token list of the input followed by a message indicating that the input conforms to the grammar. In cases where the input does not match the grammar, the output of the program consists of an error message (as illustrated in the classroom lectures) indicating the procedure in execution when the error was detected.

### Ex 3.2 - Grammar Productions for a Simple Programming Language

The productions of a grammar for a simple programming language are presented below. As in Exercise 3.1, variables are denoted with angle brackets (e.g., ⟨prog⟩, ⟨statlist⟩, ⟨statlistp⟩, etc.). The terminals of the grammar correspond to the tokens described in Section 2 (Table 1).

```
⟨prog⟩      ::= ⟨statlist⟩ EOF

⟨statlist⟩  ::= ⟨stat⟩ ⟨statlistp⟩

⟨statlistp⟩ ::= ; ⟨stat⟩ ⟨statlistp⟩
             | ε

⟨stat⟩      ::= assign ⟨expr⟩ to ⟨idlist⟩
             | print [ ⟨exprlist⟩ ]
             | read [ ⟨idlist⟩ ]
             | while ( ⟨bexpr⟩ ) ⟨stat⟩
             | conditional [ ⟨optlist⟩ ] end
             | conditional [ ⟨optlist⟩ ] else ⟨stat⟩ end
             | { ⟨statlist⟩ }

⟨idlist⟩    ::= ID ⟨idlistp⟩

⟨idlistp⟩   ::= , ID ⟨idlistp⟩
             | ε

⟨optlist⟩   ::= ⟨optitem⟩ ⟨optlistp⟩

⟨optlistp⟩  ::= ⟨optitem⟩ ⟨optlistp⟩
             | ε

⟨optitem⟩   ::= option ( ⟨bexpr⟩ ) do ⟨stat⟩

⟨bexpr⟩     ::= RELOP ⟨expr⟩ ⟨expr⟩

⟨expr⟩      ::= + ( ⟨exprlist⟩ )
             | - ⟨expr⟩ ⟨expr⟩
             | * ( ⟨exprlist⟩ )
             | / ⟨expr⟩ ⟨expr⟩
             | NUM
             | ID

⟨exprlist⟩  ::= ⟨expr⟩ ⟨exprlistp⟩

⟨exprlistp⟩ ::= , ⟨expr⟩ ⟨exprlistp⟩
             | ε
```
It should be noted that RELOP corresponds to an element of the set {==, <>, <=, >=, <, >}, NUM corresponds to a numeric constant, and ID corresponds to an identifier. Additionally, it's important to note that arithmetic expressions are written in prefix or Polish notation, unlike the infix (standard) notation used in the previous exercise. Similarly, Boolean expressions are written in prefix notation, following the convention of placing the relational operator to the left of the expressions. The grammar was modified to create an equivalent LL(1) grammar, and a recursive descent parser was written for the modified grammar.

## Exercise 4 - Direct Syntax Translation

The recursive descent parser from Exercise 3.1 was modified to evaluate simple arithmetic expressions based on the following direct syntax translation scheme:

```
⟨start⟩ ::= ⟨expr⟩ EOF { print(⟨expr.val⟩) }

⟨expr⟩  ::= ⟨term⟩ { ⟨exprp.i⟩ = ⟨term.val⟩ } ⟨exprp⟩ { ⟨expr.val⟩ = ⟨exprp.val⟩ }

⟨exprp⟩ ::= + ⟨term⟩ { ⟨exprp1.i⟩ = ⟨exprp.i⟩ + ⟨term.val⟩ } ⟨exprp1⟩ { ⟨exprp.val⟩ = ⟨exprp1.val⟩ }
         | - ⟨term⟩ { ⟨exprp1.i⟩ = ⟨exprp.i⟩ - ⟨term.val⟩ } ⟨exprp1⟩ { ⟨exprp.val⟩ = ⟨exprp1.val⟩ }
         | ε { ⟨exprp.val⟩ = ⟨exprp.i⟩ }

⟨term⟩  ::= ⟨fact⟩ { ⟨termp.i⟩ = ⟨fact.val⟩ } ⟨termp⟩ { ⟨term.val⟩ = ⟨termp.val⟩ }

⟨termp⟩ ::= * ⟨fact⟩ { ⟨termp1.i⟩ = ⟨termp.i⟩ * ⟨fact.val⟩ } ⟨termp1⟩ { ⟨termp.val⟩ = ⟨termp1.val⟩ }
         | / ⟨fact⟩ { ⟨termp1.i⟩ = ⟨termp.i⟩ / ⟨fact.val⟩ } ⟨termp1⟩ { ⟨termp.val⟩ = ⟨termp1.val⟩ }
         | ε { ⟨termp.val⟩ = ⟨termp.i⟩ }

⟨fact⟩  ::= ( ⟨expr⟩ ) { ⟨fact.val⟩ = ⟨expr.val⟩ }
         | NUM { ⟨fact.val⟩ = NUM.value }

```
It's important to note that the NUM terminal has a 'value' attribute, which is the numeric value of the terminal, provided by the lexical analyzer.

This modification enabled the parser not only to recognize the structure of the input expressions according to the defined grammar but also to compute their values as the parsing process progressed. This was achieved by integrating evaluation steps directly into the parsing procedures, effectively combining syntax analysis and interpretation into a single pass.

## Exercise 5 - Bytecode Generation

The final exercise involved creating a translator for programs written in the simple programming language, referred to as "P," as seen in Exercise 3.2. Programs in language P have the file extension `.lft`, as suggested in the theoretical lessons. The translator was designed to generate bytecode executable by the Java Virtual Machine (JVM).

Generating bytecode directly executable by the JVM is not straightforward due to the complexity of the `.class` file format (which is also a binary format). Therefore, bytecode was generated using a mnemonic language that refers to JVM instructions (assembler language) and was subsequently translated into the `.class` format by an assembler program. The mnemonic language used refers to the set of JVM instructions, and the assembler performed a 1-to-1 translation of mnemonic instructions into the corresponding JVM instruction (opcode). The assembler program used is called Jasmin, and its distribution and manual are available at [Jasmin's website](http://jasmin.sourceforge.net/).

The construction of the `.class` file from the source written in the P language followed this scheme:

`.lft` -- P compiler --> `.j` -- jasmin --> `.class`

The source file was translated by the compiler (the object of realization) into the JVM assembler language. This file (which must have the `.j` extension) was then transformed into a `.class` file by the Jasmin assembler program. In the code presented in this section, the file generated by the compiler was named `Output.j`, and the command `java -jar jasmin.jar Output.j` was used to convert it into the `Output.class` file, which could be executed with the command `java Output`.

### Meaning of Instructions in the Language P

The implemented commands in the simple programming language P are described as follows:

**assign ⟨expr⟩ to ⟨idlist⟩**
- Assigns the value of the expression ⟨expr⟩ to all identifiers in the list of identifiers ⟨idlist⟩. For example, the command `assign 3 to x, y` assigns the value 3 to both identifiers x and y.

**print [ ⟨exprlist⟩ ]**
- Prints the value of all expressions included in the list of expressions ⟨exprlist⟩ to the terminal. For example, `print[+(2,3), 4]` prints the value 5 followed by the value 4.

**read [ ⟨idlist⟩ ]**
- The command `read[ID1, ..., IDn]` allows the input of n non-negative integers from the keyboard; the ith number entered from the keyboard is assigned to the identifier IDi. For instance, the command `read[a, b]` specifies that the user of the program written in language P must input two non-negative integers via the keyboard, the first of which is assigned to the identifier a and the second to the identifier b.

**while ( ⟨bexpr⟩ ) ⟨stat⟩**
- Allows the cyclic execution of ⟨stat⟩. The condition for the execution of the loop is ⟨bexpr⟩. Note that ⟨stat⟩ can be a single statement or a sequence of statements enclosed in curly braces. For example, `while (> x 0) { assign - x 1 to x; print[x] }` decrements and prints the value of the identifier x on the terminal until x reaches the value 0.

**conditional [ option(⟨bexpr1⟩) do ⟨stat1⟩ · · · option(⟨bexprn⟩) do ⟨statn⟩ ] end**
- A conditional command: from the list of Boolean expressions ⟨bexpr1⟩ · · · ⟨bexprn⟩, if the first expression evaluated as true is ⟨bexprk⟩, then ⟨statk⟩ is executed, the conditional statement is exited, and the next instruction is processed. Note that each ⟨statk⟩ can be a single statement or a sequence of statements enclosed in curly braces. If no expression in the list ⟨bexpr1⟩ · · · ⟨bexprn⟩ is verified as true, the next instruction after the conditional statement is executed. For example, `conditional [ option(> x 0) do print[x, y] option(> y 0) do print[y] ] end` prints on the terminal the values of both x and y if the value of x is greater than 0, and only the value of y if the value of x is equal or lower than 0 and that of y is greater than 0.

**conditional [ option(⟨bexpr1⟩) do ⟨stat1⟩ · · · option(⟨bexprn⟩) do ⟨statn⟩ ] else ⟨stat⟩ end**
- Adds to the previous conditional command a case similar to Java's else or default: the ⟨stat⟩ written after the keyword else is executed in the case where no Boolean expression in the list ⟨bexpr1⟩ · · · ⟨bexprn⟩ is evaluated as true. More precisely, if the first Boolean expression evaluated as true is ⟨bexprk⟩, then ⟨statk⟩ is executed, the conditional instruction is exited, and the next instruction is processed; otherwise, if no expression in the list ⟨bexpr1⟩ · · · ⟨bexprn⟩ is verified, the ⟨stat⟩ written after the keyword else is executed, and then the next instruction is processed. For example, `conditional [ option(> x 0) do print[x, y] option(> y 0) do print[y] ] else print[z] end` prints on the terminal the values of both x and y if the value of x is greater than 0, only the value of y if the value of x is equal or lower than 0 and that of y is greater than 0, and only the value of z if the values of both x and y are equal or lower than 0.

**{ ⟨statlist⟩ }**
- Allows grouping a sequence of instructions. For example, a block of instructions that reads an input number and subsequently prints the incremented number by 1 on the terminal can be written as follows: `{read[x]; print[+(x,1)]}`.

Note the use of prefix notation for arithmetic expressions. Additionally, the operations of addition and multiplication can have a number n of arguments with n ≥ 1, while the operations of subtraction and division have exactly two arguments. For instance, the expression `*(2,3,4)` has the value 24, the expression `-*(2,4)3` has the value 5, the expression `+(2, -7 3)` has the value 6, the expression `

+(/ 10 2, 3)` has the value 8, and the expression `+(5, -7 3, 10)` has the value 19.

## Bibliographic References

Here are the bibliographic references as previously documented:

1. **Aho, Alfred V., Lam, Monica S., Sethi, Ravi, and Ullman, Jeffrey D.** "Compilatori: Principi, tecniche e strumenti." Pearson, 2019.
2. **Assembly language.** [Wikipedia, 2022](http://en.wikipedia.org/wiki/Assembly_language).
3. **Java class file.** [Wikipedia, 2022](http://en.wikipedia.org/wiki/Java_class_file).
4. **Java bytecode.** [Wikipedia, 2022](http://en.wikipedia.org/wiki/Java_bytecode).
5. **Java bytecode instruction listings.** [Wikipedia, 2022](http://en.wikipedia.org/wiki/Java_bytecode_instruction_listings).