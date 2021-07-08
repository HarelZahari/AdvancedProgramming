import java.util.*;

public class Tests {

    public static void main(String[] args) {
        ArgumentParser argumentParser = new ArgumentParser(args);

        GroupOfTests<UnitTest> allTests = new GroupOfTests<>();

        allTests.addTest(new RolUnitTest());
        allTests.addTest(new ByteReverseUnitTest());
        allTests.addTest(new InterleaveUnitTest());
        allTests.addTest(new PackStructUnitTest());
        allTests.addTest(new UpdateStructUnitTest());
        allTests.addTest(new ConvertFromBaseUnitTest());
        allTests.addTest(new BaseAddUnitTest());
        allTests.addTest(new ConvertToBaseUnitTest());
        allTests.addTest(new BaseNegateUnitTest());

        allTests.run(argumentParser.getReportFlag());
        allTests.printSummary(argumentParser.getSummaryFlag());
    }

    public enum ReportFlag {
        SILENT_REPORT,
        SHORT_REPORT,
        FULL_REPORT
    }

    public enum SummaryFlag {
        NO_SUMMARY,
        FAILED_TESTS,
        ALL_TEST
    }

    private static class ArgumentParser {
        ReportFlag REPORT_FLAG = ReportFlag.SHORT_REPORT;
        SummaryFlag SUMMARY_FLAG = SummaryFlag.FAILED_TESTS;

        final String REPORT_FLAG_STRING_KEY = "--report";
        final String SILENT_REPORT_FLAG_VALUE = "silent";
        final String SHORT_REPORT_FLAG_VALUE = "short";
        final String FULL_REPORT_FLAG_VALUE = "full";

        final String SUMMARY_FLAG_STRING_KEY = "--summary";
        final String NO_SUMMARY_SUMMARY_FLAG_VALUE = "non";
        final String FAILED_TESTS_SUMMARY_FLAG_VALUE = "failed";
        final String ALL_TESTS_SUMMARY_FLAG_VALUE = "all";

        public ArgumentParser(String[] args) {
            List<String> arguments = Arrays.asList(args);
            if (arguments.contains(REPORT_FLAG_STRING_KEY)) {
                ListIterator<String> iterator = arguments.listIterator(arguments.indexOf(REPORT_FLAG_STRING_KEY));

                if (iterator.hasNext()) {
                    iterator.next();
                    switch (iterator.next()) {
                        case SILENT_REPORT_FLAG_VALUE:
                            REPORT_FLAG = ReportFlag.SILENT_REPORT;
                            break;
                        case SHORT_REPORT_FLAG_VALUE:
                            REPORT_FLAG = ReportFlag.SHORT_REPORT;
                            break;
                        case FULL_REPORT_FLAG_VALUE:
                            REPORT_FLAG = ReportFlag.FULL_REPORT;
                            break;
                        default:
                            REPORT_FLAG = ReportFlag.SILENT_REPORT;
                    }
                }
            }

            if (arguments.contains(SUMMARY_FLAG_STRING_KEY)) {
                ListIterator<String> iterator = arguments.listIterator(arguments.indexOf(SUMMARY_FLAG_STRING_KEY));

                if (iterator.hasNext()) {
                    iterator.next();
                    switch (iterator.next()) {
                        case NO_SUMMARY_SUMMARY_FLAG_VALUE:
                            SUMMARY_FLAG = SummaryFlag.NO_SUMMARY;
                            break;
                        case FAILED_TESTS_SUMMARY_FLAG_VALUE:
                            SUMMARY_FLAG = SummaryFlag.FAILED_TESTS;
                            break;
                        case ALL_TESTS_SUMMARY_FLAG_VALUE:
                            SUMMARY_FLAG = SummaryFlag.ALL_TEST;
                            break;
                        default:
                            SUMMARY_FLAG = SummaryFlag.FAILED_TESTS;
                    }
                }
            }
        }

        public ReportFlag getReportFlag() {
            return this.REPORT_FLAG;
        }

        public SummaryFlag getSummaryFlag() {
            return this.SUMMARY_FLAG;
        }
    }

    private static class GlobalVars {
        public static final int LINE_WIDTH = 100;
        public static final int NUMBER_OF_RUNS = 100;
    }

    private static class Numbers {

        public static String getSeparatedBinaryLong(long number, char separator) {
            String binaryString = String.format("%64s", Long.toBinaryString(number)).replace(' ', '0');
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < 8; i++) {
                stringBuilder.append(binaryString.substring(i * 8, i * 8 + 8)).append(i < 7 ? separator : "");
            }
            return stringBuilder.toString();
        }

