package server.rpc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class LoadConfigurationQuery extends AbstractQuery {
    private final String url;
    private final Action action;
    private final Format format;
    private final String config;

    public LoadConfigurationQuery(String url, Action action, Format format, String config) {
        super(Operation.LOAD_CONFIGURATION);
        this.url = url;
        this.action = action;
        this.format = format;
        this.config = config;
    }

    @Override
    protected Node innerXml() {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element loadConfiguration = doc.createElement(getOperation().getName());

            setAttribute(loadConfiguration, "url", url);
            setAttribute(loadConfiguration, "action", action.name());
            setAttribute(loadConfiguration, "format", format.name());

            maybeAddConfigBody(doc, loadConfiguration);

            return loadConfiguration;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private void maybeAddConfigBody(Document doc, Element loadConfiguration) {
        if (config != null) {
            final String tag;
            switch (format) {
                case text:
                    tag = "configuration-text";
                    break;
                case set:
                    tag = "configuration-set";
                    break;
                case xml:
                    tag = "configuration";
                    break;
                default:
                    throw new RuntimeException("Not implemented");
            }

            Element configBody = doc.createElement(tag);
            configBody.setTextContent(config);
            loadConfiguration.appendChild(configBody);
        }
    }

    private void setAttribute(Element loadConfiguration, String name, String value) {
        if (value != null) {
            loadConfiguration.setAttribute(name, value);
        }
    }

    public enum Action {
        merge,
        override,
        replace,
        update
    }

    public enum Format {
        text,
        set,
        xml
    }
}