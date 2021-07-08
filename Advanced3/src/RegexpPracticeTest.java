import org.junit.Test;
import util.RegexpPractice;
import util.RegexpPractice.AVPair;

import java.text.DateFormat;
import java.util.*;

import static org.junit.Assert.*;

public class RegexpPracticeTest {

    @Test
    public void testFindSingleQuotedTextSimple() {
        ArrayList<String> inputs = new ArrayList<>();
        ArrayList<String> expected = new ArrayList<>();


        inputs.add("fdsaf'test'fdsafdsa");
        expected.add("test");

        inputs.add("   fds   af'test'fds   afdsa   ");
        expected.add("test");

        inputs.add("   fds   af   'test'    fds   afdsa   ");
        expected.add("test");

        inputs.add("   fds   af   '  test   '   fds   afdsa   ");
        expected.add("  test   ");

        inputs.add("   fds   af @#!*'test'*%#@ fds   afdsa   ");
        expected.add("test");

        inputs.add("   fds   af @#\\!*'  test   '*%#\\@ fds   afdsa   ");
        expected.add("  test   ");

        inputs.add("   fds   af @#!*' @\\#!* test@#!* \\  '*%#@ fds   afdsa   ");
        expected.add(" @\\#!* test@#!* \\  ");

        inputs.add("   fds   af @#!*' @\\  \"#!* test@#!* \\ \"  '*%#@ fds   afdsa   ");
        expected.add(" @\\  \"#!* test@#!* \\ \"  ");

        inputs.add("   fds   af @#!*' @\\#!* test@#!* \\  '*%#@ fds  'afdsa'   ");
        expected.add(" @\\#!* test@#!* \\  ");

        inputs.add("fdsaf''fdsafdsa");
        expected.add("");

        inputs.add("fdsaf $@#*&\\''\\$#%&* fdsafdsa");
        expected.add("");

        inputs.add("'onetwothree'fourfive");
        expected.add("onetwothree");

        inputs.add("'$#@*& onetwothree'fourfive");
        expected.add("$#@*& onetwothree");

        inputs.add("$#@*& 'onetwothree'fourfive");
        expected.add("onetwothree");

        inputs.add("''onetwothreefourfive");
        expected.add("");

        inputs.add("#@*&%''#@*&%onetwothreefourfive");
        expected.add("");

        inputs.add("onetwothree'fourfive'");
        expected.add("fourfive");

        inputs.add("onetwothreefourfive''");
        expected.add("");

        inputs.add("onetwothreefourfive#@*&%''#@*&%");
        expected.add("");

        inputs.add("abc'def'xyz'123'");
        expected.add("def");

        inputs.add("abc''xyz'123'");
        expected.add("");

        inputs.add("abc'def'xyz''");
        expected.add("def");

        inputs.add("fdsaftestfdsafdsa");
        expected.add(null);

        inputs.add("more'testing");
        expected.add(null);

        inputs.add("'moretesting");
        expected.add(null);

        inputs.add("moretesting'");
        expected.add(null);

        inputs.add("''");
        expected.add("");

        inputs.add("''''");
        expected.add("");

        inputs.add("'''def'");
        expected.add("");

        inputs.add("'def'''");
        expected.add("def");

        inputs.add("sdf sdfs df'' sdfsfs sdf");
        expected.add("");

        inputs.add("'");
        expected.add(null);

        inputs.add("\"\"");
        expected.add(null);

        inputs.add("\"sfsdfsdfs'sdfsdf\"");
        expected.add(null);

        inputs.add("");
        expected.add(null);


        for (int i = 0; i < inputs.size(); ++i) {
            String output = RegexpPractice.findSingleQuotedTextSimple(inputs.get(i));
            assertEquals(expected.get(i), output);
        }
    }

