import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) {
        System.out.println("Iniciando juego");
        System.out.println(getClass().getResource("/view/MenuView.fxml"));
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("view/MenuView.fxml")
            );

            Scene scene = new Scene(loader.load());

            stage.setTitle("Monopoly");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
