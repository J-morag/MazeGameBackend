package IO;

import algorithms.mazeGenerators.Maze;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        int compressedSize = super.read(b);
        byte[] compressedMaze = new byte[compressedSize];
        System.arraycopy(b, 0, compressedMaze, 0, compressedSize);
        byte[] decompressedMaze = decompressMaze(compressedMaze);
        System.arraycopy(decompressedMaze, 0, b, 0, decompressedMaze.length);
        return decompressedMaze.length;
    }


    /**
     * @param compressedBytes - compressed maze
     * @return a decompressed maze
     */
    private byte[] decompressMaze(byte[] compressedBytes){

        byte[] rowsB = new byte[4];
        byte[] columnsB = new byte[4];

        System.arraycopy(compressedBytes, 0, rowsB, 0, 4);
        System.arraycopy(compressedBytes, 4, columnsB, 0, 4);

        int rows = Maze.byteArrayToInt(rowsB);
        int columns = Maze.byteArrayToInt(columnsB);

        byte[] decompressedBytes = new byte[24 + rows*columns];
        System.arraycopy(compressedBytes, 0 , decompressedBytes, 0, 24);
        int byteArrIndex = 24;
        for (int i = 24; i < compressedBytes.length; i++) {

            boolean MSB_isOn = ( compressedBytes[i] & (byte)-128 ) != 0;
            if (MSB_isOn) decompressedBytes[byteArrIndex] = 1;
            int j = 1;
            byteArrIndex++;

            //now go over the next 7 maze locations
            int divMeByTwo = 64; // 01000000 is 64, 00100000 is 32...
            for (; j%8 != 0 && i < compressedBytes.length; j++){ //notice  i < compressedBytes.length is i not j
                boolean bitIsOn = ( compressedBytes[i] & (byte)divMeByTwo ) != 0;
                if (bitIsOn)
                    decompressedBytes[byteArrIndex] = 1;
                divMeByTwo /= 2;
                byteArrIndex++;
            }
        }
        return decompressedBytes;
    }
}
