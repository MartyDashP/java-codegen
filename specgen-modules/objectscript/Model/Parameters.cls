Class JavaSpec.Model.Parameters Extends (%RegisteredObject, %XML.Adaptor)
{

Property Name As %String(XMLNAME = "name");

Property Type As %String(XMLNAME = "type");

Property JavaDoc As %String(XMLNAME = "javaDoc");

Property Modifiers As list Of %String(XMLITEMNAME = "modifier", XMLNAME = "modifiers");

Property Annotations As list Of JavaSpec.Model.Annotation(XMLITEMNAME = "annotation", XMLNAME = "annotations");

}
