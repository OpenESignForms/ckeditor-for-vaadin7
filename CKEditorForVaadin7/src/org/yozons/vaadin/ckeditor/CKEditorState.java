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
package org.yozons.vaadin.ckeditor;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class CKEditorState extends JavaScriptComponentState {
	private static final long serialVersionUID = 7712793232513048687L;

	private String inPageConfig = null;
	private String writerIndentationChars = null;
	private String[] protectedSources = null;
	private String[] writerRulesTagNames = null; // these both go as a pair with the array lengths being equal
	private String[] writerRulesRules = null; // these both go as a pair with the array lengths being equal
	private String version = "TBD";
	private String html = "";
	private boolean viewWithoutEditor = false; // when true, we don't use CKEditor at all, and just show the editor's HTML
	private Boolean focus = null; // when set, focus is requested

	public String getInPageConfig() {
		return inPageConfig;
	}
	public void setInPageConfig(String jsonInPageConfig) {
		if ( inPageConfig == null ) { // can only set once
			inPageConfig = jsonInPageConfig;
		}
	}
	
	public String getWriterIndentationChars() {
		return writerIndentationChars;
	}
	public void setWriterIndentationChars(String v) {
		writerIndentationChars = v;
	}

	public String[] getProtectedSources() {
		return protectedSources;
	}
	public void setProtectedSources(String[] v) {
		protectedSources = v;
	}

	public String[] getWriterRulesTagNames() {
		return writerRulesTagNames;
	}
	public void setWriterRulesTagNames(String[] v) {
		writerRulesTagNames = v;
	}
	public String[] getWriterRulesRules() {
		return writerRulesRules;
	}
	public void setWriterRulesRules(String[] v) {
		writerRulesRules = v;
	}

	public String getVersion() {
		return version;
	}
	public void setVersion(String v) {
		version = v == null ? "" : v;
	}

	public String getHtml() {
		return html;
	}
	public void setHtml(String v) {
		html = v == null ? "" : v;
	}

	public boolean isViewWithoutEditor() {
		return viewWithoutEditor;
	}
	public void setViewWithoutEditor(boolean v) {
		viewWithoutEditor = v;
	}
	
	public boolean isFocus() {
		return focus == null ? false : focus.booleanValue();
	}
	public void setFocus(boolean v) {
		focus = new Boolean(v);
	}
	public void clearFocus() {
		focus = null;
	}
}