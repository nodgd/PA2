//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short COMPLEX=262;
public final static short PRINY_COMP=263;
public final static short NULL=264;
public final static short EXTENDS=265;
public final static short THIS=266;
public final static short WHILE=267;
public final static short FOR=268;
public final static short IF=269;
public final static short ELSE=270;
public final static short RETURN=271;
public final static short BREAK=272;
public final static short NEW=273;
public final static short PRINT=274;
public final static short READ_INTEGER=275;
public final static short READ_LINE=276;
public final static short LITERAL=277;
public final static short IDENTIFIER=278;
public final static short AND=279;
public final static short OR=280;
public final static short STATIC=281;
public final static short INSTANCEOF=282;
public final static short LESS_EQUAL=283;
public final static short GREATER_EQUAL=284;
public final static short EQUAL=285;
public final static short NOT_EQUAL=286;
public final static short CASE=287;
public final static short DEFAULT=288;
public final static short SUPER=289;
public final static short DCOPY=290;
public final static short SCOPY=291;
public final static short DO=292;
public final static short OD=293;
public final static short III=294;
public final static short UMINUS=295;
public final static short EMPTY=296;
public final static short PRINT_COMP=297;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   22,   23,
   23,   24,   14,   14,   14,   28,   28,   26,   26,   27,
   30,   32,   33,   33,   34,   35,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   31,
   31,   29,   29,   36,   36,   16,   17,   21,   15,   37,
   37,   18,   18,   19,   20,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    2,    1,    1,    3,    3,
    1,    3,    3,    1,    0,    2,    0,    2,    4,    5,
    4,    4,    2,    0,    2,    7,    1,    1,    1,    1,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    2,    2,    2,    2,    3,
    3,    4,    4,    1,    1,    4,    5,    6,    5,    1,
    1,    1,    0,    3,    1,    5,    9,    1,    6,    2,
    0,    2,    1,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,   10,    0,    9,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   91,   84,    0,    0,
    0,    0,   98,    0,    0,    0,    0,   90,    0,    0,
   85,    0,    0,    0,    0,    0,    0,   25,    0,    0,
    0,    0,   28,   37,   26,    0,   30,   31,   32,    0,
    0,    0,    0,   38,    0,    0,    0,    0,   59,   60,
    0,    0,    0,    0,   57,   58,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   41,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   29,   33,   34,   35,
   36,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   46,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   80,   81,    0,    0,    0,
    0,   39,    0,    0,    0,   74,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   86,    0,    0,  104,
    0,    0,   82,   83,   40,   42,    0,  105,   49,    0,
    0,   96,    0,    0,   87,    0,    0,    0,   89,   50,
    0,    0,   99,   88,    0,    0,    0,    0,    0,  100,
   53,    0,    0,   55,   56,    0,    0,    0,   97,   51,
    0,   52,
};
final static short yydgoto[] = {                          2,
    3,    4,   73,   21,   34,    8,   11,   23,   35,   36,
   74,   46,   75,   76,   77,   78,   79,   80,   81,   82,
   83,   84,  106,  107,   85,   95,   96,   88,  190,  205,
   89,  214,  207,  208,   90,  145,  203,
};
final static short yysindex[] = {                      -234,
 -229,    0, -234,    0, -203,    0, -209,  -47,    0,    0,
  452,    0,    0,    0,    0, -191,    0,  156,    0,    0,
   32,  -85,    0,    0,  -83,    0,   60,   13,   80,  156,
    0,  156,    0,  -74,   70,   79,   83,    0,   -1,  156,
   -1,    0,    0,    0,    0,    8,    0,    0,   85,   87,
   99,  121,    0,  294,  100,  102,  103,    0,  104,  109,
    0,  111,  115,  121,  121,  121,   81,    0,  121,  121,
  121,  118,    0,    0,    0,  108,    0,    0,    0,  110,
  112,  123,  124,    0,  843,  113,    0, -118,    0,    0,
  121,  121,  121,  843,    0,    0,  122,   72,  121,  129,
  139,  121,  121,  121,  121, -198,    0,  503,  -37,  -37,
  -94,  533,  843,  843,  843,  121,    0,    0,    0,    0,
    0,  121,  121,  121,  121,  121,  121,  121,  121,  121,
  121,  121,  121,  121,    0,  121,  121,  146,  544,  130,
  555,  147,  101,  843,   26,    0,    0,  566,  613,  637,
  734,    0,  121,   49,  150,    0,   30,   18,  964,   56,
   56,  -32,  -32,  131,  131,  -37,  -37,  -37,   56,   56,
  811,  843,  121,   49,  121,   49,    0,  822,  121,    0,
  -75,   82,    0,    0,    0,    0,  121,    0,    0,  167,
  165,    0,  882,  -60,    0,  843,  170, -227,    0,    0,
  121,   49,    0,    0, -227,  154,  -70,  105,  178,    0,
    0,  121,  174,    0,    0,   49,  893,  121,    0,    0,
  917,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  237,    0,  127,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  188,    0,    0,  212,
    0,  212,    0,    0,    0,  222,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -55,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -7,   -7,   -7,   -7,    0,   -7,   -7,
   -7,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  943,  482,    0,    0,    0,
   -7,  -58,   -7,  219,    0,    0,    0,    0,   -7,    0,
    0,   -7,   -7,   -7,   -7,    0,    0,    0,  384,  408,
    0,    0,  -20,  -12,   94,   -7,    0,    0,    0,    0,
    0,   -7,   -7,   -7,   -7,   -7,   -7,   -7,   -7,   -7,
   -7,   -7,   -7,   -7,    0,   -7,   -7,  155,    0,    0,
    0,    0,   -7,   42,    0,    0,    0,    0,    0,    0,
    0,    0,   -7,  -58,    0,    0,    0,   71,  -25,  907,
 1016,  361,  365,  984,  992,  437,  446,  473, 1020, 1024,
    0,  -19,  -16,  -58,   -7,  -58,    0,    0,   -7,    0,
    0,    0,    0,    0,    0,    0,   -7,    0,    0,    0,
  245,    0,    0,  -33,    0,   63,    0,    1,    0,    0,
   -5,  -58,    0,    0,    1,    0,    0,    0,    0,    0,
    0,   -7,    0,    0,    0,  -58,    0,   -7,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,  284,  277,   78,   34,    0,    0,    0,  259,    0,
   17,    0,  -97,  -69,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  140, 1221,  -26,    5,    0,    0,    0,
 -163,    0,   89,    0,    0,  -98,    0,
};
final static int YYTABLESIZE=1439;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        101,
   45,  101,  101,  103,  132,   28,  101,   28,  135,  130,
  128,  101,  129,  135,  131,   73,   28,  157,   73,   86,
   77,   43,  140,   77,   93,  101,    1,  134,   78,  133,
  101,   78,   73,   73,  206,   45,   47,   77,   77,   43,
   66,  206,   71,   70,   22,   78,   78,   67,    5,   58,
   87,   25,   65,  136,  132,   43,  186,   45,  136,  130,
  128,    7,  129,  135,  131,   86,  180,   73,    9,  179,
  188,   69,   77,  179,  191,   10,  192,  134,  194,  133,
   78,   66,   95,   71,   70,   95,   24,   98,   67,  101,
   26,  101,  132,   65,  152,  153,   87,  130,  128,   30,
  129,  135,  131,   94,  210,   31,   94,   33,  136,   33,
   39,   72,   69,   66,   72,   71,   70,   44,  219,   32,
   67,   42,   40,   41,   91,   65,   92,   86,   72,   72,
   42,  209,   68,   66,   79,   71,   70,   79,   93,   99,
   67,  100,  101,  102,   69,   65,  136,   86,  103,   86,
  104,   79,   79,   66,  105,   71,   70,  116,   87,  138,
   67,  142,  143,   72,   69,   65,  117,  132,  118,  146,
  119,   42,  130,  137,   86,   86,  135,  131,   87,  147,
   87,  120,  121,  155,   69,  173,   79,  177,  175,   86,
  187,   48,   27,   31,   29,   48,   48,   48,   48,   48,
   48,   48,  197,   38,  198,   87,   87,  200,  179,  202,
  204,  212,   48,   48,   48,   48,   48,  213,  216,   47,
   87,  136,   47,  101,  101,  101,  101,  101,  101,  215,
  101,  218,  101,  101,  101,  101,    1,  101,  101,  101,
  101,  101,  101,  101,  101,   48,    5,   48,  101,   15,
  124,  125,   20,  101,   73,  101,  101,  101,  101,  101,
  101,   47,   19,  101,   12,   13,   14,   15,   16,   17,
   47,   47,   47,   48,   49,   50,   51,  102,   52,   53,
   54,   55,   56,   57,   58,   92,    6,   20,   54,   59,
   37,    0,  185,  211,   60,    0,   61,   62,   63,   64,
  124,  125,  126,  127,   72,   12,   13,   14,   15,   16,
   17,    0,   47,    0,   48,   49,   50,   51,    0,   52,
   53,   54,   55,   56,   57,   58,    0,    0,    0,    0,
   59,    0,    0,    0,    0,   60,    0,   61,   62,   63,
   64,  111,    0,    0,   47,   72,   48,    0,    0,   72,
   72,    0,    0,   54,    0,   56,   57,   58,    0,    0,
    0,    0,   59,    0,   47,    0,   48,   60,    0,   61,
   62,   63,    0,   54,    0,   56,   57,   58,    0,    0,
    0,    0,   59,    0,   47,    0,   48,   60,    0,   61,
   62,   63,    0,   54,    0,   56,   57,   58,    0,    0,
    0,   66,   59,    0,   66,   67,    0,   60,   67,   61,
   62,   63,   12,   13,   14,   15,   16,   17,   66,   66,
   75,    0,   67,   67,   75,   75,   75,   75,   75,    0,
   75,    0,    0,   48,   48,    0,    0,   48,   48,   48,
   48,   75,   75,   75,   76,   75,    0,    0,   76,   76,
   76,   76,   76,   66,   76,    0,    0,   67,    0,    0,
    0,    0,    0,    0,    0,   76,   76,   76,    0,   76,
    0,    0,    0,   63,    0,    0,   75,   63,   63,   63,
   63,   63,   64,   63,    0,    0,   64,   64,   64,   64,
   64,    0,   64,    0,   63,   63,   63,    0,   63,    0,
   76,    0,    0,   64,   64,   64,    0,   64,    0,   65,
    0,    0,    0,   65,   65,   65,   65,   65,   58,   65,
    0,    0,   44,   58,   58,    0,   58,   58,   58,   63,
   65,   65,   65,    0,   65,    0,    0,    0,   64,  132,
   44,   58,    0,   58,  130,  128,    0,  129,  135,  131,
   12,   13,   14,   15,   16,   17,    0,    0,    0,    0,
  154,    0,  134,    0,  133,   65,    0,    0,    0,  132,
    0,   97,   58,  156,  130,  128,   19,  129,  135,  131,
  132,    0,    0,    0,  174,  130,  128,    0,  129,  135,
  131,  132,  134,  136,  133,  176,  130,  128,    0,  129,
  135,  131,  132,  134,    0,  133,    0,  130,  128,  181,
  129,  135,  131,    0,  134,    0,  133,    0,    0,    0,
    0,    0,    0,  136,    0,  134,    0,  133,    0,    0,
    0,    0,    0,    0,  136,    0,    0,    0,    0,   66,
   66,    0,    0,   67,   67,  136,    0,    0,    0,  132,
    0,    0,    0,  182,  130,  128,  136,  129,  135,  131,
    0,    0,   75,   75,    0,    0,   75,   75,   75,   75,
    0,    0,  134,  132,  133,    0,    0,  183,  130,  128,
    0,  129,  135,  131,    0,    0,   76,   76,    0,    0,
   76,   76,   76,   76,    0,    0,  134,    0,  133,    0,
    0,    0,    0,  136,    0,    0,    0,    0,   12,   13,
   14,   15,   16,   17,    0,   63,   63,    0,    0,   63,
   63,   63,   63,    0,   64,   64,    0,  136,   64,   64,
   64,   64,   18,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   65,   65,    0,    0,   65,   65,   65,   65,    0,
   58,   58,    0,    0,   58,   58,   58,   58,    0,    0,
  132,    0,    0,    0,  184,  130,  128,    0,  129,  135,
  131,  122,  123,    0,    0,  124,  125,  126,  127,    0,
    0,    0,    0,  134,    0,  133,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  122,  123,    0,    0,  124,  125,  126,  127,    0,
    0,    0,  122,  123,  136,    0,  124,  125,  126,  127,
    0,    0,    0,  122,  123,    0,    0,  124,  125,  126,
  127,    0,    0,    0,  122,  123,    0,  132,  124,  125,
  126,  127,  130,  128,    0,  129,  135,  131,  132,    0,
    0,    0,    0,  130,  128,    0,  129,  135,  131,    0,
  134,    0,  133,    0,    0,    0,    0,    0,    0,  132,
    0,  134,    0,  133,  130,  128,    0,  129,  135,  131,
    0,  122,  123,    0,    0,  124,  125,  126,  127,    0,
    0,  136,  134,  189,  133,    0,    0,    0,    0,    0,
    0,    0,  136,    0,  195,  122,  123,    0,  132,  124,
  125,  126,  127,  130,  128,    0,  129,  135,  131,  132,
    0,    0,    0,  136,  130,  128,    0,  129,  135,  131,
  201,  134,    0,  133,    0,    0,    0,   70,    0,    0,
   70,  220,  134,  132,  133,    0,    0,    0,  130,  128,
    0,  129,  135,  131,   70,   70,    0,    0,    0,    0,
    0,    0,  136,    0,    0,  222,  134,    0,  133,   57,
    0,    0,    0,  136,   57,   57,    0,   57,   57,   57,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   70,
  132,    0,   57,    0,   57,  130,  128,  136,  129,  135,
  131,    0,  122,  123,    0,    0,  124,  125,  126,  127,
    0,    0,    0,  134,   61,  133,   61,   61,   61,    0,
    0,    0,   62,   57,   62,   62,   62,    0,    0,    0,
    0,   61,   61,   61,    0,   61,    0,    0,    0,   62,
   62,   62,    0,   62,  136,    0,   71,    0,    0,   71,
   69,    0,    0,   69,   68,    0,    0,   68,    0,    0,
    0,    0,    0,   71,   71,    0,   61,   69,   69,    0,
    0,   68,   68,    0,   62,    0,    0,    0,    0,  122,
  123,    0,    0,  124,  125,  126,  127,    0,    0,    0,
  122,  123,    0,    0,  124,  125,  126,  127,   71,    0,
    0,    0,   69,    0,    0,    0,   68,    0,    0,    0,
    0,  122,  123,    0,    0,  124,  125,  126,  127,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  122,  123,    0,    0,  124,  125,  126,  127,    0,    0,
    0,  122,  123,    0,    0,  124,  125,  126,  127,    0,
    0,    0,    0,    0,    0,   70,   70,    0,    0,    0,
    0,   70,   70,    0,    0,  122,  123,    0,    0,  124,
  125,  126,  127,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   57,   57,    0,    0,   57,   57,   57,   57,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  122,    0,    0,    0,  124,  125,  126,  127,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   61,   61,    0,    0,   61,   61,   61,   61,
   62,   62,   94,    0,   62,   62,   62,   62,    0,    0,
    0,    0,    0,    0,  108,  109,  110,  112,    0,  113,
  114,  115,    0,    0,   71,   71,    0,    0,   69,   69,
   71,   71,   68,   68,   69,   69,    0,    0,   68,   68,
    0,  139,    0,  141,    0,    0,    0,    0,    0,  144,
    0,    0,  148,  149,  150,  151,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  144,    0,    0,    0,
    0,    0,  158,  159,  160,  161,  162,  163,  164,  165,
  166,  167,  168,  169,  170,    0,  171,  172,    0,    0,
    0,    0,    0,  178,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  108,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  144,    0,  193,    0,    0,    0,  196,
    0,    0,    0,    0,    0,    0,    0,  199,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  217,    0,    0,    0,    0,    0,  221,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   35,   36,   59,   37,   91,   40,   91,   46,   42,
   43,   45,   45,   46,   47,   41,   91,  116,   44,   46,
   41,   41,   92,   44,   41,   59,  261,   60,   41,   62,
   64,   44,   58,   59,  198,   41,  264,   58,   59,   59,
   33,  205,   35,   36,   11,   58,   59,   40,  278,  277,
   46,   18,   45,   91,   37,   39,  154,   41,   91,   42,
   43,  265,   45,   46,   47,   92,   41,   93,  278,   44,
   41,   64,   93,   44,  173,  123,  174,   60,  176,   62,
   93,   33,   41,   35,   36,   44,  278,   54,   40,  123,
   59,  125,   37,   45,  293,  294,   92,   42,   43,   40,
   45,   46,   47,   41,  202,   93,   44,   30,   91,   32,
   41,   41,   64,   33,   44,   35,   36,   40,  216,   40,
   40,  123,   44,   41,   40,   45,   40,  154,   58,   59,
  123,  201,  125,   33,   41,   35,   36,   44,   40,   40,
   40,   40,   40,   40,   64,   45,   91,  174,   40,  176,
   40,   58,   59,   33,   40,   35,   36,   40,  154,  278,
   40,   40,   91,   93,   64,   45,   59,   37,   59,   41,
   59,  123,   42,   61,  201,  202,   46,   47,  174,   41,
  176,   59,   59,  278,   64,   40,   93,   41,   59,  216,
   41,   37,  278,   93,  278,   41,   42,   43,   44,   45,
   46,   47,  278,  278,  123,  201,  202,   41,   44,  270,
   41,   58,   58,   59,   60,   61,   62,  288,   41,  278,
  216,   91,  278,  257,  258,  259,  260,  261,  262,  125,
  264,   58,  266,  267,  268,  269,    0,  271,  272,  273,
  274,  275,  276,  277,  278,   91,   59,   93,  282,  123,
  283,  284,   41,  287,  280,  289,  290,  291,  292,  293,
  294,  278,   41,  297,  257,  258,  259,  260,  261,  262,
  278,  264,  278,  266,  267,  268,  269,   59,  271,  272,
  273,  274,  275,  276,  277,   41,    3,   11,  288,  282,
   32,   -1,  153,  205,  287,   -1,  289,  290,  291,  292,
  283,  284,  285,  286,  297,  257,  258,  259,  260,  261,
  262,   -1,  264,   -1,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,  275,  276,  277,   -1,   -1,   -1,   -1,
  282,   -1,   -1,   -1,   -1,  287,   -1,  289,  290,  291,
  292,  261,   -1,   -1,  264,  297,  266,   -1,   -1,  279,
  280,   -1,   -1,  273,   -1,  275,  276,  277,   -1,   -1,
   -1,   -1,  282,   -1,  264,   -1,  266,  287,   -1,  289,
  290,  291,   -1,  273,   -1,  275,  276,  277,   -1,   -1,
   -1,   -1,  282,   -1,  264,   -1,  266,  287,   -1,  289,
  290,  291,   -1,  273,   -1,  275,  276,  277,   -1,   -1,
   -1,   41,  282,   -1,   44,   41,   -1,  287,   44,  289,
  290,  291,  257,  258,  259,  260,  261,  262,   58,   59,
   37,   -1,   58,   59,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,  279,  280,   -1,   -1,  283,  284,  285,
  286,   58,   59,   60,   37,   62,   -1,   -1,   41,   42,
   43,   44,   45,   93,   47,   -1,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   58,   59,   60,   -1,   62,
   -1,   -1,   -1,   37,   -1,   -1,   93,   41,   42,   43,
   44,   45,   37,   47,   -1,   -1,   41,   42,   43,   44,
   45,   -1,   47,   -1,   58,   59,   60,   -1,   62,   -1,
   93,   -1,   -1,   58,   59,   60,   -1,   62,   -1,   37,
   -1,   -1,   -1,   41,   42,   43,   44,   45,   37,   47,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   93,
   58,   59,   60,   -1,   62,   -1,   -1,   -1,   93,   37,
   59,   60,   -1,   62,   42,   43,   -1,   45,   46,   47,
  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,   -1,
   58,   -1,   60,   -1,   62,   93,   -1,   -1,   -1,   37,
   -1,  278,   91,   41,   42,   43,  125,   45,   46,   47,
   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,   46,
   47,   37,   60,   91,   62,   41,   42,   43,   -1,   45,
   46,   47,   37,   60,   -1,   62,   -1,   42,   43,   44,
   45,   46,   47,   -1,   60,   -1,   62,   -1,   -1,   -1,
   -1,   -1,   -1,   91,   -1,   60,   -1,   62,   -1,   -1,
   -1,   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,  279,
  280,   -1,   -1,  279,  280,   91,   -1,   -1,   -1,   37,
   -1,   -1,   -1,   41,   42,   43,   91,   45,   46,   47,
   -1,   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,
   -1,   -1,   60,   37,   62,   -1,   -1,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,  279,  280,   -1,   -1,
  283,  284,  285,  286,   -1,   -1,   60,   -1,   62,   -1,
   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,   -1,  279,  280,   -1,   -1,  283,
  284,  285,  286,   -1,  279,  280,   -1,   91,  283,  284,
  285,  286,  281,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,   -1,
  279,  280,   -1,   -1,  283,  284,  285,  286,   -1,   -1,
   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,   46,
   47,  279,  280,   -1,   -1,  283,  284,  285,  286,   -1,
   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,   -1,
   -1,   -1,  279,  280,   91,   -1,  283,  284,  285,  286,
   -1,   -1,   -1,  279,  280,   -1,   -1,  283,  284,  285,
  286,   -1,   -1,   -1,  279,  280,   -1,   37,  283,  284,
  285,  286,   42,   43,   -1,   45,   46,   47,   37,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   37,
   -1,   60,   -1,   62,   42,   43,   -1,   45,   46,   47,
   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,   -1,
   -1,   91,   60,   93,   62,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   91,   -1,   93,  279,  280,   -1,   37,  283,
  284,  285,  286,   42,   43,   -1,   45,   46,   47,   37,
   -1,   -1,   -1,   91,   42,   43,   -1,   45,   46,   47,
   59,   60,   -1,   62,   -1,   -1,   -1,   41,   -1,   -1,
   44,   59,   60,   37,   62,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   58,   59,   -1,   -1,   -1,   -1,
   -1,   -1,   91,   -1,   -1,   59,   60,   -1,   62,   37,
   -1,   -1,   -1,   91,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,
   37,   -1,   60,   -1,   62,   42,   43,   91,   45,   46,
   47,   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,
   -1,   -1,   -1,   60,   41,   62,   43,   44,   45,   -1,
   -1,   -1,   41,   91,   43,   44,   45,   -1,   -1,   -1,
   -1,   58,   59,   60,   -1,   62,   -1,   -1,   -1,   58,
   59,   60,   -1,   62,   91,   -1,   41,   -1,   -1,   44,
   41,   -1,   -1,   44,   41,   -1,   -1,   44,   -1,   -1,
   -1,   -1,   -1,   58,   59,   -1,   93,   58,   59,   -1,
   -1,   58,   59,   -1,   93,   -1,   -1,   -1,   -1,  279,
  280,   -1,   -1,  283,  284,  285,  286,   -1,   -1,   -1,
  279,  280,   -1,   -1,  283,  284,  285,  286,   93,   -1,
   -1,   -1,   93,   -1,   -1,   -1,   93,   -1,   -1,   -1,
   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  279,  280,   -1,   -1,  283,  284,  285,  286,   -1,   -1,
   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,   -1,
   -1,   -1,   -1,   -1,   -1,  279,  280,   -1,   -1,   -1,
   -1,  285,  286,   -1,   -1,  279,  280,   -1,   -1,  283,
  284,  285,  286,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  279,   -1,   -1,   -1,  283,  284,  285,  286,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,
  279,  280,   52,   -1,  283,  284,  285,  286,   -1,   -1,
   -1,   -1,   -1,   -1,   64,   65,   66,   67,   -1,   69,
   70,   71,   -1,   -1,  279,  280,   -1,   -1,  279,  280,
  285,  286,  279,  280,  285,  286,   -1,   -1,  285,  286,
   -1,   91,   -1,   93,   -1,   -1,   -1,   -1,   -1,   99,
   -1,   -1,  102,  103,  104,  105,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  116,   -1,   -1,   -1,
   -1,   -1,  122,  123,  124,  125,  126,  127,  128,  129,
  130,  131,  132,  133,  134,   -1,  136,  137,   -1,   -1,
   -1,   -1,   -1,  143,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  153,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  173,   -1,  175,   -1,   -1,   -1,  179,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  187,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  212,   -1,   -1,   -1,   -1,   -1,  218,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=297;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,"'#'","'$'","'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,"'@'",null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","COMPLEX","PRINY_COMP","NULL","EXTENDS","THIS","WHILE","FOR","IF",
