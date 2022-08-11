import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.util.JsonFormat;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class ProtoBuffTest {
    private static String JSON;
    private static File FILE;

    public static void main(String[] args) throws IOException {
        JSON = FileUtils.readFileToString(new File("main.json"));
        FILE = new File("main.protobuff");
        ObjectMapper objectMapper = new ObjectMapper();
        ProtobufSchema.glossary.Builder sm = ProtobufSchema.glossary.newBuilder();
        JsonFormat.parser().merge(JSON,sm);
        new FileOutputStream(FILE).write(sm.build().toByteArray());
        // Deserialize
        System.out.println(ProtobufSchema.glossary.parseFrom(new FileInputStream(FILE).readAllBytes()));


    }
}
