package ro.upt.ac.portofolii.portofoliu;

import jakarta.persistence.Embeddable;

@Embeddable
public class TutorePractica {
    private String nume;
    private String prenume;
    private String functie;
    private String drepturiSiResponsabilitati;

    public TutorePractica()
    {
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getDrepturiSiResponsabilitati() {
        return drepturiSiResponsabilitati;
    }

    public void setDrepturiSiResponsabilitati(String drepturiSiResponsabilitati) {
        this.drepturiSiResponsabilitati = drepturiSiResponsabilitati;
    }

    public String getNume()
    {
        return nume;
    }

    public void setNume(String nume)
    {
        this.nume = nume;
    }

    public String getPrenume()
    {
        return prenume;
    }

    public void setPrenume(String prenume)
    {
        this.prenume = prenume;
    }

    public String toString()
    {
        return nume+" "+prenume;
    }
}
