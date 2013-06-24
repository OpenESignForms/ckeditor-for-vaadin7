// Copyright (C) 2013 Yozons, Inc.
// CKEditor for Vaadin7 - Widget for using CKEditor within a Vaadin 7 application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software is a rewrite of the CKEditor for Vaadin (Vaadin7CKEditor) project whose code was under the org.vaadin.openesignforms.ckeditor package.
// The Vaadin Directory link for that original add-on "CKEditor wrapper for Vaadin" (supported Vaadin 6 and a quick port to Vaadin 7): 
//     http://vaadin.com/addon/ckeditor-wrapper-for-vaadin
//
// This Vaadin widget class replaces org.vaadin.openesignforms.ckeditor.CKEditorTextField from the previous versions.
//

window.org_yozons_vaadin_ckeditor_CKEditor = function() {
	var myComponent = new ckeditorForVaadin7.MyComponent(this);
	
	// Handle changes from the server side
	this.onStateChange = function() {
		console.log("on state change: editor id: " + this.myCKEditor.id + "; cid: " + this.getConnectorId() + "; viewWithoutEditor: " + this.getState().viewWithoutEditor + "; readOnly: " + this.getState().readOnly + " >>>" + this.getState().html + "<<<");
		if ( this.getState().viewWithoutEditor ) {
			if ( myComponent.myCKEditor != null ) {
				myComponent.myCKEditor.destroy();
				myComponent = null;
				this.getElement().innerHTML = this.getState().html;
			}
		} else {
			if ( myComponent == null ) {
				myComponent = new ckeditorForVaadin7.MyComponent(this);
			}
			if ( myComponent.myCKEditor.readOnly != this.getState().readOnly ) {
				myComponent.myCKEditor.setReadOnly(this.getState().readOnly);
			}
			if ( myComponent.myCKEditor.getData() != this.getState().html ) {
				myComponent.myCKEditor.setData(this.getState().html);
			}
			if ( this.getState().focus ) {
				console.log("focus requested");
				myComponent.myCKEditor.focus();
				this.requestCompleted('focus');
			}
		}
	};
	
};
