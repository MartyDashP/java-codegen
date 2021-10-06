Class JavaSpec.Model.Method Extends (%RegisteredObject, %XML.Adaptor)
{

Property Name As %String(XMLNAME = "name");

Property ReturnType As JavaSpec.Model.Type(XMLNAME = "returnType");

Property Code As %String(XMLNAME = "code");

Property JavaDoc As %String(XMLNAME = "javaDoc");

Property Modifiers As list Of %String(XMLITEMNAME = "modifier", XMLNAME = "modifiers");

Property Annotations As list Of JavaSpec.Model.Annotation(XMLITEMNAME = "annotation", XMLNAME = "annotations");

Property Exceptions As list Of %String(XMLITEMNAME = "exception", XMLNAME = "exceptions");

Property Parameters As list Of JavaSpec.Model.Parameter(XMLITEMNAME = "parameter", XMLNAME = "parameters");

Property Generics As list Of %String(XMLITEMNAME = "generic", XMLNAME = "generics");

}

