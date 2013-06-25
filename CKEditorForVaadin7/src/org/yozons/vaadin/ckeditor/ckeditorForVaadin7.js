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

// Define the namespace
var ckeditorForVaadin7 = ckeditorForVaadin7 || {};

ckeditorForVaadin7.MyComponent = function(abstractJavaScriptComponent) {
	var rootDiv = abstractJavaScriptComponent.getElement();
	rootDiv.id = 'VCKE_'+abstractJavaScriptComponent.getConnectorId();
	rootDiv.style.overflow = "hidden";
	rootDiv.style.visibility = "visible";
	rootDiv.innerHTML = "";
	
	// This is a hack attempt to resolve issues with Vaadin when the CKEditorTextField widget is set with BLUR and FOCUS listeners.
	// In particular, the Safari browser could not deal well with PASTE, right clicking in a table cell, etc.
	// because those operations resulted in BLUR then FOCUS events in rapid succession, causing the UI to update.
	// But the 200 value is too long and we find that often the button acts faster than the BLUR can fire from CKEditor
	// so Vaadin doesn't get the latest contents.
	CKEDITOR.focusManager._.blurDelay = 20; // the default is 200 even if the documentation says it's only 100
	
	var inPageConfig = ckeditorForVaadin7.convertJavaScriptStringToObject(abstractJavaScriptComponent.getState().inPageConfig);
	console.log('MyComponent creating CKEDITOR append to div id: ' + rootDiv.id + '; config: ' + abstractJavaScriptComponent.getState().inPageConfig);
	abstractJavaScriptComponent.myCKEditor = CKEDITOR.appendTo( rootDiv.id, inPageConfig );
	console.log('MyComponent created CKEDITOR: ' + abstractJavaScriptComponent.myCKEditor);
	abstractJavaScriptComponent.myCKEditor.on('instanceReady', function(ev) {
		ev.listenerData.onInstanceReady(CKEDITOR.version);
		
		ev.editor.on('blur', function(blurEvent) {
			if ( ! blurEvent.editor.readOnly ) {
				var stateData = blurEvent.listenerData.getState().html;
				var currData = blurEvent.editor.getData();
				if ( stateData != currData ) {
					blurEvent.listenerData.onValueChange(currData);
				}
			}
			//blurEvent.listenerData.onBlur();
		}, null, ev.listenerData);
		
		ev.editor.on('vaadinsave', function(vaadinsaveEvent) {
			if ( ! vaadinsaveEvent.editor.readOnly ) {
				var stateData = vaadinsaveEvent.listenerData.getState().html;
				var currData = vaadinsaveEvent.editor.getData();
				if ( stateData != currData ) {
					vaadinsaveEvent.listenerData.onValueChange(currData);
				}
			}
		}, null, ev.listenerData);
		
		if ( ev.listenerData.getState().writerIndentationChars ) {
			ev.editor.dataProcessor.writer.indentationChars = ev.listenerData.getState().writerIndentationChars;
		}
		
		if ( ev.listenerData.getState().protectedSources ) {
			for( var i=0; i < ev.listenerData.getState().protectedSources.length; ++i ) {
				var regex = ckeditorForVaadin7.convertJavaScriptStringToObject(ev.listenerData.getState().protectedSources[i]);
				ev.editor.config.protectedSource.push( regex );
			}
		}

		if ( ev.listenerData.getState().writerRulesTagNames && ev.listenerData.getState().writerRulesRules ) {
			for( var i=0; i < ev.listenerData.getState().writerRulesTagNames.length; ++i ) {
				var tagName = ev.listenerData.getState().writerRulesTagNames[i];
				var ruleString = ev.listenerData.getState().writerRulesRules[i];
				var rule = ckeditorForVaadin7.convertJavaScriptStringToObject(ruleString);
				console.log('writer rule: tag: ' + tagName + '; rule: ' + ruleString);
				ev.editor.dataProcessor.writer.setRules( tagName, rule );
			}
		}

	}, null, abstractJavaScriptComponent );
	
	return abstractJavaScriptComponent;
};

ckeditorForVaadin7.convertJavaScriptStringToObject = function(jsString) {
    try {
 		return eval('('+jsString+')');
 	} catch (e) { 
 		alert('ckeditorForVaadin7.convertJavaScriptStringToObject() INVALID JAVASCRIPT: ' + jsString); 
 		return {}; 
 	}	
};