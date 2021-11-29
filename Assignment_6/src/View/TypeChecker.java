package View;

import Model.adt.Dict;
import Model.adt.IDict;
import Model.stmt.IStmt;
import Model.types.IType;

public class TypeChecker {

    public static void run(IStmt... args) throws Exception{
        for (IStmt arg : args) {
            IDict<String, IType> typeEnv = new Dict<>();
            arg.typecheck(typeEnv);
        }
    }
}
