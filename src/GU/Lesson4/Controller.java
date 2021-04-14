package GU.Lesson4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Controller {
    @FXML
    private TextArea allMessages;
    @FXML
    private TextField msgText;
    @FXML
    private Button sendBtn;
    @FXML
    private javafx.scene.layout.GridPane parentBox;

    @FXML
    private void onSendBtnClick(ActionEvent actionEvent) {
        if (!msgText.getText().isEmpty())
            allMessages.appendText(msgText.getText() + "\n");
        msgText.clear();
    }
    @FXML
    public void initialize(){

    }


    public void onMsgTextKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER && keyEvent.isControlDown())
            onSendBtnClick(null);

    }

    public void onKeyPressedDummy(KeyEvent keyEvent) {
        msgText.requestFocus();
    }
}
