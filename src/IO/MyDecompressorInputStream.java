package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;
    private Inflater inflater;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
        this.inflater = new Inflater();
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return super.read(b);
//        try {
//            return decompressInto(b);
//        } catch (DataFormatException e) {
//            e.printStackTrace();
//            System.out.println("Error: Invalid input format");
//        }
//        return 0;
    }

//    @Override
//    public byte[] readAllBytes() throws IOException {
//        try {
//            return decompressInto(super.readAllBytes());
//        } catch (DataFormatException e) {
//            e.printStackTrace();
//            System.out.println("Error: Invalid input format");
//        }
//        return new byte[0];
//    }

    private int decompressInto(byte[] b) throws DataFormatException, IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(b);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(b.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] input = outputStream.toByteArray();
        System.arraycopy(input, 0, b, 0, b.length);
        return input.length;

    }
}
