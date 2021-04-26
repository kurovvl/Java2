
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;



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
        if (!msgText.getText().isEmpty()) {
            try {
                writeData(msgText.getText());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            allMessages.appendText("Я: " + msgText.getText() + "\n");
        }
        msgText.clear();
    }

    @FXML
    public void initialize() throws IOException{
        try {
            connectTo("127.0.0.1", 5190);
        } catch (IOException e) {

            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Не могу подключиться!");
            alert.setContentText("Ошибка подключения: \n" + e.getMessage());
            alert.setResizable(false);
            alert.showAndWait();
            throw e;
        }
    }


    public void onMsgTextKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER && keyEvent.isControlDown())
            onSendBtnClick(null);

    }


    public void onKeyPressedDummy(KeyEvent keyEvent) {
        msgText.requestFocus();
    }


    private void addMessage(String message) {
        message = message.trim();
        if (!message.isEmpty())
            allMessages.appendText("Server: " + message + "\n");
    }


    Socket socket = null;

    private void connectTo(String serverIP, int serverPort) throws IOException {
        socket = new Socket(serverIP, serverPort);
        if (socket != null) {
            var reader = new Thread(() -> {
                try {
                    readData();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            reader.setDaemon(true);
            reader.start();
        }
    }

    private void readData() throws IOException {
        DataInputStream input = new DataInputStream(socket.getInputStream());
        while (true) {
            String s = input.readUTF();
            addMessage(s);
        }
    }

    private void writeData(String message) throws IOException {
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        message = message.trim();
        if (!message.isEmpty())
            output.writeUTF(message);
        //output
    }


}


