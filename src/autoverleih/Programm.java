/**
 * @author Dimaa
 */

package autoverleih;
	
import autoverleih.managers.DatenbankManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class Programm extends Application 
{	
	public static void main(String[] args) 
	{
		Application.launch(args);
	}
	
	@Override
	public void start(Stage pPrimaryStage) throws Exception
	{
		DatenbankManager.getInstanz().initialize();

		Parent lRoot = FXMLLoader.load(this.getClass().getResource("ui/MainView.fxml"));
		pPrimaryStage.setTitle("Autoverleih");
		pPrimaryStage.setScene(new Scene(lRoot));
		pPrimaryStage.setResizable(false);
		pPrimaryStage.show();
	}
}
