import com.snapdeal.base.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.nio.Buffer;
import java.util.Map;

public class TestTemplate {

    public static void main(String[] args) {

        new TestTemplate().test();
    }

    public boolean test() {
        String template = readTemplateFromFile("test.vm");

        //System.out.println(template);
        Template compiledTemplate = Template.compile(template);
        String jsonParams = "{}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> map = objectMapper.readValue(jsonParams, Map.class);
            String evaluate = compiledTemplate.evaluate(map);
            System.out.println(evaluate);
        } catch (IOException e) {
            System.err.println("Error evaluating context" + e);
            e.printStackTrace();
        }
        return true;
    }

    public String readTemplateFromFile(String s) {
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL resource = this.getClass().getResource("/templates/" + s);
            System.out.println(resource);
            if (resource == null || resource.getPath() == null) throw new FileNotFoundException();
            String path = resource.getPath();
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            do {
                line = bufferedReader.readLine();
                if (line != null)
                    stringBuilder.append(line + "\n");
            } while (line != null);
        } catch (FileNotFoundException e) {
            System.err.println("Template file not found");
        } catch (IOException e) {
            System.err.println("Could not read template file");
        }
        return stringBuilder.toString();
    }
}
