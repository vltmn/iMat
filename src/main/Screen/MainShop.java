package main.Screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import main.components.MainScreen.*;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainShop extends VBox {
    private static final String SEARCH_FILTER = "SEARCH";
    public static final String CATEGORY_FILTER = "CATEGORY";

    @FXML
    private SplitPane splitPane;

    @FXML
    private FlowPane productFlow;

    @FXML
    private ScrollPane gridScroll;

    @FXML
    private Pane topBarWrapper;

    @FXML
    private Pane mainContainer;

    @FXML
    private Pane categoriesWrapper;

    private SearchBar searchBar;

    private ShoppingCartBtn shoppingCartBtn;

    private CategoriesList categoriesList;

    private ShoppingCartModal shoppingCartModal;


    private Map<String, Predicate<Product>> productFilters = new HashMap<>();

    private final static int PRODUCT_LIMIT = 100;

    private List<ProductCard> allProducts = new ArrayList<>();
    private EventHandler<ActionEvent> goToOrderProcess;

    public MainShop(EventHandler<ActionEvent> goToOrderProcess) {
        this.goToOrderProcess = goToOrderProcess;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/screen/main_shop.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setupView();
        populateGrid();

    }

    private void setupView() {
        //initiate custom controls
        categoriesList = new CategoriesList(mainContainer.heightProperty().subtract(16));
        searchBar = new SearchBar();
        topBarWrapper.getChildren().add(searchBar);
        Pane p = new Pane();
        HBox.setHgrow(p, Priority.ALWAYS);
        topBarWrapper.getChildren().add(p);
        shoppingCartBtn = new ShoppingCartBtn();
        HBox.setHgrow(shoppingCartBtn, Priority.ALWAYS );
        shoppingCartBtn.setOnMouseClicked((mouseEvent) -> shoppingCartModal.setVisible(!shoppingCartModal.isVisible()));
        topBarWrapper.getChildren().add(shoppingCartBtn);

        //add shopping cart modal
        shoppingCartModal = new ShoppingCartModal(goToOrderProcess, mainContainer.heightProperty());
        AnchorPane.setTopAnchor(shoppingCartModal, -8.0);
        AnchorPane.setRightAnchor(shoppingCartModal, 0.0);
        shoppingCartModal.prefWidthProperty().bind(shoppingCartBtn.widthProperty());
        shoppingCartModal.setVisible(false);
        mainContainer.getChildren().add(shoppingCartModal);

        //add the filter callbacks
        searchBar.setFilterCallback(((observable, oldValue, newValue) -> {
            productFilters.put(SEARCH_FILTER, newValue);
            filter();
        }));
        categoriesList.setFilterCallback(((observable, oldValue, newValue) ->  {
            productFilters.put(CATEGORY_FILTER, newValue);
            filter();
        }));
        categoriesWrapper.getChildren().add(categoriesList);
    }

    private void filter() {
        productFlow.getChildren().clear();
        productFlow.getChildren().addAll(allProducts);
        productFilters.values().forEach(pf -> productFlow.getChildren().removeIf(node -> {
            ProductCard pc = (ProductCard) node;
            return !pf.test(pc.getProduct());
        }));
    }

    private void populateGrid() {
        allProducts = IMatDataHandler.getInstance().getProducts().stream()
                .sorted(Comparator.comparing(Product::getName))
                .map(ProductCard::new).collect(Collectors.toList());
        allProducts.forEach(productCard -> productFlow.getChildren().add(productCard));
    }
}
