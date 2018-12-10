package aop.advisor;

public interface Advisor {
    String getAdviceBeanName();
    String getExpression();
}
