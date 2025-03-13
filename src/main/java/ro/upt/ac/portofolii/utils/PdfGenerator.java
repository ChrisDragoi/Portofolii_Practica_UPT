package ro.upt.ac.portofolii.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generatePortofoliuPdf(Portofoliu portofoliu) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("PORTOFOLIU DE PRACTICĂ", boldFont));
            document.add(new Paragraph(" "));

            addSection(document, "1. Durata totală a pregătirii practice:", String.valueOf(portofoliu.getDurataPracticii()), boldFont, regularFont);
            addSection(document, "2. Calendarul pregătirii:", portofoliu.getOrar(), boldFont, regularFont);
            addSection(document, "3. Perioada stagiului, timpul de lucru și orarul (de precizat zilele de pregătire practică în cazul timpului de lucru parțial):", portofoliu.getDataInceput() + " - " + portofoliu.getDataSfarsit(), boldFont, regularFont);
            addSection(document, "4. Adresa desfășurării stagiului de pregătire practică:", portofoliu.getLoculDesfasurarii(), boldFont, regularFont);
            addSection(document, "5. Deplasarea în afara locului unde este repartizat practicantul vizează următoarele locații:", portofoliu.getLocatiiExtra(), boldFont, regularFont);
            addSection(document, "6. Condiții de primire a studentului în stagiul de practică:", portofoliu.getCompetenteNecesare(), boldFont, regularFont);
            addSection(document, "7. Modalități prin care se asigură complementaritatea între pregătirea dobândită de studentul practicant în instituția de învățământ superior și în cadrul stagiului de practică:", portofoliu.getComplementareInvatamantPractica(), boldFont, regularFont);
            addSection(document, "8. Numele și prenumele cadrului didactic care asigură supravegherea pedagogică a practicantului pe perioada stagiului de practică:", portofoliu.getCadruDidactic().getNume() + " " + portofoliu.getCadruDidactic().getPrenume(), boldFont, regularFont);
            addSection(document, "9. Drepturi și responsabilități ale cadrului didactic:", "Monitorizarea activității studentului și evaluarea finală.", boldFont, regularFont);
            addSection(document, "10. Numele și prenumele tutorelui desemnat:", portofoliu.getTutore().getNume() + " " + portofoliu.getTutore().getPrenume(), boldFont, regularFont);
            addSection(document, "11. Drepturi și responsabilități ale tutorelui de practică:", "Îndrumarea studentului și evaluarea progresului său.", boldFont, regularFont);
            addSection(document, "12. Tematica practicii și sarcinile studentului conform prevederilor din Fișa disciplinei a stagiului de practică:", portofoliu.getTematicaSiSarcini(), boldFont, regularFont);
            addSection(document, "13. Definirea competențelor care vor fi dobândite pe perioada stagiului de practică:", portofoliu.getCompetenteDobandite(), boldFont, regularFont);

            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Eroare la generarea PDF-ului: ", e);
        }
    }

    private static void addSection(Document document, String title, String content, Font titleFont, Font contentFont) throws DocumentException {
        Paragraph p = new Paragraph();
        p.add(new Chunk(title, titleFont));
        p.add(Chunk.NEWLINE);
        p.add(new Chunk(content != null ? content : "-", contentFont));
        p.add(Chunk.NEWLINE);

        document.add(p);
    }
}