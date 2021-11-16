package Controller;
import Exceptions.StackEmptyException;
import Model.PrgState;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;
import Repo.Repo;
import Repo.IRepo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {

    //constructor
    private Repo repo;


    public Controller(IRepo _repo) {
        repo = (Repo)_repo;
    }

    public void addProgram(PrgState newPrg) {
        repo.addPrg(newPrg);
    }

    public List<Integer> getAddrFromTable(Collection<IValue> tableValues) {
        return tableValues.stream()
                .filter(v -> v.getType() instanceof RefType)
                .map(v -> { RefValue v1 = (RefValue) v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    public Map<Integer, IValue> unsafeGarbageCollector (List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        List<Integer> heapAddr = getAddrFromTable(heap.values());

        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public PrgState oneStep(PrgState state) throws Exception {
        IStack<IStmt> stack = state.getStack();
        if (stack.isEmpty())
            throw new StackEmptyException("PrgState stack is empty.");
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws Exception{
            PrgState prg = this.repo.getCrtPrg();
            IStack<IStmt> stack = prg.getStack();
            repo.logPrgStateExec();
            while (!stack.isEmpty()) {
                oneStep(prg);
                repo.logPrgStateExec();
                prg.getHeapTable().setContent(unsafeGarbageCollector(
                        getAddrFromTable(prg.getSymTable().values()),
                            prg.getHeapTable().getContent()));
                repo.logPrgStateExec();
            }
     }
}
