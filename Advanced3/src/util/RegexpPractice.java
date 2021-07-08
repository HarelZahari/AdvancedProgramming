package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is only used to practice regular expressions.
 * 
 * @author talm
 *
 */
public class RegexpPractice {
    /**
     * Search for the first occurrence of text between single quotes, return the
     * text (without the quotes). Allow an empty string. If no quoted text is found,
     * return null. Some examples :
     * <ul>
     * <li>On input "this is some 'text' and some 'additional text'" the method
     * should return "text".
     * <li>On input "this is an empty string '' and another 'string'" it should
     * return "".
     * </ul>
     * 
     * @param input
     * @return the first occurrence of text between single quotes
     */
    public static String findSingleQuotedTextSimple(String input) {
        String quoteResultString = null;
        Pattern regPattren = Pattern.compile("(['](.*?)['])"); // Catch the required pattern as described
        Matcher regMatcher = regPattren.matcher(input);
        if (regMatcher.find()) { // Check if catch any pattern
            quoteResultString = regMatcher.group(2);
        }
        return quoteResultString;
    }

    /**
     * Search for the first occurrence of text between double quotes, return the
     * text (without the quotes). (should work exactly like
     * {@link #findSingleQuotedTextSimple(String)}), except with double instead of
     * single quotes.
     * 
     * @param input
     * @return the first occurrence of text between double quotes
     */
    public static String findDoubleQuotedTextSimple(String input) {
        String quoteResultString = null;
        Pattern regPattren = Pattern.compile("([\"](.*?)[\"])"); // Catch the required pattern as described
        Matcher regMatcher = regPattren.matcher(input);
        if (regMatcher.find()) { // Check if catch any pattern
            quoteResultString = regMatcher.group(2);
        }
        return quoteResultString;
    }

    /**
     * Search for the all occurrences of text between single quotes <i>or</i> double
     * quotes. Return a list containing all the quoted text found (without the
     * quotes). Note that a double-quote inside a single-quoted string counts as a
     * regular character (e.g, on the string [quote '"this"'] ["this"] should be
     * returned). Allow empty strings. If no quoted text is found, return an empty
     * list.
     * 
     * @param input
     * @return
     */
    public static List<String> findDoubleOrSingleQuoted(String input) {
        List<String> quotesList = new ArrayList<>();
        Pattern regPattren = Pattern.compile("(['\"])(.*?)(\\1)"); // Catch the required pattern as described
        Matcher regMatcher = regPattren.matcher(input);
        while (regMatcher.find()) { // Check if catch any pattern
            quotesList.add(regMatcher.group(2));
        }
        return quotesList;
    }

    /**
     * Separate the input into <i>tokens</i> and return them in a list. A token is
     * any mixture of consecutive word characters and single-quoted strings (single
     * quoted strings may contain any character except a single quote). The returned
     * tokens should not contain the quote characters. A pair of single quotes is
     * considered an empty token (the empty string).
     * 
     * For example, the input "this-string 'has only three tokens'" should return
     * the list {"this", "string", "has only three tokens"}. The input
     * "this*string'has only two@tokens'" should return the list {"this", "stringhas
     * only two@tokens"}
     * 
     * @param input
     * @return
     */
    public static List<String> wordTokenize(String input) {
        List<String> tokenList = new ArrayList<>();
        Pattern regPattren = Pattern.compile("(['][^']*['])*(\\w*)(['][^']*['])*(\\w*)"); // Catch the required pattern as described
        Matcher regMatcher = regPattren.matcher(input);
        while (regMatcher.find()) { // Get all relevant pattern in to list if matched
            if (regMatcher.start() != regMatcher.end())
                tokenList.add(regMatcher.group(0).replaceAll("'", "")); // Replace ' to "
        }
        return tokenList;
    }