"ELSE","RETURN","BREAK","NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL",
"IDENTIFIER","AND","OR","STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL",
"EQUAL","NOT_EQUAL","CASE","DEFAULT","SUPER","DCOPY","SCOPY","DO","OD","III",
"UMINUS","EMPTY","PRINT_COMP",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : COMPLEX",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : PrintCompStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"Stmt : DoStmt",
"DoStmt : DO DoBranchList OD",
"DoBranchList : DoBranchList III DoBranch",
"DoBranchList : DoBranch",
"DoBranch : Expr ':' Stmt",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"OneCaseExpr : Constant ':' Expr ';'",
"DefCaseExpr : DEFAULT ':' Expr ';'",
"ConstCaseList : OneCaseExpr ConstCaseList",
"ConstCaseList :",
"AllCaseList : ConstCaseList DefCaseExpr",
"CondExpr : CASE '(' Expr ')' '{' AllCaseList '}'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : CondExpr",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : '@' Expr",
"Expr : '$' Expr",
"Expr : '#' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : DCOPY '(' Expr ')'",
"Expr : SCOPY '(' Expr ')'",
"Expr : THIS",
"Expr : SUPER",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"PrintCompStmt : PRINT_COMP '(' ExprList ')'",
};

//#line 525 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 718 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 56 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 62 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 66 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 76 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 82 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 86 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 90 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 94 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
                	}
