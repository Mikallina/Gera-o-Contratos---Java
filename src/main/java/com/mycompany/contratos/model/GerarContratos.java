package com.mycompany.contratos.model;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.mycompany.contratos.dao.Cliente;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerarContratos {

    public void gerarContratos(Cliente cliente) throws IOException {
        String caminho = "C:/Users/mi_bo/OneDrive/Documentos/NetBeansProjects/Contratos/";
        String outputPath = caminho + "ContratosGerados/";

        // Cria o diretório de saída se não existir
        File outputDir = new File(outputPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        String fileName = cliente.getCnpj().replace(".", "").replace("-", "").replace("/", "");
        String pdfFileName = outputPath + "Contrato_" + fileName + ".pdf";

        // Verifica se o arquivo já existe
        int fileCounter = 1;
        while (new File(pdfFileName).exists()) {
            pdfFileName = outputPath + "Contrato_" + fileName + "_" + fileCounter + ".pdf";
            fileCounter++;
        }

        // Gera o PDF usando o modelo gráfico
        generatePdf(cliente, pdfFileName);
        System.out.println("Contrato gerado: " + pdfFileName);
    }

    private void generatePdf(Cliente cliente, String pdfFile) {
        try (PdfWriter writer = new PdfWriter(pdfFile);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            PdfFont font = PdfFontFactory.createFont();
            document.add(new Paragraph("Contrato de Prestação de Serviços")
                    .setFont(font)
                    .setFontSize(24)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("\n")); // Espaço

            // Cria tabelas
            List<Table> tables = new ArrayList<>();
            tables.add(new Table(new float[]{1, 3}));
            tables.add(new Table(new float[]{1, 3}));
            tables.add(new Table(new float[]{1, 3}));

            // Adiciona células às tabelas
            addCell(tables.get(0), "INFORMAÇÕES FORNECIDAS", "");
            addCell(tables.get(0), "CNPJ:", cliente.getCnpj());
            
            addCell(tables.get(1), "Nome:", cliente.getRazaoSocial());
            addCell(tables.get(1), "CPF:", cliente.getCnpj());
            addCell(tables.get(1), "Email:", cliente.getEmail());
            
            addCell(tables.get(2), "Telefone:", cliente.getTelefone());
            addCell(tables.get(2), "Endereço:", cliente.getEndereco());
            addCell(tables.get(2), "Bairro:", cliente.getBairro());
            addCell(tables.get(2), "Cidade:", cliente.getCidade());
            addCell(tables.get(2), "Estado:", cliente.getEstado());
            addCell(tables.get(2), "CEP:", cliente.getCep());
            addCell(tables.get(0), "Data:", cliente.getData());

            // Adiciona tabelas ao documento
            for (Table table : tables) {
                document.add(table.setWidth(UnitValue.createPercentValue(100)));
            }

            document.add(new Paragraph("\n")); // Espaço
            document.add(new Paragraph("Este é um contrato gerado automaticamente.")
                    .setTextAlignment(TextAlignment.CENTER));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCell(Table table, String label, String value) {
        table.addCell(new Cell().add(new Paragraph(label).setBold()).setTextAlignment(TextAlignment.LEFT));
        table.addCell(new Cell().add(new Paragraph(value)).setTextAlignment(TextAlignment.LEFT));
    }
}