    @Test
    public void testFindDoubleQuotedTextSimple() {
        ArrayList<String> inputs = new ArrayList<>();
        ArrayList<String> expected = new ArrayList<>();

        inputs.add("fdsaf\"test\"fdsafdsa");
        expected.add("test");

        inputs.add("   fds   af\"test\"fds   afdsa   ");
        expected.add("test");

        inputs.add("   fds   af   \"test\"    fds   afdsa   ");
        expected.add("test");

        inputs.add("   fds   af   \"  test   \"   fds   afdsa   ");
        expected.add("  test   ");

        inputs.add("   fds   af @#!*\"test\"*%#@ fds   afdsa   ");
        expected.add("test");

        inputs.add("   fds   af @#\\!*\"  test   \"*%#\\@ fds   afdsa   ");
        expected.add("  test   ");

        inputs.add("   fds   af @#!*\" @\\#!* test@#!* \\  \"*%#@ fds   afdsa   ");
        expected.add(" @\\#!* test@#!* \\  ");

        inputs.add("   fds   af @#!*\" @\\  '#!* test@#!* \\ '  \"*%#@ fds   afdsa   ");
        expected.add(" @\\  '#!* test@#!* \\ '  ");

        inputs.add("   fds   af @#!*\" @\\#!* test@#!* \\  \"*%#@ fds  \"afdsa\"   ");
        expected.add(" @\\#!* test@#!* \\  ");

        inputs.add("fdsaf\"\"fdsafdsa");
        expected.add("");

        inputs.add("fdsaf $@#*&\\\"\"\\$#%&* fdsafdsa");
        expected.add("");

        inputs.add("\"onetwothree\"fourfive");
        expected.add("onetwothree");

        inputs.add("\"$#@*& onetwothree\"fourfive");
        expected.add("$#@*& onetwothree");

        inputs.add("$#@*& \"onetwothree\"fourfive");
        expected.add("onetwothree");

        inputs.add("\"\"onetwothreefourfive");
        expected.add("");

        inputs.add("#@*&%\"\"#@*&%onetwothreefourfive");
        expected.add("");

        inputs.add("onetwothree\"fourfive\"");
        expected.add("fourfive");

        inputs.add("onetwothreefourfive\"\"");
        expected.add("");

        inputs.add("onetwothreefourfive#@*&%\"\"#@*&%");
        expected.add("");

        inputs.add("abc\"def\"xyz\"123\"");
        expected.add("def");

        inputs.add("abc\"\"xyz\"123\"");
        expected.add("");

        inputs.add("abc\"def\"xyz\"\"");
        expected.add("def");

        inputs.add("fdsaftestfdsafdsa");
        expected.add(null);

        inputs.add("more\"testing");
        expected.add(null);

        inputs.add("\"moretesting");
        expected.add(null);

        inputs.add("moretesting\"");
        expected.add(null);

        inputs.add("\"\"");
        expected.add("");

        inputs.add("\"\"\"\"");
        expected.add("");

        inputs.add("\"\"\"def\"");
        expected.add("");

        inputs.add("\"def\"\"\"");
        expected.add("def");

        inputs.add("sdf sdfs df\"\" sdfsfs sdf");
        expected.add("");

        inputs.add("\"");
        expected.add(null);

        inputs.add("''");
        expected.add(null);

        inputs.add("'sfsdfsdfs'sdfsdf'");
        expected.add(null);

        inputs.add("");
        expected.add(null);

        for (int i = 0; i < inputs.size(); ++i) {
            String output = RegexpPractice.findDoubleQuotedTextSimple(inputs.get(i));
            assertEquals(expected.get(i), output);
        }
    }

