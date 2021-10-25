package View;
import Model.adt.*;
import Model.exp.ArithExp;
import Model.exp.VarExp;
import Model.exp.ValueExp;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Repo.Repo;
import Controller.Controller;
import Model.PrgState;

import java.util.Iterator;
import java.util.Scanner;


public class Main {

    static Repo myRepository = new Repo();
    static Controller myController = new Controller(myRepository);

    public static void printMenu() {
        System.out.println("1. Pick example 1.");
        System.out.println("2. Pick example 2.");
        System.out.println("3. Pick example 3.");
        System.out.println("0. Exit.");
    }

    public static void menu() {
        boolean exit = false;

        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            printMenu();
            System.out.print(">>");

            String cmd = scanner.nextLine();
            IStmt originalProgram = null;
            switch (cmd) {
                case "1":
                    // ex 1:  int v; v = 2; Print(v)
                    originalProgram = new CompStmt(new VarDeclStmt("v",new IntType()),
                            new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                                    new PrintStmt(new VarExp("v"))));
                    break;
                case "2":
                    // ex 2: a=2+3*5;b=a+1;Print(b)
                    originalProgram = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),  new CompStmt(
                                new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));
                    break;
                case "3":
                    // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
                    originalProgram = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                            new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                    new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                                            new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                            VarExp("v"))))));
                    break;
                case "0":
                    exit = true;
                    break;
            }

            if (originalProgram != null) {
                IStack<IStmt> exeStack = new MyStack<IStmt>();
                exeStack.push(originalProgram);
                IDict<String, IValue> symTable = new Dict<String, IValue>();
                IList<IValue> out = new MyList<IValue>();
                PrgState myPrgState = new PrgState(exeStack, symTable, out, originalProgram);
                myController.addProgram(myPrgState);
                myController.allStep();
            }
        }

    }


    public static void main(String[] args) {
      menu();
    }
}
