package jackson.xml;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLInputFactory;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static javax.xml.stream.XMLInputFactory.IS_NAMESPACE_AWARE;
import static javax.xml.stream.XMLInputFactory.newInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test project for Jackson issue #378: https://github.com/FasterXML/jackson-dataformat-xml/issues/378
 */
class MissingAttributeTest {

    ObjectMapper mapper = mapper();

    @Test
    void should_parse_xml_with_attribute() throws Exception {
        String xml = "<MissingAttribute someAttr=\"foo\">hei sann</MissingAttribute>";
        MissingAttribute a = mapper.readValue(xml, MissingAttribute.class);
        assertEquals(new MissingAttribute("foo", "hei sann"), a);
    }

    @Test
    void should_parse_xml_with_missing_attribute() throws Exception {
        String xml = "<MissingAttribute>hei sann</MissingAttribute>";
        MissingAttribute a = mapper.readValue(xml, MissingAttribute.class);
        assertEquals(new MissingAttribute(null, "hei sann"), a);
    }

    @Test
    void should_parse_xml_with_attribute_inside_container() throws Exception {
        String xml = "<Container><MissingAttribute someAttr=\"foo\">hei sann</MissingAttribute></Container>";
        Container c = mapper.readValue(xml, Container.class);
        assertEquals("foo", c.missingAttribute.someAttr);
        assertEquals("hei sann", c.missingAttribute.textContent);
    }

    @Test
    void should_parse_xml_with_missing_attribute_inside_container() throws Exception {
        String xml = "<Container><MissingAttribute>hei sann</MissingAttribute></Container>";
        Container c = mapper.readValue(xml, Container.class);
        assertNull(c.missingAttribute.someAttr);
        assertEquals("hei sann", c.missingAttribute.textContent);
    }

    @Test
    void should_parse_xml_withtout_attribute_or_text_inside_container() throws Exception {
        String xml = "<Container><MissingAttribute></MissingAttribute></Container>";
        Container c = mapper.readValue(xml, Container.class);
        assertNull(c.missingAttribute.someAttr);
        assertEquals("", c.missingAttribute.textContent);
    }

    static ObjectMapper mapper() {
        XMLInputFactory xmlIn = newInstance();
        xmlIn.setProperty(IS_NAMESPACE_AWARE, true);

        XmlFactory factory = new XmlFactory(xmlIn);

        JacksonXmlModule xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        xmlModule.setXMLTextElementName("textContent");

        ObjectMapper mapper = new XmlMapper(factory, xmlModule)
                .configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        mapper.configOverride(List.class).setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));
        return mapper;
    }

    static class Container {
        MissingAttribute missingAttribute;

        public Container() {
        }

        public Container(MissingAttribute missingAttribute) {
            this.missingAttribute = missingAttribute;
        }

        public MissingAttribute getMissingAttribute() {
            return missingAttribute;
        }

        public void setMissingAttribute(MissingAttribute missingAttribute) {
            this.missingAttribute = missingAttribute;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Container container = (Container) o;
            return Objects.equals(missingAttribute, container.missingAttribute);
        }

        @Override
        public int hashCode() {
            return Objects.hash(missingAttribute);
        }
    }

    static class MissingAttribute {
        String someAttr;
        String textContent;

        public MissingAttribute() {
        }

        public MissingAttribute(String textContent) {
            this.someAttr = null;
            this.textContent = textContent;
        }

        public MissingAttribute(String someAttr, String textContent) {
            this.someAttr = someAttr;
            this.textContent = textContent;
        }

        public String getSomeAttr() {
            return someAttr;
        }

        public void setSomeAttr(String someAttr) {
            this.someAttr = someAttr;
        }

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MissingAttribute that = (MissingAttribute) o;
            return Objects.equals(someAttr, that.someAttr) &&
                    Objects.equals(textContent, that.textContent);
        }

        @Override
        public int hashCode() {
            return Objects.hash(someAttr, textContent);
        }
    }
}
