Class JavaSpec.Model.Entry Extends (%RegisteredObject, %XML.Adaptor)
{

Property Key As %String(XMLNAME = "key");

Property Value As %String(XMLNAME = "value");

Method %OnNew(key As %String, value As %String) As %Status
{
	throw:(key = "") ##class(%Exception.General).%New("Key is undefined", "KEY_IS_UNDEFINED")
	
	s ..Key = key
	s ..Value = value
	
	ret $$$OK
}

}

