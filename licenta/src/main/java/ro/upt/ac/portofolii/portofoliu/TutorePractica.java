package ro.upt.ac.portofolii.portofoliu;

import jakarta.persistence.Embeddable;

@Embeddable
public class TutorePractica {
    private String nume_p;
    private String prenume_p;
    private String functie_p;
    private String drepturiSiResponsabilitati;

    public TutorePractica()
    {
    }

    public String getFunctie_p() {
        return functie_p;
    }

    public void setFunctie_p(String functie) {
        this.functie_p = functie;
    }

    public String getDrepturiSiResponsabilitati() {
        return drepturiSiResponsabilitati;
    }

    public void setDrepturiSiResponsabilitati(String drepturiSiResponsabilitati) {
        this.drepturiSiResponsabilitati = drepturiSiResponsabilitati;
    }

    public String getNume_p()
    {
        return nume_p;
    }

    public void setNume_p(String nume)
    {
        this.nume_p = nume;
    }

    public String getPrenume_p()
    {
        return prenume_p;
    }

    public void setPrenume_p(String prenume)
    {
        this.prenume_p = prenume;
    }

    public String toString()
    {
        return nume_p +" "+ prenume_p;
    }
}