break;
case 10:
//#line 98 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 11:
//#line 102 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 106 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 112 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 118 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 122 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 128 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 132 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 136 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 144 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 151 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 155 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 162 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 166 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 172 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 178 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 182 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 189 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 194 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 39:
//#line 211 "Parser.y"
{
						yyval.stmt = new Tree.DoStmt(val_peek(1).elist, val_peek(1).loc);
					}
break;
case 40:
//#line 217 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 41:
//#line 221 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Expr> ();
                		yyval.elist.add(val_peek(0).expr); 
                	}
break;
case 42:
//#line 229 "Parser.y"
{
						yyval.expr = new Tree.DoBranch(val_peek(2).expr, val_peek(0).stmt, val_peek(2).loc);
					}
break;
case 43:
//#line 235 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 44:
//#line 239 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 45:
//#line 243 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 47:
//#line 250 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 48:
//#line 256 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 49:
//#line 263 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 50:
//#line 269 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 51:
//#line 278 "Parser.y"
{
						yyval.expr = new Tree.CaseExpr(val_peek(3).expr, val_peek(1).expr, val_peek(1).loc);
					}
break;
case 52:
//#line 284 "Parser.y"
{
						yyval.expr = new Tree.CaseExpr(val_peek(1).expr, val_peek(3).loc);
					}
