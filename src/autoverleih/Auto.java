/**
 * @author Dimaa
 */

package autoverleih;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Auto 
{
	private long id;
	private String modell;
	private String kennzeichen;
	private int kilometerstand;
	private BigDecimal neuwert;
	private LocalDate anschaffungsdatum;
	
	public long getID() 
	{
		return this.id;
	}

	public void setID(long pID)
	{
		this.id = pID;
	}

	public String getModell() {
		return this.modell;
	}

	public void setModell(String pModell) 
	{
		this.modell = pModell;
	}

	public String getKennzeichen() 
	{
		return this.kennzeichen;
	}

	public void setKennzeichen(String pKennzeichen) 
	{
		this.kennzeichen = pKennzeichen;
	}

	public int getKilometerstand()
	{
		return this.kilometerstand;
	}

	public void setKilometerstand(int pKilometerstand) 
	{
		this.kilometerstand = pKilometerstand;
	}

	public BigDecimal getNeuwert() 
	{
		return this.neuwert;
	}

	public void setNeuwert(BigDecimal pNeuwert)
	{
		this.neuwert = pNeuwert;
	}

	public LocalDate getAnschaffungsdatum() 
	{
		return this.anschaffungsdatum;
	}

	public void setAnschaffungsdatum(LocalDate pAnschaffungsdatum) 
	{
		this.anschaffungsdatum = pAnschaffungsdatum;
	}
}
