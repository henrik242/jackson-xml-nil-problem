package jackson.xml.nil.problem;

import jackson.xml.nil.problem.XmlTool.Silly;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XmlToolTest {

    @Test
    void testParseXml() throws Exception {
        XmlTool tool = new XmlTool();

        String xml = "<Silly xmlns:a=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "            <a:Hey i:nil=\"true\"/>\n" +
                "            <a:Ho>to</a:Ho>\n" +
                "        </Silly>";

        Silly silly = tool.parseXml(xml);

        // Both `hey` and `ho` are null with jackson 2.10.1
        assertEquals(silly, new Silly("", "to"));
    }
}
