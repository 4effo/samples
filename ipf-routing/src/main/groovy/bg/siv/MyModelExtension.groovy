package bg.siv

import org.apache.camel.model.ProcessorDefinition
class MyModelExtension {

     static extensions = {
         
         ProcessorDefinition.metaClass.reverse = {
             delegate.transmogrify { it.reverse() }
         }
		          
     }
     
}
