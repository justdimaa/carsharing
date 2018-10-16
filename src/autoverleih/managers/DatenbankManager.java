package autoverleih.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import autoverleih.ui.MainController;

public final class DatenbankManager 
{
	private static final String cDATABASE_URL 	 = "jdbc:mysql://5.189.152.131:3306/school_spectrum";
	private static final String cUSERNAME 	     = "school";
	private static final String cPASSWORD        = "DbXrUttqOQ7mGbMO";
	private static final String cTIME_ZONE       = "Europe/Berlin";

	private static DatenbankManager datenbankManager;
	
	private Connection verbindung;
	private Properties eigenschaften;
	
	/**
	 * Initialisiert die Datenbankverbindung {@link #verbindung}.
	 */
	public void initialize()
	{
		try
		{
		    this.verbindung = DriverManager.getConnection(DatenbankManager.cDATABASE_URL, this.getEigenschaften());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public Connection getVerbindung() 
	{
		return this.verbindung;
	}

	public Properties getEigenschaften()
	{
		if (this.eigenschaften == null)
		{
			this.eigenschaften = new Properties();
			this.eigenschaften.setProperty("user",           DatenbankManager.cUSERNAME);
			this.eigenschaften.setProperty("password",       DatenbankManager.cPASSWORD);
			this.eigenschaften.setProperty("serverTimezone", DatenbankManager.cTIME_ZONE);
		}
		
		return this.eigenschaften;
	}
	
	public static DatenbankManager getInstanz()
	{
		if (DatenbankManager.datenbankManager == null)
		{
			DatenbankManager.datenbankManager = new DatenbankManager();
		}
		
		return DatenbankManager.datenbankManager;
	}
}
