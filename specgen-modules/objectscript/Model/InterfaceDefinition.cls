Class JavaSpec.Model.InterfaceDefinition Extends (%RegisteredObject, %XML.Adaptor)
{

Parameter XMLNAME = "definition";

Property Kind As %String(XMLNAME = "kind");

Property Name As %String(XMLNAME = "name");

Property JavaDoc As %String(XMLNAME = "javaDoc");

Property Modifiers As list Of %String(XMLITEMNAME = "modifier", XMLNAME = "modifiers");

Property SuperInterfaces As list Of %String(XMLITEMNAME = "superInterface", XMLNAME = "superInterfaces");

Property Annotations As list Of JavaSpec.Model.Annotation(XMLITEMNAME = "annotation", XMLNAME = "annotations");

Property Generics As list Of JavaSpec.Model.Generic(XMLITEMNAME = "generic", XMLNAME = "generics");

Property Methods As list Of JavaSpec.Model.Method(XMLITEMNAME = "method", XMLNAME = "methods");

Property Properties As list Of JavaSpec.Model.Property(XMLITEMNAME = "properties", XMLNAME = "properties");

}

