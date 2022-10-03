package com.egg.proyectoFinal.utils;

import com.egg.proyectoFinal.entities.Orden;
import com.egg.proyectoFinal.entities.Persona;
import com.lowagie.text.Font;
import com.lowagie.text.*;
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

@Component("/views/ordenes/ordenes_admin")
public class ListarOrdenesPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Orden> ordenes = (List<Orden>) model.get("ordenes");

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
        celda = new PdfPCell(new Phrase("LISTADO DE ORDENES", fuenteTitulo));
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(241,196,15));
        celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        celda.setPadding(30);
        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);

        /*Tabla Para Mostrar Listado de Ordenes*/
        PdfPTable tablaOrdenes = new PdfPTable(6);
        tablaOrdenes.setWidths(new float[] {1.5f, 3f, 2.5f, 2.5f, 1.5f, 1.5f});

        celda = new PdfPCell(new Phrase("Nº ORDEN", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaOrdenes.addCell(celda);

        celda = new PdfPCell(new Phrase("DETALLE", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaOrdenes.addCell(celda);

        celda = new PdfPCell(new Phrase("EMAIL CLIENTE", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaOrdenes.addCell(celda);

        celda = new PdfPCell(new Phrase("EMAIL PRESTADOR", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaOrdenes.addCell(celda);

        celda = new PdfPCell(new Phrase("TRABAJO ACTIVO", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaOrdenes.addCell(celda);



        celda = new PdfPCell(new Phrase("FECHA", fuenteTituloColumnas));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaOrdenes.addCell(celda);

        /*Bucle For, mostrar todos los datos de los usuarios*/
        for (Orden orden  : ordenes) {
            celda = new PdfPCell(new Phrase(orden.getId().toString(), fuenteDataCeldas));
            celda.setPadding(5);
            tablaOrdenes.addCell(celda);

            celda = new PdfPCell(new Phrase(orden.getDetalle(), fuenteDataCeldas));
            celda.setPadding(5);
            tablaOrdenes.addCell(celda);

            celda = new PdfPCell(new Phrase(orden.getEmailc(), fuenteDataCeldas));
            celda.setPadding(5);
            tablaOrdenes.addCell(celda);

            celda = new PdfPCell(new Phrase(orden.getEmailp(), fuenteDataCeldas));
            celda.setPadding(5);
            tablaOrdenes.addCell(celda);

            celda = new PdfPCell(new Phrase(String.valueOf(orden.getActivo()), fuenteDataCeldas));
            celda.setPadding(5);
            tablaOrdenes.addCell(celda);

            celda = new PdfPCell(new Phrase(String.valueOf(orden.getCreatedAt()), fuenteDataCeldas));
            celda.setPadding(5);
            tablaOrdenes.addCell(celda);



        }

        /*Anexamos las Tablas al Documento*/
        document.add(tablaTitulo);
        document.add(tablaOrdenes);
    }
}
