import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.*;
import java.util.Arrays;

public class AvroTest {

    private static String JSON;
    private static Schema SCHEMA;
    private static File FILE;
    public static void main(String[] args) throws IOException {
        JSON = FileUtils.readFileToString(new File("main.json"));
        SCHEMA = new Schema.Parser().parse(FileUtils.readFileToString(new File("AvroSchema.avsc")));
        FILE = new File("main.avro");
        byte[] data = jsonToAvro(JSON, SCHEMA);
        System.out.println(Arrays.toString(data));

        String jsonString = avroToJson(new FileInputStream(FILE).readAllBytes(), SCHEMA);
        System.out.println(jsonString);
    }

    public static byte[] jsonToAvro(String json, Schema schema) throws IOException {
        DatumReader<Object> reader = new GenericDatumReader<>(schema);
        GenericDatumWriter<Object> writer = new GenericDatumWriter<>(schema);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Decoder decoder = DecoderFactory.get().jsonDecoder(schema, json);
        Encoder encoder = EncoderFactory.get().binaryEncoder(output, null);
        Object datum = reader.read(null, decoder);
        writer.write(datum, encoder);
        encoder.flush();
        output.writeTo(new FileOutputStream("main.avro"));
        return output.toByteArray();
    }

    public static String avroToJson(byte[] avro, Schema schema) throws IOException {
        boolean pretty = false;
        GenericDatumReader<Object> reader = new GenericDatumReader<>(schema);
        DatumWriter<Object> writer = new GenericDatumWriter<>(schema);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JsonEncoder encoder = EncoderFactory.get().jsonEncoder(schema, output, pretty);
        Decoder decoder = DecoderFactory.get().binaryDecoder(avro, null);
        Object datum = reader.read(null, decoder);
        writer.write(datum, encoder);
        encoder.flush();
        output.flush();
        return new String(output.toByteArray(), "UTF-8");
    }
}