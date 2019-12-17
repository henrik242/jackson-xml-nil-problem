package jackson.xml.nil.problem;

import jackson.xml.nil.problem.XmlTool.Silly;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XmlToolTest {

    XmlTool tool = new XmlTool();

    @Test
    void failsWithJackson210() throws Exception {
        String xml =
                "<Silly xmlns:a=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "  <a:Hey i:nil=\"true\"/>\n" +
                "  <a:Ho>to</a:Ho>\n" +
                "</Silly>";

        Silly silly = tool.parseXml(xml);

        // Both `hey` and `ho` are null with Jackson 2.10.1 when using `http://www.w3.org/2001/XMLSchema-instance`
        assertEquals(new Silly("", "to"), silly);
    }

    @Test
    void doesNotFailWithJackson210() throws Exception {
        String xml =
                "<Silly xmlns:a=\"http://www.w3.org/cheese\" xmlns:i=\"http://www.w3.org/cheese\">\n" +
                "  <a:Hey i:nil=\"true\"/>\n" +
                "  <a:Ho>to</a:Ho>\n" +
                "</Silly>";

        Silly silly = tool.parseXml(xml);

        // Works in Jackson 2.10.x when not using `http://www.w3.org/2001/XMLSchema-instance`
        assertEquals(new Silly("", "to"), silly);
    }
}
