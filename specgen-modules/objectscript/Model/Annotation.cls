Class JavaSpec.Model.Annotation Extends (%RegisteredObject, %XML.Adaptor)
{

Property Name As %String(XMLNAME = "name");

Property Elements As list Of JavaSpec.Model.Entry(XMLITEMNAME = "element", XMLNAME = "elements");

}

