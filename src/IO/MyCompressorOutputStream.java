package IO;

import algorithms.mazeGenerators.Maze;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;

public class MyCompressorOutputStream extends OutputStream{

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
//        byte[] bArrayDeflated = compress(b);
//        out.write(bArrayDeflated);
        out.write(compressMaze(b));
    }

    private byte[] compressMaze(byte[] b){
        byte[] byteArr = new byte[24 + (int)Math.ceil((b.length-24)/8.0)];
        System.arraycopy(b, 0, byteArr, 0, 24);
        int byteArrIndex = 24;
        byte nextByte = 0x00;
        for (int i = 24; i < b.length;) {

            // 10000000 is -124
            if (1 == b[i]) nextByte += (byte)-128;
            int j = 1;
            i++;

            //now go over the next 7 maze locations
            int divByTwo = 64; // 01000000 is 64, 00100000 is 32...
            for (; j%8 != 0 && i < b.length; j++){
                if (1 == b[i]) nextByte += divByTwo;
                divByTwo /= 2;
                i++;
            }

            byteArr[byteArrIndex] = nextByte;
            byteArrIndex++;
            nextByte = 0x00;
        }
        return byteArr;
    }



}
