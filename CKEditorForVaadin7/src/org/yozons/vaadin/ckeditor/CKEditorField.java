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

import java.util.LinkedList;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

/**
 * This is the CKEditor JavaScriptComponent packaged into a Field.
 * @author Yozons, Inc.
 *
 */
public class CKEditorField extends CustomField<String> {
	private static final long serialVersionUID = 376794645407488571L;

	final CKEditorField thisField;
	
	CKEditor editor;
	CKEditorConfig config;
	String initialHtmlValue; // temporary for any values set before the editor is in place
	
	public CKEditorField(CKEditorConfig config) {
		this.thisField = this;
		this.config = config;
		this.setSizeFull();
	}
	
	public CKEditorField(CKEditorConfig config, String initialHtmlValue) {
		this(config);
		this.initialHtmlValue = initialHtmlValue;
	}
	
	public String getVersion() {
		return editor == null ? null : editor.getVersion();
	}
	
	public boolean isViewWithoutEditor() {
		return editor == null ? false : editor.isViewWithoutEditor();
	}
	public void setViewWithoutEditor(boolean v) {
		if ( editor != null ) {
			editor.setViewWithoutEditor(v);
		}
	}

	public void focus() {
		if ( editor != null ) {
			editor.focus();
		}
	}
	
    LinkedList<ValueChangeListener> valueChangeListeners = new LinkedList<ValueChangeListener>();
    public void addValueChangeListener(ValueChangeListener listener) {
    	if ( listener == null || editor == null )
    		return;
    	synchronized(valueChangeListeners) {
        	valueChangeListeners.add(listener);
    	}
    }
    public void removeValueChangeListener(ValueChangeListener listener) {
    	if ( listener == null || editor == null )
    		return;
    	synchronized(valueChangeListeners) {
        	valueChangeListeners.remove(listener);
    	}
    }
	
    @Override
	public boolean isReadOnly() {
		return editor == null ? false : editor.isReadOnly();
	}
    @Override
	public void setReadOnly(boolean v) {
		if ( editor != null ) {
			editor.setReadOnly(v);
		}
		super.setReadOnly(v);
	}

    @Override
    public void setWidth(float width, Unit unit) {
    	if ( editor != null ) {
    		editor.setWidth(width,unit);
    	}
		super.setWidth(width,unit);
    }
    @Override
    public void setWidth(String width) {
    	if ( editor != null ) {
    		editor.setWidth(width);
    	}
		super.setWidth(width);
    }
    
    @Override
    public void setHeight(float height, Unit unit) {
    	if ( editor != null ) {
    		editor.setHeight(height,unit);
    	}
		super.setHeight(height,unit);
    }
    @Override
    public void setHeight(String height) {
    	if ( editor != null ) {
    		editor.setHeight(height);
    	}
		super.setHeight(height);
    }
    
    @Override
    public void setSizeFull() {
    	if ( editor != null ) {
    		editor.setSizeFull();
    	}
		super.setSizeFull();
    }
    
    @Override
    public void setSizeUndefined() {
    	if ( editor != null ) {
    		editor.setSizeUndefined();
    	}
		super.setSizeUndefined();
    }

    
	// Methods related to subclassing CustomField
	protected String getInternalValue() {
		return editor == null ? initialHtmlValue : editor.getValue();
	}
	protected void setInternalValue(String newValue) {
		if ( editor == null ) {
			initialHtmlValue = newValue;
		} else {
			editor.setValue(newValue);
		}
	}

	@Override
	protected Component initContent() {
		editor = new CKEditor(config,initialHtmlValue);
		initialHtmlValue = null;
		editor.addValueChangeListener( new CKEditor.ValueChangeListener() {
			private static final long serialVersionUID = -2139454569686246032L;

			@Override
			public void valueChange(String newValue) {
				ValueChangeEvent event = new ValueChangeEvent(thisField);
		    	synchronized(valueChangeListeners) {
		        	for( ValueChangeListener listener : valueChangeListeners ) {
		        		listener.valueChange(event);
		        	}
		    	}
			}
		});

		return editor;
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}
	
	@Override
	public void detach() {
		if ( editor != null ) {
			editor.detach();
			editor = null;
		}
		valueChangeListeners.clear();
		super.detach();
	}
}