    @Test
    public void testDoubleOrSingleQuoted() {
        ArrayList<String> inputs = new ArrayList<String>();
        ArrayList<List<String>> expected = new ArrayList<List<String>>();

        inputs.add("fdsaf\"test\"fdsafdsa");
        expected.add(Arrays.asList("test"));

        inputs.add("   fds   af\"test\"fds   afdsa   ");
        expected.add(Arrays.asList("test"));

        inputs.add("   fds   af   \"test\"    fds   afdsa   ");
        expected.add(Arrays.asList("test"));

        inputs.add("   fds   af   \"  test   \"   fds   afdsa   ");
        expected.add(Arrays.asList("  test   "));

        inputs.add("   fds   af @#!*\"test\"*%#@ fds   afdsa   ");
        expected.add(Arrays.asList("test"));

        inputs.add("   fds   af @#\\!*\"  test   \"*%#\\@ fds   afdsa   ");
        expected.add(Arrays.asList("  test   "));

        inputs.add("   fds   af @#!*\" @\\#!* test@#!* \\  \"*%#@ fds   afdsa   ");
        expected.add(Arrays.asList(" @\\#!* test@#!* \\  "));

        inputs.add("   fds   af @#!*\" @\\  '#!* test@#!* \\ '  \"*%#@ fds   afdsa   ");
        expected.add(Arrays.asList(" @\\  '#!* test@#!* \\ '  "));

        inputs.add("   fds   af @#!*\" @\\#!* test@#!* \\  \"*%#@ fds  \"afdsa\"   ");
        expected.add(Arrays.asList(" @\\#!* test@#!* \\  ", "afdsa"));

        inputs.add("fdsaf\"\"fdsafdsa");
        expected.add(Arrays.asList(""));

        inputs.add("fdsaf $@#*&\\\"\"\\$#%&* fdsafdsa");
        expected.add(Arrays.asList(""));

        inputs.add("\"onetwothree\"fourfive");
        expected.add(Arrays.asList("onetwothree"));

        inputs.add("\"$#@*& onetwothree\"fourfive");
        expected.add(Arrays.asList("$#@*& onetwothree"));

        inputs.add("$#@*& \"onetwothree\"fourfive");
        expected.add(Arrays.asList("onetwothree"));

        inputs.add("\"\"onetwothreefourfive");
        expected.add(Arrays.asList(""));

        inputs.add("#@*&%\"\"#@*&%onetwothreefourfive");
        expected.add(Arrays.asList(""));

        inputs.add("onetwothree\"fourfive\"");
        expected.add(Arrays.asList("fourfive"));

        inputs.add("onetwothreefourfive\"\"");
        expected.add(Arrays.asList(""));

        inputs.add("onetwothreefourfive#@*&%\"\"#@*&%");
        expected.add(Arrays.asList(""));

        inputs.add("abc\"def\"xyz\"123\"");
        expected.add(Arrays.asList("def", "123"));

        inputs.add("abc\"\"xyz\"123\"");
        expected.add(Arrays.asList("", "123"));

        inputs.add("abc\"def\"xyz\"\"");
        expected.add(Arrays.asList("def", ""));

        inputs.add("fdsaftestfdsafdsa");
        expected.add(new ArrayList<>());

        inputs.add("more\"testing");
        expected.add(new ArrayList<>());

        inputs.add("\"moretesting");
        expected.add(new ArrayList<>());

        inputs.add("moretesting\"");
        expected.add(new ArrayList<>());

        inputs.add("\"\"");
        expected.add(Arrays.asList(""));

        inputs.add("\"\"\"\"");
        expected.add(Arrays.asList("", ""));

        inputs.add("\"\"\"def\"");
        expected.add(Arrays.asList("", "def"));

        inputs.add("\"def\"\"\"");
        expected.add(Arrays.asList("def", ""));

        inputs.add("sdf sdfs df\"\" sdfsfs sdf");
        expected.add(Arrays.asList(""));

        inputs.add("\"");
        expected.add(new ArrayList<>());

        inputs.add("'");
        expected.add(new ArrayList<>());

        inputs.add("'sfsdfsdfs'sdfsdf'");
        expected.add(Arrays.asList("sfsdfsdfs"));

        inputs.add("");
        expected.add(new ArrayList<>());

        inputs.add("'single'");
        expected.add(Arrays.asList("single"));

        inputs.add("more'single'");
        expected.add(Arrays.asList("single"));

        inputs.add("'single'more");
        expected.add(Arrays.asList("single"));

        inputs.add("\"double\"");
        expected.add(Arrays.asList("double"));

        inputs.add("more\"double\"");
        expected.add(Arrays.asList("double"));

        inputs.add("\"double\"more");
        expected.add(Arrays.asList("double"));

        inputs.add("\"double\"'single'");
        expected.add(Arrays.asList("double", "single"));

        inputs.add("'single'\"double\"");
        expected.add(Arrays.asList("single", "double"));

        inputs.add("'\"dquoted\"'");
        expected.add(Arrays.asList("\"dquoted\""));

        inputs.add("\"'squoted'\"");
        expected.add(Arrays.asList("'squoted'"));

        inputs.add("\"yes'\"no'maybe'");
        expected.add(Arrays.asList("yes'", "maybe"));

        inputs.add("'yes\"'no\"maybe\"");
        expected.add(Arrays.asList("yes\"", "maybe"));

        inputs.add("fdsaf'test'fdsafdsa");
        expected.add(Arrays.asList("test"));

        inputs.add("   fds   af'test'fds   afdsa   ");
        expected.add(Arrays.asList("test"));

        inputs.add("   fds   af   'test'    fds   afdsa   ");
        expected.add(Arrays.asList("test"));

        inputs.add("   fds   af   '  test   '   fds   afdsa   ");
        expected.add(Arrays.asList("  test   "));

        inputs.add("   fds   af @#!*'test'*%#@ fds   afdsa   ");
        expected.add(Arrays.asList("test"));

        inputs.add("   fds   af @#\\!*'  test   '*%#\\@ fds   afdsa   ");
        expected.add(Arrays.asList("  test   "));

        inputs.add("   fds   af @#!*' @\\#!* test@#!* \\  '*%#@ fds   afdsa   ");
        expected.add(Arrays.asList(" @\\#!* test@#!* \\  "));

        inputs.add("   fds   af @#!*' @\\  \"#!* test@#!* \\ \"  '*%#@ fds   afdsa   ");
        expected.add(Arrays.asList(" @\\  \"#!* test@#!* \\ \"  "));

        inputs.add("   fds   af @#!*' @\\#!* test@#!* \\  '*%#@ fds  'afdsa'   ");
        expected.add(Arrays.asList(" @\\#!* test@#!* \\  ", "afdsa"));

        inputs.add("fdsaf''fdsafdsa");
        expected.add(Arrays.asList(""));

        inputs.add("fdsaf $@#*&\\''\\$#%&* fdsafdsa");
        expected.add(Arrays.asList(""));

        inputs.add("'onetwothree'fourfive");
        expected.add(Arrays.asList("onetwothree"));

        inputs.add("'$#@*& onetwothree'fourfive");
        expected.add(Arrays.asList("$#@*& onetwothree"));

        inputs.add("$#@*& 'onetwothree'fourfive");
        expected.add(Arrays.asList("onetwothree"));

        inputs.add("''onetwothreefourfive");
        expected.add(Arrays.asList(""));

        inputs.add("#@*&%''#@*&%onetwothreefourfive");
        expected.add(Arrays.asList(""));

        inputs.add("onetwothree'fourfive'");
        expected.add(Arrays.asList("fourfive"));

        inputs.add("onetwothreefourfive''");
        expected.add(Arrays.asList(""));

        inputs.add("onetwothreefourfive#@*&%''#@*&%");
        expected.add(Arrays.asList(""));

        inputs.add("abc'def'xyz'123'");
        expected.add(Arrays.asList("def", "123"));

        inputs.add("abc''xyz'123'");
        expected.add(Arrays.asList("", "123"));

        inputs.add("abc'def'xyz''");
        expected.add(Arrays.asList("def", ""));

        inputs.add("fdsaftestfdsafdsa");
        expected.add(new ArrayList<>());

        inputs.add("more'testing");
        expected.add(new ArrayList<>());

        inputs.add("'moretesting");
        expected.add(new ArrayList<>());

        inputs.add("moretesting'");
        expected.add(new ArrayList<>());

        inputs.add("''");
        expected.add(Arrays.asList(""));

        inputs.add("''''");
        expected.add(Arrays.asList("", ""));

        inputs.add("'''def'");
        expected.add(Arrays.asList("", "def"));

        inputs.add("'def'''");
        expected.add(Arrays.asList("def", ""));

        inputs.add("sdf sdfs df'' sdfsfs sdf");
        expected.add(Arrays.asList(""));

        inputs.add("\"");
        expected.add(new ArrayList<>());


        for (int i = 0; i < inputs.size(); ++i) {
            List<String> output = RegexpPractice.findDoubleOrSingleQuoted(inputs.get(i));
            assertEquals(String.format("Test %d failed: Search <<%s>>", i, inputs.get(i)), expected.get(i), output);
        }
    }


