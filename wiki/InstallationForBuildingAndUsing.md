#1. Introduction

You may also want to read the README.txt file for other details. It only works on Vaadin 7.

#2. Details

The JAR file included has CKEditor and the JavaScriptComponent all compiled and ready to use.  Just drop the JAR file in your Vaadin project's WEB-INF/lib and you can use the example application in src/org/yozons/vaadin/ckeditor/CKEditorForVaadin7UI.java for a quick tip on using it as a Vaadin 7 component.

To build your own, please note the following:

We built the project using Eclipse Juno on Vaadin 7.0.7.  These instructions assume you are doing the same.

We have not included the CKEditor code in the source code repository.  Please download the FULL VERSION directly from http://ckeditor.com/download.  Extract the code so that the directory 'ckeditor' is located in your src/org/yozons/vaadin/ckeditor/ckeditor folder.

Our distribution removes the following from the standard CKEditor code:

    ckeditor/samples


To use the Save button inside the editor, after you've installed ckeditor in the public folder above, you will need to copy the ckeditor/plugins/vaadinsave folder into the src/org/yozons/vaadin/ckeditor/ckeditor/plugins folder.
