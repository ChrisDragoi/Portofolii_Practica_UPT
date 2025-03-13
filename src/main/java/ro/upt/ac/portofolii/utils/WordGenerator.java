package ro.upt.ac.portofolii.utils;

import org.apache.poi.xwpf.usermodel.*;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WordGenerator {

    public static byte[] generatePortofoliuDocx(Portofoliu portofoliu) {
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            addSection(document, "1. Durata totală a pregătirii practice:", String.valueOf(portofoliu.getDurataPracticii()));
            addSection(document, "2. Calendarul pregătirii:", portofoliu.getOrar());
            addSection(document, "3. Perioada stagiului, timpul de lucru și orarul (de precizat zilele de pregătire practică în cazul timpului de lucru parțial): ", portofoliu.getDataInceput() + " - " + portofoliu.getDataSfarsit());
            addSection(document, "4. Adresa desfășurării stagiului de pregătire practică: ", portofoliu.getLoculDesfasurarii());
            addSection(document, "5. Deplasarea în afara locului unde este repartizat practicantul vizează următoarele locații: ", portofoliu.getLocatiiExtra());
            addSection(document, "6. Condiții de primire a studentului în stagiul de practică:", portofoliu.getCompetenteNecesare());
            addSection(document, "7. Modalități prin care se asigură complementaritatea între pregătirea dobândită de studentul practicant în instituția de învățământ superior și în cadrul stagiului de practică: ", portofoliu.getComplementareInvatamantPractica());
            addSection(document, "8. Numele și prenumele cadrului didactic care asigură supravegherea pedagogică a practicantului pe perioada stagiului de practică: ", portofoliu.getCadruDidactic().getNume() + " " + portofoliu.getCadruDidactic().getPrenume());
            addSection(document, "9. Drepturi și responsabilități ale cadrului didactic:", "Monitorizarea activității studentului, oferirea feedbackului constant și evaluarea finală. ");
            addSection(document, "10. Numele și prenumele tutorelui desemnat:", portofoliu.getTutore().getNume() + " " + portofoliu.getTutore().getPrenume());
            addSection(document, "11. Drepturi și responsabilități ale tutorelui de practică:", "Îndrumarea studentului în efectuarea sarcinilor și evaluarea progresului său pe perioada stagiului. ");
            addSection(document, "12. Tematica practicii și sarcinile studentului conform prevederilor din Fișa disciplinei a stagiului de practică: ", portofoliu.getTematicaSiSarcini());
            addSection(document, "13. Definirea competențelor care vor fi dobândite pe perioada stagiului de practică: ", portofoliu.getCompetenteDobandite());

            document.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Eroare la generarea documentului Word: ", e);
        }
    }

    private static void addSection(XWPFDocument document, String title, String content) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun titleRun = paragraph.createRun();
        titleRun.setBold(true);
        titleRun.setText(title);
        titleRun.addBreak();

        XWPFRun contentRun = paragraph.createRun();
        contentRun.setText(content != null ? content : "-");
        contentRun.addBreak();
    }
}
