Class JavaSpec.Model.Source Extends (%RegisteredObject, %XML.Adaptor)
{

Parameter XMLNAME = "source";

Property PackageName As %String(XMLNAME = "packageName");

Property Comment As %String(XMLNAME = "comment");

Property Definition As JavaSpec.Model.InterfaceDefinition(XMLNAME = "definition");

}

