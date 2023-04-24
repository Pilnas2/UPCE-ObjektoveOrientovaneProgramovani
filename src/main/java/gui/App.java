package gui;

import data.DopravniLetadlo;
import data.Letadla;
import data.NakladniLetadlo;
import data.TypLetadla;
import data.VojenskeLetadlo;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kolekce.LinkSeznam;
import perzistence.Persistence;
import perzistence.PerzistenceTxt;
import spravce.Ovladani;
import spravce.SpravceLetadel;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private static final int HEIGHT_LV = 540;
    private static final int WIDTH_LV = 900;
    private static final int GENERUJ_POCET = 10;

    private static final String TXT = "letadla.txt";
    private static final String BIN = "letadla.bin";

    private Ovladani<Letadla> spravce;
    private ListView listview;
    private TypLetadla filtr;

    public App() {
        spravce = new SpravceLetadel(new LinkSeznam<Letadla>());
        spravce.nastavErrorLog((t) -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(t);
            alert.showAndWait();
        });
        spravce.nastavKomparator((a, b) -> a.getCisloLetu().compareTo(b.getCisloLetu()));
        filtr = null;
    }

    @Override
    public void start(Stage stage) {

        VBox root = new VBox();
        HBox hbox = new HBox();

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        ControlPanelVBox controlPanelVBox = createControlPanelVBox();
        listview = createListView();
        hbox.getChildren().addAll(listview, controlPanelVBox);

        ControlPanelHBox controlPanelHBox = createControlPanelHBox();
        root.getChildren().addAll(hbox, controlPanelHBox);

        stage.setTitle("Pujcovna letadel Pilny");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private ControlPanelVBox createControlPanelVBox() {

        ControlPanelVBox controlPanel = new ControlPanelVBox();
        controlPanel.addLabel("Procházení");
        controlPanel.addButton("První", nastavPrvniHandler);
        controlPanel.addButton("Další", nastavDalsiHandler);
        controlPanel.addButton("Předchozí", nastavPredchoziHandler);
        controlPanel.addButton("Poslední", nastavPosledniHandler);
        controlPanel.addLabel("Příkazy");
        controlPanel.addButton("Edituj", editujHandler);
        controlPanel.addButton("Výjmi", vyjmiHandler);
        controlPanel.addButton("Zobraz", zobrazHandler);
        controlPanel.addButton("Clear", clearHandler);
        return controlPanel;
    }
    private final EventHandler<ActionEvent> nastavPrvniHandler = event -> {
        spravce.prvni();
        if (spravce.dejPocet() != 0) {
            obnovit(spravce.dej());
        }

    };

    private final EventHandler<ActionEvent> nastavDalsiHandler = event -> {
        spravce.dalsi();
        if (spravce.dejPocet() != 0) {
            obnovit(spravce.dej());
        }
    };
    private final EventHandler<ActionEvent> nastavPredchoziHandler = event -> {
        spravce.predchozi();
        if (spravce.dejPocet() != 0) {
            obnovit(spravce.dej());
        }
    };
    private final EventHandler<ActionEvent> nastavPosledniHandler = event -> {
        spravce.posledni();
        if (spravce.dejPocet() != 0) {
            obnovit(spravce.dej());
        }
    };
    private final EventHandler<ActionEvent> editujHandler = event -> {
        spravce.edituj(new Editor());
        obnovit();
    };
    private final EventHandler<ActionEvent> vyjmiHandler = event -> {
        spravce.odeber();
        obnovit();
    };
    private final EventHandler<ActionEvent> zobrazHandler = event -> {
        dialogZobraz(spravce.dej());
    };
    private final EventHandler<ActionEvent> clearHandler = event -> {
        spravce.zrus();
        obnovit();

    };

    private void obnovit() {
        listview.getItems().clear();
        spravce.stream().filter(t -> {
            if (filtr == null) {
                return true;
            } else {
                return filtr == t.getTyp();
            }
        }).forEach(listview.getItems()::add);
    }

    private void obnovit(Letadla letadla) {
        obnovit();
        listview.getSelectionModel().select(letadla);
    }

    private void dialogZobraz(Letadla letadla) {
        if (letadla != null) {
            switch (letadla.getTyp()) {
                case DOPRAVNI_LETADLO -> {
                    DopravniDialog dialog = new DopravniDialog(letadla);
                    dialog.noEdit();
                    dialog.showAndWait();
                }
                case NAKLADNI_LETADLO -> {
                    NakladniDialog dialog = new NakladniDialog(letadla);
                    dialog.noEdit();
                    dialog.showAndWait();

                }
                case VOJENSKE_LETADLO -> {
                    VojenskeDialog dialog = new VojenskeDialog(letadla);
                    dialog.noEdit();
                    dialog.showAndWait();
                }
            }
        }
    }

    private ListView createListView() {
        ListView<Letadla> listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setPrefSize(WIDTH_LV, HEIGHT_LV);
        listView.setMouseTransparent(true);
        listView.setFocusTraversable(false);
        return listView;
    }

    private ControlPanelHBox createControlPanelHBox() {
        ControlPanelHBox controlPanel = new ControlPanelHBox();
        controlPanel.addButton("Generuj", generujHandler);
        controlPanel.addButton("Ulož", ulozHandler);
        controlPanel.addButton("Načti", nactiHandler);
        controlPanel.addLabel("Nový:");
        controlPanel.addComboBox("Nový", TypLetadla.valuesBezKlice(), novyHandler);
        controlPanel.addLabel("Filtr:");
        controlPanel.addComboBox("Filtr", TypLetadla.values(), filtrHandler);
        controlPanel.addButton("Najdi", najdiHandler);
        controlPanel.addButton("Zálohuj", zalohujHandler);
        controlPanel.addButton("Obnov", obnovHandler);
        controlPanel.addButton("Zruš", zrusHandler);
        return controlPanel;
    }
    private final EventHandler<ActionEvent> generujHandler = event -> {
        spravce.generuj(GENERUJ_POCET);
        obnovit();
    };
    private final EventHandler<ActionEvent> ulozHandler = event -> {
        spravce.ulozTextSoubor(TXT, PerzistenceTxt.mapperOutput);
    };
    private final EventHandler<ActionEvent> nactiHandler = event -> {
        spravce.nactiTextSoubor(TXT, PerzistenceTxt.mapperInput);
        obnovit();
    };
    private final EventHandler<ActionEvent> novyHandler = event -> {
        if (((ComboBox<TypLetadla>) event.getSource()).getValue() != null) {

            TypLetadla typ = ((ComboBox<TypLetadla>) event.getSource()).getValue();

            switch (typ) {
                case DOPRAVNI_LETADLO -> {
                    DopravniDialog dialog = new DopravniDialog();
                    Optional<DopravniLetadlo> dopravni = dialog.showAndWait();
                    if (dopravni.isPresent()) {
                        spravce.vloz(dopravni.get());
                    }

                }
                case NAKLADNI_LETADLO -> {
                    NakladniDialog dialog = new NakladniDialog();
                    Optional<NakladniLetadlo> nakladni = dialog.showAndWait();
                    if (nakladni.isPresent()) {
                        spravce.vloz(nakladni.get());
                    }

                }
                case VOJENSKE_LETADLO -> {
                    VojenskeDialog dialog = new VojenskeDialog();
                    Optional<VojenskeLetadlo> vojenske = dialog.showAndWait();
                    if (vojenske.isPresent()) {
                        spravce.vloz(vojenske.get());
                    }

                }
            }
            ((ComboBox<TypLetadla>) event.getSource()).getSelectionModel().selectFirst();
        }
        obnovit();
    };
    private final EventHandler<ActionEvent> filtrHandler = event -> {
        filtr = ((ComboBox<TypLetadla>) event.getSource()).getValue();
        obnovit();
    };
    private final EventHandler<ActionEvent> najdiHandler = event -> {
        Klic klic = getKlicDialog("Najdi");
        dialogZobraz(spravce.najdi(klic));
        obnovit();
    };
    private final EventHandler<ActionEvent> zalohujHandler = event -> {
        spravce.zalohuj(BIN);
    };
    private final EventHandler<ActionEvent> obnovHandler = event -> {
        spravce.obnov(BIN);
        obnovit();
    };
    private final EventHandler<ActionEvent> zrusHandler = event -> {
        Klic klic = getKlicDialog("Zruš");
        if (klic != null) {
            spravce.odeber(klic);
        }

        obnovit();
    };

    private Klic getKlicDialog(String title) {
        Dialog<String> dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText("Nastav Číslo letu: ");

        Optional<String> result = dialog.showAndWait();

        return result.isPresent() ? new Klic(result.get()) : null;

    }

}
