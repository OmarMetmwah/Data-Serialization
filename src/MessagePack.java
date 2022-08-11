import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MessagePack {
    private static String JSON;
    private static File FILE;
    public static void main(String[] args) throws Exception {
        JSON = FileUtils.readFileToString(new File("main.json"));
        FILE = new File("main100.msgpack");
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(JSON, Map.class);

        org.msgpack.MessagePack msgpack = new org.msgpack.MessagePack();

        // Serialize
        new FileOutputStream(FILE).write(msgpack.write(map));
        // Deserialize
        System.out.println(msgpack.read(new FileInputStream(FILE).readAllBytes()));
    }
}