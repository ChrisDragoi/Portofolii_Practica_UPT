package ro.upt.ac.portofolii.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import ro.upt.ac.portofolii.portofoliu.Portofoliu;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;


import java.awt.*;
import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generatePortofoliuPdf(Portofoliu portofoliu) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font boldFont = loadFont(Font.BOLD);
            Font regularFont = loadFont(Font.NORMAL);

            Paragraph p = new Paragraph();
            p.add(new Chunk("ANEXĂ", boldFont));
            p.add(new Chunk(" la ", regularFont));
            p.add(new Chunk("Convenția-cadru", boldFont));
            p.add(new Chunk(" privind efectuarea stagiului de practică în cadrul programelor de studii universitare de licență/masterat ", regularFont));
            document.add(p);
            document.add(new Paragraph(" ", regularFont));
            p = new Paragraph();

            Paragraph title = new Paragraph("PORTOFOLIU DE PRACTICĂ", boldFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            p.add(new Chunk("aferent ", regularFont));
            p.add(new Chunk("Convenției-cadru", boldFont));
            p.add(new Chunk(" privind efectuarea stagiului de practică în cadrul programelor de studii universitare de licență și masterat", regularFont));
            document.add(p);
            document.add(new Paragraph(" "));

            addSection(document,1,"Durata totală a pregătirii practice:", String.valueOf(portofoliu.getDurataPracticii()), boldFont, regularFont, regularFont);
            addSection(document,2,"Calendarul pregătirii:", portofoliu.getOrar(), boldFont, regularFont, regularFont);
            addSection(document, 3,"Perioada stagiului, timpul de lucru și orarul (de precizat zilele de pregătire practică în cazul timpului de lucru parțial):", portofoliu.getDataInceput() + " - " + portofoliu.getDataSfarsit(),boldFont, regularFont, regularFont);
            addSection(document, 4,"Adresa unde se va derula stagiul de pregătire practică:", portofoliu.getLoculDesfasurarii(), boldFont,regularFont, regularFont);
            addSection(document, 5,"Deplasarea în afara locului unde este repartizat practicantul vizează următoarele locații:", portofoliu.getLocatiiExtra(),boldFont, regularFont, regularFont);
            addSection(document, 6,"Condiții de primire a studentului/masterandului în stagiul de practică:", portofoliu.getCompetenteNecesare(),boldFont, regularFont, regularFont);
            addSection(document, 7,"Modalități prin care se asigură complementaritatea între pregătirea dobândită de studentul practicant în instituția de învățământ superior și în cadrul stagiului de practică:", portofoliu.getComplementareInvatamantPractica(),boldFont, regularFont, regularFont);
            addSection(document, 8,"Numele și prenumele cadrului didactic care asigură supravegherea pedagogică a practicantului pe perioada stagiului de practică:", portofoliu.getCadruDidactic().getNume() + " " + portofoliu.getCadruDidactic().getPrenume(),boldFont, regularFont, regularFont);
            addSection(document, 9,"Drepturi și responsabilități ale cadrului didactic care asigură supravegherea pedagogică a practicantului pe perioada stagiului de practică:", "Monitorizarea activității studentului și evaluarea finală.",boldFont, regularFont, regularFont);
            addSection(document, 10,"Numele și prenumele tutorelui desemnat de întreprindere care va asigura respectarea condițiilor de pregătire și dobândirea de către practicant a competențelor profesionale planificate pentru perioada stagiului de practică:", portofoliu.getTutore().getNume() + " " + portofoliu.getTutore().getPrenume(), boldFont, regularFont, regularFont);
            addSection(document, 11,"Drepturi și responsabilități ale tutorelui de practică desemnat de partenerul de practică:", "Îndrumarea studentului și evaluarea progresului său.",boldFont, regularFont, regularFont);
            addSection(document, 12,"Tematica practicii și sarcinile studentului conform prevederilor din Fișa disciplinei a stagiului de practică:", portofoliu.getTematicaSiSarcini(),boldFont, regularFont, regularFont);
            addSection(document, 13,"Definirea competențelor care vor fi dobândite pe perioada stagiului de practică:", portofoliu.getCompetenteDobandite(),boldFont, regularFont, regularFont);


            PdfPTable table = getPdfPTable(boldFont, regularFont);

            document.add(table);

            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Eroare la generarea PDF-ului: ", e);
        }
    }

    private static PdfPTable getPdfPTable(Font boldFont, Font regularFont) {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100); // Să ocupe lățimea paginii
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        String[] headers = {"Competențe", "Modulul de pregătire", "Locul de muncă", "Activități planificate", "Observații"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, boldFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(new Color(200, 200, 200)); // Fundal gri deschis
            table.addCell(headerCell);
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                PdfPCell cell = new PdfPCell(new Phrase(" ", regularFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
        }
        return table;
    }

    private static void addSection(Document document, int i, String title, String content, Font nrFont, Font titleFont, Font contentFont) throws DocumentException {
        Paragraph p = new Paragraph();
        p.add(new Chunk(i+". ", nrFont));
        p.add(new Chunk(title, titleFont));
        p.add(Chunk.NEWLINE);
        p.add(new Chunk(content != null ? content : "-", contentFont));
        p.add(Chunk.NEWLINE);

        document.add(p);
    }
    private static Font loadFont(int style) {
        try {
            BaseFont baseFont = BaseFont.createFont("static/dejavu-sans/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            return new Font(baseFont, (float) 12, style);
        } catch (Exception e) {
            throw new RuntimeException("Eroare la încărcarea fontului: " + "static/dejavu-sans/DejaVuSans.ttf", e);
        }
    }

}