import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatController {
    private static boolean continueRead = true;


    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    @FXML
    private TextArea allMessages;
    @FXML
    private TextField msgText;

    @FXML
    private Button sendBtn;
    @FXML
    private javafx.scene.layout.GridPane parentBox;

    @FXML
    private void initialize() throws IOException {
        try {
            openLoginWindow();
            openConnection();
            addCloseListener();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка подключения");
            alert.setHeaderText("Сервер не работает");
            alert.setContentText("Не забудь включить сервер!");
            alert.showAndWait();
            e.printStackTrace();
            throw e;
        }
    }

    private void openLoginWindow() throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("auth.fxml"));
        Statics.loginStage = new Stage();
        Statics.loginStage.initModality(Modality.APPLICATION_MODAL);
        Statics.loginStage.setScene(new Scene(root));
        Statics.loginStage.setTitle("Вход");
        Statics.loginStage.showAndWait();
    }

    private void openConnection() throws IOException {
        socket = ServerConnection.getSocket();
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {
                while (continueRead) {
                    System.out.println("Готовы считывать");
                    String strFromServer = in.readUTF();
                    System.out.println("Считал" + strFromServer);
                    if (strFromServer.equalsIgnoreCase("/end")) {
                        System.out.println("Конец цикла");
                        break;
                    }
                    allMessages.appendText(strFromServer + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void addCloseListener() {
        EventHandler<WindowEvent> onCloseRequest = Main.mainStage.getOnCloseRequest();
        Main.mainStage.setOnCloseRequest(event -> {
            closeConnection();
            if (onCloseRequest != null) {
                onCloseRequest.handle(event);
            }
        });
    }

    private void closeConnection() {
        try {
            continueRead = false;
            out.writeUTF("/end");
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onSendBtnClick(ActionEvent actionEvent) {
        if (!msgText.getText().trim().isEmpty()) {
            try {
                out.writeUTF(msgText.getText().trim());
                msgText.clear();
                msgText.requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка отправки сообщения");
                alert.setHeaderText("Ошибка отправки сообщения");
                alert.setContentText("При отправке сообщения возникла ошибка: " + e.getMessage());
                alert.show();
            }
        }
    }
    public void onKeyPressedDummy(KeyEvent keyEvent) {
        msgText.requestFocus();
    }
    public void onMsgTextKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER && keyEvent.isControlDown())
            onSendBtnClick(null);

    }

}
