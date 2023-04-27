package cz.upce.fei.boop.lexikalnianalyzator.statemachine;

import static cz.upce.fei.boop.lexikalnianalyzator.Counter.counter;
import cz.upce.fei.boop.lexikalnianalyzator.collection.TokenList;
import cz.upce.fei.boop.lexikalnianalyzator.token.*;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.KeyWord;
import static cz.upce.fei.boop.lexikalnianalyzator.token.enums.KeyWord.*;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.*;
import static cz.upce.fei.boop.lexikalnianalyzator.token.enums.SeparatorEnum.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * TODO Sestavit testovací případy třídy StateMachine s cílem dosáhnout co
 * největšího pokrytí.
 * <p>
 * Česky stručný popis JUnit 4 je na http://voho.eu/wiki/java-junit/ nebo
 * https://www.itnetwork.cz/java/testovani/testovani-v-jave-prvni-unit-test-v-junit
 * <p>
 * Třída {@code StateMachine} je součásti projektu Lexikální analyzátor, který
 * byl v minulých letech zadán jako první semestrální práce předmětu objektově
 * orientované programování. Pro vypracování testu a pochopení celého projektu
 * je k dispozici písemné zadání.
 *
 * <p>
 * Požadavky na testovací případy (metody):
 *
 * <ol>
 * <li>Doporučuje se volit název testovací metody podle typu kontroly s vhodným
 * číselným prefixem. Velmi se osvědčilo používat tvarové číslování. Například
 * dvouúrovňové, tj. test001_01, test002_20 apod. To umožňuje vkládat do
 * testovací třídy další testy, které patří do stejného typu kontroly. Číselná
 * identifikace umožňuje rychlejší orientaci v testovacích metodách.
 *
 * <li> Každá testovací metoda bude obsahovat na prvním řádku výpis názvu testu
 * tímto příkazem {@code print(testName.getMethodName()) }. Tím se vytiskne
 * pořadové číslo test a název metody.
 *
 * <li> Instaci třídy {@code StateMachine} nemusíme v testovací metodě vytvářet,
 * protože je automaticky vytvořena před každým spuštěním jakékoliv testovací
 * metody.
 *
 * <li>Instance testované třídy se automaticky vytváří metodou {@code setUp} s
 * anotací {code @Before}.
 *
 * <li>Úklid po vykonání testovací metody provede metoda {@code tearDown} s
 * anotací {code @After}.
 *
 * <li> Velmi se doporučuje aby byla pouze jedna kontrola v testovací metodě,
 * tj. jedna metoda {@code assert}.
 *
 *
 * <li>Pokud se kontroluje reálná číselná hodnota (float, double), tak je nutné
 * zvolit třetí parametr, kterým se volí přesnost porovnání. V našem případě
 * budeme používat přesnost, která dána konstantou {@code DELTA}.
 *
 *
 *
 * </ol>
 *
 *
 * @author
 */
public class StateMachineTest {

    @Rule
    public TestName testName = new TestName();

    static void print(String methodName) {
        System.out.println(String.format("%03d StateMachine.%s   ",
                counter++, methodName));
    }

    static final double DELTA = 1e-9;

    Machine machine;
    TokenList tokens;

    public StateMachineTest() {
    }

    @Before
    public void setUp() {
        tokens = new TokenList();
        machine = new StateMachine(tokens);
    }

    @After
    public void tearDown() {
        tokens = null;
        machine = null;
    }

