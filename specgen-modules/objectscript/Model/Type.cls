Class JavaSpec.Model.Type Extends (%RegisteredObject, %XML.Adaptor)
{

Property Name As %String(XMLNAME = "name");

Property IsArray As %Boolean(XMLNAME = "array") [ InitialExpression = 0 ];

Property Arguments As list Of JavaSpec.Model.Type(XMLITEMNAME = "argument", XMLNAME = "arguments");

ClassMethod SimpleType(name As %String, isArray As %Boolean = 0)
{
	s type = ..%New()
	s type.Name = name
	s type.IsArray = isArray
	
	ret type
}

}

