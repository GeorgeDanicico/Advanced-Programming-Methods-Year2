package View;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IntType;
import Model.types.RefType;
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

        IStack<IStmt> exeStack1 = new MyStack<IStmt>();
        exeStack1.push(ex1);
        IDict<String, IValue> symTable1 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable1 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out1 = new MyList<IValue>();
        IDict<Integer, IValue> heap1 = new Heap<Integer, IValue>();
        PrgState prg1 = new PrgState(exeStack1, symTable1, out1, ex1, fileTable1, heap1);
        IRepo repo1 = new Repo("log1.txt");
        repo1.addPrg(prg1);
        Controller ctr1 = new Controller(repo1);

        IStack<IStmt> exeStack2 = new MyStack<IStmt>();
        exeStack2.push(ex2);
        IDict<String, IValue> symTable2 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable2 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out2 = new MyList<IValue>();
        IDict<Integer, IValue> heap2 = new Heap<Integer, IValue>();
        PrgState prg2 = new PrgState(exeStack2, symTable2, out2, ex2, fileTable2, heap2);
        IRepo repo2 = new Repo("log2.txt");
        repo2.addPrg(prg2);
        Controller ctr2 = new Controller(repo2);

        IStack<IStmt> exeStack3 = new MyStack<IStmt>();
        exeStack3.push(ex3);
        IDict<String, IValue> symTable3 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable3 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out3 = new MyList<IValue>();
        IDict<Integer, IValue> heap3 = new Heap<Integer, IValue>();
        PrgState prg3 = new PrgState(exeStack3, symTable3, out3, ex3, fileTable3, heap3);
        IRepo repo3 = new Repo("log3.txt");
        repo3.addPrg(prg3);
        Controller ctr3 = new Controller(repo3);

        IStack<IStmt> exeStack4 = new MyStack<IStmt>();
        exeStack4.push(ex4);
        IDict<String, IValue> symTable4 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable4 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out4 = new MyList<IValue>();
        IDict<Integer, IValue> heap4 = new Heap<Integer, IValue>();
        PrgState prg4 = new PrgState(exeStack4, symTable4, out4, ex4, fileTable4, heap4);
        IRepo repo4 = new Repo("log4.txt");
        repo4.addPrg(prg4);
        Controller ctr4 = new Controller(repo4);

        IStack<IStmt> exeStack5 = new MyStack<IStmt>();
        exeStack5.push(ex5);
        IDict<String, IValue> symTable5 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable5 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out5 = new MyList<IValue>();
        IDict<Integer, IValue> heap5 = new Heap<Integer, IValue>();
        PrgState prg5 = new PrgState(exeStack5, symTable5, out5, ex5, fileTable5, heap5);
        IRepo repo5 = new Repo("log5.txt");
        repo5.addPrg(prg5);
        Controller ctr5 = new Controller(repo5);

        IStack<IStmt> exeStack6 = new MyStack<IStmt>();
        exeStack6.push(ex6);
        IDict<String, IValue> symTable6 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable6 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out6 = new MyList<IValue>();
        IDict<Integer, IValue> heap6 = new Heap<Integer, IValue>();
        PrgState prg6 = new PrgState(exeStack6, symTable6, out6, ex6, fileTable6, heap6);
        IRepo repo6 = new Repo("log6.txt");
        repo6.addPrg(prg6);
        Controller ctr6 = new Controller(repo6);

        IStack<IStmt> exeStack7 = new MyStack<IStmt>();
        exeStack7.push(ex7);
        IDict<String, IValue> symTable7 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable7 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out7 = new MyList<IValue>();
        IDict<Integer, IValue> heap7 = new Heap<Integer, IValue>();
        PrgState prg7 = new PrgState(exeStack7, symTable7, out7, ex7, fileTable7, heap7);
        IRepo repo7 = new Repo("log7.txt");
        repo7.addPrg(prg7);
        Controller ctr7 = new Controller(repo7);

        IStack<IStmt> exeStack8 = new MyStack<IStmt>();
        exeStack8.push(ex8);
        IDict<String, IValue> symTable8 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable8 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out8 = new MyList<IValue>();
        IDict<Integer, IValue> heap8 = new Heap<Integer, IValue>();
        PrgState prg8 = new PrgState(exeStack8, symTable8, out8, ex8, fileTable8, heap8);
        IRepo repo8 = new Repo("log8.txt");
        repo8.addPrg(prg8);
        Controller ctr8 = new Controller(repo8);

        IStack<IStmt> exeStack9 = new MyStack<IStmt>();
        exeStack9.push(ex9);
        IDict<String, IValue> symTable9 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable9 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out9 = new MyList<IValue>();
        IDict<Integer, IValue> heap9 = new Heap<Integer, IValue>();
        PrgState prg9 = new PrgState(exeStack9, symTable9, out9, ex9, fileTable9, heap9);
        IRepo repo9 = new Repo("log9.txt");
        repo9.addPrg(prg9);
        Controller ctr9 = new Controller(repo9);

        IStack<IStmt> exeStack10 = new MyStack<IStmt>();
        exeStack10.push(ex10);
        IDict<String, IValue> symTable10 = new Dict<String, IValue>();
        IDict<StringValue, BufferedReader> fileTable10 = new Dict<StringValue, BufferedReader>();
        IList<IValue> out10 = new MyList<IValue>();
        IDict<Integer, IValue> heap10 = new Heap<Integer, IValue>();
        PrgState prg10 = new PrgState(exeStack10, symTable10, out10, ex10, fileTable10, heap10);
        IRepo repo10 = new Repo("log10.txt");
        repo10.addPrg(prg10);
        Controller ctr10 = new Controller(repo10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
        menu.addCommand(new RunExample("10", ex10.toString(), ctr10));
        menu.show();

    }
}