package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;

import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.types.*;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Model.PrgState;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import Controller.Controller;
import Repo.Repo;
import Repo.IRepo;
import javafx.util.Pair;
import org.javatuples.Triplet;

public class PrgListController implements Initializable {
    @FXML
    ListView<Controller> myPrgList;
    @FXML
    Button runButton;

    public void setUp(ObservableList<Controller> myData) {
        // ex 1:  int v; v = 2; Print(v)
        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),  new CompStmt(
                        new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));

        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("fisier.txt"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))))))))));

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new ValueExp(new IntValue(5)),
                                                        new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))))))));

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))), new CompStmt(
                                new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                new PrintStmt(new ArithExp('+', new ValueExp(new IntValue(5)),
                                        new ReadHeapExp(new VarExp("v"))))))));

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));

        IStmt ex9 = new CompStmt(new VarDeclStmt("v",new IntType()),new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"),new ValueExp(new IntValue(0)),">"),
                        new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp('-',
                                new VarExp("v"), new ValueExp(new IntValue(1)))))),
                        new PrintStmt(new VarExp("v")))));

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt( new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(
                        new NewStmt("a", new ValueExp(new IntValue(22))), new CompStmt(
                        new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                        new CompStmt(new PrintStmt(new VarExp("v")),
                                new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));

        IStmt ex11 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(20))),
                        new CompStmt(new ForStmt(new ValueExp(new IntValue(0)),
                                new ValueExp(new IntValue(3)), new ArithExp('+', new VarExp("v"),
                                    new ValueExp(new IntValue(1))),
                                        new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v", new ArithExp('*', new VarExp("v"),
                                                        new ReadHeapExp(new VarExp("a"))))))),
                                                            new PrintStmt(new ReadHeapExp(new VarExp("a"))))));

        IStmt ex12 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(
                new VarDeclStmt("b", new RefType(new IntType())),new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new NewStmt("a", new ValueExp(new IntValue(0))), new CompStmt(new NewStmt("b", new
                            ValueExp(new IntValue(0))), new CompStmt(new WriteHeapStmt("a", new
                            ValueExp(new IntValue(1))), new CompStmt(new WriteHeapStmt("b", new
                                ValueExp(new IntValue(2))), new CompStmt(new CondAssignStmt("v", new RelationalExp(new ReadHeapExp(new VarExp("a")),
                                    new ReadHeapExp(new VarExp("b")), "<"), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new CompStmt(new CondAssignStmt("v", new RelationalExp(new ArithExp(
                                                '-', new ReadHeapExp(new VarExp("b")), new ValueExp(new IntValue(2))),
                                                new ReadHeapExp(new VarExp("a")), ">"), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                    new PrintStmt(new VarExp("v")))))))))
        )));

        IStmt ex13 = new CompStmt(
                new VarDeclStmt("a",new IntType()),
                new CompStmt(
                        new VarDeclStmt("b",new IntType()),
                        new CompStmt(
                                new VarDeclStmt("c",new IntType()),
                                new CompStmt(
                                        new AssignStmt("a",new ValueExp(new IntValue(1))),
                                        new CompStmt(
                                                new AssignStmt("b",new ValueExp(new IntValue(2))),
                                                new CompStmt(
                                                        new AssignStmt("c",new ValueExp(new IntValue(5))),
                                                        new CompStmt(
                                                                new SwitchStmt(
                                                                        new ArithExp('*', new VarExp("a"), new ValueExp(new IntValue(10))),
                                                                        new ArithExp('*', new VarExp("b"), new VarExp("c")),
                                                                        new ValueExp(new IntValue(10)),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("a")),
                                                                                new PrintStmt(new VarExp("b"))
                                                                        ),
                                                                        new CompStmt(
                                                                                new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                new PrintStmt(new ValueExp(new IntValue(200)))
                                                                        ),
                                                                        new PrintStmt(new ValueExp(new IntValue(300)))
                                                                ),
                                                                new PrintStmt(new ValueExp(new IntValue(300)))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        //int v; int x; int y; v=0;
        //(repeat (fork(print(v);v=v-1);v=v+1) until v==3); x=1;nop;y=3;nop;
        //print(v*10)
        IStmt ex14 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("x", new IntType()),
                        new CompStmt(
                                new VarDeclStmt("y", new IntType()),
                                new CompStmt(
                                        new AssignStmt("v",new ValueExp(new IntValue(0))),
                                        new CompStmt(
                                                new RepeatUntilStmt(
                                                        new RelationalExp(new VarExp("v"),new ValueExp(new IntValue(3)),"=="),
                                                        new CompStmt(
                                                                new ForkStmt(
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new AssignStmt(
                                                                                        "v",
                                                                                        new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))
                                                                                )
                                                                        )
                                                                ),
                                                                new AssignStmt(
                                                                        "v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1)))
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new AssignStmt("x", new ValueExp(new IntValue(1))),
                                                        new CompStmt(
                                                                new NopStmt(),
                                                                new CompStmt(
                                                                        new AssignStmt("y", new ValueExp(new IntValue(3))),
                                                                        new CompStmt(
                                                                                new NopStmt(),
                                                                                new PrintStmt(
                                                                                        new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        IStmt ex15 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))
                    ), new CompStmt(
                            new ForkStmt(new CompStmt(
                                    new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))),
                                        new CompStmt(
                                                new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))),
                                                    new PrintStmt(new VarExp("v"))
                                        )
                            )),
                            new CompStmt(new SleepStmt(10), new PrintStmt(new ArithExp('*', new VarExp("v"),
                                    new ValueExp(new IntValue(10))))
                    )
            )
        ));

        // lock example
        IStmt ex16 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())), new CompStmt(
                new VarDeclStmt("v2", new RefType(new IntType())), new CompStmt( new VarDeclStmt("x", new IntType()),
                    new CompStmt(new VarDeclStmt("q", new IntType()), new CompStmt(new NewStmt("v1", new ValueExp(
                            new IntValue(20))), new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))),
                                new CompStmt(new NewLockStmt("x"), new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(
                                        new CompStmt(new LockStmt("x"), new CompStmt(new WriteHeapStmt("v1",
                                                new ArithExp('-', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)))),
                                                    new UnlockStmt("x")))), new CompStmt(new LockStmt("x"), new CompStmt(new WriteHeapStmt("v1",
                                        new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                        new UnlockStmt("x"))))

                                        ), new CompStmt(new NewLockStmt("q"), new CompStmt(
                                        new ForkStmt(new CompStmt(new ForkStmt(
                                                new CompStmt(new LockStmt("q"), new CompStmt(new WriteHeapStmt("v2",
                                                        new ArithExp('+', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(5)))),
                                                        new UnlockStmt("q")))), new CompStmt(new LockStmt("q"), new CompStmt(new WriteHeapStmt("v2",
                                                new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                new UnlockStmt("q"))))), new CompStmt( new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),
                                                    new CompStmt(new NopStmt(), new CompStmt(new LockStmt("x"), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                            new CompStmt(new UnlockStmt("x"), new CompStmt(new LockStmt("q"), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                    new UnlockStmt("q")))))))))

                                                )))))))
        ))));

        IStmt fork1 = new CompStmt(
                new WriteHeapStmt(
                        "v1",
                        new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))
                ),
                new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                        new CountDownStmt("cnt")
                )
        );
        IStmt fork2 = new CompStmt(
                new WriteHeapStmt(
                        "v2",
                        new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))
                ),
                new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                        new CountDownStmt("cnt")
                )
        );
        IStmt fork3 = new CompStmt(
                new WriteHeapStmt(
                        "v3",
                        new ArithExp('*', new ReadHeapExp(new VarExp("v3")), new ValueExp(new IntValue(10)))
                ),
                new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v3"))),
                        new CountDownStmt("cnt")
                )
        );
        IStmt ex17 = new CompStmt(new VarDeclStmt("cnt", new IntType()), new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("v3", new RefType(new IntType())),
                                new CompStmt(
                                        new NewStmt("v1", new ValueExp(new IntValue(2))),
                                        new CompStmt(
                                                new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                new CompStmt(
                                                        new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                        new CompStmt(
                                                                new NewLatchStmt("cnt", new ReadHeapExp(new VarExp("v2"))),
                                                                new CompStmt(
                                                                        new ForkStmt(fork1),
                                                                        new CompStmt(
                                                                                new ForkStmt(fork2),
                                                                                new CompStmt(
                                                                                        new ForkStmt(fork3),
                                                                                        new CompStmt(
                                                                                                new AwaitLatchStmt("cnt"),
                                                                                                new CompStmt(
                                                                                                        new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                                        new CompStmt(
                                                                                                                new CountDownStmt("cnt"),
                                                                                                                new PrintStmt(new ValueExp(new IntValue(100)))
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        )
        );

        IStmt fork11 = new ForkStmt(new CompStmt(new AcquireStmt("cnt"),
                new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")),
                        new ValueExp(new IntValue(10)))), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                            new ReleaseStmt("cnt")))));

        IStmt fork22 = new ForkStmt(new CompStmt(new AcquireStmt("cnt"),
                new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")),
                        new ValueExp(new IntValue(10)))), new CompStmt(
                                new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")),
                                    new ValueExp(new IntValue(2)))), new CompStmt(
                                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                            new ReleaseStmt("cnt"))))));

        IStmt ex18 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(1))),
                                new CompStmt(new NewSemaphoreStmt("cnt", new ReadHeapExp(new VarExp("v1"))),
                                        new CompStmt(fork11, new CompStmt(fork22,
                                                new CompStmt(new AcquireStmt("cnt"),
                                                        new CompStmt(new PrintStmt(new ArithExp('-',
                                                                new ReadHeapExp(new VarExp("v1")),
                                                                    new ValueExp(new IntValue(1)))),
                                                                new ReleaseStmt("cnt")))))))));

        IStmt fork12 = new ForkStmt(new CompStmt(new AwaitBarrierStmt("cnt"),
                new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")),
                        new ValueExp(new IntValue(10)))), new PrintStmt(new ReadHeapExp(new VarExp("v1"))))));

        IStmt fork23 = new ForkStmt(new CompStmt(new AwaitBarrierStmt("cnt"),
                new CompStmt(new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")),
                        new ValueExp(new IntValue(10)))), new CompStmt(
                        new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")),
                                new ValueExp(new IntValue(10)))),
                        new PrintStmt(new ReadHeapExp(new VarExp("v2")))
                        ))));

        IStmt ex19 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
                                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(2))),
                                        new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(3))),
                                               new CompStmt(new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                    new CompStmt(new VarDeclStmt("cnt", new IntType()),
                                                            new CompStmt(new NewBarrierStmt("cnt", new ReadHeapExp(new VarExp("v2"))),
                                                                    new CompStmt(fork12, new CompStmt(fork23,
                                                                            new CompStmt(new AwaitBarrierStmt("cnt"),
                                                                                    new PrintStmt(new ReadHeapExp(new VarExp("v3"))))))))
                                                       ))))));

        IStmt fork13 = new ForkStmt(new CompStmt(new AcquireToyStmt("cnt"),
                new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")),
                        new ValueExp(new IntValue(10)))), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                        new ReleaseToyStmt("cnt")))));

        IStmt fork24 = new ForkStmt(new CompStmt(new AcquireToyStmt("cnt"),
                new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")),
                        new ValueExp(new IntValue(10)))), new CompStmt(
                        new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")),
                                new ValueExp(new IntValue(2)))), new CompStmt(
                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                        new ReleaseToyStmt("cnt"))))));

        IStmt ex20 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(2))),
                                new CompStmt(new NewToySemaphoreStmt("cnt", new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1))),
                                        new CompStmt(fork13, new CompStmt(fork24,
                                                new CompStmt(new AcquireToyStmt("cnt"),
                                                        new CompStmt(new PrintStmt(new ArithExp('-',
                                                                new ReadHeapExp(new VarExp("v1")),
                                                                new ValueExp(new IntValue(1)))),
                                                                new ReleaseToyStmt("cnt")))))))));

        IStmt procedure1 = new ProcedureStmt("sum", new ArrayList<String>(Arrays.asList("a", "b")),
                new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v",
                        new ArithExp('+', new VarExp("a"), new VarExp("b"))), new PrintStmt(new VarExp("v")))));

        IStmt procedure2 = new ProcedureStmt("product", new ArrayList<String>(Arrays.asList("a", "b")),
                 new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v",
                        new ArithExp('*', new VarExp("a"), new VarExp("b"))), new PrintStmt(new VarExp("v")))));

        IStmt ex21 = new CompStmt(procedure1, new CompStmt(procedure2,
                new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("w", new IntType()),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                                new CompStmt(new AssignStmt("w", new ValueExp(new IntValue(5))),
                                        new CompStmt(new CallProcedureStmt("sum",
                                                new ArrayList<Exp>(Arrays.asList(new ArithExp('*', new VarExp("v"),
                                                new ValueExp(new IntValue(10))), new VarExp("w")))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                new ForkStmt(new CompStmt(new CallProcedureStmt("product",
                                                        new ArrayList<Exp>(Arrays.asList(new VarExp("v"), new VarExp("w")))),
                                                        new ForkStmt(new CallProcedureStmt("sum",
                                                                new ArrayList<Exp>(Arrays.asList(new VarExp("v"), new VarExp("w")))))))))))))));

        // Typechecking
        Vector<IStmt> check = new Vector<>(Arrays.asList(
                ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11, ex12, ex13, ex14, ex15, ex16,
                ex17, ex18, ex19, ex20, ex21
        ));

        for (IStmt stmt : check) {
            try {
                IDict<String, IType> typeEnv = new Dict<>();
                stmt.typecheck(typeEnv);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Toy Language - Current program finished");
                alert.setHeaderText(null);
                alert.setContentText("The types aren't valid!");
                Button confirm = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                confirm.setDefaultButton(false);
                confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                alert.showAndWait();

                System.exit(1);
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Toy Language - Current program finished");
        alert.setHeaderText(null);
        alert.setContentText("Program compiled successfully!");
        Button confirm = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
        confirm.setDefaultButton(false);
        confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        alert.showAndWait();

        for (int i = 0; i < 21; i++) {
            String filename = "log" + i + ".txt";
            IRepo repo = new Repo(filename);
            Controller controller = new Controller(repo);
            IStack<IStmt> exeStack = new MyStack<IStmt>();
            IDict<String, IValue> symTable = new Dict<String, IValue>();
            IStack<IDict<String, IValue>> symTableStack = new MyStack<>();
            symTableStack.push(symTable);
            IList<IValue> out = new MyList<IValue>();
            IDict<String, BufferedReader> fileTable = new Dict<String, BufferedReader>();
            IDict<Integer, IValue> heap = new Heap<>();
            IDict<Integer, Integer> lock = new MyLockTable<>();
            IDict<Integer, Integer> latch = new MyLatchTable<>();
            IDict<Integer, Pair<Integer, ArrayList<Integer>>> sema = new MySemaphoreTable<>();
            IDict<Integer, Pair<Integer, ArrayList<Integer>>> barrier = new MyBarrierTable<>();
            IDict<Integer, Triplet<Integer, ArrayList<Integer>, Integer>> toySema = new MySemaphoreTable<>();
            IDict<String, Pair<ArrayList<String>, IStmt>> procTable = new MyProcTable<>();
            exeStack.push(check.get(i));
            PrgState myPrgState = new PrgState(
                    exeStack, symTableStack, out, check.get(i), fileTable, heap, lock, latch,
                    sema, barrier, toySema, procTable
            );
            controller.addProgram(myPrgState);
            myData.add(controller);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Controller> myData = FXCollections.observableArrayList();

        this.setUp(myData);
        myPrgList.setItems(myData);

        myPrgList.getSelectionModel().selectFirst();
        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent e) {
                Stage programStage = new Stage();
                Parent programRoot;
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == PrgRunController.class) {
                        return new PrgRunController(myPrgList.getSelectionModel().getSelectedItem());
                    } else {
                        try {
                            return type.getDeclaredConstructor().newInstance() ;
                        } catch (Exception exc) {
                            System.err.println("Could not create controller for "+type.getName());
                            throw new RuntimeException(exc);
                        }
                    }
                };
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProgramLayout.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);
                    programStage.setTitle("Toy Language - Program running");
                    programStage.setScene(programScene);
                    programStage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

}
