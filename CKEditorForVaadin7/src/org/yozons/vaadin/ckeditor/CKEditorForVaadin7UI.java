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

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class CKEditorForVaadin7UI extends UI {
	private static final long serialVersionUID = 375264049416405695L;

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
				"<p>CKEditor for Vaadin 7 is an entirely new JavaScriptComponent add-on. It comes with CKEditorField for use in FieldGroups, like this editor.</p>";

		CKEditorConfig config1 = new CKEditorConfig();
		config1.useCompactTags();
		config1.disableElementsPath();
		config1.setResizeDir(CKEditorConfig.RESIZE_DIR.HORIZONTAL);
		config1.disableSpellChecker();
		final CKEditorField editorField1 = new CKEditorField(config1);
		editorField1.setHeight(350, Unit.PIXELS);
		editorField1.setValue(editor1InitialValue);
		layout.addComponent(editorField1);
		
		editorField1.addValueChangeListener( new CKEditorField.ValueChangeListener() {
			private static final long serialVersionUID = 9185055723329190225L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				Notification.show("ValueChangeListener CKEditorField v" + editorField1.getVersion() + "/" + getVersion() + " - #1 contents: " + event.getProperty().getValue());
				editorField1.focus();
			}
		});
		
		Button testButton = new Button("Reset editor #1");
		testButton.addClickListener( new Button.ClickListener() {
			private static final long serialVersionUID = 7345617947432390275L;

			@Override
			public void buttonClick(ClickEvent event) {
				if ( ! editorField1.isReadOnly() ) {
					editorField1.setValue(editor1InitialValue);
					Notification.show("Reset CKEditor v" + editorField1.getVersion() + "/" + getVersion() + " - #1 contents: " + editorField1.getValue());
				}
			}
			
		});
		layout.addComponent(testButton);
		
		
		Button toggleReadOnlyButton1 = new Button("Toggle read-only editor #1");
		toggleReadOnlyButton1.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 5397350802604423436L;

			@Override
			public void buttonClick(ClickEvent event) {
				editorField1.setReadOnly( ! editorField1.isReadOnly() );
			}
		});
		layout.addComponent(toggleReadOnlyButton1);

		Button toggleViewWithoutEditorButton1 = new Button("Toggle view-without-editor #1");
		toggleViewWithoutEditorButton1.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = -2384699205371260656L;

			@Override
			public void buttonClick(ClickEvent event) {
				editorField1.setViewWithoutEditor( ! editorField1.isViewWithoutEditor() );
			}
		});
		layout.addComponent(toggleViewWithoutEditorButton1);
		
		// Now add in a second editor....
		final String editor2InitialValue = 
			"<p>Here is editor #2 as a CKEditor JavaScriptComponent.</p><h1>Hope you find this useful in your Vaadin 7 projects.</h1>";

		CKEditorConfig config2 = new CKEditorConfig();
		config2.addCustomToolbarLine("{ items : ['Source','Styles','Bold','VaadinSave','-','Undo','Redo','-','NumberedList','BulletedList'] }");
		config2.enableVaadinSavePlugin();
		config2.addToRemovePlugins("scayt");

		final CKEditor editor2 = new CKEditor(config2);
		editor2.setWidth(600,Unit.PIXELS);
		editor2.setHeight(350,Unit.PIXELS);
		layout.addComponent(editor2);
		editor2.setValue(editor2InitialValue);
		
		editor2.addValueChangeListener(new CKEditor.ValueChangeListener() {
			private static final long serialVersionUID = 218655218268367871L;

			public void valueChange(String newValue) {
				if ( ! newValue.equals(editor2.getValue()) )
					Notification.show("ERROR - Event value does not match editor #2's current value");
				else
					Notification.show("ValueChangeListener CKEditor v" + editor2.getVersion() + "/" + getVersion() + " - #2 contents: " + newValue);
			}
		});
		
		Button resetTextButton2 = new Button("Reset editor #2");
		resetTextButton2.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 4901941614079161802L;

			@Override
			public void buttonClick(ClickEvent event) {
				if ( ! editor2.isReadOnly() ) {
					editor2.setValue(editor2InitialValue);
					Notification.show("Reset CKEditor v" + editor2.getVersion() + "/" + getVersion() + " - #2 contents: " + editor2.getValue());
				}
			}
		});
		layout.addComponent(resetTextButton2);
		
		Button toggleReadOnlyButton2 = new Button("Toggle read-only editor #2");
		toggleReadOnlyButton2.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = -5479701197466348253L;

			@Override
			public void buttonClick(ClickEvent event) {
				editor2.setReadOnly( ! editor2.isReadOnly() );
			}
		});
		layout.addComponent(toggleReadOnlyButton2);

		Button toggleViewWithoutEditorButton2 = new Button("Toggle view-without-editor #2");
		toggleViewWithoutEditorButton2.addClickListener( new Button.ClickListener() {			
			private static final long serialVersionUID = 851106455148339016L;

			@Override
			public void buttonClick(ClickEvent event) {
				editor2.setViewWithoutEditor( ! editor2.isViewWithoutEditor() );
			}
		});
		layout.addComponent(toggleViewWithoutEditorButton2);

		// Now some extra tests for modal windows, etc.
		layout.addComponent(new Button("Open Modal Subwindow", new Button.ClickListener() {                      
			private static final long serialVersionUID = 3588690707843148999L;

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
	                ckEditor.addValueChangeListener(new CKEditor.ValueChangeListener() {
						private static final long serialVersionUID = -726248060054911895L;

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

		layout.addComponent(new Button("Open Non-Modal Subwindow with 100% Height", new Button.ClickListener() {                      
			private static final long serialVersionUID = 7205486861557298396L;

			@Override
	        public void buttonClick(ClickEvent event) {
	                Window sub = new Window("Subwindow non-modal 100% height");
	                VerticalLayout subLayout = new VerticalLayout();
	                subLayout.setSizeFull();
	                sub.setContent(subLayout);
	                sub.setWidth("80%");
	                sub.setHeight("500px");
	                UI.getCurrent().addWindow(sub);
	                
	                CKEditorConfig config = new CKEditorConfig();
	                config.useCompactTags();
	                config.disableElementsPath();
	                config.disableSpellChecker();
	                config.enableVaadinSavePlugin();
                    // set BaseFloatZIndex 1000 higher than CKEditor's default of 10000; probably a result of an editor opening
                    // in a window that's on top of the main two editors of this demo app
                    config.setBaseFloatZIndex(11000); 
                    config.setStartupFocus(true);
	                
	                final CKEditorField ckEditorField = new CKEditorField(config);
	                ckEditorField.addValueChangeListener(new CKEditorField.ValueChangeListener() {
						private static final long serialVersionUID = 51942525209836945L;

						@Override
						public void valueChange(ValueChangeEvent event) {
							Notification.show("ValueChangeListener CKEditorField v" + ckEditorField.getVersion() + "/" + getVersion() + " - POPUP NON-MODAL 100% HEIGHT contents: " + event.getProperty().getValue());
							editorField1.focus();
						}
	        		});
	                subLayout.addComponent(ckEditorField);
	                subLayout.setExpandRatio(ckEditorField,1.0f);
	                
	                final TextField textField = new TextField("TextField");
	                textField.setImmediate(true);
	                textField.addValueChangeListener(new Property.ValueChangeListener() {
						private static final long serialVersionUID = 2190248458901879579L;

						public void valueChange(ValueChangeEvent event) {
							Notification.show("TextField - POPUP NON-MODAL 100% HEIGHT contents: " + event.getProperty().getValue().toString());
	        			}
	        		});
	                subLayout.addComponent(textField);
	                
	                sub.center();
	        }
        }));

	}

	public String getVersion() {
		return "0.9.0_pre20130627";
	}
}