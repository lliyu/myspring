package context.resource;

/**
 * Resource工厂
 */
public interface ResourceFactory {
    Resource getResource(String localtions) throws Exception;
}
