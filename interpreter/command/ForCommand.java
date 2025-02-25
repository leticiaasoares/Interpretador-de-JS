package interpreter.command;

import interpreter.InterpreterException;
import interpreter.expr.Expr;
import interpreter.expr.Variable;
import interpreter.value.ListValue;
import interpreter.value.ObjectValue;
import interpreter.value.Value;

public class ForCommand extends Command{

    private Variable var;
    private Expr expr;
    private Command cmds;
    
    public ForCommand(int line, Variable var, Expr expr, Command cmds) {
        super(line);
        this.var = var;
        this.expr = expr;
        this.cmds = cmds;
    }

    @Override
    public void execute() {

        Value<?> v = expr.expr();

        if(v instanceof ListValue) {

            Value<?> listValue = expr.expr();
            for(Value<?> valueAux : ((ListValue)listValue).value()) {

                var.setValue(valueAux);
                cmds.execute();
            }

        } else if(v instanceof ObjectValue) {

            Value<?> objectValue = expr.expr();
            for(Value<?> valueAux : ((ObjectValue)objectValue).value().keySet()) {

                var.setValue(valueAux);
                cmds.execute();
            }

        } else {
            throw new InterpreterException(super.getLine());
        }
    }
}