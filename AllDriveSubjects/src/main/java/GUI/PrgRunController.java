package GUI;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;

import Model.adt.MyList;
import Model.stmt.IStmt;
import Model.value.IValue;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import Controller.Controller;

import Model.adt.IList;

import Model.PrgState;
import javafx.util.Pair;
import org.javatuples.Triplet;

public class PrgRunController implements Initializable {

    Controller myController;
    @FXML
    Label nrPrgStates;
    @FXML
    Button runButton;
    @FXML
    TableView<HashMap.Entry<Integer, Pair<Integer, ArrayList<Integer>>>> barrierTable;
    @FXML
    TableColumn<HashMap.Entry<Integer,Pair<Integer, ArrayList<Integer>>>, String> barrierTableAddress;
    @FXML
    TableColumn<HashMap.Entry<Integer,Pair<Integer, ArrayList<Integer>>>, String> barrierTableValue;
    @FXML
    TableColumn<HashMap.Entry<Integer,Pair<Integer, ArrayList<Integer>>>, String> barrierTableArray;
    @FXML
    TableView<HashMap.Entry<Integer, Pair<Integer, ArrayList<Integer>>>> semaphoreTable;
    @FXML
    TableColumn<HashMap.Entry<Integer,Pair<Integer, ArrayList<Integer>>>, String> semaphoreTableAddress;
    @FXML
    TableColumn<HashMap.Entry<Integer,Pair<Integer, ArrayList<Integer>>>, String> semaphoreTableValue;
    @FXML
    TableColumn<HashMap.Entry<Integer,Pair<Integer, ArrayList<Integer>>>, String> semaphoreTableArray;
    @FXML
    TableView<HashMap.Entry<Integer, Triplet<Integer, ArrayList<Integer>, Integer>>> toysemaphoreTable;
    @FXML
    TableColumn<HashMap.Entry<Integer,Triplet<Integer, ArrayList<Integer>, Integer>>, String> toysemaphoreTableAddress;
    @FXML
    TableColumn<HashMap.Entry<Integer,Triplet<Integer, ArrayList<Integer>, Integer>>, String> toysemaphoreTableValue;
    @FXML
    TableColumn<HashMap.Entry<Integer,Triplet<Integer, ArrayList<Integer>, Integer>>, String> toysemaphoreTableArray;
    @FXML
    TableView<HashMap.Entry<String, Pair<ArrayList<String>, IStmt>>> procTable;
    @FXML
    TableColumn<HashMap.Entry<String, Pair<ArrayList<String>, IStmt>>, String> procTableAddress;
    @FXML
    TableColumn<HashMap.Entry<String, Pair<ArrayList<String>, IStmt>>, String> procTableValue;
    @FXML
    TableView<HashMap.Entry<Integer, Integer>> latchTable;
    @FXML
    TableColumn<HashMap.Entry<Integer,Integer>, String> latchTableAddress;
    @FXML
    TableColumn<HashMap.Entry<Integer,Integer>, String> latchTableValue;
    @FXML
    TableView<HashMap.Entry<Integer, Integer>> lockTable;
    @FXML
    TableColumn<HashMap.Entry<Integer,Integer>, String> lockTableAddress;
    @FXML
    TableColumn<HashMap.Entry<Integer,Integer>, String> lockTableValue;
    @FXML
    TableView<HashMap.Entry<Integer, IValue>> heapTable;
    @FXML
    TableColumn<HashMap.Entry<Integer,IValue>, String> heapTableAddress;
    @FXML
    TableColumn<HashMap.Entry<Integer,IValue>, String> heapTableValue;
    @FXML
    ListView<String> outList;
    @FXML
    TableView<HashMap.Entry<String, BufferedReader>> fileTable;
    @FXML
    TableColumn<HashMap.Entry<String, BufferedReader>, String> fileTableFileName;
    @FXML
    TableColumn<HashMap.Entry<String, BufferedReader>, String> fileTableReader;
    @FXML
    ListView<String> prgStateList;
    @FXML
    TableView<HashMap.Entry<String, IValue>> symTable;
    @FXML
    TableColumn<HashMap.Entry<String, IValue>, String> symTableVariable;
    @FXML
    TableColumn<HashMap.Entry<String, IValue>, String> symTableValue;
    @FXML
    ListView<String> exeStackList;

