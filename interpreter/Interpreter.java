package interpreter;

import java.util.HashMap;
import java.util.Map;

import interpreter.command.Command;
import interpreter.expr.Expr;
import interpreter.expr.Variable;
import interpreter.function.NativeFunction;
import interpreter.value.FunctionValue;
import interpreter.value.ObjectValue;
import interpreter.value.TextValue;
import interpreter.value.Value;
import lexical.Token;

public class Interpreter {

    public final static Environment globals;

    static {
        globals = new Environment();

        Variable console = globals.declare(new Token("console", Token.Type.NAME, null), true);
        Map<TextValue, Value<?>> mapa = new HashMap<TextValue, Value<?>>();

        Environment fn = new Environment(globals);
        Variable params = fn.declare(new Token("params", Token.Type.NAME, null), false);

        mapa.put(new TextValue("random"), new FunctionValue(new NativeFunction(params, NativeFunction.Op.Random)));
        mapa.put(new TextValue("read"), new FunctionValue(new NativeFunction(params, NativeFunction.Op.Read)));
        mapa.put(new TextValue("log"), new FunctionValue(new NativeFunction(params, NativeFunction.Op.Log)));

        console.initialize(new ObjectValue(mapa));

    }

    private Interpreter() {
    }

    public static void interpret(Command cmd) {
        cmd.execute();
    }

    public static void interpret(Expr expr) {
        Value<?> v = expr.expr();
        if (v == null)
            System.out.println("undefined");
        else 
            System.out.println(v);
    }

}