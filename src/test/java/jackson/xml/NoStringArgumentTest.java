package jackson.xml;

import jackson.xml.XmlTool.Inner;
import jackson.xml.XmlTool.Outer;
import jackson.xml.XmlTool.Root;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoStringArgumentTest {

    @Test
    void no_string_argument() {
        String xml = "<root>" +
                     "   <outer>" +
                     "      <inner code=\"578\" />" +
                     "   </outer>" +
                     "</root>";

        Root product = XmlTool.parseRoot(xml);

        assertEquals(new Root(new Outer(new Inner("578"))), product); // SUCCESS
    }

    @Test
    void no_string_argument2() {
        String xml = "<outer>" +
                     "</outer>";

        Outer product = XmlTool.parseOuter(xml);

        assertEquals(new Outer(null), product); // SUCCESS in Jackson 2.11.x, but FAIL in 2.12.x
    }

    @Test
    void no_string_argument3() {
        String xml = "<root>" +
                     "   <outer>" +
                     "   </outer>" +
                     "</root>";

        Root product = XmlTool.parseRoot(xml);

        assertEquals(new Root(new Outer(new Inner(null))), product); // FAIL
    }
}
