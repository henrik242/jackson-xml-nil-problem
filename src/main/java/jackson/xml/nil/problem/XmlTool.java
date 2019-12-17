package jackson.xml.nil.problem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.xml.stream.XMLInputFactory;
import java.util.Objects;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static javax.xml.stream.XMLInputFactory.IS_NAMESPACE_AWARE;
import static javax.xml.stream.XMLInputFactory.newInstance;

public class XmlTool {

    public Silly parseXml(String xml) throws Exception {
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

        return mapper.readValue(xml, Silly.class);
    }

    static class Silly {
        String hey;
        String ho;

        public Silly() {
        }

        public Silly(String hey, String ho) {
            this.hey = hey;
            this.ho = ho;
        }

        public String getHey() {
            return hey;
        }

        public void setHey(String hey) {
            this.hey = hey;
        }

        public String getHo() {
            return ho;
        }

        public void setHo(String ho) {
            this.ho = ho;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Silly silly = (Silly) o;
            return Objects.equals(hey, silly.hey) &&
                    Objects.equals(ho, silly.ho);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hey, ho);
        }

        @Override
        public String toString() {
            return "Silly{" +
                    "hey='" + hey + '\'' +
                    ", ho='" + ho + '\'' +
                    '}';
        }
    }
}