    void fillSingleQuotedTestInputs(List<String> inputs, List<List<String>> expected) {

        inputs.add("fdsaf'test'fdsafdsa");
        expected.add(Arrays.asList("test"));

        inputs.add("fdsaf'te\\st'fdsafdsa");
        expected.add(Arrays.asList("te\\st"));

        inputs.add("fdsaf'te\\'st'fdsafdsa");
        expected.add(Arrays.asList("te'st"));

        inputs.add("fdsaf'te\\\"st'fdsafdsa");
        expected.add(Arrays.asList("te\\\"st"));

        inputs.add("   fds   af'test'fds   afdsa   ");
        expected.add(Arrays.asList("test"));

        inputs.add("   fds   af   'test'    f\\\"ds   af\\dsa \"   ");
        expected.add(Arrays.asList("test"));

        inputs.add("   fds   af   '  test   '   fds   afdsa   ");
        expected.add(Arrays.asList("  test   "));

        inputs.add("   fds   af @#!*'test'*%#@ fds   afdsa   ");
        expected.add(Arrays.asList("test"));

        inputs.add("   fds   af @#\\!*'  test   '*%#\\@ fds   afdsa   ");
        expected.add(Arrays.asList("  test   "));

        inputs.add("   fds   af @#!*' @\\#!* test@#!* \\  '*%#@ fds   afdsa   ");
        expected.add(Arrays.asList(" @\\#!* test@#!* \\  "));

        inputs.add("   fds   af @#!*' @\\  \"#!* test@#!* \\ \"  '*%#@ fds   afdsa   ");
        expected.add(Arrays.asList(" @\\  \"#!* test@#!* \\ \"  "));

        inputs.add("   fds   af @#!*' @\\#!* te\\st@#!* \\  '*%#@ fds  'afdsa'   ");
        expected.add(Arrays.asList(" @\\#!* te\\st@#!* \\  ", "afdsa"));

        inputs.add("   fds   af @#!*' @\\#!* test@#!* \\  '*%#@ fds  'afdsa'   ");
        expected.add(Arrays.asList(" @\\#!* test@#!* \\  ", "afdsa"));

        inputs.add("fdsaf''fdsafdsa");
        expected.add(Arrays.asList(""));

        inputs.add("fdsaf\\''\\''fdsafdsa");
        expected.add(Arrays.asList("'"));

        inputs.add("fdsaf $@#*&\\''\\$#%&* fdsafdsa");
        expected.add(new ArrayList<>());

        inputs.add("'onetwothree'fourfive");
        expected.add(Arrays.asList("onetwothree"));

        inputs.add("'$#@*& onetwothree'fourfive");
        expected.add(Arrays.asList("$#@*& onetwothree"));

        inputs.add("$#@*& 'onetwothree'fourfive");
        expected.add(Arrays.asList("onetwothree"));

        inputs.add("''onetwothreefourfive");
        expected.add(Arrays.asList(""));

        inputs.add("#@*&%''#@*&%onetwothreefourfive");
        expected.add(Arrays.asList(""));

        inputs.add("onetwothree'fourfive'");
        expected.add(Arrays.asList("fourfive"));

        inputs.add("onetwothreefourfive''");
        expected.add(Arrays.asList(""));

        inputs.add("onetwothreefourfive#@*&%''#@*&%");
        expected.add(Arrays.asList(""));

        inputs.add("abc'def'xyz'123'");
        expected.add(Arrays.asList("def", "123"));

        inputs.add("abc''xyz'123'");
        expected.add(Arrays.asList("", "123"));

        inputs.add("abc'def'xyz''");
        expected.add(Arrays.asList("def", ""));

        inputs.add("fdsaftestfdsafdsa");
        expected.add(new ArrayList<>());

        inputs.add("more'testing");
        expected.add(new ArrayList<>());

        inputs.add("'moretesting");
        expected.add(new ArrayList<>());

        inputs.add("moretesting'");
        expected.add(new ArrayList<>());

        inputs.add("''");
        expected.add(Arrays.asList(""));

        inputs.add("''''");
        expected.add(Arrays.asList("", ""));

        inputs.add("'''def'");
        expected.add(Arrays.asList("", "def"));

        inputs.add("\"'def'\"");
        expected.add(Arrays.asList("def"));

        inputs.add("'\"def\"'");
        expected.add(Arrays.asList("\"def\""));

        inputs.add("'def'''");
        expected.add(Arrays.asList("def", ""));

        inputs.add("sdf sdfs df'' sdfsfs sdf");
        expected.add(Arrays.asList(""));

        inputs.add("\"");
        expected.add(new ArrayList<>());

        inputs.add("'test'");
        expected.add(Arrays.asList("test"));

        inputs.add("more'test'");
        expected.add(Arrays.asList("test"));

        inputs.add("'test'more");
        expected.add(Arrays.asList("test"));

        inputs.add("\\'no'yes'");
        expected.add(Arrays.asList("yes"));

        inputs.add("a 'one' and 'two' and 'three'...");
        expected.add(Arrays.asList("one", "two", "three"));

        inputs.add("nothing at all");
        expected.add(Arrays.<String>asList());

        inputs.add("''");
        expected.add(Arrays.asList(""));

        inputs.add("''test");
        expected.add(Arrays.asList(""));

        inputs.add("test''");
        expected.add(Arrays.asList(""));

        inputs.add("te''st");
        expected.add(Arrays.asList(""));

        inputs.add("'This is not wrong' and 'this isn\\'t either'");
        expected.add(Arrays.asList("This is not wrong", "this isn't either"));

        inputs.add("'tw\\'o repl\\'acements' in 't\\'wo stri\\'ngs'.");
        expected.add(Arrays.asList("tw'o repl'acements", "t'wo stri'ngs"));

        inputs.add("'\\''");
        expected.add(Arrays.asList("'"));

        inputs.add("'''");
        expected.add(Arrays.asList(""));

        inputs.add("'test1'\n'test2'");
        expected.add(Arrays.asList("test1", "test2"));

        inputs.add("'test1'\b'test2'");
        expected.add(Arrays.asList("test1", "test2"));

        inputs.add("'test1'\t'test2'");
        expected.add(Arrays.asList("test1", "test2"));

        inputs.add("''''");
        expected.add(Arrays.asList("", "")); // This one is hard. Hint: \G

        inputs.add("some text 'containing a single \\, not \\''");
        expected.add(Arrays.asList("containing a single \\, not '"));
    }

