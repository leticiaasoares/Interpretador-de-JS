package interpreter.expr;

import interpreter.InterpreterException;
import interpreter.value.BoolValue;
import interpreter.value.NumberValue;
import interpreter.value.Value;

public class UnaryExpr extends Expr {
    
    public static enum Op {
        Not,  //tirou op
        Pos,   //tirou op
        Neg,   //tirou op
        PreInc,
        PosInc,
        PreDec,
        PosDec
    }

    private Expr expr;
    private Op op;

    public UnaryExpr(int line, Expr expr, Op op) {
        super(line);
        this.expr = expr;
        this.op = op;
    }

    @Override
    public Value<?> expr() {

        Value<?> v = expr.expr();

        switch (this.op) {
            case Not:   //tirei op
                return notOp(v);
            case Pos:    //tirei op        
                return posOp(v);
            case Neg:      //tirei op
                return negOp(v);
            case PreInc:
                return preIncOp(v);
            case PosInc:
                return posIncOp(v);
            case PreDec:
                return preDecOp(v);
            case PosDec:
            default:
                return posDecOp(v);
        }
    }

    private Value<?> notOp(Value<?> v) {
        boolean b = BoolValue.convert(v);
        return new BoolValue(!b);
    }

    private Value<?> posOp(Value<?> v) {
        double d = NumberValue.convert(v);
        return new NumberValue(d);
    }

    private Value<?> negOp(Value<?> v) {
        double d = NumberValue.convert(v);
        return new NumberValue(-d);
    }

    private Value<?> preIncOp(Value<?> v) {

        double d = NumberValue.convert(v);
        d = d + 1;

        if(expr instanceof SetExpr) {
            SetExpr sexpr = (SetExpr)expr;
            sexpr.setValue(new NumberValue(d));
            return new NumberValue(d);

        } else {
            throw new InterpreterException(super.getLine());
        }
    }

    private Value<?> posIncOp(Value<?> v) {

        double d = NumberValue.convert(v);
        double d1 = d + 1;

        if(expr instanceof SetExpr) {
            SetExpr sexpr = (SetExpr)expr;
            sexpr.setValue(new NumberValue(d1));
            return new NumberValue(d);

        } else {
            throw new InterpreterException(super.getLine());
        }
    }

    private Value<?> preDecOp(Value<?> v) {

        double d = NumberValue.convert(v);
        d = d - 1;

        if(expr instanceof SetExpr) {
            SetExpr sexpr = (SetExpr)expr;
            sexpr.setValue(new NumberValue(d));
            return new NumberValue(d);

        } else {
            throw new InterpreterException(super.getLine());
        }
    }

    private Value<?> posDecOp(Value<?> v) {

        double d = NumberValue.convert(v);
        double d1 = d - 1;

        if(expr instanceof SetExpr) {
            SetExpr sexpr = (SetExpr)expr;
            sexpr.setValue(new NumberValue(d1));
            return new NumberValue(d);

        } else {
            throw new InterpreterException(super.getLine());
        }
    }

}