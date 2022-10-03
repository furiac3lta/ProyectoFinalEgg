package com.egg.proyectoFinal.utils;

import com.egg.proyectoFinal.entities.Persona;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;
import java.util.Map;

@Component("/views/personas/personas")
public class ListarClientesPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Persona> personas = (List<Persona>) model.get("personas");

//        Fuente para titulos
        Font fuenteTitulo = FontFactory.getFont("Helvetica", 24, Color.GRAY);
        Font fuenteTituloColumnas = FontFactory.getFont(FontFactory.HELVETICA_BOLD ,12,Color.DARK_GRAY);
        Font fuenteDataCeldas = FontFactory.getFont("Helvetica" ,10,Color.BLACK);

        document.setPageSize(PageSize.A4.rotate());
        document.setMargins(-20,-20,40,20);
        document.open();
        PdfPCell celda = null;

        /*Tabla Para El Titulo del PDF*/
        PdfPTable tablaTitulo = new PdfPTable(1);
        celda = new PdfPCell(new Phrase("LISTADO DE USUARIOS", fuenteTitulo));
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(241,196,15));
        celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setPadding(30);
        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);

        /*Tabla Para Mostrar Listado de Usuarios*/
        PdfPTable tablaPersonas = new PdfPTable(6);
        tablaPersonas.setWidths(new float[] {0.8f, 2f, 2f, 1.5f, 3.5f, 1.5f});

        celda = new PdfPCell(new Phrase("ID", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaPersonas.addCell(celda);

        celda = new PdfPCell(new Phrase("NOMBRE", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaPersonas.addCell(celda);

        celda = new PdfPCell(new Phrase("APELLIDO", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaPersonas.addCell(celda);

        celda = new PdfPCell(new Phrase("TELEFONO", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaPersonas.addCell(celda);

        celda = new PdfPCell(new Phrase("EMAIL", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaPersonas.addCell(celda);



        celda = new PdfPCell(new Phrase("OFICIO", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaPersonas.addCell(celda);

        /*Bucle For, mostrar todos los datos de los usuarios*/
        for (Persona persona : personas) {
            celda = new PdfPCell(new Phrase(persona.getId().toString(), fuenteDataCeldas));
            celda.setPadding(5);
            tablaPersonas.addCell(celda);

            celda = new PdfPCell(new Phrase(persona.getNombre(), fuenteDataCeldas));
            celda.setPadding(5);
            tablaPersonas.addCell(celda);

            celda = new PdfPCell(new Phrase(persona.getApellido(), fuenteDataCeldas));
            celda.setPadding(5);
            tablaPersonas.addCell(celda);

            celda = new PdfPCell(new Phrase(String.valueOf(persona.getTelefono()), fuenteDataCeldas));
            celda.setPadding(5);
            tablaPersonas.addCell(celda);

            celda = new PdfPCell(new Phrase(persona.getEmail(), fuenteDataCeldas));
            celda.setPadding(5);
            tablaPersonas.addCell(celda);

            celda = new PdfPCell(new Phrase(persona.getServicio().getTipo(), fuenteDataCeldas));
            celda.setPadding(5);
            tablaPersonas.addCell(celda);



        }
//        personas.forEach(persona ->{
//            tablaPersonas.addCell(persona.getId().toString());
//            tablaPersonas.addCell(persona.getNombre());
//            tablaPersonas.addCell(persona.getApellido());
//            tablaPersonas.addCell(persona.getEmail());
//            tablaPersonas.addCell(persona.getTelefono().toString());
//            tablaPersonas.addCell(persona.getServicio().getTipo());
//
//        });


        /*Anexamos las Tablas al Documento*/
        document.add(tablaTitulo);
        document.add(tablaPersonas);


    }
}
