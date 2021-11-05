package View;
import Model.adt.*;
import Model.exp.ArithExp;
import Model.exp.VarExp;
import Model.exp.ValueExp;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.Repo;
import Repo.IRepo;
import Controller.Controller;
import Model.PrgState;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.nio.file.WatchService;
import java.util.Iterator;
import java.util.Scanner;

class Interpreter {

    public static void main(String[] args) {
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

        IStack<IStmt> exeStack1 = new MyStack<IStmt>();
        exeStack1.push(ex1);
        IDict<String, IValue> symTable1 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable1 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out1 = new MyList<IValue>();
        PrgState prg1 = new PrgState(exeStack1, symTable1, out1, ex1, fileTable1);
        IRepo repo1 = new Repo("log1.txt");
        repo1.addPrg(prg1);
        Controller ctr1 = new Controller(repo1);

        IStack<IStmt> exeStack2 = new MyStack<IStmt>();
        exeStack2.push(ex2);
        IDict<String, IValue> symTable2 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable2 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out2 = new MyList<IValue>();
        PrgState prg2 = new PrgState(exeStack2, symTable2, out2, ex2, fileTable2);
        IRepo repo2 = new Repo("log2.txt");
        repo2.addPrg(prg2);
        Controller ctr2 = new Controller(repo2);

        IStack<IStmt> exeStack3 = new MyStack<IStmt>();
        exeStack3.push(ex3);
        IDict<String, IValue> symTable3 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable3 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out3 = new MyList<IValue>();
        PrgState prg3 = new PrgState(exeStack3, symTable3, out3, ex3, fileTable3);
        IRepo repo3 = new Repo("log3.txt");
        repo3.addPrg(prg3);
        Controller ctr3 = new Controller(repo3);

        IStack<IStmt> exeStack4 = new MyStack<IStmt>();
        exeStack4.push(ex4);
        IDict<String, IValue> symTable4 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable4 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out4 = new MyList<IValue>();
        PrgState prg4 = new PrgState(exeStack4, symTable4, out4, ex4, fileTable4);
        IRepo repo4 = new Repo("log4.txt");
        repo4.addPrg(prg4);
        Controller ctr4 = new Controller(repo4);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.show();
    }
}