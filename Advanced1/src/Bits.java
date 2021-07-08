public class Bits {
    /**
     * Given an 8-byte long composed of the bytes B_1, B_2, ... , B_8, return the
     * long with byte order reversed: B_8, B_7, ..., B_1 The implementation of this
     * method shouldn't use any function calls.
     * 
     * @param a
     *            the number to reverse
     * @return
     */
    public static long byteReverse(long a) {
        long reverseNum=0;
        long currentByte;
        for(int i=0;i<8;i++) {
            reverseNum<<=8;
            //Get the current for replacement
            currentByte=a&0xff;
            reverseNum|=currentByte;
            a>>>=8;
        }
        return reverseNum;
    }

    /**
     * Given a 32-bit integer composed of 32 bits: b_31,b_30,...b_1,b_0, return the
     * integer whose bit representation is
     * b_{31-n},b_{30-n},...,b_1,b_0,b_31,...,b_{31-n+1}. The implementation of this
     * method shouldn't use any control structures (if-then, loops) or function
     * calls.
     * 
     * @param a
     *            the integer that we are rotating left (ROLing)
     * @param n
     *            the number of bits to rotate.
     * @return the ROL of a
     */
    public static int rol(int a, int n) {
        return (a << n | a >>> -n);
    }

    /**
     * Given two 32-bit integers a_31,...,a_0 and b_31,...,b_0, return the 64-bit
     * long that contains their bits interleaved: a_31,b_31,a_30,b_30,...,a_0,b_0.
     * The implementation of this method shouldn't use any function calls.
     * 
     * @param a
     * @param b
     * @return
     */
    public static long interleave(int a, int b) {
        long interleaveNumber = 0;
        long currentBitA, currentBitB;
        //Get lsb from a and b params
        for (int i = 0; i < 32; i++) {
            currentBitA = 1 & ((a >>> i ) % 2);
            currentBitB = 1 & ((b >>> i ) % 2);
            interleaveNumber <<= 1;
            interleaveNumber |= currentBitA;
            interleaveNumber <<= 1;
            interleaveNumber |= currentBitB;
        }
        return interleaveNumber;
    }

    /**
     * Pack several values into a compressed 32-bit representation. The packed
     * representation should contain
     * <table>
     * <tr>
     * <th>bits</th>
     * <th>value</th>
     * </tr>
     * <tr>
     * <td>31</td>
     * <td>1 if b1 is true, 0 otherwise</td>
     * </tr>
     * <tr>
     * <td>30-23</td>
     * <td>the value of the byte a</td>
     * </tr>
     * <tr>
     * <td>22</td>
     * <td>1 if b2 is true, 0 otherwise</td>
     * </tr>
     * <tr>
     * <td>21-6</td>
     * <td>the value of the char c</td>
     * </tr>
     * <tr>
     * <td>5-0</td>
     * <td>the constant binary value 101101</td>
     * </tr>
     * </table>
     * The implementation of this method shouldn't use any control structures
     * (if-then, loops) or function calls (you may use the conditional operator
     * "?:").
     * 
     * @param a
     * @param b1
     * @param b2
     * @param c
     * @return
     */
    public static int packStruct(byte a, boolean b1, boolean b2, char c) {
        int resultNumber=0,b1Bit,aBits,b2Bit,charBits,constBits=0b101101;
        //Copy the 31 Bit
        b1Bit=b1 ? 1 :0;
        resultNumber+=b1Bit;
        resultNumber<<=31;
        //Copy a param the the 23 location
        aBits=a&0xff;
        aBits<<=23;
        resultNumber|=aBits;
        //Copy the 22 bit
        b2Bit=b2 ? 1 :0;
        b2Bit<<=22;
        resultNumber|=b2Bit;
        //Copy the char value
        charBits=c&0xffff;
        charBits<<=6;
        resultNumber|=charBits;
        //Copy the Constant value
        resultNumber|=constBits;
        return resultNumber;
    }

    /**
     * Given a packed struct (with the same format as
     * {@link #packStruct(byte, boolean, boolean, char)}, update its byte value
     * (bits 23-30) to the new value a. The implementation of this method shouldn't
     * use any control structures (if-then, loops) or function calls.
     * 
     * @param struct
     * @param a
     * @return
     */
    public static int updateStruct(int struct, byte a) {
        int intA=a&0xff, newStruct=struct;
        intA<<=23;
        newStruct&=(1<<31)&struct;
        //Modify the 23-30 bit
        newStruct|=(0x7fffff&struct);
        newStruct|=intA;
        return newStruct;
    }
}
