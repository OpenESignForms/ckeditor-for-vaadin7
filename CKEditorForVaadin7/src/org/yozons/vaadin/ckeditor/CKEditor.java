// Copyright (C) 2013 Yozons, Inc.
// CKEditor for Vaadin7 - Widget for using CKEditor within a Vaadin 7 application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This software is a rewrite of the CKEditor for Vaadin (Vaadin7CKEditor) project whose code was under the org.vaadin.openesignforms.ckeditor package.
// The Vaadin Directory link for that original add-on "CKEditor wrapper for Vaadin" (supported Vaadin 6 and a quick port to Vaadin 7): 
//     http://vaadin.com/addon/ckeditor-wrapper-for-vaadin
// This JavaScriptComponent class replaces org.vaadin.openesignforms.ckeditor.CKEditorTextField from the previous versions.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
package org.yozons.vaadin.ckeditor;

import java.io.Serializable;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

/*
@JavaScript({"ckeditor/ckeditor.js","ckeditor/config.js","ckeditor/skins/moono/editor.css","ckeditor/lang/en.js","ckeditor/styles.js","ckeditor/contents.css","ckeditor/skins/moono/icons.png","ckeditor/plugins/vaadinsave/plugin.js","ckeditor/plugins/vaadinsave/icons/vaadinsave.png","ckeditorForVaadin7.js","ckeditor-connector.js"})
@JavaScript({"ckeditor/ckeditor.js","ckeditorForVaadin7.js","ckeditor-connector.js"})
 */

@JavaScript({"ckeditor/ckeditor.js","ckeditor/config.js","ckeditor/skins/moono/editor.css","ckeditor/lang/en.js","ckeditor/styles.js","ckeditor/contents.css","ckeditor/skins/moono/icons.png","ckeditor/plugins/vaadinsave/plugin.js","ckeditor/plugins/vaadinsave/icons/vaadinsave.png","ckeditorForVaadin7.js","ckeditor-connector.js"})
public class CKEditor extends AbstractJavaScriptComponent {
	
	public CKEditor(CKEditorConfig config) {
		System.out.println("CKEditor config: " + config.getInPageConfig());
		setWidth(100, Unit.PERCENTAGE);
		setHeight(350, Unit.PIXELS);
		setValue("");
		getState().setInPageConfig(config.getInPageConfig());
		if ( config.hasWriterIndentationChars() ) {
			getState().setWriterIndentationChars(config.getWriterIndentationChars());
		}
		if ( config.hasProtectedSource() ) {
			getState().setProtectedSources(config.getProtectedSources());
		}
		if ( config.hasWriterRules() ) {
			java.util.Set<String> writerRulesTagNames = config.getWriterRulesTagNames();
			String[] tagNames = new String[writerRulesTagNames.size()];
			String[] rules = new String[writerRulesTagNames.size()];
			int i = 0;
			for( String tagName : writerRulesTagNames ) {
				tagNames[i] = tagName;
				rules[i] = config.getWriterRuleByTagName(tagName);
				++i;
			}
			getState().setWriterRulesTagNames(tagNames);
			getState().setWriterRulesRules(rules);
		}
		
		addFunction("onInstanceReady", new JavaScriptFunction() {

			@Override
			public void call(JSONArray arguments) throws JSONException {
				String version = arguments.getString(0);
				System.out.println("CKEditor onInstanceReady(): " + version);
				getState().setVersion(version);
			}
			
		});
		
		addFunction("onValueChange", new JavaScriptFunction() {

			@Override
			public void call(JSONArray arguments) throws JSONException {
				if ( ! isReadOnly() ) {
					String value = arguments.getString(0);
					System.out.println("CKEditor onValueChange() >>>" + value + "<<<");
					getState().setHtml(value);
			    	synchronized(valueChangeListeners) {
			    		for( ValueChangeListener listener : valueChangeListeners ) {
			    			listener.valueChange(value);
			    		}
			    	}
				} else {
					System.out.println("CKEditor onValueChange() ignored because read-only");
				}
			}
			
		});
		
		addFunction("requestCompleted", new JavaScriptFunction() {

			@Override
			public void call(JSONArray arguments) throws JSONException {
				if ( arguments.length() == 1 ) {
					String completedRequest = arguments.getString(0);
					System.out.println("requestCompleted - updating shared state for type: " + completedRequest);
					if ( "focus".equals(completedRequest) ) {
						getState().clearFocus();
					} else {
						System.err.println("requestCompleted: cannot update shared state for unexpected completedRequest type: " + completedRequest);
					}
				} else {
					System.err.println("requestCompleted: Missing required single argument.");
				}
			}
			
		});

	}

	public CKEditor(CKEditorConfig config, String initialHtml) {
		this(config);
		setValue(initialHtml);
	}

	// Now for all of our shared state methods
	public String getVersion() {
		return getState().getVersion();
	}
	
	public String getValue() {
		return getState().getHtml();
	}
	public void setValue(String v) {
		getState().setHtml(v);
	}
	
	public boolean isViewWithoutEditor() {
		return getState().isViewWithoutEditor();
	}
	public void setViewWithoutEditor(boolean v) {
		getState().setViewWithoutEditor(v);
	}
	
	public void focus() {
		getState().setFocus(true);
	}
	
    @Override
    protected CKEditorState getState() {
        return (CKEditorState)super.getState();
    }

    public interface ValueChangeListener extends Serializable {
        void valueChange(String newValue);
    }
    LinkedList<ValueChangeListener> valueChangeListeners = new LinkedList<ValueChangeListener>();
    public void addValueChangeListener(ValueChangeListener listener) {
    	synchronized(valueChangeListeners) {
        	valueChangeListeners.add(listener);
    	}
    }
    public void removeValueChangeListener(ValueChangeListener listener) {
    	synchronized(valueChangeListeners) {
        	valueChangeListeners.remove(listener);
    	}
    }

    @Override
    public void detach() {
    }
}