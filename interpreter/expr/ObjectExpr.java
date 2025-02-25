package interpreter.expr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interpreter.value.ObjectValue;
import interpreter.value.TextValue;
import interpreter.value.Value;

public class ObjectExpr extends Expr{
    
    private List<ObjectItem> items;

    public ObjectExpr(int line, List<ObjectItem> items) {
        super(line);
        this.items = items;
    }


    @Override
    public Value<?> expr() {
        Map<TextValue, Value<?>> mapa = new HashMap<TextValue,Value<?>>();

        for(ObjectItem o : items) {
            mapa.put(new TextValue(o.key), (o.value.expr()));
        }

        return new ObjectValue(mapa);
    }

}