    /**
     * Parse a date string with the following general format:<br>
     * Wdy, DD-Mon-YYYY HH:MM:SS GMT<br>
     * Where: <i>Wdy</i> is the day of the week, <i>DD</i> is the day of the month,
     * <i>Mon</i> is the month, <i>YYYY</i> is the year, <i>HH:MM:SS</i> is the time
     * in 24-hour format, and <i>GMT</i> is a the constant timezone string "GMT".
     * 
     * You should also accept variants of the format:
     * <ul>
     * <li>a date without the weekday,
     * <li>spaces instead of dashes (i.e., "DD Mon YYYY"),
     * <li>case-insensitive month (e.g., allow "Jan", "JAN" and "jAn"),
     * <li>a two-digit year (assume it's between 1970 and 2069 in that case)
     * <li>a missing timezone
     * <li>allow multiple spaces wherever a single space is allowed.
     * </ul>
     * 
     * The method should return a java {@link Calendar} object with fields set to
     * the corresponding date and time. Return null if the input is not a valid date
     * string. For validity checking, consider only <i>local</i> validity: that is,
     * only checks that don't require connecting different parts of the date. For
     * example, the weekday string "XXX" is invalid, but "Fri, 09-Jun-2015" is
     * considered valid even though June 9th was a Tuesday. In the same way,
     * "40-Feb-2015" is invalid, but "31-Feb-2015" is ok because you have to know
     * the month is February in order to realize that 31 is not a possible
     * day-of-the-month.
     * 
     * @param input
     * @return
     */
    public static Calendar parseDate(String input) {
        List<String> months = Arrays.asList("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec");
        int day, month, year, hour, min, sec;
        Pattern regPattren = Pattern.compile("(^(((Sun|Mon|Tue|Wed|Thu|Fri|Sat)(,))\\s+)?([0123]{1}[0-9]{1})(-|\\s+){1}([A-Za-z]{3})(-|\\s+){1}(([1-9]{1}[0-9]{3})|[0-9]{2})\\s+([012]{1}[0-9]):([0-6]{1}[0-9]{1}):([0-6]{1}[0-9]{1})((\\s)+(GMT))?$)");
        // Catch the required pattern as described
        Matcher regMatcher = regPattren.matcher(input);
        if (regMatcher.find()) { // If find match create the Calendar obj
            month = months.indexOf(regMatcher.group(8).toLowerCase());
            day = Integer.parseInt(regMatcher.group(6));
            hour = Integer.parseInt(regMatcher.group(12));
            min = Integer.parseInt(regMatcher.group(13));
            sec = Integer.parseInt(regMatcher.group(14));
            if (month == -1 || day > 31 || hour > 24 || hour == 24 && (sec > 0 || min > 0) || min >= 60 || sec >= 60) // Check if month and clock that matched is valid otherwise return null
                return null;
            year = Integer.parseInt(regMatcher.group(10));
            if (year >= 10 && year <= 99) { // Check if year is in 2 digits and modify as described
                if (year >= 70 && year <= 99) {
                    year = 1900 + year;
                } else {
                    year = 2000 + year;
                }
            }
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            cal.set(year, month, day, hour, min, sec); // Create the Calendar obj
            return cal;
        } else {
            return null;
        }
    }

    /**
     * Search for the all occurrences of text between single quotes, but treating
     * "escaped" quotes ("\'") as normal characters. Return a list containing all
     * the quoted text found (without the quotes, and with the quoted escapes
     * replaced). Allow empty strings. If no quoted text is found, return an empty
     * list. Some examples :
     * <ul>
     * <li>On input "'This is not wrong' and 'this is isn\'t either", the method
     * should return a list containing ("This is not wrong" and "This isn't
     * either").
     * <li>On input "No quoted \'text\' here" the method should return an empty
     * list.
     * </ul>
     * 
     * @param input
     * @return all occurrences of text between single quotes, taking escaped quotes
     *         into account.
     */
    public static List<String> findSingleQuotedTextWithEscapes(String input) {
        List<String> singlequotList = new ArrayList<>();
        input=input.replaceAll("'","#'");// Create with # new definition for 2 kinds of '
        input=input.replaceAll("\\\\#'","\\\\'");
        Pattern regPattren = Pattern.compile("(#')(.*?)(#')"); // Catch the required pattern as described
        Matcher regMatcher = regPattren.matcher(input);
        while (regMatcher.find()) { // Get all relevant pattern in to list if matched
                singlequotList.add(regMatcher.group(2).replaceAll("\\\\'", "'")); // Replace \' in to ' as described
        }
        return singlequotList;
    }

