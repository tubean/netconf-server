package server.rpc;

import org.w3c.dom.Node;
import server.IQuery;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public abstract class AbstractQuery extends RPCElement implements IQuery {
	private String messageId;
	private Operation operation;

	protected AbstractQuery(Operation operation) {
		this.operation = operation;
	}

	public String toXML() {
		StringBuilder str = new StringBuilder();

		str.append("<rpc message-id=\"" + messageId + "\">");

		StringWriter out = new StringWriter();
		try {
			TransformerFactory.newInstance().newTransformer().transform(new DOMSource(innerXml()), new StreamResult(out));
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}

		str.append(out.toString());
		str.append("</rpc>");

		return str.toString();
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Operation getOperation() {
		return operation;
	}

	public RPCElement getRpcElement() {
		return this;
	}

	protected abstract Node innerXml();
}