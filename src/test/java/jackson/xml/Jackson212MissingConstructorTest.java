package jackson.xml;

import jackson.xml.XmlTool.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Jackson212MissingConstructorTest {

    @Test
    void fails_with_jackson_2_12() throws Exception {
        String xml = "<product><stuff></stuff></product>";

        Product product = XmlTool.parseXml(xml);

        assertEquals(new Product(null), product);
    }
}
