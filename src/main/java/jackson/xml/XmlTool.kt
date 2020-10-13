package jackson.xml

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlFactory
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import javax.xml.stream.XMLInputFactory

object XmlTool {

    @JvmStatic
    fun parseXml(xml: String?): Product {
        val xmlIn = XMLInputFactory.newInstance()
        val factory = XmlFactory(xmlIn)
        val xmlModule = JacksonXmlModule()

        val mapper = XmlMapper(factory, xmlModule).registerKotlinModule()

        return mapper.readValue(xml, Product::class.java)
    }

    data class Stuff(val str: String?)
    data class Product(val stuff: Stuff?)
}