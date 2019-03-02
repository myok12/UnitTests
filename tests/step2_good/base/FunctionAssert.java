package step2_good.base;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.CheckReturnValue;

public class FunctionAssert {
    private final Function fn;

    private FunctionAssert(Function fn) {
        this.fn = fn;
    }

    @CheckReturnValue
    public static FunctionAssert assertThat(Function fn) {
        return new FunctionAssert(fn);
    }

    @CheckReturnValue
    public InvokedFunctionAssert whenInvokedWith(Invocation invocation) {
        return new InvokedFunctionAssert(invocation);
    }

    public class InvokedFunctionAssert {
        private final Invocation invocation;

        private InvokedFunctionAssert(Invocation invocation) {
            this.invocation = invocation;
        }

        public void returns(JsonNode jsonNode) {
            Assertions.assertThat(fn.calculate(invocation)).isEqualTo(jsonNode);
        }

        public void isThrowing() {
            assertThatThrownBy(() -> fn.calculate(invocation));
        }
    }
}