break;
case 53:
//#line 290 "Parser.y"
{
						yyval.elist = val_peek(0).elist;
						yyval.elist.add(0, val_peek(1).expr);
					}
break;
case 54:
//#line 295 "Parser.y"
{
						yyval = new SemValue();
                		yyval.elist = new ArrayList<Expr>();
					}
break;
case 55:
//#line 302 "Parser.y"
{
						yyval.elist = val_peek(1).elist;
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 56:
//#line 309 "Parser.y"
{
                		yyval.expr = new Tree.CondExpr(val_peek(4).expr, val_peek(1).elist, val_peek(-1).loc);
                	}
break;
case 57:
//#line 315 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 61:
//#line 322 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 326 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 330 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 334 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 338 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 342 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 346 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 350 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 354 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 358 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 362 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 366 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 370 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 74:
//#line 374 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 75:
//#line 378 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 76:
//#line 382 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 77:
//#line 386 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.RE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 78:
//#line 390 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.IM, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 79:
//#line 394 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.COMPCAST, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 80:
//#line 398 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 81:
//#line 402 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 82:
//#line 406 "Parser.y"
{
                		yyval.expr = new Tree.DCopyExpr(val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 83:
//#line 410 "Parser.y"
{
                		yyval.expr = new Tree.SCopyExpr(val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 84:
//#line 414 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 85:
//#line 418 "Parser.y"
{
                		yyval.expr = new Tree.SuperExpr(val_peek(0).loc);
                	}
break;
case 86:
//#line 422 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 87:
//#line 426 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 88:
//#line 430 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 89:
//#line 434 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 90:
//#line 440 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 91:
//#line 444 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 93:
//#line 451 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 94:
//#line 458 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 95:
//#line 462 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 96:
//#line 469 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 97:
//#line 475 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 98:
//#line 481 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 99:
//#line 487 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 100:
//#line 493 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 101:
//#line 497 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 102:
//#line 503 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 103:
//#line 507 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 104:
//#line 513 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 105:
//#line 519 "Parser.y"
{
						yyval.stmt = new PrintComp(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1418 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
