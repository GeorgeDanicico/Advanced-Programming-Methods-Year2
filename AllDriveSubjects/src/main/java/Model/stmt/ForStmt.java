package Model.stmt;

import Exceptions.ExpressionException;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.exp.RelationalExp;
import Model.exp.VarExp;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class ForStmt implements IStmt{
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;
    private IStmt stmt;

    public ForStmt(Exp _exp1, Exp _exp2, Exp _exp3, IStmt _stmt) {
        exp1 = _exp1;
        exp2 = _exp2;
        exp3 = _exp3;
        stmt = _stmt;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        IType typ1, typ2, typ3;

        typeEnv.add("v", new IntType());
        typ1 = exp1.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            typ2 = exp2.typecheck(typeEnv);

            if (typ2.equals(new IntType())) {
                typ3 = exp3.typecheck(typeEnv);

                if (typ3.equals(new IntType())) {
                    stmt.typecheck(typeEnv.deepCopy());
                } else throw new ExpressionException("Invalid exception");

            } else throw new ExpressionException("Invalid excepition");
        } else throw new ExpressionException("Invalid exception");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "for(v=" + exp1.toString() + "; v < " + exp2.toString() + "; v=" + exp3.toString()
                + ") " + stmt.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getStack();

        // we checked that the expressions are valud, meaning that we don't have to
        // do the checkings again
        IStmt forStmt = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", exp1),
                        new WhileStmt(new RelationalExp(new VarExp("v"), exp2, "<"),
                                new CompStmt(stmt, new AssignStmt("v", exp3)))
                ));

        stack.push(forStmt);

        return null;
    }
}
