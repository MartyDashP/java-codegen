Class JavaSpec.Model.Source Extends (%RegisteredObject, %XML.Adaptor)
{

Parameter XMLNAME = "source";

Property PackageName As %String(XMLNAME = "packageName");

Property Comment As %String(XMLNAME = "comment");

Property StaticImports As list Of JavaSpec.Model.StaticImport(XMLITEMNAME = "staticImport", XMLNAME = "staticImports");

Property Definition As JavaSpec.Model.InterfaceDefinition(XMLNAME = "definition");

}

