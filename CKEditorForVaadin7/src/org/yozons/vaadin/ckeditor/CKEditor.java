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

/**
 * CKEditor is a JavaScriptComponent. It provides a wrapper and communications glue between CKEditor (a JavaScript library) and Vaadin.
 * 
 * Please use CKEditorField if you want the component to work in a FieldGroup (or legacy Form).
 * @author Yozons, Inc.
 */
@JavaScript({"vaadin://addon-js/CKEditorForVaadin7/ckeditor/ckeditor.js","ckeditorForVaadin7.js","ckeditor-connector.js"})
public class CKEditor extends AbstractJavaScriptComponent {
	private static final long serialVersionUID = 2232973682989450421L;

	public CKEditor(CKEditorConfig config) {
		setSizeFull();
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
			private static final long serialVersionUID = -2199710366817990634L;

			@Override
			public void call(JSONArray arguments) throws JSONException {
				String version = arguments.getString(0);
				getState().setVersion(version);
				getState().setEditorReady(true);
			}
			
		});
		
		addFunction("onValueChange", new JavaScriptFunction() {
			private static final long serialVersionUID = 6707318584258721663L;

			@Override
			public void call(JSONArray arguments) throws JSONException {
				if ( ! isReadOnly() ) {
					String value = arguments.getString(0);
					getState().setHtml(value);
			    	synchronized(valueChangeListeners) {
			    		for( ValueChangeListener listener : valueChangeListeners ) {
			    			listener.valueChange(value);
			    		}
			    	}
				} else {
					System.err.println("CKEditor onValueChange ignored because editor is read-only");
				}
			}
			
		});
		
		addFunction("requestCompleted", new JavaScriptFunction() {
			private static final long serialVersionUID = -3928003452480701563L;

			@Override
			public void call(JSONArray arguments) throws JSONException {
				if ( arguments.length() == 1 ) {
					String completedRequest = arguments.getString(0);
					if ( "focusRequested".equals(completedRequest) ) {
						getState().clearFocusRequested();
					} else {
						System.err.println("ERROR: requestCompleted: cannot update shared state for unexpected completedRequest type: " + completedRequest);
					}
				} else {
					System.err.println("ERROR: requestCompleted: Missing required single argument.");
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
		getState().setFocusRequested(true);
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