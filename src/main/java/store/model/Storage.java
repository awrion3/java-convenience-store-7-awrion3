package store.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Storage {
    private static final String NO_RESOURCE_FILE = "[ERROR] 리소스 파일이 존재하지 않습니다.";
    private static final String NO_RESOURCE_ITEM = "[ERROR] 리소스 내용이 존재하지 않습니다.";

    private static final String PRODUCTS_FILENAME = "products.md";
    private static final String PROMOTIONS_FILENAME = "promotions.md";
    private static final String FIRST_ROW_START_POINT = "name";
    private static final String DELIMITER = ",";

    private final List<List<String>> resourceProducts;
    private final List<List<String>> resourcePromotions;

    public Storage() {
        resourceProducts = new ArrayList<>();
        readFromResource(PRODUCTS_FILENAME);
        resourcePromotions = new ArrayList<>();
        readFromResource(PROMOTIONS_FILENAME);
    }

    private void readFromResource(String fileName) {
        try (InputStream resource = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
            readResourceByLine(reader, fileName);
        } catch (IOException e) {
            validateResourceFile();
        }
    }

    private void readResourceByLine(BufferedReader reader, String fileName) throws IOException {
        String resourceLine;
        while ((resourceLine = reader.readLine()) != null) {
            String[] resourceSplitLine = resourceLine.split(DELIMITER);
            List<String> resourceItems = Arrays.asList(resourceSplitLine);

            updateResourceListByLine(resourceItems, fileName);
        }
    }

    private void updateResourceListByLine(List<String> resourceItems, String fileName) {
        if (isFirstRow(resourceItems)) {
            return;
        }
        if (fileName.equals(PRODUCTS_FILENAME)) {
            resourceProducts.add(resourceItems);
        }
        if (fileName.equals(PROMOTIONS_FILENAME)) {
            resourcePromotions.add(resourceItems);
        }
    }

    private boolean isFirstRow(List<String> resourceItems) {
        String category = resourceItems.getFirst();
        return category.equals(FIRST_ROW_START_POINT);
    }

    private void validateResourceFile() {
        throw new IllegalStateException(NO_RESOURCE_FILE);
    }

    private void validateResourceProducts() {
        if (resourceProducts.isEmpty()) {
            throw new IllegalStateException(NO_RESOURCE_ITEM);
        }
    }

    private void validateResourcePromotions() {
        if (resourcePromotions.isEmpty()) {
            throw new IllegalStateException(NO_RESOURCE_ITEM);
        }
    }

    public List<List<String>> getResourceProducts() {
        validateResourceProducts();
        return resourceProducts;
    }

    public List<List<String>> getResourcePromotions() {
        validateResourcePromotions();
        return resourcePromotions;
    }
}