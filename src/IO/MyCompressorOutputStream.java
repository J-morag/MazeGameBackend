package IO;

import algorithms.mazeGenerators.Maze;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;

public class MyCompressorOutputStream extends OutputStream{

    private OutputStream out;
    private Deflater deflater;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
        this.deflater = new Deflater();
    }

    @Override
    public void write(int b) throws IOException {
        write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
//        byte[] bArrayDeflated = compress(b);
//        out.write(bArrayDeflated);
        out.write(b);
    }

    private byte[] compress(byte[] b) throws IOException {
        deflater.setInput(b);
        ByteArrayOutputStream os = new ByteArrayOutputStream(b.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            os.write(buffer, 0, count);
        }
        os.close();
        return os.toByteArray();
    }

//    /**
//     * converts integer to 2's complement array of bytes. byte[1] contains the MSB.
//     * @param integer - integer to convert.
//     * @return byte array size 4 representing the integer.
//     */
//    private static byte[] intToByteArray(int integer){
//        return ByteBuffer.allocate(4).putInt(integer).array();
//    }

}