    @Test
    public void testFindSingleQuotedTextWithEscapes() {
        ArrayList<String> inputs = new ArrayList<String>();
        ArrayList<List<String>> expect = new ArrayList<List<String>>();

        fillSingleQuotedTestInputs(inputs, expect);

        for (int i = 0; i < inputs.size(); ++i) {
            List<String> output = RegexpPractice.findSingleQuotedTextWithEscapes(inputs.get(i));
            assertEquals(String.format("Test %d failed: Search <<%s>>", i, inputs.get(i)), expect.get(i), output);
        }
    }


    @Test
    public void testFindDoubleQuotedTextWithEscapes() {
        ArrayList<String> inputs = new ArrayList<String>();
        ArrayList<List<String>> expect = new ArrayList<List<String>>();

        fillSingleQuotedTestInputs(inputs, expect);

        for (int i = 0; i < inputs.size(); ++i) {
            String input = inputs.get(i).replaceAll("'", "@@@").replaceAll("\"", "'").replaceAll("@@@", "\""); // Replace single with double quotes.
            List<String> expectedQuoteList = expect.get(i);
            ArrayList<String> expectList = new ArrayList<String>(expectedQuoteList.size());
            for (String str : expectedQuoteList) {
                if (str != null)
                    expectList.add(str.replaceAll("'", "@@@")
                            .replaceAll("\"", "'")
                            .replaceAll("@@@", "\""));
                else
                    expectList.add(null);
            }

            List<String> output = RegexpPractice.findDoubleQuotedTextWithEscapes(input);
            assertEquals(String.format("Test %d failed: Search <<%s>>", i, input), expectList, output);
        }
    }


