/**
 * @author Dimaa
 */

package autoverleih.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

import autoverleih.Auto;
import autoverleih.managers.DatenbankManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public final class MainController
{
    @FXML private TableView<Auto> tvAutos;
    @FXML private TableColumn<Auto, String> tcModell;
    @FXML private TableColumn<Auto, LocalDateTime> tcAnschaffungsdatum;

    @FXML private CheckBox cbName;
    @FXML private CheckBox cbAnschaffungsdatum;

    @FXML private RadioButton rbJava;
    @FXML private RadioButton rbMySQL;

    @FXML private Label lbNachricht;
    
	public void initialize()
	{
		// Macht die Columns in der Tabelle tvAutos nutzbar.
		this.tcModell.setCellValueFactory(new PropertyValueFactory<>("modell"));
		this.tcAnschaffungsdatum.setCellValueFactory(new PropertyValueFactory<>("anschaffungsdatum"));
		
		// Setzt die Sortieroperatoren in eine Gruppe, 
		// sodass nur einer aktiv sein kann.
		ToggleGroup lSortieroperatoren = new ToggleGroup();		
		this.rbJava.setToggleGroup(lSortieroperatoren);
		this.rbMySQL.setToggleGroup(lSortieroperatoren);
	}
	
	/**
	 * Listet alle Autos, aus der Datenbank, in der Tabelle {@link #tvAutos} auf.
	 * @param pEvent
	 */
    @FXML
    private void auflistenAutos(ActionEvent pEvent) 
    {
    	if (!this.tvAutos.getItems().isEmpty())
    	{
        	this.tvAutos.getItems().clear();
    	}
    	
		ArrayList<Auto> lAutos = this.getAutos();
		
		this.lbNachricht.setText(String.format("Es wurden %s Datenstze gefunden", lAutos.size()));
		
		this.sortierenAutos(lAutos);
		
		for (Auto lAuto : lAutos)
		{
			this.tvAutos.getItems().add(lAuto);
		}		
    }

    /**
     * Aktiviert oder deaktivert die Sortiermethoden {@link #rbJava} und {@link #rbMySQL},
     * je nachdem, ob die Sortierkriterien {@link #cbName} und {@link #cbAnschaffungsdatum} ausgewählt wurden.
     * @param pEvent
     */
    @FXML
    private void wechselnSortiermethoden(ActionEvent pEvent)
    {
    	boolean istSortierenAktiviert = this.cbName.isSelected() || this.cbAnschaffungsdatum.isSelected();
    	
		this.rbJava.setDisable(!istSortierenAktiviert);
		this.rbMySQL.setDisable(!istSortierenAktiviert);
    }
    
    /**
     * Sortiert die Liste <code>pAutos</code> 
     * nach den ausgewhlten Kriterien {@link #cbName} und {@link #cbAnschaffungsdatum},
     * wenn die Sortiermethode {@link #rbJava} ausgewählt wurde.
     * @param pAutos Die zu sortierende Liste.
     */
    private void sortierenAutos(ArrayList<Auto> pAutos)
    {
    	if (!this.rbJava.isSelected()) return;
    	
    	Comparator<Auto> lComparator = this.getAutoComparator();
    	
    	if (lComparator != null)
    	{
        	pAutos.sort(lComparator);
    	}
    }
	
    /**
     * @return Alle Datensätze aus der Datenbanktabelle <code>autos</code> als Auto.
     * @see autoverleih.Auto
     */
	public ArrayList<Auto> getAutos()
	{		
		try
		{
			Connection lVerbindung 		 = DatenbankManager.getInstanz().getVerbindung();			
			PreparedStatement lStatement = lVerbindung.prepareStatement(this.getDatenbankAutoBefehl());
			
			try (ResultSet lErgebnis = lStatement.executeQuery())
			{
				ArrayList<Auto> lAutos = new ArrayList<Auto>();
				
				while (lErgebnis.next())
				{
					Auto lAuto = new Auto();
					lAuto.setID(lErgebnis.getLong("id"));
					lAuto.setModell(lErgebnis.getString("modell"));
					lAuto.setKennzeichen(lErgebnis.getString("kennzeichen"));
					lAuto.setKilometerstand(lErgebnis.getInt("kilometerstand"));
					lAuto.setNeuwert(lErgebnis.getBigDecimal("neuwert"));
					lAuto.setAnschaffungsdatum(lErgebnis.getDate("anschaffungsdatum").toLocalDate());
					lAutos.add(lAuto);
				}
				
				return lAutos;
			}			
		}
		catch (Exception ex)
		{
			this.lbNachricht.setText("Fehler im System");
			ex.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @return Den Datenbankbefehl, um die Datensätze der Tabelle <code>autos</code> auszulesen.
	 */
	private String getDatenbankAutoBefehl()
	{
		final String cQUERY = "SELECT * FROM `autos`";
		
		if (this.rbMySQL.isSelected())
		{
		    if (this.cbName.isSelected())
		    {
			    final String cSORT = " ORDER BY `modell`";
			
			    if (this.cbAnschaffungsdatum.isSelected())
			    {
			    	return cQUERY + cSORT + ", `anschaffungsdatum`";
			    }
			
			    return cQUERY + cSORT;
		    }
		    else if (this.cbAnschaffungsdatum.isSelected())
		    {
			    return cQUERY + " ORDER BY `anschaffungsdatum`";
		    }
		}
		
		return cQUERY;
	}
	
	/**
	 * @return Den Comparator, um die Autoliste nach den ausgewählten Kriterien zu sortieren.
	 */
	private Comparator<Auto> getAutoComparator()
	{
		Comparator<Auto> lComparator = null;
    	
    	if (this.cbName.isSelected())
    	{
    		lComparator = Comparator.comparing(Auto::getModell);
        	
        	if (this.cbAnschaffungsdatum.isSelected())
        	{
        		lComparator = lComparator.thenComparing(Auto::getAnschaffungsdatum);
        	}
    	}
    	else if (this.cbAnschaffungsdatum.isSelected())
    	{
    		lComparator = Comparator.comparing(Auto::getAnschaffungsdatum);
    	}
    	
    	return lComparator;
	}
}
