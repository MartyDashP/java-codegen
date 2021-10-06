Class JavaSpec.Model.Type Extends (%RegisteredObject, %XML.Adaptor)
{

Property Name As %String(XMLNAME = "name");

Property Arguments As list Of JavaSpec.Model.Type(XMLITEMNAME = "argument", XMLNAME = "arguments");

ClassMethod SimpleType(name As %String)
{
	s type = ..%New()
	s type.Name = name
	
	ret type
}

}

