package kc.model.door;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Open extends OperationModel{
    @Override
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 100L))
    OperationModel action(String action, List<String> list) {
        list.add("action");
        System.out.println("execute: "+action);
        return this;
    }

    @Recover
    public OperationModel doRecover(Throwable ex) throws ArithmeticException {
        System.out.println("execute open doRecover: " + ex.getLocalizedMessage());
        return this;
    }
}
