package main.components.MainScreen;

import javafx.beans.binding.NumberExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CategoriesList extends VBox {
    private final String ALL_PRODUCTS = "ALL_PRODUCTS";
    private final ResourceBundle lngBndl;

    @FXML
    private VBox listPane;

    @FXML
    private ScrollPane scrollPane;

    private List<CategoriesListRow> rows = new ArrayList<>();

    private Consumer<String> selectionFunction = pc -> {
    };

    private Predicate<Product> productPredicate;

    private CategoriesList() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/MainScreen/CategoriesList.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lngBndl = ResourceBundle
                .getBundle("bundles.LangBundle", new Locale("se", "SE"));
        //allocate rows
        CategoriesListRow allProductsRow = new CategoriesListRow(ALL_PRODUCTS, selectionFunction);
        rows.add(allProductsRow);
        rows.addAll(IMatDataHandler.getInstance().getProducts().stream()
                .map(Product::getCategory)
                .distinct()
                .map(pc -> new CategoriesListRow(pc.name(), selectionFunction))
                .sorted(Comparator.comparing(CategoriesListRow::getShowingName)
                )
                .collect(Collectors.toList()));
        listPane.getChildren().addAll(rows);
        allProductsRow.setSelected(true);

    }

    public CategoriesList(NumberExpression ne) {
        this();
        scrollPane.maxHeightProperty().bind(ne);
    }

    public void setFilterCallback(ChangeListener<Predicate<Product>> productFilterListener) {
        selectionFunction = val -> {
            rows.stream().filter(r -> !r.getLabelName().equals(val))
                    .filter(r -> r.selected.get())
                    .forEach(r -> r.setSelected(false));
            Predicate<Product> old = this.productPredicate;
            if (val.equals(ALL_PRODUCTS)) {
                rows.stream().filter(r -> r.getLabelName().equals(ALL_PRODUCTS))
                        .findAny()
                        .ifPresent(r -> r.setSelected(true));
                this.productPredicate = product -> true;
            } else {
                productPredicate = product -> val.equals(product.getCategory().name());
            }
            productFilterListener.changed(null, old, productPredicate);
        };
        rows.forEach(r -> r.selectionFun = selectionFunction);
    }

    class CategoriesListRow extends HBox {
        private Consumer<String> selectionFun;
        private BooleanProperty selected = new SimpleBooleanProperty(false);

        private String labelName;

        @FXML
        private FontIcon icon;

        @FXML
        private Label name;

        CategoriesListRow(String labelName, Consumer<String> selectionFun) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/components/MainScreen/CategoriesListRow.fxml"));
            loader.setRoot(this);
            loader.setController(this);

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            this.selectionFun = selectionFun;
            this.labelName = labelName;
            setLabel();
            selected.addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    icon.setIconCode(FontAwesome.CHECK_CIRCLE_O);
                    this.getStyleClass().add("selected");
                } else {
                    icon.setIconCode(FontAwesome.CIRCLE_O);
                    this.getStyleClass().removeIf(s -> s.equalsIgnoreCase("selected"));
                }
            });
            selected.addListener((observable, oldValue, newValue) -> {
                if (oldValue.equals(newValue)) {
                    return;
                }
                if (!newValue) {
                    return;
                }
                this.selectionFun.accept(getLabelName());
            });
            this.setOnMouseClicked(event -> {
                event.consume();
                if (selected.get()) {
                    if (getLabelName().equals(ALL_PRODUCTS)) {
                        return;
                    }
                    this.selectionFun.accept(ALL_PRODUCTS);
                    return;
                }
                selected.set(!selected.get());
            });
        }


        private void setLabel() {
            if(lngBndl.containsKey(labelName)) {
                name.setText(lngBndl.getString(labelName));
            } else {
                name.setText(labelName);
            }
        }
        void setSelected(boolean selected) {
            this.selected.setValue(selected);
        }

        String getLabelName() {
            return labelName;
        }

        String getShowingName() {
            return name.getText();
        }
    }
}
