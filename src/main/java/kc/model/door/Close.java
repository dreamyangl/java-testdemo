package kc.model.door;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Close extends OperationModel{
    @Override
    @Retryable(value = Exception.class, maxAttempts = 4, backoff = @Backoff(delay = 2000L))
    OperationModel action(String action, List<String> list) {
        list.remove("open");
        System.out.println("execute: "+action);
        return this;
    }

    @Recover
    public OperationModel doRecover(Throwable ex) {
        System.out.println("execute close doRecover: " + ex.getLocalizedMessage());
        return this;
    }
}
