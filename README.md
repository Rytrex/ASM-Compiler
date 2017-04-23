##Compiler
This compiler takes in a Mini Pascal file and outputs a MIPS Assembly file.
It converts the Mini Pascal code into MIPS Assembly. We used JFlex to create
our scanner and a lookup table to create the TokenTypes for each word passed
into the program. The parser uses recursion to match all of the proper
TokenTypes to each word passed in.

##User Manual
To use this compiler, download the `Compiler.jar`
file from the repository. Then in Terminal/Command Prompt, naviagate to the
location of the `Compiler.jar`
file and run `java -jar Compiler.jar [pascal_file]`

#### Error List
`Error No File`: The file you have provided is either missing or not a valid file

`Error Scan error`: The first word in your Pascal code is not a valid word. The
first word should always be **program**

`Error type, failed to add`: You may have declared the same variable more than once.
Check your code!

`Error standard_type`: Assigning variables requires the word **INTEGER** or
**REAL**. Make sure you have that following your variables.

`Error subprogram_head`: We expected a **FUNCTION** or **PROCEDURE** in your code.

`Error factor`: Your Pascal Code is incorrect. Check your code around Variables,
Left Square Brackets, Left Parenthesis, Numbers, and not for coding mistakes

`Error sign`: We expected a **PLUS** or **MINUS** somewhere in the code.

`Error Scanner exception`: The scanner could not identify a word in your Pascal code.

`Error Match of [word_1] found [word_2] instead`: Your Pascal code had [word_1] somewhere,
but was expecting [word_2]