    /**
     * Search for the all occurrences of text between single quotes, but treating
     * "escaped" quotes ("\'") as normal characters. Return a list containing all
     * the quoted text found (without the quotes, and with the quoted escapes
     * replaced). Allow empty strings. If no quoted text is found, return an empty
     * list. Some examples :
     * <ul>
     * <li>On input "'This is not wrong' and 'this is isn\'t either", the method
     * should return a list containing ("This is not wrong" and "This isn't
     * either").
     * <li>On input "No quoted \'text\' here" the method should return an empty
     * list.
     * </ul>
     * 
     * @param input
     * @return all occurrences of text between single quotes, taking escaped quotes
     *         into account.
     */
    public static List<String> findDoubleQuotedTextWithEscapes(String input) {
        List<String> doublequotList = new ArrayList<>();
        input=input.replaceAll("\"","#\""); // Create with # new definition for 2 kinds of " 
        input=input.replaceAll("\\\\#\"","\\\\\"");
        Pattern regPattren = Pattern.compile("(#\")(.*?)(#\")"); // Catch the required pattern as described
        Matcher regMatcher = regPattren.matcher(input);
        while (regMatcher.find()) { // Get all relevant pattern in to list if matched
            doublequotList.add(regMatcher.group(2).replaceAll("\\\\\"", "\"")); // Replace \\" in to \" as described
        }
        return doublequotList;
    }

    /**
     * A class that holds an attribute-value pair.
     * 
     * Attributes are "HTTP tokens": a sequence of non-special non-whitespace
     * characters. The special characters are control characters, space, tab and the
     * characters from the following set:
     * 
     * <pre>
     * ()[]{}'"<>@,;:\/?=
     * </pre>
     * 
     * Values are arbitrary strings and may be missing.
     */
    public static class AVPair {
        public String attr;
        public String value;

        public AVPair(String attr, String value) {
            this.attr = attr;
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || (!(obj instanceof AVPair)))
                return false;
            AVPair other = (AVPair) obj;
            if (attr == null) {
                if (other.attr != null)
                    return false;
            } else if (!attr.equals(other.attr))
                return false;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }

        @Override
        public String toString() {
            if (value != null)
                return attr + "=\"" + value.replaceAll("\"", "\\\"") + "\"";
            else
                return attr;
        }
    }

    /**
     * Parse the input into a list of attribute-value pairs. The input should be a
     * valid attribute-value pair list: attr=value; attr=value; attr; attr=value...
     * If a value exists, it must be either an HTTP token (see {@link AVPair}) or a
     * double-quoted string.
     * 
     * (Note: for the second of the bonus question, implement the
     * {@link RegexpPractice#parseAvPairs2(String)} method.)
     * 
     * @param input
     * @return
     */
    public static List<AVPair> parseAvPairs(String input) {
        List<AVPair> attributeValuePairsList = new ArrayList<>();
        Pattern regPattren = Pattern.compile("((\\s)*(.*?)(\\s)*(((=)(\\s)*(\\\")?(.*?)(\\\")?(\\s)*((;\\s?)|$))|;))"); // Catch the required pattern as described
        Matcher regMatcher = regPattren.matcher(input);
        while (regMatcher.find()) { // Get all relevant pattern in to list if matched
            if (regMatcher.group(10) != null) // check if matched null value
                attributeValuePairsList.add(new AVPair(regMatcher.group(3), regMatcher.group(10).replaceAll("\\\\\"", "\""))); // Replace \\" in to \" as described and add to attr-value list
            else {
                attributeValuePairsList.add(new AVPair(regMatcher.group(3), regMatcher.group(10))); // Add to attr-value list
            }
        }
        return attributeValuePairsList;
    }

    /**
     * Parse the input into a list of attribute-value pairs. The input should be a
     * valid attribute-value pair list: attr=value; attr=value; attr; attr=value...
     * If a value exists, it must be either an HTTP token (see {@link AVPair}) or a
     * double-quoted string.
     * 
     * This method should return null if the input is not a list of attribute-value
     * pairs with the format specified above. (this is for the second part of the
     * bonus question).
     * 
     * @param input
     * @return
     */
    public static List<AVPair> parseAvPairs2(String input) {
        return null;
    }
}
