package de.hahn.blog.accessresourcesfrompath.view.resource;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import javax.faces.event.ActionEvent;


import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

public class ResourceReaderBean {
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(ResourceReaderBean.class);
    private Properties mViewProperties = null;
    private static String PROPERTY_FILE =
        "de/hahn/blog/accessresourcesfrompath/view/resource/view.properties";

    private void initProperties() {
        // instead of using the de.hahn.testproxy.backingbeans.test.properties you have to use de/hahn/testproxy/backingbeans/test.properties
        InputStream asStream =
            this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE);
        if (asStream == null) {
            // file not found
            _logger.info("File not found: '" + PROPERTY_FILE + "'");
            return;
        }
        mViewProperties = new Properties();
        try {
            mViewProperties.load(asStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!mViewProperties.isEmpty()) {
            _logger.info("Properties loaded: " + mViewProperties.toString());
        }

    }

    public void getViewResourceListner(ActionEvent actionEvent) {
        String value = "";
        if (mViewProperties == null)
            initProperties();

        BindingContainer bindingContainer =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding attrKey =
            (AttributeBinding)bindingContainer.getControlBinding("ViewResourceKey1");
        String key = (String)attrKey.getInputValue();
        if (mViewProperties != null && key != null) {
            String val = mViewProperties.getProperty(key);
            AttributeBinding attrValue =
                (AttributeBinding)bindingContainer.getControlBinding("ViewResourceValue1");
            attrValue.setInputValue(val);
        }
    }
    
    public ResourceReaderBean() {
    }

    public String cb2_action() {
        // Add event code here...
        return null;
    }
}
