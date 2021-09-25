Class JavaSpec.Model.Property Extends (%RegisteredObject, %XML.Adaptor)
{

Property Type As JavaSpec.Model.Type(XMLNAME = "type");

Property Name As %String(XMLNAME = "name");

Property JavaDoc As %String(XMLNAME = "javaDoc");

Property InitValue As %String(XMLNAME = "initValue");

Property Modifiers As list Of %String(XMLITEMNAME = "modifier", XMLNAME = "modifiers");

Property Annotations As list Of JavaSpec.Model.Annotation(XMLITEMNAME = "annotation", XMLNAME = "annotations");

}

