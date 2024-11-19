package org.BackEnd;

import com.aspose.pdf.*;

import java.util.*;

public class PDFGenerator {

    public static void GeneratePDF(HashMap<String, HashMap<String, Integer>> categoryProductMap, String pdfFilePath) {
        // Initialize the document
        try {
            Document document = new Document();
            Page page = document.getPages().add();

            // Create a font for the text
            TextFragment textFragment = new TextFragment("Ingredient Summary Report");
            textFragment.getTextState().setFont(FontRepository.findFont("Arial"));
            textFragment.getTextState().setFontSize(16);
            page.getParagraphs().add(textFragment);

            // Add some space between the title and the table
            page.getParagraphs().add(new TextFragment("\n"));

            // Loop through categories and products to generate the content
            for (String category : categoryProductMap.keySet()) {
                // Add the category heading
                textFragment = new TextFragment("Category: " + category);
                textFragment.getTextState().setFont(FontRepository.findFont("Arial"));
                textFragment.getTextState().setFontSize(12);
                page.getParagraphs().add(textFragment);

                // Create a table with two columns: Product Name and Weight
                Table table = new Table();
                table.setColumnWidths("200 100"); // Set column width

                // Add the header row
                Row headerRow = table.getRows().add();
                headerRow.getCells().add("Product Name");
                headerRow.getCells().add("Total Weight (grams)");

                // Add product rows
                HashMap<String, Integer> products = categoryProductMap.get(category);
                for (String productName : products.keySet()) {
                    Row row = table.getRows().add();
                    row.getCells().add(productName);
                    row.getCells().add(String.valueOf(products.get(productName)));
                }

                // Add the table to the page
                page.getParagraphs().add(table);

                // Add some space between categories
                page.getParagraphs().add(new TextFragment("\n"));
            }

            // Save the document
            document.save(pdfFilePath);
        } catch (Exception e) {
            Controller.getInstance().SetInfoText("Error generating pdf, check data");
        }
    }
}
