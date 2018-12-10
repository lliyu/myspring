package aop.advisor;

import aop.pointcut.PointCut;

public interface PointCutAdvisor extends Advisor{
    PointCut getPointCutResolver();
}
