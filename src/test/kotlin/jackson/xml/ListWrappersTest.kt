package no.finn.travel.shared.common.encode

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import java.util.*

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ListWrappersTest : StringSpec(
    {

        // Fails with `java.lang.AssertionError: Expected "Simple" but actual was null` in jackson 2.12.3 and above (including 2.16.0-rc1)
        // Works fine in 2.12.2 and earlier
        "should parse xml" {
            val ledgersInCode = parseAs<LedgerActivities>("path to ugly.xml")
            println(ledgersInCode.ledgerActivities[0])
            ledgersInCode.ledgerActivities.first().LedgerActivityDetails.first().loanType shouldBe "Simple"
        }
    },
) {

    companion object {
        val kotlinXmlMapper = XmlMapper(
            JacksonXmlModule().apply {
                setDefaultUseWrapper(false)
            },
        ).registerKotlinModule()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)


        inline fun <reified T : Any> parseAs(path: String): T {
            return kotlinXmlMapper.readValue(xml)
        }

        val xml = """<?xml version="1.0" encoding="utf-8"?>
<ArrayOfLedgerActivityObject xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <LedgerActivityObject>
    <LedgerTransactionDate>7/6/2017</LedgerTransactionDate>
    <Amount>1056.38</Amount>
    <LedgerTransactionDetails>
      <LedgerActivityDetailObject>
        <Amount>1056.38</Amount>
        <TransactionType>Simple</TransactionType>
      </LedgerActivityDetailObject>
    </LedgerTransactionDetails>
  </LedgerActivityObject>
  <LedgerActivityObject>
    <LedgerTransactionDate>2/27/2017</LedgerTransactionDate>
    <Amount>250.46</Amount>
    <LedgerTransactionDetails>
      <LedgerActivityDetailObject>
        <TransactionType>Simple</TransactionType>
        <Amount>250.46</Amount>
      </LedgerActivityDetailObject>
    </LedgerTransactionDetails>
  </LedgerActivityObject>
  <LedgerActivityObject>
    <LedgerTransactionDate>1/31/2017</LedgerTransactionDate>
    <Amount>124.28</Amount>
    <LedgerTransactionDetails>
      <LedgerActivityDetailObject>
        <TransactionType>Simple</TransactionType>
        <Amount>124.28</Amount>
      </LedgerActivityDetailObject>
    </LedgerTransactionDetails>
  </LedgerActivityObject>
</ArrayOfLedgerActivityObject>"""
    }
}

data class LedgerActivityDetail(

    @set:JsonProperty("TransactionType")
    var loanType: String? = null,

    @set:JsonProperty("Amount")
    var amount: String? = null,
)


@JsonRootName("LedgerActivityObject")
data class LedgerActivity(

    @set:JsonProperty("LedgerTransactionDate")
    var LedgerTransactionDate: String? = null,

    @set:JsonProperty("Amount")
    var amount: String? = null,

    // HERE IS WHERE THE MAGIC HAPPENS!!!
    //@set:JsonAlias("LedgerTransactionDetails", "LedgerActivityDetailObject")
    @JsonAlias("LedgerTransactionDetails", "LedgerActivityDetailObject")
    var LedgerActivityDetails: List<LedgerActivityDetail> = ArrayList(),
)


@JsonRootName("ArrayOfLedgerActivityObject")
data class LedgerActivities(

    @set:JsonProperty("LedgerActivityObject")
    var ledgerActivities: List<LedgerActivity> = ArrayList(),
)
