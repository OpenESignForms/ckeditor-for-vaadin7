// Copyright (C) 2013 Yozons, Inc.
// CKEditor for Vaadin7 - Widget for using CKEditor within a Vaadin 7 application.
//
// This software is released under the Apache License 2.0 <http://www.apache.org/licenses/LICENSE-2.0.html>
//
// This software is a rewrite of the CKEditor for Vaadin (Vaadin7CKEditor) project whose code was under the org.vaadin.openesignforms.ckeditor package.
// The Vaadin Directory link for that original add-on "CKEditor wrapper for Vaadin" (supported Vaadin 6 and a quick port to Vaadin 7): 
//     http://vaadin.com/addon/ckeditor-wrapper-for-vaadin
// Because this is a rewrite from scratch to use the new schemes for Vaadin 7, it's being tracked separate.  Also, users of the
// original code will need to update the package references to use org.yozons.vaadin.ckeditor instead.  We only tried to maintain
// the highest level of compatibility through the CKEditorConfig object used to help prepare the "in page config".
//
package org.yozons.vaadin.ckeditor;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.yozons.vaadin.ckeditor.CKEditor.ValueChangeListener;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class CKEditorForVaadin7UI extends UI {

	@WebServlet(value = "/*", asyncSupported = true, initParams = {
			@WebInitParam(name = "ui", value = "org.yozons.vaadin.ckeditor.CKEditorForVaadin7UI"),
			@WebInitParam(name = "productionMode", value = "false") })
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("CKEditor for Vaadin 7");
		
		final VerticalLayout layout = new VerticalLayout();
		layout.setWidth(100, Unit.PERCENTAGE);
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
		
		layout.addComponent(new Button("Hit server"));
		
		final String editor1InitialValue = 
				"<p>CKEditor for Vaadin 7 is an entirely new JavaScriptComponent add-on.</p>";

		CKEditorConfig config1 = new CKEditorConfig();
		config1.useCompactTags();
		config1.disableElementsPath();
		config1.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		config1.disableSpellChecker();
		final CKEditor editor1 = new CKEditor(config1,editor1InitialValue);
		layout.addComponent(editor1);
		
		editor1.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(String newValue) {
				if ( ! newValue.equals(editor1.getValue()) )
					Notification.show("ERROR - Event value does not match editor #1's current value");
				else
					Notification.show("ValueChangeListener CKEditor v" + editor1.getVersion() + "/" + getVersion() + " - #1 contents: " + newValue);
				editor1.focus();
			}
		});
		
		Button testButton = new Button("Reset editor #1");
		testButton.addClickListener( new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if ( ! editor1.isReadOnly() ) {
					editor1.setValue(editor1InitialValue);
					Notification.show("Reset CKEditor v" + editor1.getVersion() + "/" + getVersion() + " - #1 contents: " + editor1.getValue());
				}
			}
			
		});
		layout.addComponent(testButton);
		
		
		Button toggleReadOnlyButton1 = new Button("Toggle read-only editor #1");
		toggleReadOnlyButton1.addClickListener( new Button.ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				editor1.setReadOnly( ! editor1.isReadOnly() );
			}
		});
		layout.addComponent(toggleReadOnlyButton1);

		Button toggleViewWithoutEditorButton1 = new Button("Toggle view-without-editor #1");
		toggleViewWithoutEditorButton1.addClickListener( new Button.ClickListener() {			

			@Override
			public void buttonClick(ClickEvent event) {
				editor1.setViewWithoutEditor( ! editor1.isViewWithoutEditor() );
			}
		});
		layout.addComponent(toggleViewWithoutEditorButton1);
		
		// Now add in a second editor....
		final String editor2InitialValue = 
			"<p>Here is editor #2.</p><h1>Hope you find this useful in your Vaadin 7 projects.</h1>";

		CKEditorConfig config2 = new CKEditorConfig();
		config2.addCustomToolbarLine("{ items : ['Source','Styles','Bold','VaadinSave','-','Undo','Redo','-','NumberedList','BulletedList'] }");
		config2.enableVaadinSavePlugin();
		config2.addToRemovePlugins("scayt");

		final CKEditor editor2 = new CKEditor(config2);
		editor2.setWidth(600,Unit.PIXELS);
		layout.addComponent(editor2);
		editor2.setValue(editor2InitialValue);
		
		editor2.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(String newValue) {
				if ( ! newValue.equals(editor2.getValue()) )
					Notification.show("ERROR - Event value does not match editor #2's current value");
				else
					Notification.show("ValueChangeListener CKEditor v" + editor2.getVersion() + "/" + getVersion() + " - #2 contents: " + newValue);
			}
		});
		
		Button resetTextButton2 = new Button("Reset editor #2");
		resetTextButton2.addClickListener( new Button.ClickListener() {			

			@Override
			public void buttonClick(ClickEvent event) {
				if ( ! editor2.isReadOnly() ) {
					editor2.setValue(editor2InitialValue);
					Notification.show("Reset CKEditor v" + editor1.getVersion() + "/" + getVersion() + " - #2 contents: " + editor2.getValue());
				}
			}
		});
		layout.addComponent(resetTextButton2);
		
		Button toggleReadOnlyButton2 = new Button("Toggle read-only editor #2");
		toggleReadOnlyButton2.addClickListener( new Button.ClickListener() {			

			@Override
			public void buttonClick(ClickEvent event) {
				editor2.setReadOnly( ! editor2.isReadOnly() );
			}
		});
		layout.addComponent(toggleReadOnlyButton2);

		Button toggleViewWithoutEditorButton2 = new Button("Toggle view-without-editor #2");
		toggleViewWithoutEditorButton2.addClickListener( new Button.ClickListener() {			
	
			@Override
			public void buttonClick(ClickEvent event) {
				editor2.setViewWithoutEditor( ! editor2.isViewWithoutEditor() );
			}
		});
		layout.addComponent(toggleViewWithoutEditorButton2);

		// Now some extra tests for modal windows, etc.
		layout.addComponent(new Button("Open Modal Subwindow", new ClickListener() {                      

			@Override
            public void buttonClick(ClickEvent event) {
                    Window sub = new Window("Subwindow modal");
                    VerticalLayout subLayout = new VerticalLayout();
                    sub.setContent(subLayout);
                    
                    CKEditorConfig config = new CKEditorConfig();
                    config.useCompactTags();
                    config.disableElementsPath();
                    config.disableSpellChecker();
                    config.enableVaadinSavePlugin();
                    // set BaseFloatZIndex 1000 higher than CKEditor's default of 10000; probably a result of an editor opening
                    // in a window that's on top of the main two editors of this demo app
                    config.setBaseFloatZIndex(11000); 
                    config.setHeight("150px");
                    
                    final CKEditor ckEditor = new CKEditor(config);
	                ckEditor.addValueChangeListener(new ValueChangeListener() {

						public void valueChange(String newValue) {
							Notification.show("CKEditor v" + ckEditor.getVersion() + "/" + getVersion() + " - POPUP MODAL contents: " + newValue);
	        			}
	        		});
	                ckEditor.focus();
                    
	                subLayout.addComponent(ckEditor);
                    
                    sub.setWidth("80%");
                    sub.setModal(true);
                    sub.center();
                    
                    event.getButton().getUI().addWindow(sub);
            }
        }));

		layout.addComponent(new Button("Open Non-Modal Subwindow with 100% Height", new ClickListener() {                      

			@Override
	        public void buttonClick(ClickEvent event) {
	                Window sub = new Window("Subwindow non-modal 100% height");
	                VerticalLayout subLayout = new VerticalLayout();
	                sub.setContent(subLayout);
	                sub.setWidth("80%");
	                sub.setHeight("500px");

	                subLayout.setSizeFull();
	                
	                CKEditorConfig config = new CKEditorConfig();
	                config.useCompactTags();
	                config.disableElementsPath();
	                config.disableSpellChecker();
	                config.enableVaadinSavePlugin();
                    // set BaseFloatZIndex 1000 higher than CKEditor's default of 10000; probably a result of an editor opening
                    // in a window that's on top of the main two editors of this demo app
                    config.setBaseFloatZIndex(11000); 
                    config.setStartupFocus(true);
	                
	                final CKEditor ckEditor = new CKEditor(config);
	                ckEditor.setHeight("100%");
	                ckEditor.addValueChangeListener(new ValueChangeListener() {

						public void valueChange(String newValue) {
							Notification.show("CKEditor v" + ckEditor.getVersion() + "/" + getVersion() + " - POPUP NON-MODAL 100% HEIGHT contents: " + ckEditor.getValue());
	        			}
	        		});
	                subLayout.addComponent(ckEditor);
	                subLayout.setExpandRatio(ckEditor,1.0f);
	                
	                final TextField textField = new TextField("TextField");
	                textField.setImmediate(true);
	                textField.addValueChangeListener(new Property.ValueChangeListener() {

						public void valueChange(ValueChangeEvent event) {
							Notification.show("TextField - POPUP NON-MODAL 100% HEIGHT contents: " + event.getProperty().getValue().toString());
	        			}
	        		});
	                subLayout.addComponent(textField);
	                
	                sub.center();
	                
	                event.getButton().getUI().addWindow(sub);
	        }
        }));
	}

	public String getVersion() {
		return "0.9.0_pre20130625";
	}
}