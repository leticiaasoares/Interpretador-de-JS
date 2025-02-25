package interpreter.expr;

import java.util.List;

import interpreter.InterpreterException;
import interpreter.value.ListValue;
import interpreter.value.NumberValue;
import interpreter.value.ObjectValue;
import interpreter.value.TextValue;
import interpreter.value.Value;


public class AccessExpr extends SetExpr{

    private SetExpr base;
    private Expr index;

                                //var nao é necessariamente var, pode ser um accessExpr
    public AccessExpr(int line, SetExpr base, Expr index)
    {
        super(line);
        this.base = base;
        this.index = index;
    }

    @Override
    public Value<?> expr() { 

        Value<?> v = base.expr();
                                            //lista tem indices numericos positivos
        if((v instanceof ListValue) && (index.expr() instanceof NumberValue)) {

            if(((ListValue)v).eval()) {
                double numeroIndex = NumberValue.convert(index.expr());

                if((numeroIndex >= 0) && ((int)numeroIndex==numeroIndex)) {
                    List<Value<?>> lista = ((ListValue)v).value();

                    if(numeroIndex < lista.size()) {
                        return lista.get((int)numeroIndex);

                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }


        } else if(v instanceof ObjectValue) {

            if(((ObjectValue)v).eval()) {

               String stringIndex = TextValue.convert(index.expr());
               TextValue textIndex = new TextValue(stringIndex);

               if(((ObjectValue)v).value().containsKey(textIndex)) {
                    return ((ObjectValue)v).value().get(textIndex);

               } else {
                    return null;
                }
            } else {
                return null;
            }

        } else {
            throw new InterpreterException(super.getLine());
        }
    }

    @Override
    public void setValue(Value<?> value) {
        
        Value<?> v = base.expr();

        if(base instanceof Variable) {
            boolean b = ((Variable)base).isConstant();
            if(b) throw new InterpreterException(super.getLine());
        }

        if((v instanceof ListValue) && (index.expr() instanceof NumberValue)) {
            int numeroIndex = (int)NumberValue.convert(index.expr());

            if((numeroIndex >= 0) && ((int)numeroIndex==numeroIndex)) {

                if(numeroIndex >= ((ListValue)v).value().size()) {

                    for(int i=((ListValue)v).value().size() ; i<numeroIndex ; i++) {
                        (((ListValue)v).value()).add(i, null);
                    }
                    (((ListValue)v).value()).add(numeroIndex, value);
                } else {  
                    (((ListValue)v).value()).remove((int)numeroIndex);
                    (((ListValue)v).value()).add((int)numeroIndex, value);
                }                
            } 
            

        } else if(v instanceof ObjectValue) {

            String stringIndex = TextValue.convert(index.expr());
            TextValue textIndex = new TextValue(stringIndex);
            ((ObjectValue)v).value().put(textIndex, value);

        } else {
            throw new InterpreterException(super.getLine());
        }

    }

}