    @Test
    public void test001_02_identifier() {
        print(testName.getMethodName());
        machine.execute("e\n");
        assertEquals(TokenType.IDENTIFIER, tokens.getToken(0).getType());
    }
 @Test
    public void test001_01_identifier() {
        print(testName.getMethodName());
        machine.execute("e\n");
        assertEquals(TokenType.IDENTIFIER, tokens.getToken(0).getType());
    }    
   @Test(expected = AnalyzerException.class)
    public void test001_03_identifier() {
        print(testName.getMethodName());
        machine.execute("_\n");
        assertEquals(TokenType.IDENTIFIER, tokens.getToken(0).getType());
    }
    @Test
    public void test01_01_Number() {
        print(testName.getMethodName());
        machine.execute("1.0\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test
    public void test01_02_Number() {
        print(testName.getMethodName());
        machine.execute("1E0\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test
    public void test01_03_Number() {
        print(testName.getMethodName());
        machine.execute("1\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test
    public void test02_01_operator() {
        print(testName.getMethodName());
        machine.execute("+:06\n");
        assertEquals(TokenType.OPERATOR, tokens.getToken(0).getType());
    }

    @Test
    public void test02_02_operator() {
        print(testName.getMethodName());
        machine.execute("-;0x\n");
        assertEquals(TokenType.OPERATOR, tokens.getToken(0).getType());
    }

    @Test
    public void test02_03_operator() {
        print(testName.getMethodName());
        machine.execute("*,0.\n");
        assertEquals(TokenType.OPERATOR, tokens.getToken(0).getType());
    }

    @Test
    public void test02_04_operator() {
        print(testName.getMethodName());
        machine.execute("/=0.8\n");
        assertEquals(TokenType.OPERATOR, tokens.getToken(0).getType());
    }

    @Test
    public void test02_05_operator() {
        print(testName.getMethodName());
        machine.execute("+,0.E+0\n");
        assertEquals(TokenType.OPERATOR, tokens.getToken(0).getType());
    }

    @Test
    public void test02_06_operator() {
        print(testName.getMethodName());
        machine.execute("-,0.E0\n");
        assertEquals(TokenType.OPERATOR, tokens.getToken(0).getType());
    }

    @Test(expected = AnalyzerException.class)
    public void test03_01_octal() {
        print(testName.getMethodName());
        machine.execute("0_\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test
    public void test03_02_octal() {
        print(testName.getMethodName());
        machine.execute("0x1\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test
    public void test03_01_hexa() {
        print(testName.getMethodName());
        machine.execute("0x\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test(expected = AnalyzerException.class)
    public void test04_02_hexa() {
        print(testName.getMethodName());
        machine.execute("0x_\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test(expected = AnalyzerException.class)
    public void test05_01_desitkova() {
        print(testName.getMethodName());
        machine.execute("0_\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test(expected = AnalyzerException.class)
    public void test06_01_octal() {
        print(testName.getMethodName());
        machine.execute("0_\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test(expected = AnalyzerException.class)
    public void test07_01_Des_Exp() {
        print(testName.getMethodName());
        machine.execute("0._\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test
    public void test08_02_Des() {
        print(testName.getMethodName());
        machine.execute("0.0E0\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test(expected = AnalyzerException.class)
    public void test08_03_Des() {
        print(testName.getMethodName());
        machine.execute("0.0_\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test(expected = AnalyzerException.class)
    public void test09_03_exponent() {
        print(testName.getMethodName());
        machine.execute("0.a_\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test
    public void test09_04_exponent() {
        print(testName.getMethodName());
        machine.execute("0.e-0\n");
        assertEquals(TokenType.LONG_NUMBER, tokens.getToken(0).getType());
    }

    @Test
    public void test10_01_oddelovac() {
        print(testName.getMethodName());
        machine.execute("a*");
        assertEquals(TokenType.IDENTIFIER, tokens.getToken(0).getType());
    }

    @Test(expected = AnalyzerException.class)
    public void test10_02_oddelovac() {
        print(testName.getMethodName());
        machine.execute("a*_");
        assertEquals(TokenType.IDENTIFIER, tokens.getToken(0).getType());
    }

    @Test(expected = AnalyzerException.class)
    public void test11_02_exponenta() {
        print(testName.getMethodName());
        machine.execute("0a*_");
        assertEquals(TokenType.IDENTIFIER, tokens.getToken(0).getType());
    }

    @Test
    public void test12_03_koment() {
        print(testName.getMethodName());
        machine.execute("{execute}a\n");
        assertEquals(TokenType.IDENTIFIER, tokens.getToken(0).getType());
    }


  

}