    @Test
    public void testParseDate() {
        DateFormat df = DateFormat.getDateTimeInstance();
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        ArrayList<String> inputs = new ArrayList<String>();
        ArrayList<Long> expect = new ArrayList<Long>();

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.set(Calendar.MILLISECOND, 0);

        cal.set(2012, 4, 23, 1, 23, 31);
        long time1 = cal.getTimeInMillis();

        inputs.add("Wed, 23-May-12 01:23:31 GMT");
        cal.set(2012, 4, 23, 1, 23, 31);
        expect.add(cal.getTimeInMillis());

        inputs.add("Thu, 24-May-12 01:23:31 GMT");
        cal.set(2012, 4, 24, 1, 23, 31);
        expect.add(cal.getTimeInMillis());

        inputs.add("Fri, 25-May-12 01:23:31 GMT");
        cal.set(2012, 4, 25, 1, 23, 31);
        expect.add(cal.getTimeInMillis());

        inputs.add("Sat, 26-May-12 01:23:31 GMT");
        cal.set(2012, 4, 26, 1, 23, 31);
        expect.add(cal.getTimeInMillis());

        inputs.add("Tue, 22-May-12 01:23:31 GMT");
        cal.set(2012, 4, 22, 1, 23, 31);
        expect.add(cal.getTimeInMillis());

        inputs.add("Mon, 21-May-12 01:23:31 GMT");
        cal.set(2012, 4, 21, 1, 23, 31);
        expect.add(cal.getTimeInMillis());

        inputs.add("Sun, 20-May-12 01:23:31 GMT");
        cal.set(2012, 4, 20, 1, 23, 31);
        expect.add(cal.getTimeInMillis());

        inputs.add("Wed, 23-May-12 01:23:31 GMT");
        cal.set(2012, 4, 23, 1, 23, 31);
        expect.add(cal.getTimeInMillis());


        inputs.add("Wed, 23-May-12 01:23:31 GMT");
        expect.add(time1);

        inputs.add("23-May-12 01:23:31 GMT");
        expect.add(time1);

        inputs.add("23-May-12 01:23:31");
        expect.add(time1);

        inputs.add("23 May 2012 01:23:31 GMT");
        expect.add(time1);

        inputs.add("23    May  2012   01:23:31     GMT");
        expect.add(time1);

        inputs.add("Wed,   23 May  2012   01:23:31     GMT");
        expect.add(time1);

        inputs.add("23 mAy 2012 01:23:31 GMT");
        expect.add(time1);

        inputs.add("23 maY 12 01:23:31");
        expect.add(time1);

        inputs.add("23 jan 12 01:23:31");
        cal.set(2012, 0, 23, 1, 23, 31);
        expect.add(cal.getTimeInMillis());

        inputs.add("23 jan 89 01:23:31");
        cal.set(1989, 0, 23, 1, 23, 31);
        expect.add(cal.getTimeInMillis());

        inputs.add("01 AUG 12 15:23:31 GMT");
        cal.set(2012, 7, 1, 15, 23, 31);
        expect.add(cal.getTimeInMillis());

        inputs.add("WED,   23 May  2012   01:23:31     GMT");
        expect.add(null);

        inputs.add("Xxx,   23 May  2012   01:23:31     GMT");
        expect.add(null);

        inputs.add("   Wed,   23 May  2012   01:23:31     GMT");
        expect.add(null);

        inputs.add("Wed   ,   23 May  2012   01:23:31     GMT");
        expect.add(null);

        inputs.add("Wed,23 May  2012   01:23:31     GMT");
        expect.add(null);

        inputs.add("Wed 23 May  2012   01:23:31     GMT");
        expect.add(null);

        inputs.add("Wed. 23 May 2012 01:23:31 GMT");
        expect.add(null);

        inputs.add("wEd, 23 May 2012 01:23:31 GMT");
        expect.add(null);

        inputs.add("wed, 23 May 2012 01:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 3 May 2012 01:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 32 May 2012 01:23:31 GMT");
        expect.add(null);

        inputs.add("1 May 2012 15:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23May 2012 01:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23/May/2012 01:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23.May.2012 01:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23 June 2012 01:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23-Bla-2012 01:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23 May 012 01:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23 May 2 01:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23 May 22012 01:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23 May 012 01-23-31 GMT");
        expect.add(null);

        inputs.add("Wed, 23 May 012 01 23 31 GMT");
        expect.add(null);

        inputs.add("Wed, 23 May 2012 1:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23 May 2012 24:23:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23 May 2012 01:60:31 GMT");
        expect.add(null);

        inputs.add("Wed, 23 May 2012 01:23:60 GMT");
        expect.add(null);

        inputs.add("Wed, 23 May 2012 01:3:1 GMT");
        expect.add(null);

        inputs.add("01 May 2012-15:23:31");
        expect.add(null);

        inputs.add("01 May 2012-15/23:31");
        expect.add(null);

        inputs.add("01 May 2012-15/23:31");
        expect.add(null);

        inputs.add("01 bla 12 15:23:31 GMT");
        expect.add(null);

        inputs.add("01 May 2012 15:23:31 BLA");
        expect.add(null);

        inputs.add("Wed, 23 May 2012 01:23:31 GMT.");
        expect.add(null);

        inputs.add("Wed, 23 May 2012 01:23:31 GMT    ");
        expect.add(null);

        inputs.add("Wed, 23 May 2012 01:23:31 gmt");
        expect.add(null);


        for (int i = 0; i < inputs.size(); ++i) {
            Long expectTime = expect.get(i);
            Calendar output = RegexpPractice.parseDate(inputs.get(i));
            if (expectTime == null) {
                assertNull(String.format("Test %d failed: Parsing <<%s>> (should be null)", i, inputs.get(i)), output);
                continue;
            } else {
                assertNotNull(String.format("Test %d failed: Parsing <<%s>> (unexpected null)", i, inputs.get(i)), output);
            }
            output.set(Calendar.MILLISECOND, 0);

            long outTime = output.getTimeInMillis();

            assertEquals(String.format("Test %d failed: Parsing <<%s>> (was %s not %s)",
                    i, inputs.get(i), df.format(outTime), df.format(expectTime)),
                    (long) expectTime, outTime);
        }
    }

