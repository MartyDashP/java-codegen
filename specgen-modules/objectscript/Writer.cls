Class JavaSpec.Writer Extends %RegisteredObject
{

ClassMethod WriteSpec(model As JavaSpec.Model.Source, targetPath As %String)
{
	s xmlWriter = ##class(%XML.Writer).%New()
	s xmlWriter.Indent = 1
	s xmlWriter.Charset = "UTF-8"
	
	$$$TOE(sc, xmlWriter.OutputToFile(targetPath))
	$$$TOE(sc, xmlWriter.RootObject(model))
}

}

