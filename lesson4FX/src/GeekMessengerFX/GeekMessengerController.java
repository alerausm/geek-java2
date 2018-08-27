package GeekMessengerFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class GeekMessengerController implements GeekMessengerModel.OnGeekMessengerEventListener {

    @FXML
    WebView webView;
    private WebEngine webEngine;
    @FXML
    Button sendButton;


    @FXML
    TextField inputTextField;

    @FXML
    void handleSendButtonAction(){
        sendMessage();
    }

    @FXML
    public void handleInputTextFieldAction(ActionEvent ae){
        sendMessage();
    }


    private GeekMessengerModel model ;

    public void initModel(GeekMessengerModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
        this.model.addOnGeekMessengerEventListener(this);
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.setUserStyleSheetLocation(getClass().getResource("style.css").toString());
        webEngine.loadContent("<body><div id='content'></div></body>");
    }

    @Override
    public void onMessageReceived(GeekMessengerModel.GeekMessage message) {
        Document doc = webEngine.getDocument();
        Element el = doc.getElementById("content");
        el.appendChild(createMessageCard(doc,message));
        webEngine.executeScript("window.scrollTo(0, document.body.scrollHeight);");
  }

    private Node createMessageCard(Document doc, GeekMessengerModel.GeekMessage message) {
        Element card = doc.createElement("div");
        card.setAttribute("class","card");
        Element container = doc.createElement("div");
        container.setAttribute("class","container");

        Element authorBox = doc.createElement("h4");
        Element author = doc.createElement("b");
        author.appendChild(doc.createTextNode(message.getAuthor()));
        authorBox.appendChild(author);
        container.appendChild(authorBox);

        Element messageText = doc.createElement("p");
        messageText.appendChild(doc.createTextNode(message.getMessage()));
        Element sendDate = doc.createElement("span");
        sendDate.setAttribute("class","time-right");
        DateFormat sdf = new SimpleDateFormat("dd.MM kk:mm",Locale.getDefault());
        sendDate.appendChild(doc.createTextNode(sdf.format(message.getReceiveDate())));
        messageText.appendChild(sendDate);
        container.appendChild(messageText);

        card.appendChild(container);

        return card;
    }

    private void sendMessage() {
        if (inputTextField.getText().isEmpty()) return;
        this.model.addMessage("Я",inputTextField.getText(),new Date());
        inputTextField.setText("");
    }
}