    @Test
    public void testTokenize() {
        ArrayList<String> inputs = new ArrayList<String>();
        ArrayList<List<String>> expect = new ArrayList<List<String>>();

        inputs.add("this-string 'has only three tokens'");
        expect.add(Arrays.asList("this", "string", "has only three tokens"));

        inputs.add("this*string'has only two@tokens'");
        expect.add(Arrays.asList("this", "stringhas only two@tokens"));

        inputs.add("one'two''three' '' four 'twenty-one'");
        expect.add(Arrays.asList("onetwothree", "", "four", "twenty-one"));

        inputs.add("''");
        expect.add(Arrays.asList(""));

        inputs.add("''''");
        expect.add(Arrays.asList(""));

        inputs.add("'this'");
        expect.add(Arrays.asList("this"));

        inputs.add("''this'");
        expect.add(Arrays.asList("this"));

        inputs.add("this''");
        expect.add(Arrays.asList("this"));
        inputs.add("this ''");
        expect.add(Arrays.asList("this", ""));

        inputs.add("\\'this\\'");
        expect.add(Arrays.asList("this\\"));

        inputs.add("\"this-string' 'has only three tokens");
        expect.add(Arrays.asList("this", "string has", "only", "three", "tokens"));

        inputs.add("\"this-string' ' has only three tokens");
        expect.add(Arrays.asList("this", "string ", "has", "only", "three", "tokens"));

        inputs.add("'t$#\t@s'");
        expect.add(Arrays.asList("t$#\t@s"));

        inputs.add("'this-string' 'has only three tokens'");
        expect.add(Arrays.asList("this-string", "has only three tokens"));

        inputs.add("'this-string'-'has only three tokens'");
        expect.add(Arrays.asList("this-string", "has only three tokens"));

        inputs.add("'this-string'--this 'has only three tokens'");
        expect.add(Arrays.asList("this-string", "this", "has only three tokens"));

        inputs.add("'one'-'two\"'");
        expect.add(Arrays.asList("one", "two\""));

        inputs.add("'this-string' has only three' tokens");
        expect.add(Arrays.asList("this-string", "has", "only", "three", "tokens"));

        for (int i = 10; i < inputs.size(); ++i) {
            List<String> output = RegexpPractice.wordTokenize(inputs.get(i));
            assertEquals(String.format("Test %d failed: Search <<%s>>", i, inputs.get(i)), expect.get(i), output);
        }
    }

