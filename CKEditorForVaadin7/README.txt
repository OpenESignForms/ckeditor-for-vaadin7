File: CKEditorForVaadin7/README.txt
Last updated: 25 June 2013

First change from Eclipse via GIT/GITHUB repository usage.

  USING CKEDITOR FOR VAADIN 7 IN YOUR APPLICATION
  ===============================================

Put the pre-built JAR file and in your Vaadin application's WEB-INF/lib. 
This has everything you need to use it, including a version of CKEditor.
Because it's a pure JavaScriptComponent, it doesn't need a widgetset compilation.

Unlike its predecessor, this cannot be used as a Field (FieldGroup or legacy Form).

NOTE: This component is compiled using JDK 1.6 / Java 6.

The CKEditor code, in full as downloaded from http://ckeditor.com, is present in the 
WebContent/VAADIN/addon-js/CKEditorForVaadin7/ckeditor folder.  No changes to CKEditor were made.
However, we remove the following files from the standard CKEditor distribution as they are not needed:
   ckeditor/samples
If you are compiling yourself, you will need to install CKEditor code into your project
as we do not check in the CKEditor code in our source code system.
1) Download the latest ZIP file from ckeditor.com. We include the Full Editor version.
2) Unzip/extract the contents -- you should have a 'ckeditor' folder.
3) Copy the 'ckeditor' folder to WebContent/VAADIN/addon-js/CKEditorForVaadin7/.
4) If you want to use the Vaadin Save button plugin, copy ckeditor/plugins/vaadinsave
   to WebContent/VAADIN/addon-js/CKEditorForVaadin7/ckeditor/plugins.
   This is already done in the released CKEditor for Vaadin 7 code.

  LICENSE
  =======
  
This software component is licensed under Apache License 2.0. 
See http://www.apache.org/licenses/LICENSE-2.0.html for more details.

This component was written initially by Yozons, Inc. (www.yozons.com) 
for its Open eSignForms project (open.esignforms.com) -- not required to use this component -- 
which is separately licensed under the Affero GPL as well as a commercial licensed.

This code was based on a prior Vaadin Directory add-on component.

CKEditor is required and is licensed separately with details at http://ckeditor.com/license.

Icons are from Fat Cow Free Web Icons (http://www.fatcow.com/free-icons/) which are released 
under the Creative Commons Attribution 3.0 License.

  TODO
  ====
  * Fix CKEditor appearing in a Window. Currently it only seems to work when not in a sub-Window.

  KNOWN ISSUES
  ============
  * Need a better mechanism than the blur event to detect editor changes.
    Apparently, CKEditor will support an official onchange event in the 4.2 release (http://dev.ckeditor.com/ticket/9794) 
    that we hope will resolve this.
  
  CHANGELOG
  =========

0.9.0 (25 June 2013)
- This early release is experimental.
- Initial version running under Vaadin 7's AbstractJavaScriptComponent scheme rather than a GWT-based widget. No widgetset compilation is needed.
- Using Vaadin 7.0.7 and CKEditor 4.1.2 full version.
- If using the previous Vaadin Directory add-on ("CKEditor wrapper for Vaadin", which supported Vaadin 6 and a quick port for Vaadin 7)
  located at http://vaadin.com/addon/ckeditor-wrapper-for-vaadin, you will want to change all references to 
  org.vaadin.openesignforms.ckeditor.CKEditorTextField to use instead org.yozons.vaadin.ckeditor.CKEditor. 
  This is component is not a Field, and thus cannot be used in a FieldGroup or legacy Form.
  Note, too, that it only supports a new version of CKEditor.ValueChangeListener with the callback method 'valueChange(String newValue)'
  intead of the previous based on Vaadin events.

