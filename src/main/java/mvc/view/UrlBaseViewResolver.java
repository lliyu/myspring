package mvc.view;

/**
 * @author ly
 * @create 2019-01-13 16:00
 **/
public abstract class UrlBaseViewResolver implements ViewResolver {

    private final String REDIRECT_URL_PREFIX = "redirect:";
    private final String FOWARD_URL_PREFIX = "forward:";

    public abstract View resolverView(String viewName);
}