        public static String getSeparatedBinaryInteger(int number, char separator) {
            String binaryString = String.format("%32s", Integer.toBinaryString(number)).replace(' ', '0');
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < 4; i++) {
                stringBuilder.append(binaryString.substring(i * 8, i * 8 + 8)).append(i < 3 ? separator : "");
            }
            return stringBuilder.toString();
        }

        public static String getSeparatedBinaryChar(char number, char separator) {
            String binaryString = String.format("%16s", Long.toBinaryString(number)).replace(' ', '0');
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < 2; i++) {
                stringBuilder.append(binaryString.substring(i * 8, i * 8 + 8)).append(i < 1 ? separator : "");
            }
            return stringBuilder.toString();
        }

        public static String getBinaryByte(byte number) {

            return String.format("%8s", Integer.toBinaryString(0x000000FF & number)).replace(' ', '0');
        }

        public static String structToSeparatedBinaryString(int struct, char separator) {
            String binaryString = String.format("%32s", Integer.toBinaryString(struct)).replace(' ', '0');

            String result = binaryString.substring(0, 1) + separator +
                    binaryString.substring(1, 9) + separator +
                    binaryString.substring(9, 10) + separator +
                    binaryString.substring(10, 26) + separator +
                    binaryString.substring(26, 32);
            return result;
        }
    }

    private static class UI {
        private static void printLabel(String label, char paddingChar) {
            int paddingWidth = (GlobalVars.LINE_WIDTH - label.length()) / 2;
            String padding = ((Character) paddingChar).toString().repeat(paddingWidth);
            System.out.println(String.format("%s%s%s", padding, " " + label + " ", padding));
        }

        private static void printStringsOnOppositeSides(String left, String right, char separator, char padding, int paddingWidth) {
            int separationLength = GlobalVars.LINE_WIDTH - (left.length() + right.length() + paddingWidth * 2);
            String paddingString = ((Character) padding).toString().repeat(paddingWidth);
            String separationString = ((Character) separator).toString().repeat(separationLength);
            System.out.println(String.format("%s%s%s%s%s", paddingString, left, separationString, right, paddingString));
        }
    }

    private interface TestCase {
        boolean isPassed();

        void run(ReportFlag reportFlag);

        void printSummary();
    }

    private interface UnitTest {
        boolean isPassed();

        void run(ReportFlag reportFlag);

        void printSummary(SummaryFlag summaryFlag);
    }

    private static class GroupOfTests<T extends UnitTest> {
        private java.util.LinkedList<T> unitTests = new java.util.LinkedList<>();
        private java.util.LinkedList<T> failedUnitTests = new java.util.LinkedList<>();
        private java.util.LinkedList<T> succeededUnitTests = new java.util.LinkedList<>();

        public void addTest(T unitTest) {
            unitTests.add(unitTest);
        }

        public void run(ReportFlag reportFlag) {
            if (reportFlag != ReportFlag.SILENT_REPORT) {
                System.out.println();
                UI.printLabel("TESTING", '/');
            }

            for (T unitTest : unitTests) {
                unitTest.run(reportFlag);
                if (!unitTest.isPassed()) failedUnitTests.add(unitTest);
                else succeededUnitTests.add(unitTest);
            }

            if (reportFlag != ReportFlag.SILENT_REPORT) {
                System.out.println();
                System.out.println();
                System.out.println();
            }
        }

        public boolean isPassed() {
            return failedUnitTests.isEmpty();
        }

        private void printFailedTestsSummary(SummaryFlag summaryFlag) {
            printTests(this.failedUnitTests, summaryFlag);
        }

        private void printSucceededTestsSummary(SummaryFlag summaryFlag) {
            printTests(this.succeededUnitTests, summaryFlag);
        }

        private void printTests(java.util.LinkedList<T> tests, SummaryFlag summaryFlag) {
            for (T test : tests) {
                test.printSummary(summaryFlag);
            }
        }

        public void printSummary(SummaryFlag summaryFlag) {

            if (summaryFlag != SummaryFlag.NO_SUMMARY) {
                UI.printLabel("TESTING SUMMARY", '/');

                switch (summaryFlag) {
                    case ALL_TEST:
                        this.printSucceededTestsSummary(summaryFlag);
                        this.printFailedTestsSummary(summaryFlag);
                        break;
                    case FAILED_TESTS:
                        if (this.isPassed()) {
                            System.out.println("All tests have passed.");
                        } else {
                            System.out.println("Some tests have failed.");
                            this.printFailedTestsSummary(summaryFlag);
                        }
                        break;
                }
            }
        }
    }

    private static abstract class SimpleFunctionTest<T extends TestCase> implements UnitTest {
        String label;
        java.util.LinkedList<T> testCases = new java.util.LinkedList<>();
        java.util.LinkedList<T> failedTestCases = new java.util.LinkedList<>();
        java.util.LinkedList<T> succeededTestCases = new java.util.LinkedList<>();

        public SimpleFunctionTest(String label) {
            this.label = label;
        }

        public boolean isPassed() {
            return failedTestCases.isEmpty();
        }

        private void printFailedTestCases() {
            for (T testCase : failedTestCases) {
                testCase.printSummary();
            }
        }

        private void printSucceededTestCases() {
            for (T testCase : succeededTestCases) {
                testCase.printSummary();
            }
        }

        public void printSummary(SummaryFlag summaryFlag) {
            System.out.println();
            UI.printLabel(label, '<');
            switch (summaryFlag) {
                case ALL_TEST:
                    printSucceededTestCases();
                case FAILED_TESTS:
                    printFailedTestCases();
                    break;
            }
        }

        public void run(ReportFlag reportFlag) {

            switch (reportFlag) {
                case FULL_REPORT:
                    System.out.println();
                    UI.printLabel(String.format("Testing %s", this.label), '<');
                    break;
                case SHORT_REPORT:
                    System.out.println();
                    System.out.printf("Testing %s\n", label);
            }
            for (T testCase : testCases) {

                testCase.run(reportFlag);

                if (!testCase.isPassed()) {
                    failedTestCases.add(testCase);
                } else {
                    succeededTestCases.add(testCase);
                }
            }
        }
    }

    private static class RolUnitTest extends SimpleFunctionTest<TestCase> {
        public RolUnitTest() {
            super("rol");
            this.testCases.add(new EvenBytesAreOnesOddBytesAreZerosRol13());
            this.testCases.add(new FirstHalfIsZerosTheSecondIsOnesRol7());
            this.testCases.add(new FirstHalfIsOnesTheSecondIsZerosRol5());
            this.testCases.add(new RolNumber64bits());
            this.testCases.add(new RolNumber32bits());
            this.testCases.add(new RolMultipleRunTestCase());
        }
    }

    private static abstract class SimpleFunctionTestCase implements TestCase {

        String label;
        String summary = "There is no summary for this test case, because this test case wasn't tested.";
        protected Boolean passed = false;

        public SimpleFunctionTestCase(String label) {
            this.label = label;
        }

        public boolean isPassed() {
            return passed;
        }

        abstract public void run(ReportFlag reportFlag);

        public void printSummary() {
            System.out.println();
            UI.printLabel(label, '-');
            System.out.println(summary);
        }

        protected void printRunningReport(ReportFlag reportFlag) {
            switch (reportFlag) {
                case FULL_REPORT:
                    System.out.println();
                    UI.printLabel("Test case " + label, '-');
                    System.out.println(summary);
                    break;
                case SHORT_REPORT:
                    UI.printStringsOnOppositeSides(String.format("Test case: %s", label), (passed ? "passed" : "FAILED"), '.', ' ', 5);
            }
        }
    }

    private static abstract class SingleRunOfTheFunctionWithGivenInputTestCase<ResultType> extends SimpleFunctionTestCase {
        ResultType expected;
        ResultType result;

        public SingleRunOfTheFunctionWithGivenInputTestCase(String label) {
            super(label);
        }

        public void run(ReportFlag reportFlag) {

            this.passed = result.equals(expected);

            this.summary = this.generateSummary();

            printRunningReport(reportFlag);
        }

        abstract protected String generateSummary();
    }

    private static abstract class MultipleFunctionRunsWithRandomInputComparedToBuiltInFunctionTestCase<ResultType> extends SimpleFunctionTestCase {
        ArrayList<ResultType> results = new ArrayList<>();
        ArrayList<ResultType> expected = new ArrayList<>();
        ArrayList<Boolean> passedArray = new ArrayList<>();

        public MultipleFunctionRunsWithRandomInputComparedToBuiltInFunctionTestCase(String label) {
            super(label);
        }

        public void run(ReportFlag reportFlag) {

            passed = passedArray.stream().reduce(Boolean::logicalAnd).get();

            this.summary = generateSummary();

            this.printRunningReport(reportFlag);
        }

        abstract protected String generateSummary();
    }

    private static class RolSingleRunTestCase extends SingleRunOfTheFunctionWithGivenInputTestCase<Integer> {
        int number;
        int rol;

        public RolSingleRunTestCase(String label) {
            super(label);
        }

        public void run(ReportFlag reportFlag) {

            this.result = Bits.rol(this.number, this.rol);
            super.run(reportFlag);
        }

        protected String generateSummary() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Test case have %s.\n", (passed ? "passed" : "FAILED")));
            stringBuilder.append(String.format("Bits to rol:      %d\n", rol));
            stringBuilder.append(String.format("Number in binary: %s\n", Numbers.getSeparatedBinaryInteger(number, ' ')));
            stringBuilder.append(String.format("Expected:         %s\n", Numbers.getSeparatedBinaryInteger(expected, ' ')));
            stringBuilder.append(String.format("Output:           %s", Numbers.getSeparatedBinaryInteger(result, ' ')));

            return stringBuilder.toString();
        }

    }

    private static class EvenBytesAreOnesOddBytesAreZerosRol13 extends RolSingleRunTestCase {

        public EvenBytesAreOnesOddBytesAreZerosRol13() {
            super("Even bytes are all 1, odd bytes are 0, rol 13");
            this.number = 0xFF00_FF00;
            this.rol = 13;
            this.expected = 0x1FE0_1FE0;
        }
    }

    private static class FirstHalfIsOnesTheSecondIsZerosRol5 extends RolSingleRunTestCase {

        public FirstHalfIsOnesTheSecondIsZerosRol5() {
            super("First half of the number is 1, the second half is 0, rol 5 bits");
            this.number = 0xFFFF_0000;
            this.rol = 5;
            this.expected = 0xFFE0_001F;
        }
    }

    private static class FirstHalfIsZerosTheSecondIsOnesRol7 extends RolSingleRunTestCase {

        public FirstHalfIsZerosTheSecondIsOnesRol7() {
            super("First half of the number is 0, the second half is 1, rol 7 bits");
            this.number = 0x0000_FFFF;
            this.rol = 7;
            this.expected = 0x007F_FF80;
        }
    }

    private static class RolNumber32bits extends RolSingleRunTestCase {

        public RolNumber32bits() {
            super("Rol number 0xFF0000FF 32 bits (should return the same number)");
            this.number = 0xFF00_00FF;
            this.rol = 32;
            this.expected = 0xFF00_00FF;
        }
    }

    private static class RolNumber64bits extends RolSingleRunTestCase {

        public RolNumber64bits() {
            super("Rol number 0xFF0000FF 64 bits (should return the same number)");
            this.number = 0xFF00_00FF;
            this.rol = 64;
            this.expected = 0xFF00_00FF;
        }
    }

    private static class RolMultipleRunTestCase extends MultipleFunctionRunsWithRandomInputComparedToBuiltInFunctionTestCase<Integer> {
        ArrayList<Integer> inputs = new ArrayList<>();
        ArrayList<Integer> rols = new ArrayList<>();

        public RolMultipleRunTestCase() {
            super(String.format("Run rol %d times and compare result to Java built-in function", GlobalVars.NUMBER_OF_RUNS));
        }

        public void run(ReportFlag reportFlag) {
            Random randomizer = new Random();
            for (int i = 0; i < GlobalVars.NUMBER_OF_RUNS; i++) {
                int randomNumber = randomizer.nextInt();
                inputs.add(i, randomNumber);
                int rol = randomizer.nextInt(100);
                rols.add(i, rol);
                Integer currentResult = Bits.rol(randomNumber, rol);
                Integer currentExpected = Integer.rotateLeft(randomNumber, rol);
                this.results.add(i, currentResult);
                this.expected.add(i, currentExpected);
                passedArray.add(i, currentExpected.equals(currentResult));
            }

            super.run(reportFlag);
        }

        protected String generateSummary() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Test case have %s.\n", (passed ? "passed" : "FAILED")));
            stringBuilder.append(String.format("The number of times the function was ran:  %d\n", GlobalVars.NUMBER_OF_RUNS));
            stringBuilder.append(String.format("Number of succeeded test runs:             %d\n", this.passedArray.stream().filter(b -> b.equals(true)).count()));
            stringBuilder.append(String.format("Number of failed test runs:                %d\n", this.passedArray.stream().filter(b -> b.equals(false)).count()));
            stringBuilder.append("Here is the  5 first failed runs:\n");

            if (this.passedArray.stream().anyMatch(b -> b.equals(false))) {
                for (int i = 0; i < 5; i++) {
                    stringBuilder.append('\n');
                    stringBuilder.append(String.format("Rol %d bits\n", rols.get(i)));
                    stringBuilder.append(String.format("Input:    %s\n", Numbers.getSeparatedBinaryInteger(this.inputs.get(i), ' ')));
                    stringBuilder.append(String.format("Expected: %s\n", Numbers.getSeparatedBinaryInteger(this.expected.get(i), ' ')));
                    stringBuilder.append(String.format("Result:   %s\n", Numbers.getSeparatedBinaryInteger(this.results.get(i), ' ')));
                }
            }

            return stringBuilder.toString();
        }

    }

    private static class ByteReverseUnitTest extends SimpleFunctionTest<TestCase> {

        public ByteReverseUnitTest() {
            super("byteReverse");
            this.testCases.add(new BytesNumberedFromOneToEight());
            this.testCases.add(new MSBofInputIsOne());
            this.testCases.add(new ByteOnEvenIndexAreOnesBytesOnOddIndexAreAllZero());
            this.testCases.add(new SomeBytesHaveOneInMSB());
            this.testCases.add(new ByteReverseMultipleRunTestCase());
        }

    }

    private static abstract class ByteReverseSingleRunTestCase extends SingleRunOfTheFunctionWithGivenInputTestCase<Long> {
        long input;

        public ByteReverseSingleRunTestCase(String label) {
            super(label);
        }

        public void run(ReportFlag reportFlag) {

            this.result = Bits.byteReverse(this.input);
            super.run(reportFlag);
        }

        protected String generateSummary() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Test case have %s.\n", (passed ? "passed" : "FAILED")));
            stringBuilder.append(String.format("Input:    %s\n", Numbers.getSeparatedBinaryLong(input, ' ')));
            stringBuilder.append(String.format("Expected: %s\n", Numbers.getSeparatedBinaryLong(expected, ' ')));
            stringBuilder.append(String.format("Output:   %s", Numbers.getSeparatedBinaryLong(result, ' ')));

            return stringBuilder.toString();
        }
    }

    public static class BytesNumberedFromOneToEight extends ByteReverseSingleRunTestCase {

        public BytesNumberedFromOneToEight() {
            super("Bytes numbered from 1 to 8 - 0x0807_0605_0403_0201");
            this.input = 0x0807_0605_0403_0201L;
            this.expected = 0x0102_0304_0506_0708L;
        }
    }

    public static class SomeBytesHaveOneInMSB extends ByteReverseSingleRunTestCase {

        public SomeBytesHaveOneInMSB() {
            super("Some bytes have 1 in MSB");
            this.input = 0x8007_8005_8003_8001L;
            this.expected = 0x0180_0380_0580_0780L;
        }
    }

    public static class MSBofInputIsOne extends ByteReverseSingleRunTestCase {

        public MSBofInputIsOne() {
            super("MSB of the input is 1");
            this.input = 0x8000_0000_0000_0000L;
            this.expected = 0x0000_0000_0000_0080L;
        }
    }

    public static class ByteOnEvenIndexAreOnesBytesOnOddIndexAreAllZero extends ByteReverseSingleRunTestCase {

        public ByteOnEvenIndexAreOnesBytesOnOddIndexAreAllZero() {
            super("Bytes in even places are all 1, bytes on odd places are all 0");
            this.input = 0xFF00_FF00_FF00_FF00L;
            this.expected = 0x00FF_00FF_00FF_00FFL;
        }
    }

    public static class ByteReverseMultipleRunTestCase extends MultipleFunctionRunsWithRandomInputComparedToBuiltInFunctionTestCase<Long> {
        ArrayList<Long> inputs = new ArrayList<>();

        public ByteReverseMultipleRunTestCase() {
            super(String.format("Run byteReverse %d times and compare result to Java built-in function", GlobalVars.NUMBER_OF_RUNS));
        }

        public void run(ReportFlag reportFlag) {
            Random randomizer = new Random();
            for (int i = 0; i < GlobalVars.NUMBER_OF_RUNS; i++) {
                long randomNumber = randomizer.nextLong();
                inputs.add(i, randomNumber);
                Long currentResult = Bits.byteReverse(randomNumber);
                Long currentExpected = Long.reverseBytes(randomNumber);
                this.results.add(i, currentResult);
                this.expected.add(i, currentExpected);
                passedArray.add(i, currentExpected.equals(currentResult));
            }

            super.run(reportFlag);
        }

        protected String generateSummary() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Test case have %s.\n", (passed ? "passed" : "FAILED")));
            stringBuilder.append(String.format("The number of times the function was ran:  %d\n", GlobalVars.NUMBER_OF_RUNS));
            stringBuilder.append(String.format("Number of succeeded test runs:             %d\n", this.passedArray.stream().filter(b -> b.equals(true)).count()));
            stringBuilder.append(String.format("Number of failed test runs:                %d\n", this.passedArray.stream().filter(b -> b.equals(false)).count()));
            if (this.passedArray.stream().anyMatch(b -> b.equals(false))) {
                stringBuilder.append("Here is the  5 first failed runs:\n");

                for (int i = 0; i < 5; i++) {
                    stringBuilder.append('\n');
                    stringBuilder.append(String.format("Input:    %s\n", Numbers.getSeparatedBinaryLong(this.inputs.get(i), ' ')));
                    stringBuilder.append(String.format("Expected: %s\n", Numbers.getSeparatedBinaryLong(this.expected.get(i), ' ')));
                    stringBuilder.append(String.format("Result:   %s\n", Numbers.getSeparatedBinaryLong(this.results.get(i), ' ')));
                }
            }

            return stringBuilder.toString();
        }


    }

    private static class InterleaveUnitTest extends SimpleFunctionTest<InterleaveSingleRunTestCase> {
        public InterleaveUnitTest() {
            super("interleave");
            this.testCases.add(new FirstIntegerIsOnesAnotherIsZeros());
            this.testCases.add(new FirstIntegerIsZerosAnotherIsOnes());
        }
    }

    private static class InterleaveSingleRunTestCase extends SingleRunOfTheFunctionWithGivenInputTestCase<Long> {
        int integer1;
        int integer2;

        public InterleaveSingleRunTestCase(String label) {
            super(label);
        }

        public void run(ReportFlag reportFlag) {

            this.result = Bits.interleave(this.integer1, this.integer2);
            super.run(reportFlag);
        }

        protected String generateSummary() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Test case have %s.\n", (passed ? "passed" : "FAILED")));
            stringBuilder.append(String.format("Input1:   %s\n", Numbers.getSeparatedBinaryInteger(integer1, ' ')));
            stringBuilder.append(String.format("Input2:   %s\n", Numbers.getSeparatedBinaryInteger(integer2, ' ')));
            stringBuilder.append(String.format("Expected: %s\n", Numbers.getSeparatedBinaryLong(expected, ' ')));
            stringBuilder.append(String.format("Output:   %s", Numbers.getSeparatedBinaryLong(result, ' ')));

            return stringBuilder.toString();
        }
    }

    private static class FirstIntegerIsOnesAnotherIsZeros extends InterleaveSingleRunTestCase {
        public FirstIntegerIsOnesAnotherIsZeros() {
            super("First int contains only 1, second int only 0");
            this.integer1 = 0xFFFF_FFFF;
            this.integer2 = 0x0000_0000;
            this.expected = 0xAAAA_AAAA_AAAA_AAAAL;
        }
    }

    private static class FirstIntegerIsZerosAnotherIsOnes extends InterleaveSingleRunTestCase {
        public FirstIntegerIsZerosAnotherIsOnes() {
            super("First int contains only 0, second int only 1");
            this.integer1 = 0x0000_0000;
            this.integer2 = 0xFFFF_FFFF;
            this.expected = 0x5555_5555_5555_5555L;
        }
    }

    private static class PackStructUnitTest extends SimpleFunctionTest<PackStructSingleRunTestCase> {

        public PackStructUnitTest() {
            super("packStruct");
            this.testCases.add(new B1isTrueB2isTrueCharStartsWithOneByteStartWithOne());
            this.testCases.add(new B1isFalseB2isFalseCharIs0x0FF0ByteIs0x55());
        }

    }

    private static abstract class PackStructSingleRunTestCase extends SingleRunOfTheFunctionWithGivenInputTestCase<Integer> {
        byte aByte;
        boolean aBoolean1;
        boolean aBoolean2;
        char aChar;

        public PackStructSingleRunTestCase(String label) {
            super(label);
        }

        public void run(ReportFlag reportFlag) {

            this.result = Bits.packStruct(aByte, aBoolean1, aBoolean2, aChar);
            super.run(reportFlag);
        }

        protected String generateSummary() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Test case have %s.\n", (passed ? "passed" : "FAILED")));
            stringBuilder.append(String.format("Inputs:\n     Bool 1: %s\n     Byte:   %s\n     Bool 2: %s\n     Char:   %s\n", aBoolean1, Numbers.getBinaryByte(aByte), aBoolean2, Numbers.getSeparatedBinaryChar(aChar, ' ')));
            stringBuilder.append(String.format("Expected: %s\n", Numbers.structToSeparatedBinaryString(expected, ' ')));
            stringBuilder.append(String.format("Output:   %s", Numbers.structToSeparatedBinaryString(result, ' ')));

            return stringBuilder.toString();
        }

    }

    public static class B1isTrueB2isTrueCharStartsWithOneByteStartWithOne extends PackStructSingleRunTestCase {

        public B1isTrueB2isTrueCharStartsWithOneByteStartWithOne() {
            super("b1 = true, b2 = true, char = 0xF00F, byte = 0x99");
            this.aChar = 0xF00F;
            this.aBoolean1 = true;
            this.aBoolean2 = true;
            this.aByte = (byte) 0x99;
            this.expected = 0xCCFC_03ED;
        }

    }

    public static class B1isFalseB2isFalseCharIs0x0FF0ByteIs0x55 extends PackStructSingleRunTestCase {

        public B1isFalseB2isFalseCharIs0x0FF0ByteIs0x55() {
            super("b1 = false, b2 = false, char = 0x0FF0, byte = 0x55");
            this.aChar = 0x0FF0;
            this.aBoolean1 = false;
            this.aBoolean2 = false;
            this.aByte = (byte) 0x55;
            this.expected = 0x2A83_FC2D;
        }

    }

    private static class UpdateStructUnitTest extends SimpleFunctionTest<UpdateStructSingleRunTestCase> {

        public UpdateStructUnitTest() {
            super("updateStruct");
            this.testCases.add(new ByteStartsWithOneBooleansFalseChar0xF00F());
            this.testCases.add(new ByteStartsWithOneBooleansTrueChar0x0FF0());
            this.testCases.add(new ByteStartsWithZeroBooleansTrueChar0x0FF0());
        }

    }

    private static abstract class UpdateStructSingleRunTestCase extends SingleRunOfTheFunctionWithGivenInputTestCase<Integer> {

        byte input;
        int struct;

        public UpdateStructSingleRunTestCase(String label) {
            super(label);
        }


        public void run(ReportFlag reportFlag) {

            this.result = Bits.updateStruct(this.struct, this.input);
            super.run(reportFlag);
        }

        protected String generateSummary() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Test case have %s.\n", (passed ? "passed" : "FAILED")));
            stringBuilder.append(String.format("Input byte:    %s\n", Numbers.getBinaryByte(this.input)));
            stringBuilder.append(String.format("Input struct:  %s\n", Numbers.structToSeparatedBinaryString(struct, ' ')));
            stringBuilder.append(String.format("Expected:      %s\n", Numbers.structToSeparatedBinaryString(expected, ' ')));
            stringBuilder.append(String.format("Output:        %s", Numbers.structToSeparatedBinaryString(result, ' ')));

            return stringBuilder.toString();
        }
    }

    public static class ByteStartsWithOneBooleansFalseChar0xF00F extends UpdateStructSingleRunTestCase {

        public ByteStartsWithOneBooleansFalseChar0xF00F() {
            super("Input byte starts with 1, booleans are false, char is 0xF00F");
            this.input = (byte) 0x81;
            this.struct = 0x083C_03ED;
            this.expected = 0x40BC_03ED;
        }

    }

    public static class ByteStartsWithOneBooleansTrueChar0x0FF0 extends UpdateStructSingleRunTestCase {

        public ByteStartsWithOneBooleansTrueChar0x0FF0() {
            super("Input byte starts with 1, booleans are true, char is 0x0FF0");
            this.input = (byte) 0x81;
            this.struct = 0xAAC3_FC2D;
            this.expected = 0xC0C3_FC2D;
        }
    }

    public static class ByteStartsWithZeroBooleansTrueChar0x0FF0 extends UpdateStructSingleRunTestCase {

        public ByteStartsWithZeroBooleansTrueChar0x0FF0() {
            super("Input byte starts with 0, booleans are true, char is 0x0FF0");
            this.input = (byte) 0x3C;
            this.struct = 0xAAC3_FC2D;
            this.expected = 0x9E43_FC2D;
        }
    }

    private static class ConvertFromBaseUnitTest extends SimpleFunctionTest<ConvertFromBaseSingleRunTestCase> {

        public ConvertFromBaseUnitTest() {
            super("ConvertFromBase");
            this.testCases.add(new ConvertFromBinary());
            this.testCases.add(new ConvertFromOctal());
            this.testCases.add(new ConvertFromHex());
        }

    }

    private static abstract class ConvertFromBaseSingleRunTestCase extends SingleRunOfTheFunctionWithGivenInputTestCase<Long> {
        int[] digits;
        int base;

        public ConvertFromBaseSingleRunTestCase(String label) {
            super(label);
        }

        public void run(ReportFlag reportFlag) {

            this.result = Bases.convertFromBase(digits, base);
            super.run(reportFlag);
        }

        protected String generateSummary() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Test case have %s.\n", (passed ? "passed" : "FAILED")));
            stringBuilder.append(String.format("Input array:    %s\nBase: %d\n", Arrays.toString(digits), base));
            stringBuilder.append(String.format("Expected: %s\n", expected));
            stringBuilder.append(String.format("Output:   %s", result));

            return stringBuilder.toString();
        }
    }

    public static class ConvertFromBinary extends ConvertFromBaseSingleRunTestCase {

        public ConvertFromBinary() {
            super("Convert 0b10101100111000 from binary");
            this.digits = new int[]{0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1};
            this.base = 2;
            this.expected = 0b10101100111000L;
        }
    }

    public static class ConvertFromOctal extends ConvertFromBaseSingleRunTestCase {

        public ConvertFromOctal() {
            super("Convert 07531642 from octal");
            this.digits = new int[]{2, 4, 6, 1, 3, 5, 7};
            this.base = 8;
            this.expected = 07531642L;
        }
    }

    public static class ConvertFromHex extends ConvertFromBaseSingleRunTestCase {

        public ConvertFromHex() {
            super("Convert 0x0123456789ABCDEF from Hex");
            this.digits = new int[]{15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
            this.base = 16;
            this.expected = 0x0123_4567_89AB_CDEFL;
        }
    }

    private static class BaseAddUnitTest extends SimpleFunctionTest<BaseAddSingleRunTestCase> {

        public BaseAddUnitTest() {
            super("BaseAdd");
            this.testCases.add(new AddTwoBinaryNumbers());
            this.testCases.add(new AddTwoOctalNumbers());
            this.testCases.add(new AddTwoHexNumbers());
        }

    }

    private static abstract class BaseAddSingleRunTestCase extends SingleRunOfTheFunctionWithGivenInputTestCase<int[]> {
        int[] aDigits;
        int[] bDigits;
        int[] outDigits;
        int base;

        public BaseAddSingleRunTestCase(String label) {
            super(label);
        }

        public void run(ReportFlag reportFlag) {

            Bases.baseAdd(base, aDigits, bDigits, outDigits);
            this.result = outDigits;

            this.passed = Arrays.equals(result, expected);

            this.summary = this.generateSummary();

            printRunningReport(reportFlag);
        }

        protected String generateSummary() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Test case have %s.\n", (passed ? "passed" : "FAILED")));
            stringBuilder.append(String.format("Base:    %s\n", base));
            stringBuilder.append(String.format("First number:    %s\n", Arrays.toString(aDigits)));
            stringBuilder.append(String.format("Second number:   %s\n", Arrays.toString(bDigits)));
            stringBuilder.append(String.format("Expected:        %s\n", Arrays.toString(expected)));
            stringBuilder.append(String.format("Output:          %s", Arrays.toString(result)));

            return stringBuilder.toString();
        }
    }

    public static class AddTwoBinaryNumbers extends BaseAddSingleRunTestCase {

        public AddTwoBinaryNumbers() {
            super("Add two binary numbers 0b011010 and 0b011001");
            this.outDigits = new int[6];
            this.base = 2;
            this.aDigits = new int[]{0, 1, 0, 1, 1, 0};
            this.bDigits = new int[]{1, 0, 0, 1, 1, 0};
            this.expected = new int[]{1, 1, 0, 0, 1, 1};
        }
    }

    public static class AddTwoOctalNumbers extends BaseAddSingleRunTestCase {

        public AddTwoOctalNumbers() {
            super("Add two octal numbers 043574 and 005627");
            this.outDigits = new int[6];
            this.base = 8;
            this.aDigits = new int[]{4, 7, 5, 3, 4, 0};
            this.bDigits = new int[]{7, 2, 6, 5, 0, 0};
            this.expected = new int[]{3, 2, 4, 1, 5, 0};
        }
    }

    public static class AddTwoHexNumbers extends BaseAddSingleRunTestCase {

        public AddTwoHexNumbers() {
            super("Add two octal numbers 0xA3D71AF5 and 0x2C38A871");
            this.outDigits = new int[8];
            this.base = 16;
            this.aDigits = new int[]{5, 15, 10, 1, 7, 13, 3, 10};
            this.bDigits = new int[]{1, 7, 8, 10, 8, 3, 12, 2};
            this.expected = new int[]{6, 6, 3, 12, 15, 0, 0, 13};
        }
    }

    private static class ConvertToBaseUnitTest extends SimpleFunctionTest<TestCase> {

        public ConvertToBaseUnitTest() {
            super("ConvertToBase");
            this.testCases.add(new ConvertToBinary());
            this.testCases.add(new ConvertToOctal());
            this.testCases.add(new ConvertToBase100());
        }

    }

    private static abstract class ConvertToBaseSingleRunTestCase extends SingleRunOfTheFunctionWithGivenInputTestCase<int[]> {
        int[] digits;
        int base;
        long input;

        public ConvertToBaseSingleRunTestCase(String label) {
            super(label);
        }

        public void run(ReportFlag reportFlag) {
            Bases.convertToBase(input, base, digits);
            this.result = digits;
            this.passed = Arrays.equals(result, expected);

            this.summary = this.generateSummary();

            printRunningReport(reportFlag);
        }

        protected String generateSummary() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Test case have %s.\n", (passed ? "passed" : "FAILED")));
            stringBuilder.append(String.format("Input number: %d, Base: %d\n", input, base));
            stringBuilder.append(String.format("Expected: %s\n", Arrays.toString(expected)));
            stringBuilder.append(String.format("Output:   %s", Arrays.toString(result)));

            return stringBuilder.toString();
        }
    }

    public static class ConvertToBinary extends ConvertToBaseSingleRunTestCase {
        public ConvertToBinary() {
            super("Convert 61642 to binary");
            this.digits = new int[16];
            this.input = 0xF0CA;
            this.base = 2;
            this.expected = new int[]{0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1};
        }
    }

    public static class ConvertToOctal extends ConvertToBaseSingleRunTestCase {

        public ConvertToOctal() {
            super("Convert 16434824 to octal");
            this.digits = new int[8];
            this.input = 076543210;
            this.base = 8;
            this.expected = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        }
    }

    public static class ConvertToBase100 extends ConvertToBaseSingleRunTestCase {

        public ConvertToBase100() {
            super("Convert 1535804598412347 to octal");
            this.digits = new int[9];
            this.input = 155804584123532104L;
            this.base = 100;
            this.expected = new int[]{4, 21, 53, 23, 41, 58, 4, 58, 15};
        }
    }

    private static class BaseNegateUnitTest extends SimpleFunctionTest<BaseNegateSingleRunTestCase> {

        public BaseNegateUnitTest() {
            super("BaseNegate");
            this.testCases.add(new Negate365InBase10());
            this.testCases.add(new Negate0b1010110011InBase2());
            this.testCases.add(new Negate0x7AF0InBase16());
        }

    }

    private static abstract class BaseNegateSingleRunTestCase extends SingleRunOfTheFunctionWithGivenInputTestCase<int[]> {
        int[] digits;
        int[] outDigits;
        int base;

        public BaseNegateSingleRunTestCase(String label) {
            super(label);
        }

        public void run(ReportFlag reportFlag) {

            Bases.baseNegate(base, digits, outDigits);
            this.result = outDigits;
            this.passed = Arrays.equals(result, expected);
            this.summary = this.generateSummary();

            printRunningReport(reportFlag);
        }

        protected String generateSummary() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Test case have %s.\n", (passed ? "passed" : "FAILED")));
            stringBuilder.append(String.format("Base:    %s\n", base));
            stringBuilder.append(String.format("Input number:    %s\n", Arrays.toString(digits)));
            stringBuilder.append(String.format("Expected:        %s\n", Arrays.toString(expected)));
            stringBuilder.append(String.format("Output:          %s", Arrays.toString(result)));

            return stringBuilder.toString();
        }
    }

    public static class Negate365InBase10 extends BaseNegateSingleRunTestCase {

        public Negate365InBase10() {
            super("Negate number 365 in base 10");
            this.outDigits = new int[3];
            this.base = 10;
            this.digits = new int[]{5, 6, 3};
            this.expected = new int[]{5, 3, 6};
        }
    }

    public static class Negate0b1010110011InBase2 extends BaseNegateSingleRunTestCase {

        public Negate0b1010110011InBase2() {
            super("Negate number b1010110011 in base 2");
            this.outDigits = new int[10];
            this.base = 2;
            this.digits = new int[]{1, 1, 0, 0, 1, 1, 0, 1, 0, 1};
            this.expected = new int[]{1, 0, 1, 1, 0, 0, 1, 0, 1, 0};
        }
    }

    public static class Negate0x7AF0InBase16 extends BaseNegateSingleRunTestCase {

        public Negate0x7AF0InBase16() {
            super("Negate 0x7AF0 in base 16");
            this.outDigits = new int[4];
            this.base = 16;
            this.digits = new int[]{0, 15, 10, 7};
            this.expected = new int[]{0, 1, 5, 8};
        }
    }
}