    /**
     * This should pass if you solved the first part of the bonus question.
     */
    @Test
    public void testParseAVPairs() {
        ArrayList<String> inputs = new ArrayList<String>();
        ArrayList<List<AVPair>> expect = new ArrayList<List<AVPair>>();

        inputs.add("key");
        expect.add(Arrays.asList(new AVPair("key", null)));

        inputs.add("    key    ");
        expect.add(Arrays.asList(new AVPair("key", null)));

        inputs.add("key=val");
        expect.add(Arrays.asList(new AVPair("key", "val")));

        inputs.add("    key    =    val");
        expect.add(Arrays.asList(new AVPair("key", "val")));

        inputs.add("key=val; key2=val2");
        expect.add(Arrays.asList(new AVPair("key", "val"), new AVPair("key2", "val2")));

        inputs.add("key=val; key2");
        expect.add(Arrays.asList(new AVPair("key", "val"), new AVPair("key2", null)));

        inputs.add("key ; key2=val2");
        expect.add(Arrays.asList(new AVPair("key", null), new AVPair("key2", "val2")));

        inputs.add("  key=val ; key2 = val2");
        expect.add(Arrays.asList(new AVPair("key", "val"), new AVPair("key2", "val2")));

        inputs.add("  key=val ; key2 =    val2     ");
        expect.add(Arrays.asList(new AVPair("key", "val"), new AVPair("key2", "val2")));

        inputs.add("key = \"val1 three\"");
        expect.add(Arrays.asList(new AVPair("key", "val1 three")));

        inputs.add("key =    \"val1 three\"      ");
        expect.add(Arrays.asList(new AVPair("key", "val1 three")));

        inputs.add("key =    \"  val1 three  \"      ");
        expect.add(Arrays.asList(new AVPair("key", "  val1 three  ")));

        inputs.add("key= \"\\\"He said what?\\\"\"");
        expect.add(Arrays.asList(new AVPair("key", "\"He said what?\"")));

        inputs.add("key=val ; key2 = \"val2 three  ; \"");
        expect.add(Arrays.asList(new AVPair("key", "val"), new AVPair("key2", "val2 three  ; ")));

        inputs.add("key2 =\"val 2\"");
        expect.add(Arrays.asList(new AVPair("key2", "val 2")));

        inputs.add("key2 =\"val\\ 2\"");
        expect.add(Arrays.asList(new AVPair("key2", "val\\ 2")));

        inputs.add("key2 =\"val\\\" 2\"");
        expect.add(Arrays.asList(new AVPair("key2", "val\" 2")));

        inputs.add("key2 =\"val\\c 2\"");
        expect.add(Arrays.asList(new AVPair("key2", "val\\c 2")));

        inputs.add("key =\"val/1\"");
        expect.add(Arrays.asList(new AVPair("key", "val/1")));

        inputs.add("key1;key2=\"val 2\";key3=\"$#\\\"!'%\\$#@\" ; key4; key5=val_5*");
        expect.add(Arrays.asList(
                new AVPair("key1", null), new AVPair("key2", "val 2"), new AVPair("key3", "$#\"!'%\\$#@"),
                new AVPair("key4", null), new AVPair("key5", "val_5*")));

        //test parseAVPairs
        for (int i = 0; i < inputs.size(); ++i) {
            List<AVPair> output = RegexpPractice.parseAvPairs(inputs.get(i));
            assertEquals(String.format("Test %d failed: Search <<%s>>", i, inputs.get(i)), expect.get(i), output);
        }

        //test parseAVPairs2
        for (int i = 0; i < inputs.size(); ++i) {
            List<AVPair> output = RegexpPractice.parseAvPairs2(inputs.get(i));
            assertEquals(String.format("Test %d failed: Search <<%s>>", i, inputs.get(i)), expect.get(i), output);
        }
    }

    /**
     * Test bad inputs -- this should pass if you solved the second part of the bonus question.
     */
    @Test
    public void testParseAVPairsBadInputs() {
        ArrayList<String> inputs = new ArrayList<String>();
        inputs.add("key=val ; key2 = val2 three");
        inputs.add("(key=val ; key2 = val2");
        inputs.add("(key=");
        inputs.add("((key=val");
        inputs.add("(   ' key=val");
        inputs.add("(   'key=val'");
        inputs.add("key=val;");
        inputs.add("\tkey\t=\t\tval\t;\t\t");
        inputs.add(" = val ; key2 = val2");
        inputs.add("key=val;key2 = val2 three");
        inputs.add("key=val ; key2 = val2   ;  ");
        inputs.add("key=val  key2 = val2  ");
        inputs.add("key=val : key2 = val2  ");
        inputs.add("key=val ; key2 = \"val2 three\"   ; ");
        inputs.add("key=val ; key2 = v\\al2 ");
        inputs.add("key=val ;; key2 = val2 ");
        inputs.add("key=\"val");
        inputs.add("key=\\\"val");
        inputs.add("key=\\\"val\\\"");
        inputs.add("key= \"yes\"no");
        inputs.add(";key=val");
        inputs.add("    ;key=val");
        inputs.add("    ;    key=val");
        inputs.add(";key1=val1;key2=val2;key3=val3");
        inputs.add("key/1=val");
        inputs.add("key1=v(a)l");
        inputs.add("\"key\"=val");

        for (int i = 0; i < inputs.size(); ++i) {
            List<AVPair> output = RegexpPractice.parseAvPairs2(inputs.get(i));
            assertNull(String.format("Test %d failed: Search <<%s>>", i, inputs.get(i)), output);
        }
    }
}