    public PrgRunController(Controller myController) {
        this.myController = myController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initialRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
        prgStateList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSymTableAndExeStack();
            }
        });
        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                try {
                    myController.oneStepGUI();
                } catch (Exception e1) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Toy Language - Current program finished");
                    alert.setHeaderText(null);
                    alert.setContentText("Program successfully finished!");
                    Button confirm = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                    Stage stage = (Stage) heapTable.getScene().getWindow();
                    stage.close();
                }
                try {
                    updateUIComponents();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void initialRun() throws Exception {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setPrgStateList();
        setLockTable();
        setLatchTable();
        setSemaphoreTable();
        setBarrierTable();
        setToySemaphoreTable();
        setProcTable();
        prgStateList.getSelectionModel().selectFirst();
        setSymTableAndExeStack();
    }

    public void updateUIComponents() throws Exception {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setPrgStateList();
        setLockTable();
        setLatchTable();
        setSemaphoreTable();
        setBarrierTable();
        setToySemaphoreTable();
        setProcTable();
        if(prgStateList.getSelectionModel().getSelectedItem() == null) {
            prgStateList.getSelectionModel().selectFirst();
        }
        setSymTableAndExeStack();

        List<PrgState> list = myController.removeCompletedPrg(myController.getRepo().getPrgList().getAll());
        IList<PrgState> copyList = new MyList<>(list);
        myController.getRepo().setPrgList(copyList);
    }

    public void setNumberLabel() {
        nrPrgStates.setText("The number of PrgStates: " + myController.getRepo().getPrgList().size());
    }

    public void setSemaphoreTable() throws Exception{
        if (myController.getRepo().getPrgList().size() > 0) {
            ObservableList<HashMap.Entry<Integer, Pair<Integer, ArrayList<Integer>>>> semaphoreTableList = FXCollections.observableArrayList();
            semaphoreTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
            semaphoreTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getValue().getKey())));
            semaphoreTableArray.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getValue().toString()));
            semaphoreTableList.addAll(myController.getRepo().getPrgList().get(0).getSemaphoreTable().getContent().entrySet());
            semaphoreTable.setItems(semaphoreTableList);
            semaphoreTable.refresh();
        }
    }

    public void setToySemaphoreTable() throws Exception{
        if (myController.getRepo().getPrgList().size() > 0) {
            ObservableList<HashMap.Entry<Integer, Triplet<Integer, ArrayList<Integer>, Integer>>> toysemaphoreTableList = FXCollections.observableArrayList();
            toysemaphoreTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
            toysemaphoreTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getValue().getValue0())));
            toysemaphoreTableArray.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getValue1().toString()));
            toysemaphoreTableList.addAll(myController.getRepo().getPrgList().get(0).getToySemaphoreTable().getContent().entrySet());
            toysemaphoreTable.setItems(toysemaphoreTableList);
            toysemaphoreTable.refresh();
        }
    }
    public void setBarrierTable() throws Exception{
        if (myController.getRepo().getPrgList().size() > 0) {
            ObservableList<HashMap.Entry<Integer, Pair<Integer, ArrayList<Integer>>>> barrierTableList = FXCollections.observableArrayList();
            barrierTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
            barrierTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getValue().getKey())));
            barrierTableArray.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getValue().toString()));
            barrierTableList.addAll(myController.getRepo().getPrgList().get(0).getBarrierTable().getContent().entrySet());
            barrierTable.setItems(barrierTableList);
            barrierTable.refresh();
        }
    }

    public void setLatchTable() throws Exception{
        if (myController.getRepo().getPrgList().size() > 0) {
            ObservableList<HashMap.Entry<Integer, Integer>> latchTableList = FXCollections.observableArrayList();
            latchTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
            latchTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
            latchTableList.addAll(myController.getRepo().getPrgList().get(0).getLatchTable().getContent().entrySet());
            latchTable.setItems(latchTableList);
            latchTable.refresh();
        }
    }

    public void setLockTable() throws Exception{
        if (myController.getRepo().getPrgList().size() > 0) {
            ObservableList<HashMap.Entry<Integer, Integer>> lockTableList = FXCollections.observableArrayList();
            lockTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
            lockTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
            lockTableList.addAll(myController.getRepo().getPrgList().get(0).getLockTable().getContent().entrySet());
            lockTable.setItems(lockTableList);
            lockTable.refresh();
        }
    }

    public void setProcTable() throws Exception{
        if (myController.getRepo().getPrgList().size() > 0) {
            ObservableList<HashMap.Entry<String, Pair<ArrayList<String>, IStmt>>> procTableList = FXCollections.observableArrayList();
            procTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
            procTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
            procTableList.addAll(myController.getRepo().getPrgList().get(0).getProcTable().getContent().entrySet());
            procTable.setItems(procTableList);
            procTable.refresh();
        }
    }

    public void setHeapTable() throws Exception{
        if (myController.getRepo().getPrgList().size() > 0) {
            ObservableList<HashMap.Entry<Integer, IValue>> heapTableList = FXCollections.observableArrayList();
            heapTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
            heapTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
            heapTableList.addAll(myController.getRepo().getPrgList().get(0).getHeapTable().getContent().entrySet());
            heapTable.setItems(heapTableList);
            heapTable.refresh();
        }
    }

    public void setOutList() throws Exception{
        ObservableList<String> outObservableList = FXCollections.observableArrayList();
        if (myController.getRepo().getPrgList().size() > 0) {
            Iterator<IValue> iterator = myController.getRepo().getPrgList().get(0).getOutput().getIterator();

            while (iterator.hasNext()) {
                IValue e = iterator.next();
                outObservableList.add(e.toString());
            }
            outList.setItems(outObservableList);
            outList.refresh();
        }
    }

    public void setFileTable() throws Exception{
        if (myController.getRepo().getPrgList().size() > 0) {
            ObservableList<HashMap.Entry<String, BufferedReader>> fileTableList = FXCollections.observableArrayList();
            fileTableFileName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
            fileTableReader.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
            fileTableList.addAll(myController.getRepo().getPrgList().get(0).getFileTable().getContent().entrySet());
            fileTable.setItems(fileTableList);
        }
    }

    public void setPrgStateList() {
        ObservableList<String> prgStateIdList = FXCollections.observableArrayList();
        Iterator<PrgState> iterator = myController.getRepo().getPrgList().getIterator();
        while (iterator.hasNext()) {
            prgStateIdList.add(Integer.toString(iterator.next().getId()));
        }
        prgStateList.setItems(prgStateIdList);
    }

    public void setSymTableAndExeStack() {
        ObservableList<HashMap.Entry<String, IValue>> symTableList = FXCollections.observableArrayList();
        ObservableList<String> exeStackItemsList = FXCollections.observableArrayList();
        symTableVariable.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
        symTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        IList<PrgState> allPrgs = myController.getRepo().getPrgList();
        Iterator<PrgState> iterator = allPrgs.getIterator();

        PrgState prgResult = null;

        while (iterator.hasNext()) {
            PrgState e = iterator.next();

            if (e.getId() == Integer.parseInt(prgStateList.getSelectionModel().getSelectedItem())) {
                prgResult = e;
                break;
            }
        }

        if(prgResult != null) {
            symTableList.addAll(prgResult.getSymTable().getContent().entrySet());
            exeStackItemsList.add(prgResult.getStack().toFile());
            symTable.setItems(symTableList);
            exeStackList.setItems(exeStackItemsList);
            symTable.refresh();
        } else {
            exeStackList.setItems(exeStackItemsList);
        }
    }

}
