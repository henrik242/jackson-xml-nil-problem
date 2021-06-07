package jackson.xml

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlFactory
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import javax.xml.stream.XMLInputFactory

object XmlTool {
    val xmlIn = XMLInputFactory.newInstance()
    val factory = XmlFactory(xmlIn)
    val xmlModule = JacksonXmlModule()
    val mapper = XmlMapper(factory, xmlModule)
        .registerKotlinModule()
        .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) // Doesn't make any difference

    @JvmStatic
    fun parseRoot(xml: String?): Root = mapper.readValue(xml, Root::class.java)

    @JvmStatic
    fun parseOuter(xml: String?): Outer = mapper.readValue(xml, Outer::class.java)

    data class Inner(val code: String?)
    data class Outer(val inner: Inner?)
    data class Root(val outer: Outer?)
}