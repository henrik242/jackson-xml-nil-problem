# jackson-xml-problem
Test project for https://medium.com/@foxjstephen/how-to-actually-parse-xml-in-java-kotlin-221a9309e6e8

Issue created in https://github.com/FasterXML/jackson-module-kotlin/issues/721

Fails with `java.lang.AssertionError: Expected "Simple" but actual was null` in jackson 2.12.3 and above (including 2.16.0-rc1)

Works fine in 2.12.2 and earlier
